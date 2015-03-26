package entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class FeatEntity extends DNDEntity{

	String prerequisite;
	String special;
	String normal;
	String benefit;
	String fighterBonus;

	public FeatEntity(LinkedHashMap<String, String> input){
		this.TYPE = DNDEntity.type.FEATENTITY;
		passedData = input;
		for (Map.Entry<String, String> entry : input.entrySet()){
			String field = entry.getKey();
			String value = entry.getValue();
			switch(field){
			case "NAME":
				String temp = new String(value.split("\\[")[0].trim());
		    	this.name = temp;
		    	break;
			case "PREREQUISITE":
				this.prerequisite = value;
				break;
			case "NORMAL":
				this.normal = value;
				break;
			case "SPECIAL":
				this.special = value;
				break;
			case "BENEFIT":
				this.benefit = value;
				break;
			case "FIGHTERBONUS":
				this.fighterBonus = value;
				break;
			case "DESCRIPTION":
				this.description = value;
				break;	
			default:
				break;
			}

		}
	}

}
