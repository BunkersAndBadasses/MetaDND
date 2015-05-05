package core;
import entity.*;

public class CharSkill {
	private SkillEntity skill;
	private character character;
	private int rank = 0;
	private String abilityType;
	private int abilityMod;
	private int miscMod = 0;
	private boolean classSkill = false;
	private boolean acPen;
	private boolean untrained = false;
	public CharSkill(SkillEntity s, character c) {
		skill = s;
		character = c;
		String charClass = character.getCharClass().getName();
		classSkill = SkillInfo.isClassSkill(charClass, skill.getName());
		abilityType = skill.skillParentAttribute;
		abilityMod = setAbilityMod(); 
		acPen = skill.armorCheckPenalty;
		untrained = SkillInfo.useUntrained(skill.getName());
	}
	
	private int setAbilityMod() {
		switch(skill.skillParentAttribute) {
		case ("STR"):
			return character.getAbilityModifiers()[GameState.STRENGTH];
		case ("DEX"):
			return character.getAbilityModifiers()[GameState.DEXTERITY];
		case ("CON"):
			return character.getAbilityModifiers()[GameState.CONSTITUTION];
		case ("INT"):
			return character.getAbilityModifiers()[GameState.INTELLIGENCE];
		case ("WIS"):
			return character.getAbilityModifiers()[GameState.WISDOM];
		default: // CHA
			return character.getAbilityModifiers()[GameState.CHARISMA];
		}
		
	}
	
	public SkillEntity getSkill() { return skill; }
	
	public int getRank() { return rank; }
	
	public boolean incRank(int numSkillPoints) {
		if (!tryIncRank(numSkillPoints))
			return false;
		rank++;
		return true;
	}
	
	public boolean tryIncRank(int numSkillPoints) {
		int max = getMaxClassSkillRank(character.getLevel());
		if ((classSkill && rank == max) || (!classSkill && (rank == max/2 || numSkillPoints == 1)))
			return false;
		return true;
	}
	
	public boolean decRank() {
		if (!tryDecRank())
			return false;
		rank--;
		return true;
	}
	
	public boolean tryDecRank() {
		if (rank == 0)
			return false;
		return true;
	}
	
	public String getAbilityType() { return abilityType; }
	
	public int getAbilityMod() { return abilityMod; }
	
	public int getMiscMod() { return miscMod; }
	
	public int getTotal() { return rank + abilityMod + miscMod; }
	
	public boolean isClassSkill() { return classSkill; }
	
	public boolean hasACPen() { return acPen; }
	
	public boolean useUntrained() { return untrained; }
	
	public void setRank(int r) { rank = r ; }
	
	public void modRank(int mod) { rank += mod; }
	
	public void setMiscMod(int m) { miscMod = m; }
	
	public int getMaxClassSkillRank(int level) {
		return level + 3;
	}
}
