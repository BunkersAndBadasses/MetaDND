package guis;
import core.character;

import java.util.ArrayList;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;


/* TODO
 * 
 * FIX: when user returns to this page (hitting back button) and changes a value, that change is not saved.....
 *
 */


public class Wiz1 {
	

	private Composite wiz1;
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
	private Text wiz1LevelText;
	private Text wiz1AS1;
	private Text wiz1AS2;
	private Text wiz1AS3;
	private Text wiz1AS4;
	private Text wiz1AS5;
	private Text wiz1AS6;
	private Label badLevelInputText;
	private Label badASInputText;


	public Wiz1(Device dev, int WIDTH, int HEIGHT, final character character, 
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz1 = wizPages.get(0);
		CharacterWizard.wizPageNum = 0;
		layout.topControl = wiz1;
		panel.layout();
		this.dev = dev;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.character = character;
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

		// level field
		// label
		Label wiz1LevelLabel = new Label(wiz1, SWT.NONE);
		wiz1LevelLabel.setText("Starting Level:");
		wiz1LevelLabel.setBounds(WIDTH/2 - 65, 135, 100, 100);
		wiz1LevelLabel.pack();
		// text box
		wiz1LevelText = new Text(wiz1, SWT.BORDER | SWT.READ_ONLY); // TODO change when logic for higher levels is added
		wiz1LevelText.setBounds(WIDTH/2 + 35,130,30,30);
		wiz1LevelText.setText("1");

		// ability scores fields
		wiz1AS1 = new Text(wiz1, SWT.BORDER);
		wiz1AS1.setBounds(WIDTH/2 - 175,200,50,30);
		wiz1AS2 = new Text(wiz1, SWT.BORDER);
		wiz1AS2.setBounds(WIDTH/2 - 115,200,50,30);
		wiz1AS3 = new Text(wiz1, SWT.BORDER);
		wiz1AS3.setBounds(WIDTH/2 - 55,200,50,30);
		wiz1AS4 = new Text(wiz1, SWT.BORDER);
		wiz1AS4.setBounds(WIDTH/2 + 5,200,50,30);
		wiz1AS5 = new Text(wiz1, SWT.BORDER);
		wiz1AS5.setBounds(WIDTH/2 + 65,200,50,30);
		wiz1AS6 = new Text(wiz1, SWT.BORDER);
		wiz1AS6.setBounds(WIDTH/2 + 125,200,50,30);

		// roll button
		Button wiz1RollButton = new Button(wiz1, SWT.PUSH);
		wiz1RollButton.setText("Roll");
		wiz1RollButton.setBounds(WIDTH/2 - 50, 250, 100, 50);
		wiz1RollButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
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
		badLevelInputText = new Label(wiz1, SWT.NONE);
		badLevelInputText.setForeground(new Color(dev,255,0,0));
		badLevelInputText.setBounds(WIDTH/2 -131,320,262,30);
		badLevelInputText.setVisible(false);
		badLevelInputText.setText("invalid level: must be a positive integer");

		// this appears when there is invalid input in any ability score boxes
		badASInputText = new Label(wiz1, SWT.NONE);
		badASInputText.setForeground(new Color(dev,255,0,0));
		badASInputText.setBounds(WIDTH/2 - 200,355,400,30);
		badASInputText.setVisible(false);
		badASInputText.setText("invalid ability score: must be a positive integer from 3 to 18");


		// next button
		Button wiz1NextButton = CharacterWizard.createNextButton(wiz1);
		wiz1NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// error checking for level
				boolean error = false;
				int level = 1;
				try {
					badLevelInputText.setVisible(false);
					// TODO add cap for level
					level = Integer.parseInt(wiz1LevelText.getText());
					if (level <= 0) throw new Exception();
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
				CharacterWizard.baseAbilityScores = as;

				// clears any past error messages
				badLevelInputText.setVisible(false);
				badASInputText.setVisible(false);

				if (CharacterWizard.wizPageNum < wizPagesSize - 1)
					CharacterWizard.wizPageNum++;
				if (!CharacterWizard.wizPageCreated[1])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});
		
		// cancel button
		Button wiz1CancelButton = CharacterWizard.createCancelButton(wiz1, home, homePanel, homeLayout);
		wiz1CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.cancel)
					CharacterWizard.reset();
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
		CharacterWizard.wizPageCreated[1] = true;
		CharacterWizard.wizs.add(new Wiz2(dev, WIDTH, HEIGHT, character, panel, home, homePanel,
				layout, homeLayout, wizPages));
	}

}
