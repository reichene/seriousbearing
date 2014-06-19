package edu.hfu.rest.action.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

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


@JsonIgnoreProperties(ignoreUnknown=true)  
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class MaintainRequest {
	
	
	String effect;
	String description;
	
	MaintainRequestBasisTerm subject_term;
	MaintainRequestBasisTerm resource_term;
	MaintainRequestBasisTerm action_term;
	MaintainRequestBasisTerm rule_term;

	MaintainRequestBasisTerm new_subject_term;
	MaintainRequestBasisTerm new_resource_term;
	MaintainRequestBasisTerm new_action_term;
	MaintainRequestBasisTerm new_rule_term;
	
	
	public String getEffect() {
		return effect;
	}
	public String getDescription() {
		return description;
	}
	public MaintainRequestBasisTerm getSubject_term() {
		return subject_term;
	}
	public MaintainRequestBasisTerm getResource_term() {
		return resource_term;
	}
	public MaintainRequestBasisTerm getAction_term() {
		return action_term;
	}
	public MaintainRequestBasisTerm getRule_term() {
		return rule_term;
	}
	public MaintainRequestBasisTerm getNew_subject_term() {
		return new_subject_term;
	}
	public MaintainRequestBasisTerm getNew_resource_term() {
		return new_resource_term;
	}
	public MaintainRequestBasisTerm getNew_action_term() {
		return new_action_term;
	}
	public MaintainRequestBasisTerm getNew_rule_term() {
		return new_rule_term;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setSubject_term(MaintainRequestBasisTerm subject_term) {
		this.subject_term = subject_term;
	}
	public void setResource_term(MaintainRequestBasisTerm resource_term) {
		this.resource_term = resource_term;
	}
	public void setAction_term(MaintainRequestBasisTerm action_term) {
		this.action_term = action_term;
	}
	public void setRule_term(MaintainRequestBasisTerm rule_term) {
		this.rule_term = rule_term;
	}
	public void setNew_subject_term(MaintainRequestBasisTerm new_subject_term) {
		this.new_subject_term = new_subject_term;
	}
	public void setNew_resource_term(MaintainRequestBasisTerm new_resource_term) {
		this.new_resource_term = new_resource_term;
	}
	public void setNew_action_term(MaintainRequestBasisTerm new_action_term) {
		this.new_action_term = new_action_term;
	}
	public void setNew_rule_term(MaintainRequestBasisTerm new_rule_term) {
		this.new_rule_term = new_rule_term;
	}
	  @JsonIgnore
	public Rule getRule() {
		return generateRuleFromJSONInput(subject_term, resource_term, action_term, rule_term);
	}
	  @JsonIgnore
	public Rule getRuleUpdate() {
	
		return generateRuleFromJSONInput(new_subject_term, new_resource_term, new_action_term, new_rule_term);
	}
	
	private Rule generateRuleFromJSONInput(	MaintainRequestBasisTerm subject_term,	MaintainRequestBasisTerm resource_term,
	MaintainRequestBasisTerm action_term,	MaintainRequestBasisTerm rule_term){
		
		
		
		/**
		 *  Action
		 */
		Action new_action = null;
		
			if(action_term != null){
				new_action = new Action(mapInternalTermToAttributeTreeElement(action_term));
			}
		
		/**
		 *  Subject
		 */

		Subject new_subject = null;
		
			if(subject_term != null){
				new_subject = new Subject(mapInternalTermToAttributeTreeElement(subject_term));
			}
		
		
		/**
		 *  Resource
		 */
		Resource new_resource = null;
		
			if(resource_term != null){
				new_resource = new Resource(mapInternalTermToAttributeTreeElement(resource_term));
			}
		
		
		
		
		/**
		 *  Rule_Priority
		 */
		
		AttributeTreeElement new_rule_priority = null;
	
			if(rule_term != null){
				new_rule_priority = mapInternalTermToAttributeTreeElement(rule_term);
			}
		
	
		
		
		
		
		/**
		 *  Rule
		 * 
		 */
			
			/**
			 *  Descision
			 */
			Decision new_decision = null;
		if (this.effect != null){	
		
		
		switch (this.effect) {
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
			new_decision = Decision.UNDECIDABLE;
			
		}

		return new Rule(new_rule_priority, new_subject, new_action, new_resource, new_decision);
		
		
		
		
		
	}
	private AttributeTreeElement mapInternalTermToAttributeTreeElement(
			MaintainRequestBasisTerm rule_term) {


	AttributeTreeElement r_ate = null;
		
		if(rule_term !=null){
			
		//	if(rule_term instanceof MaintainRequestConditionTerm)	{
			if(rule_term.getType().equals("CONDITION"))	{
				
				Comparision new_comp = null;
				
				switch (rule_term.getComparision()) {
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
				
				r_ate= new Condition(rule_term.getName(), new_comp, rule_term.getValue());
			}
			//else if (rule_term  instanceof MaintainRequestConjunctionTerm){
			else if (rule_term.getType().equals("CONJUNCTION")){
				
				Function new_function = null;
				
				switch (rule_term.getFunction()) {
				case "AND":
					new_function = Function.AND;
					break;
				case "OR":
					new_function = Function.OR;
					break;
				default:
					new_function = Function.OR;
					break;
		
				}
				
				AttributeTreeElement ate_elements[] = new AttributeTreeElement[rule_term.getSubTerms().size()];
				int cnt_ate = 0;
													
				for (MaintainRequestBasisTerm i_term : rule_term.getSubTerms()) {
					
					AttributeTreeElement new_ate_child = mapInternalTermToAttributeTreeElement(i_term);
					if(new_ate_child != null){
						
						ate_elements[cnt_ate++] = (new_ate_child);
						
					}
					
				}
				
				r_ate = new Conjunction(new_function, ate_elements);
			}

		}
		
		return r_ate ;
		
		
	
	}
	

	
	public String toString(){
		
		
		String rstring = "";
		rstring = "Effect: " + this.effect;
		return rstring;
	}
	
}
