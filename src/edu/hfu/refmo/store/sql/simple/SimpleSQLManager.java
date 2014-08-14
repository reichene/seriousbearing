package edu.hfu.refmo.store.sql.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import de.abacs.base.entity.Condition;
import de.abacs.base.entity.Decision;
import de.abacs.base.entity.Rule;
import de.abacs.base.process.CreateProcessor;
import de.abacs.base.query.Create;
import edu.hfu.refmo.logger.RefmoLogr;
import edu.hfu.refmo.store.nosql.advanced.GDSSelectionOptimizer;
import edu.hfu.refmo.store.nosql.advanced.KeyStoreEntity;
import edu.hfu.refmo.store.sql.manager.EMFSingleton;
import edu.hfu.refmo.store.sql.model.advanced.DBRule;
import edu.hfu.refmo.store.sql.model.advanced.DBTerm_Condition;
import edu.hfu.refmo.store.sql.model.advanced.JPAORManager;

public class SimpleSQLManager {
	
	private static final Logger log = Logger.getLogger(JPAORManager.class
			.getName());

	public SimpleRule create(Rule rule) {
	
		EntityManager em = EMFSingleton.getEMF().createEntityManager();
		SimpleRule new_sr  = null;
		try {
			em.getTransaction().begin();
			
			// ---------------------------------------------
			// create new Simple Rule Object

			new_sr = new SimpleRule();
			new_sr.setRULE_PLAIN(mapRuleToString(rule));
			new_sr.setAttricombinations(getAttributeCombination(new_sr, rule));

			// ---------------------------------------------
			// persist new simple Rule Object

			em.persist(new_sr);
			// ---------------------------------------------
			em.getTransaction().commit();
		}
		catch(Exception e){
			
			e.printStackTrace();
			
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			em.close();
		}

		return  new_sr;
		
		
		
		
	}

	private List<SimpleAttributeCombination> getAttributeCombination(SimpleRule sr, Rule rule) {
	
		List<SimpleAttributeCombination> attcombs_entity = new ArrayList();
		Set<KeyStoreEntity> attcomb = new GDSSelectionOptimizer().getAttributeCombinationCreate(null, rule.getSubject(), rule.getAction(), rule.getResource());
		
		for (KeyStoreEntity keyStoreEntity : attcomb) {
			
			attcombs_entity.add(mapAttComToAttCombObject(sr,keyStoreEntity));

		}
		
		return attcombs_entity;
		
	}

	private SimpleAttributeCombination mapAttComToAttCombObject(SimpleRule sr, KeyStoreEntity kse) {
		
		SimpleAttributeCombination sac = new SimpleAttributeCombination();
		sac.setACTION(kse.getActionHash());
		sac.setSUBJECT(kse.getSubjectHash());
		sac.setRESOURCE(kse.getResourceHash());
		sac.setDELETEFLAG(0);
		sac.setRULE(sr);
	
		return sac;
	}

	private String mapRuleToString(Rule obj) {
		Create q = new Create(null,
				(obj.getSubject()!= null ? obj.getSubject().getRootElement()  : null),
				(obj.getAction() != null ? obj.getAction().getRootElement()  : null),
				(obj.getSubject()!= null ? obj.getResource().getRootElement()  : null),
				 obj.getDecision()
				);
		
		return q.toString();
	}

	
	
	private boolean deleteRulesById(List<Integer> ruleids){
		
		Integer items_delete = 0; 
		String qlString = "UPDATE SimpleAttributeCombination s SET s.DELETEFLAG = 1 where s.RULE.RULE_ID IN (:ruleids)";

		EntityManager em = EMFSingleton.getEMF().createEntityManager();

		try {
			em.getTransaction().begin();

		//	Query q = em.createNativeQuery(qlString);
			Query q = em.createQuery(qlString);

			q.setParameter("ruleids", ruleids);
			q.executeUpdate();
//
//			log.info("deleted items: " + items_delete);
			em.getTransaction().commit();

		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			em.close();
		}
		return ((items_delete > 0) ? true : false);
	}
	public boolean delete(Rule rule) {

		return deleteRulesById(findRelevantRuleIds(rule));
		
	}

	public 	List<Rule>  findAll() {
	
		
		EntityManager em = EMFSingleton.getEMF().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		List<Rule> r_all_dbrules = new ArrayList<Rule>();

		try {
			tx.begin();
			tx.setRollbackOnly();
			// ---------------------------------------------

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<SimpleRule> cq = cb.createQuery(SimpleRule.class);
			Root<SimpleRule> dbrule = cq.from(SimpleRule.class);
			cq.select(dbrule);
			TypedQuery<SimpleRule> q = em.createQuery(cq);
			r_all_dbrules = mapPlainRuleListToSimpleRule(q.getResultList());
			
			

			// ---------------------------------------------
			//em.getTransaction().commit();
			tx.rollback();
		}
		finally
		{
		    if (tx.isActive())
		    {
		        tx.rollback();
		    }
		}
		em.close();

		return r_all_dbrules;
		
		
		
	}

	private List<Rule>  mapPlainRuleListToSimpleRule(List<SimpleRule> resultList) {
		
		
		List<Rule> rule_list = new ArrayList();
		
		for (SimpleRule simpleRule : resultList) {
			
			
			rule_list.add(mapRuleStringToSimpleRule(simpleRule.getRULE_PLAIN()));
			
		}
		
		
		
		return rule_list;
	}

	private Rule mapRuleStringToSimpleRule(String creates) {

		return new CreateProcessor(null).mapCreateString(creates);	
				
	
	}

	public 	List<Rule>  find(Rule rule) {

		
		return mapPlainRuleListToSimpleRule(selectRulesByIds(findRelevantRuleIds(rule)));
		
	}


	
	private List<SimpleRule> selectRulesByIds(
			List<Integer> rule_ids) {

		HashMap<Integer, Rule> temp_dbrules = new HashMap<Integer, Rule>();
		List<SimpleRule> listSP = new ArrayList();


		if (rule_ids != null) {
			if (rule_ids.size() != 0) {
				EntityManager em = EMFSingleton.getEMF().createEntityManager();
				EntityTransaction tx = em.getTransaction();
				
				try {
					tx.begin();
					tx.setRollbackOnly();
					
					RefmoLogr reflog = new RefmoLogr("GETRULESFROMKEYS: MySQL Operation Select SQL");
					reflog.start();
					
					CriteriaBuilder cb = em.getCriteriaBuilder();
					CriteriaQuery<SimpleRule> cq = cb.createQuery(SimpleRule.class);
					Root<SimpleRule> dbrule = cq.from(SimpleRule.class);
					cq.select(dbrule);
					cq.where(dbrule.get("RULE_ID").in(rule_ids));
					TypedQuery<SimpleRule> q = em.createQuery(cq);
					listSP = q.getResultList();
					
					reflog.stop();
					
					tx.rollback();

				} 	finally
				{
				    if (tx.isActive())
				    {
				        tx.rollback();
				    }
				}
				em.close();


//
//				}
			}
		}
		
		log.info("find: found "+ listSP.size() + " rules ");

		//logging
		for (SimpleRule dbRule : listSP) {
			log.info(dbRule.toString());
		}
		//logging
		
		
		return listSP;
		
	
	}

	public List<Integer> findRelevantRuleIds(Rule rule) {
		
		EntityManager em = EMFSingleton.getEMF().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		List<Integer> result = new ArrayList<Integer>();
		
		if(rule != null){
		
	//	log.info("BEGIN WITH FIND QUERY AND PARAMETERS: " +rule.toString() );

		try {
			tx.begin();
			tx.setRollbackOnly();
			
		//	log.info("find rules for condition: "+ rule.toString());
			
			RefmoLogr reflog = new RefmoLogr("KEYSELECTIONTIME: MySQL Operation Select SQL");
			reflog.start();
			
			Query query = em.createNativeQuery(buildQueryStringRuleIds(rule),
					Integer.class);
			result = query.getResultList();
			
			reflog.stop();

			tx.rollback();
		}
		finally
		{
		    if (tx.isActive())
		    {
		        tx.rollback();
		    }
		}
		em.close();
		log.info(result.size() + " RuleIds found!");
		
		}

		return result;
		
		
	
	}

	private String buildQueryStringRuleIds(Rule rule) {
		
		
		String que = "SELECT  RULE_RULE_ID FROM SIMPLEATTRIBCOMB";
		
		
		Set<KeyStoreEntity> attrcomb = new GDSSelectionOptimizer().getAttributeCombination(null,rule.getSubject(), rule.getAction(), rule.getResource());
		
	if(attrcomb.size() > 0){
			
			que += " WHERE DELETEFLAG < 1 AND (";
			
		}
		for (int i = 0; i < attrcomb.size(); i++) 
		 {
			if(attrcomb.toArray()[i] != null){
				
				que = que + mapKeyStoreEntityToCond((KeyStoreEntity) attrcomb.toArray()[i]);
				
						
						if(i+1 == attrcomb.size()){
							
							
						}
						else{
							
							que = que + " OR ";
						}
				
				
			}
		 }
	if(attrcomb.size() > 0){
			
			que = que + " )";
			
		}

		log.info("Find-Query:" + que);

		return que;
		
	
	}

	private String mapKeyStoreEntityToCond(KeyStoreEntity kse) {
		
		String subquery = "";
		
		if(kse != null){
			
			
			subquery = "( " 
					 + " ACTION = '"		+ (kse.getActionHash() != null ? kse.getActionHash()  : 0)+"'"
					 + " AND SUBJECT = '"	+ (kse.getSubjectHash() != null ? kse.getSubjectHash()  : 0)+ "'"
					 + " AND RESOURCE = '" 	+ (kse.getResourceHash()  != null ? kse.getResourceHash()  : 0)+ "' ) "; 
			
		}
		
		
		
		
		return subquery;
	}

	public boolean update(Rule rule, Rule rule2) {

		List<Integer> ruleids_to_be_updated= this.findRelevantRuleIds(rule2);
	
		updateRulesCreateNew( selectRulesByIds(ruleids_to_be_updated), rule2);
		
		return deleteRulesById(ruleids_to_be_updated);
		
	}

	private void updateRulesCreateNew(List<SimpleRule> list,
			Rule rule2) {
		
		List<SimpleRule> srtobecreate = new ArrayList();
		 
		for (SimpleRule cur_sr : list) {
			
		Rule nsr = this.mapRuleStringToSimpleRule(cur_sr.getRULE_PLAIN());	 
		srtobecreate.add(updateSimpleRule(nsr, rule2));
		
		
		}
		
		persistNewSimpleRules(srtobecreate);
		
	}

	private void persistNewSimpleRules(
			List<SimpleRule> srtobecreate) {
	
		EntityManager em = EMFSingleton.getEMF().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
	

			// ---------------------------------------------
			// persist new simple Rule Object
		
			for (SimpleRule simpleRule : srtobecreate) {
				em.persist(simpleRule);
			}
			
			// ---------------------------------------------
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			em.close();
		}

	
	}

	private SimpleRule updateSimpleRule(
			Rule neur, Rule altr) {


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
		
		SimpleRule sr = new SimpleRule();
		sr.setRULE_PLAIN(this.mapRuleToString(neur));
		sr.setAttricombinations(this.getAttributeCombination(sr, neur));
		
		return sr ;
	}

}
