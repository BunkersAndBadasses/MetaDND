package guis;

import core.character;

import java.util.ArrayList;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class ManualCharacter {
	
	private ScrolledComposite manual;
	
	public ManualCharacter(Composite page){
		manual = new ScrolledComposite(page, SWT.V_SCROLL);
		
		createPageContent();
	}

	private void createPageContent() {
		
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.horizontalSpacing = 3;
		layout.numColumns = 3;
		manual.setLayout(layout);
		
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, false, false);
		
		
		
		
		
		
		
		
		
		
		
		
		
		Button saveChar = new Button(manual, SWT.PUSH);
		saveChar.setText("Save");
		gridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		saveChar.setLayoutData(gridData);
		saveChar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				
				
			}
		});
		saveChar.pack();
		
		Button cancel = new Button(manual, SWT.PUSH);
		cancel.setText("Cancel");
		gridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		cancel.setLayoutData(gridData);
		cancel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				
				
			}
		});
		cancel.pack();
		
		manual.setMinHeight(saveChar.getLocation().y + saveChar.getSize().y);
		manual.layout();
	}
	
}
