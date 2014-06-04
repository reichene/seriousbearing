package edu.hfu.rest.action.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import de.abacs.base.entity.Attribute;
import de.abacs.base.entity.Decision;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefmoRequest {

	public String ruleDescription;
	
	public String descision_string;
	public Decision descision;
	public List<RequestAttribute> action_attributes = new ArrayList();
	public List<RequestAttribute> subject_attributes = new ArrayList();
	public List<RequestAttribute> resource_attributes = new ArrayList();
	public List<RequestAttribute> rule_attributes = new ArrayList();

	public List<RequestAttribute> newRuleAttributes;
	public List<RequestAttribute> newSubjectAttributes;
	public List<RequestAttribute> newActionAttributes;
	public List<RequestAttribute> newResourceAttributes;

	public List<Attribute> getRuleAttributes() {

		return (List<Attribute>) (List) rule_attributes;
	}

	public List<Attribute> getSubjectAttributes() {

		return (List<Attribute>) (List) subject_attributes;
	}

	public List<Attribute> getActionAttributes() {
		// TODO Auto-generated method stub
		return (List<Attribute>) (List) action_attributes;
	}

	public List<Attribute> getResourceAttributes() {
		return (List<Attribute>) (List) resource_attributes;
	}

	public Decision getDescision() {
		return descision;
	}

	public List<Attribute> getNewRuleAttributes() {
		return (List<Attribute>) (List)newRuleAttributes;
	}

	public List<Attribute> getNewSubjectAttributes() {
		return (List<Attribute>) (List)newSubjectAttributes;
	}

	public List<Attribute> getNewActionAttributes() {
		return (List<Attribute>) (List)newActionAttributes;
	}

	public List<Attribute> getNewResourceAttributes() {
		return (List<Attribute>) (List)newResourceAttributes;
	}
	
	

}
