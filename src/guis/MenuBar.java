package guis;
import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import core.GameState.PAGE;
import core.DungeonConstants;
import core.Main;


public class MenuBar {
	
	private Shell m_shell;
	
	public MenuBar(final Shell shell, HomeWindow parent, PAGE page)
	{
		m_shell = shell;
		
		Menu menuBar = new Menu(shell, SWT.BAR);
        MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeFileMenu.setText("&File");
        
        // File Menu
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeFileMenu.setMenu(fileMenu);
        
        
        
        MenuItem cascadeNavigateMenu;
        Menu navigateMenu;
        MenuItem homeScreen;
        MenuItem player;
        MenuItem dungeonMaster;
        MenuItem open;
        switch (page) {
			case HomeScreen:
				cascadeNavigateMenu = new MenuItem(menuBar, SWT.CASCADE);
			    cascadeNavigateMenu.setText("&Navigate");
			    
			    //Navigate Menu
		        navigateMenu = new Menu(shell, SWT.DROP_DOWN);
		        cascadeNavigateMenu.setMenu(navigateMenu);
		        
		        // Home screen
		        homeScreen = new MenuItem(navigateMenu, SWT.PUSH);
		        homeScreen.setText("&Home");
		        
		        homeScreen.addSelectionListener(new SelectionAdapter()
		        {
		        public void widgetSelected(SelectionEvent e)
		        {
		        	
		        	parent.navigateToHomeScreen();
		        }});
		        
		        //Player screen
		        player = new MenuItem(navigateMenu, SWT.PUSH);
		        player.setText("&Players");
		        

		        player.addSelectionListener(new SelectionAdapter() {
		            @Override
		            public void widgetSelected(SelectionEvent e) {
		            	HomeWindow.loadCharacters();
		                parent.navigateToPlayerScreen();
		            }
		        });
		        
		        //Dungeon Master screen
		        dungeonMaster = new MenuItem(navigateMenu, SWT.PUSH);
		        dungeonMaster.setText("&Dungeon Masters");
		        

		        dungeonMaster.addSelectionListener(new SelectionAdapter() {
		            @Override
		            public void widgetSelected(SelectionEvent e) {
		                parent.navigateToDungeonMasterScreen();
		            }
		        });
		        
				break;
			case DungeonMasterScreen:
				
				//Open
		        open = new MenuItem(fileMenu, SWT.PUSH);
		        open.setText("&Open");
	
		        open.addSelectionListener(new SelectionAdapter() {
		            @Override
		            public void widgetSelected(SelectionEvent e) {
		            	FileDialog dialog = new FileDialog(m_shell, SWT.OPEN);
		            	dialog.setText("Open");
		            	dialog.setFilterPath(DungeonConstants.SAVEDDUNGEONSDIR.toString());
		                String[] filterExt = { "*.svg"};
		                dialog.setFilterExtensions(filterExt);
		                String selected = dialog.open();
		                if (selected == null || selected.equals("")) {
		                	return;
		                }
						String svgToLoad = "file:///" + selected;
		                
		                new DungeonViewer(svgToLoad);
		            }
		        });
				
				
				cascadeNavigateMenu = new MenuItem(menuBar, SWT.CASCADE);
			    cascadeNavigateMenu.setText("&Navigate");
			    
			    //Navigate Menu
		        navigateMenu = new Menu(shell, SWT.DROP_DOWN);
		        cascadeNavigateMenu.setMenu(navigateMenu);
		        
		        // Home screen
		        homeScreen = new MenuItem(navigateMenu, SWT.PUSH);
		        homeScreen.setText("&Home");
		        
		        homeScreen.addSelectionListener(new SelectionAdapter()
		        {
		        public void widgetSelected(SelectionEvent e)
		        {
		        	
		        	parent.navigateToHomeScreen();
		        }});
		        
		        //Player screen
		        player = new MenuItem(navigateMenu, SWT.PUSH);
		        player.setText("&Players");
		        

		        player.addSelectionListener(new SelectionAdapter() {
		            @Override
		            public void widgetSelected(SelectionEvent e) {
		            	HomeWindow.loadCharacters();
		                parent.navigateToPlayerScreen();
		            }
		        });
		        
		        //Dungeon Master screen
		        dungeonMaster = new MenuItem(navigateMenu, SWT.PUSH);
		        dungeonMaster.setText("&Dungeon Masters");
		        

		        dungeonMaster.addSelectionListener(new SelectionAdapter() {
		            @Override
		            public void widgetSelected(SelectionEvent e) {
		                parent.navigateToDungeonMasterScreen();
		            }
		        });
				break;
			case PlayerScreen:
				cascadeNavigateMenu = new MenuItem(menuBar, SWT.CASCADE);
			    cascadeNavigateMenu.setText("&Navigate");
			    
			    //Navigate Menu
		        navigateMenu = new Menu(shell, SWT.DROP_DOWN);
		        cascadeNavigateMenu.setMenu(navigateMenu);
		        
		        // Home screen
		        homeScreen = new MenuItem(navigateMenu, SWT.PUSH);
		        homeScreen.setText("&Home");
		        
		        homeScreen.addSelectionListener(new SelectionAdapter()
		        {
		        public void widgetSelected(SelectionEvent e)
		        {
		        	
		        	parent.navigateToHomeScreen();
		        }});
		        
		        //Player screen
		        player = new MenuItem(navigateMenu, SWT.PUSH);
		        player.setText("&Players");
		        

		        player.addSelectionListener(new SelectionAdapter() {
		            @Override
		            public void widgetSelected(SelectionEvent e) {
		            	HomeWindow.loadCharacters();
		                parent.navigateToPlayerScreen();
		            }
		        });
		        
		        //Dungeon Master screen
		        dungeonMaster = new MenuItem(navigateMenu, SWT.PUSH);
		        dungeonMaster.setText("&Dungeon Masters");
		        

		        dungeonMaster.addSelectionListener(new SelectionAdapter() {
		            @Override
		            public void widgetSelected(SelectionEvent e) {
		                parent.navigateToDungeonMasterScreen();
		            }
		        });
				//TODO: Matt, populate this
				break;
			case DungeonGenerationConfigScreen:
				cascadeNavigateMenu = new MenuItem(menuBar, SWT.CASCADE);
			    cascadeNavigateMenu.setText("&Navigate");
			    
			    //Navigate Menu
		        navigateMenu = new Menu(shell, SWT.DROP_DOWN);
		        cascadeNavigateMenu.setMenu(navigateMenu);
		        
		        // Home screen
		        homeScreen = new MenuItem(navigateMenu, SWT.PUSH);
		        homeScreen.setText("&Home");
		        
		        homeScreen.addSelectionListener(new SelectionAdapter()
		        {
		        public void widgetSelected(SelectionEvent e)
		        {
		        	
		        	parent.navigateToHomeScreen();
		        }});
		        
		        //Player screen
		        player = new MenuItem(navigateMenu, SWT.PUSH);
		        player.setText("&Players");
		        

		        player.addSelectionListener(new SelectionAdapter() {
		            @Override
		            public void widgetSelected(SelectionEvent e) {
		            	HomeWindow.loadCharacters();
		                parent.navigateToPlayerScreen();
		            }
		        });
		        
		        //Dungeon Master screen
		        dungeonMaster = new MenuItem(navigateMenu, SWT.PUSH);
		        dungeonMaster.setText("&Dungeon Masters");
		        

		        dungeonMaster.addSelectionListener(new SelectionAdapter() {
		            @Override
		            public void widgetSelected(SelectionEvent e) {
		                parent.navigateToDungeonMasterScreen();
		            }
		        });
				break;
			case DungeonViewerScreen:
				
				//Open
		        open = new MenuItem(fileMenu, SWT.PUSH);
		        open.setText("&Open");
	
		        open.addSelectionListener(new SelectionAdapter() {
		            @Override
		            public void widgetSelected(SelectionEvent e) {
		            	FileDialog dialog = new FileDialog(m_shell, SWT.OPEN);
		            	dialog.setText("Open");
		            	dialog.setFilterPath(DungeonConstants.SAVEDDUNGEONSDIR.toString());
		                String[] filterExt = { "*.svg"};
		                dialog.setFilterExtensions(filterExt);
		                String selected = dialog.open();
		                if (selected == null || selected.equals("")) {
		                	return;
		                }
		                String svgToLoad = "file:///" + selected;
		                
		                new DungeonViewer(svgToLoad);
		            }
		        });
		        
				//Save
		        MenuItem saveItem = new MenuItem(fileMenu, SWT.PUSH);
		        saveItem.setText("&Save");
	
		        saveItem.addSelectionListener(new SelectionAdapter() {
		            @Override
		            public void widgetSelected(SelectionEvent e) {
		            	FileDialog dialog = new FileDialog(m_shell, SWT.SAVE);
		            	dialog.setText("Save");
		            	dialog.setFilterPath(DungeonConstants.SAVEDDUNGEONSDIR.toString());
		                String[] filterExt = { "*.svg"};
		                dialog.setFilterExtensions(filterExt);
		                String selected = dialog.open();
		                if (selected == null || selected.equals("")) {
		                	return;
		                }
		                File toBeRenamed = new File(DungeonConstants.SAVEDDUNGEONSDIR, "generatedDungeon.svg");
		                File newFile = new File(selected);
						try {
							if(newFile.exists()) throw new java.io.IOException("file exists");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						boolean success = toBeRenamed.renameTo(newFile);
					    if (!success) {
					        System.out.println("fail");
					    }
		            }
		        });
		        break;
			case CharacterSheetScreen:
				break;
			default:
				break;
		}
        
        MenuItem cascadeToolsMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeToolsMenu.setText("&Tools");
        
        //Tools Menu
        Menu toolsMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeToolsMenu.setMenu(toolsMenu);
		
        //Help
        MenuItem helpItem = new MenuItem(fileMenu, SWT.PUSH);
        helpItem.setText("&Help");
        helpItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	//TODO: POPULATE
            }
        });
        
        //Exit
        MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
        exitItem.setText("&Exit");
        

        exitItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	shell.getDisplay().dispose();
                Main.exitProgram();
            }
        });
        
        
      //Die Roller
        MenuItem dieRollerItem = new MenuItem(toolsMenu, SWT.PUSH);
        dieRollerItem.setText("&Die Roller");
        

        dieRollerItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	new DieWindow(shell.getDisplay());
            }
        });
        
        //Notepad
        MenuItem notepadItem = new MenuItem(toolsMenu, SWT.PUSH);
        notepadItem.setText("&Notepad");
        

        notepadItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	new Notepad();
            }
        });
        
        //Spell Wizard
        MenuItem spellWizardItem = new MenuItem(toolsMenu, SWT.PUSH);
        spellWizardItem.setText("&Spell Wizard");
        

        spellWizardItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	new SpellWizard(shell.getDisplay());
            }
        });
        
        //Item Wizard
        MenuItem itemWizardItem = new MenuItem(toolsMenu, SWT.PUSH);
        itemWizardItem.setText("&Item Wizard");
        

        itemWizardItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	new ItemWizard(shell.getDisplay());
            }
        });
        
        //Ability Wizard
        MenuItem itemAbilityItem = new MenuItem(toolsMenu, SWT.PUSH);
        itemAbilityItem.setText("&Ability Wizard");
        
        itemAbilityItem.addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e)
        	{
        		new AbilityWizard(shell.getDisplay());
        	}
        });
        
        //Feat Wizard
        MenuItem itemFeatItem = new MenuItem(toolsMenu, SWT.PUSH);
        itemFeatItem.setText("&Feat Wizard");
        
        itemFeatItem.addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e)
        	{
        	    new FeatWizard(shell.getDisplay());
        	}
        });
        //Character Generator
        MenuItem charGenItem = new MenuItem(toolsMenu, SWT.PUSH);
        charGenItem.setText("&Character Generator");

        charGenItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
        		new CharacterWizard(shell.getDisplay());
            }
        });

        shell.setMenuBar(menuBar);
	}
}
