package guis;

import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import core.Main;
import entity.DNDEntity;

public class referencePanel {

	private Composite refPanel;
	private Composite view;
	private Composite list;
	private StackLayout stackLayout;

	private Text searchBar;
	private List searchList;
	
	public referencePanel(Composite page) {
		refPanel = new Composite(page, SWT.NONE);
		stackLayout = new StackLayout();
		createPageContent();
		
	}
	
	public Composite getRefPanel(){ return refPanel; }

	private void createPageContent() {

		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.horizontalSpacing = 3;
		layout.numColumns = 1;
		refPanel.setLayout(layout);

		searchBar = new Text(refPanel, SWT.BORDER | SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, false, false);
		searchBar.setMessage("Search");
		searchBar.setLayoutData(gridData);
		searchBar.addSelectionListener( new SelectionAdapter() { 
			public void widgetDefaultSelected( SelectionEvent e ) { 
				searchList.removeAll();
				if(Main.gameState.search(searchBar.getText())){
					for(Entry<String, DNDEntity> entry : 
						Main.gameState.searchResults.entrySet()){
						searchList.add(entry.getKey());
					}
				}
			}
		});

		view = new Composite(refPanel, SWT.NONE);
		list = new Composite(view, SWT.NONE);
		
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		view.setLayoutData(gridData);
		view.setLayout(stackLayout);


		//LIST COMPOSITE
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		list.setLayout(fillLayout);

		searchList = new List(list, SWT.V_SCROLL);
		//searchList.add("empty");
		searchList.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e){
				
				DNDEntity searchEntity = Main.gameState.searchResults.get(searchList.getSelection()[0]);
				
				searchEntity.toTooltipWindow();
				
				//backButton.setVisible(true);
				//stackLayout.topControl = info;
			}

			@Override
			//leave blank, but must have
			public void widgetSelected(SelectionEvent e) {}
		});

		list.layout();

		stackLayout.topControl = list;
		view.layout();

	}


}
