/*
 * CHOOSE FEATS
 */

/*TODO
 * martial weapon not prompting for weapon selection
 * auto feats added twice to character?
 * null pointer issue
 * add race bonus feats
 * martial/exotic - show only those weapons, not all
 * if weapon familiarity, add to martial list
 * prerequisite checking on pop-up
 */

package guis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
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

import core.CharFeat;
import core.GameState;
import core.character;
import core.Main;
import entity.ClassEntity;
import entity.DNDEntity;
import entity.DeityEntity;
import entity.FeatEntity;
import entity.SkillEntity;
import entity.WeaponEntity;

public class Wiz5 {

	private Composite wiz5;
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
	private int numFeats;
	private int numBonusFeats = 0;
	private ClassEntity charClass;
	private ArrayList<FeatEntity> feats = new ArrayList<FeatEntity>();
	private ArrayList<CharFeat> charFeats = new ArrayList<CharFeat>();
	private ArrayList<FeatEntity> bonusFeats = new ArrayList<FeatEntity>();	
	List charFeatsList;
	
	private Shell bonusFeatShell;
	private Shell featSpecialShell;
	
	private boolean specialValid = false;
	private boolean bonusDone = false;
	private boolean bonusOpen = false;
	private boolean specialOpen = false;
	
	private Label featsLabel;

	public Wiz5(CharacterWizard cw, Device dev, int WIDTH, int HEIGHT,
			final Composite panel, final StackLayout layout, 
			final ArrayList<Composite> wizPages) {
		wiz5 = wizPages.get(4);
		this.cw = cw;
		this.dev = dev;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.character = cw.getCharacter();
		this.wizPanel = panel;
		this.wizLayout = layout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(5);
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
		GridLayout layout = new GridLayout(2, true);
		wiz5.setLayout(layout);

		GridData gd;
		
		Label wiz6Label = new Label(wiz5, SWT.NONE);
		wiz6Label.setText("Choose Feats");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		wiz6Label.setLayoutData(gd);
		wiz6Label.pack();
		
		// number of remaining feats
		numFeats = 1;
		if (cw.getCharacter().getCharRace().getName().equals("Human"))
			numFeats += 1;
		
		
		////////// instantiate layout //////////
		
		GridLayout gl = new GridLayout(7, true);
		
		Composite inner = new Composite(wiz5, SWT.NONE);
		inner.setBounds(5, 20, WIDTH-10, HEIGHT-110);
		inner.setLayout(gl);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 2;
		inner.setLayoutData(gd);
		
		// feats label
		featsLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 7;
		featsLabel.setLayoutData(gd);
		
		// details label
		Label detailsLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 7;
		detailsLabel.setLayoutData(gd);
		
		// feat list
		List featsList = new List(inner, SWT.V_SCROLL | SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 3;
		gd.verticalSpan = 2;
		featsList.setLayoutData(gd);
		
		// add button
		Button addButton = new Button(inner, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.END, true, true);
		addButton.setLayoutData(gd);
		
		// character feat list
		charFeatsList = new List(inner, SWT.V_SCROLL | SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 3;
		gd.verticalSpan = 2;
		charFeatsList.setLayoutData(gd);
		
		// remove button
		Button removeButton = new Button(inner, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, true);
		removeButton.setLayoutData(gd);
		
		// error message
		Label errorMsg = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 7;
		errorMsg.setLayoutData(gd);
		
		
		////////// create content //////////
		
		// number of feats remaining label
		featsLabel.setText("Number of Feats Remaining: " + numFeats);
		
		// details label
		detailsLabel.setText("Double click on a feat to see details");
				
		// available feats list
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
		
		// selected feats list
		for (int i = 0; i < charFeats.size(); i++)
			charFeatsList.add(charFeats.get(i).getFeat().getName());
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
		
		// error message
		//errorMsg.setLocation(WIDTH/2 - 150, HEIGHT - 75);
		errorMsg.setForeground(new Color(dev, 255, 0, 0));
		errorMsg.setVisible(false);
		errorMsg.pack();                                                              
		
		// add automatic character feats
		charClass = character.getCharClass();
		String[] autoFeats = charClass.getBonusFeats();
		for (int i = 0; i < autoFeats.length; i ++) {
			if (autoFeats[i].indexOf('[') != -1) {
				String special = autoFeats[i].substring(autoFeats[i].indexOf('[')+1, autoFeats[i].indexOf(']'));
				String featName = autoFeats[i].substring(0, autoFeats[i].indexOf('[')-1);
				charFeats.add(new CharFeat((FeatEntity)Main.gameState.feats.get(featName), special));
			} else {
				charFeats.add(0, new CharFeat((FeatEntity)Main.gameState.feats.get(autoFeats[i])));
			}
		}
		updateCharFeatsList();
		numBonusFeats = charFeats.size();
		
		// add feat button
		addButton.setText("Add >");
		addButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				errorMsg.setVisible(false);
				boolean error = false;
				// check if the user can add another feat
				if (numFeats == 0) {
					errorMsg.setText("You cannot add any more feats");
					errorMsg.pack();
					errorMsg.setVisible(true);
					inner.layout();
					return;
				}
				int index = featsList.getSelectionIndex();
				// check if a feat was selected
				if (index == -1) {
					errorMsg.setText("You must select a feat to add");
					errorMsg.pack();
					errorMsg.setVisible(true);
					inner.layout();
					return;
				}
				CharFeat feat = new CharFeat(feats.get(index));
				// launches popup to select feat special
				if (feat.getFeat().getApplications() != null) {
					if (!selectFeatSpecial(feat))
						return;
				}
				// check if replacing simple weapon proficiency for select weapons to all
				if (feat.getFeat().getName().equals("Simple Weapon Proficiency")) {
					int i = 0;
					while (i < charFeats.size()) {
						if (charFeats.get(i).getFeat().getName().equals(feat.getFeat().getName())) {
							if (!(charFeats.get(i).getSpecial().equalsIgnoreCase("All"))) {
								charFeats.remove(i);
								updateCharFeatsList();
								numBonusFeats--;
							} else i++;
						} else i++;
					}
					feat.setSpecial("All");
				}
				// check if that feat was already added
				for(int i = 0; i < charFeats.size(); i++) {
					if (charFeats.get(i).getFeat().getName().equals(feat.getFeat().getName())) {
						// feat found - check if that feat can be added multiple times
						if (!feat.getFeat().canHaveMultiple()) {
							// feat cannot be added multiple times
							errorMsg.setText("Feat already added");
							errorMsg.pack();
							errorMsg.setVisible(true);
							error = true;
							inner.layout();
						}
						else {
							// feat can be added multiple times
							if (charFeats.get(i).getFeat().canStack()) {
								// feat benefits can stack - increment count of feat
								charFeats.get(i).incCount();
							}
							else {
								// feat benefits cannot stack - check if the exact same feat is added
								if (charFeats.get(i).getSpecial().equals(feat.getSpecial())) {
									errorMsg.setText("Feat already added");
									errorMsg.pack();
									errorMsg.setVisible(true);
									error = true;
									inner.layout();
								}
							}
						}
					}
				}

				// if something went wrong, do not perform the add
				if (error)
					return;
				// pop-up for extra info (i.e. weapons, schools of magic, skills, spells);
				if (!checkPrerequisites(charFeats, feat, character)) {
					errorMsg.setText("Feat requirements not met");
					errorMsg.pack();
					errorMsg.setVisible(true);
					inner.layout();
					return;					
				}


				// otherwise, add the feat
				charFeats.add(feat);
				updateCharFeatsList();
				numFeats--;
				featsLabel.setText("Number of Feats Remaining: " + numFeats);
				featsLabel.setBackground(null);
				featsLabel.pack();
			}
		});
		
		// remove feat button
		removeButton.setText("< Remove");
		removeButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				errorMsg.setVisible(false);
				// check if there are any feats to remove
				if (charFeats.isEmpty()) {
					errorMsg.setText("There are no feats to remove");
					errorMsg.pack();
					errorMsg.setVisible(true);
					inner.layout();
					return;
				}
				int index = charFeatsList.getSelectionIndex();
				// check if a feat is selected
				if (index == -1){
					errorMsg.setText("You must select a feat to remove");
					errorMsg.pack();
					errorMsg.setVisible(true);
					inner.layout();
					return;
				}
				// user cannot remove a bonus feat
				if (index < numBonusFeats) {
					errorMsg.setText("You cannot remove a class bonus feat");
					errorMsg.pack();
					errorMsg.setVisible(true);
					inner.layout();
					return;
				}
				// if nothing goes wrong, remove the feat
				charFeats.remove(index);
				updateCharFeatsList();
				numFeats++;
				featsLabel.setText("Number of Feats Remaining: " + numFeats);
				featsLabel.setBackground(null);
				featsLabel.pack();
			}
		});
		
		Button wiz6CancelButton = cw.createCancelButton(wiz5);
		gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		wiz6CancelButton.setLayoutData(gd);
		wiz6CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (cw.cancel)
					cw.reset();
			}
		});
		
		Button wiz6NextButton = cw.createNextButton(wiz5);
		gd = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		wiz6NextButton.setLayoutData(gd);
		wiz6NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// cannot continue if there is a pop up open
				if (specialOpen) {
					featSpecialShell.forceActive();
					return;
				}
				if (bonusOpen) {
					bonusFeatShell.forceActive();
					return;
				}
				
				// error checking
				if (numFeats > 0) {
					featsLabel.setBackground(new Color(dev, 255, 100, 100));
					return;
				}
				
				// if the pop up is closed
				if (!createBonusPopUp())
					return;
				
				// if all is good, save to character
				for (int i = 0; i < charFeats.size(); i++)
					character.addFeat(charFeats.get(i));
				
				// switch to next page
				if (cw.wizPageNum < wizPagesSize - 1)
					cw.wizPageNum++;
				if (!cw.wizPageCreated[5])
					createNextPage();
				wizLayout.topControl = nextPage;
				wizPanel.layout();
			}
		});
		
		//Button wiz6BackButton = cw.createBackButton(wiz5, panel, layout);

		inner.layout();
		wiz5.layout();
	}
	
	private boolean createBonusPopUp() {
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
		
		bonusDone = false;
		
		
		if (charClass.getName().equalsIgnoreCase("Cleric")) {
			String[] domains = character.getClericDomains();
			if (domains != null) {
				boolean war = false;
				for (int i = 0; i < domains.length; i++) {
					if (domains[i].equalsIgnoreCase("War")) 
						war = true;
				}
				if (war) {
					String deityName = character.getDeity();
					DeityEntity deity = (DeityEntity)Main.gameState.deities.get(deityName);
					if (deity != null) {
						String weaponName = deity.getFavoredweapon();
						WeaponEntity weapon = (WeaponEntity)Main.gameState.weapons.get(weaponName);
						if (weapon != null) {
							String type = weapon.getType();
							if (!type.equalsIgnoreCase("Simple")) {
								FeatEntity weaponFeat = (FeatEntity)Main.gameState.feats.get(type + " Weapon Proficiency");
								CharFeat weaponCharFeat = new CharFeat(weaponFeat, weaponName);
								charFeats.add(weaponCharFeat);
							}
							CharFeat weaponFocus = new CharFeat((FeatEntity)Main.gameState.feats.get("Weapon Focus"), weaponName);
							charFeats.add(weaponFocus);
						}
					}
				}
			}
		}
		updateCharFeatsList();
		numBonusFeats = charFeats.size();
		
		// compile list of bonus feats (from which the user can choose one)
		if (charClass.getName().toLowerCase().equals("fighter")){
			for (int i = 0; i < feats.size(); i++){
				if (feats.get(i).getFighterBonus() != null)
					bonusFeats.add(feats.get(i));
			}
		} else if (charClass.getName().toLowerCase().equals("monk")){
			bonusFeats.add((FeatEntity)Main.gameState.feats.get("Improved Grapple"));
			bonusFeats.add((FeatEntity)Main.gameState.feats.get("Stunning Fist"));
		} else
			return true;

		bonusOpen = true;
		
		// create shell
		Display display = wiz5.getDisplay();
		bonusFeatShell = new Shell(display);
		bonusFeatShell.setImage(new Image(display, "images/bnb_logo.gif"));
		bonusFeatShell.setText("Select Bonus Feat");
		GridLayout gridLayout = new GridLayout(2, true);
		bonusFeatShell.setLayout(gridLayout);
		bonusFeatShell.addListener(SWT.Close, new Listener() {
	        public void handleEvent(Event event) {
	            bonusDone = false;
	            bonusOpen = false;
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
		bonusFeatCombo.addListener(SWT.MouseDown, new Listener() {
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
				numBonusFeats++;
				charFeats.add(0, new CharFeat(bonusFeats.get(bonusFeatCombo.getSelectionIndex())));
				updateCharFeatsList();
				bonusDone = true;
				bonusOpen = false;
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
		
		return bonusDone;
	}
	
	public static boolean checkPrerequisites(ArrayList<CharFeat> charFeats, CharFeat feat, character character) {
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
		 * 	Spell Focus (selected school of magic)
		 */
		if (feat.getFeat().getPrerequisites() == null) 
			return true;
		String[] reqs = feat.getFeat().getPrerequisites();
		if (feat.getFeat().getName().equalsIgnoreCase("Improved Familiar")) {
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
				int value = Integer.parseInt(reqs[i].replaceAll("[^0-9]", ""));
				if (character.getBaseAttackBonus() < value) 
					return false;
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
				String featName = reqs[i].substring(0, reqs[i].indexOf("with") - 1);
				// make sure the character has that feat
				FeatEntity reqFeat = (FeatEntity) Main.gameState.feats.get(featName);
				// if that feat is a valid feat, check it
				if (reqFeat != null) {
					// check if user has already added the required feat
					boolean found = false;
					for (int j = 0; j < charFeats.size() && !found; j++) {
						// find feat
						if (charFeats.get(j).getFeat().getName().equals(reqFeat.getName())) {
							// check special
							if (charFeats.get(j).getSpecial().equals(reqFeat.getSpecial()) || reqFeat.getSpecial().equals("All"))
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
						if (charFeats.get(j).getFeat().getName().equals(reqFeat.getName())) {
							// the required feat has already been added
							found = true;
						}
					}
					if (!found)
						return false;
				}
			} else if (reqs[i].contains("Spell Focus (")) {
				// TODO fix this after addding charFeats
				String featName = reqs[i].substring(0, reqs[i].indexOf('(') - 1);
				// assume the prerequisite is another feat
				FeatEntity reqFeat = (FeatEntity) Main.gameState.feats.get(featName);
				// if that feat is a valid feat, check it
				if (reqFeat != null) {
					// check if user has already added the required feat
					boolean found = false;
					for (int j = 0; j < charFeats.size() && !found; j++) {
						if (charFeats.get(j).getFeat().getName().equals(reqFeat.getName())) {
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
						if (charFeats.get(j).getFeat().getName().equals(reqFeat.getName())) {
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
	private boolean selectFeatSpecial(CharFeat feat) {
		// create shell
		
		specialOpen = true;
				Display display = wiz5.getDisplay();
				featSpecialShell = new Shell(display);
				featSpecialShell.setImage(new Image(display, "images/bnb_logo.gif"));
				featSpecialShell.setText("Apply Feat");
				GridLayout gridLayout = new GridLayout(2, true);
				featSpecialShell.setLayout(gridLayout);
				featSpecialShell.addListener(SWT.Close, new Listener() {
			        public void handleEvent(Event event) {
			            specialValid = false;
			            specialOpen = false;
			        }
			    });
				
//				String[] specialsA = feat.getFeat().getApplications();

				// label - select a feat special
				Label selectFeatSpecial = new Label(featSpecialShell, SWT.WRAP);
				selectFeatSpecial.setText("Apply Feat:");
				GridData selectGD = new GridData(SWT.CENTER, SWT.CENTER, true, true);
				selectGD.horizontalSpan = 2;
				selectFeatSpecial.setLayoutData(selectGD);
				selectFeatSpecial.pack();
				
				// drop down menu containing feat special options
				CCombo specialsCombo = new CCombo(featSpecialShell, SWT.DROP_DOWN | SWT.READ_ONLY);

				ArrayList<String> specials = getSpecials(feat.getFeat());
				if (specials == null)
					return true;
				if (specials.size() == 1) {
					feat.setSpecial(specials.get(0));
					return true;
				}
				for (int i = 0; i < specials.size(); i++) {
					specialsCombo.add(specials.get(i));
				}
				GridData specialsGD = new GridData(SWT.CENTER, SWT.CENTER, true, true);
				specialsGD.horizontalSpan = 2;
				specialsCombo.setLayoutData(specialsGD);
				specialsCombo.addListener(SWT.MouseDown, new Listener() {
					public void handleEvent(Event event) {
						specialsCombo.setBackground(null);
					}
				});
				specialsCombo.pack();
				
				// done button
				Button done = new Button(featSpecialShell, SWT.PUSH);
				done.setText("Done");
				GridData doneGD = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
				doneGD.horizontalSpan = 2;
				done.setLayoutData(doneGD);
				done.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						if (specialsCombo.getSelectionIndex() == -1) {
							specialsCombo.setBackground(new Color(dev, 255, 100, 100));
							return;
						}
						feat.setSpecial(specialsCombo.getItem(specialsCombo.getSelectionIndex()));
						featSpecialShell.dispose();
						specialValid = true;
						specialOpen = false;
					}
				});
				done.pack();

				// open shell
				featSpecialShell.pack();
				CharacterWizard.center(featSpecialShell);
				featSpecialShell.open();
				
				// check if disposed
				while (!featSpecialShell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
		return specialValid;
	}
	
	public static ArrayList<String> getSpecials(FeatEntity feat) {
		String[] specialsArray = feat.getApplications();
		if (specialsArray == null)
			return null;
		ArrayList<String> specials = new ArrayList<String>();
		for (int i = 0; i < specialsArray.length; i++) {
			switch (specialsArray[i]) {
			case ("weapons"): {
				Collection<DNDEntity> weaponsCol =  Main.gameState.weapons.values();
				Iterator<DNDEntity> itr = weaponsCol.iterator();
				ArrayList<WeaponEntity> weapons = new ArrayList<WeaponEntity>();
				while (itr.hasNext()) {
					weapons.add((WeaponEntity) itr.next());
				}
				for (int j = 0; j < weapons.size(); j++) {
					specials.add(weapons.get(j).getName());
				}
				break;
			}
			case ("schools of magic"):
				for (int j = 0; j < GameState.schoolsOfMagic.length; j++) {
					specials.add(GameState.schoolsOfMagic[j]);
				}
				break;
			case ("skills"): {
				Collection<DNDEntity> skillsCol =  Main.gameState.skills.values();
				Iterator<DNDEntity> itr = skillsCol.iterator();
				ArrayList<SkillEntity> skills = new ArrayList<SkillEntity>();
				while (itr.hasNext()) {
					skills.add((SkillEntity) itr.next());
				}						
				for (int j = 0; j < skills.size(); j++) {
					specials.add(skills.get(j).getName());
				}
				break;
			}
			case ("selection of spells"): {
				specials.add("selection of spells");
			}
			default:
				specials.add(specialsArray[i]);
			}
		}
		return specials;
	}
	
	private void updateCharFeatsList() {
		charFeatsList.removeAll();
		for (int i = 0; i < charFeats.size(); i++){
			CharFeat curr = charFeats.get(i);
			String temp = curr.getFeat().getName();
			if (curr.getSpecial() != null)
				temp += " [" + curr.getSpecial() + "]";
			if (curr.getCount() > 1)
				temp += ": " + curr.getCount();
			charFeatsList.add(temp);
		}
	}

	private void createNextPage() {
		cw.wizPageCreated[5] = true;
		cw.wizs.add(new Wiz6(cw, dev, WIDTH, HEIGHT, wizPanel, wizLayout, wizPages));
	}

	public Composite getWiz5() { return wiz5; }
}
