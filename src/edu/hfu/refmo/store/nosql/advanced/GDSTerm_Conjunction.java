package edu.hfu.refmo.store.nosql.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.appengine.api.datastore.Key;

public  class GDSTerm_Conjunction extends GDSTerm {
	

	String function;
    List<GDSTerm_Condition> conditions = new ArrayList<GDSTerm_Condition>();
	List<GDSTerm_Conjunction> conjunctions = new ArrayList<GDSTerm_Conjunction>();  
	
	
	public Set<Key> delete(Set<Key> rule_keys) {
		
		if(this.term_key != null){
			rule_keys.add(this.term_key);
			
		}
		
		for (GDSTerm_Condition cond : conditions) {
			rule_keys = cond.delete(rule_keys);
		}
		
		
		for (GDSTerm_Conjunction conj : conjunctions) {
			
			rule_keys = conj.delete(rule_keys);
			
		}
		
		return rule_keys;
	}

	public GDSTerm_Conjunction(String cat, GDSRule gdsRule,
			GDSTerm parent_term) {
		super(cat, gdsRule, parent_term);
	}

	public GDSTerm_Conjunction(Key term_key) {
		super(term_key);
	}

	public GDSTerm_Conjunction() {
		super();
	}

	public String getFunction() {
		return function;
	}
	public List<GDSTerm_Condition> getConditions() {
		return conditions;
	}
	public List<GDSTerm_Conjunction> getConjunctions() {
		return conjunctions;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public void setConditions(List<GDSTerm_Condition> conditions) {
		this.conditions = conditions;
	}
	public void setConjunctions(List<GDSTerm_Conjunction> conjunctions) {
		this.conjunctions = conjunctions;
	}

	
	
	public void addGDSTermCondition(GDSTerm_Condition gds_new_term) {
		
		this.conditions.add(gds_new_term);
		
	}
	public void addGDSTermConjunction(GDSTerm_Conjunction gds_new_term) {
		
		this.conjunctions.add(gds_new_term);
		
	}
	public void persistConditions() {
		
		new GDSEntityManager().persistCondition( this.conditions.toArray(new GDSTerm_Condition[this.conditions.size()]));
	}

	public void persist() {

		if(this.term_key != null){
			
			new GDSEntityManager().updateConjunction(this);
			
		}
		else{
			
			new GDSEntityManager().persistNewConjunction(this);
		}
		
	}

	public GDSTerm getPersisted() {
		
		if(this.term_key == null){
			this.persist();
		}
		
		return this;
	}
	
	public String toString(){
		
		String as = " CONJUNCTION ---------------";
		
		as+= this.function;
		as+=this.term_key;
		
		for (GDSTerm_Condition ie : this.conditions) {
			as+=ie.toString() + "\n";
			
		}
		for (GDSTerm_Conjunction ie : this.conjunctions) {
			as+=ie.toString()+ "\n";
			
		}
		
		return as;
	}
	
}
