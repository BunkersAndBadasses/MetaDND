package guis;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import entity.*;
import core.character;

public class Wiz8{

	private static Composite wiz8;
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

	public Wiz8(Device dev, int WIDTH, int HEIGHT, final character character, 
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz8 = wizPages.get(7);
		Wiz8.dev = dev;
		Wiz8.WIDTH = WIDTH;
		Wiz8.HEIGHT = HEIGHT;
		Wiz8.character = character;
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.nextPage = wizPages.get(8);
		this.wizPagesSize = wizPages.size();

		createPageContent();
	}

	private void createPageContent() {
		Label wiz8Label = new Label(wiz8, SWT.NONE);
		wiz8Label.setText("Choose Domain/Specialty School");
		wiz8Label.pack();

		Button wiz8NextButton = CharacterWizard.createNextButton(wiz8);
		wiz8NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.wizPageNum < wizPagesSize - 1)
					CharacterWizard.wizPageNum++;
				if (!CharacterWizard.wizPageCreated[8])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});

		//Button wiz8BackButton = CharacterWizard.createBackButton(wiz8, panel, layout);
		Button wiz8CancelButton = CharacterWizard.createCancelButton(wiz8, home, homePanel, homeLayout);
		wiz8CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.cancel)
					cancelClear();
			}
		});
	}

	private void createNextPage() {
		CharacterWizard.wizPageCreated[8] = true;
		CharacterWizard.wizs.add(new Wiz9(dev, WIDTH, HEIGHT, character, panel, home,
				homePanel, layout, homeLayout, wizPages));
	}

	public Composite getWiz8() { return wiz8; }

	public void cancelClear() {
		CharacterWizard.reset();
		((Wiz1)CharacterWizard.wizs.get(0)).cancelClear();
		((Wiz2)CharacterWizard.wizs.get(1)).cancelClear();
		((Wiz3)CharacterWizard.wizs.get(2)).cancelClear();
		((Wiz4)CharacterWizard.wizs.get(3)).cancelClear();
		((Wiz5)CharacterWizard.wizs.get(4)).cancelClear();
		((Wiz6)CharacterWizard.wizs.get(5)).cancelClear();
		((Wiz7)CharacterWizard.wizs.get(6)).cancelClear();
	}
}