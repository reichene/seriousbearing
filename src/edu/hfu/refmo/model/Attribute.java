package edu.hfu.refmo.model;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement

public class Attribute {
	
public Attribute(){
		
	
	}

public Attribute(String attributeId){
	
	this.attributeId = attributeId;

}


	public Attribute(String attributeId, String dataType,  boolean includeInResult , String attributeValue){
		
		this.attributeId = attributeId;
		this.dataType = dataType;
		this.attributeValue = attributeValue;
		this.includeInResult = includeInResult;
		
	}
	
	
	public String attributeId;
	public String attributeValue;
	public String dataType;
	public boolean includeInResult;

}
