package guis;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Notepad {

	private Composite npComposite;
	private Text textbox;
	private Button closeButton;

	public Notepad() {
		int width, height;
		Display display = Display.getCurrent();
		width = display.getBounds().width / 3;
		height = (int) (display.getBounds().height * .75);
		Shell shell = new Shell(Display.getCurrent());
		GridLayout gl = new GridLayout(1, true);
		shell.setLayout(gl);
		GridData gd = new GridData(GridData.FILL, GridData.FILL, true, true);
		textbox = new Text(shell, SWT.V_SCROLL | SWT.WRAP);
		textbox.setLayoutData(gd);
		gd = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		closeButton = new Button(shell, SWT.PUSH);
		closeButton.setText("Close");
		closeButton.setLayoutData(gd);
		closeButton.pack();
		shell.setSize(width, height);
		//shell.pack();

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}

}
