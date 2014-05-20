package edu.hfu.rest.jersey.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.logging.Logger;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import edu.hfu.refmo.model.Attribute;
import edu.hfu.refmo.model.Request;
import edu.hfu.refmo.policystore.Rule;
import edu.hfu.refmo.policystore.objectify.OfyService;
//import edu.hfu.refmo.policystore.Rule;
//import edu.hfu.refmo.policystore.Target;
//import edu.hfu.refmo.policystoremanager.EntityManagerSingleton;
import edu.hfu.refmo.policystoremanager.DBConnectionManagerSingleton;
import edu.hfu.refmo.policystoremanager.EMFSingleton;

@Path("/request")
public class RequestManager {
	
	
	private static final Logger log = Logger.getLogger(RequestManager.class.getName());
 
 @POST
 @Consumes(MediaType.APPLICATION_JSON)
 @Produces(MediaType.APPLICATION_JSON)
public Request requestAccess(Request req) {

	 
     log.info("item attribute:" + req.action_attributes.size());
	 req.returnCombinedDecision = false;
	 	
		return req;
 }
}