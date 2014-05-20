package edu.hfu.refmo.policystore;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.InheritanceType;



@Entity
@Table(name="attributesingle")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class MatchSingle extends Match {
	
	
//	private Integer idMatch;
	private String attributeName;
	private String attributeDataType;
	private String attributeDesignator;
	private String attributeValue;
	private MatchGroup parentMatchGroup;
	
	

}
