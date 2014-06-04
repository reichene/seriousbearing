package edu.hfu.refmo.store.sql.model.basic;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@DiscriminatorValue(value="RESOURCE")
public class DBResource  extends DBCategory {
	
	

}
