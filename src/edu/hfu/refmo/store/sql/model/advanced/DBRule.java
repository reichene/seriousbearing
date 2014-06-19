package edu.hfu.refmo.store.sql.model.advanced;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RULE")
public class DBRule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer rule_id;
	private String effect;
	private String description;

	/**
	 * UPDATE:
	 */
	@OneToMany(mappedBy = "rule", cascade = CascadeType.ALL)
	@Basic(fetch = FetchType.EAGER)
	@MapKey(name = "alias")
	Map<String, DBCategory> categories = new HashMap<String, DBCategory>();
	
	/***
	 * 
	 */

//	@OneToOne(mappedBy = "rule", cascade = CascadeType.PERSIST)
//	@Basic(fetch = FetchType.EAGER)
//	private DBAction action;
//
//	@OneToOne(mappedBy = "rule", cascade = CascadeType.PERSIST)
//	@Basic(fetch = FetchType.EAGER)
//	private DBResource resource;
//
//	@OneToOne(mappedBy = "rule", cascade = CascadeType.PERSIST)
//	@Basic(fetch = FetchType.EAGER)
//	private DBSubject subject;
//
//	@OneToOne(mappedBy = "rule", cascade = CascadeType.PERSIST)
//	@Basic(fetch = FetchType.EAGER)
//	private DBRulePriority rule_priority;

	// @OneToMany(mappedBy="rule_parent")
	// @Basic(fetch = FetchType.EAGER)
	// //@JoinColumn(name="parent_rule_id")
	// List<DBTerm> dbterms;
	//

	public Integer getRule_id() {
		return rule_id;
	}

	public void setRule_id(Integer rule_id) {
		this.rule_id = rule_id;
	}

	public String getEffect() {
		return effect;
	}

	public String getDescription() {
		return description;
	}

	public DBAction getAction() {
		return  (DBAction)getCategoryByName("ACTION");
	}

	public DBResource getResource() {
		return  (DBResource)getCategoryByName("RESOURCE");
	}
	
	private DBCategory getCategoryByName(String name){
		
		DBCategory new_dbcat = null;
		
		if ( this.categories.get(name) == null) {
			
			
			if (name == "ACTION") {

				new_dbcat = new DBAction();

			}
			else if (name == "RESOURCE") {

				new_dbcat = new DBResource();
			}
			else if (name == "SUBJECT") {

				new_dbcat = new DBSubject();

			}
			else if (name == "RULE_PRIORITY") {
				new_dbcat = new DBRulePriority();

			}
			
			this.categories.put(name, new_dbcat);
		
		}
			
		else {
			new_dbcat = this.categories.get(name);
			}
		
			
			
		
		return new_dbcat;
	
	}

	public DBRulePriority getRule_priority() {	
		return  (DBRulePriority)getCategoryByName("RULE_PRIORITY");
	}

	public void setRule_priority(DBRulePriority rule_priority) {
		rule_priority.setRule(this);
		addCategory(rule_priority);
		
	}

	public DBSubject getSubject() {
		return  (DBSubject)getCategoryByName("SUBJECT");
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAction(DBAction action) {
		action.setRule(this);
		addCategory(action);
	}

	public void setResource(DBResource resource) {
		resource.setRule(this);
		addCategory(resource);
	}

	public void addCategory(DBCategory cat) {
		
		

		if (cat != null) {

			String cat_name = "";
			cat.setRule(this);
			if (cat instanceof DBAction) {

				cat_name = "ACTION";

			}
			if (cat instanceof DBResource) {

				cat_name = "RESOURCE";
			}
			if (cat instanceof DBSubject) {

				cat_name = "SUBJECT";

			}
			if (cat instanceof DBRulePriority) {
				cat_name = "RULE_PRIORITY";

			}
			
			this.categories.put(cat_name, cat);

		}

	}

	public void setSubject(DBSubject subject) {
		subject.setRule(this);
		addCategory(subject);
	}
	
	public String toString(){
		
		String ret = "";
	
		ret = ret + " \n\n new dbrule from db with rule id:  "+ this.rule_id ;
		ret = ret + " description: "+ this.description + " effect: "+ this.effect + " " ;
		
		for (String key : this.categories.keySet()) {
			
			if(this.categories.get(key).getRoot_term() != null){
			
				DBTerm rdbterm= this.categories.get(key).getRoot_term();
				
				if(rdbterm instanceof DBTerm_Condition){
					
					ret = ret + " \n Category: "+ key +" Condition ->  Name: " + ((DBTerm_Condition)rdbterm).getName() + " Comparision: " + ((DBTerm_Condition)rdbterm).getComparision_string()   +" Value: " + ((DBTerm_Condition)rdbterm).getValue() ;
					
				}
				
			
			}
		
		}
	
		
		return ret;		
		
	}

}
