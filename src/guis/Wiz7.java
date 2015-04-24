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

public class Wiz7 {

	private Composite wiz7;
	private CharacterWizard cw;
	private Device dev;
	private int WIDTH;
	private int HEIGHT;
	private character character;
	private Composite panel;
	private Composite home;
	private Composite homePanel;
	private StackLayout layout;
	private StackLayout homeLayout;
	private ArrayList<Composite> wizPages;
	private Composite nextPage;
	private int wizPagesSize;
	
	private Text goldText;
	private ArrayList<CharItem> charItems;
	private final Random rng = new Random();
	private GameState gs = Main.gameState;
	
	private List charItemsList;
	final ScrolledComposite charItemScroll;
	final Composite charItemScreen;

	public Wiz7(CharacterWizard cw, Device dev, int WIDTH, int HEIGHT,
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz7 = wizPages.get(6);
		this.cw = cw;
		this.dev = dev;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.character = cw.getCharacter();
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(8);
		this.wizPagesSize = wizPages.size();
		charItemScroll = new ScrolledComposite(wiz7, SWT.V_SCROLL | SWT.BORDER);
		charItemScreen = new Composite (charItemScroll, SWT.BORDER);
		
		createPageContent();
	}

	private void createPageContent() {
		Label wiz7Label = new Label(wiz7, SWT.NONE);
		wiz7Label.setText("Choose Equipment");
		wiz7Label.pack();

		Label goldLabel = new Label(wiz7, SWT.NONE);
		goldLabel.setText("Starting Gold(gp):");
		goldLabel.setLocation(10, 50);
		goldLabel.pack();
		
		goldText = new Text(wiz7, SWT.BORDER);
		goldText.setText("0");
		goldText.setBounds(125, 45, 80, 30);
		goldText.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				Text text = (Text) event.widget;
				text.setBackground(new Color(dev, 255, 255, 255));
			}
		});
		
		
		Button randomGold = new Button(wiz7, SWT.PUSH);
		randomGold.setText("Random");
		randomGold.setLocation(215, 45);
		randomGold.pack();
		randomGold.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				int min = 75;
				int max = 150;
				int gold = rng.nextInt(max - min) + min + 1;
				goldText.setText(Integer.toString(gold));
				goldText.setBackground(new Color(dev, 255, 255, 255));
			}
		});
		
		// search label
		Label searchLabel = new Label(wiz7, SWT.NONE);
		searchLabel.setLocation(10, 80);
		searchLabel.setText("Double click on a item to see details");
		searchLabel.pack();		
		
		// get items from references
		Collection<DNDEntity> itemsCol =  gs.items.values();
		Iterator<DNDEntity> itr = itemsCol.iterator();
		ArrayList<ItemEntity> items = new ArrayList<ItemEntity>();
		charItems = new ArrayList<CharItem>();
		while (itr.hasNext()) {
			items.add((ItemEntity) itr.next());
		}
		
		// layout for scrolling item list
		FillLayout itemLayout = new FillLayout();
		
		// create scrollable list of items
		final ScrolledComposite itemScroll = new ScrolledComposite(wiz7, SWT.V_SCROLL | SWT.BORDER);
		itemScroll.setBounds(10, 110, WIDTH/2 - 65, HEIGHT - 210);
	    itemScroll.setExpandHorizontal(true);
	    itemScroll.setExpandVertical(true);
	    itemScroll.setMinWidth(WIDTH);
		final Composite itemListScreen = new Composite(itemScroll, SWT.NONE);
		itemScroll.setContent(itemListScreen);
		itemListScreen.setSize(itemListScreen.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		itemListScreen.setLayout(itemLayout);
		
		// create scrollable list of selected items
		charItemScroll.setBounds(WIDTH/2 + 55, 110, WIDTH/2 - 75, HEIGHT - 210);
	    charItemScroll.setExpandHorizontal(true);
	    charItemScroll.setExpandVertical(true);
	    charItemScroll.setMinWidth(WIDTH);
		charItemScroll.setContent(charItemScreen);
		charItemScreen.setSize(charItemScreen.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		charItemScreen.setLayout(itemLayout);
		
		// available items list
		List itemsList = new List(itemListScreen, SWT.NONE);
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
		itemsList.pack();
		itemScroll.setMinHeight(itemsList.getBounds().height);
	    
//		// number items list
//		List numCharItemsList = new List(charItemScreen, SWT.NONE);
//		numCharItemsList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		
		// selected items list
		charItemsList = new List(charItemScreen, SWT.NONE);
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
		charItemsList.pack();
		
		// add item button
		Button addButton = new Button(wiz7, SWT.PUSH);
		addButton.setText("Add 1 >");
		addButton.setLocation(WIDTH/2 - 35, HEIGHT/2 - 110);
		addButton.pack();
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
		Button add5Button = new Button(wiz7, SWT.PUSH);
		add5Button.setText("Add 5 >");
		add5Button.setLocation(WIDTH/2 - 35, HEIGHT/2 - 80);
		add5Button.pack();
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
		Button add10Button = new Button(wiz7, SWT.PUSH);
		add10Button.setText("Add 10 >");
		add10Button.setLocation(WIDTH/2 - 38, HEIGHT/2 - 50);
		add10Button.pack();
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
		Button removeButton = new Button(wiz7, SWT.PUSH);
		removeButton.setText("< Remove 1");
		removeButton.setLocation(WIDTH/2 - 45, HEIGHT/2);
		removeButton.pack();
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
		Button removeAllButton = new Button(wiz7, SWT.PUSH);
		removeAllButton.setText("< Remove All");
		removeAllButton.setLocation(WIDTH/2 - 48, HEIGHT/2 + 30);
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
		
		itemListScreen.pack();
		charItemScreen.pack();
		
		// next button
		Button wiz7NextButton = cw.createNextButton(wiz7);
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
					cw.wizPageNum+=2;
				if (!cw.wizPageCreated[8])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});

		//Button wiz7BackButton = cw.createBackButton(wiz7, panel, layout);
		Button wiz7CancelButton = cw.createCancelButton(wiz7, home, homePanel, homeLayout);
		wiz7CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (cw.cancel)
					cw.reset();
			}
		});
	}

	private void createNextPage() {
		cw.wizPageCreated[8] = true;
		cw.wizs.add(new Wiz9(cw, dev, WIDTH, HEIGHT, panel, home,
				homePanel, layout, homeLayout, wizPages));
	}

	private void updateCharItemsList() {
		charItemsList.removeAll();
		for (int i = 0; i<charItems.size(); i++){
			CharItem curr = charItems.get(i);
			charItemsList.add(curr.getCount() + " x " + curr.getItem().getName());
		}
		charItemsList.pack();
		charItemScroll.setMinHeight(charItemsList.getBounds().height);
		charItemScreen.layout();
		charItemScroll.layout();
	}
	
	public Composite getWiz7() { return wiz7; }
}