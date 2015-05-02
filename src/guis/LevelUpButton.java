package guis;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import core.CharSkill;
import core.GameState;
import core.RNG;
import core.character;

public class LevelUpButton {
	
	private Display display;
	private Composite page;
	private Button button;
	private character character;
	private boolean cancelOpen;
	private Shell levelUpShell;
	private Shell areYouSureShell;
	private Shell curr;
	
	// stuff to save when done
	private int saveHP;
	private int saveAS; // 0-5, index of ability score to increase
	
	public LevelUpButton(Composite page, character character) {
		this.page = page;
		this.display = this.page.getDisplay();
		this.character = character;
		button = new Button(page, SWT.PUSH);
		button.setText("Level Up!");
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				checkLevelUp();
			}
		});
	}
	
	private void checkLevelUp() {
		int level = character.getLevel();
		int exp = character.getExp();
		int reqExp = getReqExp(level+1);
		if (exp < reqExp) {
			// if exp is not enough, do not level up
			notEnoughExpWindow(exp, reqExp, level);
			return;
		} else {
			// otherwise, perform the level up	
			levelUp();
		}
	}
	
	private void levelUp() {
//		levelUpShell = new Shell(display);
//		StackLayout stackLayout = new StackLayout();
//		levelUpShell.setLayout(stackLayout);
		
		int newLevel = character.getLevel() + 1;
		
		////////// PAGE NUMBERS //////////
		final int HP = 0;
		final int SKILL = 1;
		final int FEAT = 2;
		final int AS = 3;
		final int SPELL = 4;
		final int DONE = 5;
		
		//////////////////// INITIALIZE PAGES ////////////////////
		//ArrayList<Composite> pages = new ArrayList<Composite>();
		ArrayList<Shell> pages = new ArrayList<Shell>();
		
		//Composite hpPage = new Composite(levelUpShell, SWT.NONE);
		Shell hpPage = new Shell(display);
		pages.add(hpPage);
		//Composite skillsPage = new Composite(levelUpShell, SWT.NONE);
		Shell skillsPage = new Shell(display);
		pages.add(skillsPage);
		//Composite featsPage = new Composite(levelUpShell, SWT.NONE);
		Shell featsPage = new Shell(display);
		pages.add(featsPage);
		//Composite abilityScoresPage = new Composite(levelUpShell, SWT.NONE);
		Shell abilityScoresPage = new Shell(display);
		pages.add(abilityScoresPage);
		//Composite spellsPage = new Composite(levelUpShell, SWT.NONE);
		Shell spellsPage = new Shell(display);
		pages.add(spellsPage);
		
		//Composite donePage = new Composite(levelUpShell, SWT.NONE);
		Shell donePage = new Shell(display);
		pages.add(donePage);
				
		GridLayout gl;
		GridData gd;
		
		//////////////////// HIT POINTS PAGE ////////////////////
		gl = new GridLayout(4, true);
		hpPage.setLayout(gl);
		
		String hitDie = character.getCharClass().getHitDie();
		int hitDieValue = Integer.parseInt(hitDie.replaceAll("[^\\d]", ""));
		int conMod = character.getAbilityModifiers()[GameState.CONSTITUTION];
		
		Label hpLabel = new Label(hpPage, SWT.NONE);
		hpLabel.setText("Roll a " + hitDie +" for additional hit points.");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 4;
		hpLabel.setLayoutData(gd);
		
		new Label(hpPage, SWT.NONE).setLayoutData(new GridData());
		
		Text hpInput = new Text(hpPage, SWT.BORDER | SWT.CENTER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, true);
		hpInput.setLayoutData(gd);
		
		Button roll = new Button(hpPage, SWT.PUSH);
		roll.setText("Roll");
		gd = new GridData(SWT.FILL, SWT.CENTER, true, true);
		roll.setLayoutData(gd);
		roll.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				hpInput.setText(Integer.toString(new RNG().GetRandomInteger(1, hitDieValue)));
			}
		});
		
		String conString;
		if (conMod < 0)
			conString = "" + conMod;
		else
			conString = "+" + conMod;
		
		new Label(hpPage, SWT.NONE).setLayoutData(new GridData());
		
		Label totalLabel = new Label(hpPage, SWT.NONE);
		totalLabel.setText(conString + " (CON modifier) = +X");
		gd = new GridData(SWT.CENTER, SWT.BEGINNING, true, true);
		gd.horizontalSpan = 4;
		totalLabel.setLayoutData(gd);
		
		hpInput.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				hpInput.setBackground(null);
				int value = 0;
				String input = hpInput.getText();
				if (input.length() == 0) {
					totalLabel.setText(conString + " (CON modifier) = +X");
					return;
				}
				try {
					value = Integer.parseInt(input);
					if (value < 1 || value > hitDieValue)
						throw new Exception();
				} catch (Exception e) {
					hpInput.setBackground(new Color(display, 255, 100, 100));
					totalLabel.setText(conString + " (CON modifier) = +X");
					return;
				}
				int total = conMod + value;
				if (total < 1)
					total = 1;
				totalLabel.setText(conString + " (CON modifier) = +" + total);
			}
		});
		
		cancelButton(hpPage);
		
		new Label(hpPage, SWT.NONE).setLayoutData(new GridData());
		new Label(hpPage, SWT.NONE).setLayoutData(new GridData());
		
		Button hpNext = nextButton(hpPage);
		hpNext.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				hpInput.setBackground(null);
				int value = 0;
				String input = hpInput.getText();
				try {
					value = Integer.parseInt(input);
					if (value < 1 || value > hitDieValue)
						throw new Exception();
				} catch (Exception e) {
					hpInput.setBackground(new Color(display, 255, 100, 100));
					totalLabel.setText(conString + " (CON modifier) = +X");
					return;
				}
//				stackLayout.topControl = pages.get(SKILL);
//				levelUpShell.layout();
				openNextPage(pages.get(SKILL));
				
				int total = conMod + value;
				if (total < 1)
					total = 1;
				saveHP = total;
			}
		});
		hpPage.layout();
		hpPage.pack();

		
		//////////////////// SKILLS PAGE ////////////////////
		gl = new GridLayout(8, true);
		skillsPage.setLayout(gl);
		
		Label skillPointsLabel = new Label(skillsPage, SWT.NONE);
		int numSkillPoints = Integer.parseInt(Character.toString(character.getCharClass().getSkillPointsPerLevel().charAt(0))) + character.getAbilityModifiers()[GameState.INTELLIGENCE];
		//int numSkillPoints = 5;
		// TODO check if <= 0
		skillPointsLabel.setText("Skill Points Remaining: " + numSkillPoints);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 8;
		skillPointsLabel.setLayoutData(gd);
		
//		Composite titleRow = new Composite(skillsPage, SWT.BORDER);
//		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
//		gd.horizontalSpan = 2;
//		titleRow.setLayoutData(gd);
//		titleRow.setLayout(new GridLayout(8, true));
		String[] titles = {"+", Character.toString(((char) 8211)), "Skill (Type)", "Ability Mod", "Misc Mod", "Rank", "Total"};
		
		for (int i = 0; i < titles.length; i++) {
			Label label = new Label(skillsPage, SWT.BORDER | SWT.CENTER);
			gd = new GridData(SWT.FILL, SWT.FILL, true, false);
			if (i == 2)
				gd.horizontalSpan = 2;
			label.setLayoutData(gd);
			label.setText(titles[i]);				
		}
		
		ScrolledComposite skillsScroll = new ScrolledComposite(skillsPage, SWT.BORDER | SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 8;
		skillsScroll.setLayoutData(gd);
		skillsScroll.setExpandHorizontal(true);
		skillsScroll.setExpandVertical(true);
		Composite skillsInner = new Composite(skillsScroll, SWT.NONE);
		skillsScroll.setContent(skillsInner);
		GridLayout skillsInnerLayout = new GridLayout(1, true);
		skillsInner.setLayout(skillsInnerLayout);
		
		ArrayList<CharSkill> charSkills = character.getSkills();
		ArrayList<Composite> rows = new ArrayList<Composite>();
		//rows.add(titleRow);
		
		for (int i = 0; i < charSkills.size(); i++) {
			// create composite
			Composite currRow = new Composite(skillsInner, SWT.BORDER);
			rows.add(currRow);
			gd = new GridData(SWT.FILL, SWT.FILL, true, false);
			currRow.setLayoutData(gd);
			currRow.setLayout(new GridLayout(8, true));
			// + button
			Button inc = new Button(currRow, SWT.PUSH);
			inc.setText("+");
			gd = new GridData(SWT.FILL, SWT.FILL, true, false);
			inc.setLayoutData(gd);
			// - button
			Button dec = new Button(currRow, SWT.PUSH);
			dec.setText(Character.toString(((char) 8211)));
			gd = new GridData(SWT.FILL, SWT.FILL, true, false);
			dec.setLayoutData(gd);
			// skill name (type)
			Label skill = new Label(currRow, SWT.NONE);
			skill.setText(charSkills.get(i).getSkill().getName() + " (" + charSkills.get(i).getAbilityType() + ")");
			gd = new GridData(SWT.FILL, SWT.FILL, true, false);
			gd.horizontalSpan = 2;
			skill.setLayoutData(gd);
			// ability mod
			Label abilMod = new Label(currRow, SWT.CENTER);
			abilMod.setText(Integer.toString(charSkills.get(i).getAbilityMod()));
			gd = new GridData(SWT.FILL, SWT.FILL, true, false);
			abilMod.setLayoutData(gd);
			// misc mod
			Label miscMod = new Label(currRow, SWT.CENTER);
			miscMod.setText(Integer.toString(charSkills.get(i).getMiscMod()));
			gd = new GridData(SWT.FILL, SWT.FILL, true, false);
			miscMod.setLayoutData(gd);
			// rank
			Label rank = new Label(currRow, SWT.CENTER);
			rank.setText(Integer.toString(charSkills.get(i).getRank()));
			gd = new GridData(SWT.FILL, SWT.FILL, true, false);
			rank.setLayoutData(gd);
			// total
			Label total = new Label(currRow, SWT.CENTER);
			total.setText(Integer.toString(charSkills.get(i).getTotal()));
			gd = new GridData(SWT.FILL, SWT.FILL, true, false);
			total.setLayoutData(gd);
			
			currRow.layout();
			currRow.pack();
		}
		skillsInner.layout();
		skillsInner.pack();
		Composite last = rows.get(rows.size()-1);
		skillsScroll.setMinHeight(last.getLocation().y + last.getSize().y);
		//skillsScroll.setMinHeight(5000);
		
		cancelButton(skillsPage);
		
		new Label(skillsPage, SWT.NONE).setLayoutData(new GridData());
		new Label(skillsPage, SWT.NONE).setLayoutData(new GridData());
		new Label(skillsPage, SWT.NONE).setLayoutData(new GridData());
		new Label(skillsPage, SWT.NONE).setLayoutData(new GridData());
		new Label(skillsPage, SWT.NONE).setLayoutData(new GridData());
		new Label(skillsPage, SWT.NONE).setLayoutData(new GridData());
		
		Button skillsNext = nextButton(skillsPage);
		skillsNext.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				Shell nextPage;
				if ((newLevel % 3) == 0) {
					nextPage = pages.get(FEAT);
				} else if ((newLevel % 4) == 0) {
					nextPage = pages.get(AS);
				} // TODO any other pages
				else {
					nextPage = pages.get(DONE);
				}
//				stackLayout.topControl = nextPage;
//				levelUpShell.layout();
				openNextPage(nextPage);
			}
		});
		
		skillsPage.layout();
		//skillsPage.pack();
		
		//////////////////// FEATS PAGE ////////////////////
		gl = new GridLayout(2, true);
		featsPage.setLayout(gl);
		
		Label featsLabel = new Label(featsPage, SWT.NONE);
		featsLabel.setText("Add Feats");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		featsLabel.setLayoutData(gd);
		
		cancelButton(featsPage);
		
		Button featsNext = nextButton(featsPage);
		featsNext.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				Shell nextPage;
				if ((newLevel % 4) == 0) {
					nextPage = pages.get(AS);
				} // TODO any other pages
				else {
					nextPage = pages.get(DONE);
				}
//				stackLayout.topControl = nextPage;
//				levelUpShell.layout();
				openNextPage(nextPage);
			}
		});
		
		featsPage.layout();
		featsPage.pack();
		
		//////////////////// ABILITY SCORES PAGE ////////////////////
		gl = new GridLayout(4, true);
		abilityScoresPage.setLayout(gl);
		
		Label asLabel = new Label(abilityScoresPage, SWT.NONE);
		asLabel.setText("Increase Ability Score by 1");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 4;
		asLabel.setLayoutData(gd);
		
		new Label(abilityScoresPage, SWT.NONE).setLayoutData(new GridData());
		
		Button strength = new Button(abilityScoresPage, SWT.RADIO);
		strength.setText("Strength: " + character.getAbilityScores()[GameState.STRENGTH]);
		gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		strength.setLayoutData(gd);
		
		new Label(abilityScoresPage, SWT.NONE).setLayoutData(new GridData());
		new Label(abilityScoresPage, SWT.NONE).setLayoutData(new GridData());

		Button dexterity = new Button(abilityScoresPage, SWT.RADIO);
		dexterity.setText("Dexterity: " + character.getAbilityScores()[GameState.DEXTERITY]);
		gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		dexterity.setLayoutData(gd);
		
		new Label(abilityScoresPage, SWT.NONE).setLayoutData(new GridData());
		new Label(abilityScoresPage, SWT.NONE).setLayoutData(new GridData());

		Button constitution = new Button(abilityScoresPage, SWT.RADIO);
		constitution.setText("Constitution: " + character.getAbilityScores()[GameState.CONSTITUTION]);
		gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		constitution.setLayoutData(gd);
		
		new Label(abilityScoresPage, SWT.NONE).setLayoutData(new GridData());
		new Label(abilityScoresPage, SWT.NONE).setLayoutData(new GridData());

		Button intelligence = new Button(abilityScoresPage, SWT.RADIO);
		intelligence.setText("Intelligence: " + character.getAbilityScores()[GameState.INTELLIGENCE]);
		gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		intelligence.setLayoutData(gd);
		
		new Label(abilityScoresPage, SWT.NONE).setLayoutData(new GridData());
		new Label(abilityScoresPage, SWT.NONE).setLayoutData(new GridData());

		Button wisdom = new Button(abilityScoresPage, SWT.RADIO);
		wisdom.setText("Wisdom: " + character.getAbilityScores()[GameState.WISDOM]);
		gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		wisdom.setLayoutData(gd);
		
		new Label(abilityScoresPage, SWT.NONE).setLayoutData(new GridData());
		new Label(abilityScoresPage, SWT.NONE).setLayoutData(new GridData());

		Button charisma = new Button(abilityScoresPage, SWT.RADIO);
		charisma.setText("Charisma: " + character.getAbilityScores()[GameState.CHARISMA]);
		gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		charisma.setLayoutData(gd);
		
		new Label(abilityScoresPage, SWT.NONE).setLayoutData(new GridData());

		cancelButton(abilityScoresPage);
		
		new Label(abilityScoresPage, SWT.NONE).setLayoutData(new GridData());
		new Label(abilityScoresPage, SWT.NONE).setLayoutData(new GridData());
		
		Button asNext = nextButton(abilityScoresPage);
		asNext.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				if (strength.getSelection())
					saveAS = GameState.STRENGTH;
				else if (dexterity.getSelection())
					saveAS = GameState.DEXTERITY;
				else if (constitution.getSelection())
					saveAS = GameState.CONSTITUTION;
				else if (intelligence.getSelection())
					saveAS = GameState.INTELLIGENCE;
				else if (wisdom.getSelection())
					saveAS = GameState.WISDOM;
				else if (charisma.getSelection())
					saveAS = GameState.CHARISMA;
				else
					// nothing is selected
					return;
				Shell nextPage;
//				if () {
//				// TODO any other pages
//				}else {
					nextPage = pages.get(DONE);
//				stackLayout.topControl = nextPage;
//				levelUpShell.layout();
				openNextPage(nextPage);
			}
		});
		
		abilityScoresPage.layout();
		abilityScoresPage.pack();

		//////////////////// SPELLS PAGES ////////////////////
		gl = new GridLayout(2, true);
		spellsPage.setLayout(gl);
		
		cancelButton(spellsPage);
		
		Button spellsNext = nextButton(spellsPage);
		spellsNext.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				Shell nextPage;
//				if () {
//				// TODO any other pages
//				} 
//				else {
					nextPage = pages.get(DONE);
//				stackLayout.topControl = nextPage;
//				levelUpShell.layout();
					openNextPage(nextPage);
			}
		});
		
		spellsPage.layout();
		spellsPage.pack();
		
		//////////////////// DONE PAGE ////////////////////
		gl = new GridLayout(2, true);
		donePage.setLayout(gl);
		
		Label doneLabel = new Label(donePage, SWT.NONE);
		doneLabel.setText("You have successfully leveled up!");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 2;
		doneLabel.setLayoutData(gd);
		
		Label saveLabel = new Label(donePage, SWT.NONE);
		saveLabel.setText("Click 'Save' to save all changes.");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 2;
		saveLabel.setLayoutData(gd);
		
		cancelButton(donePage);
		
		Button saveButton = new Button(donePage, SWT.PUSH);
		saveButton.setText("Save");
		gd = new GridData(SWT.RIGHT, SWT.END, true, true);
		saveButton.setLayoutData(gd);
		saveButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				// TODO save anything else?
				character.incLevel();
				//levelUpShell.dispose();
				curr.dispose();
			}
		});
		donePage.layout();
		donePage.pack();
		
		/*
		 * TODO do all the other shit too
		 * 
		 * general:
		 * base attack bonus
		 * base save bonuses
		 * ability score (level % 4, one point)
		 * hit points (hit die + level*inc con mod)
		 * skill points (level + 3)
		 * feats (level % 3)
		 * spells
		 * class features
		 * 
		*/
		
		// wizard: bonus feat every 5th level
		// 		+2 spells of any level they can cast
		
		// open shell
//		stackLayout.topControl = pages.get(HP);
//		levelUpShell.layout();
//		levelUpShell.pack();
//		CharacterWizard.center(levelUpShell);
//		levelUpShell.open();
		
		curr = pages.get(HP);
		CharacterWizard.center(curr);
		curr.layout();
		curr.pack();
		curr.open();
		

		// check if disposed
		while (!curr.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	private void notEnoughExpWindow(int exp, int reqExp, int level) {
		Shell shell = new Shell(display);
		GridLayout layout = new GridLayout(1, true);
		shell.setLayout(layout);
		
		GridData gd;
		
		Label notEnough = new Label(shell, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		notEnough.setLayoutData(gd);
		notEnough.setText("You do not have enough experience points to level up!");
		
		Label youNeed = new Label(shell, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		youNeed.setLayoutData(gd);
		youNeed.setText("You need " + reqExp + " exp to become level " + (level+1) + ".");
		
		Label youHave = new Label(shell, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		youHave.setLayoutData(gd);
		youHave.setText("You only have " + exp + " exp.");
		
		Button okay = new Button(shell, SWT.PUSH);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		okay.setLayoutData(gd);
		okay.setText("Okay");
		okay.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				shell.dispose();
			}
		});
		
		// open shell
		shell.pack();
		shell.layout();
		CharacterWizard.center(shell);
		shell.open();

		// check if disposed
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	private Button cancelButton(Composite c) {
		Button button = new Button(c, SWT.PUSH);
		button.setText("Cancel");
		GridData gd = new GridData(SWT.LEFT, SWT.END, true, false);
		button.setLayoutData(gd);
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				if (cancelOpen) {
					areYouSureShell.forceActive();
					return;
				}
				cancelOpen = true;
				
				// create shell
				areYouSureShell = new Shell(display);
				areYouSureShell.addListener(SWT.Close, new Listener() {
			        public void handleEvent(Event event) {
			            cancelOpen = false;
			        }
			    });
				areYouSureShell.setText("Cancel");
				GridLayout gridLayout = new GridLayout(2, true);
				areYouSureShell.setLayout(gridLayout);

				// label - Are you sure? 
				GridData gd1 = new GridData(SWT.CENTER, SWT.CENTER, true, true);
				gd1.horizontalSpan = 2;
				Label areYouSure = new Label(areYouSureShell, SWT.WRAP);
				areYouSure.setText("Are you sure you want to cancel?");
				areYouSure.setLayoutData(gd1);
				areYouSure.pack();
				
				// no button
				Button no = new Button(areYouSureShell, SWT.PUSH);
				no.setText("No, Don't Cancel");
				no.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
				no.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						cancelOpen = false;
						areYouSureShell.dispose();
					}
				});
				no.pack();
				
				// yes button
				Button yes = new Button(areYouSureShell, SWT.PUSH);
				yes.setText("Yes, Cancel");
				yes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
				yes.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						cancelOpen = false;
						areYouSureShell.dispose();
						//levelUpShell.dispose();
						curr.dispose();
					}
				});
				yes.pack();

				// open shell
				areYouSureShell.pack();
				CharacterWizard.center(areYouSureShell);
				areYouSureShell.open();
				
				// check if disposed
				while (!areYouSureShell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		});
		return button;
	}
	
	private Button nextButton(Composite c) {
		Button button = new Button(c, SWT.PUSH);
		button.setText("Next");
		GridData gd = new GridData(SWT.RIGHT, SWT.END, true, false);
		button.setLayoutData(gd);
		return button;
	}
	
	private void openNextPage(Shell next) {
		curr.dispose();
		curr = next;
		curr.layout();
		curr.pack();
		CharacterWizard.center(curr);
		curr.open();
	}
	
	/**
	 * returns value of exp necessary to be at specified level
	 * @param level
	 * @return
	 */
	private int getReqExp(int level) {
		if (level < 2)
			return 0;
		else
			return (level-1)*1000 + getReqExp(level-1);
	}
	
	public Button getButton() { return button; }
	
}
