package core;

import entity.SkillEntity;

public class SkillAdjNode {

	private SkillEntity skill;
	private int adj;
	
	public SkillAdjNode(SkillEntity skill, int adj) {
		this.skill = skill;
		this.adj = adj;
	}
	
	public SkillEntity getSkill() { 
		return skill;
	}
	
	public int getAdj() {
		return adj;
	}
	
}
