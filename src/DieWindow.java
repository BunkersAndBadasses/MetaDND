import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/*
 * The class for the Die Window.
 * @author Innocentius Shellingford
 */
public class DieWindow {
	private Shell shell;
	public DieWindow(Display display)
	{
		shell = new Shell(display);
		shell.setText("Die Roller");
		shell.open();
		shell.setLayout(new FillLayout());
	}
}
