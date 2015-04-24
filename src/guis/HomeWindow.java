
package guis;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.commons.io.FileUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.DungeonConstants;
import core.DungeonGenerator;
import core.GameState;
import core.GridMapper;
import core.Main;
import core.RNG;
import core.character;

import entity.ClassEntity;
import entity.DNDEntity;
import entity.RaceEntity;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JScrollPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class HomeWindow {
	private static String version = "Ver0.8.Alpha";
	private static DocumentBuilderFactory dbFactory;
	private static DocumentBuilder dBuilder;
	private static Document doc;
	private static Element element;
	private static Display display;
	private Shell shell;
	private static final int WIDTH = 900;
	private static final int HEIGHT = 700;
	public static boolean cancel = false;
	public static int monies;
	private HomeWindow hw;
	private static HashMap<String, String> filepaths;

	private StackLayout m_mainWindowLayout;
	private StackLayout charLayout;
	private Composite m_mainWindow;
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
	private referencePanel playerScreenReferencePanel;
	private referencePanel dungeonScreenReferencePanel;
	private final Composite dungeonScreenComp;
	private final List dungeonList;

	private final JSVGCanvas svgCanvas = new JSVGCanvas();
	private boolean dungeonScreenPopulated = false;

	public static int[] baseAbilityScores = new int[6];

	public HomeWindow(Display d) {

		hw = this;
		display = d;
		shell = new Shell(d);
		Image logo = new Image(d, "images/bnb_logo.gif");
		shell.setImage(logo);
		shell.setText("Meta D&D " + version);
		shell.setSize(WIDTH, HEIGHT);
		shell.setLayout(new GridLayout(3, false));

		DungeonConstants.SAVEDDUNGEONSDIR.mkdir();

		

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

		// this is to make the load list of dungeons dynamic.
		///////////////DUNGEON SCREEN STUFF//////////////////
		dungeonScreenComp = new Composite(dungeonScreen, SWT.NONE);
		
		Label dungeonLabel = new Label(dungeonScreenComp, SWT.NONE);
		dungeonLabel.setText("Dungeons:");
		Font dungeonFont = Main.boldFont;
		dungeonLabel.setFont(dungeonFont);
		
		// generate new dungeon
		Button generateButton = new Button(dungeonScreenComp, SWT.PUSH);
		generateButton.setText("Generate New Dungeon");
		generateButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				new MenuBarDungeon(shell, hw);
				mainWindowLayout.topControl = dungeonGenConfig;
				mainWindow.layout();
			}
		});
		
		dungeonList = new List(dungeonScreenComp, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		///////////////DUNGEON SCREEN STUFF//////////////////
		
		createPageContent();
		
		new MenuBarHomeScreen(shell, this); //Add menu bar to windows like this
		
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

		// this grid layout size allows us to have permanent centering of these buttons,
		// regardless of user resize.
		GridLayout homeScreenLayout = new GridLayout(6, true);
		homeScreen.setLayout(homeScreenLayout);

		GridLayout dungeonScreenLayout = new GridLayout(5, true);
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
				String name = charList.getItem(charList.getSelectionIndex());
				String path = filepaths.get(name);
				String[] arg = {path};
				shell.close();

				//TODO 
				//this crashes the program due to characters not having an 
				// image file linked in their xml
				CharacterMain.main(arg);
			}

			@Override
			//leave blank, but must have
			public void widgetSelected(SelectionEvent e) {}
		});

		charLayout.topControl = charList;
		view.layout();

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
		
		Button playerBack = new Button(characterComp, SWT.PUSH);
		playerBack.setText("Back");
		chargridData = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		playerBack.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				
				navigateToHomeScreen();
				
			}
		});

		// Call the seach panel composite
		playerScreenReferencePanel = new referencePanel(playerScreen);
		Composite ps_rp = playerScreenReferencePanel.getRefPanel();
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		ps_rp.setLayoutData(gridData);


		playerScreen.layout();


		///////////////////PLAYER SCREEN//////////////////////////

		///////////////////DUNGEON SCREEN//////////////////////////

		populateDungeonScreen();

		///////////////////DUNGEON SCREEN//////////////////////////    

		///////////////////DUNGEON VIEWER//////////////////////////   

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
				
				int sizeOfSquare = 50;
				int totalSize = sizeSelection*sizeOfSquare ;
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				while (totalSize + 50> screenSize.getWidth()) {
					sizeOfSquare -= 5;
					totalSize = sizeSelection*sizeOfSquare;
				}
				while (totalSize + 50>  screenSize.getHeight()) {
					sizeOfSquare -= 5;
					totalSize = sizeSelection*sizeOfSquare;
				}
				
				shell.setSize(totalSize + 50, totalSize + 75);

				DungeonGenerator rdg = new DungeonGenerator(sizeSelection, density);
				rdg.GenerateDungeon();
				rdg.printDungeon(true);

				GridMapper gm = new GridMapper(DungeonConstants.SAVEDDUNGEONSDIR.toString() + "//generatedDungeon.bnb", sizeOfSquare); 
				gm.generateSVG();

				String toSet = "file:///";
				toSet += DungeonConstants.SAVEDDUNGEONSDIR.toString() + "//generatedDungeon.svg";
				svgCanvas.setURI(toSet);
				//svgCanvas.setURI("file:///" + DungeonConstants.SAVEDDUNGEONSDIR.toString() + "//generatedDungeon.svg");

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
				navigateToDungeonScreen();
			}
		});
		homeScreen.pack();
		mainWindowLayout.topControl = homeScreen;

	}
	
	public void navigateToHomeScreen() {
		new MenuBarHomeScreen(shell, hw);
		GameState.PAGE_NUMBER = 0;
		this.shell.setSize(WIDTH, HEIGHT);
		this.m_mainWindowLayout.topControl = this.homeScreen;
		this.m_mainWindow.layout();
	}

	public void navigateToDungeonScreen() {
		new MenuBarDungeon(shell, hw);
		populateDungeonScreen();
		GameState.PAGE_NUMBER = 1;
		this.shell.setSize(WIDTH, HEIGHT);
		this.m_mainWindowLayout.topControl = this.dungeonScreen;
		this.m_mainWindow.layout();
	}

	public void navigateToPlayerScreen() {
		new MenuBarHomeScreen(shell, hw);
		GameState.PAGE_NUMBER = 3;
		this.shell.setSize(WIDTH, HEIGHT);
		this.m_mainWindowLayout.topControl = this.playerScreen;
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
		final GridLayout gridLay = new GridLayout();
		gridLay.makeColumnsEqualWidth = false;
		gridLay.horizontalSpacing = 3;
		gridLay.numColumns = 3;
		deleteChar.setLayout(gridLay);
		deleteChar.setText("Delete");
		//deleteFile.setSize(250, 150);
		center(deleteChar);

		new Label(deleteChar, SWT.NONE); 
		
		Label confirm = new Label(deleteChar, SWT.NONE);
		GridData gridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		gridData.horizontalSpan = 3;
		confirm.setLayoutData(gridData);
		confirm.setText("Are you sure you want to delete " + name + "?");

		Button cancel = new Button(deleteChar, SWT.PUSH);
		cancel.setText("Cancel");
		gridData = new GridData(SWT.RIGHT, SWT.BEGINNING, false, false);
		gridData.horizontalIndent = 5;
		cancel.setLayoutData(gridData);
		cancel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				deleteChar.dispose();
			}
		});

		new Label(deleteChar, SWT.NONE); 
		
		Button deleteFinal = new Button(deleteChar, SWT.PUSH);
		deleteFinal.setText("Delete");
		gridData = new GridData(SWT.RIGHT, SWT.END, false, false);
		gridData.horizontalIndent = 5;
		deleteFinal.setLayoutData(gridData);
		deleteFinal.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				String charName = name.replaceAll("[^A-Za-z0-9]", "");
				File charDirectory = new File(System.getProperty("user.dir")
						+ "//" + "User Data" + "//Character" + "//DND" + charName);
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

	public void populateDungeonScreen() {
		if (!dungeonScreenPopulated) {
			
			GridData dungeonScreenCompGD = new GridData(SWT.FILL, SWT.FILL, true, true);
			dungeonScreenCompGD.horizontalSpan = 3;
			dungeonScreenComp.setLayoutData(dungeonScreenCompGD);
			
			GridLayout dungeonScreenCompLayout = new GridLayout(1, false);
			dungeonScreenComp.setLayout(dungeonScreenCompLayout);
			
			// placeholder labels take up columns 2 and 3 in the grid.
			new Label(dungeonScreenComp, SWT.NONE);  
			new Label(dungeonScreenComp, SWT.NONE); 

			
			GridData listGD = new GridData();
			listGD.grabExcessHorizontalSpace = true;
			listGD.grabExcessVerticalSpace = true;
			listGD.widthHint = 400;
			listGD.heightHint = 500;
			dungeonList.setLayoutData(listGD);
			
			// placeholder labels to make it look gooder
			new Label(dungeonScreenComp, SWT.NONE);  
			new Label(dungeonScreenComp, SWT.NONE); 
			new Label(dungeonScreenComp, SWT.NONE);  
			
	
			// load dungeon
			Button loadButton = new Button(dungeonScreenComp, SWT.PUSH);
			loadButton.setText("Load Dungeon");
			loadButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					if (dungeonList.getSelectionIndex() < 0) {
						return;
					}
					
					String dungeonToLoad = dungeonList.getSelection()[0];
					String toSet = "file:///";
					toSet += DungeonConstants.SAVEDDUNGEONSDIR.toString() + "//" + dungeonToLoad;
					svgCanvas.setURI(toSet);
	
					new MenuBarDungeon(shell, hw);
					GameState.PAGE_NUMBER = 2;
					mainWindowLayout.topControl = dungeonViewer;
					mainWindow.layout();
	
				}
			});
			
			Button dungeonBack = new Button(dungeonScreenComp, SWT.PUSH);
			dungeonBack.setText("Back");
			dungeonScreenCompGD = new GridData(SWT.LEFT, SWT.CENTER, true, true);
			dungeonBack.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					
					navigateToHomeScreen();
					
				}
			});
			
			Composite BUTTONS = new Composite(dungeonScreen, SWT.NONE);
			GridLayout buttonsLayout = new GridLayout();
			buttonsLayout.makeColumnsEqualWidth = true;
			buttonsLayout.verticalSpacing = 15;
			buttonsLayout.numColumns = 1;
			BUTTONS.setLayout(buttonsLayout);
			
			Button Loot = new Button(BUTTONS, SWT.PUSH);
			Loot.setText("Generate Loot");
			GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, true);
			Loot.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					RNG rng = new RNG();
					
					//TODO Loot generation
					int armorNum = Main.gameState.armor.size();
					int weaponsNum = Main.gameState.weapons.size();
					int itemNum = Main.gameState.items.size();
					
					if (armorNum == 0 || weaponsNum == 0 || itemNum == 0) {
						return;
					}
					
					int choice = rng.GetRandomInteger(1, 4);
					
					if(choice == 1){//Generate Armor
						int armor = rng.GetRandomInteger(0, armorNum) - 1;
						
						Collection<DNDEntity> armorCol = Main.gameState.armor.values();
						Iterator<DNDEntity> itr = armorCol.iterator();
						ArrayList<DNDEntity> armorArray = new ArrayList<DNDEntity>();
						while (itr.hasNext()) {
							armorArray.add((DNDEntity) itr.next());
						}

						armorArray.get(armor).toTooltipWindow();
					}else if(choice == 2){// Generate Weapons
						int weapon = rng.GetRandomInteger(0, weaponsNum) - 1;
						
						Collection<DNDEntity> weaponCol = Main.gameState.weapons.values();
						Iterator<DNDEntity> itr = weaponCol.iterator();
						ArrayList<DNDEntity> weaponArray = new ArrayList<DNDEntity>();
						while (itr.hasNext()) {
							weaponArray.add((DNDEntity) itr.next());
						}
						
						weaponArray.get(weapon).toTooltipWindow();
					}else if(choice == 3){// Generate Item
						int item = rng.GetRandomInteger(0, itemNum) - 1;
						
						Collection<DNDEntity> itemCol = Main.gameState.items.values();
						Iterator<DNDEntity> itr = itemCol.iterator();
						ArrayList<DNDEntity> itemArray = new ArrayList<DNDEntity>();
						while (itr.hasNext()) {
							itemArray.add((DNDEntity) itr.next());
						}
						
						itemArray.get(item).toTooltipWindow();
					}else{// Generate Gold
						monies = rng.GetRandomInteger(1, 500);
						
						final Shell lootGold = new Shell(display);
						lootGold.setText("Loot: Gold");
						//saveName.setSize(300, 200);
						center(lootGold);
						GridLayout layout = new GridLayout();
						layout.makeColumnsEqualWidth = true;
						layout.horizontalSpacing = 3;
						layout.numColumns = 3;
						lootGold.setLayout(layout);
						
						// this appears when there is an empty save
						Label lootLabel = new Label(lootGold, SWT.NONE);
						lootLabel.setText("Gold Pieces: " + monies);
						GridData gridData = new GridData(SWT.CENTER, SWT.CENTER, true, false);
						gridData.horizontalIndent = 5;
						gridData.horizontalSpan = 3;
						lootLabel.setLayoutData(gridData);
						lootLabel.pack();
						
						Button okay = new Button(lootGold, SWT.PUSH);
						okay.setText("Close");
						gridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
						gridData.horizontalIndent = 5;
						okay.setLayoutData(gridData);
						okay.addListener(SWT.Selection, new Listener() {
							public void handleEvent(Event event) {
								lootGold.dispose();
								
							}
						});
						okay.pack();
						
						new Label(lootGold, SWT.NONE); 
						
						Button reRoll = new Button(lootGold, SWT.PUSH);
						reRoll.setText("ReRoll");
						gridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
						gridData.horizontalIndent = 5;
						reRoll.setLayoutData(gridData);
						reRoll.addListener(SWT.Selection, new Listener() {
							public void handleEvent(Event event) {
								monies = rng.GetRandomInteger(1, 500);
								lootLabel.setText("Gold Pieces: " + monies);
								lootLabel.pack();
								//lootGold.pack();
							}
						});
						reRoll.pack();
						
						lootGold.pack();
						
						lootGold.open();
						while (!lootGold.isDisposed()) {
							if (!display.readAndDispatch()) {
								display.sleep();
							}
						}
					}
					
				}
			});
			
			Button Trap = new Button(BUTTONS, SWT.PUSH);
			Trap.setText("Generate Trap");
			gridData = new GridData(SWT.FILL, SWT.CENTER, true, true);
			Trap.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					RNG rng = new RNG();
					
					//TODO Trap generation
					int trapNum = Main.gameState.traps.size();
					if (trapNum == 0) {
						return;
					}
					int trap = rng.GetRandomInteger(0, trapNum) - 1;
					
					Collection<DNDEntity> trapCol = Main.gameState.traps.values();
					Iterator<DNDEntity> itr = trapCol.iterator();
					ArrayList<DNDEntity> trapArray = new ArrayList<DNDEntity>();
					while (itr.hasNext()) {
						trapArray.add((DNDEntity) itr.next());
					}
					
					trapArray.get(trap).toTooltipWindow();
					
				}
			});
			
			Button Monster = new Button(BUTTONS, SWT.PUSH);
			Monster.setText("Generate Monster");
			gridData = new GridData(SWT.FILL, SWT.CENTER, true, true);
			Monster.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					RNG rng = new RNG();
					
					//TODO Monster generation
					int monsterNum = Main.gameState.monsters.size();
					if (monsterNum == 0) {
						return;
					}
					int monster = rng.GetRandomInteger(0, monsterNum) - 1;
					
					Collection<DNDEntity> monsterCol = Main.gameState.monsters.values();
					Iterator<DNDEntity> itr = monsterCol.iterator();
					ArrayList<DNDEntity> monsterArray = new ArrayList<DNDEntity>();
					while (itr.hasNext()) {
						monsterArray.add((DNDEntity) itr.next());
					}
					
					monsterArray.get(monster).toTooltipWindow();
					
				}
			});
			
			// Call the search panel composite
			dungeonScreenReferencePanel = new referencePanel(dungeonScreen);
			Composite ds_rp = dungeonScreenReferencePanel.getRefPanel();
			gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
			ds_rp.setLayoutData(gridData);
			
			dungeonScreen.pack();
			dungeonScreenPopulated = true;
		}
		
		dungeonList.removeAll();
		// populate the list
		for (String s: DungeonConstants.SAVEDDUNGEONSDIR.list()) {
			if (s.contains(".svg")) {
				dungeonList.add(s);
			}
		}
		// make the list look good.
		
		
	}
	
	//	public static void main(String[] args) {
	//		Display display = new Display();
	//		HomeWindow hw = new HomeWindow(display);
	//		display.dispose();
	//	}
}
