package guis;

import java.awt.Rectangle;
import java.io.IOException;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.JSVGScrollPane;
import org.apache.batik.util.XMLResourceDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import core.DungeonConstants;
import core.DungeonGenerator;
import core.GameState;
import core.GridMapper;


public class DungeonViewer {

	private Display m_display;
	private Shell m_shell;
	private final Composite m_view;
	private final Composite m_embeddedView;
	private final JSVGCanvas svgCanvas = new JSVGCanvas();
	private HomeWindow m_hw;
	
	
	public DungeonViewer(HomeWindow hw, int sizeSelection, double density) {
		
		this.m_hw = hw;
		
		m_display = Display.getCurrent();
		if (m_display == null ) {
			m_display = new Display();
		}
		m_shell = new Shell(m_display);
		Image logo = new Image(m_display, "images/bnb_logo.gif");
		m_shell.setImage(logo);
		m_shell.setText("Meta D&D " );
		m_shell.setLayout(new GridLayout(3, false));
		m_shell.setText("Dungeon Viewer");		
		
		m_view = new Composite(m_shell, SWT.NONE);
		m_embeddedView = new Composite(m_view, SWT.EMBEDDED);
		m_view.setLayoutData(new GridData(GridData.FILL_BOTH));
		m_view.setLayout(new GridLayout(1, true));
		
		GridLayout gridLayout = new GridLayout(1, false);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		m_embeddedView.setLayoutData(gridData);
		m_embeddedView.setLayout(gridLayout);
		
		
		// this is the size at which the generator works best
		int sizeOfSquare = 30;
		

		DungeonGenerator rdg = new DungeonGenerator(sizeSelection, density);
		rdg.GenerateDungeon();
		rdg.printDungeon(true);

		GridMapper gm = new GridMapper(DungeonConstants.SAVEDDUNGEONSDIR.toString() + "//generatedDungeon.bnb", sizeOfSquare); 
		gm.generateSVG();

		String toSet = "file:///";
		toSet += DungeonConstants.SAVEDDUNGEONSDIR.toString() + "//generatedDungeon.svg";
		svgCanvas.setURI(toSet);
		
		
		
		configureContent();
		
		run();
	}
	
	public DungeonViewer(String svgToLoad) {
		m_display = Display.getCurrent();
		if (m_display == null ) {
			m_display = new Display();
		}
		m_shell = new Shell(m_display);
		Image logo = new Image(m_display, "images/bnb_logo.gif");
		m_shell.setImage(logo);
		m_shell.setText("Meta D&D " );
		m_shell.setSize(1000, 1000);
		m_shell.setLayout(new GridLayout(3, false));
		m_shell.setText("Dungeon Viewer");		
		
		
		
		m_view = new Composite(m_shell, SWT.NONE);
		m_embeddedView = new Composite(m_view, SWT.EMBEDDED);
		m_view.setLayoutData(new GridData(GridData.FILL_BOTH));
		m_view.setLayout(new GridLayout(1, true));
		
		GridLayout gridLayout = new GridLayout(1, false);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		m_embeddedView.setLayoutData(gridData);
		m_embeddedView.setLayout(gridLayout);
		
		svgCanvas.setURI(svgToLoad);
		
		configureContent();
		
		run();
		
	}
	
	private void configureContent() {
	
		m_shell.setSize(GameState.DEFAULT_WIDTH, GameState.DEFAULT_HEIGHT);
		
		///////////////////DUNGEON VIEWER//////////////////////////   
		
		// embed the swing element in the swt composite
		java.awt.Frame fileTableFrame = SWT_AWT.new_Frame(m_embeddedView);
		java.awt.Panel panel = new java.awt.Panel(new java.awt.BorderLayout());
		
		JSVGScrollPane jsvgsp = new JSVGScrollPane(svgCanvas);
		
		fileTableFrame.add(panel);
		panel.add(jsvgsp);
		
		new MenuBar(m_shell, m_hw, GameState.PAGE.DungeonViewerScreen);
		
		
		///////////////////DUNGEON VIEWER////////////////////////// 
	}
	
	public void run() {
		m_shell.open();

		while (!m_shell.isDisposed()) {
			if (!m_display.readAndDispatch()) {
				m_display.sleep();
			}
		}
	}
	
}
