package core;

/*
 * XML saving from 
 * http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
 * 
 * XML loading from 
 * http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 * 
 */

//NON-BRANCH WORK
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.*;

import core.Roll;

public class DnDie {

	static Random rng = new Random();
	
	// Rolls the number of a given die (ie rolls 5 20s)
	public static int roll (int die, int dieCount){

		int number = 0;

		for(int i = 0; i < dieCount; i ++){
			number += rng.nextInt(die) + 1;
		}

		return number;
	}
	
	//roll the die, and add in the modifier
	public static int roll (int die, int dieCount, int modifier){

		int number = modifier;

		for(int i = 0; i < dieCount; i ++){
			number += rng.nextInt(die) + 1;
		}
		
		return number;
	}

	// loads the die, and then sends them off to get rolled
	public static int rollFavDie(String fileName){
		
		ArrayList<Roll> loaded = new ArrayList<Roll>(10);
		loaded = loadFavDie(fileName);
		int rolls = 0;
		
		for(int i = 0; i < loaded.size(); i++){
			if(loaded.get(i).getModifier() != 0)
				rolls += roll(loaded.get(i).getDieSize(), 
						loaded.get(i).getDieCount());
			else
				rolls += roll(loaded.get(i).getDieSize(), 
						loaded.get(i).getDieCount(), 
						loaded.get(i).getModifier());
		}

		return rolls;
	}

	// save the favorite die selection to the xml
	public static void saveFavDie(String fileName, ArrayList<Roll> roll){
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(fileName);
			doc.appendChild(rootElement);

			for(int i = 0; i < roll.size(); i++){
				// Die elements
				Element Die = doc.createElement("Die");
				rootElement.appendChild(Die);

				// What die is being saved
				Element dieSize = doc.createElement("die_Size");
				dieSize.appendChild(doc.createTextNode(
						Integer.toString(roll.get(i).getDieSize())));
				Die.appendChild(dieSize);

				// shorten way
				// Die.setAttribute("id", "1");

				// The number of die
				Element dieCount = doc.createElement("die_Count");
				dieCount.appendChild(doc.createTextNode(
						Integer.toString(roll.get(i).getDieCount())));
				Die.appendChild(dieCount);

				// The modifier on the die roll (if applies)
				//TODO Need to check to see if there is a modifier or player thingy
				Element modifier = doc.createElement("modifier");
				modifier.appendChild(doc.createTextNode(
						Integer.toString(roll.get(i).getModifier())));
				Die.appendChild(modifier);

				// What is this player level Adjustment?
				//TODO Need to check to see if there is a modifier or player thingy
				Element playerLevelAdjust = doc.createElement("player_Level_Adjust");
				playerLevelAdjust.appendChild(doc.createTextNode(
						Integer.toString(roll.get(i).getPlayerLevelAdjust())));
				Die.appendChild(playerLevelAdjust);

			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("favRolls/" + fileName + ".xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
		return;
	}

	// load the favorite die selection from the xml
	public static ArrayList<Roll> loadFavDie(String fileName){

		ArrayList<Roll> roll = new ArrayList<Roll>(50);

		try {

			File fXmlFile = new File("favRolls/" + fileName + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Favorite_Die: " + doc.getDocumentElement().getNodeName());

			//TODO
			NodeList nList = doc.getElementsByTagName("Die");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				//System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;


					//System.out.println("die_Size : " + eElement.getElementsByTagName("die_Size").item(0).getTextContent());
					String dieSize = 
							eElement.getElementsByTagName("die_Size").item(0).getTextContent();
					//System.out.println("die_Count : " + eElement.getElementsByTagName("die_Count").item(0).getTextContent());
					String dieCount = 
							eElement.getElementsByTagName("die_Count").item(0).getTextContent();
					//System.out.println("modifier : " + eElement.getElementsByTagName("modifier").item(0).getTextContent());
					String modifier = 
							eElement.getElementsByTagName("modifier").item(0).getTextContent();
					//System.out.println("player_Level_Adjust : " + eElement.getElementsByTagName("player_Level_Adjust").item(0).getTextContent());
					String playerLevelAdjust = 
							eElement.getElementsByTagName("player_Level_Adjust").item(0).getTextContent();

					roll.add( new Roll(Integer.parseInt(dieSize), 
							Integer.parseInt(dieCount), 
							Integer.parseInt(modifier), 
							Integer.parseInt(playerLevelAdjust)));

				}
			}
		} catch (Exception e) {
			System.out.println("Load failed");
		}

		return roll;
	}

	// delete a favorite die from the xml
	public static void deleteFavDie(String fileName){
		File file = new File(fileName + ".xml");

		try{

			if(file.delete()){
				System.out.println(file.getName() + " is deleted!");
			}else{
				System.out.println("Delete operation has failed.");
			}

		}catch(Exception error){
			error.printStackTrace();
		}

		return;
	}

}
