package guis;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import entity.AbilityEntity;
/**
 * Class for ability_wizard, handle input and output
 * @author Innocentius
 *
 */
public class Ability_wizard {
	private Shell shell;
	private Display display;
	private int pagenum;
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;//copy from character wizard, see for change
	private AbilityEntity newability; //TODO create ability entity
	public Ability_wizard(Display d)
	{
		display = d;
		shell = new Shell(d);
		shell.setText("Create New Ability");
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
