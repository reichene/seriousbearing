package edu.hfu.refmo.processor;

import de.abacs.base.process.Processor;
import de.abacs.base.query.Query;
import de.abacs.base.strategy.DenyOverridesStrategy;
import edu.hfu.refmo.store.factory.RuleStoreManagerFactory;
import edu.hfu.rest.action.model.RefmoResponse;

public class ProcessorManager {
	
	public static RefmoResponse process(Query new_query){
		
		
		Processor processor = new Processor(RuleStoreManagerFactory.getRuleStoreManagerClass(), new DenyOverridesStrategy());
       
		//for test usage only
		//processor.printRuleSet();
		
        return new RefmoResponse(processor.process(new_query.toString()));
		
		
		
	}

}
