package guis;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;

import core.CharFeat;
import core.CharItem;
import core.CharSkill;
import core.GameState;
import core.Main;
import core.RNG;
import core.character;
import entity.AbilityEntity;
import entity.ClassEntity;
import entity.DNDEntity;
import entity.FeatEntity;
import entity.ItemEntity;
import entity.RaceEntity;
import entity.SkillEntity;
import entity.WeaponEntity;



/*
 * TODO: 
 * 
 * launch wizards (add custom)
 * monk - add wis to ac
 * barbarian illiteracy
 * speak language skill
 * barbarian: rage per day
 * 
 * change skill list - numbers in line, headers
 * back button
 * starting at level > 1
 * adding custom skills
 * 
 * special abilities being saved as null
 */

public class CharacterWizard {

	int pageNum = -1;

	private Device dev;
	private Display display;
	private Shell shell;
	private StackLayout wizLayout;
	private ManualCharacter manChar;
	private static RNG randomgene;
	public int wizPageNum = -1;
	public boolean cancel = false;
	public boolean cancelOpen = false;
	private Shell areYouSureShell;
	public boolean[] wizPageCreated = { false, false, false, false,
		false, false, false, false};
	
	private Composite wizPanel;
	
	private StackLayout homeLayout;
	private Composite homePanel;
	private Composite home;

	private ArrayList<Composite> wizPages;

	public ArrayList<Object> wizs = new ArrayList<Object>();

	private character character;
	private CharacterWizard cw = this;

	public int[] baseAbilityScores = new int[6];

	public CharacterWizard(Display d) {
		display = d;
		shell = new Shell(d);
		shell.setImage(new Image(display, "images/bnb_logo.gif"));
		shell.setText("Create New Character");
		shell.setSize(GameState.CHARWIZ_WIDTH, GameState.CHARWIZ_HEIGHT);
		FillLayout shellLayout = new FillLayout();
		shell.setLayout(shellLayout);
		character = new character();
		wizPages = new ArrayList<Composite>();
        randomgene = new RNG();
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

	public static void center(Shell shell) {

		Rectangle bds = shell.getDisplay().getBounds();

		Point p = shell.getSize();

		int nLeft = (bds.width - p.x) / 2;
		int nTop = (bds.height - p.y) / 2;

		shell.setBounds(nLeft, nTop, p.x, p.y);
	}

	private void createPageContent() {

		// the first thing that shows up is homePanel, which has a layout that
		// includes the home, wizPanel, manualPanel, and randomPanel pages
		// it rests on the home page
		// 'panel' in the name implies it holds a collection of pages
		// clicking on 'interactive character wizard' button changes the top
		// control of homePanel to wizPanel. the top control of wizPanel is
		// wiz1, which is the first page of the character wizard. clicking
		// back/next only changes the top control of wizPanel.
		// clicking cancel changes the top control of homePanel back to home
		// and resets the character wizard

		//////////////////// HOME PANEL SETUP ////////////////////////////

		homePanel = new Composite(shell, SWT.NONE);
		homePanel.setBounds(0, 0, GameState.CHARWIZ_WIDTH, GameState.CHARWIZ_HEIGHT);
		homeLayout = new StackLayout();
		homePanel.setLayout(homeLayout);

		//////////////////// HOME SCREEN SETUP ////////////////////////////

		// this screen is what is first seen when the window opens. 
		// it contains the buttons that link to the character wizard, the manual
		// character entering, and the random character generation
		home = new Composite(homePanel, SWT.BORDER);
		home.setLocation(homePanel.getLocation());
		home.setSize(homePanel.getSize().x, homePanel.getSize().y - 25);
		GridLayout gridLayout = new GridLayout(4, true);
		home.setLayout(gridLayout);
		
		GridData gd;
		
		Label homeLabel = new Label(home, SWT.NONE);
		homeLabel.setText("Let's create a character!");
		Font font1 = new Font(display, new FontData(display.getSystemFont().getFontData()[0].getName(), 24,
				SWT.BOLD));
		homeLabel.setFont(font1);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 4;
		homeLabel.setLayoutData(gd);
		homeLabel.pack();

		//
		
		new Label(home, SWT.NONE).setLayoutData(new GridData());
		
		Label homeLabel2 = new Label(home, SWT.NONE);
		homeLabel2.setText("Choose a method:");
		Font font2 = new Font(homeLabel.getDisplay(), new FontData(display.getSystemFont().getFontData()[0].getName(), 18,
				SWT.BOLD));
		homeLabel2.setFont(font2);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		homeLabel2.setLayoutData(gd);

		new Label(home, SWT.NONE).setLayoutData(new GridData());
		
		//
		
		new Label(home, SWT.NONE).setLayoutData(new GridData());
		
		Button wizardButton = new Button(home, SWT.PUSH | SWT.CENTER);
		wizardButton.setText("Interactive Character Wizard");
		wizardButton.setFont(font2);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 2;
		wizardButton.setLayoutData(gd);

		new Label(home, SWT.NONE).setLayoutData(new GridData());

		//
		
		new Label(home, SWT.NONE).setLayoutData(new GridData());

//		Button manualButton = new Button(home, SWT.PUSH);
//		manualButton.setText("Manual");
		Font font3 = new Font(homeLabel.getDisplay(), new FontData(display.getSystemFont().getFontData()[0].getName(), 18,
				SWT.NONE));
//		manualButton.setFont(font3);
//		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
//		manualButton.setLayoutData(gd);

		Button randomButton = new Button(home, SWT.PUSH);
		randomButton.setText("Random");
		randomButton.setFont(font3);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 2;
		randomButton.setLayoutData(gd);

		new Label(home, SWT.NONE).setLayoutData(new GridData());

		//
		
		new Label(home, SWT.NONE).setLayoutData(new GridData());
		new Label(home, SWT.NONE).setLayoutData(new GridData());
		new Label(home, SWT.NONE).setLayoutData(new GridData());
		new Label(home, SWT.NONE).setLayoutData(new GridData());

		//home.pack();
		home.layout();

		// set home as the first screen viewed when new character window  is launched
		homeLayout.topControl = home;

		shell.layout();
		
		// ///////////////// WIZARD PANEL SETUP ///////////////////////////

		wizPanel = new Composite(homePanel, SWT.BORDER);
		wizPanel.setBounds(0, 0, GameState.CHARWIZ_WIDTH, GameState.CHARWIZ_HEIGHT);
		wizPanel.setBackground(new Color(dev, 255, 0, 0));
		wizLayout = new StackLayout();
		wizPanel.setLayout(wizLayout);


		// ///////////////// MANUAL PANEL SETUP ///////////////////////////

		GridLayout manualWizardLayout = new GridLayout();
		manualWizardLayout.makeColumnsEqualWidth = false;
		manualWizardLayout.horizontalSpacing = 3;
		manualWizardLayout.numColumns = 1;
		
		final ScrolledComposite manualWizard = new ScrolledComposite(homePanel, SWT.V_SCROLL | SWT.BORDER);
		manualWizard.setLayout(manualWizardLayout);
		manualWizard.setBounds(0, 0, GameState.CHARWIZ_WIDTH, GameState.CHARWIZ_HEIGHT);
		manualWizard.setAlwaysShowScrollBars(true);
		wizPanel.setBounds(0, 0, GameState.CHARWIZ_WIDTH, (int) (GameState.CHARWIZ_HEIGHT * (.75)));
		GridData manData = new GridData(SWT.FILL, SWT.FILL, true, true);
		manChar = new ManualCharacter(manualWizard);
		Composite manPage = manChar.getManualCharacter();
		manPage.setLayoutData(manData);
		manPage.layout();
		manualWizard.layout();
		homePanel.layout();
		
//		
//		// Call the seach panel composite
//		playerScreenReferencePanel = new referencePanel(playerScreen);
//		Composite ps_rp = playerScreenReferencePanel.getRefPanel();
//		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		ps_rp.setLayoutData(gridData);
		
//		Label csManual = new Label(manualWizard, SWT.BOLD);
//		csManual.setLocation(GameState.CHARWIZ_WIDTH/2-50, GameState.CHARWIZ_HEIGHT/2);
//		csManual.setText("Coming Soon!");
//		csManual.pack();
		//createCancelButton(manualWizard, home, homePanel, homeLayout);
		


		// ////////////////// RANDOM PANEL SETUP //////////////////////////

		final Composite randomPanel = new Composite(homePanel, SWT.BORDER);
		final StackLayout randomLayout = new StackLayout();
		randomPanel.setLayout(randomLayout);
		//final Composite randomPage1 = new Composite(randomPanel, SWT.NONE);
		//randomPage1.setSize(manualPanel.getSize());
		//randomLayout.topControl = randomPage1;
		//randomPanel.layout();
		//Label csRandom = new Label(randomPage1, SWT.BOLD);
		//csRandom.setLocation(GameState.CHARWIZ_WIDTH/2-50, GameState.CHARWIZ_HEIGHT/2);
		//csRandom.setText("Coming Soon!");
		//csRandom.pack();
		//createCancelButton(randomPage1, home, homePanel, homeLayout);


		// ////////////////// HOME BUTTON LISTENERS ///////////////////////
		
		wizardButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// create the first page (creates next pages at runtime)
				instantiateWizPages();
				Wiz1 wiz1 = new Wiz1(cw, dev, GameState.CHARWIZ_WIDTH, GameState.CHARWIZ_HEIGHT, wizPanel, wizLayout,  wizPages);
				wizs.add(wiz1);
				wizLayout.topControl = wizPages.get(0);
				wizPanel.layout();
				homeLayout.topControl = wizPanel;
				homePanel.layout();
			}
		});
//		manualButton.addListener(SWT.Selection, new Listener() {
//			public void handleEvent(Event event) {
//				homeLayout.topControl = manualWizard;
//				homePanel.layout();
//			}
//		});
		randomButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				randomgeneration(randomPanel, randomLayout, homePanel);
				//homeLayout.topControl = randomPanel;
				//homePanel.layout();
			}

		});

	}

    /**
     * This method is called when the random generation of character is called.
     * It generate a new character
     * The character's description will always be: This is a random generation character.
     * The other user specified stuff will leave as <empty>, but the critical information
     * especially numbers, we will fill it out.
     * The other information of character, if you want to change it, should be done
     * @author Innocentius
     */
	@SuppressWarnings("unused")
	private void randomgeneration(final Composite randomPanel, final StackLayout randomLayout, 
			final Composite homePanel) 
	{
		character = new character();
		character.setLevel(1);
		character.setExp(0);
		Collection<DNDEntity> racecol = Main.gameState.races.values();
		character.setCharRace((RaceEntity) racecol.toArray()[randomgene.GetRandomInteger(0, racecol.size()-1)]);
		Collection<DNDEntity> classcol = Main.gameState.classes.values();
		character.setCharClass((ClassEntity) classcol.toArray()[randomgene.GetRandomInteger(0, classcol.size()-1)]);
		//Deity
		String[] deities = { 
				"Boccob(N): god of magic", 
				"Corellon Larethian(CG): god of the elves", 
				"Ehlonna(NG): goddess of the woodlands", 
				"Erythnul(CE): god of slaughter", 
				"Fharlanghn(N): god of roads", 
				"Garl Glittergold(NG): god of the gnomes",
				"Gruumsh(CE): chief god of the orcs", 
				"Heironeous(LG): god of valor", 
				"Hextor(LE): god of tyranny",
				"Kord(CG): god of strength",
				"Moradin(LG): god of the dwarves",
				"Nerull(NE): god of death",
				"Obad-Hai(N): god of nature",
				"Olidammara(CN): god of rogues",
				"Pelor(NG): god of the sun",
				"St. Cuthbert(LN): god of retribution", 
				"Vecna(NE): god of secrets", 
				"Wee Jas(LN): goddess of death and magic",
				"Yondalla(LG): goddess of the halflings" };
		if(character.getCharClass().getName().equalsIgnoreCase("Cleric"))
		{
		character.setDeity(deities[randomgene.GetRandomInteger(0, deities.length - 1)]);
		}
		//Alignment
				String[] Alignments = {"Lawful Good", "Lawful Neutral", "Lawful Evil", 
						"Neutral Good", "True Neutral", "Neutral Evil", "Chaotic Good", 
						"Chaotic Neutral", "Chaotic Evil"};
				character.setAlignment(Alignments[randomgene.GetRandomInteger(0, Alignments.length - 1)]);
				//TODO add consistency with deity

		//Size
		character.setSize(character.getCharRace().getSize());
		//Age
		//110-175 Elf
		//15-35 Human
		//40-125 Dwarf
		//40-100 Gnome
		//20-62 Half-elf
		//14-30 Half-orc
		//20-50 Halfling
		RaceEntity racec = character.getCharRace();
		boolean x = false;
		if(racec.getName().equalsIgnoreCase("Elf"))
			character.setAge(String.valueOf(randomgene.GetRandomInteger(110, 175)));
		else if(racec.getName().equalsIgnoreCase("Human"))
			character.setAge(String.valueOf(randomgene.GetRandomInteger(15, 35)));
		else if(racec.getName().equalsIgnoreCase("Dwarf"))
			character.setAge(String.valueOf(randomgene.GetRandomInteger(40, 125)));
		else if(racec.getName().equalsIgnoreCase("Gnome"))
			character.setAge(String.valueOf(randomgene.GetRandomInteger(40, 100)));
		else if(racec.getName().equalsIgnoreCase("Half-elf"))
			character.setAge(String.valueOf(randomgene.GetRandomInteger(20, 62)));
		else if(racec.getName().equalsIgnoreCase("Half-orc"))
			character.setAge(String.valueOf(randomgene.GetRandomInteger(14, 30)));
		else if(racec.getName().equalsIgnoreCase("Halfling"))
			character.setAge(String.valueOf(randomgene.GetRandomInteger(20, 50)));
		else// TODO create a race field of AGE BOUNDARY
		{
			character.setAge(String.valueOf(randomgene.GetRandomInteger(18, 50)));
		}
		//Gender
		String[] gend = {"Male", "Female"};
		character.setGender(gend[randomgene.GetRandomInteger(0, gend.length - 1)]);
		//Height
		int height = 0;
		if(racec.getName().equalsIgnoreCase("Elf"))
			height = randomgene.GetRandomInteger(55, 65);
		else if(racec.getName().equalsIgnoreCase("Human"))
			height = randomgene.GetRandomInteger(55, 78);
		else if(racec.getName().equalsIgnoreCase("Dwarf"))
			height = randomgene.GetRandomInteger(45, 53);
		else if(racec.getName().equalsIgnoreCase("Gnome"))
			height = randomgene.GetRandomInteger(36, 44);
		else if(racec.getName().equalsIgnoreCase("Half-elf"))
			height = randomgene.GetRandomInteger(55, 71);
		else if(racec.getName().equalsIgnoreCase("Half-orc"))
			height = randomgene.GetRandomInteger(55, 82);
		else if(racec.getName().equalsIgnoreCase("Halfling"))
			height = randomgene.GetRandomInteger(32, 40);
		else// TODO create a race field of height BOUNDARY
		{
			height = randomgene.GetRandomInteger(55, 78);
		}
		String heightString = "";
		heightString += Integer.toString(height/12);
		heightString += "'";
		heightString += Integer.toString(height % 12);
		heightString += "\"";
		character.setHeight(heightString);
		//Weight
		int weight = 0;
		if(racec.getName().equalsIgnoreCase("Elf"))
			weight = randomgene.GetRandomInteger(80, 160);
		else if(racec.getName().equalsIgnoreCase("Human"))
			weight = randomgene.GetRandomInteger(125, 280);
		else if(racec.getName().equalsIgnoreCase("Dwarf"))
			weight = randomgene.GetRandomInteger(85, 230);
		else if(racec.getName().equalsIgnoreCase("Gnome"))
			weight = randomgene.GetRandomInteger(35, 50);
		else if(racec.getName().equalsIgnoreCase("Half-elf"))
			weight = randomgene.GetRandomInteger(80, 230);
		else if(racec.getName().equalsIgnoreCase("Half-orc"))
			weight = randomgene.GetRandomInteger(110, 400);
		else if(racec.getName().equalsIgnoreCase("Halfling"))
			weight = randomgene.GetRandomInteger(25, 40);
		else// TODO create a race field of height BOUNDARY
		{
			weight = randomgene.GetRandomInteger(125, 280);
		}
		String weightString = Integer.toString(weight)+ " lbs";
		character.setWeight(weightString);
		//Eyes will fill by the user
		//Hair will fill by the user
		//Skin will fill by the user
		//Description
		character.setDescription("This is a random generation character");
		//Ability score
		do
		{
		character.setAbilityScores(randomgene.GetRandomInteger(3, 18), randomgene.GetRandomInteger(3, 18), randomgene.GetRandomInteger(3, 18), 
				randomgene.GetRandomInteger(3, 18), randomgene.GetRandomInteger(3, 18), randomgene.GetRandomInteger(3, 18));
		
		} while(checkreroll(character));
		//HP and remaining HP
		character.setHitPoints(Integer.parseInt(character.getCharClass().getHitDie().split("d")[1]) + character.getAbilityModifiers()[2]);
		if(character.getHitPoints() < 3)
		{
			character.setHitPoints(3);
		}
		//Languages -- pick three
		ArrayList<String> langs = new ArrayList<String>();
		langs.add("Common");
		if(character.getCharClass().getName().equalsIgnoreCase("Druid"))
		{
			langs.add("Druidic");	
		}
		character.setLanguages(langs);
		//Gold
		character.setGP(randomgene.GetRandomInteger(50, 200));
		//Feats
		Collection<DNDEntity> featcol = Main.gameState.feats.values();
		//Random 1 feat
		CharFeat randomToAdd = new CharFeat((FeatEntity) featcol.toArray()[randomgene.GetRandomInteger(0, featcol.size() - 1)]);
		System.out.println("Random feat is " + randomToAdd.getFeat().getName());
		System.out.println("Char meets prereqs is " + Wiz5.checkPrerequisites(character.getFeats(), randomToAdd, character));
		boolean done = false;
		do {
			if(Wiz5.checkPrerequisites(character.getFeats(), randomToAdd, character)){
				for(int i = 0; i < character.getFeats().size(); i++) {
					if (character.getFeats().get(i).getFeat().getName().equals(randomToAdd.getFeat().getName())) {
						// feat found - check if that feat can be added multiple times
						if (!randomToAdd.getFeat().canHaveMultiple()) {
							// feat cannot be added multiple times
							System.out.println("Feat already added");

						}
						else {
							// feat can be added multiple times
							if (character.getFeats().get(i).getFeat().canStack()) {
								// feat benefits can stack - increment count of feat
								character.getFeats().get(i).incCount();
								done = true;
								break;
							}
							else {
								// feat benefits cannot stack - check if the exact same feat is added
								if (character.getFeats().get(i).getSpecial().equals(randomToAdd.getSpecial())) {
									System.out.println("Feat already added");
								}
							}
						}
					}
				}
				if(done)
					break;
				character.addFeat(randomToAdd);
				System.out.println("Feat added");
				done = true;
			}
			else{ 
					randomToAdd = new CharFeat((FeatEntity) featcol.toArray()[randomgene.GetRandomInteger(0, featcol.size() - 1)]);
					System.out.println("New random feat is " + randomToAdd.getFeat().getName());
					System.out.println("Char meets prereqs is " + Wiz5.checkPrerequisites(character.getFeats(), randomToAdd, character));
			}
		}while(!done);
		System.out.println("------------------------------------");
			
		//Bonus feat
		
		// add automatic feats (like armor/weapon proficiency)
		String[] autoFeats = character.getCharClass().getBonusFeats();
		for (int i = 0; i < autoFeats.length; i ++) {
			if (autoFeats[i].indexOf('[') != -1) {
				String special = autoFeats[i].substring(autoFeats[i].indexOf('[')+1, autoFeats[i].indexOf(']'));
				String featName = autoFeats[i].substring(0, autoFeats[i].indexOf('[')-1);
				CharFeat feat = new CharFeat((FeatEntity)Main.gameState.feats.get(featName), special);
				character.addFeat(feat);
				//System.out.println("adding " + feat.getFeat().getName() + " [" + feat.getSpecial() + "]");
			} else {
				CharFeat feat = new CharFeat((FeatEntity)Main.gameState.feats.get(autoFeats[i]));
				character.addFeat(feat);
				//System.out.println("feat to add: " + autoFeats[i]);
				//System.out.println("feat: " + feat.getFeat());
				//System.out.println("adding " + feat.getFeat().getName());
			}
		}
		//Special Ability
		Collection<DNDEntity> abilitycol = Main.gameState.abilities.values();
		String[] sa = character.getCharRace().getSpecialAbilities();
		for(String a : sa)
		{
			AbilityEntity c = null;
			for(Object b : abilitycol.toArray())
			{
				c = (AbilityEntity)b;
				if(c.getName().equalsIgnoreCase(a))
				{
					break;
				}
			}
			character.addSpecialAbility(c);
		}
		//Skill
		Collection<DNDEntity> skillcol = Main.gameState.skills.values();
		for(DNDEntity skill: skillcol)
		{
			character.addSkill(new CharSkill((SkillEntity)skill, character));
		}
		//Spells is <empty> for now
		Collection<DNDEntity> spellcol = Main.gameState.spells.values();
		//wizard get all level 0 spell + (3 + INT modifier) of level 1 spell
		//Sorcerer get 4 level 0 spell + 2 level 1 spell
		//Prepared spells are <empty>
		//Items is randomized
		Collection<DNDEntity> itemcol = Main.gameState.items.values();
		//0~7 kind of items
		//1~10 per item
		int itemk = randomgene.GetRandomInteger(0, 7);
		int itemn;
		for(int i = 0; i < itemk; i++)
		{
			itemn = randomgene.GetRandomInteger(1, 10);
			CharItem a =  new CharItem((ItemEntity) itemcol.toArray()[randomgene.GetRandomInteger(0, itemcol.toArray().length - 1)]);
			a.setCount(itemn);
			character.addItem(a);
		}
		//Weapons
		Collection<DNDEntity> weaponcol = Main.gameState.weapons.values();
		for(int i = 0; i < 3; i++)
		{
			ItemEntity a = (ItemEntity) weaponcol.toArray()[randomgene.GetRandomInteger(0, weaponcol.toArray().length - 1)];
			CharItem b = new CharItem(a); 
			character.addWeapon(b);
			if(i == 0)
			{
				character.setPrimaryWeapon((WeaponEntity)a);
			}
			if(i == 1)
			{
				character.setSecondaryWeapon((WeaponEntity)a);
			}
		}
		//Armor
		Collection<DNDEntity> armorcol = Main.gameState.armor.values();
		for(int i = 0; i < 2; i++)
		{
			ItemEntity a = (ItemEntity) armorcol.toArray()[randomgene.GetRandomInteger(0, armorcol.toArray().length - 1)];
			if(a.getName().contains("Shield"))
			{
				i--;
			}
			else
			{
				CharItem b = new CharItem(a);
				character.addArmor(b);
				if(i == 0)
				{
					character.setCurrArmor(a);;
				}
			}
		}
		//Shield
		Boolean shieldadded = false;
		do
		{
			ItemEntity a = (ItemEntity) armorcol.toArray()[randomgene.GetRandomInteger(0, armorcol.toArray().length - 1)];
			if(a.getName().contains("Shield"))
			{
				shieldadded = true;
				character.addShield(new CharItem(a));
				character.setCurrShield(a);
			}
		}while(shieldadded == false);
		//Notes will be <empty>
		Shell newshell = new Shell(display);
		newshell.setSize(GameState.CHARWIZ_WIDTH, GameState.CHARWIZ_HEIGHT);
		ScrolledComposite sc = new ScrolledComposite(newshell, SWT.V_SCROLL);
		sc.setBounds(0, 0, GameState.CHARWIZ_WIDTH - 20, GameState.CHARWIZ_HEIGHT - 50);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		
		Composite c = new Composite(sc, SWT.NONE);
		sc.setContent(c);
		c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));	
		 Button cancelButton = new Button(c, SWT.PUSH);
			cancelButton.setText("Cancel");
			cancelButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					newshell.close();
				}
				
			});
			
			cancelButton.pack();
			Text namebox = new Text(c, SWT.NONE);
			namebox.setMessage("Enter Name HERE!");
			namebox.pack();
			
			Button saveButton = new Button(c, SWT.PUSH);
			saveButton.setText("Save");
			saveButton.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event event)
				{
					if(namebox.getText().equals(""))
					{
						namebox.setBackground(display.getSystemColor(SWT.COLOR_RED));
						return;
					}
					else if(namebox.getText().length() > 200 ){
						//TODO ADD a error message box
						//TODO Add check for non-alphabatic word
						final Shell saveNameError = new Shell(display);
						saveNameError.setText("Character Name Error");
						//saveName.setSize(300, 200);
						center(saveNameError);
						GridLayout layout = new GridLayout();
						layout.makeColumnsEqualWidth = false;
						layout.horizontalSpacing = 3;
						layout.numColumns = 3;
						saveNameError.setLayout(layout);
						
						// this appears when there is an empty save
						Label badSaveFinal = new Label(saveNameError, SWT.NONE);
						badSaveFinal.setForeground(new Color(dev,255,0,0));
						badSaveFinal.setText("Invalid Save: Character name is capped at 200 chars.");
						GridData gridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
						gridData.horizontalIndent = 5;
						badSaveFinal.setLayoutData(gridData);
						badSaveFinal.pack();
						
						Button okay = new Button(saveNameError, SWT.PUSH);
						okay.setText("Okay");
						gridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
						gridData.horizontalIndent = 5;
						okay.setLayoutData(gridData);
						okay.addListener(SWT.Selection, new Listener() {
							public void handleEvent(Event event) {
								saveNameError.dispose();
								
							}
						});
						okay.pack();
						
						saveNameError.pack();
						
						saveNameError.open();
						while (!saveNameError.isDisposed()) {
							if (!display.readAndDispatch()) {
								display.sleep();
							}
						}
						
						return;
					}
					//check for special character
					if(namebox.getText().matches("[^a-zA-Z0-9 ]"))
					{
						namebox.setBackground(display.getSystemColor(SWT.COLOR_RED));
						return;
					}
					character.setName(namebox.getText());
					Wiz8.saveCharacter(character);
					newshell.close();
					shell.dispose();
				}
			});
			saveButton.pack();
		GridLayout layout = new GridLayout(1, false);
		c.setLayout(layout);
		Label a = new Label(c, SWT.LEFT);
		a.setText(character.toString());
		a.pack();
		
	    
		
	   
		c.pack();
		sc.setMinHeight(c.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		newshell.pack();
		center(newshell);
		newshell.open();
		newshell.addListener (SWT.Resize,  new Listener () {
	        public void handleEvent (Event e) {
	          Rectangle rect = newshell.getClientArea ();
	          sc.setBounds(rect);
	        }
	      });
		while(!newshell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
	}
	/**
	 * creates a next button on composite c in the bottom right corner.
	 * this does NOT set the listener! (each one is different, that is set 
	 * after this method is called)
	 * @param c
	 * @return
	 */
	public Button createNextButton(Composite c) {
		Button nextButton = new Button(c, SWT.PUSH);
		nextButton.setText("Next");
		//nextButton.setBounds(GameState.CHARWIZ_WIDTH - 117, GameState.CHARWIZ_HEIGHT - 90, 100, 50);
		return nextButton;
	}

	/**
	 * creates a back button on composite c in the bottom right corner.
	 * also sets the listener for the created button that changes the top 
	 * control page of the layout of the panel to be the previous page
	 * @param c
	 * @param panel
	 * @param layout
	 * @return
	 */
	public Button createBackButton(Composite c, final Composite panel,
			final StackLayout layout) {
		Button backButton = new Button(c, SWT.PUSH);
		backButton.setText("Back");
		backButton.setBounds(GameState.CHARWIZ_WIDTH - 220, GameState.CHARWIZ_HEIGHT - 90, 100, 50);
		backButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (wizPageNum > 0)
					wizPageNum--;
				layout.topControl = wizPages.get(wizPageNum);
				panel.layout();
			}
		});
		return backButton;
	}

	/**
	 * creates a cancel button on composite c in bottom left corner.
	 * also sets the listener for the created button that changes the homePanel
	 * top control to be home and resets the wizard page counter wizPageNum
	 * @param c
	 * @param home
	 * @param panel
	 * @param layout
	 * @return
	 */
	public Button createCancelButton(Composite c) {
		Button cancelButton = new Button(c, SWT.PUSH);
		cancelButton.setText("Cancel");
		//cancelButton.setBounds(10, GameState.CHARWIZ_HEIGHT - 90, 100, 50);
		cancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (cancelOpen) {
					cancelForceActive();
					return;
				}
				cancel = false;
				cancelOpen = true;
				
				// create shell
				areYouSureShell = new Shell(display);
				areYouSureShell.addListener(SWT.Close, new Listener() {
			        public void handleEvent(Event event) {
			            cancel = false;
			            cancelOpen = false;
			        }
			    });
				areYouSureShell.setText("Cancel");
				GridLayout gridLayout = new GridLayout(2, true);
				areYouSureShell.setLayout(gridLayout);

				// label - Are you sure? 
				GridData gd1 = new GridData(SWT.CENTER, SWT.CENTER, true, true);
				gd1.horizontalSpan = 2;
				Label areYouSure = new Label(areYouSureShell, SWT.WRAP);
				areYouSure.setText("Are you sure you want to cancel?");
				areYouSure.setLayoutData(gd1);
				areYouSure.pack();
				
				// no button
				Button no = new Button(areYouSureShell, SWT.PUSH);
				no.setText("No, Don't Cancel");
				no.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
				no.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						cancel = false;
						cancelOpen = false;
						areYouSureShell.dispose();
					}
				});
				no.pack();
				
				// yes button
				Button yes = new Button(areYouSureShell, SWT.PUSH);
				yes.setText("Yes, Cancel");
				yes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
				yes.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						cancel = true;
						cancelOpen = false;
						areYouSureShell.dispose();
					}
				});
				yes.pack();

				// open shell
				areYouSureShell.pack();
				center(areYouSureShell);
				areYouSureShell.open();
				
				// check if disposed
				while (!areYouSureShell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
				
				// if user clicks yes, return to new character home
				if (cancel) {
					shell.setSize(GameState.CHARWIZ_WIDTH, GameState.CHARWIZ_HEIGHT);
					center(shell);
					wizPageNum = -1;
					homeLayout.topControl = home;
					homePanel.layout();
				}
			}
		});
		return cancelButton;
	}
	
	public void cancelForceActive() {
		areYouSureShell.forceActive();
	}

	public character getCharacter() {
		return character;
	}
	
	private void instantiateWizPages() {
		// initialize all pages
		wizPages = new ArrayList<Composite>();
		final Composite wiz1 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz1);
		final Composite wiz2 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz2);
		final Composite wiz3 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz3);
		final Composite wiz4 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz4);
		final Composite wiz5 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz5);
		final Composite wiz6 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz6);
		final Composite wiz7 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz7);
		final Composite wiz8 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz8);
//		final Composite wiz9 = new Composite(wizPanel, SWT.NONE);
//		wizPages.add(wiz9);
	}
	
	public CharacterWizard getThis() { return cw; }
	
	public void disposeShell() { shell.dispose(); }
	
	public int[] getBaseAbilityScores() { return baseAbilityScores; }
	public void setBaseAbilityScores(int[] a) { baseAbilityScores = a; }
	
	public void reset() {
		character = new character();
		for (int i = 0; i < wizPageCreated.length; i++) {
			wizPageCreated[i] = false;
		}
		wizs = new ArrayList<Object>();
		for (int i = 0; i < wizPages.size(); i++)
			wizPages.get(i).dispose();
	}
	
	/**
	 * If the character has his highest ability score to be less or equal to 13
	 * or if the total ability modifier of the character is less or equal to 0,
	 * it will do a reroll.
	 * This method is for the puopose to check that.
	 * It is used when random generating character.
	 * @return yes if the character need a reroll, no if not.
	 */
	public boolean checkreroll(character character)
	{
		//reroll is true if ability modifiers sum <= 0
		//or the highest die roll is less or equal 13
		try
		{
		int totalmod = 0;
		int[] mods = character.getAbilityModifiers();
		for(int i = 0; i < mods.length; i++)
		{
			totalmod += mods[i];
		}
		if(totalmod <= 0)
		{
			return true;
		}
		int highest = 0;
		int[] scores = character.getAbilityScores();
		for(int j = 0; j < scores.length; j++)
		{
			if(scores[j] > highest)
			{
				highest = scores[j];
			}
		}
		if(highest <= 13)
		{
			return true;
		}
		return false;
		}
		catch(Exception a)
		{
			return false;
		}
	}

}
