package edu.hfu.refmo.store.sql.model.advanced;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;




@Entity
@DiscriminatorValue(value="CONDITION")
public class DBTerm_Condition extends DBTerm {

	    private String name;
	    private String comparision_string;
	    private String value;
	
	    
	    public DBTerm_Condition(String name, String comp, String value, Comparision equal) {
	
	    	this.name = name;
			this.comparision_string = comp;
			this.value = value;
		
		}
	    
		public void setRule(DBRule rule) {
			this.rule_parent = rule;
			
			
		}
	    
	    public DBTerm_Condition(String name,  String value, Comparision equal) {
			this.name = name;
			
			this.value = value;
		}
	    
	    
	    public DBTerm_Condition(String name, String comp, String value) {
			this.name = name;
			this.comparision_string = comp;
			this.value = value;

		}
	    
		public String getName() {
			return name;
		}
		public String getComparision_string() {
			return comparision_string;
		}
		public String getValue() {
			return value;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setComparision_string(String comparision_string) {
			this.comparision_string = comparision_string;
		}
		public void setValue(String value) {
			this.value = value;
		}
	    
	    public enum Comparision {
	        EQUAL, GREATER, SMALLER
	    }

}
