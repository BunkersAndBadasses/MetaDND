import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;


public class SpellGUI {
	private static ArrayList<String> spellsKnown;

	private static ArrayList<String> getSpells() {
		spellsKnown = new ArrayList<String>();
		spellsKnown.add("TODO");
		return spellsKnown;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Display display = new Display();
		Shell shell = new Shell(display);
		
		
		new MenuBar(shell);
					
		FormLayout layout = new FormLayout();
		shell.setLayout(layout);
		
		final Combo spellSel = new Combo(shell, SWT.READ_ONLY);
		String spellArr[] = new String[getSpells().size()];
		spellArr= spellsKnown.toArray(spellArr);
		spellSel.setItems(spellArr); // TODO Load Spells known
		FormData spellSelData = new FormData(140,30);
		spellSel.select(0);
		spellSelData.left = new FormAttachment(5);
		spellSelData.top = new FormAttachment(5);
		spellSel.setLayoutData(spellSelData);
		
		Button cast = new Button(shell, SWT.PUSH);
		cast.setText("Cast");
		FormData castData = new FormData(80,24);
		castData.left = new FormAttachment(spellSel, 5, SWT.RIGHT);
		castData.top = new FormAttachment(spellSel, 0, SWT.TOP);
		cast.setLayoutData(castData);
		
		cast.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        // TODO Cast spell
		    }
		}); 
		
		Button removeSpell = new Button(shell, SWT.PUSH);
		removeSpell.setText("Remove from spell list");
		FormData removeData = new FormData(167,24);
		removeData.left = new FormAttachment(spellSel, 0, SWT.LEFT);
		removeData.top = new FormAttachment(spellSel, 5, SWT.BOTTOM);
		removeSpell.setLayoutData(removeData);
		
		removeSpell.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        // TODO Remove spell
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
