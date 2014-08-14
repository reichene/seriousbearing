package edu.hfu.refmo.store.sql.simple;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "SIMPLEATTRIBCOMB")
public class SimpleAttributeCombination {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer ATTRCOMB_ID;
	private String ACTION;
	private String RESOURCE;
	private String SUBJECT;
	private Integer DELETEFLAG;
	
	
	 @ManyToOne
	private SimpleRule RULE;


	public Integer getATTRCOMB_ID() {
		return ATTRCOMB_ID;
	}


	public String getACTION() {
		return ACTION;
	}


	public String getRESOURCE() {
		return RESOURCE;
	}


	public String getSUBJECT() {
		return SUBJECT;
	}


	public Integer isDELETEFLAG() {
		return DELETEFLAG;
	}


	public SimpleRule getRULE() {
		return RULE;
	}


	public void setATTRCOMB_ID(Integer aTTRCOMB_ID) {
		ATTRCOMB_ID = aTTRCOMB_ID;
	}


	public void setACTION(String aCTION) {
		ACTION = aCTION;
	}


	public void setRESOURCE(String rESOURCE) {
		RESOURCE = rESOURCE;
	}


	public void setSUBJECT(String sUBJECT) {
		SUBJECT = sUBJECT;
	}


	public void setDELETEFLAG(Integer dELETEFLAG) {
		DELETEFLAG = dELETEFLAG;
	}


	public void setRULE(SimpleRule rULE) {
		RULE = rULE;
	}

}



