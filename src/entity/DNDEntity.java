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
	public abstract String getName();
	public abstract String getDescription();
	public abstract void toTooltipWindow();
	
	public type getEntityType(){
		return this.TYPE;
	}

}
