package guis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;

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
		int numFeats = 1;
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
		GridLayout featLayout = new GridLayout();
		featLayout.numColumns = 1;
		
		// create scrollable list of feats
		final ScrolledComposite featScreenScroll = new ScrolledComposite(wiz5, SWT.V_SCROLL | SWT.BORDER);
		featScreenScroll.setBounds(10, 110, WIDTH/2 - 15, HEIGHT - 210);
	    featScreenScroll.setExpandHorizontal(true);
	    featScreenScroll.setExpandVertical(true);
	    featScreenScroll.setMinSize(WIDTH, HEIGHT);
		final Composite featListScreen = new Composite(featScreenScroll, SWT.NONE);
		featScreenScroll.setContent(featListScreen);
		featListScreen.setLayout(featLayout);
		featListScreen.setSize(featListScreen.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		// create scrollable list of selected feats
		final ScrolledComposite charFeatScreenScroll = new ScrolledComposite(wiz5, SWT.V_SCROLL | SWT.BORDER);
		charFeatScreenScroll.setBounds(WIDTH/2 + 5, 110, WIDTH/2 - 25, HEIGHT - 210);
	    charFeatScreenScroll.setExpandHorizontal(true);
	    charFeatScreenScroll.setExpandVertical(true);
	    charFeatScreenScroll.setMinSize(WIDTH, HEIGHT);
		final Composite charFeatScreen = new Composite (charFeatScreenScroll, SWT.BORDER);
		charFeatScreen.setLayout(featLayout);
		charFeatScreenScroll.setContent(charFeatScreen);
		charFeatScreen.setSize(charFeatScreen.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		// available feats list
		List featsList = new List(featListScreen, SWT.NONE);
		for (int i = 0; i < feats.size(); i++) {
			featsList.add(feats.get(i).getName());
			System.out.println(feats.get(i).getName());
		}
		featsList.setLayoutData(new GridData());
		
		// selected feats list
		List charFeatsList = new List(charFeatScreen, SWT.NONE);
		
		// add feat button
		Button addButton = new Button(wiz5, SWT.PUSH);
		addButton.setText("Add");
		addButton.setLocation(WIDTH/2 - 100, 60);
		addButton.pack();
		addButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				boolean error = false;
				int index = featsList.getSelectionIndex();
				if (index == -1)
					error = true;
				if (charFeats.contains(featsList.getItem(index)))
					error = true;
				if (error)
					return;
				charFeatsList.add(featsList.getItem(index));
				charFeats.add(feats.get(index));
			}
		});
		
		// remove feat button
		Button removeButton = new Button(wiz5, SWT.PUSH);
		removeButton.setText("Remove");
		removeButton.setLocation(WIDTH/2 + 10, 60);
		removeButton.pack();
		
		

		Button wiz5NextButton = CharacterWizard.createNextButton(wiz5);
		wiz5NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
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
