package guis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import core.Main;
import entity.ClassEntity;
import entity.DNDEntity;
import entity.RaceEntity;

public class ManualCharacter {

	private Composite manual;
	private ScrolledComposite scrolled;

	public ManualCharacter(ScrolledComposite scrolledPage){
		manual = new Composite(scrolledPage, SWT.NONE);
		scrolled = scrolledPage;
		createPageContent();
	}

	public Composite getManualCharacter(){ return manual; }

	private void createPageContent() {

		Collection<DNDEntity> racesCol = Main.gameState.races.values();
		Iterator<DNDEntity> itr = racesCol.iterator();
		ArrayList<RaceEntity> races = new ArrayList<RaceEntity>();
		while (itr.hasNext()) {
			races.add((RaceEntity) itr.next());
		}

		// get classes from references
		Collection<DNDEntity> classesCol = Main.gameState.classes.values();
		Iterator<DNDEntity> itr2 = classesCol.iterator();
		ArrayList<ClassEntity> classes = new ArrayList<ClassEntity>();
		while (itr2.hasNext()) {
			classes.add((ClassEntity) itr2.next());
		}

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

		Text charNameText = new Text(manual, SWT.BORDER | SWT.CENTER);
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

		Text charLevelText = new Text(manual, SWT.BORDER | SWT.CENTER);
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

		Text charEXPText = new Text(manual, SWT.BORDER | SWT.CENTER);
		charEXPText.setText("");
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		charEXPText.setLayoutData(gridData);

		//Race
		Label charRace = new Label(manual, SWT.NONE);
		charRace.setText("Race: ");
		gridData = new GridData(SWT.END, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		gridData.horizontalSpan = 1;
		charRace.setLayoutData(gridData);
		charRace.pack();

		// race drop down menu
		Combo raceDropDown = new Combo(manual, SWT.DROP_DOWN | SWT.READ_ONLY);
		for (int i = 0; i < races.size(); i++) {
			raceDropDown.add(races.get(i).getName());
		}
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		raceDropDown.setLayoutData(gridData);

		//Class
		Label charClass = new Label(manual, SWT.NONE);
		charClass.setText("Class: ");
		gridData = new GridData(SWT.END, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		gridData.horizontalSpan = 1;
		charClass.setLayoutData(gridData);
		charClass.pack();

		// class drop down menu
		Combo classDropDown = new Combo(manual, SWT.DROP_DOWN | SWT.READ_ONLY);
		for (int i = 0; i < classes.size(); i++) {
			classDropDown.add(classes.get(i).getName());
		}
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		classDropDown.setLayoutData(gridData);

		//Alignment
		Label charAlignment = new Label(manual, SWT.NONE);
		charAlignment.setText("Alignment: ");
		gridData = new GridData(SWT.END, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		gridData.horizontalSpan = 1;
		charAlignment.setLayoutData(gridData);
		charAlignment.pack();

		Text charAlignmentText = new Text(manual, SWT.BORDER | SWT.CENTER);
		charAlignmentText.setText("");
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		charAlignmentText.setLayoutData(gridData);

		//Deity
		Label charDeity = new Label(manual, SWT.NONE);
		charDeity.setText("Deity: ");
		gridData = new GridData(SWT.END, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		gridData.horizontalSpan = 1;
		charDeity.setLayoutData(gridData);
		charDeity.pack();

		Text charDeityText = new Text(manual, SWT.BORDER | SWT.CENTER);
		charDeityText.setText("");
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		charDeityText.setLayoutData(gridData);
		
		//Size
		Label charSize = new Label(manual, SWT.NONE);
		charSize.setText("Size: ");
		gridData = new GridData(SWT.END, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		gridData.horizontalSpan = 1;
		charSize.setLayoutData(gridData);
		charSize.pack();

		// class drop down menu
		Combo sizeDropDown = new Combo(manual, SWT.DROP_DOWN | SWT.READ_ONLY);
		for (int i = 0; i < Main.gameState.sizeStrings.length; i++) {
			sizeDropDown.add(Main.gameState.sizeStrings[i]);
		}
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		sizeDropDown.setLayoutData(gridData);


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

		//TODO set the height for this scrollable composite from the Character Wizard
		scrolled.setMinHeight(saveChar.getLocation().y + saveChar.getSize().y);
		scrolled.layout();
		manual.layout();
		manual.pack();
	}

}
