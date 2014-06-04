package edu.hfu.refmo.store.nosql.entity;

import com.googlecode.objectify.annotation.Subclass;



@Subclass(index=true)
public class DSNoSqlMatch_Action extends  DSNoSqlMatch{

	public DSNoSqlMatch_Action() {
		super();

	}

	public DSNoSqlMatch_Action(String attribute_name, String attribute_value,
			String attribute_datatype, String attribute_designator) {
		super(attribute_name, attribute_value, attribute_datatype, attribute_designator);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
