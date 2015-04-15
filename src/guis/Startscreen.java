package guis;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Startscreen {
	private static Shell shell;
	private static Display display;
	public static boolean cancel = false;
	private static final int WIDTH = 720;
	private static final int HEIGHT = 350;
	public Startscreen(Display d)
	{
		display = d;
		shell = new Shell(d);
		shell.setText("Welcome!");
		shell.setSize(WIDTH,HEIGHT);
		createPageContent();
		run();
	}
	private void run() {
		center(shell);

        shell.open();

        while (!shell.isDisposed()) {
          if (!display.readAndDispatch()) {
            display.sleep();
          }
        }
		
	}
	private static void center(Shell shell) 
	{

        Rectangle bds = shell.getDisplay().getBounds();

        Point p = shell.getSize();

        int nLeft = (bds.width - p.x) / 2;
        int nTop = (bds.height - p.y) / 2;

        shell.setBounds(nLeft, nTop, p.x, p.y);
    }
	private void createPageContent() {
		Image bb = new Image(display, ".//images//bnb_logo.gif");
		Label a = new Label(shell, SWT.BORDER);
		a.setText("MetaDND by Bunkers and BadAsses "
				+ " ver 0.2.23"+"\nDeveloper Only!");
		a.pack();
		Label b = new Label(shell, SWT.PUSH);
		b.setImage(bb);
		b.pack();
		shell.pack();
	}
}
