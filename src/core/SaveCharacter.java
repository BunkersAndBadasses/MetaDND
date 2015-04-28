package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SaveCharacter {

    private static Document doc;
    private static File stocks;
    private static Document element;
    private static character c = Main.gameState.currentlyLoadedCharacter;
    private String temp;
    String empty; 
    int mt;
    String fo = "";
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private File toWrite;
    private BufferedWriter bw;



    public SaveCharacter(Boolean New, String fn) {
        try {
            // New character file
            if (New) {
                fo += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Character>\n";

                String charName = c.getName().replaceAll("[^A-Za-z0-9]", "");
                try{
                    File CHARACTER = new File(System.getProperty("user.dir") + "//" + "User Data" + "//" + "Character");
                    CHARACTER.mkdir();
                    File CHARDIR = new File(System.getProperty("user.dir") + "//" + "User Data" + "//" + "Character" + "//DND" + charName);
                    CHARDIR.mkdir();
                    toWrite = new File(CHARACTER.getPath() + "//DND" + charName + "//DND" + charName +".xml");
                    FileOutputStream fos = new FileOutputStream(toWrite);
                    
                    bw = new BufferedWriter(new OutputStreamWriter(fos));
                    
                }catch(Exception e){
                    e.printStackTrace();
                }
                bw.write(fo);

                try {
                    empty  = c.getName();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Name", empty);

                try {
                    mt  = c.getLevel();
                } catch (NullPointerException npe) { mt = 0; }
                appendValue("Level", mt);

                try {
                    mt  = c.getExp();
                } catch (NullPointerException npe) { mt = 0; }
                appendValue("Exp", mt);

                try {
                    empty  = c.getCharRace().getName();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Race", empty);

                try {
                    empty  = c.getAlignment();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Alignment", empty);

                try {
                    empty  = c.getDeity();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Deity", empty);

                try {
                    mt  = c.getSize();
                } catch (NullPointerException npe) { mt = 0;}
                appendValue("Size", mt);

                try {
                    empty  = c.getAge();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Age", empty);

                try {
                    empty  = c.getGender();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Gender", empty);

                try {
                    empty  = c.getHeight();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Height", empty);

                try {
                    empty  = c.getWeight();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Weight", empty);

                try {
                    empty  = c.getEyes();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Eyes", empty);

                try {
                    empty  = c.getHair();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Hair", empty);

                try {
                    empty  = c.getSkin();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Skin", empty);

                try {
                    empty  = c.getDescription();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Description", empty);

                try {
                    mt  = c.getSpells().size();
                } catch (NullPointerException npe) { mt = 0; }
                temp = " ";
                if (mt != 0) {
                    temp = "";
                    for(int i = 0; i < c.getSpells().size(); i++){
                        temp += c.getSpells().get(i).getName() + "/";
                    }
                }
                appendValue("Spells", temp);

                try {
                    mt  = c.getSpecialAbilities().size();
                } catch (NullPointerException npe) { mt = 0; }
                temp = " ";
                if (mt != 0) {
                    temp = "";
                    for(int i = 0; i < c.getSpecialAbilities().size(); i++){
                        temp += c.getSpecialAbilities().get(i).getName() + "/";
                    }
                }
                appendValue("SpecialAbilities", temp);

                try {
                    mt  = c.getPrepSpells().size();
                } catch (NullPointerException npe) { mt = 0; }
                temp = " ";
                if (mt != 0) {
                    temp = "";
                    for(int i = 0; i < c.getPrepSpells().size(); i++){
                        temp += c.getPrepSpells().get(i).getName() + "/";
                    }
                }
                appendValue("PreparedSpells", temp);

                try {
                    empty = "";
                    for (int i = 0; i < c.getAC().length; i++)
                        empty += c.getAC()[i] + "/";
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("AC", empty);

                try {
                    mt  = c.getTouchAC();
                } catch (NullPointerException npe) { mt = 0; }
                appendValue("TouchAC", mt);

                try {
                    mt  = c.getFlatFootedAC();
                } catch (NullPointerException npe) { mt = 0; }
                appendValue("FlatFootedAC", mt);


                try {
                    empty  = "";
                    for (int i = 0; i < c.getInitMod().length; i++)
                        empty +=  c.getInitMod()[i]  + "/";
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Init", empty);

                try {
                    empty = "";
                    for (int i = 0; i < c.getFortSave().length; i++)
                        empty += c.getFortSave()[i] + "/";

                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Fortitude",empty);

                try {
                    empty  = "";
                    for (int i = 0; i < c.getReflexSave().length; i++)
                        empty += c.getReflexSave()[i] + "/";
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Reflex", empty);

                try {
                    empty  = "";
                    for (int i = 0; i < c.getWillSave().length; i++)
                        empty += c.getWillSave()[i] + "/";
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Will", empty);

                try {
                    mt  = c.getBaseAttackBonus();
                } catch (NullPointerException npe) { mt = 0; }
                appendValue("BaseAttack", mt);

                try {
                    mt  = c.getSpellResistance();
                } catch (NullPointerException npe) { mt = 0; }
                appendValue("SpellResistance", mt);

                try {
                    mt  = c.getSpeed();
                } catch (NullPointerException npe) { mt = 0; }
                appendValue("Speed", mt);

                try {
                    empty  = "";
                    for (int i = 0; i < c.getGrappleMod().length; i++)
                        empty += c.getGrappleMod()[i]  + "/";
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Grapple", empty);

                try {
                    mt  = c.getDamageReduction();
                } catch (NullPointerException npe) { mt = 0; }
                appendValue("DamageReduction", mt);

                try {
                    empty  = "";
                    for (int i = 0; i < c.getClericDomains().length; i++ ) {
                        empty += c.getClericDomains()[i] + "/";
                    }
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("ClericDomains", empty);

                try {
                    empty  = c.getDruidAnimalCompanion();
                    if(empty == null) { empty = " "; }
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("DruidCompanion", empty);

                try {
                    empty  = c.getRangerFavoredEnemy();
                    if(empty == null) { empty = " "; }
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("RangerFavoredEnemy", empty);

                try {
                    empty  = c.getFamiliar();
                    if(empty == null) { empty = " "; }
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Familiar", empty);

                try {
                    empty  = c.getWizardSpecialtySchool();
                    if(empty == null) { empty = " "; }
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("WizardSpecialty", empty);

                try {
                    empty  = "";
                    for(int i = 0; i < c.getWizardProhibitedSchools().length; i++){
                        empty += c.getWizardProhibitedSchools()[i]+ "/";
                    }
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("WizardProhibitedSchools", empty);

                try {
                    empty  = c.getImage();
                    if(empty == null) { empty = " "; }
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Image", empty);

                try {
                    empty  = c.getCharClass().getName();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Class", empty);

                try {
                    mt  = c.getSecLevel();
                } catch (NullPointerException npe) { mt = 0; }
                appendValue("SecLevel", mt);

                try {
                    empty  = c.getSecClass().getName();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("SecClass", empty);

                try {
                    appendValue("STR", c.getAbilityScores()[0]);
                    appendValue("DEX", c.getAbilityScores()[1]);
                    appendValue("CON", c.getAbilityScores()[2]);
                    appendValue("INT", c.getAbilityScores()[3]);
                    appendValue("WIS", c.getAbilityScores()[4]);
                    appendValue("CHA", c.getAbilityScores()[5]);
                } catch (NullPointerException npe) {
                    appendValue("STR", 0);
                    appendValue("DEX", 0);
                    appendValue("CON", 0);
                    appendValue("INT", 0);
                    appendValue("WIS", 0);
                    appendValue("CHA", 0);
                }

                try {
                    mt  = c.getHitPoints();
                } catch (NullPointerException npe) { mt = 0; }
                appendValue("HP", mt);

                try {
                    empty  = c.getPrimaryWeapon().getName();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("PrimaryWeapon", empty);

                try {
                    empty  = c.getSecondaryWeapon().getName();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("SecondaryWeapon", empty);

                try { // TODO
                    empty  = c.getCurrArmor().getName();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Armor", empty);

                try {
                    empty  = c.getCurrShield().getName();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Shield", empty);

                try {
                    empty  = c.getNotes();
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Notes", empty);

                try {
                    mt  = c.getDamageTaken();
                } catch (NullPointerException npe) { mt = 0; }
                appendValue("DamageTaken", mt);

                try {
                    mt  = c.getPP();
                } catch (NullPointerException npe) { mt = 0; }
                appendValue("PP", mt);

                try {
                    mt  = c.getGP();
                } catch (NullPointerException npe) { mt = 0; }
                appendValue("GP", mt);

                try {
                    mt  = c.getSP();
                } catch (NullPointerException npe) { mt = 0; }
                appendValue("SP", mt);

                try {
                    mt  = c.getCP();
                } catch (NullPointerException npe) { mt = 0; }
                appendValue("CP", mt);

                // itemname;count
                try {
                    empty  = "";
                    for(int i = 0; i < c.getItems().size(); i++){
                        empty += c.getItems().get(i).getName() + ";" + c.getItems().get(i).getCount() + "/";
                    }
                } catch (NullPointerException npe) { empty = " "; }
                if (c.getItems().size() == 0) { empty = " "; }
                appendValue("Items", empty);


                try {
                    empty  = "";
                    for(int i = 0; i < c.getLanguages().size(); i++){
                        empty += c.getLanguages().get(i) + "/";
                    }
                } catch (NullPointerException npe) { empty = " "; }
                appendValue("Languages", empty);

                // weaponname;quantity
                try {
                    empty  = "";
                    for(int i = 0; i < c.getWeapons().size(); i++){
                        empty += c.getWeapons().get(i).getName() + ";" +  c.getWeapons().get(i).getCount() + "/";

                    }
                } catch (NullPointerException npe) { empty = " "; }
                if (c.getWeapons().size() == 0) { empty = " "; }
                appendValue("Weapons", empty); 

                // armorname;quantity
                try {
                    empty  = "";
                    for(int i = 0; i < c.getArmor().size(); i++){
                        empty += c.getArmor().get(i).getName()  + ";"+ c.getArmor().get(i).getCount() + "/";
                    }
                } catch (NullPointerException npe) { empty = " "; }
                if (c.getArmor().size() == 0) { empty = " "; }
                appendValue("Armors", empty); 

                // skillname;abilitymod;miscmod;rank 
                try {
                    empty  = "";
                    for(int i = 0; i < c.getSkills().size(); i++){
                        empty += c.getSkills().get(i).getSkill().getName() + ";" + c.getSkills().get(i).getAbilityMod() + ";" 
                                + c.getSkills().get(i).getMiscMod() + ";" + c.getSkills().get(i).getRank() + "/";
                    }
                } catch (NullPointerException npe) { empty = " "; }
                if (c.getSkills().size() == 0) { empty = " "; }
                appendValue("Skills", empty);


                // shieldname;quantity
                try {
                    empty  = "";
                    for(int i = 0; i < c.getShields().size(); i++){
                        empty += c.getShields().get(i).getName() + ";" + c.getShields().get(i).getCount() +  "/";
                    }
                } catch (NullPointerException npe) { empty = " "; }
                if (c.getShields().size() == 0) { empty = " "; }
                appendValue("Shields", empty);


                // featName;special;count
                try {
                    empty  = "";
                    for(int i = 0; i < c.getFeats().size(); i++){
                        empty += c.getFeats().get(i).getFeat().getName();
                        if (c.getFeats().get(i).getSpecial() != null)
                            empty += ";" + c.getFeats().get(i).getSpecial(); 
                        empty += ";" + c.getFeats().get(i).getCount() + "/";
                    }
                } catch (NullPointerException npe) { empty = " "; }
                if (c.getFeats().size() == 0) { empty = " "; }
                appendValue("Feats", empty); //*/
                bw.write("</Character>");
                bw.close();



                /*TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);

                //change back to character.getName() is not working correctly
                String charName = c.getName().replaceAll("[^A-Za-z0-9]", "");
                try{
                    File CHARACTER = new File(System.getProperty("user.dir") + "//" + "User Data" + "//" + "Character");
                    CHARACTER.mkdir();
                    File CHARDIR = new File(CHARACTER.getPath() + "//DND" + charName);
                    CHARDIR.mkdir();
                    StreamResult result = new StreamResult(CHARDIR.getPath() + "//DND" + charName + ".xml");
                    transformer.transform(source, result);
                }catch(Exception e){
                    e.printStackTrace();
                } // TODO end*/

            }
            // Not a new Character
            else {
                String filename = fn;
                stocks = new File(filename);
                dbFactory = DocumentBuilderFactory.newInstance();
                dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.parse(stocks);
                doc.getDocumentElement().normalize();

                NodeList nodes = doc.getElementsByTagName("Character");

                Node node = nodes.item(0);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    writeValue("Name", c.getName());
                    writeValue("Level", c.getLevel());
                    writeValue("Exp", c.getExp());
                    writeValue("Race", c.getCharRace().getName());
                    writeValue("Alignment", c.getAlignment());

                    writeValue("Deity", c.getDeity());
                    writeValue("Size", c.getSize());
                    writeValue("Age", c.getAge());
                    writeValue("Gender", c.getGender());
                    writeValue("Height", c.getHeight());

                    writeValue("Weight", c.getWeight());
                    writeValue("Eyes", c.getEyes());
                    writeValue("Hair", c.getHair());
                    writeValue("Skin", c.getSkin());
                    writeValue("Description", c.getDescription());

                    temp = "";
                    for(int i = 0; i < c.getSpells().size(); i++){
                        temp += c.getSpells().get(i).getName() + "/";
                    }
                    writeValue("Spells", temp);
                    temp = "";
                    for(int i = 0; i < c.getSpecialAbilities().size(); i++){
                        temp += c.getSpecialAbilities().get(i).getName() + "/";
                    }
                    writeValue("SpecialAbilities", temp);
                    temp = "";
                    for(int i = 0; i < c.getPrepSpells().size(); i++){
                        temp += c.getPrepSpells().get(i).getName() + "/";
                    }
                    writeValue("PreparedSpells", temp);
                    temp = Integer.toString(c.getAC()[0]);
                    for (int i = 0; i < c.getAC().length; i++)
                        temp += " + " + c.getAC()[i];
                    writeValue("AC", temp);
                    writeValue("TouchAC", c.getTouchAC());

                    writeValue("FlatFootedAC", c.getFlatFootedAC());
                    temp = Integer.toString(c.getInitMod()[0]);
                    for (int i = 0; i < c.getInitMod().length; i++)
                        temp += " + " + c.getInitMod()[i];
                    writeValue("Init", temp);
                    temp = Integer.toString(c.getFortSave()[0]);
                    for (int i = 0; i < c.getFortSave().length; i++)
                        temp += " + " + c.getFortSave()[i];
                    writeValue("Fortitude", temp);
                    temp = Integer.toString(c.getReflexSave()[0]);
                    for (int i = 0; i < c.getReflexSave().length; i++)
                        temp += " + " + c.getReflexSave()[i];
                    writeValue("Reflex", temp);
                    temp = Integer.toString(c.getWillSave()[0]);
                    for (int i = 0; i < c.getWillSave().length; i++)
                        temp += " + " + c.getWillSave()[i];
                    writeValue("Will", temp);

                    writeValue("BaseAttack", c.getBaseAttackBonus());
                    writeValue("SpellResistance", c.getSpellResistance());
                    writeValue("Speed", c.getSpeed());
                    temp = Integer.toString(c.getGrappleMod()[0]);
                    for (int i = 0; i < c.getGrappleMod().length; i++)
                        temp += " + " + c.getGrappleMod()[i];
                    writeValue("Grapple", temp);
                    writeValue("DamageReduction", c.getDamageReduction());

                    writeValue("ClericDomains", c.getName());//
                    writeValue("DruidCompanion", c.getDruidAnimalCompanion());
                    writeValue("RangerFavoredEnemy", c.getRangerFavoredEnemy());
                    writeValue("Familiar", c.getFamiliar());
                    writeValue("WizardSpecialty", c.getWizardSpecialtySchool());

                    temp = "";
                    for(int i = 0; i < c.getWizardProhibitedSchools().length; i++){
                        temp += c.getWizardProhibitedSchools()[i]+ "/";
                    }
                    writeValue("WizardProhibitedSchools", temp);
                    writeValue("Image", c.getImage());
                    writeValue("Class", c.getClass().getName());
                    writeValue("SecLevel", c.getSecLevel());
                    writeValue("SecClass", c.getSecClass().getName());

                    writeValue("STR", c.getAbilityModifiers()[0]);
                    writeValue("DEX", c.getAbilityModifiers()[1]);
                    writeValue("CON", c.getAbilityModifiers()[2]);
                    writeValue("INT", c.getAbilityModifiers()[3]);
                    writeValue("WIS", c.getAbilityModifiers()[4]);

                    writeValue("CHA", c.getAbilityModifiers()[5]);
                    writeValue("HP", c.getHitPoints());
                    writeValue("Speed", c.getSpeed());
                    writeValue("PrimaryWeapon", c.getPrimaryWeapon().getName());
                    writeValue("SecondaryWeapon", c.getSecondaryWeapon().getName());

                    writeValue("Armor", c.getCurrArmor().getName());
                    writeValue("Shield", c.getCurrShield().getName());
                    writeValue("Notes", c.getNotes());
                    writeValue("DamageTaken", c.getDamageTaken());
                    writeValue("PP", c.getPP());

                    writeValue("GP", c.getGP());
                    writeValue("SP", c.getSP());
                    writeValue("CP", c.getCP());
                    temp = "";
                    // itemname;count
                    for(int i = 0; i < c.getItems().size(); i++){
                        temp += c.getItems().get(i).getName() + ";" + c.getItems().get(i).getCount() + "/";
                    }
                    writeValue("Items", temp);
                    temp = "";
                    for(int i = 0; i < c.getLanguages().size(); i++){
                        temp += c.getLanguages().get(i) + "/";
                    }
                    writeValue("Languages", temp);
                    temp = "";
                    // weaponname;quantity
                    for(int i = 0; i < c.getWeapons().size(); i++){
                        temp += c.getWeapons().get(i).getName() + ";" +  c.getWeapons().get(i).getCount() + "/";
                    }
                    writeValue("Weapons", temp); 
                    temp = "";
                    // armorname;quantity
                    for(int i = 0; i < c.getArmor().size(); i++){
                        temp += c.getArmor().get(i).getName()  + ";"+ c.getArmor().get(i).getCount() + "/";
                    }
                    writeValue("Armors", temp); 
                    temp = "";
                    // skillname:abilitymod+miscmod+rank 
                    for(int i = 0; i < c.getSkills().size(); i++){
                        temp += c.getSkills().get(i).getSkill().getName() + ":" + c.getSkills().get(i).getAbilityMod() + "+" + c.getSkills().get(i).getMiscMod() + "+" + c.getSkills().get(i).getRank() + "/";
                    }
                    writeValue("Skills", temp);
                    temp = "";
                    // shieldname;quantity
                    for(int i = 0; i < c.getShields().size(); i++){
                        temp += c.getShields().get(i).getName() + ";" + c.getShields().get(i).getCount() +  "/";
                    }
                    writeValue("Shields", temp);
                    temp = "";
                    // featName:special;count
                    for(int i = 0; i < c.getFeats().size(); i++){
                        temp += c.getFeats().get(i).getFeat().getName();
                        if (c.getFeats().get(i).getSpecial() != null)
                            temp += ":" + c.getFeats().get(i).getSpecial(); 
                        temp += ";" + c.getFeats().get(i).getCount() + "/";
                    }
                    writeValue("Feats", temp);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public SaveCharacter(Boolean New) {
        this(New, c.getFilename());
    }

    private static boolean writeValue(String tag, String value){
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        node.setTextContent(value);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(stocks.getAbsolutePath());
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private static boolean writeValue(String tag, int value) {
        return writeValue(tag, Integer.toString(value));
    }

    private boolean appendValue(String tag, String value) {
        fo = "<" + tag + ">" + value + "</" + tag + ">\n";
        try {
            bw.write(fo);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*Element temp = doc.createElement(tag);
        temp.appendChild(doc.createTextNode(value));
        ele.appendChild(temp);*/
        return true;
    }

    private boolean appendValue(String tag, int value) {
        return appendValue(tag, Integer.toString(value));
    }

}
