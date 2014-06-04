package edu.hfu.refmo.store.sql.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name = "matchelemnestsingle" )
public class Match {
	
	
	// single table for class
	@Id
	@Column(name="IdMatchElement")
	private Integer idMatch;
	
	// adjecency list
//	@ManyToOne
//	private Match parentMatchId; 

	@ManyToOne
	@Column(name="FunctionType")
	private Function functionType;
	
	@ManyToOne
	@Column(name="TargetId")
	private Target targe;
	
	// nested set
	private Integer lft;
	private Integer rght;
	
	// discriminator
	private boolean isMatchGroup;
	
	
	// Match leafs (group or single match)
    @OneToMany
    @JoinColumn(name="parentMatchId")
	private ArrayList<Match> matchNodes;
	
	
   
}
