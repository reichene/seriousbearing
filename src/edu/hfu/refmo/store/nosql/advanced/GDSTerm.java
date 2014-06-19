package edu.hfu.refmo.store.nosql.advanced;

import java.util.Set;

import com.google.appengine.api.datastore.Key;

public class GDSTerm {
	
	String category;
    String discriminatr;
	Key term_key;
	GDSTerm parent_term;
	GDSRule parent_rule;
	
	  public enum TermPara {
	        NAME, VALUE, COMPARISION, FUNCTION, DISCRIMINATOR, CATEGORY, TERM_KEY, PARENT_TERM, PARENT_RULE
	    }
	  
	
	
	
	    
	public GDSTerm(String category, GDSRule gdsRule,
			GDSTerm parent_term) {

		this.category = category;
		this.parent_rule = gdsRule;
		this.parent_term = parent_term;
		
	}
	
	
	  public GDSTerm(Key term_key) {

		  this.term_key = term_key;
	}


	public GDSTerm() {
		// TODO Auto-generated constructor stub
	}


	public Key getTerm_key() {
		return term_key;
	}
	public String getCategory() {
		return category;
	}
	public String getDiscriminatr() {
		return discriminatr;
	}
	public GDSTerm getParent_term() {
		return parent_term;
	}
	public GDSRule getParent_rule() {
		return parent_rule;
	}
	public void setTerm_key(Key term_key) {
		this.term_key = term_key;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setDiscriminatr(String discriminatr) {
		this.discriminatr = discriminatr;
	}
	public void setParent_term(GDSTerm parent_term) {
		this.parent_term = parent_term;
	}



	public void persist() {
		// TODO Auto-generated method stub
		
	}


	public void setParent_rule(GDSRule parent_rule2) {
		this.parent_rule = parent_rule2;
		
	}


	public Set<Key> delete(Set<Key> rule_keys) {
		// TODO Auto-generated method stub
		return null;
	}






	

}
