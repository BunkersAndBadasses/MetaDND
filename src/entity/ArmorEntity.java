package entity;

public class ArmorEntity extends DNDEntity {

	private int armorBonus;
	private int maxDexBonus;
	private int armorCheckPenalty;
	private int cost;
	private boolean isMagic;
	private int magicBonus; // if isMagic == false, leave null
	private String[] magicProperties; // if isMagic == false, leave null
    private int quantity;
    
    public ArmorEntity(){
    	
    }
	@Override
	public void search(String searchString, Thread runningThread) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}
	public int getArmorBonus() {
		return armorBonus;
	}
	public void setArmorBonus(int armorBonus) {
		this.armorBonus = armorBonus;
	}
	public int getMaxDexBonus() {
		return maxDexBonus;
	}
	public void setMaxDexBonus(int maxDexBonus) {
		this.maxDexBonus = maxDexBonus;
	}
	public int getArmorCheckPenalty() {
		return armorCheckPenalty;
	}
	public void setArmorCheckPenalty(int armorCheckPenalty) {
		this.armorCheckPenalty = armorCheckPenalty;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
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
    public void setQuanitity(int count) {
        quantity = count;
    }
    
    public int getQuantity() {
        return quantity;
    }
	
}
