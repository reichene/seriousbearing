package edu.hfu.rest.action.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown=true)  
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class MaintainRequestConjunctionTerm extends MaintainRequestBasisTerm {

	
	public MaintainRequestConjunctionTerm(String fun,
			MaintainRequestBasisTerm[] mrbt) {
		super(fun, mrbt);
		// TODO Auto-generated constructor stub
	}

	String function;
    private List<MaintainRequestBasisTerm> subTerms = new ArrayList<MaintainRequestBasisTerm>();

    
    
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
