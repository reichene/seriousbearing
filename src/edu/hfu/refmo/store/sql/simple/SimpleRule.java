package edu.hfu.refmo.store.sql.simple;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "SIMPLERULE")
public class SimpleRule {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer RULE_ID;
	private String RULE_PLAIN;
	
	
    @OneToMany(mappedBy="RULE", cascade = CascadeType.ALL)
	@Basic(fetch = FetchType.EAGER)
    @JoinColumn(name="ATTRCOMB_ID")
    private List<SimpleAttributeCombination> attricombinations;


	public void setRULE_ID(Integer rULE_ID) {
		RULE_ID = RULE_ID;
	}


	public Integer getRULE_ID() {
		return RULE_ID;
	}


	public String getRULE_PLAIN() {
		return RULE_PLAIN;
	}


	public List<SimpleAttributeCombination> getAttricombinations() {
		return attricombinations;
	}


	public void setRULE_PLAIN(String PLAIN) {
		RULE_PLAIN = PLAIN;
	}


	public void setAttricombinations(
			List<SimpleAttributeCombination> attricombinations) {
		this.attricombinations = attricombinations;
	}
	  public String toString(){
		return RULE_PLAIN;
		  
		  
		  
	  }
}


