package edu.hfu.refmo.store.sql.model.advanced;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="Term")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="term")
public abstract class DBTerm {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer term_id;
	
	@ManyToOne
	private DBCategory category;
	
	@ManyToOne
	protected DBRule rule_parent;
	
	@ManyToOne
    @JoinColumn(name="parent_id")
    private DBTerm_Conjunction parent;
	
	public DBTerm( ) {
		
	}
	
	public DBTerm(DBRule new_rule) {
		this.rule_parent = new_rule;
	}

	public DBRule getRule() {
		return rule_parent;
	}

	public void setRule(DBRule rule) {
		this.rule_parent = rule;
	}



	public Integer getTerm_id() {
		return term_id;
	}

	public DBCategory getCategory() {
		return category;
	}

	public DBTerm_Conjunction getParent() {
		return parent;
	}

	public void setTerm_id(Integer term_id) {
		this.term_id = term_id;
	}

	public void setCategory(DBCategory category) {
		this.category = category;
	}

	public void setParent(DBTerm_Conjunction parent) {
		this.parent = parent;
	}

	public void setParentConjunction(DBTerm_Conjunction dbTerm_Conjunction) {

		this.parent = dbTerm_Conjunction;
		
	}


}
