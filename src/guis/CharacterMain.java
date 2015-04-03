package guis;

import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;


public class CharacterMain {
	private static ArrayList<String> spellsKnown;
	private static ArrayList<String> spellsPrepared;
	private static ArrayList<String> allSpells;
	private static ArrayList<String> materials;

	private static ArrayList<String> getSpells() {
		spellsKnown = new ArrayList<String>();
		spellsKnown.add("TODO: Known Spells");
		return spellsKnown;
	}
	private static ArrayList<String> getPrepared() {
		spellsPrepared = new ArrayList<String>();
		spellsPrepared.add("TODO: Prepared Spells");
		return spellsPrepared;
	}

	private static ArrayList<String> getAllSpells() {
		allSpells = new ArrayList<String>();
		allSpells.add("TODO: All Spells");
		return allSpells;
	}

	private static ArrayList<String> getMaterials() {
		materials = new ArrayList<String>();
		materials.add("TODO: Materials");
		return materials;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Display display = new Display();
		Shell shell = new Shell(display);


		new MenuBar(shell); //Add menu bar to windows like this
		
		FormLayout layout = new FormLayout();
		shell.setLayout(layout);
		
		Label label = new Label(shell, SWT.NONE);
		Image image = new Image(display, "images/SetWidth150-blank-profile.jpg");
        label.setImage(image);
        
		FormData imageData = new FormData(150,188);
		
		imageData.left = new FormAttachment(5);
		imageData.top = new FormAttachment(5);
		label.setLayoutData(imageData);

		Button cast = new Button(shell, SWT.PUSH);
		cast.setText("Cast");
		FormData castData = new FormData(80,24);
		castData.left = new FormAttachment(label, 5, SWT.RIGHT);
		castData.top = new FormAttachment(label, 0, SWT.TOP);
		cast.setLayoutData(castData);

		cast.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Cast spell
			}
		}); 

		 

		shell.open(); // Open the Window and process the clicks
		while (!shell.isDisposed()) {
			if (display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		
	}

}