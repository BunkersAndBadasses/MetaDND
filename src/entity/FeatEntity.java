package entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class FeatEntity extends DNDEntity{

	String prerequisite;
	String special;
	String normal;
	String benefit;
	String fighterBonus;

	public FeatEntity(LinkedHashMap<String, String> input){
		this.TYPE = DNDEntity.type.FEAT;
		passedData = input;
		for (Map.Entry<String, String> entry : input.entrySet()){
			String field = entry.getKey();
			String value = entry.getValue();
			switch(field){
			case "NAME":
				String temp = new String(value.split("\\[")[0].trim());
		    	this.name = temp;
		    	break;
			case "PREREQUISITE":
				this.prerequisite = value;
				break;
			case "NORMAL":
				this.normal = value;
				break;
			case "SPECIAL":
				this.special = value;
				break;
			case "BENEFIT":
				this.benefit = value;
				break;
			case "FIGHTERBONUS":
				this.fighterBonus = value;
				break;
			case "DESCRIPTION":
				this.description = value;
				break;	
			default:
				break;
			}

		}
	}

	@Override
	public void search(String searchString, Thread runningThread) {
		// TODO Auto-generated method stub
		
	}

	public String getPrerequisite() {
		return prerequisite;
	}

	public void setPrerequisite(String prerequisite) {
		this.prerequisite = prerequisite;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getNormal() {
		return normal;
	}

	public void setNormal(String normal) {
		this.normal = normal;
	}

	public String getBenefit() {
		return benefit;
	}

	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}

	public String getFighterBonus() {
		return fighterBonus;
	}

	public void setFighterBonus(String fighterBonus) {
		this.fighterBonus = fighterBonus;
	}

}
