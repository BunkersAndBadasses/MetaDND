package guis;

import core.Character;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;



public class Wiz10 {

	private static Composite wiz10;
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
	private int wizPagesSize;

	public Wiz10(Device dev, int WIDTH, int HEIGHT, final Character character, 
			final Composite panel, Composite home, Composite homePanel, 
			final StackLayout layout, final StackLayout homeLayout, 
			final ArrayList<Composite> wizPages) {
		wiz10 = wizPages.get(9);
		Wiz10.dev = dev;
		Wiz10.WIDTH = WIDTH;
		Wiz10.HEIGHT = HEIGHT;
		Wiz10.character = character;
		this.panel = panel;
		this.home = home;
		this.homePanel = homePanel;
		this.layout = layout;
		this.homeLayout = homeLayout;
		this.wizPages = wizPages;
		this.wizPagesSize = wizPages.size();

		createPageContent();
	}
	
	private void createPageContent() {
		Label wiz10Label = new Label(wiz10, SWT.NONE);
		wiz10Label.setText("Done!");
		wiz10Label.pack();

		Button wiz10SaveButton = new Button(wiz10, SWT.PUSH);
		wiz10SaveButton.setText("Save");
		wiz10SaveButton.setBounds(WIDTH-117,HEIGHT-90, 100, 50);
		wiz10SaveButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// save
			}
		});

		Button wiz10BackButton = CharacterWizard.createBackButton(wiz10, panel, layout);
		Button wiz10CancelButton = CharacterWizard.createCancelButton(wiz10, home, homePanel, homeLayout);
		wiz10CancelButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (CharacterWizard.cancel)
					cancelClear();
			}
		});
	}
	
	public Composite getWiz10() { return wiz10; }
	
	public static void cancelClear() {
		CharacterWizard.character = new Character();
		Wiz1.cancelClear();
		Wiz2.cancelClear();
		Wiz3.cancelClear();
		Wiz4.cancelClear();
		Wiz5.cancelClear();
		Wiz6.cancelClear();
		Wiz7.cancelClear();
		Wiz8.cancelClear();
		Wiz9.cancelClear();
	}
}
