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
 * TODO save armor/shield bonus to ac
 * TODO here: add specific weapon proficiencies to xml
 */

package guis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;

import entity.*;
import core.CharItem;
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
	
	private List charWeaponsList;
	private List charArmorList;
	private List charShieldsList;
	private List weaponsList;
	private List armorList;
	private List shieldsList;
	
	private ArrayList<CharItem> charWeapons = new ArrayList<CharItem>();
	private ArrayList<CharItem> charArmor = new ArrayList<CharItem>();
	private ArrayList<CharItem> charShields = new ArrayList<CharItem>();


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

		createPageContent();
	}

	private void createPageContent() {
		Label wiz8Label = new Label(wiz8, SWT.NONE);
		wiz8Label.setText("Choose Weapons and Armor");
		wiz8Label.pack();
		
		GridLayout gl = new GridLayout(6, true);
		
		Composite inner = new Composite(wiz8, SWT.NONE);
		inner.setBounds(5, 20, WIDTH-10, HEIGHT-110);
		inner.setLayout(gl);
		
		GridData gd;
		
		
		// initialize layout
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
		gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 2;
		charWeaponsList.setLayoutData(gd);
		charWeaponsList.pack();
		
		charArmorList = new List(inner, SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 2;
		charArmorList.setLayoutData(gd);
		charArmorList.pack();
		
		charShieldsList = new List(inner, SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 2;
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
		
		
		// add/remove button listeners
		
		 addWeapon.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) { 
				int index = weaponsList.getSelectionIndex();
				if (index == -1)
					return;
				
			}
		 });
		 removeWeapon.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) { 
				int index = charWeaponsList.getSelectionIndex();
				if (index == -1)
					return;
				
			}
		 });
		 addArmor.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) { 
				int index = armorList.getSelectionIndex();
				if (index == -1)
					return;
				
			}
		 });
		 removeArmor.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) { 
				int index = charArmorList.getSelectionIndex();
				if (index == -1)
					return;
				
			}
		 });
		 addShield.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) { 
				int index = shieldsList.getSelectionIndex();
				if (index == -1)
					return;
				
			}
		 });
		 removeShield.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) { 
				int index = charShieldsList.getSelectionIndex();
				if (index == -1)
					return;
				
			}
		 });
		
		
		
		
		
		
		
		// next button
		Button wiz8NextButton = cw.createNextButton(wiz8);
		wiz8NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
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

	private void createNextPage() {
		cw.wizPageCreated[8] = true;
		cw.wizs.add(new Wiz9(cw, dev, WIDTH, HEIGHT, panel, home,
				homePanel, layout, homeLayout, wizPages));
	}

	public Composite getWiz8() { return wiz8; }
}