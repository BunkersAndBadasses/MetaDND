
package guis;
import org.apache.batik.swing.JSVGCanvas;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import core.DungeonConstants;
import core.DungeonGenerator;
import core.GridMapper;

import java.awt.FlowLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileSystemView;

/*
 * iteration 2: 
 * 
 * back button
 * starting at level > 1
 * adding custom skills
 */

public class HomeWindow {
 
	private static Display display;
	private Shell shell;
	private static final int WIDTH = 900;
	private static final int HEIGHT = 700;
	public static boolean cancel = false;
	private File savedDungeonsLoc;

	public static int[] baseAbilityScores = new int[6];

	public HomeWindow(Display d) {
		display = d;
		shell = new Shell(d);
		shell.setText("Meta D&D");
		shell.setSize(WIDTH, HEIGHT);
		shell.setLayout(new GridLayout(3, false));
		
		JFileChooser fr = new JFileChooser();
	    FileSystemView fw = fr.getFileSystemView();
	    File tmp = new File(fw.getDefaultDirectory(), "MetaDnD");
	    if (!tmp.exists()) {
	    	tmp.mkdir();
	    }
	    savedDungeonsLoc = tmp;
	    
		new MenuBar(shell); //Add menu bar to windows like this
		
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

	private static void center(Shell shell) {

		Rectangle bds = shell.getDisplay().getBounds();

		Point p = shell.getSize();

		int nLeft = (bds.width - p.x) / 2;
		int nTop = (bds.height - p.y) / 2;

		shell.setBounds(nLeft, nTop, p.x, p.y);
	}

	private void createPageContent() {

		
		// the stack layout allows us to navigate from one view to another.
		final Composite mainWindow = new Composite(shell, SWT.NONE);
		mainWindow.setLayoutData(new GridData(GridData.FILL_BOTH));
        final StackLayout mainWindowLayout = new StackLayout();
        mainWindow.setLayout(mainWindowLayout);
        
        final Composite homeScreen = new Composite(mainWindow, SWT.NONE);
        final Composite dungeonScreen = new Composite(mainWindow, SWT.NONE);
        final Composite dungeonViewer = new Composite(mainWindow, SWT.EMBEDDED);
        final Composite dungeonGenConfig = new Composite(mainWindow, SWT.NONE);
        
        // this grid layout size allows us to have permanent centering of these buttons,
        // regardless of user resize.
        GridLayout homeScreenLayout = new GridLayout(6, true);
        homeScreen.setLayout(homeScreenLayout);
        
        GridLayout dungeonScreenLayout = new GridLayout(1, true);
        dungeonScreenLayout.marginLeft = 10;
        dungeonScreen.setLayout(dungeonScreenLayout);
        
        GridLayout dungeonGenConfigLayout = new GridLayout(2, true);
        dungeonGenConfig.setLayout(dungeonGenConfigLayout);
       
        ///////////////////HOME SCREEN//////////////////////////
        
        // placeholder labels take up columns 1 and 2 in the grid.
        new Label(homeScreen, SWT.NONE);  
        new Label(homeScreen, SWT.NONE);  
        
        // default size of the buttons is 200 by 100
        // the buttons occupy columns 3 and 4 in the grid.
        GridData playersGD = new GridData();
        playersGD.grabExcessHorizontalSpace = true;
        playersGD.grabExcessVerticalSpace = true;
        playersGD.widthHint = 200;
        playersGD.heightHint = 100;
        
        Button playersButton = new Button(homeScreen, SWT.PUSH); 
        playersButton.setLayoutData(playersGD);
        playersButton.setText("Players");
        playersButton.setSize(new Point(300, 400));
        playersButton.pack();
        
        // each element should have its own griddata object.
        GridData dungeonMastersGD = new GridData();
        dungeonMastersGD.grabExcessHorizontalSpace = true;
        dungeonMastersGD.grabExcessVerticalSpace = true;
        dungeonMastersGD.widthHint = 200;
        dungeonMastersGD.heightHint = 100;
        
        Button dungeonMastersButton = new Button(homeScreen, SWT.PUSH);
        dungeonMastersButton.setLayoutData(dungeonMastersGD);
        dungeonMastersButton.setText("Dungeon Masters");
        dungeonMastersButton.pack();

        // placeholder labels for columns 5 and 6
        new Label(homeScreen, SWT.NONE);  
        new Label(homeScreen, SWT.NONE);  

        ///////////////////HOME SCREEN//////////////////////////
        
        
        ///////////////////DUNGEON SCREEN//////////////////////////
        
        Label dungeonLabel = new Label(dungeonScreen, SWT.NONE);
		dungeonLabel.setText("Dungeons:");
		Font dungeonFont = new Font(dungeonLabel.getDisplay(), new FontData("Arial", 18,
				SWT.BOLD));
		dungeonLabel.setFont(dungeonFont);
		
		// placeholder labels take up columns 2 and 3 in the grid.
        new Label(dungeonScreen, SWT.NONE);  
        new Label(dungeonScreen, SWT.NONE); 
        
        // generate new dungeon
		Button generateButton = new Button(dungeonScreen, SWT.PUSH);
		generateButton.setText("Generate New");
		generateButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				mainWindowLayout.topControl = dungeonGenConfig;
				mainWindow.layout();
			}
		});
        
        dungeonScreen.pack();
        
        ///////////////////DUNGEON SCREEN//////////////////////////    
        
        ///////////////////DUNGEON VIEWER//////////////////////////   
        
        final JSVGCanvas svgCanvas = new JSVGCanvas();
        
        // embed the swing element in the swt composite
        java.awt.Frame fileTableFrame = SWT_AWT.new_Frame(dungeonViewer);
        java.awt.Panel panel = new java.awt.Panel(new java.awt.BorderLayout());
        JScrollPane jsp = new JScrollPane(svgCanvas);
        fileTableFrame.add(panel);
        panel.add(jsp);
        ///////////////////DUNGEON VIEWER////////////////////////// 
        
        ///////////////////DUNGEON GENCONFIG////////////////////////// 
        Label sizeLabel = new Label(dungeonGenConfig, SWT.NONE);
        sizeLabel.setText("Size of Dungeon:");
        Label densityLabel = new Label(dungeonGenConfig, SWT.NONE);
        densityLabel.setText("Density of Passable Terrain:");
        
        final Scale sizeSlider = new Scale(dungeonGenConfig, SWT.NULL);
        sizeSlider.setIncrement(1);
        sizeSlider.setMaximum(DungeonConstants.MAX_DUNGEON_SIZE);
        sizeSlider.setMinimum(DungeonConstants.MIN_DUNGEON_SIZE);
        sizeSlider.setSelection(30);
        sizeSlider.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
        
        final Scale densitySlider = new Scale(dungeonGenConfig, SWT.NULL);
        densitySlider.setIncrement(1);
        densitySlider.setMaximum(50);
        densitySlider.setMinimum(10);
        densitySlider.setSelection(20);
        dungeonGenConfig.pack();
        
        Button cancelButton = new Button(dungeonGenConfig, SWT.NONE);
        cancelButton.setText("Cancel");
        cancelButton.addListener(SWT.Selection, new Listener() {
        	public void handleEvent(Event event) {
        		mainWindowLayout.topControl = dungeonScreen;
        		mainWindow.layout();
        	}
        });
        
        // confirm generation configuration
        Button confirmButton = new Button(dungeonGenConfig, SWT.NONE);
        confirmButton.setText("Confirm");
        confirmButton.addListener(SWT.Selection, new Listener() {
        	public void handleEvent(Event event) {
        		int sizeSelection = sizeSlider.getSelection();
        		int densitySelection = densitySlider.getSelection();
        		double density = 1 - ((double)densitySelection/100);
        		
        		System.out.println(sizeSelection);
        		System.out.println(density);
        		
        		DungeonGenerator rdg = new DungeonGenerator(sizeSelection, density);
        		rdg.GenerateDungeon();
        		rdg.printDungeon(true);

        		GridMapper gm = new GridMapper("generatedDungeon.txt");
        		gm.generateSVG();
        		
        		svgCanvas.setURI("file:///C:\\Users\\Curt\\Workspace\\MetaDND\\generatedDungeon.svg");
        		mainWindowLayout.topControl = dungeonViewer;
        		mainWindow.layout();
        	}
        });
        
		
		/////////////////////NESTED BUTTON LISTENERS////////////////////////
				
		dungeonMastersButton.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event event) {
			final List dungeonList = new List(dungeonScreen, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
			int counter = 0;
			
			// populate the list
			for (String s: savedDungeonsLoc.list()) {
				dungeonList.add(s);
				counter++;
			}
			for (int i = counter; i < 20; i++) {
				dungeonList.add("");
			}
			
			// make the list look good.
			GridData listGD = new GridData();
			listGD.grabExcessHorizontalSpace = true;
			listGD.grabExcessVerticalSpace = true;
			listGD.widthHint = 400;
			listGD.heightHint = 500;
			dungeonList.setLayoutData(listGD);
			
			// placeholder labels to make it look gooder
			new Label(dungeonScreen, SWT.NONE);  
		    new Label(dungeonScreen, SWT.NONE); 
		    new Label(dungeonScreen, SWT.NONE);  
		    new Label(dungeonScreen, SWT.NONE); 
		    
		    // load dungeon
			Button loadButton = new Button(dungeonScreen, SWT.PUSH);
			loadButton.setText("Load Dungeon");
			loadButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					
					String toSet = "file:///";
					toSet += savedDungeonsLoc.toString() + "//" + dungeonList.getSelection()[dungeonList.getSelectionIndex()];
					svgCanvas.setURI(toSet);
					mainWindowLayout.topControl = dungeonViewer;
					mainWindow.layout();
				}
			});
			
			dungeonScreen.pack();
			mainWindowLayout.topControl = dungeonScreen;
			mainWindow.layout();
		}
		});
		
		/////////////////////BUTTON LISTENERS////////////////////////
		mainWindowLayout.topControl = homeScreen;

        
		
		
	}
	

	public static void main(String[] args) {
		Display display = new Display();
		HomeWindow hw = new HomeWindow(display);
		display.dispose();
	}

}
