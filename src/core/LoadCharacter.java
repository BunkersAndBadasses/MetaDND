package core;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import entity.AbilityEntity;
import entity.ArmorEntity;
import entity.ClassEntity;
import entity.FeatEntity;
import entity.ItemEntity;
import entity.RaceEntity;
import entity.SkillEntity;
import entity.SpellEntity;
import entity.WeaponEntity;

public class LoadCharacter {

    private File stocks;
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;
    private Element element;
    private String delims = "[/]+"; 
    private String regex = "[?]+";

    public LoadCharacter(String xmlLocation, character loadChar){
        try {
            character c = loadChar;
            c.setFilename(xmlLocation);
            stocks = new File(c.getFilename());
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(stocks);
            doc.getDocumentElement().normalize();
            String temp, name, description;
            String[] tempArr;

            NodeList nodes = doc.getElementsByTagName("Character");

            Node node = nodes.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {


                element = (Element) node;
                
                c.setExp(Integer.parseInt(getValue("EXP", element)));
                c.setCharRace((RaceEntity) Main.gameState.races.get(getValue("Race", element)));
                c.setAlignment(getValue("Alignment", element));
                c.setDeity(getValue("Deity", element));
                c.setSize(Integer.parseInt(getValue("Size", element)));
                c.setAge(getValue("Age", element));
                c.setGender(getValue("Gender", element));
                c.setHeight(getValue("Height", element));
                c.setWeight(getValue("Weight", element));
                c.setEyes(getValue("Eyes", element));
                c.setHair(getValue("Hair", element));
                c.setSkin(getValue("Skin", element));
                c.setDescription(getValue("Description", element));
                
                temp = getValue("SpecialAbilities", element);
                tempArr = temp.split(delims);
                for(int i = 0; i < tempArr.length; i ++){
                    name = tempArr[i].split(regex)[0];
                    description = tempArr[i].split(regex)[1];
                    AbilityEntity ae = new AbilityEntity(name, description);
                    c.addSpecialAbility(ae);
                }
                
                temp = getValue("Spells", element);
                tempArr = temp.split(delims);
                for(int i = 0; i < tempArr.length; i ++) {
                    c.addSpell((SpellEntity) Main.gameState.spells.get(tempArr[i]));
                }
                
                getValue("PreparedSpells", element);
                tempArr = temp.split(delims);
                for(int i = 0; i < tempArr.length; i ++) {
                    c.prepSpell((SpellEntity) Main.gameState.spells.get(tempArr[i]));
                }
                String[] acString = getValue("AC", element).split(" + ");
                int[] ac = new int[acString.length];
                for (int i = 0; i < acString.length; i++) {
                	ac[i] = Integer.parseInt(acString[i]);
                }
                c.setAC(ac);
                String[] initString = getValue("Init", element).split(" + ");
                int[] init = new int[initString.length];
                for (int i = 0; i < initString.length; i++) {
                	init[i] = Integer.parseInt(initString[i]);
                }
                c.setInitMod(init);
                String[] fortString = getValue("Fortitude", element).split(" + ");
                int[] fort = new int[fortString.length];
                for (int i = 0; i < fortString.length; i++) {
                	fort[i] = Integer.parseInt(fortString[i]);
                }
                String[] reflexString = getValue("Reflex", element).split(" + ");
                int[] reflex = new int[reflexString.length];
                for (int i = 0; i < reflexString.length; i++) {
                	reflex[i] = Integer.parseInt(reflexString[i]);
                }
                String[] willString = getValue("Will", element).split(" + ");
                int[] will = new int[willString.length];
                for (int i = 0; i < willString.length; i++) {
                	will[i] = Integer.parseInt(willString[i]);
                }
                c.setSavingThrows(fort, reflex, will);
                c.setBaseAttackBonus(Integer.parseInt(getValue("BaseAttack", element)));//
                c.setSpellResistance(Integer.parseInt(getValue("SpellResistance", element)));
                String[] grappleString = getValue("Grapple", element).split(" + ");
                int[] grapple = new int[grappleString.length];
                for (int i = 0; i < grappleString.length; i++) {
                	grapple[i] = Integer.parseInt(grappleString[i]);
                }
                c.setGrappleMod(grapple);
                c.setSpeed(Integer.parseInt(getValue("Speed", element)));
                c.setDamageReduction(Integer.parseInt(getValue("DamageReduction", element)));
                
                temp = getValue("ClericDomains", element);
                tempArr = temp.split(delims);
                c.setClericDomains(tempArr);
                
                c.setDruidAnimalCompanion(getValue("DruidCompanion", element));
                c.setRangerFavoredEnemy(getValue("RangerFavoredEnemy", element));
                c.setFamiliar(getValue("Familiar", element));
                c.setWizardSpecialtySchool(getValue("WizardSpecialty", element));
                
                temp = getValue("WizardProhibitedSchools", element);
                tempArr= temp.split(delims);
                c.setWizardProhibitedSchools(tempArr);


                c.setImage(getValue("Image", element));
                c.setName(getValue("Name", element));
                c.setLevel(Integer.parseInt(getValue("Level", element)));
                temp = getValue("Class", element);
                c.setCharClass((ClassEntity) Main.gameState.classes.get(temp));
                c.setSecLevel(Integer.parseInt(getValue("SecLevel", element)));
                temp = getValue("SecClass", element);
                c.setCharSecClass((ClassEntity) Main.gameState.classes.get(temp));

                c.setAbilityScores(Integer.parseInt(getValue("STR", element)),
                        Integer.parseInt(getValue("DEX", element)),
                        Integer.parseInt(getValue("CON", element)),
                        Integer.parseInt(getValue("INT", element)),
                        Integer.parseInt(getValue("WIS", element)),
                        Integer.parseInt(getValue("CHA", element)));
                c.setHitPoints(Integer.parseInt(getValue("HP", element)));
                c.setSpeed(Integer.parseInt(getValue("Speed", element)));

                temp = getValue("PrimaryWeapon", element);
                c.setPrimary((WeaponEntity) Main.gameState.weapons.get(temp));
                temp =  getValue("SecondaryWeapon", element);
                c.setSecondary((WeaponEntity) Main.gameState.weapons.get(temp));

                temp = getValue("Armor", element);
                c.setCurrArmor((ArmorEntity) Main.gameState.armor.get(temp));
                temp  = getValue("Shield", element);
                c.setShield((ArmorEntity) Main.gameState.armor.get(temp));
                c.setNotes(getValue("Notes", element));
                c.setDamageTaken(Integer.parseInt(getValue("DamageTaken", element)));
                c.setPP(Integer.parseInt(getValue("PP", element)));
                c.setGP(Integer.parseInt(getValue("GP", element)));
                c.setSP(Integer.parseInt(getValue("SP", element)));
                c.setCP(Integer.parseInt(getValue("CP", element)));

                temp = getValue("Items", element);
                tempArr = temp.split(delims);
                // itemname;count
                for(int i = 0; i < tempArr.length; i++){
                    //temp = tempArr[i].replaceAll("[^a-zA-Z;]", "");
                	String itemName = tempArr[i].substring(0, tempArr[i].indexOf(';'));
                	ItemEntity item = (ItemEntity)Main.gameState.items.get(itemName);
                	//TODO here
                    int count = Integer.parseInt(tempArr[i].replaceAll("[^\\d.]", "")); 
                    CharItem ci = new CharItem((ItemEntity) Main.gameState.items.get(temp));
                    ci.setCount(count);
                    c.addItem(ci);
                }

                temp = getValue("Languages", element);
                c.setLanguages(new ArrayList<String>(Arrays.asList(temp.split(delims))));

                temp = getValue("Weapons", element);
                tempArr = temp.split(delims);
                // weaponname;quantity
                for(int i = 0; i < tempArr.length; i++){
                    temp = tempArr[i].replaceAll("[^a-zA-Z;]", "");
                    int count = Integer.parseInt(tempArr[i].replaceAll("[^\\d.]", "")); 
                    WeaponEntity we = (WeaponEntity) (Main.gameState.weapons.get(temp));
                    we.setQuanitity(count);
                    c.addWeapon(we);
                }

                temp = getValue("Armors", element);
                tempArr = temp.split(delims);
                // armorname;quantity
                for(int i = 0; i < tempArr.length; i++){
                    temp = tempArr[i].replaceAll("[^a-zA-Z;]", "");
                    int count = Integer.parseInt(tempArr[i].replaceAll("[^\\d.]", "")); 
                    ArmorEntity ae = (ArmorEntity) (Main.gameState.armor.get(temp));
                    ae.setQuanitity(count);
                    c.addArmor(ae);
                }

                temp = getValue("Skills", element);
                tempArr = temp.split(delims);
                // skillname:abilitymod+miscmod+rank
                for(int i = 0; i < tempArr.length; i++){
                    temp = tempArr[i].replaceAll("[^a-zA-Z:+]", "");
                    int count = Integer.parseInt(tempArr[i].replaceAll("[^\\d.]", "")); 
                    CharSkill cs =  new CharSkill((SkillEntity) (Main.gameState.skills.get(temp)),c);
                    cs.setRank(count);
                }

                temp = getValue("Shields", element);
                tempArr = temp.split(delims);
                // shieldname;quantity
                for(int i = 0; i < tempArr.length; i++){
                    temp = tempArr[i].replaceAll("[^a-zA-Z;]", "");
                    int count = Integer.parseInt(tempArr[i].replaceAll("[^\\d.]", "")); 
                    ArmorEntity ae = (ArmorEntity) (Main.gameState.armor.get(temp));
                    ae.setQuanitity(count);
                    c.addShield(ae);
                }

                temp = getValue("Feats", element);
                tempArr = temp.split(delims);
                // featName:special;count
                for(int i = 0; i < tempArr.length; i++){
                	String featName = tempArr[i].substring(0, tempArr[i].indexOf(':'));
                	FeatEntity feat = (FeatEntity)Main.gameState.feats.get(featName);
                	int count = Integer.parseInt(tempArr[i].substring(tempArr[i].indexOf(';')+1));
                	if (tempArr[i].indexOf(':')!= -1) {
                		String special = tempArr[i].substring(tempArr[i].indexOf(':')+1, tempArr[i].indexOf(';'));
                		c.addFeat(new CharFeat(feat, special, count));
                	} else
                		c.addFeat(new CharFeat(feat, count));
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }
}


