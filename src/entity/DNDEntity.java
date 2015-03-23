package entity;
/*
 * Generic entity class, extend this when creating searchable entities
 */
public abstract class DNDEntity {
	
	String name;
	String description;
	
	//TODO Replace void with actual window object
	abstract String getName();
	abstract String getDescription();
	abstract void toTooltipWindow();

}
