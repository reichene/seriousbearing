package edu.hfu.refmo.store.sql.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name = "target" )
public class Target {

	public Target() {
		
	}
	
	public Target(String p_category) {
	
		this.category = p_category;
	}
	@Id
	private Integer idTarget;
	private String category;
	
    @OneToMany
    @JoinColumn(name="TargetId")
	private ArrayList<Match> matchNodes;
	
	//private MatchGroup rootMatchGroup;
	
}
