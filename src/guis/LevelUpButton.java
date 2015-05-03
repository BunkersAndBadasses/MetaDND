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
import core.SkillAdjNode;
import core.character;
import entity.DNDEntity;
import entity.FeatEntity;

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

	private boolean specialOpen;
	private boolean specialValid;
	private Shell featSpecialShell;
	
	private List charFeatsList;
	private int numFeats;
	private int numCharFeats;

	// stuff to save when done
	private int saveHP = 0;
	private int saveAS = -1; // 0-5, index of ability score to increase
	private ArrayList<SkillAdjNode> saveSkills;
	private ArrayList<CharFeat> saveFeats = new ArrayList<CharFeat>();

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

		////////// PAGE NUMBERS //////////
		final int AS = 0;
		final int HP = 1;
		final int SKILL = 2;
		final int FEAT = 3;
		final int SPELL = 4;
		final int DONE = 5;

		//////////////////// INITIALIZE PAGES ////////////////////
		//ArrayList<Composite> pages = new ArrayList<Composite>();
		ArrayList<Shell> pages = new ArrayList<Shell>();

		//Composite abilityScoresPage = new Composite(levelUpShell, SWT.NONE);
		Shell abilityScoresPage = new Shell(display);
		pages.add(abilityScoresPage);
		//Composite hpPage = new Composite(levelUpShell, SWT.NONE);
		Shell hpPage = new Shell(display);
		pages.add(hpPage);
		//Composite skillsPage = new Composite(levelUpShell, SWT.NONE);
		Shell skillsPage = new Shell(display);
		pages.add(skillsPage);
		//Composite featsPage = new Composite(levelUpShell, SWT.NONE);
		Shell featsPage = new Shell(display);
		pages.add(featsPage);
		//Composite spellsPage = new Composite(levelUpShell, SWT.NONE);
		Shell spellsPage = new Shell(display);
		pages.add(spellsPage);

		//Composite donePage = new Composite(levelUpShell, SWT.NONE);
		Shell donePage = new Shell(display);
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
				Shell nextPage;
				if (skipSkills) {
					if (level%3 == 0)
						nextPage = pages.get(FEAT);
					//					else if() TODO
					//						nextPage = pages.get(SPELL);
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
				if (level % 3 == 0) {
					nextPage = pages.get(FEAT);
				} 
				// TODO any other pages
				else {
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

		numFeats = 1;
		// TODO add any other bonus feats, like wizards and fighters

		// get feats from references 
		Collection<DNDEntity> featsCol =  Main.gameState.feats.values();
		Iterator<DNDEntity> itr = featsCol.iterator();
		ArrayList<FeatEntity> feats = new ArrayList<FeatEntity>();
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
				// error checking
				if (numFeats > 0) {
					featsLabel.setBackground(new Color(display, 255, 100, 100));
					return;
				}
				Shell nextPage;
				// TODO any other pages
				//else
				nextPage = pages.get(DONE);
				openNextPage(nextPage);
			}
		});

		featsPage.layout();
		featsPage.pack();

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
				// TODO save everything
				// TODO refresh character sheet
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

