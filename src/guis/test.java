package guis;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class test {
	
	private Display display;
	private Shell shell;
	private final int WIDTH = 700;
	private final int HEIGHT = 500;
	
	
	public test(Display d) {
		display = d;
		shell = new Shell(d);
		shell.setText("test");
		shell.setSize(WIDTH, HEIGHT);

		createPageContent();

		run();
	}

	public void run() {
		center(shell);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private static void center(Shell shell) {

		Rectangle bds = shell.getDisplay().getBounds();

		Point p = shell.getSize();

		int nLeft = (bds.width - p.x) / 2;
		int nTop = (bds.height - p.y) / 2;

		shell.setBounds(nLeft, nTop, p.x, p.y);
	}

	private void createPageContent() {
		
		final Composite homePanel = new Composite(shell, SWT.NONE);
		homePanel.setBounds(0,0,WIDTH,HEIGHT);
//		StackLayout homeLayout = new StackLayout();
//		homePanel.setLayout(homeLayout);
		
//		final Composite page1 = new Composite(homePanel, SWT.NONE);
		
		Button button = new Button(homePanel, SWT.PUSH);
		button.setText("test");
		button.pack();
//		final Text text = new Text(page1, SWT.BORDER);
//		text.setLocation(0, 50);
//		text.pack();
//		button.addListener(SWT.Selection, new Listener() {
//			public void handleEvent(Event event) {
//				text.setText("Hello");
//			}
//		});
//		
//		homeLayout.topControl = page1;
//		homePanel.layout();

		
		
	}
	
	
	public static void main(String[] args) {
		Display display = new Display();
		test t = new test(display);
		display.dispose();
	}

}
