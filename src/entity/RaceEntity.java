package entity;
import java.util.ArrayList;


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
	
	private String name;
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
	
	public RaceEntity() {
		
	}
	
	
	
}
