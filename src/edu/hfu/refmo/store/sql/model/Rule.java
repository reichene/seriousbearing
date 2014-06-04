package edu.hfu.refmo.store.sql.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Rule {
	
	
	public Rule() {
		
		
	}
	
	
	public Rule(String p_description, Target p_target) {
			this.description = p_description;
			this.targets.add(p_target);
	}
	
	 public Rule( String description, String effect) {
		// TODO Auto-generated constructor stub
		 
		
		 this.description = description;
		 this.effect = effect;
	
	}
	 public Rule(Integer idRule, String description, String effect) {
		// TODO Auto-generated constructor stub
		 
		 this.idRule = idRule;
		 this.description = description;
		 this.effect = effect;
	
	}
	public Rule(Integer idRule, String effect) {

		this.effect = effect;
		this.idRule = idRule;
		
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idRule;
	private String effect;
	private String description;
	
    @OneToMany
    @JoinColumn(name="ParentRule")
	private ArrayList<Target> targets;

	public void addTarget(Target p_target) {


		this.targets.add(p_target);
		
	}

	
	

}
