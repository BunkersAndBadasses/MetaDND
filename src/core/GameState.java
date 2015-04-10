package core;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.Semaphore;

import entity.DNDEntity;

public class GameState {
	//Everything you need to access globally, store it here
	public LinkedHashMap<String, DNDEntity> races;
	public LinkedHashMap<String, DNDEntity> classes;
	public LinkedHashMap<String, DNDEntity> spells;
	public LinkedHashMap<String, DNDEntity> feats;
	public LinkedHashMap<String, DNDEntity> skills;
	public LinkedHashMap<String, DNDEntity> items;
	public LinkedHashMap<String, DNDEntity> weapons;
	public LinkedHashMap<String, DNDEntity> armor;
	public LinkedHashMap<String, DNDEntity> monsters;
	public LinkedHashMap<String, DNDEntity> traps;
	
	//When building custom content add it to this HashMap. Everything here will be saved to disk to a CustomContent.xml
	public LinkedHashMap<String, DNDEntity> customContent;
	
	public character currentlyLoadedCharacter;
	public String currCharFilePath;
	public boolean playerMode;
	
	public HashMap<String, DNDEntity> searchResults;
	public Semaphore searchResultsLock;
	
	public GameState(){
		spells = new LinkedHashMap<String, DNDEntity>();
		feats = new LinkedHashMap<String, DNDEntity>();
		skills = new LinkedHashMap<String, DNDEntity>();
		races = new LinkedHashMap<String, DNDEntity>();
		classes = new LinkedHashMap<String, DNDEntity>();
		items = new LinkedHashMap<String, DNDEntity>();
		weapons = new LinkedHashMap<String, DNDEntity>();
		armor = new LinkedHashMap<String, DNDEntity>();
		monsters = new LinkedHashMap<String, DNDEntity>();
		traps = new LinkedHashMap<String, DNDEntity>();
		searchResultsLock = new Semaphore(1);
		searchResults = new HashMap<String, DNDEntity>();
	}
	
	public void saveCustomContent(){
		
	}
	
	public boolean search(String searchString){
		SearchThread st1 = new SearchThread("Spells");
		SearchThread st2 = new SearchThread("Feats");
		SearchThread st3 = new SearchThread("Skills");
		SearchThread st4 = new SearchThread("Classes");
		SearchThread st5 = new SearchThread("Races");
		
		st1.start(this.spells, searchString);
		st2.start(this.feats, searchString);
		st3.start(this.skills, searchString);
		st4.start(this.classes, searchString);
		st5.start(this.races, searchString);
		
		try {
			st1.getSearchThread().join();
			st2.getSearchThread().join();
			st3.getSearchThread().join();
			st4.getSearchThread().join();
			st5.getSearchThread().join();
		} catch (InterruptedException e) {
			System.out.println("Error joining threads!");
			return false;
		}
		System.out.println("All threads joined. Ending search!");
		return true;
	}

}
