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
import entity.FeatEntity;
import entity.SpellEntity;

/**
 * The class that handle Spell wizard interface, input and export.
 * @author Innocentius Shellingford
 *
 */
public class Spell_wizard {
	private static Shell shell;
	private static Display display;
	public static boolean cancel = false;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;//copy from character wizard, see for change
	private static ArrayList<Composite> wizPages;
	private static int wizpagenum;
	public static SpellEntity newspell;
	static String spellname;
	static String spellcomp;
	static String spellschool;
	static String spellrange;
	static String spelleffect;
    static String spellcastime;
	static String spellscript;
	static String spellmaterial;
	static String spellsaving;
	static String spellfocus;
	static String spellduration;
	static String spelllevel;
	static String spellresistance;
	public Spell_wizard(Display d)
	{
		display = d;
		shell = new Shell(d);
		shell.setText("Create new Spell");
		//int width = display.getMonitors()[0].getBounds().width;
		//shell.setSize(width / 3, width * 2 / 3);
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
	 * To create a spell, we need user input from the following:
	 * NAME
	 * COMPONENT
	 * SCHOOL 
	 * RANGE
	 * EFFECT
	 * CASTINGTIME
	 * DESCRIPTION
	 * MATERIALCOMPONENT
	 * SAVINGTHROW
	 * FOCUS
	 * DURATION
	 * LEVEL
	 * SPELLRESISTANCE
	 * 
	 * Change input position
	 * Take name, level, duration, range at first page
	 * Take component, school, material component, focus at second page
	 * Take casting time, effect, saving throw, spell resistance at third page
	 * Take description at last page.
	 * We get the inputs, put them into Strings, hash map it, then create a new feat... BUT where should I put it?
	 * TODO verify the storing method
	 */
	private void createPageContent() 
	{
		GridLayout gl = new GridLayout(4, true);
        gl.verticalSpacing = 10;
		
		GridData gd;
		final Label wiz1Label = new Label(shell, SWT.NONE);
		wiz1Label.setText("Enter Fields");
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 4;
		wiz1Label.setLayoutData(gd);
		wiz1Label.pack();
		Text nameInput = new Text(shell, SWT.BORDER);
		nameInput.setMessage("Name");
		gd = new GridData(GridData.FILL, GridData.FILL, false, false);
		gd.horizontalSpan = 2;
		nameInput.setLayoutData(gd);
		nameInput.pack();
		//Component
		Text componentInput = new Text(shell, SWT.BORDER);
		componentInput.setMessage("Component");
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 2;
		componentInput.setLayoutData(gd);
		componentInput.pack();
		//school
		Text schoolInput = new Text(shell, SWT.BORDER);
		schoolInput.setMessage("School");
		gd = new GridData(GridData.FILL, GridData.FILL, false, false);
		gd.horizontalSpan = 2;
		schoolInput.setLayoutData(gd);
		schoolInput.pack();
		//range
		Text rangeInput = new Text(shell, SWT.BORDER);
		rangeInput.setMessage("Range");
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 2;
		rangeInput.setLayoutData(gd);
		rangeInput.pack();
		//Effect
		Text effectInput = new Text(shell, SWT.BORDER);
		effectInput.setMessage("Effect");
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 4;
		effectInput.setLayoutData(gd);
		effectInput.pack();
		//castingtime
		Text castimeInput = new Text(shell, SWT.BORDER);
		castimeInput.setMessage("Casting Time");
		gd = new GridData(GridData.FILL, GridData.FILL, false, false);
		gd.horizontalSpan = 2;
		castimeInput.setLayoutData(gd);
		castimeInput.pack();
		//materialcomponent
		Text materialInput = new Text(shell, SWT.BORDER);
		materialInput.setMessage("Material Component");
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 2;
		materialInput.setLayoutData(gd);
		materialInput.pack();
		//Savingthrow
		Text savthrowInput = new Text(shell, SWT.BORDER);
		savthrowInput.setMessage("Saving Throw");
		gd = new GridData(GridData.FILL, GridData.FILL, false, false);
		gd.horizontalSpan = 2;
		savthrowInput.setLayoutData(gd);
		savthrowInput.pack();
		//Focus
		Text focusInput = new Text(shell, SWT.BORDER);
		focusInput.setMessage("Focus");
		gd = new GridData(GridData.FILL, GridData.FILL, false, false);
		gd.horizontalSpan = 2;
		focusInput.setLayoutData(gd);
		focusInput.pack();
		//Duration
		Text durationInput = new Text(shell, SWT.BORDER);
		durationInput.setMessage("Duration");
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 2;
		durationInput.setLayoutData(gd);
		durationInput.pack();
		//spellresistance
		Text resistanceInput = new Text(shell, SWT.BORDER);
		resistanceInput.setMessage("Spell Resistance");
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 3;
		resistanceInput.setLayoutData(gd);
		resistanceInput.pack();
		//level
				Text levelInput = new Text(shell, SWT.BORDER);
				levelInput.setMessage("Level");
				gd = new GridData(GridData.FILL, GridData.FILL, false, false);
				levelInput.setLayoutData(gd);
				levelInput.pack();
		//description
		
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
				shell.close();
			}
		}
		);
		gd = new GridData(GridData.FILL, GridData.CENTER, false, false);
		gd.horizontalSpan = 1;
		save.setLayoutData(gd);
		save.pack();
		shell.setLayout(gl);
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
//				wizpage1text.setText("FireBall");
//				Button next1 = createNextButton(wizpage1);//TODO cancel and previous button
//				createBackButton(wizpage1, wizPanel, wizLayout);
//				createCancelButton(wizpage1, wizPanel, wizLayout);
//				next1.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage1text.getText() != "")
//						{
//							spellname = wizpage1text.getText();
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
//				//Page2 -- component
//				final Composite wizpage2 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz2Label = new Label(wizpage2, SWT.NONE);
//				wiz2Label.setText("Enter Component: (required)");
//				wiz2Label.pack();
//				final Text wizpage2text = new Text(wizpage2, SWT.BORDER);
//				wizpage2text.setBounds(50, 50, 150, 50);
//				wizpage2text.setText("Fire");
//				Button next2 = createNextButton(wizpage2);
//				createBackButton(wizpage2, wizPanel, wizLayout);
//				createCancelButton(wizpage2, wizPanel, wizLayout);
//				next2.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage2text.getText() != "")
//						{
//								spellcomp = wizpage2text.getText();
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
//						}
//						else
//						{
//							wiz2Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//						}
//								
//
//
//					}
//				});
//				wizPages.add(wizpage2);
//				//Page3 -- School
//				final Composite wizpage3 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz3Label = new Label(wizpage3, SWT.NONE);
//				wiz3Label.setText("Enter School: (required)");
//				wiz3Label.pack();
//				final Text wizpage3text = new Text(wizpage3, SWT.BORDER);
//				wizpage3text.setBounds(50, 50, 150, 50);
//				wizpage3text.setText("Descruction");
//				Button next3 = createNextButton(wizpage3);
//				createBackButton(wizpage3, wizPanel, wizLayout);
//				createCancelButton(wizpage3, wizPanel, wizLayout);
//				next3.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage3text.getText() != "")
//						{
//							spellschool = wizpage3text.getText();
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
//				//Page4 -- Range
//				final Composite wizpage4 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz4Label = new Label(wizpage4, SWT.NONE);
//				wiz4Label.setText("Enter Range: (required)");
//				wiz4Label.pack();
//				final Text wizpage4text = new Text(wizpage4, SWT.BORDER);
//				wizpage4text.setBounds(50, 50, 150, 50);
//				wizpage4text.setText("50 feet");
//				Button next4 = createNextButton(wizpage4);
//				createBackButton(wizpage4, wizPanel, wizLayout);
//				createCancelButton(wizpage4, wizPanel, wizLayout);
//				next4.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage4text.getText() != "")
//						{
//							spellrange = wizpage4text.getText();
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
//							wiz4Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//						}
//					}
//				});
//				wizPages.add(wizpage4);
//				//Page5 -- effect
//				final Composite wizpage5 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz5Label = new Label(wizpage5, SWT.NONE);
//				wiz5Label.setText("Enter Effect: (required)");
//				wiz5Label.pack();
//				final Text wizpage5text = new Text(wizpage5, SWT.BORDER);
//				wizpage5text.setBounds(50, 50, 250, 150);
//				wizpage5text.setText("No effect");
//				Button next5 = createNextButton(wizpage5);
//				createBackButton(wizpage5, wizPanel, wizLayout);
//				createCancelButton(wizpage5, wizPanel, wizLayout);
//				next5.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage5text.getText() != "")
//						{
//							spelleffect = wizpage5text.getText();
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
//							wiz5Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//						}
//					}
//				});
//				wizPages.add(wizpage5);
//				//Page6 -- casting time
//				final Composite wizpage6 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz6Label = new Label(wizpage6, SWT.NONE);
//				wiz6Label.setText("Enter Casting Time: (required)");
//				wiz6Label.pack();
//				final Text wizpage6text = new Text(wizpage6, SWT.BORDER);
//				wizpage6text.setBounds(50, 50, 150, 50);
//				wizpage6text.setText("1 turn");
//				Button next6 = createNextButton(wizpage6);
//				createBackButton(wizpage6, wizPanel, wizLayout);
//				createCancelButton(wizpage6, wizPanel, wizLayout);
//				next6.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage5text.getText() != "")
//						{
//							spellcastime = wizpage6text.getText();
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
//							wiz6Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//						}
//					}
//				});
//				wizPages.add(wizpage6);
//				//Page7 -- Material Component
//				final Composite wizpage7 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz7Label = new Label(wizpage7, SWT.NONE);
//				wiz7Label.setText("Enter Material Component: (required)");
//				wiz7Label.pack();
//				final Text wizpage7text = new Text(wizpage7, SWT.BORDER);
//				wizpage7text.setBounds(50, 50, 150, 50);
//				wizpage7text.setText("None");
//				Button next7 = createNextButton(wizpage7);
//				createBackButton(wizpage7, wizPanel, wizLayout);
//				createCancelButton(wizpage7, wizPanel, wizLayout);
//				next7.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage7text.getText() != "")
//						{
//								spellmaterial = wizpage7text.getText();
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
//						}
//						else
//						{
//							wiz7Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//						}
//								
//
//
//					}
//				});
//				wizPages.add(wizpage7);
//				//Page8 -- saving throw
//				final Composite wizpage8 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz8Label = new Label(wizpage8, SWT.NONE);
//				wiz8Label.setText("Enter Saving Throw: (required)");
//				wiz8Label.pack();
//				final Text wizpage8text = new Text(wizpage8, SWT.BORDER);
//				wizpage8text.setBounds(50, 50, 200, 100);
//				wizpage8text.setText("Will negate XX");
//				Button next8 = createNextButton(wizpage8);
//				createBackButton(wizpage8, wizPanel, wizLayout);
//				createCancelButton(wizpage8, wizPanel, wizLayout);
//				next8.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage8text.getText() != "")
//						{
//								spellsaving = wizpage8text.getText();
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
//						}
//						else
//						{
//							wiz8Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//						}
//								
//
//
//					}
//				});
//				wizPages.add(wizpage8);
//				//Page9 -- Focus
//				final Composite wizpage9 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz9Label = new Label(wizpage9, SWT.NONE);
//				wiz9Label.setText("Enter Focus: (required)");
//				wiz9Label.pack();
//				final Text wizpage9text = new Text(wizpage9, SWT.BORDER);
//				wizpage9text.setBounds(50, 50, 200, 100);
//				wizpage9text.setText("A dart");
//				Button next9 = createNextButton(wizpage9);
//				createBackButton(wizpage9, wizPanel, wizLayout);
//				createCancelButton(wizpage9, wizPanel, wizLayout);
//				next9.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage9text.getText() != "")
//						{
//								spellfocus = wizpage9text.getText();
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
//						}
//						else
//						{
//							wiz9Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//						}
//								
//
//
//					}
//				});
//				wizPages.add(wizpage9);
//				//Page10 -- Duration
//				final Composite wizpage10 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz10Label = new Label(wizpage10, SWT.NONE);
//				wiz10Label.setText("Enter Duration: (required)");
//				wiz10Label.pack();
//				final Text wizpage10text = new Text(wizpage10, SWT.BORDER);
//				wizpage10text.setBounds(50, 50, 150, 50);
//				wizpage10text.setText("5 Turns");
//				Button next10 = createNextButton(wizpage10);
//				createBackButton(wizpage10, wizPanel, wizLayout);
//				createCancelButton(wizpage10, wizPanel, wizLayout);
//				next10.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage10text.getText() != "")
//						{
//								spellduration = wizpage10text.getText();
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
//						}
//						else
//						{
//							wiz10Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//						}
//								
//
//
//					}
//				});
//				wizPages.add(wizpage10);
//				//Page11 -- Level
//				final Composite wizpage11 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz11Label = new Label(wizpage11, SWT.NONE);
//				wiz11Label.setText("Enter Level (required)");
//				wiz11Label.pack();
//				final Text wizpage11text = new Text(wizpage11, SWT.BORDER);
//				wizpage11text.setBounds(50, 50, 150, 50);
//				wizpage11text.setText("1");
//				Button next11 = createNextButton(wizpage11);
//				createBackButton(wizpage11, wizPanel, wizLayout);
//				createCancelButton(wizpage11, wizPanel, wizLayout);
//				next11.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage11text.getText() != "")
//						{
//							try
//							{
//								if(Integer.parseInt(wizpage11text.getText()) >= 0)
//								{
//							spelllevel = String.valueOf(Integer.parseInt(wizpage11text.getText()));
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
//								}
//								else
//								{
//									wiz11Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//								}
//							}
//							catch(NumberFormatException a)
//							{
//								wiz11Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//							}
//						}
//						else
//						{
//							wiz11Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//						}
//					}
//				});
//				wizPages.add(wizpage11);
//				//Page12 -- resistance
//				final Composite wizpage12 = new Composite(wizPanel, SWT.NONE);
//				final Label wiz12Label = new Label(wizpage12, SWT.NONE);
//				wiz12Label.setText("Enter Spell resistance: (Yes/No)");
//				wiz12Label.pack();
//				final Text wizpage12text = new Text(wizpage12, SWT.BORDER);
//				wizpage12text.setBounds(50, 50, 150, 50);
//				wizpage12text.setText("Yes");
//				Button next12 = createNextButton(wizpage12);
//				createBackButton(wizpage12, wizPanel, wizLayout);
//				createCancelButton(wizpage12, wizPanel, wizLayout);
//				next12.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage12text.getText() != "")
//						{
//								spellresistance = wizpage12text.getText();
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
//						}
//						else
//						{
//							wiz12Label.setBackground(display.getSystemColor(SWT.COLOR_RED));
//						}
//								
//
//
//					}
//				});
//				wizPages.add(wizpage12);
//				//Page13 - Description
//				final Composite wizpage13 = new Composite(wizPanel, SWT.NONE);
//				Label wiz13Label = new Label(wizpage13, SWT.NONE);
//				wiz13Label.setText("Enter Description (Optional)");
//				wiz13Label.pack(); 
//				final Text wizpage13text = new Text(wizpage13, SWT.BORDER);
//				wizpage13text.setBounds(50, 50, 300, 200);
//				wizpage13text.setText("Description here");
//				Button next13 = createNextButton(wizpage13);
//				createBackButton(wizpage13, wizPanel, wizLayout);
//				createCancelButton(wizpage13, wizPanel, wizLayout);
//				next13.addListener(SWT.Selection, new Listener()
//				{
//					public void handleEvent(Event event)
//					{
//						if(wizpage13text.getText() != "")
//						{
//							spellscript = wizpage13text.getText();
//						}
//						else
//						{
//							spellscript = "<empty>";
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
//				wizPages.add(wizpage13);
//				
//				wizLayout.topControl = wizpage1;
//				wizPanel.layout();
	}
	/**
	 * 	static String spellname;
	static String spellcomp;
	static String spellschool;
	static String spellrange;
	static String spelleffect;
    static String spellcastime;
	static String spellscript;
	static String spellmaterial;
	static String spellsaving;
	static String spellfocus;
	static String spellduration;
	static String spelllevel;
	static String spellresistance;
	 * @param wizPanel
	 * @param wizLayout
	 */
	private void CreateVerificationPage(final Composite wizPanel,
			final StackLayout wizLayout) {
		if(wizPages.size() > wizpagenum + 1)
		{
			wizPages.remove(wizpagenum + 1);
		}
		final Composite verific = new Composite(wizPanel, SWT.NONE);
		Label wiz14Label = new Label(verific, SWT.NONE);
		wiz14Label.setText("Name: " + spellname +"\nComponent: "+ spellcomp
				+"\nSchool: " + spellschool + "\nRange: " + spellrange + 
				"\nEffect: " + spelleffect + "\nCasting Time: " +
				spellcastime + "\nMaterial Component: " + spellmaterial + 
				"\nSaving Throw: " + spellsaving +"\nFocus: " + spellfocus + 
				"\nDuration: " + spellduration + "\nLevel: " + spelllevel + 
				"\nSpell Resistance: " + spellresistance + "\nDescription: " + spellscript);
		wiz14Label.pack();
		Button confirm = new Button(verific, SWT.PUSH);
		createBackButton(verific, wizPanel, wizLayout);
		createCancelButton(verific, wizPanel, wizLayout);
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
	public Shell getshell()
	{
		return shell;
	}
}
