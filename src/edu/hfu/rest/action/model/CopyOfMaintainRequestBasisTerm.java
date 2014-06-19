package edu.hfu.rest.action.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown=true)  
@XmlAccessorType(XmlAccessType.FIELD)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = MaintainRequestConditionTerm.class, name="Condition"), @Type(value = MaintainRequestConjunctionTerm.class, name="Conjunction")}) 
@XmlRootElement
public abstract class CopyOfMaintainRequestBasisTerm {

	



}
