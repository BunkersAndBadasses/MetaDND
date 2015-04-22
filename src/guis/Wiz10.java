/*
 * DONE! SAVE CHARACTER TO XML
 */

/*
 * TODO: 
 * 
 * display character information
 * 
 * add: 
 * 	cleric domain
 * 	druid animal companion
 * 	ranger favored enemy
 *  sorcerer familiar
 *  wizard familiar, specialty school/prohibited school
 * 
 */

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
import org.eclipse.swt.widgets.Text;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import core.Main;
import core.character;

public class Wiz10 {

    private CharacterWizard cw;
    private Composite wiz10;
    private int WIDTH;
    private int HEIGHT;
    private character character;
    private Composite home;
    private Composite homePanel;
    private StackLayout homeLayout;

    public Wiz10(CharacterWizard cw, Device dev, int WIDTH, int HEIGHT, 
            final Composite panel, Composite home, Composite homePanel, 
            final StackLayout layout, final StackLayout homeLayout, 
            final ArrayList<Composite> wizPages) {
        wiz10 = wizPages.get(9);
        this.cw = cw;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.character = cw.getCharacter();
        this.home = home;
        this.homePanel = homePanel;
        this.homeLayout = homeLayout;

        createPageContent();
    }

    private void createPageContent() {
        Label wiz10Label = new Label(wiz10, SWT.NONE);
        wiz10Label.setText("Done!");
        wiz10Label.pack();

        Text charText = new Text(wiz10, SWT.BORDER | SWT.READ_ONLY | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        charText.setBounds(10, 20, WIDTH - 40, HEIGHT - 110);
        charText.setText(character.toString());
        //charText.pack();

        Button wiz10SaveButton = new Button(wiz10, SWT.PUSH);
        wiz10SaveButton.setText("Save");
        wiz10SaveButton.setBounds(WIDTH-117,HEIGHT-90, 100, 50);
        wiz10SaveButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {

                // save
            	saveCharacter(character);
                cw.disposeShell();
                return;

            }
        });

        //Button wiz10BackButton = cw.createBackButton(wiz10, panel, layout);
        Button wiz10CancelButton = cw.createCancelButton(wiz10, home, homePanel, homeLayout);
        wiz10CancelButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                if (cw.cancel)
                    cw.reset();
            }
        });
    }
    public static void saveCharacter(character character) {
    	try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element Character = doc.createElement("Character");
            doc.appendChild(Character);

            // Character name
            Element Name = doc.createElement("Name");
            Name.appendChild(doc.createTextNode(character.getName()));
            Character.appendChild(Name);

            // Level
            Element Level = doc.createElement("Level");
            Level.appendChild(doc.createTextNode(
                    Integer.toString(character.getLevel())));
            Character.appendChild(Level);

            // Exp
            Element Exp = doc.createElement("Exp");
            Exp.appendChild(doc.createTextNode(
                    Integer.toString(character.getExp())));
            Character.appendChild(Exp);

            // Class
            Element Class = doc.createElement("Class");
            Class.appendChild(doc.createTextNode(character.getCharClass().getName()));
            Character.appendChild(Class);

            if(character.getCharSecClass() != null){
                // Second Class
                Element SecClass = doc.createElement("SecClass");
                SecClass.appendChild(doc.createTextNode(character.getCharSecClass().getName()));
                Character.appendChild(SecClass);
            }

            // Race
            Element Race = doc.createElement("Race");
            Race.appendChild(doc.createTextNode(character.getCharRace().getName()));
            Character.appendChild(Race);		

            // Alignment
            Element Alignment = doc.createElement("Alignment");
            Alignment.appendChild(doc.createTextNode(character.getAlignment()));
            Character.appendChild(Alignment);		

            // Size
            Element Size = doc.createElement("Size");
            Size.appendChild(doc.createTextNode(
                    Integer.toString(character.getSize())));
            Character.appendChild(Size);		

            // Age
            Element Age = doc.createElement("Age");
            Age.appendChild(doc.createTextNode(character.getAge()));
            Character.appendChild(Age);	

            // Gender
            Element Gender = doc.createElement("Gender");
            Gender.appendChild(doc.createTextNode(character.getGender()));
            Character.appendChild(Gender);	

            // Height
            Element Height = doc.createElement("Height");
            Height.appendChild(doc.createTextNode(character.getHeight()));
            Character.appendChild(Height);	

            // Weight
            Element Weight = doc.createElement("Weight");
            Weight.appendChild(doc.createTextNode(character.getWeight()));
            Character.appendChild(Weight);	

            // Eyes
            Element Eyes = doc.createElement("Eyes");
            Eyes.appendChild(doc.createTextNode(character.getEyes()));
            Character.appendChild(Eyes);	

            // Hair
            Element Hair = doc.createElement("Hair");
            Hair.appendChild(doc.createTextNode(character.getHair()));
            Character.appendChild(Hair);	

            // Skin
            Element Skin = doc.createElement("Skin");
            Skin.appendChild(doc.createTextNode(character.getSkin()));
            Character.appendChild(Skin);	

            // Strength
            Element Strength = doc.createElement("STR");
            Strength.appendChild(doc.createTextNode(
                    Integer.toString(character.getAbilityScores()[0])));
            Character.appendChild(Strength);	

            // Dexterity
            Element Dexterity = doc.createElement("DEX");
            Dexterity.appendChild(doc.createTextNode(
                    Integer.toString(character.getAbilityScores()[1])));
            Character.appendChild(Dexterity);	

            // Constitution
            Element Constitution = doc.createElement("CON");
            Constitution.appendChild(doc.createTextNode(
                    Integer.toString(character.getAbilityScores()[2])));
            Character.appendChild(Constitution);	

            // Intelligence
            Element Intelligence = doc.createElement("INT");
            Intelligence.appendChild(doc.createTextNode(
                    Integer.toString(character.getAbilityScores()[3])));
            Character.appendChild(Intelligence);	

            // Wisdom
            Element Wisdom = doc.createElement("WIS");
            Wisdom.appendChild(doc.createTextNode(
                    Integer.toString(character.getAbilityScores()[4])));
            Character.appendChild(Wisdom);	

            // Charisma
            Element Charisma = doc.createElement("CHA");
            Charisma.appendChild(doc.createTextNode(
                    Integer.toString(character.getAbilityScores()[5])));
            Character.appendChild(Charisma);	

            // Hitpoints
            Element Hitpoints = doc.createElement("HP");
            Hitpoints.appendChild(doc.createTextNode(
                    Integer.toString(character.getHitPoints())));
            Character.appendChild(Hitpoints);	

            // Remaining Hitpoints
            Element RemainingHP = doc.createElement("DMG");
            RemainingHP.appendChild(doc.createTextNode(
                    Integer.toString(character.getDamageTaken())));
            Character.appendChild(RemainingHP);	

            //Go through the Skill list
            String skillList = ""; 
            for(int i = 0; i < character.getSkills().size(); i++){
                // Skill Name
                skillList += character.getSkills().get(i).getSkill().getName()
                        + " " + character.getSkills().get(i).getRank();
                if( i != character.getSkills().size() - 1) {
                    skillList += "/";
                }
            }
            Element Skills = doc.createElement("Skills");
            Skills.appendChild(doc.createTextNode(skillList));
            Character.appendChild(Skills);

			// Launguages
			Element Languages = doc.createElement("Languages");
	//		Launguages.appendChild(doc.createTextNode(character.getLanguages())); TODO
			Character.appendChild(Languages);	

            // Gold
            Element Gold = doc.createElement("GP");
            Gold.appendChild(doc.createTextNode(
                    Integer.toString(character.getgold())));
            Character.appendChild(Gold);

            //Go through the Feat list
            String featsList = "";
            for(int i = 0; i < character.getFeats().size(); i++){
                // Feat
                featsList += character.getFeats().get(i).getName() + "/";

            }
            Element Feats = doc.createElement("Feats");
            Feats.appendChild(doc.createTextNode(featsList));
            Character.appendChild(Feats);

            //Go through the Abilities list
            String abilityList = "";
            for(int i = 0; i < character.getSpecialAbilities().size(); i++){

                // Ability
                abilityList += character.getSpecialAbilities().get(i).getName() + "/";


            }
            Element Ability = doc.createElement("Abilities");
            Ability.appendChild(doc.createTextNode(abilityList));
            Character.appendChild(Ability);	

            //Go through the Spell list TODO
            String spellList = " ";
            for(int i = 0; i < character.getSpells().size(); i++){

                // Spell Name
                spellList += character.getSpells().get(i).getName() + "/";

            }
            Element Spell = doc.createElement("Spells");
            Spell.appendChild(doc.createTextNode(spellList));
            Character.appendChild(Spell);

            //Go through the Prep Spell list TODO
            String prepSpells = " ";
            for(int i = 0; i < character.getPrepSpells().size(); i++){

                // Prep Spell Name
                prepSpells += character.getPrepSpells().get(i).getName() + "/";

            }
            Element PrepSpell = doc.createElement("PreparedSpells");
            PrepSpell.appendChild(doc.createTextNode(prepSpells));
            Character.appendChild(PrepSpell);	

            //Go through the Items list
            String itemList = "";
            for(int i = 0; i < character.getItems().size(); i++){

                // Item Name
                itemList += character.getItems().get(i).getName() + "/";

            }
            Element Item = doc.createElement("Items");
            Item.appendChild(doc.createTextNode(itemList));
            Character.appendChild(Item);

            //Go through the Weapons list TODO
            String weaponsList = " ";
            for(int i = 0; i < character.getWeapons().size(); i++){

                // Weapons Name
                weaponsList += character.getWeapons().get(i).getName();

            }
            Element Weapon = doc.createElement("Weapons");
            Weapon.appendChild(doc.createTextNode(weaponsList));
            Character.appendChild(Weapon);

            //Go through the Armor list TODO
            Element Armors = doc.createElement("Armors");
            String armorList = " ";
            for(int i = 0; i < character.getArmor().size(); i++){
                armorList += character.getArmor().get(i).getName();
                if( i != character.getArmor().size() - 1) {
                    armorList += "/";
                }
            }
            Armors.appendChild(doc.createTextNode(armorList));
            Character.appendChild(Armors);	

            //Go through the Shield list
            Element shields = doc.createElement("Shields");
            String shieldList = " ";
            //for(int i = 0; i < character.getArmor().size(); i++){
            // shieldList += character.getArmor().get(i).getName();
            // if( i != character.getArmor().size() - 1) {
            //   shieldList += "/";
            // }
            //}
            shields.appendChild(doc.createTextNode(shieldList));
            Character.appendChild(shields);  

            // AC
            Element AC = doc.createElement("AC");
            AC.appendChild(doc.createTextNode(Integer.toString(
                    character.getAC())));
            Character.appendChild(AC);	

            // TouchAC
            Element TouchAC = doc.createElement("TouchAC");
            TouchAC.appendChild(doc.createTextNode(Integer.toString(
                    character.getTouchAC())));
            Character.appendChild(TouchAC);	

            // flatFootedAC
            Element FlatAC = doc.createElement("FlatAC");
            FlatAC.appendChild(doc.createTextNode(Integer.toString(
                    character.getFlatFootedAC())));
            Character.appendChild(FlatAC);	

            // initMod
            Element initMod = doc.createElement("InitMod");
            initMod.appendChild(doc.createTextNode(Integer.toString(
                    character.getInitMod())));
            Character.appendChild(initMod);	

            // Fortitude Saving throw
            Element fortSave = doc.createElement("FortSave");
            fortSave.appendChild(doc.createTextNode(Integer.toString(
                    character.getSavingThrows()[0])));
            Character.appendChild(fortSave);	

            // Reflex Saving throw
            Element reflexSave = doc.createElement("ReflexSave");
            reflexSave.appendChild(doc.createTextNode(Integer.toString(
                    character.getSavingThrows()[1])));
            Character.appendChild(reflexSave);	

            // Will Saving throw
            Element willSave = doc.createElement("WillSave");
            willSave.appendChild(doc.createTextNode(Integer.toString(
                    character.getSavingThrows()[2])));
            Character.appendChild(willSave);	

            // baseAttackBonus
            Element baseAttackBonus = doc.createElement("BaseAttackBonus");
            baseAttackBonus.appendChild(doc.createTextNode(Integer.toString(
                    character.getBaseAttackBonus())));
            Character.appendChild(baseAttackBonus);	

            // spellResistance
            Element spellResistance = doc.createElement("SpellResistance");
            spellResistance.appendChild(doc.createTextNode(Integer.toString(
                    character.getSpellResistance())));
            Character.appendChild(spellResistance);	

            // grappleMod
            Element grappleMod = doc.createElement("GrappleMod");
            grappleMod.appendChild(doc.createTextNode(Integer.toString(
                    character.getGrappleMod())));
            Character.appendChild(grappleMod);	

            // Speed
            Element Speed = doc.createElement("Speed");
            Speed.appendChild(doc.createTextNode(Integer.toString(
                    character.getSpeed())));
            Character.appendChild(Speed);

            // damageReduction
            Element damageReduction = doc.createElement("DamageReduction");
            damageReduction.appendChild(doc.createTextNode(Integer.toString(
                    character.getDamageReduction())));
            Character.appendChild(damageReduction);

            // TODO
            Element shield = doc.createElement("Shield");
            shield.appendChild(doc.createTextNode(" "));
            Character.appendChild(shield);

            // TODO
            Element pp = doc.createElement("PP");
            pp.appendChild(doc.createTextNode(" "));
            Character.appendChild(pp);

            // TODO
            Element armor = doc.createElement("Armor");
            armor.appendChild(doc.createTextNode(" "));
            Character.appendChild(armor);

            // TODO
            Element sp = doc.createElement("SP");
            sp.appendChild(doc.createTextNode(" "));
            Character.appendChild(sp);

            // TODO
            Element cp = doc.createElement("CP");
            cp.appendChild(doc.createTextNode(" "));
            Character.appendChild(cp);

            // TODO
            Element secClass = doc.createElement("SecClass");
            secClass.appendChild(doc.createTextNode(" "));
            Character.appendChild(secClass);

            // TODO
            Element secLevel = doc.createElement("SecLevel");
            secLevel.appendChild(doc.createTextNode("0"));
            Character.appendChild(secLevel);

            // TODO
            Element priWeapon = doc.createElement("PrimaryWeapon");
            priWeapon.appendChild(doc.createTextNode(" "));
            Character.appendChild(priWeapon);

            // TODO
            Element secWeapon = doc.createElement("SecondaryWeapon");
            secWeapon.appendChild(doc.createTextNode(" "));
            Character.appendChild(secWeapon);

            // TODO
            Element dmgTaken = doc.createElement("DamageTaken");
            dmgTaken.appendChild(doc.createTextNode(" "));
            Character.appendChild(dmgTaken);

            // TODO
            Element imagePath = doc.createElement("Image");
            imagePath.appendChild(doc.createTextNode(" "));
            Character.appendChild(imagePath);

            //Go through the ClericDomains list
            String clericDomainList = "";
            if(character.getClericDomains() != null){

                for(int i = 0; i < character.getClericDomains().length; i++){

                    // Item Name
                    clericDomainList += character.getClericDomains()[i] + "/";

                }
            }
            Element clericDomains = doc.createElement("ClericDomains");
            clericDomains.appendChild(doc.createTextNode(clericDomainList));
            Character.appendChild(clericDomains);

            // druidAnimalCompanion
            String animalCompanion = "";
            if (character.getDruidAnimalCompanion() != null)
                animalCompanion = character.getDruidAnimalCompanion();
            Element druidAnimalCompanion = doc.createElement("DruidAnimalCompanion");
            druidAnimalCompanion.appendChild(doc.createTextNode(animalCompanion));
            Character.appendChild(druidAnimalCompanion);

            // rangerFavoredEnemy
            String favoriteEnemy = "";
            if (character.getRangerFavoredEnemy() != null)
                favoriteEnemy = character.getRangerFavoredEnemy();
            Element rangerFavoredEnemy = doc.createElement("RangerFavoredEnemy");
            rangerFavoredEnemy.appendChild(doc.createTextNode(
                    favoriteEnemy));
            Character.appendChild(rangerFavoredEnemy);

            // familiar
            String familiarName = "";
            if (character.getFamiliar() != null)
                familiarName = character.getFamiliar();
            Element familiar = doc.createElement("Familiar");
            familiar.appendChild(doc.createTextNode(
                    familiarName));
            Character.appendChild(familiar);

            // wizardSpecialtySchool
            String specialtySchool = "";
            if (character.getWizardSpecialtySchool() != null)
                specialtySchool = character.getWizardSpecialtySchool();
            Element wizardSpecialtySchool = doc.createElement("WizardSpecialtySchool");
            wizardSpecialtySchool.appendChild(doc.createTextNode(
                    specialtySchool));
            Character.appendChild(wizardSpecialtySchool);

            //Go through the wizardProhibitedSchools list
            String wizardProhibitedSchoolsList = "";
            if(character.getWizardProhibitedSchools() != null){
                for(int i = 0; i < character.getWizardProhibitedSchools().length; i++){

                    // Item Name
                    wizardProhibitedSchoolsList += character.getWizardProhibitedSchools()[i] + "/";

                }
            }
            Element wizardProhibitedSchools = doc.createElement("WizardProhibitedSchools");
            wizardProhibitedSchools.appendChild(doc.createTextNode(wizardProhibitedSchoolsList));
            Character.appendChild(wizardProhibitedSchools);

            // Notes
            Element Notes = doc.createElement("Notes");
            Notes.appendChild(doc.createTextNode(character.getNotes()));
            Character.appendChild(Notes);	

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            //change back to character.getName() is not working correctly
            String charName = character.getName().replaceAll("[^A-Za-z0-9]", "");
            try{
                File CHARACTER = new File(System.getProperty("user.dir") + "//" + "User Data" + "//" + "Character");
                CHARACTER.mkdir();
                File CHARDIR = new File(System.getProperty("user.dir") + "//" + "User Data" + "//" + "Character" + "//DND" + charName);
                CHARDIR.mkdir();
                StreamResult result = new StreamResult(CHARDIR.getPath() + "//DND" + charName + ".xml");


                transformer.transform(source, result);
            }catch(Exception e){
                
            }
            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            //TODO Refresh the character list when new Character is made
            // add to random character
            HomeWindow.loadCharacters();
            System.out.println("File saved!");


        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public Composite getWiz10() { return wiz10; }

}
