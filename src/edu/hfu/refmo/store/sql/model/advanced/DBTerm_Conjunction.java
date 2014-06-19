package edu.hfu.refmo.store.sql.model.advanced;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value = "CONJUNCTION")
public class DBTerm_Conjunction extends DBTerm {

	private String function_string;
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	@Basic(fetch = FetchType.EAGER)
	private List<DBTerm> subordinate_terms = new ArrayList<DBTerm>();

	public DBTerm_Conjunction(String funct, Function and, DBTerm... db_terms) {

		this.function_string = funct;
		for (DBTerm dbTerm : db_terms) {

			dbTerm.setParentConjunction(this);
			this.subordinate_terms.add(dbTerm);

		}

	}

	public void addDBTerm(DBTerm new_dbterm) {
		if(new_dbterm != null){
		new_dbterm.setParentConjunction(this);
		this.subordinate_terms.add(new_dbterm);
		}
	}

	public DBTerm_Conjunction(String funct, DBTerm... db_terms) {

		this.function_string = funct;
		for (DBTerm dbTerm : db_terms) {

			dbTerm.setParentConjunction(this);
			this.subordinate_terms.add(dbTerm);

		}

	}

	public DBTerm_Conjunction(Function and, DBTerm... db_terms) {

		for (DBTerm dbTerm : db_terms) {

			dbTerm.setParentConjunction(this);
			this.subordinate_terms.add(dbTerm);

		}

	}

	public DBTerm_Conjunction() {
		// TODO Auto-generated constructor stub
	}

	public String getFunction_string() {
		return function_string;
	}

	public List<DBTerm> getSubordinate_terms() {
		return subordinate_terms;
	}

	public void setFunction_string(String function_string) {
		this.function_string = function_string;
	}

	public void setSubordinate_terms(List<DBTerm> subordinate_terms) {
		this.subordinate_terms = subordinate_terms;
	}

	public void setRule(DBRule rule) {
		this.rule_parent = rule;

		for (DBTerm i_dbterm : subordinate_terms) {

			i_dbterm.setRule(rule);

		}
	}

	public enum Function {
		AND, OR
	}

}
