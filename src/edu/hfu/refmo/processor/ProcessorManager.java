package edu.hfu.refmo.processor;

import de.abacs.base.process.Processor;
import de.abacs.base.query.Query;
import de.abacs.base.store.RuleStore;
import de.abacs.base.strategy.DenyOverridesStrategy;
import edu.hfu.refmo.logger.RefmoLogr;
import edu.hfu.refmo.store.factory.RuleStoreManagerFactory;
import edu.hfu.refmo.store.nosql.advanced.NoSqlRuleStore;
import edu.hfu.refmo.store.nosql.simple.NoSqlRuleStoreSimple;
import edu.hfu.refmo.store.sql.model.advanced.SqlRuleStore;
import edu.hfu.rest.action.model.RefmoResponse;

public class ProcessorManager {
	
	public static RefmoResponse process(Query new_query){
		
		
		Processor processor = new Processor(RuleStoreManagerFactory.getRuleStoreManagerClass(), new DenyOverridesStrategy());
       
		//for test usage only
//	processor.printRuleSet();
		
		RefmoLogr reflog = new RefmoLogr(new_query.toString());
		reflog.start();
		
		RefmoResponse rr =  new RefmoResponse(processor.process(new_query.toString()));
		
		reflog.stop();
		
        return rr;
		
		
		
	}

	public static RefmoResponse process(boolean a, Query new_query){
		
//		RuleStore rs = null;
//		if (a){
//		  rs = new SqlRuleStore(); 
//	       
//		}
//		else {
//		 rs = new NoSqlRuleStoreSimple();
//		}
		Processor processor = new Processor(RuleStoreManagerFactory.getRuleStoreManagerClass(), new DenyOverridesStrategy());
       
		//for test usage only
		//processor.printRuleSet();
		
		RefmoLogr reflog = new RefmoLogr(new_query.toString());
		reflog.start();
		
		RefmoResponse rr =  new RefmoResponse(processor.process(new_query.toString()));
		
		reflog.stop();
		
        return rr;
		
	}

}
