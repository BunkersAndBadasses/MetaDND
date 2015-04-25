package guis;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import core.character;

public class LevelUpButton {
	
	private Button button;
	private character character;
	
	public LevelUpButton(Composite page, character c) {
		character = c;
		button = new Button(page, SWT.PUSH);
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				levelUpLogic();
			}
		});
	}
	
	private void levelUpLogic() {
		// check exp
		int level = character.getLevel();
		int exp = character.getExp();
		
		// if exp is not enough, do not level up
		if (exp < getReqExp(level+1))
			return;
		
		// otherwise, perform the level up
		
		character.setLevel(level+1);
		
		// TODO do all the other shit too
	}
	
	/**
	 * returns value of exp necessary to be at specified level
	 * @param level
	 * @return
	 */
	private int getReqExp(int level) {
		if (level < 2)
			return 0;
		else if (level == 2) 
			return 1000;
		else
			return (level-1)*1000 + getReqExp(level-1);
	}
	
	public Button getButton() { return button; }
	
}
