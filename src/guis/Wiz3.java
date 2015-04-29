/*
 * APPLY ABILITY SCORES
 */

package guis;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;

import core.GameState;
import core.character;
import entity.ClassEntity;
import entity.RaceEntity;

public class Wiz3 {

	private Composite wiz3;
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
	
	private List strList;
	private List dexList;
	private List conList;
	private List intList;
	private List wisList;
	private List chaList;

	ArrayList<ArrayList<Button>> buttons;
	
	private int[] abilityScoresBefore;
	private int[] abilityScoresAfter = new int[6];
	private Label errorLabel;
	private RaceEntity charRace;
	private ClassEntity charClass;
	//private ClassEntity charSecClass;
	//private Label choiceLabel2;

	public Wiz3(CharacterWizard cw, Device dev, int WIDTH, int HEIGHT, 
			final Composite panel, final StackLayout layout, 
			final ArrayList<Composite> wizPages, int[] abilityScoresIn) {
		// initialization
		wiz3 = wizPages.get(2);
		this.cw = cw;
		this.dev = dev;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.character = cw.getCharacter();
		this.panel = panel;
		this.layout = layout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(3);
		this.wizPagesSize = wizPages.size();
		abilityScoresBefore = abilityScoresIn;
		//choiceLabel2 = new Label(wiz3, SWT.NONE);
		charRace = cw.getCharacter().getCharRace();
		charClass = cw.getCharacter().getCharClass();
		
		buttons = new ArrayList<ArrayList<Button>>(6);

		createPageContent();
	}

	private void createPageContent() {
		Label wiz3Label = new Label(wiz3, SWT.NONE);
		wiz3Label.setText("Apply Ability Scores");
		wiz3Label.pack();
		
		////////// instantiate layout //////////
		
		GridLayout gl = new GridLayout(6, true);
		
		Composite inner = new Composite(wiz3, SWT.NONE);
		inner.setBounds(5, 20, WIDTH-10, HEIGHT-110);
		inner.setLayout(gl);

		GridData gd;
		
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 6;
		new Label(inner, SWT.NONE).setLayoutData(gd);
		
		////////// class/race label //////////
		Label choiceLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 6;
		choiceLabel.setLayoutData(gd);
		////////// class/race label //////////

		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 6;
		new Label(inner, SWT.NONE).setLayoutData(gd);
		
		
		////////// strength //////////
		new Label(inner, SWT.NONE).setLayoutData(new GridData());
		
		Label strLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		strLabel.setLayoutData(gd);
		Composite strComp = new Composite(inner, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.horizontalSpan = 3;
		strComp.setLayoutData(gd);
		GridLayout strGL = new GridLayout(6, true);
		strComp.setLayout(strGL);
		buttons.add(createASButtons(strComp));
		strComp.layout();
		
		new Label(inner, SWT.NONE).setLayoutData(new GridData());
		////////// strength //////////
		
		////////// dexterity //////////
		new Label(inner, SWT.NONE).setLayoutData(new GridData());
		
		Label dexLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		dexLabel.setLayoutData(gd);
		Composite dexComp = new Composite(inner, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.horizontalSpan = 3;
		dexComp.setLayoutData(gd);
		GridLayout dexGL = new GridLayout(6, true);
		dexComp.setLayout(dexGL);
		buttons.add(createASButtons(dexComp));
		dexComp.layout();
		
		new Label(inner, SWT.NONE).setLayoutData(new GridData());
		////////// dexterity //////////
		
		////////// constitution //////////
		new Label(inner, SWT.NONE).setLayoutData(new GridData());
		
		Label conLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		conLabel.setLayoutData(gd);
		Composite conComp = new Composite(inner, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.horizontalSpan = 3;
		conComp.setLayoutData(gd);
		GridLayout conGL = new GridLayout(6, true);
		conComp.setLayout(conGL);
		buttons.add(createASButtons(conComp));
		conComp.layout();
		
		new Label(inner, SWT.NONE).setLayoutData(new GridData());
		////////// constitution //////////
		
		////////// intelligence //////////
		new Label(inner, SWT.NONE).setLayoutData(new GridData());
		
		Label intLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		intLabel.setLayoutData(gd);
		Composite intComp = new Composite(inner, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.horizontalSpan = 3;
		intComp.setLayoutData(gd);
		GridLayout intGL = new GridLayout(6, true);
		intComp.setLayout(intGL);
		buttons.add(createASButtons(intComp));
		intComp.layout();
		
		new Label(inner, SWT.NONE).setLayoutData(new GridData());
		////////// intelligence //////////
		
		////////// wisdom //////////
		new Label(inner, SWT.NONE).setLayoutData(new GridData());
		
		Label wisLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		wisLabel.setLayoutData(gd);
		Composite wisComp = new Composite(inner, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.horizontalSpan = 3;
		wisComp.setLayoutData(gd);
		GridLayout wisGL = new GridLayout(6, true);
		wisComp.setLayout(wisGL);
		buttons.add(createASButtons(wisComp));
		wisComp.layout();
		
		new Label(inner, SWT.NONE).setLayoutData(new GridData());
		////////// wisdom //////////
		
		////////// charisma //////////
		new Label(inner, SWT.NONE).setLayoutData(new GridData());
		
		Label chaLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		chaLabel.setLayoutData(gd);
		Composite chaComp = new Composite(inner, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.horizontalSpan = 3;
		chaComp.setLayoutData(gd);
		GridLayout chaGL = new GridLayout(6, true);
		chaComp.setLayout(chaGL);
		buttons.add(createASButtons(chaComp));
		chaComp.layout();
		
		new Label(inner, SWT.NONE).setLayoutData(new GridData());
		////////// charisma //////////
		
		////////// error //////////
		// error label
		errorLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 6;
		errorLabel.setLayoutData(gd);
		////////// error //////////
		
		////////// instantiate layout //////////


		////////// button listeners //////////
		for (int i = 0; i < buttons.size(); i++) {
			for (int j = 0; j < buttons.get(i).size(); j++) {
				buttons.get(i).get(j).addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event e) {
						errorLabel.setVisible(false);
						int index = 0;
						// find index of selected button, reset empty rows
						for (int j = 0; j < buttons.get(0).size(); j++) {
							boolean all = true;
							for (int i = 0; i < buttons.size(); i++) {
								if (buttons.get(i).get(j).equals(e.widget)) {
									index = j;
								}
								if (buttons.get(i).get(j).getSelection()) 
									all = false;
							}
							if (all) {
								for (int i = 0; i < buttons.size(); i++) {
									buttons.get(i).get(j).setForeground(null);
								}
							}
						}
						// deselect and color red the row of the selected button
						for (int i = 0; i < buttons.size(); i++) {
							if (!buttons.get(i).get(index).equals(e.widget)) {
								buttons.get(i).get(index).setSelection(false);
								buttons.get(i).get(index).setForeground(wiz3.getDisplay().getSystemColor(SWT.COLOR_RED));
							} else {
								buttons.get(i).get(index).setForeground(null);
							}
						}
					}
				});
			}
		}
		////////// button listeners //////////
		
		
		////////// create content //////////

		// labels for user choice of character race and class
		choiceLabel.setText("You chose: " + charRace.getName() + " " + charClass.getName());
		choiceLabel.pack();
		
		// labels for ability scores

		// strength label
		strLabel.setText("Strength");
		strLabel.pack();
		
		// dexterity label
		dexLabel.setText("Dexterity");
		dexLabel.pack();

		// constitution label
		conLabel.setText("Constitution");
		conLabel.pack();

		// intelligence label
		intLabel.setText("Intelligence");
		intLabel.pack();

		// wisdom label
		wisLabel.setText("Wisdom");
		wisLabel.pack();

		// charisma label
		chaLabel.setText("Charisma");
		chaLabel.pack();
		
		// error label
		errorLabel.setForeground(new Color(dev, 255, 0, 0));
		errorLabel.setText("You must select a value for each ability!");
		errorLabel.setVisible(false);
		errorLabel.pack();
		
		// next button
		Button wiz3NextButton = cw.createNextButton(wiz3);
		wiz3NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// error checking - make sure each list has something selected
				boolean error = false;
				{
					int value = 0;
					// find selected button, also checks if no button is selected in that row
					for (int i = 0; i < buttons.get(0).size(); i++) {
						if (buttons.get(0).get(i).getSelection()) {
							value = Integer.parseInt(buttons.get(0).get(i).getText());
						}
					}
					if (value == 0)
						error = true;
					else 
						abilityScoresAfter[0] = value;
				}
				{
					int value = 0;
					// find selected button, also checks if no button is selected in that row
					for (int i = 0; i < buttons.get(1).size(); i++) {
						if (buttons.get(1).get(i).getSelection()) {
							value = Integer.parseInt(buttons.get(1).get(i).getText());
						}
					}
					if (value == 0)
						error = true;
					else 
						abilityScoresAfter[1] = value;
				}
				{
					int value = 0;
					// find selected button, also checks if no button is selected in that row
					for (int i = 0; i < buttons.get(2).size(); i++) {
						if (buttons.get(2).get(i).getSelection()) {
							value = Integer.parseInt(buttons.get(2).get(i).getText());
						}
					}
					if (value == 0)
						error = true;
					else 
						abilityScoresAfter[2] = value;
				}
				{
					int value = 0;
					// find selected button, also checks if no button is selected in that row
					for (int i = 0; i < buttons.get(3).size(); i++) {
						if (buttons.get(3).get(i).getSelection()) {
							value = Integer.parseInt(buttons.get(3).get(i).getText());
						}
					}
					if (value == 0)
						error = true;
					else 
						abilityScoresAfter[3] = value;
				}
				{
					int value = 0;
					// find selected button, also checks if no button is selected in that row
					for (int i = 0; i < buttons.get(4).size(); i++) {
						if (buttons.get(4).get(i).getSelection()) {
							value = Integer.parseInt(buttons.get(4).get(i).getText());
						}
					}
					if (value == 0)
						error = true;
					else 
						abilityScoresAfter[4] = value;
				}
				{
					int value = 0;
					// find selected button, also checks if no button is selected in that row
					for (int i = 0; i < buttons.get(5).size(); i++) {
						if (buttons.get(5).get(i).getSelection()) {
							value = Integer.parseInt(buttons.get(5).get(i).getText());
						}
					}
					if (value == 0)
						error = true;
					else 
						abilityScoresAfter[5] = value;
				}
				
				if (error) {
					errorLabel.setVisible(true);
					return;
				}
				
				// if all is good, save to character
				
				// set ability scores
				int[] racialMods = character.getCharRace().getAbilityAdj();
				character.setAbilityScores(
						abilityScoresAfter[0] + racialMods[0], 
						abilityScoresAfter[1] + racialMods[1], 
						abilityScoresAfter[2] + racialMods[2], 
						abilityScoresAfter[3] + racialMods[3], 
						abilityScoresAfter[4] + racialMods[4], 
						abilityScoresAfter[5] + racialMods[5]);	
				
				// set hitpoints
				int hitDie = Integer.parseInt(character.getCharClass().getHitDie().substring(1));
				int hp = hitDie + character.getAbilityModifiers()[GameState.CONSTITUTION];
				if (hp < 3)
					hp = 3;
				character.setHitPoints(hp);
				
				if (cw.wizPageNum < wizPagesSize - 1)
					cw.wizPageNum++;
				if (!cw.wizPageCreated[3])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});

		// back button
		//Button wiz3BackButton = cw.createBackButton(wiz3, panel, layout);

		// cancel button
		Button wiz3CancelButton = cw.createCancelButton(wiz3);
		wiz3CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (cw.cancel)
					cw.reset();
			}
		});	
		////////// create content //////////
		
		inner.layout();
	}
	
	private void createNextPage() {
		cw.wizPageCreated[3] = true;
		cw.wizs.add(new Wiz4(cw, dev, WIDTH, HEIGHT, panel, layout, wizPages));
	}
	
//	public void updateCharRace() {
//		charRace = cw.getCharacter().getCharRace();
//		String text = charRace.getName() + " " + charClass.getName();
//		choiceLabel2.setText(text);
//		choiceLabel2.pack();
//	}
//	
//	public void updateCharClass() {
//		charClass = cw.getCharacter().getCharClass();
//		choiceLabel2.setText(charRace.getName() + " " + charClass.getName());
//		choiceLabel2.pack();
//	}
//	
//	public void updateCharSecClass() {
//		charSecClass = cw.getCharacter().getCharSecClass();
//		if (charSecClass == null)
//			choiceLabel2.setText(charRace.getName() + " " + charClass.getName());
//		else if (charSecClass.equals(""))
//			choiceLabel2.setText(charRace.getName() + " " + charClass.getName());
//		else
//			choiceLabel2.setText(charRace.getName() + " " + charClass.getName() + "-" + charSecClass.getName());
//		choiceLabel2.pack();
//	}

	private ArrayList<Button> createASButtons(Composite c) {
		ArrayList<Button> buttons = new ArrayList<Button>();
		for (int i = 0; i < abilityScoresBefore.length; i++) {
			GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
			Button button = new Button(c, SWT.RADIO);
			button.setLayoutData(gd);
			button.setText(Integer.toString(abilityScoresBefore[i]));
			buttons.add(button);
		}
		return buttons;
	}
	
	public Composite getWiz3() { return wiz3; }

}
