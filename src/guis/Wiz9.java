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
import core.Main;
import core.character;

public class Wiz9{

	private Composite wiz9;
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

	private boolean skip = false;
	
	public Wiz9(CharacterWizard cw, Device dev, int WIDTH, int HEIGHT,
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz9 = wizPages.get(8);
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
		this.nextPage = wizPages.get(9);
		this.wizPagesSize = wizPages.size();

		createPageContent();
	}
	
	private void createPageContent() {
		Label wiz9Label = new Label(wiz9, SWT.NONE);
		wiz9Label.setText("Select Known Spells");
		wiz9Label.pack();
		
		
		// initialize layout
		
		GridLayout gl = new GridLayout(7, true);
		
		Composite inner = new Composite(wiz9, SWT.NONE);
		inner.setBounds(5, 20, WIDTH-10, HEIGHT-110);
		inner.setLayout(gl);
		
		GridData gd;
		
		Label numSpellsLeft = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 7;
		numSpellsLeft.setLayoutData(gd);
		
		List spellsList = new List(inner, SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 3;
		gd.verticalSpan = 2;
		spellsList.setLayoutData(gd);
		
		Button addButton = new Button(inner, SWT.PUSH);
		gd = new GridData(SWT.CENTER, SWT.END, false, true);
		addButton.setLayoutData(gd);
		
		List charSpellsList =  new List(inner, SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 3;
		gd.verticalSpan = 2;
		charSpellsList.setLayoutData(gd);
		
		Button removeButton = new Button(inner, SWT.PUSH);
		gd = new GridData(SWT.CENTER, SWT.BEGINNING, false, true);
		removeButton.setLayoutData(gd);
		
		Button wiz9NextButton = cw.createNextButton(wiz9);
		wiz9NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// save known spells
				// if wizard - save entire list of spells? or not?
				
				if (cw.wizPageNum < wizPagesSize - 1)
					cw.wizPageNum++;
				if (!cw.wizPageCreated[9])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});
		wiz9NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (skip) {
					if (cw.wizPageNum < wizPagesSize - 1)
						cw.wizPageNum++;
					if (!cw.wizPageCreated[9])
						createNextPage();
					layout.topControl = nextPage;
					panel.layout();
				}
			}
		});
		
		
		//Button wiz9BackButton = cw.createBackButton(wiz9, panel, layout);
		Button wiz9CancelButton = cw.createCancelButton(wiz9, home, homePanel, homeLayout);
		wiz9CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (cw.cancel)
					cw.reset();
			}
		});
		
		
		// create content
		
		// if character does not have known spells (non-casters, wizard), skip to next page
		if (character.getCharClass().getSpellsKnown() == null) {
			skip = true;
			wiz9NextButton.setSelection(true);
			return;
		}
		else {
			
			int[][] temp = character.getCharClass().getSpellsKnown();
			int[] numSpells;
			if (character.getLevel() >= temp.length)
				numSpells = temp[temp.length-1];
			else
				numSpells = temp[character.getLevel()-1];
			
			// get spells from references
			Collection<DNDEntity> spellsCol =  Main.gameState.spells.values();
			Iterator<DNDEntity> spellItr = spellsCol.iterator();
			ArrayList<SpellEntity> spells = new ArrayList<SpellEntity>();
			while (spellItr.hasNext()) {
				spells.add((SpellEntity) spellItr.next());
			}
			
			
			
		}
		
		inner.layout();
	}
	
	private void createNextPage() {
		cw.wizPageCreated[9] = true;
		cw.wizs.add(new Wiz10(cw, dev, WIDTH, HEIGHT, panel, home,
				homePanel, layout, homeLayout, wizPages));
	}
	
	public Composite getWiz9() { return wiz9; }
}