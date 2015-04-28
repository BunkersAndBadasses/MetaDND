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
    private String regex = "[;]+";
    private String str;

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
            String[] tempMiniArr;

            NodeList nodes = doc.getElementsByTagName("Character");

            Node node = nodes.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {


                element = (Element) node;

                str = getValue("Exp", element);
                if (str.equals(" ")) { str = "0"; }
                c.setExp(Integer.parseInt(str));

                str = getValue("Race", element);
                if (!str.equals(" ")) { 
                    c.setCharRace((RaceEntity) Main.gameState.races.get(str));
                }

                str = getValue("Alignment", element);
                if (!str.equals(" ")) { 
                    c.setAlignment(str);
                }

                str = getValue("Deity", element);
                if (!str.equals(" ")) { 
                    c.setDeity(str);
                }

                str = getValue("Size", element);
                if (str.equals(" ")) { str = "0"; }
                c.setSize(Integer.parseInt(str));

                str = getValue("Age", element);
                //if (str.equals(" ")) { str = "0"; }
                c.setAge(str);

                str = getValue("Gender", element);
                //if (str.equals(" ")) { str = "0"; }
                c.setGender(str);

                str = getValue("Height", element);
                //if (str.equals(" ")) { str = "0"; }
                c.setHeight(getValue("Height", element));

                str = getValue("Weight", element);
                //if (str.equals(" ")) { str = "0"; }
                c.setWeight(str);

                str = getValue("Eyes", element);
                //if (str.equals(" ")) { str = "0"; }
                c.setEyes(str);

                str = getValue("Hair", element);
                //if (str.equals(" ")) { str = "0"; }
                c.setHair(str);

                str = getValue("Skin", element);
                //if (str.equals(" ")) { str = "0"; }
                c.setSkin(str);

                str = getValue("Description", element);
                //if (str.equals(" ")) { str = "0"; }
                c.setDescription(str);

                str = getValue("SpecialAbilities", element);
                if (!str.equals(" ")) {
                    tempArr = str.split(delims);
                    for(int i = 0; i < tempArr.length; i ++){
                        name = tempArr[i].split(regex)[0];
                        description = tempArr[i].split(regex)[1];
                        AbilityEntity ae = new AbilityEntity(name, description);
                        c.addSpecialAbility(ae);
                    }
                }

                str = getValue("Spells", element);
                if (!str.equals(" ")) {
                    tempArr = str.split(delims);
                    for(int i = 0; i < tempArr.length; i ++) {
                        c.addSpell((SpellEntity) Main.gameState.spells.get(tempArr[i]));
                    }
                }

                str = getValue("PreparedSpells", element);
                if (!str.equals(" ")) {
                    tempArr = str.split(delims);
                    for(int i = 0; i < tempArr.length; i ++) {
                        c.prepSpell((SpellEntity) Main.gameState.spells.get(tempArr[i]));
                    }
                }

                String[] acString = getValue("AC", element).split(delims);
                int[] ac = new int[acString.length];
                for (int i = 0; i < acString.length; i++) {
                    ac[i] = Integer.parseInt(acString[i]);
                }
                c.setAC(ac);

                String[] initString = getValue("Init", element).split(delims);
                int[] init = new int[initString.length];
                for (int i = 0; i < initString.length; i++) {
                    init[i] = Integer.parseInt(initString[i]);
                }
                c.setInitMod(init);

                String[] fortString = getValue("Fortitude", element).split(delims);
                int[] fort = new int[fortString.length];
                for (int i = 0; i < fortString.length; i++) {
                    fort[i] = Integer.parseInt(fortString[i]);
                }

                String[] reflexString = getValue("Reflex", element).split(delims);
                int[] reflex = new int[reflexString.length];
                for (int i = 0; i < reflexString.length; i++) {
                    reflex[i] = Integer.parseInt(reflexString[i]);
                }

                String[] willString = getValue("Will", element).split(delims);
                int[] will = new int[willString.length];
                for (int i = 0; i < willString.length; i++) {
                    will[i] = Integer.parseInt(willString[i]);
                }
                c.setSavingThrows(fort, reflex, will); // TODO

                str = getValue("BaseAttack", element);
                if (!str.equals(" ")) { 
                    c.setBaseAttackBonus(Integer.parseInt(str));
                }

                str = getValue("SpellResistance", element);
                if (!str.equals(" ")) {
                    c.setSpellResistance(Integer.parseInt(str));
                }


                String[] grappleString = getValue("Grapple", element).split(delims);
                int[] grapple = new int[grappleString.length];
                for (int i = 0; i < grappleString.length; i++) {
                    grapple[i] = Integer.parseInt(grappleString[i]);
                }
                c.setGrappleMod(grapple);

                str = getValue("Speed", element);
                if (str.equals(" ")) { str = "30"; }
                c.setSpeed(Integer.parseInt(str));

                str = getValue("DamageReduction", element);
                if (str.equals(" ")) { str = "0"; }
                c.setDamageReduction(Integer.parseInt(str));

                str = getValue("ClericDomains", element);
                if (!str.equals(" ")) { 
                    tempArr = str.split(delims);
                    c.setClericDomains(tempArr);
                }

                str = getValue("DruidCompanion", element);
                if (!str.equals(" ")) {
                    c.setDruidAnimalCompanion(str);
                }

                str = getValue("RangerFavoredEnemy", element);
                if (!str.equals(" ")) {
                    c.setRangerFavoredEnemy(str);
                }

                str = getValue("Familiar", element);
                if (!str.equals(" ")) {
                    c.setFamiliar(str);
                }

                str = getValue("WizardSpecialty", element);
                if (!str.equals(" ")) {
                    c.setWizardSpecialtySchool(str);
                }

                str = getValue("WizardProhibitedSchools", element);
                if (!str.equals(" ")) {
                    tempArr= str.split(delims);
                    c.setWizardProhibitedSchools(tempArr);
                }

                str = getValue("Image", element);
                c.setImage(str);

                str = getValue("Name", element);
                c.setName(str);

                str = getValue("Level", element);
                if (!str.equals(" ")) { str = "1"; }
                c.setLevel(Integer.parseInt(str));

                str = getValue("Class", element);
                if (!str.equals(" ")) {
                    c.setCharClass((ClassEntity) Main.gameState.classes.get(str));
                }

                str = getValue("SecLevel", element);
                if (!str.equals(" ")) { str = "0"; }
                c.setSecLevel(Integer.parseInt(str));

                str = getValue("SecClass", element);
                if (!str.equals(" ")) {
                    c.setCharSecClass((ClassEntity) Main.gameState.classes.get(str));
                }

                // Should be fine
                c.setAbilityScores(Integer.parseInt(getValue("STR", element)),
                        Integer.parseInt(getValue("DEX", element)),
                        Integer.parseInt(getValue("CON", element)),
                        Integer.parseInt(getValue("INT", element)),
                        Integer.parseInt(getValue("WIS", element)),
                        Integer.parseInt(getValue("CHA", element)));


                c.setHitPoints(Integer.parseInt(getValue("HP", element)));

                str = getValue("PrimaryWeapon", element);
                if (!str.equals(" ")) {
                    c.setPrimaryWeapon((WeaponEntity) Main.gameState.weapons.get(str));
                }

                str = getValue("SecondaryWeapon", element);
                if (!str.equals(" ")) {
                    c.setSecondaryWeapon((WeaponEntity) Main.gameState.weapons.get(str));
                }

                str = getValue("Armor", element);
                if (!str.equals(" ")) {
                    c.setCurrArmor((ArmorEntity) Main.gameState.armor.get(str));
                }

                str = getValue("Shield", element);
                if (!str.equals(" ")) {
                    c.setCurrShield((ArmorEntity) Main.gameState.armor.get(str));
                }


                c.setNotes(getValue("Notes", element));
                c.setDamageTaken(Integer.parseInt(getValue("DamageTaken", element)));
                c.setPP(Integer.parseInt(getValue("PP", element)));
                c.setGP(Integer.parseInt(getValue("GP", element)));
                c.setSP(Integer.parseInt(getValue("SP", element)));
                c.setCP(Integer.parseInt(getValue("CP", element)));


                str = getValue("Items", element);
                if (!str.equals(" ")) {
                    tempArr = str.split(delims);
                    // itemname;count
                    for(int i = 0; i < tempArr.length; i++){
                        tempMiniArr = tempArr[i].split(regex);
                        String itemName = tempMiniArr[0];
                        ItemEntity item = (ItemEntity)Main.gameState.items.get(itemName);
                        int count = Integer.parseInt(tempMiniArr[1]);
                        CharItem ci = new CharItem(item, count);
                        c.addItem(ci);
                    }
                }

                // Going to assume this is good
                temp = getValue("Languages", element);
                c.setLanguages(new ArrayList<String>(Arrays.asList(temp.split(delims))));

                str = getValue("Weaponms", element);
                if (!str.equals(" ")) {
                    tempArr = str.split(delims);
                    // weaponname;quantity
                    for(int i = 0; i < tempArr.length; i++){
                        tempMiniArr = tempArr[i].split(regex);
                        temp = tempMiniArr[0];
                        int count = Integer.parseInt(tempMiniArr[1]); 
                        WeaponEntity we = (WeaponEntity) (Main.gameState.weapons.get(temp));
                        CharItem ci = new CharItem(we, count);
                        c.addWeapon(ci);
                    }
                }

                temp = getValue("Armors", element);
                tempArr = temp.split(delims);
                // armorname;quantity
                for(int i = 0; i < tempArr.length; i++){
                    temp = tempArr[i].replaceAll("[^a-zA-Z;]", "");
                    int count = Integer.parseInt(tempArr[i].replaceAll("[^\\d.]", "")); 
                    ItemEntity ae = (ItemEntity) (Main.gameState.armor.get(temp)); // some in armor list are weapons
                    CharItem ci = new CharItem(ae,count);
                    c.addArmor(ci);
                }

                temp = getValue("Skills", element);
                tempArr = temp.split(delims);
                // skillname:abilitymod+miscmod+rank
                for(int i = 0; i < tempArr.length; i++){
                    temp = tempArr[i].replaceAll("[^a-zA-Z:+]", "");
                    int count = Integer.parseInt(tempArr[i].replaceAll("[^\\d.]", "")); 
                    CharSkill cs =  new CharSkill((SkillEntity) (Main.gameState.skills.get(temp)),c);
                    cs.setRank(count);
                    // TODO this needs to change. ability mod and misc mod need to be saved to char skill
                }

                temp = getValue("Shields", element);
                tempArr = temp.split(delims);
                // shieldname;quantity
                for(int i = 0; i < tempArr.length; i++){
                    temp = tempArr[i].replaceAll("[^a-zA-Z;]", "");
                    int count = Integer.parseInt(tempArr[i].replaceAll("[^\\d.]", "")); 
                    ItemEntity ae = (ItemEntity) (Main.gameState.armor.get(temp));  // some in armor list are weapons
                    CharItem ci = new CharItem(ae, count);
                    c.addShield(ci);
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


