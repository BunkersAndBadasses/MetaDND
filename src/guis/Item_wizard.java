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
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;//copy from character wizard, see for change
	private static ArrayList<Composite> wizPages;
	public static boolean cancel = false;
	private ItemEntity newitem;
	static String ItemName;
	static String ItemWeight;
	static String ItemScript;
	static String ItemValue;
	private static int wizPageNum;
	public Item_wizard(Display d)
	{
		display = d;
		shell = new Shell(d);
		shell.setText("Create New Item");
		//int width = display.getMonitors()[0].getBounds().width;
		//shell.setSize(width / 3, width * 2 / 9);
		wizPages = new ArrayList<Composite>();
		wizPageNum = 0;
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
		GridLayout gl = new GridLayout(4, true);
        gl.verticalSpacing = 5;
		shell.setLayout(gl);
		GridData gd;
		final Label wiz1Label = new Label(shell, SWT.NONE);
		wiz1Label.setText("Enter Fields");
		gd = new GridData(GridData.FILL, GridData.FILL,false, false);
		gd.horizontalSpan = 4;
		wiz1Label.setLayoutData(gd);
		wiz1Label.pack();
		//Name
		Text nameInput = new Text(shell, SWT.BORDER);
		nameInput.setMessage("Name");
		gd = new GridData(GridData.FILL, GridData.FILL, false, false);
		gd.horizontalSpan = 2;
		nameInput.setLayoutData(gd);
		nameInput.pack();
		//Weight
		Text weightInput = new Text(shell, SWT.BORDER);
		weightInput.setMessage("Weight");
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 2;
		weightInput.setLayoutData(gd);
		weightInput.pack();
		//Value
		Text valueInput = new Text(shell, SWT.BORDER);
		valueInput.setMessage("Value");
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 2;
		valueInput.setLayoutData(gd);
		valueInput.pack();
		//Description
		Text descriptionInput = new Text(shell, SWT.WRAP | SWT.V_SCROLL);
		descriptionInput.setText("Description (Optional)");
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 4;
		gd.verticalSpan = 15;
		descriptionInput.setLayoutData(gd);
		descriptionInput.pack();
		
		Label blank = new Label(shell, SWT.NONE);
		gd = new GridData(GridData.FILL, GridData.FILL, true, true);
		gd.horizontalSpan = 4;
		blank.setLayoutData(gd);
		blank.pack();
		Button save = new Button(shell, SWT.PUSH);

		save.setText("Save");
		save.addListener(SWT.Selection, new Listener()
		{
			public void handleEvent(Event event)
			{
				Boolean checkfault = false;
				if(nameInput.getText().equals(""))
				{
					checkfault = true;
					nameInput.setBackground(display.getSystemColor(SWT.COLOR_RED));
				}
				if(weightInput.getText().equals(""))
				{
					checkfault = true;
					weightInput.setBackground(display.getSystemColor(SWT.COLOR_RED));
				}
				if(valueInput.getText().equals(""))
				{
					checkfault = true;
					valueInput.setBackground(display.getSystemColor(SWT.COLOR_RED));
				}
				if(checkfault)
				{
					return;
				}
				LinkedHashMap<String, String> a = new LinkedHashMap<String, String>();
				a.put("NAME", nameInput.getText());
				a.put("DESCRIPTION", descriptionInput.getText());
				a.put("WEIGHT", weightInput.getText());
				a.put("VALUE", valueInput.getText());
				newitem = new ItemEntity(a);
				Main.gameState.abilities.put(nameInput.getText(), newitem);
				Main.gameState.customContent.put(nameInput.getText(), newitem);
				shell.close();
			}
		}
		);
		gd = new GridData(GridData.FILL, GridData.CENTER, false, false);
		gd.horizontalSpan = 1;
		save.setLayoutData(gd);
		save.pack();

		shell.layout();
		shell.pack();
//		//wizard
//		final Composite wizPanel = new Composite(shell, SWT.BORDER);
//		wizPanel.setBounds(0,0,WIDTH, HEIGHT);
//		final StackLayout wizLayout = new StackLayout();
//		wizPanel.setLayout(wizLayout);
//		
//		//Page1 -- Name
//		final Composite wizpage1 = new Composite(wizPanel, SWT.NONE);
//		wizpage1.setBounds(0,0,WIDTH,HEIGHT);
//		
//		final Label wiz1Label = new Label(wizpage1, SWT.NONE);
//		wiz1Label.setText("Enter Name (required)");
//		wiz1Label.pack();
//		final Text wizpage1text = new Text(wizpage1, SWT.BORDER);
//		wizpage1text.setBounds(50, 50, 150, 50);
//		wizpage1text.setText("Mr.NONAME");
//		Button next1 = createNextButton(wizpage1);//TODO cancel and previous button
//		Button Back1 = createBackButton(wizpage1, wizPanel, wizLayout);
//		Button Cancel1 = createCancelButton(wizpage1, wizPanel, wizLayout);
//		next1.addListener(SWT.Selection, new Listener()
//		{
//			public void handleEvent(Event event)
//			{
//				if(wizpage1text.getText() != "")
//				{
//					ItemName = wizpage1text.getText();
//					if(wizPageNum < wizPages.size() - 1)
//					{
//						wizPageNum++;
//						wizLayout.topControl = wizPages.get(wizPageNum);
//						wizPanel.layout();
//					}
//					else if(wizPageNum == wizPages.size() - 1)
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
//		
//		wizPages.add(wizpage1);
//		//Page2 -- Weight
//		final Composite wizpage2 = new Composite(wizPanel, SWT.NONE);
//		final Label wiz2Label = new Label(wizpage2, SWT.NONE);
//		wiz2Label.setText("Enter Weight (required)");
//		wiz2Label.pack();
//		final Text wizpage2text = new Text(wizpage2, SWT.BORDER);
//		wizpage2text.setBounds(50, 50, 150, 50);
//		wizpage2text.setText("1");
//		Button next2 = createNextButton(wizpage2);
//		Button Back2 = createBackButton(wizpage2, wizPanel, wizLayout);
//		Button Cancel2 = createCancelButton(wizpage2, wizPanel, wizLayout);
//		next2.addListener(SWT.Selection, new Listener()
//		{
//			public void handleEvent(Event event)
//			{
//				if(wizpage2text.getText() != "")
//				{
//					try
//					{
//					if(Integer.parseInt(wizpage2text.getText()) >= 0)
//					{
//						ItemWeight = String.valueOf(Integer.parseInt(wizpage2text.getText()));
//						if(wizPageNum < wizPages.size() - 1)
//						{
//							wizPageNum++;
//							wizLayout.topControl = wizPages.get(wizPageNum);
//							wizPanel.layout();
//						}
//						else if(wizPageNum == wizPages.size() - 1)
//						{
//							shell.close();
//						}
//					}
//					else
//					{
//						wiz2Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//					}
//					}
//					catch(NumberFormatException a)
//					{
//						wiz2Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//					}
//				}
//				else
//				{
//					wiz2Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//				}
//			}
//		});
//		wizPages.add(wizpage2);
//		//Page3 -- Value
//		final Composite wizpage3 = new Composite(wizPanel, SWT.NONE);
//		final Label wiz3Label = new Label(wizpage3, SWT.NONE);
//		wiz3Label.setText("Enter Value (required)");
//		wiz3Label.pack();
//		final Text wizpage3text = new Text(wizpage3, SWT.BORDER);
//		wizpage3text.setBounds(50, 50, 150, 50);
//		wizpage3text.setText("1");
//		Button next3 = createNextButton(wizpage3);
//		Button Back3 = createBackButton(wizpage3, wizPanel, wizLayout);
//		Button Cancel3 = createCancelButton(wizpage3, wizPanel, wizLayout);
//		next3.addListener(SWT.Selection, new Listener()
//		{
//			public void handleEvent(Event event)
//			{
//				if(wizpage3text.getText() != "")
//				{
//					ItemValue = wizpage3text.getText();
//					if(wizPageNum < wizPages.size() - 1)
//					{
//						wizPageNum++;
//						wizLayout.topControl = wizPages.get(wizPageNum);
//						wizPanel.layout();
//					}
//					else if(wizPageNum == wizPages.size() - 1)
//					{
//						shell.close();
//					}
//				}
//						
//				else
//				{
//					wiz3Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//				}
//			}
//		});
//		wizPages.add(wizpage3);
//		//Page4 -- Description (optional)
//		final Composite wizpage4 = new Composite(wizPanel, SWT.NONE);
//		Label wiz4Label = new Label(wizpage4, SWT.NONE);
//		wiz4Label.setText("Enter Description (Optional)");
//		wiz4Label.pack(); 
//		final Text wizpage4text = new Text(wizpage4, SWT.BORDER);
//		wizpage4text.setBounds(50, 50, 300, 200);
//		wizpage4text.setText("Description here");
//		Button next4 = createNextButton(wizpage4);
//		Button Back4 = createBackButton(wizpage4, wizPanel, wizLayout);
//		Button Cancel4 = createCancelButton(wizpage4, wizPanel, wizLayout);
//		next4.addListener(SWT.Selection, new Listener()
//		{
//			public void handleEvent(Event event)
//			{
//				if(wizpage4text.getText() != "")
//				{
//					ItemScript = wizpage4text.getText();
//				}
//				else
//				{
//					ItemScript = "<empty>";
//				}
//				CreateVerificationPage(wizPanel, wizLayout);
//				if(wizPageNum < wizPages.size() - 1)
//				{
//					wizPageNum++;
//					
//					wizLayout.topControl = wizPages.get(wizPageNum);
//					wizPanel.layout();
//				}
//				else if(wizPageNum == wizPages.size() - 1)
//				{
//					shell.close();
//				}
//			}
//		});
//		wizPages.add(wizpage4);
//		//Page5 -- Verification -- See CreateVerification Page
//		wizLayout.topControl = wizpage1;
//		wizPanel.layout();
	}
	/**
	 * creates a next button on composite c in the bottom right corner.
	 * this does NOT set the listener! (each one is different, that is set 
	 * after this method is called)
	 * @param c
	 * @return
	 */
	public static void CreateVerificationPage(final Composite p, final StackLayout l)
	{
		if(wizPages.size() > wizPageNum + 1)
		{
			wizPages.remove(wizPageNum + 1);
		}
		final Composite verific = new Composite(p, SWT.NONE);
		Label wiz5Label = new Label(verific, SWT.NONE);
		wiz5Label.setText("Name: " + ItemName + "\nWeight: " + ItemWeight 
				+ "\nValue: " + ItemValue + "\nDescription: " + ItemScript);
		wiz5Label.pack();
		Button confirm = new Button(verific, SWT.PUSH);
		createBackButton(verific, p, l);
		createCancelButton(verific, p, l);
		confirm.setText("Confirm");
		confirm.setBounds(WIDTH-117, HEIGHT - 90, 100, 50);
		confirm.addListener(SWT.Selection, new Listener()
		{
			public void handleEvent(Event event)
			{
				//TODO save the item
				shell.close();
			}
		});
		wizPages.add(verific);
	}
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
	public static Button createCancelButton(Composite c,
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
					shell.close();
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
