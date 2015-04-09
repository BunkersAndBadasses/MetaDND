package guis;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import java.util.ArrayList;
import core.character;



/*
 * TODO: 
 * 
 * comment out sec class
 * comment out back buttons
 * fix modifier logic (not rounding up)
 * add manual/random pages ("Coming soon!")
 * save character? 
 * launch wizards (add custom)
 * add search pop-up ("Coming soon!")
 * check feat prerequisites
 */

/*
 * iteration 2: 
 * 
 * second class
 * searching
 * back button
 * starting at level > 1
 * adding custom skills
 */

public class CharacterWizard {

	int pageNum = -1;
 
	private static Device dev;
	private static Display display;
	private Shell shell;
	private static StackLayout wizLayout;
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;
	public static int wizPageNum = -1;
	public static boolean cancel = false;
	public static boolean[] wizPageCreated = { false, false, false, false,
			false, false, false, false, false, false };

	private static ArrayList<Composite> wizPages;

	private static character character;

	public static int[] baseAbilityScores = new int[6];

	public CharacterWizard(Display d) {
		display = d;
		shell = new Shell(d);
		shell.setText("Create New Character");
		shell.setSize(WIDTH, HEIGHT);
		character = new character();
		wizPages = new ArrayList<Composite>();

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

		// the first thing that shows up is homePanel, which has a layout that
		// includes the home, wizPanel, manualPanel, and randomPanel pages
		// it rests on the home page
		// 'panel' in the name implies it holds a collection of pages
		// clicking on 'interactive character wizard' button changes the top
		// control of homePanel to wizPanel. the top control of wizPanel is
		// wiz1, which is the first page of the character wizard. clicking
		// back/next only changes the top control of wizPanel.
		// clicking cancel changes the top control of homePanel back to home
		// and resets the character wizard

		//////////////////// HOME PANEL SETUP ////////////////////////////

		final Composite homePanel = new Composite(shell, SWT.NONE);
		homePanel.setBounds(0, 0, WIDTH, HEIGHT);
		final StackLayout homeLayout = new StackLayout();
		homePanel.setLayout(homeLayout);

		//////////////////// HOME SCREEN SETUP ////////////////////////////

		// this screen is what is first seen when the window opens. 
		// it contains the buttons that link to the character wizard, the manual
		// character entering, and the random character generation
		final Composite home = new Composite(homePanel, SWT.NONE);
		home.setBounds(0, 0, WIDTH, HEIGHT);

		Label homeLabel = new Label(home, SWT.NONE);
		homeLabel.setText("Let's create a character!");
		Font font1 = new Font(homeLabel.getDisplay(), new FontData("Arial", 24,
				SWT.BOLD));
		homeLabel.setFont(font1);
		homeLabel.setBounds(WIDTH / 2 - 180, 40, 100, 100);
		homeLabel.pack();

		Label homeLabel2 = new Label(home, SWT.NONE);
		homeLabel2.setText("\nChoose a method:");
		Font font2 = new Font(homeLabel.getDisplay(), new FontData("Arial", 18,
				SWT.BOLD));
		homeLabel2.setFont(font2);
		homeLabel2.setBounds(WIDTH / 2 - 100, 65, 100, 100);
		homeLabel2.pack();

		Button wizardButton = new Button(home, SWT.PUSH);
		wizardButton.setText("Interactive\nCharacter Wizard");
		wizardButton.setFont(font2);
		wizardButton.setBounds(WIDTH / 2 - 150, 150, 300, 150);

		Button manualButton = new Button(home, SWT.PUSH);
		manualButton.setText("Manual");
		Font font3 = new Font(homeLabel.getDisplay(), new FontData("Arial", 18,
				SWT.NONE));
		manualButton.setFont(font3);
		manualButton.setBounds(WIDTH / 2 - 150, 310, 145, 75);

		Button randomButton = new Button(home, SWT.PUSH);
		randomButton.setText("Random");
		randomButton.setFont(font3);
		randomButton.setBounds(WIDTH / 2 + 5, 310, 145, 75);

		// set home as the first screen viewed when new character window  is launched
		homeLayout.topControl = home;

		// ///////////////// WIZARD PANEL SETUP ///////////////////////////

		final Composite wizPanel = new Composite(homePanel, SWT.BORDER);
		wizPanel.setBounds(0, 0, WIDTH, HEIGHT);
		wizPanel.setBackground(new Color(dev, 255, 0, 0));
		wizLayout = new StackLayout();
		wizPanel.setLayout(wizLayout);

		// ///////////////// MANUAL PANEL SETUP ///////////////////////////

		final Composite manualPanel = new Composite(homePanel, SWT.BORDER);
		wizPanel.setBounds(0, 0, WIDTH, (int) (HEIGHT * (.75)));
		final StackLayout manualLayout = new StackLayout();
		manualPanel.setLayout(manualLayout);

		// ////////////////// RANDOM PANEL SETUP //////////////////////////

		final Composite randomPanel = new Composite(homePanel, SWT.BORDER);
		wizPanel.setBounds(0, 0, WIDTH, (int) (HEIGHT * (.75)));
		final StackLayout randomLayout = new StackLayout();
		randomPanel.setLayout(randomLayout);

		// ////////////////// HOME BUTTON LISTENERS ///////////////////////

		wizardButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				wizLayout.topControl = wizPages.get(0);
				wizPanel.layout();
				homeLayout.topControl = wizPanel;
				homePanel.layout();
			}
		});
		manualButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				homeLayout.topControl = manualPanel;
				homePanel.layout();
			}
		});
		randomButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				homeLayout.topControl = randomPanel;
				homePanel.layout();
			}
		});

		
		/////////////////////// WIZARD PAGES ////////////////////////////

		// initialize all pages
		final Composite wiz1 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz1);
		final Composite wiz2 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz2);
		final Composite wiz3 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz3);
		final Composite wiz4 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz4);
		final Composite wiz5 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz5);
		final Composite wiz6 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz6);
		final Composite wiz7 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz7);
		final Composite wiz8 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz8);
		final Composite wiz9 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz9);
		final Composite wiz10 = new Composite(wizPanel, SWT.NONE);
		wizPages.add(wiz10);

		// create the first page (creates next pages at runtime)
		new Wiz1(dev, WIDTH, HEIGHT, character, wizPanel, home,
				homePanel, wizLayout, homeLayout, wizPages);
	}


	/**
	 * creates a next button on composite c in the bottom right corner.
	 * this does NOT set the listener! (each one is different, that is set 
	 * after this method is called)
	 * @param c
	 * @return
	 */
	public static Button createNextButton(Composite c) {
		Button nextButton = new Button(c, SWT.PUSH);
		nextButton.setText("Next");
		nextButton.setBounds(WIDTH - 117, HEIGHT - 90, 100, 50);
		return nextButton;
	}

	/**
	 * creates a back button on composite c in the bottom right corner.
	 * also sets the listener for the created button that changes the top 
	 * control page of the layout of the panel to be the previous page
	 * @param c
	 * @param panel
	 * @param layout
	 * @return
	 */
	public static Button createBackButton(Composite c, final Composite panel,
			final StackLayout layout) {
		Button backButton = new Button(c, SWT.PUSH);
		backButton.setText("Back");
		backButton.setBounds(WIDTH - 220, HEIGHT - 90, 100, 50);
		backButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (wizPageNum > 0)
					wizPageNum--;
				layout.topControl = wizPages.get(wizPageNum);
				panel.layout();
			}
		});
		return backButton;
	}

	/**
	 * creates a cancel button on composite c in bottom left corner.
	 * also sets the listener for the created button that changes the homePanel
	 * top control to be home and resets the wizard page counter wizPageNum
	 * @param c
	 * @param home
	 * @param panel
	 * @param layout
	 * @return
	 */
	public static Button createCancelButton(Composite c, final Composite home,
			final Composite panel, final StackLayout layout) {
		Button cancelButton = new Button(c, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.setBounds(10, HEIGHT - 90, 100, 50);
		cancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				cancel = false;
				final Shell areYouSureShell = new Shell(display);
				areYouSureShell.setText("Cancel");
				areYouSureShell.setSize(300, 200);
				center(areYouSureShell);

				Label areYouSure = new Label(areYouSureShell, SWT.NONE);
				areYouSure.setLocation(40,50);
				areYouSure.setText("Are you sure you want to cancel?");
				areYouSure.pack();

				Button yes = new Button(areYouSureShell, SWT.PUSH);
				yes.setBounds(10,130,130,30);
				yes.setText("Yes, Cancel");
				yes.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						cancel = true;
						areYouSureShell.dispose();
					}
				});

				Button no = new Button(areYouSureShell, SWT.PUSH);
				no.setBounds(160,130,130,30);
				no.setText("No, Don't Cancel");
				no.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						cancel = false;
						areYouSureShell.dispose();
					}
				});

				areYouSureShell.open();
				while (!areYouSureShell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
				if (cancel) {
					wizPageNum = -1;
					layout.topControl = home;
					panel.layout();
				}
			}
		});
		return cancelButton;
	}
	
	public static character getCharacter() {
		return character;
	}

	public static void reset() {
		character = new character();
		for (int i = 0; i < wizPageCreated.length; i++) {
			wizPageCreated[i] = false;
		}
	}

	public static void main(String[] args) {
		Display display = new Display();
		new CharacterWizard(display);
		display.dispose();
	}

}
