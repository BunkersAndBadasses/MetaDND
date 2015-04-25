/*
 * ADD DESCRIPTION
 */

//TODO alignment checking different

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
import org.eclipse.swt.widgets.List;
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
import entity.RaceEntity;

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
	private List langInput;
	private Text customLang;
	private int remainingBonusLangs;
	private int numBonusLangs;

	private String domains[] = {"Air", "Animal", "Chaos", "Death", "Destruction", 
			"Earth", "Evil", "Fire", "Good", "Healing", "Knowledge", "Law", 
			"Luck", "Magic", "Plant", "Protection", "Strength", "Sun", 
			"Travel", "Trickery", "War", "Water"};

	private Random rng = new Random();

	private ClassEntity charClass;
	private RaceEntity charRace;

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
		charClass = cw.getCharacter().getCharClass();
		charRace = cw.getCharacter().getCharRace();

		createPageContent();
	}

	private void createPageContent() {
		// main label
		Label wiz4Label = new Label(wiz4, SWT.NONE);
		wiz4Label.setText("Add Description");
		wiz4Label.pack();
		
		GridLayout gl = new GridLayout(8, true);
		
		Composite inner = new Composite(wiz4, SWT.NONE);
		inner.setBounds(5, 20, WIDTH-10, HEIGHT-110);
		inner.setLayout(gl);

		GridData gd;
		
		
		// initialize layout
		
		nameInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 4;
		nameInput.setLayoutData(gd);
		
		genderInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 2;
		genderInput.setLayoutData(gd);
		
		ageInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 2;
		ageInput.setLayoutData(gd);
		
		heightInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 2;
		heightInput.setLayoutData(gd);
		
		Button heightRandom = new Button(inner, SWT.PUSH);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 2;
		heightRandom.setLayoutData(gd);
		
		eyesInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		eyesInput.setLayoutData(gd);
		
		hairInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		hairInput.setLayoutData(gd);
		
		skinInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		skinInput.setLayoutData(gd);
		
		Label spacer = new Label(inner, SWT.NONE);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		spacer.setLayoutData(gd);		
		
		weightInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 2;
		weightInput.setLayoutData(gd);
		
		Button weightRandom = new Button(inner, SWT.PUSH);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 2;
		weightRandom.setLayoutData(gd);
		
		descriptionInput = new Text(inner, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.SEARCH);
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 4;
		gd.verticalSpan = 2;
		descriptionInput.setLayoutData(gd);
		
		alignmentInput1 = new CCombo(inner, SWT.DROP_DOWN | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 2;
		alignmentInput1.setLayoutData(gd);	
		
		alignmentInput2 = new CCombo(inner, SWT.DROP_DOWN | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 2;
		alignmentInput2.setLayoutData(gd);
		
		deityListInput = new Combo(inner, SWT.DROP_DOWN | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 4;
		deityListInput.setLayoutData(gd);
		
		Button deitySearchButton = new Button(inner, SWT.PUSH);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		deitySearchButton.setLayoutData(gd);
		
		deityInput = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 3;
		deityInput.setLayoutData(gd);		
		
		Label addLang = new Label(inner, SWT.NONE);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 2;
		addLang.setLayoutData(gd);
		
		Label knownLangs = new Label(inner, SWT.NONE);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 3;
		knownLangs.setLayoutData(gd);
		
		Label possibleLangs = new Label(inner, SWT.NONE);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.horizontalSpan = 3;
		possibleLangs.setLayoutData(gd);
		
		customLang = new Text(inner, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		gd.horizontalSpan = 2;
		customLang.setLayoutData(gd);
		
		langInput = new List(inner, SWT.BORDER | SWT.V_SCROLL | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL, GridData.FILL, true, true);
		gd.horizontalSpan = 3;
		gd.verticalSpan = 2;
		langInput.setLayoutData(gd);
		
		List possibleLangsList = new List(inner, SWT.BORDER | SWT.V_SCROLL | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL, GridData.FILL, true, true);
		gd.horizontalSpan = 3;
		gd.verticalSpan = 2;
		possibleLangsList.setLayoutData(gd);
		
		Button removeLang = new Button(inner, SWT.PUSH);
		gd = new GridData(GridData.FILL, GridData.CENTER, true, true);
		gd.horizontalSpan = 2;
		removeLang.setLayoutData(gd);
		
		
		// create content
		
		// name
		
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

		deityListInput.add("Deity");
		for (int i = 0; i < deities.size(); i++) {
			deityListInput.add(deities.get(i).getName() + " (" + deities.get(i).getAlignment() + ")");
		}
		deityListInput.select(0);
		deityListInput.pack();

		// custom deity/selected deity text box
		deityInput.setText("");
		deityInput.setMessage("Custom Deity");
		deityInput.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event e) {
				deityInput.setBackground(null);
			}
		});
		deityInput.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				deityListInput.setBackground(null);
				deityInput.setBackground(null);
				if (!deitySelect) {
					deityListInput.select(0);
				}
				deitySelect = false;
			}
		});
		deityInput.pack();

		deityListInput.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				deityListInput.setBackground(null);
				deitySelect = true;
				if (deityListInput.getSelectionIndex() == 0)
					deityInput.setText("");
				else 
					deityInput.setText(deities.get(deityListInput.getSelectionIndex()-1).getName());
			}
		});
		deityListInput.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event e) {
				deityListInput.setBackground(null);
			}
		});

		
		// deity search button
		deitySearchButton.setText("Details");
		deitySearchButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				deityListInput.setBackground(null);
				int index = deityListInput.getSelectionIndex();
				if (index == 0 || index == -1) {
					deityListInput.setBackground(red);
					return;
				}
				deities.get(index-1).toTooltipWindow();
			}
		});
		deitySearchButton.pack();

		// alignment

		alignmentInput1.add("Law-Chaos");
		alignmentInput1.add("Lawful");
		alignmentInput1.add("Neutral");
		alignmentInput1.add("Chaotic");
		alignmentInput1.select(0);
		alignmentInput1.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event e) {
				alignmentInput1.setBackground(null);
			}
		});	
		alignmentInput1.pack();

		alignmentInput2.add("Good-Evil");
		alignmentInput2.add("Good");
		alignmentInput2.add("Neutral");
		alignmentInput2.add("Evil");
		alignmentInput2.select(0);
		alignmentInput2.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event e) {
				alignmentInput2.setBackground(null);
			}
		});
		alignmentInput2.pack();


		// eyes
		eyesInput.setText("");
		eyesInput.setMessage("Eyes");
		eyesInput.pack();


		// hair 
		hairInput.setText("");
		hairInput.setMessage("Hair");
		hairInput.pack();


		// skin
		skinInput.setText("");
		skinInput.setMessage("Skin");
		skinInput.pack();
		
		
		// height

		heightInput.setText("");
		heightInput.setMessage("Height");
		heightInput.pack();

		heightRandom.setText("Random Height");
		heightRandom.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int height = 0;
				int min = 0;
				int max = 0;
				switch (charRace.getName()) {

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
		weightInput.setText("");
		weightInput.setMessage("Weight");
		weightInput.pack();

		weightRandom.setText("Random Weight");
		weightRandom.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int weight = 0;
				int min = 0;
				int max = 0;
				switch (charRace.getName()) {

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
		genderInput.setText("");
		genderInput.setMessage("Gender");
		genderInput.pack();

		
		// age
		ageInput.setText("");
		ageInput.setMessage("Age");
		ageInput.pack();
		
		
		// description
		descriptionInput.setMessage("Description");
		descriptionInput.pack();
		

		// languages
		langInput.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				addLang.setBackground(null);
			}
		});

		customLang.setMessage("Custom Language");
		customLang.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event e) {
				if (remainingBonusLangs == 0)
					return;
				if (customLang.getText().length() == 0)
					return;
				langInput.add(customLang.getText());
				remainingBonusLangs--;
				if (remainingBonusLangs == 1)
					addLang.setText("Pick " + Integer.toString(remainingBonusLangs) + " Bonus Language");
				else
					addLang.setText("Pick " + Integer.toString(remainingBonusLangs) + " Bonus Languages");
				addLang.pack();
				inner.layout();
				addLang.setBackground(null);
			}
		});

		removeLang.setText("Remove");
		removeLang.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				if (numBonusLangs != remainingBonusLangs) {
					langInput.remove(langInput.getItemCount()-1);
					remainingBonusLangs++;
					if (remainingBonusLangs == 1)
						addLang.setText("Pick " + Integer.toString(remainingBonusLangs) + " Bonus Language");
					else
						addLang.setText("Pick " + Integer.toString(remainingBonusLangs) + " Bonus Languages");
					addLang.pack();
					inner.layout();
				}
			}
		});
		
		if (numBonusLangs < 0)
			numBonusLangs = 0;
		remainingBonusLangs = numBonusLangs;
		if (remainingBonusLangs == 1)
			addLang.setText("Pick " + Integer.toString(remainingBonusLangs) + " Bonus Language");
		else
			addLang.setText("Pick " + Integer.toString(remainingBonusLangs) + " Bonus Languages");
		addLang.pack();
		
		knownLangs.setText("Known Languages:");
		knownLangs.pack();
		
		possibleLangs.setText("Possible Languages:");
		possibleLangs.pack();

		String[] raceLangs = charRace.getAutoLanguages(); 
		for(int i = 0; i < raceLangs.length; i++) 
			langInput.add(raceLangs[i]);
		
		String[] raceBonusLangs = charRace.getBonusLanguages();
		for (int i = 0; i < raceBonusLangs.length; i++)
			possibleLangsList.add(raceBonusLangs[i]);
		String[] classBonusLangs = charClass.getBonusLanguages();
		if (classBonusLangs != null) {
		for (int i = 0; i < classBonusLangs.length; i++)
			possibleLangsList.add(classBonusLangs[i]);
		}
		possibleLangsList.pack();
		possibleLangsList.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event e) {
				if (remainingBonusLangs == 0)
					return;
				langInput.add(possibleLangsList.getItem(possibleLangsList.getSelectionIndex()));
				remainingBonusLangs--;
				if (remainingBonusLangs == 1)
					addLang.setText("Pick " + Integer.toString(remainingBonusLangs) + " Bonus Language");
				else
					addLang.setText("Pick " + Integer.toString(remainingBonusLangs) + " Bonus Languages");
				addLang.pack();
				inner.layout();
				addLang.setBackground(null);
			}
		});
		inner.layout();

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
				if (condensed.length() > 200 ) {
					nameInput.setBackground(red);
					error = true;
				}
				if (remainingBonusLangs > 0){
					addLang.setBackground(red);
					error = true;
				}
				if (charClass.getName().equalsIgnoreCase("cleric")) {
					if (deityInput.getText().length() == 0) {
						deityInput.setBackground(red);
						error = true;
					}
					if (alignmentInput1.getSelectionIndex() < 1) {
						alignmentInput1.setBackground(red);
						error = true;
					}
					if (alignmentInput2.getSelectionIndex() < 1) {
						alignmentInput2.setBackground(red);
						error = true;
					}
				}
				if (error)
					return;

				String a1, a2;
				if (alignmentInput1.getSelectionIndex() < 1)
					a1 = "<empty>";
				else 
					a1 = alignmentInput1.getText();
				if (alignmentInput2.getSelectionIndex() < 1)
					a2 = "<empty>";
				else
					a2 = alignmentInput2.getText();

				DeityEntity deitySelect;
				if (deityListInput.getSelectionIndex() >= 1)
					deitySelect = deities.get(deityListInput.getSelectionIndex()-1);
				else
					deitySelect = null;

				boolean done = true;
				done = checkAlignmentPopUp(a1, a2, deitySelect);
				if (done) {
					if (charClass.getName().equalsIgnoreCase("cleric"))
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
				for (int i = 0; i < langInput.getItemCount(); i++)
					character.addLanguage(langInput.getItem(i));

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
				finished = false;
			}
		});

		// warning label
		Label warning = new Label(alignmentShell, SWT.WRAP);
		GridData warningGD = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		warningGD.horizontalSpan = 2;
		warning.setLayoutData(warningGD);


		switch(charClass.getName()) {
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
			String[] deityAlignment = deity.getAlignment().split(" ");
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
				String d1 = domains1.getText();
				String d2 = domains2.getText();
				String[] domains = {d1, d2};
				character.setClericDomains(domains);
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
