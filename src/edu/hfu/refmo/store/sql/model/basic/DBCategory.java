package edu.hfu.refmo.store.sql.model.basic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="Category")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="category")
@DiscriminatorValue(value="CATEGORY")
public class DBCategory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer category_id;
	private String description;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="rule_id")
	private DBRule rule ;

	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="category")
	@JoinColumn(name="category")
	private List<DBMatch> match_terms = new ArrayList<DBMatch>();
	
	
	public String getDescription() {
		return description;
	}

	public List<DBMatch> getMatch_terms() {
		return match_terms;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMatch_terms(List<DBMatch> match_terms) {
		this.match_terms.addAll(match_terms);
	}
	
	public void addMatchTerm(DBMatch match_term) {
		
		match_term.setCategory(this);		
		this.match_terms.add(match_term);
	}

	public DBRule getRule() {
		return rule;
	}

	public void setRule(DBRule rule) {
		this.rule = rule;
	}


	
	

}
