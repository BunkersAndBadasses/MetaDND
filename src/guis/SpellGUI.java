package guis;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import core.Main;
import core.character;
import entity.SpellEntity;


public class SpellGUI {
    private HashMap<String, SpellEntity> allSpells = new HashMap<String, SpellEntity>();
    private ArrayList<String> materials = new ArrayList<String>();
    private character c;
    private ArrayList<String> spellsPrepared = new ArrayList<String>();
    private ArrayList<String> spellsKnown = new ArrayList<String>();
    private ArrayList<String> allArr = new ArrayList<String>();
    private String version = "Spell Manager";
    private String[] strArr;
    int[][] spd;
    int[] bSpells = new int[10];
    int classMod;
    int[] totSpells = new int[10];
    private int[] castTot = new int[10];
    private int[] prepTot = new int[10];
    private String className;
    private String adjName;
    private String[] titles = { "Level", "Total", "Cast", "Prepared" };
    private Table spellTable;
    private Combo spellSel;
    private Combo preparedSel;


    private void getInfo(){
        c = Main.gameState.currentlyLoadedCharacter;
        className = c.getCharClass().getName();
        loadArr();
        ArrayList<String> seTemp = new ArrayList<String>();
        seTemp.addAll(Main.gameState.spells.keySet());
        for(int i = 0; i < seTemp.size(); i++){
            SpellEntity se = (SpellEntity) Main.gameState.spells.get(seTemp.get(i));
            int level = mostRelevantLevel(se);
            allSpells.put("" + level + ". " + seTemp.get(i), se);
            allArr.add("" + level + ". " + seTemp.get(i));
        }
        try {
            for(int i = 0; i < c.getSpells().size(); i++) {
                int level = mostRelevantLevel(c.getSpells().get(i));
                spellsKnown.add("" + level + ". " + c.getSpells().get(i).getName()); 
            }
        } catch (NullPointerException npe) {

        }

        try {
            for(int i = 0; i < c.getPrepSpells().size(); i++) {
                int level = mostRelevantLevel(c.getPrepSpells().get(i));
                spellsPrepared.add("" + level + ". " + c.getPrepSpells().get(i).getName());
            } 
        } catch (NullPointerException npe) {

        }
        allArr.sort(String.CASE_INSENSITIVE_ORDER);
        spellsKnown.sort(String.CASE_INSENSITIVE_ORDER);
        spellsPrepared.sort(String.CASE_INSENSITIVE_ORDER);

    }

    private int mostRelevantLevel(SpellEntity se) {
        int level = -1;
        for(int i = 0; i < se.getLevel().length; i++){
            String trans =  se.getLevel()[i];
            trans = trans.replaceAll("[^\\d.]", "");
            int ti = Integer.parseInt(trans);
            if(level < 0) level = ti;
            String modName = se.getLevel()[i];
            modName = modName.replaceAll("[^A-Za-z]", "").trim();
            if(modName.equals(adjName)) {
                level = ti;
                break;
            }
            else if(modName.equals("Druid") || modName.equals("Sorcerer/Wizard")) {
                level = ti;
            }

        }
        return level;
    }

    public SpellGUI(String args) {
        Display display = Display.getCurrent();
        Shell shell = new Shell(display);

        Image logo = new Image(display, "images/bnb_logo.gif");
        shell.setImage(logo);
        shell.setText("Meta D&D " + version );
        getInfo();

        FormLayout layout = new FormLayout();
        shell.setLayout(layout);

        spellSel = new Combo(shell, SWT.READ_ONLY);
        
        FormData spellSelData = new FormData(140,30);
        spellSel.select(0);
        spellSelData.left = new FormAttachment(5);
        spellSelData.top = new FormAttachment(5);
        spellSel.setLayoutData(spellSelData);

        Button cast = new Button(shell, SWT.PUSH);
        cast.setText("Cast");
        FormData castData = new FormData(80,24);
        castData.left = new FormAttachment(spellSel, 5, SWT.RIGHT);
        castData.top = new FormAttachment(spellSel, 0, SWT.TOP);
        cast.setLayoutData(castData);

        cast.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if(spellSel.getSelectionIndex() == 0) {

                }
                else {
                    int ind = spellSel.getSelectionIndex();
                    String tmp = spellSel.getText();
                    String num = tmp.substring(0, 1);
                    int spellLev = Integer.parseInt(num);
                    castTot[spellLev] = castTot[spellLev] + 1;
                    refresh();
                }
            }
        }); 

        Button removeSpell = new Button(shell, SWT.PUSH);
        removeSpell.setText("Remove from spell list");
        FormData removeData = new FormData(167,24);
        removeData.left = new FormAttachment(spellSel, 0, SWT.LEFT);
        removeData.top = new FormAttachment(spellSel, 5, SWT.BOTTOM);
        removeSpell.setLayoutData(removeData);

        removeSpell.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String str = "";
                if(spellsKnown.size()== 0 || spellSel.getSelectionIndex() == 0) {
                    
                }
                else {
                    str = spellSel.getText();
                    SpellEntity se = allSpells.get(str);
                    c.delSpell(se);
                    spellsKnown.remove(str);
                    refresh();
                }
            }
        }); 

        Button prepareSpell = new Button(shell, SWT.PUSH);
        prepareSpell.setText("Prepare");
        FormData prepareData = new FormData(80,24);
        prepareData.left = new FormAttachment(removeSpell, 5, SWT.RIGHT);
        prepareData.top = new FormAttachment(removeSpell, 0, SWT.TOP);
        prepareSpell.setLayoutData(prepareData);

        prepareSpell.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String str = "";
                if(spellsKnown.size()== 0 || spellSel.getSelectionIndex() == 0) {
                    
                }
                else {
                    str = spellSel.getText();
                    String num = str.substring(0, 1);
                    int spellLev = Integer.parseInt(num);
                    prepTot[spellLev] = prepTot[spellLev] + 1;
                    SpellEntity se = allSpells.get(str);
                    c.prepSpell(se);
                    spellsPrepared.add(str);
                    refresh();
                }
            }
        }); 


        preparedSel = new Combo(shell, SWT.READ_ONLY);
        
        FormData preparedData = new FormData(140,30);
        preparedSel.select(0);
        preparedData.left = new FormAttachment(removeSpell, 0, SWT.LEFT);
        preparedData.top = new FormAttachment(removeSpell, 24, SWT.BOTTOM);
        preparedSel.setLayoutData(preparedData);

        Button castPrep = new Button(shell, SWT.PUSH);
        castPrep.setText("Cast");
        FormData castPrepData = new FormData(80,24);
        castPrepData.left = new FormAttachment(preparedSel, 5, SWT.RIGHT);
        castPrepData.top = new FormAttachment(preparedSel, 0, SWT.TOP);
        castPrep.setLayoutData(castPrepData);

        castPrep.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String str = "";
                if(spellsPrepared.size()== 0 || preparedSel.getSelectionIndex() == 0) {
                    
                }
                else {
                    str = preparedSel.getText();
                    String num = str.substring(0, 1);
                    int spellLev = Integer.parseInt(num);
                    castTot[spellLev] = castTot[spellLev] + 1;
                    prepTot[spellLev] = prepTot[spellLev] - 1;
                    SpellEntity se = allSpells.get(str);
                    c.unprepSpell(se);
                    spellsPrepared.remove(str);
                    refresh();
                }
            }
        }); 

        Button removePrepSpell = new Button(shell, SWT.PUSH);
        removePrepSpell.setText("Remove from prepared");
        FormData removePrepData = new FormData(167,24);
        removePrepData.left = new FormAttachment(preparedSel, 0, SWT.LEFT);
        removePrepData.top = new FormAttachment(preparedSel, 5, SWT.BOTTOM);
        removePrepSpell.setLayoutData(removePrepData);

        removePrepSpell.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String str = "";
                if(spellsPrepared.size()== 0 || preparedSel.getSelectionIndex() == 0) {
                    
                }
                else {
                    str = preparedSel.getText();
                    SpellEntity se = allSpells.get(str);
                    c.unprepSpell(se);
                    spellsPrepared.remove(str);
                    refresh();
                }
            }
        }); 

        Button resetPrep = new Button(shell, SWT.PUSH);
        resetPrep.setText("Reset");
        FormData resetPrepData = new FormData(80,24);
        resetPrepData.left = new FormAttachment(removePrepSpell, 5, SWT.RIGHT);
        resetPrepData.top = new FormAttachment(removePrepSpell, 0, SWT.TOP);
        resetPrep.setLayoutData(resetPrepData);

        resetPrep.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                spellsPrepared = new ArrayList<String>();
                prepTot = new int[10];
                c.resetPrepSpells();
                refresh();
            }
        }); 


        final Combo newSpellSel = new Combo(shell, SWT.READ_ONLY);
        String[] strArr = new String[allArr.size()];
        strArr = allArr.toArray(strArr);
        newSpellSel.setItems(strArr);
        FormData allSpellData = new FormData(140,30);
        newSpellSel.select(0);
        allSpellData.left = new FormAttachment(removePrepSpell, 0, SWT.LEFT);
        allSpellData.top = new FormAttachment(removePrepSpell, 24, SWT.BOTTOM);
        newSpellSel.setLayoutData(allSpellData);

        Button getInfo = new Button(shell, SWT.PUSH);
        getInfo.setText("Get information");
        FormData getInfoData = new FormData(120,24);
        getInfoData.left = new FormAttachment(newSpellSel, 5, SWT.RIGHT);
        getInfoData.top = new FormAttachment(newSpellSel, 0, SWT.TOP);
        getInfo.setLayoutData(getInfoData); 

        getInfo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // TODO Cast spell
            }
        }); 

        Button addSpell = new Button(shell, SWT.PUSH);
        addSpell.setText("Add to spell list");
        FormData addData = new FormData(167,24);
        addData.left = new FormAttachment(newSpellSel, 0, SWT.LEFT);
        addData.top = new FormAttachment(newSpellSel, 5, SWT.BOTTOM);
        addSpell.setLayoutData(addData);

        addSpell.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // TODO Remove spell
            }
        }); 

        // TODO  Spell materials

        final Combo materialSel = new Combo(shell, SWT.READ_ONLY);
        if (materials.size() != 0) {
            strArr = new String[materials.size()];
            strArr = materials.toArray(strArr);
            materialSel.setItems((String[]) materials.toArray());
        }
        FormData materialData = new FormData(140,30);
        materialSel.select(0);
        materialData.left = new FormAttachment(addSpell, 0, SWT.LEFT);
        materialData.top = new FormAttachment(addSpell, 24, SWT.BOTTOM);
        materialSel.setLayoutData(materialData);

        Button matAdd = new Button(shell, SWT.PUSH);
        matAdd.setText("Add quantity");
        FormData matAddData = new FormData(120,24);
        matAddData.left = new FormAttachment(materialSel, 0, SWT.LEFT);
        matAddData.top = new FormAttachment(materialSel, 5, SWT.BOTTOM);
        matAdd.setLayoutData(matAddData); 

        matAdd.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // TODO Add materials
            }
        }); 

        Spinner matSpin = new Spinner(shell, SWT.COLOR_GREEN);
        matSpin.setMinimum(0);
        matSpin.setMaximum(1000000);
        FormData matSpinData = new FormData(40,24);
        matSpinData.left = new FormAttachment(materialSel, 5, SWT.RIGHT);
        matSpinData.top = new FormAttachment(materialSel, 0, SWT.TOP);
        matSpin.setLayoutData(matSpinData);

        Button matSub= new Button(shell, SWT.PUSH);
        matSub.setText("Remove quantity");
        FormData matSubData = new FormData(120,24);
        matSubData.left = new FormAttachment(matAdd, 5, SWT.RIGHT);
        matSubData.top = new FormAttachment(matAdd, 0, SWT.TOP);
        matSub.setLayoutData(matSubData);

        matSub.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // TODO Remove materials
            }
        }); 

        // Spell Wizard

        Button spellWiz= new Button(shell, SWT.PUSH);
        spellWiz.setText("Spell wizard");
        FormData spellWizData = new FormData(120,24);
        spellWizData.left = new FormAttachment(matAdd, 0, SWT.LEFT);
        spellWizData.top = new FormAttachment(matAdd, 24, SWT.BOTTOM);
        spellWiz.setLayoutData(spellWizData);

        spellWiz.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // TODO Launch Spell Wizard
                new SpellWizard(Display.getCurrent());
            }
        }); 

        // Table

        spellTable = new Table(shell, SWT.BORDER );
        for (int loopIndex = 0; loopIndex < 10; loopIndex++) {
            TableItem item = new TableItem(spellTable, SWT.NULL);  
        }
        
        spellTable.setHeaderVisible(true);
        for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
            TableColumn column = new TableColumn(spellTable, SWT.NULL);
            column.setAlignment(SWT.CENTER);
            column.setText(titles[loopIndex]);
        }

        
        for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
            spellTable.getColumn(loopIndex).pack();
        }



        FormData spellTableData = new FormData(165,200);

        spellTableData.left = new FormAttachment(cast, 60, SWT.RIGHT);
        spellTableData.top = new FormAttachment(cast, 0, SWT.TOP);
        spellTable.setLayoutData(spellTableData);

        Button resetTable = new Button(shell, SWT.PUSH);
        resetTable.setText("Reset");
        FormData resetTableData = new FormData(80,24);
        resetTableData.left = new FormAttachment(spellTable, 50, SWT.LEFT);
        resetTableData.top = new FormAttachment(spellTable, 10, SWT.BOTTOM);
        resetTable.setLayoutData(resetTableData);

        resetTable.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // TODO reset table
            }
        }); 
        
        refresh();

        shell.open(); // Open the Window and process the clicks
        while (!shell.isDisposed()) {
            if (display.readAndDispatch()) {
                display.sleep();
            }
        }


    }

    private void refresh() {
        allArr.sort(String.CASE_INSENSITIVE_ORDER);
        spellsKnown.sort(String.CASE_INSENSITIVE_ORDER);
        spellsPrepared.sort(String.CASE_INSENSITIVE_ORDER);
        spellSel.removeAll();
        preparedSel.removeAll();
        
        if (spellsKnown.size() != 0) {
            strArr = new String[spellsKnown.size()];
            spellSel.setItems(spellsKnown.toArray(strArr)); 
        }
        spellSel.add("Known Spells", 0);
        spellSel.select(0);
        
        if (spellsPrepared.size() != 0) {
            strArr = new String[spellsPrepared.size()];
            preparedSel.setItems(spellsPrepared.toArray(strArr));
        }
        preparedSel.add("Prepared Spells", 0);
        preparedSel.select(0);
        

        
        for (int loopIndex = 0; loopIndex < 10; loopIndex++) {
            TableItem item = spellTable.getItem(loopIndex);
            item.setText("" + loopIndex); // TODO Set actual values
            item.setText(0, "" + loopIndex);
            item.setText(1, "" + totSpells[loopIndex]);
            item.setText(2, "" + castTot[loopIndex]);
            item.setText(3, "" + prepTot[loopIndex]);
        }

        

    }

    private void loadArr() {
        int[][] sorc = {
                {5,3,0,0,0,0,0,0,0,0},
                {6,4,0,0,0,0,0,0,0,0}, // 2
                {6,5,0,0,0,0,0,0,0,0}, // 3
                {6,6,3,0,0,0,0,0,0,0}, // 4
                {6,6,4,0,0,0,0,0,0,0}, // 5
                {6,6,5,3,0,0,0,0,0,0}, // 6
                {6,6,6,4,0,0,0,0,0,0}, // 7
                {6,6,6,5,3,0,0,0,0,0}, // 8
                {6,6,6,6,4,0,0,0,0,0}, // 9
                {6,6,6,6,5,3,0,0,0,0}, // 10
                {6,6,6,6,6,4,0,0,0,0}, // 11
                {6,6,6,6,6,5,3,0,0,0}, // 12
                {6,6,6,6,6,6,4,0,0,0}, // 13
                {6,6,6,6,6,6,5,3,0,0}, // 14
                {6,6,6,6,6,6,6,4,0,0}, // 15
                {6,6,6,6,6,6,6,5,3,0}, // 16
                {6,6,6,6,6,6,6,6,4,0}, // 17
                {6,6,6,6,6,6,6,6,5,3}, // 18
                {6,6,6,6,6,6,6,6,6,4}, // 19
                {6,6,6,6,6,6,6,6,6,6}};

        int[][] wiz = {
                {3,1,0,0,0,0,0,0,0,0}, // 1
                {4,2,0,0,0,0,0,0,0,0}, // 2
                {4,2,1,0,0,0,0,0,0,0}, // 3
                {4,3,2,0,0,0,0,0,0,0}, // 4
                {4,3,2,1,0,0,0,0,0,0}, // 5
                {4,3,3,2,0,0,0,0,0,0}, // 6
                {4,4,3,2,1,0,0,0,0,0}, // 7
                {4,4,3,3,2,0,0,0,0,0}, // 8
                {4,4,4,3,2,1,0,0,0,0}, // 9
                {4,4,4,3,3,2,0,0,0,0}, // 10
                {4,4,4,4,3,2,1,0,0,0}, // 11
                {4,4,4,4,3,3,2,0,0,0}, // 12
                {4,4,4,4,4,3,2,1,0,0}, // 13
                {4,4,4,4,4,3,3,2,0,0}, // 14
                {4,4,4,4,4,4,3,2,1,0}, // 15
                {4,4,4,4,4,4,3,3,2,0}, // 16
                {4,4,4,4,4,4,4,3,2,1}, // 17
                {4,4,4,4,4,4,4,3,3,2}, // 18
                {4,4,4,4,4,4,4,6,3,3},
                {4,4,4,4,4,4,4,6,6,4}};

        int[][] clerk = {
                {3,2,0,0,0,0,0,0,0,0}, // 1
                {4,3,0,0,0,0,0,0,0,0}, // 2
                {4,3,2,0,0,0,0,0,0,0}, // 3
                {5,4,3,0,0,0,0,0,0,0}, // 4
                {5,4,3,2,0,0,0,0,0,0}, // 5
                {5,4,4,3,0,0,0,0,0,0}, // 6
                {6,5,4,3,2,0,0,0,0,0}, // 7
                {6,5,4,4,3,0,0,0,0,0}, // 8
                {6,5,5,4,3,2,0,0,0,0}, // 9
                {6,5,5,4,4,3,0,0,0,0}, // 10
                {6,6,5,5,4,3,2,0,0,0}, // 11
                {6,6,5,5,4,4,3,0,0,0}, // 12
                {6,6,6,5,5,4,3,2,0,0}, // 13
                {6,6,6,5,5,4,4,3,0,0}, // 14
                {6,6,6,6,5,5,4,3,2,0}, // 15
                {6,6,6,6,5,5,4,4,3,0}, // 16
                {6,6,6,6,6,5,5,4,3,2}, // 17
                {6,6,6,6,6,5,5,4,4,3}, // 18
                {6,6,6,6,6,6,5,5,4,4},
                {6,6,6,6,6,6,5,5,5,5}};

        int[][] druid = {
                {3,1,0,0,0,0,0,0,0,0}, // 1
                {4,2,0,0,0,0,0,0,0,0}, // 2
                {4,2,1,0,0,0,0,0,0,0}, // 3
                {5,3,2,0,0,0,0,0,0,0}, // 4
                {5,3,2,1,0,0,0,0,0,0}, // 5
                {5,3,3,2,0,0,0,0,0,0}, // 6
                {6,4,3,2,1,0,0,0,0,0}, // 7
                {6,4,3,3,2,0,0,0,0,0}, // 8
                {6,4,4,3,2,1,0,0,0,0}, // 9
                {6,4,4,3,3,2,0,0,0,0}, // 10
                {6,5,4,4,3,2,1,0,0,0}, // 11
                {6,5,4,4,3,3,2,0,0,0}, // 12
                {6,5,5,4,4,3,2,1,0,0}, // 13
                {6,5,5,4,4,3,3,2,0,0}, // 14
                {6,5,5,5,4,4,3,2,1,0}, // 15
                {6,5,5,5,4,4,3,3,2,0}, // 16
                {6,5,5,5,5,4,4,3,2,1}, // 17
                {6,5,5,5,5,4,4,3,3,2}, // 18
                {6,5,5,5,5,5,4,4,3,3},
                {6,5,5,5,5,5,4,4,4,4}};

        int[][] bard = {
                {2,0,0,0,0,0,0,0,0,0}, // 1
                {3,9,0,0,0,0,0,0,0,0}, // 2
                {3,1,0,0,0,0,0,0,0,0}, // 3
                {3,2,9,0,0,0,0,0,0,0}, // 4
                {3,3,1,0,0,0,0,0,0,0}, // 5
                {3,3,2,0,0,0,0,0,0,0}, // 6
                {3,3,2,9,0,0,0,0,0,0}, // 7
                {3,3,3,1,0,0,0,0,0,0}, // 8
                {3,3,3,2,0,0,0,0,0,0}, // 9
                {3,3,3,2,9,0,0,0,0,0}, // 10
                {3,3,3,3,1,0,0,0,0,0}, // 11
                {3,3,3,3,2,0,0,0,0,0}, // 12
                {3,3,3,3,2,9,0,0,0,0}, // 13
                {4,3,3,3,3,1,0,0,0,0}, // 14
                {4,4,3,3,3,2,0,0,0,0}, // 15
                {4,4,4,3,3,2,9,0,0,0}, // 16
                {4,4,4,4,3,3,1,0,0,0}, // 17
                {4,4,4,4,4,3,2,0,0,0}, // 18
                {4,4,4,4,4,4,3,0,0,0},
                {4,4,4,4,4,4,4,0,0,0}};

        int[][] fuckingRanger = {
                {0,0,0,0,0,0,0,0,0,0}, // 1
                {0,0,0,0,0,0,0,0,0,0}, // 2
                {0,0,0,0,0,0,0,0,0,0}, // 3
                {0,9,0,0,0,0,0,0,0,0}, // 4
                {0,9,0,0,0,0,0,0,0,0}, // 5
                {0,1,0,0,0,0,0,0,0,0}, // 6
                {0,1,0,0,0,0,0,0,0,0}, // 7
                {0,1,9,0,0,0,0,0,0,0}, // 8
                {0,1,9,0,0,0,0,0,0,0}, // 9
                {0,1,1,0,0,0,0,0,0,0}, // 10
                {0,1,1,9,0,0,0,0,0,0}, // 11
                {0,1,1,1,0,0,0,0,0,0}, // 12
                {0,1,1,1,0,0,0,0,0,0}, // 13
                {0,2,1,1,9,0,0,0,0,0}, // 14
                {0,2,1,1,1,0,0,0,0,0}, // 15
                {0,2,2,1,1,0,0,0,0,0}, // 16
                {0,2,2,2,1,0,0,0,0,0}, // 17
                {0,3,2,2,1,0,0,0,0,0}, // 18
                {0,3,3,3,2,0,0,0,0,0},
                {0,3,3,3,3,0,0,0,0,0}};
        
        int[][] paladin = {
                {0,0,0,0,0,0,0,0,0,0}, // 1
                {0,0,0,0,0,0,0,0,0,0}, // 2
                {0,0,0,0,0,0,0,0,0,0}, // 3
                {0,9,0,0,0,0,0,0,0,0}, // 4
                {0,9,0,0,0,0,0,0,0,0}, // 5
                {0,1,0,0,0,0,0,0,0,0}, // 6
                {0,1,0,0,0,0,0,0,0,0}, // 7
                {0,1,9,0,0,0,0,0,0,0}, // 8
                {0,1,9,0,0,0,0,0,0,0}, // 9
                {0,1,1,0,0,0,0,0,0,0}, // 10
                {0,1,1,9,0,0,0,0,0,0}, // 11
                {0,1,1,1,0,0,0,0,0,0}, // 12
                {0,1,1,1,0,0,0,0,0,0}, // 13
                {0,2,1,1,9,0,0,0,0,0}, // 14
                {0,2,1,1,1,0,0,0,0,0}, // 15
                {0,2,2,1,1,0,0,0,0,0}, // 16
                {0,2,2,2,1,0,0,0,0,0}, // 17
                {0,3,2,2,1,0,0,0,0,0}, // 18
                {0,3,3,3,2,0,0,0,0,0},
                {0,3,3,3,3,0,0,0,0,0}};

        switch(className) {

        case "Bard":
            spd = bard;
            classMod = c.getAbilityModifiers()[5];
            adjName = className;
            break;

        case "Cleric":
            spd = clerk;
            classMod = c.getAbilityModifiers()[4];
            adjName = className;
            break;

        case "Druid":
            spd = druid;
            classMod = c.getAbilityModifiers()[4];
            adjName = className;
            break;
            
        case "Paladin":
            spd = paladin;
            classMod = c.getAbilityModifiers()[4];
            adjName = className;
            break;

        case "Ranger":
            spd = fuckingRanger;
            classMod = c.getAbilityModifiers()[4];
            adjName = className;
            break;

        case "Sorcerer":
            spd= sorc;
            classMod = c.getAbilityModifiers()[5];
            adjName = "Sorcerer/Wizard";
            break;

        case "Wizard":
            spd = wiz;
            classMod = c.getAbilityModifiers()[3];
            adjName = "Sorcerer/Wizard";
            break;

        default:
            spd = new int[20][10];
        }
        int[] bs = {0,
                (classMod + 3)/4, // 1
                (classMod + 2)/4, // 2
                (classMod + 1)/4, // 3
                (classMod + 0)/4, // 4
                (classMod - 1)/4, // 5
                (classMod - 2)/4, // 6
                (classMod - 3)/4, // 7
                (classMod - 4)/4, // 8
                (classMod - 5)/4};
        bSpells = bs;
        int lev = c.getLevel();
        for(int i = 0; i < 10; i ++) {
            if(spd[lev][i] == 0 )
                totSpells[i] = 0;
            else if (spd[lev][i] == 9){
                totSpells[i] = 0;
                if (bSpells[i] > 0) totSpells[i] = bSpells[i];
            }
            else {
                totSpells[i] = spd[lev][i];
                if(bSpells[i] > 0) totSpells[i] += bSpells[i];
            }
        }
    }
}

