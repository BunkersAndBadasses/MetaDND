package guis;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import core.DnDie;
import core.Roll;

/*
 * The class for the Die Window.
 * @author Ryan Ranney
 */
public class DieWindow {

	private Display display;
	private Shell shell;
	private Composite dieWin;
	private static Text dieBox;
	private static Text dieCountBox;
	private static Text modText;
	private static Text nameBox;
	private static Text total;
	private static Device dev;
	private static Label invalidOperation;
	//private static Label badInputText;
	//private static Label badSaveText;
	//private static Label badLoadText;
	//private static Label badDeleteText;
	private static Label badSaveFinal;
	private static Combo favList;
	private final int WIDTH = 230;
	private final int HEIGHT = 500;
	private int[] numDie = {0, 0, 0, 0, 0, 0, 0};
	private static String modString = "0";


	public DieWindow(Display d) {
		display = d;
		shell = new Shell(d);
		shell.setText("Die Roller");
		//shell.setSize(WIDTH, HEIGHT);
		dieWin = new Composite(shell, SWT.NONE);
		//dieWin.setBounds(0, 0, WIDTH, HEIGHT);

		createPageContent();
		shell.pack();
		run();
	}

	public void run() {
		center(shell);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private static void center(Shell shell) {

		Rectangle bds = shell.getDisplay().getBounds();

		Point p = shell.getSize();

		int nLeft = (bds.width - p.x) / 2;
		int nTop = (bds.height - p.y) / 2;

		shell.setBounds(nLeft, nTop, p.x, p.y);
	}

	private void createPageContent() {

		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.horizontalSpacing = 3;
		layout.numColumns = 3;
		dieWin.setLayout(layout);

		ArrayList<Button> incButtons = new ArrayList<Button>();
		ArrayList<Button> decButtons = new ArrayList<Button>();
//		final String [] dieNames = {"d4    ", "d6    ", "d8    ", "d10  ",
//				"d12  ", "d20  ", "d100"};
		final String [] dieNames = {"d4", "d6", "d8", "d10",
				"d12", "d20", "d100"};
		final int [] dieNameNumbers = {4, 6, 8, 10, 12, 20, 100};
		
		//DIE TEXT AND INC/DEC BUTTONS
		Label[] dieLabels = new Label[7]; 
		//GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		for(int i = 0; i < 7; i++){

			// The die X number text that's updated on button push
			final Label dieText = new Label(dieWin, SWT.NONE);
			dieLabels[i] = dieText; 
			Font font1 = new Font(display, new FontData("Arial", 24,
					SWT.NONE));
			dieText.setFont(font1);
			//dieText.setLocation(20, (i*40) + 24);
			//dieText.setText(dieNames[i] + " x " + numDie[i]);
			dieText.setText(numDie[i] + dieNames[i]);
			GridData gridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
			gridData.horizontalIndent = 5;
			dieText.setLayoutData(gridData);
			//dieText.pack();

			Button inc = new Button(dieWin, SWT.PUSH);
			inc.setText("+");
			//inc.setLocation(145, (i*40) + 20);
			//inc.setSize(33,33);
			gridData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
			inc.setLayoutData(gridData);
			final int index = i;
			inc.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					if (numDie[index] >= 20)
						return;

					numDie[index] ++;
					dieText.setText(numDie[index] + dieNames[index]);
					dieWin.layout();
				}
			});
			incButtons.add(inc);

			Button dec = new Button(dieWin, SWT.PUSH);
			dec.setText("-");
			//dec.setBounds(175, (i*40) + 20, 33, 33);
			gridData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
			dec.setLayoutData(gridData);
			dec.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					if (numDie[index] <= 0) 
						return;

					numDie[index] --;
					dieText.setText(numDie[index] + dieNames[index]);
					dieWin.layout();

				}
			});
			decButtons.add(dec);
		}
		
		// Custom Die text
		final Label custom = new Label(dieWin, SWT.NONE);
		Font font2 = new Font(display, new FontData("Arial", 24,
				SWT.NONE));
		custom.setFont(font2);
		custom.setText("Custom \n (#, die)");
		GridData gridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		custom.setLayoutData(gridData);
		//mod.pack();

		//Custom die Count box
		dieCountBox = new Text(dieWin, SWT.BORDER);
		dieCountBox.setText("0");
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		dieCountBox.setLayoutData(gridData);
		
		//Custom die box
		dieBox = new Text(dieWin, SWT.BORDER);
		dieBox.setText("0");
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		dieBox.setLayoutData(gridData);
		
		// Mod text
		final Label mod = new Label(dieWin, SWT.NONE);
		mod.setFont(font2);
		mod.setText("Modifier = ");
		gridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		mod.setLayoutData(gridData);
		//mod.pack();

		//Mod text box
		modText = new Text(dieWin, SWT.BORDER);
		modText.setText("0");
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		modText.setLayoutData(gridData);

		// Total text
		final Label totalText = new Label(dieWin, SWT.NONE);
		Font font4 = new Font(display, new FontData("Arial", 24,
				SWT.BOLD));
		totalText.setFont(font4);
		totalText.setText("Total = ");
		gridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		totalText.setLayoutData(gridData);

		// Total's read-only display box
		total = new Text(dieWin, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		total.setText("0");
		Font font5 = new Font(display, new FontData("Arial", 16,
				SWT.NONE));
		total.setFont(font5);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		total.setLayoutData(gridData);

		// ROLL BUTTON
		Font font3 = new Font(display, new FontData("Arial", 15,
				SWT.NONE));
		Button roll = new Button(dieWin, SWT.PUSH);
		roll.setText("Roll");
		roll.setFont(font3);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalIndent = 5;
		roll.setLayoutData(gridData);
		roll.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int modInt = 0;
				int rollTotal = 0;
				int custDie = 0;
				int custDieCount = 0;
				boolean dieRolled = false;

				try{
					
					invalidOperation.setVisible(false);
					modInt = Integer.parseInt(modText.getText());
					custDie = Integer.parseInt(dieBox.getText());
					custDieCount = Integer.parseInt(dieCountBox.getText());
					
					if(modInt <= -100 || modInt >= 100){
						invalidOperation.setText("Invalid modifier: -100 < mod < 100");
						invalidOperation.setVisible(true);
						
						return;
					}
					
					if(custDie < 0 || custDie > 1000 || custDie == 1){
						invalidOperation.setText("Invalid Custom Die: 1 < Die <1000");
						invalidOperation.setVisible(true);
						
						return;
					}
					
					if(custDieCount < 0 || custDieCount > 20){
						invalidOperation.setText("Invalid Custom Cnt: 0< count< 21");
						invalidOperation.setVisible(true);
						
						return;
					}
					
					if(custDie != 0 && custDieCount == 0){
						invalidOperation.setText("Invalid Custom #: select die count");
						invalidOperation.setVisible(true);
						
						return;
					}
					
					if(custDie == 0 && custDieCount != 0){
						invalidOperation.setText("Invalid Custom Die: select a die");
						invalidOperation.setVisible(true);
						
						return;
					}
					
					for(int i = 0; i < 7; i++){
						if(numDie[i] != 0)
							dieRolled = true;
					}

					if(!dieRolled && modInt == 0 && custDie == 0 && custDieCount == 0){
						invalidOperation.setText("Invalid Roll: must roll at least 1 die.");
						invalidOperation.setVisible(true);
						
						return;
					}
					
				}catch(Exception error){
					modText.setText("0");
					dieBox.setText("0");
					dieCountBox.setText("0");
					invalidOperation.setText("Invalid Textbox Input: numbers only");
					invalidOperation.setVisible(true);
					
					return;
				}

				//add the rolling to this
				for(int i = 0; i < 7; i++){
					rollTotal += DnDie.roll(dieNameNumbers[i], numDie[i]);
					//System.out.println(rollTotal);
				}
				rollTotal += modInt;
				
				if(custDie != 0 && custDieCount != 0){
					rollTotal += DnDie.roll(custDie, custDieCount);
				}

				total.setText(Integer.toString(rollTotal));

			}
		});

		// SAVE BUTTON
		Button save = new Button(dieWin, SWT.PUSH);
		save.setText("Save");
		save.setFont(font3);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		save.setLayoutData(gridData);
		save.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int modInt = 0;
				boolean notUsed = true;
				int custDie = 0;
				int custDieCount = 0;
				boolean dieRolled = false;

				try{
					
					invalidOperation.setVisible(false);
					modInt = Integer.parseInt(modText.getText());
					custDie = Integer.parseInt(dieBox.getText());
					custDieCount = Integer.parseInt(dieCountBox.getText());
					
					if(modInt <= -100 || modInt >= 100){
						invalidOperation.setText("Invalid modifier: -100 < mod < 100");
						invalidOperation.setVisible(true);
						
						return;
					}
					
					if(custDie < 0 || custDie > 1000 || custDie == 1){
						invalidOperation.setText("Invalid Custom Die: 1 < Die <1000");
						invalidOperation.setVisible(true);
						
						return;
					}
					
					if(custDieCount < 0 || custDieCount > 20){
						invalidOperation.setText("Invalid Custom Cnt: 0< count< 21");
						invalidOperation.setVisible(true);
						
						return;
					}
					
					if(custDie != 0 && custDieCount == 0){
						invalidOperation.setText("Invalid Custom #: select die count");
						invalidOperation.setVisible(true);
						
						return;
					}
					
					if(custDie == 0 && custDieCount != 0){
						invalidOperation.setText("Invalid Custom Die: select a die");
						invalidOperation.setVisible(true);
						
						return;
					}
					
					for(int i = 0; i < 7; i++){
						if(numDie[i] != 0)
							dieRolled = true;
					}

					if(!dieRolled && modInt == 0 && custDie == 0 && custDieCount == 0){
						invalidOperation.setText("Invalid Roll: must roll at least 1 die.");
						invalidOperation.setVisible(true);
						
						return;
					}
					
				}catch(Exception error){
					modText.setText("0");
					dieBox.setText("0");
					dieCountBox.setText("0");
					invalidOperation.setText("Invalid Textbox Input: numbers only");
					invalidOperation.setVisible(true);
					
					return;
				}

				ArrayList<Roll> roll = new ArrayList<Roll>(9);

				//add die that were added
				for(int i = 0; i < 7; i++){
					if(numDie[i] > 0){
						notUsed = false;
						roll.add(new Roll(dieNameNumbers[i], numDie[i]));
					}
				}

				if(custDie != 0 && custDieCount != 0){
					notUsed = false;
					roll.add(new Roll(custDie, custDieCount));
				}
				
				// if a die was added, or a mod was there
				if(modInt != 0){
					roll.add(new Roll(0, 0, modInt));

				}else if(notUsed){
					invalidOperation.setText("Invalid Save: at least 1 die or mod.");
					invalidOperation.setVisible(true);
					return;
				}

				final ArrayList<Roll> rollFinal = roll;

				notUsed = true;

				//TODO layout this save window
				final Shell saveName = new Shell(display);
				saveName.setText("Save");
				//saveName.setSize(300, 200);
				center(saveName);

				// this appears when there is an empty save
				badSaveFinal = new Label(saveName, SWT.NONE);
				badSaveFinal.setForeground(new Color(dev,255,0,0));
				badSaveFinal.setLocation(10,110);
				badSaveFinal.setVisible(false);
				badSaveFinal.setText("Invalid Save: must be aplhanumeric values only");
				badSaveFinal.pack();

				Label name = new Label(saveName, SWT.NONE);
				name.setLocation(77,50);
				name.setText("Favorite Dice Roll Name");
				name.pack();

				//Save name text box
				nameBox = new Text(saveName, SWT.BORDER);
				nameBox.setText("");
				nameBox.setBounds(90,75,120,30);

				Button cancel = new Button(saveName, SWT.PUSH);
				cancel.setBounds(10,130,130,30);
				cancel.setText("Cancel");
				cancel.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {

						saveName.dispose();
					}
				});

				Button saveFinal = new Button(saveName, SWT.PUSH);
				saveFinal.setBounds(160,130,130,30);
				saveFinal.setText("Save");
				saveFinal.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {

						badSaveFinal.setVisible(false);
						boolean found = false;
						Pattern p1 = Pattern.compile(".*\\W+.*");

						if(nameBox.getText().equalsIgnoreCase("")){
							badSaveFinal.setText("Invalid Save: must have a file name.");
							badSaveFinal.setVisible(true);
							return;
						}else if(nameBox.getText().length() > 20){
							badSaveFinal.setText("Invalid Save: file name is capped at 20 chars.");
							badSaveFinal.setVisible(true);
							return;
						}
						Matcher m = p1.matcher(nameBox.getText());
						if(m.find()){
							badSaveFinal.setText("Invalid Save: must be alphanumeric values only");
							badSaveFinal.setVisible(true);
							return;
						}		

						try{
						DnDie.saveFavDie(nameBox.getText(), rollFinal);
						
						if(favList.indexOf(nameBox.getText()) == -1)
							favList.add(nameBox.getText());
						}catch(Exception e){
							badSaveFinal.setText("Invalid Save: start with a letter");
							badSaveFinal.setVisible(true);
							
							return;
						}
						
						saveName.dispose();
					}
				});
				
				saveName.pack();


				saveName.open();
				while (!saveName.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		});

		favList = new Combo(dieWin, SWT.DROP_DOWN | SWT.READ_ONLY);
		//favList.setBounds(250, 90, 200, 30);
		favList.add("Favorite Die Roll");
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 3;
		gridData.horizontalIndent = 3;
		favList.setLayoutData(gridData);
		favList.select(0);

		try {
			Files.walk(Paths.get("./favRolls")).forEach(filePath ->{
				if(filePath.getFileName().toString().contains(".xml")){
					String fileName = filePath.getFileName().toString();
					fileName = (String) fileName.subSequence(0, fileName.length() - 4);
					favList.add(fileName);
				}
				else{
					//System.out.println(filePath.getFileName() + " is not an XML file");
				}
			});
		} catch (IOException e) {


		}

		// The button that loads the selected file name into the die window.
		Button load = new Button(dieWin, SWT.PUSH);
		load.setText("Load");
		load.setFont(font3);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalIndent = 5;
		load.setLayoutData(gridData);
		load.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				invalidOperation.setVisible(false);
				
				if(favList.getSelectionIndex() == 0){
					invalidOperation.setText("Invalid Load: must select a file.");
					invalidOperation.setVisible(true);
					
					return;
				}

				ArrayList<Roll> loaded = new ArrayList<Roll>(8);
				loaded = DnDie.loadFavDie(favList.getItem(favList.getSelectionIndex()));

				boolean d4 = false;
				boolean d6 = false;
				boolean d8 = false;
				boolean d10 = false;
				boolean d12 = false;
				boolean d20 = false;
				boolean d100 = false;
				boolean custom = false;
				boolean modded = false;

				for(int i = 0; i < loaded.size(); i++){
					if(loaded.get(i).getDieSize() == 4){
						numDie[0] = loaded.get(i).getDieCount();
						dieLabels[0].setText(numDie[0] + dieNames[0]);
						dieWin.layout();
						d4 = true;
					}
					else if(loaded.get(i).getDieSize() == 6){
						numDie[1] = loaded.get(i).getDieCount();
						dieLabels[1].setText(numDie[1] + dieNames[1]);
						dieWin.layout();
						d6 = true;
					}
					else if(loaded.get(i).getDieSize() == 8){
						numDie[2] = loaded.get(i).getDieCount();
						dieLabels[2].setText(numDie[2] + dieNames[2]);
						dieWin.layout();
						d8 = true;
					}
					else if(loaded.get(i).getDieSize() == 10){
						numDie[3] = loaded.get(i).getDieCount();
						dieLabels[3].setText(numDie[3] + dieNames[3]);
						dieWin.layout();
						d10 = true;
					}
					else if(loaded.get(i).getDieSize() == 12){
						numDie[4] = loaded.get(i).getDieCount();
						dieLabels[4].setText(numDie[4] + dieNames[4]);
						dieWin.layout();
						d12 = true;
					}
					else if(loaded.get(i).getDieSize() == 20){
						numDie[5] = loaded.get(i).getDieCount();
						dieLabels[5].setText(numDie[5] + dieNames[5]);
						dieWin.layout();
						d20 = true;
					}
					else if(loaded.get(i).getDieSize() == 100){
						numDie[6] = loaded.get(i).getDieCount();
						dieLabels[6].setText(numDie[6] + dieNames[6]);
						dieWin.layout();
						d100 = true;
					}else if(loaded.get(i).getDieSize() != 0){
						custom = true;
						dieBox.setText(Integer.toString(loaded.get(i).getDieSize()));
						dieCountBox.setText(Integer.toString(loaded.get(i).getDieCount()));
					}
					if(loaded.get(i).getModifier() != 0){
						modded = true;
						modText.setText(Integer.toString(loaded.get(i).getModifier()));
					}

					if(!d4){
						numDie[0] = 0;
						dieLabels[0].setText(numDie[0] + dieNames[0]);
						dieWin.layout();
					}
					if(!d6){
						numDie[1] = 0;
						dieLabels[1].setText(numDie[1] + dieNames[1]);
						dieWin.layout();
					}
					if(!d8){
						numDie[2] = 0;
						dieLabels[2].setText(numDie[2] + dieNames[2]);
						dieWin.layout();
					}
					if(!d10){
						numDie[3] = 0;
						dieLabels[3].setText(numDie[3] + dieNames[3]);
						dieWin.layout();
					}
					if(!d12){
						numDie[4] = 0;
						dieLabels[4].setText(numDie[4] + dieNames[4]);
						dieWin.layout();
					}
					if(!d20){
						numDie[5] = 0;
						dieLabels[5].setText(numDie[5] + dieNames[5]);
						dieWin.layout();
					}
					if(!d100){
						numDie[6] = 0;
						dieLabels[6].setText(numDie[6] + dieNames[6]);
						dieWin.layout();
					}
					if(!modded){
						modText.setText("0");
					}
					if(!custom){
						dieBox.setText("0");
						dieCountBox.setText("0");
					}

				}

			}
		});
		
		Button delete = new Button(dieWin, SWT.PUSH);
		delete.setText("Delete");
		delete.setFont(font3);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		delete.setLayoutData(gridData);
		delete.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				
				String deleteMe = favList.getItem(favList.getSelectionIndex());
				
				invalidOperation.setVisible(false);
				
				if(favList.getSelectionIndex() == 0){
					invalidOperation.setText("Invalid Delete: must select a file.");
					invalidOperation.setVisible(true);
					
					return;
				}

				//TODO layout this delete window
				final Shell deleteFile = new Shell(display);
				deleteFile.setText("Delete");
				//deleteFile.setSize(250, 150);
				center(deleteFile);

				Label name = new Label(deleteFile, SWT.NONE);
				name.setLocation(20,40);
				name.setText("Are you sure you want to delete?");
				name.pack();

				Button cancel = new Button(deleteFile, SWT.PUSH);
				cancel.setBounds(10,90,80,30);
				cancel.setText("Cancel");
				cancel.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {

						deleteFile.dispose();
					}
				});

				Button saveFinal = new Button(deleteFile, SWT.PUSH);
				saveFinal.setBounds(160,90,80,30);
				saveFinal.setText("Delete");
				saveFinal.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {

						DnDie.deleteFavDie(deleteMe);
						favList.remove(deleteMe);
						favList.select(0);
						deleteFile.dispose();
					}
				});
				
				deleteFile.pack();

				deleteFile.open();
				while (!deleteFile.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
			
		});

		// this appears when there is an invalid operation attempt
		invalidOperation = new Label(dieWin, SWT.NONE);
		invalidOperation.setForeground(new Color(dev,255,0,0));
		invalidOperation.setVisible(false);
		invalidOperation.setText("");
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 3;
		gridData.verticalSpan = 2;
		invalidOperation.setLayoutData(gridData);
		
		dieWin.pack();
		return;
		
	}

	public static void main(String[] args) {
		Display display = new Display();
		DieWindow dw = new DieWindow(display);
		display.dispose();
	}

}
