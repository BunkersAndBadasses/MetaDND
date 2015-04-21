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
		System.out.println("Creating " +  threadName );
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
				System.out.println(filePath.getFileName() + " is not an XML file");
		});
		xmls.forEach(File -> System.out.println(File.getName()));
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
					SpellEntity testSpell = new SpellEntity(entity);
					Main.gameState.spells.put(testSpell.getName(), testSpell);
				}
				else if (node.getNodeName() == "FEAT") {
					FeatEntity testFeat = new FeatEntity(entity);
					Main.gameState.feats.put(testFeat.getName(), testFeat);
				}
				else if(node.getNodeName() == "SKILL"){
					SkillEntity testSkill = new SkillEntity(entity);
					Main.gameState.skills.put(testSkill.getName(), testSkill);
				}
				else if(node.getNodeName() == "ITEM"){
					ItemEntity testItem = new ItemEntity(entity);
					Main.gameState.items.put(testItem.getName(), testItem);
				}
				else if(node.getNodeName() == "RACE"){
					RaceEntity testRace = new RaceEntity(entity);
					Main.gameState.races.put(testRace.getName(), testRace);
				}
				else if(node.getNodeName() == "CLASS"){
					ClassEntity testClass = new ClassEntity(entity);
					Main.gameState.classes.put(testClass.getName(), testClass);
				}
				else if(node.getNodeName() == "DEITY"){
					DeityEntity testDeity = new DeityEntity(entity);
					Main.gameState.deities.put(testDeity.getName(), testDeity);
				}
				else if(node.getNodeName() == "TRAP"){
					TrapEntity testTrap = new TrapEntity(entity);
					Main.gameState.traps.put(testTrap.getName(), testTrap);
				}
				else if(node.getNodeName() == "MONSTER"){
					MonsterEntity testMonster = new MonsterEntity(entity);
					Main.gameState.monsters.put(testMonster.getName(), testMonster);
				}
				else if(node.getNodeName() == "WEAPON"){
					WeaponEntity testWeapon = new WeaponEntity(entity);
					Main.gameState.weapons.put(testWeapon.getName(), testWeapon);
				}
				else if(node.getNodeName() == "ARMOR"){
					WeaponEntity testArmor = new WeaponEntity(entity);
					Main.gameState.armor.put(testArmor.getName(), testArmor);
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
		System.out.println("Ending " + threadName );
	}

	public void start()
	{
		System.out.println("Starting " + threadName );
		if (xmlLoadThread == null)
		{
			xmlLoadThread = new Thread (this, threadName);
			xmlLoadThread.start();
		}
	}
}
