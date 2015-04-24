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

import core.GameState;
import core.Main;
import entity.FeatEntity;

/**
 * The class that handle Feat wizard interface, input and export.
 * Feature Wizard don't do random generation.
 * @author Innocentius Shellingford
 *
 */
public class FeatWizard 
{
	private static Shell shell;
	private static Display display;
	public static boolean cancel = false;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;//copy from character wizard, see for change
	private static ArrayList<Composite> wizPages;
	private static int wizpagenum;
	public static FeatEntity newfeat;
	static String featname;
	static String featprereq;
	static String featnormal;
	static String featspecial;
	static String featbenefit;
	static String featfighter;
	static String featscript;
	public FeatWizard(Display d)
	{
		if (GameState.isWizardOpen("Feat")) {
			return;
		}
		display = d;
		shell = new Shell(d);
		shell.setText("Create new Feat");
		//int width = display.getMonitors()[0].getBounds().width;
		//shell.setSize(width / 3, width * 2 / 9);
		wizpagenum = 0;
		wizPages = new ArrayList<Composite>();
		createPageContent();
		GameState.wizardsOpen.add("Feat");
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
        GameState.wizardsOpen.remove("Feat");
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
	 * To create a feat, we need user input from the following:
	 * NAME
	 * PREREQUISITE
	 * NORMAL 
	 * SPECIAL
	 * BENEFIT
	 * FIGHTERBONUS
	 * DESCRIPTION
	 * 
	 * We get input of name, prerequisite on the same page.
	 * Then second on normal, special, benefit, fighter bonus.
	 * Description at the third.
	 * We get the inputs, put them into Strings, hash map it, then create a new feat... BUT where should I put it?
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
		Text nameInput = new Text(shell, SWT.BORDER);
		nameInput.setMessage("Name");
		gd = new GridData(GridData.FILL, GridData.FILL, false, false);
		gd.horizontalSpan = 2;
		nameInput.setLayoutData(gd);
		nameInput.pack();
		Text typeInput = new Text(shell, SWT.BORDER);
		typeInput.setMessage("Type");
		gd = new GridData(GridData.FILL, GridData.FILL, false, false);
		gd.horizontalSpan = 2;
		typeInput.setLayoutData(gd);
		typeInput.pack();
		Text prereqInput = new Text(shell, SWT.BORDER);
		prereqInput.setMessage("Prerequisite");
		gd = new GridData(GridData.FILL, GridData.FILL, false, false);
		gd.horizontalSpan = 2;
		prereqInput.setLayoutData(gd);
		prereqInput.pack();
		
		Text normalInput = new Text(shell, SWT.BORDER);
		normalInput.setMessage("Normal");
		gd = new GridData(GridData.FILL, GridData.FILL, false, false);
		gd.horizontalSpan = 2;
		normalInput.setLayoutData(gd);
		normalInput.pack();
		
		Text specialInput = new Text(shell, SWT.BORDER);
		specialInput.setMessage("Special");
		gd = new GridData(GridData.FILL, GridData.FILL, false, false);
		gd.horizontalSpan = 2;
		specialInput.setLayoutData(gd);
		specialInput.pack();
		
		//Benefit
		Text benefitInput = new Text(shell, SWT.WRAP | SWT.BORDER);
		benefitInput.setText("Benefit");
		gd = new GridData(GridData.FILL, GridData.FILL, false, false);
		gd.horizontalSpan = 3;
		gd.verticalSpan = 5;
		benefitInput.setLayoutData(gd);
		benefitInput.pack();
	    //Fighter bonus
		Label FighterLabel = new Label(shell, SWT.NONE);
		FighterLabel.setText("Fighter Bonus");
		gd = new GridData(GridData.FILL, GridData.FILL, false, false);
		gd.horizontalSpan = 1;
		FighterLabel.setLayoutData(gd);
		FighterLabel.pack();
		Button FighterInput = new Button(shell, SWT.CHECK);
		gd = new GridData(GridData.CENTER, GridData.FILL, true, false);
		gd.horizontalSpan = 1;
		FighterInput.setLayoutData(gd);
		FighterInput.pack();
		//Description
		Text descriptionInput = new Text(shell, SWT.WRAP | SWT.V_SCROLL|SWT.BORDER);
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
				LinkedHashMap<String, String> a = new LinkedHashMap<String, String>();
				if(nameInput.getText().equals(""))
				{
					nameInput.setBackground(display.getSystemColor(SWT.COLOR_RED));
					checkfault = true;
				}
				if(typeInput.getText().equals(""))
				{
					typeInput.setBackground(display.getSystemColor(SWT.COLOR_RED));
					checkfault = true;
				}
				if(benefitInput.getText().equals(""))
				{
					benefitInput.setBackground(display.getSystemColor(SWT.COLOR_RED));
					checkfault = true;
				}
				if(checkfault)
				{
					return;
				}
				a.put("NAME", nameInput.getText());
				a.put("TYPE", typeInput.getText());
				a.put("BENEFIT", benefitInput.getText());
				if(!prereqInput.getText().equals(""))
				{
					a.put("PREREQUISITES", prereqInput.getText());
				}
				if(!normalInput.getText().equals(""))
				{
					a.put("NORMAL", normalInput.getText());
				}
				if(!specialInput.getText().equals(""))
				{
					a.put("SPECIAL", specialInput.getText());
				}
				
				
				if(FighterInput.getSelection())
				{
					featfighter = "Yes";
					a.put("FIGHTERBONUS", featfighter);
				}
				featname = nameInput.getText();
				a.put("DESCRIPTION", descriptionInput.getText());
				newfeat = new FeatEntity(a);
				Main.gameState.abilities.put(featname, newfeat);
				Main.gameState.customContent.put(featname, newfeat);
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
//				final Composite wizPanel = new Composite(shell, SWT.BORDER);
//				wizPanel.setBounds(0,0,WIDTH, HEIGHT);
//				final StackLayout wizLayout = new StackLayout();
//				wizPanel.setLayout(wizLayout);
//				
//				//Page1 -- Name
//				final Composite wizpage1 = new Composite(wizPanel, SWT.NONE);
//				wizpage1.setBounds(0,0,WIDTH,HEIGHT);
//				
//				final Label wiz1Label = new Label(wizpage1, SWT.NONE);
//				wiz1Label.setText("Enter Name (required)");
//				wiz1Label.pack();
//				final Text wizpage1text = new Text(wizpage1, SWT.BORDER);
//				wizpage1text.setBounds(50, 50, 150, 50);
//				wizpage1text.setText("A Normal Human");
//				Button next1 = createNextButton(wizpage1);//TODO cancel and previous button
//				createBackButton(wizpage1, wizPanel, wizLayout);
//				createCancelButton(wizpage1, wizPanel, wizLayout);
//				next1.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage1text.getText() != "")
//						{
//							featname = wizpage1text.getText();
//							if(wizpagenum < wizPages.size() - 1)
//							{
//								wizpagenum++;
//								wizLayout.topControl = wizPages.get(wizpagenum);
//								wizPanel.layout();
//							}
//							else if(wizpagenum == wizPages.size() - 1)
//							{
//								System.out.println("PANIC: ITEM WIZARD PAGE 1 OUT");
//								shell.close();
//							}
//						}
//						else
//						{
//							wiz1Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//						}
//					}
//				}
//				);
//				
//				wizPages.add(wizpage1);
//				//Page2 -- prerequisite
//				final Composite wizpage2 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz2Label = new Label(wizpage2, SWT.NONE);
//				wiz2Label.setText("Enter Prerequisite: (optional)");
//				wiz2Label.pack();
//				final Text wizpage2text = new Text(wizpage2, SWT.BORDER);
//				wizpage2text.setBounds(50, 50, 250, 150);
//				wizpage2text.setText("Prerequisite Here");
//				Button next2 = createNextButton(wizpage2);
//				createBackButton(wizpage2, wizPanel, wizLayout);
//				createCancelButton(wizpage2, wizPanel, wizLayout);
//				next2.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage2text.getText() != "")
//						{
//								featprereq = wizpage2text.getText();
//						}
//						else
//						{
//							featprereq = "<empty>";
//						}
//								if(wizpagenum < wizPages.size() - 1)
//								{
//									wizpagenum++;
//									wizLayout.topControl = wizPages.get(wizpagenum);
//									wizPanel.layout();
//								}
//								else if(wizpagenum == wizPages.size() - 1)
//								{
//									shell.close();
//								}
//
//
//					}
//				});
//				wizPages.add(wizpage2);
//				//Page3 -- Normal
//				final Composite wizpage3 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz3Label = new Label(wizpage3, SWT.NONE);
//				wiz3Label.setText("Enter Normal: (required)");
//				wiz3Label.pack();
//				final Text wizpage3text = new Text(wizpage3, SWT.BORDER);
//				wizpage3text.setBounds(50, 50, 200, 50);
//				wizpage3text.setText("No effect");
//				Button next3 = createNextButton(wizpage3);
//				createBackButton(wizpage3, wizPanel, wizLayout);
//				createCancelButton(wizpage3, wizPanel, wizLayout);
//				next3.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage3text.getText() != "")
//						{
//							featnormal = wizpage3text.getText();
//							if(wizpagenum < wizPages.size() - 1)
//							{
//								wizpagenum++;
//								wizLayout.topControl = wizPages.get(wizpagenum);
//								wizPanel.layout();
//							}
//							else if(wizpagenum == wizPages.size() - 1)
//							{
//								shell.close();
//							}
//						}
//						else
//						{
//							wiz3Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//						}
//					}
//				});
//				wizPages.add(wizpage3);
//				//Page4 -- Specials
//				final Composite wizpage4 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz4Label = new Label(wizpage4, SWT.NONE);
//				wiz4Label.setText("Enter Special: (required)");
//				wiz4Label.pack(); 
//				final Text wizpage4text = new Text(wizpage4, SWT.BORDER);
//				wizpage4text.setBounds(50, 50, 150, 50);
//				wizpage4text.setText("No effect");
//				Button next4 = createNextButton(wizpage4);
//				createBackButton(wizpage4, wizPanel, wizLayout);
//				createCancelButton(wizpage4, wizPanel, wizLayout);
//				next4.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage4text.getText() != "")
//						{
//							featspecial = wizpage4text.getText();
//						if(wizpagenum < wizPages.size() - 1)
//						{
//							wizpagenum++;
//							
//							wizLayout.topControl = wizPages.get(wizpagenum);
//							wizPanel.layout();
//						}
//						else if(wizpagenum == wizPages.size() - 1)
//						{
//							shell.close();
//						}
//						}
//						else
//						{
//							wiz4Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//						}
//					}
//				});
//				wizPages.add(wizpage4);
//				//Page5 -- Benefit
//				final Composite wizpage5 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz5Label = new Label(wizpage5, SWT.NONE);
//				final Text wizpage5text = new Text(wizpage5, SWT.BORDER);
//				wizpage5text.setBounds(50, 50, 150, 50);
//				wizpage5text.setText("No effect");
//				Button next5 = createNextButton(wizpage5);
//				createBackButton(wizpage5, wizPanel, wizLayout);
//				createCancelButton(wizpage5, wizPanel, wizLayout);
//				wiz5Label.setText("Enter Benefit: (required)");
//				wiz5Label.pack(); 
//				next5.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage5text.getText() != "")
//						{
//							featbenefit = wizpage5text.getText();
//						if(wizpagenum < wizPages.size() - 1)
//						{
//							wizpagenum++;
//							
//							wizLayout.topControl = wizPages.get(wizpagenum);
//							wizPanel.layout();
//						}
//						else if(wizpagenum == wizPages.size() - 1)
//						{
//							shell.close();
//						}
//						}
//						else
//						{
//							wiz5Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//						}
//					}
//				});
//				wizPages.add(wizpage5);
//				//Page6 -- Fighter Bonus or not
//				final Composite wizpage6 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz6Label = new Label(wizpage6, SWT.NONE);
//				final Text wizpage6text = new Text(wizpage6, SWT.BORDER);
//				wizpage6text.setBounds(50, 50, 150, 50);
//				wizpage6text.setText("Yes");
//				Button next6 = createNextButton(wizpage6);
//				createBackButton(wizpage6, wizPanel, wizLayout);
//				createCancelButton(wizpage6, wizPanel, wizLayout);
//				wiz6Label.setText("Enter If Fighter Bonus: (Yes/No)");
//				wiz6Label.pack(); 
//				next6.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage6text.getText() != "")
//						{
//							featfighter = wizpage6text.getText();
//						if(wizpagenum < wizPages.size() - 1)
//						{
//							wizpagenum++;
//							
//							wizLayout.topControl = wizPages.get(wizpagenum);
//							wizPanel.layout();
//						}
//						else if(wizpagenum == wizPages.size() - 1)
//						{
//							shell.close();
//						}
//						}
//						else
//						{
//							wiz6Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//						}
//					}
//				});
//				wizPages.add(wizpage6);
//				//Page7 -- Description
//				final Composite wizpage7 = new Composite(wizPanel, SWT.NONE);
//				Label wiz7Label = new Label(wizpage7, SWT.NONE);
//				wiz7Label.setText("Enter Description (Optional)");
//				wiz7Label.pack(); 
//				final Text wizpage7text = new Text(wizpage7, SWT.BORDER);
//				wizpage7text.setBounds(50, 50, 300, 200);
//				wizpage7text.setText("Description here");
//				Button next7 = createNextButton(wizpage7);
//				createBackButton(wizpage7, wizPanel, wizLayout);
//				createCancelButton(wizpage7, wizPanel, wizLayout);
//				next7.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage7text.getText() != "")
//						{
//							featscript = wizpage7text.getText();
//						}
//						else
//						{
//							featscript = "<empty>";
//						}
//						CreateVerificationPage(wizPanel, wizLayout);
//						if(wizpagenum < wizPages.size() - 1)
//						{
//							wizpagenum++;
//							
//							wizLayout.topControl = wizPages.get(wizpagenum);
//							wizPanel.layout();
//						}
//						else if(wizpagenum == wizPages.size() - 1)
//						{
//							shell.close();
//						}
//					}
//
//					
//				});
//				wizPages.add(wizpage7);
//				wizLayout.topControl = wizpage1;
//				wizPanel.layout();
//	}
//	private void CreateVerificationPage(final Composite wizPanel,
//			final StackLayout wizLayout) {
//		if(wizPages.size() > wizpagenum + 1)
//		{
//			wizPages.remove(wizpagenum + 1);
//		}
//		final Composite verific = new Composite(wizPanel, SWT.NONE);
//		Label wiz8Label = new Label(verific, SWT.NONE);
//		wiz8Label.setText("Name: " + featname + "\nPrerequisite: " + featprereq 
//				+ "\nNormal: " + featnormal + "\nSpecial: " + featspecial +
//				"\nBenefit: " + featbenefit + "\nFighter Bonus: " + featfighter
//				+ "\nDescription: " + featscript);
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
				if (wizpagenum > 0)
					wizpagenum--;
				layout.topControl = wizPages.get(wizpagenum);
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