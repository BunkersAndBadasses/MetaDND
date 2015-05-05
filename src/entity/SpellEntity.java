package entity;

import java.util.LinkedHashMap;
import java.util.Map;

import core.Main;

public class SpellEntity extends DNDEntity{

	String school;
	//Map<String, Integer> level; //Key is what class can cast, level is what level the spell is for that class, map for multiple classes?
	String components;
	String castingTime;
	String range;
	String effect;
	String duration;
	String savingThrow;
	boolean spellResistance;
	String materialComponent;
	String focus;
	String damage;
	String damageAlternate;
	String[] level;
	
	public SpellEntity(LinkedHashMap<String, String> input){
		this.TYPE = DNDEntity.type.SPELL;
		passedData = input;
		for (Map.Entry<String, String> entry : input.entrySet()){
		    String field = entry.getKey();
		    String value = entry.getValue();
		    switch(field){
		    case "NAME":
		    	this.name = value;
		    	break;
		    case "COMPONENTS":
		    	this.components = value;
		    	break;
		    case "SCHOOL":
		    	this.school = value;
		    	break;
		    case "RANGE":
		    	this.range = value;
		    	break;
		    case "EFFECT":
		    	this.effect = value;
		    	break;
		    case "CASTINGTIME":
		    	this.castingTime = value;
		    	break;
		    case "DESCRIPTION":
		    	this.description = value;
		    	break;
		    case "MATERIALCOMPONENT":
		    	this.materialComponent = value;
		    	break;
		    case "SAVINGTHROW":
		    	this.savingThrow = value;
		    	break;
		    case "FOCUS":
		    	this.focus = value;
		    	break;
		    case "DURATION":
		    	this.duration = value;
		    	break;
		    case "LEVEL":
		    	this.level = value.split(", ");
		    	break;
		    case "SPELLRESISTANCE":
		    	if(value.toUpperCase().contains("NO")){
		    		this.spellResistance = false;
		    	}
		    	else
		    		this.spellResistance = true;
		    	break;
		    case "DAMAGE":
		    	this.damage = value;
		    	break;
		    case "DAMAGEALTERNATE":
		    	this.damageAlternate = value;
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
		
		if(this.effect != null && this.effect.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			//System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			//System.out.println("Lock released.");
			return;
		}
		
		if(this.components != null && this.components.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			//System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			//System.out.println("Lock released.");
			return;
		}
		
		if(this.school != null && this.school.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			//System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			//System.out.println("Lock released.");
			return;
		}
		
		if(this.focus != null && this.focus.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			//System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			//System.out.println("Lock released.");
			return;
		}
		
		if(this.materialComponent != null && this.materialComponent.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			//System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			//System.out.println("Lock released.");
			return;
		}	
		
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String[] getLevel() {
		return level;
	}

	public void setLevel(String[] level) {
		this.level = level;
	}

	public String getComponents() {
		return components;
	}

	public void setComponents(String components) {
		this.components = components;
	}

	public String getCastingTime() {
		return castingTime;
	}

	public void setCastingTime(String castingTime) {
		this.castingTime = castingTime;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getSavingThrow() {
		return savingThrow;
	}

	public void setSavingThrow(String savingThrow) {
		this.savingThrow = savingThrow;
	}

	public boolean isSpellResistance() {
		return spellResistance;
	}

	public void setSpellResistance(boolean spellResistance) {
		this.spellResistance = spellResistance;
	}

	public String getMaterialComponent() {
		return materialComponent;
	}

	public void setMaterialComponent(String materialComponent) {
		this.materialComponent = materialComponent;
	}

	public String getFocus() {
		return focus;
	}

	public void setFocus(String focus) {
		this.focus = focus;
	}

	public String getDamage() {
		return damage;
	}

	public void setDamage(String damage) {
		this.damage = damage;
	}

	public String getDamageAlternate() {
		return damageAlternate;
	}

	public void setDamageAlternate(String damageAlternate) {
		this.damageAlternate = damageAlternate;
	}
	
	@Override
	public String saveCustomContent() {
		String output = this.oneTab + "<SPELL>\n";
		output += this.twoTabs + "<NAME>\n";
		output += this.threeTabs + this.name + "\n";
		output += this.twoTabs + "</NAME>\n";
		output += this.twoTabs + "<SCHOOL>\n";
		output += this.threeTabs + this.school + "\n";
		output += this.twoTabs + "</SCHOOL>\n";
		output += this.twoTabs + "<LEVEL>\n";
		output += this.threeTabs;
		for(int i = 0; i < this.level.length; i++){
			output += this.level[i];
			if(i != this.level.length - 1)
				output += ", ";
		}
		output += "\n";
		output += this.twoTabs + "</LEVEL>\n";
		output += this.twoTabs + "<COMPONENTS>\n";
		output += this.threeTabs + this.components + "\n";
		output += this.twoTabs + "</COMPONENTS>\n";
		output += this.twoTabs + "<CASTINGTIME>\n";
		output += this.threeTabs + this.castingTime + "\n";
		output += this.twoTabs + "</CASTINGTIME>\n";
		if(this.range != null){
			output += this.twoTabs + "<RANGE>\n";
			output += this.threeTabs + this.range + "\n";
			output += this.twoTabs + "</RANGE>\n";
		}
		if(this.effect != null){
			output += this.twoTabs + "<EFFECT>\n";
			output += this.threeTabs + this.effect + "\n";
			output += this.twoTabs + "</EFFECT>\n";
		}
		if(this.duration != null){
			output += this.twoTabs + "<DURATION>\n";
			output += this.threeTabs + this.duration + "\n";
			output += this.twoTabs + "</DURATION>\n";
		}
		if(this.savingThrow != null){
			output += this.twoTabs + "<SAVINGTHROW>\n";
			output += this.threeTabs + this.savingThrow + "\n";
			output += this.twoTabs + "</SAVINGTHROW>\n";
		}
		if(this.spellResistance == false){
			output += this.twoTabs + "<SPELLRESISTANCE>\n";
			output += this.threeTabs + "No\n";
			output += this.twoTabs + "</SPELLRESISTANCE>\n";
		}
		if(this.materialComponent != null){
			output += this.twoTabs + "<MATERIALCOMPONENT>\n";
			output += this.threeTabs + this.materialComponent;
			output += this.twoTabs + "</MATERIALCOMPONENT>\n";
		}
		if(this.focus != null){
			output += this.twoTabs + "<FOCUS>\n";
			output += this.threeTabs + this.focus;
			output += this.twoTabs + "</FOCUS>\n";
		}
		if(this.damage != null){
			output += this.twoTabs + "<DAMAGE>\n";
			output += this.threeTabs + this.materialComponent;
			output += this.twoTabs + "</DAMAGE>\n";
		}
		output += this.twoTabs + "<DESCRIPTION>\n";
		output += this.threeTabs + this.description + "\n";
		output += this.twoTabs + "</DESCRIPTION>\n";
		output += this.oneTab + "</SPELL>\n";
		return output;
	}
}
