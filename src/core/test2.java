package core;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import guis.CharacterWizard;

import java.awt.color.*;
import java.util.ArrayList;

public class test2 {

	int pageNum = -1;
	
	private Device dev;
	private Display display;
	private Shell shell;
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;
	private int wizPageNum = -1;
	
	private Character character;
	
	public test2(Display d) { 
		display = d;
		shell = new Shell(d);
        shell.setText("CharacterWizard");
        shell.setSize(WIDTH,HEIGHT);
        character = new Character();
        
        createPageContent();

        run();
    }
	
	
	public void run() {
		center(shell);
		
        shell.open();

        while (!shell.isDisposed()) {
          if (!display.readAndDispatch()) {
            display.sleep();
          }
        }
	}
	
    private void center(Shell shell) {

        Rectangle bds = shell.getDisplay().getBounds();

        Point p = shell.getSize();

        int nLeft = (bds.width - p.x) / 2;
        int nTop = (bds.height - p.y) / 2;

        shell.setBounds(nLeft, nTop, p.x, p.y);
    }
	
	private void createPageContent() {
		final Composite homePanel = new Composite(shell,SWT.NONE);
		homePanel.setBounds(0,0,WIDTH,HEIGHT);
		final StackLayout homeLayout = new StackLayout();
		homePanel.setLayout(homeLayout);


		//////////////////// HOME SCREEN SETUP ////////////////////////////
		
		final Composite home = new Composite(homePanel, SWT.NONE);
		home.setBounds(0,0,WIDTH,HEIGHT);
		
		Label homeLabel = new Label(home, SWT.NONE);
		homeLabel.setText("Let's create a character!");
		Font font1 = new Font( homeLabel.getDisplay(), new FontData( "Arial", 24, SWT.BOLD ) );
		homeLabel.setFont(font1);
		homeLabel.setBounds(WIDTH/2 - 180, 40, 100,100);
		homeLabel.pack();
		
		Label homeLabel2 = new Label(home, SWT.NONE);
		homeLabel2.setText("\nChoose a method:");
		Font font2 = new Font( homeLabel.getDisplay(), new FontData( "Arial", 18, SWT.BOLD ) );
		homeLabel2.setFont(font2);
		homeLabel2.setBounds(WIDTH/2 - 100, 65, 100,100);
		homeLabel2.pack();
		
		Button wizardButton = new Button(home, SWT.PUSH);
		wizardButton.setText("Wizard");
		wizardButton.setFont(font2);
		wizardButton.setBounds(WIDTH/2-150,150,300,150);

		Button manualButton = new Button(home, SWT.PUSH);
		manualButton.setText("Manual");
		Font font3 = new Font( homeLabel.getDisplay(), new FontData( "Arial", 18, SWT.NONE ) );
		manualButton.setFont(font3);
		manualButton.setBounds(WIDTH/2 - 150 ,310,145,75);

		Button randomButton = new Button(home, SWT.PUSH);
		randomButton.setText("Random");
		randomButton.setFont(font3);
		randomButton.setBounds(WIDTH/2 + 5,310,145,75);

		// set home as the first screen viewed when char wizard is launched
		homeLayout.topControl = home;
		
		
		
		/////////////////// WIZARD SETUP /////////////////////////////////
		
		final Composite wizPanel = new Composite(homePanel,SWT.BORDER);
		wizPanel.setBounds(0,0,WIDTH,HEIGHT);
		final StackLayout wizLayout = new StackLayout();
		wizPanel.setLayout(wizLayout);
		wizPanel.setBackground(new Color(dev,255,0,0));
		
		
		/////////////////// MANUAL SETUP ////////////////////////////////
		
		final Composite manual = new Composite(homePanel,SWT.NONE);
		final Composite manualPanel = new Composite(manual,SWT.BORDER);
		wizPanel.setBounds(0,0,WIDTH,(int) (HEIGHT*(.75)));
		final StackLayout manualLayout = new StackLayout();
		manualPanel.setLayout(manualLayout);
		
		
		/////////////////// RANDOM SETUP ////////////////////////////////
		
		final Composite random = new Composite(homePanel,SWT.NONE);
		final Composite randomPanel = new Composite(random,SWT.BORDER);
		wizPanel.setBounds(0,0,WIDTH,(int) (HEIGHT*(.75)));
		final StackLayout randomLayout = new StackLayout();
		randomPanel.setLayout(randomLayout);
	    
		
		/////////////////// HOME BUTTON LISTENERS //////////////////////
		
		wizardButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				homeLayout.topControl = wizPanel;
				homePanel.layout();
			}
		});
		manualButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				homeLayout.topControl = manual;
				homePanel.layout();
			}
		});
		randomButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				homeLayout.topControl = random;
				homePanel.layout();
			}
		});
				
		

	
		
		
		///////////////////// WIZARD PAGES ////////////////////////////
	    
		final ArrayList<Composite> wizPages = new ArrayList<Composite>();
		
		/////////////////////////   1   ///////////////////////////////
		// choose level and ability scores
		final Composite wiz1 = new Composite(wizPanel,SWT.NONE);
		wizPageNum = 0;
		wizLayout.topControl = wiz1;
		wizPanel.layout();
		Label wiz1Label = new Label(wiz1, SWT.NONE);
		wiz1Label.setText("Select starting level and roll for ability scores");
		wiz1Label.pack();
		final Text wiz1LevelText = new Text(wiz1, SWT.BORDER);
		wiz1LevelText.setBounds(50,50,50,50);
		wiz1LevelText.setText("1");
		wiz1LevelText.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				System.out.println(wiz1LevelText.getText());
			}
		});
		
		
		
		
		wizPages.add(wiz1);
		
		/////////////////////////   2   //////////////////////////////
		// choose class and race
		final Composite wiz2 = new Composite(wizPanel,SWT.NONE);
		Label wiz2Label = new Label(wiz2, SWT.NONE);
		wiz2Label.setText("Select Class and Race");
		wiz2Label.pack();
		wizPages.add(wiz2);

		/////////////////////////   3   //////////////////////////////
		// apply ability scores
		final Composite wiz3 = new Composite(wizPanel,SWT.NONE);
		Label wiz3Label = new Label(wiz3, SWT.NONE);
		wiz3Label.setText("Apply Ability Scores");
		wiz3Label.pack();
		wizPages.add(wiz3);
		
		/////////////////////////   4   //////////////////////////////
		// rank skills
		final Composite wiz4 = new Composite(wizPanel,SWT.NONE);
		Label wiz4Label = new Label(wiz4, SWT.NONE);
		wiz4Label.setText("Add Ranks to Skills");
		wiz4Label.pack();
		wizPages.add(wiz4);
		
		/////////////////////////   5   //////////////////////////////
		// choose feats
		final Composite wiz5= new Composite(wizPanel,SWT.NONE);
		Label wiz5Label = new Label(wiz5, SWT.NONE);
		wiz5Label.setText("Choose Feats");
		wiz5Label.pack();
		wizPages.add(wiz5);
		
		/////////////////////////   6   //////////////////////////////
		// add description
		final Composite wiz6 = new Composite(wizPanel,SWT.NONE);
		Label wiz6Label = new Label(wiz6, SWT.NONE);
		wiz6Label.setText("Add Description");
		wiz6Label.pack();
		wizPages.add(wiz6);
	    
		/////////////////////////   7   //////////////////////////////
		// choose equipment
		final Composite wiz7 = new Composite(wizPanel,SWT.NONE);
		Label wiz7Label = new Label(wiz7, SWT.NONE);
		wiz7Label.setText("Choose Equipment");
		wiz7Label.pack();
		wizPages.add(wiz7);
		
		/////////////////////////   8   //////////////////////////////
		// choose domain/specialty school
		final Composite wiz8 = new Composite(wizPanel,SWT.NONE);
		Label wiz8Label = new Label(wiz8, SWT.NONE);
		wiz8Label.setText("Choose Domain/Specialty School");
		wiz8Label.pack();
		wizPages.add(wiz8);
		
		/////////////////////////   9   //////////////////////////////
		// select known spells
		final Composite wiz9 = new Composite(wizPanel,SWT.NONE);
		Label wiz9Label = new Label(wiz9, SWT.NONE);
		wiz9Label.setText("Select Known Spells");
		wiz9Label.pack();
		wizPages.add(wiz9);
		
		/////////////////////////   10   /////////////////////////////
		// Done
		final Composite wiz10 = new Composite(wizPanel,SWT.NONE);
		Label wiz10Label = new Label(wiz10, SWT.NONE);
		wiz10Label.setText("Done!");
		wiz10Label.pack();
		wizPages.add(wiz10);
		

		///////////// WIZARD BACK/NEXT/CANCEL BUTTONS ////////////////////
		
	    Button nextButton = new Button(wizPanel, SWT.PUSH);
	    nextButton.setText("Next");
	    nextButton.setBounds(WIDTH-117,HEIGHT-90, 100, 50);
	    nextButton.addListener(SWT.Selection, new Listener() {
	      public void handleEvent(Event event) {
	        if (wizPageNum < wizPages.size() - 1)
	        	wizPageNum++;
	        wizLayout.topControl = wizPages.get(wizPageNum);
	        wizPanel.layout();
	      }
	    });
	    
	    Button backButton = new Button(wizPanel, SWT.PUSH);
	    backButton.setText("Back");
	    backButton.setBounds(WIDTH-220,HEIGHT-90, 100, 50);
	    backButton.addListener(SWT.Selection, new Listener() {
	      public void handleEvent(Event event) {
	    	if (wizPageNum > 0)
	    		wizPageNum--;
	        wizLayout.topControl = wizPages.get(wizPageNum);
	        wizPanel.layout();
	      }
	    });
		
	    Button cancelButton = new Button(wizPanel, SWT.PUSH);
	    cancelButton.setText("Cancel");
	    cancelButton.setBounds(10,HEIGHT-90, 100, 50);
	    cancelButton.addListener(SWT.Selection, new Listener() {
	      public void handleEvent(Event event) {
	        wizPageNum = -1;
	        //wizLayout.topControl = page0;
	        homeLayout.topControl = home;
	        homePanel.layout();
	      }
	    }); // TODO do more here: prompt yes/no, discard all changes

	    
	}
	
    public static void main(String[] args) {
        Display display = new Display();
        CharacterWizard cw = new CharacterWizard(display);
        display.dispose();
    }
    
}
