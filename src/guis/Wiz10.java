package guis;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import entity.*;
import core.CharItem;
import core.CharSkill;
import core.Main;
import core.character;

public class Wiz10 {

	private Composite wiz10;
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
	private int wizPagesSize;

	public Wiz10(Device dev, int WIDTH, int HEIGHT, final character character, 
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz10 = wizPages.get(9);
		this.dev = dev;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.character = character;
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.wizPagesSize = wizPages.size();

		createPageContent();
	}

	private void createPageContent() {
		Label wiz10Label = new Label(wiz10, SWT.NONE);
		wiz10Label.setText("Done!");
		wiz10Label.pack();

		Button wiz10SaveButton = new Button(wiz10, SWT.PUSH);
		wiz10SaveButton.setText("Save");
		wiz10SaveButton.setBounds(WIDTH-117,HEIGHT-90, 100, 50);
		wiz10SaveButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// save

				//				private String name;
				//				private int level = 1;
				//				private int exp = 0;
				//				private ClassEntity charClass;		// change to type Class once refs are added
				//				private RaceEntity charRace;				// ^^ ditto for Race
				//				private ClassEntity charSecClass;
				//				private String alignment;
				//				private String deity;
				//				private int size; // TODO int? string?
				//				private String age; 
				//				private String gender;
				//				private String height;
				//				private String weight;
				//				private String eyes;
				//				private String hair;
				//				private String skin;
				//				private String[] appearance = {eyes, hair, skin};
				//				private String description;
				//				// "STR", "DEX", "CON", "INT", "WIS", "CHA"
				//				private int[] abilityScores = new int[6];
				//				private int hp; // hitpoints
				//				private int remainingHP;
				//				private ArrayList<CharSkill> skillsList;
				//				private String languages;
				//				private int gold;
				//				private ArrayList<FeatEntity> feats = new ArrayList<FeatEntity>();
				//				private ArrayList<AbilityEntity> specialAbilities = new ArrayList<AbilityEntity>();
				//				private ArrayList<SpellEntity> spells = new ArrayList<SpellEntity>();
				//				private ArrayList<SpellEntity> prepSpells = new ArrayList<SpellEntity>();
				//				private ArrayList<CharItem> items = new ArrayList<CharItem>();
				//				private ArrayList<WeaponEntity> weapons = new ArrayList<WeaponEntity>();
				//				private ArrayList<ArmorEntity> armors = new ArrayList<ArmorEntity>();
				//				private String notes;
				//				
				try {

					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

					// root elements
					Document doc = docBuilder.newDocument();
					Element rootElement = doc.createElement(character.getName());
					doc.appendChild(rootElement);


					// Character
					Element Character = doc.createElement("CHARACTER");
					rootElement.appendChild(Character);

					// Character name
					Element Name = doc.createElement("NAME");
					Name.appendChild(doc.createTextNode(character.getName()));
					Character.appendChild(Name);

					// Level
					Element Level = doc.createElement("LEVEL");
					Level.appendChild(doc.createTextNode(
							Integer.toString(character.getLevel())));
					Character.appendChild(Level);

					// Exp
					Element Exp = doc.createElement("EXP");
					Exp.appendChild(doc.createTextNode(
							Integer.toString(character.getExp())));
					Character.appendChild(Exp);

					// Class
					Element Class = doc.createElement("CLASS");
					Class.appendChild(doc.createTextNode(character.getCharClass().getName()));
					Character.appendChild(Class);

					if(character.getCharSecClass().getName() != null){
						// Second Class
						Element SecClass = doc.createElement("SECOND_CLASS");
						SecClass.appendChild(doc.createTextNode(character.getCharSecClass().getName()));
						Character.appendChild(SecClass);
					}

					// Race
					Element Race = doc.createElement("RACE");
					Race.appendChild(doc.createTextNode(character.getCharRace().getName()));
					Character.appendChild(Race);		

					// Alignment
					Element Alignment = doc.createElement("ALIGNMENT");
					Alignment.appendChild(doc.createTextNode(character.getAlignment()));
					Character.appendChild(Alignment);		

					// Size
					Element Size = doc.createElement("SIZE");
					Size.appendChild(doc.createTextNode(
							Integer.toString(character.getSize())));
					Character.appendChild(Size);		

					// Age
					Element Age = doc.createElement("AGE");
					Age.appendChild(doc.createTextNode(character.getAge()));
					Character.appendChild(Age);	

					// Gender
					Element Gender = doc.createElement("GENDER");
					Gender.appendChild(doc.createTextNode(character.getGender()));
					Character.appendChild(Gender);	

					// Height
					Element Height = doc.createElement("HEIGHT");
					Height.appendChild(doc.createTextNode(character.getHeight()));
					Character.appendChild(Height);	

					// Weight
					Element Weight = doc.createElement("WEIGHT");
					Weight.appendChild(doc.createTextNode(character.getWeight()));
					Character.appendChild(Weight);	

					// Eyes
					Element Eyes = doc.createElement("EYES");
					Eyes.appendChild(doc.createTextNode(character.getEyes()));
					Character.appendChild(Eyes);	

					// Hair
					Element Hair = doc.createElement("HAIR");
					Hair.appendChild(doc.createTextNode(character.getHair()));
					Character.appendChild(Hair);	

					// Skin
					Element Skin = doc.createElement("SKIN");
					Skin.appendChild(doc.createTextNode(character.getSkin()));
					Character.appendChild(Skin);	

					// Strength
					Element Strength = doc.createElement("STRENGTH");
					Strength.appendChild(doc.createTextNode(
							Integer.toString(character.getAbilityScores()[0])));
					Character.appendChild(Strength);	

					// Dexterity
					Element Dexterity = doc.createElement("DEXTERITY");
					Dexterity.appendChild(doc.createTextNode(
							Integer.toString(character.getAbilityScores()[1])));
					Character.appendChild(Dexterity);	

					// Constitution
					Element Constitution = doc.createElement("CONSTITUTION");
					Constitution.appendChild(doc.createTextNode(
							Integer.toString(character.getAbilityScores()[2])));
					Character.appendChild(Constitution);	

					// Intelligence
					Element Intelligence = doc.createElement("INTELLIGENCE");
					Intelligence.appendChild(doc.createTextNode(
							Integer.toString(character.getAbilityScores()[3])));
					Character.appendChild(Intelligence);	

					// Wisdom
					Element Wisdom = doc.createElement("WISDOM");
					Wisdom.appendChild(doc.createTextNode(
							Integer.toString(character.getAbilityScores()[4])));
					Character.appendChild(Wisdom);	

					// Charisma
					Element Charisma = doc.createElement("CHARISMA");
					Charisma.appendChild(doc.createTextNode(
							Integer.toString(character.getAbilityScores()[5])));
					Character.appendChild(Charisma);	

					// Hitpoints
					Element Hitpoints = doc.createElement("HITPOINTS");
					Hitpoints.appendChild(doc.createTextNode(
							Integer.toString(character.getHitPoints())));
					Character.appendChild(Hitpoints);	

					// Remaining Hitpoints
					Element RemainingHP = doc.createElement("REMAINING_HITPOINTS");
					RemainingHP.appendChild(doc.createTextNode(
							Integer.toString(character.getRemainingHitPoints())));
					Character.appendChild(RemainingHP);	

					//Go through the Skill list
					for(int i = 0; i < character.getSkills().size(); i++){
						// Skill Name
						Element SkillName = doc.createElement("SKILL_NAME");
						SkillName.appendChild(doc.createTextNode(
								character.getSkills().get(i).getSkill().getName()));
						Character.appendChild(SkillName);	

						// Skill Rank
						Element SkillRank = doc.createElement("SKILL_RANK");
						SkillRank.appendChild(doc.createTextNode(
								Integer.toString(character.getSkills().get(i).getRank())));
						Character.appendChild(SkillRank);	
					}

					// Launguages
					Element Launguages = doc.createElement("LANGUAGES");
					Launguages.appendChild(doc.createTextNode(character.getLanguages()));
					Character.appendChild(Launguages);	

					// Gold
					Element Gold = doc.createElement("GOLD");
					Gold.appendChild(doc.createTextNode(
							Integer.toString(character.getgold())));
					Character.appendChild(Gold);

					//Go through the Feat list
					for(int i = 0; i < character.getFeats().size(); i++){

						// Feat
						Element Feat = doc.createElement("FEAT");
						Feat.appendChild(doc.createTextNode(
								character.getFeats().get(i).getName()));
						Character.appendChild(Feat);	

					}

					//Go through the Abilities list
					for(int i = 0; i < character.getSpecialAbilities().size(); i++){

						// Ability
						Element Ability = doc.createElement("ABILITY");
						Ability.appendChild(doc.createTextNode(
								character.getSpecialAbilities().get(i).getName()));
						Character.appendChild(Ability);	

					}

					//Go through the Spell list
					for(int i = 0; i < character.getSpells().size(); i++){

						// Spell Name
						Element Spell = doc.createElement("SPELL");
						Spell.appendChild(doc.createTextNode(
								character.getSpells().get(i).getName()));
						Character.appendChild(Spell);	

					}

					//Go through the Prep Spell list
					for(int i = 0; i < character.getPrepSpells().size(); i++){

						// Prep Spell Name
						Element PrepSpell = doc.createElement("PREP_SPELL");
						PrepSpell.appendChild(doc.createTextNode(
								character.getPrepSpells().get(i).getName()));
						Character.appendChild(PrepSpell);	

					}

					//Go through the Items list
					for(int i = 0; i < character.getItems().size(); i++){

						// Item Name
						Element Item = doc.createElement("ITEM");
						Item.appendChild(doc.createTextNode(
								character.getItems().get(i).getName()));
						Character.appendChild(Item);	

					}

					//Go through the Weapons list
					for(int i = 0; i < character.getWeapons().size(); i++){

						// Weapons Name
						Element Weapon = doc.createElement("WEAPON");
						Weapon.appendChild(doc.createTextNode(
								character.getWeapons().get(i).getName()));
						Character.appendChild(Weapon);	

					}

					//Go through the Armor list
					for(int i = 0; i < character.getArmor().size(); i++){

						// Armor Name
						Element Armor = doc.createElement("ARMOR");
						Armor.appendChild(doc.createTextNode(
								character.getArmor().get(i).getName()));
						Character.appendChild(Armor);	

					}

					// Notes
					Element Notes = doc.createElement("NOTES");
					Notes.appendChild(doc.createTextNode(character.getNotes()));
					Character.appendChild(Notes);	

					// write the content into xml file
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);

					//change back to character.getName() is not working correctly
					String charName = character.getName().replaceAll("[^A-Za-z0-9]", "");
					try{
						File CHARDIR = new File(System.getProperty("user.dir") + "//" + "User Data" + "//" + charName);
						CHARDIR.mkdir();
						StreamResult result = new StreamResult(new File("User Data//" + charName + "//" + charName + ".xml"));
					
						transformer.transform(source, result);
					}catch(Exception e){
						File CHARDIR = new File(System.getProperty("user.dir") + "//" + "User Data" + "//DND" + charName);
						CHARDIR.mkdir();
						StreamResult result = new StreamResult(new File("User Data//" + charName + "//DND" + charName + ".xml"));
					
						transformer.transform(source, result);
					}
					// Output to console for testing
					// StreamResult result = new StreamResult(System.out);

					System.out.println("File saved!");

				} catch (ParserConfigurationException pce) {
					pce.printStackTrace();
				} catch (TransformerException tfe) {
					tfe.printStackTrace();
				}
				return;

			}
		});

		//Button wiz10BackButton = CharacterWizard.createBackButton(wiz10, panel, layout);
		Button wiz10CancelButton = CharacterWizard.createCancelButton(wiz10, home, homePanel, homeLayout);
		wiz10CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.cancel)
					CharacterWizard.reset();
			}
		});
	}

	public Composite getWiz10() { return wiz10; }

}
