package core;
import entity.*;

public class CharSkill {
	private SkillEntity skill;
	private Character character;
	private int rank = 0;
	private String abilityType;
	private int abilityMod;
	private int miscMod = 0;
	private boolean classSkill = false;
	private boolean acPen;
	private boolean halfPoint = false;

	
	public CharSkill(SkillEntity s, Character c) {
		skill = s;
		character = c;
		String charClass = character.getCharClass(); // TODO
		classSkill = ClassSkillList.isClassSkill(charClass, skill.getName());
		abilityType = skill.skillParentAttribute;
		abilityMod = setAbilityMod(); 
		// TODO set miscMod
		acPen = skill.armorCheckPenalty;
	}
	
	// TODO check this logic // TODO int rounding off
	private int setAbilityMod() {
		int base;
		switch(skill.skillParentAttribute) {
		case ("STR"):
			base = character.getAbilityScores()[Character.STRENGTH];
		break;
		case ("DEX"):
			base = character.getAbilityScores()[Character.DEXTERITY];
		break;
		case ("CON"):
			base = character.getAbilityScores()[Character.CONSTITUTION];
		break;
		case ("INT"):
			base = character.getAbilityScores()[Character.INTELLIGENCE];
		break;
		case ("WIS"):
			base = character.getAbilityScores()[Character.WISDOM];
		break;
		default: // CHA
			base = character.getAbilityScores()[Character.CHARISMA];
		break;
		}
		return (base - 8) / 2;
	}
	
	public SkillEntity getSkill() { return skill; }
	
	public int getRank() { return rank; }
	
	public boolean incRank() {
		if ((classSkill && rank ==4) || (!classSkill && rank == 2)) // TODO check this - max 2 ranks for cross-class skills???
			return false;
		if(!classSkill){
			if (!halfPoint) {
				halfPoint = true;
				return true;		
			} else 
				halfPoint = false;
		}
		rank++;
		return true;
	}
	
	public boolean decRank() {
		if (rank == 0)
			return false;
		if (!classSkill) {
			if (halfPoint) {
				halfPoint = false;
			} else {
				halfPoint = true;
				return true;
			}
		}
		rank--;
		return true;
	}
	
	public String getAbilityType() { return abilityType; }
	
	public int getAbilityMod() { return abilityMod; }
	
	public int getMiscMod() { return miscMod; }
	
	public int getTotal() { return rank + abilityMod + miscMod; }
	
	public boolean isClassSkill() { return classSkill; }
	
	public boolean hasACPen() { return acPen; }
}
