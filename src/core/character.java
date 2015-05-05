package core;
import java.util.ArrayList;

import entity.*;
import guis.CharacterMain;

public class character {	

    //////////////// VARIABLES /////////////////
    private String name = " ";
    private int level = 1;
    private int exp = 0;
    private ClassEntity secClass;//
    private int secLevel = 0;//
    private ClassEntity charClass = null;
    private RaceEntity charRace = null;	
    private ClassEntity charSecClass = null;
    private String alignment = " ";
    private String deity = " ";
    private int size = 0;
    private String age = " "; 
    private String gender = " ";
    private String height = " "; 
    private String weight = " "; 
    private String eyes = " "; 
    private String hair = " "; 
    private String skin = " "; 
    private String description = " "; 
    private int[] abilityScores = {0,0,0,0,0,0};							// STR, DEX, CON, INT, WIS, CHA
    private int hp = 0; 													// hitpoints
    private int dmg = 0;
    private ArrayList<CharSkill> skills = new ArrayList<CharSkill>();
    private ArrayList<String> languages = new ArrayList<String>();
    private int gp = 0;
    private int pp = 0;//
    private int sp = 0;//
    private int cp = 0;//
    private String imageUrl;//
    private ArrayList<CharFeat> feats = new ArrayList<CharFeat>();
    private ArrayList<AbilityEntity> specialAbilities = new ArrayList<AbilityEntity>();
    private ArrayList<SpellEntity> spells = new ArrayList<SpellEntity>();
    private ArrayList<SpellEntity> prepSpells = new ArrayList<SpellEntity>();
    private ArrayList<CharItem> items = new ArrayList<CharItem>();
    private ArrayList<CharItem> weapons = new ArrayList<CharItem>();
    private ArrayList<CharItem> armor = new ArrayList<CharItem>();
    private ArrayList<CharItem> shields = new ArrayList<CharItem>();
    private String notes = " ";
    private ItemEntity currArmor;//
    private ItemEntity currShield;//
    private WeaponEntity priWeapon;//
    private WeaponEntity secWeapon;//

    private int[] AC = {10, 0, 0, 0, 0, 0};									//10 + armor bonus + shield bonus + Dex modifier + size modifier + misc modifier
    private int touchAC = (AC[0] + AC[3] + AC[4] + AC[5]); 					// same as ac, but without shield or armor bonuses
    private int flatFootedAC = (AC[0] + AC[1] + AC[2] + AC[4] + AC[5]);		// same as ac, but without dex bonus
    private int[] initMod = {0, 0};											// dex mod + misc mod
    private int[] fortSave = {0, 0, 0, 0}; 									// base save + ability mod + magic mod + misc mod
    private int[] reflexSave = {0, 0, 0, 0};  								// base save + ability mod + magic mod + misc mod
    private int[] willSave = {0, 0, 0, 0};  								// base save + ability mod + magic mod + misc mod
    private int[][] savingThrows = {fortSave, reflexSave, willSave};
    private int baseAttackBonus = 0;
    private int spellResistance = 0;
    private int[] grappleMod = {0, 0, 0, 0};								// base attack bonus + str mod + size mod + misc mod
    private int speed = 0;	// feet
    private int damageReduction = 0;

    private String[] clericDomains = null;
    private String druidAnimalCompanion = null;
    private String rangerFavoredEnemy = null;
    private String familiar = null;
    private String wizardSpecialtySchool = null;
    private String[] wizardProhibitedSchools = null;
    private String filename;
    private CharacterMain charMain;


    //////////////// METHODS ///////////////////
    public character() {}
    
    public CharacterMain getCharMain() { return charMain; }
    public void setCharMain(CharacterMain cm) { charMain = cm; }

    public String getName() { return name; }
    public void setName(String n) { name = n; }

    public void setLevel(int l) { level = l; }
    public void incLevel() { level++; }
    public int getLevel() { return level; }

    public void setSecLevel(int l) { secLevel = l; }
    public int getSecLevel() { return secLevel; }

    public void setExp(int exp) { this.exp = exp; }
    public void incExp(int exp) { this.exp += exp; }
    public int getExp() {return exp;}

    public void setCharRace(RaceEntity r) { charRace = r; }
    public RaceEntity getCharRace() { return charRace; }

    public void setCharClass(ClassEntity c) { charClass = c; }
    public ClassEntity getCharClass() { return charClass; }

    public void setSetClass(ClassEntity c) { secClass = c; }
    public ClassEntity getSecClass() { return secClass; }

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
    /**
     * Set the ability score for the character.
     * @param str
     * @param dex
     * @param con
     * @param intel
     * @param wis
     * @param cha
     */
    public void setAbilityScores(int str, int dex, int con, int intel, int wis, int cha) { // TODO change this to AbilityScore class (setting the base scores?)
        abilityScores[GameState.STRENGTH] = str;
        abilityScores[GameState.DEXTERITY] = dex;
        abilityScores[GameState.CONSTITUTION] = con;
        abilityScores[GameState.INTELLIGENCE] = intel;
        abilityScores[GameState.WISDOM] = wis;
        abilityScores[GameState.CHARISMA] = cha;
        setGrappleStrMod(getAbilityModifiers()[GameState.STRENGTH]);
        setInitDexMod(getAbilityModifiers()[GameState.DEXTERITY]);
        setACDexMod(getAbilityModifiers()[GameState.DEXTERITY]);
        setFortSaveConMod(getAbilityModifiers()[GameState.CONSTITUTION]);
        setReflexSaveDexMod(getAbilityModifiers()[GameState.DEXTERITY]);
        setWillSaveWisMod(getAbilityModifiers()[GameState.WISDOM]);
    }
    public void modifyAbilityScores(int[] mods) {
        if (mods.length != 6)
            return;
        for (int i = 0; i < mods.length; i++) 
            abilityScores[i] += mods[i];
        setGrappleStrMod(getAbilityModifiers()[GameState.STRENGTH]);
        setInitDexMod(getAbilityModifiers()[GameState.DEXTERITY]);
        setACDexMod(getAbilityModifiers()[GameState.DEXTERITY]);
        setFortSaveConMod(getAbilityModifiers()[GameState.CONSTITUTION]);
        setReflexSaveDexMod(getAbilityModifiers()[GameState.DEXTERITY]);
        setWillSaveWisMod(getAbilityModifiers()[GameState.WISDOM]);
    }

    public int[] getAbilityScores() { return abilityScores; }
    /**
     * The ability modifier equation is (ability score / 2) - 5, round down
     * @return an int array of ability modifiers of the character.
     */
    public int[] getAbilityModifiers() {
        int[] mods = new int[6];
        for (int i = 0; i < abilityScores.length; i++)
            mods[i] = (abilityScores[i]/2)-5;
        return mods;
    }
    
    public int getAbilityModifier(int base) {
    	return (base/2)-5;
    }

    public void setImage(String url){ imageUrl = url; }
    public String getImage() { return imageUrl; }

    public void setPrimaryWeapon(WeaponEntity pri){ priWeapon = pri; }
    public WeaponEntity getPrimaryWeapon() { return priWeapon; }

    public void setSecondaryWeapon(WeaponEntity sec){ secWeapon= sec; }
    public WeaponEntity getSecondaryWeapon() { return secWeapon; }

    public void setCurrShield(ItemEntity shi){ //TODO fix armor switching and shield switching
        int temp = 0;
        if (currShield != null) 
            temp -= ((ArmorEntity)currShield).getArmorBonus();
        if (shi != null)  
            temp += ((ArmorEntity)shi).getArmorBonus();
        currShield = shi; 
        this.setACShieldBonus(this.getACShieldBonus() + temp);
    }
    public ItemEntity getCurrShield() { return currShield; }

    public void setCurrArmor(ItemEntity arm){ 
        int temp = 0;
        if (currArmor != null) 
            temp -= ((ArmorEntity)currArmor).getArmorBonus();
        if (arm != null)  
            temp += ((ArmorEntity)arm).getArmorBonus();
        currArmor = arm; 
        this.setACArmorBonus(this.getACArmorBonus() + temp);
    }
    public ItemEntity getCurrArmor() { return currArmor; }

    public void setHitPoints(int hp) { this.hp = hp; resetDamage(); }
    public void modHitPoints(int inc) { this.hp += inc; }
    public int getHitPoints() { return hp; }

    public void setDamageTaken(int rhp) { dmg = rhp; }
    public int getDamageTaken(){ return dmg; }

    public void adjDamage(int adj) { dmg += adj; }
    public void resetDamage() { dmg = 0; }

    public void setSkills(ArrayList<CharSkill> s) { skills = s; }
    public ArrayList<CharSkill> getSkills(){ return skills;}

    public void addSkill (CharSkill cs) { skills.add(cs); }

    public void setLanguages(ArrayList<String> l) { languages = l; }
    public ArrayList<String> getLanguages(){ return languages; }
    public void addLanguage(String l) { languages.add(l); }

    public void setGold(int[] gold) {
        if (gold.length != 4)
            return;
        pp = gold[0];
        gp = gold[1];
        sp = gold[2];
        cp = gold[3];
    }
    public int[] getGold() {
        int[] tmp = {pp, gp, sp, cp};
        return tmp;
    }

    public void setPP(int g) { pp = g; }
    public int getPP() { return pp; }

    public void setGP(int g) { gp = g; }
    public int getGP() { return gp; }

    public void setSP(int g) { sp = g; }
    public int getSP() { return sp; }

    public void setCP(int g) { cp = g; }
    public int getCP() { return cp; }

    public void addFeat(CharFeat f) { feats.add(f); }
    public void delFeat(CharFeat f) { feats.remove(f); }
    public ArrayList<CharFeat> getFeats() { return feats; }
    public void setFeats(ArrayList<CharFeat> feats) { this.feats = feats; }

    public void addSpecialAbility(AbilityEntity a) { specialAbilities.add(a); }
    public void delSpecialAbility(AbilityEntity a) { specialAbilities.remove(a); }
    public ArrayList<AbilityEntity> getSpecialAbilities() { return specialAbilities; }

    public void addSpell(SpellEntity s) { spells.add(s); }
    public void delSpell(SpellEntity s) {spells.remove(s); }
    public void setSpells(ArrayList<SpellEntity> s) { spells = s; }
    public ArrayList<SpellEntity> getSpells() { return spells; }

    public void prepSpell(SpellEntity s) { prepSpells.add(s); } // TODO type spell or string?
    public void unprepSpell(SpellEntity s) { prepSpells.remove(s); } // remove spell from prepSpell list
    public ArrayList<SpellEntity> getPrepSpells() { return prepSpells; }
    public void resetPrepSpells() { prepSpells = new ArrayList<SpellEntity>(); }

    public void addItem(CharItem i) { items.add(i); }
    public void delItem(CharItem i) { items.remove(i); }
    public void setItems(ArrayList<CharItem> i) { items = i; }
    public ArrayList<CharItem> getItems() { return items; }	

    public void addWeapon(CharItem w) { weapons.add(w); }
    public void delWeapon(CharItem w) { weapons.remove(w); }
    public void setWeapons(ArrayList<CharItem> w) { weapons = w; }
    public ArrayList<CharItem> getWeapons() { return weapons; }

    public void addArmor(CharItem a) { armor.add(a); }
    public void delArmor(CharItem a) { armor.remove(a); }
    public void setArmor(ArrayList<CharItem> a) { armor = a; }
    public ArrayList<CharItem> getArmor() { return armor; }

    public void addShield(CharItem a) { shields.add(a); }
    public void delShield(CharItem a) { shields.remove(a); }
    public void setShields(ArrayList<CharItem> a) { shields = a; }
    public ArrayList<CharItem> getShields() { return shields; }


    public void setNotes(String n) { notes = n; }
    public String getNotes() { return notes; }

    //10 + armor bonus + shield bonus + Dexterity modifier + size modifier + misc modifier
    public void setAC(int[] ac) { 
        if (ac.length != 5)
            return;
        // first number is always 10
        for (int i = 0; i < AC.length; i++)
            AC[i+1] = ac[i];
    }
    public void setACArmorBonus(int a) { AC[1] = a; }
    public void setACShieldBonus(int a) { AC[2] = a; }
    public void setACDexMod(int a) { AC[3] = a; }
    public void setACSizeMod(int a) { AC[4] = a; }
    public void setACMiscMod(int a) { AC[5] = a; }
    public int getACArmorBonus() { return AC[1]; }
    public int getACShieldBonus() { return AC[2]; }
    public int getACDexMod() { return AC[3]; }
    public int getACSizeMod() { return AC[4]; }
    public int getACMiscMod() { return AC[5]; }
    public int[] getAC() { return AC; }
    public int getACTotal() {
        int total = 0;
        for (int i = 0; i < AC.length; i++)
            total += AC[i];
        return total;
    }

    public int getTouchAC() { 
        touchAC = (AC[0] + AC[3] + AC[4] + AC[5]);
        return touchAC; 
        }

    public int getFlatFootedAC() {
        flatFootedAC = (AC[0] + AC[1] + AC[2] + AC[4] + AC[5]); 
        return flatFootedAC; 
        }

    public void setInitMod(int[] i) { 
        if (i.length != 2)
            return;
        initMod = i; 
    }
    public void setInitDexMod(int a) { initMod[0] = a; }
    public void setInitMiscMod(int a) { initMod[1] = a; }
    public int[] getInitMod() { return initMod; }
    public int getInitDexMod() { return initMod[0]; }
    public int getInitMiscMod() { return initMod[1]; }
    public int getInitModTotal() {
        int total = 0;
        for (int i = 0; i < initMod.length; i++)
            total += initMod[i];
        return total;
    }

    // base save + ability mod + magic mod + misc mod
    public void setSavingThrows(int[] f, int[] r, int[] w) { 
        setFortSave(f);
        setReflexSave(r);
        setWillSave(w);
    }
    public void setFortSave(int[] f) { 
        if (f.length != 4)
            return;
        fortSave = f; 
    }
    public void setReflexSave(int[] r) { 
        if (r.length != 4)
            return;
        reflexSave = r; 
    }
    public void setWillSave(int[] w) { 
        if (w.length != 4)
            return;
        willSave = w; 
    }
    public void setFortSaveBaseSave(int a) { fortSave[0] = a; }
    public void setFortSaveConMod(int a) { fortSave[1] = a; }
    public void setFortSaveMagicMod(int a) { fortSave[2] = a; }
    public void setFortSaveMiscMod(int a) { fortSave[3] = a; }
    public void setReflexSaveBaseSave(int a) { reflexSave[0] = a; }
    public void setReflexSaveDexMod(int a) { reflexSave[1] = a; }
    public void setReflexSaveMagicMod(int a) { reflexSave[2] = a; }
    public void setReflexSaveMiscMod(int a) { reflexSave[3] = a; }
    public void setWillSaveBaseSave(int a) { willSave[0] = a; }
    public void setWillSaveWisMod(int a) { willSave[1] = a; }
    public void setWillSaveMagicMod(int a) { willSave[2] = a; }
    public void setWillSaveMiscMod(int a) { willSave[3] = a; }
    public int[][] getSavingThrows() { return savingThrows; }
    public int[] getFortSave() { return fortSave; }
    public int[] getReflexSave() { return reflexSave; }
    public int[] getWillSave() { return willSave; }
    public int[] getSavingThrowsTotals() {
        int[] totals = {getFortSaveTotal(), getReflexSaveTotal(), getWillSaveTotal()};
        return totals;
    }
    public int getFortSaveTotal() {
        int total = 0;
        for (int i = 0; i < 4; i++)
            total += fortSave[i];
        return total;
    }
    public int getReflexSaveTotal() {
        int total = 0;
        for (int i = 0; i < 4; i++)
            total += reflexSave[i];
        return total;
    }
    public int getWillSaveTotal() {
        int total = 0;
        for (int i = 0; i < 4; i++)
            total += willSave[i];
        return total;
    }
    public int getFortSaveBaseSave() { return fortSave[0]; }
    public int  getFortSaveConMod() { return fortSave[1]; }
    public int getFortSaveMagicMod() { return fortSave[2]; }
    public int getFortSaveMiscMod() { return fortSave[3]; }
    public int getReflexSaveBaseSave() { return reflexSave[0]; }
    public int getReflexSaveDexMod() { return reflexSave[1]; }
    public int getReflexSaveMagicMod() { return reflexSave[2]; }
    public int getReflexSaveMiscMod() { return reflexSave[3]; }
    public int getWillSaveBaseSave() { return willSave[0]; }
    public int getWillSaveWisMod() { return willSave[1]; }
    public int getWillSaveMagicMod() { return willSave[2]; }
    public int getWillSaveMiscMod() { return willSave[3]; }

    public void setBaseAttackBonus(int b) {
        baseAttackBonus = b; 
        setGrappleAttackBonus(baseAttackBonus);
    }
    public int getBaseAttackBonus() { return baseAttackBonus; }

    public void setSpellResistance(int s) { spellResistance = s; }
    public int getSpellResistance() { return spellResistance; }

    public void setGrappleMod(int[] g) {
        if (g.length != 4)
            return;
        grappleMod = g; 
    }
    public void setGrappleAttackBonus(int a) { grappleMod[0] = a; }
    public void setGrappleStrMod(int a) { grappleMod[1] = a; }
    public void setGrappleSizeMod(int a) { grappleMod[2] = a; }
    public void setGrappleMiscMod(int a) { grappleMod[3] = a; }
    public int[] getGrappleMod() { return grappleMod; }
    public int getGrappleAttackBonus() { return grappleMod[0]; }
    public int getGrappleStrMod() { return grappleMod[1]; }
    public int getGrappleSizeMod() { return grappleMod[2]; }
    public int getGrappleMiscMod() { return grappleMod[3]; }
    public int getGrappleModTotal() {
        int total = 0;
        for (int i = 0; i < grappleMod.length; i++)
            total += grappleMod[i];
        return total;
    }

    public void setSpeed(int s) { speed = s; }
    public int getSpeed() { return speed; }

    public void setDamageReduction(int d) { damageReduction = d; }
    public int getDamageReduction() { return damageReduction; }

    public void setClericDomains(String[] d) {
        clericDomains = new String[d.length];
        for (int i = 0; i < d.length; i++)
            clericDomains[i] = d[i];
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



    /**
     * Print all the information of the character.
     * Seperated by a "\n" symbol.
     * @return A string that contain all the information of the character.
     */
    public String toString() {
        String s = "";
        s += "Name: " + name + "\n";
        s += "Level: " + level + "\n";
        s += "Exp: " + exp + "\n";
        if (charRace == null)
            s += "Race:  \n";
        else
            s += "Race: " + charRace.getName()  + "\n";
        if (charClass == null)
            s += "Class:  \n";
        else
            s += "Class: " + charClass.getName() + "\n";
        if (clericDomains != null) {
            s += "Cleric Domains: ";
            s += clericDomains[0];
            for (int i = 1; i < clericDomains.length; i++)
                s += ", " + clericDomains[i];
            s += "\n";
        }
        if (druidAnimalCompanion != null)
            s += "Druid Animal Companion: " + druidAnimalCompanion + "\n";
        if (rangerFavoredEnemy != null)
            s += "Ranger Favored Enemy: " + rangerFavoredEnemy + "\n";
        if (familiar != null)
            s += "Familiar: " + familiar + "\n";
        if (wizardSpecialtySchool != null)
            s += "Wizard Specialty School: " + wizardSpecialtySchool + "\n";
        if (wizardProhibitedSchools != null) {
            s += "Wizard Prohibited School(s): ";
            s += wizardProhibitedSchools[0];
            for (int i = 1; i < wizardProhibitedSchools.length; i++)
                s += ", " + wizardProhibitedSchools[i];
            s += "\n";
        }
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
        s += "Remaining HP: " + (hp - dmg) + "\n";
        s += "Armor Class: " + getACTotal() + "\n";
        s += "Touch AC: " + touchAC + "\n";
        s += "Flat-Footed AC: " + flatFootedAC + "\n";
        s += "Initiative Modifier: " + getInitModTotal() + "\n";
        s += "Saving Throws: \n";
        s += "\tFortitude: " + getFortSaveTotal() + "\n";
        s += "\tReflex: " + getReflexSaveTotal() + "\n";
        s += "\tWill: " + getWillSaveTotal() + "\n";
        s += "Base Attack Bonus: " + baseAttackBonus + "\n";
        s += "Spell Resistance: " + spellResistance + "\n";
        s += "Grapple Modifier: " + getGrappleModTotal() + "\n";
        s += "Speed(feet): " + speed + "\n";
        s += "Damage Reduction: " + damageReduction + "\n";		
        s += "Skills: " + "\n";
        for (int i = 0; i < skills.size(); i++)
            s += "\t" + skills.get(i).getSkill().getName() + ": " + skills.get(i).getRank() + "\n";
        s += "Languages: \n";
        if (languages.size() == 0)
            s += "\t \n";
        for (int i = 0; i < languages.size(); i++)
            s += "\t" + languages.get(i) + "\n";
        s += "Gold: " + gp + "\n";
        s += "Feats: " + "\n";
        if (feats.size() == 0)
            s += "\t \n";
        for (int i = 0; i < feats.size(); i++) {
            s += "\t" + feats.get(i) + "\n";
        }
        s += "Special Abilities: " + "\n";
        if (specialAbilities.size() == 0)
            s += "\t \n";
        for (int i = 0; i < specialAbilities.size(); i++) {
        	if (specialAbilities.get(i) != null)
        		s += "\t" + specialAbilities.get(i).getName() + "\n";
        }
        s += "Spells: " + "\n";
        if (spells.size() == 0)
            s += "\t \n";
        for (int i = 0; i < spells.size(); i++)
        	if (spells.get(i) == null)
        		s+= "\tnull\n";
        	else
        		s += "\t" + spells.get(i).getName() + "\n";
        s += "Prepared Spells: " + "\n";
        if (prepSpells.size() == 0)
            s += "\t \n";
        for (int i = 0; i < prepSpells.size(); i++)
            s += "\t" + prepSpells.get(i).getName() + "\n";
        s += "Items: " + "\n";
        if (items.size() == 0)
            s += "\t \n";
        for (int i = 0; i < items.size(); i++)
            s += "\t" + items.get(i).getCount() + " x " + items.get(i).getItem().getName() + "\n";
        s += "Weapons: " + "\n";
        if (weapons.size() == 0)
            s += "\t \n";
        for (int i = 0; i < weapons.size(); i++)
            s += "\t" + weapons.get(i).getCount() + " x " + weapons.get(i).getItem().getName() + "\n";
        s += "Primary Weapon: ";
        if (priWeapon == null)
            s += " \n";
        else 
            s += priWeapon.getName() + "\n";
        s += "Secondary Weapon: ";
        if (secWeapon == null)
            s += " \n";
        else 
            s += secWeapon.getName() + "\n";
        s += "Armor: " + "\n";
        if (armor.size() == 0)
            s += "\t \n";
        for (int i = 0; i < armor.size(); i++)
            s += "\t" + armor.get(i).getCount() + " x " + armor.get(i).getItem().getName() + "\n";
        s += "Current Armor: ";
        if (currArmor == null)
            s += " \n";
        else 
            s += currArmor.getName() + "\n";
        s += "Shields: " + "\n";
        if (shields.size() == 0)
            s += "\t \n";
        for (int i = 0; i < shields.size(); i++)
            s += "\t" + shields.get(i).getCount() + " x " + shields.get(i).getItem().getName() + "\n";
        s += "Current Shield: ";
        if (currShield == null)
            s += " \n";
        else 
            s += currShield.getName() + "\n";
        s += "Notes: " + notes + "\n";
        return s; 
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String fn) {
        filename = fn; 
    }
}
