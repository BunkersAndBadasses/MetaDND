package entity;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;

import core.Main;


public class RaceEntity extends DNDEntity{

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
	
	private int[] abilityAdj = new int[6];
	private String favoredClass;
	private String personality;
	private String physicalDescription;
	private String relations;
	private String alignment;
	private String[] lands;
	private String religion;
	private String[] specialAbilities;
	private String[] autoLanguages;
	private String[] bonusLanguages;
	private String[] racialBonuses; // i.e. skill check adj's, extra feats
	private String[] names;
	private int size;
	private int speed; // in feet
	
	public RaceEntity(LinkedHashMap<String, String> input){
		this.TYPE = DNDEntity.type.RACE;
		passedData = input;
		ArrayList<String> racialBonuses = new ArrayList<String>();
		for (Map.Entry<String, String> entry : input.entrySet()){
		    String field = entry.getKey();
		    String value = entry.getValue();
		    switch(field){
		    case "NAME":
		    	String tempN = new String(value.split("\\[")[0].trim());
		    	tempN = WordUtils.capitalize(tempN.toLowerCase());
				tempN = WordUtils.capitalize(tempN, '(', '[');
		    	this.name = tempN;
		    	break;
		    case "DESCRIPTION":
		    	this.description = value;
		    	break;
		    case "ABILITYADJ":
		    	String[] temp = value.split(", ");
		    	for(int i = 0; i < temp.length; i++){
		    		this.abilityAdj[i] = Integer.parseInt(temp[i]);
		    	}
		    	break;
		    case "FAVCLASS":
		    	this.favoredClass = value;
		    	break;
		    case "SIZE":
		    	String size = value.split(":")[0];
		    	if(size.equalsIgnoreCase("FINE"))
		    		this.size = SIZE_FINE;
		    	else if(size.equalsIgnoreCase("DIMUNITIVE"))
		    		this.size = SIZE_DIMINUTIVE;
		    	else if(size.equalsIgnoreCase("TINY"))
		    		this.size = SIZE_TINY;
		    	else if(size.equalsIgnoreCase("SMALL"))
		    		this.size = SIZE_SMALL;
		    	else if(size.equalsIgnoreCase("MEDIUM"))
		    		this.size = SIZE_MEDIUM;
		    	else if(size.equalsIgnoreCase("LARGE"))
		    		this.size = SIZE_LARGE;
		    	else if(size.equalsIgnoreCase("HUGE"))
		    		this.size = SIZE_HUGE;
		    	else if(size.equalsIgnoreCase("GARGANTUAN"))
		    		this.size = SIZE_GARGANTUAN;
		    	else if(size.equalsIgnoreCase("COLOSSAL"))
		    		this.size = SIZE_COLOSSAL;
		    	break;
		    case "SPECIALABILITIES":
		    	this.specialAbilities = value.split(";");
		    	break;
		    case "SPEED":
		    	this.speed = Integer.parseInt(value.replaceAll("[\\D]", ""));
		    	break;
		    case "TRAITS":
		    	String[] temp1 = value.split("\n");
		    	for(int i = 0; i < temp1.length; i++){
		    		racialBonuses.add(temp1[i].trim());
		    	}
		    	this.racialBonuses = racialBonuses.toArray(temp1);
		    	break;
		    case "SKILLCHECKADJ":
		    	String[] temp2 = value.split("\n");
		    	for(int i = 0; i < temp2.length; i++){
		    		racialBonuses.add(temp2[i].trim());
		    	}
		    	this.racialBonuses = racialBonuses.toArray(temp2);
		    	break;
		    case "AUTOMATICLANGUAGES":
		    	this.autoLanguages = value.split(", ");
		    	break;
		    case "BONUSLANGUAGES":
		    	this.bonusLanguages = value.split(", ");
		    	break;
		    default:
		    	break;
		    }
		
	}
	}

	@Override
	public void search(String searchString, Thread runningThread) throws InterruptedException {
		
		if(this.name != null && this.name.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			System.out.println("Lock released.");
			return;
		}
		
		if(this.description != null && this.description.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			System.out.println("Lock released.");
			return;
		}
		
		if(this.favoredClass != null && this.favoredClass.toLowerCase().contains(searchString)){
			Main.gameState.searchResultsLock.acquire();
			System.out.println("Lock aquired, adding " + this.name + " to results list.");
			Main.gameState.searchResults.put(this.name, this);
			Main.gameState.searchResultsLock.release();
			System.out.println("Lock released.");
			return;
		}
		
	}

	public int[] getAbilityAdj() {
		return abilityAdj;
	}

	public void setAbilityAdj(int[] abilityAdj) {
		this.abilityAdj = abilityAdj;
	}

	public String getFavoredClass() {
		return favoredClass;
	}

	public void setFavoredClass(String favoredClass) {
		this.favoredClass = favoredClass;
	}

	public String getPersonality() {
		return personality;
	}

	public void setPersonality(String personality) {
		this.personality = personality;
	}

	public String getPhysicalDescription() {
		return physicalDescription;
	}

	public void setPhysicalDescription(String physicalDescription) {
		this.physicalDescription = physicalDescription;
	}

	public String getRelations() {
		return relations;
	}

	public void setRelations(String relations) {
		this.relations = relations;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public String[] getLands() {
		return lands;
	}

	public void setLands(String[] lands) {
		this.lands = lands;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String[] getSpecialAbilities() {
		return specialAbilities;
	}

	public void setSpecialAbilities(String[] specialAbilities) {
		this.specialAbilities = specialAbilities;
	}

	public String[] getAutoLanguages() {
		return autoLanguages;
	}

	public void setAutoLanguages(String[] autoLanguages) {
		this.autoLanguages = autoLanguages;
	}

	public String[] getBonusLanguages() {
		return bonusLanguages;
	}

	public void setBonusLanguages(String[] bonusLanguages) {
		this.bonusLanguages = bonusLanguages;
	}

	public String[] getRacialBonuses() {
		return racialBonuses;
	}

	public void setRacialBonuses(String[] racialBonuses) {
		this.racialBonuses = racialBonuses;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	
}
