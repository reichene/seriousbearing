package edu.hfu.rest.action.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import de.abacs.base.entity.Decision;


@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class RefmoResponse {

	public List<String> warning = new ArrayList<String>();
	public Decision decision;
 	
	public RefmoResponse(){
		
		
	}
	public RefmoResponse(StackTraceElement[] stackTraceElements, String string, String string2, String string3, String string4) {

		
		for (StackTraceElement stackTraceElement : stackTraceElements) {
			
			warning.add( stackTraceElement.toString());
		}
		
		warning.add(string);
		warning.add(string2);
		warning.add(string3);
		warning.add(string4);
		
	}
	public List<String> getWarning() {
		return warning;
	}
	public void setWarning(List<String> warning) {
		this.warning = warning;
	}
	public void setDecision(Decision decision) {
		this.decision = decision;
	}
	public RefmoResponse(Decision finalDecision) {
		
	decision = finalDecision;
		
	}
	public RefmoResponse(String string) {
		warning.add( string);
	}
	public Decision getDecision() {
		return decision;
		
	}

}
