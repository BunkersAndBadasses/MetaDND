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
	public LinkedHashMap<String, DNDEntity> spells;
	public LinkedHashMap<String, DNDEntity> feats;
	public LinkedHashMap<String, DNDEntity> skills;
	
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
		Files.walk(Paths.get(".\\XML")).forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				xmls.add(filePath.toFile());	
			}
		});
		spells = new LinkedHashMap<String, DNDEntity>();
		feats = new LinkedHashMap<String, DNDEntity>();
		skills = new LinkedHashMap<String, DNDEntity>();
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
					spells.put(testSpell.getName(), testSpell);
					if(testSpell.getName().equalsIgnoreCase("acid arrow"))
						testSpell.toTooltipWindow();
					
				}
				else if (node.getNodeName() == "FEAT") {
					FeatEntity testFeat = new FeatEntity(entity);
					feats.put(testFeat.getName(), testFeat);
				}
				else if(node.getNodeName() == "SKILL"){
					SkillEntity testSkill = new SkillEntity(entity);
					skills.put(testSkill.getName(), testSkill);
				}
			}
		}
		spells.size(); //Literally here so I can put a breakpoint
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
