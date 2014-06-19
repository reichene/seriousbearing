package edu.hfu.refmo.store.sql.model.advanced;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="ACTION")
public class DBAction extends DBCategory {

}
