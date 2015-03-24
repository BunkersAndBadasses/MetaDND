package entity;

import java.util.HashMap;
import java.util.Map;

public class SpellEntity extends DNDEntity{

	String school;
	Map<String, Integer> level; //Key is what class can cast, level is what level the spell is for that class, map for multiple classes?
	String components;
	String castingTime;
	String range;
	String effect;
	String duration;
	String savingThrow;
	boolean spellResistance;
	String materialComponent;
	String focus;
	
	public SpellEntity(HashMap<String, String> input) {
		this.TYPE = DNDEntity.type.SPELLENTITY;
		for (Map.Entry<String, String> entry : input.entrySet()) {
		    String field = entry.getKey();
		    String value = entry.getValue();
		    switch(field){
		    case "NAME":
		    	this.name = value;
		    default:
		    	break;
		    }
		}
	}
	
	@Override
	String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void toTooltipWindow() {
		// TODO Auto-generated method stub
		//Need some generic GUI window here, feed in data and open
	}

}
