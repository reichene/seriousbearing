package edu.hfu.refmo.store.nosql.advanced;

import java.util.Set;

import com.google.appengine.api.datastore.Key;

public class GDSTerm_Condition extends GDSTerm {

	
	String name;
	String value;
	String comparision;
	
	
	public Set<Key> delete(Set<Key> rule_keys) {
		if(this.term_key != null){
			rule_keys.add(this.term_key);
			
		}
		return rule_keys;
	}
	
	public GDSTerm_Condition(String cat, GDSRule gdsRule,
			GDSTerm parent_term) {
		super(cat, gdsRule, parent_term);
	}
	public GDSTerm_Condition() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	public String getComparision() {
		return comparision;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setComparision(String comparision) {
		this.comparision = comparision;
	}
	public void persist() {
		
		if(this.term_key == null){
		new GDSEntityManager().persistCondition(this);
		}
		else{
			
		new GDSEntityManager().updateCondition(this);	
		}
	}
	
	public String toString(){
		
		String as = " CONDITION ------------- \n ";
		
		
		as+=this.comparision +"\n";
		as+=this.name +"\n";
		as+=this.term_key + "\n";
		as+=this.value +"\n";
	
		return as;
	}
	
}
