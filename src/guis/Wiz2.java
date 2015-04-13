/*
 * CHOOSE RACE AND CLASS
 */

package guis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
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

import core.Main;
import core.character;
import entity.ClassEntity;
import entity.DNDEntity;
import entity.RaceEntity;

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
	
	private Label badRaceSelect;
	private Label badClassSelect;
	private Label badSearch;
	private Combo raceDropDown;
	private Combo classDropDown;
	private Combo secClassDropDown;
	public Wiz2(Device dev, int WIDTH, int HEIGHT,
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz2 = wizPages.get(1);
		this.dev = dev;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.character = CharacterWizard.getCharacter();
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(2);

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

		// race drop down menu
		raceDropDown = new Combo(wiz2, SWT.DROP_DOWN | SWT.READ_ONLY);
		for (int i = 0; i < races.size(); i++) {
			raceDropDown.add(races.get(i).getName());
		}
		raceDropDown.setLocation(100,HEIGHT/2 - 75);
		raceDropDown.pack();

		// class drop down menu
		classDropDown = new Combo(wiz2, SWT.DROP_DOWN | SWT.READ_ONLY);
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

/*
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
					// launch search
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
					// launch search
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
					// launch search
				}
			}
		});		


		// add custom buttons - launches respective wizard to add new item
		Button raceAddCustomButton = createAddCustomButton(wiz2);
		raceAddCustomButton.setLocation(97, HEIGHT/2 + 20);
		raceAddCustomButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// launch race wizard
			}
		});

		Button classAddCustomButton = createAddCustomButton(wiz2);
		classAddCustomButton.setLocation(WIDTH/2 - 67, HEIGHT/2 + 20);
		classAddCustomButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// launch class wizard
			}
		});

		Button secClassAddCustomButton = createAddCustomButton(wiz2);
		secClassAddCustomButton.setLocation(WIDTH - 222, HEIGHT/2 + 20);
		secClassAddCustomButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// launch class wizard
			}
		});
*/ // TODO add later

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
				String charClass = classes.get(classDropDown.getSelectionIndex()).getName();
				if (charClass.equalsIgnoreCase("druid") 
						| charClass.equalsIgnoreCase("ranger")
						| charClass.equalsIgnoreCase("sorcerer")
						| charClass.equalsIgnoreCase("wizard")
						)
					extraStuffWindow(charClass);
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

//	/**
//	 * creates a 'search' button. does not set location or add listener.
//	 * literally only creates a button with a specific size with the text set 
//	 * to "Search"
//	 * @return
//	 */
//	private Button createSearchButton(Composite c) {
//		Button searchButton = new Button(c, SWT.PUSH);
//		searchButton.setText("Search");
//		searchButton.setSize(80,30);
//		return searchButton;
//	}
//
//	/**
//	 * creates a 'add custom' button. does not set location or add listener.
//	 * literally only creates a button with a specific size with the text set 
//	 * to "Add Custom"
//	 * @return
//	 */
//	private Button createAddCustomButton(Composite c) {
//		Button addCustomButton = new Button(c, SWT.PUSH);
//		addCustomButton.setText("Add Custom");
//		addCustomButton.setSize(100,30);
//		return addCustomButton;
//	}
// TODO add later
	
	private void extraStuffWindow(String charClass) {
		// druid - animal companion
		// ranger - favored enemy
		// sorcerer - familiar
		// wizard - specialty school, familiar
		
		// create shell
		Display display = wiz2.getDisplay();
		final Shell classExtrasShell = new Shell(display);
		classExtrasShell.setText("Class Extras");
		GridLayout gridLayout = new GridLayout(2, true);
		classExtrasShell.setLayout(gridLayout);
		classExtrasShell.addListener(SWT.Close, new Listener() {
	        public void handleEvent(Event event) {
	            event.doit = false;
	        	return;
	        }
	    });
		
		charClass = charClass.toLowerCase();
		switch(charClass) {
		case ("druid"):
			// label - select an animal companion
			Label druidLabel = new Label(classExtrasShell, SWT.NONE);
			druidLabel.setText("Select an animal companion");
			GridData gd1 = new GridData(SWT.CENTER, SWT.CENTER, true, true);
			gd1.horizontalSpan = 2;
			druidLabel.setLayoutData(gd1);
			druidLabel.pack();
			
			// list of available animal companions
			Combo acList = new Combo(classExtrasShell, SWT.DROP_DOWN | SWT.READ_ONLY);
			String[] companions = {"badger", "camel", "dire rat", "dog", "riding dog", "eagle", "hawk", "horse(light)", "horse(heavy)", "owl", "pony", "snake(small)", "snake(medium)", "wolf", "porpoise", "shark(medium)", "squid"};
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
			GridData gd4 = new GridData(SWT.LEFT, SWT.CENTER, true, false);
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
			
			break;
		case ("ranger"):
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
			
			feList.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					if (feList.getItem(feList.getSelectionIndex()).equals("Humanoid")){
						subtypeList.deselectAll();
						subtypeList.removeAll();
						String[] humanoids = {"aquatic", "dwarf", "elf", "goblinoid", "gnoll", "gnome", "halfling", "human", "orc", "reptillian"};
						for (int i = 0; i < humanoids.length; i++)
							subtypeList.add(humanoids[i]);
						subtypeList.pack();
					} else if (feList.getItem(feList.getSelectionIndex()).equals("Outsider")){
						subtypeList.deselectAll();
						subtypeList.removeAll();
						String[] outsiders = {"air", "chaotic", "earth", "evil", "fire", "good", "lawful", "native", "water"};
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

			break;
		case ("sorcerer"):
			break;
		default: // wizard
			break;
		}
		// TODO 
		// add cancel button
		
		// done button
		Button done = new Button(classExtrasShell, SWT.PUSH);
		done.setText("Done");
		GridData doneGD = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		doneGD.horizontalSpan = 2;
		done.setLayoutData(doneGD);
		done.pack();
		done.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				classExtrasShell.dispose();
			}
		});
		
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
	
		
	}
	
	public Composite getWiz2() { return wiz2; }

	private void createNextPage() {
		CharacterWizard.wizPageCreated[2] = true;
		CharacterWizard.wizs.add(new Wiz3(dev, WIDTH, HEIGHT, panel, home,
				homePanel, layout, homeLayout, wizPages, CharacterWizard.baseAbilityScores));
		layout.topControl = nextPage;
		panel.layout();
	}
}