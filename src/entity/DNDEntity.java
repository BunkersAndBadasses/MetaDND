package entity;
/*
 * Generic entity class, extend this when creating searchable entities
 */
public abstract class DNDEntity {
	
	enum type{
		SPELLENTITY
	}
	
	type TYPE; //Enumerated type, must have to determine what type of entity this is
	String name;
	String description;
	
	
	//TODO Replace void with actual window object
	abstract String getName();
	abstract String getDescription();
	abstract void toTooltipWindow();
	
	public type getEntityType(){
		return this.TYPE;
	}

}
