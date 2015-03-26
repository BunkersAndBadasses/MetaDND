package entity;

import java.util.LinkedHashMap;
import java.util.Map;

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
	
	public SpellEntity(LinkedHashMap<String, String> input){
		this.TYPE = DNDEntity.type.SPELLENTITY;
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
		    default:
		    	break;
		    }
		}
	}
}
