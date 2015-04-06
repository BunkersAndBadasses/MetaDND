package entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClassEntity extends DNDEntity{
	
	String alignmentRestriction;
	String[] bonusLanguages;
	String hitDie;
	String[] baseAttackBonus;
	int[] fortSave;
	int[] reflexSave;
	int[] willSave;
	String special;
	String spellsPerDay;
	String spellsKnown;
	String classSkills;
	String skillPointsFirstLevel;
	String skillPointsPerLevel;
	String[] bonusFeats;
	String classAbilities;
	String druidCompanion;
	String paladinMount;
	String familiar;
	String exclass;
	
	
	//TODO Needs a lot of additional additions based on other object types, only baseline is here for functionality
	public ClassEntity(LinkedHashMap<String, String> input){
		this.TYPE = DNDEntity.type.CLASS;
		passedData = input;
		for (Map.Entry<String, String> entry : input.entrySet()){
		    String field = entry.getKey();
		    String value = entry.getValue();
		    switch(field){
		    case "NAME":
		    	this.name = value;
		    	break;
		    case "ALIGNMENTRESTRICTIONS":
		    	this.alignmentRestriction = value;
		    	break;
		    case "BONUSLANGUAGE":
		    	this.bonusLanguages = value.split(", ");
		    	break;
		    case "HITDIE":
		    	this.hitDie = value;
		    	break;
		    case "BASEATTACKBONUS":
		    	this.baseAttackBonus = value.split(", ");
		    	break;
		    case "FORTSAVE":
		    	String[] fortSaveList = value.split(", ");
		    	this.fortSave = new int[fortSaveList.length];
		    	for(int i = 0; i < fortSaveList.length; i++){
		    		this.fortSave[i] = Integer.parseInt(fortSaveList[i]);
		    	}
		    	break;
		    case "REFLEXSAVE":
		    	String[] reflexSaveList = value.split(", ");
		    	this.reflexSave = new int[reflexSaveList.length];
		    	for(int i = 0; i < reflexSaveList.length; i++){
		    		this.reflexSave[i] = Integer.parseInt(reflexSaveList[i]);
		    	}
		    	break;
		    case "WILLSAVE":
		    	String[] willSaveList = value.split(", ");
		    	this.willSave = new int[willSaveList.length];
		    	for(int i = 0; i < willSaveList.length; i++){
		    		this.willSave[i] = Integer.parseInt(willSaveList[i]);
		    	}
		    	break;
		    case "SPECIAL":
		    	this.special = value; //Needs refinement in XML for specific level values, but this will work for now
		    	break;
		    case "SPELLSPERDAY":
		    	this.spellsPerDay = value;
		    	break;
		    case "SPELLSKNOWN":
		    	this.spellsKnown = value; //TODO Replace with proper spell entity objects
		    	break;
		    case "SKILLPOINTSFIRST":
		    	this.skillPointsFirstLevel = value;
		    	break;
		    case "SKILLPOINTS":
		    	this.skillPointsPerLevel = value;
		    	break;
		    case "BONUSFEATS": //TODO replace with proper feat enitity objects
		    	this.bonusFeats = value.split(", ");
		    	break;
		    case "CLASSABILITIES":
		    	this.classAbilities = value;
		    	break;
		    case "CLASSSKILLS":
		    	this.classSkills = value;
		    	break;
		    case "PALADINMOUNT":
		    	this.paladinMount = value;
		    	break;
		    case "DRUIDCOMPANION":
		    	this.druidCompanion = value;
		    	break;
		    case "WIZARDFAMILIAR":
		    	this.familiar = value;
		    	break;
		    case "EXCLASS":
		    	this.exclass = value;
		    	break;
		    default:
		    	break;
		    	
		    }
		}
	}

}
