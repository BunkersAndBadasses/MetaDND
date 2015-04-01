import java.util.ArrayList;


public class Character {
	
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
	private String charClass;		// change to type Class once refs are added
	private String charRace;				// ^^ ditto for Race
	private String charSecClass;
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
	private String description;
	private int[] abilityScores = new int[6];
	private int hp; // hitpoints
	private int remainingHP;
	private ArrayList<CharSkill> skillsList;
	private ArrayList<String> languages;
	private ArrayList<Feat> feats;
	private ArrayList<Ability> abilities;
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
	public void setCharClass(String c) { charClass = c; }
	public String getCharClass() { return charClass; }
	public void setCharSecClass(String c) { charSecClass = c; }
	public String getCharSecClass() { return charSecClass; }
	public void setCharRace(String r) { charRace = r; }
	public String getCharRace() { return charRace; }
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
	public void setRank(Skill skill, int rank) {} //TODO
	public void addLanguage(String l) { languages.add(l); }
	public void addFeat(Feat f) { feats.add(f); }
	public void addSpecialAbility(Ability a) { abilities.add(a); }
	public void addSpell(Spell s) { spells.add(s); }
	public void prepSpell(Spell s) { prepSpells.add(s); } // TODO type spell or string?
	public void unprepSpell(Spell s) { prepSpells.remove(s); } // remove spell from prepSpell list
	public void addItem(Item i) { items.add(i); }
	public void addWeapon(Weapon w) { weapons.add(w); }
	public void addArmor(Armor a) { armors.add(a); }
	public void setNotes(String n) { notes = n; } // TODO add to/edit? delete?
	
	
	
	
	
}
