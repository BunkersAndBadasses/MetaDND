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

/*
 * TODO sort spells by level rather than alphabetically?
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
	
	private Composite inner;
	
	private List charSpellsList;
	private Label numSpellsLeft;
	private int[] numSpells;
	
	private ArrayList<SpellEntity> charSpells = new ArrayList<SpellEntity>();
	
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
		
		inner = new Composite(wiz9, SWT.NONE);
		inner.setBounds(5, 20, WIDTH-10, HEIGHT-110);
		inner.setLayout(gl);
		
		GridData gd;
		
		numSpellsLeft = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
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
		
		charSpellsList =  new List(inner, SWT.V_SCROLL);
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
		// TODO not working
		if (character.getCharClass().getSpellsKnown() == null) {
//			if (cw.wizPageNum < wizPagesSize - 1)
//				cw.wizPageNum++;
//			if (!cw.wizPageCreated[9])
//				createNextPage();
//			layout.topControl = nextPage;
//			panel.layout();
//			return;
//		
		} else {
			
			int[][] temp = character.getCharClass().getSpellsKnown();
			if (character.getLevel() >= temp.length)
				numSpells = temp[temp.length-1];
			else
				numSpells = temp[character.getLevel()-1];
			if (numSpells[0] == -1);
				//TODO - character cannot add spells yet: switch page!
			updateNumSpellsLeft();
			
			// get spells from references
			Collection<DNDEntity> spellsCol =  Main.gameState.spells.values();
			Iterator<DNDEntity> spellItr = spellsCol.iterator();
			ArrayList<SpellEntity> spells = new ArrayList<SpellEntity>();
			while (spellItr.hasNext()) {
				spells.add((SpellEntity) spellItr.next());
			}
			
			// add spells to list
			for (int i = 0; i < spells.size(); i++) {
				int level = getLevel(spells.get(i));
				if (level > -1) {
					// only add spells they can learn
					if (numSpells[level] >= 0)
						spellsList.add(spells.get(i).getName() + ": lvl. " + level);
				}
			}
			
			// create buttons
			
			// add button
			addButton.setText("Add");
			addButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					int index = spellsList.getSelectionIndex();
					if (index == -1)
						return;

					String temp = spellsList.getItem(index);
					String spellName = temp.substring(0, temp.indexOf(':'));
					
					// check if already added
					for (int i = 0; i < charSpells.size(); i++) {
						if (charSpells.get(i).getName().equalsIgnoreCase(spellName)) {
							// TODO add error message
							return;
						}
					}
					
					
					// check level
					int spellLevel = Integer.parseInt(temp.replaceAll("[^\\d]", ""));
					if (numSpells[spellLevel] > 0) {
						charSpells.add((SpellEntity)Main.gameState.spells.get(spellName));
						updateCharSpellsList();
						numSpells[spellLevel]--;
						updateNumSpellsLeft();
					} else {
						// TODO error message
					}
						
				}
			});
			
			// remove button
			removeButton.setText("Remove");
			removeButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					int index = charSpellsList.getSelectionIndex();
					if (index == -1)
						return;
					String temp = charSpellsList.getItem(index);
					int level = Integer.parseInt(temp.replaceAll("[^\\d]", ""));
					numSpells[level]++;
					charSpells.remove(index);
					updateCharSpellsList();
					updateNumSpellsLeft();
				}
			});			
		}
		
		inner.layout();
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
		numSpellsLeft.setText(result);
		numSpellsLeft.pack();
		inner.layout();
	}
	
	private int getLevel(SpellEntity spell) {
		String[] levelArr = spell.getLevel();
		boolean found = false;
		if (levelArr != null) { // TODO take this if out once spells xml is fixed
			for (int j = 0; j < levelArr.length && !found; j++) {
				if (levelArr[j].contains(character.getCharClass().getName())) {
					return Integer.parseInt(levelArr[j].replaceAll("[^\\d]", ""));
				}
			}
		}
		return -1;
	}
	
	private void createNextPage() {
		cw.wizPageCreated[9] = true;
		cw.wizs.add(new Wiz10(cw, dev, WIDTH, HEIGHT, panel, home,
				homePanel, layout, homeLayout, wizPages));
	}
	
	public Composite getWiz9() { return wiz9; }
	
	class Spell {
		
		private SpellEntity spell;
		private int level;

		public Spell(SpellEntity spell, int level) {
			this.setSpell(spell);
			this.level = level;
		}

		public SpellEntity getSpell() {
			return spell;
		}

		public void setSpell(SpellEntity spell) {
			this.spell = spell;
		}
		
		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}
		
	}
}

