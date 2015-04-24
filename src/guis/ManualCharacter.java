package guis;

import core.character;

import java.util.ArrayList;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class ManualCharacter {
	
	private Composite manual;
	private ScrolledComposite scrolled;
	
	public ManualCharacter(ScrolledComposite scrolledPage){
		manual = new Composite(scrolledPage, SWT.BORDER);
		scrolled = scrolledPage;
		createPageContent();
	}

	private void createPageContent() {
		
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.horizontalSpacing = 3;
		layout.numColumns = 3;
		manual.setLayout(layout);
		
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, false, false);
		
		//NAME
		Label charName = new Label(manual, SWT.NONE);
		charName.setText("Name: ");
		gridData = new GridData(SWT.END, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		gridData.horizontalSpan = 1;
		charName.setLayoutData(gridData);
		charName.pack();
		
		Text charNameText = new Text(manual, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		charNameText.setText("");
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		charNameText.setLayoutData(gridData);
		
		//Level
		Label charLevel = new Label(manual, SWT.NONE);
		charLevel.setText("Level: ");
		gridData = new GridData(SWT.END, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		gridData.horizontalSpan = 1;
		charLevel.setLayoutData(gridData);
		charLevel.pack();
		
		Text charLevelText = new Text(manual, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		charLevelText.setText("");
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		charLevelText.setLayoutData(gridData);
		
		//EXP
		Label charEXP = new Label(manual, SWT.NONE);
		charEXP.setText("EXP: ");
		gridData = new GridData(SWT.END, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		gridData.horizontalSpan = 1;
		charEXP.setLayoutData(gridData);
		charEXP.pack();
		
		Text charEXPText = new Text(manual, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		charEXPText.setText("");
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		charEXPText.setLayoutData(gridData);
		

		
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
		
		//TODO set the height for this scrollable composite from the Character Wizard
		scrolled.setMinHeight(saveChar.getLocation().y + saveChar.getSize().y);
		scrolled.layout();
		manual.layout();
	}
	
}
