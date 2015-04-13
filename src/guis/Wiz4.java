/*
 * ADD DESCRIPTION
 */

/*
 * TODO 
 * barb - non lawful
 * bard - non lawful
 * cleric - align must be within 1 step of deity's align, must match domain (if applicable)
 * druid - must have one neutral, animal companion
 * fighter - none
 * monk - lawful
 * paladin - lawful good!
 * ranger - select favored enemy (p.47)
 * rogue - none
 * sorcerer - none
 * wizard - specialization school, familiar
 */

package guis;
import java.util.ArrayList;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Listener;

import core.GameState;
import core.character;

public class Wiz4 {

	private Composite wiz4;
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

	private Text nameInput;
	private Combo alignmentInput1;
	private Combo alignmentInput2;
	private Text deityInput;
	private Combo deityListInput;
	private boolean deitySelect = false;
	private Text heightInput;
	private Text weightInput;
	private Text ageInput;
	private Text genderInput;
	private Text eyesInput;
	private Text hairInput;
	private Text skinInput;
	private Text descriptionInput;
	private Text langInput;
	private String possibleLangList = "Abyssal, Aquan, Auran, Celestial,"
			+ " Common, Draconic, Druidic, Dwarven, Elven, "
			+ "Giant, Gnome, Goblin, Gnoll, Halfling, Ignan, "
			+ "Infernal, Orc, Sylvan, Terran, Undercommon";

	private int numBonusLangs = CharacterWizard.getCharacter().getAbilityModifiers()[GameState.INTELLIGENCE];

	private Random rng = new Random();

	private String charClass;
	private String charRace;

	private final Color red = new Color(dev, 255, 100, 100);
	private final Color white = new Color(dev, 255, 255, 255);


	public Wiz4(Device dev, int WIDTH, int HEIGHT, 
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz4 = wizPages.get(3);
		this.dev = dev;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.character = CharacterWizard.getCharacter();
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(4);
		this.wizPagesSize = wizPages.size();

		charClass = CharacterWizard.getCharacter().getCharClass().getName();
		charRace = CharacterWizard.getCharacter().getCharRace().getName();

		createPageContent();
	}

	private void createPageContent() {

		Label wiz4Label = new Label(wiz4, SWT.NONE);
		wiz4Label.setText("Add Description");
		wiz4Label.pack();


		// name
		Label name = new Label(wiz4, SWT.NONE);
		name.setText("Name:");
		name.setLocation(5, 50);
		name.pack();

		nameInput = new Text(wiz4, SWT.BORDER);
		nameInput.setBounds(85, 50, 400, 30);
		nameInput.setText("");
		nameInput.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				Text text = (Text) event.widget;
				text.setBackground(white);
			}
		});

		// alignment
		Label alignment = new Label(wiz4, SWT.NONE);
		alignment.setText("Alignment:");
		alignment.setLocation(5, 100);
		alignment.pack();

		alignmentInput1 = new Combo(wiz4, SWT.DROP_DOWN | SWT.READ_ONLY);
		alignmentInput1.add("Lawful");
		alignmentInput1.add("Neutral");
		alignmentInput1.add("Chaotic");
		alignmentInput1.setLocation(85, 100);
		alignmentInput1.pack();

		alignmentInput2 = new Combo(wiz4, SWT.DROP_DOWN | SWT.READ_ONLY);
		alignmentInput2.add("Good");
		alignmentInput2.add("Neutral");
		alignmentInput2.add("Evil");
		alignmentInput2.setLocation(180, 100);
		alignmentInput2.pack();


		// deity
		String[] deities = { "Boccob(N): god of magic", 
				"Corellon Larethian(CG): god of the elves", 
				"Ehlonna(NG): goddess of the woodlands", 
				"Erythnul(CE): god of slaughter", 
				"Fharlanghn(N): god of roads", 
				"Garl Glittergold(NG): god of the gnomes",
				"Gruumsh(CE): chief god of the orcs", 
				"Heironeous(LG): god of valor", 
				"Hextor(LE): god of tyranny",
				"Kord(CG): god of strength",
				"Moradin(LG): god of the dwarves",
				"Nerull(NE): god of death",
				"Obad-Hai(N): god of nature",
				"Olidammara(CN): god of rogues",
				"Pelor(NG): god of the sun",
				"St. Cuthbert(LN): god of retribution", 
				"Vecna(NE): god of secrets", 
				"Wee Jas(LN): goddess of death and magic",
		"Yondalla(LG): goddess of the halflings" };
		final String[] deityNames = {"Boccob", "Corellon Larethian", "Ehlonna", 
				"Erythnul", "Fharlanghn", "Garl Glittergold", "Gruumsh", 
				"Heironeous", "Hextor", "Kord", "Moradin", "Nerull", 
				"Obad-Hai", "Olidammara", "Pelor", "St.Cuthbert", "Vecna", 
				"Wee Jas", "Yondalla" };

		Label deity = new Label(wiz4, SWT.NONE);
		deity.setText("Deity:");
		deity.setLocation(5, 150);
		deity.pack();

		deityListInput = new Combo(wiz4, SWT.DROP_DOWN | SWT.READ_ONLY);
		deityListInput.setBounds(85, 150, 310, 30);
		deityListInput.add("");
		for (int i = 0; i < deities.length; i++) {
			deityListInput.add(deities[i]);
		}

		deityInput = new Text(wiz4, SWT.BORDER);
		deityInput.setBounds(400, 150, 180, 30);
		deityInput.setText("");
		deityInput.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				if (!deitySelect)
					deityListInput.deselectAll();
				deitySelect = false;
			}
		});

		deityListInput.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				deitySelect = true;
				if (deityListInput.getSelectionIndex() == 0)
					deityInput.setText("");
				else 
					deityInput.setText(deityNames[deityListInput.getSelectionIndex()-1]);
			}
		});


		// height
		Label height = new Label(wiz4, SWT.NONE);
		height.setText("Height:");
		height.setLocation(5, 200);
		height.pack();

		heightInput = new Text(wiz4, SWT.BORDER);
		heightInput.setLocation(85, 200);
		heightInput.setText("");
		heightInput.pack();

		Button heightRandom = new Button(wiz4, SWT.PUSH);
		heightRandom.setLocation(165, 200);
		heightRandom.setText("Random Height");
		heightRandom.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int height = 0;
				int min = 0;
				int max = 0;
				switch (charRace) {

				case ("Dwarf"):
					min = 45;
				max = 53;
				height = rng.nextInt(max - min) + min + 1;
				break;
				case ("Elf"):
					min = 55;
				max = 65;
				height = rng.nextInt(max - min) + min + 1;
				break;
				case ("Gnome"): 
					min = 36;
				max = 44;
				height = rng.nextInt(max - min) + min + 1;
				break;
				case ("Half-elf"):
					min = 55;
				max = 71;
				height = rng.nextInt(max - min) + min + 1;
				break;
				case ("Half-orc"):
					min = 55;
				max = 82;
				height = rng.nextInt(max - min) + min + 1;
				break;
				case ("Halfling"):
					min = 32;
				max = 40;
				height = rng.nextInt(max - min) + min + 1;
				break;
				default:
					// human
					min = 55;
					max = 78;
					height = rng.nextInt(max - min) + min + 1;
					break;
				}
				String heightString = "";
				heightString += Integer.toString(height/12);
				heightString += "'";
				heightString += Integer.toString(height % 12);
				heightString += "\"";
				heightInput.setText(heightString);
			}
		});
		heightRandom.pack();


		// weight
		Label weight = new Label(wiz4, SWT.NONE);
		weight.setText("Weight:");
		weight.setLocation(5, 250);
		weight.pack();

		weightInput = new Text(wiz4, SWT.BORDER);
		weightInput.setLocation(85, 250);
		weightInput.setText("");
		weightInput.pack();

		Button weightRandom = new Button(wiz4, SWT.PUSH);
		weightRandom.setLocation(165, 250);
		weightRandom.setText("Random Weight");
		weightRandom.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int weight = 0;
				int min = 0;
				int max = 0;
				switch (charRace) {

				case ("Dwarf"):
					min = 85;
				max = 230;
				weight = rng.nextInt(max - min) + min + 1;
				break;
				case ("Elf"):
					min = 80;
				max = 160;
				weight = rng.nextInt(max - min) + min + 1;
				break;
				case ("Gnome"): 
					min = 35;
				max = 50;
				weight = rng.nextInt(max - min) + min + 1;
				break;
				case ("Half-elf"):
					min = 80;
				max = 230;
				weight = rng.nextInt(max - min) + min + 1;
				break;
				case ("Half-orc"):
					min = 110;
				max = 440;
				weight = rng.nextInt(max - min) + min + 1;
				break;
				case ("Halfling"):
					min = 25;
				max = 40;
				weight = rng.nextInt(max - min) + min + 1;
				break;
				default:
					// human
					min = 125;
					max = 280;
					weight = rng.nextInt(max - min) + min + 1;
					break;
				}
				String weightString = Integer.toString(weight)+ " lbs";
				weightInput.setText(weightString);
			}
		});
		weightRandom.pack();


		// age
		Label age = new Label(wiz4, SWT.NONE);
		age.setText("Age:");
		age.setLocation(435, 100);
		age.pack();

		ageInput = new Text(wiz4, SWT.BORDER);
		ageInput.setBounds(475, 100, 50, 30);
		ageInput.setText("");


		// gender
		Label gender = new Label(wiz4, SWT.NONE);
		gender.setText("Gender:");
		gender.setLocation(290, 100);
		gender.pack();

		ageInput.pack();
		genderInput = new Text(wiz4, SWT.BORDER);
		genderInput.setBounds(355, 100, 50, 30);
		genderInput.setText("");


		// eyes
		Label eyes = new Label(wiz4, SWT.NONE);
		eyes.setText("Eyes:");
		eyes.setLocation(290,200);
		eyes.pack();

		eyesInput = new Text(wiz4, SWT.BORDER);
		eyesInput.setBounds(330, 200, 75, 30);
		eyesInput.setText("");


		// hair 
		Label hair = new Label(wiz4, SWT.NONE);
		hair.setText("Hair:");
		hair.setLocation(430, 200);
		hair.pack();

		hairInput = new Text(wiz4, SWT.BORDER);
		hairInput.setBounds(470, 200, 75, 30);
		hairInput.setText("");


		// skin
		Label skin = new Label(wiz4, SWT.NONE);
		skin.setText("Skin:");
		skin.setLocation(565, 200);
		skin.pack();

		skinInput = new Text(wiz4, SWT.BORDER);
		skinInput.setBounds(605,200,75,30);
		skinInput.setText("");


		// description
		Label description = new Label(wiz4, SWT.NONE);
		description.setText("Description:");
		description.setLocation(290,250);
		description.pack();

		descriptionInput = new Text(wiz4, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		descriptionInput.setBounds(380,250, 300, 90);
		descriptionInput.setText("");

		// languages
		Label languages = new Label(wiz4, SWT.NONE);
		languages.setText("Languages:");
		languages.setLocation(5, 300);
		languages.pack();

		langInput = new Text(wiz4, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		langInput.setBounds(85, 300, 285, 40);
		langInput.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				Text text = (Text) event.widget;
				text.setBackground(white);
			}
		});

		Label addLang = new Label(wiz4, SWT.NONE);		
		if (numBonusLangs < 0)
			numBonusLangs = 0;
		addLang.setText("Pick " + Integer.toString(numBonusLangs) + " More:");
		addLang.setLocation(5,350);
		addLang.pack();


		String langList = "Common";
		switch(charRace) {
		case ("Dwarf"):
			langList += ", Dwarven";
		break;
		case ("Elf"):
			langList += ", Elven";
		break;
		case ("Gnome"):
			langList += ", Gnome";
		break;
		case ("Half-Elf"):
			langList += ", Elven";
		break;
		case ("Half-Orc"):
			langList += ", Orc";
		break;
		case ("Halfling"):
			langList += ", Halfling";
		break;
		default : // human
			break;
		}
		langInput.setText(langList);

		Label possibleLangs = new Label(wiz4, SWT.WRAP);
		possibleLangs.setText(possibleLangList);
		possibleLangs.setBounds(95,350,580,40);


		// next button
		Button wiz4NextButton = CharacterWizard.createNextButton(wiz4);
		wiz4NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// error checking
				boolean error = false;
				// checks if name is the empty string or comprised of only whitespace/non-alphanumeric characters
				String condensed = nameInput.getText().replaceAll("\\s","");
				condensed = condensed.replaceAll("[^A-Za-z0-9]", "");
				if (condensed.length() == 0) {
					nameInput.setText("");
					nameInput.setBackground(red);
					error = true;
				}
				if (langInput.getText().length() == 0){
					langInput.setBackground(red);
					error = true;
				}
				if (error)
					return;

				// if no errors, save to character
				//name, alignment, deity, height, weight, age, gender, eyes, hair, skin, description, languages
				character.setName(nameInput.getText());	
				String a1, a2;
				if (alignmentInput1.getSelectionIndex() == -1)
					a1 = "<empty>";
				else 
					a1 = alignmentInput1.getText() + " ";
				if (alignmentInput2.getSelectionIndex() == -1)
					a2 = "<empty>";
				else
					a2 = alignmentInput2.getText();
				character.setAlignment(a1 + a2);
				if (deityInput.getText().length() != 0)
					character.setDeity(deityInput.getText());
				if (heightInput.getText().length() != 0)
					character.setHeight(heightInput.getText());
				if (weightInput.getText().length() != 0)
					character.setWeight(weightInput.getText());
				if (ageInput.getText().length() != 0)
					character.setAge(ageInput.getText());
				if (genderInput.getText().length() != 0)
					character.setGender(genderInput.getText());
				if (eyesInput.getText().length() != 0)
					character.setEyes(eyesInput.getText());
				if (hairInput.getText().length() != 0)
					character.setHair(hairInput.getText());
				if (skinInput.getText().length() != 0)
					character.setSkin(skinInput.getText());
				if (descriptionInput.getText().length() != 0)
					character.setDescription(descriptionInput.getText());
				character.setLanguages(langInput.getText());

				// change to next page				
				if (CharacterWizard.wizPageNum < wizPagesSize - 1)
					CharacterWizard.wizPageNum++;
				if (!CharacterWizard.wizPageCreated[6])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});


		// back button
		//Button wiz4BackButton = CharacterWizard.createBackButton(wiz6, panel, layout);


		// cancel button
		Button wiz4CancelButton = CharacterWizard.createCancelButton(wiz4, home, homePanel, homeLayout);
		wiz4CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.cancel)
					CharacterWizard.reset();
			}
		});
	}

	private void createNextPage() {
		CharacterWizard.wizPageCreated[4] = true;
		CharacterWizard.wizs.add(new Wiz5(dev, WIDTH, HEIGHT, panel, home,
				homePanel, layout, homeLayout, wizPages));
	}

	public Composite getWiz6() { return wiz4; }
}
