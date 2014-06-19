package edu.hfu.rest.action.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown=true)  
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class MaintainRequestConditionTerm extends MaintainRequestBasisTerm {

    public MaintainRequestConditionTerm(String fun,
			MaintainRequestBasisTerm[] mrbt) {
		super(fun, mrbt);
		// TODO Auto-generated constructor stub
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
	private String name;
    private String comparision;
    private String value;



}
