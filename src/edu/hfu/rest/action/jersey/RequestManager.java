package edu.hfu.rest.action.jersey;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import de.abacs.base.entity.Attribute;
import de.abacs.base.entity.Decision;
import edu.hfu.refmo.processor.ProcessorManager;
import edu.hfu.refmo.query.QueryManagerImpl;
import edu.hfu.rest.action.model.RefmoRequest;
import edu.hfu.rest.action.model.RefmoResponse;
import edu.hfu.rest.action.model.RequestAttribute;

@Path("/request")
public class RequestManager {

	private static final Logger log = Logger.getLogger(RequestManager.class
			.getName());

	@Path("/mime")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RefmoResponse requestAccess(RefmoRequest req) {

		RefmoResponse rr = null;

		try {
			rr = ProcessorManager.process(new QueryManagerImpl().read(
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
	@Path("/uri")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public RefmoResponse requestAccess(@Context UriInfo info) {

		
		RefmoResponse rr = null;

		try {
			RefmoRequest refre = RequestManager.parseURI(info);
			rr = ProcessorManager.process(new QueryManagerImpl().read(
					refre.getRuleAttributes(), refre.getSubjectAttributes(),
					refre.getActionAttributes(), refre.getResourceAttributes()));
		}

		catch (Exception e) {

			rr = new RefmoResponse(e.getStackTrace());

		}
		return rr;



		// log.info("item attribute:" + req.action_attributes.size());
		// req.returnCombinedDecision = false;

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
				log.info("attribute_suffix "+ s_key.substring(0,4)  +  "attribute: "+ s_key.substring(4));
			}
		
			if (s_key.startsWith("__s_")) {

				refre.subject_attributes.add(ra_new);

			} else if (s_key.startsWith("__o_")) {

				refre.resource_attributes.add(ra_new);
			} else if (s_key.startsWith("__a_")) {

				refre.action_attributes.add(ra_new);
			} else if (s_key.startsWith("__r_")) {

				refre.rule_attributes.add(ra_new);
			} else {
				
				log.info("attribute: "+ s_key);

				switch (s_key.substring(4)) {
				case "descision":
					
					refre.descision_string = s_key.substring(4);
					break;
					
				case "rule_description":
					refre.ruleDescription = s_key.substring(4);
					break;
				default:
					
					break;
				}

			}

		}
		
		return refre;
	}

}