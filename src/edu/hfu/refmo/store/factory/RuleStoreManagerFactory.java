package edu.hfu.refmo.store.factory;

import de.abacs.base.store.RuleStore;
import edu.hfu.refmo.helper.RuleStoreHelper;
import edu.hfu.refmo.store.nosql.advanced.NoSqlRuleStore;
import edu.hfu.refmo.store.nosql.simple.NoSqlRuleStoreSimple;
import edu.hfu.refmo.store.sql.model.advanced.SqlRuleStore;

public class RuleStoreManagerFactory {

	
    public static RuleStore getRuleStoreManagerClass() { 
  //    RuleStore rs = new SqlRuleStore(); 
  //   RuleStore rs = new NoSqlRuleStore();
   // RuleStore rs = new NoSqlRuleStoreSimple();
        return 	RuleStoreHelper.getCurrentRuleStore();
    } 
}
