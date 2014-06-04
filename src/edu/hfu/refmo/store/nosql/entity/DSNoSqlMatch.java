package edu.hfu.refmo.store.nosql.entity;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class DSNoSqlMatch {
	
	
	@Id Long id;
	
	
	String attribute_name;
	String attribute_value;
	String attribute_datatype;
	String attribute_designator;
	
	
	
	
	public DSNoSqlMatch(String attribute_name, String attribute_value,
			String attribute_datatype, String attribute_designator) {
		
		this.attribute_name = attribute_name;
		this.attribute_value = attribute_value;
		this.attribute_datatype = attribute_datatype;
		this.attribute_designator = attribute_designator;
	}

	public DSNoSqlMatch() {
	
	}

	public String getAttribute_name() {
		return attribute_name;
	}

	public String getAttribute_value() {
		return attribute_value;
	}

	public String getAttribute_datatype() {
		return attribute_datatype;
	}

	public String getAttribute_designator() {
		return attribute_designator;
	}

	public void setAttribute_name(String attribute_name) {
		this.attribute_name = attribute_name;
	}

	public void setAttribute_value(String attribute_value) {
		this.attribute_value = attribute_value;
	}

	public void setAttribute_datatype(String attribute_datatype) {
		this.attribute_datatype = attribute_datatype;
	}

	public void setAttribute_designator(String attribute_designator) {
		this.attribute_designator = attribute_designator;
	}

	@Load @Parent Ref<DSNoSqlRule> rule;
	
	

	public DSNoSqlRule getRule() {
		return rule.get();
	}
	
	public void setRule(DSNoSqlRule rule) {
		this.rule = Ref.create(rule);
	}
	
	

}
