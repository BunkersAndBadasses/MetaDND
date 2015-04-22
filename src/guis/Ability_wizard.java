package guis;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import core.Main;
import entity.AbilityEntity;
/**
 * Class for ability_wizard, handle input and output
 * @author Innocentius
 *
 */
public class Ability_wizard {
	private static Shell shell;
	private static Display display;
	public static boolean cancel = false;
	private static final int WIDTH = 600;
    private static final int HEIGHT = 400;//copy from character wizard, see for change
	private static ArrayList<Composite> wizPages;
	private static int wizpagenum;
	public static AbilityEntity newability;
	static String abilityname;
	static String abilityscript;
	public Ability_wizard(Display d)
	{
		display = d;
		shell = new Shell(d);
		shell.setText("Create New Ability");
		int width = display.getMonitors()[0].getBounds().width;
		shell.setSize(width / 3, width * 2 / 9);
		wizpagenum = 0;
		wizPages = new ArrayList<Composite>();
		createPageContent();
		run();
	}
	public void run()
	{
		center(shell);

        shell.open();

        while (!shell.isDisposed()) {
          if (!display.readAndDispatch()) {
            display.sleep();
          }
        }
	}
	/**
	 * Set window to be the center.
	 * @param shell the window needed to be in the center
	 */
	private static void center(Shell shell) 
	{

        Rectangle bds = shell.getDisplay().getBounds();

        Point p = shell.getSize();

        int nLeft = (bds.width - p.x) / 2;
        int nTop = (bds.height - p.y) / 2;

        shell.setBounds(nLeft, nTop, p.x, p.y);
    }
	/**
	 * We get the inputs, put them into Strings, hash map it, then create a new feat... BUT where should I put it?
	 * TODO verify the storing method
	 */
	private void createPageContent() 
	{
        GridLayout gl = new GridLayout(6, true);
        gl.verticalSpacing = 5;
		shell.setLayout(gl);
		GridData gd;
		final Label wiz1Label = new Label(shell, SWT.NONE);
		wiz1Label.setText("Enter Fields");
		gd = new GridData(GridData.FILL, GridData.FILL,false, false);
		gd.horizontalSpan = 6;
		wiz1Label.setLayoutData(gd);
		wiz1Label.pack();
		Text nameInput = new Text(shell, SWT.BORDER);
		nameInput.setMessage("Name");
		gd = new GridData(GridData.FILL, GridData.FILL, false, false);
		gd.horizontalSpan = 3;
		nameInput.setLayoutData(gd);
		nameInput.pack();
		Text descriptionInput = new Text(shell, SWT.WRAP | SWT.V_SCROLL |SWT.BORDER);
		descriptionInput.setText("Description (Optional)");
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 6;
		gd.verticalSpan = 26;
		descriptionInput.setLayoutData(gd);
		descriptionInput.pack();
		Label blank = new Label(shell, SWT.NONE);
		gd = new GridData(GridData.FILL, GridData.FILL, true, true);
		gd.verticalSpan = 4;
		gd.horizontalSpan = 6;
		blank.setLayoutData(gd);
		blank.pack();
		Button save = new Button(shell, SWT.PUSH);

		save.setText("Save");
		save.addListener(SWT.Selection, new Listener()
		{
			public void handleEvent(Event event)
			{
				if(nameInput.getText().equals(""))
				{
					nameInput.setBackground(display.getSystemColor(SWT.COLOR_RED));
					return;
				}
				abilityname = nameInput.getText();
				abilityscript = descriptionInput.getText();
				LinkedHashMap<String, String> a = new LinkedHashMap<String, String>();
				a.put("NAME", abilityname);
				a.put("DESCRIPTION", abilityscript);
				newability = new AbilityEntity(a);
				Main.gameState.abilities.put(abilityname, newability);
				Main.gameState.customContent.put(abilityname, newability);
				shell.close();
			}
		}
		);
		gd = new GridData(GridData.FILL, GridData.CENTER, false, false);
		gd.horizontalSpan = 1;
		save.setLayoutData(gd);
		//save.pack();
		shell.layout();
		shell.pack();
//		final Text wizpage1text = new Text(wizpage1, SWT.BORDER);
//		wizpage1text.setBounds(50, 50, 150, 50);
//		wizpage1text.setText("Ability_NONAME");
//		Button next1 = createNextButton(wizpage1);//TODO cancel and previous button
//		createBackButton(wizpage1, wizPanel, wizLayout);
//		createCancelButton(wizpage1, wizPanel, wizLayout);
//		next1.addListener(SWT.Selection, new Listener()
//		{
//			public void handleEvent(Event event)
//			{
//				if(wizpage1text.getText() != "")
//				{
//					abilityname = wizpage1text.getText();
//					if(wizpagenum < wizPages.size() - 1)
//					{
//						wizpagenum++;
//						wizLayout.topControl = wizPages.get(wizpagenum);
//						wizPanel.layout();
//					}
//					else if(wizpagenum == wizPages.size() - 1)
//					{
//						System.out.println("PANIC: ITEM WIZARD PAGE 1 OUT");
//						shell.close();
//					}
//				}
//				else
//				{
//					wiz1Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//				}
//			}
//		}
//		);
//		wizpage1.pack();
//		wizPages.add(wizpage1);
//		//Page2 -- Description
//		final Composite wizpage2 = new Composite(wizPanel, SWT.NONE);
//		Label wiz2Label = new Label(wizpage2, SWT.NONE);
//		wiz2Label.setText("Enter Description (Optional)");
//		wiz2Label.pack(); 
//		final Text wizpage2text = new Text(wizpage2, SWT.BORDER);
//		wizpage2text.setBounds(50, 50, 300, 200);
//		wizpage2text.setText("Description here");
//		Button next2 = createNextButton(wizpage2);
//		createBackButton(wizpage2, wizPanel, wizLayout);
//		createCancelButton(wizpage2, wizPanel, wizLayout);
//		next2.addListener(SWT.Selection, new Listener()
//		{
//			public void handleEvent(Event event)
//			{
//				if(wizpage2text.getText() != "")
//				{
//					abilityscript = wizpage2text.getText();
//				}
//				else
//				{
//					abilityscript = "<empty>";
//				}
//				CreateVerificationPage(wizPanel, wizLayout);
//				if(wizpagenum < wizPages.size() - 1)
//				{
//					wizpagenum++;
//					
//					wizLayout.topControl = wizPages.get(wizpagenum);
//					wizPanel.layout();
//				}
//				else if(wizpagenum == wizPages.size() - 1)
//				{
//					shell.close();
//				}
//			}
//
//			
//		});
//		wizPages.add(wizpage2);
//		wizLayout.topControl = wizpage1;
//		wizPanel.layout();
	}
//	private void CreateVerificationPage(final Composite wizPanel,
//			final StackLayout wizLayout) {
//		if(wizPages.size() > wizpagenum + 1)
//		{
//			wizPages.remove(wizpagenum + 1);
//		}
//		final Composite verific = new Composite(wizPanel, SWT.NONE);
//		Label wiz8Label = new Label(verific, SWT.NONE);
//		wiz8Label.setText("Name: " + abilityname + "\nDescription: " + abilityscript);
//		wiz8Label.pack();
//		Button confirm = new Button(verific, SWT.PUSH);
//		createBackButton(verific, wizPanel, wizLayout);
//		createCancelButton(verific, wizPanel, wizLayout);
//		confirm.setText("Confirm");
//		confirm.setBounds(WIDTH-117, HEIGHT - 90, 100, 50);
//		confirm.addListener(SWT.Selection, new Listener()
//		{
//			public void handleEvent(Event event)
//			{
//				//TODO save the item
//				shell.close();
//			}
//		});
//		wizPages.add(verific);
//		
//	}
//	public static Button createNextButton(Composite c) {
//		Button nextButton = new Button(c, SWT.PUSH);
//		nextButton.setText("Next");
//		nextButton.setBounds(WIDTH - 117, HEIGHT - 90, 100, 50);
//		return nextButton;
//	}
//
//	/**
//	 *COPY FROM CHAR WIZARD
//	 * creates a back button on composite c in the bottom right corner.
//	 * also sets the listener for the created button that changes the top 
//	 * control page of the layout of the panel to be the previous page
//	 * @param c
//	 * @param panel
//	 * @param layout
//	 * @return
//	 */
//	public static Button createBackButton(Composite c, final Composite panel,
//			final StackLayout layout) {
//		Button backButton = new Button(c, SWT.PUSH);
//		backButton.setText("Back");
//		backButton.setBounds(WIDTH - 220, HEIGHT - 90, 100, 50);
//		backButton.addListener(SWT.Selection, new Listener() {
//
//			public void handleEvent(Event event) {
//				if (wizpagenum > 0)
//					wizpagenum--;
//				layout.topControl = wizPages.get(wizpagenum);
//				panel.layout();
//			}
//		});
//		return backButton;
//	}
//
//	/**
//	 * COPY FROM CHAR WIZARD
//	 * creates a cancel button on composite c in bottom left corner.
//	 * also sets the listener for the created button that changes the homePanel
//	 * top control to be home and resets the wizard page counter wizPageNum
//	 * @param c
//	 * @param home
//	 * @param panel
//	 * @param layout
//	 * @return
//	 */
//	public static Button createCancelButton(Composite c,
//			final Composite panel, final StackLayout layout) {
//		Button cancelButton = new Button(c, SWT.PUSH);
//		cancelButton.setText("Cancel");
//		cancelButton.setBounds(10, HEIGHT - 90, 100, 50);
//		cancelButton.addListener(SWT.Selection, new Listener() {
//			public void handleEvent(Event event) {
//				cancel = false;
//				final Shell areYouSureShell = new Shell(display);
//				areYouSureShell.setText("Cancel");
//				areYouSureShell.setSize(300, 200);
//				center(areYouSureShell);
//
//				Label areYouSure = new Label(areYouSureShell, SWT.NONE);
//				areYouSure.setLocation(40,50);
//				areYouSure.setText("Are you sure you want to cancel?");
//				areYouSure.pack();
//
//				Button yes = new Button(areYouSureShell, SWT.PUSH);
//				yes.setBounds(10,130,130,30);
//				yes.setText("Yes, Cancel");
//				yes.addListener(SWT.Selection, new Listener() {
//					public void handleEvent(Event event) {
//						cancel = true;
//						areYouSureShell.dispose();
//					}
//				});
//
//				Button no = new Button(areYouSureShell, SWT.PUSH);
//				no.setBounds(160,130,130,30);
//				no.setText("No, Don't Cancel");
//				no.addListener(SWT.Selection, new Listener() {
//					public void handleEvent(Event event) {
//						cancel = false;
//						areYouSureShell.dispose();
//					}
//				});
//
//				areYouSureShell.open();
//				while (!areYouSureShell.isDisposed()) {
//					if (!display.readAndDispatch()) {
//						display.sleep();
//					}
//				}
//				if (cancel) {
//					shell.close();
//				}
//			}
//		});
//		return cancelButton;
//	}
	/**
	 * simple getter
	 */
	public Shell getshell()
	{
		return shell;
	}
}
