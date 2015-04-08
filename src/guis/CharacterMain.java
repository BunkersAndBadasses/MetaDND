package guis;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;


public class CharacterMain {
	private static String charName;
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
	private static int dmgVal;
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
	private static String shield;
	private static String armor;
	private static Color background;
	

	public static void main(String[] args) {
		getPlayerInfo();
		
		
		// TODO Auto-generated method stub
		Display display = new Display();
		Shell shell = new Shell(display);
		background = new Color(display, 255, 222, 173);
		


		new MenuBar(shell); //Add menu bar to windows like this
		
		FormLayout layout = new FormLayout();
		shell.setLayout(layout);
		
		Label label = new Label(shell, SWT.NONE);
		Image image = new Image(display, "images/SetWidth150-blank-profile.jpg");
        label.setImage(image);
        
		FormData imageData = new FormData(150,188);
		
		imageData.left = new FormAttachment(10);
		imageData.top = new FormAttachment(10);
		label.setLayoutData(imageData);

		Label nameLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		nameLabel.setText(charName);
		GC gc = new GC(nameLabel);
		Point length = gc.textExtent("Name: " + charName);
		FormData nameData = new FormData(/*length.x*/ 150,17);
		nameLabel.setBackground(background);
		nameData.left = new FormAttachment(label, 0, SWT.LEFT);
		nameData.top = new FormAttachment(label, 10, SWT.BOTTOM);
		nameLabel.setLayoutData(nameData);
		
		Label initLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		initLabel.setText("Initiative: " + initVal);
		FormData initData = new FormData(150,17);
		initLabel.setBackground(background);
		initData.left = new FormAttachment(nameLabel, 0, SWT.LEFT);
		initData.top = new FormAttachment(nameLabel, 10, SWT.BOTTOM);
		initLabel.setLayoutData(initData);
		
		Label tempLabel1 = coreStatGUI(shell, label);
		Label tempLabel2 = classBoxGUI(shell, tempLabel1);
		Label tempLabel3 = saveBoxGUI(shell, label);
		Label tempLabel4 = weaponBoxGUI(shell, tempLabel3);
		Label tempLabel5 = weaponSecBoxGUI(shell, tempLabel4);
		Combo tempLabel6 = selBoxGUI(shell, tempLabel5);
		Combo tempLabel7 = featsEtcBox(shell, initLabel, args);
		
		Text notesText = new Text(shell, SWT.BORDER);
		notesText.setText("(Notes)");
		FormData notesData = new FormData(660,100);
		notesText.setBackground(background);
		notesData.left = new FormAttachment(tempLabel7, 0, SWT.LEFT);
		notesData.top = new FormAttachment(tempLabel7, 50, SWT.BOTTOM);
		notesText.setLayoutData(notesData);
		
		Button saveNotesButt = new Button(shell, SWT.CENTER | SWT.PUSH);
		saveNotesButt.setText("Save Notes");
		FormData saveNotesData = new FormData(127,24);
		saveNotesButt.setBackground(background);
		saveNotesData.right = new FormAttachment(notesText, 0, SWT.RIGHT);
		saveNotesData.top = new FormAttachment(notesText, 10, SWT.BOTTOM);
		saveNotesButt.setLayoutData(saveNotesData);
		saveNotesButt.addSelectionListener(new SelectionAdapter() {
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
	
	
	private static Combo featsEtcBox(Shell shell, Label label, final String[] args) {
		Combo skillCombo = new Combo(shell, SWT.CENTER);
		skillCombo.add("Skill");
		skillCombo.select(0);
		FormData skillComboData = new FormData(99,5);
		skillCombo.setBackground(background);
		skillComboData.left = new FormAttachment(label, 0, SWT.LEFT);
		skillComboData.top = new FormAttachment(label, 20, SWT.BOTTOM);
		skillCombo.setLayoutData(skillComboData);
		
		Combo spellCombo = new Combo(shell, SWT.CENTER);
		spellCombo.add("Spells");
		spellCombo.select(0);
		FormData spellComboData = new FormData(100,5);
		spellCombo.setBackground(background);
		spellComboData.left = new FormAttachment(skillCombo, 10, SWT.RIGHT);
		spellComboData.top = new FormAttachment(skillCombo, 0, SWT.TOP);
		spellCombo.setLayoutData(spellComboData);
		
		Combo featCombo = new Combo(shell, SWT.CENTER);
		featCombo.add("Feats");
		featCombo.select(0);
		FormData featComboData = new FormData(99,5);
		featCombo.setBackground(background);
		featComboData.left = new FormAttachment(spellCombo, 10, SWT.RIGHT);
		featComboData.top = new FormAttachment(spellCombo, 0, SWT.TOP);
		featCombo.setLayoutData(featComboData);
		
		Combo languageCombo = new Combo(shell, SWT.CENTER);
		languageCombo.add("Languages");
		languageCombo.select(0);
		FormData languageComboData = new FormData(100,5);
		languageCombo.setBackground(background);
		languageComboData.left = new FormAttachment(featCombo, 10, SWT.RIGHT);
		languageComboData.top = new FormAttachment(featCombo, 0, SWT.TOP);
		languageCombo.setLayoutData(languageComboData);
		
		Combo inventoryCombo = new Combo(shell, SWT.CENTER);
		inventoryCombo.add("Inventory");
		inventoryCombo.select(0);
		FormData inventoryComboData = new FormData(99,5);
		inventoryCombo.setBackground(background);
		inventoryComboData.left = new FormAttachment(languageCombo, 10, SWT.RIGHT);
		inventoryComboData.top = new FormAttachment(languageCombo, 0, SWT.TOP);
		inventoryCombo.setLayoutData(inventoryComboData);
		
		
		
		Button spellButt = new Button(shell, SWT.CENTER | SWT.PUSH);
		spellButt.setText("Spell Wizard");
		FormData spellButtData = new FormData(127,24);
		spellButt.setBackground(background);
		spellButtData.left = new FormAttachment(spellCombo, 0, SWT.LEFT);
		spellButtData.top = new FormAttachment(spellCombo, 10, SWT.BOTTOM);
		spellButt.setLayoutData(spellButtData);
		spellButt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SpellGUI.main(args);
			}
		}); 
		
		Button featButt = new Button(shell, SWT.CENTER | SWT.PUSH);
		featButt.setText("Feat Wizard");
		FormData featButtData = new FormData(127,24);
		featButt.setBackground(background);
		featButtData.left = new FormAttachment(featCombo, 0, SWT.LEFT);
		featButtData.top = new FormAttachment(featCombo, 10, SWT.BOTTOM);
		featButt.setLayoutData(featButtData);
		featButt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO reset table
			}
		}); 
		
		Button inventoryButt = new Button(shell, SWT.CENTER | SWT.PUSH);
		inventoryButt.setText("Item Wizard");
		FormData inventoryButtData = new FormData(127,24);
		inventoryButt.setBackground(background);
		inventoryButtData.left = new FormAttachment(inventoryCombo, 0, SWT.LEFT);
		inventoryButtData.top = new FormAttachment(inventoryCombo, 10, SWT.BOTTOM);
		inventoryButt.setLayoutData(inventoryButtData);
		
		inventoryButt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO reset table
			}
		}); 
		
		return skillCombo;
	}


	private static Combo selBoxGUI(Shell shell, Label label) {
		Combo priCombo = new Combo(shell, SWT.CENTER);
		priCombo.add("Primary");
		priCombo.select(0);
		FormData priComboData = new FormData(75,5);
		priCombo.setBackground(background);
		priComboData.left = new FormAttachment(label, 0, SWT.LEFT);
		priComboData.top = new FormAttachment(label, 10, SWT.BOTTOM);
		priCombo.setLayoutData(priComboData);
		
		Combo secCombo = new Combo(shell, SWT.CENTER);
		secCombo.add("Secondary");
		secCombo.select(0);
		FormData secComboData = new FormData(75,5);
		secCombo.setBackground(background);
		secComboData.left = new FormAttachment(priCombo, 10, SWT.RIGHT);
		secComboData.top = new FormAttachment(priCombo, 0, SWT.TOP);
		secCombo.setLayoutData(secComboData);
		
		Combo armorCombo = new Combo(shell, SWT.CENTER);
		armorCombo.add("Armor");
		armorCombo.select(0);
		FormData armorComboData = new FormData(75,5);
		armorCombo.setBackground(background);
		armorComboData.left = new FormAttachment(secCombo, 10, SWT.RIGHT);
		armorComboData.top = new FormAttachment(secCombo, 0, SWT.TOP);
		armorCombo.setLayoutData(armorComboData);
		
		Combo shieldCombo = new Combo(shell, SWT.CENTER);
		shieldCombo.add("Shield");
		shieldCombo.select(0);
		FormData shieldComboData = new FormData(75,5);
		shieldCombo.setBackground(background);
		shieldComboData.left = new FormAttachment(armorCombo, 10, SWT.RIGHT);
		shieldComboData.top = new FormAttachment(armorCombo, 0, SWT.TOP);
		shieldCombo.setLayoutData(shieldComboData);
		
		Button change = new Button(shell, SWT.PUSH);
		change.setText("Change");
		change.setBackground(background);
		FormData changeData = new FormData(64,24);
		changeData.left = new FormAttachment(shieldCombo, 10, SWT.RIGHT);
		changeData.top = new FormAttachment(shieldCombo, 0, SWT.TOP);
		change.setLayoutData(changeData);

		change.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO reset table
			}
		}); 
		
		return shieldCombo;
	}


	private static Label weaponSecBoxGUI(Shell shell, Label label) {
		Label priLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		priLabel.setText(priWeapon);
		FormData priData = new FormData(100,17);
		priLabel.setBackground(background);
		priData.left = new FormAttachment(label, 0, SWT.LEFT);
		priData.top = new FormAttachment(label, 10, SWT.BOTTOM);
		priLabel.setLayoutData(priData);
		
		Label priBonusLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		priBonusLabel.setText("Bonus: " + priWeapon);
		FormData priBonusData = new FormData(70,17);
		priBonusLabel.setBackground(background);
		priBonusData.left = new FormAttachment(priLabel, 10, SWT.RIGHT);
		priBonusData.top = new FormAttachment(priLabel, 0, SWT.TOP);
		priBonusLabel.setLayoutData(priBonusData);
		
		Label priDamageLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		priDamageLabel.setText("Damage: " + priWeapon);
		FormData priDamageData = new FormData(70,17);
		priDamageLabel.setBackground(background);
		priDamageData.left = new FormAttachment(priBonusLabel, 10, SWT.RIGHT);
		priDamageData.top = new FormAttachment(priBonusLabel, 0, SWT.TOP);
		priDamageLabel.setLayoutData(priDamageData);
		
		Label priRangeLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		priRangeLabel.setText("Range: " + priWeapon);
		FormData priRangeData = new FormData(70,17);
		priRangeLabel.setBackground(background);
		priRangeData.left = new FormAttachment(priDamageLabel, 10, SWT.RIGHT);
		priRangeData.top = new FormAttachment(priDamageLabel, 0, SWT.TOP);
		priRangeLabel.setLayoutData(priRangeData);
		
		Label priCriticalLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		priCriticalLabel.setText("Ctrit: " + priWeapon);
		FormData priCriticalData = new FormData(70,17);
		priCriticalLabel.setBackground(background);
		priCriticalData.left = new FormAttachment(priRangeLabel, 10, SWT.RIGHT);
		priCriticalData.top = new FormAttachment(priRangeLabel, 0, SWT.TOP);
		priCriticalLabel.setLayoutData(priCriticalData);
		
		Label priTypeLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		priTypeLabel.setText("Type: " + priWeapon);
		FormData priTypeData = new FormData(70,17);
		priTypeLabel.setBackground(background);
		priTypeData.left = new FormAttachment(priCriticalLabel, 10, SWT.RIGHT);
		priTypeData.top = new FormAttachment(priCriticalLabel, 0, SWT.TOP);
		priTypeLabel.setLayoutData(priTypeData);
		return priLabel;
	}

	private static Label weaponBoxGUI(Shell shell, Label label) {
		Label priLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		priLabel.setText(priWeapon);
		FormData priData = new FormData(100,17);
		priLabel.setBackground(background);
		priData.left = new FormAttachment(label, 0, SWT.LEFT);
		priData.top = new FormAttachment(label, 21, SWT.BOTTOM);
		priLabel.setLayoutData(priData);
		
		Label priBonusLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		priBonusLabel.setText("Bonus: " + priWeapon);
		FormData priBonusData = new FormData(70,17);
		priBonusLabel.setBackground(background);
		priBonusData.left = new FormAttachment(priLabel, 10, SWT.RIGHT);
		priBonusData.top = new FormAttachment(priLabel, 0, SWT.TOP);
		priBonusLabel.setLayoutData(priBonusData);
		
		Label priDamageLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		priDamageLabel.setText("Damage: " + priWeapon);
		FormData priDamageData = new FormData(70,17);
		priDamageLabel.setBackground(background);
		priDamageData.left = new FormAttachment(priBonusLabel, 10, SWT.RIGHT);
		priDamageData.top = new FormAttachment(priBonusLabel, 0, SWT.TOP);
		priDamageLabel.setLayoutData(priDamageData);
		
		Label priRangeLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		priRangeLabel.setText("Range: " + priWeapon);
		FormData priRangeData = new FormData(70,17);
		priRangeLabel.setBackground(background);
		priRangeData.left = new FormAttachment(priDamageLabel, 10, SWT.RIGHT);
		priRangeData.top = new FormAttachment(priDamageLabel, 0, SWT.TOP);
		priRangeLabel.setLayoutData(priRangeData);
		
		Label priCriticalLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		priCriticalLabel.setText("Ctrit: " + priWeapon);
		FormData priCriticalData = new FormData(70,17);
		priCriticalLabel.setBackground(background);
		priCriticalData.left = new FormAttachment(priRangeLabel, 10, SWT.RIGHT);
		priCriticalData.top = new FormAttachment(priRangeLabel, 0, SWT.TOP);
		priCriticalLabel.setLayoutData(priCriticalData);
		
		Label priTypeLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		priTypeLabel.setText("Type: " + priWeapon);
		FormData priTypeData = new FormData(70,17);
		priTypeLabel.setBackground(background);
		priTypeData.left = new FormAttachment(priCriticalLabel, 10, SWT.RIGHT);
		priTypeData.top = new FormAttachment(priCriticalLabel, 0, SWT.TOP);
		priTypeLabel.setLayoutData(priTypeData);
		return priLabel;
	}
	
	private static Label saveBoxGUI(Shell shell, Label label) {
		Label acLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		acLabel.setText("AC: " + acVal);
		FormData acData = new FormData(120,17);
		acLabel.setBackground(background);
		acData.left = new FormAttachment(label, 10, SWT.RIGHT);
		acData.top = new FormAttachment(label, 100, SWT.TOP);
		acLabel.setLayoutData(acData);
		
		Label ffLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		ffLabel.setText("Flat Footed: " + ffVal);
		FormData ffData = new FormData(120,17);
		ffLabel.setBackground(background);
		ffData.left = new FormAttachment(acLabel, 10, SWT.RIGHT);
		ffData.top = new FormAttachment(acLabel, 0, SWT.TOP);
		ffLabel.setLayoutData(ffData);
		
		Label touchLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		touchLabel.setText("Touch AC: " + touchVal);
		FormData touchData = new FormData(120,17);
		touchLabel.setBackground(background);
		touchData.left = new FormAttachment(ffLabel, 10, SWT.RIGHT);
		touchData.top = new FormAttachment(ffLabel, 0, SWT.TOP);
		touchLabel.setLayoutData(touchData);
		
		Label reflexLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		reflexLabel.setText("Reflex: " + refVal);
		FormData reflexData = new FormData(120,17);
		reflexLabel.setBackground(background);
		reflexData.left = new FormAttachment(acLabel, 0, SWT.LEFT);
		reflexData.top = new FormAttachment(acLabel, 10, SWT.BOTTOM);
		reflexLabel.setLayoutData(reflexData);
		
		Label fortLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		fortLabel.setText("Fortitude: " + fortVal);
		FormData fortData = new FormData(120,17);
		fortLabel.setBackground(background);
		fortData.left = new FormAttachment(reflexLabel, 10, SWT.RIGHT);
		fortData.top = new FormAttachment(reflexLabel, 0, SWT.TOP);
		fortLabel.setLayoutData(fortData);
		
		Label willLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		willLabel.setText("Will: " + willVal);
		FormData willData = new FormData(120,17);
		willLabel.setBackground(background);
		willData.left = new FormAttachment(fortLabel, 10, SWT.RIGHT);
		willData.top = new FormAttachment(fortLabel, 0, SWT.TOP);
		willLabel.setLayoutData(willData);
		
		return reflexLabel;
	}
	
	private static Label classBoxGUI(Shell shell, Label label) {
		Label classLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		classLabel.setText("Class: " + charClass);
		FormData classData = new FormData(120,17);
		classLabel.setBackground(background);
		classData.left = new FormAttachment(label, 10, SWT.RIGHT);
		classData.top = new FormAttachment(label, 0, SWT.TOP);
		classLabel.setLayoutData(classData);
		
		Label classSecLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		classSecLabel.setText("Class: " + charSecClass);
		FormData classSecData = new FormData(120,17);
		classSecLabel.setBackground(background);
		classSecData.left = new FormAttachment(classLabel, 0, SWT.LEFT);
		classSecData.top = new FormAttachment(classLabel, 10, SWT.BOTTOM);
		classSecLabel.setLayoutData(classSecData);
		
		Label levelLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		levelLabel.setText("Level: " + charLevel + ", " + charSecLevel);
		FormData levelData = new FormData(120,17);
		levelData.left = new FormAttachment(classSecLabel, 0, SWT.LEFT);
		levelData.top = new FormAttachment(classSecLabel, 10, SWT.BOTTOM);
		levelLabel.setBackground(background);
		levelLabel.setLayoutData(levelData);
		
		Label hpLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		hpLabel.setText("HP: " + hpVal);
		FormData hpData = new FormData(120,17);
		hpLabel.setBackground(background);
		hpData.left = new FormAttachment(classLabel, 10, SWT.RIGHT);
		hpData.top = new FormAttachment(classLabel, 0, SWT.TOP);
		hpLabel.setLayoutData(hpData);
		
		Label dmgLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		dmgLabel.setText("Damage: ");
		FormData dmgData = new FormData(75,17);
		dmgLabel.setBackground(background);
		dmgData.left = new FormAttachment(hpLabel, 0, SWT.LEFT);
		dmgData.top = new FormAttachment(hpLabel, 10, SWT.BOTTOM);
		dmgLabel.setLayoutData(dmgData);
		
		Text dmgText = new Text(shell, SWT.BORDER | SWT.CENTER);
		dmgText.setText(""+ dmgVal);
		FormData dmgTextData = new FormData(27,13);
		dmgText.setBackground(background);
		dmgTextData.left = new FormAttachment(dmgLabel, 5, SWT.RIGHT);
		dmgTextData.top = new FormAttachment(dmgLabel, 0, SWT.TOP);
		dmgText.setLayoutData(dmgTextData);
		
		Label speedLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		speedLabel.setText("Speed: " + speedVal);
		FormData speedData = new FormData(120,17);
		speedLabel.setBackground(background);
		speedData.left = new FormAttachment(dmgLabel, 0, SWT.LEFT);
		speedData.top = new FormAttachment(dmgLabel, 10, SWT.BOTTOM);
		speedLabel.setLayoutData(speedData);
		return levelLabel;
	}
	
	private static Label coreStatGUI(Shell shell, Label label) {
		Label strLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		strLabel.setText("STR: " + strVal);
		FormData strData = new FormData(120,17);
		strLabel.setBackground(background);
		strData.left = new FormAttachment(label, 10, SWT.RIGHT);
		strData.top = new FormAttachment(label, 0, SWT.TOP);
		strLabel.setLayoutData(strData);

		Label dexLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		dexLabel.setText("DEX: " + dexVal);
		FormData dexData = new FormData(120,17);
		dexLabel.setBackground(background);
		dexData.left = new FormAttachment(strLabel, 0, SWT.LEFT);
		dexData.top = new FormAttachment(strLabel, 10, SWT.BOTTOM);
		dexLabel.setLayoutData(dexData);
		
		Label conLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		conLabel.setText("CON: " + conVal);
		FormData conData = new FormData(120,17);
		conLabel.setBackground(background);
		conData.left = new FormAttachment(dexLabel, 0, SWT.LEFT);
		conData.top = new FormAttachment(dexLabel, 10, SWT.BOTTOM);
		conLabel.setLayoutData(conData);
		
		Label intLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		intLabel.setText("INT: " + intVal);
		FormData intData = new FormData(120,17);
		intLabel.setBackground(background);
		intData.left = new FormAttachment(strLabel, 10, SWT.RIGHT);
		intData.top = new FormAttachment(strLabel, 0, SWT.TOP);
		intLabel.setLayoutData(intData);
		
		Label wisLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		wisLabel.setText("WIS: " + wisVal);
		FormData wisData = new FormData(120,17);
		wisLabel.setBackground(background);
		wisData.left = new FormAttachment(intLabel, 0, SWT.LEFT);
		wisData.top = new FormAttachment(intLabel, 10, SWT.BOTTOM);
		wisLabel.setLayoutData(wisData);
		
		Label chaLabel = new Label(shell, SWT.BORDER | SWT.CENTER);
		chaLabel.setText("CHA: " + chaVal);
		FormData chaData = new FormData(120,17);
		chaLabel.setBackground(background);
		chaData.left = new FormAttachment(wisLabel, 0, SWT.LEFT);
		chaData.top = new FormAttachment(wisLabel, 10, SWT.BOTTOM);
		chaLabel.setLayoutData(chaData);
		
		return intLabel;
	}
	private static void getPlayerInfo() {
		// TODO Auto-generated method stub
		charName = getCharName();
		charLevel = getCharLevel();
		charClass = getCharClass();
		charSecLevel = getCharSecLevel();
		charSecClass = getCharSecClass();
		strVal = getStr();
		dexVal = getDex();
		conVal = getCha();
		intVal = getInt();
		wisVal = getWis();
		chaVal = getCha();
		hpVal = getHP();
		speedVal = getSpeed();
		dmgVal = getDmg();
		priWeapon = getPrimaryWeapon();
		secWeapon =  getSecondaryWeapon();
		armor = getArmor();
		shield = getShield();
	}
	private static String getShield() {
		// TODO Auto-generated method stub
		return "";
	}
	private static String getArmor() {
		// TODO Auto-generated method stub
		return "";
	}
	private static String getSecondaryWeapon() {
		// TODO Auto-generated method stub
		return "";
	}
	private static String getPrimaryWeapon() {
		// TODO Auto-generated method stub
		return "";
	}
	private static int getDmg() {
		// TODO Auto-generated method stub
		return 0;
	}
	private static int getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}
	private static int getHP() {
		// TODO Auto-generated method stub
		return 0;
	}
	private static String getCharSecClass() {
		// TODO Auto-generated method stub
		return "";
	}
	private static int getCharSecLevel() {
		// TODO Auto-generated method stub
		return 0;
	}
	private static String getCharClass() {
		// TODO Auto-generated method stub
		return "Warrior";
	}
	private static int getWis() {
		// TODO Auto-generated method stub
		return 0;
	}
	private static int getInt() {
		// TODO Auto-generated method stub
		return 0;
	}
	private static int getDex() {
		// TODO Auto-generated method stub
		return 0;
	}
	private static int getStr() {
		// TODO Auto-generated method stub
		return 0;
	}
	private static int getCha() {
		// TODO Auto-generated method stub
		return 0;
	}
	private static int getCharLevel() {
		// TODO Auto-generated method stub
		return 0;
	}
	private static String getCharName() {
		return "Biggus Dickus"; // TODO
	}

}