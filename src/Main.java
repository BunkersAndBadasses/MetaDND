import org.eclipse.swt.widgets.*;

public class Main {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Testing box");
		shell.open();
		//DieWindow a = new DieWindow(display); To open a die window
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
