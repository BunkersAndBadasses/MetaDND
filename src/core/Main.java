package core;
import guis.HomeWindow;
import guis.Startscreen;

import org.eclipse.swt.widgets.*;

public class Main {
	
	public static GameState gameState;

	public static void main(String[] args) {
		gameState = new GameState();
		xmlLoader xmls = new xmlLoader("xmlTestThread");
		//SearchThread st1 = new SearchThread("Spells");
		//SearchThread st2 = new SearchThread("Spells");
		xmls.start();
		
		
		Display display = new Display();
		new Startscreen(display);
		new HomeWindow(display);
		//shell.setBounds(0,0,500,500);
		//shell.setText("Testing box");
		//new MenuBar(shell);
		//Button button = new Button(shell, SWT.PUSH);
		//button.setText("I'm a button");
		//button.setBounds(75,75,100,50);
		//button.addListener(SWT.Selection, new Listener() {
		//	public void handleEvent(Event event) {
				//System.out.println("You clicked the button");
				
		//		st1.start(Main.gameState.spells, "acid");
		//		st2.start(Main.gameState.spells, "fire");
		//	}
		//});
		
		//int die = 20;
		//int number = 3;
		//int array[] = new int [number];
		//String dieRolls = "";
		
//		array = DnDie.roll(die, number);
//		
//		for(int i = 0; i < array.length; i++){
//			dieRolls += array[i] + ", ";
//		}
//		
//		Text helloWorldTest = new Text(shell, SWT.NONE);
//		helloWorldTest.setText(dieRolls);
//		helloWorldTest.pack();
//        Item_wizard item_wizard = new Item_wizard(display);   //To open and test item_wizard
		//shell.open();
		
		display.dispose();
		System.out.println("Exiting");
		System.exit(0);
	}

}
