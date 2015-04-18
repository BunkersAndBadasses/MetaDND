
package guis;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.commons.io.FileUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.DnDie;
import core.DungeonConstants;
import core.DungeonGenerator;
import core.GameState;
import core.GridMapper;
import core.Main;
import core.character;
import entity.ClassEntity;
import entity.RaceEntity;

import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/*
 * TODO
 * 
 * fix menu bar home screen link - opens a new home window instead of navigating back to home in the same window
 * but if no homewindow is open, open one (only one can be open at a time)
 */

public class HomeWindow {

	private static DocumentBuilderFactory dbFactory;
	private static DocumentBuilder dBuilder;
	private static Document doc;
	private static Element element;
	private static Display display;
	private Device dev;
	private Shell shell;
	private static final int WIDTH = 900;
	private static final int HEIGHT = 700;
	public static boolean cancel = false;
	private HomeWindow hw;
	private static HashMap<String, String> filepaths;

	private StackLayout m_mainWindowLayout;
	private StackLayout charLayout;
	private Composite m_mainWindow;
	private Composite m_dungeonScreen;
	private Composite m_playerScreen;
	private Composite view;
	private static List charList;
	// the stack layout allows us to navigate from one view to another.
	private final Composite mainWindow;

	private StackLayout mainWindowLayout = new StackLayout();

	private final Composite homeScreen;
	private final Composite dungeonScreen;
	private final Composite dungeonViewer;
	private final Composite dungeonGenConfig;
	private final Composite playerScreen;

	public static int[] baseAbilityScores = new int[6];

	public HomeWindow(Display d) {

		hw = this;
		display = d;
		shell = new Shell(d);
		Image logo = new Image(d, "images/bnb_logo.gif");
		shell.setImage(logo);
		shell.setText("Meta D&D");
		shell.setSize(WIDTH, HEIGHT);
		shell.setLayout(new GridLayout(3, false));

		DungeonConstants.SAVEDDUNGEONSDIR.mkdir();

		new MenuBar(shell); //Add menu bar to windows like this

		// the stack layout allows us to navigate from one view to another.
		mainWindow = new Composite(shell, SWT.NONE);

		mainWindow.setLayoutData(new GridData(GridData.FILL_BOTH));
		mainWindowLayout = new StackLayout();
		mainWindow.setLayout(mainWindowLayout);

		homeScreen = new Composite(mainWindow, SWT.NONE);
		dungeonScreen = new Composite(mainWindow, SWT.NONE);
		dungeonViewer = new Composite(mainWindow, SWT.EMBEDDED);
		dungeonGenConfig = new Composite(mainWindow, SWT.NONE);
		playerScreen = new Composite(mainWindow, SWT.NONE);

		createPageContent();

		run();
	}

	public void run() {
		center(shell);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private static void center(Shell shell) {

		Rectangle bds = shell.getDisplay().getBounds();

		Point p = shell.getSize();

		int nLeft = (bds.width - p.x) / 2;
		int nTop = (bds.height - p.y) / 2;

		shell.setBounds(nLeft, nTop, p.x, p.y);
	}


	private void createPageContent() {



		this.m_mainWindow = mainWindow;
		this.m_mainWindowLayout = mainWindowLayout;
		this.m_dungeonScreen = dungeonScreen;
		this.m_playerScreen = playerScreen;

		// this grid layout size allows us to have permanent centering of these buttons,
		// regardless of user resize.
		GridLayout homeScreenLayout = new GridLayout(6, true);
		homeScreen.setLayout(homeScreenLayout);

		GridLayout dungeonScreenLayout = new GridLayout(1, true);
		dungeonScreenLayout.marginLeft = 10;
		dungeonScreen.setLayout(dungeonScreenLayout);

		GridLayout playerScreenLayout = new GridLayout(4, true);
		playerScreen.setLayout(playerScreenLayout);

		GridLayout dungeonGenConfigLayout = new GridLayout(2, true);
		dungeonGenConfig.setLayout(dungeonGenConfigLayout);

		///////////////////HOME SCREEN//////////////////////////

		// placeholder labels take up columns 1 and 2 in the grid.
		new Label(homeScreen, SWT.NONE);  
		new Label(homeScreen, SWT.NONE);  

		// default size of the buttons is 200 by 100
		// the buttons occupy columns 3 and 4 in the grid.
		GridData playersGD = new GridData();
		playersGD.grabExcessHorizontalSpace = true;
		playersGD.grabExcessVerticalSpace = true;
		playersGD.widthHint = 200;
		playersGD.heightHint = 100;

		Button playersButton = new Button(homeScreen, SWT.PUSH); 
		playersButton.setLayoutData(playersGD);
		playersButton.setText("Players");
		playersButton.setSize(new Point(300, 400));
		playersButton.pack();

		// each element should have its own griddata object.
		GridData dungeonMastersGD = new GridData();
		dungeonMastersGD.grabExcessHorizontalSpace = true;
		dungeonMastersGD.grabExcessVerticalSpace = true;
		dungeonMastersGD.widthHint = 200;
		dungeonMastersGD.heightHint = 100;

		Button dungeonMastersButton = new Button(homeScreen, SWT.PUSH);
		dungeonMastersButton.setLayoutData(dungeonMastersGD);
		dungeonMastersButton.setText("Dungeon Masters");
		dungeonMastersButton.pack();

		// placeholder labels for columns 5 and 6
		new Label(homeScreen, SWT.NONE);  
		new Label(homeScreen, SWT.NONE);  

		///////////////////HOME SCREEN//////////////////////////

		///////////////////PLAYER SCREEN//////////////////////////

		Composite characterComp = new Composite(playerScreen, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 3;
		characterComp.setLayoutData(gridData);

		GridLayout characterCompLayout = new GridLayout(1, false);
		characterComp.setLayout(characterCompLayout);
		GridData chargridData = new GridData(SWT.FILL, SWT.FILL, false, false);


		Label playerLabel = new Label(characterComp, SWT.NONE);
		playerLabel.setText("Characters:");
		Font playerFont = Main.boldFont;
		playerLabel.setFont(playerFont);
		playerLabel.setLayoutData(chargridData);

		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		view = new Composite(characterComp, SWT.NONE);
		charLayout = new StackLayout();
		view.setLayoutData(gridData);
		view.setLayout(charLayout);

		//TODO change this to stack layout? 
		// How are we gonna open the character sheet?
		charList = new List(view, SWT.V_SCROLL);
		chargridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		charList.setLayoutData(chargridData);
		charList.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e){
				//TODO populate the Character sheet 

			}

			@Override
			//leave blank, but must have
			public void widgetSelected(SelectionEvent e) {}
		});

		charLayout.topControl = charList;
		view.layout();

		//TODO Do we want a button, or do we want to double click charaacter?
		Button loadChar = new Button(characterComp, SWT.PUSH);
		loadChar.setText("Load Character");
		chargridData = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		loadChar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (charList.getSelectionIndex() < 0) {
					return;
				}
				String name = charList.getItem(charList.getSelectionIndex());
				String path = filepaths.get(name);
				String[] arg = {path};
				shell.close();

				//TODO 
				//this crashes the program due to characters not having an 
				// image file linked in their xml
				CharacterMain.main(arg);
			}
		});

		Button addChar = new Button(characterComp, SWT.PUSH);
		addChar.setText("Add Character");
		chargridData = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		addChar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				new CharacterWizard(shell.getDisplay());

			}
		});

		Button deleteChar = new Button(characterComp, SWT.PUSH);
		deleteChar.setText("Delete Character");
		chargridData = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		deleteChar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (charList.getSelectionIndex() < 0) {
					return;
				}
				String charDescription = charList.getItem(charList.getSelectionIndex());
				String delim = "[,]";
				String[] tokens = charDescription.split(delim);
				//TODO delete a char
				deleteCharacter(tokens[0]);
			}
		});

		// Call the seach panel composite
		referencePanel rp = new referencePanel(playerScreen);
		Composite refPanel = rp.getRefPanel();
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		refPanel.setLayoutData(gridData);


		playerScreen.layout();


		///////////////////PLAYER SCREEN//////////////////////////

		///////////////////DUNGEON SCREEN//////////////////////////

		Label dungeonLabel = new Label(dungeonScreen, SWT.NONE);
		dungeonLabel.setText("Dungeons:");
		Font dungeonFont = Main.boldFont;
		dungeonLabel.setFont(dungeonFont);

		// placeholder labels take up columns 2 and 3 in the grid.
		new Label(dungeonScreen, SWT.NONE);  
		new Label(dungeonScreen, SWT.NONE); 

		// generate new dungeon
		Button generateButton = new Button(dungeonScreen, SWT.PUSH);
		generateButton.setText("Generate New");
		generateButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				new MenuBarDungeon(shell, hw);
				mainWindowLayout.topControl = dungeonGenConfig;
				mainWindow.layout();
			}
		});


		dungeonScreen.pack();

		///////////////////DUNGEON SCREEN//////////////////////////    

		///////////////////DUNGEON VIEWER//////////////////////////   

		final JSVGCanvas svgCanvas = new JSVGCanvas();

		// embed the swing element in the swt composite
		java.awt.Frame fileTableFrame = SWT_AWT.new_Frame(dungeonViewer);
		java.awt.Panel panel = new java.awt.Panel(new java.awt.BorderLayout());

		JScrollPane jsp = new JScrollPane(svgCanvas);
		jsp.setViewportView(svgCanvas);
		//jsp.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//jsp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//jsp.setWheelScrollingEnabled(true);
		fileTableFrame.add(panel);
		panel.add(jsp);

		///////////////////DUNGEON VIEWER////////////////////////// 

		///////////////////DUNGEON GENCONFIG////////////////////////// 
		Label sizeLabel = new Label(dungeonGenConfig, SWT.NONE);
		sizeLabel.setText("Size of Dungeon:");
		Label densityLabel = new Label(dungeonGenConfig, SWT.NONE);
		densityLabel.setText("Density of Passable Terrain:");

		final Scale sizeSlider = new Scale(dungeonGenConfig, SWT.NULL);
		sizeSlider.setIncrement(1);
		sizeSlider.setMaximum(DungeonConstants.MAX_DUNGEON_SIZE);
		sizeSlider.setMinimum(DungeonConstants.MIN_DUNGEON_SIZE);
		sizeSlider.setSelection(30);
		sizeSlider.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));

		final Scale densitySlider = new Scale(dungeonGenConfig, SWT.NULL);
		densitySlider.setIncrement(1);
		densitySlider.setMaximum(50);
		densitySlider.setMinimum(10);
		densitySlider.setSelection(20);
		dungeonGenConfig.pack();

		Button cancelButton = new Button(dungeonGenConfig, SWT.NONE);
		cancelButton.setText("Cancel");
		cancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				new MenuBarDungeon(shell, hw);
				mainWindowLayout.topControl = dungeonScreen;
				mainWindow.layout();
			}
		});

		// confirm generation configuration
		Button confirmButton = new Button(dungeonGenConfig, SWT.NONE);
		confirmButton.setText("Confirm");
		confirmButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int sizeSelection = sizeSlider.getSelection();
				int densitySelection = densitySlider.getSelection();
				double density = 1 - ((double)densitySelection/100);

				// This seems like magic, and it kind of is, but just go with it.
				int sizeOfSquare;
				if (sizeSelection >= 10 && sizeSelection < 20) {
					sizeOfSquare = 50;
				}
				else if (sizeSelection >= 20 && sizeSelection < 30) {
					sizeOfSquare = 40;
				}
				else if (sizeSelection >= 30 && sizeSelection < 40) {
					sizeOfSquare = 30;
				}
				else if (sizeSelection >= 40 && sizeSelection < 50) {
					sizeOfSquare = 30;
				}
				else if (sizeSelection >= 50 && sizeSelection < 60) {
					sizeOfSquare = 20;
				}
				else if (sizeSelection >= 60 && sizeSelection < 100) {
					sizeOfSquare = 10;
				}
				else {
					sizeOfSquare = 30;
				}

				//System.out.println(sizeSelection);
				//System.out.println(density);

				DungeonGenerator rdg = new DungeonGenerator(sizeSelection, density);
				rdg.GenerateDungeon();
				rdg.printDungeon(true);

				GridMapper gm = new GridMapper(DungeonConstants.SAVEDDUNGEONSDIR + "\\generatedDungeon.bnb", sizeOfSquare); //TODO: make this not hard coded.
				gm.generateSVG();

				svgCanvas.setURI("file:///" + DungeonConstants.SAVEDDUNGEONSDIR.toString() + "\\generatedDungeon.svg");

				GameState.PAGE_NUMBER = 2;
				new MenuBarDungeon(shell, hw);
				mainWindowLayout.topControl = dungeonViewer;
				mainWindow.layout();
			}
		});


		/////////////////////NESTED BUTTON LISTENERS////////////////////////
		// dungeonMaster button
		//		loadButton
		//      

		playersButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				//TODO call function to populate charList with characters
				loadCharacters();
				playerScreen.pack();
				navigateToPlayerScreen();
			}
		});

		dungeonMastersButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				final List dungeonList = new List(dungeonScreen, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
				int counter = 0;

				// populate the list
				for (String s: DungeonConstants.SAVEDDUNGEONSDIR.list()) {
					if (s.contains(".svg")) {
						dungeonList.add(s);
						counter++;
					}
				}
				for (int i = counter; i < 20; i++) {
					dungeonList.add("");
				}

				// make the list look good.
				GridData listGD = new GridData();
				listGD.grabExcessHorizontalSpace = true;
				listGD.grabExcessVerticalSpace = true;
				listGD.widthHint = 400;
				listGD.heightHint = 500;
				dungeonList.setLayoutData(listGD);

				// placeholder labels to make it look gooder
				new Label(dungeonScreen, SWT.NONE);  
				new Label(dungeonScreen, SWT.NONE); 
				new Label(dungeonScreen, SWT.NONE);  
				new Label(dungeonScreen, SWT.NONE); 

				// load dungeon
				Button loadButton = new Button(dungeonScreen, SWT.PUSH);
				loadButton.setText("Load Dungeon");
				loadButton.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						String dungeonToLoad = dungeonList.getSelection()[dungeonList.getSelectionIndex()];
						if (dungeonToLoad.equals("")){
							return;
						}
						String toSet = "file:///";
						toSet += DungeonConstants.SAVEDDUNGEONSDIR.toString() + "//" + dungeonToLoad;
						svgCanvas.setURI(toSet);

						new MenuBarDungeon(shell, hw);

						shell.setMaximized(true);
						GameState.PAGE_NUMBER = 2;
						mainWindowLayout.topControl = dungeonViewer;
						mainWindow.layout();

					}
				});

				dungeonScreen.pack();
				navigateToDungeonScreen();
			}
		});
		homeScreen.pack();
		mainWindowLayout.topControl = homeScreen;

	}

	public void navigateToDungeonScreen() {
		new MenuBarDungeon(shell, hw);
		GameState.PAGE_NUMBER = 1;
		this.m_mainWindowLayout.topControl = this.m_dungeonScreen;
		this.m_mainWindow.layout();
	}

	public void navigateToPlayerScreen() {
		GameState.PAGE_NUMBER = 3;
		this.m_mainWindowLayout.topControl = this.m_playerScreen;
		this.m_mainWindow.layout();
	}

	//Parse through the user folders, and load the characters into
	//	the charList on the player window
	public static void loadCharacters(){

		charList.removeAll();
		File CHARDIR = new File(System.getProperty("user.dir") + "//" + "User Data" + "//Character");
		File[] files = new File(CHARDIR.getPath()).listFiles();

		if(files != null){
			character[] CharacterNames = new character[files.length];
			int numCharacters = 0;
			filepaths = new HashMap<String, String>();
			String tmpName;

			for(int i = 0; i < files.length; i++){

				if(files[i].isDirectory()){

					CharacterNames[numCharacters] = new character();

					//TODO load the characters
					try{
						File CHARXML = new File(CHARDIR.getPath() + "//" + files[i].getName() + 
								"//" + files[i].getName() + ".xml");
						dbFactory = DocumentBuilderFactory.newInstance();
						dBuilder = dbFactory.newDocumentBuilder();
						doc = dBuilder.parse(CHARXML);
						doc.getDocumentElement().normalize();

						NodeList nodes = doc.getElementsByTagName("Character");

						Node node = nodes.item(0);

						if (node.getNodeType() == Node.ELEMENT_NODE) {
							element = (Element) node;
							String name = getValue("Name", element);
							CharacterNames[numCharacters].setName(name);  
							CharacterNames[numCharacters].setLevel(Integer.parseInt(getValue("Level", element)));
							CharacterNames[numCharacters].setCharClass((ClassEntity)Main.gameState.classes.get(getValue("Class", element)));
							CharacterNames[numCharacters].setCharRace((RaceEntity)Main.gameState.races.get(getValue("Race", element)));
						}
						tmpName = CharacterNames[numCharacters].getName() + ", Level " 
								+ CharacterNames[numCharacters].getLevel() + " " 
								+ CharacterNames[numCharacters].getCharRace().getName()
								+ " " + CharacterNames[numCharacters].getCharClass().getName();

						charList.add(tmpName);
						filepaths.put(tmpName, CHARXML.getPath());

						numCharacters ++;

					} catch (Exception ex) {
						ex.printStackTrace();
					}

				}

			}
		}
	}

	public static void deleteCharacter(String name){
		//TODO pull up the delete confirmation, showing the name of the character
		//		and reminds that favRolls with the char will also be deleted.
		//	with cancel/confirm buttons
		// if confirm, delete the folder that the character file is in.

		final Shell deleteChar = new Shell(display);
		deleteChar.setText("Delete");
		//deleteFile.setSize(250, 150);
		center(deleteChar);

		Label confirm = new Label(deleteChar, SWT.NONE);
		confirm.setLocation(20,40);
		confirm.setText("Are you sure you want to delete " + name + "?");
		confirm.pack();

		Button cancel = new Button(deleteChar, SWT.PUSH);
		cancel.setBounds(10,90,80,30);
		cancel.setText("Cancel");
		cancel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				deleteChar.dispose();
			}
		});

		Button saveFinal = new Button(deleteChar, SWT.PUSH);
		saveFinal.setBounds(160,90,80,30);
		saveFinal.setText("Delete");
		saveFinal.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				File charDirectory = new File(System.getProperty("user.dir") + "//" + "User Data" + "//Character" + "//" + name);
				try {
					FileUtils.deleteDirectory(charDirectory);
					loadCharacters();
					deleteChar.dispose();
				} catch (IOException e) {
					System.out.println("Could not delete Character.");
				}
				deleteChar.dispose();
			}
		});

		deleteChar.pack();

		deleteChar.open();
		while (!deleteChar.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}


	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

	//	public static void main(String[] args) {
	//		Display display = new Display();
	//		HomeWindow hw = new HomeWindow(display);
	//		display.dispose();
	//	}
}
