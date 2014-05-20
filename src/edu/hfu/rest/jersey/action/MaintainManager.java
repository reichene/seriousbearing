package edu.hfu.rest.jersey.action;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/maintain")
public class MaintainManager {
	

// /**
// * ----------------------------------------------------
// *  GET INTERFACES
// * ----------------------------------------------------
// * ----------------------------------------------------
// */	
	
 @GET
// @Produces( "application/")
 @Path("/rule/{ruleid}")
 public String getCompleteRuleByRuleId(@PathParam("ruleid") String id) {

	return "export";
 }
 
// 
 @GET
// @Produces( "application/json")
@Path("/rule/{ruleid}/{category}")
public String getMatchElementsByRuleIdAndCategory(@PathParam("ruleid") String id, @PathParam("category") String category) {

	return "export";
 }
// 
//
// 
// @GET
// @Produces( "application/json")
// @Path("/target/{targetid}")
// public String getMatchElementsByTargetId(@PathParam("targetid") String id) {
//
//	return "export";
// }
//
// 
 @GET
 @Path("/matchgroup/{matchgid}")
 public String getMatchGroupById(@QueryParam("depth") String depth, @PathParam("matchgid") String id) {
	 
	 System.out.println("depth:" +depth);
	 System.out.println(id);

	return "exportmatchgroup";
 }
 
 
}