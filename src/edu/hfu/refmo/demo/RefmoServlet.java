package edu.hfu.refmo.demo;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;

import edu.hfu.refmo.fi.impl.Attibute;
import edu.hfu.refmo.fi.targets.ActionTarget;
import edu.hfu.refmo.fi.targets.EnvironmentTarget;
import edu.hfu.refmo.fi.targets.ObjectTarget;
import edu.hfu.refmo.fi.targets.SubjectTarget;
import edu.hfu.refmo.store.nosql.entity.DSNoSqlMatch_Action;
import edu.hfu.refmo.store.nosql.entity.DSNoSqlMatch_Resource;
import edu.hfu.refmo.store.nosql.entity.DSNoSqlMatch_Subject;
import edu.hfu.refmo.store.nosql.entity.DSNoSqlRule;
import edu.hfu.refmo.store.nosql.manager.OfyServNoEmbBas;
import edu.hfu.refmo.store.nosql.manager.OfyService;
import edu.hfu.refmo.store.nosql.model.Condition;
import edu.hfu.refmo.store.nosql.model.Eltern;
import edu.hfu.refmo.store.nosql.model.Kind;
import edu.hfu.refmo.store.nosql.model.NoSqlMatchGroup;
import edu.hfu.refmo.store.nosql.model.NoSqlRule;
import edu.hfu.refmo.store.nosql.model.Sohn;
import edu.hfu.refmo.store.nosql.model.Tochter;
import edu.hfu.refmo.store.sql.manager.DBConnectionManagerSingleton;
import edu.hfu.refmo.store.sql.manager.EMFSingleton;
import edu.hfu.refmo.store.sql.model.basic.DBAction;
import edu.hfu.refmo.store.sql.model.basic.DBMatch;
import edu.hfu.refmo.store.sql.model.basic.DBResource;
import edu.hfu.refmo.store.sql.model.basic.DBRule;
import edu.hfu.refmo.store.sql.model.basic.DBSubject;
import edu.hfu.rest.action.jersey.RequestManager;

@SuppressWarnings("serial")
public class RefmoServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(RequestManager.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		/*
		 *  Datastore
		 *  
		 	*/
			 try{
		  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		   Entity employee = new Entity("Employee");
		   employee.setProperty("firstName", "Antonio");
		   employee.setProperty("lastName", "Salieri");

		   Date hireDate = new Date();
		   employee.setProperty("hireDate", hireDate);
		   employee.setProperty("attendedHrTraining", true);
		  datastore.put(employee);
		  

			 }
			 catch(Exception e){
				 
				 e.printStackTrace();
			 }
			 System.out.println("Hallowelt welt welt");

			 
			 
			  
			  /*
			   *  Datastore Objectify
			   */
			 
			/*
			NoSqlRule new_rule =  new edu.hfu.refmo.fi.impl.Rule().create("Description", true, false)
				.setTargetConditionFor(
						new SubjectTarget().setTerms(
									new Attibute("A").eq("C")
									.or(new Attibute("B", new String()).ne(new Attibute("O", new String())))
									.or(new Attibute("C").lt(9))
							        .and(new Attibute("E").gt(20)
							    		   .or(new Attibute("G").ne(3))
							    		   .or(new Attibute("Z").le("22"))
							    		)
							        .and(new Attibute("E").gt(20)
							    		   .or(new Attibute("G").ne(3))
							    		   .or(new Attibute("Z").le("22"))
							    		)
							    	),
						new ObjectTarget().setTerms(
									new Attibute("A").eq("Z")
									.or(new Attibute("B").ne(new Attibute("O",  new Integer(0))))
									.or(new Attibute("C").lt(9))
							        .and(new Attibute("E").gt(20)
							    		   .or(new Attibute("G").ne(3))
							    		   .or(new Attibute("Z").le("22"))
							    		)
						    		),
						new EnvironmentTarget().setTerms(
									new Attibute("A").eq("Z")
									.or(new Attibute("B").ne(new Attibute("O", new Long(0))))
									.or(new Attibute("C").lt(9))
									),
						new ActionTarget().setTerms(
									new Attibute("A").eq("Z")
									.or(new Attibute("B").ne(new Attibute("O", new BigDecimal(1))))
									.or(new Attibute("C").lt(9))
									)				
						)
				.defineEffect(true).return_NoSqlRule();
			 */
			 
//		Objectify ofy = OfyService.ofy();
//			 Condition porsche = new Condition("2FAST", 2);
//			 Sohn sohn1 = new Sohn();
//			 sohn1.alter = 9;
//			 porsche.kind = new Kind();
//			 ofy.save().entity(porsche).now();    // async without the now()
//
//			 
			  
			  /*
			   *  Datastore Objectify 2
			   */
			 
			 Objectify ofy2 = OfyServNoEmbBas.ofy();
			 DSNoSqlRule ns_rule = new DSNoSqlRule("effect", "description");
			 ofy2.save().entity(ns_rule).now(); 	
			 
//			 DSNoSqlMatch_Action action_match = new  DSNoSqlMatch_Action("attributename","attributevalue","attribute_datatype","attribute_designator");
//			 DSNoSqlMatch_Resource resource_match = new  DSNoSqlMatch_Resource("attributename","attributevalue","attribute_datatype","attribute_designator");
//			 DSNoSqlMatch_Subject subject_match = new  DSNoSqlMatch_Subject("attributename","attributevalue","attribute_datatype","attribute_designator");
//			
			 ns_rule.addMatch(new DSNoSqlMatch_Action("attributename","attributevalue","attribute_datatype","attribute_designator"));
			 ns_rule.addMatch(new DSNoSqlMatch_Subject("attributename","attributevalue","attribute_datatype","attribute_designator"));
			 ns_rule.addMatch(new DSNoSqlMatch_Resource("attributename","attributevalue","attribute_datatype","attribute_designator"));
			 		
			
			 
			 /**
			  * JDBC
			  * 
			  */
			 try {
			 Connection conn = DBConnectionManagerSingleton.getConnection();
			 Statement st = conn.createStatement(); 
			 ResultSet res = st.executeQuery("SELECT * FROM Rule");
			 while (res.next()) { 
			

			     log.info(res.getString("rule_id"));
			     log.info(res.getString("description"));
			 } 
			 
			 
			 conn.close(); } 
		    catch (Exception e) { e.printStackTrace(); } 
			 

			 
			 /**
			  *  JPA / Nucleas
			  * 
			  * 
			  */
//			 
//			 try{
//		     // Insert a few rows.
//		  EntityManager em = EMFSingleton.getEMF().createEntityManager();
//		  em.getTransaction().begin();
//		  
//		  Rule nrule1 = new Rule( "new rule", "permit");
//		// Target t1 =  new Target("action");
//		// Target t2 = new Target("subject");
//		//  nrule1.addTarget(t1);
//		//  nrule1.addTarget(t2);
//		  em.persist(nrule1);
//		//  em.persist(7);
//		 // em.persist(new Greeting("user", new Date(), "Hi!"));
//		  em.getTransaction().commit();
//		  em.close();
//			 }
//			 catch(Exception e){
//				 
//				 e.printStackTrace();
//			 }

			 try{
			 EntityManager em = EMFSingleton.getEMF().createEntityManager();
     		 em.getTransaction().begin();
     		  
     		  DBRule new_rule = new DBRule();
     		  new_rule.setDescription("rule description");
     		  new_rule.setEffect("permit");
     		  new_rule.addRuleMatch(new DBMatch("attributeId", "AttributeValue", "attributeDesignator", "dataType"));
      		  new_rule.addRuleMatch(new DBMatch("attributeId", "AttributeValue", "attributeDesignator", "dataType"));
      		  new_rule.addRuleMatch(new DBMatch("attributeId", "AttributeValue", "attributeDesignator", "dataType"));
     		 
     		  
     		  
     		  DBAction action = new DBAction();
     		  DBSubject subject = new DBSubject();
     		  DBResource resource = new DBResource();
     		  
     		  
     		  action.setDescription("actiondescription");
     		  subject.setDescription("subjectdescription");
     		  resource.setDescription("resourcedescription");  
     
     		  action.addMatchTerm(new DBMatch("attributeId", "AttributeValue", "attributeDesignator", "dataType"));
     		  action.addMatchTerm(new DBMatch("attributeI2d", "AttributeValue2", "attributeDesignato2r", "dataType2"));
       		
     		  subject.addMatchTerm(new DBMatch("attributeId", "AttributeValue", "attributeDesignator", "dataType"));
     		  subject.addMatchTerm(new DBMatch("attributeI2d", "AttributeValue2", "attributeDesignato2r", "dataType2"));
       		
    		  resource.addMatchTerm(new DBMatch("attributeId", "AttributeValue", "attributeDesignator", "dataType"));
     		  resource.addMatchTerm(new DBMatch("attributeI2d", "AttributeValue2", "attributeDesignato2r", "dataType2"));
       		
     		  
     		  new_rule.setAction(action);
     		  new_rule.setResource(resource);
     		  new_rule.setSubject(subject);
     		  
     		  em.persist(new_rule);
     		  em.getTransaction().commit();
    		  em.close();
    		
		
			 }
			 catch(Exception e){
			 
				 e.printStackTrace();
			 }
			 
		resp.getWriter().println("Hello, world - Demo application  finished successfully");
	}
}
