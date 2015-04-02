package guis;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import entity.FeatEntity;
import entity.ItemEntity;

/**
 * The class that handle Item wizard interface, input and export.
 * Item wizard don't handle random generation.
 * @author Innocentius Shellingford
 *
 */
public class Item_wizard {
	private Shell shell;
	private Display display;
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;//copy from character wizard, see for change
	private ItemEntity newitem;
	public Item_wizard(Display d)
	{
		display = d;
		shell = new Shell(d);
		shell.setText("Item Wizard");
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
	 * To create a item, we need user input from the following:
	 * NAME
	 * LEVEL
	 * WEIGHT
	 * DESCRIPTION
	 * RESTRICTIONS
	 * Take Name, Level, Weight on the same page
	 * Take description and restriction on another.
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
