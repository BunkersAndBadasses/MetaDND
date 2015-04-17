/*
 * ADD DESCRIPTION
 */

package guis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import org.eclipse.swt.layout.RowLayout;
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
import core.Main;
import core.character;
import entity.ClassEntity;
import entity.DNDEntity;
import entity.DeityEntity;

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

	private String domains[] = {"Air", "Animal", "Chaos", "Death", "Destruction", 
			"Earth", "Evil", "Fire", "Good", "Healing", "Knowledge", "Law", 
			"Luck", "Magic", "Plant", "Protection", "Strength", "Sun", 
			"Travel", "Trickery", "War", "Water"};

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

		numBonusLangs = character.getAbilityModifiers()[GameState.INTELLIGENCE];
		charClass = cw.getCharacter().getCharClass().getName();
		charRace = cw.getCharacter().getCharRace().getName();

		createPageContent();
	}

	private void createPageContent() {
		// main label
		Label wiz4Label = new Label(wiz4, SWT.NONE);
		wiz4Label.setText("Add Description");
		wiz4Label.pack();
		
		GridLayout gl = new GridLayout(8, false);
		
		Composite inner = new Composite(wiz4, SWT.BORDER);
		inner.setBounds(5, 20, WIDTH-10, HEIGHT-110);
		inner.setLayout(gl);

		GridData gd;
		
		// initialize layout
		nameInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 4;
		nameInput.setLayoutData(gd);
		
		genderInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		gd.horizontalSpan = 2;
		genderInput.setLayoutData(gd);
		
		ageInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		gd.horizontalSpan = 2;
		ageInput.setLayoutData(gd);
		
		deityListInput = new Combo(inner, SWT.DROP_DOWN | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 5;
		deityListInput.setLayoutData(gd);
		
		deityInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		gd.horizontalSpan = 3;
		deityInput.setLayoutData(gd);
		
		alignmentInput1 = new CCombo(inner, SWT.DROP_DOWN | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		gd.horizontalSpan = 2;
		alignmentInput1.setLayoutData(gd);	
		
		alignmentInput2 = new CCombo(inner, SWT.DROP_DOWN | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		gd.horizontalSpan = 2;
		alignmentInput2.setLayoutData(gd);
		
		eyesInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		eyesInput.setLayoutData(gd);
		
		hairInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		hairInput.setLayoutData(gd);
		
		skinInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		skinInput.setLayoutData(gd);
		
		Label spacer = new Label(inner, SWT.NONE);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		spacer.setLayoutData(gd);
		
		heightInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		heightInput.setLayoutData(gd);
		
		Button heightRandom = new Button(inner, SWT.PUSH);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		gd.horizontalSpan = 2;
		heightRandom.setLayoutData(gd);
		
		weightInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		weightInput.setLayoutData(gd);
		
		Button weightRandom = new Button(inner, SWT.PUSH);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		gd.horizontalSpan = 2;
		weightRandom.setLayoutData(gd);
		
		descriptionInput = new Text(inner, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		
		
		langInput = new Text(inner, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);

		Label addLang = new Label(inner, SWT.NONE);	
		
		Label possibleLangs = new Label(inner, SWT.WRAP);
		
		
		// create content
		// name
		//		Label name = new Label(inner, SWT.NONE);
		//		name.setText("Name:");
		//		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		//		name.setLayoutData(gd);
		//		name.pack();

		nameInput.setText("");
		nameInput.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				Text text = (Text) event.widget;
				text.setBackground(white);
			}
		});
		nameInput.setMessage("Name");
		nameInput.pack();


		// deity

		// get deities from references
		Collection<DNDEntity> deitiesCol = Main.gameState.deities.values();
		Iterator<DNDEntity> itr2 = deitiesCol.iterator();
		ArrayList<DeityEntity> deities = new ArrayList<DeityEntity>();
		while (itr2.hasNext()) {
			deities.add((DeityEntity) itr2.next());
		}

		//		Label deity = new Label(inner, SWT.NONE);
		//		deity.setText("Deity:");
		//		deity.setLocation(5, 150);
		//		deity.pack();

		//		deityListInput.setBounds(85, 150, 310, 30);
		deityListInput.add("<none>");
		for (int i = 0; i < deities.size(); i++) {
			deityListInput.add(deities.get(i).getName() + " (" + deities.get(i).getAlignment() + ")");
		}
		deityListInput.setText("Deity");
		deityListInput.pack();

		//		deityInput.setBounds(400, 150, 180, 30);
		deityInput.setText("");
		deityInput.setMessage("Custom Deity");
		deityInput.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event e) {
				deityInput.setBackground(null);
			}
		});
		deityInput.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				deityInput.setBackground(null);
				if (!deitySelect) {
					deityListInput.deselectAll();
					deityListInput.setText("Deity");
				}
				deitySelect = false;
			}
		});
//		deityInput.pack();

		deityListInput.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				deitySelect = true;
				if (deityListInput.getSelectionIndex() == 0)
					deityInput.setText("");
				else 
					deityInput.setText(deities.get(deityListInput.getSelectionIndex()-1).getName());
			}
		});


		// alignment
		//		Label alignment = new Label(inner, SWT.NONE);
		//		alignment.setText("Alignment:");
		//		alignment.setLocation(5, 100);
		//		alignment.pack();

		alignmentInput1.add("Lawful");
		alignmentInput1.add("Neutral");
		alignmentInput1.add("Chaotic");
		//		alignmentInput1.setLocation(85, 100);
		alignmentInput1.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event e) {
				alignmentInput1.setBackground(null);
			}
		});	
		alignmentInput1.pack();

		alignmentInput2.add("Good");
		alignmentInput2.add("Neutral");
		alignmentInput2.add("Evil");
		//		alignmentInput2.setLocation(180, 100);
		alignmentInput2.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event e) {
				alignmentInput2.setBackground(null);
			}
		});
		alignmentInput2.pack();


		// eyes
//		Label eyes = new Label(inner, SWT.NONE);
//		eyes.setText("Eyes:");
//		eyes.setLocation(290,200);
//		eyes.pack();

//		eyesInput.setBounds(330, 200, 75, 30);
		eyesInput.setText("");
		eyesInput.setMessage("Eyes");
		eyesInput.pack();


		// hair 
//		Label hair = new Label(inner, SWT.NONE);
//		hair.setText("Hair:");
//		hair.setLocation(430, 200);
//		hair.pack();


//		hairInput.setBounds(470, 200, 75, 30);
		hairInput.setText("");
		hairInput.setMessage("Hair");
		hairInput.pack();


		// skin
//		Label skin = new Label(inner, SWT.NONE);
//		skin.setText("Skin:");
//		skin.setLocation(565, 200);
//		skin.pack();


//		skinInput.setBounds(605,200,75,30);
		skinInput.setText("");
		skinInput.setMessage("Skin");
		skinInput.pack();
		
		
		// height
		//		Label height = new Label(inner, SWT.NONE);
		//		height.setText("Height:");
		//		height.setLocation(5, 200);
		//		height.pack();

		//		heightInput.setLocation(85, 200);
		heightInput.setText("");
		heightInput.setMessage("Height");
		heightInput.pack();

		//		heightRandom.setLocation(165, 200);
		heightRandom.setText("Random Height");
		heightRandom.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int height = 0;
				int min = 0;
				int max = 0;
				switch (charRace) {

				case ("Dwarf"): 
				{
					min = 45;
					max = 53;
					height = rng.nextInt(max - min) + min + 1;
					break;
				}
				case ("Elf"):
				{
					min = 55;
					max = 65;
					height = rng.nextInt(max - min) + min + 1;
					break;
				}
				case ("Gnome"): 
				{
					min = 36;
					max = 44;
					height = rng.nextInt(max - min) + min + 1;
					break;
				}
				case ("Half-elf"):
				{
					min = 55;
					max = 71;
					height = rng.nextInt(max - min) + min + 1;
					break;
				}
				case ("Half-orc"):
				{
					min = 55;
					max = 82;
					height = rng.nextInt(max - min) + min + 1;
					break;
				}
				case ("Halfling"):
				{
					min = 32;
					max = 40;
					height = rng.nextInt(max - min) + min + 1;
					break;
				}
				default:
				{
					// human
					min = 55;
					max = 78;
					height = rng.nextInt(max - min) + min + 1;
					break;
				}
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
//		Label weight = new Label(inner, SWT.NONE);
//		weight.setText("Weight:");
//		weight.setLocation(5, 250);
//		weight.pack();


//		weightInput.setLocation(85, 250);
		weightInput.setText("");
		weightInput.setMessage("Weight");
		weightInput.pack();

//		weightRandom.setLocation(165, 250);
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


		// gender
		//		Label gender = new Label(inner, SWT.NONE);
		//		gender.setText("Gender:");
		//		gender.setLocation(290, 100);
		//		gender.pack();

		//		genderInput.setBounds(355, 100, 50, 30);
		genderInput.setText("");
		genderInput.setMessage("Gender");
		genderInput.pack();

		
		// age
		//		Label age = new Label(inner, SWT.NONE);
		//		age.setText("Age:");
		//		age.setLocation(435, 100);
		//		age.pack();

		//		ageInput.setBounds(475, 100, 50, 30);
		ageInput.setText("");
		ageInput.setMessage("Age");
		ageInput.pack();
		
		
		// description
//		Label description = new Label(inner, SWT.NONE);
//		description.setText("Description:");
//		description.setLocation(290,250);
//		description.pack();

//		descriptionInput.setBounds(380,250, 300, 90);
		descriptionInput.setText("");
		descriptionInput.setMessage("Description");
		

//		// languages
//		Label languages = new Label(inner, SWT.NONE);
//		languages.setText("Languages:");
//		languages.setLocation(5, 300);
//		languages.pack();

//		langInput.setBounds(85, 300, 285, 40);
		langInput.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				Text text = (Text) event.widget;
				text.setBackground(white);
			}
		});

	
		if (numBonusLangs < 0)
			numBonusLangs = 0;
		addLang.setText("Pick " + Integer.toString(numBonusLangs) + " More:");
//		addLang.setLocation(5,350);
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

				DeityEntity deitySelect;
				if (deityListInput.getSelectionIndex() != -1)
					deitySelect = deities.get(deityListInput.getSelectionIndex());
				else
					deitySelect = null;

				boolean done = true;
				done = checkAlignmentPopUp(a1, a2, deitySelect);
				if (done) {
					if (charClass.equalsIgnoreCase("cleric"))
						done = clericPopUp(deitySelect);
				}
				if (!done)
					return;
				// if no errors, save to character
				//name, alignment, deity, height, weight, age, gender, eyes, hair, skin, description, languages
				character.setName(nameInput.getText());	
				character.setAlignment(a1 + " " + a2);
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

		inner.layout();
	}

	private boolean checkAlignmentPopUp(String a1, String a2, DeityEntity deity) {
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
			// cleric's alignment must be within 1 step of deity
			if (deity == null)
				return true;
			if (a1.equals("<empty>") || a2.equals("<empty>"))
				return true;
			String[] deityAlignment = deity.getAlignment().split("\\s");
			char d1 = Character.toLowerCase(deityAlignment[0].charAt(0));
			char d2 = Character.toLowerCase(deityAlignment[1].charAt(0));
			// if alignment is true neutral, set d1 and d2 to n (neutral)
			if (d1 == 't')
				d1 = d2;
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

	private boolean clericPopUp(DeityEntity deity) {
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

		if (deity != null)
			domains = deity.getDomain();

		for(int i = 0; i < domains.length; i++) {
			domains1.add(domains[i]);
		}
		domains1.pack();

		// set listeners
		domains1.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				domains1.setBackground(null);
				domains2.setBackground(null);
				domains2.removeAll();
				for(int i = 0; i < domains.length; i++) {
					if(!domains1.getItem(domains1.getSelectionIndex()).equals(domains[i])){
						domains2.add(domains[i]);
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
