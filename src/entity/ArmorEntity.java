package entity;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;

import core.Main;

public class ArmorEntity extends ItemEntity {

	private int armorBonus;
	private int maxDexBonus;
	private int armorCheckPenalty;
	private String cost;
	private boolean isMagic;
	private int spellFailureChance;
	private int magicBonus; // if isMagic == false, leave null
	private String magicProperties; // if isMagic == false, leave null
	private String speed30;
	private String speed20;
	private double weight;

	public ArmorEntity(LinkedHashMap<String, String> input) {
		super(input);
		this.TYPE = DNDEntity.type.ARMOR;
		passedData = input;
		for (Map.Entry<String, String> entry : input.entrySet()) {
			String field = entry.getKey();
			String value = entry.getValue();
			switch (field) {
			case "NAME":
				String tempN = WordUtils.capitalize(value.toLowerCase());
				tempN = WordUtils.capitalize(tempN);
				this.name = tempN;
				break;
			case "COST":
				this.cost = value;
				break;
			case "ARMORBONUS":
				if (!value.equalsIgnoreCase("-"))
					this.armorBonus = Integer.parseInt(value);
				else
					this.armorBonus = 0;
				break;
			case "MAXDEXBONUS":
				if (!value.equalsIgnoreCase("-"))
					this.maxDexBonus = Integer.parseInt(value);
				else
					this.maxDexBonus = 0;
				break;
			case "ARMORCHECKPENALTY":
				if (!value.equalsIgnoreCase("-"))
					this.armorCheckPenalty = Integer.parseInt(value);
				else
					this.armorCheckPenalty = 0;
				break;
			case "ARCANESPELLFAILURECHANCE":
				if (!value.equalsIgnoreCase("-"))
					this.maxDexBonus = Integer.parseInt(value.split("%")[0]);
				else
					this.maxDexBonus = 0;
				break;
			 case "ISMAGIC":
			    	if(value.equalsIgnoreCase("true"))
			    		this.isMagic = true;
			    	else
			    		this.isMagic = false;
			    	break;
			    case "MAGICPROPERTIES":
			    	if(this.isMagic)
			    		this.magicProperties = value;
			    	break;
			    case "SPEED30FT":
					this.speed30 = value;
					break;
			    case "SPEED20FT":
					this.speed20 = value;
					break;
			    case "WEIGHT":
			    	String temp;
			    	temp = value.replaceAll("[^\\d]+", "");
			    	if (temp.length() == 0)
			    		this.weight = 0;
			    	else
			    		this.weight = Double.parseDouble(temp);
			    	break;
			    case "DESCRIPTION":
			    	this.description = value;
			    	break;
			default:
				break;
			}
		}

	}

	public int getSpellFailureChance() {
		return spellFailureChance;
	}

	public void setSpellFailureChance(int spellFailureChance) {
		this.spellFailureChance = spellFailureChance;
	}

	public String getSpeed30() {
		return speed30;
	}

	public void setSpeed30(String speed30) {
		this.speed30 = speed30;
	}

	public String getSpeed20() {
		return speed20;
	}

	public void setSpeed20(String speed20) {
		this.speed20 = speed20;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}


	@Override
	public void search(String searchString, Thread runningThread)
			throws InterruptedException {
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

	}

	public int getArmorBonus() {
		return armorBonus;
	}

	public void setArmorBonus(int armorBonus) {
		this.armorBonus = armorBonus;
	}

	public int getMaxDexBonus() {
		return maxDexBonus;
	}

	public void setMaxDexBonus(int maxDexBonus) {
		this.maxDexBonus = maxDexBonus;
	}

	public int getArmorCheckPenalty() {
		return armorCheckPenalty;
	}

	public void setArmorCheckPenalty(int armorCheckPenalty) {
		this.armorCheckPenalty = armorCheckPenalty;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public boolean isMagic() {
		return isMagic;
	}

	public void setMagic(boolean isMagic) {
		this.isMagic = isMagic;
	}

	public int getMagicBonus() {
		return magicBonus;
	}

	public void setMagicBonus(int magicBonus) {
		this.magicBonus = magicBonus;
	}

	public String getMagicProperties() {
		return magicProperties;
	}

	public void setMagicProperties(String magicProperties) {
		this.magicProperties = magicProperties;
	}
	
	@Override
	public String saveCustomContent() {
		String output = "";
		return output;
	}

}
