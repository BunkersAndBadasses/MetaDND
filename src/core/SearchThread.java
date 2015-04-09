package core;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import entity.DNDEntity;

public class SearchThread implements Runnable{
	private Thread SearchThread;
	private String threadName;
	private LinkedHashMap<String, DNDEntity> input;
	private String searchString;
	
	
	SearchThread(String name){
		threadName = name;
		System.out.println("Creating " +  threadName );
	}
	
	public void start(LinkedHashMap<String, DNDEntity> input, String searchString) {
			System.out.println("Starting " + threadName );
			if (SearchThread == null)
			{
				this.input = input;
				this.searchString = searchString.toLowerCase();
				SearchThread = new Thread (this, threadName);
				SearchThread.start();
			}
		}

	@Override
	public void run() {
		for (Entry<String, DNDEntity> entry : input.entrySet()){
			entry.getValue().search(this.searchString, this.SearchThread);
			
		}
		System.out.println("Ending " + threadName );
	}

}
