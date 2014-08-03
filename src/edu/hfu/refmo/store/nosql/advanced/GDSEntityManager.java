package edu.hfu.refmo.store.nosql.advanced;

import java.util.ArrayList;
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
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import edu.hfu.refmo.logger.RefmoLogr;
import edu.hfu.refmo.store.nosql.advanced.GDSRule.DSElements;
import edu.hfu.refmo.store.nosql.advanced.GDSRule.RulePara;
import edu.hfu.refmo.store.nosql.advanced.GDSTerm.TermPara;

public class GDSEntityManager {

	private static final Logger log = Logger.getLogger(GDSEntityManager.class
			.getName());

	public List<GDSRule> findAll() {

		log.info("start query: find-all");
		List<GDSRule> r_rule_list = new ArrayList<GDSRule>();

		try {
			
			RefmoLogr reflog = new RefmoLogr("Persistence Operation Find All NoSQL");
			reflog.start();
			
			
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			Query q = new Query(DSElements.RULE.name()).setKeysOnly();

			for (Entity result : datastore.prepare(q).asList(
					FetchOptions.Builder.withDefaults())) {

			//	log.info("found rule with key: " + result.getKey());
				r_rule_list.add(loadSingleRuleByAncestorKey(result.getKey()));

				// TEST
				// printCurrentEntity( result);

				
				reflog.stop();
			}

		}

		catch (Exception e) {

			e.printStackTrace();
		}

		return r_rule_list;
	}

	private GDSRule loadSingleRuleByAncestorKey(Key key) {

		GDSRule new_gds_rule = null;

		try {
			RefmoLogr reflog = new RefmoLogr("3. Datastore Operation FindByKey NoSQL");
			reflog.start();
			
			
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
		//	log.info("start query: find-rule-by-ancestor-key with key: "
		//			+ key.getKind() + " " + key.getId());
			Query q = new Query().setAncestor(key);
			List<Entity> results = datastore.prepare(q).asList(
					FetchOptions.Builder.withDefaults());
			
			new_gds_rule = parseSingleAncestorPathToRule(results);
			
			reflog.stop();
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		return new_gds_rule;
		
	}

	private GDSRule parseSingleAncestorPathToRule(List<Entity> results) {

		GDSRule gdsrule = new GDSRule();

		Map<Key, GDSTerm> term_map = new HashMap<Key, GDSTerm>();

		for (Entity entity : results) {

//			// test
//			this.printCurrentEntity(entity);

			if (entity.getKind().equals(DSElements.RULE.name())) {

						
				gdsrule = setGDSRuleProperties(gdsrule, entity);

			}

			else if (entity.getKind().equals(DSElements.TERM.name())) {

				GDSTerm new_term = null;
				
				/***
				 *  use given object reference
				 */
				if(term_map.containsKey(entity.getKey())){
					
					new_term = term_map.get(entity.getKey());
				}
				
				

				if (((String) entity.getProperty(TermPara.DISCRIMINATOR.name())).equals(DSElements.CONDITION.name())) {

										
					new_term = setGDSTermCondProps((GDSTerm_Condition)new_term, entity);

				}

				else if (((String) entity.getProperty(TermPara.DISCRIMINATOR.name())).equals(DSElements.CONJUNCTION
						.name())) {

					new_term = setGDSTermConjProps((GDSTerm_Conjunction)new_term, entity);
				}

				/***
					 * 
					 */

				
				
				if(gdsrule != null && new_term != null){
					
					new_term.setParent_rule(gdsrule);
				}
				
						
				term_map.put(entity.getKey(), new_term);
				
				
				
				
				

				if (entity.getParent() != null) {
					if (entity.getParent().getKind().equals(DSElements.TERM.name())) {

						GDSTerm_Conjunction gdstc = null;
						// Parent Term = Conjunction
						if (term_map.containsKey(entity.getParent())) {

							gdstc = (GDSTerm_Conjunction) term_map.get(entity
									.getParent());
						} else {
							gdstc = new GDSTerm_Conjunction(entity.getParent());
							term_map.put(entity.getParent(), gdstc);
						}

						new_term.setParent_term(gdstc);

						// ---------------------------------------------

						if (new_term instanceof GDSTerm_Condition) {

							gdstc.addGDSTermCondition((GDSTerm_Condition) new_term);

						}

						else if (new_term instanceof GDSTerm_Conjunction) {

							gdstc.addGDSTermConjunction((GDSTerm_Conjunction) new_term);

						}

					} else if (entity.getParent().getKind().equals(DSElements.RULE.name())) {

						if (gdsrule != null) {

							new_term.setParent_rule(gdsrule);

							if (entity.hasProperty(TermPara.CATEGORY.name())) {

								String category = (String) entity
										.getProperty(TermPara.CATEGORY.name());
								

								if (category.equals(GDSCategory.ACTION.name())) {
									
									gdsrule.setCategory_action(new_term);
								}

								if (category.equals(GDSCategory.SUBJECT.name())) {
									
									gdsrule.setCategory_subject(new_term);

								}

								if (category.equals(GDSCategory.RESOURCE.name())) {
									gdsrule.setCategory_resource(new_term);
								}
								if (category.equals(GDSCategory.RULE_PRIORITY.name())) {
									
									gdsrule.setCategory_rule_priority(new_term);

								}

							}

						}

					}

				}

			}

		}

		return gdsrule;
	}

	private GDSTerm_Conjunction setGDSTermConjProps(GDSTerm_Conjunction conj, Entity entity) {

		if(conj == null){
			conj = new GDSTerm_Conjunction();
			conj.setTerm_key(entity.getKey());
			
			
		}
		conj.setTerm_key(entity.getKey());
		
		if (entity.hasProperty(TermPara.DISCRIMINATOR.name())) {
			conj.setDiscriminatr((String)entity.getProperty(TermPara.DISCRIMINATOR.name()));
		}
		
		if (entity.hasProperty(TermPara.CATEGORY.name())) {
			conj.setCategory((String)entity.getProperty(TermPara.CATEGORY.name()));
		}

		if (entity.hasProperty(TermPara.FUNCTION.name())) {
			conj.setFunction((String)entity.getProperty(TermPara.FUNCTION.name()));
		}

		
		return conj;
	}

	private GDSTerm_Condition setGDSTermCondProps(GDSTerm_Condition cond, Entity entity) {
	
		if(cond == null){
			
			cond = new GDSTerm_Condition();
		
		}
		
		cond.setTerm_key(entity.getKey());
		
		if (entity.hasProperty(TermPara.DISCRIMINATOR.name())) {
			cond.setDiscriminatr((String)entity.getProperty(TermPara.DISCRIMINATOR.name()));
		}
		
		if (entity.hasProperty(TermPara.CATEGORY.name())) {
			cond.setCategory((String)entity.getProperty(TermPara.CATEGORY.name()));
		}

		if (entity.hasProperty(TermPara.NAME.name())) {
			cond.setName((String)entity.getProperty(TermPara.NAME.name()));
		}

		if (entity.hasProperty(TermPara.VALUE.name())) {
			cond.setValue((String)entity.getProperty(TermPara.VALUE.name()));
		}

		if (entity.hasProperty(TermPara.COMPARISION.name())) {
			cond.setComparision((String)entity.getProperty(TermPara.COMPARISION.name()));
		}

	
		
		
		return cond;
	}

	private GDSRule setGDSRuleProperties(GDSRule gdsrule, Entity entity) {
		
		if(gdsrule == null){
			
			gdsrule = new GDSRule();
			
		}
		
		gdsrule.setRule_key(entity.getKey());
		
		if (entity.hasProperty(RulePara.DESCRIPTION.name())) {
			gdsrule.setDescription((String)entity.getProperty(RulePara.DESCRIPTION.name()));
		}

		if (entity.hasProperty(RulePara.DECISION.name())) {
			gdsrule.setDecision((String)entity.getProperty(RulePara.DECISION.name()));
		}
			
		
		return gdsrule;
	}

	public List<GDSRule> find(Map<String, List<List<String>>> attributeMap) {

		log.info("start query: find with select parameters" + attributeMap.toString());
		
		List<GDSRule> rstl = new ArrayList<GDSRule>();
		
		RefmoLogr reflog = new RefmoLogr("GETRULESFROMKEYS: Persistence Operation Find NoSQL");
		reflog.start();

		for (Key rule_key : findRuleKeys(attributeMap)) {
			if(rule_key != null){
			rstl.add(this.loadSingleRuleByAncestorKey(rule_key));
			}
		}
		
		reflog.stop();
//		//TEST
		this.printGDSRule(rstl);

		
		
		return rstl;
	}

	private void printGDSRule(List<GDSRule> rstl) {
		
		log.info("ENDRESULT: Found \n" + rstl.size() + " items ");

for (GDSRule gdsRule : rstl) {
	
//	this.printCurrentEntity(entity);
	log.info(gdsRule.toString());
	
}
		
	}

	public Set<Key> findRuleKeys(Map<String, List<List<String>>> attributeMap) {

		Set<Key> relevant_rulekeys = null;
		if(attributeMap.size() == 0){
			
			relevant_rulekeys = this.findAllRules();
		}
		
		RefmoLogr reflog1 = new RefmoLogr("KEYSELECTIONTIME: 1.1 Datastore Operation Find NoSQL Keys");
		reflog1.start();
		for (String cat_key : attributeMap.keySet()) {
			
		//log.info("select rule key for category: " + cat_key);
		
			RefmoLogr reflog = new RefmoLogr("2. Persistence Operation FindCategory NoSQL");
			reflog.start();
			
			relevant_rulekeys = this.findRuleKeysEachCategory(
					relevant_rulekeys, attributeMap.get(cat_key), cat_key);
			
			reflog.stop();
		}
		reflog1.stop();
		return relevant_rulekeys;
	}

	private Set<Key> findAllRules() {
		log.info("start query: find-all");
		Set<Key> r_rule_list = new HashSet<Key>();

		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			Query q = new Query(DSElements.RULE.name()).setKeysOnly();

			for (Entity result : datastore.prepare(q).asList(
					FetchOptions.Builder.withDefaults())) {

			//	log.info("found rule with key: " + result.getKey());
				r_rule_list.add(result.getKey());

				// TEST
				// printCurrentEntity( result);

			}

		}

		catch (Exception e) {

			e.printStackTrace();
		}

		return r_rule_list;
	}

	private Set<Key> findRuleKeysEachCategory(Set<Key> relevant_rulekeys,
			List<List<String>> list, String cat_key) {

		/***
		 * 
		 * attributes by category
		 */

		Set<Key> attribute_keys = new HashSet<>();
		
		/**
		 *   no select parameter defined for the  current category 
		 */
		if(list.size() != 0){
		attribute_keys = relevant_rulekeys;
		}
		
	//	if(attribute_keys != null){
	//	log.info("RULES IN LIST"+ attribute_keys.toString());
	//	}
		Set<Key> no_terms_keys = new HashSet<>();
		RefmoLogr reflog1 = new RefmoLogr("2.1 Datastore Operation FindCategoryAttribute NoSQL");
		reflog1.start();
		for (List<String> attribute_list : list) {
			
			//log.info("rule_keys by category and single attribute: "+ attribute_list.toString());

			attribute_keys = getRulesBySingleAttribute(cat_key, attribute_keys,
					attribute_list);

		}

		reflog1.stop();
		/**
		 * get rule keys, where no terms for a given category are existing
		 */
		
		RefmoLogr reflog2 = new RefmoLogr("2.2 Datastore Operation FindCategoryNoAttribute NoSQL");
		reflog2.start();
		no_terms_keys = getRulesWithoutCategoryTerms(cat_key, relevant_rulekeys);
		reflog2.stop();
//		log.info("rules without any terms for the slected category: " + no_terms_keys.toString());
		
	
		
		

		/**
		 * OR marriage both keys
		 * 
		 */

		for (Key key : no_terms_keys) {

			if (attribute_keys.contains(key) != true) {

				attribute_keys.add(key);
			}

		}

		return attribute_keys;
	}

	private Set<Key> getRulesBySingleAttribute(String cat_key,
			Set<Key> relevant_rulekeys, List<String> attribute_list) {

		Set<Key> new_relevant_rulekeys = new HashSet<>();
		List<Filter> filters = new ArrayList<Filter>();
		
		boolean empty = false;

		if (attribute_list.size() == 3) {
			
			// test
			
			
				
			
			filters.add(new FilterPredicate(TermPara.NAME.name(),
					FilterOperator.EQUAL, attribute_list.get(0)));
			filters.add(new FilterPredicate(TermPara.CATEGORY.name(),
					FilterOperator.EQUAL, attribute_list.get(1)));
			filters.add(new FilterPredicate(TermPara.DISCRIMINATOR.name(),
					FilterOperator.EQUAL, attribute_list.get(2)));
			
		}

		if (relevant_rulekeys != null) {
			
		
			if(relevant_rulekeys.size() == 0 ){
				empty = true;
			}
			else{
				
 	//	IN Operation is very slow, not able to scale
	//	--------------------------------------------
				Filter action_filter = new FilterPredicate(
						TermPara.PARENT_RULE.name(), FilterOperator.IN,
						relevant_rulekeys);
				filters.add(action_filter);
//				
				// Filter OR Filter OR Filter....
//				
//				CompositeFilter cf_keyor= CompositeFilterOperator.or(buildKeyOrFilter(relevant_rulekeys));
//				filters.add(cf_keyor);
				
				
			}
		}
		
		if(!empty){

		try {
			
			RefmoLogr reflog = new RefmoLogr("Datastore Operation FindByFilter NoSQL");
			reflog.start();
			
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			
			CompositeFilter cf= CompositeFilterOperator.and(filters);

			Query endjquery = new Query(DSElements.TERM.name())
					.setFilter(cf);
//			endjquery.addProjection(new PropertyProjection(TermPara.PARENT_RULE
//					.name(), com.google.appengine.api.datastore.Key.class));
//			// endjquery.addProjection(new PropertyProjection("parent_rule",
			// Key.class));
			List<Entity> allresultsend = datastore.prepare(endjquery).asList(
					FetchOptions.Builder.withDefaults());
			
			reflog.stop();
			
		//	log.info("results for filter" + cf.toString());

			RefmoLogr reflog2 = new RefmoLogr("merge entity keys NOSQL");
			reflog2.start();
			
			for (Entity entity : allresultsend) {

				// TEST
		//		printCurrentEntity(entity);

				Key k_rausgabe = (Key) entity.getProperty(TermPara.PARENT_RULE
						.name());
				if (k_rausgabe != null) {

					// add rule key to list
					new_relevant_rulekeys.add(k_rausgabe);

				}

			}
			
			reflog2.stop();

		}

		catch (Exception e) {

			e.printStackTrace();
		}
		}

		return new_relevant_rulekeys;
	}

	private List<Filter> buildKeyOrFilter(Set<Key> relevant_rulekeys) {
		
		List<Filter> newfilterlist = new ArrayList();
		for (Key key : relevant_rulekeys) {
			
		
			newfilterlist.add(new FilterPredicate(TermPara.PARENT_RULE.name(), FilterOperator.EQUAL,key));			
			
			
		}
		
		
		return newfilterlist;
	}

	private void printCurrentEntity(Entity entity) {

		log.info("ENTITY-KEY: " + entity.getKey() + " ENTITY-KIND: " + entity.getKind() + " ENTITY-PARENT-KEY: " + entity.getParent());

		// properties ;)
	
		
		String logst = "";

		for (int i = 0; i < entity.getProperties().keySet().size(); i++) {

			logst += (entity.getProperties().keySet().toArray()[i]
					+ " : "
					+ entity.getProperties().get(
							entity.getProperties().keySet().toArray()[i])) + "\n";

		}
		log.info("ENTITY properties --- \n" + logst);
	}

	private Set<Key> getRulesWithoutCategoryTerms(String cat_key,
			Set<Key> relevant_rulekeys) {

		Set<Key> new_relevant_rulekeys = new HashSet<>();
		Set<Key> selected_rulekeys = new HashSet<>();

		/**
		 * select rule keys without any terms
		 */

		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			// Use class Query to assemble a query
			Query q = new Query(DSElements.RULE.name());

			// Use PreparedQuery interface to retrieve results
			PreparedQuery pq = datastore.prepare(q);
			
			

			for (Entity entity : pq.asIterable()) {

				// TEST
			//	printCurrentEntity(entity);

				Key key_category = (Key) entity.getProperty(cat_key);
				
			//	log.info("rule keys without a term in category "+ cat_key);

				/***
				 * ALTERNATIVE: Filter Key Query q = new Query("Person")
				 * .addFilter(Entity.KEY_RESERVED_PROPERTY,
				 * Query.FilterOperator.GREATER_THAN, lastSeenKey);
				 * 
				 */

				if (key_category == null) {

				
					selected_rulekeys.add(entity.getKey());
				//	log.info("rule  without a term in category "+ cat_key);
					//this.printCurrentEntity(entity);

				}

			}

			if (relevant_rulekeys == null) {

				new_relevant_rulekeys = selected_rulekeys;
			}

			else {
				
//				log.info("merge both list: ");
//				log.info(relevant_rulekeys.toString());
//				log.info(selected_rulekeys.toString());

				for (Key key : selected_rulekeys) {

					if (relevant_rulekeys.contains(key)) {
						new_relevant_rulekeys.add(key);

					}

				}
			}

			// rules without a term for the given category OR rule keys in list

		}

		catch (Exception e) {

			e.printStackTrace();
		}

		return new_relevant_rulekeys;
	}

	public List<GDSRule> deleteByRuleObject(List<GDSRule> list) {

		Set<Key> rule_keys = new HashSet<Key>();

		for (GDSRule rule : list) {

			rule.delete(rule_keys);
		}

		this.deleteByRuleKey(rule_keys);

		return list;
	}

	public boolean deleteByRuleKey(Set<Key> list) {

		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			datastore.delete(list);

			// }
		} catch (Exception e) {

			e.printStackTrace();

		}
		return true;
	}

	public void persistCondition(GDSTerm_Condition... conditions) {

		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			List<Entity> cond_entities = new ArrayList<Entity>();
			for (GDSTerm_Condition gdsTerm_Condition : conditions) {

				cond_entities.add(this.setConditionEntityProperties(null,
						gdsTerm_Condition));

			}

			Integer i = 0;
			for (Key entity_key : datastore.put(cond_entities)) {

				conditions[i++].setTerm_key(entity_key);

			}

		}

		catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void persistRule(GDSRule gdsRule) {

		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			Entity new_rule = setRuleEntityProperties(null, gdsRule);
			datastore.put(new_rule);
			gdsRule.setRule_key(new_rule.getKey());

		}

		catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void persistNewConjunction(GDSTerm_Conjunction gdsTerm_Conjunction) {
		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			Entity new_conjunction = this.setConjunctionEntityProperties(null,
					gdsTerm_Conjunction);
			datastore.put(new_conjunction);
			gdsTerm_Conjunction.setTerm_key(new_conjunction.getKey());

		}

		catch (Exception e) {

			e.printStackTrace();
		}

	}

	public GDSRule getRuleByKey(Key ancestorKey) {

		return this.loadSingleRuleByAncestorKey(ancestorKey);
	}

	public void updateCondition(GDSTerm_Condition gdsTerm_Condition) {

		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			// Filter cond_filter = new
			// FilterPredicate(Entity.KEY_RESERVED_PROPERTY,
			// Query.FilterOperator.EQUAL,
			// gdsTerm_Condition.getTerm_key());
			//
			// Query q = new
			// Query(DSElements.CONDITION.name()).setFilter(cond_filter);
			//
			// List<Entity> resut_entities =
			// datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
			//
			// if(resut_entities.size() == 1){
			//
		
			
			Entity entity_condition = this.setConditionEntityProperties(
					datastore.get(gdsTerm_Condition.getTerm_key()),
					gdsTerm_Condition);
			datastore.put(entity_condition);

			// }
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void updateConjunction(GDSTerm_Conjunction gdsTerm_Conjunction) {
		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			// Filter cond_filter = new
			// FilterPredicate(Entity.KEY_RESERVED_PROPERTY,
			// Query.FilterOperator.EQUAL,
			// gdsTerm_Conjunction.getTerm_key());
			//
			// Query q = new
			// Query(DSElements.CONJUNCTION.name()).setFilter(cond_filter);
			//
			// List<Entity> resut_entities =
			// datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
			//
			// if(resut_entities.size() == 1){
			//
			Entity entity_conjunction = this.setConjunctionEntityProperties(
					datastore.get(gdsTerm_Conjunction.getTerm_key()),
					gdsTerm_Conjunction);
			datastore.put(entity_conjunction);

			// }
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void updateRule(GDSRule gdsRule) {
		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			// Filter cond_filter = new
			// FilterPredicate(Entity.KEY_RESERVED_PROPERTY,
			// Query.FilterOperator.EQUAL,
			// gdsRule.getRule_key());
			//
			// Query q = new
			// Query(DSElements.RULE.name()).setFilter(cond_filter);
			//
			// List<Entity> resut_entities =
			// datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
			//
			// if(resut_entities.size() == 1){

			Entity entity_rule = this.setRuleEntityProperties(
					datastore.get(gdsRule.getRule_key()), gdsRule);
			datastore.put(entity_rule);

			// }
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private Entity setRuleEntityProperties(Entity rule_entity, GDSRule gds_rule) {

		if (rule_entity == null) {
			
			
			Key ruleStoreKey = KeyFactory.createKey("RULESTORE", "FUFUQ");
			rule_entity = new Entity(DSElements.RULE.name(), ruleStoreKey );

		}

		if (gds_rule.getCategory_action() != null) {
			rule_entity.setProperty(GDSCategory.ACTION.name(), gds_rule
					.getCategory_action().getTerm_key());
		}

		if (gds_rule.getCategory_resource() != null) {
			rule_entity.setProperty(GDSCategory.RESOURCE.name(), gds_rule
					.getCategory_resource().getTerm_key());
		}
		if (gds_rule.getCategory_rule_priority() != null) {
			rule_entity.setProperty(GDSCategory.RULE_PRIORITY.name(), gds_rule
					.getCategory_rule_priority().getTerm_key());
		}

		if (gds_rule.getCategory_subject() != null) {
			rule_entity.setProperty(GDSCategory.SUBJECT.name(), gds_rule
					.getCategory_subject().getTerm_key());
		}

		if (gds_rule.getDescription() != null) {
			rule_entity.setProperty(RulePara.DESCRIPTION.name(),
					gds_rule.getDescription());
		}

		if (gds_rule.getDecision() != null) {
			rule_entity.setProperty(RulePara.DECISION.name(),
					gds_rule.getDecision());
		}

		return rule_entity;

	}

	private Entity setConditionEntityProperties(Entity cond_entity,
			GDSTerm_Condition gds_cond) {

		if (cond_entity == null) {

			if (gds_cond.getParent_rule() != null) {
				if (gds_cond.getParent_term() == null) {

					cond_entity = new Entity(DSElements.TERM.name(), gds_cond
							.getParent_rule().getRule_key());

				} else if (gds_cond.getParent_term() != null) {
					cond_entity = new Entity(DSElements.TERM.name(), gds_cond
							.getParent_term().getTerm_key());
				}
			}
		}

		cond_entity.setProperty(TermPara.DISCRIMINATOR.name(),
				DSElements.CONDITION.name());

		if (gds_cond.getCategory() != null) {
			cond_entity.setProperty(TermPara.CATEGORY.name(),
					gds_cond.getCategory());
		}

		if (gds_cond.getName() != null) {
			cond_entity.setProperty(TermPara.NAME.name(), gds_cond.getName());
		}
		if (gds_cond.getValue() != null) {
			cond_entity.setProperty(TermPara.VALUE.name(), gds_cond.getValue());
		}

		if (gds_cond.getComparision() != null) {
			cond_entity.setProperty(TermPara.COMPARISION.name(),
					gds_cond.getComparision());
		}

		if (gds_cond.getParent_rule() != null) {
			cond_entity.setProperty(TermPara.PARENT_RULE.name(), gds_cond
					.getParent_rule().getRule_key());
		}

		return cond_entity;

	}

	private Entity setConjunctionEntityProperties(Entity conj_entity,
			GDSTerm_Conjunction gds_conj) {

		if (conj_entity == null) {

			if (gds_conj.getParent_rule() != null) {
				if (gds_conj.getParent_term() == null) {

					conj_entity = new Entity(DSElements.TERM.name(), gds_conj
							.getParent_rule().getRule_key());

				} else if (gds_conj.getParent_term() != null) {
					conj_entity = new Entity(DSElements.TERM.name(), gds_conj
							.getParent_term().getTerm_key());
				}
			}

		}

		conj_entity.setProperty(TermPara.DISCRIMINATOR.name(),
				DSElements.CONJUNCTION.name());

		if (gds_conj.getCategory() != null) {
			conj_entity.setProperty(TermPara.CATEGORY.name(),
					gds_conj.getCategory());
		}

		if (gds_conj.getFunction() != null) {
			conj_entity.setProperty(TermPara.FUNCTION.name(),
					gds_conj.getFunction());
		}

		if (gds_conj.getParent_rule() != null) {
			conj_entity.setProperty(TermPara.PARENT_RULE.name(), gds_conj
					.getParent_rule().getRule_key());
		}

		return conj_entity;

	}

	public boolean deleteByAncestorRuleKey(Set<Key> list) {

		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			List<Entity> results_all = new ArrayList<Entity>();
			Set<Key> list_keys = new HashSet<Key>();

			for (Key key : list) {

		//		log.info("delete by key: select child-terms for rule with key: " +  key.toString());
				Query q = new Query().setAncestor(key);
				List<Entity> results = datastore.prepare(q).asList(
						FetchOptions.Builder.withDefaults());
				results_all.addAll(results);
				
			//	log.info("child terms for rule: " + results.toString());
				
				

			}

			for (Entity entity : results_all) {

				list_keys.add(entity.getKey());
				
			}
	//		log.info("delete by key: child-terms with keys: " + list_keys.toString());
			deleteByRuleKey(list_keys);
			
		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	public void deleteAll() {
	
		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			Query q =  new Query().setKeysOnly();
			List<Entity> results = datastore.prepare(q).asList(
					FetchOptions.Builder.withDefaults());
			
			Set<Key> keys = new HashSet<Key>();
			for (Entity key : results) {
				
				keys.add(key.getKey());
			}
			deleteByRuleKey(keys);

	
		} catch (Exception e) {

			e.printStackTrace();
		}


	}

	public void updateConditions(Map<Key, GDSTerm_Condition> update_condit_key) {
		
		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
	
			List<Entity> updated_entities = new ArrayList<Entity>();
			Map<Key, Entity> key_entity=  datastore.get(update_condit_key.keySet());
			
			for (Key ie_key : update_condit_key.keySet()) {
				
				
				// update object
				// update_condit_key.get(ie_key);
				
				// to update
			   //	key_entity.get(ie_key);
				
				
				updated_entities.add(this.setConditionEntityProperties(key_entity.get(ie_key), update_condit_key.get(ie_key)));
				
			}
		

			datastore.put(updated_entities);

			// }
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public int count() {
		
		int sum = 0;
		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			Query q =  new Query("RULE").setKeysOnly();
			
			List<Entity> results = datastore.prepare(q).asList(
					FetchOptions.Builder.withDefaults());
			
			sum = results.size();
			
	
		} catch (Exception e) {

			e.printStackTrace();
		}
		return sum;
		
	}

	public List<GDSRule> find(Filter combinationFilter) {
		log.info("start query: find with select parameters" +  combinationFilter.toString());
		
		List<GDSRule> rstl = new ArrayList<GDSRule>();
		
		RefmoLogr reflog = new RefmoLogr("GETRULESFROMKEYS: Persistence Operation Find NoSQL");
		reflog.start();

		for (Key rule_key : findRuleKeys(combinationFilter)) {
			if(rule_key != null){
			rstl.add(this.loadSingleRuleByAncestorKey(rule_key));
			}
		}
		
		reflog.stop();
//		//TEST
		this.printGDSRule(rstl);

		
		
		return rstl;
		
	}

	private Set<Key> findRuleKeys(Filter combinationFilter) {
		
		Set<Key> new_relevant_rulekeys = new HashSet<>();

	

		try {
			

			
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			

			Query endjquery = new Query(TermKey.TERMKEY.name()).setFilter(combinationFilter);
		//	endjquery.addProjection(new PropertyProjection(TermKey.RULEKEY.name(), com.google.appengine.api.datastore.Key.class));

			
			RefmoLogr reflog1 = new RefmoLogr("KEYSELECTIONTIME: 1.1 Datastore Operation Find NoSQL Keys");
			reflog1.start();
			List<Entity> allresultsend = datastore.prepare(endjquery).asList(
					FetchOptions.Builder.withDefaults());
			
			reflog1.stop();
			
			
			for (Entity key : allresultsend) {
				
				
				new_relevant_rulekeys.add((Key) key.getProperty(TermKey.RULEKEY.name()));
				
			}
	

		}

		catch (Exception e) {

			e.printStackTrace();
		}
		

	
		
		

		return new_relevant_rulekeys ;
		
		
	}

}
