package edu.hfu.refmo.store.nosql.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Subclass;

import edu.hfu.refmo.fi.model.FunctionLog;
import edu.hfu.refmo.fi.store.Match;





//public class NoSqlMatchGroup extends NoSqlMatch {
public class NoSqlMatchGroup extends NoSqlMatch{
	

	
	public NoSqlMatch match_basis;
	public NoSqlMatch match_comp;

	public String functionType;
	public String functionClass;
	public FunctionLog function;

	




}
