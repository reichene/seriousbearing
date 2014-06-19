package edu.hfu.refmo.store.sql.model.advanced;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="RESOURCE")
public class DBResource  extends DBCategory {
	
	

}
