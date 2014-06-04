package edu.hfu.refmo.store.nosql.model;

import java.util.ArrayList;



import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

import edu.hfu.refmo.fi.store.MatchGroup;

@Entity
public class NoSqlRule {
	

	@Id
	public Long idRule;
	public String effect;
	public String description;
	

	
	/*
	public @Load Ref<NoSqlMatchGroup> mg_subject; 
	public @Load Ref<NoSqlMatchGroup> mg_object; 
	public @Load Ref<NoSqlMatchGroup> mg_action; 
	public @Load Ref<NoSqlMatchGroup> mg_enviro; 
	*/
	
	
	 public NoSqlMatchGroup subject_target;
	public NoSqlMatch action_target;
	public NoSqlMatch object_target;
	public NoSqlMatch environ_target;
	
	
	public NoSqlRule() {
		
		
	}
	
	

	
	 public NoSqlRule( String description, String effect) {
		// TODO Auto-generated constructor stub
		 
		
		 this.description = description;
		 this.effect = effect;
	
	}
	 public NoSqlRule(Long idRule, String description, String effect) {
		// TODO Auto-generated constructor stub
		 
		 this.idRule = idRule;
		 this.description = description;
		 this.effect = effect;
	
	}
	public NoSqlRule(Long idRule, String effect) {

		this.effect = effect;
		this.idRule = idRule;
		
	}




	public void setAction_target(NoSqlMatchGroup translate) {
		this.action_target = translate;
		
	}




	public void setObject_target(NoSqlMatchGroup translate) {
		this.object_target = translate;
		
	}




	public void setSubject_target(NoSqlMatchGroup translate) {
		this.subject_target = translate;
		
	}




	public void setEnviron_target(NoSqlMatchGroup translate) {
		this.environ_target = translate;
		
	}


}
