package edu.hfu.refmo.store.sql.model.basic;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="ACTION")
public class DBAction extends DBCategory {

}
