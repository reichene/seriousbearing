package edu.hfu.refmo.store.sql.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.mortbay.log.Log;

import com.google.appengine.api.utils.SystemProperty;

import edu.hfu.refmo.store.sql.model.advanced.JPAORManager;

public class EMFSingleton {
	
	private static final Logger log = Logger.getLogger(EMFSingleton.class
			.getName());

	    private static EntityManagerFactory instance = null;
	 
	    private EMFSingleton() {}
	 
	    
	    public static EntityManagerFactory getEMF() {
	        if (instance == null) {
	        	
	     
	        	EntityManagerFactory emf = null;
	        	 Map<String, String> properties = new HashMap<String, String>();
	        	    if (SystemProperty.environment.value() ==
	        	          SystemProperty.Environment.Value.Production) {
//	        	properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.GoogleDriver");
//	        //	properties.put("javax.persistence.jdbc.user", System.getProperty("cloudsql.user"));
//	        //	properties.put("javax.persistence.jdbc.password", System.getProperty("cloudsql.password"));
//	        	properties.put("javax.persistence.jdbc.url", System.getProperty("cloudsql.url")+System.getProperty("cloudsql.database")+"?user="+ System.getProperty("cloudsql.user"));
//	        	
//	        	emf = Persistence.createEntityManagerFactory(
//	        	        "JPAReferenceMonitorAdva", properties);
	    	        	
	        	
	        	emf = Persistence.createEntityManagerFactory("JPAReferenceMonitorAdvaProduct");
	        	    } else {
//	        	properties.put("javax.persistence.jdbc.driver","com.mysql.jdbc.Driver");
//	        	properties.put("javax.persistence.jdbc.user", System.getProperty("cloudsql.user.dev"));
//	        	properties.put("javax.persistence.jdbc.password", System.getProperty("cloudsql.password.dev"));
//	        	properties.put("javax.persistence.jdbc.url", System.getProperty("cloudsql.url.dev")+System.getProperty("cloudsql.database.dev"));
	        	  
	        	emf = Persistence.createEntityManagerFactory("JPAReferenceMonitorAdva");
	        	    
	        	    }

	        

	        	
	        	    instance = emf;
	        	    log.info("new entity manager instance");
	        	
	        }
	        return instance;
	    }
	}
