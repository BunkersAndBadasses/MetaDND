package entity;

import java.util.Map;

public class SpellEntity extends DNDEntity{

	String name;
	String description;
	String school;
	Map<String, Integer> level; //Key is what class can cast, level is what level the spell is for that class, map for multiple
	String components;
	String castingTime;
	String range;
	String effect;
	String duration;
	String savingThrow;
	boolean spellResistance;
	String materialComponent;
	String focus;
	
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
