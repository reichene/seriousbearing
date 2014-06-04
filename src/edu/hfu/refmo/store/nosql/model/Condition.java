package edu.hfu.refmo.store.nosql.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Condition {
	  public Condition(String p_license, int p_color) {
		  license =  p_license;
		  p_color = p_color;
		  
	}
	  @Id public Long id;
	   @Index public String license;
	   public int color;
	  public Kind kind;
}
