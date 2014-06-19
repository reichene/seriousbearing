package edu.hfu.rest.action.jersey;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



import de.abacs.base.entity.Rule;
import edu.hfu.refmo.processor.ProcessorManager;
import edu.hfu.refmo.query.QueryManagerImpl;
import edu.hfu.rest.action.model.MaintainRequest;
import edu.hfu.rest.action.model.RefmoResponse;

@Path("/maintain")
public class MaintainManager {

	private static final Logger log = Logger.getLogger(MaintainManager.class
			.getName());
	
	
	/**
 * Delete rules definied by attribtues from rule store
 * @param req
 * @return
 */
	
	@PUT
	@Path("/rule/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RefmoResponse deleteRule(MaintainRequest req) {
		
	
		/**
		 *  for test usage only
		 *  
		 */
		// req = this.getMainReqTest();
		
		log.info(req.toString());
		
		RefmoResponse rr = new RefmoResponse();

		try {
			
			log.info("item attribute:" + req.getDescription());
			Rule new_rule = req.getRule();
			rr = ProcessorManager.process(new QueryManagerImpl().delete(
					new_rule.getRootElement(),
					(new_rule.getSubject() != null ? new_rule.getSubject().getRootElement(): null),
					(new_rule.getResource() != null ? new_rule.getResource().getRootElement():null ),
					(new_rule.getAction() != null ? new_rule.getAction().getRootElement() :null)
					));
		}

		catch (Exception e) {

			rr = new RefmoResponse(e.getStackTrace(), e.getMessage(), e.getLocalizedMessage(), e.toString(), e.getCause().toString());
			log.warning(e.getMessage());
		//	e.printStackTrace();

		}
		return rr;

	}
 
	/**
	 *  Update Rules
	 * @param req
	 * @return
	 */
		
		@POST
		@Path("/rule")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public RefmoResponse updateRule(MaintainRequest req) {
			
			
			log.info(req.toString());
			/**
			 *  for test usage only
			 *  
			 */
		//	req = this.getMainReqTest();
			
			
			RefmoResponse rr = new RefmoResponse();

			try {
				
				
				log.info("item attribute:" + req.getDescription());
				Rule new_rule = req.getRule();
				Rule update_rule = req.getRuleUpdate();
				rr = ProcessorManager.process(new QueryManagerImpl().update(
						new_rule.getRootElement(),
						(new_rule.getSubject() != null ? new_rule.getSubject().getRootElement(): null),
						(new_rule.getResource() != null ? new_rule.getResource().getRootElement():null ),
						(new_rule.getAction() != null ? new_rule.getAction().getRootElement() :null),
						
						update_rule.getRootElement(),
						(update_rule.getSubject() != null ? update_rule.getSubject().getRootElement():null),
						(update_rule.getResource() != null ? update_rule.getResource().getRootElement():null),
						(update_rule.getAction() != null ? update_rule.getAction().getRootElement():null)
						));
			}

			catch (Exception e) {

				rr = new RefmoResponse(e.getStackTrace(), e.getMessage(), e.getLocalizedMessage(), e.toString(), e.getCause().toString());
				log.warning(e.getMessage());

			}
			return rr;
			// log.info("item attribute:" + req.action_attributes.size());


		}
	 
		/**
		 * Create rules definied by attribtues from rule store
		 * @param req
		 * @return
		 */
			
			@PUT
			@Path("/rule")
			@Consumes(MediaType.APPLICATION_JSON)
			@Produces(MediaType.APPLICATION_JSON)
			public RefmoResponse createRule(MaintainRequest req) {
				
				/**
				 *  for test usage only
				 *  
				 */
			//	req = this.getMainReqTest();
				
				log.info(req.toString());
				
							
				RefmoResponse rr = new RefmoResponse();

				try {
					
					
					log.info("item attribute:" + req.getDescription());
					Rule new_rule = req.getRule();
					rr = ProcessorManager.process(new QueryManagerImpl().create(
							new_rule.getRootElement(),
							(new_rule.getSubject() != null ? new_rule.getSubject().getRootElement(): null),
							(new_rule.getResource() != null ? new_rule.getResource().getRootElement():null ),
							(new_rule.getAction() != null ? new_rule.getAction().getRootElement() :null),
							new_rule.getDecision()	
							));
				}

				catch (Exception e) {

					rr = new RefmoResponse(e.getStackTrace(), e.getMessage(), e.getLocalizedMessage(), e.toString(), e.getCause().toString());
					e.printStackTrace();

				}
				return rr;
				// log.info("item attribute:" + req.action_attributes.size());

			}

//			@GET
//			@Path("/test_plain")
//			//@Consumes("text/plain")
//			@Produces("text/plain")
//			public String testMapper() {
//				
//				
////				 GsonBuilder builder = new GsonBuilder();
////			     builder.setPrettyPrinting().serializeNulls();
////			     Gson gson = builder.create();
////			     
////			    String new_json = gson.toJson(this.sampleMR());
////			    gson.fromJson(new_json, MaintainRequest.class);
////				
//			    
//			    
//			    
//			    return new_json;
//				
//				
//			}
			
			
			@GET
			@Path("/test")
			@Produces(MediaType.APPLICATION_JSON)
			public MaintainRequest testRule() {
		
				return sampleMR();
				// log.info("item attribute:" + req.action_attributes.size());
				// req.returnCombinedDecision = false;

			}
			
			private MaintainRequest sampleMR(){
				

				MaintainRequest mr = new MaintainRequest();
				mr.setEffect("PERMIT");
				mr.setDescription("Descirption");
				
//				
//				MaintainRequestConditionTerm mrct1 = new MaintainRequestConditionTerm();
//				mrct1.setName("time");
//				mrct1.setValue("9");
//				mrct1.setComparision("EQUAL");
//				
//				MaintainRequestConditionTerm mrct2 = new MaintainRequestConditionTerm();
//				mrct2.setName("surname");
//				mrct2.setValue("bleher");
//				mrct2.setComparision("EQUAL");
//				
//				MaintainRequestConditionTerm mrct3 = new MaintainRequestConditionTerm();
//				mrct3.setName("address");
//				mrct3.setValue("weg");
//				mrct3.setComparision("EQUAL");
//				
//				MaintainRequestConditionTerm mrct4 = new MaintainRequestConditionTerm();
//				mrct4.setName("housenumber");
//				mrct4.setValue("7");
//				mrct4.setComparision("EQUAL");
//				
//				/**
//				 * 
//				 * 
//				 */
//				
//				MaintainRequestConditionTerm mrct11 = new MaintainRequestConditionTerm();
//				mrct11.setName("time");
//				mrct11.setValue("4");
//				mrct11.setComparision("EQUAL");
//				
//				MaintainRequestConditionTerm mrct21 = new MaintainRequestConditionTerm();
//				mrct21.setName("surname");
//				mrct21.setValue("maier");
//				mrct21.setComparision("EQUAL");
//				
//				MaintainRequestConditionTerm mrct31= new MaintainRequestConditionTerm();
//				mrct31.setName("address");
//				mrct31.setValue("mustermann");
//				mrct31.setComparision("EQUAL");
//				
//				MaintainRequestConditionTerm mrct41 = new MaintainRequestConditionTerm();
//				mrct41.setName("housenumber");
//				mrct41.setValue("5");
//				mrct41.setComparision("EQUAL");
//				
//				MaintainRequestConjunctionTerm mrct00 = new MaintainRequestConjunctionTerm();
//				mrct00.setFunction("OR");
//				mrct00.setSubTerm(mrct11);
//				mrct00.setSubTerm(mrct2);
//				
//				mr.setAction_term(mrct00);
//				mr.setSubject_term(mrct2);
//				mr.setResource_term(mrct3);
//				mr.setRule_term(mrct00);
//				
//				mr.setNew_action_term(mrct11);
//				mr.setNew_subject_term(mrct21);
//				mr.setNew_resource_term(mrct00);
//				mr.setNew_rule_term(mrct00);
				
				log.info(" neues maitain object");
			
				return mr;
			}
 
}