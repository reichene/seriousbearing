package edu.hfu.refmo.store.sql.model.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import edu.hfu.refmo.store.sql.manager.EMFSingleton;

public class RuleManagerSQL {

	private static final Logger log = Logger.getLogger(RuleManagerSQL.class
			.getName());
	
	public DBRule create(DBRule p_dbrule) {

		EntityManager em = EMFSingleton.getEMF().createEntityManager();
		DBRule ret_dbrule = null;
		try {
			em.getTransaction().begin();
			// ---------------------------------------------
			em.persist(p_dbrule);
			// ---------------------------------------------
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			em.close();
		}

		return ret_dbrule;
	}

	public boolean delete(DBRule p_dbrule) {

		Integer items_delete = 0;
		List<Integer> rule_ids = this.findRelevantRuleIds(p_dbrule);
		String qlString = "delete from DBRule r where r.rule_id IN (:rule_ids)";

		EntityManager em = EMFSingleton.getEMF().createEntityManager();

		try {
			em.getTransaction().begin();

			Query q = em.createQuery(qlString);
			q.setParameter("rule_ids", rule_ids);
			items_delete = q.executeUpdate();

			log.info("deleted items: " + items_delete);
			em.getTransaction().commit();

		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			em.close();
		}
		return ((items_delete > 0) ? true : false);

	}

	private String[] singleSubQuery(DBCategory category, String[] querystring) {

		String cat = "";

		String klammer = querystring[1];
		String subquery = querystring[0];

		if (category instanceof DBAction) {
			cat = "ACTION";
		}

		else if (category instanceof DBResource) {

			cat = "RESOURCE";
		}

		else if (category instanceof DBSubject) {

			cat = "SUBJECT";
		}

		else if (category instanceof DBRulePriority) {
			cat = "RULE_PRIORITY";

		}

		if (category.getRoot_term() != null) {

			if (category.getRoot_term() instanceof DBTerm_Condition) {

				String att_que = " AND a.rule_id IN ( SELECT b.rule_id FROM RULE AS a "
						+ " LEFT OUTER JOIN CATEGORY AS b "
						+ " ON a.rule_id = b.RULE_ID "
						+ " LEFT OUTER JOIN TERM AS c "
						+ " ON b.category_id = c.CATEGORY_CATEGORY_ID "
						+ " WHERE (b.category = '"
						+ cat
						+ "' AND c.NAME='"
						+ ((DBTerm_Condition) category.getRoot_term())
								.getName()
						+ "') "
						+ " OR ( b.category = '"
						+ cat + "' AND c.CATEGORY_CATEGORY_ID IS NULL)  ";

				if (subquery == null) {
					subquery = att_que;
				} else {
					subquery = subquery + att_que;
				}

				if (klammer == null) {
					klammer = ")";
				} else {
					klammer = klammer + " ) ";
				}

			}

			else if (category.getRoot_term() instanceof DBTerm_Conjunction) {

				for (DBTerm dbterm : ((DBTerm_Conjunction) category
						.getRoot_term()).getSubordinate_terms()) {
					if (dbterm instanceof DBTerm_Condition) {

						String begin_subquery = " AND a.rule_id IN ( ";

						String att_que = " SELECT b.rule_id FROM RULE AS a "
								+ " LEFT OUTER JOIN CATEGORY AS b "
								+ " ON a.rule_id = b.RULE_ID "
								+ " LEFT OUTER JOIN TERM AS c "
								+ " ON b.category_id = c.CATEGORY_CATEGORY_ID "
								+ " WHERE (b.category = '" + cat
								+ "' AND c.NAME='"
								+ ((DBTerm_Condition) dbterm).getName() + "') "
								+ " OR ( b.category = '" + cat
								+ "' AND c.CATEGORY_CATEGORY_ID IS NULL) ";

						if (subquery == null) {
							subquery = begin_subquery + att_que;
						} else {
							subquery = subquery + begin_subquery + att_que;
						}

						if (klammer == null) {
							klammer = ")";
						} else {
							klammer = klammer + " ) ";
						}

					}

				}

			}

		}

		querystring[0] = subquery;
		querystring[1] = klammer;

		return querystring;

	}

	private String buildQueryString(DBRule p_dbrule) {

		String[] query = new String[2];

		if (p_dbrule.getAction() != null) {

			query = singleSubQuery(p_dbrule.getAction(), query);
		}

		if (p_dbrule.getResource() != null) {

			query = singleSubQuery(p_dbrule.getResource(), query);

		}

		if (p_dbrule.getSubject() != null) {

			query = singleSubQuery(p_dbrule.getSubject(), query);

		}

		if (p_dbrule.getRule_priority() != null) {
			query = singleSubQuery(p_dbrule.getRule_priority(), query);
		}

		if (query[0] == null) {
			query[0] = " ";
		}

		if (query[1] == null) {
			query[1] = " ";
		}

		String where = "WHERE a.rule_id IS NOT NULL ";
		// String que_erg = buildQueryString(p_dbrule);

		// String que =
		// "SELECT DISTINCT a.rule_id, a.description, a.effect FROM rule AS a LEFT JOIN category AS b "
		// +"ON a.rule_id = b.RULE_ID LEFT JOIN term AS c "
		// +"ON b.category_id = c.CATEGORY_CATEGORY_ID " + where +
		// query[0]+query[1];
		//

		// String que =
		// "SELECT DISTINCT a.rule_id FROM rule AS a LEFT JOIN category AS b "
		// +"ON a.rule_id = b.RULE_ID LEFT JOIN term AS c "
		// +"ON b.category_id = c.CATEGORY_CATEGORY_ID " + where +
		// query[0]+query[1];

		String que = "SELECT DISTINCT a.rule_id FROM RULE AS a LEFT JOIN CATEGORY AS b "
				+ "ON a.rule_id = b.RULE_ID LEFT JOIN TERM AS c "
				+ "ON b.category_id = c.CATEGORY_CATEGORY_ID "
				+ where
				+ query[0] + query[1];

		log.info("Query:" + que);

		return que;

	}

	public List<DBRule> findByRuleId(List<Integer> rule_ids) {

		List<DBRule> temp_dbrules = new ArrayList<DBRule>();

		if (rule_ids != null) {
			if (rule_ids.size() != 0) {
				EntityManager em = EMFSingleton.getEMF().createEntityManager();
				try {
					em.getTransaction().begin();

					CriteriaBuilder cb = em.getCriteriaBuilder();
					CriteriaQuery<DBRule> cq = cb.createQuery(DBRule.class);
					Root<DBRule> dbrule = cq.from(DBRule.class);
					cq.select(dbrule);
					cq.where(dbrule.get("rule_id").in(rule_ids));
					TypedQuery<DBRule> q = em.createQuery(cq);
					temp_dbrules = q.getResultList();

					em.getTransaction().commit();

				} finally {
					if (em.getTransaction().isActive()) {
						em.getTransaction().rollback();
					}

					em.close();
				}

				for (DBRule dbRule : temp_dbrules) {
					log.info(dbRule.toString());

				}
			}
		}

		return temp_dbrules;

	}

	public List<DBRule> find(DBRule p_dbrule) {

		return this.findByRuleId(this.findRelevantRuleIds(p_dbrule));

	}

	public List<Integer> findRelevantRuleIds(DBRule p_dbrule) {

		EntityManager em = EMFSingleton.getEMF().createEntityManager();
		List<Integer> result = new ArrayList<Integer>();

		try {
			em.getTransaction().begin();

			Query query = em.createNativeQuery(buildQueryString(p_dbrule),
					Integer.class);
			result = query.getResultList();

			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			em.close();
		}
		log.info(result.size() + " RuleIds found!");

		return result;

	}

	public List<DBRule> findAll() {

		EntityManager em = EMFSingleton.getEMF().createEntityManager();
		List<DBRule> r_all_dbrules = new ArrayList<DBRule>();

		try {
			em.getTransaction().begin();
			// ---------------------------------------------

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DBRule> cq = cb.createQuery(DBRule.class);
			Root<DBRule> dbrule = cq.from(DBRule.class);
			cq.select(dbrule);
			TypedQuery<DBRule> q = em.createQuery(cq);
			r_all_dbrules = q.getResultList();

			// ---------------------------------------------
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			em.close();
		}

		return r_all_dbrules;

	}

	public List<DBRule> update(DBRule p_dbrule, DBRule p_update_dbrule) {
		
		
		DBTerm_Condition update_rp_condition = null;
		DBTerm_Condition update_action = null;
		DBTerm_Condition update_subject = null;
		DBTerm_Condition update_resource = null;
		
		if(p_update_dbrule != null){
		if(p_update_dbrule.getRule_priority() != null){
			if(p_update_dbrule.getRule_priority().getRoot_term() != null){
				if(p_update_dbrule.getRule_priority().getRoot_term() instanceof DBTerm_Condition){
					 update_rp_condition = (DBTerm_Condition)p_update_dbrule.getRule_priority().getRoot_term() ;
			
				}
			}
		}
		
		if(p_update_dbrule.getAction() != null){
			if(p_update_dbrule.getAction().getRoot_term() != null){
				if(p_update_dbrule.getAction().getRoot_term() instanceof DBTerm_Condition){
					update_action = (DBTerm_Condition)p_update_dbrule.getAction().getRoot_term() ;
			
				}
			}
		}
		
		if(p_update_dbrule.getSubject() != null){
			if(p_update_dbrule.getSubject().getRoot_term() != null){
				if(p_update_dbrule.getSubject().getRoot_term() instanceof DBTerm_Condition){
					 update_subject = (DBTerm_Condition)p_update_dbrule.getSubject().getRoot_term() ;
			
				}
			}
		}
	
		if(p_update_dbrule.getResource() != null){
			if(p_update_dbrule.getResource().getRoot_term() != null){
				if(p_update_dbrule.getResource().getRoot_term() instanceof DBTerm_Condition){
					 update_resource = (DBTerm_Condition)p_update_dbrule.getResource().getRoot_term() ;
			
				}
			}
		}
		}
		

		List<DBRule> relevant_rules = this.find(p_dbrule);
		
		for (DBRule dbr : relevant_rules) {
		
		if (relevant_rules.size() > 0) {

			/**
			 * Transaction
			 */

			EntityManager em = EMFSingleton.getEMF().createEntityManager();

			try {
				em.getTransaction().begin();

				//iterate rules
			
				dbr.setDescription("neue descirption"+ dbr.getRule_id());

				if ((dbr.getAction()) != null) {

					dbr.getAction().setDescription("new action description" + dbr.getRule_id() );

					if (dbr.getAction().getRoot_term() != null) {

						if (dbr.getAction().getRoot_term() instanceof DBTerm_Condition) {

							if(update_action != null){
								
							this.updateConditionTerm(dbr.getAction(), update_action);
							
							}
						}
					}
				}

				if (dbr.getResource() != null) {
					
					dbr.getResource().setDescription("new resource description" + dbr.getRule_id() );

					if (dbr.getResource().getRoot_term() != null) {

						if (dbr.getResource().getRoot_term() instanceof DBTerm_Condition) {

							
							if(update_resource != null){
								
								this.updateConditionTerm(dbr.getResource(), update_resource);
								
							}
						}
					}
				}

				if (dbr.getSubject() != null) {
					dbr.getSubject().setDescription("new subject description" + dbr.getRule_id() );

					if (dbr.getSubject().getRoot_term() != null) {

						if (dbr.getSubject().getRoot_term() instanceof DBTerm_Condition) {

							if(update_subject != null){
								this.updateConditionTerm(dbr.getSubject(), update_subject);
							}
						}
					}
				}

				if (dbr.getRule_priority() != null) {
					dbr.getRule_priority().setDescription("new rule priority description" + dbr.getRule_id() );

					if (dbr.getRule_priority().getRoot_term() != null) {

						if (dbr.getRule_priority().getRoot_term() instanceof DBTerm_Condition) {

							if(update_rp_condition!= null){
								this.updateConditionTerm(dbr.getRule_priority(), update_rp_condition);
							}
						}
					}
				}

				em.merge(dbr);

				// ---------------------------------------------
				em.getTransaction().commit();
			} finally {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}

				em.close();
			}
		}

		}

		return null;
	}

	private void updateConditionTerm(DBCategory category,
			DBTerm_Condition update_cond ){
	
		
		((DBTerm_Condition) category.getRoot_term()).setName( update_cond.getName());
		((DBTerm_Condition) category.getRoot_term()).setValue( update_cond.getValue());
		((DBTerm_Condition) category.getRoot_term()).setComparision_string(update_cond.getComparision_string());
	
		
		
		
	}

	public void deleteAll() {
		
		Integer items_deleted = 0;
			String qlString = "Delete from RULE";

		EntityManager em = EMFSingleton.getEMF().createEntityManager();

		try {
			em.getTransaction().begin();

			Query q = em.createNativeQuery(qlString);
			items_deleted = q.executeUpdate();

			log.info("deleted items: " + items_deleted);
			em.getTransaction().commit();

		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			em.close();
		}
	
		
	}

}
