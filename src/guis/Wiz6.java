package src.guis;
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

/*
 * change random weight values
 */


public class Wiz6 {

	private static Composite wiz6;
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
	
	
	private Label noNameError;
	private Label noAlignmentError;
	
	private static Text nameInput;
	private static Combo alignmentInput1;
	private static Combo alignmentInput2;
	private static Text deityInput;
	private static Combo deityListInput;
	private static boolean deitySelect = false;
	private static Text heightInput;
	private static Text weightInput;
	private static Text ageInput;
	private static Text genderInput;
	private static Text eyesInput;
	private static Text hairInput;
	private static Text skinInput;
	private static Text descriptionInput;
	private static Text langInput;
	private static ArrayList<String> langList = new ArrayList<String>();
	private static ArrayList<String> possibleLangList = new ArrayList<String>();

	private static int numBonusLangs = (CharacterWizard.character.getAbilityScores()[Character.INTELLIGENCE] - 8 ) /2;

	
	
	private static Random rng = new Random();
	
	private static String charClass;
	private static String charRace;
	
	

	public Wiz6(Device dev, int WIDTH, int HEIGHT, final Character character, 
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz6 = wizPages.get(5);
		Wiz6.dev = dev;
		Wiz6.WIDTH = WIDTH;
		Wiz6.HEIGHT = HEIGHT;
		Wiz6.character = character;
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(6);
		this.wizPagesSize = wizPages.size();

		createPageContent();
	}

	private void createPageContent() {
		
		// TODO this isn't coming through
		charClass = CharacterWizard.character.getCharClass();
		charRace = CharacterWizard.character.getCharRace();
		
		
		Label wiz6Label = new Label(wiz6, SWT.NONE);
		wiz6Label.setText("Add Description");
		wiz6Label.pack();

		// name
		Label name = new Label(wiz6, SWT.NONE);
		name.setText("Name:");
		name.setLocation(0, 50);
		name.pack();
		
		nameInput = new Text(wiz6, SWT.BORDER);
		nameInput.setBounds(80, 50, 400, 30);
		
		
		// alignment
		Label alignment = new Label(wiz6, SWT.NONE);
		alignment.setText("Alignment:");
		alignment.setLocation(0, 100);
		alignment.pack();
		
		alignmentInput1 = new Combo(wiz6, SWT.DROP_DOWN | SWT.READ_ONLY);
		alignmentInput1.add("Lawful");
		alignmentInput1.add("Neutral");
		alignmentInput1.add("Chaotic");
		alignmentInput1.setLocation(80, 100);
		alignmentInput2 = new Combo(wiz6, SWT.DROP_DOWN | SWT.READ_ONLY);
		alignmentInput2.add("Good");
		alignmentInput2.add("Neutral");
		alignmentInput2.add("Evil");
		alignmentInput2.setLocation(175, 100);
		// TODO add listeners

		alignmentInput1.pack();
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
		
		Label deity = new Label(wiz6, SWT.NONE);
		deity.setText("Deity:");
		deity.setLocation(0, 150);
		deity.pack();
		
		deityListInput = new Combo(wiz6, SWT.DROP_DOWN | SWT.READ_ONLY);
		deityListInput.setLocation(80, 150);
		deityListInput.add("");
		for (int i = 0; i < deities.length; i++) {
			deityListInput.add(deities[i]);
		}
		deityListInput.pack();
		
		deityInput = new Text(wiz6, SWT.BORDER);
		deityInput.setBounds(400, 148, 180, 30);
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
		Label height = new Label(wiz6, SWT.NONE);
		height.setText("Height:");
		height.setLocation(0, 200);
		height.pack();
		
		heightInput = new Text(wiz6, SWT.BORDER);
		heightInput.setLocation(80, 200);
		heightInput.pack();
		
		Button heightRandom = new Button(wiz6, SWT.PUSH);
		heightRandom.setLocation(155, 198);
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
		
		// TODO add label for average range of heights for that race? 
		

		
		
		// weight
				/*
		 * human: 125-250
		 * 
		 * 
		 */
		Label weight = new Label(wiz6, SWT.NONE);
		weight.setText("Weight:");
		weight.setLocation(0, 250);
		weight.pack();
		
		weightInput = new Text(wiz6, SWT.BORDER);
		weightInput.setLocation(80, 250);
		weightInput.pack();
		
		Button weightRandom = new Button(wiz6, SWT.PUSH);
		weightRandom.setLocation(155, 248);
		weightRandom.setText("Random Weight");
		weightRandom.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int weight = 0;
				int min = 0;
				int max = 0;
				switch (charRace) {

				case ("Dwarf"):
					min = 45;
					max = 53;
					weight = rng.nextInt(max - min) + min + 1;
					break;
				case ("Elf"):
					min = 55;
					max = 65;
					weight = rng.nextInt(max - min) + min + 1;
					break;
				case ("Gnome"): 
					min = 36;
					max = 44;
					weight = rng.nextInt(max - min) + min + 1;
					break;
				case ("Half-elf"):
					min = 55;
					max = 71;
					weight = rng.nextInt(max - min) + min + 1;
					break;
				case ("Half-orc"):
					min = 55;
					max = 82;
					weight = rng.nextInt(max - min) + min + 1;
					break;
				case ("Halfling"):
					min = 32;
					max = 40;
					weight = rng.nextInt(max - min) + min + 1;
					break;
				default:
					// human
					min = 125;
					max = 250;
					weight = rng.nextInt(max - min) + min + 1;
					break;
				}
				String weightString = Integer.toString(weight)+ " lbs";
				weightInput.setText(weightString);
			}
		});
		weightRandom.pack();
		
		// age
		Label age = new Label(wiz6, SWT.NONE);
		age.setText("Age:");
		age.setLocation(510, 50);
		age.pack();
		
		ageInput = new Text(wiz6, SWT.BORDER);
		ageInput.setLocation(550, 50);
		ageInput.pack();
		
		
		// gender
		Label gender = new Label(wiz6, SWT.NONE);
		gender.setText("Gender:");
		gender.setLocation(335, 100);
		gender.pack();
		
		genderInput = new Text(wiz6, SWT.BORDER);
		genderInput.setBounds(400, 100, 150, 30);
		
		
		// eyes 250
		Label eyes = new Label(wiz6, SWT.NONE);
		eyes.setText("Eyes:");
		eyes.setLocation(285,200);
		eyes.pack();

		eyesInput = new Text(wiz6, SWT.BORDER);
		eyesInput.setBounds(325, 200, 75, 30);
		
		
		// hair 
		Label hair = new Label(wiz6, SWT.NONE);
		hair.setText("Hair:");
		hair.setLocation(425, 200);
		hair.pack();
		
		hairInput = new Text(wiz6, SWT.BORDER);
		hairInput.setBounds(465, 200, 75, 30);
		
		
		// skin
		Label skin = new Label(wiz6, SWT.NONE);
		skin.setText("Skin:");
		skin.setLocation(560, 200);
		skin.pack();
		
		skinInput = new Text(wiz6, SWT.BORDER);
		skinInput.setBounds(600,200,75,30);
		
		
		// description
		Label description = new Label(wiz6, SWT.NONE);
		description.setText("Description:");
		description.setLocation(285,250);
		description.pack();
		
		descriptionInput = new Text(wiz6, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		descriptionInput.setBounds(375,250, 300, 150);
		
		// languages
		Label languages = new Label(wiz6, SWT.NONE);
		languages.setText("Languages:");
		languages.setLocation(0, 300);
		languages.pack();
		
		langInput = new Text(wiz6, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		langInput.setBounds(80, 300, 285, 40);
		
		Label addLang = new Label(wiz6, SWT.NONE);		
		if (numBonusLangs < 0)
			numBonusLangs = 0;
		addLang.setText("Pick " + Integer.toString(numBonusLangs));
		addLang.setLocation(0,350);
		addLang.pack();
		
		langList.add("Common");
		// add racial languages TODO
		String charRace = CharacterWizard.character.getCharRace();
		switch(charRace) {
		case ("Dwarf"):
			break;
		case ("Elf"):
			break;
		case ("Gnome"):
			break;
		case ("Half-Elf"):
			break;
		case ("Half-Orc"):
			break;
		case ("Halfling"):
			break;
		default : // human
			break;
		}

		//TODO add all possible languages 
		possibleLangList.add("blah"); 
		String possibleLangString = possibleLangList.get(0);
		for (int i = 1; i < possibleLangList.size(); i++)
			possibleLangString += ", " + possibleLangList.get(i);
		
		//TODO this isn't working
		Label possibleLangs = new Label(wiz6, SWT.WRAP | SWT.MULTI);
		possibleLangs.setText(/*possibleLangString*/"hi");
		possibleLangs.setBackground(new Color(dev, 255, 0, 0));
		possibleLangs.setBounds(80,350,280,40);
		
		
		// next button
		Button wiz6NextButton = CharacterWizard.createNextButton(wiz6);
		wiz6NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.wizPageNum < wizPagesSize - 1)
					CharacterWizard.wizPageNum++;
				if (!CharacterWizard.wizPageCreated[6])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});

		
		// back button
		Button wiz6BackButton = CharacterWizard.createBackButton(wiz6, panel, layout);
		
		
		// cancel button
		Button wiz6CancelButton = CharacterWizard.createCancelButton(wiz6, home, homePanel, homeLayout);
		wiz6CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.cancel)
					cancelClear();
			}
		});
	}
	
	private void createNextPage() {
		CharacterWizard.wizPageCreated[6] = true;
		new Wiz7(dev, WIDTH, HEIGHT, character, panel, home,
				homePanel, layout, homeLayout, wizPages);
	}

	public Composite getWiz6() { return wiz6; }

	public static void cancelClear() {
		CharacterWizard.character = new Character();
		Wiz1.cancelClear();
		Wiz2.cancelClear();
		Wiz3.cancelClear();
		Wiz4.cancelClear();
		Wiz5.cancelClear();
		
		nameInput.setText("");
		alignmentInput1.deselectAll();
		alignmentInput2.deselectAll();
	}
}
