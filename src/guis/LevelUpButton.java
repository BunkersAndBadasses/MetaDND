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
	
	public LevelUpButton(Composite page, character character) {
		this.character = character;
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
		
		character.incLevel();
		
		// TODO do all the other shit too
		
		// general:
		// bonus feat every third level (level%3 = 0)
		// max class skill ranks : level + 3
		// inc ability score every 4th level (level%4 = 0)
		// skill points # + int mod
		// hitpoints (roll hit die + con mod), add at least 1
		// class abilities?
		
		
		
		
		
		
	}
	
	/**
	 * returns value of exp necessary to be at specified level
	 * @param level
	 * @return
	 */
	private int getReqExp(int level) {
		if (level < 2)
			return 0;
		else
			return (level-1)*1000 + getReqExp(level-1);
	}
	
	public Button getButton() { return button; }
	
}
