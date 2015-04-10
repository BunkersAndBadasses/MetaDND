package guis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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

import core.GameState;
import core.Main;
import core.character;
import entity.ClassEntity;
import entity.DNDEntity;
import entity.FeatEntity;
import entity.RaceEntity;

/* TODO
 * 
 * add get from references
 * 
 * add listeners to class and sec class drop downs, fix next button - updateClass in wiz3
 * 
 * 
 */


public class Wiz2 {

	private Composite wiz2;
	private Device dev;
	private int WIDTH;
	private int HEIGHT;
	private character character;
	private Composite panel;
	private Composite home;
	private Composite homePanel;
	private StackLayout layout;
	private StackLayout homeLayout;
	private ArrayList<Composite> wizPages;
	private Composite nextPage;
	private int wizPagesSize;
	
	private Label badRaceSelect;
	private Label badClassSelect;
	private Label badSearch;
	private Combo raceDropDown;
	private Combo classDropDown;
	private Combo secClassDropDown;
	private final String[] races = {"Dwarf", "Elf", "Gnome", "Half-Elf", "Half-Orc", "Halfling", "Human"}; // TODO add import from references?
	private final String[] classes = {"Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Wizard"};

	public Wiz2(Device dev, int WIDTH, int HEIGHT, final character character,
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz2 = wizPages.get(1);
		this.dev = dev;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.character = character;
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(2);
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

		// secondary class label
		Label secClassLabel = new Label(wiz2, SWT.NONE);
		secClassLabel.setText("Secondary Class: ");
		secClassLabel.setLocation(WIDTH-230,150);
		secClassLabel.pack();

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


		// drop down menus for race, class, and secondary class
		// race drop down menu
		raceDropDown = new Combo(wiz2, SWT.DROP_DOWN | SWT.READ_ONLY);
		// TODO add from references instead
		for (int i = 0; i < races.size(); i++) {
			raceDropDown.add(races.get(i).getName());
		}
		raceDropDown.setLocation(100,HEIGHT/2 - 75);
		raceDropDown.addListener(SWT.Selection, new Listener () {
			public void handleEvent(Event event) {
				int index = raceDropDown.getSelectionIndex();
			}
		});
		raceDropDown.pack();

		// class drop down menu
		classDropDown = new Combo(wiz2, SWT.DROP_DOWN | SWT.READ_ONLY);
		// TODO add from references instead
		for (int i = 0; i < classes.size(); i++) {
			classDropDown.add(classes.get(i).getName());
		}
		classDropDown.setLocation(WIDTH/2 - 70,HEIGHT/2 - 75);
		classDropDown.addListener(SWT.Selection, new Listener () {
			public void handleEvent(Event event) {
				int index = classDropDown.getSelectionIndex();
				secClassDropDown.deselect(index + 1);
			}
		});
		classDropDown.pack();

		// secondary class drop down menu
		secClassDropDown = new Combo(wiz2, SWT.DROP_DOWN | SWT.READ_ONLY);
		// TODO add from references instead
		secClassDropDown.add("");
		for (int i = 0; i < classes.size(); i++) {
			secClassDropDown.add(classes.get(i).getName());
		}
		secClassDropDown.setLocation(WIDTH-225,HEIGHT/2 - 75);
		secClassDropDown.addListener(SWT.Selection, new Listener () {
			public void handleEvent(Event event) {
				int index = secClassDropDown.getSelectionIndex();
				if (index == 0)
					return;
				classDropDown.deselect(index - 1);
			}
		});
		secClassDropDown.pack();
		secClassDropDown.setEnabled(false);


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

				// if all goes well, save race/class
				CharacterWizard.getCharacter().setCharRace(races.get(raceDropDown.getSelectionIndex()));
				CharacterWizard.getCharacter().setCharClass(classes.get(classDropDown.getSelectionIndex()));
				int secClassIndex = secClassDropDown.getSelectionIndex();
				if (secClassIndex < 1)
					CharacterWizard.getCharacter().setCharSecClass(null);
				else 
					CharacterWizard.getCharacter().setCharSecClass(classes.get(secClassIndex));
				//				Wiz3.updateCharRace();
				//				Wiz3.updateCharClass();
				//				Wiz3.updateCharSecClass();

//				// change to next page
//				if (CharacterWizard.wizPageNum < wizPagesSize - 1)
//					CharacterWizard.wizPageNum++;
//				if (!CharacterWizard.wizPageCreated[2])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();

				// clear any past error messages
				badRaceSelect.setVisible(false);
				badClassSelect.setVisible(false);
			}
		});


		// back button
		//		Button wiz2BackButton = CharacterWizard.createBackButton(wiz2, panel, layout);
		//		wiz2BackButton.addListener(SWT.Selection, new Listener() {
		//			public void handleEvent(Event event) {
		//				badSearch.setVisible(false);
		//				badRaceSelect.setVisible(false);
		//				badClassSelect.setVisible(false);
		//			}
		//		});


		// cancel button
		Button wiz2CancelButton = CharacterWizard.createCancelButton(wiz2, home,
				homePanel, homeLayout);
		wiz2CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.cancel)
					CharacterWizard.reset();
			}
		});
	}
	/**
	 * creates a 'search' button. does not set location or add listener.
	 * literally only creates a button with a specific size with the text set 
	 * to "Search"
	 * @return
	 */
	private Button createSearchButton(Composite c) {
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
	private Button createAddCustomButton(Composite c) {
		Button addCustomButton = new Button(c, SWT.PUSH);
		addCustomButton.setText("Add Custom");
		addCustomButton.setSize(100,30);
		return addCustomButton;
	}

	public Composite getWiz2() { return wiz2; }

	private void createNextPage() {
		CharacterWizard.wizPageCreated[2] = true;
		CharacterWizard.wizs.add(new Wiz3(dev, WIDTH, HEIGHT, character, panel, home,
				homePanel, layout, homeLayout, wizPages, CharacterWizard.baseAbilityScores));
		layout.topControl = nextPage;
		panel.layout();
	}

}
