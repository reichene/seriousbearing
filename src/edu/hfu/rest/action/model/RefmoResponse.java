package edu.hfu.rest.action.model;

import de.abacs.base.entity.Decision;

public class RefmoResponse {

	public String[] warning;
	public Decision decision;
 	
	public RefmoResponse(){
		
		
	}
	public RefmoResponse(StackTraceElement[] stackTraceElements) {

		int i = 0;
		for (StackTraceElement stackTraceElement : stackTraceElements) {
			
			warning[i++] = stackTraceElement.toString();
		}
		
	}
	public RefmoResponse(Decision finalDecision) {
		
	decision = finalDecision;
		
	}

}
