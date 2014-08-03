package edu.hfu.refmo.store.nosql.advanced;

import java.util.Set;

import com.google.appengine.api.datastore.Key;

import de.abacs.base.entity.AttributeTreeElement;
import de.abacs.base.entity.Condition;
import de.abacs.base.entity.Conjunction;
import de.abacs.base.entity.Decision;

public class GDSRule {
	
//	public GDSRule(	GDSTerm category_action, GDSTerm category_subject, 	GDSTerm category_rule_priority, 
//	GDSTerm category_resource ){
//		
//	}

	Key rule_key;
	
	String decision;
	String description;
	
	GDSTerm category_action;
	GDSTerm category_subject;
	GDSTerm category_rule_priority;
	GDSTerm category_resource;
	  
	
	  public enum RulePara {
	        DECISION, DESCRIPTION
	    }
	  
	public enum DSElements {
	        CONDITION, CONJUNCTION, RULE, TERM
	    }
	
	public GDSRule(){
//		
//		
//		
//		
	}
	
	
	
	public void initByKey(Key ancestorKey){
		
		new GDSEntityManager().getRuleByKey(ancestorKey);
		
	}
	
	public GDSRule(String description, AttributeTreeElement ruleRootElement,
			AttributeTreeElement subject, AttributeTreeElement action, AttributeTreeElement resource, Decision decision){

		
		this.description = description;
		this.decision = decision.toString();		

		this.category_action = this.setRootCatTerm(action, GDSCategory.ACTION.name());
		this.category_resource = this.setRootCatTerm(resource, GDSCategory.RESOURCE.name());
		this.category_rule_priority = this.setRootCatTerm(ruleRootElement, GDSCategory.RULE_PRIORITY.name());
		this.category_subject = this.setRootCatTerm(subject, GDSCategory.SUBJECT.name());
		
		this.persist();
		
	}
	



	private GDSTerm setRootCatTerm(AttributeTreeElement rt, String category) {
	
		GDSTerm regdsterm= null;
		
		if( rt != null){
			
			
			if(rt instanceof Conjunction){
				
				regdsterm = generateGDST(rt, category, this.getPersisted(), null);

			}
			
			else if(rt instanceof Condition){
				
				 regdsterm = generateGDST(rt, category, this.getPersisted(), null);
				 ((GDSTerm_Condition)regdsterm).persist();
			}
			
		}
		
		
		return regdsterm;
	}

	private GDSRule getPersisted() {

		if(this.rule_key == null){
			
			this.persist();
		}
		
		return this;
	}

	private GDSTerm generateGDST(AttributeTreeElement rt, String category,
			GDSRule gdsRule, GDSTerm parent_term) {

		
	GDSTerm regdsterm= null;
		
		if( rt != null){
			
			
			if(rt instanceof Conjunction){
				 
				GDSTerm_Conjunction new_parent_term = new GDSTerm_Conjunction(category, gdsRule, parent_term);		
				((GDSTerm_Conjunction)new_parent_term).setFunction(((Conjunction) rt).getFunction().toString());
				((GDSTerm_Conjunction)new_parent_term).persist();
				
				 
				 for (AttributeTreeElement i_ate : ((Conjunction)rt).getChildren()) {
					
					 
					 GDSTerm gds_new_term = generateGDST(i_ate, category,  gdsRule, new_parent_term.getPersisted());
				
				
					 if( gds_new_term instanceof GDSTerm_Condition){
						 
						 ((GDSTerm_Conjunction)new_parent_term).addGDSTermCondition((GDSTerm_Condition)gds_new_term );
						 
					 }
					 else if(gds_new_term instanceof GDSTerm_Conjunction) {
						 
						 ((GDSTerm_Conjunction)new_parent_term).addGDSTermConjunction((GDSTerm_Conjunction)gds_new_term );
						 gds_new_term.setParent_term(new_parent_term);
						 
					 }					 
				 }
				 
				 ((GDSTerm_Conjunction)new_parent_term).persistConditions();
				 regdsterm = new_parent_term.getPersisted();

			}
			
			else if(rt instanceof Condition){
					
				GDSTerm_Condition new_cond = new GDSTerm_Condition(category, gdsRule, parent_term);
				 new_cond.setValue(((Condition) rt).getValue());
				 new_cond.setName(((Condition) rt).getName());
				 new_cond.setComparision(((Condition) rt).getComparision().toString());
				 
				regdsterm = new_cond;
				// regdsterm.s
			}
			
		}
		
		
		return regdsterm;
		
		
		
	}


	public Key getRule_key() {
		return rule_key;
	}

	public void setRule_key(Key rule_key) {
		this.rule_key = rule_key;
	}

	
	public String getDecision() {
		return decision;
	}

	public String getDescription() {
		return description;
	}

	public GDSTerm getCategory_action() {
		return category_action;
	}

	public GDSTerm getCategory_subject() {
		return category_subject;
	}

	public GDSTerm getCategory_rule_priority() {
		return category_rule_priority;
	}

	public GDSTerm getCategory_resource() {
		return category_resource;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	
	}

	public void setDescription(String description) {
		this.description = description;
	
	}

	public void setCategory_action(GDSTerm action) {
		if(action.getTerm_key() == null) action.setParent_rule(this); action.persist();
		this.category_action = action;
	
	}

	public void setCategory_subject(GDSTerm subject) {
		if(subject.getTerm_key() == null) subject.setParent_rule(this); subject.persist();
		this.category_subject = subject;
	
	}

	public void setCategory_rule_priority(GDSTerm ruleprio) {
		if(ruleprio.getTerm_key() == null) ruleprio.setParent_rule(this); ruleprio.persist();
		this.category_rule_priority = ruleprio;
	
	}

	public void setCategory_resource(GDSTerm resource) {
		if(resource.getTerm_key() == null)  resource.setParent_rule(this);  resource.persist();
		this.category_resource = resource;
	
	}

	public void persist() {
		
		
		if(this.getRule_key() != null)
		{
			
			new GDSEntityManager().updateRule(this);
			
		}
		else{
			
			new GDSEntityManager().persistRule(this);
			
		}
		
	}



	public Set<Key> delete(Set<Key> rule_keys) {
	
		
		if(this.rule_key != null){
			rule_keys.add(this.rule_key);
		}
		
		if(this.category_action != null){
			rule_keys = this.category_action.delete(rule_keys);
		}
		
	if(this.category_resource != null){
		rule_keys = this.category_resource.delete(rule_keys);
		}
	
	if(this.category_rule_priority != null){
		rule_keys = this.category_rule_priority.delete(rule_keys);
	}
	
	if(this.category_subject != null){
		rule_keys = this.category_subject.delete(rule_keys);
	}
	return rule_keys;
		
		
	}
	
	public String toString(){
		
		String as = " RULE BEGIN -------------------\n";
		
		as = as + " ENTIY KEY " + this.rule_key +" ";
		as = as + " ENTIY DECISION " + this.decision +" ";
		as = as + " DESCRIPTION " + this.description + "\n";
		
		
		as = as + "---------- ACTION \n";
		if(this.category_action != null){
		as = as + "  " + this.category_action.toString();}
		
		as = as + "---------- RESOURC \n";
		if(this.category_resource != null){
		as = as + " "+ this.category_resource.toString();
		}
		as = as + "---------- RULEPRIO \n";
		if(this.category_rule_priority!= null){
		as = as + " " + this.category_rule_priority.toString();
		}
		
		as = as + "---------- SUBJECT \n";
		if(this.category_subject != null){
		as = as + "  " + this.category_subject.toString();
		}
		return as;
		
	}

	

}
