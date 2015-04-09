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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import core.CharSkill;
import core.Character;
import core.DnDie;
import core.Roll;

/*
 * The class for the Die Window.
 * @author Innocentius Shellingford
 */
public class DieWindow {

	private Display display;
	private Shell shell;
	private Composite dieWin;
	private static Text modText;
	private static Text nameBox;
	private static Text total;
	private static Device dev;
	private static Label badInputText;
	private static Label badSaveText;
	private static Label badSaveFinal;
	private static Combo favList;
	private final int WIDTH = 700;
	private final int HEIGHT = 500;
	private int[] numDie = {0, 0, 0, 0, 0, 0};
	private static String modString = "0";


	public DieWindow(Display d) {
		display = d;
		shell = new Shell(d);
		shell.setText("Die Roller");
		shell.setSize(WIDTH, HEIGHT);
		dieWin = new Composite(shell, SWT.NONE);
		dieWin.setBounds(0, 0, WIDTH, HEIGHT);

		createPageContent();

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
		shell.setLayout(layout);

		ArrayList<Label> dieLabels = new ArrayList<Label>();
		ArrayList<Button> incButtons = new ArrayList<Button>();
		ArrayList<Button> decButtons = new ArrayList<Button>();
		final String [] dieNames = {"d4  ", "d6  ", "d8  ", "d10","d12", "d20"};
		final int [] dieNameNumbers = {4, 6, 8, 10, 12, 20};
		GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);

		// this appears when there is invalid int in the mod box
		badInputText = new Label(dieWin, SWT.NONE);
		badInputText.setForeground(new Color(dev,255,0,0));
		badInputText.setLocation(WIDTH/2 -150,330);
		badInputText.setVisible(false);
		badInputText.setText("Invalid modifier: must be an integer -100 < X < 100");
		badInputText.pack();

		// this appears when there is an empty save
		badSaveText = new Label(dieWin, SWT.NONE);
		badSaveText.setForeground(new Color(dev,255,0,0));
		badSaveText.setLocation(WIDTH/2 -150,330);
		badSaveText.setVisible(false);
		badSaveText.setText("Invalid Save: must have at least 1 die or modifier");
		badSaveText.pack();

		// Mod text
		final Label mod = new Label(dieWin, SWT.NONE);
		Font font2 = new Font(display, new FontData("Arial", 24,
				SWT.NONE));
		mod.setFont(font2);//TODO make this not bold
		mod.setLocation(20, 265);
		//gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		//gridData.horizontalIndent = 5;
		//mod.setLayoutData(gridData);
		mod.setText("Modifier = ");
		mod.pack();

		//Mod text box
		modText = new Text(dieWin, SWT.BORDER);
		modText.setText("0");
		modText.setBounds(180,263,30,30);
		modText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Text text = (Text) e.widget;
				modString = text.getText();
			}
		});

		// Total text
		final Label totalText = new Label(dieWin, SWT.NONE);
		Font font4 = new Font(display, new FontData("Arial", 24,
				SWT.BOLD));
		totalText.setFont(font4);
		totalText.setLocation(55, 310);
		totalText.setText("Total = ");
		totalText.pack();

		// Total's read-only display box
		total = new Text(dieWin, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		total.setLocation(190, 308);//138
		total.setSize(45, 30);
		total.setText("0");
		Font font5 = new Font(display, new FontData("Arial", 16,
				SWT.NONE));
		total.setFont(font5);


		Font font3 = new Font(display, new FontData("Arial", 18,
				SWT.NONE));
		Button roll = new Button(dieWin, SWT.PUSH);
		roll.setText("Roll");
		roll.setFont(font3);
		roll.setLocation(75, 370);
		roll.setSize(85, 50);
		roll.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int modInt = 0;
				int rollTotal = 0;

				try{

					badInputText.setVisible(false);
					badSaveText.setVisible(false);
					modInt = Integer.parseInt(modString);

					if(modInt <= -100 || modInt >= 100)
						throw new Exception();

				}catch(Exception error){
					badInputText.setVisible(true);
					return;
				}

				//add the rolling to this
				for(int i = 0; i < 6; i++){
					rollTotal += DnDie.roll(dieNameNumbers[i], numDie[i]);
					//System.out.println(rollTotal);
				}
				rollTotal += modInt;

				total.setText(Integer.toString(rollTotal));

			}
		});

		Button save = new Button(dieWin, SWT.PUSH);
		save.setText("Save");
		save.setFont(font3);
		save.setLocation(175, 370);
		save.setSize(85, 50);
		save.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int modInt = 0;
				boolean notUsed = true;
				try{

					badInputText.setVisible(false);
					badSaveText.setVisible(false);
					modInt = Integer.parseInt(modString);

					if(modInt <= -100 || modInt >= 100)
						throw new Exception();

				}catch(Exception error){
					badInputText.setVisible(true);
					return;
				}

				ArrayList<Roll> roll = new ArrayList<Roll>(6);

				//add die that were added
				for(int i = 0; i < 6; i++){
					if(numDie[i] > 0){
						notUsed = false;
						roll.add(new Roll(dieNameNumbers[i], numDie[i]));
					}
				}

				// if a die was added, or a mod was there
				if(modInt != 0){
					roll.add(new Roll(0, 0, modInt));

				}else if(notUsed){
					badSaveText.setVisible(true);
					return;
				}

				final ArrayList<Roll> rollFinal = roll;

				notUsed = true;
				//TODO call the Save name window
				//DnDie.saveFavDie("test", roll);
				final Shell saveName = new Shell(display);
				saveName.setText("Save");
				saveName.setSize(300, 200);
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
						Pattern p = Pattern.compile(".*\\W+.*");
						Matcher m = p.matcher(nameBox.getText());
						if(m.find()){
							badSaveFinal.setVisible(true);
							return;
						}

						DnDie.saveFavDie(nameBox.getText(), rollFinal);
						favList.add(nameBox.getText());
						saveName.dispose();
					}
				});
				//Regex parser


				saveName.open();
				while (!saveName.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		});



		favList = new Combo(dieWin, SWT.DROP_DOWN | SWT.READ_ONLY);
		favList.setBounds(250, 90, 200, 30);
		favList.add("Favorite Die Roll");
		favList.select(0);
		//		favList.addListener(SWT.Selection, new Listener() {
		//			public void handleEvent(Event event) {
		//				
		//				
		//			}
		//		});

		try {
			Files.walk(Paths.get("./favRolls")).forEach(filePath ->{
				if(filePath.getFileName().toString().contains(".xml")){
					String fileName = filePath.getFileName().toString();
					fileName = (String) fileName.subSequence(0, fileName.length() - 4);
					favList.add(fileName);
				}
				else
					System.out.println(filePath.getFileName() + " is not an XML file");
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}

		for(int i = 0; i < 6; i++){

			// The die X number text that's updated on button push
			final Label dieText = new Label(dieWin, SWT.NONE);
			Font font1 = new Font(display, new FontData("Arial", 24,
					SWT.NONE));
			dieText.setFont(font1);
			dieText.setLocation(20, (i*40) + 24);
			dieText.setText(dieNames[i] + " x " + numDie[i]);
			dieText.pack();

			Button inc = new Button(dieWin, SWT.PUSH);
			inc.setText("+");
			inc.setLocation(145, (i*40) + 20);
			inc.setSize(33,33);
			final int index = i;
			inc.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					if (numDie[index] >= 20)
						return;

					numDie[index] ++;
					dieText.setText(dieNames[index] + " x " + numDie[index]);
					dieText.pack();
				}
			});
			incButtons.add(inc);

			Button dec = new Button(dieWin, SWT.PUSH);
			dec.setText("-");
			dec.setBounds(175, (i*40) + 20, 33, 33);
			dec.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					if (numDie[index] <= 0) 
						return;

					numDie[index] --;
					dieText.setText(dieNames[index] + " x " + numDie[index]);
					dieText.pack();

				}
			});
			decButtons.add(dec);
		}

	}

	public static void main(String[] args) {
		Display display = new Display();
		DieWindow dw = new DieWindow(display);
		display.dispose();
	}

}
