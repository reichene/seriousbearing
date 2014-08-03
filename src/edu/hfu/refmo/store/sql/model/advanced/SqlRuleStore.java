 package edu.hfu.refmo.store.sql.model.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import de.abacs.base.entity.Action;
import de.abacs.base.entity.AttributeTreeElement;
import de.abacs.base.entity.Condition;
import de.abacs.base.entity.Condition.Comparision;
import de.abacs.base.entity.Conjunction;
import de.abacs.base.entity.Conjunction.Function;
import de.abacs.base.entity.Decision;
import de.abacs.base.entity.Resource;
import de.abacs.base.entity.Rule;
import de.abacs.base.entity.Subject;
import de.abacs.base.store.RuleStore;
import edu.hfu.refmo.logger.RefmoLogr;




public class SqlRuleStore implements RuleStore {
	
	private static final Logger log = Logger.getLogger(SqlRuleStore.class
			.getName());

	private DBTerm mapAttributeTreeElementToInternalTerm(AttributeTreeElement p_ate){
		
		DBTerm r_dbterm = null;
		
		try {
		
		if(p_ate !=null){
			
			if(p_ate instanceof de.abacs.base.entity.Condition)	{
				
				if(((de.abacs.base.entity.Condition) p_ate).getName() != null && ((de.abacs.base.entity.Condition) p_ate).getName() != ""){
				r_dbterm = new DBTerm_Condition(
						((de.abacs.base.entity.Condition) p_ate).getName(),
						((de.abacs.base.entity.Condition) p_ate).getComparision().toString(), 
						((de.abacs.base.entity.Condition) p_ate).getValue()
						);
			}
			}
			else if (p_ate instanceof de.abacs.base.entity.Conjunction){
				
				
				r_dbterm = new DBTerm_Conjunction(((de.abacs.base.entity.Conjunction) p_ate).getFunction().toString());
							
				for (AttributeTreeElement i_ate : ((de.abacs.base.entity.Conjunction) p_ate).getChildren()) {
					
//					DBTerm i_new_dbterm = mapAttributeTreeElementToInternalTerm(i_ate);
//					if(i_new_dbterm != null){
//						
//						((DBTerm_Conjunction)r_dbterm).addDBTerm(i_new_dbterm);
					
//				}
						
					((DBTerm_Conjunction)r_dbterm).addDBTerm((mapAttributeTreeElementToInternalTerm(i_ate) != null ? mapAttributeTreeElementToInternalTerm(i_ate) :null));
						
					
					
				}
			}

		}
		} catch(Exception e){
			
			//e.printStackTrace();
			}
		
		return r_dbterm ;
		
	}
	
	private AttributeTreeElement mapInternalTermToAttributeTreeElement(DBTerm p_dbterm){
	
		AttributeTreeElement r_ate = null;
		
		if(p_dbterm !=null){
			
			if(p_dbterm instanceof DBTerm_Condition)	{
				
				//log.info("Condition" + ((DBTerm_Condition) p_dbterm).getComparision_string());
				
				Comparision new_comp = null;
				
				if(((DBTerm_Condition) p_dbterm).getComparision_string() != null){
				
				switch (((DBTerm_Condition) p_dbterm).getComparision_string()) {
				case "EQUAL":
					new_comp = Comparision.EQUAL;
					break;
				case "GREATER":
					new_comp = Comparision.GREATER;
					break;
				case "SMALLER":
					new_comp = Comparision.SMALLER;
					break;
		
				default: 
					new_comp = Comparision.EQUAL;
					break;
				}
				
				}
				else{
					
					new_comp = Comparision.EQUAL;
				}
				
				//log.info(new_comp.toString());
				
				r_ate= new Condition(((DBTerm_Condition) p_dbterm).getName(), new_comp, ((DBTerm_Condition) p_dbterm).getValue());
			}
			else if (p_dbterm instanceof DBTerm_Conjunction){
				
				//log.info("Conjunction" + ((DBTerm_Conjunction) p_dbterm).getFunction_string() );
				
				
				Function new_function = null;
				
				if(((DBTerm_Conjunction) p_dbterm).getFunction_string() != null){
				switch (((DBTerm_Conjunction) p_dbterm).getFunction_string()) {
				case "AND":
					new_function = Function.AND;
					break;
				case "OR":
					new_function = Function.OR;
					break;
				default: 
					new_function = Function.AND;
					break;

		
				}
				}
				else {
					new_function= Function.AND;
					
				}
				
				AttributeTreeElement ate_elements[] = new AttributeTreeElement[((DBTerm_Conjunction) p_dbterm).getSubordinate_terms().size()];
				int cnt_ate = 0;
													
				for (DBTerm i_dbterm : ((DBTerm_Conjunction) p_dbterm).getSubordinate_terms()) {
					
					AttributeTreeElement new_ate_child = mapInternalTermToAttributeTreeElement(i_dbterm);
					if(new_ate_child != null){
						
						ate_elements[cnt_ate++] = (new_ate_child);
						
					}
					
				}
				
				r_ate = new Conjunction(new_function, ate_elements);
			//	log.info(new_function.toString());
			
			}

		}
	//	log.info(r_ate.toString());
		
		return r_ate ;
		

	}
	
	private Rule mapInternStructureToPolicyLang(DBRule p_dbrule){
		
	
		
		/**
		 *  Action
		 */
		AttributeTreeElement new_action_ate = null;
		if(p_dbrule.getAction() != null){
			if(p_dbrule.getAction().getRoot_term() != null){
				new_action_ate = mapInternalTermToAttributeTreeElement(p_dbrule.getAction().getRoot_term());
			}
		}
		Action new_action = new Action(new_action_ate);
		
		/**
		 *  Subject
		 */
		AttributeTreeElement new_subject_ate = null;
		
		if(p_dbrule.getSubject() != null){
			if(p_dbrule.getSubject().getRoot_term() != null){
				new_subject_ate  = mapInternalTermToAttributeTreeElement(p_dbrule.getSubject().getRoot_term());
			}
		}
		Subject new_subject = new Subject(new_subject_ate);
		
		/**
		 *  Resource
		 */
		AttributeTreeElement new_resource_ate = null;
		
		if(p_dbrule.getResource() != null){
			if(p_dbrule.getResource().getRoot_term() != null){
				new_resource_ate  = mapInternalTermToAttributeTreeElement(p_dbrule.getResource().getRoot_term());
			}
		}
		Resource new_resource = new Resource(new_resource_ate);
		
		
		/**
		 *  Rule_Priority
		 */
		
		AttributeTreeElement new_rule_priority = null;
		if(p_dbrule.getRule_priority() != null){
			if(p_dbrule.getRule_priority().getRoot_term() != null){
				new_rule_priority = mapInternalTermToAttributeTreeElement(p_dbrule.getRule_priority().getRoot_term());
			}
		}
	
		
		
		
		
		/**
		 *  Rule
		 * 
		 */
			
			/**
			 *  Descision
			 */
			
		Decision new_decision = null;
		
		if(p_dbrule.getEffect() != null){
		
		switch (p_dbrule.getEffect()) {
		case "DENY":
			new_decision = Decision.DENY;
			break;
		case "PERMIT":
			new_decision = Decision.PERMIT;
			break;
		case "UNDECIDABLE":
			new_decision = Decision.UNDECIDABLE;
			break;

		default:
			new_decision = Decision.UNDECIDABLE;
			break;
		}
		}
		else {
			
			new_decision =  Decision.UNDECIDABLE;
		}
		
		//log.info("neue_regel:");

		return new Rule(new_rule_priority, new_subject, new_action, new_resource, new_decision);
	
	}
	
	
	public DBRule mapPolicyLangToInternStructure(AttributeTreeElement p_priority_rule, Subject p_subject, Action p_action,
	Resource p_resource){
		
		
		/*
		 *  Rule
		 * 
		 */
		
		DBRule new_rule = new DBRule();
		

		
		/*
		 *  Subject
		 */
		DBSubject new_subject = new DBSubject();
		new_subject.setDescription("subject category description");
		if (p_subject != null){
			if(p_subject.getRootElement() !=null){
			DBTerm root_term = mapAttributeTreeElementToInternalTerm(p_subject.getRootElement());
				if(root_term != null){
			
				new_subject.setDescription("subject category description");
				new_subject.setRoot_term(root_term);
				
				}
			}
		}
		new_rule.setSubject(new_subject);
		
		/*
		 * Resource
		 */
		
		DBAction new_action = new 	DBAction();
		new_action.setDescription("action category description");
		if (p_action != null){
			if(p_action.getRootElement() !=null){
			DBTerm root_term = mapAttributeTreeElementToInternalTerm(p_action.getRootElement());
				if(root_term != null){
					
			
				new_action.setRoot_term(root_term);
			
				
				}
			}
		}
		new_rule.setAction(new_action);
		/*
		 *  Action 
		 */
		
		DBResource new_resource = new DBResource();
		new_resource.setDescription("resource category description");
		if (p_resource != null){
			if(p_resource.getRootElement() !=null){
			DBTerm root_term = mapAttributeTreeElementToInternalTerm(p_resource.getRootElement());
				if(root_term != null){
			
				new_resource.setRoot_term(root_term);
				
				}
			}
		}
		new_rule.setResource(new_resource);
		/*
		 *  Priority Rule 
		 */
		DBRulePriority new_rule_priority = new DBRulePriority();
		new_rule_priority.setDescription("rule priority description");
		if(p_priority_rule !=null){
			
			DBTerm root_term = mapAttributeTreeElementToInternalTerm(p_priority_rule);
			if(root_term != null){
			
				new_rule_priority.setRoot_term(root_term);
				
		
				
			}
		}
		new_rule.setRule_priority(new_rule_priority);
		return new_rule;
		
		
		
	}
	
	@Override
	public boolean create(AttributeTreeElement p_root_element, Subject p_subject, Action p_action,
			Resource p_resource, Decision p_decision) {
	
		DBRule new_dbrule =  mapPolicyLangToInternStructure(p_root_element, p_subject, p_action,
				p_resource );
		new_dbrule.setDescription("description");
		new_dbrule.setEffect(p_decision.toString());
		new_dbrule = new JPAORManager().create(new_dbrule);

		return false;
	}

	@Override
	public boolean delete(AttributeTreeElement p_root_element, Subject p_subject, Action p_action,
			Resource p_resource) {
		
		DBRule new_dbrule =  mapPolicyLangToInternStructure(p_root_element, p_subject, p_action,
				p_resource );
		new JPAORManager().delete(new_dbrule);
		
		return false;
		
	}

	@Override
	public List<Rule> find(AttributeTreeElement p_root_element, Subject p_subject, Action p_action,
			Resource p_resource) {
		
		RefmoLogr reflog = new RefmoLogr("Persistence Operation mapInInternalStructure SQL");
		reflog.start();
		
		DBRule new_dbrule =  mapPolicyLangToInternStructure(p_root_element, p_subject, p_action,
				p_resource );
		reflog.stop();
		
		return 	this.mapInternalListToRuleList(new JPAORManager().find(new_dbrule));
		
	}
	
	private List<Rule> mapInternalListToRuleList(List<DBRule> db_rules){
	
		List<Rule> rules = new ArrayList<Rule>();
		
		for (DBRule db_rule : db_rules){
			
			rules.add(this.mapInternStructureToPolicyLang(db_rule));
		}
		
		return rules;
		
	}

	@Override
	public List<Rule> findAll() {
	
		return this.mapInternalListToRuleList(new JPAORManager().findAll());
	}

	@Override
	public boolean update(AttributeTreeElement p_root_element, Subject p_subject, Action p_action,
			Resource p_resource, AttributeTreeElement p_root_element_new, Subject p_subject_new, Action p_action_new,
			Resource p_resource_new) {

		DBRule new_dbrule =  mapPolicyLangToInternStructure(p_root_element, p_subject, p_action,
				p_resource );
		
		DBRule new_dbrule_update =  mapPolicyLangToInternStructure(p_root_element_new, p_subject_new, p_action_new,
				p_resource_new );
		
		
		new JPAORManager().update(new_dbrule, new_dbrule_update  );
		
		return false;
	}

	public void deleteAll() {
		new JPAORManager().deleteAll();
		
	}

	public int count() {
		return new JPAORManager().count();
		
		
	}



}
