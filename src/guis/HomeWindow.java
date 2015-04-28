
package guis;
import org.apache.commons.io.FileUtils;
import org.eclipse.swt.SWT;
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
import core.GameState;
import core.Main;
import core.RNG;
import core.character;
import entity.ClassEntity;
import entity.DNDEntity;
import entity.RaceEntity;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class HomeWindow {
	private static String version = "Ver0.9.Beta";
	private static DocumentBuilderFactory dbFactory;
	private static DocumentBuilder dBuilder;
	private static Document doc;
	private static Element element;
	private static Display display;
	private Shell shell;
	public static boolean cancel = false;
	public static int monies;
	private HomeWindow hw;
	private static HashMap<String, String> filepaths;

	private StackLayout m_mainWindowLayout;
	private StackLayout charLayout;
	private Composite m_mainWindow;
	private static List charList;
	// the stack layout allows us to navigate from one view to another.
	private Composite mainWindow;

	private StackLayout mainWindowLayout = new StackLayout();

	private final Composite homeScreen;
	private final Composite dungeonMasterScreen;
	private final Composite dungeonGenConfig;
	private Composite characterPanel;
	private Composite characterComp;
	private Composite playerScreen;
	private referencePanel playerScreenReferencePanel;
	private referencePanel dungeonMasterScreenReferencePanel;
	private final Composite dungeonMasterScreenComp;
	private final List dungeonList;

	private boolean dungeonMasterScreenPopulated = false;

	public static int[] baseAbilityScores = new int[6];

	public HomeWindow(Display d) {

		hw = this;
		display = d;
		shell = new Shell(d);
		Image logo = new Image(d, "images/bnb_logo.gif");
		shell.setImage(logo);
		shell.setText("Meta D&D " + version);
		shell.setSize(GameState.DEFAULT_WIDTH, GameState.DEFAULT_HEIGHT);
		shell.setLayout(new GridLayout(3, false));

		DungeonConstants.SAVEDDUNGEONSDIR.mkdir();

		// the stack layout allows us to navigate from one view to another.
		mainWindow = new Composite(shell, SWT.NONE);

		mainWindow.setLayoutData(new GridData(GridData.FILL_BOTH));
		mainWindowLayout = new StackLayout();
		mainWindow.setLayout(mainWindowLayout);

		homeScreen = new Composite(mainWindow, SWT.NONE);
		dungeonMasterScreen = new Composite(mainWindow, SWT.NONE);
		
		dungeonGenConfig = new Composite(mainWindow, SWT.NONE);
		playerScreen = new Composite(mainWindow, SWT.NONE);

		// this is to make the load list of dungeons dynamic.
		///////////////DUNGEON SCREEN STUFF//////////////////
		dungeonMasterScreenComp = new Composite(dungeonMasterScreen, SWT.NONE);
		
		Label dungeonLabel = new Label(dungeonMasterScreenComp, SWT.NONE);
		dungeonLabel.setText("Dungeons:");
		Font dungeonFont = Main.boldFont;
		dungeonLabel.setFont(dungeonFont);
		
		// generate new dungeon
		Button generateButton = new Button(dungeonMasterScreenComp, SWT.PUSH);
		generateButton.setText("Generate New Dungeon");
		generateButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				new MenuBar(shell, hw, GameState.PAGE.DungeonGenerationConfigScreen);
				mainWindowLayout.topControl = dungeonGenConfig;
				mainWindow.layout();
			}
		});
		
		dungeonList = new List(dungeonMasterScreenComp, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		///////////////DUNGEON SCREEN STUFF//////////////////
		
		createPageContent();
		
		new MenuBar(shell, this, GameState.PAGE.HomeScreen); //Add menu bar to windows like this
		
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

		GridLayout dungeonMasterScreenLayout = new GridLayout(5, true);
		dungeonMasterScreenLayout.marginLeft = 10;
		dungeonMasterScreen.setLayout(dungeonMasterScreenLayout);

		GridLayout playerScreenLayout = new GridLayout(4, true);
		playerScreen.setLayout(playerScreenLayout);

		GridLayout dungeonGenConfigLayout = new GridLayout(3, true);
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
		
		characterPanel = new Composite(playerScreen, SWT.NONE);
		characterComp = new Composite(characterPanel, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 3;
		characterPanel.setLayoutData(gridData);
		charLayout = new StackLayout();
		characterPanel.setLayout(charLayout);
		
		charLayout.topControl = characterComp;

		GridLayout characterCompLayout = new GridLayout(1, true);
		characterComp.setLayout(characterCompLayout);
		
		GridData chargridData = new GridData(SWT.FILL, SWT.BEGINNING, true , false);
		Label playerLabel = new Label(characterComp, SWT.NONE);
		playerLabel.setText("Characters:");
		Font playerFont = Main.boldFont;
		playerLabel.setFont(playerFont);
		playerLabel.setLayoutData(chargridData);

		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);

		charList = new List(characterComp, SWT.V_SCROLL);
		chargridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		chargridData.verticalSpan = 4;
		charList.setLayoutData(chargridData);
		charList.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e){
				//TODO populate the Character sheet 
				
				int charInd = charList.getSelectionIndex();
				if (charInd < 0 || charInd > charList.getSize().x) {
					return;
				}
				String charToLoad = charList.getSelection()[0];
				String path = filepaths.get(charToLoad);
				System.out.println(path);
				String[] args = {path};			
				CharacterMain test = new CharacterMain(args, characterPanel, shell);
				charLayout.topControl = test.getMainWindow();
				characterPanel.layout();
				shell.setBounds((int)(display.getBounds().width * .05), (int)(display.getBounds().height * .05), (int)(display.getBounds().width * .9), (int)(display.getBounds().height * .8));
				
			}

			@Override
			//leave blank, but must have
			public void widgetSelected(SelectionEvent e) {}
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

		// set screens
		characterComp.layout();
		characterPanel.layout();
		playerScreen.layout();


		///////////////////PLAYER SCREEN//////////////////////////

		///////////////////DUNGEON MASTERS SCREEN//////////////////////////

		populateDungeonMasterScreen();

		///////////////////DUNGEON SCREEN//////////////////////////    


		///////////////////DUNGEON GENCONFIG////////////////////////// 
		new Label(dungeonGenConfig, SWT.NONE);
		Composite sliderComposite = new Composite(dungeonGenConfig, SWT.CENTER);
		GridLayout sliderCompositeLayout = new GridLayout(2, false);
		GridData sliderCompositeGridData = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		//sliderCompositeGridData.minimumWidth = 300;
		sliderComposite.setLayoutData(sliderCompositeGridData);
		sliderComposite.setLayout(sliderCompositeLayout);
		
		Label sizeLabel = new Label(sliderComposite, SWT.NONE);
		sizeLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		sizeLabel.setText("Size of Dungeon:");
		
		final Scale sizeSlider = new Scale(sliderComposite, SWT.NONE);
		sizeSlider.setIncrement(1);
		sizeSlider.setMaximum(DungeonConstants.MAX_DUNGEON_SIZE);
		sizeSlider.setMinimum(DungeonConstants.MIN_DUNGEON_SIZE);
		sizeSlider.setSelection(30);
		GridData sizeSliderGD = new GridData(SWT.LEFT, SWT.FILL, true, true);
		sizeSlider.setLayoutData(sizeSliderGD);
		
		Label densityLabel = new Label(sliderComposite, SWT.NONE);
		densityLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		densityLabel.setText("Density of Passable Terrain:");
		
		final Scale densitySlider = new Scale(sliderComposite, SWT.NULL);
		densitySlider.setIncrement(1);
		densitySlider.setMaximum(50);
		densitySlider.setMinimum(10);
		densitySlider.setSelection(20);
		densitySlider.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true));
		
		Label squareSizeLabel = new Label(sliderComposite, SWT.NONE);
		squareSizeLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		squareSizeLabel.setText("Size of Dungeon Tile:");

		final Scale squareSizeSlider = new Scale(sliderComposite, SWT.NULL);
		squareSizeSlider.setIncrement(5);
		squareSizeSlider.setMaximum(50);
		squareSizeSlider.setMinimum(10);
		squareSizeSlider.setSelection(30);
		squareSizeSlider.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false));
		sliderComposite.pack();
		


		Button cancelButton = new Button(sliderComposite, SWT.NONE);
		cancelButton.setText("Cancel");
		cancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				new MenuBar(shell, hw, GameState.PAGE.DungeonMasterScreen);
				navigateToDungeonMasterScreen();
			}
		});
		
		// confirm generation configuration
		Button confirmButton = new Button(sliderComposite, SWT.NONE);
		confirmButton.setText("Confirm");
		confirmButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int sizeSelection = sizeSlider.getSelection();
				int densitySelection = densitySlider.getSelection();
				double density = 1 - ((double)densitySelection/100);
				int squareSize = squareSizeSlider.getSelection();
				new DungeonViewer(hw, sizeSelection, density, squareSize);
			}
		});

		dungeonGenConfig.pack();
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
				navigateToDungeonMasterScreen();
			}
		});
		homeScreen.pack();
		mainWindowLayout.topControl = homeScreen;

	}
	
	public void navigateToHomeScreen() {
		new MenuBar(shell, this, GameState.PAGE.HomeScreen);
		GameState.currentPage = GameState.PAGE.HomeScreen;
		this.shell.setSize(GameState.DEFAULT_WIDTH, GameState.DEFAULT_HEIGHT);
		this.m_mainWindowLayout.topControl = this.homeScreen;
		this.m_mainWindow.layout();
	}

	public void navigateToDungeonMasterScreen() {
		new MenuBar(shell, this, GameState.PAGE.DungeonMasterScreen);
		populateDungeonMasterScreen();
		GameState.currentPage = GameState.PAGE.DungeonMasterScreen;
		this.shell.setSize(GameState.DEFAULT_WIDTH, GameState.DEFAULT_HEIGHT);
		this.m_mainWindowLayout.topControl = this.dungeonMasterScreen;
		this.m_mainWindow.layout();
	}

	public void navigateToPlayerScreen() {
		new MenuBar(shell, this, GameState.PAGE.PlayerScreen);
		GameState.currentPage = GameState.PAGE.PlayerScreen;
		this.shell.setSize(GameState.DEFAULT_WIDTH, GameState.DEFAULT_HEIGHT);
		this.m_mainWindowLayout.topControl = this.playerScreen;
		charLayout.topControl = characterComp;
		characterPanel.layout();
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

	public void populateDungeonMasterScreen() {
		if (!dungeonMasterScreenPopulated) {
			
			GridData dungeonMasterScreenCompGD = new GridData(SWT.FILL, SWT.FILL, true, true);
			dungeonMasterScreenCompGD.horizontalSpan = 3;
			dungeonMasterScreenComp.setLayoutData(dungeonMasterScreenCompGD);
			
			GridLayout dungeonMasterScreenCompLayout = new GridLayout(1, false);
			dungeonMasterScreenComp.setLayout(dungeonMasterScreenCompLayout);
			
			// placeholder labels take up columns 2 and 3 in the grid.
			new Label(dungeonMasterScreenComp, SWT.NONE);  
			new Label(dungeonMasterScreenComp, SWT.NONE); 

			
			GridData listGD = new GridData(SWT.FILL, SWT.FILL, true, true);
			listGD.widthHint = 400;
			listGD.heightHint = 500;
			dungeonList.setLayoutData(listGD);
			
	
		
			
			
			
			// load dungeon
			Button loadButton = new Button(dungeonMasterScreenComp, SWT.PUSH);
			loadButton.setText("Load Dungeon");
			loadButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					if (dungeonList.getSelectionIndex() < 0) {
						return;
					}
					
					String dungeonToLoad = dungeonList.getSelection()[0];
					String svgToLoad = "file:///";
					svgToLoad += DungeonConstants.SAVEDDUNGEONSDIR.toString() + "//" + dungeonToLoad;
					
					new DungeonViewer(svgToLoad);
	
				}
			});
			
			Button dungeonBack = new Button(dungeonMasterScreenComp, SWT.PUSH);
			dungeonBack.setText("Back");
			dungeonMasterScreenCompGD = new GridData(SWT.LEFT, SWT.CENTER, true, true);
			dungeonBack.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					
					navigateToHomeScreen();
					
				}
			});
			
			Composite BUTTONS = new Composite(dungeonMasterScreen, SWT.NONE);
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
						int armor = rng.GetRandomInteger(0, armorNum - 1);
						
						Collection<DNDEntity> armorCol = Main.gameState.armor.values();
						Iterator<DNDEntity> itr = armorCol.iterator();
						ArrayList<DNDEntity> armorArray = new ArrayList<DNDEntity>();
						while (itr.hasNext()) {
							armorArray.add((DNDEntity) itr.next());
						}

						armorArray.get(armor).toTooltipWindow();
					}else if(choice == 2){// Generate Weapons
						int weapon = rng.GetRandomInteger(0, weaponsNum- 1);
						
						Collection<DNDEntity> weaponCol = Main.gameState.weapons.values();
						Iterator<DNDEntity> itr = weaponCol.iterator();
						ArrayList<DNDEntity> weaponArray = new ArrayList<DNDEntity>();
						while (itr.hasNext()) {
							weaponArray.add((DNDEntity) itr.next());
						}
						
						weaponArray.get(weapon).toTooltipWindow();
					}else if(choice == 3){// Generate Item
						int item = rng.GetRandomInteger(0, itemNum- 1);
						
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
			dungeonMasterScreenReferencePanel = new referencePanel(dungeonMasterScreen);
			Composite ds_rp = dungeonMasterScreenReferencePanel.getRefPanel();
			gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
			ds_rp.setLayoutData(gridData);
			
			dungeonMasterScreen.pack();
			dungeonMasterScreenPopulated = true;
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
