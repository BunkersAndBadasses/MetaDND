package guis;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.Collection;

import core.Main;
import core.RNG;
import core.character;
import entity.ClassEntity;
import entity.DNDEntity;
import entity.FeatEntity;
import entity.RaceEntity;



/*
 * TODO: 
 * 
 * fix modifier logic (not rounding up)
 * launch wizards (add custom)
 * add search pop-up ("Coming soon!")
 * check feat prerequisites
 * save items
 * set hp
 * set size
 * monk - add wis to ac
 * 
 * make it so only one instance of char wiz can be open at one time
 */

/*
 * iteration 2: 
 * 
 * second class
 * searching
 * back button
 * starting at level > 1
 * adding custom skills
 * saving character image
 */

public class CharacterWizard {

	int pageNum = -1;

	private Device dev;
	private Display display;
	private Shell shell;
	private StackLayout wizLayout;
	private static RNG randomgene;
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;
	public int wizPageNum = -1;
	public boolean cancel = false;
	public boolean[] wizPageCreated = { false, false, false, false,
		false, false, false, false, false, false };
	
	private Composite wizPanel;

	private ArrayList<Composite> wizPages;

	public ArrayList<Object> wizs = new ArrayList<Object>();

	private character character;
	private CharacterWizard cw = this;

	public int[] baseAbilityScores = new int[6];

	public CharacterWizard(Display d) {
		display = d;
		shell = new Shell(d);
		shell.setText("Create New Character");
		shell.setSize(WIDTH, HEIGHT);
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

		final Composite homePanel = new Composite(shell, SWT.NONE);
		homePanel.setBounds(0, 0, WIDTH, HEIGHT);
		final StackLayout homeLayout = new StackLayout();
		homePanel.setLayout(homeLayout);

		//////////////////// HOME SCREEN SETUP ////////////////////////////

		// this screen is what is first seen when the window opens. 
		// it contains the buttons that link to the character wizard, the manual
		// character entering, and the random character generation
		final Composite home = new Composite(homePanel, SWT.NONE);
		home.setBounds(0, 0, WIDTH, HEIGHT);

		Label homeLabel = new Label(home, SWT.NONE);
		homeLabel.setText("Let's create a character!");
		Font font1 = new Font(homeLabel.getDisplay(), new FontData("Arial", 24,
				SWT.BOLD));
		homeLabel.setFont(font1);
		homeLabel.setBounds(WIDTH / 2 - 180, 40, 100, 100);
		homeLabel.pack();

		Label homeLabel2 = new Label(home, SWT.NONE);
		homeLabel2.setText("\nChoose a method:");
		Font font2 = new Font(homeLabel.getDisplay(), new FontData("Arial", 18,
				SWT.BOLD));
		homeLabel2.setFont(font2);
		homeLabel2.setBounds(WIDTH / 2 - 100, 65, 100, 100);
		homeLabel2.pack();

		Button wizardButton = new Button(home, SWT.PUSH);
		wizardButton.setText("Interactive\n Character Wizard");
		wizardButton.setFont(font2);
		wizardButton.setBounds(WIDTH / 2 - 150, 150, 300, 150);

		Button manualButton = new Button(home, SWT.PUSH);
		manualButton.setText("Manual");
		Font font3 = new Font(homeLabel.getDisplay(), new FontData("Arial", 18,
				SWT.NONE));
		manualButton.setFont(font3);
		manualButton.setBounds(WIDTH / 2 - 150, 310, 145, 75);

		Button randomButton = new Button(home, SWT.PUSH);
		randomButton.setText("Random");
		randomButton.setFont(font3);
		randomButton.setBounds(WIDTH / 2 + 5, 310, 145, 75);

		// set home as the first screen viewed when new character window  is launched
		homeLayout.topControl = home;


		// ///////////////// WIZARD PANEL SETUP ///////////////////////////

		wizPanel = new Composite(homePanel, SWT.BORDER);
		wizPanel.setBounds(0, 0, WIDTH, HEIGHT);
		wizPanel.setBackground(new Color(dev, 255, 0, 0));
		wizLayout = new StackLayout();
		wizPanel.setLayout(wizLayout);


		// ///////////////// MANUAL PANEL SETUP ///////////////////////////

		final Composite manualPanel = new Composite(homePanel, SWT.BORDER);
		wizPanel.setBounds(0, 0, WIDTH, (int) (HEIGHT * (.75)));
		final StackLayout manualLayout = new StackLayout();
		manualPanel.setLayout(manualLayout);
		final Composite manualPage1 = new Composite(manualPanel, SWT.NONE);
		manualPage1.setSize(manualPanel.getSize());
		manualLayout.topControl = manualPage1;
		manualPanel.layout();
		Label csManual = new Label(manualPage1, SWT.BOLD);
		csManual.setLocation(WIDTH/2-50, HEIGHT/2);
		csManual.setText("Coming Soon!");
		csManual.pack();
		createCancelButton(manualPage1, home, homePanel, homeLayout);


		// ////////////////// RANDOM PANEL SETUP //////////////////////////

		final Composite randomPanel = new Composite(homePanel, SWT.BORDER);
		wizPanel.setBounds(0, 0, WIDTH, (int) (HEIGHT * (.75)));
		final StackLayout randomLayout = new StackLayout();
		randomPanel.setLayout(randomLayout);
		//final Composite randomPage1 = new Composite(randomPanel, SWT.NONE);
		//randomPage1.setSize(manualPanel.getSize());
		//randomLayout.topControl = randomPage1;
		//randomPanel.layout();
		//Label csRandom = new Label(randomPage1, SWT.BOLD);
		//csRandom.setLocation(WIDTH/2-50, HEIGHT/2);
		//csRandom.setText("Coming Soon!");
		//csRandom.pack();
		//createCancelButton(randomPage1, home, homePanel, homeLayout);


		// ////////////////// HOME BUTTON LISTENERS ///////////////////////
		
		wizardButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// create the first page (creates next pages at runtime)
				instantiateWizPages();
				Wiz1 wiz1 = new Wiz1(cw, dev, WIDTH, HEIGHT, wizPanel, home,
						homePanel, wizLayout, homeLayout, wizPages);
				wizs.add(wiz1);
				wizLayout.topControl = wizPages.get(0);
				wizPanel.layout();
				homeLayout.topControl = wizPanel;
				homePanel.layout();
			}
		});
		manualButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				homeLayout.topControl = manualPanel;
				homePanel.layout();
			}
		});
		randomButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				randomgeneration(randomPanel, randomLayout);
				homeLayout.topControl = randomPanel;
				homePanel.layout();
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
	private void randomgeneration(final Composite randomPanel, final StackLayout randomLayout) 
	{
		character = new character();
		character.setLevel(1);
		character.setExp(0);
		Collection<DNDEntity> racecol = Main.gameState.races.values();
		character.setCharRace((RaceEntity) racecol.toArray()[randomgene.GetRandomInteger(0, racecol.size()-1)]);
		Collection<DNDEntity> classcol = Main.gameState.classes.values();
		character.setCharClass((ClassEntity) classcol.toArray()[randomgene.GetRandomInteger(0, classcol.size()-1)]);
		//Alignment
		String[] Alignments = {"Lawful Good", "Lawful Neutral", "Lawful Evil", 
				"Neutral Good", "True Neutral", "Neutral Evil", "Chaotic Good", 
				"Chaotic Neutral", "Chaotic Evil"};
		character.setAlignment(Alignments[randomgene.GetRandomInteger(0, Alignments.length - 1)]);
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
		if(racec.getName().equalsIgnoreCase("Human"))
			height = randomgene.GetRandomInteger(55, 78);
		if(racec.getName().equalsIgnoreCase("Dwarf"))
			height = randomgene.GetRandomInteger(45, 53);
		if(racec.getName().equalsIgnoreCase("Gnome"))
			height = randomgene.GetRandomInteger(36, 44);
		if(racec.getName().equalsIgnoreCase("Half-elf"))
			height = randomgene.GetRandomInteger(55, 71);
		if(racec.getName().equalsIgnoreCase("Half-orc"))
			height = randomgene.GetRandomInteger(55, 82);
		if(racec.getName().equalsIgnoreCase("Halfling"))
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
		if(racec.getName().equalsIgnoreCase("Human"))
			weight = randomgene.GetRandomInteger(125, 280);
		if(racec.getName().equalsIgnoreCase("Dwarf"))
			weight = randomgene.GetRandomInteger(85, 230);
		if(racec.getName().equalsIgnoreCase("Gnome"))
			weight = randomgene.GetRandomInteger(35, 50);
		if(racec.getName().equalsIgnoreCase("Half-elf"))
			weight = randomgene.GetRandomInteger(80, 230);
		if(racec.getName().equalsIgnoreCase("Half-orc"))
			weight = randomgene.GetRandomInteger(110, 400);
		if(racec.getName().equalsIgnoreCase("Halfling"))
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
		} while(character.checkreroll());
		//HP and remaining HP
		character.setHitPoints(Integer.parseInt(character.getCharClass().getHitDie().split("d")[1]) + character.getAbilityModifiers()[2]);
		if(character.getHitPoints() < 3)
		{
			character.setHitPoints(3);
		}
		//Languages -- pick three
		character.setLanguages("Common");
		if(character.getCharClass().getName().equalsIgnoreCase("Druid"))
		{
			character.setLanguages(character.getLanguages() + ", Druidic");
		}
		//Gold
		character.setGold(randomgene.GetRandomInteger(0, 200));
		//Feats
		Collection<DNDEntity> featcol = Main.gameState.feats.values();
		//Random 1 feat
		character.addFeat((FeatEntity) featcol.toArray()[randomgene.GetRandomInteger(0, featcol.size() - 1)]);
		//Human gain "Feat for select"
		if(character.getCharRace().getName().equalsIgnoreCase("Human"))
		{
			character.addFeat((FeatEntity) featcol.toArray()[randomgene.GetRandomInteger(0, featcol.size() - 1)]);
		}
		//Fighter get a "Fighter bonus feat"
		if(character.getCharClass().getName().equalsIgnoreCase("Fighter"))
		{
			boolean a = false;
			do
			{
				FeatEntity b = (FeatEntity) featcol.toArray()[randomgene.GetRandomInteger(0, featcol.size() - 1)];
				if(b.getFighterBonus() == null)
				{
					a = true;
				}
				else
				{
					a = false;
					character.addFeat(b);
				}
			} while(a = true);
		}
		//Wizard gain "Scribe Scroll"
		if(character.getCharClass().getName().equalsIgnoreCase("Wizard"))
		{
			character.addFeat((FeatEntity) Main.gameState.feats.get("Scribe Scroll"));
		}
		//Ranger gain "Track"
		if(character.getCharClass().getName().equalsIgnoreCase("Ranger"))
		{
			character.addFeat((FeatEntity) Main.gameState.feats.get("Track"));
		}
		//Special Ability
		//TODO
		//Spells
		Collection<DNDEntity> spellcol = Main.gameState.spells.values();
		//wizard get all level 0 spell + (3 + INT modifier) of level 1 spell
		//Sorcerer get 4 level 0 spell + 2 level 1 spell
		//TODO
		//Prepared spells are <empty>
		//Items is <empty>
		//Weapons is <empty>
		//Armor is <empty>
		//Notes will be <empty>
		final Composite randomPage1 = new Composite(randomPanel, SWT.NONE);
		randomPage1.setSize(randomPanel.getSize());
		randomLayout.topControl = randomPage1;
		randomPanel.layout();
	    Label csRandom = new Label(randomPage1, SWT.BOLD);
	    csRandom.setText(character.toString());
	    csRandom.pack();
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
		nextButton.setBounds(WIDTH - 117, HEIGHT - 90, 100, 50);
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
		backButton.setBounds(WIDTH - 220, HEIGHT - 90, 100, 50);
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
	public Button createCancelButton(Composite c, final Composite home,
			final Composite panel, final StackLayout layout) {
		Button cancelButton = new Button(c, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.setBounds(10, HEIGHT - 90, 100, 50);
		cancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				cancel = false;
				
				// create shell
				final Shell areYouSureShell = new Shell(display);
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
				
				// yes button
				Button yes = new Button(areYouSureShell, SWT.PUSH);
				yes.setText("Yes, Cancel");
				yes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
				yes.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						cancel = true;
						areYouSureShell.dispose();
					}
				});
				yes.pack();

				// no button
				Button no = new Button(areYouSureShell, SWT.PUSH);
				no.setText("No, Don't Cancel");
				no.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
				no.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						cancel = false;
						areYouSureShell.dispose();
					}
				});
				no.pack();

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
					wizPageNum = -1;
					layout.topControl = home;
					panel.layout();
				}
			}
		});
		return cancelButton;
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
		final Composite wiz9 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz9);
		final Composite wiz10 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz10);
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
}
