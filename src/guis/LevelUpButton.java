package guis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
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
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import core.CharFeat;
import core.CharSkill;
import core.GameState;
import core.Main;
import core.RNG;
import core.SaveCharacter;
import core.SkillAdjNode;
import core.character;
import entity.AbilityEntity;
import entity.DNDEntity;
import entity.FeatEntity;
import entity.SpellEntity;

public class LevelUpButton {

	private Button button;

	public LevelUpButton(Composite page, character character) {
		button = new Button(page, SWT.PUSH);
		button.setText("Level Up!");
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				new LevelUpLogic(button, character, page.getDisplay());
			}
		});
	}

	public Button getButton() { return button; }

}

class LevelUpLogic {
	private Display display;
	private character character;
	private boolean cancelOpen;
	//	private Shell levelUpShell;
	private Shell areYouSureShell;
	private Shell curr;

	private int numSkillPoints;
	private boolean skipSkills = false;
	private boolean skipFeats = true;
	private boolean skipFighter = true;
	private boolean skipSpells = false;

	private boolean specialOpen;
	private boolean specialValid;
	private Shell featSpecialShell;
	
	private List charFeatsList;
	private int numFeats = 0;
	private int numCharFeats;
	
	private int[] numSpells;
	private List charSpellsList;
	private Label numSpellsLeft;
	private int bonusSpells;
	private int wizHighestLevel;
	int[] origNumSpells = null;

	
	private ArrayList<FeatEntity> feats;

	// stuff to save when done
	private int saveHP = 0;
	private int saveAS = -1; // 0-5, index of ability score to increase
	private ArrayList<String> saveSpecialAbilities = new ArrayList<String>();
	private ArrayList<SkillAdjNode> saveSkills = new ArrayList<SkillAdjNode>();
	private ArrayList<CharFeat> saveFeats = new ArrayList<CharFeat>();
	private ArrayList<SpellEntity> saveSpells = new ArrayList<SpellEntity>();

	public LevelUpLogic(Button button, character character, Display display) {
		this.character = character;
		this.display = display;
		checkLevelUp();
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
			character.incLevel();
			levelUp();
		}
	}

	private void levelUp() {
		//		levelUpShell = new Shell(display);
		//		StackLayout stackLayout = new StackLayout();
		//		levelUpShell.setLayout(stackLayout);

		int level = character.getLevel();
		String[] specials = character.getCharClass().getSpecial()[level];
		if (specials != null) {
		for (int i = 0; i < specials.length; i++)
			saveSpecialAbilities.add(specials[i]);
		}
		// TODO when saving, don't save 'bonus feat'
		
		////////// PAGE NUMBERS //////////
		final int AS = 0;
		final int HP = 1;
		final int SKILL = 2;
		final int FEAT = 3;
		final int FIGHTER = 4;
		final int SPELL = 5;
		final int DONE = 6;

		//////////////////// INITIALIZE PAGES ////////////////////
		//ArrayList<Composite> pages = new ArrayList<Composite>();
		ArrayList<Shell> pages = new ArrayList<Shell>();

		//Composite abilityScoresPage = new Composite(levelUpShell, SWT.NONE);
		Shell abilityScoresPage = new Shell(display);
		abilityScoresPage.setImage(new Image(display, "images/bnb_logo.gif"));
		pages.add(abilityScoresPage);
		//Composite hpPage = new Composite(levelUpShell, SWT.NONE);
		Shell hpPage = new Shell(display);
		hpPage.setImage(new Image(display, "images/bnb_logo.gif"));
		pages.add(hpPage);
		//Composite skillsPage = new Composite(levelUpShell, SWT.NONE);
		Shell skillsPage = new Shell(display);
		skillsPage.setImage(new Image(display, "images/bnb_logo.gif"));
		pages.add(skillsPage);
		//Composite featsPage = new Composite(levelUpShell, SWT.NONE);
		Shell featsPage = new Shell(display);
		featsPage.setImage(new Image(display, "images/bnb_logo.gif"));
		pages.add(featsPage);
		//Composite fighterPage = new Composite(levelUpShell, SWT.NONE);
		Shell fighterPage = new Shell(display);
		fighterPage.setImage(new Image(display, "images/bnb_logo.gif"));
		pages.add(fighterPage);
		//Composite spellsPage = new Composite(levelUpShell, SWT.NONE);
		Shell spellsPage = new Shell(display);
		spellsPage.setImage(new Image(display, "images/bnb_logo.gif"));
		pages.add(spellsPage);

		//Composite donePage = new Composite(levelUpShell, SWT.NONE);
		Shell donePage = new Shell(display);
		donePage.setImage(new Image(display, "images/bnb_logo.gif"));
		pages.add(donePage);

		GridLayout gl;
		GridData gd;


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
				openNextPage(pages.get(HP));
			}
		});

		abilityScoresPage.layout();
		abilityScoresPage.pack();


		//////////////////// HIT POINTS PAGE ////////////////////
		// TODO check if ability score changed was con, add changed mod instead
		gl = new GridLayout(4, true);
		hpPage.setLayout(gl);

		String hitDie = character.getCharClass().getHitDie();
		int hitDieValue = Integer.parseInt(hitDie.replaceAll("[^\\d]", ""));
		int conBase = character.getAbilityScores()[GameState.CONSTITUTION];
		if (saveAS == GameState.CONSTITUTION)
			conBase++;
		int conMod = character.getAbilityModifier(conBase);

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
				Shell nextPage;
				if (skipSkills) {
					if (!skipFeats)
						nextPage = pages.get(FEAT);
					else if (!skipFighter)
						nextPage = pages.get(FIGHTER);
					else if(!skipSpells)
						nextPage = pages.get(SPELL);
					else
						nextPage = pages.get(DONE);
				} else {
					nextPage = pages.get(SKILL);
				}
				openNextPage(nextPage); 
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
		// adds +1 if player added a point to intelligence during level up
		int intBase = character.getAbilityScores()[GameState.INTELLIGENCE];
		if (saveAS == GameState.INTELLIGENCE)
			intBase++;
		int intMod = character.getAbilityModifier(intBase);
		numSkillPoints = Integer.parseInt(Character.toString(character.getCharClass().getSkillPointsPerLevel().charAt(0))) + intMod;
		//int numSkillPoints = 5;
		if (numSkillPoints <= 0)
			skipSkills = true;
		skillPointsLabel.setText("Skill Points Remaining: " + numSkillPoints);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 8;
		skillPointsLabel.setLayoutData(gd);

		// class skill color
		Color classSkillColor = new Color(display, 0, 200, 100);

		// cross class skill color
		Color crossClassSkillColor = new Color(display, 0, 0, 255);

		// class skill label
		Label classSkillLabel = new Label(skillsPage, SWT.NONE);
		classSkillLabel.setForeground(classSkillColor);
		classSkillLabel.setText("Class Skills: 1 point = 1 rank");
		gd = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		gd.horizontalSpan = 4;
		classSkillLabel.setLayoutData(gd);

		// cross-class skill label
		Label crossClassSkillLabel = new Label(skillsPage, SWT.NONE);
		crossClassSkillLabel.setForeground(crossClassSkillColor);
		crossClassSkillLabel.setText("Cross-Class Skills: 2 points = 1 rank");
		gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gd.horizontalSpan = 4;
		crossClassSkillLabel.setLayoutData(gd);

		// untrained label
		Label untrainedLabel = new Label(skillsPage, SWT.NONE);
		untrainedLabel.setText(Character.toString((char)8226) + " : skill can be used untrained");
		gd = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		gd.horizontalSpan = 4;
		untrainedLabel.setLayoutData(gd);

		// ac penalty label
		Label acPenLabel = new Label(skillsPage, SWT.NONE);
		acPenLabel.setText("*: AC penalty  **: double AC penalty");
		gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gd.horizontalSpan = 4;
		acPenLabel.setLayoutData(gd);

		// column titles
		String[] titles = {"+", Character.toString(((char) 8211)), "Skill (Type)", "Rank", "Ability Mod", "Misc Mod", "Total"};
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

		// error label
		Label errorLabel = new Label(skillsPage, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 8;
		errorLabel.setLayoutData(gd);
		errorLabel.setVisible(false);
		errorLabel.setForeground(new Color(display, 255,0,0));
		errorLabel.pack();

		ArrayList<CharSkill> charSkills = character.getSkills();
		saveSkills = new ArrayList<SkillAdjNode>();
		ArrayList<Composite> rows = new ArrayList<Composite>();

		for (int i = 0; i < charSkills.size(); i++) {
			// set current, add to list
			SkillAdjNode current = new SkillAdjNode(charSkills.get(i), 0);
			saveSkills.add(current);
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
			final String acPen;
			if (current.getCharSkill().hasACPen()) {
				if (current.getCharSkill().getSkill().getName().equalsIgnoreCase("Swim"))
					acPen = "**";
				else 
					acPen = "*";
			} else 
				acPen = "";
			final String untrained;
			if (current.getCharSkill().useUntrained())
				untrained = Character.toString ((char) 8226);
			else 
				untrained = "  ";
			skill.setText(untrained + charSkills.get(i).getSkill().getName() + " (" + charSkills.get(i).getAbilityType() + ")" + acPen);
			if (current.getCharSkill().isClassSkill())
				skill.setForeground(classSkillColor);
			else
				skill.setForeground(crossClassSkillColor);
			gd = new GridData(SWT.FILL, SWT.FILL, true, false);
			gd.horizontalSpan = 2;
			skill.setLayoutData(gd);
			// rank
			Label rank = new Label(currRow, SWT.CENTER);
			rank.setText(Integer.toString(charSkills.get(i).getRank()));
			gd = new GridData(SWT.FILL, SWT.FILL, true, false);
			rank.setLayoutData(gd);
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
			// total
			Label total = new Label(currRow, SWT.CENTER);
			int totalTemp = current.getCharSkill().getTotal() + current.getAdj();
			String temp = "";
			if (totalTemp > 0)
				temp += "+";
			temp += totalTemp;
			total.setText(temp);
			gd = new GridData(SWT.FILL, SWT.FILL, true, false);
			total.setLayoutData(gd);
			// inc listener
			inc.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					errorLabel.setVisible(false);
					if (numSkillPoints == 0) {
						errorLabel.setText("You have no more skill points to spend!");
						errorLabel.setVisible(true);
						skillsPage.layout();
						return;
					}
					if (current.getCharSkill().tryIncRank(numSkillPoints)) {
						current.incAdj();
						rank.setText(Integer.toString(current.getCharSkill().getRank() + current.getAdj()));
						int totalTemp = current.getCharSkill().getTotal() + current.getAdj();
						String temp = "";
						if (totalTemp > 0)
							temp += "+";
						temp += totalTemp;
						total.setText(temp);						
						if (!current.getCharSkill().isClassSkill())
							numSkillPoints--;
						numSkillPoints--;
						skillPointsLabel.setText("Skill Points Remaining: " + numSkillPoints);
						skillPointsLabel.pack();
					} else {
						errorLabel.setText("You have maxed out that skill!");
						errorLabel.setVisible(true);
						skillsPage.layout();
					}
				}
			});
			// dec listener
			dec.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					errorLabel.setVisible(false);
					if (current.decAdj()) {
						rank.setText(Integer.toString(current.getCharSkill().getRank() + current.getAdj()));
						int totalTemp = current.getCharSkill().getTotal() + current.getAdj();
						String temp = "";
						if (totalTemp > 0)
							temp += "+";
						temp += totalTemp;
						total.setText(temp);
						if (!current.getCharSkill().isClassSkill())
							numSkillPoints++;
						numSkillPoints++;
						skillPointsLabel.setText("Skill Points Remaining: " + numSkillPoints);
						skillPointsLabel.pack();
					} else {
						errorLabel.setText("That skill's rank cannot be decreased further!");
						errorLabel.setVisible(true);
						skillsPage.layout();
					}
				}
			});

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
				if (numSkillPoints > 0) {
					errorLabel.setText("You must spend all skill points!");
					errorLabel.setVisible(true);
					skillsPage.layout();
					return;
				}
				Shell nextPage;
				if (!skipFeats) {
					nextPage = pages.get(FEAT);
				} else if (!skipFighter) {
					nextPage = pages.get(FIGHTER);
				} else if (!skipSpells) {
					nextPage = pages.get(SPELL);
				} else {
					nextPage = pages.get(DONE);
				}
				openNextPage(nextPage);
			}
		});

		skillsPage.layout();
		skillsPage.pack();

		//////////////////// FEATS PAGE ////////////////////
		gl = new GridLayout(7, true);
		featsPage.setLayout(gl);
		
		// check if feat page should be skipped
		if (level % 3 == 0) {
			// all characters get a bonus feat every 3 levels
			skipFeats = false;
			numFeats++;
		}
		for (int i = 0; i < saveSpecialAbilities.size(); i++) {
			// check if that character's class gets a bonus feat at that level
			if (saveSpecialAbilities.get(i).equalsIgnoreCase("bonus feat")) {
				String charClass = character.getCharClass().getName();
				// fighters and monks choose from a selection of bonus feats
				if (charClass.equalsIgnoreCase("fighter") || charClass.equalsIgnoreCase("monk"))
					skipFighter = false;
				else {
					numFeats++;
					skipFeats = false;
				}
			}
		}

		// get feats from references 
		Collection<DNDEntity> featsCol =  Main.gameState.feats.values();
		Iterator<DNDEntity> itr = featsCol.iterator();
		feats = new ArrayList<FeatEntity>();
		while (itr.hasNext()) {
			feats.add((FeatEntity) itr.next());
		}

		// feats label
		Label featsLabel = new Label(featsPage, SWT.NONE);
		featsLabel.setText("Number of Feats Remaining: " + numFeats);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 7;
		featsLabel.setLayoutData(gd);

		// details label
		Label detailsLabel = new Label(featsPage, SWT.NONE);
		detailsLabel.setText("Double click on a feat to see details");
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 7;
		detailsLabel.setLayoutData(gd);

		// feat list
		List featsList = new List(featsPage, SWT.V_SCROLL | SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 3;
		gd.verticalSpan = 2;
		featsList.setLayoutData(gd);
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

		// add button
		Button addButton = new Button(featsPage, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.END, true, true);
		addButton.setLayoutData(gd);
		addButton.setText("Add >");

		// character feat list
		charFeatsList = new List(featsPage, SWT.V_SCROLL | SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 3;
		gd.verticalSpan = 2;
		charFeatsList.setLayoutData(gd);
		// selected feats list
		numCharFeats =  character.getFeats().size();
		for (int i = 0; i < character.getFeats().size(); i++) {
			// create copy of list
			saveFeats.add(character.getFeats().get(i));
		}
		
		updateCharFeatsList();
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

		// remove button
		Button removeButton = new Button(featsPage, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, true);
		removeButton.setLayoutData(gd);
		removeButton.setText("< Remove");

		// error message
		Label errorMsg = new Label(featsPage, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 7;
		errorMsg.setLayoutData(gd);
		errorMsg.setForeground(new Color(display, 255, 0, 0));
		errorMsg.setVisible(false);
		errorMsg.pack();

		// add feat listener
		addButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				errorMsg.setVisible(false);
				boolean error = false;
				// check if the user can add another feat
				if (numFeats == 0) {
					errorMsg.setText("You cannot add any more feats");
					errorMsg.pack();
					errorMsg.setVisible(true);
					featsPage.layout();
					return;
				}
				int index = featsList.getSelectionIndex();
				// check if a feat was selected
				if (index == -1) {
					errorMsg.setText("You must select a feat to add");
					errorMsg.pack();
					errorMsg.setVisible(true);
					featsPage.layout();
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
					while (i < saveFeats.size()) {
						if (saveFeats.get(i).getFeat().getName().equals(feat.getFeat().getName())) {
							if (!(saveFeats.get(i).getSpecial().equalsIgnoreCase("All"))) {
								saveFeats.remove(i);
								updateCharFeatsList();
								numCharFeats--;
							} else i++;
						} else i++;
					}
					feat.setSpecial("All");
				}
				// check if that feat was already added
				for(int i = 0; i < saveFeats.size(); i++) {
					if (saveFeats.get(i).getFeat().getName().equals(feat.getFeat().getName())) {
						// feat found - check if that feat can be added multiple times
						if (!feat.getFeat().canHaveMultiple()) {
							// feat cannot be added multiple times
							errorMsg.setText("Feat already added");
							errorMsg.pack();
							errorMsg.setVisible(true);
							error = true;
							featsPage.layout();
						}
						else {
							// feat can be added multiple times
							if (saveFeats.get(i).getFeat().canStack()) {
								// feat benefits can stack - increment count of feat
								saveFeats.get(i).incCount();
							}
							else {
								// feat benefits cannot stack - check if the exact same feat is added
								if (saveFeats.get(i).getSpecial().equals(feat.getSpecial())) {
									errorMsg.setText("Feat already added");
									errorMsg.pack();
									errorMsg.setVisible(true);
									error = true;
									featsPage.layout();
								}
							}
						}
					}
				}
				// if something went wrong, do not perform the add
				if (error)
					return;
				// pop-up for extra info (i.e. weapons, schools of magic, skills, spells);
				if (!Wiz5.checkPrerequisites(saveFeats, feat, character)) {
					errorMsg.setText("Feat requirements not met");
					errorMsg.pack();
					errorMsg.setVisible(true);
					featsPage.layout();
					return;					
				}
				// otherwise, add the feat
				saveFeats.add(feat);
				updateCharFeatsList();
				numFeats--;
				featsLabel.setText("Number of Feats Remaining: " + numFeats);
				featsLabel.setBackground(null);
				featsLabel.pack();
			}
		});
		
		// remove feat listener
		removeButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				errorMsg.setVisible(false);
				// check if there are any feats to remove
				if (saveFeats.isEmpty()) {
					errorMsg.setText("There are no feats to remove");
					errorMsg.pack();
					errorMsg.setVisible(true);
					featsPage.layout();
					return;
				}
				int index = charFeatsList.getSelectionIndex();
				// check if a feat is selected
				if (index == -1){
					errorMsg.setText("You must select a feat to remove");
					errorMsg.pack();
					errorMsg.setVisible(true);
					featsPage.layout();
					return;
				}
				// user cannot remove a bonus feat
				if (index < numCharFeats) {
					errorMsg.setText("You cannot remove a character feat");
					errorMsg.pack();
					errorMsg.setVisible(true);
					featsPage.layout();
					return;
				}
				// if nothing goes wrong, remove the feat
				saveFeats.remove(index);
				updateCharFeatsList();
				numFeats++;
				featsLabel.setText("Number of Feats Remaining: " + numFeats);
				featsLabel.setBackground(null);
				featsLabel.pack();
			}
		});

		cancelButton(featsPage);

		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 5;
		new Label(featsPage, SWT.NONE).setLayoutData(gd);

		Button featsNext = nextButton(featsPage);
		featsNext.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				// cannot continue if there is a pop up open
				if (specialOpen) {
					featSpecialShell.forceActive();
					return;
				}	
//				if (bonusOpen) {
//					bonusFeatShell.forceActive();
//					return;
//				}
				// error checking
				if (numFeats > 0) {
					featsLabel.setBackground(new Color(display, 255, 100, 100));
					return;
				}
				Shell nextPage;
				if (level % 2 == 0 && !skipFighter)
					nextPage = pages.get(FIGHTER);
				else if (!skipSpells)
					nextPage = pages.get(SPELL);
				else
					nextPage = pages.get(DONE);
				openNextPage(nextPage);
			}
		});

		featsPage.layout();
		featsPage.pack();

		//////////////////// FIGHTER PAGE ////////////////////
		// TODO check if they already have that feat!
		// compile list of bonus feats (from which the user can choose one)
		ArrayList<FeatEntity> bonusFeats = new ArrayList<FeatEntity>();
		if (character.getCharClass().getName().toLowerCase().equals("fighter")){
			if (character.getLevel()%2 == 0) {
				for (int i = 0; i < feats.size(); i++){
					if (feats.get(i).getFighterBonus() != null)
						bonusFeats.add(feats.get(i));
				}
			} else
				skipFighter = true;
		} else if (character.getCharClass().getName().toLowerCase().equals("monk")){
			if (character.getLevel() == 2) {
				bonusFeats.add((FeatEntity)Main.gameState.feats.get("Combat Reflexes"));
				bonusFeats.add((FeatEntity)Main.gameState.feats.get("Deflect Arrows"));
			} else if (character.getLevel() == 6) {
				bonusFeats.add((FeatEntity)Main.gameState.feats.get("Improved Disarm"));
				bonusFeats.add((FeatEntity)Main.gameState.feats.get("Improved Trip"));
			} else
				skipFighter = true;
		} else
			skipFighter = true;

//		bonusOpen = true;
		
//		// create shell
//		bonusFeatShell = new Shell(display);
//		bonusFeatShell.setImage(new Image(display, "images/bnb_logo.gif"));
//		bonusFeatShell.setText("Select Bonus Feat");
		GridLayout gridLayout = new GridLayout(2, true);
		fighterPage.setLayout(gridLayout);
//		fighterPage.addListener(SWT.Close, new Listener() {
//	        public void handleEvent(Event event) {
//	            bonusDone = false;
//	            bonusOpen = false;
//	        }
//	    });

		// label - select a bonus feat
		Label selectBonusFeat = new Label(fighterPage, SWT.WRAP);
		selectBonusFeat.setText("Select A Bonus Feat");
		GridData selectGD = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		selectGD.horizontalSpan = 2;
		selectBonusFeat.setLayoutData(selectGD);
		selectBonusFeat.pack();
		
		// drop down menu containing bonus feat options
		CCombo bonusFeatCombo = new CCombo(fighterPage, SWT.DROP_DOWN | SWT.READ_ONLY);
		for (int i = 0; i < bonusFeats.size(); i++)
			bonusFeatCombo.add(bonusFeats.get(i).getName());
		GridData featsGD = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		featsGD.horizontalSpan = 2;
		bonusFeatCombo.setLayoutData(featsGD);
		bonusFeatCombo.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				bonusFeatCombo.setBackground(new Color(display, 255, 255, 255));
			}
		});
		bonusFeatCombo.pack();
		
		// done button
//		Button done = new Button(fighterPage, SWT.PUSH);
//		done.setText("Done");
//		GridData doneGD = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
//		doneGD.horizontalSpan = 2;
//		done.setLayoutData(doneGD);
//		done.addListener(SWT.Selection, new Listener() {
//			public void handleEvent(Event event) {
//				if (bonusFeatCombo.getSelectionIndex() == -1) {
//					bonusFeatCombo.setBackground(new Color(display, 255, 100, 100));
//					return;
//				}
//				numCharFeats++;
//				saveFeats.add(0, new CharFeat(bonusFeats.get(bonusFeatCombo.getSelectionIndex())));
//				updateCharFeatsList();
//				bonusDone = true;
//				bonusOpen = false;
//				fighterPage.dispose();
//			}
//		});
//		done.pack();
//
//		// open shell
//		bonusFeatShell.pack();
//		CharacterWizard.center(bonusFeatShell);
//		bonusFeatShell.open();
//		
//		// check if disposed
//		while (!bonusFeatShell.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
//		
//		return bonusDone;
		
		cancelButton(fighterPage);

		Button fighterNext = nextButton(fighterPage);
		fighterNext.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				if (bonusFeatCombo.getSelectionIndex() == -1) {
					bonusFeatCombo.setBackground(new Color(display, 255, 100, 100));
					return;
				}
				numCharFeats++;
				saveFeats.add(0, new CharFeat(bonusFeats.get(bonusFeatCombo.getSelectionIndex())));
				Shell nextPage;
				if (!skipSpells)
					nextPage = pages.get(SPELL);
				else
					nextPage = pages.get(DONE);
				openNextPage(nextPage);
			}
		});

		fighterPage.layout();
		fighterPage.pack();

		
		//////////////////// SPELL PAGE ////////////////////
		gl = new GridLayout(7, true);
		spellsPage.setLayout(gl);

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
			 * TODO: 
			 * 
			 * wizard - gets 2 bonus spells every level? only show spells that they can cast 
			 * 	(from spellsKnown)
			 * shouldn't have level-spells to select? 
			 * characters who can cast spells starting at certain levels, check? 
			 * add spells known only once they can cast spells?
			 * check if they already have that spell!
			 * add known spells to charspellslist
			 * 
			 */
			

			// check if character is a spell caster
			if (!character.getCharClass().isCaster())
				skipSpells = true;

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
//					for (int i = 0; i < spells.size(); i++) {
//						if (Wiz7.getLevel(character, spells.get(i)) == 0){
//							if (Wiz7.checkIfProhibited(character, spells.get(i))) {
//								character.addSpell(spells.get(i));
//							}
//						}
//					}
					//TODO do stuff here?
				} else {
					skipSpells = true;
				}
			}

			// initialize layout
			spellsPage.setImage(new Image(display, "images/bnb_logo.gif"));
			spellsPage.setText("Select Known Spells");

			numSpellsLeft = new Label(spellsPage, SWT.NONE);
			gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
			gd.horizontalSpan = 7;
			numSpellsLeft.setLayoutData(gd);

			detailsLabel = new Label(spellsPage, SWT.NONE);
			gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
			gd.horizontalSpan = 7;
			detailsLabel.setLayoutData(gd);

			Label spellErrorLabel = new Label(spellsPage, SWT.NONE);
			gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
			gd.horizontalSpan = 7;
			spellErrorLabel.setLayoutData(gd);		

			List spellsList = new List(spellsPage, SWT.V_SCROLL);
			gd = new GridData(SWT.FILL, SWT.FILL, true, true);
			gd.horizontalSpan = 3;
			gd.verticalSpan = 2;
			spellsList.setLayoutData(gd);

			addButton = new Button(spellsPage, SWT.PUSH);
			gd = new GridData(SWT.CENTER, SWT.END, false, true);
			addButton.setLayoutData(gd);

			charSpellsList =  new List(spellsPage, SWT.V_SCROLL);
			gd = new GridData(SWT.FILL, SWT.FILL, true, true);
			gd.horizontalSpan = 3;
			gd.verticalSpan = 2;
			charSpellsList.setLayoutData(gd);

			removeButton = new Button(spellsPage, SWT.PUSH);
			gd = new GridData(SWT.CENTER, SWT.BEGINNING, false, true);
			removeButton.setLayoutData(gd);

//			Button cancelButton = new Button(spellsPage, SWT.PUSH);
//			gd = new GridData(SWT.LEFT, SWT.END, true, false);
//			gd.horizontalSpan = 3;
//			cancelButton.setLayoutData(gd);
//
//			// placeholder
//			new Label(spellsPage, SWT.NONE).setLayoutData(new GridData());
//
//			Button doneButton = new Button(spellsPage, SWT.PUSH);
//			gd = new GridData(SWT.RIGHT, SWT.END, true, false);
//			gd.horizontalSpan = 3;
//			doneButton.setLayoutData(gd);
			
			// create content
			if (!skipSpells) {

			// num spells left label
			int[][] temp = character.getCharClass().getSpellsKnown();
			// get num spells the character can know based on their level
			if (temp == null) {
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
				skipSpells = true;
			}

			origNumSpells = new int[numSpells.length];
			for (int i = 0; i < origNumSpells.length; i++)
				origNumSpells[i] = numSpells[i];
			updateNumSpellsLeft();
			spellsPage.layout();

			// details label
			detailsLabel.setText("Double click on a spell to see details");
			detailsLabel.pack();

			// error label - set text when called
			errorLabel.setForeground(new Color(display, 255, 0, 0));
			errorLabel.setVisible(false);

			// add spells to list
			for (int i = 0; i < spells.size(); i++) {
				int spellLevel = Wiz7.getLevel(character, spells.get(i));
				if (spellLevel > -1) {
					// only add spells they can learn
					if (numSpells[spellLevel] > 0 && Wiz7.checkIfProhibited(character, spells.get(i)))
						spellsList.add(spells.get(i).getName() + ": lvl. " + spellLevel);
					if (bonusSpells != 0) {
						if (spellLevel <= wizHighestLevel && Wiz7.checkIfProhibited(character, spells.get(i))) {
							spellsList.add(spells.get(i).getName() + ": lvl. " + spellLevel);
						}
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
						spellErrorLabel.setText("You must select a spell to add");
						spellErrorLabel.pack();
						spellsPage.layout();
						spellErrorLabel.setVisible(true);
						return;
					}

					String spell = spellsList.getItem(index);
					String spellName = spell.substring(0, spell.indexOf(':'));

					// check if already added
					for (int i = 0; i < saveSpells.size(); i++) {
						if (saveSpells.get(i).getName().equalsIgnoreCase(spellName)) {
							spellErrorLabel.setText("Spell already added");
							spellErrorLabel.pack();
							spellsPage.layout();
							spellErrorLabel.setVisible(true);
							return;
						}
					}

					// check level
					int spellLevel = Integer.parseInt(spell.replaceAll("[^\\d]", ""));
					if (numSpells[spellLevel] > 0) {
						saveSpells.add((SpellEntity)Main.gameState.spells.get(spellName));
						updateCharSpellsList();
						numSpells[spellLevel]--;
						updateNumSpellsLeft();
						spellsPage.layout();
					} else if (bonusSpells > 0) {
						saveSpells.add((SpellEntity)Main.gameState.spells.get(spellName));
						updateCharSpellsList();
						bonusSpells--;
						updateNumSpellsLeft();
						spellsPage.layout();
					} else {
						spellErrorLabel.setText("You cannot add a spell of that level");
						spellErrorLabel.pack();
						spellsPage.layout();
						spellErrorLabel.setVisible(true);
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
						spellErrorLabel.setText("You must select a spell to remove");
						spellErrorLabel.pack();
						spellsPage.layout();
						spellErrorLabel.setVisible(true);
						return;
					}
					String temp = charSpellsList.getItem(index);
					int level = Integer.parseInt(temp.replaceAll("[^\\d]", ""));
					if (numSpells[level] == origNumSpells[level]) {
						bonusSpells++;
					} else
						numSpells[level]++;
					saveSpells.remove(index);
					updateCharSpellsList();
					updateNumSpellsLeft();
					spellsPage.layout();
				}
			});	

//			// cancel button
//			cancelButton.setText("Cancel");
//			cancelButton.addListener(SWT.Selection, new Listener() {
//				public void handleEvent(Event e) {
//					spellsGood = false;
//					spellsPage.dispose();
//					spellOpen = false;
//				}
//			});
//
//			// done button
//			doneButton.setText("Done");
//			doneButton.addListener(SWT.Selection, new Listener() {
//				public void handleEvent(Event e) {
//					// check if they have any spells left
//
//
////					// if they have chosen all known spells, save and close
////					for (int i = 0; i < saveSpells.size(); i++) {
////						character.addSpell(charSpells.get(i));
////					}
////
////					spellsGood = true;
////					spellsPage.dispose();
////					spellOpen = false;
////				}
//			});
////
//			spellsPage.layout();
//
//			// open shell
//			spellsPage.pack();
//			spellsPage.layout();
//			CharacterWizard.center(spellsPage);
//			spellsPage.open();
//
//			// check if disposed
//			while (!spellsPage.isDisposed()) {
//				if (!wiz7.getDisplay().readAndDispatch()) {
//					wiz7.getDisplay().sleep();
//				}
//			}
//
//			return spellsGood;

		cancelButton(spellsPage);
		
		gd = new GridData();
		gd.horizontalSpan = 5;
		new Label(spellsPage, SWT.NONE).setLayoutData(gd);

		Button spellsNext = nextButton(spellsPage);
		spellsNext.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				for (int i = 0; i < numSpells.length; i++) {
					if (numSpells[i] > 0){
						numSpellsLeft.setBackground(new Color(display, 255, 100, 100));
						return;
					}
				}
				Shell nextPage = pages.get(DONE);
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
				// save everything
				character.modHitPoints(saveHP);
				int[] asMod = {0, 0, 0, 0, 0, 0};
				if (saveAS > -1)
					asMod[saveAS]++;
				character.modifyAbilityScores(asMod);
				String[] specials = character.getCharClass().getSpecial()[character.getLevel()];
				for (int i = 0; i < specials.length; i++) {
					if (!specials[i].equalsIgnoreCase("bonus feat")){
					    AbilityEntity ae = (AbilityEntity)Main.gameState.abilities.get(specials[i]);
						if (ae != null )character.addSpecialAbility(ae);
					}
				}
				ArrayList<CharSkill> newSkills = new ArrayList<CharSkill>();
				for (int i = 0; i < saveSkills.size(); i++) {
					CharSkill temp = saveSkills.get(i).getCharSkill();
					temp.modRank(saveSkills.get(i).getAdj());
					newSkills.add(temp);
				}
				character.setSkills(newSkills);
				if (saveFeats.size() != 0)
					character.setFeats(saveFeats);
				if (saveSpells.size() != 0)
					character.setSpells(saveSpells);
				
				// TODO save to xml?

				// refresh character sheet
				character.getCharMain().getPlayerInfo(character.getFilename(), false);
				character.getCharMain().refresh();
				new SaveCharacter(false);
				
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
		 * skill points (level + 3 max rank, # + intel mod points)
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

		if (level%4 == 0)
			curr = pages.get(AS);
		else 
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
						// reverse the level up
						character.setLevel(character.getLevel()-1);
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

	private boolean selectFeatSpecial(CharFeat feat) {
		// create shell
		specialOpen = true;
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

		ArrayList<String> specials = Wiz5.getSpecials(feat.getFeat());
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
					specialsCombo.setBackground(new Color(display, 255, 100, 100));
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
	
	private void updateCharSpellsList() {
		charSpellsList.removeAll();
		for (int i = 0; i < saveSpells.size(); i++){
			charSpellsList.add(saveSpells.get(i).getName() + ": lvl. " + Wiz7.getLevel(character, saveSpells.get(i)));
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
	}
	
	private void updateCharFeatsList() {
		charFeatsList.removeAll();
		for (int i = 0; i < saveFeats.size(); i++){
			CharFeat curr = saveFeats.get(i);
			String temp = curr.getFeat().getName();
			if (curr.getSpecial() != null)
				temp += " [" + curr.getSpecial() + "]";
			if (curr.getCount() > 1)
				temp += ": " + curr.getCount();
			charFeatsList.add(temp);
		}
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
}

