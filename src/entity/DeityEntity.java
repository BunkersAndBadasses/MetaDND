package entity;

import java.util.LinkedHashMap;
import java.util.Map;

import core.Main;

public class DeityEntity extends DNDEntity{

	private String alignment;
	private String[] domain;
	private String favoredWeapon;

	public DeityEntity(LinkedHashMap<String, String> input){
		this.TYPE = DNDEntity.type.DEITY;
		passedData = input;
		for (Map.Entry<String, String> entry : input.entrySet()){
		    String field = entry.getKey();
		    String value = entry.getValue();
		    switch(field){
		    case "NAME":
		    	this.name = value;
		    	break;
		    case "ALIGNMENT":
		    	this.setAlignment(value);
		    	break;
		    case "DOMAIN":
		    	this.setDomain(value.split(", "));
		    	break;
		    case "DESCRIPTION":
		    	this.description = value;
		    	break;
		    case "FAVOREDWEAPON":
		    	this.favoredWeapon = value;
		    	break;
		    default:
		    	break;
		    }
		}
	}

	@Override
	public void search(String searchString, Thread runningThread)
			throws InterruptedException {
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
		if(this.alignment != null && this.alignment.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			//System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			//System.out.println("Lock released.");
			return;
		}
		if(this.domain != null){
			for(int i = 0; i < this.domain.length; i++){
				if(this.domain[i].contains(searchString)){
					Main.gameState.searchResultsLock.acquire();
					//System.out.println("Lock aquired, adding " + this.name + " to results list.");
					Main.gameState.searchResults.put(this.name, this);
					Main.gameState.searchResultsLock.release();
					//System.out.println("Lock released.");
					return;
				}
			}
		}
		
	}

	public String[] getDomain() {
		return domain;
	}

	public void setDomain(String[] domain) {
		this.domain = domain;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	@Override
	public String saveCustomContent() {
		String output = "";
		return output;
	}
}
