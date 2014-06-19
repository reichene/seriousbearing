package edu.hfu.rest.action.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)  
@XmlRootElement
public  class MaintainRequestBasisTerm {

	private String name;
    private String comparision;
    private String value;

    private String type;
    private String function;
    private List<MaintainRequestBasisTerm> subTerms = new ArrayList<MaintainRequestBasisTerm>();
    
    
    public MaintainRequestBasisTerm(){
    	
    }
    public MaintainRequestBasisTerm(String fun){
    	this.type = "CONJUNCTION";
    	this.function = fun;
    	
    }
    
    public MaintainRequestBasisTerm(String fun, MaintainRequestBasisTerm...mrbt){
    	this.type = "CONJUNCTION";
     	this.function = fun;
    	this.subTerms.addAll(Arrays.asList(mrbt));
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MaintainRequestBasisTerm(String nam, String com, String val){
    	
    	this.type = "CONDITION";
    	this.name = nam;
        this.comparision = com;
        this.value = val;
    	
    }

    public String getName() {
  		return name;
  	}
  	public String getComparision() {
  		return comparision;
  	}
  	public String getValue() {
  		return value;
  	}
  	public void setName(String name) {
  		this.name = name;
  	}
  	public void setComparision(String comparision) {
  		this.comparision = comparision;
  	}
  	public void setValue(String value) {
  		this.value = value;
  	}
  	
  	 public String getFunction() {
 		return function;
 	}

 	public List<MaintainRequestBasisTerm> getSubTerms() {
 		return subTerms;
 	}
 	public void setFunction(String function) {
 		this.function = function;
 	}
 	
 	public void setSubTerm(MaintainRequestBasisTerm mrbt){
 		if(mrbt!= null){
 		this.subTerms.add(mrbt);
 		}
 	}
 	
 	public void setSubTerms(List<MaintainRequestBasisTerm> subTerms) {
 		this.subTerms = subTerms;
 	}
}
