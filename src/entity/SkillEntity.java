package entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class SkillEntity extends DNDEntity{

	String check;
	String special;
	String tryagain;
	String action;
	String synergy;
	String restriction;
	String untrained;

	public SkillEntity(LinkedHashMap<String, String> input){
		this.TYPE = DNDEntity.type.SKILL;
		passedData = input;
		for (Map.Entry<String, String> entry : input.entrySet()){
			String field = entry.getKey();
			String value = entry.getValue();
			switch(field){
			case "NAME":
				String temp = new String(value.split("\\(")[0].trim());
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
	public void search() {
		// TODO Auto-generated method stub
		
	}

}
