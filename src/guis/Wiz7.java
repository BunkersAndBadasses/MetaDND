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

		createPageContent();
	}

	private void createPageContent() {
		Label wiz7Label = new Label(wiz7, SWT.NONE);
		wiz7Label.setText("Choose Equipment");
		wiz7Label.pack();

		Button wiz7NextButton = CharacterWizard.createNextButton(wiz7);
		wiz7NextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.wizPageNum < wizPagesSize - 1)
					CharacterWizard.wizPageNum++;
				if (!CharacterWizard.wizPageCreated[7])
					createNextPage();
				layout.topControl = nextPage;
				panel.layout();
			}
		});

		Button wiz7BackButton = CharacterWizard.createBackButton(wiz7, panel, layout);
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
		new Wiz8(dev, WIDTH, HEIGHT, character, panel, home,
				homePanel, layout, homeLayout, wizPages);
	}

	public Composite getWiz7() { return wiz7; }

	public static void cancelClear() {
		CharacterWizard.reset();
		Wiz1.cancelClear();
		Wiz2.cancelClear();
		Wiz3.cancelClear();
		Wiz4.cancelClear();
		Wiz5.cancelClear();
		Wiz6.cancelClear();
	}
}
