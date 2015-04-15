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
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Listener;

import core.GameState;
import core.character;

public class Wiz4 {

	private Composite wiz4;
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

	private Text nameInput;
	private CCombo alignmentInput1;
	private CCombo alignmentInput2;
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

	private int numBonusLangs;

	private Random rng = new Random();

	private String charClass;
	private String charRace;

	private boolean goOn;
	private boolean finished;

	private final Color red = new Color(dev, 255, 100, 100);
	private final Color white = new Color(dev, 255, 255, 255);


	public Wiz4(CharacterWizard cw, Device dev, int WIDTH, int HEIGHT, 
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		numBonusLangs = cw.getCharacter().getAbilityModifiers()[GameState.INTELLIGENCE];
		wiz4 = wizPages.get(3);
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
		this.nextPage = wizPages.get(4);
		this.wizPagesSize = wizPages.size();

		charClass = cw.getCharacter().getCharClass().getName();
		charRace = cw.getCharacter().getCharRace().getName();

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

		alignmentInput1 = new CCombo(wiz4, SWT.DROP_DOWN | SWT.READ_ONLY);
		alignmentInput1.add("Lawful");
		alignmentInput1.add("Neutral");
		alignmentInput1.add("Chaotic");
		alignmentInput1.setLocation(85, 100);
		alignmentInput1.pack();
		alignmentInput1.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event e) {
				alignmentInput1.setBackground(null);
			}
		});
		

		alignmentInput2 = new CCombo(wiz4, SWT.DROP_DOWN | SWT.READ_ONLY);
		alignmentInput2.add("Good");
		alignmentInput2.add("Neutral");
		alignmentInput2.add("Evil");
		alignmentInput2.setLocation(180, 100);
		alignmentInput2.pack();
		alignmentInput2.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event e) {
				alignmentInput2.setBackground(null);
			}
		});
		

		// deity
		String[] deities = { 
				"Boccob(N): god of magic", 
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
		deityInput.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event e) {
				deityInput.setBackground(null);
			}
		});
		deityInput.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				deityInput.setBackground(null);
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
		Button wiz4NextButton = cw.createNextButton(wiz4);
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
				if (charClass.equalsIgnoreCase("cleric")) {
					if (deityInput.getText().length() == 0) {
						deityInput.setBackground(red);
						error = true;
					}
					if (alignmentInput1.getSelectionIndex() == -1) {
						alignmentInput1.setBackground(red);
						error = true;
					}
					if (alignmentInput2.getSelectionIndex() == -1) {
						alignmentInput2.setBackground(red);
						error = true;
					}
				}
				if (error)
					return;

				String a1, a2;
				if (alignmentInput1.getSelectionIndex() == -1)
					a1 = "<empty>";
				else 
					a1 = alignmentInput1.getText();
				if (alignmentInput2.getSelectionIndex() == -1)
					a2 = "<empty>";
				else
					a2 = alignmentInput2.getText();
				String deitySelect;
				if (deityListInput.getSelectionIndex() != -1)
					deitySelect = deityListInput.getItem(deityListInput.getSelectionIndex());
				else
					deitySelect = "";

				boolean done = true;
				done = checkAlignmentPopUp(a1, a2, deitySelect);
				if (done) {
					if (charClass.equalsIgnoreCase("cleric"))
						done = clericPopUp(deityInput.getText());
				}
				if (!done)
					return;
				// if no errors, save to character
				//name, alignment, deity, height, weight, age, gender, eyes, hair, skin, description, languages
				character.setName(nameInput.getText());	

				character.setAlignment(a1 + " " + a2);
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
				if (cw.wizPageNum < wizPagesSize - 1)
					cw.wizPageNum++;
				if (!cw.wizPageCreated[4])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});


		// back button
		//Button wiz4BackButton = cw.createBackButton(wiz6, panel, layout);


		// cancel button
		Button wiz4CancelButton = cw.createCancelButton(wiz4, home, homePanel, homeLayout);
		wiz4CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (cw.cancel)
					cw.reset();
			}
		});
	}

	private boolean checkAlignmentPopUp(String a1, String a2, String deity) {
		if (a1.equals("<empty>") || a2.equals("<empty>"))
			return true;

		goOn = false;

		// create shell
		Display display = wiz4.getDisplay();
		final Shell alignmentShell = new Shell(display);
		alignmentShell.setText("Check Alignment");
		GridLayout gridLayout = new GridLayout(2, true);
		alignmentShell.setLayout(gridLayout);
		alignmentShell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				event.doit = false;
				return;
			}
		});

		// warning label
		Label warning = new Label(alignmentShell, SWT.WRAP);
		GridData warningGD = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		warningGD.horizontalSpan = 2;
		warning.setLayoutData(warningGD);


		switch(charClass) {
		case("Barbarian"): 
		{
			// must be non-lawful
			if (!a1.equalsIgnoreCase("lawful"))
				return true;
			warning.setText("Barbarians should be non-lawful.");
			break;
		}
		case("Bard"): 
		{
			// must be non-lawful
			if (!a1.equalsIgnoreCase("lawful"))
				return true;
			warning.setText("Bards should be non-lawful.");
			break;
		}
		case("Cleric"): 
		{
			// cleric's alignment must be within 1 step of deit
			if (a1.equals("<empty>") || a2.equals("<empty>"))
				return true;
			String deityAlignment = deity.substring(deity.indexOf('(')+1, deity.indexOf(')'));
			deityAlignment = deityAlignment.toLowerCase();
			char d1 = deityAlignment.charAt(0);
			char d2;
			if (deityAlignment.length() == 1)
				d2 = deityAlignment.charAt(0);
			else 
				d2 = deityAlignment.charAt(1);
			char c1 = Character.toLowerCase(a1.charAt(0));
			char c2 = Character.toLowerCase(a2.charAt(0));

			int step = 0;

			if (d1 == c1){
				if (d2 == c2); // no step difference
				else {
					if (d2 == 'n' || c2 == 'n')
						step++;
					else 
						step += 2;
				}
			} else {
				if (d1 == 'n' || c1 == 'n')
					step++;
				else 
					step += 2;
				if (d2 == c2); // no step difference
				else if (d2 == 'n' || c2 == 'n')
					step ++;
				else 
					step += 2;
			}
			if (step <= 1)
				return true;
			warning.setText("There should only be one step difference between the deity's alignment and the cleric's alignment.");
			break;
		}
		case("Druid"): 
		{
			// must have at lease one neutral
			if (a1.equalsIgnoreCase("neutral") | a2.equalsIgnoreCase("neutral"))
				return true;
			warning.setText("Druids should have at least one neutral alignment.");
			break;
		}
		case("Monk"): 
		{
			// must be lawful
			if (a1.equalsIgnoreCase("lawful"))
				return true;
			warning.setText("Monks should be lawful.");
			break;
		}
		case("Paladin"): 
		{
			// must be lawful good
			if (a1.equalsIgnoreCase("lawful") && a2.equalsIgnoreCase("good"))
				return true;
			warning.setText("Paladins should be lawful good.");
			break;
		}
		case("Fighter"): 
		case("Ranger"): 
		case("Rogue"): 
		case("Sorcerer"): 
		default: // wizard
			// no alignment restrictions
			return true;
		}
		warning.pack();

		// display user's alignment choice
		Label userChoice = new Label(alignmentShell, SWT.NONE);
		userChoice.setText("You chose: " + a1 + " " + a2);
		GridData userChoiceGD = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		userChoiceGD.horizontalSpan = 2;
		userChoice.setLayoutData(userChoiceGD);
		userChoice.pack();

		// label - do you want to continue
		Label continueLabel = new Label(alignmentShell, SWT.WRAP);
		continueLabel.setText("Do you want to continue with this alignment?");
		GridData continueGD = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		continueGD.horizontalSpan = 2;
		continueLabel.setLayoutData(continueGD);
		continueLabel.pack();

		// no button
		Button no = new Button(alignmentShell, SWT.PUSH);
		no.setText("No");
		no.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		no.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				goOn = false;
				alignmentShell.dispose();
			}
		});
		no.pack();

		// yes button
		Button yes = new Button(alignmentShell, SWT.PUSH);
		yes.setText("Yes");
		yes.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		yes.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				goOn = true;
				alignmentShell.dispose();
			}
		});
		yes.pack();

		// open shell
		alignmentShell.pack();
		CharacterWizard.center(alignmentShell);
		alignmentShell.open();

		// check if disposed
		while (!alignmentShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		return goOn;
	}

	private boolean clericPopUp(String deity) {
		// choose domain based on deity select
		finished = false;

		// create shell
		Display display = wiz4.getDisplay();
		final Shell clericShell = new Shell(display);
		clericShell.setText("Set Domains");
		GridLayout gridLayout = new GridLayout(2, true);
		clericShell.setLayout(gridLayout);
		clericShell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				event.doit = false;
				return;
			}
		});
		// label - do you want to continue
		Label domainsLabel = new Label(clericShell, SWT.WRAP);
		domainsLabel.setText("Select Two Domains");
		GridData continueGD = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		continueGD.horizontalSpan = 2;
		domainsLabel.setLayoutData(continueGD);
		domainsLabel.pack();

		CCombo domains1 = new CCombo(clericShell, SWT.DROP_DOWN);
		domains1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		CCombo domains2 = new CCombo(clericShell, SWT.DROP_DOWN);
		domains2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		domains2.setEnabled(false);

		ArrayList<String> domains = new ArrayList<String>();

		switch(deity) {
		case("Boccob"): {
			domains.add("Knowledge");
			domains.add("Magic");
			domains.add("Trickery");
			break;
		}
		case("Corellon Larethian"): {
			domains.add("Chaos");
			domains.add("Good");
			domains.add("Protection");
			domains.add("War");
			break;
		}
		case("Ehlonna"): {
			domains.add("Animal");
			domains.add("Good");
			domains.add("Plant");
			domains.add("Sun");
			break;
		}
		case("Erythnul"): {
			domains.add("Chaos");
			domains.add("Evil");
			domains.add("Trickery");
			domains.add("War");
			break;
		}
		case("Fharlanghn"): {
			domains.add("Luck");
			domains.add("Protection");
			domains.add("Travel");
			break;
		}
		case("Garl Glittergold"): {
			domains.add("Good");
			domains.add("Protection");
			domains.add("Trickery");
			break;
		}
		case("Gruumsh"): {
			domains.add("Chaos");
			domains.add("Evil");
			domains.add("Strength");
			domains.add("War");
			break;
		}
		case("Heironeous"): {
			domains.add("Good");
			domains.add("Law");
			domains.add("War");
			break;
		}
		case("Hextor"): {
			domains.add("Destruction");
			domains.add("Evil");
			domains.add("Law");
			domains.add("War");
			break;
		}
		case("Kord"): {
			domains.add("Chaos");
			domains.add("Good");
			domains.add("Luck");
			domains.add("Strength");
			break;
		}
		case("Moradin"): {
			domains.add("Earth");
			domains.add("Good");
			domains.add("Law");
			domains.add("Protection");
			break;
		}
		case("Nerull"): {
			domains.add("Death");
			domains.add("Evil");
			domains.add("Trickery");
			break;
		}
		case("Obad-Hai"): {
			domains.add("Air");
			domains.add("Animal");
			domains.add("Earth");
			domains.add("Fire");
			domains.add("Plant");
			domains.add("Water");
			break;
		}
		case("Olidammara"): {
			domains.add("Chaos");
			domains.add("Luck");
			domains.add("Trickery");
			break;
		}
		case("Pelor"): {
			domains.add("Good");
			domains.add("Healing");
			domains.add("Strength");
			domains.add("Sun");
			break;
		}
		case("St.Cuthbert"): {
			domains.add("Destruction");
			domains.add("Law");
			domains.add("Protection");
			domains.add("Strength");
			break;
		}
		case("Vecna"): {
			domains.add("Evil");
			domains.add("Knowledge");
			domains.add("Magic");
			break;
		}
		case("Wee Jas"): {
			domains.add("Death");
			domains.add("Law");
			domains.add("Magic");
			break;
		}
		case("Yondalla"): {
			domains.add("Good");
			domains.add("Law");
			domains.add("Protection");
			break;
		}
		default: { 
			// anything else - can select from any domains
			domains.add("Air");
			domains.add("Animal");
			domains.add("Chaos");
			domains.add("Death");
			domains.add("Destruction");
			domains.add("Earth");
			domains.add("Evil");
			domains.add("Fire");
			domains.add("Good");
			domains.add("Healing");
			domains.add("Knowledge");
			domains.add("Law");
			domains.add("Luck");
			domains.add("Magic");
			domains.add("Plant");
			domains.add("Protection");
			domains.add("Strength");
			domains.add("Sun");
			domains.add("Travel");
			domains.add("Trickery");
			domains.add("War");
			domains.add("Water");
			break;
		}
		}

		for(int i = 0; i < domains.size(); i++) {
			domains1.add(domains.get(i));
		}
		domains1.pack();

		// set listeners
		domains1.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				domains1.setBackground(null);
				domains2.setBackground(null);
				domains2.removeAll();
				for(int i = 0; i < domains.size(); i++) {
					if(!domains1.getItem(domains1.getSelectionIndex()).equals(domains.get(i))){
						domains2.add(domains.get(i));
					}
				}
				domains2.setEnabled(true);
				domains2.pack();
			}
		});
		
		domains2.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				domains2.setBackground(null);
			}
		});


		// cancel button
		Button cancel = new Button(clericShell, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		cancel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				finished = false;
				clericShell.dispose();
			}
		});
		cancel.pack();

		// done button
		Button done = new Button(clericShell, SWT.PUSH);
		done.setText("Done");
		done.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		done.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean error = false;
				if (domains1.getSelectionIndex() == -1) {
					domains1.setBackground(red);
					error = true;
				}
				if (domains2.getSelectionIndex() == -1) {
					domains2.setBackground(red);
					error = true;
				}
				if (error) 
					return;
				finished = true;
				clericShell.dispose();
			}
		});
		done.pack();

		// open shell
		clericShell.pack();
		clericShell.layout();
		CharacterWizard.center(clericShell);
		clericShell.open();

		// check if disposed
		while (!clericShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		return finished;
	}

	private void createNextPage() {
		cw.wizPageCreated[4] = true;
		cw.wizs.add(new Wiz5(cw, dev, WIDTH, HEIGHT, panel, home,
				homePanel, layout, homeLayout, wizPages));
	}

	public Composite getWiz6() { return wiz4; }
}
