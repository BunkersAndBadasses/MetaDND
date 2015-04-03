package entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class ItemEntity extends DNDEntity{

	double weight;
	String value;
	
	public ItemEntity(LinkedHashMap<String, String> input){
		this.TYPE = DNDEntity.type.ITEM;
		passedData = input;
		for (Map.Entry<String, String> entry : input.entrySet()){
		    String field = entry.getKey();
		    String value = entry.getValue();
		    switch(field){
		    case "NAME":
		    	this.name = value;
		    	break;
		    case "DESCRIPTION":
		    	this.description = value;
		    	break;
		    case "WEIGHT":
		    	String temp;
		    	temp = value.split(" ")[0];
		    	this.weight = Double.parseDouble(temp);
		    	break;
		    case "VALUE":
		    	this.value = value;
		    	break;
		    default:
		    	break;
		    }    
		}
	}
}
