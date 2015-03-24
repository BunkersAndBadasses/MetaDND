package core;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class test {

  static int selection = -1;
  static int pageNum = -1;

  public static void main(String[] args) {
    Display display = new Display();
    Shell shell = new Shell(display);
    shell.setBounds(10, 10, 300, 200);
    final ArrayList<Composite> wizPages = new ArrayList<Composite>(); // holds all pages for character wizard
    final ArrayList<Composite> manualPages = new ArrayList<Composite>(); // holds all pages for manual char entering
    final ArrayList<Composite> randomPages = new ArrayList<Composite>(); // holds all pages for random char generation
    final ArrayList<ArrayList<Composite>> selections = new ArrayList<ArrayList<Composite>>(3);
    selections.add(wizPages);
    selections.add(manualPages);
    selections.add(randomPages);
    // create the composite that the pages will share
    final Composite home = new Composite(shell, SWT.BORDER);
    home.setBounds(0, 0, 300, 200);
    final StackLayout homeLayout = new StackLayout();
    home.setLayout(homeLayout);

    // create home page content
    final Composite wizard = new Composite(home, SWT.BORDER);
    wizard.setBounds(0,0,200,100);
    final StackLayout wizLayout = new StackLayout();
    wizard.setLayout(wizLayout);

    Label welcome = new Label(home, SWT.NONE);
    welcome.setText("New Character!");
    welcome.pack();
    // create the button that will switch to next page
    Button interactive = new Button(home, SWT.PUSH);
    interactive.setText("Interactive");
    interactive.setBounds(113, 45, 80, 25);
    interactive.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event event) {
        selection = 0;
        homeLayout.topControl = wizard;
        home.layout();
      }
    });
    		
    

    
    
    
    // create the first page's content
    final Composite page0 = new Composite(wizard, SWT.NONE);
    wizPages.add(page0);
    page0.setLayout(new RowLayout());
    Label label = new Label(page0, SWT.NONE);
    label.setText("Label on page 1");
    label.pack();

    // create the second page's content
    final Composite page1 = new Composite(wizard, SWT.NONE);
    wizPages.add(page1);
    page1.setLayout(new RowLayout());
    Button button = new Button(page1, SWT.NONE);
    button.setText("Button on page 2");
    button.pack();

    // create the button that will switch to next page
    Button nextButton = new Button(wizard, SWT.PUSH);
    nextButton.setText("Next");
    nextButton.setBounds(213, 145, 80, 25);
    nextButton.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event event) {
        pageNum = ++pageNum % 2;
        homeLayout.topControl = wizPages.get(pageNum);
        home.layout();
      }
    });
    // create the button that will switch to previous page
    Button backButton = new Button(wizard, SWT.PUSH);
    backButton.setText("Back");
    backButton.setBounds(130, 145, 80, 25);
    backButton.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event event) {
        pageNum = (--pageNum) % 2;
        if (pageNum < 0) pageNum = 1;
        homeLayout.topControl = wizPages.get(pageNum);
        home.layout();
      }
    });

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }
}
