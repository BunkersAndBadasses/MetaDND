package entity;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;

import core.Main;

public class FeatEntity extends DNDEntity{

	String[] prerequisites;
	String special;
	String normal;
	String benefit;
	String fighterBonus;
	String type;

	public FeatEntity(LinkedHashMap<String, String> input){
		this.TYPE = DNDEntity.type.FEAT;
		passedData = input;
		for (Map.Entry<String, String> entry : input.entrySet()){
			String field = entry.getKey();
			String value = entry.getValue();
			switch(field){
			case "NAME":
				String temp = new String(value.split("\\[")[0].trim());
				String typeTemp = value.split("\\[")[1].trim();				
				typeTemp = typeTemp.substring(0, typeTemp.length() - 1).toLowerCase();
				this.type = WordUtils.capitalize(typeTemp);
				temp = WordUtils.capitalize(temp.toLowerCase());
				temp = WordUtils.capitalize(temp, '(', '[');
		    	this.name = temp;
		    	break;
			case "PREREQUISITES":
				this.prerequisites = value.split(", ");
				break;
			case "NORMAL":
				this.normal = value;
				break;
			case "SPECIAL":
				this.special = value;
				break;
			case "BENEFIT":
				this.benefit = value;
				break;
			case "FIGHTERBONUS":
				this.fighterBonus = value;
				break;
			case "DESCRIPTION":
				this.description = value;
				break;	
			default:
				break;
			}

		}
	}

	@Override
	public void search(String searchString, Thread runningThread) throws InterruptedException {
		// TODO Auto-generated method stub
		
		if(this.name != null && this.name.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			//System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			//System.out.println("Lock released.");
			return;
		}
		
		if(this.description != null && this.description.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			//System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			//System.out.println("Lock released.");
			return;
		}
		
		if(this.special != null && this.special.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			//System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			//System.out.println("Lock released.");
			return;
		}
		
		if(this.benefit != null && this.benefit.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			System.out.println("Lock released.");
			return;
		}
		
		if(this.type != null && this.type.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			System.out.println("Lock released.");
			return;
		}
		
		if(this.fighterBonus != null && this.fighterBonus.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			System.out.println("Lock released.");
			return;
		}
		
	}

	public String[] getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(String[] prerequisites) {
		this.prerequisites = prerequisites;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getNormal() {
		return normal;
	}

	public void setNormal(String normal) {
		this.normal = normal;
	}

	public String getBenefit() {
		return benefit;
	}

	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}

	public String getFighterBonus() {
		return fighterBonus;
	}

	public void setFighterBonus(String fighterBonus) {
		this.fighterBonus = fighterBonus;
	}

}
