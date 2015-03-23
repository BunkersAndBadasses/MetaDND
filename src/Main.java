import guis.MenuBar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

public class Main {

	public static void main(String[] args) {
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
		
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
