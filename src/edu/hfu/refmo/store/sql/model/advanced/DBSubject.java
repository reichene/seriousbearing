package edu.hfu.refmo.store.sql.model.advanced;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="SUBJECT")
public class 	DBSubject  extends DBCategory {

}
