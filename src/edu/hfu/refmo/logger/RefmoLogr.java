package edu.hfu.refmo.logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import edu.hfu.refmo.store.nosql.advanced.GDSTerm_Condition;
import edu.hfu.rest.action.jersey.RequestController;

public class RefmoLogr {
	
	private static final Logger log = Logger.getAnonymousLogger();
	
	private final boolean logInDB = false;
	private final boolean logActive = true;
	private final String entityName = "DurationLog";
	private String description;
	
	private long startTime;
	private long stopTime;
	
	
	public RefmoLogr(String log_description){
		this.description = log_description;
		
	}

	public void start() {
	if(logActive){
			
		this.startTime = System.nanoTime();
			
		}
	}
	
	public Long stop() {
		
		Long timeDuration = 0L;
		
	if(logActive){
		
		this.stopTime = System.nanoTime();
		
		timeDuration = this.stopTime - this.startTime;
		this.logInConsole(TimeUnit.MILLISECONDS.convert(timeDuration, TimeUnit.NANOSECONDS));
		
		if(this.logInDB){
			
		this.saveInDB(TimeUnit.MILLISECONDS.convert(timeDuration, TimeUnit.NANOSECONDS));
		}
			
		}
	return timeDuration;
	}
	
	private void saveInDB(Long timeDuration){
		
		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			
		Entity timelog = new Entity(this.entityName);
		timelog.setProperty("StartInNS", this.startTime);
		timelog.setProperty("EndInNS", this.stopTime);
		timelog.setProperty("DurationInMS", timeDuration);
		timelog.setProperty("Description", this.description);

		datastore.put(timelog);


		}

		catch (Exception e) {

			e.printStackTrace();
		}
		
	}
	
	private void logInConsole(Long timeDuration){
		
		
		log.info("S("+this.startTime+"ns)"+ " | "+"E("+this.stopTime+"ns) | D(" + timeDuration+ "ms) | Description: "+ this.description );		
	}
}
