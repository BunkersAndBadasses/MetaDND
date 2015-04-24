package entity;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;

import core.Main;

public class WeaponEntity extends DNDEntity{

	private String damageSmall; // i.e. [2,6,3] for damage of 2 d6 + 3
	private String damageMedium;
	private int[] criticalRange = new int[2];
	private int criticalMultiplier;
	private String range;
	private String damageType; // i.e. "piercing"
	private String type;
	private boolean isMagic;
	private int magicBonus; // if isMagic == false, leave null
	private String magicProperties; // if isMagic == false, leave null
	private int quantity;
	private String value;
	private String weight;
	
	public WeaponEntity(LinkedHashMap<String, String> input) {
		this.TYPE = DNDEntity.type.WEAPON;
		passedData = input;
		for (Map.Entry<String, String> entry : input.entrySet()){
		    String field = entry.getKey();
		    String value = entry.getValue();
		    switch(field){
		    case "NAME":
		    	String tempN = WordUtils.capitalize(value.toLowerCase());
				tempN = WordUtils.capitalize(tempN);
		    	this.name = tempN;
		    	break;
		    case "TYPE":
		    	this.type = value;
		    	break;
		    case "COST":
		    	this.value = value;
		    	break;
		    case "DAMAGESMALL":
		    	this.damageSmall = value;
		    	break;
		    case "DAMAGEMEDIUM":
		    	this.damageMedium = value;
		    	break;
		    case "CRITICAL":
		    	String[] crits = value.split("x");
		    	String[] critRange = crits[0].split("-");
		    	if(!crits[0].equalsIgnoreCase("-"))
		    		this.criticalMultiplier = Integer.parseInt(crits[1]);
		    	else
		    		this.criticalMultiplier = 1;
		    	if(critRange.length > 1){
		    		critRange[1] = critRange[1].substring(0, critRange[1].length() - 1);
		    		this.criticalRange[0] = Integer.parseInt(critRange[0]);
		    		this.criticalRange[1] = Integer.parseInt(critRange[1]);
		    	}	
		    	break;
		    case "RANGEINCREMENT":
		    	this.range = value;
		    	break;
		    case "WEIGHT":
		    	this.weight = value;
		    	break;
		    case "DAMAGETYPE":
		    	this.damageType = value;
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
		    case "DESCRIPTION":
		    	this.description = value;
		    	break;
		    }
		}
	}
	
	@Override
	public void search(String searchString, Thread runningThread) throws InterruptedException {
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
		if(this.damageType != null && this.damageType.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			//System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			//System.out.println("Lock released.");
			return;
		}
		
	}
	public String getDamageSmall() {
		return damageSmall;
	}
	public void setDamageSmall(String damageSmall) {
		this.damageSmall = damageSmall;
	}
	public String getDamageMedium() {
		return damageMedium;
	}
	public void setDamageMedium(String damageMedium) {
		this.damageMedium = damageMedium;
	}
	public int[] getCriticalRange() {
		return criticalRange;
	}
	public void setCriticalRange(int[] criticalRange) {
		this.criticalRange = criticalRange;
	}
	public int getCriticalMultiplier() {
		return criticalMultiplier;
	}
	public void setCriticalMultiplier(int criticalMultiplier) {
		this.criticalMultiplier = criticalMultiplier;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getDamageType() {
		return damageType;
	}
	public void setDamageType(String type) {
		this.damageType = type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	
	public void setQuanitity(int i ) {
	    quantity = i;
	}
	
	public int getQuantity() {
	    return quantity;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public String getWeight()
	{
		return weight;
	}
	public void setWeight(String a)
	{
		this.weight = a;
	}
	@Override
	public String saveCustomContent() {
		String output = "";
		return output;
	}
		
}
