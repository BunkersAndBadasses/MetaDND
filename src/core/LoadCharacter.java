package core;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import entity.ClassEntity;

public class LoadCharacter {
    
    private File stocks;
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;
    private Element element;

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
                c.set = Integer.parseInt(getValue("SecLevel", element));
                charSecClass = getValue("SecClass", element);
                strVal = Integer.parseInt(getValue("STR", element));
                dexVal = Integer.parseInt(getValue("DEX", element));
                conVal = Integer.parseInt(getValue("CON", element));
                intVal = Integer.parseInt(getValue("INT", element));
                wisVal = Integer.parseInt(getValue("WIS", element));
                chaVal = Integer.parseInt(getValue("CHA", element));
                hpVal = Integer.parseInt(getValue("HP", element));
                speedVal = Integer.parseInt(getValue("Speed", element));
                priWeapon = getValue("PrimaryWeapon", element);
                secWeapon =  getValue("SecondaryWeapon", element);
                armorName = getValue("Armor", element);
                shieldName  = getValue("Shield", element);
                notes = getValue("Notes", element);
                dmgTaken = getValue("DamageTaken", element);
                pp = getValue("PP", element);
                gp = getValue("GP", element);
                sp = getValue("SP", element);
                cp = getValue("CP", element);

                String raw = getValue("Items", element);
                items = raw.split(delims);

                raw = getValue("Languages", element);
                languages = raw.split(delims);

                raw = getValue("Weapons", element);
                weapons = raw.split(delims);

                raw = getValue("Armors", element);
                armors = raw.split(delims);

                raw = getValue("Skills", element);
                skills = raw.split(delims);

                raw = getValue("Spells", element);
                spells = raw.split(delims);

                raw = getValue("Shields", element);
                shields = raw.split(delims);
                
                raw = getValue("Feats", element);
                feats = raw.split(delims);

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


