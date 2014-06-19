package edu.hfu.refmo.store.sql.model.advanced;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="RULE_PRIORITY")
public class DBRulePriority  extends DBCategory {
	
	

}
