package edu.hfu.refmo.store.nosql.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import de.abacs.base.entity.Action;
import de.abacs.base.entity.AttributeTreeElement;
import de.abacs.base.entity.Decision;
import de.abacs.base.entity.Resource;
import de.abacs.base.entity.Rule;
import de.abacs.base.entity.Subject;
import de.abacs.base.store.RuleStore;
import edu.hfu.refmo.logger.RefmoLogr;
import edu.hfu.refmo.store.nosql.advanced.NoSqlRuleStore;
import edu.hfu.refmo.store.sql.simple.SimpleSQLManager;

public class NoSqlRuleStoreSimple implements RuleStore {


		private static final Logger log = Logger.getLogger(NoSqlRuleStore.class
				.getName());

		@Override
		public boolean create(AttributeTreeElement arg0, Subject arg1,
				Action arg2, Resource arg3, Decision arg4) {
			
			
			new SimpleNoSQLManager().create(new Rule( null, arg1, arg2,  arg3, arg4));
	
			return false;
		}

		@Override
		public boolean delete(AttributeTreeElement arg0, Subject arg1,
				Action arg2, Resource arg3) {

			new SimpleNoSQLManager().delete(new Rule( null, arg1, arg2,  arg3, null));
			return false;
		}

		@Override
		public List<Rule> find(AttributeTreeElement arg0, Subject arg1,
				Action arg2, Resource arg3) {
			
			return new ArrayList<Rule>(new SimpleNoSQLManager().find(new Rule( null, arg1, arg2,  arg3, null)).values());
		}

		@Override
		public List<Rule> findAll() {
			
			return new ArrayList<Rule>(new SimpleNoSQLManager().findAll().values());
		}

		@Override
		public boolean update(AttributeTreeElement arg0, Subject arg1,
				Action arg2, Resource arg3, AttributeTreeElement arg4,
				Subject arg5, Action arg6, Resource arg7) {
			// TODO Auto-generated method stub
			return new SimpleNoSQLManager().update(new Rule( null, arg1, arg2,  arg3, null),
												   new Rule( null, arg5, arg6,  arg7, null)
					);
												   
		}

		@Override
		public Long findKey(AttributeTreeElement arg0, Subject arg1,
				Action arg2, Resource arg3) {
			// TODO Auto-generated method stub
			
			RefmoLogr reflog = new RefmoLogr("time to get  rule keys");
			reflog.start();
			

			new SimpleNoSQLManager().findeRuleKeys(new Rule( null, arg1, arg2,  arg3, null));
		

			return reflog.stop();
		}
		
		
		
}