package guis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.CharFeat;
import core.CharItem;
import core.CharSkill;
import core.DungeonConstants;
import core.GameState;
import core.LoadCharacter;
import core.Main;
import core.SaveCharacter;
import core.character;
import entity.AbilityEntity;
import entity.ArmorEntity;
import entity.ItemEntity;
import entity.SkillEntity;
import entity.WeaponEntity;


public class CharacterMain {
    private static DocumentBuilderFactory dbFactory;
    private static DocumentBuilder dBuilder;
    private Document doc;
    private String filename;
    private Element element;
    private String charName;
    private String shieldName;
    private String armorName;
    private String charClass;
    private int charLevel;
    private String charSecClass;
    private int charSecLevel;
    private int strVal;
    private int dexVal;
    private int conVal;
    private int intVal;
    private int wisVal;
    private int chaVal;
    private int hpVal;
    private int speedVal;
    private int acVal;
    private int ffVal;
    private int touchVal;
    private int fortVal;
    private int refVal;
    private int willVal;
    private int initVal;
    private String priWeapon;
    private String secWeapon;
    private ArrayList<String> items;
    private ArrayList<String> languages;
    private ArrayList<String> weapons;
    private ArrayList<String> armors;
    private ArrayList<String> skills;
    private ArrayList<String> specialAbilities;
    private ArrayList<String> shields;
    private ArrayList<String> feats;
    private String notes;
    private String imagePath;
    private String dmgTaken;
    private String delims = "[/]+";
    private String pp, gp, sp, cp;
    private File stocks;
    private StackLayout mainWindowLayout;
    private Display display;
    private Composite mainWindow;
    private Composite mainComp;
    private GridLayout charLayout;
    private String bonus;
    private String exp;
    private Shell m_shell;
    private Image m_characterImage;
    private character c;
    private String [] priVals = new String[6];
    private String [] secVals = new String[6];
    private String[] strArr;
    private Combo shieldCombo;
    private Combo armorCombo;
    private Combo priCombo;
    private Combo secCombo;
    private Label secLabel;
    private Label secBonusLabel;
    private Label secDamageLabel;
    private Label secRangeLabel;
    private Label secCriticalLabel;
    private Label secTypeLabel;
    private Label initLabel;
    private Label speedLabel;
    private Label armorLabel;
    private Label acLabel;
    private Label ffLabel;
    private Label touchLabel;
    private Label shieldLabel;
    private Label reflexLabel;
    private Label fortLabel;
    private Label willLabel;
    private Label priBonusLabel;
    private Label priDamageLabel;
    private Label priRangeLabel;
    private Label priCriticalLabel;
    private Label priTypeLabel;
    private boolean boo;
    private Combo skillCombo;
    private Combo specAbilCombo;
    private Combo featCombo;
    private Combo languageCombo;
    private Combo inventoryCombo;
    private Label priLabel;
    private Label wisLabel;
    private Label chaLabel;
    private Label levelLabel;
    private Label strLabel;
    private Label dexLabel;
    private Label classLabel;
    private Label hpLabel;
    private Label conLabel;
    private Label intLabel;


    public CharacterMain(String[] args, Composite panel, Shell shell) {

        m_shell = shell;

        String pathName = args[0];


        Main.gameState.currentlyLoadedCharacter = new character();
        c = Main.gameState.currentlyLoadedCharacter;
        getPlayerInfo(pathName, true);

        // TODO Auto-generated method stub

        mainWindow = new Composite(panel, SWT.NONE);
        mainWindow.setLayoutData(new GridData(GridData.FILL_BOTH));
        mainWindowLayout = new StackLayout();
        mainWindow.setLayout(mainWindowLayout);
        mainComp = new Composite(mainWindow, SWT.NONE);
        charLayout = new GridLayout(5, true);
        charLayout.makeColumnsEqualWidth = true;
        mainComp.setLayout(charLayout);


        //panel.setImage(new Image(display, "images/bnb_logo.gif"));

        GridData imageGD = new GridData();
        imageGD.verticalSpan = 7;
        imageGD.grabExcessHorizontalSpace = true;
        imageGD.grabExcessVerticalSpace = false;
        imageGD.heightHint = 188;
        imageGD.horizontalAlignment = SWT.CENTER;
        imageGD.widthHint = 155;
        Label img = new Label(mainComp, SWT.CENTER);
        if (imagePath.equals(" ")) {
            imagePath = "images/SetWidth150-blank-profile.jpg";
        }
        m_characterImage = new Image(Display.getCurrent(), imagePath);
        img.setImage(m_characterImage);
        img.setLayoutData(imageGD);
        img.pack();

        GridData statGD = new GridData();
        statGD.horizontalAlignment = SWT.CENTER;
        statGD.grabExcessHorizontalSpace = true;
        statGD.widthHint = 160;
        statGD.heightHint = 17;

        strLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        strLabel.setLayoutData(statGD);


        dexLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        dexLabel.setLayoutData(statGD);

        classLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);        
        classLabel.setLayoutData(statGD);

        hpLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        hpLabel.setLayoutData(statGD);
        hpLabel.pack();

        bonus = "";
        if (conVal >= 10) {
            bonus +="+";
        }
        conLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        conLabel.setText("CON: " + conVal + " (" + bonus + (conVal/2 - 5) + ")");
        conLabel.setLayoutData(statGD);

        bonus = "";
        if (intVal >= 10) {
            bonus +="+";
        }
        intLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        intLabel.setText("INT: " + intVal + " (" + bonus + (intVal/2 - 5) + ")");
        intLabel.setLayoutData(statGD);

        Label secClassLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        if (!charSecClass.equals("")) secClassLabel.setText("Sec Class: " + charSecClass);
        secClassLabel.setLayoutData(statGD);

        //Composite for damage
        Composite dmgComp = new Composite(mainComp, SWT.NONE);
        GridLayout dmgGrid = new GridLayout(2, true);
        dmgComp.setLayout(dmgGrid);

        GridData damageGD = new GridData();
        damageGD.horizontalAlignment = SWT.CENTER;
        damageGD.grabExcessHorizontalSpace = true;
        damageGD.widthHint = 120;
        damageGD.heightHint = 17;

        Label dmgLabel = new Label(dmgComp, SWT.BORDER | SWT.CENTER);
        dmgLabel.setText("Damage: ");
        dmgLabel.setLayoutData(damageGD);

        GridData dmgGD = new GridData();
        dmgGD.horizontalAlignment = SWT.CENTER;
        //dmgGD.grabExcessHorizontalSpace = true;
        dmgGD.widthHint = 20;
        dmgGD.heightHint = 17;

        StyledText dmgText = new StyledText(dmgComp, SWT.BORDER | SWT.CENTER);
        dmgText.setText(""+ dmgTaken);
        dmgText.setLayoutData(dmgGD);

        GridData dmgGD2 = new GridData();
        dmgGD2.horizontalAlignment = SWT.CENTER;
        dmgGD2.grabExcessHorizontalSpace = true;
        dmgGD2.widthHint = 120;

        dmgComp.setLayoutData(dmgGD2);
        dmgComp.pack();


        wisLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        wisLabel.setLayoutData(statGD);


        chaLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        chaLabel.setLayoutData(statGD);

        levelLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        levelLabel.setLayoutData(statGD);

        speedLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        speedLabel.setLayoutData(statGD);

        Label a = new Label(mainComp, SWT.NONE);
        a.setLayoutData(statGD);
        a = new Label(mainComp, SWT.NONE);
        a.setLayoutData(statGD);
        a = new Label(mainComp, SWT.NONE);
        a.setLayoutData(statGD);
        a = new Label(mainComp, SWT.NONE);
        a.setLayoutData(statGD);

        // Armor, shields, and saving throws
        armorLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        armorLabel.setLayoutData(statGD);

        acLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        acLabel.setLayoutData(statGD);

        ffLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        ffLabel.setLayoutData(statGD);

        touchLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        touchLabel.setLayoutData(statGD);

        shieldLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        shieldLabel.setLayoutData(statGD);

        reflexLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        reflexLabel.setLayoutData(statGD);

        fortLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        fortLabel.setLayoutData(statGD);

        willLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        willLabel.setLayoutData(statGD);



        /////////////// Weapons box row //////////////
        Composite weap1Comp = new Composite(mainComp, SWT.NONE);
        GridLayout weap1Grid = new GridLayout(6, true);
        weap1Comp.setLayout(weap1Grid);
        GridData weapCompGD = new GridData();
        weapCompGD.horizontalSpan = 4;
        weapCompGD.verticalSpan = 2;
        weapCompGD.horizontalAlignment = SWT.CENTER;
        weapCompGD.grabExcessHorizontalSpace = true;
        weap1Comp.setLayoutData(weapCompGD );

        GridData weapGD = new GridData();
        weapGD.horizontalAlignment = SWT.CENTER;
        weapGD.grabExcessHorizontalSpace = true;
        weapGD.widthHint = 200;
        weapGD.heightHint = 17;

        priLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        priLabel.setLayoutData(weapGD);



        priBonusLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        priBonusLabel.setLayoutData(weapGD);

        priDamageLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        priDamageLabel.setLayoutData(weapGD);

        priRangeLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        priRangeLabel.setLayoutData(weapGD);

        priCriticalLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        priCriticalLabel.setLayoutData(weapGD);

        priTypeLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        priTypeLabel.setLayoutData(weapGD);

        /////////////Secondary weapon box /////////
        secLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        secLabel.setLayoutData(weapGD);

        secBonusLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        secBonusLabel.setLayoutData(weapGD);

        secDamageLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        secDamageLabel.setLayoutData(weapGD);

        secRangeLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        secRangeLabel.setLayoutData(weapGD);

        secCriticalLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        secCriticalLabel.setLayoutData(weapGD);

        secTypeLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        secTypeLabel.setLayoutData(weapGD);
        weap1Comp.pack();


        Button uploadButton = new Button(mainComp, SWT.PUSH);
        uploadButton.setText("Upload Image");

        GridData uploadGD = new GridData();
        uploadGD.horizontalAlignment = SWT.CENTER;
        uploadGD.grabExcessHorizontalSpace = true;
        uploadButton.setLayoutData(uploadGD);
        uploadButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                FileDialog dialog = new FileDialog(m_shell, SWT.OPEN);
                dialog.setText("Open");
                dialog.setFilterPath(GameState.IMAGESFOLDER.toString());
                String[] filterExt = { "*.png;*.jpg;*.gif"};
                dialog.setFilterExtensions(filterExt);
                String selected = dialog.open();
                if (selected == null || selected.equals("")) {
                    return;
                }
                m_characterImage = new Image(Display.getCurrent(), selected);
                img.setImage(m_characterImage);


                File imageFile = new File(selected);
                String imageName = imageFile.getName().replaceAll("\\s", ""); 
                String spacelessName = charName.replaceAll("[^A-Za-z0-9]", "");
                File copiedImageFile = new File(System.getProperty("user.dir") + "//" + 
                        "User Data" + "//Character" + "//DND" + spacelessName, imageFile.getName());
                writeValue("Image", System.getProperty("user.dir") + "//" + 
                        "User Data" + "//Character" + "//DND" + spacelessName + "//" 
                        + imageName, element);
                try {
                    FileUtils.copyFile(imageFile, copiedImageFile);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }
        }); 

        Label nameLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        nameLabel.setText(charName);
        nameLabel.setLayoutData(statGD);



        priCombo = new Combo(mainComp, SWT.CENTER | SWT.READ_ONLY);
        priCombo.setLayoutData(statGD);

        secCombo = new Combo(mainComp, SWT.CENTER | SWT.READ_ONLY);
        secCombo.setLayoutData(statGD);

        armorCombo = new Combo(mainComp, SWT.CENTER | SWT.READ_ONLY);
        armorCombo.setLayoutData(statGD);

        shieldCombo = new Combo(mainComp, SWT.CENTER | SWT.READ_ONLY);
        shieldCombo.setLayoutData(statGD);

        initLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        initLabel.setLayoutData(statGD);


        GridData buttonGD = new GridData();
        buttonGD.horizontalAlignment = SWT.CENTER;
        buttonGD.grabExcessHorizontalSpace = true;
        buttonGD.widthHint = 160;

        new Label(mainComp, SWT.NONE);
        new Label(mainComp, SWT.NONE);
        new Label(mainComp, SWT.NONE);

        Button change = new Button(mainComp, SWT.PUSH);
        change.setText("Change");
        change.setLayoutData(buttonGD);

        change.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {

                if (shieldCombo.getSelectionIndex() == 0) {

                } else if (shieldCombo.getSelectionIndex() == 1) {
                    shieldName = "";
                    c.setCurrShield(null);
                }
                else {
                    shieldName = shieldCombo.getText();
                    c.setCurrShield((ItemEntity)Main.gameState.armor.get(shieldName));
                }

                if (armorCombo.getSelectionIndex() == 0) {

                } else if (armorCombo.getSelectionIndex() == 1) {
                    armorName = "";
                    c.setCurrArmor(null);
                }
                else {
                    armorName = armorCombo.getText();
                    c.setCurrArmor((ItemEntity)Main.gameState.armor.get(armorName));
                }

                if (priCombo.getSelectionIndex() == 0) {

                } else if (priCombo.getSelectionIndex() == 1) {
                    priWeapon = "";
                    c.setPrimaryWeapon(null);
                }
                else {
                    priWeapon = priCombo.getText();
                    c.setPrimaryWeapon((WeaponEntity) Main.gameState.weapons.get(priWeapon));
                }


                if (secCombo.getSelectionIndex() == 0) {

                } else if (secCombo.getSelectionIndex() == 1) {
                    secWeapon = "";
                    c.setSecondaryWeapon(null);
                }
                else {
                    secWeapon = secCombo.getText();
                    c.setSecondaryWeapon((WeaponEntity) Main.gameState.weapons.get(secWeapon));
                }

                refresh();
            }
        }); 

        skillCombo = new Combo(mainComp, SWT.CENTER | SWT.READ_ONLY);
        skillCombo.setLayoutData(statGD);

        specAbilCombo = new Combo(mainComp, SWT.CENTER | SWT.READ_ONLY);
        specAbilCombo.setLayoutData(statGD);

        featCombo = new Combo(mainComp, SWT.CENTER | SWT.READ_ONLY);
        featCombo.setLayoutData(statGD);

        languageCombo = new Combo(mainComp, SWT.CENTER | SWT.READ_ONLY);
        languageCombo.setLayoutData(statGD);

        inventoryCombo = new Combo(mainComp, SWT.CENTER | SWT.READ_ONLY);
        inventoryCombo.setLayoutData(statGD);

        Button infoButt = new Button(mainComp, SWT.CENTER | SWT.PUSH);
        infoButt.setText("Character Info");
        infoButt.setLayoutData(buttonGD);
        infoButt.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                toTooltipWindow();
            }
        }); 

        Button spellButt = new Button(mainComp, SWT.CENTER | SWT.PUSH);
        spellButt.setText("Spell Manager");
        spellButt.setLayoutData(buttonGD);
        spellButt.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                new SpellGUI(args[0]);
            }
        }); 

        Button featButt = new Button(mainComp, SWT.CENTER | SWT.PUSH);
        featButt.setText("Feat Wizard");
        featButt.setLayoutData(buttonGD);
        featButt.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                new FeatWizard(shell.getDisplay());
            }
        }); 

        new Label(mainComp, SWT.NONE);

        Button inventoryButt = new Button(mainComp, SWT.CENTER | SWT.PUSH);
        inventoryButt.setText("Item Wizard");
        inventoryButt.setLayoutData(buttonGD);

        inventoryButt.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                new ItemWizard(Display.getCurrent());
            }
        }); 

        StyledText notesText = new StyledText(mainComp, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        notesText.setText(notes);
        GridData notesGD = new GridData(SWT.FILL, SWT.FILL, true, true);
        notesGD.horizontalSpan = 5;
        notesText.setLayoutData(notesGD);

        //Composite for damage
        Composite currencyComp = new Composite(mainComp, SWT.NONE);
        GridLayout currencyGrid = new GridLayout(10, true);
        currencyComp.setLayout(currencyGrid);

        GridData currencyGD = new GridData();
        currencyGD.horizontalAlignment = SWT.CENTER;
        currencyGD.grabExcessHorizontalSpace = true;
        currencyGD.widthHint = 140;
        currencyGD.heightHint = 17;

        GridData amountGD = new GridData();
        amountGD.horizontalAlignment = SWT.CENTER;
        amountGD.widthHint = 40;
        amountGD.heightHint = 17;

        //Money Tracker
        Label ppLabel = new Label(currencyComp, SWT.BORDER | SWT.CENTER);
        ppLabel.setText("PP");
        ppLabel.setLayoutData(currencyGD);

        StyledText ppText = new StyledText(currencyComp, SWT.BORDER );
        ppText.setText(pp);
        ppText.setLayoutData(amountGD);

        Label gpLabel = new Label(currencyComp, SWT.BORDER | SWT.CENTER);
        gpLabel.setText("GP");
        gpLabel.setLayoutData(currencyGD);

        StyledText gpText = new StyledText(currencyComp, SWT.BORDER);
        gpText.setText(gp);
        gpText.setLayoutData(amountGD);

        Label spLabel = new Label(currencyComp, SWT.BORDER | SWT.CENTER);
        spLabel.setText("SP");
        spLabel.setLayoutData(currencyGD);

        StyledText spText = new StyledText(currencyComp, SWT.BORDER);
        spText.setText(sp);
        spText.setLayoutData(amountGD);

        Label cpLabel = new Label(currencyComp, SWT.BORDER | SWT.CENTER);
        cpLabel.setText("CP");
        cpLabel.setLayoutData(currencyGD);

        StyledText cpText = new StyledText(currencyComp, SWT.BORDER);
        cpText.setText(cp);
        cpText.setLayoutData(amountGD);

        Label expLabel = new Label(currencyComp, SWT.BORDER | SWT.CENTER);
        expLabel.setText("EXP");
        expLabel.setLayoutData(currencyGD);

        StyledText expText = new StyledText(currencyComp, SWT.BORDER);
        expText.setText(exp);
        expText.setLayoutData(amountGD);

        GridData currencyCompGD = new GridData();
        currencyCompGD.horizontalAlignment = SWT.CENTER;
        currencyCompGD.grabExcessHorizontalSpace = true;
        //currencyCompGD.widthHint = 120;
        currencyCompGD.horizontalSpan = 4;

        currencyComp.setLayoutData(currencyCompGD);
        currencyComp.pack();
        
        Button levelUpButton = new LevelUpButton(mainComp, Main.gameState.currentlyLoadedCharacter).getButton();
        //GridData gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
        levelUpButton.setLayoutData(buttonGD);
        
        new Label(mainComp, SWT.NONE).setLayoutData(buttonGD);
        new Label(mainComp, SWT.NONE).setLayoutData(buttonGD);
        new Label(mainComp, SWT.NONE).setLayoutData(buttonGD);
        new Label(mainComp, SWT.NONE).setLayoutData(buttonGD);

        //Save All Information
        Button saveAllButt = new Button(mainComp, SWT.CENTER | SWT.PUSH);
        saveAllButt.setText("Save All");
        saveAllButt.setLayoutData(buttonGD);
        saveAllButt.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                notes = notesText.getText();
                boolean failed = false;
                exp = expText.getText();
                gp = gpText.getText();
                pp = ppText.getText();
                sp = spText.getText();
                cp = cpText.getText();
                dmgTaken = dmgText.getText();
                try {
                    c.setNotes(notes);
                    c.setExp(Integer.parseInt(exp));
                    c.setPP(Integer.parseInt(pp));
                    c.setGP(Integer.parseInt(gp));
                    c.setSP(Integer.parseInt(sp));
                    c.setCP(Integer.parseInt(cp));
                    c.setDamageTaken(Integer.parseInt(dmgTaken));
                } catch (Exception ex) {
                    failed = true;
                }
                if(!failed) {
                    new SaveCharacter(false);
                }
                else {
                    //TODO notify failure
                }

                /*writeValue("Notes", notes, element);
                String damageTaken = dmgText.getText();
                writeValue("DamageTaken", damageTaken, element);
                writeValue("Shield", shieldName, element);
                writeValue("Armor", armorName, element);
                writeValue("PrimaryWeapon", priWeapon, element);
                writeValue("SecondaryWeapon", secWeapon, element);
                writeValue("PP", ppText.getText(), element);
                writeValue("GP", gpText.getText(), element);
                writeValue("SP", spText.getText(), element);
                writeValue("CP", cpText.getText(), element);
                writeValue("Exp", expText.getText(), element);*/
            }
        });

        refresh();

        //new Label(mainComp, SWT.NONE);
        mainComp.layout();

        //new MenuBar(shell); //Add menu bar to windows like this
        mainComp.pack();
        mainWindowLayout.topControl = mainComp;
        //mainWindow.layout();



        //shell.open(); // Open the Window and process the clicks
        //        while (!shell.isDisposed()) {
        //            if (display.readAndDispatch()) {
        //                display.sleep();
        //            }
        //        }


    }


    public Composite getMainWindow() {
        return mainWindow;
    }


    public void getPlayerInfo(String pathName, boolean red) {
        filename = pathName;
        if(red) new LoadCharacter(pathName, c);
        charName = c.getName();
        imagePath = c.getImage();
        charLevel = c.getLevel();
        charClass = c.getCharClass().getName();
        charSecLevel = c.getSecLevel();
        charSecClass = "";
        if(c.getSecClass() != null) {
            charSecClass = c.getSecClass().getName();
        }

        strVal = c.getAbilityScores()[0];
        dexVal = c.getAbilityScores()[1];
        conVal = c.getAbilityScores()[2];
        intVal = c.getAbilityScores()[3];
        wisVal = c.getAbilityScores()[4];
        chaVal = c.getAbilityScores()[5];
        armorName = "";
        if (c.getCurrArmor() != null) {
            armorName = c.getCurrArmor().getName();
            ArmorEntity ae = (ArmorEntity) c.getCurrArmor();
            c.setACArmorBonus(ae.getArmorBonus());
        }
        shieldName = "";
        if (c.getCurrShield() != null ) {
            shieldName = c.getCurrShield().getName();
            ArmorEntity ae = (ArmorEntity) c.getCurrShield();
            c.setACShieldBonus(ae.getArmorBonus());
        }

        acVal = 0;
        for (int i = 0; i < c.getAC().length; i++) {
            acVal += c.getAC()[i];
        }
        ffVal = c.getTouchAC();
        touchVal = c.getTouchAC();
        willVal = c.getWillSaveTotal();
        refVal = c.getReflexSaveTotal();
        fortVal = c.getFortSaveTotal();
        initVal = c.getInitModTotal();

        notes = c.getNotes();
        dmgTaken = "" + c.getDamageTaken(); //TODO
        pp = "" + c.getPP();//TODO
        gp = "" + c.getGP();//TODO
        sp = "" + c.getSP();//TODO
        cp = "" + c.getCP();//TODO
        exp = "" + c.getExp(); //TODO

        items = new ArrayList<String>();
        for(int i = 0; i < c.getItems().size(); i ++) {
            CharItem ci = c.getItems().get(i);
            items.add(ci.getName() + " (" + ci.getCount() + ")");
        }

        languages = new ArrayList<String>();
        for(int i = 0; i < c.getLanguages().size(); i ++) {
            String s = c.getLanguages().get(i);
            languages.add(s);
        }

        weapons = new ArrayList<String>();
        for(int i = 0; i < c.getWeapons().size(); i ++) {
            CharItem ci = c.getWeapons().get(i);
            items.add(ci.getName() + " (" + ci.getCount() + ")");
            weapons.add(ci.getName());
        }

        armors = new ArrayList<String>();
        for(int i = 0; i < c.getArmor().size(); i ++) {
            CharItem ci = c.getArmor().get(i);
            items.add(ci.getName() + " (" + ci.getCount() + ")");
            armors.add(ci.getName());
        }

        shields = new ArrayList<String>();
        for(int i = 0; i < c.getShields().size(); i ++) {
            CharItem ci = c.getShields().get(i);
            items.add(ci.getName() + " (" + ci.getCount() + ")");
            shields.add(ci.getName());
        }

        skills = new ArrayList<String>();
        for(int i = 0; i < c.getSkills().size(); i ++) {
            CharSkill cs = c.getSkills().get(i);
            skills.add(cs.getSkill().getName() + " (" + cs.getTotal()+ ")");
        }

        specialAbilities = new ArrayList<String>();
        for(int i = 0; i < c.getSpecialAbilities().size(); i ++) {
            AbilityEntity ae = c.getSpecialAbilities().get(i);
            specialAbilities.add(ae.getName());
        }

        feats = new ArrayList<String>();
        for(int i = 0; i < c.getFeats().size(); i ++) {
            CharFeat cf = c.getFeats().get(i);
            int count = cf.getCount();
            String addOn = "";
            if (cf.getSpecial() != null && !cf.getSpecial().equals("")){ 
                addOn += ": " + cf.getSpecial();
            }
            if ( count != 1) addOn += " (" + count + ")";
            feats.add(cf.getFeat().getName() + addOn);
        }
        items.sort(String.CASE_INSENSITIVE_ORDER);
        languages.sort(String.CASE_INSENSITIVE_ORDER);
        weapons.sort(String.CASE_INSENSITIVE_ORDER);
        armors.sort(String.CASE_INSENSITIVE_ORDER);
        shields.sort(String.CASE_INSENSITIVE_ORDER);
        skills.sort(String.CASE_INSENSITIVE_ORDER);
        feats.sort(String.CASE_INSENSITIVE_ORDER);

        speedVal = c.getSpeed();
        hpVal = c.getHitPoints();
        priWeapon = "";
        if(c.getPrimaryWeapon() != null) {
            priWeapon = c.getPrimaryWeapon().getName();
        }
        secWeapon = "";
        if(c.getSecondaryWeapon() != null) {
            secWeapon = c.getSecondaryWeapon().getName();
        }
        try {

            stocks = new File(filename);
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(stocks);
            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName("Character");

            Node node = nodes.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // TODO
        //if(red)new test(display);
    }



    private String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        String str;
        try {
            str = node.getNodeValue();
        } catch (NullPointerException n) {
            str = "0";
        }

        return str;
    }

    private boolean writeValue(String tag, String value, Element element){
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        node.setTextContent(value);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(stocks.getAbsolutePath());
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public void refresh() {
        //TODO
        //m_shell.setText("Character Page: " + charName);
        
        classLabel.setText("Class: " + charClass);

        hpLabel.setText("HP: " + c.getHitPoints());
        bonus = "";
        bonus += "Level: " + c.getLevel();
        if (charSecLevel != 0)bonus += "," + charSecLevel;
        levelLabel.setText(bonus);
        
        bonus = "";
        if (intVal >= 10) {
            bonus +="+";
        }
        intLabel.setText("INT: " + intVal + " (" + bonus + (intVal/2 - 5) + ")");
        
        bonus = "";
        if (conVal >= 10) {
            bonus +="+";
        }
        conLabel.setText("CON: " + conVal + " (" + bonus + (conVal/2 - 5) + ")");

        bonus = "";
        if (strVal >= 10) {
            bonus +="+";
        }
        strLabel.setText("STR: " + strVal + " (" + bonus + (strVal/2 - 5) + ")");

        bonus = "";
        if (dexVal >= 10) {
            bonus +="+";
        }
        dexLabel.setText("DEX: " + dexVal + " (" + bonus + (dexVal/2 - 5) + ")");

        bonus = "";
        if (wisVal >= 10) {
            bonus +="+";
        }

        wisLabel.setText("WIS: " + wisVal + " (" + bonus + (wisVal/2 - 5) + ")");
        bonus = "";
        if (chaVal >= 10) {
            bonus +="+";
        }
        chaLabel.setText("CHA: " + chaVal + " (" + bonus + (chaVal/2 - 5) + ")");

        speedLabel.setText("Speed: " + speedVal);

        acVal = c.getACTotal();
        ffVal = c.getTouchAC();
        touchVal = c.getTouchAC();

        items = new ArrayList<String>();
        for(int i = 0; i < c.getItems().size(); i ++) {
            CharItem ci = c.getItems().get(i);
            items.add(ci.getName() + " (" + ci.getCount() + ")");
        }

        languages = new ArrayList<String>();
        for(int i = 0; i < c.getLanguages().size(); i ++) {
            String s = c.getLanguages().get(i);
            languages.add(s);
        }

        weapons = new ArrayList<String>();
        for(int i = 0; i < c.getWeapons().size(); i ++) {
            CharItem ci = c.getWeapons().get(i);
            items.add(ci.getName() + " (" + ci.getCount() + ")");
            weapons.add(ci.getName());
        }

        armors = new ArrayList<String>();
        for(int i = 0; i < c.getArmor().size(); i ++) {
            CharItem ci = c.getArmor().get(i);
            items.add(ci.getName() + " (" + ci.getCount() + ")");
            armors.add(ci.getName());
        }

        shields = new ArrayList<String>();
        for(int i = 0; i < c.getShields().size(); i ++) {
            CharItem ci = c.getShields().get(i);
            items.add(ci.getName() + " (" + ci.getCount() + ")");
            shields.add(ci.getName());
        }

        skills = new ArrayList<String>();
        for(int i = 0; i < c.getSkills().size(); i ++) {
            CharSkill cs = c.getSkills().get(i);
            skills.add(cs.getSkill().getName() + " (" + cs.getTotal()+ ")");
        }

        specialAbilities = new ArrayList<String>();
        for(int i = 0; i < c.getSpecialAbilities().size(); i ++) {
            AbilityEntity ae = c.getSpecialAbilities().get(i);
            specialAbilities.add(ae.getName());
        }

        feats = new ArrayList<String>();
        for(int i = 0; i < c.getFeats().size(); i ++) {
            CharFeat cf = c.getFeats().get(i);
            int count = cf.getCount();
            String addOn = "";
            if (cf.getSpecial() != null && !cf.getSpecial().equals("")){ 
                addOn += ": " + cf.getSpecial();
            }
            if ( count != 1) addOn += " (" + count + ")";
            feats.add(cf.getFeat().getName() + addOn);
        }
        items.sort(String.CASE_INSENSITIVE_ORDER);
        languages.sort(String.CASE_INSENSITIVE_ORDER);
        weapons.sort(String.CASE_INSENSITIVE_ORDER);
        armors.sort(String.CASE_INSENSITIVE_ORDER);
        shields.sort(String.CASE_INSENSITIVE_ORDER);
        skills.sort(String.CASE_INSENSITIVE_ORDER);
        feats.sort(String.CASE_INSENSITIVE_ORDER);


        armorLabel.setText(armorName);

        acLabel.setText("AC: " + acVal);
        ffLabel.setText("Flat Footed: " + ffVal);
        touchLabel.setText("Touch AC: " + touchVal);
        shieldLabel.setText(shieldName);
        reflexLabel.setText("Reflex: " + refVal);
        fortLabel.setText("Fortitude: " + fortVal);
        willLabel.setText("Will: " + willVal);

        boo = false;
        if (!priWeapon.equals("")) {
            boo = true;
            priVals[0] = "" + c.getBaseAttackBonus();
            priVals[1] = c.getPrimaryWeapon().getDamageMedium();
            priVals[2] = c.getPrimaryWeapon().getRange();
            int i = c.getPrimaryWeapon().getCriticalRange()[0];
            priVals[3] = "";
            if (i != 0) priVals[3] = "" + i + "-20";
            priVals[4] = "" + c.getPrimaryWeapon().getCriticalMultiplier();
            priVals[5] = c.getPrimaryWeapon().getDamageType();
        }

        priBonusLabel.setText("Bonus: " + (boo ? priVals[0] : ""));
        priDamageLabel.setText("Damage: " + (boo ? priVals[1] : ""));
        priRangeLabel.setText("Range: " + (boo ? priVals[2] : ""));
        priCriticalLabel.setText("Crit: " + (boo ? priVals[3] + "  x" + priVals[4]: ""));
        priTypeLabel.setText("Type: " + (boo ? priVals[5] : ""));

        boo = false;
        if (!secWeapon.equals("")) {
            boo = true;
            secVals[0] = "" + c.getBaseAttackBonus();
            secVals[1] = c.getSecondaryWeapon().getDamageMedium();
            secVals[2] = c.getSecondaryWeapon().getRange();
            int i = c.getSecondaryWeapon().getCriticalRange()[0];
            secVals[3] = "";
            if (i != 0) secVals[3] = "" + i + "-20";
            secVals[4] = "" + c.getSecondaryWeapon().getCriticalMultiplier();
            secVals[5] = c.getSecondaryWeapon().getDamageType();
        }

        secLabel.setText(secWeapon);
        secBonusLabel.setText("Bonus: " + (boo ? secVals[0] : ""));
        secDamageLabel.setText("Damage: " + (boo ? secVals[1] : ""));
        secRangeLabel.setText("Range: " + (boo ? secVals[2] : ""));
        secCriticalLabel.setText("Crit: " + (boo ? secVals[3] + "  x" + secVals[4]: ""));
        secTypeLabel.setText("Type: " + (boo ? secVals[5] : ""));

        priLabel.setText(priWeapon);
        secLabel.setText(secWeapon);
        armorLabel.setText(armorName);
        shieldLabel.setText(shieldName);


        strArr = new String[weapons.size()];
        priCombo.setItems(weapons.toArray(strArr));
        priCombo.add("Primary", 0);
        priCombo.add("None", 1);
        priCombo.select(0);

        strArr = new String[weapons.size()];
        secCombo.setItems(weapons.toArray(strArr));
        secCombo.add("Secondary", 0);
        secCombo.add("None", 1);
        secCombo.select(0);

        strArr = new String[armors.size()];
        armorCombo.setItems(armors.toArray(strArr));
        armorCombo.add("Armor", 0);
        armorCombo.add("None", 1);
        armorCombo.select(0);

        strArr = new String[shields.size()];
        shieldCombo.setItems(shields.toArray(strArr));
        shieldCombo.add("Shield", 0);
        shieldCombo.add("None", 1);
        shieldCombo.select(0);

        initLabel.setText("Initiative: " + initVal);

        strArr = new String[skills.size()];
        skillCombo.setItems(skills.toArray(strArr));
        skillCombo.add("Skills", 0);
        skillCombo.select(0);

        strArr = new String[specialAbilities.size()];
        specAbilCombo.setItems(specialAbilities.toArray(strArr));
        specAbilCombo.add("Special Abilities", 0);
        specAbilCombo.select(0);

        strArr = new String[feats.size()];
        featCombo.setItems(feats.toArray(strArr));
        featCombo.add("Feats", 0);
        featCombo.select(0);

        strArr = new String[languages.size()];
        languageCombo.setItems(languages.toArray(strArr));
        languageCombo.add("Languages", 0);
        languageCombo.select(0);

        strArr = new String[items.size()];
        inventoryCombo.setItems(items.toArray(strArr));
        inventoryCombo.add("Inventory", 0);;
        inventoryCombo.select(0);

    }

    public Composite getMainComp() {
        return mainComp;
    }

    private void toTooltipWindow(){

        Display display = Display.getCurrent();
        Shell shell = new Shell(display);
        Image logo = new Image(display, "images/bnb_logo.gif");
        shell.setImage(logo);
        Monitor monitor = display.getPrimaryMonitor();
        Rectangle bounds = monitor.getBounds();
        int WIDTH = 700;
        int HEIGHT = (int)(bounds.height * 2.0/3.0);

        ScrolledComposite sc = new ScrolledComposite(shell, SWT.V_SCROLL | SWT.H_SCROLL);
        sc.setBounds(0, 0, WIDTH - 20, HEIGHT - 50);
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        Composite com = new Composite(sc, SWT.NONE);
        sc.setContent(com);
        com.setSize(com.computeSize(SWT.DEFAULT, SWT.DEFAULT)); 
        GridLayout layout = new GridLayout(1, false);
        com.setLayout(layout);

        //Font boldFont = new Font(display, new FontData( display.getSystemFont().getFontData()[0].getName(), 12, SWT.BOLD ));

        Label textLabel = new Label(com, SWT.NONE);
        String windowSize = "(.{" + bounds.width / 18 + "} )";
        //This guy finds a space every 120 characters and makes a new line, nice text formatting for the tooltip windows
        String parsedStr = c.toString().replaceAll(windowSize, "$1\n");
        String finalString = "";
        String[] split = parsedStr.split("\n");
        for(int i = 0; i < split.length; i++){
            finalString += split[i].trim() + "\n";
        }

        //parsedStr = parsedStr.replaceAll("\t", "");
        textLabel.setText(finalString);
        textLabel.pack();

        com.pack();
        sc.setMinHeight(com.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);


        shell.setLocation((int)(bounds.width * .75) - com.getSize().x / 2, (int)(bounds.height * .05));

        shell.pack();
        shell.open();

        while(!shell.isDisposed()){
            if(!display.readAndDispatch())
                display.sleep();
        }
    }


}
