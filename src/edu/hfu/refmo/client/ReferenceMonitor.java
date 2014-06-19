package edu.hfu.refmo.client;


import java.util.ArrayList;
import java.util.List;

import edu.hfu.refmo.client.Condition.Comparision;
import edu.hfu.refmo.client.Conjunction.Function;
import edu.hfu.rest.action.model.MaintainRequest;
import edu.hfu.rest.action.model.MaintainRequestBasisTerm;
import edu.hfu.rest.action.model.RefmoRequest;
import edu.hfu.rest.action.model.RequestAttribute;

public class ReferenceMonitor {
	
	private String URI;
	
	public ReferenceMonitor(String string) {
		this.URI = string;
	}

	public ReferenceMonitor() {
		// TODO Auto-generated constructor stub
	}

	private RefmoRequest mapCategoriesToRefmoRequest( Category...categories){
		
		RefmoRequest rr = new RefmoRequest();
	
		
		
		for (Category category : categories) {
			
			if(category instanceof Action){
				if(category.getRoot_term() != null){
				rr.setAction_attributes(getAllAttributesForRefmoRequest(new ArrayList<RequestAttribute>(), category.getRoot_term()));
				}
			}
			else if(category instanceof Subject){
				if(category.getRoot_term() != null){
					rr.setSubject_attributes(getAllAttributesForRefmoRequest(new ArrayList<RequestAttribute>(),category.getRoot_term()));
				}
			}
			else if(category instanceof Resource){
				if(category.getRoot_term() != null){
					rr.setResource_attributes(getAllAttributesForRefmoRequest(new ArrayList<RequestAttribute>(),category.getRoot_term()));
				}
			}
			else if(category instanceof RulePriority){
				if(category.getRoot_term() != null){
					rr.setRule_attributes(getAllAttributesForRefmoRequest(new ArrayList<RequestAttribute>(),category.getRoot_term()));
				}
			}
			
			
		}
		
		if(rr.getAction_attributes() != null){
			
			if(rr.getAction_attributes().size() == 1){
			
				rr.getAction_attributes().add(new RequestAttribute());
			
			}
			
		}
		
	if(rr.getRule_attributes() != null){
			
			if(rr.getRule_attributes().size() == 1){
			
				rr.getRule_attributes().add(new RequestAttribute());
			
			}
			
		}
	
	if(rr.getSubject_attributes() != null){
		
		if(rr.getSubject_attributes().size() == 1){
		
			rr.getSubject_attributes().add(new RequestAttribute());
		
		}
		
	}
	
	if(rr.getResource_attributes() != null){
		
		if(rr.getResource_attributes().size() == 1){
		
			rr.getResource_attributes().add(new RequestAttribute());
		
		}
		
	}
		
		
		
		return rr;
		
		
	}
	
	private List<RequestAttribute> getAllAttributesForRefmoRequest(
			List<RequestAttribute> attlist, Term term) {
	


		
		if( term instanceof Condition){
			
			attlist.add(new RequestAttribute(((Condition) term).getName(), ((Condition) term).getValue()));
			}
	
		if( term instanceof Conjunction){
			
	
			for (Term ie : ((Conjunction) term).getTerm_childs()) {
				
				attlist = getAllAttributesForRefmoRequest(attlist, ie);
				
			}
			
			
		}
	

	
	return attlist;
		
		
		
	}



	private MaintainRequest mapCategoriesToMaintainRequest( Category...categories){
	
		MaintainRequest mr = new MaintainRequest();
	
		
		
		for (Category category : categories) {
			
			if(category instanceof Action){
				if(category.getRoot_term() != null){
				mr.setAction_term(this.parseCategoryToBasisTerm(category.getRoot_term()));
				}
			}
			else if(category instanceof Subject){
				if(category.getRoot_term() != null){
				mr.setSubject_term(this.parseCategoryToBasisTerm(category.getRoot_term()));
				}
			}
			else if(category instanceof Resource){
				if(category.getRoot_term() != null){
				mr.setResource_term(this.parseCategoryToBasisTerm(category.getRoot_term()));
				}
			}
			else if(category instanceof RulePriority){
				if(category.getRoot_term() != null){
				mr.setRule_term(this.parseCategoryToBasisTerm(category.getRoot_term()));
				}
			}
			
			
		}
		
		
		return mr;
		
		
	}
	
	private MaintainRequestBasisTerm parseCategoryToBasisTerm(Term term) {
		
		MaintainRequestBasisTerm mrbt = null;
			
			if( term instanceof Condition){
				
				mrbt = new MaintainRequestBasisTerm(((Condition) term).getName(),((Condition) term).getCompare().toString(), ((Condition) term).getValue());
//				((MaintainRequestConditionTerm) mrbt).setName(((Condition) term).getName());
//				((MaintainRequestConditionTerm) mrbt).setValue(((Condition) term).getValue());
//				((MaintainRequestConditionTerm) mrbt).setComparision(((Condition) term).getCompare().toString());
			
				
//				mrbt = new MaintainRequestConditionTerm();
//				((MaintainRequestConditionTerm) mrbt).setName(((Condition) term).getName());
//				((MaintainRequestConditionTerm) mrbt).setValue(((Condition) term).getValue());
//				((MaintainRequestConditionTerm) mrbt).setComparision(((Condition) term).getCompare().toString());
			}
		
			if( term instanceof Conjunction){
				
			    mrbt = new MaintainRequestBasisTerm(((Conjunction) term).getFunction().toString());

				
				for (Term ie : ((Conjunction) term).getTerm_childs()) {
					
					mrbt.setSubTerm(parseCategoryToBasisTerm(ie));
					
				}
				
				
			}
		
		
		return mrbt;
	}

	public void authorize( boolean b, Category...categories){
		if(!b){
		
		new RequestMaker(this.URI).requestAccess(mapCategoriesToRefmoRequest(categories));
		}
		else {
			new RequestMaker(this.URI).requestAccessByURI(mapCategoriesToRefmoRequest(categories));	
			
		}
	}
	
	public void deleteRule(Category...categories){
		
		new RequestMaker(this.URI).delete(mapCategoriesToMaintainRequest(categories));
		
	}
	
	public void updateRule(Category[] categories, Category[] update_categories ){
		
		new RequestMaker(this.URI).update(mergeFindUpdate(mapCategoriesToMaintainRequest(categories), mapCategoriesToMaintainRequest(update_categories)));
	}
	
	private MaintainRequest mergeFindUpdate(MaintainRequest  find, MaintainRequest  update){
		
		find.setNew_action_term(update.getAction_term());
		find.setNew_resource_term(update.getResource_term());
		find.setNew_rule_term(update.getRule_term());
		find.setNew_subject_term(update.getSubject_term());
		
		return find;
	}


	public void createRule(String description, Category...categories) {
		
		new RequestMaker(this.URI).create(mapCategoriesToMaintainRequest(categories));
		
	}

	public void example() {


		  /***
		   * 
		   * TEST ReferencMonitor Client
		   */
		  
			
			Category[] cats_up = new Category[4];
			Category[] cats = new Category[4];
			cats[0] = new RulePriority(new Condition(Comparision.EQUAL, "rulep", "asd"));
			cats[1] = new Action(new Condition(Comparision.EQUAL, "action", "asd"));
			cats[2] = new Subject(new Condition(Comparision.EQUAL, "subject", "asd"));
			cats[3] = new Resource(new Condition(Comparision.EQUAL, "resource", "asd"));
			
			cats_up[0] = new RulePriority(new Condition(Comparision.EQUAL, "asd", "asd"));
			cats_up[1] = new Action(new Condition(Comparision.EQUAL, "asd", "asd"));
			cats_up[2] = new Subject(new Condition(Comparision.EQUAL, "asd", "asd"));
			cats_up[3] = new Resource(new Condition(Comparision.EQUAL, "asd", "asd"));	
			
					

			ReferenceMonitor rm = new ReferenceMonitor("http://localhost:8888/");
			rm.createRule("martin reichendecker", cats);
			rm.updateRule(cats, cats_up);
			rm.deleteRule(cats);
			rm.authorize( false, cats);
			rm.authorize(true, cats);
	}



}
