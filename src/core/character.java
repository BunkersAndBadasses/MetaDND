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
	public final static String[] sizeStrings = {"Fine", "Diminutive", "Tiny", "Small", "Medium", "Large", "Huge", "Gargantuan", "Colossal" };
	
	//////////////// VARIABLES /////////////////
	private String name = "<empty>";
	private int level = 1;
	private int exp = 0;
	private ClassEntity charClass = null;
	private RaceEntity charRace = null;	
	private ClassEntity charSecClass = null;
	private String alignment = "<empty>";
	private String deity = "<empty>";
	private int size;
	private String age = "<empty>"; 
	private String gender = "<empty>";
	private String height = "<empty>"; 
	private String weight = "<empty>"; 
	private String eyes = "<empty>"; 
	private String hair = "<empty>"; 
	private String skin = "<empty>"; 
	private String description = "<empty>"; 
	private int[] abilityScores = {0,0,0,0,0,0};	// STR, DEX, CON, INT, WIS, CHA
	private int hp = 0; // hitpoints
	private int remainingHP = 0;
	private ArrayList<CharSkill> skills = new ArrayList<CharSkill>();
	private String languages = "<empty>";
	private int gold = 0;
	private ArrayList<FeatEntity> feats = new ArrayList<FeatEntity>();
	private ArrayList<AbilityEntity> specialAbilities = new ArrayList<AbilityEntity>();
	private ArrayList<SpellEntity> spells = new ArrayList<SpellEntity>();
	private ArrayList<SpellEntity> prepSpells = new ArrayList<SpellEntity>();
	private ArrayList<CharItem> items = new ArrayList<CharItem>();
	private ArrayList<WeaponEntity> weapons = new ArrayList<WeaponEntity>();
	private ArrayList<ArmorEntity> armor = new ArrayList<ArmorEntity>();
	private String notes = "<empty>";

	
	//////////////// METHODS ///////////////////
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

	public void setEyes(String e) { eyes = e; }
	public String getEyes(){ return eyes; }
	
	public void setHair(String h) { hair = h; }
	public String getHair(){ return hair; }
	
	public void setSkin(String s) { skin = s; }
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
	public int[] getAbilityModifiers() {
		int[] mods = new int[6];
		for (int i = 0; i < abilityScores.length; i++)
			mods[i] = (abilityScores[i]/2)-5;
		return mods;
	}
	
	public void setHitPoints(int hp) { this.hp = hp; resetRemainingHitPoints(); }
	public int getHitPoints() { return hp; }
	
	public void setRemainingHitPoints(int rhp) { remainingHP = rhp; }
	public int getRemainingHitPoints(){ return remainingHP; }

	public void changeRemainingHitPoints(int adj) { remainingHP += adj; }
	public void resetRemainingHitPoints() { remainingHP = hp; }
	
	public void setSkills(ArrayList<CharSkill> s) { skills = s; }
	public ArrayList<CharSkill> getSkills(){ return skills;}

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
		
	
	public String toString() {
		String s = "";
		s += "Name: " + name + "\n";
		s += "Level: " + level + "\n";
		s += "Exp: " + exp + "\n";
		if (charRace == null)
			s += "Race: <empty>\n";
		else
			s += "Race: " + charRace.getName()  + "\n";
		if (charClass == null)
			s += "Class: <empty>\n";
		else
			s += "Class: " + charClass.getName() + "\n";
		if (charSecClass != null)
			s += "Second Class: " + charSecClass.getName() + "\n";
		s += "Alignment: " + alignment + "\n";
		s += "Deity: " + deity + "\n";
		s += "Size: " + sizeStrings[size] + "\n";
		s += "Age: " + age + "\n";
		s += "Gender: " + gender + "\n";
		s += "Height: " + height + "\n";
		s += "Weight: " + weight + "\n";
		s += "Eyes: " + eyes + "\n";
		s += "Hair: " + hair + "\n";
		s += "Skin: " + skin + "\n";
		s += "Description: " + description + "\n";
		s += "Ability Scores: " + "\n";
		for (int i = 0; i < abilityScores.length; i++)
			s += "\t" + abilityScoreTypes[i] + ": " + abilityScores[i] + "\n";
		s += "HP: " + hp + "\n";
		s += "Remaining HP: " + remainingHP + "\n";
		s += "Skills: " + "\n";
		for (int i = 0; i < skills.size(); i++)
			s += "\t" + skills.get(i).getSkill().getName() + ": " + skills.get(i).getRank() + "\n";
		s += "Languages: " + languages + "\n";
		s += "Gold: " + gold + "\n";
		s += "Feats: " + "\n";
		for (int i = 0; i < feats.size(); i++)
			s += "\t" + feats.get(i).getName() + "\n";
		s += "Special Abilities: " + "\n";
		for (int i = 0; i < specialAbilities.size(); i++)
			s += "\t" + specialAbilities.get(i).getName() + "\n";
		s += "Spells: " + "\n";
		for (int i = 0; i < spells.size(); i++)
			s += "\t" + spells.get(i).getName() + "\n";
		s += "Prepared Spells: " + "\n";
		for (int i = 0; i < prepSpells.size(); i++)
			s += "\t" + prepSpells.get(i).getName() + "\n";
		s += "Items: " + "\n";
		for (int i = 0; i < items.size(); i++)
			s += "\t" + items.get(i).getItem().getName() + ": " + items.get(i).getCount() + "\n";
		s += "Weapons: " + "\n";
		for (int i = 0; i < weapons.size(); i++)
			s += "\t" + weapons.get(i).getName() + "\n";
		s += "Armor: " + "\n";
		for (int i = 0; i < armor.size(); i++)
			s += "\t" + armor.get(i).getName() + "\n";
		s += "Notes: " + notes + "\n";
		return s; 
	}
}
