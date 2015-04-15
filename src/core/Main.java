package core;
import guis.HomeWindow;
import guis.Startscreen;

import org.eclipse.swt.widgets.*;

public class Main {
	
	public static GameState gameState;

	public static void main(String[] args) {
		gameState = new GameState();
			
		Display display = new Display();
		//new Startscreen(display);
		new HomeWindow(display);
		
		display.dispose();
		System.out.println("Exiting");
		System.exit(0);
	}

}
