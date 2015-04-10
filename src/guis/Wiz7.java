package guis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
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

public class Wiz7 {

	private static Composite wiz7;
	private static Device dev;
	private static int WIDTH;
	private static int HEIGHT;
	private static character character;
	private Composite panel;
	private Composite home;
	private Composite homePanel;
	private StackLayout layout;
	private StackLayout homeLayout;
	private ArrayList<Composite> wizPages;
	private Composite nextPage;
	private int wizPagesSize;
	
	private Text goldText;
	private String charRace;
	private String charClass;
	private final static Random rng = new Random();
	private GameState gs = Main.gameState;

	public Wiz7(Device dev, int WIDTH, int HEIGHT, final character character, 
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz7 = wizPages.get(6);
		Wiz7.dev = dev;
		Wiz7.WIDTH = WIDTH;
		Wiz7.HEIGHT = HEIGHT;
		Wiz7.character = character;
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(7);
		this.wizPagesSize = wizPages.size();
		charRace = CharacterWizard.getCharacter().getCharRace().getName();
		charClass = CharacterWizard.getCharacter().getCharClass().getName();

		createPageContent();
	}

	private void createPageContent() {
		Label wiz7Label = new Label(wiz7, SWT.NONE);
		wiz7Label.setText("Choose Equipment");
		wiz7Label.pack();

		Label goldLabel = new Label(wiz7, SWT.NONE);
		goldLabel.setText("Starting Gold:");
		goldLabel.setLocation(10, 50);
		goldLabel.pack();
		
		goldText = new Text(wiz7, SWT.BORDER);
		goldText.setText("0");
		goldText.setBounds(120, 45, 80, 30);
		goldText.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				Text text = (Text) event.widget;
				text.setBackground(new Color(dev, 255, 255, 255));
			}
		});
		
		
		Button randomGold = new Button(wiz7, SWT.PUSH);
		randomGold.setText("Random");
		randomGold.setLocation(210, 45);
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
		
		
		// get feats from references
		Collection<DNDEntity> itemsCol =  gs.items.values();
		Iterator<DNDEntity> itr = itemsCol.iterator();
		ArrayList<ItemEntity> items = new ArrayList<ItemEntity>();
		ArrayList<CharItem> charItems = new ArrayList<CharItem>();
		ArrayList<Integer> numCharItems = new ArrayList<Integer>();
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
		final ScrolledComposite charItemScroll = new ScrolledComposite(wiz7, SWT.V_SCROLL | SWT.BORDER);
		charItemScroll.setBounds(WIDTH/2 + 55, 110, WIDTH/2 - 75, HEIGHT - 210);
	    charItemScroll.setExpandHorizontal(true);
	    charItemScroll.setExpandVertical(true);
	    charItemScroll.setMinWidth(WIDTH);
		final Composite charItemScreen = new Composite (charItemScroll, SWT.BORDER);
		charItemScroll.setContent(charItemScreen);
		charItemScreen.setSize(charItemScreen.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		charItemScreen.setLayout(itemLayout);
		
		// available items list
		List itemsList = new List(itemListScreen, SWT.NONE);
		for (int i = 0; i < items.size(); i++) {
			itemsList.add(items.get(i).getName());
		}
		itemsList.pack();
		itemScroll.setMinHeight(itemsList.getBounds().height);
	    
//		// number items list
//		List numCharItemsList = new List(charItemScreen, SWT.NONE);
//		numCharItemsList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		
		// selected items list
		List charItemsList = new List(charItemScreen, SWT.NONE);
		charItemsList.pack();
		
		// add item button
		Button addButton = new Button(wiz7, SWT.PUSH);
		addButton.setText("Add >");
		addButton.setLocation(WIDTH/2 - 25, HEIGHT/2 - 50);
		addButton.pack();
		addButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				int index = itemsList.getSelectionIndex();
				String selection = itemsList.getItem(index);
				if (index == -1)
					return;

				for(int i = 0; i < charItems.size(); i++) {
					// if item is already added, increment
					if (charItems.get(i).getName().equals(selection)) {
						charItems.get(i).incCount();
						numCharItems.set(i, charItems.get(i).getCount());
						charItemsList.setItem(i, Integer.toString(numCharItems.get(i)) + " x " + charItems.get(i).getName());
						System.out.println("incrementing " + charItems.get(i).getName()); //TODO
						return;
					}
				}
				// otherwise add it to the list
				CharItem c = new CharItem(items.get(index));
				charItems.add(c);
				charItemsList.add("1 x " + selection);
				numCharItems.add(c.getCount());
				System.out.println("adding " + itemsList.getItem(index)); //TODO
			


				charItemsList.pack();
				charItemScroll.setMinHeight(charItemsList.getBounds().height);
				charItemScreen.layout();
				charItemScroll.layout();
			}
		});
		
		// remove item button
		Button removeButton = new Button(wiz7, SWT.PUSH);
		removeButton.setText("< Remove");
		removeButton.setLocation(WIDTH/2 - 38, HEIGHT/2);
		removeButton.pack();
		removeButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				if (charItems.isEmpty())
					return;
				int index = charItemsList.getSelectionIndex();
				if (index == -1)
					return;
				if (charItems.get(index).decCount()) {
					System.out.println("successfully decremented " + itemsList.getItem(index)); //TODO
					numCharItems.set(index, charItems.get(index).getCount());
					charItemsList.setItem(index, Integer.toString(numCharItems.get(index)) + " x " + charItems.get(index).getName());
				}
				else {
					charItemsList.remove(index);
					charItems.remove(index);
					numCharItems.remove(index);
				}


				charItemScreen.pack();
				charItemScroll.setMinHeight(charItemsList.getBounds().height);
				charItemScreen.layout();
				charItemScroll.layout();
				
			}
		});
		itemListScreen.pack();
		charItemScreen.pack();
		
		
		
		
		
		
		
		
		Button wiz7NextButton = CharacterWizard.createNextButton(wiz7);
		wiz7NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				boolean error = false;
				int gold = 0;
				try {
					gold = Integer.parseInt(goldText.getText());
					if (gold < 0) 
						throw new Exception();
				} catch (Exception e) {
					goldText.setBackground(new Color(dev, 255, 100, 100));
					error = true;
				}
				
				if (error)
					return;
				
				
				if (CharacterWizard.wizPageNum < wizPagesSize - 1)
					CharacterWizard.wizPageNum++;
				if (!CharacterWizard.wizPageCreated[7])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});

		//Button wiz7BackButton = CharacterWizard.createBackButton(wiz7, panel, layout);
		Button wiz7CancelButton = CharacterWizard.createCancelButton(wiz7, home, homePanel, homeLayout);
		wiz7CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.cancel)
					cancelClear();
			}
		});
	}

	private void createNextPage() {
		CharacterWizard.wizPageCreated[7] = true;
		CharacterWizard.wizs.add(new Wiz8(dev, WIDTH, HEIGHT, character, panel, home,
				homePanel, layout, homeLayout, wizPages));
	}

	public Composite getWiz7() { return wiz7; }

	public void cancelClear() {
		CharacterWizard.reset();
		((Wiz1)CharacterWizard.wizs.get(0)).cancelClear();
		((Wiz2)CharacterWizard.wizs.get(1)).cancelClear();
		((Wiz3)CharacterWizard.wizs.get(2)).cancelClear();
		((Wiz4)CharacterWizard.wizs.get(3)).cancelClear();
		((Wiz5)CharacterWizard.wizs.get(4)).cancelClear();
		((Wiz6)CharacterWizard.wizs.get(5)).cancelClear();
	}
}