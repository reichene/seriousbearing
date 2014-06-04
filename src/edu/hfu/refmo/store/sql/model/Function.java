package edu.hfu.refmo.store.sql.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "function" )
public class Function {
	
	@Id
	private Integer idFunction;
	private String  description;
	private String  javaInterface;

}
