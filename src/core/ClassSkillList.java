package core;

public class ClassSkillList {

	private static final String[] charClasses = {"Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Wizard"};
	private static final String[] barbarian = {"Climb", "Craft", "Handle Animal", "Intimidate", "Jump", "Listen", "Ride", "Survival", "Swim"};
	private static final String[] bard = {"Appraise", "Balance", "Bluff", "Climb", "Concentration", "Craft", "Decipher Script", "Diplomacy", "Disguise", "Escape Artist", "Gather Information", "Hide", "Jump", "Knowledge(arcana)", "Knowledge(architecture and engineering)", "Knowledge(dungeoneering)", "Knowledge(geography)", "Knowledge(history)", "Knowledge(local)", "Knowledge(nature)", "Knowledge(nobility and royalty)", "Knowledge(religion)", "Knowledge(the planes)", "Listen", "Move Silently", "Perform", "Profession", "Sense Motive", "Slight of Hand", "Speak Language", "Spellcraft", "Swim", "Tumble", "Use Magic Device"};
	private static final String[] cleric = {"Concentration", "Craft", "Diplomacy", "Heal", "Knowledge(arcana)", "Knowledge(history)", "Knowledge(religion)", "Knowledge(the planes)", "Profession", "Spellcraft"};
	private static final String[] druid = {"Concentration", "Craft", "Diplomacy", "Handle Animal", "Heal", "Knowledge(nature)", "Listen", "Profession", "Ride", "Spellcraft", "Spot", "Survival", "Swim"};
	private static final String[] fighter = {"Climb", "Craft", "Handle Animal", "Intimidate", "Jump", "Ride", "Swim"};
	private static final String[] monk = {"Balance", "Climb", "Concentration", "Craft", "Diplomacy", "Escape Artist", "Hide", "Jump", "Knowledge(arcana)", "Knowledge(religion)", "Listen", "Move Silently", "Perform", "Profession", "Sense Motive", "Spot", "Swim", "Tumble"};
	private static final String[] paladin = {"Concentration", "Craft", "Diplomacy", "Handle Animal", "Heal", "Knowledge(nobility and royalty)", "Knowledge(religion)", "Profession", "Ride", "Sense Motive"};
	private static final String[] ranger = {"Climb", "Concentration", "Craft", "Handle Animal", "Heal", "Hide", "Jump", "Knowledge(dungeoneering)", "Knowledge(geography)", "Knowledge(nature)", "Listen", "Move Silently", "Profession", "Ride", "Search", "Spot", "Survival", "Swim", "Use Rope"};
	private static final String[] rogue = {"Appraise", "Balance", "Bluff", "Climb", "Craft", "Decipher Script", "Diplomacy", "Disable Device", "Disguise", "Escape Artist", "Forgery" ,"Gather Information", "Hide", "Intimidate", "Jump", "Knowledge(local)", "Listen", "Move Silently", "Open Lock", "Perform", "Profession", "Search", "Sense Motive", "Sleight of Hand", "Spot", "Swim", "Tumble", "Use Magic Device", "Use Rope"};
	private static final String[] sorcerer = {"Bluff", "Concentration", "Craft", "Knowledge(arcana)", "Profession", "Spellcraft"};
	private static final String[] wizard = {"Concentration", "Craft", "Decipher Script", "Knowledge(arcana)", "Knowledge(architecture and engineering)", "Knowledge(dungeoneering)", "Knowledge(geography)", "Knowledge(history)", "Knowledge(local)", "Knowledge(nature)", "Knowledge(nobility and royalty)", "Knowledge(religion)", "Knowledge(the planes)", "Profession", "Spellcraft"};
	private static final String [][] classSkills = {barbarian, bard, cleric, druid, fighter, monk, paladin, ranger, rogue, sorcerer, wizard};
	
	public static String[] getClassSkillList(String charClass) {
		for (int i = 0; i < charClasses.length; i++) {
			if (charClass.equalsIgnoreCase(charClasses[i]))
				return classSkills[i];
		}
		return null;
	}
	
	public static boolean isClassSkill(String charClass, String skillName) {
		String[] csList = getClassSkillList(charClass);
		if (csList == null)
			return false;
		for (int i = 0; i < csList.length; i++) {
			if (skillName.equalsIgnoreCase(csList[i]))
				return true;
		}
		return false;
	}
	
	
	
	
	
}
