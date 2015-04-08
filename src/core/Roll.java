package core;

public class Roll {
	int dieCount;
	int dieSize;
	int modifier;
	int playerLevelAdjust; 
	
	public Roll(int dieSize, int dieCount){
		this.dieCount = dieCount;
		this.dieSize = dieSize;
	}
	public Roll(int dieSize, int dieCount, int modifier){
		this.dieCount = dieCount;
		this.dieSize = dieSize;
		this.modifier = modifier;
	}
	public Roll(int dieSize, int dieCount, int modifier, int playerLevelAdjust){
		this.dieCount = dieCount;
		this.dieSize = dieSize;
		this.modifier = modifier;
		this.playerLevelAdjust = playerLevelAdjust;
	}
	
	public int getDieCount(){
		return dieCount;
	}
	
	public int getDieSize(){
		return dieSize;
	}
	
	public int getModifier(){
		return modifier;
	}
	
	public int getPlayerLevelAdjust(){
		return playerLevelAdjust;
	}
	
}


