package edu.hfu.refmo.policystoremanager;

import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;
import com.mysql.jdbc.Connection;

public class DBConnectionManagerSingleton {

	static Connection conn = null;
	
	    public static Connection getConnection() throws Exception {

	  
	       
	        	    if (SystemProperty.environment.value() ==
	        	          SystemProperty.Environment.Value.Production) {
	      
	                    Class.forName("com.mysql.jdbc.GoogleDriver").newInstance();
	                    conn = (Connection) DriverManager.getConnection(System.getProperty("cloudsql.url")+System.getProperty("cloudsql.database"), System.getProperty("cloudsql.user"), System.getProperty("cloudsql.password"));

	        	    } else {
	        	    	
	        	        Class.forName("com.mysql.jdbc.Driver").newInstance();
	        	    	 conn = (Connection) DriverManager.getConnection(System.getProperty("cloudsql.url.dev")+System.getProperty("cloudsql.database.dev"), System.getProperty("cloudsql.user.dev"), System.getProperty("cloudsql.password.dev")); 	    }
					
	        	    
	        	    return conn;

	   
	    	       

	        	

	    }
	}
