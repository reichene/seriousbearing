package edu.hfu.refmo.demo;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.abacs.base.entity.Action;
import de.abacs.base.entity.Condition;
import de.abacs.base.entity.Condition.Comparision;
import de.abacs.base.entity.Decision;
import de.abacs.base.entity.Resource;
import de.abacs.base.entity.Rule;
import de.abacs.base.entity.Subject;
import de.abacs.base.store.RuleStore;
import edu.hfu.refmo.client.Category;
import edu.hfu.refmo.client.Conjunction;
import edu.hfu.refmo.client.ReferenceMonitor;
import edu.hfu.refmo.client.RulePriority;
import edu.hfu.refmo.client.Conjunction.Function;
import edu.hfu.refmo.processor.ProcessorManager;
import edu.hfu.refmo.query.QueryManagerImpl;
import edu.hfu.refmo.store.nosql.advanced.RuleStoreManagerGDatastore;
import edu.hfu.refmo.store.sql.model.advanced.RuleStoreManagerSqlDAOAdvanced;
import edu.hfu.refmo.testing.TestDataGenerator;
import edu.hfu.rest.action.jersey.RequestManager;
import edu.hfu.rest.action.model.RefmoResponse;

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
	//		 try{
				 
		/*
		 *  RULE ENTITY		 
		 */
				 
//		  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//		  Entity ds_rule = new Entity("Rule");
//		  ds_rule.setProperty("effect", "PERMIT _HEY");
//		  ds_rule.setProperty("description", "rule description");
//		  datastore.put(ds_rule);
//		  Key  ds_ruleKey = ds_rule.getKey();
//		   
//		   
////			private String effect;
////			private String description;
////		   
//		   
//			/*
//			 *  TERM CONJUNCTION ENTITY		 
//			 */
//		  
//		  Entity ds_term_conj = new Entity("Term", ds_ruleKey);
//		  ds_term_conj.setProperty("category", "action");
//		  ds_term_conj.setProperty("discriminator", "conjunction");
//		  ds_term_conj.setProperty("function", "OR");
//		  ds_term_conj.setProperty("parent_rule", ds_ruleKey);
//		  datastore.put(ds_term_conj);
//		  Key  ds_term_conj_key =  ds_term_conj.getKey();
//		  
//		  /**
//		   *  TERM CONDITION
//		   * 
//		   */
//		  
//		  Entity ds_term_cond = new Entity("Term", ds_term_conj_key);
//		  ds_term_cond.setProperty("category", "action");
//		  ds_term_cond.setProperty("discriminator", "condition");
//		  ds_term_cond.setProperty("name", "a");
//		  ds_term_cond.setProperty("value", "attributevalue");
//		  ds_term_cond.setProperty("comparision", "EQ");
//		  ds_term_cond.setProperty("parent_rule", ds_ruleKey);
//		  datastore.put(ds_term_cond);
//		  
//		  Entity ds_term_cond6 = new Entity("Term", ds_term_conj_key);
//		  ds_term_cond6.setProperty("category", "action");
//		  ds_term_cond6.setProperty("discriminator", "condition");
//		  ds_term_cond6.setProperty("name", "a");
//		  ds_term_cond6.setProperty("value", "attributevalue2");
//		  ds_term_cond6.setProperty("comparision", "EQ");
//		  ds_term_cond6.setProperty("parent_rule", ds_ruleKey);
//		  datastore.put(ds_term_cond6);
//		  
//		  Entity ds_term_cond2 = new Entity("Term", ds_term_conj_key);
//		  ds_term_cond2.setProperty("category", "resource");
//		  ds_term_cond2.setProperty("discriminator", "condition");
//		  ds_term_cond2.setProperty("name", "a");
//		  ds_term_cond2.setProperty("value", "attributevalue");
//		  ds_term_cond2.setProperty("comparision", "EQ");
//		  ds_term_cond2.setProperty("parent_rule", ds_ruleKey);
//		  datastore.put(ds_term_cond2);
//		  
//		  Entity ds_term_cond3 = new Entity("Term", ds_term_conj_key);
//		  ds_term_cond3.setProperty("category", "subject");
//		  ds_term_cond3.setProperty("discriminator", "condition");
//		  ds_term_cond3.setProperty("name", "a");
//		  ds_term_cond3.setProperty("value", "attributevalue");
//		  ds_term_cond3.setProperty("comparision", "EQ");
//		  ds_term_cond3.setProperty("parent_rule", ds_ruleKey);
//		  datastore.put(ds_term_cond3);
//		  
//		  
//		  Entity ds_term_cond4 = new Entity("Term", ds_term_conj_key);
//		  ds_term_cond4.setProperty("category", "rule_priority");
//		  ds_term_cond4.setProperty("discriminator", "condition");
//		  ds_term_cond4.setProperty("name", "a");
//		  ds_term_cond4.setProperty("value", "attributevalue");
//		  ds_term_cond4.setProperty("comparision", "EQ");
//		  ds_term_cond4.setProperty("parent_rule", ds_ruleKey);
//		  datastore.put(ds_term_cond4);
//		  
//		  ds_rule.setProperty("action_category_key", ds_term_conj_key);
//		  
//		  datastore.put(ds_rule);
//		  
////		  
//		  /***
//		   * testquery with specific entity kind
//		   */
//		  
//		  Query q = new Query("Term").setAncestor(ds_ruleKey);
//		  
//		// Use PreparedQuery interface to retrieve results
//		  PreparedQuery pq = datastore.prepare(q);
//
//
//		  for (Entity result : pq.asIterable()) {
//		    String firstName = (String) result.getProperty("name");
//		    String lastName = (String) result.getProperty("value");
//
//		    System.out.println(firstName + " " + lastName );
//		  }
//		  
//		  
//		  /***
//		   * testquery with all types of entity kinds
//		   */
//		  
//		
//		  
//		  Query allKindsByAnQuery = new Query() .setAncestor(ds_ruleKey);
//				  
//		List<Entity> results = datastore.prepare(allKindsByAnQuery).asList(FetchOptions.Builder.withDefaults());
//		
//		String rausgabe = "";
//		
//		for (Entity entity : results) {
//			
//			System.out.println("##################################");
////			rausgabe = rausgabe + " name:  " + (String) entity.getProperty("name");
////			rausgabe = rausgabe + " value:  " + (String) entity.getProperty("value");
////			rausgabe = rausgabe + " category:  " + (String) entity.getProperty("category");
////			rausgabe = rausgabe + " discriminator: " + (String) entity.getProperty("discriminator");
////			rausgabe = rausgabe + " comparision: " + (String) entity.getProperty("comparision");
////			rausgabe = rausgabe + " function: " + (String) entity.getProperty("function");	
////			rausgabe = rausgabe + "  kind:  " +entity.getKind();
////			
////			System.out.println(rausgabe);
////			rausgabe = "";
//			
//			// ---------keys
//			
//			Key k_rausgabe = (Key) entity.getProperty("parent_rule");
//			if(k_rausgabe != null){
//				
//				System.out.println(" parent_key_kind: " + k_rausgabe.getKind() + " parent_key_name:  " + k_rausgabe.getName() + " parent_key_id: " + k_rausgabe.getId());
//				
//			}
//			Key direct_parent = entity.getParent();
//			if(direct_parent != null){
//				
//				System.out.println(" direct_parent_key_kind: " + direct_parent.getKind() + " direct_parent_key_name:  " + direct_parent.getName() + " direct_parent_key_id: " + direct_parent.getId());
//				
//			}
//			
//			// properties ;)
//			System.out.println("properties ---");
//			
//			for (int i = 0; i < entity.getProperties().keySet().size(); i++) {
//				
//			System.out.println( entity.getProperties().keySet().toArray()[i] + " : "+ entity.getProperties().get(entity.getProperties().keySet().toArray()[i]));
//				
//			}
//
//			
//		}
//		
//		
//
//		/**
//		 * 
//		 *  QUERY by Filters
//		 * 
//		 */
//		String[][] atts= new String[4][3];
//		
//		// category: action
//		atts[0][0] = "a";
//		atts[0][1] = "action";
//		atts[0][2] = "condition";
//		
//		// category: resource
//		atts[1][0] = "a";
//		atts[1][1] = "resource";
//		atts[1][2] = "condition";
//		
//		// category: subject
//		atts[2][0] = "a";
//		atts[2][1] = "subject";
//		atts[2][2] = "condition";
//		
//		// category: rule_priority
//		atts[3][0] = "a";
//		atts[3][1] = "rule_priority";
//		atts[3][2] = "condition";
//		
//		
//		Filter[] filters_or = new Filter[atts.length];
//		
//		for (int i = 0; i < atts.length; i++) {
//			
//			Filter[] filters_and = new Filter[3];
//			
//			for (int j = 0; j < atts[i].length; j++) {
//				
//				String tname = "";
//				if(j==0){tname="name";}
//				else if(j==1){tname="category";}
//				else if(j==2){tname="discriminator";}
//				filters_and[j] =   new FilterPredicate(tname,  FilterOperator.EQUAL, atts[i][j]);
//				
//			}		
//			
//			try {
//			Filter compFilter_and =  CompositeFilterOperator.and(filters_and);
//			filters_or[i] = compFilter_and;
//			}
//			catch(Exception e){ e.printStackTrace();}
//	
//			
//		}
//		
//		Set <Key> relevant_rules = new HashSet<Key>();
//		
//		Filter compFilter_or =
//				CompositeFilterOperator.or(filters_or);
//		
//		Query projquery = new Query("Term").setFilter(compFilter_or);
////		projquery.addProjection(new PropertyProjection("parent_rule", Key.class));
////		
//		List<Entity> allresults = datastore.prepare(projquery).asList(FetchOptions.Builder.withDefaults());
//		
//		System.out.println("RESULTS-----------------------------------\n\n\n");
//		
//		for (Entity entity : allresults) {
//		
//			System.out.println("New Result: ----------------- "); 
//			Key k_rausgabe = (Key) entity.getProperty("parent_rule");
//			if(k_rausgabe != null){
//				
//				relevant_rules.add(k_rausgabe);
//				
//				
//				System.out.println(" parent_key_kind: " + k_rausgabe.getKind() + " parent_key_name:  " + k_rausgabe.getName() + " parent_key_id: " + k_rausgabe.getId());
//				
//			}
//			Key direct_parent = entity.getParent();
//			if(direct_parent != null){
//				
//				System.out.println(" direct_parent_key_kind: " + direct_parent.getKind() + " direct_parent_key_name:  " + direct_parent.getName() + " direct_parent_key_id: " + direct_parent.getId());
//				
//			}
//			
//			// properties ;)
//			System.out.println("properties ---");
//			
//			for (int i = 0; i < entity.getProperties().keySet().size(); i++) {
//				
//			System.out.println( entity.getProperties().keySet().toArray()[i] + " : "+ entity.getProperties().get(entity.getProperties().keySet().toArray()[i]));
//				
//			}
//				
//				
//		}
//		Filter second_filter = new FilterPredicate("value",  FilterOperator.EQUAL, "attributevalue2");
//		Filter action_filter =  new FilterPredicate("parent_rule",  FilterOperator.IN, relevant_rules);
//		
//		Filter compFilter_end =	CompositeFilterOperator.and( action_filter, second_filter);
//			 
//		Query endjquery = new Query("Term").setFilter(compFilter_end);
////		projquery.addProjection(new PropertyProjection("parent_rule", Key.class));
////		
//		List<Entity> allresultsend = datastore.prepare(endjquery).asList(FetchOptions.Builder.withDefaults());
//		
//		System.out.println("END RESULTS-----------------------------------\n\n\n");
//		
//		for (Entity entity : allresultsend) {
//		
//			System.out.println("New Result: ----------------- "); 
//			Key k_rausgabe = (Key) entity.getProperty("parent_rule");
//			if(k_rausgabe != null){
//				
//				relevant_rules.add(k_rausgabe);
//				
//				
//				System.out.println(" parent_key_kind: " + k_rausgabe.getKind() + " parent_key_name:  " + k_rausgabe.getName() + " parent_key_id: " + k_rausgabe.getId());
//				
//			}
//			Key direct_parent = entity.getParent();
//			if(direct_parent != null){
//				
//				System.out.println(" direct_parent_key_kind: " + direct_parent.getKind() + " direct_parent_key_name:  " + direct_parent.getName() + " direct_parent_key_id: " + direct_parent.getId());
//				
//			}
//			
//			// properties ;)
//			System.out.println("properties ---");
//			
//			for (int i = 0; i < entity.getProperties().keySet().size(); i++) {
//				
//			System.out.println( entity.getProperties().keySet().toArray()[i] + " : "+ entity.getProperties().get(entity.getProperties().keySet().toArray()[i]));
//				
//			}
//				
//				
//		} 
//			 
////		
////		 Entity ds_rule_up = new Entity(ds_ruleKey);
////		// ds_rule_up .setProperty("effect", "PERMIT");
////		 ds_rule_up .setProperty("description", "rule description UPDATE");
////		  datastore.put(ds_rule_up);
////		
//			 
//			 }
//			 
//			 catch(Exception e){
//				 
//				 e.printStackTrace();
//			 }
//			 System.out.println("\n \n \n -------- /n Hallowelt welt welt");

			 
			 
			  
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
			 
//			 Objectify ofy2 = OfyServNoEmbBas.ofy();
//			 DSNoSqlRule ns_rule = new DSNoSqlRule("effect", "description");
//			 ofy2.save().entity(ns_rule).now(); 	
//			 
////			 DSNoSqlMatch_Action action_match = new  DSNoSqlMatch_Action("attributename","attributevalue","attribute_datatype","attribute_designator");
////			 DSNoSqlMatch_Resource resource_match = new  DSNoSqlMatch_Resource("attributename","attributevalue","attribute_datatype","attribute_designator");
////			 DSNoSqlMatch_Subject subject_match = new  DSNoSqlMatch_Subject("attributename","attributevalue","attribute_datatype","attribute_designator");
////			
//			 ns_rule.addMatch(new DSNoSqlMatch_Action("attributename","attributevalue","attribute_datatype","attribute_designator"));
//			 ns_rule.addMatch(new DSNoSqlMatch_Subject("attributename","attributevalue","attribute_datatype","attribute_designator"));
//			 ns_rule.addMatch(new DSNoSqlMatch_Resource("attributename","attributevalue","attribute_datatype","attribute_designator"));
//			 		
//			
			 
			 /**
			  * JDBC
			  * 
			  */
//			 try {
//			 Connection conn = DBConnectionManagerSingleton.getConnection();
//			 Statement st = conn.createStatement(); 
//			 ResultSet res = st.executeQuery("SELECT * FROM Rule");
//			 while (res.next()) { 
//			
//
//			     log.info(res.getString("rule_id"));
//			     log.info(res.getString("description"));
//			 } 
//			 
//			 
//			 conn.close(); } 
//		    catch (Exception e) { e.printStackTrace(); } 
			 

			 
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

//			 try{
//			 EntityManager em = EMFSingleton.getEMF().createEntityManager();
//     		 em.getTransaction().begin();
//     		  
//     		  DBRule new_rule = new DBRule();
//     		  new_rule.setDescription("rule description");
//     		  new_rule.setEffect("permit");
//  
//     		  DBRulePriority rule_priority = new DBRulePriority();
//     		  rule_priority.setRoot_term(new DBTerm_Condition("aname0","EQ", "1",Comparision.GREATER));
//     		  rule_priority.setDescription("rule_priority description");
//     		   
//     		       		  
//     		  DBAction action = new DBAction();
//     		  action.setRoot_term(new DBTerm_Condition("aname0","EQ", "1",Comparision.GREATER));
//     		  action.setDescription("action description");
//     		  
//     		  DBSubject subject = new DBSubject();
//    		  subject.setRoot_term(new DBTerm_Condition("aname0","EQ", "1",Comparision.GREATER));
//    		  subject.setDescription("subject_description");
//    		  
//     		  DBResource resource = new DBResource();
//     		  resource.setDescription("resource description");
//     		  DBTerm_Conjunction child_conj= new DBTerm_Conjunction(
//     			
//     				  "OR",
//     					Function.OR,
//     					new DBTerm_Condition("aname2","GT", "5", Comparision.GREATER),
//     					new DBTerm_Condition("aname3","NE", "7", Comparision.EQUAL),
//     					new DBTerm_Condition("aname4","LE", "10", Comparision.SMALLER),
//     					new DBTerm_Conjunction(
//     							"OR",
//     	     					Function.OR,
//     	     					new DBTerm_Condition("aname2","GT", "5", Comparision.GREATER),
//     	     					new DBTerm_Condition("aname3","NE", "7", Comparision.EQUAL)
//     							)
//     		   	
//     				  );
//     		  DBTerm_Conjunction root_conj = new DBTerm_Conjunction( "AND", Function.AND, new DBTerm_Condition("aname", "EQ", "value", Comparision.EQUAL), child_conj );   
//         	  resource.setRoot_term( root_conj);
//     		  
//     	
//         	  new_rule.setAction(action);
//     		  new_rule.setResource(resource);
//    		  new_rule.setSubject(subject);
//     		  
////     		  new_rule.addCategory(action);
////     		  new_rule.addCategory(resource);
////     		  new_rule.addCategory(subject);
//     		  
//     		  em.persist(new_rule);
//     		  em.getTransaction().commit();
//    		  em.close();
//    		
//		
//			 }
//			 catch(Exception e){
//			 
//				 e.printStackTrace();
//			 }
			 
			 
			 
			 
			 /*
			  *
			  *
			  */
//			    MaintainManager mm = new MaintainManager();
//			 	RefmoRequest refr = mm.testDataRefmoRequest(new RefmoRequest());
//			 	    		 	    
//			 
//				RefmoResponse rr = ProcessorManager.process(new QueryManagerImpl().create(
//				refr.getRuleAttributes(), refr.getSubjectAttributes(),
//				refr.getActionAttributes(), refr.getResourceAttributes(),  Decision.PERMIT));
//				
//			 
//				resp.getWriter().println("Hello, world - Demo application  finished successfully");
			 
			 
			 /***
			  * 
			  * generate random test data
			  * 
			  */
			 
		//	 generateRandomRules(11);
			 
		
		
		  String new_query = req.getParameter("query");
		  
		  
			 /***
			  * 
			  * test find function nosql
			  * 
			  */
			TestDataGenerator tdg =  new TestDataGenerator();
			String nosql_true = req.getParameter("sql");
			RuleStore rsmgd = null;
			
			// SQL
			if(nosql_true != null ){
				if(nosql_true.equals("true")){
					rsmgd = new RuleStoreManagerSqlDAOAdvanced();
					((RuleStoreManagerSqlDAOAdvanced)rsmgd).deleteAll();
			
			}}
			else{
			// NOSQL
		
			
			rsmgd = new RuleStoreManagerGDatastore();
			 ((RuleStoreManagerGDatastore)rsmgd).deleteAll();
			}
			 
			 rsmgd.create((tdg.getRulePrioAttributeTreeElement()),
					 ((Subject)tdg.generateAbstractElementByCategory("S")), 
					 ((Action)tdg.generateAbstractElementByCategory("A")),
					 ((Resource)tdg.generateAbstractElementByCategory("R")),
					 Decision.DENY);
			 
		
			 rsmgd.findAll();
		 
			 rsmgd.find((tdg.getRulePrioAttributeTreeElement()),
					 ((Subject)tdg.generateAbstractElementByCategory("S")),
					((Action)tdg.generateAbstractElementByCategory("A")),
					((Resource)tdg.generateAbstractElementByCategory("R")));
			 
		
			 
			/**
			 * not to delete
			 */
			 rsmgd.create(new Condition("rp2", Comparision.EQUAL, "no matter"),
					 new Subject(new Condition("s2", Comparision.EQUAL, "zack")),
					 new Action(new Condition("a2", Comparision.EQUAL, "zack")),
					 new Resource(new Condition("r2", Comparision.EQUAL, "zack")),  Decision.PERMIT);
			 
			
			 rsmgd.create(new Condition("rp1", Comparision.EQUAL, "no matter"),
					 new Subject(new Condition("s1", Comparision.EQUAL, "zack")),
					 new Action(new Condition("a1", Comparision.EQUAL, "zack")),
					 new Resource(new Condition("r1", Comparision.EQUAL, "zack")),  Decision.PERMIT);
			 
			 
			 rsmgd.create(new Condition("rp1", Comparision.EQUAL, "no matter"),
					 null,
					 new Action(new Condition("a1", Comparision.EQUAL, "zack")),
					 new Resource(new Condition("r1", Comparision.EQUAL, "zack")),  Decision.PERMIT);
			 
			 
			 rsmgd.create(new Condition("rp1", Comparision.EQUAL, "no matter"),
					 null,
				     null,
					 new Resource(new Condition("r1", Comparision.EQUAL, "zack")),  Decision.PERMIT);
			 
		
			 rsmgd.find(new Condition("rp1", Comparision.EQUAL, "no matter"),
					 new Subject(new Condition("s1", Comparision.EQUAL, "zack")),
					 null,
					 new Resource(new Condition("r1", Comparision.EQUAL, "zack")));
			 
			 rsmgd.find(new Condition("rp1", de.abacs.base.entity.Condition.Comparision.EQUAL, "no matter"),
					 new Subject(new Condition("s1", de.abacs.base.entity.Condition.Comparision.EQUAL, "zack")),
					 new Action(new Condition("a1", de.abacs.base.entity.Condition.Comparision.EQUAL, "zack")),
					 new Resource(new Condition("r1", de.abacs.base.entity.Condition.Comparision.EQUAL, "zack")));
			 
			 

			 
			 rsmgd.update(
					 new Condition("rp1", de.abacs.base.entity.Condition.Comparision.EQUAL, "no matter"),
					 new Subject(new Condition("s1", de.abacs.base.entity.Condition.Comparision.EQUAL, "zack")),
					 new Action(new Condition("a1", de.abacs.base.entity.Condition.Comparision.EQUAL, "zack")),
					 new Resource(new Condition("r1", de.abacs.base.entity.Condition.Comparision.EQUAL, "zack")), 
					 
					 new Condition("rp3", de.abacs.base.entity.Condition.Comparision.EQUAL, "no matter"),
					 new Subject(new Condition("s3", de.abacs.base.entity.Condition.Comparision.EQUAL, "zack")),
					 new Action(new Condition("a3", de.abacs.base.entity.Condition.Comparision.EQUAL, "zack")),
					 new Resource(new Condition("r3", de.abacs.base.entity.Condition.Comparision.EQUAL, "zack")));
					 
//				
//
//			 
//			 rsmgd.delete(new Condition("rp2", de.abacs.base.entity.Condition.Comparision.EQUAL, "no matter"),
//			 new Subject(new Condition("s2", de.abacs.base.entity.Condition.Comparision.EQUAL, "zack")),
//			 new Action(new Condition("a2", de.abacs.base.entity.Condition.Comparision.EQUAL, "zack")),
//			 new Resource(new Condition("r2", de.abacs.base.entity.Condition.Comparision.EQUAL, "zack")));
//	 	 
//			  rsmgd = null;
			 /***
			  * 
			  * test find function sql
			  * 
			  */
			 
//			 
//			 RuleManagerSQL rms = new RuleManagerSQL();
//			 rms.find(new TestDataGenerator().getDBRule());
//			 
//			 rms.delete(new TestDataGenerator().getDBRule());
//			 
//			 rms.update(new TestDataGenerator().getDBRule(), null);
			  
			  
			  
			
				
				// SQL
				if(new_query != null ){
					if(new_query.equals("true")){
				new ReferenceMonitor().example();
					}}
	}
	
	
//	private static void  generateRandomRequestTest(){
//		
//		
//		RefmoResponse rr = null;	
//		TestDataGenerator tdg = new TestDataGenerator();
//			try {
//				
//				Rule new_rule = tdg.getRandomRefmoRequest().getRule();
//				rr = ProcessorManager.process(new QueryManagerImpl().read(
//						new_rule.getRootElement(),
//						new_rule.getSubject().getRootElement(),
//						new_rule.getResource().getRootElement(),
//						new_rule.getAction().getRootElement()
//						
//						));
//			}
//
//			catch (Exception e) {
//
//			
//				e.printStackTrace();
//
//			}
//		
//	}
	
	private static void  generateRandomRules(int numberTestData){
		
		
		RefmoResponse rr = null;	
		TestDataGenerator tdg = new TestDataGenerator();
			try {
				
				
				
				for (int i = 0; i <= numberTestData; i++) {
					Rule new_rule = tdg.randomMaintainRequest().getRule();
					rr = ProcessorManager.process(new QueryManagerImpl().create(
							new_rule.getRootElement(),
							new_rule.getSubject().getRootElement(),
							new_rule.getResource().getRootElement(),
							new_rule.getAction().getRootElement(),
							new_rule.getDecision()	
							));
			    }
			}

			catch (Exception e) {

			
				e.printStackTrace();

			}
		
	}
}
