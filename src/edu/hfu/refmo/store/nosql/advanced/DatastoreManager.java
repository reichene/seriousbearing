package edu.hfu.refmo.store.nosql.advanced;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.abacs.base.entity.Action;
import de.abacs.base.entity.AttributeTreeElement;
import de.abacs.base.entity.Condition;
import de.abacs.base.entity.Conjunction;
import de.abacs.base.entity.Decision;
import de.abacs.base.entity.Resource;
import de.abacs.base.entity.Rule;
import de.abacs.base.entity.Subject;


import edu.hfu.refmo.store.nosql.advanced.GDSRule.DSElements;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class DatastoreManager {

	public GDSRule create(AttributeTreeElement ruleRootElement,
			Subject subject, Action action, Resource resource, Decision decision) {

		GDSRule new_gdsrule = new GDSRule("description", ruleRootElement,
				(subject != null ? subject.getRootElement() : null),
				(action != null ? action.getRootElement() : null),
				(resource != null ? resource.getRootElement() : null), decision);
		
		
		
		new GDSSelectionOptimizer().createOptimizationKeys(new_gdsrule.getRule_key(), ruleRootElement,	subject, action, resource);
		
		
		return new_gdsrule;

	}

	public List<GDSRule> update(AttributeTreeElement newRuleRootElement,
			Subject newSubject, Action newAction, Resource newResource,
			List<GDSRule> list) {

		Condition cond_action = null;
		Condition cond_resource = null;
		Condition cond_subject = null;
		Condition cond_rule = null;

		if (newRuleRootElement != null) {
			if (newRuleRootElement instanceof Condition) {

				cond_rule = (Condition) newRuleRootElement;
			}

		}
		if (newSubject != null) {
			if (newSubject.getRootElement() != null) {
				if (newSubject.getRootElement() instanceof Condition) {

					cond_subject = (Condition) newSubject.getRootElement();
				}
			}

		}

		if (newAction != null) {
			if (newAction.getRootElement() != null) {
				if (newAction.getRootElement() instanceof Condition) {

					cond_action = (Condition) newAction.getRootElement();
				}

			}

		}
		if (newResource != null) {
			if (newResource.getRootElement() != null) {
				if (newResource.getRootElement() instanceof Condition) {

					cond_resource = (Condition) newResource.getRootElement();
				}

			}
		}
		
	

		for (GDSRule gdsRule : list) {
			
			Map<Key, GDSTerm_Condition> update_condit_key= new HashMap<Key, GDSTerm_Condition>();
		
			Boolean aend = false;
			
			if (cond_action != null) {
				if (gdsRule.getCategory_action() == null) {
					
					 GDSTerm_Condition new_action_gds_condition =  new GDSTerm_Condition(GDSCategory.ACTION.name(), gdsRule, null);
					 gdsRule.setCategory_action(new_action_gds_condition);
					 aend = true;

				//	 update_condit.add(new_action_gds_condition);
					 
				} else if (gdsRule.getCategory_action() instanceof GDSTerm_Condition) {
					
					update_condit_key.put(((GDSTerm_Condition)gdsRule.getCategory_action()).getTerm_key(), this.updatePropsOfGDSTerm_Condition(((GDSTerm_Condition)gdsRule.getCategory_action()), cond_action));

				}
			}

			
			if (cond_resource != null) {
				if (gdsRule.getCategory_resource() == null) {
					 GDSTerm_Condition new_resource_gds_condition =  new GDSTerm_Condition(GDSCategory.RESOURCE.name(), gdsRule, null);
					 gdsRule.setCategory_resource(new_resource_gds_condition);
					 aend = true;
					 
				//	 update_condit.add(new_resource_gds_condition);
				}
				else if (gdsRule.getCategory_resource() instanceof GDSTerm_Condition) {
					
					update_condit_key.put(((GDSTerm_Condition)gdsRule.getCategory_resource()).getTerm_key(), this.updatePropsOfGDSTerm_Condition(((GDSTerm_Condition)gdsRule.getCategory_resource()), cond_resource));
				}
			}
			
			
			if (cond_rule != null) {
				if (gdsRule.getCategory_rule_priority() == null) {
					 GDSTerm_Condition new_ruleprio_gds_condition =  new GDSTerm_Condition(GDSCategory.RULE_PRIORITY.name(), gdsRule, null);
					 gdsRule.setCategory_rule_priority(new_ruleprio_gds_condition);
					 aend = true;
					 
					// update_condit.add( new_ruleprio_gds_condition);
				} 
				else if (gdsRule.getCategory_rule_priority() instanceof GDSTerm_Condition) 
				{
									
					update_condit_key.put(((GDSTerm_Condition)gdsRule.getCategory_rule_priority()).getTerm_key(), this.updatePropsOfGDSTerm_Condition(((GDSTerm_Condition)gdsRule.getCategory_rule_priority()), cond_rule));
				}
			}
			if (cond_subject != null) {
				if (gdsRule.getCategory_subject() == null) {
					 GDSTerm_Condition new_subject_gds_condition =  new GDSTerm_Condition(GDSCategory.SUBJECT.name(), gdsRule, null);
					 gdsRule.setCategory_subject( new_subject_gds_condition);
					 aend = true;
					 
					// update_condit.add(new_subject_gds_condition);
				} 
				else if (gdsRule.getCategory_subject() instanceof GDSTerm_Condition) {
					
					update_condit_key.put(((GDSTerm_Condition)gdsRule.getCategory_subject()).getTerm_key(),this.updatePropsOfGDSTerm_Condition(((GDSTerm_Condition)gdsRule.getCategory_subject()), cond_subject));
				}
			}
			
			new GDSEntityManager().updateConditions( update_condit_key);
			
			
			if( aend = true){
	
				gdsRule.persist();		
			}

		}

		return list;
	}

	private GDSTerm_Condition updatePropsOfGDSTerm_Condition(
			GDSTerm_Condition gdsTerm_Condition, Condition cond_subject) {

		
		gdsTerm_Condition.setName(cond_subject.getName());
		gdsTerm_Condition.setValue(cond_subject.getValue());
		gdsTerm_Condition.setComparision(cond_subject.getComparision().toString());
		
		
		return gdsTerm_Condition;
	}

	public boolean deleteByKey(Set<Key> list) {
		return new GDSEntityManager().deleteByAncestorRuleKey(list);
	}



	public List<GDSRule> findAll() {

		return new GDSEntityManager().findAll();
	}

	public List<GDSRule> find(AttributeTreeElement ruleRootElement,
			Subject subject, Action action, Resource resource) {

		Map<String, List<List<String>>> attributeMap = this
				.prepareAttributesForFindOps(

				ruleRootElement, (subject != null ? subject.getRootElement()
						: null), (action != null ? action.getRootElement()
						: null), (resource != null ? resource.getRootElement()
						: null));

		
		// select 1
				 new GDSEntityManager().find(attributeMap);
		
		// select2
	return new GDSEntityManager().find(new GDSSelectionOptimizer().getCombinationFilter(ruleRootElement,	subject, action, resource));
		
		
	}

	private Map<String, List<List<String>>> prepareAttributesForFindOps(
			AttributeTreeElement ruleRootElement, AttributeTreeElement subject,
			AttributeTreeElement action, AttributeTreeElement resource) {

		Map<String, List<List<String>>> attributeMap = new HashMap<String, List<List<String>>>();

		if (ruleRootElement != null) {
			attributeMap.put(GDSCategory.RULE_PRIORITY.name(),
					getCatAttributeList(GDSCategory.RULE_PRIORITY.name(), ruleRootElement));

		}

//		if (subject != null) {
//			attributeMap
//					.put(GDSCategory.SUBJECT.name(), getCatAttributeList(GDSCategory.SUBJECT.name(), subject));
//
//		}
//
//		if (action != null) {
//			attributeMap.put(GDSCategory.ACTION.name(), getCatAttributeList(GDSCategory.ACTION.name(), action));
//		}
//
//		if (resource != null) {
//			attributeMap.put(GDSCategory.RESOURCE.name(),
//					getCatAttributeList(GDSCategory.RESOURCE.name(), resource));
//		}
//		
//		
//			attributeMap.put(GDSCategory.RULE_PRIORITY.name(),
//					getCatAttributeList(GDSCategory.RULE_PRIORITY.name(), ruleRootElement));
			attributeMap
					.put(GDSCategory.SUBJECT.name(), getCatAttributeList(GDSCategory.SUBJECT.name(), subject));
		
			attributeMap.put(GDSCategory.ACTION.name(), getCatAttributeList(GDSCategory.ACTION.name(), action));
		
			attributeMap.put(GDSCategory.RESOURCE.name(),
					getCatAttributeList(GDSCategory.RESOURCE.name(), resource));

		return attributeMap;

	}

	private List<List<String>> getAttributeList(String cat,
			AttributeTreeElement rre, List<List<String>> aat_clist) {

		if (rre != null) {

			if (rre instanceof Condition) {

				List<String> alist = new ArrayList<String>();
				alist.add(((Condition) rre).getName());
				alist.add(cat);
				alist.add(DSElements.CONDITION.name());

				aat_clist.add(alist);
			}

			else if (rre instanceof Conjunction) {

				for (AttributeTreeElement ate : ((Conjunction) rre)
						.getChildren()) {

					if (ate != null) {
						aat_clist = getAttributeList(cat, ate, aat_clist);
					}
				}

			}
		}

		return aat_clist;

	}

	private List<List<String>> getCatAttributeList(String cat,
			AttributeTreeElement rre) {

		List<List<String>> cat_list = new ArrayList<List<String>>();

		if (rre != null) {

			cat_list = getAttributeList(cat, rre, cat_list);

		}

		return cat_list;
	}

	public Set<Key> findRuleKeys(AttributeTreeElement ruleRootElement,
			Subject subject, Action action, Resource resource) {
		
		Map<String, List<List<String>>> attributeMap = this
				.prepareAttributesForFindOps(

				ruleRootElement, (subject != null ? subject.getRootElement(): null), 
				(action != null ? action.getRootElement(): null), 
				(resource != null ? resource.getRootElement(): null));
		
		return new GDSEntityManager().findRuleKeys(attributeMap);
	
	}

	public void deleteByObject(List<GDSRule> list) {
		 new GDSEntityManager().deleteByRuleObject(list);
		
	}

	public void deleteAll() {
		new GDSEntityManager().deleteAll();
		
	}

	public int count() {
		
		return new GDSEntityManager().count();
	}

}
