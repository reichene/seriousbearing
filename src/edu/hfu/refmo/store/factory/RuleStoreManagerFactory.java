package edu.hfu.refmo.store.factory;

import de.abacs.base.store.RuleStore;
import edu.hfu.refmo.store.nosql.advanced.RuleStoreManagerGDatastore;
import edu.hfu.refmo.store.sql.model.advanced.RuleStoreManagerSqlDAOAdvanced;

public class RuleStoreManagerFactory {

	
    public static RuleStore getRuleStoreManagerClass() { 
    	RuleStore rs = new RuleStoreManagerSqlDAOAdvanced(); 
    //     RuleStore rs = new RuleStoreManagerGDatastore();
        return rs; 
    } 
}
