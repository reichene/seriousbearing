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
	public RefmoResponse(StackTraceElement[] stackTraceElements) {

		
//		for (StackTraceElement stackTraceElement : stackTraceElements) {
//			
//			warning.add( stackTraceElement.toString());
//		}
		
	}
	public RefmoResponse(Decision finalDecision) {
		
	decision = finalDecision;
		
	}
	public Decision getDecision() {
		return decision;
		
	}

}
