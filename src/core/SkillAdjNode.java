package core;

import entity.SkillEntity;


public class SkillAdjNode {

	private CharSkill charSkill;
	private SkillEntity skill;
	private int adj;
	
	public SkillAdjNode(SkillEntity skill, int adj) {
		this.skill = skill;
		this.adj = adj;
	}
	
	public SkillAdjNode(CharSkill charSkill, int adj) {
		this.charSkill = charSkill;
		this.adj = adj;
	}
	
	public CharSkill getCharSkill() {
		return charSkill;
	}
	
	public SkillEntity getSkill() { 
		return skill;
	}
	
	public int getAdj() {
		return adj;
	}
	
	public void incAdj() {
		adj++;
	}
	
	public boolean decAdj() {
		if (adj == 0)
			return false;
		adj--;
		return true;
	}
	
	public void setAdj(int adj) {
		this.adj = adj;
	}
	
}
