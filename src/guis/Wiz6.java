/*
 * CHOOSE ITEMS
 */

package guis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import entity.*;
import core.CharItem;
import core.GameState;
import core.Main;
import core.character;

public class Wiz6 {

	private Composite wiz6;
	private CharacterWizard cw;
	private Device dev;
	private int WIDTH;
	private int HEIGHT;
	private character character;
	private Composite panel;
	private StackLayout layout;
	private ArrayList<Composite> wizPages;
	private Composite nextPage;
	private int wizPagesSize;
	
	private Text goldText;
	private ArrayList<CharItem> charItems;
	private final Random rng = new Random();
	private GameState gs = Main.gameState;
	
	private List charItemsList;
//	final ScrolledComposite charItemScroll;
//	final Composite charItemScreen;
	private Composite inner;

	public Wiz6(CharacterWizard cw, Device dev, int WIDTH, int HEIGHT,
			final Composite panel, final StackLayout layout, 
			final ArrayList<Composite> wizPages) {
		wiz6 = wizPages.get(5);
		this.cw = cw;
		this.dev = dev;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.character = cw.getCharacter();
		this.panel = panel;
		this.layout = layout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(6);
		this.wizPagesSize = wizPages.size();
//		charItemScroll = new ScrolledComposite(wiz7, SWT.V_SCROLL | SWT.BORDER);
//		charItemScreen = new Composite (charItemScroll, SWT.BORDER);
		
		createPageContent();
	}

	private void createPageContent() {
		Label wiz7Label = new Label(wiz6, SWT.NONE);
		wiz7Label.setText("Choose Equipment");
		wiz7Label.pack();

		//////////instantiate layout //////////
		
		GridLayout gl = new GridLayout(5, true);
		
		inner = new Composite(wiz6, SWT.NONE);
		inner.setBounds(5, 20, WIDTH-10, HEIGHT-110);
		inner.setLayout(gl);

		GridData gd;
		
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 5;
		new Label(inner, SWT.NONE).setLayoutData(gd);
		
		////////////////////
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		new Label(inner, SWT.NONE).setLayoutData(gd);
		
		// gold label
		Label goldLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		goldLabel.setLayoutData(gd);
		
		// gold text box
		goldText = new Text(inner, SWT.BORDER | SWT.CENTER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		goldText.setLayoutData(gd);

		
		// random gold button
		Button randomGold = new Button(inner, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		randomGold.setLayoutData(gd);
		
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		new Label(inner, SWT.NONE).setLayoutData(gd);
		////////////////////
		
		////////////////////
		// details
		Label detailsLabel = new Label(inner, SWT.NONE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.horizontalSpan = 5;
		detailsLabel.setLayoutData(gd);
		////////////////////
		
		////////////////////
		// item list
		List itemsList = new List(inner, SWT.V_SCROLL | SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 2;
		gd.verticalSpan = 8;
		itemsList.setLayoutData(gd);
			
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		new Label(inner, SWT.NONE).setLayoutData(gd);

		// char item list
		charItemsList = new List(inner, SWT.V_SCROLL | SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 2;
		gd.verticalSpan = 8;
		charItemsList.setLayoutData(gd);
		////////////////////
		
		////////////////////
		// add 1
		Button addButton = new Button(inner, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		addButton.setLayoutData(gd);
		
		// add 5
		Button add5Button = new Button(inner, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		add5Button.setLayoutData(gd);
		
		// add 10
		Button add10Button = new Button(inner, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		add10Button.setLayoutData(gd);
		
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		new Label(inner, SWT.NONE).setLayoutData(gd);
		
		// remove 1
		Button removeButton = new Button(inner, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		removeButton.setLayoutData(gd);
		
		// remove all
		Button removeAllButton = new Button(inner, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		removeAllButton.setLayoutData(gd);
		
		// placeholder
		gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		new Label(inner, SWT.NONE).setLayoutData(gd);
		////////////////////
		
		
		////////// create content //////////
		
		goldLabel.setText("Starting Gold(gp):");
		goldLabel.pack();
		
		goldText.setText("0");
		goldText.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				Text text = (Text) event.widget;
				text.setBackground(new Color(dev, 255, 255, 255));
			}
		});
		
		
		randomGold.setText("Random");
		randomGold.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				int min = 75;
				int max = 150;
				int gold = rng.nextInt(max - min) + min + 1;
				goldText.setText(Integer.toString(gold));
				goldText.setBackground(new Color(dev, 255, 255, 255));
			}
		});
		
		// details label
		detailsLabel.setText("Double click on a item to see details");
		
		// get items from references
		Collection<DNDEntity> itemsCol =  gs.items.values();
		Iterator<DNDEntity> itr = itemsCol.iterator();
		ArrayList<ItemEntity> items = new ArrayList<ItemEntity>();
		charItems = new ArrayList<CharItem>();
		while (itr.hasNext()) {
			items.add((ItemEntity) itr.next());
		}
		
		// available items list
		for (int i = 0; i < items.size(); i++) {
			itemsList.add(items.get(i).getName());
		}
		itemsList.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e){
				int index = itemsList.getSelectionIndex();
				if (index == -1)
					return;
				String itemName = itemsList.getItem(index);
				((ItemEntity)Main.gameState.items.get(itemName)).toTooltipWindow();
			}
			@Override
			//leave blank, but must have
			public void widgetSelected(SelectionEvent e) {}
		});

		// selected items list
		charItemsList.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e){
				int index = charItemsList.getSelectionIndex();
				if (index == -1)
					return;
				charItems.get(index).getItem().toTooltipWindow();
			}
			@Override
			//leave blank, but must have
			public void widgetSelected(SelectionEvent e) {}
		});
		
		// add item button
		addButton.setText("Add 1 >");
		addButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				int index = itemsList.getSelectionIndex();
				// check if an item is selected
				if (index == -1)
					return;
				// get selected item
				String selection = itemsList.getItem(index);
				// if item is already added, increment
				for(int i = 0; i < charItems.size(); i++) {
					if (charItems.get(i).getName().equals(selection)) {
						charItems.get(i).incCount();
						if (charItems.get(i).getCount() > 100)
							charItems.get(i).setCount(100);
						charItemsList.setItem(i, Integer.toString(charItems.get(i).getCount()) + " x " + charItems.get(i).getName());
						return;
					}
				}
				// otherwise add it to the list
				CharItem c = new CharItem(items.get(index));
				charItems.add(c);
				charItemsList.add("1 x " + selection);
			
				// refresh char items list
				updateCharItemsList();
			}
		});
		
		// add 5 button
		add5Button.setText("Add 5 >");
		add5Button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				int index = itemsList.getSelectionIndex();
				// check if an item is selected
				if (index == -1)
					return;
				// get selected item
				String selection = itemsList.getItem(index);
				// if item is already added, increment
				for(int i = 0; i < charItems.size(); i++) {
					if (charItems.get(i).getName().equals(selection)) {
						charItems.get(i).incCountBy(5);
						if (charItems.get(i).getCount() > 100)
							charItems.get(i).setCount(100);
						charItemsList.setItem(i, Integer.toString(charItems.get(i).getCount()) + " x " + charItems.get(i).getName());
						return;
					}
				}
				// otherwise add it to the list
				CharItem c = new CharItem(items.get(index), 5);
				charItems.add(c);
				charItemsList.add("5 x " + selection);
			
				// refresh char items list
				updateCharItemsList();
			}
		});
		
		// add 10 button
		add10Button.setText("Add 10 >");
		add10Button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				int index = itemsList.getSelectionIndex();
				// check if an item is selected
				if (index == -1)
					return;
				// get selected item
				String selection = itemsList.getItem(index);
				// if item is already added, increment
				for(int i = 0; i < charItems.size(); i++) {
					if (charItems.get(i).getName().equals(selection)) {
						charItems.get(i).incCountBy(10);
						if (charItems.get(i).getCount() > 100)
							charItems.get(i).setCount(100);
						charItemsList.setItem(i, Integer.toString(charItems.get(i).getCount()) + " x " + charItems.get(i).getName());
						return;
					}
				}
				// otherwise add it to the list
				CharItem c = new CharItem(items.get(index), 10);
				charItems.add(c);
				charItemsList.add("10 x " + selection);
			
				// refresh char items list
				updateCharItemsList();
			}
		});


		
		// remove item button
		removeButton.setText("< Remove 1");
		removeButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				if (charItems.isEmpty())
					return;
				int index = charItemsList.getSelectionIndex();
				if (index == -1)
					return;
				if (charItems.get(index).decCount()) {
					charItemsList.setItem(index, Integer.toString(charItems.get(index).getCount()) + " x " + charItems.get(index).getName());
				}
				else {
					charItemsList.remove(index);
					charItems.remove(index);
				}
				updateCharItemsList();
			}
		});
		
		// remove all button
		removeAllButton.setText("< Remove All");
		removeAllButton.pack();
		removeAllButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				if (charItems.isEmpty())
					return;
				int index = charItemsList.getSelectionIndex();
				if (index == -1)
					return;
				charItemsList.remove(index);
				charItems.remove(index);
				updateCharItemsList();
			}
		});
		
		// next button
		Button wiz7NextButton = cw.createNextButton(wiz6);
		wiz7NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int gold = 0;
				try {
					gold = Integer.parseInt(goldText.getText());
					if (gold < 0) 
						throw new Exception();
				} catch (Exception e) {
					goldText.setBackground(new Color(dev, 255, 100, 100));
					return;
				}
				character.setGP(gold);
				
				for (int i = 0; i < charItems.size(); i++) {
					character.addItem(charItems.get(i));
				}
				
				if (cw.wizPageNum < wizPagesSize - 1)
					cw.wizPageNum++;
				if (!cw.wizPageCreated[7])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});

		//Button wiz7BackButton = cw.createBackButton(wiz7, panel, layout);
		Button wiz7CancelButton = cw.createCancelButton(wiz6);
		wiz7CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (cw.cancel)
					cw.reset();
			}
		});
		
		inner.layout();
	}

	private void createNextPage() {
		cw.wizPageCreated[6] = true;
		cw.wizs.add(new Wiz7(cw, dev, WIDTH, HEIGHT, panel, layout, wizPages));
	}

	private void updateCharItemsList() {
		charItemsList.removeAll();
		for (int i = 0; i<charItems.size(); i++){
			CharItem curr = charItems.get(i);
			charItemsList.add(curr.getCount() + " x " + curr.getItem().getName());
		}
		charItemsList.pack();
		inner.layout();
	}
	
	public Composite getWiz6() { return wiz6; }
}