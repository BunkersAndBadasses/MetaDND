import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

public class Main {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Testing box");
		
		int die = 20;
		int number = 3;
		int array[] = new int [number];
		String dieRolls = "";
		
		array = DnDie.roll(die, number);
		
		for(int i = 0; i < array.length; i++){
			dieRolls += array[i] + ", ";
		}
		
		Text helloWorldTest = new Text(shell, SWT.NONE);
		helloWorldTest.setText(dieRolls);
		helloWorldTest.pack();
		
		shell.open();
		DieWindow a = new DieWindow(display); //To open a die window
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
