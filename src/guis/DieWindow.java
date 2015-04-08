package guis;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
	private Button roll_d20;
	public DieWindow(Display display)
	{
		shell = new Shell(display);
		shell.setText("Die Roller");
		label = new Label(shell, SWT.NONE);
		label.setText("D20");
		text = new Text (shell, SWT.BORDER);
		text.setLayoutData (new RowData (100, SWT.DEFAULT));
		roll_d20 = new Button (shell, SWT.PUSH);
		roll_d20.setText ("Roll");
		roll_d20.addSelectionListener(new SelectionAdapter() { 
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Replace it to die roller function
				Double d = Math.random() * 20 + 1;
				int c = d.intValue();
				text.setText(String.valueOf(c));
			}	
		});
		shell.setDefaultButton (roll_d20);
		shell.setLayout (new RowLayout ());
		shell.pack ();
		shell.open();
	}
	public Shell getshell()
	{
		return shell;
	}
}
