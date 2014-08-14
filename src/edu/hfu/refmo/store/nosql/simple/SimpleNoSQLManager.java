package edu.hfu.refmo.store.nosql.simple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.sun.org.apache.xml.internal.security.c14n.helper.AttrCompare;

import de.abacs.base.entity.Condition;
import de.abacs.base.entity.Rule;
import de.abacs.base.process.CreateProcessor;
import de.abacs.base.query.Create;
import edu.hfu.refmo.logger.RefmoLogr;
import edu.hfu.refmo.store.nosql.advanced.GDSCategory;
import edu.hfu.refmo.store.nosql.advanced.GDSSelectionOptimizer;
import edu.hfu.refmo.store.nosql.advanced.NoSqlRuleStore;
import edu.hfu.refmo.store.nosql.advanced.TermKey;
import edu.hfu.refmo.store.nosql.advanced.GDSRule.DSElements;
import edu.hfu.refmo.store.nosql.advanced.GDSRule.RulePara;
import edu.hfu.refmo.store.nosql.advanced.KeyStoreEntity;
import edu.hfu.refmo.store.sql.simple.SimpleRule;

public class SimpleNoSQLManager {

	private static final String RULESTORE = "RULESTORE";
	private static final String OBJRULE = "OBJRULE";
	private static final String RULE = "RULE";
	private static final String RULEATTCOMB = "RULEATTCOMB" ;
	private static final String ACTION = "ACTION";
	private static final String SUBJECT = "SUBJECT";
	private static final String RESOURCE = "RESOURCE";
	private static final String DELETEFLAG = "DELETEFLAG";
	
	private static final Logger log = Logger.getLogger(NoSqlRuleStore.class
			.getName());

	public void create(Rule rule) {
		
		/* store Rule */
		Key new_rule_key = persistRuleObject(rule);

		/* generate and persist attribut-combination-keys */
		persistAttributCombinKeys(generateAttribuCombinKeys(new_rule_key, rule));
		
	}

	private void persistAttributCombinKeys(List<Entity> AttribuCombinEntities) {
	
		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			datastore.put(AttribuCombinEntities);

		}

		catch (Exception e) {

			e.printStackTrace();
		}
		
	}

	private List<Entity> generateAttribuCombinKeys(Key new_rule_key, Rule rule) {
	
		List<Entity> attcombs_entity = new ArrayList();
		Set<KeyStoreEntity> attcomb = new GDSSelectionOptimizer().getAttributeCombinationCreate(null, rule.getSubject(), rule.getAction(), rule.getResource());
		
		for (KeyStoreEntity keyStoreEntity : attcomb) {
			
			attcombs_entity.add(mapAttComToEntity(new_rule_key, keyStoreEntity));

		}
		
		return attcombs_entity;
	}

	private Entity mapAttComToEntity(Key new_rule_key, KeyStoreEntity keySE) {
		
		
		Entity rule_entity = new Entity(RULEATTCOMB, new_rule_key );
		rule_entity.setProperty(ACTION, keySE.getActionHash());
		rule_entity.setProperty(SUBJECT, keySE.getSubjectHash());
		rule_entity.setProperty(RESOURCE, keySE.getResourceHash());
		rule_entity.setProperty(DELETEFLAG, 0);
		
		
		return rule_entity ;
	}

	private Key persistRuleObject(Rule rule) {
		Entity new_rule = null;
		
		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			new_rule = mapRuleObjectToEntity(rule);
			datastore.put(new_rule);
			

		}

		catch (Exception e) {

			e.printStackTrace();
		}
		
		
		return new_rule.getKey();
	}

	private Entity mapRuleObjectToEntity(Rule rule) {
		
		
		Key ruleStoreKey = KeyFactory.createKey(RULESTORE, "FUFUQ");
		Entity rule_entity = new Entity(OBJRULE, ruleStoreKey );
		rule_entity.setUnindexedProperty(RULE, this.mapRuleObjToString(rule));

		return rule_entity;
	}
	
	public Rule mapCreaRuleStrToObject(String creates){
		
		return new CreateProcessor(null).mapCreateString(creates);	
			
	}
	
	public String mapRuleObjToString(Rule obj){
		
		Create q = new Create(null,
				(obj.getSubject()!= null ? obj.getSubject().getRootElement()  : null),
				(obj.getAction() != null ? obj.getAction().getRootElement()  : null),
				(obj.getSubject()!= null ? obj.getResource().getRootElement()  : null),
				 obj.getDecision()
				);
		
		return q.toString();
	}

	public void delete(Rule rule) {

		
		
	try {
			

			
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			
				
			RefmoLogr reflog1 = new RefmoLogr("KEYSELECTIONTIME: 1.1 Datastore Operation Find NoSQL Keys");
			reflog1.start();
			
			datastore.delete(this.findeRuleKeys(rule));
			
			reflog1.stop();
			
			
		

		}

		catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	private HashMap<Key, Rule> findeRuleByRuleKeys(Set<Key> rule_keys){
		 
		HashMap<Key, Rule> ret_rulel = new HashMap();
			

			try {
				

				
				DatastoreService datastore = DatastoreServiceFactory
						.getDatastoreService();
				
				
				

					
				RefmoLogr reflog1 = new RefmoLogr("KEYSELECTIONTIME: 1.1 Datastore Operation Find NoSQL Keys");
				reflog1.start();
				
				Map<Key, Entity> entymap= datastore.get( rule_keys);
				
				reflog1.stop();
				
				
				System.out.println("RESULTS:" + entymap.values().size());
				
				for (Key key : entymap.keySet()) {
					
					ret_rulel.put(key, mapCreaRuleStrToObject((String)entymap.get(key).getProperty(RULE)));
					
					System.out.println("KEY: "+ key.toString()  + " - " + ret_rulel.get(key).toString());
				}
		

			}

			catch (Exception e) {

				e.printStackTrace();
			}
			
			
			
			
			
			
			return ret_rulel;
		
	}

	public 	HashMap<Key, Rule> find(Rule rule) {
	
		
		return this.findeRuleByRuleKeys(findeRuleKeys(rule));
	
		
	}

	public Set<Key> findeRuleKeys(Rule rule) {

		Set<Key> new_relevant_rulekeys = new HashSet<>();

		if(rule!=null){

		try {
			
		//	log.info("find rules for condition: "+ rule.toString());
			
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			
			
			Collection<Filter> subFilters = new ArrayList();
			Filter filter_attcom = getAttComFilter(rule);
	
		    //	select rule combination
			subFilters.add(filter_attcom);
			
			// select rules where delete flag isnt set
			Filter notdelete = new FilterPredicate(DELETEFLAG, FilterOperator.LESS_THAN,1);
			subFilters.add(notdelete);
			
			Filter ret_filter = new CompositeFilter(CompositeFilterOperator.AND ,subFilters);
			Query endjquery = new Query(RULEATTCOMB).setFilter(filter_attcom);
			
			RefmoLogr reflog1 = new RefmoLogr("KEYSELECTIONTIME: 1.1 Datastore Operation Find NoSQL Keys");
			reflog1.start();
			List<Entity> allresultsend = datastore.prepare(endjquery).asList(
					FetchOptions.Builder.withDefaults());
			
			reflog1.stop();
			
			
			for (Entity key : allresultsend) {
				
				
				new_relevant_rulekeys.add((Key) key.getKey().getParent());
				
			}
	

		}

		catch (Exception e) {

			e.printStackTrace();
		}
		}
		return new_relevant_rulekeys ;

		
	}

	private Filter getAttComFilter(Rule rule) {
	
		return new GDSSelectionOptimizer().getCombinationFilter(null,rule.getSubject(), rule.getAction(), rule.getResource());

	}

	public 	HashMap<Key, Rule>  findAll() {
		
		HashMap<Key, Rule> ret_rulel = new HashMap();
		try {
			

			
		DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
	
			
		Query q = new Query(OBJRULE);


		PreparedQuery pq = datastore.prepare(q);


		for (Entity result : pq.asIterable()) {

				ret_rulel.put(result.getKey(), mapCreaRuleStrToObject((String)result.getProperty(RULE)));

		}
		
		}

		catch (Exception e) {

			e.printStackTrace();
		}
		
		
		return ret_rulel;
	}

	public boolean update(Rule rule, Rule rule2) {

		
		Set<Key> rule_keys= this.findeRuleKeys(rule);
		
		deleteExistingRules(rule);
		
		
		updateNewRules(findeRuleByRuleKeys(rule_keys), rule2);
		
		return false;
	}

	private void updateNewRules(HashMap<Key, Rule> hashMap, Rule rule2) {
		
		
		updateRuleObject(hashMap, rule2);
		
		
		
	}
	


	private void updateRuleObject(HashMap<Key, Rule> rules, Rule rule2) {
		
		
	
		for (Rule irule : rules.values()) {
			
			this.create(updateSimpleRule(rule2, irule )); // neu , alt
			
		}
		

	}

	private Rule updateSimpleRule(
			Rule altr, Rule neur) {


		Condition update_rp_condition = null;
		Condition update_action = null;
		Condition update_subject = null;
		Condition update_resource = null;
		
		if(altr != null){
		if(altr.getRootElement() != null){
			if(altr.getRootElement() != null){
				if(altr.getRootElement() instanceof Condition){
					 update_rp_condition = (Condition) altr.getRootElement() ;
					 neur.setRootElement(update_rp_condition);
			
				}
			}
		}
		
		if(altr.getAction() != null){
			if(altr.getAction().getRootElement() != null){
				if(altr.getAction().getRootElement() instanceof Condition){
					update_action = (Condition) altr.getAction().getRootElement() ;
					 neur.setRootElement(update_action);
			
				}
			}
		}
		
		if(altr.getSubject() != null){
			if(altr.getSubject().getRootElement() != null){
				if(altr.getSubject().getRootElement() instanceof Condition){
					 update_subject = (Condition) altr.getSubject().getRootElement() ;
					 neur.setRootElement(update_subject);
				}
			}
		}
	
		if(altr.getResource() != null){
			if(altr.getResource().getRootElement() != null){
				if(altr.getResource().getRootElement() instanceof Condition){
					 update_resource = (Condition) altr.getResource().getRootElement() ;
					 neur.setRootElement(update_resource);
				}
			}
		}
		}
		

		
		return neur ;
	}
	private void deleteExistingRules(Rule rule) {
		
		
		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			Collection<Filter> subFilters = new ArrayList();
			Filter filter_attcom = getAttComFilter(rule);
	
		    //	select rule combination
			subFilters.add(filter_attcom);
			
			// select rules where delete flag isnt set
			Filter notdelete = new FilterPredicate(DELETEFLAG, FilterOperator.LESS_THAN,1);
			subFilters.add(notdelete);
			
			Filter ret_filter = new CompositeFilter(CompositeFilterOperator.AND ,subFilters);
			Query endjquery = new Query(RULEATTCOMB).setFilter(filter_attcom);
			
			RefmoLogr reflog1 = new RefmoLogr("KEYSELECTIONTIME: 1.1 Datastore Operation Find NoSQL Keys");
			reflog1.start();
			List<Entity> allresultsend = datastore.prepare(endjquery).asList(
					FetchOptions.Builder.withDefaults());
			
			reflog1.stop();
			
			
			for (Entity ent : allresultsend) {
				
				
				ent.setProperty(DELETEFLAG, 1);
				
			}
			
			
			
			datastore.put(allresultsend);
		}

		catch (Exception e) {

			e.printStackTrace();
		}
		
		
		
		
		
		
	}

}
