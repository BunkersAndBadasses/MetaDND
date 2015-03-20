import java.util.ArrayList;


public class Race {

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
	private String description;
	private int[] abilityAdj = new int[6];
	private String favoredClass;
	private int size;
	private int speed; // in feet
	private ArrayList<String> specialAbilities;
	private ArrayList<String> autoLanguages;
	private ArrayList<String> bonusLanguages;
	private ArrayList<SkillAdjNode> skillCheckAdj;
	private String miscTraits;
	
	
	
	public Race() {
		
	}
	
	
	
}
