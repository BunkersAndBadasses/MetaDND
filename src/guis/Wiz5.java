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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import entity.*;
import core.character;
/* TODO
 * fix + - buttons
 * add text box to add custom skill
 * add boxes next to craft, profession, etc
 * add extra cleric class skills
 * add class bonuses (i.e. druid nature sense)
 */

public class Wiz5 {

	private Composite wiz5;
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

	private Label numSkillPointsLabel;
	private Label unusedSkillPointsError;
	private String charClass;
	private int numSkillPoints;
	private ArrayList<CharSkill> charSkills = new ArrayList<CharSkill>();
	
	
	public Wiz5(CharacterWizard cw, Device dev, int WIDTH, int HEIGHT, 
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz5 = wizPages.get(4);
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
		this.nextPage = wizPages.get(5);
		this.wizPagesSize = wizPages.size();

		createPageContent();
	}

	private void createPageContent() {
		Label wiz5Label = new Label(wiz5, SWT.NONE);
		wiz5Label.setText("Add Ranks to Skills");
		wiz5Label.pack();



		// set number of skill points
		charClass = cw.getCharacter().getCharClass().getName();
		int classPoints;
		switch(charClass) {
		case ("Cleric") :
		case ("Fighter") :
		case ("Paladin") :
		case ("Sorcerer") :
		case ("Wizard") :
			classPoints = 2;
			break;
		case ("Barbarian") :
		case ("Monk") :
		case ("Druid") :
			classPoints = 4;
			break;
		case ("Bard") :
		case ("Ranger") :
			classPoints = 6;
		break;
		default : // Rogue
			classPoints = 8;
			break;	
		}
		int intMod = cw.getCharacter().getAbilityModifiers()[GameState.INTELLIGENCE];
		numSkillPoints = (classPoints + intMod) * 4;
		if (numSkillPoints < 4) 
			numSkillPoints = 4;
		if (cw.getCharacter().getCharRace().equals("Human"))
			numSkillPoints += 4;

		// "skill points remaining: " label
		Label skillPointsLabel = new Label(wiz5, SWT.NONE);
		skillPointsLabel.setLocation(250,30);
		skillPointsLabel.setText("Skill Points Remaining:");
		skillPointsLabel.pack();

		// number of remaining skill points label
		numSkillPointsLabel = new Label(wiz5, SWT.NONE);
		numSkillPointsLabel.setLocation(405, 30);
		numSkillPointsLabel.setText(Integer.toString(numSkillPoints));
		numSkillPointsLabel.pack();

		// example skill label
		Label exampleSkillLabel = new Label(wiz5, SWT.NONE);
		exampleSkillLabel.setLocation(25, 60);
		exampleSkillLabel.setText("Skill (Type) = (Ability Mod) + (Misc Mod) + (Rank) = (Total)" 
					+ "         *: AC penalty  **: double AC penalty");
		exampleSkillLabel.pack();

		// class skill label
		Label classSkillLabel = new Label(wiz5, SWT.NONE);
		classSkillLabel.setLocation(20, 85);
		Color classSkillColor = new Color(dev, 0, 200, 100);
		classSkillLabel.setForeground(classSkillColor);
		classSkillLabel.setText("Class Skills: 1 point = 1 rank");
		classSkillLabel.pack();

		// cross-class skill label
		Label crossClassSkillLabel = new Label(wiz5, SWT.NONE);
		crossClassSkillLabel.setLocation(225, 85);
		Color crossClassSkillColor = new Color(dev, 0, 0, 255);
		crossClassSkillLabel.setForeground(crossClassSkillColor);
		crossClassSkillLabel.setText("Cross-Class Skills: 2 points = 1 rank");
		crossClassSkillLabel.pack();
		
		// untrained label
		Label untrainedLabel = new Label(wiz5, SWT.NONE);
		untrainedLabel.setLocation(470, 85);
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

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
//		GridData gridData = new GridData();
//		gridData.horizontalAlignment = SWT.LEFT;
		
		// set up scrollable composite
		final ScrolledComposite skillsScreenScroll = new ScrolledComposite(wiz5, SWT.V_SCROLL | SWT.BORDER);
		skillsScreenScroll.setBounds(10, 110, WIDTH - 30, HEIGHT - 210);
	    skillsScreenScroll.setExpandHorizontal(true);
	    skillsScreenScroll.setExpandVertical(true);
	    skillsScreenScroll.setMinWidth(WIDTH);
		final Composite skillsScreen = new Composite(skillsScreenScroll, SWT.NONE);
		skillsScreenScroll.setContent(skillsScreen);
		skillsScreen.setSize(skillsScreen.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		skillsScreen.setLayout(gridLayout);
		
		// create content (+/- buttons, skills, ranks, mods, etc
		ArrayList<Label> skillNameLabels = new ArrayList<Label>();
		ArrayList<Button> incButtons = new ArrayList<Button>();
		ArrayList<Button> decButtons = new ArrayList<Button>();

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
//			skillName.setLocation(260, (i*30) + 10);
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

//			inc.setBounds(200, (i*30) + 10, 20, 20);
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
						numSkillPointsLabel.setText(Integer.toString(numSkillPoints));
						numSkillPointsLabel.pack();
						unusedSkillPointsError.setVisible(false);
					}
				}
			});
			incButtons.add(inc);
//			dec.setBounds(220, (i*30) + 10, 20, 20);
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
						numSkillPointsLabel.setText(Integer.toString(numSkillPoints));
						numSkillPointsLabel.pack();
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
				((Wiz6) cw.wizs.get(5)).createBonusPopUp();
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
		Button wiz5CancelButton = cw.createCancelButton(wiz5, home, homePanel, homeLayout);
		wiz5CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (cw.cancel)
					cw.reset();
			}
		});
	}

	private void createNextPage() {
		cw.wizPageCreated[5] = true;		
		cw.wizs.add(new Wiz6(cw, dev, WIDTH, HEIGHT, panel, home,
				homePanel, layout, homeLayout, wizPages));
	}

	public Composite getWiz5() { return wiz5; }

}
