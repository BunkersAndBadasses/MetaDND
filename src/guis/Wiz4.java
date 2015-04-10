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
/*
 * add text box to add custom skill
 * add boxes next to craft, profession, etcS
 */

public class Wiz4 {

	private static Composite wiz4;
	private static Device dev;
	private static int WIDTH;
	private static int HEIGHT;
	private static character character;
	private Composite panel;
	private Composite home;
	private Composite homePanel;
	private StackLayout layout;
	private StackLayout homeLayout;
	private ArrayList<Composite> wizPages;
	private Composite nextPage;
	private int wizPagesSize;

	private Label numSkillPointsLabel;
	private static Label unusedSkillPointsError;
	private String charClass;
	private int numSkillPoints;
	private ArrayList<CharSkill> charSkills = new ArrayList<CharSkill>();
	private GameState gs = Main.gameState;
	
	
	public Wiz4(Device dev, int WIDTH, int HEIGHT, final character character, 
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz4 = wizPages.get(3);
		Wiz4.dev = dev;
		Wiz4.WIDTH = WIDTH;
		Wiz4.HEIGHT = HEIGHT;
		Wiz4.character = character;
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(4);
		this.wizPagesSize = wizPages.size();

		createPageContent();
	}

	private void createPageContent() {
		Label wiz4Label = new Label(wiz4, SWT.NONE);
		wiz4Label.setText("Add Ranks to Skills");
		wiz4Label.pack();



		// set number of skill points
		charClass = CharacterWizard.getCharacter().getCharClass().getName();
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
		int intMod = ((CharacterWizard.getCharacter().getAbilityScores()[core.character.INTELLIGENCE]) - 8)/2; // TODO check this logic
		numSkillPoints = (classPoints + intMod) * 4;
		if (numSkillPoints < 4) 
			numSkillPoints = 4;
		if (CharacterWizard.getCharacter().getCharRace().equals("Human"))
			numSkillPoints += 4;

		// "skill points remaining: " label
		Label skillPointsLabel = new Label(wiz4, SWT.NONE);
		skillPointsLabel.setLocation(250,30);
		skillPointsLabel.setText("Skill Points Remaining:");
		skillPointsLabel.pack();

		// number of remaining skill points label
		numSkillPointsLabel = new Label(wiz4, SWT.NONE);
		numSkillPointsLabel.setLocation(405, 30);
		numSkillPointsLabel.setText(Integer.toString(numSkillPoints));
		numSkillPointsLabel.pack();

		// example skill label
		Label exampleSkillLabel = new Label(wiz4, SWT.NONE);
		exampleSkillLabel.setLocation(25, 60);
		exampleSkillLabel.setText("Skill (Type) = (Ability Mod) + (Misc Mod) + (Rank) = (Total)" 
					+ "         *: AC penalty  **: double AC penalty");
		exampleSkillLabel.pack();

		// class skill label
		Label classSkillLabel = new Label(wiz4, SWT.NONE);
		classSkillLabel.setLocation(20, 85);
		Color classSkillColor = new Color(dev, 0, 200, 100);
		classSkillLabel.setForeground(classSkillColor);
		classSkillLabel.setText("Class Skills: 1 point = 1 rank");
		classSkillLabel.pack();

		// cross-class skill label
		Label crossClassSkillLabel = new Label(wiz4, SWT.NONE);
		crossClassSkillLabel.setLocation(225, 85);
		Color crossClassSkillColor = new Color(dev, 0, 0, 255);
		crossClassSkillLabel.setForeground(crossClassSkillColor);
		crossClassSkillLabel.setText("Cross-Class Skills: 2 points = 1 rank");
		crossClassSkillLabel.pack();
		
		// untrained label
		Label untrainedLabel = new Label(wiz4, SWT.NONE);
		untrainedLabel.setLocation(470, 85);
		untrainedLabel.setText(Character.toString((char)8226) + " : skill can be used untrained");
		untrainedLabel.pack();

		// get skills from references
		Collection<DNDEntity> skillsCol =  gs.skills.values();
		Iterator<DNDEntity> itr = skillsCol.iterator();
		ArrayList<SkillEntity> skills = new ArrayList<SkillEntity>();
		while (itr.hasNext()) {
			skills.add((SkillEntity) itr.next());
		}
		
		for (int i = 0; i < skills.size(); i++) {
			charSkills.add(new CharSkill(skills.get(i), CharacterWizard.getCharacter()));
		}

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
//		GridData gridData = new GridData();
//		gridData.horizontalAlignment = SWT.LEFT;
		
		// set up scrollable composite
		final ScrolledComposite skillsScreenScroll = new ScrolledComposite(wiz4, SWT.V_SCROLL | SWT.BORDER);
		skillsScreenScroll.setBounds(10, 110, WIDTH - 30, HEIGHT - 210);
	    skillsScreenScroll.setExpandHorizontal(true);
	    skillsScreenScroll.setExpandVertical(true);
	    skillsScreenScroll.setMinSize(WIDTH, (charSkills.size() * 30 + 10));
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
			incGD.widthHint = 30;
			inc.setLayoutData(incGD);
			Button dec = new Button(skillsScreen, SWT.PUSH);
			dec.setText("-");
			GridData decGD = new GridData(SWT.LEFT);
			decGD.widthHint = 30;
			dec.setLayoutData(decGD);
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
					if (current.incRank()) {
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

		// create error label
		unusedSkillPointsError = new Label(wiz4, SWT.NONE);
		unusedSkillPointsError.setVisible(false);
		unusedSkillPointsError.setLocation(200, HEIGHT - 75);
		unusedSkillPointsError.setText("You must use all of your skill points!");
		unusedSkillPointsError.setForeground(new Color(dev, 255,0,0));
		unusedSkillPointsError.pack();
		
		// next button
		Button wiz4NextButton = CharacterWizard.createNextButton(wiz4);
		wiz4NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// make sure all skill points are used
//				if (numSkillPoints > 0) {
//					unusedSkillPointsError.setVisible(true);
//					return;
//				}
//TODO				
				// save to character
				CharacterWizard.getCharacter().setSkills(charSkills);

				// move on to next page
				if (CharacterWizard.wizPageNum < wizPagesSize - 1)
					CharacterWizard.wizPageNum++;
				if (!CharacterWizard.wizPageCreated[4])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});

//		// back button
//		Button wiz4BackButton = CharacterWizard.createBackButton(wiz4, panel, layout);
//		wiz4BackButton.addListener(SWT.Selection, new Listener() {
//			public void handleEvent(Event event) {
//				unusedSkillPointsError.setVisible(false);
//			}
//		});
		
		// cancel button
		Button wiz4CancelButton = CharacterWizard.createCancelButton(wiz4, home, homePanel, homeLayout);
		wiz4CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.cancel)
					cancelClear();
			}
		});
	}

	private void createNextPage() {
		CharacterWizard.wizPageCreated[4] = true;		
		CharacterWizard.wizs.add(new Wiz5(dev, WIDTH, HEIGHT, character, panel, home,
				homePanel, layout, homeLayout, wizPages));
	}

	public Composite getWiz4() { return wiz4; }

	public void cancelClear() {
		CharacterWizard.reset();
		((Wiz1)CharacterWizard.wizs.get(0)).cancelClear();
		((Wiz2)CharacterWizard.wizs.get(1)).cancelClear();
		((Wiz3)CharacterWizard.wizs.get(2)).cancelClear();
		unusedSkillPointsError.setVisible(false);
	}

}
