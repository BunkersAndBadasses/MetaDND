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
	String level;
	
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
		    	this.level = value;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
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
		String output = "<SPELL>\n";
		output += "<NAME>\n";
		output += this.name + "\n";
		output += "</NAME>\n";
		output += "<SCHOOL>\n";
		output += this.school + "\n";
		output += "</SCHOOL>\n";
		output += "<LEVEL>\n";
		output += this.level + "\n";
		output += "</LEVEL>\n";
		output += "<COMPONENTS>\n";
		output += this.components + "\n";
		output += "</COMPONENTS>\n";
		output += "<CASTINGTIME>\n";
		output += this.castingTime + "\n";
		output += "</CASTINGTIME>\n";
		if(this.range != null){
			output += "<RANGE>\n";
			output += this.range + "\n";
			output += "</RANGE>\n";
		}
		if(this.effect != null){
			output += "<EFFECT>\n";
			output += this.effect + "\n";
			output += "</EFFECT>\n";
		}
		if(this.duration != null){
			output += "<DURATION>\n";
			output += this.duration + "\n";
			output += "</DURATION>\n";
		}
		if(this.savingThrow != null){
			output += "<SAVINGTHROW>\n";
			output += this.savingThrow + "\n";
			output += "</SAVINGTHROW>\n";
		}
		if(this.spellResistance == false){
			output += "<SPELLRESISTANCE>\n";
			output +=  "No\n";
			output += "</SPELLRESISTANCE>\n";
		}
		if(this.materialComponent != null){
			output += "<MATERIALCOMPONENT>\n";
			output +=  this.materialComponent;
			output += "</MATERIALCOMPONENT>\n";
		}
		if(this.focus != null){
			output += "<FOCUS>\n";
			output +=  this.focus;
			output += "</FOCUS>\n";
		}
		if(this.damage != null){
			output += "<DAMAGE>\n";
			output +=  this.materialComponent;
			output += "</DAMAGEALTERNATE>\n";
		}
		output += "<DESCRIPTION>\n";
		output += this.description + "\n";
		output += "</DESCRIPTION>\n";
		output += "</SPELL>\n";
		return output;
	}
}
