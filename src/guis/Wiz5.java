/*
 * ADD RANKS TO SKILLS
 */

/*
 * TODO add barbarian illiteracy, custom skills, profession/craft boxes
 * add class modifiers - i.e. druid gets +2 Knowledge(nature) and Survival checks
 */

package guis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import core.CharSkill;
import core.GameState;
import core.Main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import entity.*;
import core.character;
/* TODO
 * add text box to add custom skill
 * add boxes next to craft, profession, etc
 * fix speak language
 * add extra cleric class skills
 * add class bonuses (i.e. druid nature sense)
 */

public class Wiz5 {

	private static final int INC = 0;
	private static final int DEC = 1;
	private static final int NAME = 2;
	private static final int RANK = 3;
	private static final int ABILMOD = 4;
	private static final int MISCMOD = 5;
	private static final int TOTAL = 6;

	private Composite wiz5;
	private CharacterWizard cw;
	private Device dev;
	private int WIDTH;
	private int HEIGHT;
	private character character;
	private Composite panel;
	private StackLayout layout;
	private ArrayList<Composite> wizPages;
	private Composite nextPage;
	private int wizPagesSize;

	private Composite inner;

	private Label skillPointsLabel;
	private Label unusedSkillPointsError;
	private String charClass;
	private int numSkillPoints;
	private ArrayList<CharSkill> charSkills = new ArrayList<CharSkill>();


	public Wiz5(CharacterWizard cw, Device dev, int WIDTH, int HEIGHT, 
			final Composite panel, final StackLayout layout, 
			final ArrayList<Composite> wizPages) {
		wiz5 = wizPages.get(4);
		this.cw = cw;
		this.dev = dev;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.character = cw.getCharacter();
		this.panel = panel;
		this.layout = layout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(5);
		this.wizPagesSize = wizPages.size();

		charClass = character.getCharClass().getName();

		inner = new Composite(wiz5, SWT.NONE);

		createPageContent();
	}

	private void createPageContent() {
		Label wiz5Label = new Label(wiz5, SWT.NONE);
		wiz5Label.setText("Add Ranks to Skills");
		wiz5Label.pack();

		GridLayout gl = new GridLayout(3, false);

		Composite inner = new Composite(wiz5, SWT.NONE);
		inner.setBounds(5, 20, WIDTH-10, HEIGHT-110);
		inner.setLayout(gl);

		GridData gd;

		skillPointsLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 3;
		skillPointsLabel.setLayoutData(gd);

//		numSkillPointsLabel = new Label(inner, SWT.NONE);
//		gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
//		gd.horizontalSpan = 2;
//		numSkillPointsLabel.setLayoutData(gd);
		
		Label exampleSkillLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gd.horizontalSpan = 3;
		exampleSkillLabel.setLayoutData(gd);

		Label classSkillLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		classSkillLabel.setLayoutData(gd);

		Label crossClassSkillLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		crossClassSkillLabel.setLayoutData(gd);

		Label untrainedLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		untrainedLabel.setLayoutData(gd);

		//		Table skillTable = new Table(inner, SWT.PUSH | SWT.BORDER | SWT.V_SCROLL);
		//		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		//		gd.horizontalSpan = 3;
		//		skillTable.setLayoutData(gd);
		//		skillTable.setHeaderVisible(true);
		//		skillTable.setLinesVisible(true);
		//		String[] titles = { "+", Character.toString ((char) 8211), "Skill (Type)", "Rank", "Ability Modifier", "Misc. Modifier", "Total"};
		//		
		//		for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
		//			TableColumn column = new TableColumn(skillTable, SWT.NONE);
		//			column.setText(titles[loopIndex]);
		//		}

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;

		// set up scrollable composite
		final ScrolledComposite skillsScreenScroll = new ScrolledComposite(inner, SWT.V_SCROLL | SWT.BORDER);
		//skillsScreenScroll.setBounds(10, 110, WIDTH - 30, HEIGHT - 210);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 3;
		skillsScreenScroll.setLayoutData(gd);
		skillsScreenScroll.setExpandHorizontal(true);
		skillsScreenScroll.setExpandVertical(true);
		skillsScreenScroll.setMinWidth(WIDTH);
		final Composite skillsScreen = new Composite(skillsScreenScroll, SWT.NONE);
		skillsScreenScroll.setContent(skillsScreen);
		skillsScreen.setSize(skillsScreen.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		skillsScreen.setLayout(gridLayout);

		// set number of skill points
		String pointsString = cw.getCharacter().getCharClass().getSkillPointsPerLevel();
		int classPoints = Integer.parseInt(Character.toString(pointsString.charAt(0)));

		int intMod = cw.getCharacter().getAbilityModifiers()[GameState.INTELLIGENCE];
		numSkillPoints = (classPoints + intMod) * 4;
		if (numSkillPoints < 4) 
			numSkillPoints = 4;
		if (cw.getCharacter().getCharRace().equals("Human"))
			numSkillPoints += 4;

		// "skill points remaining: " label
		//skillPointsLabel.setLocation(250,30);
		skillPointsLabel.setText("Skill Points Remaining: " + numSkillPoints);
		skillPointsLabel.pack();
//
//		// number of remaining skill points label
//		//numSkillPointsLabel.setLocation(405, 30);
//		numSkillPointsLabel.setText(Integer.toString(numSkillPoints));
//		numSkillPointsLabel.pack();

		// example skill label
		//exampleSkillLabel.setLocation(25, 60);
		exampleSkillLabel.setText("Skill (Type) = (Ability Mod) + (Misc Mod) + (Rank) = (Total)" 
				+ "         *: AC penalty  **: double AC penalty");
		exampleSkillLabel.pack();

		// class skill label
		//classSkillLabel.setLocation(20, 85);
		Color classSkillColor = new Color(dev, 0, 200, 100);
		classSkillLabel.setForeground(classSkillColor);
		classSkillLabel.setText("Class Skills: 1 point = 1 rank");
		classSkillLabel.pack();

		// cross-class skill label
		//crossClassSkillLabel.setLocation(225, 85);
		Color crossClassSkillColor = new Color(dev, 0, 0, 255);
		crossClassSkillLabel.setForeground(crossClassSkillColor);
		crossClassSkillLabel.setText("Cross-Class Skills: 2 points = 1 rank");
		crossClassSkillLabel.pack();

		// untrained label
		//untrainedLabel.setLocation(470, 85);
		untrainedLabel.setText(Character.toString((char)8226) + " : skill can be used untrained");
		untrainedLabel.pack();

		// get skills from references
		Collection<DNDEntity> skillsCol =  Main.gameState.skills.values();
		Iterator<DNDEntity> itr = skillsCol.iterator();
		ArrayList<SkillEntity> skills = new ArrayList<SkillEntity>();
		while (itr.hasNext()) {
			skills.add((SkillEntity) itr.next());
		}

		// TODO add misc modifiers
		// class racial modifiers? 
		// familiars modifiers

		for (int i = 0; i < skills.size(); i++) {
			charSkills.add(new CharSkill(skills.get(i), character));
		}
		
		



		// create content (+/- buttons, skills, ranks, mods, etc
		ArrayList<Label> skillNameLabels = new ArrayList<Label>();
		ArrayList<Button> incButtons = new ArrayList<Button>();
		ArrayList<Button> decButtons = new ArrayList<Button>();

		// instantiate table items
		//		for (int i = 0; i < charSkills.size(); i++) {
		//		      new TableItem(skillTable, SWT.NONE);
		//		}

		//TableItem[] items = skillTable.getItems();

		//		for (int i = 0; i < charSkills.size(); i++) {
		//			TableEditor editor = new TableEditor(skillTable);

		/*
			// inc button
//			Button incButton = new Button(skillTable, SWT.PUSH);
//			incButton.setText("+");
//			incButton.addListener(SWT.Selection, new Listener() {
//				public void handleEvent(Event event) {
////					if (numSkillPoints == 0)
////						return;
////					if (current.incRank(numSkillPoints)) {
////						skillName.setText(untrained + current.getSkill().getName() + " (" 
////								+ current.getAbilityType() + ")" + acPen + " = " 
////								+ abilityMod + " + " + miscMod + " + " 
////								+ current.getRank() + " = " + current.getTotal());
////						skillName.pack();
////						if (!current.isClassSkill())
////							numSkillPoints--;
////						numSkillPoints--;
////						numSkillPointsLabel.setText(Integer.toString(numSkillPoints));
////						numSkillPointsLabel.pack();
////						unusedSkillPointsError.setVisible(false);
////					}
//				}
//			});
//			incButton.pack();
//		    editor.minimumWidth = incButton.getSize().x;
//		    editor.horizontalAlignment = SWT.LEFT;
//			editor.setEditor(incButton, items[i], 0); // sets location in table

			// dec button
//			editor = new TableEditor(skillTable);
//			Button decButton = new Button(skillTable, SWT.PUSH);
//			decButton.setText(Character.toString ((char) 8211));
//			decButton.addListener(SWT.Selection, new Listener() {
//				public void handleEvent(Event event) {
////					if (current.decRank()) {
////						skillName.setText(untrained + current.getSkill().getName() + " (" 
////								+ current.getAbilityType() + ")" + acPen + " = " + abilityMod 
////								+ " + " + miscMod + " + " + current.getRank() 
////								+ " = " + current.getTotal());
////						skillName.pack();
////						if (!current.isClassSkill())
////							numSkillPoints++;
////						numSkillPoints++;
////						numSkillPointsLabel.setText(Integer.toString(numSkillPoints));
////						numSkillPointsLabel.pack();
////						unusedSkillPointsError.setVisible(false);
////					}
//				}
//			});
//			decButton.pack();
//		    editor.minimumWidth = decButton.getSize().x;
//		    editor.horizontalAlignment = SWT.LEFT;
//			editor.setEditor(decButton, items[i], 1);
		 */
		//			CharSkill skill = charSkills.get(i);
		//			TableItem item = new TableItem(skillTable, SWT.NONE);
		//			item.setText(INC, "+");
		//			item.setText(DEC, Character.toString ((char) 8211));
		//			item.setText(NAME, getSkillText(skill));
		//			item.setText(RANK, "0");
		//			item.setText(ABILMOD, Integer.toString(charSkills.get(i).getAbilityMod()));
		//			item.setText(MISCMOD, Integer.toString(charSkills.get(i).getMiscMod()));
		/*
			// skill name
//			editor = new TableEditor(skillTable);
			Text skillName = new Text(skillTable, SWT.READ_ONLY);
			skillName.setText(getSkillText(skill));
			if (skill.isClassSkill())
				skillName.setForeground(classSkillColor);
			else
				skillName.setForeground(crossClassSkillColor);
//			editor.grabHorizontal = true;
//			editor.setEditor(skillName, items[i], 2);
//			skillName.pack();

			// rank
//			editor = new TableEditor(skillTable);
			Text rank = new Text(skillTable, SWT.READ_ONLY);
			rank.setText("0");
			//editor.grabHorizontal = true;
//			editor.setEditor(rank, items[i], 3);
//			rank.pack();

			// ability mod
//			editor = new TableEditor(skillTable);
			Text abilityMod = new Text(skillTable, SWT.READ_ONLY);
			abilityMod.setText(Integer.toString(skill.getAbilityMod()));
//			editor.grabHorizontal = true;
//			editor.setEditor(abilityMod, items[i], 4);
//			abilityMod.pack();

			// misc mod
//			editor = new TableEditor(skillTable);
			Text miscMod = new Text(skillTable, SWT.READ_ONLY);
			miscMod.setText(Integer.toString(skill.getAbilityMod()));
//			editor.grabHorizontal = true;
//			editor.setEditor(miscMod, items[i], 5);
//			miscMod.pack();

			// total
//			editor = new TableEditor(skillTable);
			Text total = new Text(skillTable, SWT.READ_ONLY);
			total.setText("0");
//			editor.grabHorizontal = true;
//			editor.setEditor(total, items[i], 6);
//			total.pack();
		 * 
		 */
		//	}
		//		skillTable.pack();
		inner.layout();

		for(int i = 0; i < charSkills.size(); i++) {
			Button inc = new Button(skillsScreen, SWT.PUSH);
			inc.setText("+");
			GridData incGD = new GridData(SWT.LEFT);
			inc.setLayoutData(incGD);
			inc.pack();
			Button dec = new Button(skillsScreen, SWT.PUSH);
			dec.setText(Character.toString ((char) 8211));
			GridData decGD = new GridData(SWT.LEFT);
			dec.setLayoutData(decGD);
			dec.pack();
			final Label skillName = new Label(skillsScreen, SWT.NONE);
			skillName.setLayoutData(new GridData(SWT.LEFT));
			final CharSkill current = charSkills.get(i);
			final int abilityMod = current.getAbilityMod();
			final int miscMod = current.getMiscMod();
			final String acPen;
			if (current.hasACPen()) {
				if (current.getSkill().getName().equalsIgnoreCase("Swim"))
					acPen = "**";
				else 
					acPen = "*";
			} else 
				acPen = "";
			final String untrained;
			if (current.useUntrained())
				untrained = Character.toString ((char) 8226);
			else 
				untrained = "  ";
			skillName.setText(untrained + current.getSkill().getName() + " (" 
					+ current.getAbilityType() + ")" + acPen + " = " + abilityMod + " + " 
					+ miscMod + " + " + current.getRank() + " = " + current.getTotal());
			if (current.isClassSkill())
				skillName.setForeground(classSkillColor);
			else
				skillName.setForeground(crossClassSkillColor);
			skillName.pack();
			skillNameLabels.add(skillName);
			inc.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					if (numSkillPoints == 0)
						return;
					if (current.incRank(numSkillPoints)) {
						skillName.setText(untrained + current.getSkill().getName() + " (" 
								+ current.getAbilityType() + ")" + acPen + " = " 
								+ abilityMod + " + " + miscMod + " + " 
								+ current.getRank() + " = " + current.getTotal());
						skillName.pack();
						if (!current.isClassSkill())
							numSkillPoints--;
						numSkillPoints--;
						skillPointsLabel.setText("Skill Points Remaining: " + numSkillPoints);
						skillPointsLabel.pack();
						unusedSkillPointsError.setVisible(false);
					}
				}
			});
			incButtons.add(inc);
			dec.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					if (current.decRank()) {
						skillName.setText(untrained + current.getSkill().getName() + " (" 
								+ current.getAbilityType() + ")" + acPen + " = " + abilityMod 
								+ " + " + miscMod + " + " + current.getRank() 
								+ " = " + current.getTotal());
						skillName.pack();
						if (!current.isClassSkill())
							numSkillPoints++;
						numSkillPoints++;
						skillPointsLabel.setText("Skill Points Remaining: " + numSkillPoints);
						skillPointsLabel.pack();
						unusedSkillPointsError.setVisible(false);
					}
				}
			});
			decButtons.add(dec);
			skillsScreen.pack();
		}


		skillsScreenScroll.setMinHeight(incButtons.get(incButtons.size()-1).getLocation().y + incButtons.get(incButtons.size()-1).getSize().y);

		// create error label
		unusedSkillPointsError = new Label(wiz5, SWT.NONE);
		unusedSkillPointsError.setVisible(false);
		unusedSkillPointsError.setLocation(200, HEIGHT - 75);
		unusedSkillPointsError.setText("You must use all of your skill points!");
		unusedSkillPointsError.setForeground(new Color(dev, 255,0,0));
		unusedSkillPointsError.pack();

		// next button
		Button wiz5NextButton = cw.createNextButton(wiz5);
		wiz5NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// make sure all skill points are used
				if (numSkillPoints > 0) {
					unusedSkillPointsError.setVisible(true);
					return;
				}

				// save to character
				character.setSkills(charSkills);

				// move on to next page
				if (cw.wizPageNum < wizPagesSize - 1)
					cw.wizPageNum++;
				if (!cw.wizPageCreated[5])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});

		//		// back button
		//		Button wiz4BackButton = cw.createBackButton(wiz4, panel, layout);
		//		wiz4BackButton.addListener(SWT.Selection, new Listener() {
		//			public void handleEvent(Event event) {
		//				unusedSkillPointsError.setVisible(false);
		//			}
		//		});

		// cancel button
		Button wiz5CancelButton = cw.createCancelButton(wiz5);
		wiz5CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (cw.cancel)
					cw.reset();
			}
		});
	}

	private String getSkillText(CharSkill skill) {
		final String acPen;
		if (skill.hasACPen()) {
			if (skill.getSkill().getName().equalsIgnoreCase("Swim"))
				acPen = "**";
			else 
				acPen = "*";
		} else 
			acPen = "";
		final String untrained;
		if (skill.useUntrained())
			untrained = Character.toString ((char) 8226);
		else 
			untrained = "  ";
		return (untrained + skill.getSkill().getName() + " (" 
				+ skill.getAbilityType() + ")" + acPen);

	}

	private void createNextPage() {
		cw.wizPageCreated[5] = true;		
		cw.wizs.add(new Wiz6(cw, dev, WIDTH, HEIGHT, panel, layout, wizPages));
	}

	public Composite getWiz5() { return wiz5; }

}
