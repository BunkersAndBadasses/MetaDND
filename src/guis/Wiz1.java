/*
 * CHOOSE ABILITY SCORES, CHOOSE RACE AND CLASS
 */

package guis;
import core.GameState;
import core.Main;
import core.character;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import entity.AbilityEntity;
import entity.ClassEntity;
import entity.DNDEntity;
import entity.RaceEntity;

public class Wiz1 {	

	private Composite wiz1;
	private CharacterWizard cw;
	private Device dev;
	private int WIDTH;
	private int HEIGHT;
	private character character;
	private Composite wizPanel;
	private StackLayout wizLayout;
	private ArrayList<Composite> wizPages;
	private Composite nextPage;
	private int wizPagesSize;

	public int[] as = new int[6]; // ability scores array
	private Text wiz1AS1;
	private Text wiz1AS2;
	private Text wiz1AS3;
	private Text wiz1AS4;
	private Text wiz1AS5;
	private Text wiz1AS6;
	//private Label badLevelInputText;
	private Label badASInputText;
	
	private Combo raceDropDown;
	private Combo classDropDown;
	
	private Label badSearch;
	private Label badRaceSelect;
	private Label badClassSelect;
	
	private boolean finished;
	private boolean popUpOpen = false;
	
	private Shell classExtrasShell;


	public Wiz1(CharacterWizard cw, Device dev, int WIDTH, int HEIGHT, 
			final Composite panel, final StackLayout layout, 
			final ArrayList<Composite> wizPages) {
		wiz1 = wizPages.get(0);
		layout.topControl = wiz1;
		panel.layout();
		this.cw = cw;
		this.dev = dev;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.character = cw.getCharacter();
		this.wizPanel = panel;
		this.wizLayout = layout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(1);
		this.wizPagesSize = wizPages.size();

		createPageContent();
	}

	private void createPageContent() {
		GridLayout layout = new GridLayout(2, true);
		wiz1.setLayout(layout);
		
		GridData gd;
		
		Label wiz1Label = new Label(wiz1, SWT.NONE);
		wiz1Label.setText("Roll initial ability scores and select character race and class");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		wiz1Label.setLayoutData(gd);
		
		
		// initialize layout
		GridLayout gl = new GridLayout(10, false);
		
		Composite inner = new Composite(wiz1, SWT.NONE);
		inner.setBounds(5, 20, WIDTH-10, HEIGHT-110);
		inner.setLayout(gl);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 2;
		inner.setLayoutData(gd);

		////////////////////
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 10;
		new Label(inner, SWT.NONE).setLayoutData(gd);
		////////////////////
		
		////////////////////
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 10;
		new Label(inner, SWT.NONE).setLayoutData(gd);
		////////////////////
		
		////////////////////
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 4;
		new Label(inner, SWT.NONE).setLayoutData(gd);
		
		// level label
		Label wiz1LevelLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		wiz1LevelLabel.setLayoutData(gd);
		
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 4;
		new Label(inner, SWT.NONE).setLayoutData(gd);
		////////////////////
		
		////////////////////
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 2;
		new Label(inner, SWT.NONE).setLayoutData(gd);

		// ability scores fields
		wiz1AS1 = new Text(inner, SWT.BORDER | SWT.CENTER);
		gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		wiz1AS1.setLayoutData(gd);
		wiz1AS2 = new Text(inner, SWT.BORDER | SWT.CENTER);
		gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		wiz1AS2.setLayoutData(gd);
		wiz1AS3 = new Text(inner, SWT.BORDER | SWT.CENTER);
		gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		wiz1AS3.setLayoutData(gd);
		wiz1AS4 = new Text(inner, SWT.BORDER | SWT.CENTER);
		gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		wiz1AS4.setLayoutData(gd);
		wiz1AS5 = new Text(inner, SWT.BORDER | SWT.CENTER);
		gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		wiz1AS5.setLayoutData(gd);
		wiz1AS6 = new Text(inner, SWT.BORDER | SWT.CENTER);
		gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		wiz1AS6.setLayoutData(gd);

		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 2;
		new Label(inner, SWT.NONE).setLayoutData(gd);
		////////////////////
		
		////////////////////
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 4;
		new Label(inner, SWT.NONE).setLayoutData(gd);
		
		// roll button
		Button wiz1RollButton = new Button(inner, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		wiz1RollButton.setLayoutData(gd);
		
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 4;
		new Label(inner, SWT.NONE).setLayoutData(gd);
		////////////////////
		
		////////////////////
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 10;
		new Label(inner, SWT.NONE).setLayoutData(gd);
		////////////////////
		
		////////////////////
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		new Label(inner, SWT.NONE).setLayoutData(gd);
		
		// race label
		Label raceLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.RIGHT, SWT.CENTER, true, true);
		raceLabel.setLayoutData(gd);
		
		// race drop down
		raceDropDown = new Combo(inner, SWT.DROP_DOWN | SWT.READ_ONLY);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, true);
		gd.horizontalSpan = 2;
		raceDropDown.setLayoutData(gd);
		
		// race details button
		Button raceSearchButton = createSearchButton(inner);
		gd = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		raceSearchButton.setLayoutData(gd);
		
		// class label
		Label classLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.RIGHT, SWT.CENTER, true, true);
		classLabel.setLayoutData(gd);
		
		// class drop down 
		classDropDown = new Combo(inner, SWT.DROP_DOWN | SWT.READ_ONLY);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, true);
		gd.horizontalSpan = 2;
		classDropDown.setLayoutData(gd);
		
		// class details button
		Button classSearchButton = createSearchButton(inner);
		gd = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		classSearchButton.setLayoutData(gd);
		
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		new Label(inner, SWT.NONE).setLayoutData(gd);
		////////////////////

//		// level error message
//		badLevelInputText = new Label(inner, SWT.NONE);
//		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
//		gd.horizontalSpan = 10;
//		badLevelInputText.setLayoutData(gd);
		
		////////////////////
		// ability score error message
		badASInputText = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 10;
		badASInputText.setLayoutData(gd);
		////////////////////
		
		////////////////////
		// race error
		badRaceSelect = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 10;
		badRaceSelect.setLayoutData(gd);
		////////////////////
		
		////////////////////
		// class error
		badClassSelect = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 10;
		badClassSelect.setLayoutData(gd);
		////////////////////
		
		////////////////////
		// details error
		badSearch = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 10;
		badSearch.setLayoutData(gd);
		////////////////////
		
		// create content
		
		// level field
		wiz1LevelLabel.setText("Starting Level: 1");
		wiz1LevelLabel.pack();

		// roll button
		wiz1RollButton.setText("Roll");
		wiz1RollButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				badASInputText.setVisible(false);
				//badLevelInputText.setVisible(false);
				int[] roll = genAS();
				wiz1AS1.setText(Integer.toString(roll[0]));
				wiz1AS2.setText(Integer.toString(roll[1]));
				wiz1AS3.setText(Integer.toString(roll[2]));
				wiz1AS4.setText(Integer.toString(roll[3]));
				wiz1AS5.setText(Integer.toString(roll[4]));
				wiz1AS6.setText(Integer.toString(roll[5]));
			}
		});
		
		// race label
		raceLabel.setText("Race: ");

		// class label
		classLabel.setText("Class: ");

		// get races from references
		Collection<DNDEntity> racesCol = Main.gameState.races.values();
		Iterator<DNDEntity> itr = racesCol.iterator();
		ArrayList<RaceEntity> races = new ArrayList<RaceEntity>();
		while (itr.hasNext()) {
			races.add((RaceEntity) itr.next());
		}

		// get classes from references
		Collection<DNDEntity> classesCol = Main.gameState.classes.values();
		Iterator<DNDEntity> itr2 = classesCol.iterator();
		ArrayList<ClassEntity> classes = new ArrayList<ClassEntity>();
		while (itr2.hasNext()) {
			classes.add((ClassEntity) itr2.next());
		}
		
		// race drop down
		for (int i = 0; i < races.size(); i++) {
			raceDropDown.add(races.get(i).getName());
		}
		
		// class drop down
		for (int i = 0; i < classes.size(); i++) {
			classDropDown.add(classes.get(i).getName());
		}
		
		// race details
		raceSearchButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (raceDropDown.getText().equals("")) {
					// nothing is selected to be searched - display error
					badSearch.setVisible(true);
				} else {
					badSearch.setVisible(false);
					// launch search
					DNDEntity search = Main.gameState.races.get(raceDropDown.getText());
					search.toTooltipWindow();
				}
			}
		});
		
		// class details
		classSearchButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (classDropDown.getText().equals("")) {
					// nothing is selected to be searched - display error
					badSearch.setVisible(true);
				} else {
					badSearch.setVisible(false);
					// launch search
					DNDEntity search = Main.gameState.classes.get(classDropDown.getText());
					search.toTooltipWindow();
				}
			}
		});

//		// this appears when there is invalid input in level box
//		badLevelInputText.setForeground(wiz1.getDisplay().getSystemColor(SWT.COLOR_RED));
//		badLevelInputText.setVisible(false);
//		badLevelInputText.setText("invalid level: must be a positive integer");

		// this appears when there is invalid input in any ability score boxes
		badASInputText.setForeground(wiz1.getDisplay().getSystemColor(SWT.COLOR_RED));
		badASInputText.setVisible(false);
		badASInputText.setText("invalid ability score: must be a positive integer from 3 to 18");

		// search error
		badSearch.setForeground(wiz1.getDisplay().getSystemColor(SWT.COLOR_RED));
		badSearch.setVisible(false);
		badSearch.setText("You must select an item to see details!");
		
		// race error
		badRaceSelect.setForeground(wiz1.getDisplay().getSystemColor(SWT.COLOR_RED));
		badRaceSelect.setVisible(false);
		badRaceSelect.setText("You must select a race!");
		
		// class error
		badClassSelect.setForeground(wiz1.getDisplay().getSystemColor(SWT.COLOR_RED));
		badClassSelect.setVisible(false);
		badClassSelect.setText("You must select a class!");
		
		inner.layout();
		
		
		// cancel button
		Button wiz1CancelButton = cw.createCancelButton(wiz1);
		gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		wiz1CancelButton.setLayoutData(gd);
		wiz1CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (cw.cancel)
					cw.reset();
			}
		});
		
		// next button
		Button wiz1NextButton = cw.createNextButton(wiz1);
		gd = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		wiz1NextButton.setLayoutData(gd);
		wiz1NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// user cannot move on if there is a pop up open
				if (popUpOpen) {
					classExtrasShell.forceActive();
					return;		
				}
				
				// error checking for level
				boolean error = false;
				int level = 1;
//				try {
//					badLevelInputText.setVisible(false);
//					if (level <= 0 || level > 25) throw new Exception();
//				} catch (Exception e) {
//					badLevelInputText.setVisible(true);
//					error = true;
//				}

				// error checking for ability scores
				try {
					badASInputText.setVisible(false);
					as[0] = Integer.parseInt(wiz1AS1.getText());
					as[1] = Integer.parseInt(wiz1AS2.getText());
					as[2] = Integer.parseInt(wiz1AS3.getText());
					as[3] = Integer.parseInt(wiz1AS4.getText());
					as[4] = Integer.parseInt(wiz1AS5.getText());
					as[5] = Integer.parseInt(wiz1AS6.getText());
					for (int i = 0; i < 6; i++)
						if (as[i] < 3 || as[i] > 18) throw new Exception();
				} catch (Exception e) {
					badASInputText.setVisible(true);
					error = true;
				}
				
				badSearch.setVisible(false);

				// error checking for race/class
				badRaceSelect.setVisible(false);	// clear any past errors
				// check if user selected a race

				if (raceDropDown.getSelectionIndex() == -1) {
					badRaceSelect.setVisible(true);
					error = true;
				}

				badClassSelect.setVisible(false);	// clear any past errors
				// check if user selected a class
				if (classDropDown.getSelectionIndex() == -1) {
					badClassSelect.setVisible(true);
					error = true;					
				}

				// user cannot move on with an error
				if (error) return;

				// if all goes well, save info
				character.setLevel(level);
				cw.setBaseAbilityScores(as);
				boolean done = true;
				String charClass = classes.get(classDropDown.getSelectionIndex()).getName();
				if (charClass.equalsIgnoreCase("druid") 
						| charClass.equalsIgnoreCase("ranger")
						| charClass.equalsIgnoreCase("sorcerer")
						| charClass.equalsIgnoreCase("wizard")
						)
					done = extraStuffWindow(charClass);
				if (!done)
					return;
				
				// if all good, save
				
				// set race
				character.setCharRace(races.get(raceDropDown.getSelectionIndex()));
				// set class
				character.setCharClass(classes.get(classDropDown.getSelectionIndex()));
				// set second class (if any)
//				int secClassIndex = secClassDropDown.getSelectionIndex();
//				if (secClassIndex < 1)
					character.setCharSecClass(null);
//				else 
//					character.setCharSecClass(classes.get(secClassIndex));
				// set size
				character.setSize(character.getCharRace().getSize());
				// set size
				character.setSpeed(character.getCharRace().getSpeed());
				if (charClass.equalsIgnoreCase("Barbarian"))
					character.setSpeed(character.getCharRace().getSpeed() + 10);
				// set initial ac (defaults to 10 + 0 + 0 + 0 + 0 + 0
				// dex modifier will update in wiz3, armor and shield will update in wiz8
				character.setACSizeMod(GameState.acAttackSizeMods[character.getSize()]);
				// auto saves touch ac and flat footed ac from ac
				// init mod will update in wiz3
				// set saving throws
				character.setFortSaveBaseSave(character.getCharClass().getFortSave()[character.getLevel()-1]);
				character.setReflexSaveBaseSave(character.getCharClass().getReflexSave()[character.getLevel()-1]);
				character.setWillSaveBaseSave(character.getCharClass().getWillSave()[character.getLevel()-1]);
				// set base attack bonus
				character.setBaseAttackBonus((character.getCharClass().getBaseAttackBonus())[character.getLevel()-1]);
				// set grapple mod
				// size mod updates with setbaseattackbonus, str mod updates in wiz3
				character.setGrappleSizeMod(GameState.grappleSizeMods[character.getSize()]);
				// set abilities
				String[] abilities = character.getCharRace().getSpecialAbilities();
				for (int i = 0; i < abilities.length; i++)
					if (!abilities[i].equals(""))
						character.addSpecialAbility((AbilityEntity)Main.gameState.abilities.get(abilities[i]));


				if (cw.wizPageNum < wizPagesSize - 1)
					cw.wizPageNum++;
				if (!cw.wizPageCreated[1])
					createNextPage();
				wizLayout.topControl = nextPage;
				wizPanel.layout();
			}
		});
		wiz1.layout();

	}
	
	/**
	 *  generates random number between 3 and 18 (for use as an ability score)
	 *  simulates rolling 4 dnd dropping the lowest roll
	 */
	private int[] genAS() {
		Random r = new Random();
		int[] result = { 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < 6; i++) {
			int roll[] = { r.nextInt(6) + 1, r.nextInt(6) + 1,
					r.nextInt(6) + 1, r.nextInt(6) + 1 };
			int min = 7; // max value a roll can be is 6
			for (int j = 0; j < 4; j++) {
				result[i] += roll[j];
				if (roll[j] < min)
					min = roll[j];
			}
			result[i] -= min;
		}
		return result;
	}
	
	private Button createSearchButton(Composite c) {
		Button searchButton = new Button(c, SWT.PUSH);
		searchButton.setText("Details");
		searchButton.setSize(80,30);
		return searchButton;
	}
	
	private boolean extraStuffWindow(String c) {
		// druid - animal companion
		// ranger - favored enemy
		// sorcerer - familiar
		// wizard - specialty school, familiar

		finished = false;
		popUpOpen = true;
		
		// create shell
		Display display = wiz1.getDisplay();
		classExtrasShell = new Shell(display);
		classExtrasShell.setImage(new Image(display, "images/bnb_logo.gif"));
		classExtrasShell.setText("Class Extras");
		GridLayout gridLayout = new GridLayout(2, true);
		classExtrasShell.setLayout(gridLayout);
		classExtrasShell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				finished = false;
				popUpOpen = false;
			}
		});

		final String charClass = c.toLowerCase();
		switch(charClass) {
		case ("druid"): 
		{
			// label - select an animal companion
			Label druidLabel = new Label(classExtrasShell, SWT.NONE);
			druidLabel.setText("Select an animal companion");
			GridData gd1 = new GridData(SWT.CENTER, SWT.CENTER, true, true);
			gd1.horizontalSpan = 2;
			druidLabel.setLayoutData(gd1);
			druidLabel.pack();

			// list of available animal companions
			Combo acList = new Combo(classExtrasShell, SWT.DROP_DOWN | SWT.READ_ONLY);
			String[] companions = {"Badger", "Camel", "Dire Rat", "Dog", "Riding Dog", "Eagle", "Hawk", "Horse(light)", "Horse(heavy)", "Owl", "Pony", "Snake(small)", "Snake(medium)", "Wolf", "Porpoise", "Shark(medium)", "Squid"};
			for (int i = 0; i < companions.length; i++){
				acList.add(companions[i]);
			}
			GridData gd2 = new GridData(SWT.CENTER, SWT.CENTER, true, false);
			gd2.horizontalSpan = 2;
			acList.setLayoutData(gd2);
			acList.pack();

			// label - add custom companion
			Label customLabel = new Label(classExtrasShell, SWT.NONE);
			customLabel.setText("OR Enter Custom: ");
			GridData gd3 = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
			customLabel.setLayoutData(gd3);
			customLabel.pack();

			// text input for custom companion
			Text customInput = new Text(classExtrasShell, SWT.BORDER);
			GridData gd4 = new GridData(SWT.FILL, SWT.CENTER, true, false);
			customInput.setLayoutData(gd4);
			customInput.pack();

			// user can either select from the list OR enter a custom
			// when the list is selected, the custom input is erased
			// when the custom input is selected, the list is deselected 
			customInput.addListener(SWT.MouseUp, new Listener() {
				public void handleEvent(Event e) {
					acList.deselectAll();
				}
			});

			acList.addListener(SWT.MouseUp, new Listener() {
				public void handleEvent(Event e) {
					customInput.setText("");
				}
			});

			// cancel button
			Button cancel = new Button(classExtrasShell, SWT.PUSH);
			cancel.setText("Cancel");
			GridData gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
			cancel.setLayoutData(gd);
			cancel.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					classExtrasShell.dispose();
					finished = false;
					popUpOpen = false;
				}
			});
			
			// done button
			Button done = new Button(classExtrasShell, SWT.PUSH);
			done.setText("Done");
			GridData doneGD = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
			done.setLayoutData(doneGD);
			done.pack();
			done.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					if (acList.getSelectionIndex() != -1)
						character.setDruidAnimalCompanion(acList.getItem(acList.getSelectionIndex()));
					else if (customInput.getText().length() > 0)
						character.setDruidAnimalCompanion(customInput.getText());
					classExtrasShell.dispose();
					finished = true;
					popUpOpen = false;
				}
			});
			break;
		}
		case ("ranger"):
		{
			Label rangerLabel = new Label(classExtrasShell, SWT.NONE);
			rangerLabel.setText("Select a favored enemy");
			GridData gd5 = new GridData(SWT.CENTER, SWT.CENTER, true, true);
			gd5.horizontalSpan = 2;
			rangerLabel.setLayoutData(gd5);
			rangerLabel.pack();

			// list of available animal companions
			Combo feList = new Combo(classExtrasShell, SWT.DROP_DOWN | SWT.READ_ONLY);
			String[] enemies = {"Aberration", "Animal", "Construct", "Dragon", "Elemental", "Fey", "Giant", "Humanoid", "Magical Beast", "Monstrous humanoid", "Ooze", "Outsider", "Plant", "Undead", "Vermin"};
			for (int i = 0; i < enemies.length; i++){
				feList.add(enemies[i]);
			}
			GridData gd6 = new GridData(SWT.CENTER, SWT.CENTER, true, false);
			gd6.horizontalSpan = 2;
			feList.setLayoutData(gd6);
			feList.pack();

			// favored enemy subtype list for humanoid/outsider
			Label subtypeLabel = new Label(classExtrasShell, SWT.NONE);
			subtypeLabel.setText("Subtype: ");
			GridData gd7 = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
			subtypeLabel.setLayoutData(gd7);
			subtypeLabel.pack();
			subtypeLabel.setVisible(false);

			Combo subtypeList = new Combo(classExtrasShell, SWT.DROP_DOWN | SWT.READ_ONLY);
			GridData gd8 = new GridData(SWT.LEFT, SWT.CENTER, true, false);
			subtypeList.setLayoutData(gd8);
			subtypeList.pack();
			subtypeList.setVisible(false);
			subtypeList.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					subtypeList.setBackground(null);
				}
			});

			feList.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					subtypeList.setBackground(null);
					if (feList.getItem(feList.getSelectionIndex()).equals("Humanoid")){
						subtypeList.deselectAll();
						subtypeList.removeAll();
						String[] humanoids = {"Aquatic", "Dwarf", "Elf", "Goblinoid", "Gnoll", "Gnome", "Halfling", "Human", "Orc", "Reptillian"};
						for (int i = 0; i < humanoids.length; i++)
							subtypeList.add(humanoids[i]);
						subtypeList.pack();
					} else if (feList.getItem(feList.getSelectionIndex()).equals("Outsider")){
						subtypeList.deselectAll();
						subtypeList.removeAll();
						String[] outsiders = {"Air", "Chaotic", "Earth", "Evil", "Fire", "Good", "Lawful", "Native", "Water"};
						for (int i = 0; i < outsiders.length; i++)
							subtypeList.add(outsiders[i]);
						subtypeList.pack();
					} else {
						subtypeList.setVisible(false);
						subtypeLabel.setVisible(false);
						return;
					}
					subtypeList.setVisible(true);
					subtypeLabel.setVisible(true);
					return;
				}
			});

			// label - add custom favored enemy
			Label customLabel = new Label(classExtrasShell, SWT.NONE);
			customLabel.setText("OR Enter Custom: ");
			GridData gd3 = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
			customLabel.setLayoutData(gd3);
			customLabel.pack();

			// text input for custom favored enemy
			Text customInput = new Text(classExtrasShell, SWT.BORDER);
			GridData gd4 = new GridData(SWT.FILL, SWT.CENTER, true, false);
			customInput.setLayoutData(gd4);
			customInput.pack();

			// user can either select from the list OR enter a custom
			// when the list is selected, the custom input is erased
			// when the custom input is selected, the list is deselected 
			customInput.addListener(SWT.MouseUp, new Listener() {
				public void handleEvent(Event e) {
					subtypeList.setBackground(null);
					subtypeLabel.setVisible(false);
					subtypeList.setVisible(false);
					feList.deselectAll();
				}
			});

			feList.addListener(SWT.MouseUp, new Listener() {
				public void handleEvent(Event e) {
					customInput.setText("");
				}
			});

			// cancel button
			Button cancel = new Button(classExtrasShell, SWT.PUSH);
			cancel.setText("Cancel");
			GridData gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
			cancel.setLayoutData(gd);
			cancel.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					classExtrasShell.dispose();
					finished = false;
					popUpOpen = false;
				}
			});
			
			// done button
			Button done = new Button(classExtrasShell, SWT.PUSH);
			done.setText("Done");
			GridData doneGD = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
			done.setLayoutData(doneGD);
			done.pack();
			done.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					boolean error = false;
					if (feList.getSelectionIndex() != -1) {
						if (feList.getItem(feList.getSelectionIndex()).equals("Humanoid") 
								|| feList.getItem(feList.getSelectionIndex()).equals("Outsider")) {
							if (subtypeList.getSelectionIndex() != -1) {
								character.setRangerFavoredEnemy(feList.getItem(feList.getSelectionIndex()) + "(" 
										+ subtypeList.getItem(subtypeList.getSelectionIndex()) + ")");
							} else {
								subtypeList.setBackground(new Color(dev, 255, 100, 100));
								error = true;
							}
						} else {
							character.setRangerFavoredEnemy(feList.getItem(feList.getSelectionIndex()));
						}
					} else if (customInput.getText().length() > 0) {
						character.setRangerFavoredEnemy(customInput.getText());
					}
					if (!error) {
						classExtrasShell.dispose();
						finished = true;
						popUpOpen = false;
					}
				}
			});
			break;
		}
		case ("sorcerer"):
		{
			// label - select an animal companion
			Label sorcererLabel = new Label(classExtrasShell, SWT.NONE);
			sorcererLabel.setText("Select a familiar");
			GridData gd1 = new GridData(SWT.CENTER, SWT.CENTER, true, true);
			gd1.horizontalSpan = 2;
			sorcererLabel.setLayoutData(gd1);
			sorcererLabel.pack();

			// list of available familiars
			Combo famList = new Combo(classExtrasShell, SWT.DROP_DOWN | SWT.READ_ONLY);
			String[] familiars = {"Bat", "Cat", "Hawk", "Lizard", "Owl", "Rat", "Raven", "Snake", "Toad", "Weasel"};
			famList.add("<none>");
			for (int i = 0; i < familiars.length; i++){
				famList.add(familiars[i]);
			}
			GridData gd2 = new GridData(SWT.CENTER, SWT.CENTER, true, false);
			gd2.horizontalSpan = 2;
			famList.setLayoutData(gd2);
			famList.pack();
			famList.select(0);

			// label - add custom familiar
			Label customLabel = new Label(classExtrasShell, SWT.NONE);
			customLabel.setText("OR Enter Custom: ");
			GridData gd3 = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
			customLabel.setLayoutData(gd3);
			customLabel.pack();

			// text input for custom companion
			Text customInput = new Text(classExtrasShell, SWT.BORDER);
			GridData gd4 = new GridData(SWT.FILL, SWT.CENTER, true, false);
			customInput.setLayoutData(gd4);
			customInput.pack();

			// user can either select from the list OR enter a custom
			// when the list is selected, the custom input is erased
			// when the custom input is selected, the list is deselected 
			customInput.addListener(SWT.MouseUp, new Listener() {
				public void handleEvent(Event e) {
					famList.select(0);
				}
			});

			famList.addListener(SWT.MouseUp, new Listener() {
				public void handleEvent(Event e) {
					customInput.setText("");
				}
			});

			// cancel button
			Button cancel = new Button(classExtrasShell, SWT.PUSH);
			cancel.setText("Cancel");
			GridData gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
			cancel.setLayoutData(gd);
			cancel.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					classExtrasShell.dispose();
					finished = false;
					popUpOpen = false;
				}
			});
			
			// done button
			Button done = new Button(classExtrasShell, SWT.PUSH);
			done.setText("Done");
			GridData doneGD = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
			done.setLayoutData(doneGD);
			done.pack();
			done.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					if (famList.getSelectionIndex() > 0) {
						character.setFamiliar(famList.getItem(famList.getSelectionIndex()));
					} else if (customInput.getText().length() > 0) {
						character.setFamiliar(customInput.getText());
					}
					classExtrasShell.dispose();
					finished = true;
					popUpOpen = false;
				}
			});
			break;
		}
		default: // wizard
		{
			// label - select an animal companion
			Label sorcererLabel = new Label(classExtrasShell, SWT.NONE);
			sorcererLabel.setText("Select a Familiar");
			GridData gd1 = new GridData(SWT.CENTER, SWT.CENTER, true, true);
			gd1.horizontalSpan = 2;
			sorcererLabel.setLayoutData(gd1);
			sorcererLabel.pack();

			// list of available familiars
			CCombo famList = new CCombo(classExtrasShell, SWT.DROP_DOWN | SWT.READ_ONLY);
			String[] familiars = {"Bat", "Cat", "Hawk", "Lizard", "Owl", "Rat", "Raven", "Snake", "Toad", "Weasel"};
			famList.add("<none>");
			for (int i = 0; i < familiars.length; i++){
				famList.add(familiars[i]);
			}
			GridData gd2 = new GridData(SWT.CENTER, SWT.CENTER, true, false);
			gd2.horizontalSpan = 2;
			famList.setLayoutData(gd2);
			famList.pack();
			famList.select(0);

			// label - add custom familiar
			Label customLabel = new Label(classExtrasShell, SWT.NONE);
			customLabel.setText("OR Enter Custom: ");
			GridData gd3 = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
			customLabel.setLayoutData(gd3);
			customLabel.pack();

			// text input for custom companion
			Text customInput = new Text(classExtrasShell, SWT.BORDER);
			GridData gd4 = new GridData(SWT.FILL, SWT.CENTER, true, false);
			customInput.setLayoutData(gd4);
			customInput.pack();

			// user can either select from the list OR enter a custom
			// when the list is selected, the custom input is erased
			// when the custom input is selected, the list is deselected 
			customInput.addListener(SWT.MouseUp, new Listener() {
				public void handleEvent(Event e) {
					famList.select(0);
				}
			});

			famList.addListener(SWT.MouseUp, new Listener() {
				public void handleEvent(Event e) {
					customInput.setText("");
				}
			});

			// label - select specialty school
			Label sorcererLabel2 = new Label(classExtrasShell, SWT.NONE);
			sorcererLabel2.setText("Select a Specialty School");
			GridData gd5 = new GridData(SWT.CENTER, SWT.CENTER, true, true);
			gd5.horizontalSpan = 2;
			sorcererLabel2.setLayoutData(gd5);
			sorcererLabel2.pack();

			// list of specialty schools
			CCombo ssList = new CCombo(classExtrasShell, SWT.DROP_DOWN | SWT.READ_ONLY);
			ssList.add("<none>");
			for (int i = 0; i < GameState.schoolsOfMagic.length; i++){
				ssList.add(GameState.schoolsOfMagic[i]);
			}
			GridData gd6 = new GridData(SWT.CENTER, SWT.CENTER, true, false);
			gd6.horizontalSpan = 2;
			ssList.setLayoutData(gd6);
			ssList.select(0);
			ssList.pack();

			// label - select forbidden school(s)
			Label sorcererLabel3 = new Label(classExtrasShell, SWT.NONE);
			sorcererLabel3.setText("Select Prohibited Schools");
			GridData gd7 = new GridData(SWT.CENTER, SWT.CENTER, true, true);
			gd7.horizontalSpan = 2;
			sorcererLabel3.setLayoutData(gd7);
			sorcererLabel3.pack();

			// list of prohibited schools
			CCombo psList1 = new CCombo(classExtrasShell, SWT.DROP_DOWN | SWT.READ_ONLY);
			GridData gd8 = new GridData(SWT.FILL, SWT.CENTER, true, false);
			psList1.setLayoutData(gd8);
			psList1.pack();
			psList1.setEnabled(false);

			// list of prohibited schools
			CCombo psList2 = new CCombo(classExtrasShell, SWT.DROP_DOWN | SWT.READ_ONLY);
			GridData gd9 = new GridData(SWT.FILL, SWT.CENTER, true, false);
			psList2.setLayoutData(gd9);
			psList2.pack();
			psList2.setEnabled(false);

			ssList.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					psList1.setBackground(null);
					psList2.setBackground(null);
					psList1.removeAll();
					psList2.removeAll();
					if (ssList.getSelectionIndex() == 0) {
						psList1.setEnabled(false);
						psList2.setEnabled(false);
						return;
					}
					for (int i = 0; i < GameState.schoolsOfMagic.length; i++) {
						if (!(ssList.getItem(ssList.getSelectionIndex()).equals(GameState.schoolsOfMagic[i])))
							if (!(GameState.schoolsOfMagic[i]).equals("Divination")) {
								psList1.add(GameState.schoolsOfMagic[i]);
							}
					}
					psList1.pack();
					psList1.setEnabled(true);
					psList2.setEnabled(false);
					classExtrasShell.layout();
				}
			});
			
			ssList.addListener(SWT.MouseUp, new Listener() {
				public void handleEvent(Event e) {
					psList1.setBackground(null);
					psList2.setBackground(null);
				}
			});

			psList1.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					psList1.setBackground(null);
					psList2.setBackground(null);
					if (ssList.getItem(ssList.getSelectionIndex()).equals("Divination"))
						return;
					psList2.removeAll();
					for (int i = 0; i < psList1.getItemCount(); i++) {
						if (i != psList1.getSelectionIndex()) {
							psList2.add(psList1.getItem(i));
						}
					}
					psList2.pack();
					psList2.setEnabled(true);
					classExtrasShell.layout();
				}
			});
			
			psList1.addListener(SWT.MouseUp, new Listener() {
				public void handleEvent(Event e) {
					psList1.setBackground(null);
					psList2.setBackground(null);
				}
			});

			psList2.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					psList1.setBackground(null);
					psList2.setBackground(null);
				}
			});
			
			psList2.addListener(SWT.MouseUp, new Listener() {
				public void handleEvent(Event e) {
					psList1.setBackground(null);
					psList2.setBackground(null);
				}
			});

			// cancel button
			Button cancel = new Button(classExtrasShell, SWT.PUSH);
			cancel.setText("Cancel");
			GridData gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
			cancel.setLayoutData(gd);
			cancel.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					classExtrasShell.dispose();
					finished = false;
					popUpOpen = false;
				}
			});
			
			// done button
			Button done = new Button(classExtrasShell, SWT.PUSH);
			done.setText("Done");
			GridData doneGD = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
			done.setLayoutData(doneGD);
			done.pack();
			done.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					boolean error = false;
					// save familiar
					if (famList.getSelectionIndex() > 0) {
						character.setFamiliar(famList.getItem(famList.getSelectionIndex()));
					} else if (customInput.getText().length() > 0) {
						character.setFamiliar(customInput.getText());
					}
					// save specialty school
					if (ssList.getSelectionIndex() > 0) {
						if (psList1.getSelectionIndex() == -1) {
							psList1.setBackground(new Color(dev, 255, 100, 100));
							error = true;
						} else if (psList2.getSelectionIndex() == -1 && !ssList.getItem(ssList.getSelectionIndex()).equals("Divination")) {
							psList2.setBackground(new Color(dev, 255, 100, 100));
							error = true;
						} else {
							character.setWizardSpecialtySchool(ssList.getItem(ssList.getSelectionIndex()));
							String[] prohibitedSchools = new String[2];
							if (psList2.getSelectionIndex() != -1) {
								prohibitedSchools[0] = psList1.getItem(psList1.getSelectionIndex());
								prohibitedSchools[1] = psList2.getItem(psList2.getSelectionIndex());
								character.setWizardProhibitedSchools(prohibitedSchools);
							} else {
								prohibitedSchools = new String[1];
								prohibitedSchools[0] = psList1.getItem(psList1.getSelectionIndex());
							}
							character.setWizardProhibitedSchools(prohibitedSchools);

						}
					}
					if (!error) {
						classExtrasShell.dispose();
						finished = true;
						popUpOpen = false;
					}
				}
			});
			break;
		}
		}		

		// open shell
		classExtrasShell.pack();
		CharacterWizard.center(classExtrasShell);
		classExtrasShell.open();

		// check if disposed
		while (!classExtrasShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		return finished;
	}

	public Composite getWiz1() { return wiz1; }

	private void createNextPage() {
		cw.wizPageCreated[1] = true;
		cw.wizs.add(new Wiz2(cw, dev, WIDTH, HEIGHT, wizPanel, 
				wizLayout, wizPages, cw.getBaseAbilityScores()));
	}

}
