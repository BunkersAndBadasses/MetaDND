package core;
import java.util.ArrayList;
import entity.*;

public class character {	
	
	//////////////// VARIABLES /////////////////
	private String name = "<empty>";
	private int level = 1;
	private int exp = 0;
	private ClassEntity charClass = null;
	private RaceEntity charRace = null;	
	private ClassEntity charSecClass = null;
	private String alignment = "<empty>";
	private String deity = "<empty>";
	private int size = 0;
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
	
	// TODO add
	private int AC = 0;
	private int touchAC = 0;
	private int flatFootedAC = 0;
	private int initMod = 0;//
	private int[] savingThrows = {0,0,0}; // fortitude, reflex, will
	private int baseAttackBonus = 0;
	private int spellResistance = 0;
	private int grappleMod = 0;
	private int speed = 0;//
	private int damageReduction = 0;
	private String[] clericDomains = null;
	private String druidAnimalCompanion = null;
	private String rangerFavoredEnemy = null;
	private String familiar = null;
	private String wizardSpecialtySchool = null;
	private String[] wizardProhibitedSchools = null;
	
	
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
		abilityScores[GameState.STRENGTH] = str;
		abilityScores[GameState.DEXTERITY] = dex;
		abilityScores[GameState.CONSTITUTION] = con;
		abilityScores[GameState.INTELLIGENCE] = intel;
		abilityScores[GameState.WISDOM] = wis;
		abilityScores[GameState.CHARISMA] = cha;
	}
	public int[] getAbilityScores() { return abilityScores; }
	public int[] getAbilityModifiers() {
		int[] mods = new int[6];
		for (int i = 0; i < abilityScores.length; i++)
			mods[i] = (abilityScores[i]/2)-5;
		return mods;
	}
	public boolean checkreroll()
	{
		//reroll is true if ability modifiers sum <= 0
		//or the highest die roll is less or equal 13
		try
		{
		int totalmod = 0;
		int[] mods = getAbilityModifiers();
		for(int i = 0; i < mods.length; i++)
		{
			totalmod += mods[i];
		}
		if(totalmod <= 0)
		{
			return true;
		}
		int highest = 0;
		int[] scores = getAbilityScores();
		for(int j = 0; j < scores.length; j++)
		{
			if(scores[j] > highest)
			{
				highest = scores[j];
			}
		}
		if(highest <= 13)
		{
			return true;
		}
		return false;
		}
		catch(Exception a)
		{
			return false;
		}
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
		
	public void setAC(int ac) { AC = ac; }
	public int getAC() { return AC; }
	
	public void setTouchAC(int t) { touchAC = t; }
	public int getTouchAC() { return touchAC; }
	
	public void setFlatFootedAC(int f) { flatFootedAC = f; }
	public int getFlatFootedAC() { return flatFootedAC; }
	
	public void setInitMod(int i) { initMod = i; }
	public int getInitMod() { return initMod; }
	
	public void setSavingThrows(int f, int r, int w) { 
		savingThrows[0] = f;
		savingThrows[1] = r; 
		savingThrows[2] = w;
	}
	public int[] getSavingThrows() { return savingThrows; }
	
	public void setBaseAttackBonus(int b) { baseAttackBonus = b; }
	public int getBaseAttackBonus() { return baseAttackBonus; }
	
	public void setSpellResistance(int s) { spellResistance = s; }
	public int getSpellResistance() { return spellResistance; }
	
	public void setGrappleMod(int g) { grappleMod = g; }
	public int getGrappleMod() { return grappleMod; }
	
	public void setSpeed(int s) { speed = s; }
	public int getSpeed() { return speed; }
	
	public void setDamageReduction(int d) { damageReduction = d; }
	public int getDamageReduction() { return damageReduction; }
	
	public void setClericDomains(String[] d) {
		clericDomains[0] = d[0];
		clericDomains[1] = d[1];
	}
	public String[] getClericDomains() { return clericDomains; }
	
	public void setDruidAnimalCompanion(String a) { druidAnimalCompanion = a; }
	public String getDruidAnimalCompanion() { return druidAnimalCompanion; }
	
	public void setRangerFavoredEnemy(String f) { rangerFavoredEnemy = f; }
	public String getRangerFavoredEnemy() { return rangerFavoredEnemy; }
	
	public void setFamiliar(String f) { familiar = f; }
	public String getFamiliar() { return familiar; }
	
	public void setWizardSpecialtySchool(String s) { wizardSpecialtySchool = s; }
	public String getWizardSpecialtySchool() { return wizardSpecialtySchool; }
	
	public void setWizardProhibitedSchools(String[] p) {
		if (p.length > 1) {
			wizardProhibitedSchools = new String[2];
			wizardProhibitedSchools[0] = p[0];
			wizardProhibitedSchools[1] = p[1];
		} else {
			wizardProhibitedSchools = new String[1];
			wizardProhibitedSchools[0] = p[0];
		}
	}
	public String[] getWizardProhibitedSchools() { return wizardProhibitedSchools; }
	
	
	
	
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
		s += "Size: " + GameState.sizeStrings[size] + "\n";
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
			s += "\t" + GameState.abilityScoreTypes[i] + ": " + abilityScores[i] + "\n";
		s += "HP: " + hp + "\n";
		s += "Remaining HP: " + remainingHP + "\n";
		s += "Skills: " + "\n";
		for (int i = 0; i < skills.size(); i++)
			s += "\t" + skills.get(i).getSkill().getName() + ": " + skills.get(i).getRank() + "\n";
		s += "Languages: " + languages + "\n";
		s += "Gold: " + gold + "\n";
		s += "Feats: " + "\n";
		if (feats.size() == 0)
			s += "<empty>\n";
		for (int i = 0; i < feats.size(); i++)
			s += "\t" + feats.get(i).getName() + "\n";
		s += "Special Abilities: " + "\n";
		if (specialAbilities.size() == 0)
			s += "<empty>\n";
		for (int i = 0; i < specialAbilities.size(); i++)
			s += "\t" + specialAbilities.get(i).getName() + "\n";
		s += "Spells: " + "\n";
		if (spells.size() == 0)
			s += "<empty>\n";
		for (int i = 0; i < spells.size(); i++)
			s += "\t" + spells.get(i).getName() + "\n";
		s += "Prepared Spells: " + "\n";
		if (prepSpells.size() == 0)
			s += "<empty>\n";
		for (int i = 0; i < prepSpells.size(); i++)
			s += "\t" + prepSpells.get(i).getName() + "\n";
		s += "Items: " + "\n";
		if (items.size() == 0)
			s += "<empty>\n";
		for (int i = 0; i < items.size(); i++)
			s += "\t" + items.get(i).getItem().getName() + ": " + items.get(i).getCount() + "\n";
		s += "Weapons: " + "\n";
		if (weapons.size() == 0)
			s += "<empty>\n";
		for (int i = 0; i < weapons.size(); i++)
			s += "\t" + weapons.get(i).getName() + "\n";
		s += "Armor: " + "\n";
		if (armor.size() == 0)
			s += "<empty>\n";
		for (int i = 0; i < armor.size(); i++)
			s += "\t" + armor.get(i).getName() + "\n";
		s += "Notes: " + notes + "\n";
		return s; 
	}
}
