package entity;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/*
 * Generic entity class, extend this when creating searchable entities
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
		DUNGEON
	}
	
	type TYPE; //Enumerated type, must have to determine what type of entity this is
	String name;
	String description;
	LinkedHashMap<String, String> passedData; //Data passed in for entity constructor, make sure this isn't NULL otherwise tooltip windows won't work
	
	
	//TODO Replace void with actual window object
	public String getName(){
		return this.name;
	}
	public String getDescription(){
		return this.description;
	}
	
	public type getEntityType(){
		return this.TYPE;
	}
	
	public void toTooltipWindow(){
		int WIDTH = 700;
		int HEIGHT = 700;
		Display display = new Display();
		Shell shell = new Shell(display);
		Monitor primary = display.getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shell.getBounds();
	    
	    int x = (int) (bounds.x + (bounds.width - rect.width) * 2);
	    int y = (int) (bounds.y + (bounds.height - rect.height) * .1);
		shell.setText(this.name);
		ScrolledComposite sc = new ScrolledComposite(shell, SWT.V_SCROLL);
		sc.setBounds(0, 10, WIDTH, HEIGHT - 50);
		sc.setExpandVertical(true);
		
		Composite c = new Composite(sc, SWT.NONE);
		sc.setContent(c);
		c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.wrap = true;
		//GridLayout layout = new GridLayout(1, false);
		//layout.numColumns = 1;
		c.setLayout(layout);
		
		Font boldFont = new Font(display, new FontData( display.getSystemFont().getFontData()[0].getName(), 12, SWT.BOLD ));
		
		for (Map.Entry<String, String> entry : passedData.entrySet()){
			//GridData temp = new GridData();
			Label titleLabel = new Label(c, SWT.LEFT);
			titleLabel.setText(entry.getKey());
			titleLabel.setFont(boldFont);
			Label textLabel = new Label(c, SWT.LEFT);
			//This guy finds a space every 120 characters and makes a new line, nice text formatting for the tooltip windows
			String parsedStr = entry.getValue().replaceAll("(.{120} )", "$1\n");
			parsedStr = parsedStr.replaceAll("\t", "");
			textLabel.setText(parsedStr);
		}
		sc.setMinSize(WIDTH, HEIGHT + 800);
		
		c.pack();
		
	    shell.setLocation(x, y);
		shell.pack();
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
