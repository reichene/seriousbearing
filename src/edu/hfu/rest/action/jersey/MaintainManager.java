package edu.hfu.rest.action.jersey;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import edu.hfu.refmo.processor.ProcessorManager;
import edu.hfu.refmo.query.QueryManagerImpl;
import edu.hfu.rest.action.model.RefmoRequest;
import edu.hfu.rest.action.model.RefmoResponse;

@Path("/maintain")
public class MaintainManager {

	
	
	

/**
 * Delete rules definied by attribtues from rule store
 * @param req
 * @return
 */
	
	@DELETE
	@Path("/rule")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RefmoResponse deleteRule(RefmoRequest req) {

		RefmoResponse rr = null;

		try {
			rr = ProcessorManager.process(new QueryManagerImpl().delete(
					req.getRuleAttributes(), req.getSubjectAttributes(),
					req.getActionAttributes(), req.getResourceAttributes()));
		}

		catch (Exception e) {

			rr = new RefmoResponse(e.getStackTrace());

		}
		return rr;
		// log.info("item attribute:" + req.action_attributes.size());
		// req.returnCombinedDecision = false;

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
		public RefmoResponse updateRule(RefmoRequest req) {

			RefmoResponse rr = null;

			try {
				rr = ProcessorManager.process(new QueryManagerImpl().update(
						req.getRuleAttributes(), req.getSubjectAttributes(),
						req.getActionAttributes(), req.getResourceAttributes(),  req.getNewRuleAttributes(), req.getNewSubjectAttributes(), req.getNewActionAttributes(), req.getNewResourceAttributes()));
			}

			catch (Exception e) {

				rr = new RefmoResponse(e.getStackTrace());

			}
			return rr;
			// log.info("item attribute:" + req.action_attributes.size());
			// req.returnCombinedDecision = false;

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
			public RefmoResponse createRule(RefmoRequest req) {

				RefmoResponse rr = null;

				try {
					rr = ProcessorManager.process(new QueryManagerImpl().create(
							req.getRuleAttributes(), req.getSubjectAttributes(),
							req.getActionAttributes(), req.getResourceAttributes(),req.getDescision()));
				}

				catch (Exception e) {

					rr = new RefmoResponse(e.getStackTrace());

				}
				return rr;
				// log.info("item attribute:" + req.action_attributes.size());
				// req.returnCombinedDecision = false;

			}
 
}