package entity;

import java.util.LinkedHashMap;
import java.util.Map;

import core.Main;

public class MonsterEntity extends DNDEntity {

	String type;
	String hitDie;
	int initiative;
	String Speed;
	String armorClass;
	String grapple;
	String attack;
	String fullAttack;
	String reach;
	String[] specialAttacks;
	String[] specialQualities;
	int[] saves;
	int[] abilities;
	String[] skills;
	String[] feats;
	String environment;
	String organization;
	int challengeRating;
	String treasure;
	String alignment;
	String advancement;
	int levelAdjustment;
	public MonsterEntity(LinkedHashMap<String, String> input){
		this.TYPE = DNDEntity.type.MONSTER;
		passedData = input;
		for (Map.Entry<String, String> entry : input.entrySet()){
			String field = entry.getKey();
			String value = entry.getValue();
			switch(field){
			case "NAME":
		    	this.name = value;
		    	break;
			case "HITDICE":
				this.hitDie = value;
				break;
			case "INITIATIVE":
				this.initiative = Integer.parseInt(value);
				break;
			case "SPEED":
				this.Speed = value;
				break;
			case "TYPE":
				this.type = value;
				break;
			case "ARMORCLASS":
				this.armorClass = value;
				break;
			case "DESCRIPTION":
				this.description = value;
				break;
			case "GRAPPLE":
				this.grapple = value;
				break;
			case "CHALLENGERATING":
				try{
					this.challengeRating = Integer.parseInt(value);
				} catch(NumberFormatException e){
					this.challengeRating = 0;
				}
				break;
			case "ATTACK":
				this.attack = value;
				break;
			case "FULLATTACK":
				this.fullAttack = value;
				break;
			case "REACH":
				this.reach = value;
				break;
			case "SPECIALATTACKS":
				this.specialAttacks = value.split(", ");
				break;
			case "SPECIALQUALITIES":
				this.specialQualities = value.split(", ");
				break;
			case "SAVES":
				String[] temp = value.split(", ");
				this.saves = new int[temp.length];
				for(int i = 0; i < temp.length; i++){
					temp[i] = temp[i].replaceAll("[\\D|^-]", "");
					try{
						this.saves[i] = Integer.parseInt(temp[i]);
					} catch(NumberFormatException e){
					}
				}
				break;
			case "ABILITIES":
				String[] temp1 = value.split(", ");
				this.abilities = new int[temp1.length];
				for(int i = 0; i < temp1.length; i++){
					temp1[i] = temp1[i].replaceAll("[\\D]", "");
					try{
						this.abilities[i] = Integer.parseInt(temp1[i]);
					} catch(NumberFormatException e){
					}
				}
				break;
			case "SKILLS":
				this.skills = value.split(", ");
				break;
			case "FEATS":
				this.feats = value.split(", ");
				break;
			case "ENVIRONMENT":
				this.environment = value;
				break;
			case "ORGANIZATION":
				this.organization = value;
				break;
			case "TREASURE":
				this.treasure = value;
				break;
			case "ALIGNMENT":
				this.alignment = value;
				break;
			case "ADVANCEMENT":
				this.advancement = value;
				break;
			case "LEVELDAJUSTMENT":
				if(value.contentEquals("-"))
					this.levelAdjustment = 0;
				else
					this.levelAdjustment = Integer.parseInt(value);
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
		if(this.environment != null && this.environment.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			//System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			//System.out.println("Lock released.");
			return;
		}
		if(this.organization != null && this.organization.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			//System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			//System.out.println("Lock released.");
			return;
		}
		if(this.skills != null){
			for(int i = 0; i < this.skills.length; i++){
				if(this.skills[i].contains(searchString)){
					Main.gameState.searchResultsLock.acquire();
					//System.out.println("Lock aquired, adding " + this.name + " to results list.");
					Main.gameState.searchResults.put(this.name, this);
					Main.gameState.searchResultsLock.release();
					//System.out.println("Lock released.");
					return;
				}
			}
		}
		if(this.feats != null){
			for(int i = 0; i < this.feats.length; i++){
				if(this.feats[i].contains(searchString)){
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
	@Override
	public String saveCustomContent() {
		String output = "";
		return output;
	}

}
