package edu.hfu.refmo.store.nosql.manager;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyServNoEmbBas {
    static {
    	
    	try{
       factory().register(edu.hfu.refmo.store.nosql.entity.DSNoSqlMatch.class);
       factory().register(edu.hfu.refmo.store.nosql.entity.DSNoSqlMatch_Action.class);
       factory().register(edu.hfu.refmo.store.nosql.entity.DSNoSqlMatch_Resource.class);
       factory().register(edu.hfu.refmo.store.nosql.entity.DSNoSqlMatch_Subject.class);
       factory().register(edu.hfu.refmo.store.nosql.entity.DSNoSqlRule.class);
    	}
  catch(Exception e){
    		e.printStackTrace();
    		
    	}
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}