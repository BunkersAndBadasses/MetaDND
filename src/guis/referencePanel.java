package guis;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import core.character;

public class referencePanel {

	private Composite wiz4;
	private Device dev;
	private int WIDTH;
	private int HEIGHT;
	private character character;
	private Composite refPanel;
	private Composite results;
	private Composite info;
	private StackLayout layout;
	private StackLayout homeLayout;

	private Text searchBar;

	private final Color white = new Color(dev, 255, 255, 255);

	public referencePanel(Device dev, int WIDTH, int HEIGHT, 
			final Composite info, Composite refPanel, Composite results, 
			final StackLayout layout, final StackLayout homeLayout) {
		this.dev = dev;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.character = CharacterWizard.getCharacter();
		this.info = info;
		this.refPanel = refPanel;
		this.results = results;
		this.layout = layout;
		this.homeLayout = homeLayout;

		createPageContent();
	}

	private void createPageContent() {

		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.horizontalSpacing = 3;
		layout.numColumns = 1;
		refPanel.setLayout(layout);

		searchBar = new Text(refPanel, SWT.BORDER);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		searchBar.setText("Search");
		searchBar.setLayoutData(gridData);
		searchBar.addSelectionListener( new SelectionAdapter() { 
			public void widgetDefaultSelected( SelectionEvent e ) { 
					//TODO populate the search list
			}
		});

		// generate new dungeon
		Button backButton = new Button(refPanel, SWT.PUSH);
		backButton.setText("Back");
		backButton.setVisible(false);
		backButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
					//TODO close the information page
			}
		});
		

	}


}
