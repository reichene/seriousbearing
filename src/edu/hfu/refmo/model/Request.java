package edu.hfu.refmo.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Request {
	
	
	public Request() {
		
	
	}
	public Request(String string) {
		
		
		// TODO Auto-generated constructor stub
	}
	public boolean returnPolicyId = false;
	public boolean returnCombinedDecision = false;
	
	public List<Attribute> subject_attributes= new ArrayList();
	public List<Attribute> action_attributes= new ArrayList();
	public List<Attribute> resource_attributes= new ArrayList();
	public List<Attribute> environment_attributes= new ArrayList();
	
	
	

}
