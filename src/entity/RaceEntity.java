package entity;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


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
		    	this.name = value;
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
	public void search() {
		// TODO Auto-generated method stub
		
	}
	
	
}
