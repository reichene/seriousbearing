package edu.hfu.refmo.store.nosql.manager;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {
    static {
    	
    	try{
       factory().register(edu.hfu.refmo.store.nosql.model.Condition.class);
//       factory().register(edu.hfu.refmo.store.nosql.model.Eltern.class);
//       factory().register(edu.hfu.refmo.store.nosql.model.Sohn.class);
//       factory().register(edu.hfu.refmo.store.nosql.model.Kind.class);
//      
      // factory().register(edu.hfu.refmo.store.nosql.model.Tochter.class);
   
//     
//       
//    	
      /*  factory().register(edu.hfu.refmo.store.nosql.model.NoSqlMatchGroup.class);
       factory().register(edu.hfu.refmo.store.nosql.model.NoSqlMatchSingle.class);
   factory().register(edu.hfu.refmo.store.nosql.model.NoSqlMatch.class );  */  }
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