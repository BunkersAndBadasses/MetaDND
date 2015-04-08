package core;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import entity.*;


public class xmlLoader implements Runnable{
	private Thread xmlLoadThread;
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
		Files.walk(Paths.get(".\\XML")).forEach(filePath ->{
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
						// System.out.println(name);
						// System.out.println(content);
					}
				}
				if (node.getNodeName() == "SPELL") {
					SpellEntity testSpell = new SpellEntity(entity);
					Main.gameState.spells.put(testSpell.getName(), testSpell);
//					if(testSpell.getName().equalsIgnoreCase("acid arrow"))
//						testSpell.toTooltipWindow();
				}
				else if (node.getNodeName() == "FEAT") {
					FeatEntity testFeat = new FeatEntity(entity);
					Main.gameState.feats.put(testFeat.getName(), testFeat);
					if(testFeat.getName().equalsIgnoreCase("track"))
					testFeat.toTooltipWindow();
				}
				else if(node.getNodeName() == "SKILL"){
					SkillEntity testSkill = new SkillEntity(entity);
					Main.gameState.skills.put(testSkill.getName(), testSkill);
				}
				else if(node.getNodeName() == "ITEM"){
					ItemEntity testItem = new ItemEntity(entity);
					Main.gameState.items.put(testItem.getName(), testItem);
//					if(testItem.getName().equalsIgnoreCase("thunderstone"))
//						testItem.toTooltipWindow();
				}
				else if(node.getNodeName() == "RACE"){
					RaceEntity testRace = new RaceEntity(entity);
					Main.gameState.races.put(testRace.getName(), testRace);
//					if(testRace.getName().equalsIgnoreCase("halfling"))
//						testRace.toTooltipWindow();
				}
				else if(node.getNodeName() == "CLASS"){
					ClassEntity testClass = new ClassEntity(entity);
					Main.gameState.classes.put(testClass.getName(), testClass);
//					if(testClass.getName().equalsIgnoreCase("barbarian"))
//						testClass.toTooltipWindow();
				}
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
