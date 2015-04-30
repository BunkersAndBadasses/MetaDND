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
    private static ArrayList<String> allSpells = new ArrayList<String>();
    private static ArrayList<String> materials = new ArrayList<String>();
    private static character c;
    private static ArrayList<String> spellsPrepared = new ArrayList<String>();
    private static HashMap<String, SpellEntity> spellsKnown = new HashMap<String, SpellEntity>();
    private static String version = "Ver0.9.Beta";
    private static String[] strArr;

    private static void getInfo(){
        c = Main.gameState.currentlyLoadedCharacter;
        allSpells.addAll(Main.gameState.spells.keySet());
        try {
            for(int i = 0; i < c.getSpells().size(); i++) {
                spellsKnown.put(c.getSpells().get(i).getName(), c.getSpells().get(i));  
            }
        } catch (NullPointerException npe) {

        }

        try {
            for(int i = 0; i < c.getPrepSpells().size(); i++) {
                spellsPrepared.add(c.getPrepSpells().get(i).getName());
            } 
        } catch (NullPointerException npe) {

        }

    }

    public static void main(String args) {
        // TODO Auto-generated method stub
        Display display = Display.getCurrent();
        Shell shell = new Shell(display);
        
        Image logo = new Image(display, "images/bnb_logo.gif");
        shell.setImage(logo);
        shell.setText("Meta D&D " + version );
        getInfo();

        FormLayout layout = new FormLayout();
        shell.setLayout(layout);

        final Combo spellSel = new Combo(shell, SWT.READ_ONLY);
        if (spellsKnown.size() != 0) {
            strArr = new String[spellsKnown.size()];
            spellSel.setItems(spellsKnown.keySet().toArray(strArr)); // TODO Load Spells known
        }
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
                // TODO Cast spell
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
                // TODO Remove spell
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
                // TODO Prepare spell
            }
        }); 

        // TODO Prepared Spells

        final Combo preparedSel = new Combo(shell, SWT.READ_ONLY);
        if (spellsPrepared.size() != 0) {
            preparedSel.setItems((String[]) spellsPrepared.toArray()); // TODO Load prepared spells
        }
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

        cast.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // TODO Cast spell
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
                // TODO Remove spell
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
                // TODO reset preparation
            }
        }); 

        // TODO all Spells

        final Combo newSpellSel = new Combo(shell, SWT.READ_ONLY);
        String[] strArr = new String[allSpells.size()];
        strArr = allSpells.toArray(strArr);
        newSpellSel.setItems(strArr); // TODO Load all spells
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
            materialSel.setItems((String[]) materials.toArray()); // TODO Load all spells
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
            }
        }); 

        // Table

        Table spellTable = new Table(shell, SWT.BORDER );
        spellTable.setHeaderVisible(true);
        String[] titles = { "Level", "Total", "Cast", "Prepared" };

        for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
            TableColumn column = new TableColumn(spellTable, SWT.NULL);
            column.setAlignment(SWT.CENTER);
            column.setText(titles[loopIndex]);
        }

        for (int loopIndex = 0; loopIndex < 10; loopIndex++) {
            TableItem item = new TableItem(spellTable, SWT.NULL);
            item.setText("" + loopIndex); // TODO Set actual values
            item.setText(0, "" + loopIndex);
            item.setText(1, "" + 0);
            item.setText(2, "" + 0);
            item.setText(3, "" + 0);
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

        shell.open(); // Open the Window and process the clicks
        while (!shell.isDisposed()) {
            if (display.readAndDispatch()) {
                display.sleep();
            }
        }


    }

}
