package entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class AbilityEntity extends DNDEntity{

	public AbilityEntity(LinkedHashMap<String, String> input)
	{
		for (Map.Entry<String, String> entry : input.entrySet())
		{
		    String field = entry.getKey();
		    String value = entry.getValue();
		    switch(field)
		    {
		    case "NAME":
		    	this.name = value;
		    	break;
		    case "DESCRIPTION":
		    	this.description = value;
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
