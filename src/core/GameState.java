package core;

import java.util.LinkedHashMap;

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
	
	public Character currentlyLoadedCharacter;
	public String currCharFilePath;
	public boolean playerMode;
	
	public GameState(){
		
	}

}
