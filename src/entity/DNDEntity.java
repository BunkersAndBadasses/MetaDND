package entity;

import guis.MenuBar;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

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
		Display display = new Display();
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		Shell shell = new Shell(display);
		shell.setLayout(gridLayout);
		shell.setBounds(0,0,500,500);
		shell.setText(this.name);
		Font boldFont = new Font(display, new FontData( display.getSystemFont().getFontData()[0].getName(), 12, SWT.BOLD ));
		for (Map.Entry<String, String> entry : passedData.entrySet()){
			Label titleLabel = new Label(shell, SWT.LEFT);
			titleLabel.setText(entry.getKey());
			titleLabel.setFont(boldFont);
			Label textLabel = new Label(shell, SWT.LEFT);
			//This guy finds a space every 120 characters and makes a new line, nice text formatting for the tooltip windows
			String parsedStr = entry.getValue().replaceAll("(.{120} )", "$1\n");
			parsedStr = parsedStr.replaceAll("\t", "");
			textLabel.setText(parsedStr);
		}
		Monitor primary = display.getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shell.getBounds();
	    
	    int x = (int) (bounds.x + (bounds.width - rect.width) * .75);
	    int y = (int) (bounds.y + (bounds.height - rect.height) * .15);
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
