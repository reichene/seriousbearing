package edu.hfu.refmo.store.nosql.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Subclass;

import edu.hfu.refmo.fi.model.Function;
import edu.hfu.refmo.store.sql.model.MatchGroup;


@Subclass
public class NoSqlMatchSingle extends NoSqlMatch {
	

	public String attributeName;
	
	public String attributeValueDataType;
	public String attributeValue;
		public Function function;
	public String functionType;
	public String functionClass;
	
	public String attributeDesignator;
	public String attributeDesignatorDataType;
	
	
	
	}

	

