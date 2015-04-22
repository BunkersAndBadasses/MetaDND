/*
 * CHOOSE FEATS
 */

package guis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import core.GameState;
import core.character;
import core.Main;
import entity.ClassEntity;
import entity.DNDEntity;
import entity.FeatEntity;

public class Wiz6 {

	private Composite wiz6;
	private CharacterWizard cw;
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
	private int numFeats;
	private int numBonusFeats = 0;
	private ClassEntity charClass;
	private ArrayList<FeatEntity> feats = new ArrayList<FeatEntity>();
	private ArrayList<FeatEntity> charFeats = new ArrayList<FeatEntity>();
	List charFeatsList;
	
	private Label numFeatsLabel;

	public Wiz6(CharacterWizard cw, Device dev, int WIDTH, int HEIGHT,
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz6 = wizPages.get(5);
		this.cw = cw;
		this.dev = dev;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.character = cw.getCharacter();
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(6);
		this.wizPagesSize = wizPages.size();
		
		// get feats from references 
		Collection<DNDEntity> featsCol =  Main.gameState.feats.values();
		Iterator<DNDEntity> itr = featsCol.iterator();
		while (itr.hasNext()) {
			feats.add((FeatEntity) itr.next());
		}
		
		createPageContent();
		charClass = character.getCharClass();
	}

	private void createPageContent() {
		Label wiz6Label = new Label(wiz6, SWT.NONE);
		wiz6Label.setText("Choose Feats");
		wiz6Label.pack();
		
		// "number of feats remaining: " label
		Label featsLabel = new Label(wiz6, SWT.NONE);
		featsLabel.setLocation(240,30);
		featsLabel.setText("Number of Feats Remaining:");
		featsLabel.pack();

		// number of remaining feats
		numFeats = 1;
		if (cw.getCharacter().getCharRace().getName().equals("Human"))
			numFeats += 1;
		
		// number of remaining feats label
		numFeatsLabel = new Label(wiz6, SWT.NONE);
		numFeatsLabel.setLocation(435, 30);
		numFeatsLabel.setText(Integer.toString(numFeats));
		numFeatsLabel.pack();
		
		// search label
		Label searchLabel = new Label(wiz6, SWT.NONE);
		searchLabel.setLocation(230, 60);
		searchLabel.setText("Double click on a feat to see details");
		searchLabel.pack();
		
		// grid layout for both available and selected feat lists
		FillLayout featLayout = new FillLayout();
		
		// create scrollable list of feats
		final ScrolledComposite featScreenScroll = new ScrolledComposite(wiz6, SWT.V_SCROLL | SWT.BORDER);
		featScreenScroll.setBounds(10, 110, WIDTH/2 - 65, HEIGHT - 210);
	    featScreenScroll.setExpandHorizontal(true);
	    featScreenScroll.setExpandVertical(true);
	    featScreenScroll.setMinWidth(WIDTH);
		final Composite featListScreen = new Composite(featScreenScroll, SWT.NONE);
		featScreenScroll.setContent(featListScreen);
		featListScreen.setSize(featListScreen.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		featListScreen.setLayout(featLayout);
				
		// TODO scroll not working, okay because for now, only 1-2 feats can be added anyways
		// create scrollable list of selected feats
		final ScrolledComposite charFeatScreenScroll = new ScrolledComposite(wiz6, SWT.V_SCROLL | SWT.BORDER);
		charFeatScreenScroll.setBounds(WIDTH/2 + 55, 110, WIDTH/2 - 75, HEIGHT - 210);
	    charFeatScreenScroll.setExpandHorizontal(true);
	    charFeatScreenScroll.setExpandVertical(true);
	    charFeatScreenScroll.setMinWidth(WIDTH);
		final Composite charFeatScreen = new Composite (charFeatScreenScroll, SWT.BORDER);
		charFeatScreenScroll.setContent(charFeatScreen);
		charFeatScreen.setSize(charFeatScreen.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		charFeatScreen.setLayout(featLayout);
		
		// available feats list
		List featsList = new List(featListScreen, SWT.NONE);
		for (int i = 0; i < feats.size(); i++) {
			featsList.add(feats.get(i).getName());
		}
		featsList.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e){
				int index = featsList.getSelectionIndex();
				if (index == -1)
					return;
				String featName = featsList.getItem(index);
				((FeatEntity)Main.gameState.feats.get(featName)).toTooltipWindow();
			}
			@Override
			//leave blank, but must have
			public void widgetSelected(SelectionEvent e) {}
		});
		featsList.pack();
		featScreenScroll.setMinHeight(featsList.getBounds().height);
	    	
		// selected feats list
		charFeatsList = new List(charFeatScreen, SWT.NONE);
		for (int i = 0; i < charFeats.size(); i++)
			charFeatsList.add(charFeats.get(i).getName());
		charFeatsList.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e){
				int index = charFeatsList.getSelectionIndex();
				if (index == -1)
					return;
				String featName = charFeatsList.getItem(index);
				((FeatEntity)Main.gameState.feats.get(featName)).toTooltipWindow();
			}
			@Override
			//leave blank, but must have
			public void widgetSelected(SelectionEvent e) {}
		});
		charFeatsList.pack();
		
		// error message
		Label errorMsg = new Label(wiz6, SWT.NONE);
		errorMsg.setLocation(WIDTH/2 - 150, HEIGHT - 75);
		errorMsg.setForeground(new Color(dev, 255, 0, 0));
		errorMsg.setVisible(false);
		errorMsg.pack();                                                              
		
		// add feat button

		Button addButton = new Button(wiz6, SWT.PUSH);
		addButton.setText("Add >");
		addButton.setLocation(WIDTH/2 - 25, HEIGHT/2 - 50);
		addButton.pack();
		addButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				errorMsg.setVisible(false);
				boolean error = false;
				// check if the user can add another feat
				if (numFeats == 0) {
					errorMsg.setText("You cannot add any more feats");
					errorMsg.pack();
					errorMsg.setVisible(true);
					return;
				}
				int index = featsList.getSelectionIndex();
				// check if a feat was selected
				if (index == -1) {
					errorMsg.setText("You must select a feat to add");
					errorMsg.pack();
					errorMsg.setVisible(true);
					return;
				}
				// check if that feat was already added
				FeatEntity feat = (FeatEntity) Main.gameState.feats.get(featsList.getItem(index));
				for(int i = 0; i < charFeats.size(); i++) {
					if (charFeats.get(i).getName().equals(featsList.getItem(index))) {
						// check if that feat can be added multiple times TODO
						//TODO skill focus, spell focus, can be added multiple times
						// spell mastery, toughness
						// otherwise, error
						errorMsg.setText("Feat already added");
						errorMsg.pack();
						errorMsg.setVisible(true);
						error = true;
					}
				}
				// if something went wrong, do not perform the add
				if (error)
					return;
				//TODO pop-up for extra info (i.e. spell focus = school, weapon focus = weapon)
				if (!checkPrerequisites(feat, character)) {
					errorMsg.setText("Feat requirements not met");
					errorMsg.pack();
					errorMsg.setVisible(true);
					return;
				}


				// otherwise, add the feat
				// check if feat applies to a specific weapon/spell etc TODO
				// pop-up if it does
				charFeatsList.add(featsList.getItem(index));
				charFeats.add(feats.get(index));
				numFeats--;
				numFeatsLabel.setText(Integer.toString(numFeats));
				numFeatsLabel.setBackground(null);
				numFeatsLabel.pack();
				charFeatsList.pack();
				charFeatScreenScroll.setMinHeight(charFeatsList.getBounds().height);
				charFeatScreen.layout();
				charFeatScreenScroll.layout();
			}
		});
		
		// remove feat button
		Button removeButton = new Button(wiz6, SWT.PUSH);
		removeButton.setText("< Remove");
		removeButton.setLocation(WIDTH/2 - 38, HEIGHT/2);
		removeButton.pack();
		removeButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				errorMsg.setVisible(false);
				// check if there are any feats to remove
				if (charFeats.isEmpty()) {
					errorMsg.setText("There are no feats to remove");
					errorMsg.pack();
					errorMsg.setVisible(true);
					return;
				}
				int index = charFeatsList.getSelectionIndex();
				// check if a feat is selected
				if (index == -1){
					errorMsg.setText("You must select a feat to remove");
					errorMsg.pack();
					errorMsg.setVisible(true);
					return;
				}
				// user cannot remove a bonus feat
				if (index < numBonusFeats) {
					errorMsg.setText("You cannot remove a class bonus feat");
					errorMsg.pack();
					errorMsg.setVisible(true);
					return;
				}
				// if nothing goes wrong, remove the feat
				charFeatsList.remove(index);
				charFeats.remove(index);
				numFeats++;
				numFeatsLabel.setText(Integer.toString(numFeats));
				numFeatsLabel.setBackground(null);
				numFeatsLabel.pack();
				charFeatsList.pack();
				charFeatScreenScroll.setMinHeight(charFeatsList.getBounds().height);
				charFeatScreen.layout();
				charFeatScreenScroll.layout();
			}
		});
		
		featListScreen.pack();
		charFeatScreen.pack();

		Button wiz6NextButton = cw.createNextButton(wiz6);
		wiz6NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// error checking
				if (numFeats > 0) {
					numFeatsLabel.setBackground(new Color(dev, 255, 100, 100));
					return;
				}
				
				// if all is good, save to character
				for (int i = 0; i < charFeats.size(); i++)
					character.addFeat(charFeats.get(i));
				
				// switch to next page
				if (cw.wizPageNum < wizPagesSize - 1)
					cw.wizPageNum++;
				if (!cw.wizPageCreated[6])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});
		
		//Button wiz6BackButton = cw.createBackButton(wiz5, panel, layout);
		Button wiz6CancelButton = cw.createCancelButton(wiz6, home, homePanel, homeLayout);
		wiz6CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (cw.cancel)
					cw.reset();
			}
		});
	}
	
	void createBonusPopUp() {
		// get lists of bonus feats
		
		/*
		 * barbarian: simple/martial weapon proficiency, light/medium armor, all shields(not towers)
		 * bard: simple weapons (plus extras - p.28), light armor, light shields, bard - no silent spell
		 * cleric: simple weapons, all armors, all shields(not towers), martial weapons if deity's favored weapon is martial, weapon focus for deities favored weapon
		 * druid: light and medium armor, shields(not towers)
		 * fighter: bonus feat (from list - must meet prerequisite), simple and martial weapons, all armor, all shields
		 * monk: improved grapple/stunning fist (does not need to meet prerequisites), improved unarmed strike
		 * paladin: simple and martial weapons, all armor, all shields(not towers)
		 * ranger: simple and martial weapons, light armor, light shields(not towers), track
		 * rogue: simple weapons, light armor
		 * sorcerer: simple weapons
		 * wizard: scribe scroll
		 */
		
		ArrayList<FeatEntity> bonusFeats = new ArrayList<FeatEntity>();	
		
		// add automatic feats (like armor/weapon proficiency)
		String[] autoFeats = charClass.getBonusFeats();
		for (int i = 0; i < autoFeats.length; i ++) {
			charFeats.add(0, (FeatEntity)Main.gameState.feats.get(autoFeats[i]));
			charFeatsList.add(charFeats.get(0).getName());
		}
		numBonusFeats = autoFeats.length;
		
		// compile list of bonus feats (from which the user can choose one)
		if (charClass.getName().toLowerCase().equals("fighter")){
			numBonusFeats++;
			for (int i = 0; i < feats.size(); i++){
				if (feats.get(i).getFighterBonus() != null)
					bonusFeats.add(feats.get(i));
			}
		} else if (charClass.getName().toLowerCase().equals("monk")){
			numBonusFeats++;
			bonusFeats.add((FeatEntity)Main.gameState.feats.get("Improved Grapple"));
			bonusFeats.add((FeatEntity)Main.gameState.feats.get("Stunning Fist"));
		} else
			return;
		
		// create shell
		Display display = wiz6.getDisplay();
		final Shell bonusFeatShell = new Shell(display);
		bonusFeatShell.setText("Select Bonus Feat");
		GridLayout gridLayout = new GridLayout(2, true);
		bonusFeatShell.setLayout(gridLayout);
		bonusFeatShell.addListener(SWT.Close, new Listener() {
	        public void handleEvent(Event event) {
	            event.doit = false;
	        	return;
	        }
	    });

		// label - select a bonus feat
		Label selectBonusFeat = new Label(bonusFeatShell, SWT.WRAP);
		selectBonusFeat.setText("Select A Bonus Feat");
		GridData selectGD = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		selectGD.horizontalSpan = 2;
		selectBonusFeat.setLayoutData(selectGD);
		selectBonusFeat.pack();
		
		// drop down menu containing bonus feat options
		CCombo bonusFeatCombo = new CCombo(bonusFeatShell, SWT.DROP_DOWN | SWT.READ_ONLY);
		for (int i = 0; i < bonusFeats.size(); i++)
			bonusFeatCombo.add(bonusFeats.get(i).getName());
		GridData featsGD = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		featsGD.horizontalSpan = 2;
		bonusFeatCombo.setLayoutData(featsGD);
		bonusFeatCombo.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				bonusFeatCombo.setBackground(new Color(dev, 255, 255, 255));
			}
		});
		bonusFeatCombo.pack();
		
		// done button
		Button done = new Button(bonusFeatShell, SWT.PUSH);
		done.setText("Done");
		GridData doneGD = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		doneGD.horizontalSpan = 2;
		done.setLayoutData(doneGD);
		done.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (bonusFeatCombo.getSelectionIndex() == -1) {
					bonusFeatCombo.setBackground(new Color(dev, 255, 100, 100));
					return;
				}
				charFeats.add(0, bonusFeats.get(bonusFeatCombo.getSelectionIndex()));
				charFeatsList.add(charFeats.get(0).getName());
				bonusFeatShell.dispose();
			}
		});
		done.pack();

		// open shell
		bonusFeatShell.pack();
		CharacterWizard.center(bonusFeatShell);
		bonusFeatShell.open();
		
		// check if disposed
		while (!bonusFeatShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public boolean checkPrerequisites(FeatEntity feat, character character) {
		/*
		 * prerequisite possibilities: 
		 * 
		 *           another feat
		 * 		Spell Focus (Conjuration)
		 *        Caster level x
		 * 		Str xx
		 * 		Int xx
		 * 		Dex xx
		 * 		Wis xx
		 * 		base attack bonus +x
		 * 		(plus Str 13 for bastard sword or dwarven waraxe)
		 * 		Ability to turn or rebuke creatures
		 * 		Proficiency with selected weapon
		 * 		Weapon Focus with selected weapon
		 * 		Greater Weapon Focus with selected weapon
		 * 		Weapon Specialization with selected weapon
		 * 		Fighter level x
		 * 		Ability to acquire a new familiar
		 * 		compatible alignment
		 * 		sufficiently high level
		 * 		Character level x
		 * 		Ride 1 rank
		 * 		wild shape ability
		 * 		Weapon Proficiency (crossbow type chosen)
		 * 		Wizard level x
		 */
		if (feat.getPrerequisites() == null) 
			return true;
		String[] reqs = feat.getPrerequisites();
		if (feat.getName().equalsIgnoreCase("Improved Familiar")) {
			// TODO
		}
		for (int i = 0; i < reqs.length; i++) {
			if (reqs[i].substring(0, 3).equalsIgnoreCase("Str ")) {
				int value = Integer.parseInt(reqs[i].substring(4));
				if (character.getAbilityScores()[GameState.STRENGTH] < value)
					return false;
			} else if (reqs[i].substring(0, 3).equalsIgnoreCase("Dex ")) {
				int value = Integer.parseInt(reqs[i].substring(4));
				if (character.getAbilityScores()[GameState.DEXTERITY] < value)
					return false;
			} else if (reqs[i].substring(0, 3).equalsIgnoreCase("Con ")) {
				int value = Integer.parseInt(reqs[i].substring(4));
				if (character.getAbilityScores()[GameState.CONSTITUTION] < value)
					return false;
			} else if (reqs[i].substring(0, 3).equalsIgnoreCase("Int ")) {
				int value = Integer.parseInt(reqs[i].substring(4));
				if (character.getAbilityScores()[GameState.INTELLIGENCE] < value)
					return false;
			} else if (reqs[i].substring(0, 3).equalsIgnoreCase("Wis ")) {
				int value = Integer.parseInt(reqs[i].substring(4));
				if (character.getAbilityScores()[GameState.WISDOM] < value)
					return false;
			} else if (reqs[i].substring(0, 3).equalsIgnoreCase("Cha ")) {
				int value = Integer.parseInt(reqs[i].substring(4));
				if (character.getAbilityScores()[GameState.CHARISMA] < value)
					return false;
			} else if (reqs[i].contains("base attack bonus")) {
				//				if (reqs[i].contains("plus Str 13")) {
				//					// TODO here bastard sword/dwarven waraxe
				//				}
			} else if (reqs[i].contains("Barbarian level")) {
				if (character.getCharClass().getName().equalsIgnoreCase("Barbarian")){
					int value = Integer.parseInt((reqs[i].substring(reqs[i].indexOf("level")).substring(6)));
					if (character.getLevel() < value)
						return false;
				} else 
					return false;
			} else if (reqs[i].contains("Bard level")) {
				if (character.getCharClass().getName().equalsIgnoreCase("Bard")){
					int value = Integer.parseInt((reqs[i].substring(reqs[i].indexOf("level")).substring(6)));
					if (character.getLevel() < value)
						return false;
				} else 
					return false;
			} else if (reqs[i].contains("Cleric level")) {
				if (character.getCharClass().getName().equalsIgnoreCase("Cleric")){
					int value = Integer.parseInt((reqs[i].substring(reqs[i].indexOf("level")).substring(6)));
					if (character.getLevel() < value)
						return false;
				} else 
					return false;
			} else if (reqs[i].contains("Druid level")) {
				if (character.getCharClass().getName().equalsIgnoreCase("Druid")){
					int value = Integer.parseInt((reqs[i].substring(reqs[i].indexOf("level")).substring(6)));
					if (character.getLevel() < value)
						return false;
				} else 
					return false;
			} else if (reqs[i].contains("Fighter level")) {
				if (character.getCharClass().getName().equalsIgnoreCase("Fighter")){
					int value = Integer.parseInt((reqs[i].substring(reqs[i].indexOf("level")).substring(6)));
					if (character.getLevel() < value)
						return false;
				} else 
					return false;
			} else if (reqs[i].contains("Monk level")) {
				if (character.getCharClass().getName().equalsIgnoreCase("Monk")){
					int value = Integer.parseInt((reqs[i].substring(reqs[i].indexOf("level")).substring(6)));
					if (character.getLevel() < value)
						return false;
				} else 
					return false;
			} else if (reqs[i].contains("Paladin level")) {
				if (character.getCharClass().getName().equalsIgnoreCase("Paladin")){
					int value = Integer.parseInt((reqs[i].substring(reqs[i].indexOf("level")).substring(6)));
					if (character.getLevel() < value)
						return false;
				} else 
					return false;
			} else if (reqs[i].contains("Ranger level")) {
				if (character.getCharClass().getName().equalsIgnoreCase("Ranger")){
					int value = Integer.parseInt((reqs[i].substring(reqs[i].indexOf("level")).substring(6)));
					if (character.getLevel() < value)
						return false;
				} else 
					return false;
			} else if (reqs[i].contains("Sorcerer level")) {
				if (character.getCharClass().getName().equalsIgnoreCase("Sorcerer")){
					int value = Integer.parseInt((reqs[i].substring(reqs[i].indexOf("level")).substring(6)));
					if (character.getLevel() < value)
						return false;
				} else 
					return false;
			} else if (reqs[i].contains("Wizard level")) {
				if (character.getCharClass().getName().equalsIgnoreCase("Wizard")){
					int value = Integer.parseInt((reqs[i].substring(reqs[i].indexOf("level")).substring(6)));
					if (character.getLevel() < value)
						return false;
				} else 
					return false;
			} else if (reqs[i].contains("Caster level")) {
				if (!(character.getCharClass().getName().equalsIgnoreCase("Barbarian")
						|| character.getCharClass().getName().equalsIgnoreCase("Fighter")
						|| character.getCharClass().getName().equalsIgnoreCase("Monk")
						|| character.getCharClass().getName().equalsIgnoreCase("Rogue"))){
					int value = Integer.parseInt((reqs[i].substring(reqs[i].indexOf("level")).substring(6)));
					if (character.getLevel() < value)
						return false;
				} else 
					return false;
			} else if (reqs[i].contains("Character level")) {
				int value = Integer.parseInt((reqs[i].substring(reqs[i].indexOf("level")).substring(6)));
				if (character.getLevel() < value)
					return false;
			} else if (reqs[i].equalsIgnoreCase("wild shape ability")) {

			} else if (reqs[i].contains("Ride") &&  reqs[i].contains("rank")) {

			} else if (reqs[i].contains("with selected weapon")) {
				// TODO fix this after addding charFeats
				String featName = reqs[i].substring(0, reqs[i].indexOf("with") - 1);
				// assume the prerequisite is another feat
				FeatEntity reqFeat = (FeatEntity) Main.gameState.feats.get(featName);
				// if that feat is a valid feat, check it
				if (reqFeat != null) {
					// check if user has already added the required feat
					boolean found = false;
					for (int j = 0; j < charFeats.size() && !found; j++) {
						if (charFeats.get(j).getName().equals(reqFeat.getName())) {
							// the required feat has already been added
							found = true;
						}
					}
					if (!found)
						return false;
				}
				//				if (reqs[i].contains("Weapon Focus")) {
				//
				//				} else if (reqs[i].contains("Greater Weapon Focus")) {
				//
				//				} else if (reqs[i].contains("Weapon Specialization")) {
				//
				//				} 
			} else if (reqs[i].equalsIgnoreCase("Ability to turn or rebuke creatures")) {

			}  else if (reqs[i].equalsIgnoreCase("Weapon Proficiency (crossbow type chosen)")) {
				// TODO fix this after addding charFeats
				String featName = reqs[i].substring(0, reqs[i].indexOf('(') - 1);
				// assume the prerequisite is another feat
				FeatEntity reqFeat = (FeatEntity) Main.gameState.feats.get(featName);
				// if that feat is a valid feat, check it
				if (reqFeat != null) {
					// check if user has already added the required feat
					boolean found = false;
					for (int j = 0; j < charFeats.size() && !found; j++) {
						if (charFeats.get(j).getName().equals(reqFeat.getName())) {
							// the required feat has already been added
							found = true;
						}
					}
					if (!found)
						return false;
				}
			} else if (reqs[i].equalsIgnoreCase("Spell Focus (Conjuration)")) {
				// TODO fix this after addding charFeats
				String featName = reqs[i].substring(0, reqs[i].indexOf('(') - 1);
				// assume the prerequisite is another feat
				FeatEntity reqFeat = (FeatEntity) Main.gameState.feats.get(featName);
				// if that feat is a valid feat, check it
				if (reqFeat != null) {
					// check if user has already added the required feat
					boolean found = false;
					for (int j = 0; j < charFeats.size() && !found; j++) {
						if (charFeats.get(j).getName().equals(reqFeat.getName())) {
							// the required feat has already been added
							found = true;
						}
					}
					if (!found)
						return false;
				}
			} else {
				// assume the prerequisite is another feat
				FeatEntity reqFeat = (FeatEntity) Main.gameState.feats.get(reqs[i]);
				// if that feat is a valid feat, check it
				if (reqFeat != null) {
					// check if user has already added the required feat
					boolean found = false;
					for (int j = 0; j < charFeats.size() && !found; j++) {
						if (charFeats.get(j).getName().equals(reqFeat.getName())) {
							// the required feat has already been added
							found = true;
						}
					}
					if (!found)
						return false;
				}
				// otherwise, assume it's fine
			}
		}
		return true;
	}

	private void createNextPage() {
		cw.wizPageCreated[6] = true;
		cw.wizs.add(new Wiz7(cw, dev, WIDTH, HEIGHT, panel, home,
				homePanel, layout, homeLayout, wizPages));
	}

	public Composite getWiz6() { return wiz6; }
}
