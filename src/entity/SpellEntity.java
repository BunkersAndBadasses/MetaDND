package entity;

import java.util.LinkedHashMap;
import java.util.Map;

import core.Main;

public class SpellEntity extends DNDEntity{

	String school;
	Map<String, Integer> level; //Key is what class can cast, level is what level the spell is for that class, map for multiple classes?
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
		    	//TODO Figure out logic here later, with what we want
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
	public void search(String searchString, Thread runningThread) {
		// TODO Auto-generated method stub
		
		if(this.name.toLowerCase().contains(searchString)){
			System.out.println(this.name + " contains " + searchString + " in NAME");
			try {
				Main.gameState.searchResultsLock.acquire();
				System.out.println("Lock aquired, adding " + this.name + " to results list.");
				Main.gameState.searchResults.add(this);
			}
			catch (InterruptedException e) {
				System.out.println(runningThread.getName() + " failed!");
			}
			finally{
				Main.gameState.searchResultsLock.release();
				System.out.println("Lock released.");
			}
		}
		
		if(this.description.toLowerCase().contains(searchString)){
			//System.out.println(this.name + " contains " + searchString + " in DESCRIPTION");
		}
		if(this.effect != null && this.effect.toLowerCase().contains(searchString)){
			//System.out.println(this.name + " contains " + searchString + " in EFFECT");
		}
		
		//System.out.println("Searching " + this.name);
		
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Map<String, Integer> getLevel() {
		return level;
	}

	public void setLevel(Map<String, Integer> level) {
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
}
