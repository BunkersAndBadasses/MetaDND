package entity;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;

import core.Main;

public class AbilityEntity extends DNDEntity{
    
    public AbilityEntity(String n, String d){
        this.name = n;
        this.description = d;
    }

	public AbilityEntity(LinkedHashMap<String, String> input)
	{
		for (Map.Entry<String, String> entry : input.entrySet())
		{
			this.TYPE = DNDEntity.type.ABILITY;
			this.passedData = input;
		    String field = entry.getKey();
		    String value = entry.getValue();
		    switch(field)
		    {
		    case "NAME":
		    	this.name = value;
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
	public void search(String searchString, Thread runningThread) throws InterruptedException {
		if(this.name != null && this.name.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			//System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name + "(" + WordUtils.capitalize(this.TYPE.toString().toLowerCase()) + ")", this);
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

}
