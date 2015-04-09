package guis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
import org.eclipse.swt.widgets.ScrollBar;

import core.character;
import core.GameState;
import core.Main;
import entity.DNDEntity;
import entity.FeatEntity;

public class Wiz5 {

	private static Composite wiz5;
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
	private int numFeats;
	private static GameState gs = Main.gameState;
	
	private static Label numFeatsLabel;

	public Wiz5(Device dev, int WIDTH, int HEIGHT, final character character, 
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz5 = wizPages.get(4);
		Wiz5.dev = dev;
		Wiz5.WIDTH = WIDTH;
		Wiz5.HEIGHT = HEIGHT;
		Wiz5.character = character;
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(5);
		this.wizPagesSize = wizPages.size();
		
		createPageContent();
	}

	private void createPageContent() {
		Label wiz5Label = new Label(wiz5, SWT.NONE);
		wiz5Label.setText("Choose Feats");
		wiz5Label.pack();
		
		// "number of feats remaining: " label
		Label featsLabel = new Label(wiz5, SWT.NONE);
		featsLabel.setLocation(240,30);
		featsLabel.setText("Number of Feats Remaining:");
		featsLabel.pack();

		// number of remaining feats
		numFeats = 1;
		if (CharacterWizard.getCharacter().getCharRace().equals("Human"))
			numFeats += 1;
		
		// number of remaining feats label
		numFeatsLabel = new Label(wiz5, SWT.NONE);
		numFeatsLabel.setLocation(435, 30);
		numFeatsLabel.setText(Integer.toString(numFeats));
		numFeatsLabel.pack();
		
		// get feats from references
		Collection<DNDEntity> featsCol =  gs.feats.values();
		Iterator<DNDEntity> itr = featsCol.iterator();
		ArrayList<FeatEntity> feats = new ArrayList<FeatEntity>();
		ArrayList<FeatEntity> charFeats = new ArrayList<FeatEntity>();
		while (itr.hasNext()) {
			feats.add((FeatEntity) itr.next());
		}

		
		// grid layout for both available and selected feat lists
		FillLayout featLayout = new FillLayout();
		
		// create scrollable list of feats
		final ScrolledComposite featScreenScroll = new ScrolledComposite(wiz5, SWT.V_SCROLL | SWT.BORDER);
		featScreenScroll.setBounds(10, 110, WIDTH/2 - 65, HEIGHT - 210);
	    featScreenScroll.setExpandHorizontal(true);
	    featScreenScroll.setExpandVertical(true);
	    featScreenScroll.setMinWidth(WIDTH);
		final Composite featListScreen = new Composite(featScreenScroll, SWT.NONE);
		featScreenScroll.setContent(featListScreen);
		featListScreen.setSize(featListScreen.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		featListScreen.setLayout(featLayout);
				
		// TODO scroll not working, okay because for now, only 1-2 feats can be added anyways
		// create scrollable list of selected feats
		final ScrolledComposite charFeatScreenScroll = new ScrolledComposite(wiz5, SWT.V_SCROLL | SWT.BORDER);
		charFeatScreenScroll.setBounds(WIDTH/2 + 55, 110, WIDTH/2 - 75, HEIGHT - 210);
	    charFeatScreenScroll.setExpandHorizontal(true);
	    charFeatScreenScroll.setExpandVertical(true);
	    charFeatScreenScroll.setMinWidth(WIDTH);
		final Composite charFeatScreen = new Composite (charFeatScreenScroll, SWT.BORDER);
		charFeatScreenScroll.setContent(charFeatScreen);
		charFeatScreen.setSize(charFeatScreen.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		charFeatScreen.setLayout(featLayout);
		
		// available feats list
		List featsList = new List(featListScreen, SWT.NONE);
		for (int i = 0; i < feats.size(); i++) {
			featsList.add(feats.get(i).getName());
		}
		featsList.pack();
		featScreenScroll.setMinHeight(featsList.getBounds().height);
	    	
		// selected feats list
		List charFeatsList = new List(charFeatScreen, SWT.NONE);
		charFeatsList.pack();
		
		// add feat button
		Button addButton = new Button(wiz5, SWT.PUSH);
		addButton.setText("Add >");
		addButton.setLocation(WIDTH/2 - 25, HEIGHT/2 - 50);
		addButton.pack();
		addButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean error = false;
				if (numFeats == 0)
					error = true;
				int index = featsList.getSelectionIndex();
				if (index == -1)
					error = true;
				for(int i = 0; i < charFeats.size(); i++) {
					if (charFeats.get(i).getName().equals(featsList.getItem(index)))
						error = true;
				}
				if (error)
					return;
				// TODO check prerequisites
				charFeatsList.add(featsList.getItem(index));
				charFeats.add(feats.get(index));
				numFeats--;
				numFeatsLabel.setText(Integer.toString(numFeats));
				numFeatsLabel.setForeground(new Color(dev, 0, 0, 0));
				numFeatsLabel.pack();
				charFeatsList.pack();
				charFeatScreenScroll.setMinHeight(charFeatsList.getBounds().height);
				charFeatScreen.layout();
			}
		});
		
		// remove feat button
		Button removeButton = new Button(wiz5, SWT.PUSH);
		removeButton.setText("< Remove");
		removeButton.setLocation(WIDTH/2 - 38, HEIGHT/2);
		removeButton.pack();
		removeButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				if (charFeats.isEmpty())
					return;
				int index = charFeatsList.getSelectionIndex();
				if (index == -1)
					return;
				charFeatsList.remove(index);
				charFeats.remove(index);
				numFeats++;
				numFeatsLabel.setText(Integer.toString(numFeats));
				numFeatsLabel.setForeground(new Color(dev, 0, 0, 0));
				numFeatsLabel.pack();
				charFeatsList.pack();
				charFeatScreenScroll.setMinHeight(charFeatsList.getBounds().height);
				charFeatScreen.layout();
			}
		});
		
		featListScreen.pack();
		charFeatScreen.pack();

		Button wiz5NextButton = CharacterWizard.createNextButton(wiz5);
		wiz5NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// error checking
				if (numFeats > 0) {
					numFeatsLabel.setForeground(new Color(dev, 255, 0, 0));
					return;
				}
				
				// if all is good, save to character
				for (int i = 0; i < charFeats.size(); i++)
					CharacterWizard.getCharacter().addFeat(charFeats.get(i));
				
				// switch to next page
				if (CharacterWizard.wizPageNum < wizPagesSize - 1)
					CharacterWizard.wizPageNum++;
				if (!CharacterWizard.wizPageCreated[5])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});

		Button wiz5BackButton = CharacterWizard.createBackButton(wiz5, panel, layout);
		Button wiz5CancelButton = CharacterWizard.createCancelButton(wiz5, home, homePanel, homeLayout);
		wiz5CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.cancel)
					cancelClear();
			}
		});
	}

	private void createNextPage() {
		CharacterWizard.wizPageCreated[5] = true;
		new Wiz6(dev, WIDTH, HEIGHT, character,  panel, home,
				homePanel, layout, homeLayout, wizPages);
	}

	public Composite getWiz5() { return wiz5; }

	public static void cancelClear() {
		CharacterWizard.reset();
		Wiz1.cancelClear();
		Wiz2.cancelClear();
		Wiz3.cancelClear();
		Wiz4.cancelClear();
		// TODO
	}
}
