package entity;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;

public class TrapEntity extends DNDEntity{
	
	int challengeRating;
	String trigger;
	String reset;
	String damage;
	int searchDC;
	int disableDC;
	String value;
	String save;
	String type;
	String bypass;
	String target;

	public TrapEntity(LinkedHashMap<String, String> input){
		this.TYPE = DNDEntity.type.TRAP;
		passedData = input;
		for (Map.Entry<String, String> entry : input.entrySet()){
			String field = entry.getKey();
			String value = entry.getValue();
			switch(field){
			case "NAME":
				String temp = new String(value.split("\\[")[0].trim());
				String typeTemp = value.split("\\[")[1].trim();
				typeTemp = typeTemp.substring(0, typeTemp.length() - 1).toLowerCase();
				this.type = WordUtils.capitalize(typeTemp);
				temp = WordUtils.capitalize(temp.toLowerCase());
				temp = WordUtils.capitalize(temp, '(', '[');
		    	this.name = temp;
		    	break;
			case "TRIGGER":
				this.trigger = value;
				break;
			case "RESET":
				this.reset = value;
				break;
			case "DAMAGE":
				this.damage = value;
				break;
			case "type":
				this.type = value;
				break;
			case "BYPASS":
				this.bypass = value;
				break;
			case "DESCRIPTION":
				this.description = value;
				break;
			case "TARGET":
				this.target = value;
				break;
			case "CHALLENGERATING":
				this.challengeRating = Integer.parseInt(value);
				break;
			case "SEARCHDC":
				this.searchDC = Integer.parseInt(value);
				break;
			default:
				break;
			}

		}
	}

	@Override
	public void search(String searchString, Thread runningThread) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

}
