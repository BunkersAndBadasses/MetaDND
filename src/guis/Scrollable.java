package guis;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class Scrollable {

	private ScrolledComposite sc;
	private Composite c;
	
	public Scrollable(int x, int y, int width, int height, int minWidth, int minHeight, Composite shell) {
		sc = new ScrolledComposite(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		sc.setBounds(x, y, width, height);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setMinSize(minWidth, minHeight);
		c = new Composite(sc, SWT.NONE);
		sc.setContent(c);
		c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));	
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		c.setLayout(layout);
	}
	
}
