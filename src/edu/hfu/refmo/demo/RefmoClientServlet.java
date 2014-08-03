package edu.hfu.refmo.demo;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.hfu.refmo.client.Action;
import edu.hfu.refmo.client.Category;
import edu.hfu.refmo.client.Condition.Comparision;
import edu.hfu.refmo.client.Conjunction.Function;
import edu.hfu.refmo.client.Conjunction;
import edu.hfu.refmo.client.ReferenceMonitor;
import edu.hfu.refmo.client.Resource;
import edu.hfu.refmo.client.RulePriority;
import edu.hfu.refmo.client.Subject;
import edu.hfu.refmo.client.Condition;


@SuppressWarnings("serial")
public class RefmoClientServlet extends HttpServlet {
	
	  private static final Logger log = Logger.getLogger(RefmoClientServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
	
			
		  /***
		   * 
		   * TEST ReferencMonitor Client
		   */
		  
			
			Category[] cats_up = new Category[4];
			Category[] cats = new Category[4];
			cats[0] = new RulePriority(new Condition(Comparision.EQUAL, "PRIORITY", "3"));
			cats[1] = new Action(new Condition(Comparision.EQUAL, "TYP", "AENDERN"));
			cats[2] = new Subject(new Condition(Comparision.EQUAL, "ROLLE", "ADMIN"));
			cats[3] = new Resource(new Condition(Comparision.EQUAL, "ZUST", "STABIL"));
			
//			cats_up[0] = new RulePriority(new Condition(Comparision.EQUAL, "neu_rulep", "asd"));
//			cats_up[1] = new Action(new Condition(Comparision.EQUAL, "neu_action", "asd"));
//			cats_up[2] = new Subject(new Condition(Comparision.EQUAL, "neu_subject", "asd"));
//			cats_up[3] = new Resource(new Condition(Comparision.EQUAL, "neu_resource", "asd"));	
//			
					
		//	ReferenceMonitor rm = new ReferenceMonitor("http://serious-bearing-580.appspot.com/");
			ReferenceMonitor rm = new ReferenceMonitor("http://localhost:8888/");
		
			rm.createRule("neue regel", cats);
			
			rm.authorize(true,cats);
			
//			rm.deleteRule(true, cats_up);
//			rm.deleteRule(false, cats_up);
			rm.updateRule(cats, cats_up);
			
			
			rm.authorize(true,cats);
			rm.authorize(false,cats);
			
//			rm.createRule("description", cats);
//			rm.createRule("description2", cats_up);
			
			
	}
	
	

	

}
