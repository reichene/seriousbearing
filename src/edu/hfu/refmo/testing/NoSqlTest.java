package edu.hfu.refmo.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.api.ThreadManager;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import de.abacs.base.entity.Action;
import de.abacs.base.entity.AttributeTreeElement;
import de.abacs.base.entity.Condition;
import de.abacs.base.entity.Condition.Comparision;
import de.abacs.base.entity.Decision;
import de.abacs.base.entity.Resource;
import de.abacs.base.entity.Rule;
import de.abacs.base.entity.Subject;
import edu.hfu.refmo.logger.RefmoLogr;
import edu.hfu.refmo.store.nosql.advanced.GDSRule;
import edu.hfu.refmo.store.nosql.advanced.NoSqlRuleStore;
import edu.hfu.refmo.store.nosql.advanced.DatastoreManager;

public class NoSqlTest {
	 
	private final LocalServiceTestHelper helper =
		        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		 
	}

	@Before
	public void setUp() throws Exception {
		
		 helper.setUp();
			
		
	}

	@After
	public void tearDown() throws Exception {
		
		helper.tearDown();
	}

	@Test public void updateRule()
	{
	
		
		
		DatastoreManager rsmns = new DatastoreManager();
		NoSqlRuleStore rsgmgd = new NoSqlRuleStore();		
		
		AttributeTreeElement ruleRootElement = null;
		Subject subject = new Subject(new Condition("creattr", Comparision.EQUAL, "crevalue"));
		Action action = new Action(new Condition("creattr", Comparision.EQUAL, "crevalue"));
		Resource resource = new Resource(new Condition("creattr", Comparision.EQUAL, "crevalue"));
		Decision decision = Decision.PERMIT;
		Rule new_rule = new Rule(ruleRootElement, subject, action, resource, decision);
		 

		
		// create new rule object
		rsmns.create(ruleRootElement, subject, action, resource, decision);
		
		// find and map object 
		GDSRule new_gdsrule= rsmns.find(ruleRootElement, subject, action, resource).get(0);
		Rule new_persisted_rule = rsgmgd.parseGDSRuleToRule(new_gdsrule);
		
		// update persisted rule
		// ----------------------------------------------
		List<GDSRule> gdsrules_list_select = new ArrayList();
		gdsrules_list_select.add(new_gdsrule);
		
		// update subject value
		Subject subject_update = new Subject(new Condition("update_attr", Comparision.EQUAL, "update_value"));
		Rule new_update_rule = new Rule(ruleRootElement, subject_update, action, resource, decision);	
		
		List<GDSRule> gdsrules_list_result = rsmns.update(null, subject_update, null, null, gdsrules_list_select);
		
		
		// find and map updated object 
		GDSRule new_gdsrule_updated = rsmns.find(ruleRootElement, subject_update, action, resource).get(0);
		Rule new_persisted_rule_updated  = rsgmgd.parseGDSRuleToRule(new_gdsrule_updated);
		
		// testing
		assertEquals("Select single rule by updated properties", new_update_rule, new_persisted_rule_updated);
		
		// testing
		// select rules with old conditions
		assertEquals("Select rules by old entity properties",  rsmns.find(ruleRootElement, subject, action, resource).size(), 0);

	}
	@Test public void createRule()
	{
		AttributeTreeElement ruleRootElement = null;
		Subject subject = new Subject(new Condition("creattr", Comparision.EQUAL, "crevalue"));
		Action action = new Action(new Condition("creattr", Comparision.EQUAL, "crevalue"));
		Resource resource = new Resource(new Condition("creattr", Comparision.EQUAL, "crevalue"));
		Decision decision = Decision.PERMIT;
		Rule new_rule = new Rule(ruleRootElement, subject, action, resource, decision);
		 
		DatastoreManager rsmns = new DatastoreManager();
		NoSqlRuleStore rsgmgd = new NoSqlRuleStore();
		
		// create new rule object
		rsmns.create(ruleRootElement, subject, action, resource, decision);
		
		
		// find and map object 
		GDSRule new_gdsrule= rsmns.find(ruleRootElement, subject, action, resource).get(0);
		Rule new_persisted_rule = rsgmgd.parseGDSRuleToRule(new_gdsrule);
		
		// testing
		assertEquals("Create new rule test result", new_rule, new_persisted_rule);
		
	
		
		
	}
	@Test public void deleteRule()
	{
		

		AttributeTreeElement ruleRootElement = null;
		Subject subject = new Subject(new Condition("creattr", Comparision.EQUAL, "crevalue"));
		Action action = new Action(new Condition("creattr", Comparision.EQUAL, "crevalue"));
		Resource resource = new Resource(new Condition("creattr", Comparision.EQUAL, "crevalue"));
		Decision decision = Decision.PERMIT;
		Rule new_rule = new Rule(ruleRootElement, subject, action, resource, decision);
		 
		DatastoreManager rsmns = new DatastoreManager();
		NoSqlRuleStore rsgmgd = new NoSqlRuleStore();
		
		// create new rule object
		rsmns.create(ruleRootElement, subject, action, resource, decision);
		
		// create second rule
		rsmns.create(null,
		 new Subject(new Condition("creattr2", Comparision.EQUAL, "crevalue2")),
		 new Action(new Condition("creattr2", Comparision.EQUAL, "crevalue2")),
		 new Resource(new Condition("creattr2", Comparision.EQUAL, "crevalue2")),
	     Decision.DENY
				);
		
		
		// find all rules 
		int cnt_before_delete = rsmns.findAll().size();
		
		// delete single rule
		rsmns.deleteByKey(rsmns.findRuleKeys(ruleRootElement, subject, action, resource));
		
		// find all rules 
		int cnt_after_delete = rsmns.findAll().size();
		
		// testing
		assertNotEquals(cnt_after_delete, cnt_before_delete);
		
	}
	@Test public void findRule()
	{
		
		AttributeTreeElement ruleRootElement = null;
		Subject subject = new Subject(new Condition("creattr", Comparision.EQUAL, "crevalue"));
		Action action = new Action(new Condition("creattr", Comparision.EQUAL, "crevalue"));
		Resource resource = new Resource(new Condition("creattr", Comparision.EQUAL, "crevalue"));
		Decision decision = Decision.PERMIT;
		Rule new_rule = new Rule(ruleRootElement, subject, action, resource, decision);
		 
		DatastoreManager rsmns = new DatastoreManager();
		NoSqlRuleStore rsgmgd = new NoSqlRuleStore();
		
		// create new rule object
		rsmns.create(ruleRootElement, subject, action, resource, decision);
		
		
		// find and map object 
		GDSRule new_gdsrule= rsmns.find(ruleRootElement, subject, action, resource).get(0);
		Rule new_persisted_rule = rsgmgd.parseGDSRuleToRule(new_gdsrule);
		
		// testing
		assertEquals("Find rule test result", new_rule, new_persisted_rule);
		
		
	}
}
