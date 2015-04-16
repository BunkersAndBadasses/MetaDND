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

public class Monster_wizard {
	private static Shell shell;
	private static Display display;
	public static boolean cancel = false;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;//copy from character wizard, see for change
	private static int wizpagenum;
	private static ArrayList<Composite> wizPages;

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
	private void createPageContent() 
	{
		//TODO
	}
	private void CreateVerificationPage(final Composite wizPanel,
			final StackLayout wizLayout) 
	{
		//TODO
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
