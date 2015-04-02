import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

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
	private Feat newfeat;
	public Feat_wizard(Display d)
	{
		display = d;
		shell = new Shell(d);
		shell.setText("Feat Wizard");
		shell.setSize(WIDTH,HEIGHT);
		newfeat = new Feat();
	}
	public void run()
	{
		center(shell);

		setBackgroundColor();
		
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
	 * From Character Wizard.
	 */
	private void setBackgroundColor() 
	{
	    Color red = display.getSystemColor(SWT.COLOR_RED);
	    
	    //shell.setLayout(new FillLayout());

	    shell.setBackground(red);
	}
	private void createPageContent() 
	{
		
	}
	public Shell getshell()
	{
		return shell;
	}
}