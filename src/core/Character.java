package core;
import java.util.ArrayList;

import entity.FeatEntity;
import entity.SkillEntity;


public class Character {
	
	private final static int STRENGTH = 0;
	private final static int DEXTERITY = 1;
	private final static int CONSTITUTION = 2;
	private final static int INTELLIGENCE = 3;
	private final static int WISDOM = 4;
	private final static int CHARISMA = 5;
	
	private final static int SIZE_FINE = 0;
	private final static int SIZE_DIMINUTIVE = 1;
	private final static int SIZE_TINY = 2;
	private final static int SIZE_SMALL = 3;
	private final static int SIZE_MEDIUM = 4;
	private final static int SIZE_LARGE = 5;
	private final static int SIZE_HUGE = 6;
	private final static int SIZE_GARGANTUAN = 7;
	private final static int SIZE_COLOSSAL = 8;
	
	private String name;
	private int level = 1;
	private int exp = 0;
	private CharClass charClass;
	private Race race;
	private String alignment;
	private int size; // TODO int? string?
	private int age; 
	private String gender;
	private int height;
	private int weight;
	private String eyes;
	private String hair;
	private String skin;
	private String[] appearance = {eyes, hair, skin};
	private int[] abilityScores = new int[6];
	private int hp; // hitpoints
	private int remainingHP;
	private ArrayList<CharSkill> skillsList;
	private ArrayList<String> languages;
	private ArrayList<FeatEntity> feats;
	private ArrayList<String> specialAbilities;
	private ArrayList<Spell> spells;
	private ArrayList<Spell> prepSpells;
	private ArrayList<Item> items;
	private ArrayList<Weapon> weapons;
	private ArrayList<Armor> armors;
	private String notes;
	
	
	
	public Character() {}
	
	public void setName(String n) { name = n; }
	public void setLevel(int l) { level = l; }
	public void setExp(int exp) { this.exp = exp; }
	public void incExp(int exp) { this.exp += exp; }
	public void setClass(CharClass c) { charClass = c; }
	public void setRace(Race r) { race = r; }
	public void setAlignment(String a) { alignment = a; }
	public void setSize(int s) { size = s; }
	public void setAge(int a) { age = a; }
	public void setGender(String g) { gender = g; }
	public void setHeight(int h) { height = h; }
	public void setWeight(int w) { weight = w; }
	public void setAppearance(String eyes, String hair, String skin) {
		this.eyes = eyes;
		this.hair = hair;
		this.skin = skin;
	}
	public void setAbilityScores(int str, int dex, int con, int intel, int wis, int cha) { // TODO change this to AbilityScore class (setting the base scores?)
		abilityScores[STRENGTH] = str;
		abilityScores[DEXTERITY] = dex;
		abilityScores[CONSTITUTION] = con;
		abilityScores[INTELLIGENCE] = intel;
		abilityScores[WISDOM] = wis;
		abilityScores[CHARISMA] = cha;
	}
	public void setHitPoints(int hp) { this.hp = hp; }
	public void setRemainingHitPoints(int rhp) { remainingHP = rhp; }
	public void changeRemainingHitPoints(int adj) { remainingHP += adj; }
	public void resetRemainingHitPoints() { remainingHP = hp; }
	public void setSkills(ArrayList<SkillEntity> s) {} //TODO import list of skills
	public void setRank(SkillEntity skill, int rank) {} //TODO
	public void addLanguage(String l) { languages.add(l); }
	public void addFeat(FeatEntity f) { feats.add(f); }
	public void addSpecialAbility(String sa) { specialAbilities.add(sa); }
	public void addSpell(Spell s) { spells.add(s); }
	public void prepSpell(Spell s) { prepSpells.add(s); } // TODO type spell or string?
	public void unprepSpell(Spell s) { prepSpells.remove(s); } // remove spell from prepSpell list
	public void addItem(Item i) { items.add(i); }
	public void addWeapon(Weapon w) { weapons.add(w); }
	public void addArmor(Armor a) { armors.add(a); }
	public void setNotes(String n) { notes = n; } // TODO add to/edit? delete?
	
	
	
	
	
}
