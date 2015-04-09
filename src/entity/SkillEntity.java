package entity;

import java.util.LinkedHashMap;
import java.util.Map;

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
