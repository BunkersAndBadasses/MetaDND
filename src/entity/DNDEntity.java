package entity;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * Generic entity class, extend this when creating searchable entities
 * 
 */
public abstract class DNDEntity {
	
	enum type{
		SPELL,
		FEAT,
		SKILL,
		ITEM,
		WEAPON,
		ARMOR,
		RACE,
		CLASS,
		MONSTER,
		TRAP,
		DUNGEON,
		ABILITY,
		DEITY
	}
	
	type TYPE; //Enumerated type, must have to determine what type of entity this is
	String name;
	String description;
	LinkedHashMap<String, String> passedData; //Data passed in for entity constructor, make sure this isn't NULL otherwise tooltip windows won't work
	
	
	//TODO Replace void with actual window object
	/**
	 * Get name of the entity.
	 * @return The name of the entity, in a plain string.
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * Get Description of the entity.
	 * @return The Description of the entity, in a string, including "\n" symbol.
	 */
	public String getDescription(){
		return this.description;
	}
	/**
	 * Get the type of the entity.
	 * @return The type of the entity.
	 */
	public type getEntityType(){
		return this.TYPE;
	}
	/**
	 * Create a new shell that display the DNDEntity information on it.
	 * The window will be automaticlly adjusted and scrollable.
	 */
	public void toTooltipWindow(){
	
		Display display = Display.getCurrent();
		Shell shell = new Shell(display);
		Monitor monitor = display.getPrimaryMonitor();
	    Rectangle bounds = monitor.getBounds();
	    shell.setText(this.name);
	    int WIDTH = 700;
		int HEIGHT = (int)(bounds.height * 2.0/3.0);
		
		ScrolledComposite sc = new ScrolledComposite(shell, SWT.V_SCROLL);
		sc.setBounds(0, 0, WIDTH - 20, HEIGHT - 50);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		
		Composite c = new Composite(sc, SWT.NONE);
		sc.setContent(c);
		c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));	
		GridLayout layout = new GridLayout(1, false);
		c.setLayout(layout);
		
		Font boldFont = new Font(display, new FontData( display.getSystemFont().getFontData()[0].getName(), 12, SWT.BOLD ));
		for (Map.Entry<String, String> entry : passedData.entrySet()){
			Label titleLabel = new Label(c, SWT.LEFT);
			if(entry.getKey().equals("NAME"))
				titleLabel.setText(this.TYPE.toString() + " " + entry.getKey());
			else
				titleLabel.setText(entry.getKey());
			titleLabel.setFont(boldFont);
			titleLabel.pack();
			Label textLabel = new Label(c, SWT.LEFT);
			String windowSize = "(.{" + bounds.width / 16 + "} )";
			//This guy finds a space every 120 characters and makes a new line, nice text formatting for the tooltip windows
			String parsedStr = entry.getValue().replaceAll(windowSize, "$1\n");
			parsedStr = parsedStr.replaceAll("\t", "");
			textLabel.setText(parsedStr);
			textLabel.pack();
		}
		
		int heightSum = 0;
		for(int i = 0; i < c.getChildren().length; i++){
			heightSum += c.getChildren()[i].getSize().y + layout.verticalSpacing;
		}
		
		sc.setMinHeight(heightSum);
		c.pack();
		
	    shell.setLocation((int)(bounds.width * .75) - c.getSize().x / 2, (int)(bounds.height * .05));
	    
		shell.pack();
		shell.open();
		shell.addListener (SWT.Resize,  new Listener () {
	        public void handleEvent (Event e) {
	          Rectangle rect = shell.getClientArea ();
	          sc.setBounds(rect);
	        }
	      });
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
	}
	
	public abstract void search(String searchString, Thread runningThread) throws InterruptedException;
	/**
	 * Same as Getentitytype.
	 * @return Get the type of the entity.
	 */
	public type getTYPE() {
		return TYPE;
	}
	/**
	 * Set the type of the entity.
	 * @param Type
	 */
	public void setTYPE(type Type) {
		TYPE = Type;
	}
	/**
	 * Get the data from the XML that create this entity.
	 * @return
	 */
	public LinkedHashMap<String, String> getPassedData() {
		return passedData;
	}
	/**
	 * Don't do anything.
	 * Sorry we created this method by accident and
	 * didn't think about the usage of it.
	 * @param passedData
	 */
	public void setPassedData(LinkedHashMap<String, String> passedData) {
		//this.passedData = passedData;
	}
	/**
	 * Set the name of entity.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Set the description of entity.
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
