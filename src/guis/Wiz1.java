/*
 * CHOOSE ABILITY SCORES
 */

package guis;
import core.character;

import java.util.ArrayList;
import java.util.Random;

import org.eclipse.swt.SWT;
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

public class Wiz1 {	

	private Composite wiz1;
	private CharacterWizard cw;
	private Device dev;
	private int WIDTH;
	private int HEIGHT;
	private character character;
	private Composite panel;
	private Composite home;
	private Composite homePanel;
	private StackLayout layout;
	private StackLayout homeLayout;
	private ArrayList<Composite> wizPages;
	private Composite nextPage;
	private int wizPagesSize;

	public int[] as = new int[6]; // ability scores array
	//private Text wiz1LevelText;
	//private Label wiz1LevelText;
	private Text wiz1AS1;
	private Text wiz1AS2;
	private Text wiz1AS3;
	private Text wiz1AS4;
	private Text wiz1AS5;
	private Text wiz1AS6;
	private Label badLevelInputText;
	private Label badASInputText;


	public Wiz1(CharacterWizard cw, Device dev, int WIDTH, int HEIGHT, 
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz1 = wizPages.get(0);
		//CharacterWizard.wizPageNum = 0;
		layout.topControl = wiz1;
		panel.layout();
		this.cw = cw;
		this.dev = dev;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.character = cw.getCharacter();
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(1);
		this.wizPagesSize = wizPages.size();

		createPageContent();
	}

	private void createPageContent() {
		Label wiz1Label = new Label(wiz1, SWT.NONE);
		wiz1Label.setText("Select starting level and roll for ability scores");
		wiz1Label.pack();
		
		
		// initialize layout
		
		GridLayout gl = new GridLayout(10, false);
		
		Composite inner = new Composite(wiz1, SWT.BORDER);
		inner.setBounds(5, 20, WIDTH-10, HEIGHT-110);
		inner.setLayout(gl);

		GridData gd;
		
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 10;
		new Label(inner, SWT.NONE).setLayoutData(gd);

		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 10;
		new Label(inner, SWT.NONE).setLayoutData(gd);

		// level label
		Label wiz1LevelLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 10;
		wiz1LevelLabel.setLayoutData(gd);
		
//		// level text
//		wiz1LevelText = new Label(inner, SWT.NONE);
//		gd = new GridData(SWT.LEFT, SWT.CENTER, true, false);
//		gd.horizontalSpan = 4;
//		wiz1LevelText.setLayoutData(gd);
		
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 2;
		new Label(inner, SWT.NONE).setLayoutData(gd);

		// ability scores fields
		wiz1AS1 = new Text(inner, SWT.BORDER);
		gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		wiz1AS1.setLayoutData(gd);
		//wiz1AS1.setBounds(WIDTH/2 - 175,200,50,30);
		wiz1AS2 = new Text(inner, SWT.BORDER);
		gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		wiz1AS2.setLayoutData(gd);
		//wiz1AS2.setBounds(WIDTH/2 - 115,200,50,30);
		wiz1AS3 = new Text(inner, SWT.BORDER);
		gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		wiz1AS3.setLayoutData(gd);
		//wiz1AS3.setBounds(WIDTH/2 - 55,200,50,30);
		wiz1AS4 = new Text(inner, SWT.BORDER);
		gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		wiz1AS4.setLayoutData(gd);
		//wiz1AS4.setBounds(WIDTH/2 + 5,200,50,30);
		wiz1AS5 = new Text(inner, SWT.BORDER);
		gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		wiz1AS5.setLayoutData(gd);
		//wiz1AS5.setBounds(WIDTH/2 + 65,200,50,30);
		wiz1AS6 = new Text(inner, SWT.BORDER);
		gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		wiz1AS6.setLayoutData(gd);
		//wiz1AS6.setBounds(WIDTH/2 + 125,200,50,30);

		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 2;
		new Label(inner, SWT.NONE).setLayoutData(gd);

		// roll button
		Button wiz1RollButton = new Button(inner, SWT.PUSH);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 10;
		wiz1RollButton.setLayoutData(gd);

		// level error message
		badLevelInputText = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 10;
		badLevelInputText.setLayoutData(gd);
		
		// ability score error message
		badASInputText = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.horizontalSpan = 10;
		badASInputText.setLayoutData(gd);
		
		
		// create content
		
		// level field
		// label
		wiz1LevelLabel.setText("Starting Level: 1");
		//wiz1LevelLabel.setBounds(WIDTH/2 - 65, 135, 100, 100);
		wiz1LevelLabel.pack();
		// text box
		//wiz1LevelText = new Text(wiz1, SWT.BORDER | SWT.READ_ONLY);
		//wiz1LevelText.setBounds(WIDTH/2 + 35,130,30,30);
		//wiz1LevelText.setText("1");

		// roll button
		wiz1RollButton.setText("Roll");
		//wiz1RollButton.setBounds(WIDTH/2 - 50, 250, 100, 50);
		wiz1RollButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				badASInputText.setVisible(false);
				badLevelInputText.setVisible(false);
				int[] roll = genAS();
				wiz1AS1.setText(Integer.toString(roll[0]));
				wiz1AS2.setText(Integer.toString(roll[1]));
				wiz1AS3.setText(Integer.toString(roll[2]));
				wiz1AS4.setText(Integer.toString(roll[3]));
				wiz1AS5.setText(Integer.toString(roll[4]));
				wiz1AS6.setText(Integer.toString(roll[5]));
			}
		});

		// this appears when there is invalid input in level box
		badLevelInputText.setForeground(new Color(dev,255,0,0));
		//badLevelInputText.setBounds(WIDTH/2 -131,320,262,30);
		badLevelInputText.setVisible(false);
		badLevelInputText.setText("invalid level: must be a positive integer");

		// this appears when there is invalid input in any ability score boxes
		badASInputText.setForeground(new Color(dev,255,0,0));
		//badASInputText.setBounds(WIDTH/2 - 200,355,400,30);
		badASInputText.setVisible(false);
		badASInputText.setText("invalid ability score: must be a positive integer from 3 to 18");

		inner.layout();
		
		// next button
		Button wiz1NextButton = cw.createNextButton(wiz1);
		wiz1NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// error checking for level
				boolean error = false;
				int level = 1;
				try {
					badLevelInputText.setVisible(false);
					//level = Integer.parseInt(wiz1LevelText.getText());
					if (level <= 0 || level > 25) throw new Exception();
				} catch (Exception e) {
					badLevelInputText.setVisible(true);
					error = true;
				}

				// error checking for ability scores
				try {
					badASInputText.setVisible(false);
					as[0] = Integer.parseInt(wiz1AS1.getText());
					as[1] = Integer.parseInt(wiz1AS2.getText());
					as[2] = Integer.parseInt(wiz1AS3.getText());
					as[3] = Integer.parseInt(wiz1AS4.getText());
					as[4] = Integer.parseInt(wiz1AS5.getText());
					as[5] = Integer.parseInt(wiz1AS6.getText());
					for (int i = 0; i < 6; i++)
						if (as[i] < 3 || as[i] > 18) throw new Exception();
				} catch (Exception e) {
					badASInputText.setVisible(true);
					error = true;
				}

				// user cannot move on with an error
				if (error) return;

				// if all goes well, save info
				character.setLevel(level);
				cw.setBaseAbilityScores(as);

				// clears any past error messages
				badLevelInputText.setVisible(false);
				badASInputText.setVisible(false);

				if (cw.wizPageNum < wizPagesSize - 1)
					cw.wizPageNum++;
				if (!cw.wizPageCreated[1])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});
		
		// cancel button
		Button wiz1CancelButton = cw.createCancelButton(wiz1, home, homePanel, homeLayout);
		wiz1CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (cw.cancel)
					cw.reset();
			}
		});
	}
	
	/**
	 *  generates random number between 3 and 18 (for use as an ability score)
	 *  simulates rolling 4 dnd dropping the lowest roll
	 */
	private int[] genAS() {
		Random r = new Random();
		int[] result = { 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < 6; i++) {
			int roll[] = { r.nextInt(6) + 1, r.nextInt(6) + 1,
					r.nextInt(6) + 1, r.nextInt(6) + 1 };
			int min = 7; // max value a roll can be is 6
			for (int j = 0; j < 4; j++) {
				result[i] += roll[j];
				if (roll[j] < min)
					min = roll[j];
			}
			result[i] -= min;
		}
		return result;
	}

	public Composite getWiz1() { return wiz1; }

	private void createNextPage() {
		cw.wizPageCreated[1] = true;
		cw.wizs.add(new Wiz2(cw, dev, WIDTH, HEIGHT, panel, home, homePanel,
				layout, homeLayout, wizPages));
	}

}
