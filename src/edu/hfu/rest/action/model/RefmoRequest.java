package edu.hfu.rest.action.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import de.abacs.base.entity.Action;
import de.abacs.base.entity.AttributeTreeElement;
import de.abacs.base.entity.Condition;
import de.abacs.base.entity.Condition.Comparision;
import de.abacs.base.entity.Conjunction;
import de.abacs.base.entity.Conjunction.Function;
import de.abacs.base.entity.Resource;
import de.abacs.base.entity.Rule;
import de.abacs.base.entity.Subject;

@XmlRootElement

@JsonIgnoreProperties(ignoreUnknown = true)
public class RefmoRequest {
	
	
	private List<RequestAttribute> action_attributes = new ArrayList<RequestAttribute>();
	
	private List<RequestAttribute> subject_attributes = new ArrayList<RequestAttribute>();
	
	private List<RequestAttribute> resource_attributes = new ArrayList<RequestAttribute>();
	
	private List<RequestAttribute> rule_attributes = new ArrayList<RequestAttribute>(); 

	
	
	private AttributeTreeElement  mapRequestAttributesToCondition(List<RequestAttribute> p_request_attributes){
	
		AttributeTreeElement r_ate = null;

			
			List<AttributeTreeElement> atel = new ArrayList<AttributeTreeElement>();
			//AttributeTreeElement ate_elements[] = new AttributeTreeElement[p_request_attributes.size()];
			for (RequestAttribute requestAttribute : p_request_attributes) {
				
				if(requestAttribute.getName() != null && requestAttribute.getName() != ""){
				atel.add(new Condition(requestAttribute.getName(), Comparision.EQUAL, requestAttribute.getValue()));
				//atel.add(new Condition(Comparision.EQUAL, requestAttribute.getName(),  requestAttribute.getValue()));
				}
				
			}
			
			
			if(atel.size() == 1){
			r_ate = atel.get(0);
			}
			else{
				r_ate = new Conjunction(Function.AND,  atel.toArray(new AttributeTreeElement[atel.size()]));
			}
			
		
		
		
		return r_ate ;		
	}
	
	public Rule getRule(){
		
		
		Subject new_subject = null;
		Resource new_resource = null;
		Action new_action = null;
		
		AttributeTreeElement rule_priority = null;
		
		if(action_attributes != null ){
			if(action_attributes.size() != 0){
				
				
				new_action = new Action(mapRequestAttributesToCondition(action_attributes));
						
				
			}
		}
		
		if(subject_attributes != null ){
			if(subject_attributes.size() != 0){
				
				
				new_subject = new Subject(mapRequestAttributesToCondition(subject_attributes));
						
				
			}
		}
		
		if(resource_attributes != null ){
			if(resource_attributes.size() != 0){
				
				
				new_resource = new Resource(mapRequestAttributesToCondition(resource_attributes));
						
				
			}
		}
		
		
		if(rule_attributes != null ){
			if(rule_attributes.size() != 0){
				
				
				rule_priority = mapRequestAttributesToCondition(rule_attributes);
						
				
			}
		}
		
		
		
		return new Rule(rule_priority, new_subject, new_action, new_resource, null);
	}


	public List<RequestAttribute> getAction_attributes() {
		return action_attributes;
	}

	public List<RequestAttribute> getSubject_attributes() {
		return subject_attributes;
	}

	public List<RequestAttribute> getResource_attributes() {
		return resource_attributes;
	}

	public List<RequestAttribute> getRule_attributes() {
		return rule_attributes;
	}

	

	public void setAction_attributes(List<RequestAttribute> action_attributes) {
		this.action_attributes = action_attributes;
	}

	public void setSubject_attributes(List<RequestAttribute> subject_attributes) {
		this.subject_attributes = subject_attributes;
	}

	public void setResource_attributes(List<RequestAttribute> resource_attributes) {
		this.resource_attributes = resource_attributes;
	}

	public void setRule_attributes(List<RequestAttribute> rule_attributes) {
		this.rule_attributes = rule_attributes;
	}

	
	public String toString(){
		
		
		String rstring = "";
		rstring = "Action-attributes: " + this.action_attributes.toString();
		return rstring;
	}


	

	
	

}
