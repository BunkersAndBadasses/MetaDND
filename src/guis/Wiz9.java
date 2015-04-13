/*
 * CHOOSE SPELLS
 */

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

public class Wiz9{

	private Composite wiz9;
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

	public Wiz9(Device dev, int WIDTH, int HEIGHT,
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz9 = wizPages.get(8);
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
		this.nextPage = wizPages.get(9);
		this.wizPagesSize = wizPages.size();

		createPageContent();
	}
	
	private void createPageContent() {
		Label wiz9Label = new Label(wiz9, SWT.NONE);
		wiz9Label.setText("Select Known Spells");
		wiz9Label.pack();

		Button wiz9NextButton = CharacterWizard.createNextButton(wiz9);
		wiz9NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				
				if (CharacterWizard.wizPageNum < wizPagesSize - 1)
					CharacterWizard.wizPageNum++;
				if (!CharacterWizard.wizPageCreated[9])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});

		//Button wiz9BackButton = CharacterWizard.createBackButton(wiz9, panel, layout);
		Button wiz9CancelButton = CharacterWizard.createCancelButton(wiz9, home, homePanel, homeLayout);
		wiz9CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.cancel)
					CharacterWizard.reset();
			}
		});
	}
	
	private void createNextPage() {
		CharacterWizard.wizPageCreated[9] = true;
		CharacterWizard.wizs.add(new Wiz10(dev, WIDTH, HEIGHT, panel, home,
				homePanel, layout, homeLayout, wizPages));
	}
	
	public Composite getWiz9() { return wiz9; }
}