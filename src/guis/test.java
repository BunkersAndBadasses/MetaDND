package guis;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import core.Main;
import core.character;
import entity.ClassEntity;

public class test {

	public static void main(String[] args) {
		new test(new Display());
	}
	
	public test(Display display) {
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, true));
		
		character c = Main.gameState.currentlyLoadedCharacter;
//		c.setLevel(1);
		c.setExp(6000);
//		c.setCharClass((ClassEntity)Main.gameState.classes.get("Barbarian"));
		
		Button button = new LevelUpButton(shell, c).getButton();
		button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		// open shell
		shell.pack();
		shell.layout();
		CharacterWizard.center(shell);
		shell.open();
//
//		// check if disposed
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
	}
}
