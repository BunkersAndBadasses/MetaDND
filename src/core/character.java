package core;
import java.util.ArrayList;
import entity.*;

public class character {
	
	//////////////// CONSTANTS /////////////////
	public final static int STRENGTH = 0;
	public final static int DEXTERITY = 1;
	public final static int CONSTITUTION = 2;
	public final static int INTELLIGENCE = 3;
	public final static int WISDOM = 4;
	public final static int CHARISMA = 5;
	public final static String[] abilityScoreTypes = {"STR", "DEX", "CON", "INT", "WIS", "CHA"};
	
	private final static int SIZE_FINE = 0;
	private final static int SIZE_DIMINUTIVE = 1;
	private final static int SIZE_TINY = 2;
	private final static int SIZE_SMALL = 3;
	private final static int SIZE_MEDIUM = 4;
	private final static int SIZE_LARGE = 5;
	private final static int SIZE_HUGE = 6;
	private final static int SIZE_GARGANTUAN = 7;
	private final static int SIZE_COLOSSAL = 8;
	
	//////////////// VARIABLES /////////////////
	private String name;
	private int level = 1;
	private int exp = 0;
	private ClassEntity charClass;		// change to type Class once refs are added
	private RaceEntity charRace;				// ^^ ditto for Race
	private ClassEntity charSecClass;
	private String alignment;
	private String deity;
	private int size; // TODO int? string?
	private String age; 
	private String gender;
	private String height;
	private String weight;
	private String eyes;
	private String hair;
	private String skin;
	private String[] appearance = {eyes, hair, skin};
	private String description;
	// "STR", "DEX", "CON", "INT", "WIS", "CHA"
	private int[] abilityScores = new int[6];
	private int hp; // hitpoints
	private int remainingHP;
	private ArrayList<CharSkill> skillsList;
	private String languages;
	private int gold;
	private ArrayList<FeatEntity> feats = new ArrayList<FeatEntity>();
	private ArrayList<AbilityEntity> specialAbilities = new ArrayList<AbilityEntity>();
	private ArrayList<SpellEntity> spells = new ArrayList<SpellEntity>();
	private ArrayList<SpellEntity> prepSpells = new ArrayList<SpellEntity>();
	private ArrayList<CharItem> items = new ArrayList<CharItem>();
	private ArrayList<WeaponEntity> weapons = new ArrayList<WeaponEntity>();
	private ArrayList<ArmorEntity> armor = new ArrayList<ArmorEntity>();
	private String notes;

	
	public character() {}
	
	public String getName() { return name; }
	public void setName(String n) { name = n; }
	
	public void setLevel(int l) { level = l; }
	public int getLevel() { return level; }
	
	public void setExp(int exp) { this.exp = exp; }
	public void incExp(int exp) { this.exp += exp; }
	public int getExp() {return exp;}
	
	public void setCharRace(RaceEntity r) { charRace = r; }
	public RaceEntity getCharRace() { return charRace; }
	
	public void setCharClass(ClassEntity c) { charClass = c; }
	public ClassEntity getCharClass() { return charClass; }
	
	public void setCharSecClass(ClassEntity c) { charSecClass = c; }
	public ClassEntity getCharSecClass() { return charSecClass; }

	public void setAlignment(String a) { alignment = a; }
	public String getAlignment() { return alignment; }

	public void setDeity(String d) { deity = d; }
	public String getDeity() { return deity; }
	
	public void setSize(int s) { size = s; }
	public int getSize(){ return size; }

	public void setAge(String a) { age = a; }
	public String getAge(){ return age; }

	public void setGender(String g) { gender = g; }
	public String getGender(){ return gender; } 

	public void setHeight(String h) { height = h; }
	public String getHeight(){ return height; }

	public void setWeight(String w) { weight = w; }
	public String getWeight(){ return weight; }

	public void setAppearance(String eyes, String hair, String skin) {
		this.eyes = eyes;
		this.hair = hair;
		this.skin = skin;
	}
	public String getEyes(){ return eyes; }
	public String getHair(){ return hair; }
	public String getSkin(){ return skin; }
	
	public void setDescription(String d) { description = d; }
	public String getDescription(){ return description; }

	public void setAbilityScores(int str, int dex, int con, int intel, int wis, int cha) { // TODO change this to AbilityScore class (setting the base scores?)
		abilityScores[STRENGTH] = str;
		abilityScores[DEXTERITY] = dex;
		abilityScores[CONSTITUTION] = con;
		abilityScores[INTELLIGENCE] = intel;
		abilityScores[WISDOM] = wis;
		abilityScores[CHARISMA] = cha;
	}
	public int[] getAbilityScores() { return abilityScores; }
	
	public void setHitPoints(int hp) { this.hp = hp; }
	public int getHitPoints() { return hp; }
	
	public void setRemainingHitPoints(int rhp) { remainingHP = rhp; }
	public int getRemainingHitPoints(){ return remainingHP; }

	public void changeRemainingHitPoints(int adj) { remainingHP += adj; }
	public void resetRemainingHitPoints() { remainingHP = hp; }
	
	public void setSkills(ArrayList<CharSkill> s) { skillsList = s; }
	public ArrayList<CharSkill> getSkills(){ return skillsList;}

	public void setLanguages(String l) { languages = l; }
	public String getLanguages(){ return languages; }
	public void addLanguage(String l) { languages += ", " + l; }
	
	public void setGold(int g) { gold = g; }
	public int getgold() { return gold; }
	
	public void addFeat(FeatEntity f) { feats.add(f); }
	public ArrayList<FeatEntity> getFeats() { return feats; }
	
	public void addSpecialAbility(AbilityEntity a) { specialAbilities.add(a); }
	public ArrayList<AbilityEntity> getSpecialAbilities() { return specialAbilities; }
	
	public void addSpell(SpellEntity s) { spells.add(s); }
	public void setSpells(ArrayList<SpellEntity> s) { spells = s; }
	public ArrayList<SpellEntity> getSpells() { return spells; }
	
	public void prepSpell(SpellEntity s) { prepSpells.add(s); } // TODO type spell or string?
	public void unprepSpell(SpellEntity s) { prepSpells.remove(s); } // remove spell from prepSpell list
	public ArrayList<SpellEntity> getPrepSpells() { return prepSpells; }
	
	public void addItem(CharItem i) { items.add(i); }
	public void setItems(ArrayList<CharItem> i) { items = i; }
	public ArrayList<CharItem> getItems() { return items; }	
	
	public void addWeapon(WeaponEntity w) { weapons.add(w); }
	public void setWeapons(ArrayList<WeaponEntity> w) { weapons = w; }
	public ArrayList<WeaponEntity> getWeapons() { return weapons; }
	
	public void addArmor(ArmorEntity a) { armor.add(a); }
	public void setArmor(ArrayList<ArmorEntity> a) { armor = a; }
	public ArrayList<ArmorEntity> getArmor() { return armor; }
	
	public void setNotes(String n) { notes = n; } // TODO add to/edit? delete?
	public String getNotes() { return notes; }
		
}
