package guis;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*; 

public class Wizard {
	
	private Shell shell;

	public Wizard(Display d, int width, int height) {
	    
        shell = new Shell(d);
        shell.setText("CharacterWizard");
        shell.setSize(width,height);

        center(shell);

        shell.open();

        while (!shell.isDisposed()) {
          if (!d.readAndDispatch()) {
            d.sleep();
          }
        }
    }
	
	public Shell getShell() {
		return shell;
	}
	
    public void center(Shell shell) {

        Rectangle bds = shell.getDisplay().getBounds();

        Point p = shell.getSize();

        int nLeft = (bds.width - p.x) / 2;
        int nTop = (bds.height - p.y) / 2;

        shell.setBounds(nLeft, nTop, p.x, p.y);
    }

    public static void main(String[] args) {
        Display display = new Display();
        new CharacterWizard(display);
        display.dispose();
    }
}
