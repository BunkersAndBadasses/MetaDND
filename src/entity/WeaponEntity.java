package entity;

import core.Roll;

public class WeaponEntity extends DNDEntity{

	private Roll damage; // i.e. [2,6,3] for damage of 2 d6 + 3
	private int[] criticalRange = new int[2];
	private int criticalMultiplier;
	private int range;
	private String type; // i.e. "piercing"
	private boolean isMagic;
	private int magicBonus; // if isMagic == false, leave null
	private String[] magicProperties; // if isMagic == false, leave null
	@Override
	public void search(String searchString, Thread runningThread) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}
	public Roll getDamage() {
		return damage;
	}
	public void setDamage(Roll damage) {
		this.damage = damage;
	}
	public int[] getCriticalRange() {
		return criticalRange;
	}
	public void setCriticalRange(int[] criticalRange) {
		this.criticalRange = criticalRange;
	}
	public int getCriticalMultiplier() {
		return criticalMultiplier;
	}
	public void setCriticalMultiplier(int criticalMultiplier) {
		this.criticalMultiplier = criticalMultiplier;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isMagic() {
		return isMagic;
	}
	public void setMagic(boolean isMagic) {
		this.isMagic = isMagic;
	}
	public int getMagicBonus() {
		return magicBonus;
	}
	public void setMagicBonus(int magicBonus) {
		this.magicBonus = magicBonus;
	}
	public String[] getMagicProperties() {
		return magicProperties;
	}
	public void setMagicProperties(String[] magicProperties) {
		this.magicProperties = magicProperties;
	}
		
}
