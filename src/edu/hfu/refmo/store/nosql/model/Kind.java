package edu.hfu.refmo.store.nosql.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;



public class Kind {

	public Long id;
	public String name =  this.getClass().getSimpleName();
	public String vorname;
	public Integer alter;
	
//	public Eltern eltern;
	
}
