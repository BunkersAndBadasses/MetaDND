package guis;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Image;
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
import entity.SpellEntity;

/**
 * The class that handle Spell wizard interface, input and export.
 * @author Innocentius Shellingford
 *
 */
public class SpellWizard {
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
	public Boolean desfirst;
	public SpellWizard(Display d)
	{
		if (GameState.isWindowOpen("Spell")) {
			return;
		}
		display = d;
		shell = new Shell(d);
		shell.setImage(new Image(display, "images/bnb_logo.gif"));
		shell.setText("Create new Spell");
		//int width = display.getMonitors()[0].getBounds().width;
		//shell.setSize(width / 3, width * 2 / 3);
		wizpagenum = 0;
		wizPages = new ArrayList<Composite>();
		desfirst = false;
		createPageContent();
		GameState.windowsOpen.add("Spell");
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
        GameState.windowsOpen.remove("Spell");
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
		Label resistanceLabel = new Label(shell, SWT.NONE);
		resistanceLabel.setText("Can Spell Be Resisted");
		gd = new GridData(GridData.CENTER, GridData.FILL, false, false);
		gd.horizontalSpan = 1;
		resistanceLabel.setLayoutData(gd);
		resistanceLabel.pack();
		Button resistanceInput = new Button(shell, SWT.CHECK);
		gd = new GridData(GridData.CENTER, GridData.FILL, false, false);
		gd.horizontalSpan = 1;
		resistanceInput.setLayoutData(gd);
		resistanceInput.pack();
		//level
		Text levelInput = new Text(shell, SWT.BORDER);
		levelInput.setMessage("Level");
		gd = new GridData(GridData.FILL, GridData.FILL, false, false);
		levelInput.setLayoutData(gd);
		levelInput.pack();
		//Damage
		Text damageInput = new Text(shell, SWT.BORDER);
		damageInput.setMessage("Damage");
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 1;
		damageInput.setLayoutData(gd);
		damageInput.pack();
		//DamageAlternative
		Text damagealterInput = new Text(shell, SWT.BORDER);
		damagealterInput.setMessage("Damage Alternative");
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 2;
		damagealterInput.setLayoutData(gd);
		damagealterInput.pack();
	
		//description
		
		Text descriptionInput = new Text(shell, SWT.WRAP | SWT.V_SCROLL);
		descriptionInput.setText("Description (Optional)");
		descriptionInput.addListener(SWT.Activate, new Listener(){
			public void handleEvent(Event event)
			{
				if(!desfirst)
				{
					descriptionInput.setText("");
					desfirst = true;
				}
			}
		});
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
					checkfault = true;
					nameInput.setBackground(display.getSystemColor(SWT.COLOR_RED));
				}
				if(componentInput.getText().equals(""))
				{
					checkfault = true;
					componentInput.setBackground(display.getSystemColor(SWT.COLOR_RED));
				}
				if(schoolInput.getText().equals(""))
				{
					checkfault = true;
					schoolInput.setBackground(display.getSystemColor(SWT.COLOR_RED));
				}
				
				if(castimeInput.getText().equals(""))
				{
					checkfault = true;
					castimeInput.setBackground(display.getSystemColor(SWT.COLOR_RED));
				}
				
				if(levelInput.getText().equals(""))
				{
					checkfault = true;
					levelInput.setBackground(display.getSystemColor(SWT.COLOR_RED));
				}
				if(checkfault)
				{
					return;
				}
				a.put("NAME", nameInput.getText());
				a.put("COMPONENTS", componentInput.getText());
				a.put("SCHOOL", schoolInput.getText());
				a.put("CASTINGTIME", castimeInput.getText());
				a.put("LEVEL", levelInput.getText());
				if(resistanceInput.getSelection())
				{
					a.put("SPELLRESISTANCE", "YES");
				}
				else
				{
					a.put("SPELLRESISTANCE", "NO");
				}
				if(!materialInput.getText().equals(""))
				{
					a.put("MATERIALCOMPONENT", materialInput.getText());					
				}
				if(!savthrowInput.getText().equals(""))
				{
					a.put("SAVINGTHROW", savthrowInput.getText());					
				}
				if(!focusInput.getText().equals(""))
				{
					a.put("FOCUS", focusInput.getText());					
				}
				if(!durationInput.getText().equals(""))
				{
					a.put("DURATION", durationInput.getText());
				}
				if(!damageInput.getText().equals(""))
				{
					a.put("DAMAGE", damageInput.getText());
				}
				if(!damagealterInput.getText().equals(""))
				{
					a.put("DAMAGEALTERNATE", damagealterInput.getText());
				}
				if(!rangeInput.getText().equals(""))
				{
					a.put("RANGE", rangeInput.getText());
				}
				if(!effectInput.getText().equals(""))
				{
					a.put("EFFECT", effectInput.getText());					
				}
				a.put("DESCRIPTION", descriptionInput.getText());
				newspell = new SpellEntity(a);
				Main.gameState.spells.put(newspell.getName(), newspell);
				Main.gameState.customContent.put(newspell.getName(), newspell);
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
	@SuppressWarnings("unused")
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
