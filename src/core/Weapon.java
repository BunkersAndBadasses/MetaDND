
public class Weapon extends Item {

	private Roll damage; // i.e. [2,6,3] for damage of 2 d6 + 3
	private int[] criticalRange = new int[2];
	private int criticalMultiplier;
	private int range;
	private String type; // i.e. "piercing"
	private boolean isMagic;
	private int magicBonus; // if isMagic == false, leave null
	private String[] magicProperties; // if isMagic == false, leave null
		
}
