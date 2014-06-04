 package edu.hfu.refmo.store.sql.manager;

import java.util.ArrayList;
import java.util.List;

import de.abacs.base.entity.Action;
import de.abacs.base.entity.Attribute;
import de.abacs.base.entity.AttributeImpl;
import de.abacs.base.entity.Decision;
import de.abacs.base.entity.Resource;
import de.abacs.base.entity.Rule;
import de.abacs.base.entity.Subject;
import de.abacs.base.store.RuleStore;
import edu.hfu.refmo.store.sql.model.basic.DBAction;
import edu.hfu.refmo.store.sql.model.basic.DBMatch;
import edu.hfu.refmo.store.sql.model.basic.DBResource;
import edu.hfu.refmo.store.sql.model.basic.DBRule;
import edu.hfu.refmo.store.sql.model.basic.DBSubject;
import edu.hfu.refmo.store.sql.model.basic.RuleManager;

public class RuleStoreManagerSqlDAO implements RuleStore {

	@Override
	public boolean create(List<Attribute> p_rule_att, Subject p_subject, Action p_action,
			Resource p_resource, Decision p_decision) {
		
		
		DBRule new_dbrule =  parseInDBStructure(p_rule_att, p_subject, p_action,
				p_resource, new DBRule() );
		new_dbrule.setDescription(p_decision.toString());
		
		new_dbrule = new RuleManager().create(new_dbrule);
		
		return false;
	}

	@Override
	public boolean delete(List<Attribute> p_rule_att, Subject p_subject, Action p_action,
			Resource p_resource) {
		
		
		List<DBRule> dbrules = new RuleManager().delete(
				parseInDBStructure(p_rule_att, p_subject, p_action,
						p_resource, new DBRule() 
				));
		
		if (parseDBListInPolicyList(dbrules).size() != 0) return true;	
				return false;
	}

	@Override
	public List<Rule> find(List<Attribute> p_rule_att, Subject p_subject, Action p_action,
			Resource p_resource) {
			
		return parseDBListInPolicyList(new RuleManager().find(
			   parseInDBStructure(p_rule_att, p_subject, p_action,
						p_resource, new DBRule()
				)));
		
		
	}

	@Override
	public List<Rule> findAll() {
	

		return parseDBListInPolicyList(new RuleManager().findAll());
	}

	@Override
	public boolean update(List<Attribute> p_rule_att, Subject p_subject, Action p_action,
			Resource p_resource, List<Attribute> p_new_rule_att, Subject p_new_subject, Action p_new_action,
			Resource p_new_resource) {
		
		
		parseDBListInPolicyList(new RuleManager().update(
				parseInDBStructure(p_rule_att, p_subject, p_action,	p_resource, new DBRule() ), 
				parseInDBStructure(p_new_rule_att, p_new_subject, p_new_action, p_new_resource, new DBRule() )
				));
		return false;
	}
	
	
	private List<Rule> parseDBListInPolicyList(List<DBRule> p_dbrules){
	
		List<Rule> rules = new ArrayList<Rule>();
		
		for (DBRule i_dbrule : p_dbrules) {
			
			
			rules.add(parseDBInPolicyLanguage(i_dbrule));	
		}
		
		return rules;
		
		
		
	}
	
	private Rule parseDBInPolicyLanguage(DBRule p_dbrule){
		
					
		return new Rule(parseDBMatchesToAttribute(p_dbrule.getPriority_matches()),
				new Subject(parseDBMatchesToAttribute( p_dbrule.getSubject().getMatch_terms())),
				new Action(parseDBMatchesToAttribute( p_dbrule.getAction().getMatch_terms())),
				new Resource(parseDBMatchesToAttribute( p_dbrule.getResource().getMatch_terms())),
			    Decision.PERMIT);
		
		
	}
	
	

	
	private DBRule parseInDBStructure(List<Attribute> p_rule_att, Subject p_subject, Action p_action,
			Resource p_resource, DBRule p_rule ){
		    
			DBRule cur_rule = null;
		
			// DBRule object parameter set?
		    if(p_rule == null){
				
				cur_rule = new DBRule();
			}
			
		    // if set take object and update fields;
			else {
				cur_rule = p_rule;
				
			}
		    
		    
		    // Rule -----------------------------------------
		    
		
		    
		    // Attributes
		    // ----------------------------------------------
		    
		    cur_rule.setPriority_matches(parseAttributesToDBMatches(p_rule_att));
		    
		    
		    // Subject Attributes
		    // ----------------------------------------------
		   
		   if(p_subject != null){
			   
			   DBSubject new_subject = new DBSubject();
			   new_subject.setMatch_terms(parseAttributesToDBMatches(p_subject.getAttributes()));
			   
		   }
		    
		    
		    // Action Attributes
		    // ----------------------------------------------
	  
		   if(p_action != null){
			   
			   DBAction new_action = new DBAction(); 
			   new_action.setMatch_terms(parseAttributesToDBMatches(p_action.getAttributes()));
		   }
		    
		    
		    // Resource Attributes
		    // ----------------------------------------------
		   if(p_resource != null){
			   
			   DBResource new_resource = new DBResource();
			   new_resource.setMatch_terms(parseAttributesToDBMatches(p_resource.getAttributes()));
			   
		   }
		   
		    return cur_rule;
		
		
	}
	
	
	private List<Attribute> parseDBMatchesToAttribute(List<DBMatch> p_dbmatches ){
		
		List<Attribute> attributes = new ArrayList<Attribute>();
		
		for (DBMatch db_match : p_dbmatches) {

			
			 attributes.add(new AttributeImpl(db_match.getAttribute_name(), db_match.getAttribute_value()));
		}
		
		
		return attributes;
		
	}
	
	private List<DBMatch> parseAttributesToDBMatches( List<Attribute> p_attributes ){
		
		List<DBMatch> match_terms = new ArrayList<DBMatch>();
		
		for (Attribute i_att : p_attributes) {
			
			i_att.getId();
			i_att.getLink();
			i_att.getName();
			i_att.getValue();
			
		  match_terms.add(new DBMatch(i_att.getName(), i_att.getValue(),"dataType"));
	
		 
		}
			
				
		return match_terms;
		
		
	}

}
