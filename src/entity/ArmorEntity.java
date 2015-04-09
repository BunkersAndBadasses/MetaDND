package entity;

public class ArmorEntity extends DNDEntity {

	private int armorBonus;
	private int maxDexBonus;
	private int armorCheckPenalty;
	private int cost;
	private boolean isMagic;
	private int magicBonus; // if isMagic == false, leave null
	private String[] magicProperties; // if isMagic == false, leave null
	@Override
	public void search(String searchString) {
		// TODO Auto-generated method stub
		
	}
	
}
