package edu.hfu.refmo.store.sql.model.basic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NamedQuery(name="findAll", query="SELECT p FROM DBRule p ")
@Table(name="Rule")
public class DBRule {
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer rule_id;
	private String effect;
	private String description;
	
	@OneToOne(mappedBy="rule", cascade=CascadeType.PERSIST)
    private DBAction action;
	@OneToOne(mappedBy="rule", cascade=CascadeType.PERSIST)
    private DBResource resource;
	@OneToOne(mappedBy="rule", cascade=CascadeType.PERSIST)
    private DBSubject subject;
	
    @OneToMany(cascade=CascadeType.PERSIST, mappedBy="rule") // mapped-by class field 
	@JoinColumn(name="rule")
    private List<DBMatch> priority_matches = new ArrayList<DBMatch>();
	
	public String getEffect() {
		return effect;
	}

	public String getDescription() {
		return description;
	}

	public DBAction getAction() {
		return action;
	}

	public DBResource getResource() {
		return resource;
	}



	public DBSubject getSubject() {
		return subject;
	}

	public List<DBMatch> getPriority_matches() {
		return priority_matches;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAction(DBAction action) {
		action.setRule(this);
		this.action = action;
	}

	public void setResource(DBResource resource) {
		resource.setRule(this);
		this.resource = resource;
	}

	public void addRuleMatch(DBMatch matchel) {
		matchel.setRule(this);
		this.priority_matches.add(matchel);
	}
	

	public void setSubject(DBSubject subject) {
		subject.setRule(this);
		this.subject = subject;
	}

	public void setPriority_matches(List<DBMatch> priority_matches) {
		this.priority_matches.addAll(priority_matches);
	}

	

}
