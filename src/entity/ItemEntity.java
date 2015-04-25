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
		    	temp = value.replaceAll("[^\\d]+", "");
		    	if (temp.length() == 0)
		    		this.weight = 0;
		    	else
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
		String output = this.oneTab + "<ITEM>\n";
		output += this.twoTabs + "<NAME>\n";
		output += this.threeTabs + this.name + "\n";
		output += this.twoTabs + "</NAME>\n";
		output += this.twoTabs + "<WEIGHT>\n";
		output += this.threeTabs + this.weight + " lb.\n";
		output += this.twoTabs + "</WEIGHT>\n";
		output += this.twoTabs + "<VALUE>\n";
		output += this.threeTabs + this.value + "\n";
		output += this.twoTabs + "</VALUE>\n";
		output += this.twoTabs + "<DESCRIPTION>\n";
		output += this.threeTabs + this.description + "\n";
		output += this.twoTabs + "</DESCRIPTION>\n";
		output += this.oneTab + "</ITEM>\n";
		return output;
	}
}
