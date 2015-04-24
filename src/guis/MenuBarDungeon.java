package guis;
import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.FileDialog;

import core.DungeonConstants;
import core.Main;


public class MenuBarDungeon {
	
	private Shell m_shell;
	private Menu menuBar;
	private MenuItem cascadeFileMenu;
	private MenuItem cascadeToolsMenu;
	private Menu fileMenu;
	private MenuItem saveItem;
	private HomeWindow parent;
	
	public MenuBarDungeon(final Shell shell, HomeWindow parent)
	{
		
		this.parent = parent;
		this.m_shell = shell;
		
		menuBar = new Menu(shell, SWT.BAR);
		cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeFileMenu.setText("&File");
        
        cascadeToolsMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeToolsMenu.setText("&Tools");
        
        
        fileMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeFileMenu.setMenu(fileMenu);
        
        //Save
        saveItem = new MenuItem(fileMenu, SWT.PUSH);
        saveItem.setText("&Save");
        

        saveItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	//TODO:
            	FileDialog dialog = new FileDialog(m_shell, SWT.SAVE);
            	dialog.setText("Save");
            	dialog.setFilterPath(DungeonConstants.SAVEDDUNGEONSDIR.toString());
                String[] filterExt = { "*.svg"};
                dialog.setFilterExtensions(filterExt);
                String selected = dialog.open();
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
			        System.out.println("Failure to rename File: " + newFile.toString());
			    }
				
            }
        });
        
        
        //Open
        MenuItem openItem = new MenuItem(fileMenu, SWT.PUSH);
        openItem.setText("&Open");
        

        openItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	//TODO
            }
        });
        
        //Help
        MenuItem helpItem = new MenuItem(fileMenu, SWT.PUSH);
        helpItem.setText("&Help");
        

        helpItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	//TODO
            }
        });
        
        //Close
        MenuItem closeItem = new MenuItem(fileMenu, SWT.PUSH);
        closeItem.setText("&Close");
        

        closeItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	shell.dispose();
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
        
        
        //Tools Menu
        Menu toolsMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeToolsMenu.setMenu(toolsMenu);
        
        // Home screen
        MenuItem homeScreen = new MenuItem(toolsMenu, SWT.PUSH);
        homeScreen.setText("&Home Screen");
        
        homeScreen.addSelectionListener(new SelectionAdapter()
        {
        public void widgetSelected(SelectionEvent e)
        {
        	
        	parent.navigateToHomeScreen();
        }});
        
      //Player screen
        MenuItem dunChaItem = new MenuItem(toolsMenu, SWT.PUSH);
        dunChaItem.setText("&Players Screen");
        

        dunChaItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	HomeWindow.loadCharacters();
                parent.navigateToPlayerScreen();
            }
        });
        
        //Dungeon Master screen
        MenuItem dunGenItem = new MenuItem(toolsMenu, SWT.PUSH);
        dunGenItem.setText("&Dungeon Masters");
        

        dunGenItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                parent.navigateToDungeonMasterScreen();
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

