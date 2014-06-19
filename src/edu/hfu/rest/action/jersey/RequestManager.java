 package edu.hfu.rest.action.jersey;

import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import de.abacs.base.entity.Rule;
import edu.hfu.refmo.processor.ProcessorManager;
import edu.hfu.refmo.query.QueryManagerImpl;
import edu.hfu.refmo.testing.TestDataGenerator;
import edu.hfu.rest.action.model.RefmoRequest;
import edu.hfu.rest.action.model.RefmoResponse;
import edu.hfu.rest.action.model.RequestAttribute;

@Path("/request")
public class RequestManager {

	/**
	 *  keep in mind: processor currently supports only equal conditions 
	 *  rule_priority attributes arent observed
	 *
	 */
	
	
	private static final Logger log = Logger.getLogger(RequestManager.class
			.getName());

	@Path("/mime")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RefmoResponse requestAccess(RefmoRequest req) {
		
		//log.info(req.toString());

		RefmoResponse rr = new RefmoResponse();

		try {
			
			Rule new_rule = req.getRule();
			rr = ProcessorManager.process(new QueryManagerImpl().read(
					 new_rule.getRootElement(),
					(new_rule.getSubject() != null ? new_rule.getSubject().getRootElement():null),
					(new_rule.getAction() != null ? new_rule.getAction().getRootElement():null),
					(new_rule.getResource() != null ? new_rule.getResource().getRootElement():null)
				
					
					));
		}

		catch (Exception e) {

		
			rr = new RefmoResponse(e.getStackTrace());
			e.printStackTrace();

		}
		return rr;
		// log.info("item attribute:" + req.action_attributes.size());
		// req.returnCombinedDecision = false;

	}
	@Path("/uri")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public RefmoResponse requestAccess(@Context UriInfo info) {

		
		RefmoResponse rr = new RefmoResponse();

		try {
			RefmoRequest refre = RequestManager.parseURI(info);
			Rule new_rule = refre.getRule();
			rr = ProcessorManager.process(new QueryManagerImpl().read(
					new_rule.getRootElement(),
					(new_rule.getSubject() != null ? new_rule.getSubject().getRootElement():null),
					(new_rule.getAction() != null ? new_rule.getAction().getRootElement():null),
					(new_rule.getResource() != null ? new_rule.getResource().getRootElement():null)
					));
		}

		catch (Exception e) {

			rr = new RefmoResponse(e.getStackTrace());
			e.printStackTrace();
		}
		return rr;



		// log.info("item attribute:" + req.action_attributes.size());
		// req.returnCombinedDecision = false;

	}
	
	@Path("/test")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public RefmoRequest getTestRefmoRequest() {
		
		new TestDataGenerator().getRandomRefmoRequest();
		
		return null;
	}
	
	private static RefmoRequest parseURI(UriInfo info) {
		RefmoRequest refre = new RefmoRequest();

		MultivaluedMap<String, String> uri_Parameters = info
				.getQueryParameters();

		Set<String> Keys = uri_Parameters.keySet();
		for (String s_key : Keys) {

			RequestAttribute ra_new = new RequestAttribute(s_key.substring(4),
					info.getQueryParameters().getFirst(s_key));
			
			if (s_key.startsWith("__")){
				log.info("attribute_suffix "+ s_key.substring(0,4)  +  " attribute: "+ s_key.substring(4));
			}
		
			if (s_key.startsWith("__s_")) {
				if(refre.getSubject_attributes() != null){
				refre.getSubject_attributes().add(ra_new);
			}
			} else if (s_key.startsWith("__o_")) {
				if(refre.getResource_attributes() != null){
				refre.getResource_attributes().add(ra_new);
			}
			} else if (s_key.startsWith("__a_")) {
				if(refre.getAction_attributes() != null){
				refre.getAction_attributes().add(ra_new);
			}
			} else if (s_key.startsWith("__r_")) {
				
				if(refre.getRule_attributes() != null){
				refre.getRule_attributes().add(ra_new);
				}
			} else {
				
				log.info("attribute: "+ s_key);

			
			}

		}
		
		return refre;
	}

}