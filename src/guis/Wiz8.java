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


package guis;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import entity.*;
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

		//Button wiz8BackButton = cw.createBackButton(wiz8, panel, layout);
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