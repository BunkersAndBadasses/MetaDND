package guis;

import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Startscreen {
	private static Shell shell;
	private static Display display;
	public static boolean cancel = false;
	//private static final int WIDTH = 720;
	//private static final int HEIGHT = 350;

	public Startscreen(Display d) {
		display = d;
		shell = new Shell(d, SWT.NO_TRIM | SWT.NO_FOCUS);
		shell.setText("Welcome!");
		//shell.setSize(WIDTH, HEIGHT);
		createPageContent();
		run();
	}

	private void run() {
		center(shell);
		shell.setAlpha(0);
		shell.open();
		boolean fadeInDone = false;
		while (!shell.isDisposed()) {
			
			if (!display.readAndDispatch()) {
				if(!fadeInDone)
					shell.setAlpha(shell.getAlpha() + 1);
				else
					shell.setAlpha(shell.getAlpha() - 1);
				try {
					TimeUnit.MILLISECONDS.sleep(8);
					if(shell.getAlpha() >= 254){
						//TimeUnit.MILLISECONDS.sleep(2000);
						//shell.dispose();
						fadeInDone = true;
					}
					if(shell.getAlpha() == 0 && fadeInDone){
						//TimeUnit.MILLISECONDS.sleep(2000);
						shell.dispose();
					}
				} catch (InterruptedException e) {
					//e.printStackTrace();
				}
				
				//display.sleep();
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

		ImageData ideaData = new ImageData(".//images//bnb_logo.gif");
		int whitePixel = ideaData.palette.getPixel(new RGB(255, 255, 255));
		ideaData.transparentPixel = whitePixel;
		Image transparentIdeaImage = new Image(display, ideaData);
		Label transparentIdeaLabel = new Label(shell, SWT.NONE);
		transparentIdeaLabel.setImage(transparentIdeaImage);
		Canvas canvas = new Canvas(shell, SWT.NONE);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(transparentIdeaImage, 0, 0);
			}
		});
		transparentIdeaLabel.pack();
		shell.pack();
	}
}
