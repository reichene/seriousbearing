package edu.hfu.refmo.store.sql.model.basic;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Match")
public class DBMatch {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer match_id;

	public String attribute_name;
	public String attribute_value;
	public String attribute_datatype;
	public String attribute_designator;
	
	@ManyToOne
	public DBRule rule;
	
	@ManyToOne
	public DBCategory category;
	
	
	
	
	public DBMatch(String attribute_name, String attribute_value,
			 String attribute_designator, String attribute_datatype) {
		super();
		this.attribute_name = attribute_name;
		this.attribute_value = attribute_value;
		this.attribute_datatype = attribute_datatype;
		this.attribute_designator = attribute_designator;
	}

	
	public DBMatch(String attribute_name, String attribute_value,
			String attribute_datatype) {
		super();
		this.attribute_name = attribute_name;
		this.attribute_value = attribute_value;
		this.attribute_datatype = attribute_datatype;
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

	public DBRule getRule() {
		return rule;
	}

	public DBCategory getCategory() {
		return category;
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

	public void setRule(DBRule rule) {
		this.rule = rule;
	}

	public void setCategory(DBCategory category) {
		this.category = category;
	}


	
	
	

	
	
	
   
}
