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
	private boolean halfPoint = false;
	
	public CharSkill(SkillEntity s, character c) {
		skill = s;
		character = c;
		String charClass = character.getCharClass().getName();
		classSkill = SkillInfo.isClassSkill(charClass, skill.getName());
		abilityType = skill.skillParentAttribute;
		abilityMod = setAbilityMod(); 
		// TODO set miscMod
		acPen = skill.armorCheckPenalty;
		untrained = SkillInfo.useUntrained(skill.getName());
	}
	
	// TODO check this logic // TODO int rounding off
	private int setAbilityMod() {
		int base;
		switch(skill.skillParentAttribute) {
		case ("STR"):
			base = character.getAbilityScores()[GameState.STRENGTH];
		break;
		case ("DEX"):
			base = character.getAbilityScores()[GameState.DEXTERITY];
		break;
		case ("CON"):
			base = character.getAbilityScores()[GameState.CONSTITUTION];
		break;
		case ("INT"):
			base = character.getAbilityScores()[GameState.INTELLIGENCE];
		break;
		case ("WIS"):
			base = character.getAbilityScores()[GameState.WISDOM];
		break;
		default: // CHA
			base = character.getAbilityScores()[GameState.CHARISMA];
		break;
		}
		return (base - 8) / 2;
	}
	
	public SkillEntity getSkill() { return skill; }
	
	public int getRank() { return rank; }
	
	public boolean incRank(int numSkillPoints) {
		if ((classSkill && rank ==4) || (!classSkill && (rank == 2 || numSkillPoints == 1)))
			return false;
//		if(!classSkill){
//			if (!halfPoint) {
//				halfPoint = true;
//				return true;		
//			} else 
//				halfPoint = false;
//		}
		rank++;
		return true;
	}
	
	public boolean decRank() {
		if (rank == 0)
			return false;
//		if (!classSkill) {
//			if (halfPoint) {
//				halfPoint = false;
//			} else {
//				halfPoint = true;
//				return true;
//			}
//		}
		rank--;
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
}
