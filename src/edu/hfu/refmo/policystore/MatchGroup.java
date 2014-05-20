package edu.hfu.refmo.policystore;

import javax.persistence.Entity;
import javax.persistence.InheritanceType;
import javax.persistence.Inheritance;




@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class MatchGroup extends Match {
	
	//private Integer idMatch;
	private String description;

}
