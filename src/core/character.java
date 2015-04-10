package core;
import java.util.ArrayList;
import entity.*;

public class character {
	
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
	private ArrayList<AbilityEntity> abilities = new ArrayList<AbilityEntity>();
	private ArrayList<SpellEntity> spells = new ArrayList<SpellEntity>();
	private ArrayList<SpellEntity> prepSpells = new ArrayList<SpellEntity>();
	private ArrayList<CharItem> items = new ArrayList<CharItem>();
	private ArrayList<WeaponEntity> weapons = new ArrayList<WeaponEntity>();
	private ArrayList<ArmorEntity> armors = new ArrayList<ArmorEntity>();
	private String notes;
	
	
	
	public character() {}
	
	public String getName() { return name; }
	public void setName(String n) { name = n; }
	public void setLevel(int l) { level = l; }
	public void setExp(int exp) { this.exp = exp; }
	public void incExp(int exp) { this.exp += exp; }
	public void setCharClass(ClassEntity c) { charClass = c; }
	public ClassEntity getCharClass() { return charClass; }
	public void setCharSecClass(ClassEntity c) { charSecClass = c; }
	public ClassEntity getCharSecClass() { return charSecClass; }
	public void setCharRace(RaceEntity r) { charRace = r; }
	public RaceEntity getCharRace() { return charRace; }
	public void setAlignment(String a) { alignment = a; }
	public void setDeity(String d) { deity = d; }
	public void setSize(int s) { size = s; }
	public void setAge(String a) { age = a; }
	public void setGender(String g) { gender = g; }
	public void setHeight(String h) { height = h; }
	public void setWeight(String w) { weight = w; }
	public void setAppearance(String eyes, String hair, String skin) {
		this.eyes = eyes;
		this.hair = hair;
		this.skin = skin;
	}
	public void setDescription(String d) { description = d; }
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
	public void setRemainingHitPoints(int rhp) { remainingHP = rhp; }
	public void changeRemainingHitPoints(int adj) { remainingHP += adj; }
	public void resetRemainingHitPoints() { remainingHP = hp; }
	public void setSkills(ArrayList<CharSkill> s) { skillsList = s; }
	public void setRank(SkillEntity skill, int rank) {} //TODO
	public void setLanguages(String l) { languages = l; }
	public void addLanguage(String l) { languages += ", " + l; }
	public void setGold(int g) { gold = g; }
	public int getgold() { return gold; }
	public void addFeat(FeatEntity f) { feats.add(f); }
	public void addSpecialAbility(AbilityEntity a) { abilities.add(a); }
	public void addSpell(SpellEntity s) { spells.add(s); }
	public void prepSpell(SpellEntity s) { prepSpells.add(s); } // TODO type spell or string?
	public void unprepSpell(SpellEntity s) { prepSpells.remove(s); } // remove spell from prepSpell list
	public void addItem(CharItem i) { items.add(i); }
	public void addWeapon(WeaponEntity w) { weapons.add(w); }
	public void addArmor(ArmorEntity a) { armors.add(a); }
	public void setNotes(String n) { notes = n; } // TODO add to/edit? delete?
	public int getLevel() { return level;}
	public int getExp() {return exp;}
	public String getAlignment() {return alignment;}
	public int getSize(){return size;}
	public String getAge(){return age;}
	public String getGender(){return gender;}
	public String getHeight(){return height;}
	public String getWeight(){return weight;}
	public String getEyes(){return eyes;}
	public String getHair(){return hair;}
	public String getSkin(){return skin;}
	public String getDescription(){return description;}
	public int getHp(){return hp;}
	public int getRemainingHp(){return remainingHP;}
	public ArrayList<CharSkill> getSkills(){ return skillsList;}
	public String getLanguages(){return languages;}
	// blah
	
	
	
}
