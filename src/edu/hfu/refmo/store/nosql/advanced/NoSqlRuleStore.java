package edu.hfu.refmo.store.nosql.advanced;

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

public class NoSqlRuleStore implements RuleStore {

	private static final Logger log = Logger.getLogger(NoSqlRuleStore.class
			.getName());
	@Override
	public boolean create(AttributeTreeElement ruleRootElement,
			Subject subject, Action action, Resource resource, Decision decision) {
		
		
		RefmoLogr reflog = new RefmoLogr("RuleStoreManager create Rule");
		reflog.start();
		
		GDSRule new_gdsrule = new DatastoreManager().create(ruleRootElement, subject, action, resource, decision);
		
		
		
		reflog.stop();
	//	log.info(new_gdsrule.toString());
		
		
		return false;
	}

	@Override
	public boolean update(AttributeTreeElement ruleRootElement,
			Subject subject, Action action, Resource resource,
			AttributeTreeElement newRuleRootElement, Subject newSubject,
			Action newAction, Resource newResource) {

		DatastoreManager rsmns = new DatastoreManager();
	
		RefmoLogr reflog = new RefmoLogr("RuleStoreManager update Rule");
		reflog.start();
	
		rsmns.update(newRuleRootElement, newSubject, newAction, newResource, rsmns.find(ruleRootElement, subject, action, resource));
		
		reflog.stop();
		return false;
	}

	@Override
	public boolean delete(AttributeTreeElement ruleRootElement,
			Subject subject, Action action, Resource resource) {
//		
//		RuleStoreManagerNoSql rsmns = new RuleStoreManagerNoSql();
//		rsmns.deleteByObject(rsmns.find(ruleRootElement, subject, action, resource));
//		
//		
		DatastoreManager rsmns = new DatastoreManager();
		
		RefmoLogr reflog = new RefmoLogr("RuleStoreManager delete Rule");
		reflog.start();
		
		rsmns.deleteByKey(rsmns.findRuleKeys(ruleRootElement, subject, action, resource));
	
		reflog.start();
		
		return false;
	}

	@Override
	public List<Rule> findAll() {
		
		List<Rule> zack= parseGDSToPolicLang(new DatastoreManager().findAll());
		return zack;
	}

	@Override
	public List<Rule> find(AttributeTreeElement ruleRootElement,
			Subject subject, Action action, Resource resource) {
		return parseGDSToPolicLang(new DatastoreManager().find(ruleRootElement, subject, action, resource));
	}
	
	public List<Rule> parseGDSToPolicLang(List<GDSRule> all_gdsrules) {
		
		RefmoLogr reflog = new RefmoLogr("RuleStoreManager find Rule");
		reflog.start();
		
		List<Rule> par_list = new ArrayList<Rule>();

		for (GDSRule gdsRule : all_gdsrules) {
			
			par_list.add(parseGDSRuleToRule(gdsRule));
		}
		
		reflog.stop();
		return par_list;
		
	}
	
	public Rule parseGDSRuleToRule(GDSRule p_gds_rule){
		
		
	if(p_gds_rule != null){
		
		/**
		 *  Descision
		 */
		
	Decision new_decision = null;
	
	if(p_gds_rule.getDecision() != null){
	
	switch (p_gds_rule.getDecision()) {
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
	
		Action action = null;
		if( p_gds_rule.getCategory_action() != null){
			
			action = new Action(this.mapGDSTermToAttributeTreeElement(p_gds_rule.getCategory_action()));
			
		}
		Resource resource = null;
		if( p_gds_rule.getCategory_resource() != null){
			
			resource = new Resource(this.mapGDSTermToAttributeTreeElement(p_gds_rule.getCategory_resource()));
			
		}
		AttributeTreeElement rule_prio = null;
		if( p_gds_rule.getCategory_rule_priority() != null){
			
			rule_prio = this.mapGDSTermToAttributeTreeElement(p_gds_rule.getCategory_rule_priority());
			
		}
		Subject subject =null;
		if( p_gds_rule.getCategory_subject() != null){
		
			subject = new Subject(this.mapGDSTermToAttributeTreeElement( p_gds_rule.getCategory_subject()));
		}
	
		return new Rule(rule_prio, subject, action, resource, new_decision);
	}
	else { return null;}
	
	}

	public AttributeTreeElement mapGDSTermToAttributeTreeElement(
			GDSTerm gdst) {
		
		AttributeTreeElement rate = null;
		
		if(gdst != null){
			
			
			if( gdst instanceof GDSTerm_Condition){
				
				Comparision new_comp = null;
				
				if(((GDSTerm_Condition) gdst).getComparision() != null){
				
				switch (((GDSTerm_Condition) gdst).getComparision() ) {
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
				
				Condition new_cond = new Condition(((GDSTerm_Condition) gdst).getName(), new_comp, ((GDSTerm_Condition) gdst).getValue());
				rate = new_cond;
				
			}
			else if( gdst instanceof GDSTerm_Conjunction){
				
				Function new_function = null;
				
				if(((GDSTerm_Conjunction) gdst).getFunction() != null){
				switch (((GDSTerm_Conjunction) gdst).getFunction()) {
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
				
				List<AttributeTreeElement> childs = new ArrayList<AttributeTreeElement>();
				
				
				for (GDSTerm_Condition gdst_cond : ((GDSTerm_Conjunction) gdst).getConditions()) {
				
					childs.add(this.mapGDSTermToAttributeTreeElement(gdst_cond));
				}
				
				for (GDSTerm_Conjunction gdst_conj : ((GDSTerm_Conjunction) gdst).getConjunctions()) {
					
					childs.add(this.mapGDSTermToAttributeTreeElement(gdst_conj));
				}
				
				Conjunction new_conjunction = new Conjunction(new_function, childs.toArray(new AttributeTreeElement[childs.size()]));
				
				rate = new_conjunction;
				
			}
		}
		
		return rate;
	}

	public void deleteAll() {
		
		new DatastoreManager().deleteAll();
		
	}

	public int count() {
		return new DatastoreManager().count();
	
		
	}
}