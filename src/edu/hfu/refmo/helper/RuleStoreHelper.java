package edu.hfu.refmo.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.mortbay.log.Log;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheService.SetPolicy;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import de.abacs.base.store.RuleStore;
import edu.hfu.refmo.store.nosql.advanced.NoSqlRuleStore;
import edu.hfu.refmo.store.nosql.simple.NoSqlRuleStoreSimple;
import edu.hfu.refmo.store.sql.model.advanced.SqlRuleStore;
import edu.hfu.refmo.store.sql.simple.SimpleSqlRuleStore;

public class RuleStoreHelper {

	private static final String RULESTORE = "RULESTORE";
	
	private static final Logger log = Logger.getLogger(NoSqlRuleStore.class
			.getName());


	public static void setCurrentRuleStore(Integer ite) {


		
		try {
			
			
			String key = RULESTORE;
		    Integer value = ite;

				    // Using the synchronous cache
				    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
				    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
				//    value = (byte[]) syncCache.get(key); // read from cache
				  //  if (value == null) {
				      // get value from other source
				      // ........
				    	
				    
				    
				 //     Expiration ex = Expiration().byDeltaSeconds(Integer.MAX_VALUE);
					boolean succ = syncCache.put(key, value,null,SetPolicy.SET_ALWAYS); // populate cache
				 //   }
			
			if(!succ){
				

				DatastoreService datastore = DatastoreServiceFactory
						.getDatastoreService();
				
				Entity datastore_ent = new Entity("RULESTORE", "RULESIDTEMP");
				datastore_ent.setProperty("RULESTORE_ID", ite);
				
				datastore.put((datastore_ent));
				
			}
			
		}

		catch (Exception e) {

			e.printStackTrace();
		}
		
		
	}

	public static RuleStore getCurrentRuleStore() {
	
		
		Integer rulestore_id = 0;
		RuleStore rs = null;
try {
			

	
			 
	
				//memcache
				// Using the synchronous cache
			    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
				syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
				rulestore_id = (Integer) syncCache.get(RULESTORE); // read from cache
	
				if( rulestore_id ==  null){
			 
				// datastore
				DatastoreService datastore = DatastoreServiceFactory
						.getDatastoreService();
				
				Key k = KeyFactory.createKey("RULESTORE", "RULESIDTEMP");
				Entity curr_rulestore = datastore.get(k);
				
				 rulestore_id = (int) (long) curr_rulestore.getProperty("RULESTORE_ID");
				 
				 RuleStoreHelper.setCurrentRuleStore(rulestore_id);
				}
				 
			 
			
			switch ( rulestore_id) {
			case 1:
				rs =  new NoSqlRuleStore();
				break;
			case 2:
				rs = new SimpleSqlRuleStore();
				break;
			case 3:
				rs = new SqlRuleStore();
				break;
			case 4:
				rs = new NoSqlRuleStoreSimple();
				break;

			default:
				rs = new NoSqlRuleStore();
				break;
			}
			System.out.println("current RuleStore: " + rs.getClass().getName() );

		}

		catch (Exception e) {

			e.printStackTrace();
		}

		
		
		return  rs;
	
		
	}

}
