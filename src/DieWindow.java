import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

/*
 * The class for the Die Window.
 * @author Innocentius Shellingford
 */
public class DieWindow {
	private Shell shell;
	private Label label;
	private Text text;
	private Button ok;
	public DieWindow(Display display)
	{
		shell = new Shell(display);
		shell.setText("Die Roller");
		label = new Label(shell, SWT.NONE);
		label.setText("TEXT");
		text = new Text (shell, SWT.BORDER);
		text.setLayoutData (new RowData (100, SWT.DEFAULT));
		ok = new Button (shell, SWT.PUSH);
		ok.setText ("OK");
		shell.setDefaultButton (ok);
		shell.setLayout (new RowLayout ());
		shell.pack ();
		shell.open();
	}
}
