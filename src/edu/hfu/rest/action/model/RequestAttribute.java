package edu.hfu.rest.action.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import de.abacs.base.entity.Attribute;
import de.abacs.base.entity.Link;

@JsonIgnoreProperties(ignoreUnknown=true)  
@XmlRootElement
public class RequestAttribute implements Attribute {

	
    public int id;
    public String name;
    public String value;
    public String dataType;
 
    
    public RequestAttribute(){
    	
    	
    }
    public RequestAttribute(String name, String value){
    	this.name = name;
    	this.value = value;
    	
    }
    
 //   public Link link;
    
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Link getLink() {
		return null;
	}
	public void setLink(Link link) {
		//this.link = null;
	}
    


}
