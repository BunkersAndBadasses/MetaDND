package core;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import core.Roll;
import guis.DieWindow;
/**
 * XML saving from 
 * http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
 * 
 * XML loading from 
 * http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 * 
 * @author Ryan
 */
public class DnDie {

	static RNG rng = new RNG();


	/** Rolls the number of a given die (ie rolls 5 20s)
	 * 
	 * @param die
	 * @param dieCount
	 * @return an integer that represent the result.
	 */
	public static int roll (int die, int dieCount){

		int number = 0;

		for(int i = 0; i < dieCount; i ++){
			number += rng.GetRandomInteger(1, die);
		}

		return number;
	}

	/** roll the die, and add in the modifier
	 * 
	 * @param die
	 * @param dieCount
	 * @param modifier
	 * @return an integer that represent the result.
	 */
	public static int roll (int die, int dieCount, int modifier){

		int number = modifier;

		for(int i = 0; i < dieCount; i ++){
			number += rng.GetRandomInteger(1, die);
		}

		return number;
	}

	/** loads the die, and then sends them off to get rolled
	 * 
	 * @param fileName
	 * @return an integer that represent the final result.
	 */
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

	/** save the favorite die selection to the xml
	 * 
	 * @param fileName
	 * @param roll
	 */
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
				Element modifier = doc.createElement("modifier");
				modifier.appendChild(doc.createTextNode(
						Integer.toString(roll.get(i).getModifier())));
				Die.appendChild(modifier);

				// What is this player level Adjustment?
				Element playerLevelAdjust = doc.createElement("player_Level_Adjust");
				playerLevelAdjust.appendChild(doc.createTextNode(
						Integer.toString(roll.get(i).getPlayerLevelAdjust())));
				Die.appendChild(playerLevelAdjust);

			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result;

			if(Main.gameState.currentlyLoadedCharacter == null){
				File CHARDIR = new File(System.getProperty("user.dir") + "//" + 
						"User Data" + "//favRolls");
				CHARDIR.mkdir();
				result = new StreamResult(CHARDIR.getPath() + "//" + fileName + ".xml");
			}else{
				String charFileName = Main.gameState.currentlyLoadedCharacter.getName();
				charFileName = charFileName.replaceAll("[^A-Za-z0-9]", "");
				File CHARDIR = new File(System.getProperty("user.dir") + "//" + 
						"User Data" + "//Character" + "//DND" + charFileName + "//favRolls");
				CHARDIR.mkdir();
				result = new StreamResult(CHARDIR.getPath() + "//" + fileName + ".xml");
			}
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

	/** load the favorite die selection from the xml
	 * 
	 * @param fileName
	 * @return The selected favorite die roll as a form of ArrayList<Roll>
	 */
	public static ArrayList<Roll> loadFavDie(String fileName){

		ArrayList<Roll> roll = new ArrayList<Roll>(50);

		try {
			File fXmlFile;

			if(Main.gameState.currentlyLoadedCharacter == null){
				fXmlFile = new File(System.getProperty("user.dir") + "//" + 
						"User Data" + "//" + "favRolls" + "//" + fileName + ".xml");
			}else{
				String charFileName = Main.gameState.currentlyLoadedCharacter.getName();
				charFileName = charFileName.replaceAll("[^A-Za-z0-9]", "");
				fXmlFile = new File(System.getProperty("user.dir") + "//" + 
						"User Data" + "//Character" + "//DND" + charFileName + "//favRolls"  +
						"//" + fileName + ".xml");
			}

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Favorite_Die: " + doc.getDocumentElement().getNodeName());

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

	//populates the Favorite die rolls list
	public static void populateDieList(){
		DieWindow.favList.removeAll();
		DieWindow.favList.add("Favorite Die Roll");
		DieWindow.favList.select(0);
		try {
			//Loads the current favRolls into the list
			if(Main.gameState.currentlyLoadedCharacter == null){
				Files.walk(Paths.get(System.getProperty("user.dir") + "//" + 
						"User Data" + "//favRolls")).forEach(filePath ->{
							if(filePath.getFileName().toString().contains(".xml")){
								String fileName = filePath.getFileName().toString();
								fileName = (String) fileName.subSequence(0, fileName.length() - 4);
								DieWindow.favList.add(fileName);
							}
							else{
								//System.out.println(filePath.getFileName() + " is not an XML file");
							}
						});
			}
			else{
				String charFileName = Main.gameState.currentlyLoadedCharacter.getName();
				charFileName = charFileName.replaceAll("[^A-Za-z0-9]", "");
				String filepath;
				if(charFileName == "BiggusDickus"){
					filepath = System.getProperty("user.dir") + "//" + 
							"User Data" + "//Character" + "//" + charFileName + "//favRolls";
				}
				else{
					filepath = System.getProperty("user.dir") + "//" + 
							"User Data" + "//Character" + "//DND" + charFileName + "//favRolls";
				}
				Files.walk(Paths.get(filepath)).forEach(filePath ->{
					if(filePath.getFileName().toString().contains(".xml")){
						String fileName = filePath.getFileName().toString();
						fileName = (String) fileName.subSequence(0, fileName.length() - 4);
						DieWindow.favList.add(fileName);
					}
					else{
						//System.out.println(filePath.getFileName() + " is not an XML file");
					}
				});
			}

		} catch (IOException e) {


		}
	}




	/** delete a favorite die from the xml
	 * 
	 * @param fileName
	 */
	public static void deleteFavDie(String fileName){
		File file = new File(System.getProperty("user.dir") + "//" + 
				"User Data" + "//" + "favRolls" + "//" + fileName + ".xml");

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
