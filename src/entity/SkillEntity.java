package entity;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;

import core.Main;

public class SkillEntity extends DNDEntity{

	public String skillParentAttribute;
	String check;
	String special;
	String tryagain;
	String action;
	String synergy;
	String restriction;
	String untrained;
	public boolean armorCheckPenalty;

	public SkillEntity(LinkedHashMap<String, String> input){
		this.TYPE = DNDEntity.type.SKILL;
		passedData = input;
		for (Map.Entry<String, String> entry : input.entrySet()){
			String field = entry.getKey();
			String value = entry.getValue();
			switch(field){
			case "NAME":
				String[] splits = value.split("\\(");
				String temp = splits[0].trim();
				String temp2 = splits[1].substring(0, 3);
				this.armorCheckPenalty = splits[1].contains("ARMOR CHECK PENALTY");
				this.skillParentAttribute = temp2;
		    	temp = WordUtils.capitalize(temp.toLowerCase());
		    	this.name = temp;
		    	break;
			case "CHECK":
				this.check = value;
				break;
			case "TRYAGAIN":
				this.tryagain = value;
				break;
			case "SPECIAL":
				this.special = value;
				break;
			case "ACTION":
				this.action = value;
				break;
			case "SYNERGY":
				this.synergy = value;
				break;
			case "RESTRICTION":
				this.restriction = value;
				break;
			case "UNTRAINED":
				this.untrained = value;
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
		
		if(this.name != null && this.name.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			System.out.println("Lock released.");
			return;
		}
		
		if(this.description != null && this.description.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			System.out.println("Lock released.");
			return;
		}
		
	}

	public String getSkillParentAttribute() {
		return skillParentAttribute;
	}

	public void setSkillParentAttribute(String skillParentAttribute) {
		this.skillParentAttribute = skillParentAttribute;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getTryagain() {
		return tryagain;
	}

	public void setTryagain(String tryagain) {
		this.tryagain = tryagain;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSynergy() {
		return synergy;
	}

	public void setSynergy(String synergy) {
		this.synergy = synergy;
	}

	public String getRestriction() {
		return restriction;
	}

	public void setRestriction(String restriction) {
		this.restriction = restriction;
	}

	public String getUntrained() {
		return untrained;
	}

	public void setUntrained(String untrained) {
		this.untrained = untrained;
	}

	public boolean isArmorCheckPenalty() {
		return armorCheckPenalty;
	}

	public void setArmorCheckPenalty(boolean armorCheckPenalty) {
		this.armorCheckPenalty = armorCheckPenalty;
	}

}
