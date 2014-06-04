package edu.hfu.refmo.store.factory;

import de.abacs.base.store.RuleStore;
import edu.hfu.refmo.store.sql.manager.RuleStoreManagerSqlDAO;

public class RuleStoreManagerFactory {

	
    public static RuleStore getRuleStoreManagerClass() { 
         RuleStore rs = new RuleStoreManagerSqlDAO(); 
        return rs; 
    } 
}
