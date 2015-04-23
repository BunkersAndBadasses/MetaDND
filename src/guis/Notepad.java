package guis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import org.apache.commons.io.FileUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import core.Main;

public class Notepad {

	private Composite npComposite;
	private Text textbox;
	private Button closeButton;

	public Notepad() {
		int width, height;
		Display display = Display.getCurrent();
		width = display.getBounds().width / 3;
		height = (int) (display.getBounds().height * .75);
		Shell shell = new Shell(Display.getCurrent());
		GridLayout gl = new GridLayout(1, true);
		shell.setLayout(gl);
		GridData gd = new GridData(GridData.FILL, GridData.FILL, true, true);
		textbox = new Text(shell, SWT.V_SCROLL | SWT.WRAP);
		textbox.setLayoutData(gd);
		try {
			File notes = new File(".//User Data//Notes.txt");
			String text = FileUtils.readFileToString(notes, "UTF-8");
			textbox.setText(text);
			
		} catch (Exception e) {
			System.out.println("Notes.txt does not exist");
		}
		gd = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		closeButton = new Button(shell, SWT.PUSH);
		closeButton.setText("Close");
		closeButton.setLayoutData(gd);
		closeButton.pack();
		shell.setSize(width, height);
		
		shell.addListener(SWT.Close, new Listener() {
		      public void handleEvent(Event event) {
		        saveText();
		      }
		    });
		
		closeButton.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		        saveText();
		        shell.close();
		      }
		    });

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}
	
	public void saveText(){
		FileWriter fw;
		try {
			String filePath = ".//User Data";//Main.gameState.currCharFilePath;
			if(filePath != null){
				fw = new FileWriter(new File(filePath + "/Notes.txt"));
				fw.write(this.textbox.getText());
				fw.close();
				System.out.println("Close");
			}
			
		}catch(Exception e){
			System.out.println("Exception!");
		}
	}

}
