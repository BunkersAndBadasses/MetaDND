package core;
import entity.*;

public class CharSkill {
	private SkillEntity skill;
	private int rank = 0;
	private String abilityType;
	private int abilityMod;
	private int miscMod = 0;
	private boolean classSkill = false;
	private boolean halfPoint = false;
	
	public CharSkill(SkillEntity s, Character c) {
		// search skills for skill(name)
		// set that this.skill = returned skill
		// 
		skill = s;
		String charClass = c.getCharClass(); // TODO
		classSkill = false; // TODO get info from class if this skill is a class skill
		//TODO
		//abilityType = Character.abilityScoreTypes[skill.getAbilityType()];
		//abilityMod = ((c.getAbilityScores()[skill.getAbilityType()]) - 8) / 2; // TODO check this logic // TODO int rounding off
		// TODO set miscMod
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
}
