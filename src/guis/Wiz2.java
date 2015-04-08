package guis;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import entity.*;
import core.Character;

/* TODO
 * 
 * add get from references
 * 
 * add listeners to class and sec class drop downs, fix next button - updateClass in wiz3
 * 
 * 
 */


public class Wiz2 {

	private static Composite wiz2;
	private static Device dev;
	private static int WIDTH;
	private static int HEIGHT;
	private static Character character;
	private static Composite panel;
	private static Composite home;
	private static Composite homePanel;
	private static StackLayout layout;
	private StackLayout homeLayout;
	private static ArrayList<Composite> wizPages;
	private static Composite nextPage;
	private int wizPagesSize;

	private static String charRace = "";
	private static String charClass = "";
	private static String charSecClass = "";
	private static Label badRaceSelect;
	private static Label badClassSelect;
	private static Label badSearch;
	private static Combo raceDropDown;
	private static Combo classDropDown;
	private static Combo secClassDropDown;
	private final static String[] races = {"Dwarf", "Elf", "Gnome", "Half-Elf", "Half-Orc", "Halfling", "Human"}; // TODO add import from references?
	private final static String[] classes = {"Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Wizard"};

	public Wiz2(Device dev, int WIDTH, int HEIGHT, final Character character,
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz2 = wizPages.get(1);
		Wiz2.dev = dev;
		Wiz2.WIDTH = WIDTH;
		Wiz2.HEIGHT = HEIGHT;
		Wiz2.character = character;
		Wiz2.panel = panel;
		Wiz2.home = home;
		Wiz2.homePanel = homePanel;
		Wiz2.layout = layout;
		this.homeLayout = homeLayout;
		Wiz2.wizPages = wizPages;
		Wiz2.nextPage = wizPages.get(2);
		this.wizPagesSize = wizPages.size();

		createPageContent();

	}

	private void createPageContent() {
		Label wiz2Label = new Label(wiz2, SWT.NONE);
		wiz2Label.setText("Select Class and Race");
		wiz2Label.pack();

		// labels for race, class, and secondary class fields
		// race label
		Label raceLabel = new Label(wiz2, SWT.NONE);
		raceLabel.setText("Race: ");
		raceLabel.setLocation(130,150);
		raceLabel.pack();

		// class label
		Label classLabel = new Label(wiz2, SWT.NONE);
		classLabel.setText("Class: ");
		classLabel.setLocation(WIDTH/2 - 40,150);
		classLabel.pack();

		// secondary class label TODO
		Label secClassLabel = new Label(wiz2, SWT.NONE);
		secClassLabel.setText("Secondary Class: ");
		secClassLabel.setLocation(WIDTH-230,150);
		secClassLabel.pack();


		// drop down menus for race, class, and secondary class
		// race drop down menu
		raceDropDown = new Combo(wiz2, SWT.DROP_DOWN | SWT.READ_ONLY);
		// TODO add from references instead
		for (int i = 0; i < races.length; i++) {
			raceDropDown.add(races[i]);
		}
		raceDropDown.setLocation(100,HEIGHT/2 - 75);
		raceDropDown.addListener(SWT.Selection, new Listener () {
			public void handleEvent(Event event) {
				int index = raceDropDown.getSelectionIndex();
				charRace = races[index];
			}
		});
		raceDropDown.pack();

		// class drop down menu
		classDropDown = new Combo(wiz2, SWT.DROP_DOWN | SWT.READ_ONLY);
		// TODO add from references instead
		for (int i = 0; i < classes.length; i++) {
			classDropDown.add(classes[i]);
		}
		classDropDown.setLocation(WIDTH/2 - 70,HEIGHT/2 - 75);
		classDropDown.addListener(SWT.Selection, new Listener () {
			public void handleEvent(Event event) {
				int index = classDropDown.getSelectionIndex();
				charClass = classes[index];
				secClassDropDown.deselect(index + 1);
			}
		});
		classDropDown.pack();

		// secondary class drop down menu
		secClassDropDown = new Combo(wiz2, SWT.DROP_DOWN | SWT.READ_ONLY);
		// TODO add from references instead
		secClassDropDown.add("");
		for (int i = 0; i < classes.length; i++) {
			secClassDropDown.add(classes[i]);
		}
		secClassDropDown.setLocation(WIDTH-225,HEIGHT/2 - 75);
		secClassDropDown.addListener(SWT.Selection, new Listener () {
			public void handleEvent(Event event) {
				int index = secClassDropDown.getSelectionIndex();
				if (index == 0)
					return;
				charSecClass = classes[index - 1];
				classDropDown.deselect(index - 1);
			}
		});
		secClassDropDown.pack();


		// error handling
		// this appears when an item is not selected and search is clicked
		badSearch = new Label(wiz2, SWT.NONE);
		badSearch.setForeground(new Color(dev,255,0,0));
		badSearch.setBounds(WIDTH/2 - 117,HEIGHT/2 + 70,234,30);
		badSearch.setVisible(false);
		badSearch.setText("you must select an item to search!");

		// this appears when a race is not selected
		badRaceSelect = new Label(wiz2, SWT.NONE);
		badRaceSelect.setForeground(new Color(dev,255,0,0));
		badRaceSelect.setBounds(WIDTH/2 - 80,HEIGHT/2 + 105,160,30);
		badRaceSelect.setVisible(false);
		badRaceSelect.setText("you must select a race!");

		// this appears when a class is not selected
		badClassSelect = new Label(wiz2, SWT.NONE);
		badClassSelect.setForeground(new Color(dev,255,0,0));
		badClassSelect.setBounds(WIDTH/2 -80,HEIGHT/2 + 140,160,30);
		badClassSelect.setVisible(false);
		badClassSelect.setText("you must select a class!");


		// search buttons - searches references using selection in drop down 
		Button raceSearchButton = createSearchButton(wiz2);
		raceSearchButton.setLocation(106, HEIGHT/2 - 30);
		raceSearchButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (raceDropDown.getText().equals("")) {
					// nothing is selected to be searched - display error
					badSearch.setVisible(true);
				} else {
					badSearch.setVisible(false);
					// launch search TODO
				}
			}
		});

		Button classSearchButton = createSearchButton(wiz2);
		classSearchButton.setLocation(WIDTH/2 - 58, HEIGHT/2 - 30);
		classSearchButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (classDropDown.getText().equals("")) {
					// nothing is selected to be searched - display error
					badSearch.setVisible(true);
				} else {
					badSearch.setVisible(false);
					// launch search TODO
				}
			}
		});

		Button secClassSearchButton = createSearchButton(wiz2);
		secClassSearchButton.setLocation(WIDTH - 213, HEIGHT/2 - 30);
		secClassSearchButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (secClassDropDown.getText().equals("")) {
					// nothing is selected to be searched - display error
					badSearch.setVisible(true);
				} else {
					badSearch.setVisible(false);
					// launch search TODO
				}
			}
		});		


		// add custom buttons - launches respective wizard to add new item
		Button raceAddCustomButton = createAddCustomButton(wiz2);
		raceAddCustomButton.setLocation(97, HEIGHT/2 + 20);
		raceAddCustomButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// launch race wizard TODO
			}
		});

		Button classAddCustomButton = createAddCustomButton(wiz2);
		classAddCustomButton.setLocation(WIDTH/2 - 67, HEIGHT/2 + 20);
		classAddCustomButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// launch class wizard TODO
			}
		});

		Button secClassAddCustomButton = createAddCustomButton(wiz2);
		secClassAddCustomButton.setLocation(WIDTH - 222, HEIGHT/2 + 20);
		secClassAddCustomButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// launch class wizard TODO
			}
		});


		// next button
		Button wiz2NextButton = CharacterWizard.createNextButton(wiz2);
		wiz2NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				badSearch.setVisible(false);

				// error checking
				boolean error = false;
//				badRaceSelect.setVisible(false);	// clear any past errors
//				// check if user selected a race
//
//				if (charRace.length() == 0) {
//					badRaceSelect.setVisible(true);
//					error = true;
//				}
//
//				badClassSelect.setVisible(false);	// clear any past errors
//				// check if user selected a class
//				if (charClass.length() == 0) {
//					badClassSelect.setVisible(true);
//					error = true;					
//				} // TODO uncomment when done testing

				// user cannot move on with an error
				if (error) return;

				// change to next page
				if (CharacterWizard.wizPageNum < wizPagesSize - 1)
					CharacterWizard.wizPageNum++;
				if (!CharacterWizard.wizPageCreated[2])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
				
				// if all goes well, save race/class

				CharacterWizard.getCharacter().setCharRace(charRace);
				CharacterWizard.getCharacter().setCharClass(charClass);
				if (charSecClass.length() != 0)
					CharacterWizard.getCharacter().setCharSecClass(charSecClass);
				Wiz3.updateCharRace();
				Wiz3.updateCharClass();
				Wiz3.updateCharSecClass();


				// clear any past error messages
				badRaceSelect.setVisible(false);
				badClassSelect.setVisible(false);
			}
		});


		// back button
		Button wiz2BackButton = CharacterWizard.createBackButton(wiz2, panel, layout);
		wiz2BackButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				badSearch.setVisible(false);
				badRaceSelect.setVisible(false);
				badClassSelect.setVisible(false);
			}
		});


		// cancel button
		Button wiz2CancelButton = CharacterWizard.createCancelButton(wiz2, home,
				homePanel, homeLayout);
		wiz2CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.cancel)
					cancelClear();
			}
		});
	}
	/**
	 * creates a 'search' button. does not set location or add listener.
	 * literally only creates a button with a specific size with the text set 
	 * to "Search"
	 * @return
	 */
	private static Button createSearchButton(Composite c) {
		Button searchButton = new Button(c, SWT.PUSH);
		searchButton.setText("Search");
		searchButton.setSize(80,30);
		return searchButton;
	}

	/**
	 * creates a 'add custom' button. does not set location or add listener.
	 * literally only creates a button with a specific size with the text set 
	 * to "Add Custom"
	 * @return
	 */
	private static Button createAddCustomButton(Composite c) {
		Button addCustomButton = new Button(c, SWT.PUSH);
		addCustomButton.setText("Add Custom");
		addCustomButton.setSize(100,30);
		return addCustomButton;
	}

	public Composite getWiz2() { return wiz2; }

	private void createNextPage() {
		CharacterWizard.wizPageCreated[2] = true;
		new Wiz3(dev, WIDTH, HEIGHT, character, panel, home,
				homePanel, layout, homeLayout, wizPages, CharacterWizard.baseAbilityScores);
		layout.topControl = nextPage;
		panel.layout();
	}

	public static void cancelClear() {
		CharacterWizard.reset();
		Wiz1.cancelClear();
		raceDropDown.deselectAll();
		classDropDown.deselectAll();
		secClassDropDown.deselectAll();
		charRace = "";
		charClass = "";
		charSecClass = "";
		badRaceSelect.setVisible(false);
		badClassSelect.setVisible(false);
		badSearch.setVisible(false);
	}

}
