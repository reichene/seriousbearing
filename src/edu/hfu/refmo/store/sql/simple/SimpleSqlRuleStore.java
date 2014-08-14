package edu.hfu.refmo.store.sql.simple;

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
import edu.hfu.refmo.store.nosql.simple.SimpleNoSQLManager;
import edu.hfu.refmo.store.sql.model.advanced.JPAORManager;

public class SimpleSqlRuleStore implements RuleStore {


		private static final Logger log = Logger.getLogger(NoSqlRuleStore.class
				.getName());

		@Override
		public boolean create(AttributeTreeElement arg0, Subject arg1,
				Action arg2, Resource arg3, Decision arg4) {
			
			
			new SimpleSQLManager().create(new Rule( null, arg1, arg2,  arg3, arg4));
	
			return false;
		}

		@Override
		public boolean delete(AttributeTreeElement arg0, Subject arg1,
				Action arg2, Resource arg3) {

			new SimpleSQLManager().delete(new Rule( null, arg1, arg2,  arg3, null));
			return false;
		}

		@Override
		public List<Rule> find(AttributeTreeElement arg0, Subject arg1,
				Action arg2, Resource arg3) {
			
			return new SimpleSQLManager().find(new Rule( null, arg1, arg2,  arg3, null));
		}

		@Override
		public List<Rule> findAll() {
			
			return new SimpleSQLManager().findAll();
		}

		@Override
		public boolean update(AttributeTreeElement arg0, Subject arg1,
				Action arg2, Resource arg3, AttributeTreeElement arg4,
				Subject arg5, Action arg6, Resource arg7) {
			// TODO Auto-generated method stub
			return new SimpleSQLManager().update(new Rule( null, arg1, arg2,  arg3, null),
												   new Rule( null, arg5, arg6,  arg7, null)
					);
												   
		}

		@Override
		public Long findKey(AttributeTreeElement arg0, Subject arg1,
				Action arg2, Resource arg3) {
			
			
			
			
			RefmoLogr reflog = new RefmoLogr("time to get  rule keys");
			reflog.start();
			

			new SimpleSQLManager().findRelevantRuleIds(new Rule( null, arg1, arg2,  arg3, null));
		

			return reflog.stop();
			
		}
		
		
		
}