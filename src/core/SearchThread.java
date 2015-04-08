package core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import entity.DNDEntity;

public class SearchThread implements Runnable{
	private Thread SearchThread;
	private String threadName;
	private LinkedHashMap<String, DNDEntity> input;
	
	
	SearchThread(String name){
		threadName = name;
		System.out.println("Creating " +  threadName );
	}
	
	public void start(LinkedHashMap<String, DNDEntity> input) {
			System.out.println("Starting " + threadName );
			if (SearchThread == null)
			{
				this.input = input;
				SearchThread = new Thread (this, threadName);
				SearchThread.start();
			}
		}

	@Override
	public void run() {
		for (Entry<String, DNDEntity> entry : input.entrySet()){
			entry.getValue().search();
			
		}
		System.out.println("Ending " + threadName );
	}

}
