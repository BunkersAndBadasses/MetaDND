package guis;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import core.Character;


public class Wiz4 {

	private static Composite wiz4;
	private static Device dev;
	private static int WIDTH;
	private static int HEIGHT;
	private static Character character;
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
	
	
	public Wiz4(Device dev, int WIDTH, int HEIGHT, final Character character, 
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
		String charClass = CharacterWizard.character.getCharClass();
		int classPoints = 8; // TODO - different for each class
		int intMod = ((CharacterWizard.character.getAbilityScores()[Character.INTELLIGENCE]) - 8)/2; // TODO check this logic
		numSkillPoints = (classPoints + intMod) * 4;
		if (numSkillPoints < 4) 
			numSkillPoints = 4;
		if (CharacterWizard.character.getCharRace().equals("Human"))
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

		// example skill label ■ (Name)(ability type): =(ability modifier)+(misc modifier)+(rank) = (total)​
		// TODO add symbol for untrained/trained
		Label exampleSkillLabel = new Label(wiz4, SWT.NONE);
		exampleSkillLabel.setLocation(35, 60);
		exampleSkillLabel.setText("(Skill Name)[(Ability Type)] = (Ability Modifier) + (Miscellaneous Modifier) + (Rank) = (Total)");
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
		untrainedLabel.setText("■ : skill can be used untrained");
		untrainedLabel.pack();

		// TODO get skills 
		Skill[] tempSkills =
			{new Skill("skill1", "blah", 0, true), new Skill("skill2", "blah", 1, true), 
				new Skill("skill3", "blah", 2, true), new Skill("skill4", "blah", 3, true), 
				new Skill("skill5", "blah", 4, false), new Skill("skill6", "blah", 5, false), 
				new Skill("skill7", "blah", 0, true), new Skill("skill8", "blah", 3, false), 
				new Skill("skill9", "blah", 2, false), new Skill("skill10", "blah", 5, true)};

		for (int i = 0; i < tempSkills.length; i++) {
			charSkills.add(new CharSkill(tempSkills[i], CharacterWizard.character));
		}

		final Composite skillsScreen = new Composite(wiz4, SWT.BORDER
				| SWT.SCROLL_PAGE); // TODO scroll bar?
		skillsScreen.setBounds(10, 110, WIDTH - 30, HEIGHT - 210);

		ArrayList<Label> skillNameLabels = new ArrayList<Label>();
		ArrayList<Button> incButtons = new ArrayList<Button>();
		ArrayList<Button> decButtons = new ArrayList<Button>();

		for(int i = 0; i < charSkills.size(); i++) {
			final Label skillName = new Label(skillsScreen, SWT.NONE);
			skillName.setLocation(70, (i*30) + 10);
			final CharSkill current = charSkills.get(i);
			final int abilityMod = current.getAbilityMod();
			final int miscMod = current.getMiscMod();
			final String untrained;
			if (current.getSkill().canUseUntrained())
				untrained = "■";
			else 
				untrained = "    ";
			skillName.setText(untrained + current.getSkill().getName() + " (" 
					+ current.getAbilityType() + ") = " + abilityMod + " + " 
					+ miscMod + " + " + current.getRank() + " = " + current.getTotal());
			if (current.isClassSkill())
				skillName.setForeground(classSkillColor);
			else
				skillName.setForeground(crossClassSkillColor);
			skillName.pack();
			skillNameLabels.add(skillName);
			Button inc = new Button(skillsScreen, SWT.PUSH);
			inc.setText("+");
			inc.setBounds(10, (i*30) + 10, 20, 20);
			inc.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					if (numSkillPoints == 0)
						return;
					if (current.incRank()) {
						skillName.setText(untrained + current.getSkill().getName() + " (" 
								+ current.getAbilityType() + ") = " 
								+ abilityMod + " + " + miscMod + " + " 
								+ current.getRank() + " = " + current.getTotal());
						skillName.pack();
						numSkillPoints--;
						numSkillPointsLabel.setText(Integer.toString(numSkillPoints));
						numSkillPointsLabel.pack();
						unusedSkillPointsError.setVisible(false);
					}
				}
			});
			incButtons.add(inc);
			Button dec = new Button(skillsScreen, SWT.PUSH);
			dec.setText("-");
			dec.setBounds(30, (i*30) + 10, 20, 20);
			dec.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					if (current.decRank()) {
						skillName.setText(untrained + current.getSkill().getName() + " (" 
								+ current.getAbilityType() + ") = " + abilityMod 
								+ " + " + miscMod + " + " + current.getRank() 
								+ " = " + current.getTotal());
						skillName.pack();
						numSkillPoints++;
						numSkillPointsLabel.setText(Integer.toString(numSkillPoints));
						numSkillPointsLabel.pack();
						unusedSkillPointsError.setVisible(false);
					}
				}
			});
			decButtons.add(dec);
		}


		unusedSkillPointsError = new Label(wiz4, SWT.NONE);
		unusedSkillPointsError.setVisible(false);
		unusedSkillPointsError.setLocation(200, HEIGHT - 75);
		unusedSkillPointsError.setText("You must use all of your skill points!");
		unusedSkillPointsError.setForeground(new Color(dev, 255,0,0));
		unusedSkillPointsError.pack();
		
		
		
		



		Button wiz4NextButton = CharacterWizard.createNextButton(wiz4);
		wiz4NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// make sure all skill points are used
//				if (numSkillPoints > 0) {
//					unusedSkillPointsError.setVisible(true);
//					return;
//				} // TODO uncomment when done testing
				
				// save to character
				CharacterWizard.character.setSkills(charSkills);

				// move on to next page
				if (CharacterWizard.wizPageNum < wizPagesSize - 1)
					CharacterWizard.wizPageNum++;
				if (!CharacterWizard.wizPageCreated[4])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});

		Button wiz4BackButton = CharacterWizard.createBackButton(wiz4, panel, layout);
		wiz4BackButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				unusedSkillPointsError.setVisible(false);
			}
		});
		
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
		new Wiz5(dev, WIDTH, HEIGHT, character, panel, home,
				homePanel, layout, homeLayout, wizPages);
	}

	public Composite getWiz4() { return wiz4; }

	public static void cancelClear() {
		CharacterWizard.character = new Character();
		Wiz1.cancelClear();
		Wiz2.cancelClear();
		Wiz3.cancelClear();
		unusedSkillPointsError.setVisible(false);
	}

}
