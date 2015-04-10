package core;

import java.util.LinkedHashMap;
import java.util.LinkedList;
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
	
	
	// 0 = homeScreen
	// 1 = dungeonScreen
	// 2 = dungeonViewer
	public static int PAGE_NUMBER = -1;
	
	//When building custom content add it to this HashMap. Everything here will be saved to disk to a CustomContent.xml
	public LinkedHashMap<String, DNDEntity> customContent;
	
	public character currentlyLoadedCharacter;
	public String currCharFilePath;
	public boolean playerMode;
	
	public LinkedList<DNDEntity> searchResults;
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
		searchResults = new LinkedList<DNDEntity>();
	}
	
	public void saveCustomContent(){
		
	}

}
