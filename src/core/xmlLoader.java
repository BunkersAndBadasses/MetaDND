package core;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import entity.*;


public class xmlLoader implements Runnable{
	private Thread xmlLoadThread;
	public Thread getXmlLoadThread() {
		return xmlLoadThread;
	}

	public void setXmlLoadThread(Thread xmlLoadThread) {
		this.xmlLoadThread = xmlLoadThread;
	}

	private String threadName;
	
	
	xmlLoader(String name){
		threadName = name;
		//System.out.println("Creating " +  threadName );
	}

	public void loadFiles() throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Load the input XML document, parse it and return an instance of the
		// Document class.
		ArrayList<File> xmls = new ArrayList<File>();
		Files.walk(Paths.get("./XML")).forEach(filePath ->{
			if(filePath.getFileName().toString().contains(".xml")){
				if (Files.isRegularFile(filePath)) {
					xmls.add(filePath.toFile());	
				}
			}
			else
				;//System.out.println(filePath.getFileName() + " is not an XML file");
		});
		//xmls.forEach(File -> System.out.println(File.getName()));
		for (int x = 0; x < xmls.size(); x++) {
			Document document = builder.parse(xmls.get(x));
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				NodeList children = node.getChildNodes();
				LinkedHashMap<String, String> entity = new LinkedHashMap<String, String>();
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					for (int j = 0; j < children.getLength(); j++) {
						if (children.item(j) instanceof Element == false)
							continue;
						String name = children.item(j).getNodeName().trim();
						String content = children.item(j).getTextContent()
								.trim();
						entity.put(name, content);
					}
				}
				if (node.getNodeName() == "SPELL") {
					SpellEntity readSpell = new SpellEntity(entity);
					Main.gameState.spells.put(readSpell.getName(), readSpell);
					if(node.getParentNode().getNodeName() == "CUSTOM")
						Main.gameState.customContent.put(readSpell.getName(), readSpell);
				}
				else if (node.getNodeName() == "FEAT") {
					FeatEntity readFeat = new FeatEntity(entity);
					Main.gameState.feats.put(readFeat.getName(), readFeat);
					if(node.getParentNode().getNodeName() == "CUSTOM")
						Main.gameState.customContent.put(readFeat.getName(), readFeat);
				}
				else if(node.getNodeName() == "SKILL"){
					SkillEntity readSkill = new SkillEntity(entity);
					Main.gameState.skills.put(readSkill.getName(), readSkill);
					if(node.getParentNode().getNodeName() == "CUSTOM")
						Main.gameState.customContent.put(readSkill.getName(), readSkill);
				}
				else if(node.getNodeName() == "ITEM"){
					ItemEntity readItem = new ItemEntity(entity);
					Main.gameState.items.put(readItem.getName(), readItem);
					if(node.getParentNode().getNodeName() == "CUSTOM")
						Main.gameState.customContent.put(readItem.getName(), readItem);
				}
				else if(node.getNodeName() == "RACE"){
					RaceEntity readRace = new RaceEntity(entity);
					Main.gameState.races.put(readRace.getName(), readRace);
					if(node.getParentNode().getNodeName() == "CUSTOM")
						Main.gameState.customContent.put(readRace.getName(), readRace);
				}
				else if(node.getNodeName() == "CLASS"){
					ClassEntity readClass = new ClassEntity(entity);
					Main.gameState.classes.put(readClass.getName(), readClass);
					if(node.getParentNode().getNodeName() == "CUSTOM")
						Main.gameState.customContent.put(readClass.getName(), readClass);
				}
				else if(node.getNodeName() == "DEITY"){
					DeityEntity readDeity = new DeityEntity(entity);
					Main.gameState.deities.put(readDeity.getName(), readDeity);
					if(node.getParentNode().getNodeName() == "CUSTOM")
						Main.gameState.customContent.put(readDeity.getName(), readDeity);
				}
				else if(node.getNodeName() == "TRAP"){
					TrapEntity readTrap = new TrapEntity(entity);
					Main.gameState.traps.put(readTrap.getName(), readTrap);
					if(node.getParentNode().getNodeName() == "CUSTOM")
						Main.gameState.customContent.put(readTrap.getName(), readTrap);
				}
				else if(node.getNodeName() == "MONSTER"){
					MonsterEntity readMonster = new MonsterEntity(entity);
					Main.gameState.monsters.put(readMonster.getName(), readMonster);
					if(node.getParentNode().getNodeName() == "CUSTOM")
						Main.gameState.customContent.put(readMonster.getName(), readMonster);
				}
				else if(node.getNodeName() == "WEAPON"){
					WeaponEntity readWeapon = new WeaponEntity(entity);
					Main.gameState.weapons.put(readWeapon.getName(), readWeapon);
					if(node.getParentNode().getNodeName() == "CUSTOM")
						Main.gameState.customContent.put(readWeapon.getName(), readWeapon);
				}
				else if(node.getNodeName() == "ARMOR"){
					WeaponEntity readArmor = new WeaponEntity(entity);
					Main.gameState.armor.put(readArmor.getName(), readArmor);
					if(node.getParentNode().getNodeName() == "CUSTOM")
						Main.gameState.customContent.put(readArmor.getName(), readArmor);
				}
				else if(node.getNodeName() == "ABILITY"){
					AbilityEntity readAbility = new AbilityEntity(entity);
					Main.gameState.abilities.put(readAbility.getName(), readAbility);
					if(node.getParentNode().getNodeName() == "CUSTOM")
						Main.gameState.customContent.put(readAbility.getName(), readAbility);
				}
			}
		}
			
			for (Map.Entry<String, DNDEntity> entry : Main.gameState.races.entrySet()){
				if(((RaceEntity) entry.getValue()).getBonusLanguages()[0].startsWith("Any (other than")){
					String[] langs = new String[Main.gameState.languages.size()];
					int i = 0;
					for(Map.Entry<String, String> langEntry : Main.gameState.languages.entrySet()){
						langs[i] = langEntry.getValue();
						i++;
					}
					((RaceEntity) entry.getValue()).setBonusLanguages(langs);
				}
			}
	}

	@Override
	public void run() {
		try {
			loadFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("Ending " + threadName );
	}

	public void start()
	{
		//System.out.println("Starting " + threadName );
		if (xmlLoadThread == null)
		{
			xmlLoadThread = new Thread (this, threadName);
			xmlLoadThread.start();
		}
	}
}
