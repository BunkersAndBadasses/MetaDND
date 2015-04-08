package core;
import guis.Ability_wizard;
import guis.Feat_wizard;
import guis.Item_wizard;
import guis.MenuBar;
import guis.Spell_wizard;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

public class Main {
	
	public static GameState gameState;

	public static void main(String[] args) {
		gameState = new GameState();
		xmlLoader xmls = new xmlLoader("xmlTestThread");
		xmls.start();
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setBounds(0,0,500,500);
		shell.setText("Testing box");
		new MenuBar(shell);
		Button button = new Button(shell, SWT.PUSH);
		button.setText("I'm a button");
		button.setBounds(75,75,100,50);
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				System.out.println("You clicked the button");
			}
		});
		
		int die = 20;
		int number = 3;
		int array[] = new int [number];
		String dieRolls = "";
		
//		array = DnDie.roll(die, number);
//		
//		for(int i = 0; i < array.length; i++){
//			dieRolls += array[i] + ", ";
//		}
//		
//		Text helloWorldTest = new Text(shell, SWT.NONE);
//		helloWorldTest.setText(dieRolls);
//		helloWorldTest.pack();
        //Item_wizard item_wizard = new Item_wizard(display);   //To open and test item_wizard
		
		//Feat_wizard feat = new Feat_wizard(display);
		//Ability_wizard ability = new Ability_wizard(display);
		//Spell_wizard spell = new Spell_wizard(display);
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		shell.dispose();
		System.out.println("Exiting");
		System.exit(0);
	}

}
