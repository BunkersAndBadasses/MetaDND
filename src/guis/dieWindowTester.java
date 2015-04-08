package guis;

import org.eclipse.swt.widgets.Display;

public class dieWindowTester {

	public static void main(String [] args){
	
		Display display = new Display();
		DieWindow dw = new DieWindow(display);
		display.dispose();
		
	}
}
