package entity;

import java.util.LinkedHashMap;
import java.util.Map;

import core.Main;

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
		
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String saveCustomContent() {
		String output = "<ABILITY>\n";
		output += "<NAME>\n";
		output += this.name + "\n";
		output += "</NAME>\n";
		output += "<WEIGHT>\n";
		output += this.weight + "lb.\n";
		output += "</WEIGHT>\n";
		output += "<VALUE>\n";
		output += this.value + "gp.\n";
		output += "</VALUE>\n";
		output += "<DESCRIPTION>\n";
		output += this.description + "\n";
		output += "</DESCRIPTION>\n";
		output += "</ABILITY>\n";
		return output;
	}
}
