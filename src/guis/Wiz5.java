package guis;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import entity.*;
import core.Character;

public class Wiz5 {

	private static Composite wiz5;
	private static Device dev;
	private static int WIDTH;
	private static int HEIGHT;
	private static Character character;
	private Composite panel;
	private Composite home;
	private Composite homePanel;
	private StackLayout layout;
	private StackLayout homeLayout;
	private ArrayList<Composite> wizPages;
	private Composite nextPage;
	private int wizPagesSize;
	
	private static Label numFeatsLabel;

	public Wiz5(Device dev, int WIDTH, int HEIGHT, final Character character, 
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
		
		
		final ScrolledComposite featScreenScroll = new ScrolledComposite(wiz5, SWT.V_SCROLL | SWT.BORDER);
		featScreenScroll.setBounds(10, 110, WIDTH/2 - 15, HEIGHT - 210);
	    featScreenScroll.setExpandHorizontal(true);
	    featScreenScroll.setExpandVertical(true);
	    featScreenScroll.setMinSize(WIDTH, HEIGHT);
		final Composite featListScreen = new Composite(featScreenScroll, SWT.NONE);
		featScreenScroll.setContent(featListScreen);
		featListScreen.setSize(featListScreen.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		final Composite featScreen = new Composite (wiz5, SWT.BORDER);
		featScreen.setBounds(WIDTH/2 + 5, 110, WIDTH/2 - 25, HEIGHT - 210);
		
		
		

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
