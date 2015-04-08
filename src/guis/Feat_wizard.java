package guis;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import entity.FeatEntity;

/**
 * The class that handle Feat wizard interface, input and export.
 * Feature Wizard don't do random generation.
 * @author Innocentius Shellingford
 *
 */
public class Feat_wizard 
{
	private Shell shell;
	private Display display;
	private int pagenum;
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;//copy from character wizard, see for change
	private FeatEntity newfeat;
	String featname;
	String featprereq;
	String featnormal;
	String featspecial;
	String featfighter;
	String featscript;
	public Feat_wizard(Display d)
	{
		display = d;
		shell = new Shell(d);
		shell.setText("Feat Wizard");
		shell.setSize(WIDTH,HEIGHT);
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
	private void center(Shell shell) 
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
		//TODO
	}
	/**
	 * simple getter
	 */
	public Shell getshell()
	{
		return shell;
	}
}