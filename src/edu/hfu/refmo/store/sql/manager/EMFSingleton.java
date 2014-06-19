package edu.hfu.refmo.store.sql.manager;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.appengine.api.utils.SystemProperty;

public class EMFSingleton {


	    private static EntityManagerFactory instance = null;
	 
	    private EMFSingleton() {}
	 
	    
	    public static EntityManagerFactory getEMF() {
	        if (instance == null) {
	        	
	     
	       
	        	 Map<String, String> properties = new HashMap<String, String>();
	        	    if (SystemProperty.environment.value() ==
	        	          SystemProperty.Environment.Value.Production) {
	        	properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.GoogleDriver");
	        	properties.put("javax.persistence.jdbc.user", System.getProperty("cloudsql.user"));
	        //	properties.put("javax.persistence.jdbc.password", System.getProperty("cloudsql.password"));
	        	properties.put("javax.persistence.jdbc.url", System.getProperty("cloudsql.url")+System.getProperty("cloudsql.database"));
	        	    } else {
	        	properties.put("javax.persistence.jdbc.driver","com.mysql.jdbc.Driver");
	        	properties.put("javax.persistence.jdbc.user", System.getProperty("cloudsql.user.dev"));
	        	properties.put("javax.persistence.jdbc.password", System.getProperty("cloudsql.password.dev"));
	        	properties.put("javax.persistence.jdbc.url", System.getProperty("cloudsql.url.dev")+System.getProperty("cloudsql.database.dev"));
	        	    }

	        	EntityManagerFactory emf = Persistence.createEntityManagerFactory(
	        	        "JPAReferenceMonitorAdva", properties);

	        	    // Insert a few rows.
	        	    instance = emf;

	        	
	        }
	        return instance;
	    }
	}
