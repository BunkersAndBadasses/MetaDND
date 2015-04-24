package guis;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.Main;


public class CharacterMain {
    private static DocumentBuilderFactory dbFactory;
    private static DocumentBuilder dBuilder;
    private static Document doc;
    private static String filename;
    private static Element element;
    private static String charName;
    private static String shieldName;
    private static String armorName;
    private static String charClass;
    private static int charLevel;
    private static String charSecClass;
    private static int charSecLevel;
    private static int strVal;
    private static int dexVal;
    private static int conVal;
    private static int intVal;
    private static int wisVal;
    private static int chaVal;
    private static int hpVal;
    private static int speedVal;
    private static int acVal;
    private static int ffVal;
    private static int touchVal;
    private static int fortVal;
    private static int refVal;
    private static int willVal;
    private static int initVal;
    private static String priWeapon;
    private static String secWeapon;
    private static String[] items;
    private static String[] languages;
    private static String[] weapons;
    private static String[] armors;
    private static String[] skills;
    private static String[] spells;
    private static String[] shields;
    private static String[] feats;
    private static String notes;
    private static String imagePath;
    private static String dmgTaken;
    private static String delims = "[/]+";
    private static String pp, gp, sp, cp;
    private static File stocks;
    private static StackLayout mainWindowLayout;
    private static Display display;
    private static Shell shell;
    private static Composite mainWindow;
    private static Composite mainComp;
    private static GridLayout charLayout;
    private static String bonus;
    private static String exp;


    public static void main(String[] args) {
        String pathName = args[0];
        getPlayerInfo(pathName);

        // TODO Auto-generated method stub
        display = Display.getCurrent();
        shell = new Shell(display);
        shell.setLayout(new GridLayout(1, false));
        new MenuBar(shell); //Add menu bar to windows like this
        mainWindow = new Composite(shell, SWT.NONE);
        mainWindow.setLayoutData(new GridData(GridData.FILL_BOTH));
        mainWindowLayout = new StackLayout();
        mainWindow.setLayout(mainWindowLayout);
        mainComp = new Composite(mainWindow, SWT.NONE);
        charLayout = new GridLayout(5, true);
        charLayout.makeColumnsEqualWidth = true;
        mainComp.setLayout(charLayout);
        

        shell.setImage(new Image(display, "images/bnb_logo.gif"));

        GridData imageGD = new GridData();
        imageGD.verticalSpan = 8;
        imageGD.grabExcessHorizontalSpace = true;
        imageGD.grabExcessVerticalSpace = false;
        imageGD.heightHint = 188;
        imageGD.horizontalAlignment = SWT.CENTER;
        imageGD.widthHint = 155;
        Label img = new Label(mainComp, SWT.CENTER);
        if (imagePath.equals(" ")) {
            imagePath = "images/SetWidth150-blank-profile.jpg";
        }
        Image image = new Image(Display.getCurrent(), imagePath);
        img.setImage(image);
        img.setLayoutData(imageGD);
        img.pack();

        GridData statGD = new GridData();
        statGD.horizontalAlignment = SWT.CENTER;
        statGD.grabExcessHorizontalSpace = true;
        statGD.widthHint = 160;
        statGD.heightHint = 17;
        bonus = "";
        if (strVal >= 10) {
            bonus +="+";
        }
        Label strLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        strLabel.setText("STR: " + strVal + " (" + bonus + (strVal/2 - 5) + ")");
        strLabel.setLayoutData(statGD);

        bonus = "";
        if (dexVal >= 10) {
            bonus +="+";
        }
        Label dexLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        dexLabel.setText("DEX: " + dexVal + " (" + bonus + (dexVal/2 - 5) + ")");
        dexLabel.setLayoutData(statGD);

        Label classLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        classLabel.setText("Class: " + charClass);
        classLabel.setLayoutData(statGD);

        Label hpLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        hpLabel.setText("HP: " + hpVal);
        hpLabel.setLayoutData(statGD);
        hpLabel.pack();

        bonus = "";
        if (conVal >= 10) {
            bonus +="+";
        }
        Label conLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        conLabel.setText("CON: " + conVal + " (" + bonus + (conVal/2 - 5) + ")");
        conLabel.setLayoutData(statGD);

        bonus = "";
        if (intVal >= 10) {
            bonus +="+";
        }
        Label intLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        intLabel.setText("INT: " + intVal + " (" + bonus + (intVal/2 - 5) + ")");
        intLabel.setLayoutData(statGD);

        Label secClassLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        secClassLabel.setText("Sec Class: " + charSecClass);
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

        bonus = "";
        if (wisVal >= 10) {
            bonus +="+";
        }
        Label wisLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        wisLabel.setText("WIS: " + wisVal + " (" + bonus + (wisVal/2 - 5) + ")");
        wisLabel.setLayoutData(statGD);

        bonus = "";
        if (chaVal >= 10) {
            bonus +="+";
        }
        Label chaLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        chaLabel.setText("CHA: " + chaVal + " (" + bonus + (chaVal/2 - 5) + ")");
        chaLabel.setLayoutData(statGD);

        Label levelLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        levelLabel.setText("Level: " + charLevel + ", " + charSecLevel);
        levelLabel.setLayoutData(statGD);

        Label speedLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        speedLabel.setText("Speed: " + speedVal);
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
        Label armorLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        armorLabel.setText(armorName);
        armorLabel.setLayoutData(statGD);

        Label acLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        acLabel.setText("AC: " + acVal);
        acLabel.setLayoutData(statGD);

        Label ffLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        ffLabel.setText("Flat Footed: " + ffVal);
        ffLabel.setLayoutData(statGD);

        Label touchLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        touchLabel.setText("Touch AC: " + touchVal);
        touchLabel.setLayoutData(statGD);

        Label shieldLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        shieldLabel.setText(shieldName);
        shieldLabel.setLayoutData(statGD);

        Label reflexLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        reflexLabel.setText("Reflex: " + refVal);
        reflexLabel.setLayoutData(statGD);

        Label fortLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        fortLabel.setText("Fortitude: " + fortVal);
        fortLabel.setLayoutData(statGD);

        Label willLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        willLabel.setText("Will: " + willVal);
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
        
        Label priLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        priLabel.setText(priWeapon);
        priLabel.setLayoutData(weapGD);

        Label priBonusLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        priBonusLabel.setText("Bonus: ");
        priBonusLabel.setLayoutData(weapGD);

        Label priDamageLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        priDamageLabel.setText("Damage: ");
        priDamageLabel.setLayoutData(weapGD);

        Label priRangeLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        priRangeLabel.setText("Range: ");
        priRangeLabel.setLayoutData(weapGD);

        Label priCriticalLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        priCriticalLabel.setText("Crit: ");
        priCriticalLabel.setLayoutData(weapGD);

        Label priTypeLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        priTypeLabel.setText("Type: ");
        priTypeLabel.setLayoutData(weapGD);

        /////////////Secondary weapon box /////////
        Label secLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        secLabel.setText(secWeapon);
        secLabel.setLayoutData(weapGD);

        Label secBonusLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        secBonusLabel.setText("Bonus: ");
        secBonusLabel.setLayoutData(weapGD);

        Label secDamageLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        secDamageLabel.setText("Damage: ");
        secDamageLabel.setLayoutData(weapGD);

        Label secRangeLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        secRangeLabel.setText("Range: ");
        secRangeLabel.setLayoutData(weapGD);

        Label secCriticalLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        secCriticalLabel.setText("Crit: ");
        secCriticalLabel.setLayoutData(weapGD);

        Label secTypeLabel = new Label(weap1Comp, SWT.BORDER | SWT.CENTER);
        secTypeLabel.setText("Type: ");
        secTypeLabel.setLayoutData(weapGD);
        weap1Comp.pack();
        
        
        Label nameLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        nameLabel.setText(charName);
        nameLabel.setLayoutData(statGD);
        
        
        
        Combo priCombo = new Combo(mainComp, SWT.CENTER);
        priCombo.setItems(weapons);
        priCombo.add("Primary", 0);
        priCombo.add("None", 1);
        priCombo.select(0);
        priCombo.setLayoutData(statGD);
        
        Combo secCombo = new Combo(mainComp, SWT.CENTER);
        secCombo.setItems(weapons);
        secCombo.add("Secondary", 0);
        secCombo.add("None", 1);
        secCombo.select(0);
        secCombo.setLayoutData(statGD);

        Combo armorCombo = new Combo(mainComp, SWT.CENTER);
        armorCombo.setItems(armors);
        armorCombo.add("Armor", 0);
        armorCombo.add("None", 1);
        armorCombo.select(0);
        armorCombo.setLayoutData(statGD);

        Combo shieldCombo = new Combo(mainComp, SWT.CENTER);
        shieldCombo.setItems(shields);
        shieldCombo.add("Shield", 0);
        shieldCombo.add("None", 1);
        shieldCombo.select(0);
        shieldCombo.setLayoutData(statGD);
        
        Label initLabel = new Label(mainComp, SWT.BORDER | SWT.CENTER);
        initLabel.setText("Initiative: " + initVal);
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
                    shieldName = " ";
                }
                else {
                    shieldName = shieldCombo.getText();
                }

                if (armorCombo.getSelectionIndex() == 0) {

                } else if (armorCombo.getSelectionIndex() == 1) {
                    armorName = " ";
                }
                else {
                    armorName = armorCombo.getText();
                }

                if (priCombo.getSelectionIndex() == 0) {

                } else if (priCombo.getSelectionIndex() == 1) {
                    priWeapon = " ";
                }
                else {
                    priWeapon = priCombo.getText();
                }


                if (secCombo.getSelectionIndex() == 0) {

                } else if (secCombo.getSelectionIndex() == 1) {
                    secWeapon = " ";
                }
                else {
                    secWeapon = secCombo.getText();
                }
                priLabel.setText(priWeapon);
                secLabel.setText(secWeapon);
                armorLabel.setText(armorName);
                shieldLabel.setText(shieldName);
            }
        }); 
        
        Combo skillCombo = new Combo(mainComp, SWT.CENTER);
        skillCombo.setItems(skills);
        skillCombo.add("Skills", 0);
        skillCombo.select(0);
        skillCombo.setLayoutData(statGD);

        Combo spellCombo = new Combo(mainComp, SWT.CENTER);
        spellCombo.setItems(spells);
        spellCombo.add("Spells", 0);
        spellCombo.select(0);
        spellCombo.setLayoutData(statGD);

        Combo featCombo = new Combo(mainComp, SWT.CENTER);
        featCombo.setItems(feats);
        featCombo.add("Feats", 0);
        featCombo.select(0);
        featCombo.setLayoutData(statGD);

        Combo languageCombo = new Combo(mainComp, SWT.CENTER);
        languageCombo.setItems(languages);
        languageCombo.add("Languages", 0);
        languageCombo.select(0);
        languageCombo.setLayoutData(statGD);

        Combo inventoryCombo = new Combo(mainComp, SWT.CENTER);
        inventoryCombo.setItems(items);
        inventoryCombo.add("Inventory", 0);;
        inventoryCombo.select(0);
        inventoryCombo.setLayoutData(statGD);
        
        new Label(mainComp, SWT.NONE);
        
        Button spellButt = new Button(mainComp, SWT.CENTER | SWT.PUSH);
        spellButt.setText("Spell Manager");
        spellButt.setLayoutData(buttonGD);
        spellButt.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                SpellGUI.main(args);
            }
        }); 

        Button featButt = new Button(mainComp, SWT.CENTER | SWT.PUSH);
        featButt.setText("Feat Wizard");
        featButt.setLayoutData(buttonGD);
        featButt.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                //TODO
            }
        }); 
        
        new Label(mainComp, SWT.NONE);

        Button inventoryButt = new Button(mainComp, SWT.CENTER | SWT.PUSH);
        inventoryButt.setText("Item Wizard");
        inventoryButt.setLayoutData(buttonGD);

        inventoryButt.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // TODO reset table
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
        
      //Save All Information
        Button saveAllButt = new Button(mainComp, SWT.CENTER | SWT.PUSH);
        saveAllButt.setText("Save All");
        saveAllButt.setLayoutData(buttonGD);
        saveAllButt.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                notes = notesText.getText();
                writeValue("Notes", notes, element);
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
                writeValue("XP", expText.getText(), element);
            }
        });
        
        //new Label(mainComp, SWT.NONE);
        mainComp.layout();

        //new MenuBar(shell); //Add menu bar to windows like this
        mainComp.pack();
        mainWindowLayout.topControl = mainComp;
        //mainWindow.layout();



        shell.open(); // Open the Window and process the clicks
        while (!shell.isDisposed()) {
            if (display.readAndDispatch()) {
                display.sleep();
            }
        }


    }


    private static void getPlayerInfo(String pathName) {
        // TODO Auto-generated method stub
        filename = pathName;
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
                imagePath = getValue("Image", element);
                charName = getValue("Name", element);
                charLevel = Integer.parseInt(getValue("Level", element));
                charClass = getValue("Class", element);
                charSecLevel = Integer.parseInt(getValue("SecLevel", element));
                charSecClass = getValue("SecClass", element);
                strVal = Integer.parseInt(getValue("STR", element));
                dexVal = Integer.parseInt(getValue("DEX", element));
                conVal = Integer.parseInt(getValue("CON", element));
                intVal = Integer.parseInt(getValue("INT", element));
                wisVal = Integer.parseInt(getValue("WIS", element));
                chaVal = Integer.parseInt(getValue("CHA", element));
                hpVal = Integer.parseInt(getValue("HP", element));
                speedVal = Integer.parseInt(getValue("Speed", element));
                priWeapon = getValue("PrimaryWeapon", element);
                secWeapon =  getValue("SecondaryWeapon", element);
                armorName = getValue("Armor", element);
                shieldName  = getValue("Shield", element);
                notes = getValue("Notes", element);
                dmgTaken = getValue("DamageTaken", element);
                pp = getValue("PP", element);
                gp = getValue("GP", element);
                sp = getValue("SP", element);
                cp = getValue("CP", element);
                exp = getValue("XP", element);

                String raw = getValue("Items", element);
                items = raw.split(delims);

                raw = getValue("Languages", element);
                languages = raw.split(delims);

                raw = getValue("Weapons", element);
                weapons = raw.split(delims);

                raw = getValue("Armors", element);
                armors = raw.split(delims);

                raw = getValue("Skills", element);
                skills = raw.split(delims);

                raw = getValue("Spells", element);
                spells = raw.split(delims);

                raw = getValue("Shields", element);
                shields = raw.split(delims);

                raw = getValue("Feats", element);
                feats = raw.split(delims);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        String str;
        try {
            str = node.getNodeValue();
        } catch (NullPointerException n) {
            str = "0"; // TODO looky here
        }

        return str;
    }

    private static boolean writeValue(String tag, String value, Element element){
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

}
