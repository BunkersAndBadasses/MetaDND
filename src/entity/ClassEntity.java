package entity;

import java.util.LinkedHashMap;
import java.util.Map;

import core.Main;

public class ClassEntity extends DNDEntity {

	String alignmentRestriction;
	String[] bonusLanguages;
	String hitDie;
	String[] baseAttackBonusString;
	int [] baseAttackBonus;
	int[] fortSave;
	int[] reflexSave;
	int[] willSave;
	String special;
	int[][] spellsPerDay;
	int[][] spellsKnown;
	String classSkills;
	String skillPointsFirstLevel;
	String skillPointsPerLevel;
	String[] bonusFeats;
	String classAbilities;
	String druidCompanion;
	String paladinMount;
	String familiar;
	String exclass;
	boolean caster;

	// TODO Needs a lot of additional additions based on other object types,
	// only baseline is here for functionality
	public ClassEntity(LinkedHashMap<String, String> input) {
		this.TYPE = DNDEntity.type.CLASS;
		passedData = input;
		for (Map.Entry<String, String> entry : input.entrySet()) {
			String field = entry.getKey();
			String value = entry.getValue();
			switch (field) {
			case "NAME":
				this.name = value;
				break;
			case "ALIGNMENTRESTRICTIONS":
				this.alignmentRestriction = value;
				break;
			case "BONUSLANGUAGE":
				this.bonusLanguages = value.split(", ");
				break;
			case "HITDIE":
				this.hitDie = value.substring(0, value.length() - 1);
				break;
			case "BASEATTACKBONUS":
				this.baseAttackBonusString = value.split(", ");
				baseAttackBonus = new int[baseAttackBonusString.length];
				for (int i = 0; i < baseAttackBonusString.length; i++) {
					int index = baseAttackBonusString[i].indexOf('/');
					if (index == -1)
						baseAttackBonus[i] = Integer.parseInt(baseAttackBonusString[i]);
					else
						baseAttackBonus[i] = Integer.parseInt(baseAttackBonusString[i].substring(0, index));
				}
				break;
			case "FORTSAVE":
				String[] fortSaveList = value.split(", ");
				this.fortSave = new int[fortSaveList.length];
				for (int i = 0; i < fortSaveList.length; i++) {
					this.fortSave[i] = Integer.parseInt(fortSaveList[i]);
				}
				break;
			case "REFLEXSAVE":
				String[] reflexSaveList = value.split(", ");
				this.reflexSave = new int[reflexSaveList.length];
				for (int i = 0; i < reflexSaveList.length; i++) {
					this.reflexSave[i] = Integer.parseInt(reflexSaveList[i]);
				}
				break;
			case "WILLSAVE":
				String[] willSaveList = value.split(", ");
				this.willSave = new int[willSaveList.length];
				for (int i = 0; i < willSaveList.length; i++) {
					this.willSave[i] = Integer.parseInt(willSaveList[i]);
				}
				break;
			case "SPECIAL":
				this.special = value; // Needs refinement in XML for specific
										// level values, but this will work for
										// now
				break;
			case "SPELLSPERDAY":
				//System.out.println("Spells per day: " + this.name);
				boolean arrayInitialized = false;
				String[] charLevel = value.split("\n");
				for (int i = 0; i < charLevel.length; i++){
					charLevel[i] = charLevel[i].trim();
					String[] spellLevelSplit = charLevel[i].split(", ");
					if(!arrayInitialized){
						this.spellsPerDay = new int[charLevel.length][spellLevelSplit.length];
						arrayInitialized = true;
					}
					for(int j = 0; j < spellLevelSplit.length; j++){
						if(spellLevelSplit[j].equalsIgnoreCase("-")){
							spellLevelSplit[j] = "0";
						}
						this.spellsPerDay[i][j] = Integer.parseInt(spellLevelSplit[j]);
					}
				}
				break;
			case "SPELLSKNOWN":
				//System.out.println("Spells per day: " + this.name);
				boolean arrayInitialized1 = false;
				String[] charLevel1 = value.split("\n");
				for (int i = 0; i < charLevel1.length; i++){
					charLevel1[i] = charLevel1[i].trim();
					String[] spellLevelSplit = charLevel1[i].split(", ");
					if(!arrayInitialized1){
						this.spellsKnown = new int[charLevel1.length][spellLevelSplit.length];
						arrayInitialized1 = true;
					}
					for(int j = 0; j < spellLevelSplit.length; j++){
						if(spellLevelSplit[j].equalsIgnoreCase("-")){
							spellLevelSplit[j] = "-1";
						}
						this.spellsKnown[i][j] = Integer.parseInt(spellLevelSplit[j]);
					}
				}
				break;
			case "SKILLPOINTSFIRST":
				this.skillPointsFirstLevel = value;
				break;
			case "SKILLPOINTS":
				this.skillPointsPerLevel = value;
				break;
			case "BONUSFEATS": // TODO replace with proper feat enitity objects
				this.bonusFeats = value.split(" \\| "); // TODO NOT WORKING
				break;
			case "CLASSABILITIES":
				this.classAbilities = value;
				break;
			case "CLASSSKILLS":
				this.classSkills = value;
				break;
			case "PALADINMOUNT":
				this.paladinMount = value;
				break;
			case "DRUIDCOMPANION":
				this.druidCompanion = value;
				break;
			case "WIZARDFAMILIAR":
				this.familiar = value;
				break;
			case "EXCLASS":
				this.exclass = value;
				break;
			default:
				break;

			}
			caster = (spellsPerDay != null || spellsKnown != null);
		}
	}

	@Override
	public void search(String searchString, Thread runningThread)
			throws InterruptedException {

		if (this.name != null && this.name.toLowerCase().contains(searchString)) {
			Main.gameState.searchResultsLock.acquire();
			// System.out.println("Lock aquired, adding " + this.name +
			// " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			// System.out.println("Lock released.");
			return;
		}

		if (this.description != null
				&& this.description.toLowerCase().contains(searchString)) {
			Main.gameState.searchResultsLock.acquire();
			// System.out.println("Lock aquired, adding " + this.name +
			// " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			// System.out.println("Lock released.");
			return;
		}

		if (this.classAbilities != null
				&& this.classAbilities.toLowerCase().contains(searchString)) {
			Main.gameState.searchResultsLock.acquire();
			// System.out.println("Lock aquired, adding " + this.name +
			// " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			// System.out.println("Lock released.");
			return;
		}

		if (this.classSkills != null
				&& this.classSkills.toLowerCase().contains(searchString)) {
			Main.gameState.searchResultsLock.acquire();
			// System.out.println("Lock aquired, adding " + this.name +
			// " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			// System.out.println("Lock released.");
			return;
		}

		if (this.special != null
				&& this.special.toLowerCase().contains(searchString)) {
			Main.gameState.searchResultsLock.acquire();
			// System.out.println("Lock aquired, adding " + this.name +
			// " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			// System.out.println("Lock released.");
			return;
		}

	}

	public String getAlignmentRestriction() {
		return alignmentRestriction;
	}

	public void setAlignmentRestriction(String alignmentRestriction) {
		this.alignmentRestriction = alignmentRestriction;
	}

	public String[] getBonusLanguages() {
		return bonusLanguages;
	}

	public void setBonusLanguages(String[] bonusLanguages) {
		this.bonusLanguages = bonusLanguages;
	}

	public String getHitDie() {
		return hitDie;
	}

	public void setHitDie(String hitDie) {
		this.hitDie = hitDie;
	}

	public String[] getBaseAttackBonusString() {
		return baseAttackBonusString;
	}

	public void setBaseAttackBonusString(String[] baseAttackBonus) {
		this.baseAttackBonusString = baseAttackBonus;
	}
	
	public int[] getBaseAttackBonus() {
		return baseAttackBonus;
	}
	
	public void setBaseAttackBonus(int[] b) {
		baseAttackBonus = b;
	}

	public int[] getFortSave() {
		return fortSave;
	}

	public void setFortSave(int[] fortSave) {
		this.fortSave = fortSave;
	}

	public int[] getReflexSave() {
		return reflexSave;
	}

	public void setReflexSave(int[] reflexSave) {
		this.reflexSave = reflexSave;
	}

	public int[] getWillSave() {
		return willSave;
	}

	public void setWillSave(int[] willSave) {
		this.willSave = willSave;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public int[][] getSpellsPerDay() {
		return spellsPerDay;
	}

	public void setSpellsPerDay(int[][] spellsPerDay) {
		this.spellsPerDay = spellsPerDay;
	}

	public int[][] getSpellsKnown() {
		return spellsKnown;
	}

	public void setSpellsKnown(int[][] spellsKnown) {
		this.spellsKnown = spellsKnown;
	}

	public String getClassSkills() {
		return classSkills;
	}

	public void setClassSkills(String classSkills) {
		this.classSkills = classSkills;
	}

	public String getSkillPointsFirstLevel() {
		return skillPointsFirstLevel;
	}

	public void setSkillPointsFirstLevel(String skillPointsFirstLevel) {
		this.skillPointsFirstLevel = skillPointsFirstLevel;
	}

	public String getSkillPointsPerLevel() {
		return skillPointsPerLevel;
	}

	public void setSkillPointsPerLevel(String skillPointsPerLevel) {
		this.skillPointsPerLevel = skillPointsPerLevel;
	}

	public String[] getBonusFeats() {
		return bonusFeats;
	}

	public void setBonusFeats(String[] bonusFeats) {
		this.bonusFeats = bonusFeats;
	}

	public String getClassAbilities() {
		return classAbilities;
	}

	public void setClassAbilities(String classAbilities) {
		this.classAbilities = classAbilities;
	}

	public String getDruidCompanion() {
		return druidCompanion;
	}

	public void setDruidCompanion(String druidCompanion) {
		this.druidCompanion = druidCompanion;
	}

	public String getPaladinMount() {
		return paladinMount;
	}

	public void setPaladinMount(String paladinMount) {
		this.paladinMount = paladinMount;
	}

	public String getFamiliar() {
		return familiar;
	}

	public void setFamiliar(String familiar) {
		this.familiar = familiar;
	}

	public String getExclass() {
		return exclass;
	}

	public void setExclass(String exclass) {
		this.exclass = exclass;
	}
	
	public boolean isCaster() { 
		return caster;
	}
	
	public void setCaster(boolean caster) {
		this.caster = caster;
	}

	@Override
	public String saveCustomContent() {
		String output = "";
		return output;
	}

}
