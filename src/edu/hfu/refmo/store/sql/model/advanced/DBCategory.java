package edu.hfu.refmo.store.sql.model.advanced;


import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CATEGORY")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="category")
@DiscriminatorValue(value="CATEGORY")
public class DBCategory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer category_id;
	private String description;
	
//	@OneToOne(cascade=CascadeType.PERSIST)
//	@JoinColumn(name="rule_id")
//	private DBRule rule ;
//	
	
	/***
	 *  UPDATE
	 */
	@Basic
	@Column(name="KEY")
	private String alias;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="rule_id")
	private DBRule rule;
	
	/***
	 * 
	 * 
	 */

	@OneToOne(mappedBy="category", cascade=CascadeType.ALL)
	private DBTerm root_term;
	
	
	public DBTerm getRoot_term() {
		return root_term;
	}


	public void setRoot_term(DBTerm root_term) {
		root_term.setCategory(this);
		this.root_term = root_term;
	
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}




	public DBRule getRule() {
		return rule;
	}

	public void setRule(DBRule rule) {
		this.rule = rule;
		
		if (this.root_term != null){
			
			this.root_term.setRule(rule);
			
		}
	}


	
	

}
