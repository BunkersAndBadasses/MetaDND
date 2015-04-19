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

import entity.ArmorEntity;
import entity.ClassEntity;
import entity.FeatEntity;
import entity.ItemEntity;
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

    public LoadCharacter(String xmlLocation, character loadChar){
        try {
            character c = loadChar;
            stocks = new File(xmlLocation);
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(stocks);
            doc.getDocumentElement().normalize();
            String temp;

            NodeList nodes = doc.getElementsByTagName("Character");

            Node node = nodes.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;
                c.setImage(getValue("Image", element));
                c.setName(getValue("Name", element));
                c.setLevel(Integer.parseInt(getValue("Level", element)));
                temp = getValue("Class", element);
                c.setCharClass((ClassEntity) Main.gameState.classes.get(temp));
                c.setSecLevel(Integer.parseInt(getValue("SecLevel", element)));
                temp = getValue("SecClass", element);
                c.setCharClass((ClassEntity) Main.gameState.classes.get(temp));

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
                c.setGold(Integer.parseInt(getValue("GP", element)));
                c.setSP(Integer.parseInt(getValue("SP", element)));
                c.setCP(Integer.parseInt(getValue("CP", element)));

                temp = getValue("Items", element);
                String[] tempArr = temp.split(delims);
                for(int i = 0; i < tempArr.length; i++){
                    temp = tempArr[i].replaceAll("[^a-zA-Z]", "");
                    int count = Integer.parseInt(tempArr[i].replaceAll("[^\\d.]", "")); 
                    CharItem ci = new CharItem((ItemEntity) Main.gameState.items.get(tempArr[i]));
                    ci.setCouunt(count);
                    c.addItem(ci);
                }

                temp = getValue("Languages", element);
                c.setLanguages(new ArrayList<String>(Arrays.asList(temp.split(delims))));

                temp = getValue("Weapons", element);
                tempArr = temp.split(delims);
                for(int i = 0; i < tempArr.length; i++){
                    temp = tempArr[i].replaceAll("[^a-zA-Z]", "");
                    int count = Integer.parseInt(tempArr[i].replaceAll("[^\\d.]", "")); 
                    WeaponEntity we = (WeaponEntity) (Main.gameState.weapons.get(tempArr[i]));
                    we.setQuanitity(count);
                    c.addWeapon(we);
                }

                temp = getValue("Armors", element);
                tempArr = temp.split(delims);
                for(int i = 0; i < tempArr.length; i++){
                    temp = tempArr[i].replaceAll("[^a-zA-Z]", "");
                    int count = Integer.parseInt(tempArr[i].replaceAll("[^\\d.]", "")); 
                    ArmorEntity ae = (ArmorEntity) (Main.gameState.armor.get(tempArr[i]));
                    ae.setQuanitity(count);
                    c.addArmor(ae);
                }

                temp = getValue("Skills", element);
                tempArr = temp.split(delims);
                for(int i = 0; i < tempArr.length; i++){
                    temp = tempArr[i].replaceAll("[^a-zA-Z]", "");
                    int count = Integer.parseInt(tempArr[i].replaceAll("[^\\d.]", "")); 
                    CharSkill cs =  new CharSkill((SkillEntity) (Main.gameState.skills.get(tempArr[i])),c);
                    cs.setRank(count);
                }

                temp = getValue("Spells", element);
                tempArr = temp.split(delims);
                for(int i = 0; i < tempArr.length; i++){
                    temp = tempArr[i].replaceAll("[^a-zA-Z]", "");
                    SpellEntity se =  (SpellEntity) (Main.gameState.spells.get(tempArr[i]));
                    c.addSpell(se);
                }

                temp = getValue("Shields", element);
                tempArr = temp.split(delims);
                for(int i = 0; i < tempArr.length; i++){
                    temp = tempArr[i].replaceAll("[^a-zA-Z]", "");
                    int count = Integer.parseInt(tempArr[i].replaceAll("[^\\d.]", "")); 
                    ArmorEntity ae = (ArmorEntity) (Main.gameState.armor.get(tempArr[i]));
                    ae.setQuanitity(count);
                    c.addShield(ae);
                }

                temp = getValue("Feats", element);
                tempArr = temp.split(delims);
                for(int i = 0; i < tempArr.length; i++){
                    temp = tempArr[i].replaceAll("[^a-zA-Z]", "");
                    FeatEntity fe = (FeatEntity) (Main.gameState.feats.get(tempArr[i]));
                    c.addFeat(fe);
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


