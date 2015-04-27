/*
 * CHOOSE WEAPONS/ARMOR
 */

/*
 * barbarian: simple/martial weapon proficiency, light/medium armor, all shields(not towers)
 * bard: simple weapons (plus longsword, rapier, sap, short sword, shortbow, and whip), light armor, light shields
 * cleric: simple weapons, all armors, all shields(not towers), martial weapons if deity's favored weapon is martial, weapon focus for deities favored weapon
 * druid: club, dagger, dart, quarterstaff, scimitar, sickle, shortspear, sling, and spear, all natural attacks, light and medium armor, NO METAL ARMOR, all shields(again no metal)
 * fighter: bonus feat (from list - must meet prerequisite), simple and martial weapons, all armor, all shields
 * monk: club, crossbow(light or heavy), dagger, handaxe, javelin, kama, nunchaku, quarterstaff, sai, shuriken, siangham, sling, no armor or shields
 * paladin: simple and martial weapons, all armor, all shields(not towers)
 * ranger: simple and martial weapons, light armor, light shields(not towers)
 * rogue: simple weapons (plus hand crossbow, rapier, sap, shortbow, short sword), light armor, no shields
 * sorcerer: simple weapons, no armor, no shields
 * wizard: club, dagger, crossbow(light and heavy), quarterstaff, no armor, no shields  
 */

/*
 * TODO only show weapons/armor/shields the character is proficient with
 */

package guis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import entity.*;
import core.CharItem;
import core.GameState;
import core.Main;
import core.character;

public class Wiz8{

	private Composite wiz8;
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
	
	private boolean primaryGood = false;
	private boolean spellsGood = false;
	private boolean popUpOpen = false;
	
	private List charWeaponsList;
	private List charArmorList;
	private List charShieldsList;
	private List weaponsList;
	private List armorList;
	private List shieldsList;
	
	private List charSpellsList;
	private Label numSpellsLeft;
	private int[] numSpells;
	private int[] origNumSpells;
	private int bonusSpells = 0;
	private int wizHighestLevel;
	private Shell spellShell;
	private Shell primaryShell;
	
	private ArrayList<CharItem> charWeapons = new ArrayList<CharItem>();
	private ArrayList<CharItem> charArmor = new ArrayList<CharItem>();
	private ArrayList<CharItem> charShields = new ArrayList<CharItem>();
	private ArrayList<SpellEntity> charSpells = new ArrayList<SpellEntity>();


	public Wiz8(CharacterWizard cw, Device dev, int WIDTH, int HEIGHT, 
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz8 = wizPages.get(7);
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
		this.nextPage = wizPages.get(8);
		this.wizPagesSize = wizPages.size();
		spellShell = new Shell(wiz8.getDisplay());

		createPageContent();
	}

	private void createPageContent() {
		Label wiz8Label = new Label(wiz8, SWT.NONE);
		wiz8Label.setText("Choose Weapons and Armor");
		wiz8Label.pack();
		
		
		// initialize layout
		
		GridLayout gl = new GridLayout(6, true);
		
		Composite inner = new Composite(wiz8, SWT.NONE);
		inner.setBounds(5, 20, WIDTH-10, HEIGHT-110);
		inner.setLayout(gl);
		
		GridData gd;
		
		Label detailsLabel = new Label(inner, SWT.NONE);
		detailsLabel.setText("Double click on an item to see details");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 6;
		detailsLabel.setLayoutData(gd);
		
		Label weaponsLabel = new Label(inner, SWT.NONE);
		weaponsLabel.setText("Weapons");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		weaponsLabel.setLayoutData(gd);
		weaponsLabel.pack();
		
		Label armorLabel = new Label(inner, SWT.NONE);
		armorLabel.setText("Armor");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		armorLabel.setLayoutData(gd);
		armorLabel.pack();
		
		Label shieldsLabel = new Label(inner, SWT.NONE);
		shieldsLabel.setText("Shield");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		shieldsLabel.setLayoutData(gd);
		shieldsLabel.pack();
		
		charWeaponsList = new List(inner, SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 2;
		gd.verticalSpan = 2;
		charWeaponsList.setLayoutData(gd);
		charWeaponsList.pack();
		
		charArmorList = new List(inner, SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 2;
		gd.verticalSpan = 2;
		charArmorList.setLayoutData(gd);
		charArmorList.pack();
		
		charShieldsList = new List(inner, SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 2;
		gd.verticalSpan = 2;
		charShieldsList.setLayoutData(gd);
		charShieldsList.pack();
		
		Button addWeapon = new Button(inner, SWT.PUSH);
		addWeapon.setText("Add");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		addWeapon.setLayoutData(gd);
		addWeapon.pack();
		
		Button removeWeapon = new Button(inner, SWT.PUSH);
		removeWeapon.setText("Remove");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		removeWeapon.setLayoutData(gd);
		removeWeapon.pack();
		
		Button addArmor = new Button(inner, SWT.PUSH);
		addArmor.setText("Add");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		addArmor.setLayoutData(gd);
		addArmor.pack();
		
		Button removeArmor = new Button(inner, SWT.PUSH);
		removeArmor.setText("Remove");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		removeArmor.setLayoutData(gd);
		removeArmor.pack();
		
		Button addShield = new Button(inner, SWT.PUSH);
		addShield.setText("Add");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		addShield.setLayoutData(gd);
		addShield.pack();
		
		Button removeShield = new Button(inner, SWT.PUSH);
		removeShield.setText("Remove");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		removeShield.setLayoutData(gd);
		removeShield.pack();
		
		Label weaponsListLabel = new Label(inner, SWT.NONE);
		weaponsListLabel.setText("Weapons List");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		weaponsListLabel.setLayoutData(gd);
		weaponsListLabel.pack();
		
		Label armorListLabel = new Label(inner, SWT.NONE);
		armorListLabel.setText("Armor List");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		armorListLabel.setLayoutData(gd);
		armorListLabel.pack();
		
		Label shieldsListLabel = new Label(inner, SWT.NONE);
		shieldsListLabel.setText("Shield List");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		shieldsListLabel.setLayoutData(gd);
		shieldsListLabel.pack();
		
		weaponsList = new List(inner, SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 2;
		weaponsList.setLayoutData(gd);
		weaponsList.pack();
		
		armorList = new List(inner, SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 2;
		armorList.setLayoutData(gd);
		armorList.pack();
		
		shieldsList = new List(inner, SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 2;
		shieldsList.setLayoutData(gd);
		shieldsList.pack();
		
		inner.layout();
		
		
		// get content
		
		// get weapons from references
		Collection<DNDEntity> weaponsCol =  Main.gameState.weapons.values();
		Iterator<DNDEntity> weaponItr = weaponsCol.iterator();
		ArrayList<WeaponEntity> weapons = new ArrayList<WeaponEntity>();
		while (weaponItr.hasNext()) {
			weapons.add((WeaponEntity) weaponItr.next());
		}
		
		// get armor/shields from references
		Collection<DNDEntity> armorCol =  Main.gameState.armor.values();
		Iterator<DNDEntity> armorItr = armorCol.iterator();
		ArrayList<DNDEntity> armor = new ArrayList<DNDEntity>(); // can be armor or weapons?
		while (armorItr.hasNext()) {
			armor.add(armorItr.next());
		}
		
		// add weapons to list
		for (int i = 0; i < weapons.size(); i++) {
			weaponsList.add(weapons.get(i).getName());
		}
		
		// add armor/shields to list
		for (int i = 0; i < armor.size(); i++) {
			if (armor.get(i).getName().contains("Shield"))
				shieldsList.add(armor.get(i).getName());
			else
				armorList.add(armor.get(i).getName());
		}
		
		
		// double click listeners to launch tool tip window
		
		weaponsList.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e){
				int index = weaponsList.getSelectionIndex();
				if (index == -1)
					return;
				String itemName = weaponsList.getItem(index);
				((ItemEntity)Main.gameState.weapons.get(itemName)).toTooltipWindow();
			}
			@Override
			//leave blank, but must have
			public void widgetSelected(SelectionEvent e) {}
		});
		
		armorList.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e){
				int index = armorList.getSelectionIndex();
				if (index == -1)
					return;
				String itemName = armorList.getItem(index);
				((ItemEntity)Main.gameState.armor.get(itemName)).toTooltipWindow();
			}
			@Override
			//leave blank, but must have
			public void widgetSelected(SelectionEvent e) {}
		});
		
		shieldsList.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e){
				int index = shieldsList.getSelectionIndex();
				if (index == -1)
					return;
				String itemName = shieldsList.getItem(index);
				((ItemEntity)Main.gameState.armor.get(itemName)).toTooltipWindow();
			}
			@Override
			//leave blank, but must have
			public void widgetSelected(SelectionEvent e) {}
		});
		
		
		// add/remove button listeners
		
		 addWeapon.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) { 
				int index = weaponsList.getSelectionIndex();
				if (index == -1)
					return;
				// check if it's already added
				for (int i = 0; i < charWeapons.size(); i++) {
					if (charWeapons.get(i).getName().equalsIgnoreCase(weaponsList.getItem(index))) {
						charWeapons.get(i).incCount();
						updateCharWeaponsList();
						return;
					}
				}
				ItemEntity add = (WeaponEntity) Main.gameState.weapons.get(weaponsList.getItem(index));
				charWeapons.add(new CharItem(add));
				updateCharWeaponsList();
			}
		 });
		 removeWeapon.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) { 
				int index = charWeaponsList.getSelectionIndex();
				if (index == -1)
					return;
				charWeapons.remove(index);
				updateCharWeaponsList();
			}
		 });
		 addArmor.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) { 
				int index = armorList.getSelectionIndex();
				if (index == -1)
					return;
				for (int i = 0; i < charArmor.size(); i++) {
					if (charArmor.get(i).getName().equalsIgnoreCase(armorList.getItem(index))) {
						charArmor.get(i).incCount();
						updateCharArmorList();
						return;
					}
				}
				ItemEntity add = (ItemEntity) Main.gameState.armor.get(armorList.getItem(index));
				charArmor.add(new CharItem(add));
				updateCharArmorList();
			}
		 });
		 removeArmor.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) { 
				int index = charArmorList.getSelectionIndex();
				if (index == -1)
					return;
				charArmor.remove(index);
				updateCharArmorList();				
			}
		 });
		 addShield.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) { 
				int index = shieldsList.getSelectionIndex();
				if (index == -1)
					return;
				for (int i = 0; i < charShields.size(); i++) {
					if (charShields.get(i).getName().equalsIgnoreCase(shieldsList.getItem(index))) {
						charShields.get(i).incCount();
						updateCharShieldsList();
						return;
					}
				}
				ItemEntity add = (ItemEntity) Main.gameState.armor.get(shieldsList.getItem(index));
				charShields.add(new CharItem(add));
				updateCharShieldsList();
			}
		 });
		 removeShield.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) { 
				int index = charShieldsList.getSelectionIndex();
				if (index == -1)
					return;
				charShields.remove(index);
				updateCharShieldsList();
			}
		 });
		 
		
		// next button
		Button wiz8NextButton = cw.createNextButton(wiz8);
		wiz8NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// launch pop-up (if user clicks cancel, do not continue)
				if (!setPrimary())
					return;
				if (!selectSpells())
					return;
				
				//TODO this isn't working
				if (popUpOpen) {
					if (!primaryShell.isDisposed())
						primaryShell.forceActive();
					else if (!spellShell.isDisposed())
						spellShell.forceActive();
					return;
				}
				
				// save weapons
				character.setWeapons(charWeapons);
				// save armor
				character.setArmor(charArmor);
				// save shields
				character.setShields(charShields);
				
				// add armor bonus to ac
				ArmorEntity temp = null;
				try {
					temp = (ArmorEntity)character.getCurrArmor();
				} catch (Exception e) {
					character.setACArmorBonus(0);
				}
				if (temp == null)
					character.setACArmorBonus(0);
				else
					character.setACArmorBonus(temp.getArmorBonus());
				
				// add shield bonus to ac
				temp = null;
				try {
					temp = (ArmorEntity)character.getCurrShield();
				} catch (Exception e) {
					character.setACShieldBonus(0);
				}
				if (temp == null)
					character.setACShieldBonus(0);
				else
					character.setACShieldBonus(temp.getArmorBonus());
				
				// switch to next page
				if (cw.wizPageNum < wizPagesSize - 1)
					cw.wizPageNum++;
				if (!cw.wizPageCreated[8])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});
		
		
		// back button
		//Button wiz8BackButton = cw.createBackButton(wiz8, panel, layout);
		
		
		// cancel button
		Button wiz8CancelButton = cw.createCancelButton(wiz8, home, homePanel, homeLayout);
		wiz8CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (cw.cancel)
					cw.reset();
			}
		});
	}

	private boolean setPrimary() {
		// pop up window in which the user chooses their primary weapon, armor, and shield
		primaryGood = false;
		// create shell
		Display display = wiz8.getDisplay();
		primaryShell = new Shell(display);
		primaryShell.setText("Set Primary");
		GridLayout gridLayout = new GridLayout(2, true);
		primaryShell.setLayout(gridLayout);
		primaryShell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				primaryGood = false;
				popUpOpen = false;
			}
		});

		GridData gd;
		
		if (charWeapons.size() == 0 && charArmor.size() == 0 && charShields.size() == 0)
			return true;

		if (charWeapons.size() > 0) {

			Label primaryWeapon = new Label(primaryShell, SWT.NONE);
			primaryWeapon.setText("Select Primary Weapon");
			gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
			gd.horizontalSpan = 2;
			primaryWeapon.setLayoutData(gd);
			primaryWeapon.pack();

			Combo primaryWeaponList = new Combo(primaryShell, SWT.DROP_DOWN | SWT.READ_ONLY);
			for (int i = 0; i < charWeapons.size(); i++ ) {
				primaryWeaponList.add(charWeapons.get(i).getName());
			}
			gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
			gd.horizontalSpan = 2;
			primaryWeaponList.setLayoutData(gd);
			primaryWeaponList.pack();

			primaryWeaponList.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					int index = primaryWeaponList.getSelectionIndex();
					String weapon = primaryWeaponList.getItem(index);
					if (index == -1)
						return;
					WeaponEntity temp = (WeaponEntity) Main.gameState.weapons.get(weapon);
					character.setPrimaryWeapon(temp);
				}
			});
			
			if (charWeapons.size() == 1) {
				primaryWeaponList.select(0);
			}
			
			if (charWeapons.size() > 1) {
				
				Label secondaryWeapon = new Label(primaryShell, SWT.NONE);
				secondaryWeapon.setText("Select Secondary Weapon");
				gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
				gd.horizontalSpan = 2;
				secondaryWeapon.setLayoutData(gd);
				secondaryWeapon.pack();

				Combo secondaryWeaponList = new Combo(primaryShell, SWT.DROP_DOWN | SWT.READ_ONLY);
				gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
				gd.horizontalSpan = 2;
				secondaryWeaponList.setLayoutData(gd);
				secondaryWeaponList.setEnabled(false);
				
				secondaryWeaponList.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event e) {
						int index = secondaryWeaponList.getSelectionIndex();
						String weapon = secondaryWeaponList.getItem(index);
						if (index == -1)
							return;
						WeaponEntity temp = (WeaponEntity) Main.gameState.weapons.get(weapon);
						character.setSecondaryWeapon(temp);
					}
				});
				
				primaryWeaponList.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event e) {
						int index = primaryWeaponList.getSelectionIndex();
						secondaryWeaponList.removeAll();
						for (int i = 0; i < charWeapons.size(); i++ ) {
							if (!charWeapons.get(i).getName().equals(primaryWeaponList.getItem(index)))
								secondaryWeaponList.add(charWeapons.get(i).getName());
						}
						if (secondaryWeaponList.getItemCount() == 1)
							secondaryWeaponList.select(0);
						secondaryWeaponList.setEnabled(true);
						secondaryWeaponList.pack();
						primaryShell.layout();
					}
				});
				
			}
		}
		
		if (charArmor.size() > 0) {
			Label primaryArmor = new Label(primaryShell, SWT.NONE);
			primaryArmor.setText("Select Primary Armor");
			gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
			gd.horizontalSpan = 2;
			primaryArmor.setLayoutData(gd);
			primaryArmor.pack();

			Combo primaryArmorList = new Combo(primaryShell, SWT.DROP_DOWN | SWT.READ_ONLY);
			for (int i = 0; i < charArmor.size(); i++ ) {
				primaryArmorList.add(charArmor.get(i).getName());
			}
			gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
			gd.horizontalSpan = 2;
			primaryArmorList.setLayoutData(gd);
			primaryArmorList.pack();

			primaryArmorList.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					int index = primaryArmorList.getSelectionIndex();
					String armor = primaryArmorList.getItem(index);
					if (index == -1)
						return;
					ItemEntity temp = (ItemEntity) Main.gameState.armor.get(armor);
					character.setCurrArmor(temp);
				}
			});
			
			if (charArmor.size() == 1) {
				primaryArmorList.select(0);
			}
		}
		
		if (charShields.size() > 0) {
			Label primaryShield = new Label(primaryShell, SWT.NONE);
			primaryShield.setText("Select Primary Shield");
			gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
			gd.horizontalSpan = 2;
			primaryShield.setLayoutData(gd);
			primaryShield.pack();

			Combo primaryShieldList = new Combo(primaryShell, SWT.DROP_DOWN | SWT.READ_ONLY);
			for (int i = 0; i < charShields.size(); i++ ) {
				primaryShieldList.add(charShields.get(i).getName());
			}
			gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
			gd.horizontalSpan = 2;
			primaryShieldList.setLayoutData(gd);
			primaryShieldList.pack();

			primaryShieldList.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					int index = primaryShieldList.getSelectionIndex();
					String shield = primaryShieldList.getItem(index);
					if (index == -1)
						return;
					ItemEntity temp = (ItemEntity) Main.gameState.armor.get(shield);
					character.setCurrShield(temp);
				}
			});
			
			if (charShields.size() == 1) {
				primaryShieldList.select(0);
			}
		}
		
		popUpOpen = true;

		// cancel button
		Button cancel = new Button(primaryShell, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		cancel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				primaryGood = false;
				popUpOpen = false;
				primaryShell.dispose();
			}
		});
		cancel.pack();

		
		// done button
		Button done = new Button(primaryShell, SWT.PUSH);
		done.setText("Done");
		done.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		done.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				// all primary items saved when selected
				primaryGood = true;
				popUpOpen = false;
				primaryShell.dispose();
			}
		});
		done.pack();
		
		// open shell
		primaryShell.pack();
		primaryShell.layout();
		CharacterWizard.center(primaryShell);
		primaryShell.open();

		// check if disposed
		while (!primaryShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		return primaryGood;
	}

	private boolean selectSpells() {

		/*
		 * barbarian - no spells, non lawful
		 * bard - cha, arcane(bard spell list), non lawful
		 * cleric - wis, divine(cleric spell list), alignment must match domain, alignment must be within 1 step of deities, st cuthbert only LN or LG, 
		 * 		choose god/domain (choose two from god's domains list, or choose no deity and select any two), 
		 * 		domain adds class skills!
		 * druid - wis, divine(druid spell list), can't use spells that are opposite his/her own alignment, 
		 * 		animal companion 35, must have neutral?
		 * fighter - no spells
		 * monk - lawful, no spells, 
		 * paladin - wis, divine, lawful good!, spells at 4th level, mount(5th level)
		 * ranger - divine, 5th level, favored enemy
		 * rogue - no spells
		 * sorcerer - cha, arcane, familiar
		 * wizard - int, arcane, familiar, school specialization(optional), must choose 2 two schools to give up(not divination), if divination, give up 1
		 * 		spells known = all 0 level (- prohibited schools) + 3 + INT MOD 1st level spells
		 */



		// check if character is a spell caster
		if (!character.getCharClass().isCaster())
			return true;

		// get spells from references
		Collection<DNDEntity> spellsCol =  Main.gameState.spells.values();
		Iterator<DNDEntity> spellItr = spellsCol.iterator();
		ArrayList<SpellEntity> spells = new ArrayList<SpellEntity>();
		while (spellItr.hasNext()) {
			spells.add((SpellEntity) spellItr.next());
		}

		// check if character can select spells
		if (character.getCharClass().getSpellsKnown() == null) {
			if (character.getCharClass().getName().equalsIgnoreCase("Wizard")) {
				// add all 0 level wizard spells that aren't in their prohibited schools
				for (int i = 0; i < spells.size(); i++) {
					if (getLevel(spells.get(i)) == 0){
						if (checkIfProhibited(spells.get(i))) {
							character.addSpell(spells.get(i));
						}
					}
				}
			} else {
				// add that character's spell list to their known spells
				for (int i = 0; i < spells.size(); i++) {
					try { //TODO fix
						if (getLevel(spells.get(i)) != -1) {
							character.addSpell(spells.get(i));
						}
					} catch (Exception e) {
						System.out.println("failed at spell "+spells.get(i).getName());
					}
				}
				return true;
			}
		}

		// initialize layout

		spellShell.setText("Select Known Spells");
		GridLayout gl = new GridLayout(7, true);
		spellShell.setLayout(gl);
		spellShell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				primaryGood = false;
				popUpOpen = false;
			}
		});

		GridData gd;

		numSpellsLeft = new Label(spellShell, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 7;
		numSpellsLeft.setLayoutData(gd);
		
		Label detailsLabel = new Label(spellShell, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 7;
		detailsLabel.setLayoutData(gd);
		
		Label errorLabel = new Label(spellShell, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 7;
		errorLabel.setLayoutData(gd);		

		List spellsList = new List(spellShell, SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 3;
		gd.verticalSpan = 2;
		spellsList.setLayoutData(gd);

		Button addButton = new Button(spellShell, SWT.PUSH);
		gd = new GridData(SWT.CENTER, SWT.END, false, true);
		addButton.setLayoutData(gd);

		charSpellsList =  new List(spellShell, SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 3;
		gd.verticalSpan = 2;
		charSpellsList.setLayoutData(gd);

		Button removeButton = new Button(spellShell, SWT.PUSH);
		gd = new GridData(SWT.CENTER, SWT.BEGINNING, false, true);
		removeButton.setLayoutData(gd);

		Button cancelButton = new Button(spellShell, SWT.PUSH);
		gd = new GridData(SWT.LEFT, SWT.END, true, false);
		gd.horizontalSpan = 3;
		cancelButton.setLayoutData(gd);
		
		// placeholder
		new Label(spellShell, SWT.NONE).setLayoutData(new GridData());
		
		Button doneButton = new Button(spellShell, SWT.PUSH);
		gd = new GridData(SWT.RIGHT, SWT.END, true, false);
		gd.horizontalSpan = 3;
		doneButton.setLayoutData(gd);
		
		
		// create content

		// num spells left label
		int[][] temp = character.getCharClass().getSpellsKnown();
		// get num spells the character can know based on their level
		if (temp == null) {
			
			int level = character.getLevel();
			int[] wizSPD;
			if (level-1 >= character.getCharClass().getSpellsPerDay().length) {
				wizSPD = character.getCharClass().getSpellsPerDay()[character.getCharClass().getSpellsPerDay().length-1];
			} else 
				wizSPD = character.getCharClass().getSpellsPerDay()[character.getLevel() - 1];
			wizHighestLevel = 0;
			for (int i = 0; i < wizSPD.length; i++) {
				if (wizSPD.length >= 0)
					wizHighestLevel = i;
			}
			bonusSpells = 2*(level-1);
			int[] wizSpells = new int[wizSPD.length];
			for (int i = 0; i < wizSpells.length; i++) {
				if (i == 1)
					wizSpells[i] = 3+character.getAbilityModifiers()[GameState.INTELLIGENCE];
				else if (wizSPD[i] == -1)
					wizSpells[i] = -1;
				else
					wizSpells[i] = 0;
			}
			numSpells = wizSpells;
		}
		else if (character.getLevel() - 1 >= temp.length)
			numSpells = temp[temp.length-1];
		else
			numSpells = temp[character.getLevel() - 1];
		// character cannot yet add spells at their level
		if (numSpells[0] == -1) {
			return true;
		}
		
		popUpOpen = true;
		
		origNumSpells = new int[numSpells.length];
		for (int i = 0; i < origNumSpells.length; i++)
			origNumSpells[i] = numSpells[i];
		updateNumSpellsLeft();
		
		// details label
		detailsLabel.setText("Double click on a spell to see details");
		detailsLabel.pack();
		
		// error label - set text when called
		errorLabel.setForeground(new Color(dev, 255, 0, 0));
		errorLabel.setVisible(false);

		// add spells to list
		for (int i = 0; i < spells.size(); i++) {
			int level = getLevel(spells.get(i));
			if (level > -1) {
				// only add spells they can learn
				if (numSpells[level] > 0 && checkIfProhibited(spells.get(i)))
					spellsList.add(spells.get(i).getName() + ": lvl. " + level);
				if (bonusSpells != 0) {
					if (level <= wizHighestLevel && checkIfProhibited(spells.get(i))) {
						spellsList.add(spells.get(i).getName() + ": lvl. " + level);
					}
				}
			}
		}
		spellsList.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e){
				int index = spellsList.getSelectionIndex();
				if (index == -1)
					return;
				String spellName = spellsList.getItem(index).substring(0, spellsList.getItem(index).indexOf(':'));
				Main.gameState.spells.get(spellName).toTooltipWindow();
			}
			@Override
			//leave blank, but must have
			public void widgetSelected(SelectionEvent e) {}
		});
		
		charSpellsList.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e){
				int index = charSpellsList.getSelectionIndex();
				if (index == -1)
					return;
				String spellName = charSpellsList.getItem(index).substring(0, charSpellsList.getItem(index).indexOf(':'));
				Main.gameState.spells.get(spellName).toTooltipWindow();
			}
			@Override
			//leave blank, but must have
			public void widgetSelected(SelectionEvent e) {}
		});

		// create buttons

		// add button
		addButton.setText("Add");
		addButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				numSpellsLeft.setBackground(null);
				errorLabel.setVisible(false);
				int index = spellsList.getSelectionIndex();
				if (index == -1) {
					errorLabel.setText("You must select a spell to add");
					errorLabel.pack();
					spellShell.layout();
					errorLabel.setVisible(true);
					return;
				}

				String spell = spellsList.getItem(index);
				String spellName = spell.substring(0, spell.indexOf(':'));

				// check if already added
				for (int i = 0; i < charSpells.size(); i++) {
					if (charSpells.get(i).getName().equalsIgnoreCase(spellName)) {
						errorLabel.setText("Spell already added");
						errorLabel.pack();
						spellShell.layout();
						errorLabel.setVisible(true);
						return;
					}
				}

				// check level
				int spellLevel = Integer.parseInt(spell.replaceAll("[^\\d]", ""));
				if (numSpells[spellLevel] > 0) {
					charSpells.add((SpellEntity)Main.gameState.spells.get(spellName));
					updateCharSpellsList();
					numSpells[spellLevel]--;
					updateNumSpellsLeft();
				} else if (bonusSpells > 0) {
					charSpells.add((SpellEntity)Main.gameState.spells.get(spellName));
					updateCharSpellsList();
					bonusSpells--;
					updateNumSpellsLeft();
				} else {
					errorLabel.setText("You cannot add a spell of that level");
					errorLabel.pack();
					spellShell.layout();
					errorLabel.setVisible(true);
				}

			}
		});

		// remove button
		removeButton.setText("Remove");
		removeButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				numSpellsLeft.setBackground(null);
				errorLabel.setVisible(false);
				int index = charSpellsList.getSelectionIndex();
				if (index == -1) {
					errorLabel.setText("You must select a spell to remove");
					errorLabel.pack();
					spellShell.layout();
					errorLabel.setVisible(true);
					return;
				}
				String temp = charSpellsList.getItem(index);
				int level = Integer.parseInt(temp.replaceAll("[^\\d]", ""));
				if (numSpells[level] == origNumSpells[level]) {
					bonusSpells++;
				} else
					numSpells[level]++;
				charSpells.remove(index);
				updateCharSpellsList();
				updateNumSpellsLeft();
			}
		});	
		
		// cancel button
		cancelButton.setText("Cancel");
		cancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				spellsGood = false;
				spellShell.dispose();
			}
		});
		
		// done button
		doneButton.setText("Done");
		doneButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				// check if they have any spells left
				for (int i = 0; i < numSpells.length; i++) {
					if (numSpells[i] > 0){
						numSpellsLeft.setBackground(new Color(dev, 255, 100, 100));
						return;
					}
				}
				
				if (popUpOpen)
					return;
				
				// if they have chosen all known spells, save and close
				for (int i = 0; i < charSpells.size(); i++) {
					character.addSpell(charSpells.get(i));
				}
				
				spellsGood = true;
				spellShell.dispose();
			}
		});

		spellShell.layout();
		
		// open shell
				spellShell.pack();
				spellShell.layout();
				CharacterWizard.center(spellShell);
				spellShell.open();

				// check if disposed
				while (!spellShell.isDisposed()) {
					if (!wiz8.getDisplay().readAndDispatch()) {
						wiz8.getDisplay().sleep();
					}
				}

		return spellsGood;
	}

	private void updateCharSpellsList() {
		charSpellsList.removeAll();
		for (int i = 0; i < charSpells.size(); i++){
			charSpellsList.add(charSpells.get(i).getName() + ": lvl. " + getLevel(charSpells.get(i)));
		}
	}

	private void updateNumSpellsLeft() {
		String result = "0 level spells: " + numSpells[0];
		for (int i = 1; i < numSpells.length; i++) {
			if (numSpells[i] >= 0) {
				result += "\n" + i + " level spells: " + numSpells[i];
			}
		}
		if (bonusSpells != 0) {
			result += "\nBonus Spells: " + bonusSpells;
		}
		numSpellsLeft.setText(result);
		numSpellsLeft.pack();
		spellShell.layout();
	}

	private int getLevel(SpellEntity spell) {
		String[] levelArr = spell.getLevel();
		boolean found = false;
		if (levelArr != null) { // take this if out once spells xml is fixed
			for (int j = 0; j < levelArr.length && !found; j++) {
				if (levelArr[j].contains(character.getCharClass().getName())) {
					return Integer.parseInt(levelArr[j].replaceAll("[^\\d]", ""));
				}
			}
		}
		return -1;
	}
	
	/**
	 * returns true if spell is allowed(not prohibited) and false if prohibited
	 * @param spell
	 * @return
	 */
	private boolean checkIfProhibited(SpellEntity spell) {
		if (character.getWizardProhibitedSchools() == null)
			return true;
		for (int k = 0; k < character.getWizardProhibitedSchools().length; k++) {
			if (spell.getSchool().toLowerCase().contains(character.getWizardProhibitedSchools()[k].toLowerCase())) {
				return false;
			}
		}
		return true;
	}
	
	private void updateCharWeaponsList() {
		charWeaponsList.removeAll();
		for (int i = 0; i < charWeapons.size(); i++){
			CharItem curr = charWeapons.get(i);
			charWeaponsList.add(curr.getCount() + " x " + curr.getItem().getName());
		}
	}
	
	private void updateCharArmorList() {
		charArmorList.removeAll();
		for (int i = 0; i < charArmor.size(); i++){
			CharItem curr = charArmor.get(i);
			charArmorList.add(curr.getCount() + " x " + curr.getItem().getName());
		}
	}
	
	private void updateCharShieldsList() {
		charShieldsList.removeAll();
		for (int i = 0; i < charShields.size(); i++){
			CharItem curr = charShields.get(i);
			charShieldsList.add(curr.getCount() + " x " + curr.getItem().getName());
		}
	}

	private void createNextPage() {
		cw.wizPageCreated[8] = true;
		cw.wizs.add(new Wiz9(cw, dev, WIDTH, HEIGHT, panel, home,
				homePanel, layout, homeLayout, wizPages));
	}

	public Composite getWiz8() { return wiz8; }
}