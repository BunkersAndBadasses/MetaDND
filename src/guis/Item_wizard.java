package guis;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import entity.FeatEntity;
import entity.ItemEntity;

/**
 * The class that handle Item wizard interface, input and export.
 * Item wizard don't handle random generation.
 * @author Innocentius Shellingford
 *
 */
public class Item_wizard {
	private static Shell shell;
	private static Display display;
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;//copy from character wizard, see for change
	private static ArrayList<Composite> wizPages;
	public static boolean cancel = false;
	public static boolean[] wizPageCreated = {false, false, false, false, false};
	private ItemEntity newitem;
	String ItemName;
	String ItemWeight;
	String ItemScript;
	String ItemValue;
	private static int wizPageNum;
	public Item_wizard(Display d)
	{
		display = d;
		shell = new Shell(d);
		shell.setText("Create New Item");
		shell.setSize(WIDTH,HEIGHT);
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
	 * To create a item, we need user input from the following:
	 * NAME
	 * WEIGHT
	 * DESCRIPTION
	 * VALUE
	 * TODO verify the storing method
	 */
	private void createPageContent() 
	{		
		//wizard
		final Composite wizard = new Composite(shell, SWT.NONE);
		final Composite wizPanel = new Composite(wizard, SWT.BORDER);
		wizPanel.setBounds(0,0,0, (int) (HEIGHT * 0.75));
		final StackLayout wizLayout = new StackLayout();
		wizPanel.setLayout(wizLayout);
		wizPanel.layout();
		//Page1 -- Name
		final Composite wizpage1 = new Composite(wizPanel, SWT.NONE);
		wizPageNum = 1;
		wizLayout.topControl = wizpage1;
		final Label wiz1Label = new Label(wizpage1, SWT.NONE);
		wiz1Label.setText("Enter Name (required)");
		wiz1Label.pack();
		final Text wizpage1text = new Text(wizpage1, SWT.BORDER);
		wizpage1text.setBounds(50, 50, 50, 50);
		wizpage1text.setText("Mr.NONAME");
		Button next1 = createNextButton(wizpage1);//TODO cancel and previous button
		next1.addListener(SWT.Selection, new Listener()
		{
			public void handleEvent(Event event)
			{
				if(wizpage1text.getText() != "")
				{
					ItemName = wizpage1text.getText();
					if(wizPageNum < wizPages.size() - 1)
					{
						wizPageNum++;
					}
					else if(wizPageNum == wizPages.size() - 1)
					{
						shell.close();
					}
				}
				else
				{
					wiz1Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
				}
			}
		}
		);
		wizPages.add(wizpage1);
		//Page2 -- Weight
		final Composite wizpage2 = new Composite(wizPanel, SWT.NONE);
		final Label wiz2Label = new Label(wizpage2, SWT.NONE);
		wiz2Label.setText("Enter Value (required)");
		wiz2Label.pack();
		final Text wizpage2text = new Text(wizpage2, SWT.BORDER);
		wizpage1text.setBounds(50, 50, 50, 50);
		wizpage1text.setText("1");
		Button next2 = createNextButton(wizpage2);//TODO cancel and previous button
		next2.addListener(SWT.Selection, new Listener()
		{
			public void handleEvent(Event event)
			{
				if(wizpage2text.getText() != "")
				{
					ItemWeight = wizpage2text.getText();
					if(wizPageNum < wizPages.size() - 1)
					{
						wizPageNum++;
					}
					else if(wizPageNum == wizPages.size() - 1)
					{
						shell.close();
					}
				}
				else
				{
					wiz2Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
				}
			}
		});
		wizPages.add(wizpage2);
		//Page3 -- Value
		final Composite wizpage3 = new Composite(wizPanel, SWT.NONE);
		final Label wiz3Label = new Label(wizpage3, SWT.NONE);
		wiz3Label.setText("Enter Value (required)");
		wiz3Label.pack();
		final Text wizpage3text = new Text(wizpage3, SWT.BORDER);
		wizpage3text.setBounds(50, 50, 50, 50);
		wizpage3text.setText("1");
		Button next3 = createNextButton(wizpage3);//TODO cancel and previous button
		next3.addListener(SWT.Selection, new Listener()
		{
			public void handleEvent(Event event)
			{
				if(wizpage3text.getText() != "")
				{
					ItemValue = wizpage3text.getText();
					if(wizPageNum < wizPages.size() - 1)
					{
						wizPageNum++;
					}
					else if(wizPageNum == wizPages.size() - 1)
					{
						shell.close();
					}
				}
				else
				{
					wiz3Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
				}
			}
		});
		wizPages.add(wizpage3);
		//Page4 -- Description (optional)
		final Composite wizpage4 = new Composite(wizPanel, SWT.NONE);
		Label wiz4Label = new Label(wizpage4, SWT.NONE);
		wiz4Label.setText("Enter Description (Optional)");
		wiz4Label.pack(); 
		final Text wizpage4text = new Text(wizpage4, SWT.BORDER);
		wizpage4text.setBounds(200, 200, 200, 200);
		wizpage4text.setText("Description here");
		Button next4 = createNextButton(wizpage4);//TODO cancel and previous button
		next4.addListener(SWT.Selection, new Listener()
		{
			public void handleEvent(Event event)
			{
				if(wizpage4text.getText() != "")
				{
					ItemScript = wizpage4text.getText();
				}
				else
				{
					ItemScript = "<empty>";
				}
				if(wizPageNum < wizPages.size() - 1)
				{
					wizPageNum++;
				}
				else if(wizPageNum == wizPages.size() - 1)
				{
					shell.close();
				}
			}
		});
		wizPages.add(wizpage4);
		//Page5 -- Verification
		//TODO lay all information entered here for confirmation
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
	 *COPY FROM CHAR WIZARD
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
	 * COPY FROM CHAR WIZARD
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
	/**
	 * simple getter
	 */
	public Shell getshell()
	{
		return shell;
	}
}
