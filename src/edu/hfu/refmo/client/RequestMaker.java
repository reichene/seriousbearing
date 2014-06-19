package edu.hfu.refmo.client;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import edu.hfu.rest.action.model.MaintainRequest;
import edu.hfu.rest.action.model.RefmoRequest;
import edu.hfu.rest.action.model.RefmoResponse;
import edu.hfu.rest.action.model.RequestAttribute;

public class RequestMaker {
	
	private String URI;
	private static String URImaintain = "refmo/maintain/rule/";
	private static String URIrequest = "refmo/request/";
	
	public RequestMaker(String uri2) {

		this.URI = uri2;
	}

	private Client getClient(){
		
		Client client = Client.create();
		return client;
	}

	public void requestAccess(RefmoRequest mr) {
		   try{
		WebResource webResource = getClient().resource(URI+URIrequest+"mime");
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, mr);
		handleResponse(response);
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	}

	public void delete(MaintainRequest mr) {
		   try{
		WebResource webResource = getClient().resource(URI+URImaintain+"delete");
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, mr);
		handleResponse(response);
	        }catch(Exception e){
	        	  e.printStackTrace();
	        }
	}

	public void update(MaintainRequest mr) {
		   try{
		WebResource webResource = getClient().resource(URI+URImaintain);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, mr);
		handleResponse(response);
	        }
		   catch(Exception e){
	        	  e.printStackTrace();
	        }
	}

	public void create(MaintainRequest mr) {
		   try{
			   
		
			   
	    mr.setEffect("PERMIT");
	    mr.setDescription("test desc");
		WebResource webResource = getClient().resource(URI+URImaintain);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, mr);

		handleResponse(response);
		
		   
	        }catch(Exception e){
	        	  e.printStackTrace();
	        }
		 
	
	}

	private String handleResponse(ClientResponse response) {
		
		  String decision = "";
        
		  if(response != null){
		  
		  System.out.println(response.toString());  
		  
		if(response.getStatus() == 200){
	          
//	         System.out.println(response.getType().toString());
//	         System.out.println(response.hasEntity());
//	         System.out.println(response.getEntityTag());
	           
	           
	         if(response.hasEntity()){
	        	 RefmoResponse res = null; 
	        	 String res_string = null;
	        try{
	        	  res = response.getEntity(RefmoResponse.class);
	        }
	        catch(Exception e){
	        	
	        	res_string = response.getEntity(String.class);
	        }
	         	
	        	 if(res != null){
	        		 
	        		 if(res.getDecision() != null){
	        		 System.out.println("Decision: "+ res.getDecision().toString());
	        		 
	        		 
	        	 }
	        		 else{
	        			 
	        			 if(res.getWarning() != null){
	        				 
	        				 System.out.println("warning: " + res.getWarning().toString() );
	        			 }
	        			 System.out.println(res + " " + res.toString());
	        			 
	        		 }
	        		 }
	        	 else {
	        		 
	        		 System.out.println( res_string);
	        	 } 
	           }
	  	         
	       }
		
		else {
			if(response.hasEntity()){
	        	   
	     //   	 decision =  response.getEntity(RefmoResponse.class).warning.toString();
	        	 System.out.println("Warnings: Request terminated with errors  ");
	           }
			
		}
		
		  }
		
		return decision;
		
	}

	public void requestAccessByURI(RefmoRequest mapCategoriesToRefmoRequest) {

		   try{
			   
			   
			   MultivaluedMap<String, String> params = new MultivaluedMapImpl();
			   
			   params = buildGetParamList(params, mapCategoriesToRefmoRequest);
		       // params.add("list", null);
		        
				WebResource webResource = getClient().resource(URI+URIrequest+"uri");
				ClientResponse response = webResource.queryParams(params).type("application/x-www-form-urlencoded").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
				handleResponse(response);
			        }
				   catch(Exception e){
			        	  e.printStackTrace();
			 }
	

	      		
		
	}

	private MultivaluedMap<String, String> buildGetParamList(
			MultivaluedMap<String, String> params,
			RefmoRequest rr) {

		
//		if (s_key.startsWith("__")){
//			log.info("attribute_suffix "+ s_key.substring(0,4)  +  "attribute: "+ s_key.substring(4));
//		}
//	
//		if (s_key.startsWith("__s_")) {
//
//			refre.getSubject_attributes().add(ra_new);
//
//		} else if (s_key.startsWith("__o_")) {
//
//			refre.getResource_attributes().add(ra_new);
//		} else if (s_key.startsWith("__a_")) {
//
//			refre.getAction_attributes().add(ra_new);
//		} else if (s_key.startsWith("__r_")) {
//
//			refre.getRule_attributes().add(ra_new);
//		} else {
//			
//			log.info("attribute: "+ s_key);
//
//		
//		}
		if(rr.getAction_attributes()!= null){
		params = getAttributesForURI(rr.getAction_attributes(), params, "__a_");
		}
		if(rr.getSubject_attributes()!= null){
		params = getAttributesForURI(rr.getSubject_attributes(), params, "__s_");
		}
		if(rr.getResource_attributes()!= null){
		params = getAttributesForURI(rr.getResource_attributes(), params, "__o_");
		}
		if(rr.getRule_attributes()!= null){
		params = getAttributesForURI(rr.getRule_attributes(), params, "__r_");
		}
		
		return params;
	}

	private MultivaluedMap<String, String> getAttributesForURI(
			List<RequestAttribute> attributes,
			MultivaluedMap<String, String> params, String suffix) {
		
		  for (RequestAttribute requestAttribute : attributes) {
			  
			  if(requestAttribute.getName() != null && requestAttribute.getName()!=""){
				  
				  params.add(suffix+requestAttribute.getName(), requestAttribute.getValue());
				  
			  }
			  
			  
			
		 }
		
		
		
		return params;
	}

}
