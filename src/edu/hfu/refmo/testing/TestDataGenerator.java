package edu.hfu.refmo.testing;

import java.util.ArrayList;
import java.util.List;

import de.abacs.base.entity.AbstractElement;
import de.abacs.base.entity.Action;
import de.abacs.base.entity.AttributeTreeElement;
import de.abacs.base.entity.Condition;
import de.abacs.base.entity.Condition.Comparision;
import de.abacs.base.entity.Conjunction;
import de.abacs.base.entity.Conjunction.Function;
import de.abacs.base.entity.Resource;
import de.abacs.base.entity.Subject;
import edu.hfu.refmo.store.sql.model.advanced.DBAction;
import edu.hfu.refmo.store.sql.model.advanced.DBCategory;
import edu.hfu.refmo.store.sql.model.advanced.DBResource;
import edu.hfu.refmo.store.sql.model.advanced.DBRule;
import edu.hfu.refmo.store.sql.model.advanced.DBRulePriority;
import edu.hfu.refmo.store.sql.model.advanced.DBSubject;
import edu.hfu.refmo.store.sql.model.advanced.DBTerm;
import edu.hfu.refmo.store.sql.model.advanced.DBTerm_Condition;
import edu.hfu.rest.action.model.MaintainRequest;
import edu.hfu.rest.action.model.MaintainRequestBasisTerm;
import edu.hfu.rest.action.model.RefmoRequest;
import edu.hfu.rest.action.model.RequestAttribute;

public class TestDataGenerator {
	
	
	List<String> random_Attributes_Rule = new ArrayList<String>();
	List<String> random_Attributes_Subject = new ArrayList<String>();
	List<String> random_Attributes_Object= new ArrayList<String>();
	List<String> random_Attributes_Action = new ArrayList<String>();
	
	public TestDataGenerator(){
		
	    initARule();
	    initASubject();
	    initAObject();
	    initAAction();
		
	} 
	
	
	private void initAAction() {

	random_Attributes_Action.add("a1");
	random_Attributes_Action.add("a20");
	random_Attributes_Action.add("a30");
	random_Attributes_Action.add("a40");
	random_Attributes_Action.add("a50");
	random_Attributes_Action.add("a60");
	random_Attributes_Action.add("a70");
	random_Attributes_Action.add("a80");
	random_Attributes_Action.add("a90");
		
	}


	private void initAObject() {
		
		random_Attributes_Object.add("r10");
		random_Attributes_Object.add("r20");
		random_Attributes_Object.add("r30");
		random_Attributes_Object.add("r40");
		random_Attributes_Object.add("r50");
		random_Attributes_Object.add("r60");
		random_Attributes_Object.add("r70");
		random_Attributes_Object.add("r80");
		random_Attributes_Object.add("r90");
		
	}


	private void initASubject() {

	random_Attributes_Subject.add("s10");
	random_Attributes_Subject.add("s20");
	random_Attributes_Subject.add("s30");
	random_Attributes_Subject.add("s40");
	random_Attributes_Subject.add("s50");
	random_Attributes_Subject.add("s60");
	random_Attributes_Subject.add("s70");
	random_Attributes_Subject.add("s80");
	random_Attributes_Subject.add("s90");

		
	}


	private void initARule() {
      random_Attributes_Rule.add("rp10");
      random_Attributes_Rule.add("rp20");
      random_Attributes_Rule.add("rp30");
      random_Attributes_Rule.add("rp40");
      random_Attributes_Rule.add("rp50");
      random_Attributes_Rule.add("rp60");
      random_Attributes_Rule.add("rp70");
      random_Attributes_Rule.add("rp80");
      random_Attributes_Rule.add("rp90");
     
	}
	
	public AbstractElement generateAbstractElementByCategory(String ae_name){
	
		AbstractElement ae = null;
		
		if(ae_name == "A"){
			System.out.println("new action abstract element: ");	
			ae = new Action(this.generateAttributeTreeElement(this.random_Attributes_Action, false));
		}
			
		
		if(ae_name == "S"){
			System.out.println("new  subject abstract element: ");	
			ae = new Subject(this.generateAttributeTreeElement(this.random_Attributes_Subject, false));
		}
	
	
		if(ae_name == "R"){
			System.out.println("new resource abstract element: ");	
			ae = new Resource(this.generateAttributeTreeElement(this.random_Attributes_Object, false));
			
		}
		
		return ae;
		
	}
	
	
	public AttributeTreeElement getRulePrioAttributeTreeElement(){
		System.out.println("new rule priority attribute tree element: ");	
		return generateAttributeTreeElement(this.random_Attributes_Rule, false);
	}


	private  AttributeTreeElement generateAttributeTreeElement(
			List<String> random_Attributes, boolean b) {

		AttributeTreeElement ate = null;
	    Condition cond = new Condition(getRandomAttribute(random_Attributes), Comparision.GREATER, "not relevant");
	    System.out.println("new condition: " + cond.getName() + " | " + cond.getValue() + " | "+ cond.getComparision());
		
		ate = cond;
		
		
		if(b){
			
			Conjunction conjunction_c1  = null;
			Conjunction conjunction_c2 = null; 
			System.out.println("new OR conjunction: ");
			Condition[] conds1= getRandomConditions(random_Attributes); 
			if(conds1.length > 0){
			 conjunction_c1 = new Conjunction(Function.AND,  conds1 );
			}
			
			System.out.println("new AND conjunction: ");
			Condition[] conds2= getRandomConditions(random_Attributes); 
			if(conds1.length > 0){
			 conjunction_c2 = new Conjunction(Function.AND,  conds2 );
			}
			
			
			System.out.println("new OR conjunction: ");
			
			if(conjunction_c2 != null && conjunction_c1 != null){
			 ate = new Conjunction(Function.OR,  conjunction_c1, conjunction_c2, cond);
			}
			
			else if(conjunction_c2 != null && conjunction_c1 == null){
			ate = new Conjunction(Function.OR,  conjunction_c2, cond);	
			}
			else if(conjunction_c2 == null && conjunction_c1 != null){
			 ate = new Conjunction(Function.OR,  conjunction_c1, cond);	
			}
			else{
				
				ate = cond;
			}
		
		}
		
		
		return ate;
	}

	private Condition[] getRandomConditions(List<String> random_Attributes){
		Condition[] cond_list = new Condition[this.randomNumber(0, 5)];
		
		for (int i = 0; i < cond_list.length; i++) {
			
			cond_list[i] = new Condition(getRandomAttribute(random_Attributes), Comparision.EQUAL, "not relevant");
			
			System.out.println("new condition: " + cond_list[i].getName() + " | " + cond_list[i].getValue() + " | "+ cond_list[i].getComparision());
			
		}
		return cond_list;
	}
	private String getRandomAttribute(List<String> random_Attributes) {
		String att = random_Attributes.get(this.randomNumber(0, random_Attributes.size()-1));
		System.out.println("random attribute: "+ att);
		return att;
		
	}


	public RefmoRequest getRandomRefmoRequest(){
		
		RefmoRequest rr = new RefmoRequest();
			
		rr.setAction_attributes(this.generateRandomAttributes(this.random_Attributes_Action));
		rr.setSubject_attributes(this.generateRandomAttributes(this.random_Attributes_Subject));
		rr.setResource_attributes(this.generateRandomAttributes(this.random_Attributes_Object));
		rr.setRule_attributes(this.generateRandomAttributes(this.random_Attributes_Rule));
		
		
		return rr ;	
	}


	public List<RequestAttribute> generateRandomAttributes(List<String> att_set){
		
		
		List<RequestAttribute> reats = new ArrayList<RequestAttribute>();
		
		int number_att = this.randomNumber(0, 5);
		
		System.out.println("RefmoRequestAttributes");
		
		for (int i = 0; i <=  number_att; i++) {
		
			RequestAttribute ra = new RequestAttribute();
			ra.setName(att_set.get(this.randomNumber(0, att_set.size()-1)));
			ra.setValue("value is not relevant");
			reats.add(ra);
			
			System.out.println("RefmoRequest Attribute: "+ra.getName() );
			
			
		}
		
		
		return reats;
				
		
	}
	
	
	public MaintainRequest randomMaintainRequest(){
		
		
		MaintainRequest mr = new MaintainRequest();
		mr.setDescription("random description");
		
		
		
		if (this.randomNumber(0, 2)>1){
			mr.setEffect("PERMIT");
		}
		
		else {
			
			mr.setEffect("DENY");
		}
		

		System.out.println("New MaintainRequest with Effect " + mr.getEffect() + " and description "+  mr.getDescription());
		
		System.out.println("- Action: ");
		mr.setAction_term(generateRandomMainReqCondTerm(this.random_Attributes_Action));
		System.out.println("- Resource: ");
		mr.setResource_term(generateRandomMainReqCondTerm(this.random_Attributes_Object));		
		System.out.println("- Rule Priority: ");
		mr.setRule_term(generateRandomMainReqCondTerm(this.random_Attributes_Rule));
	
		
		/**
		 *  Subject Conjunction
		 * 
		 */
		
		System.out.println("- Subject: ");
//		
//		MaintainRequestBasisTerm mrct = new MaintainRequestBasisTerm();
//		mrct.setFunction("OR");
//		mrct.setSubTerm(generateRandomMainReqCondTerm(this.random_Attributes_Subject));
//		mrct.setSubTerm(generateRandomMainReqCondTerm(this.random_Attributes_Subject));
//		mrct.setSubTerm(generateRandomMainReqCondTerm(this.random_Attributes_Subject));
//		mrct.setSubTerm(generateRandomMainReqCondTerm(this.random_Attributes_Subject));
//		mrct.setSubTerm(generateRandomMainReqCondTerm(this.random_Attributes_Subject));
//		
//		MaintainRequestBasisTerm mrct2 = new MaintainRequestBasisTerm();
//		mrct2.setFunction("AND");
//		mrct2.setSubTerm(generateRandomMainReqCondTerm(this.random_Attributes_Subject));
//		mrct2.setSubTerm(generateRandomMainReqCondTerm(this.random_Attributes_Subject));
//		mrct2.setSubTerm(generateRandomMainReqCondTerm(this.random_Attributes_Subject));
//		mrct2.setSubTerm(generateRandomMainReqCondTerm(this.random_Attributes_Subject));
//		mrct2.setSubTerm(mrct);
//		
		mr.setSubject_term(generateRandomMainReqCondTerm(this.random_Attributes_Subject));
		
		
		System.out.println("- new Action: ");
		mr.setNew_action_term(generateRandomMainReqCondTerm(this.random_Attributes_Action));
		System.out.println("- new Resource: ");
		mr.setNew_resource_term(generateRandomMainReqCondTerm(this.random_Attributes_Object));
		System.out.println("- new Rule Priority: ");
		mr.setNew_rule_term(generateRandomMainReqCondTerm(this.random_Attributes_Rule));
		System.out.println("- Subject: ");
		mr.setNew_subject_term(generateRandomMainReqCondTerm(this.random_Attributes_Subject));
		return mr;
		
		
	}
	
	
	public MaintainRequestBasisTerm generateRandomMainReqCondTerm(List<String> random_Attributes){
		MaintainRequestBasisTerm mrct = null;
		
		//if(randomNumber(0,50)>20){
			
		mrct = new MaintainRequestBasisTerm();
		mrct.setName(random_Attributes.get(randomNumber(0,random_Attributes.size()-1)));
		mrct.setValue("value is not relevant");
		mrct.setType("CONDITION");
		mrct.setComparision("EQUAL");
		
		System.out.println("MaintainRequestConditionTerm: " + mrct.getName() + " Comparision: "+ mrct.getComparision());
		
		//}
		
		return mrct;		
		
	}
	
	public int randomNumber(int low, int high) {
		high++;
		return (int) (Math.random() * (high - low) + low);
	}


	public DBRule getDBRule() {
		
		DBRule dbr = new DBRule();
	
		dbr.setAction((DBAction)getRandomCategory("A"));
		dbr.setResource((DBResource)getRandomCategory("R"));
		dbr.setSubject((DBSubject)getRandomCategory("S"));
		dbr.setRule_priority((DBRulePriority)getRandomCategory("P"));
		dbr.setDescription("description");
		dbr.setEffect("PERMIT");
				
		return dbr;
	}


	private DBCategory getRandomCategory(String category) {
		
		DBCategory r_category = null;
		
		switch (category) {
		case "A":
			r_category = new DBAction();
			if (this.randomNumber(0, 2)>1){
			r_category.setRoot_term(generateRandomDBTerm(this.random_Attributes_Action));
			}
			break;
		case "R":
			r_category = new DBResource();
			if (this.randomNumber(0, 2)>1){
			r_category.setRoot_term(generateRandomDBTerm(this.random_Attributes_Object));
			}
			break;
		case "S":
			r_category = new DBSubject();
			if (this.randomNumber(0, 2)>1){
			r_category.setRoot_term(generateRandomDBTerm(this.random_Attributes_Subject));
			}
			break;
		case "P":
			r_category = new DBRulePriority();
			if (this.randomNumber(0, 2)>1){
			r_category.setRoot_term(generateRandomDBTerm(this.random_Attributes_Rule));
			}
			break;

	
		}
		
		
		return r_category;
	}


	private DBTerm generateRandomDBTerm(List<String> randomaa) {
	
		DBTerm r_dbterm = new DBTerm_Condition(randomaa.get(randomNumber(0,randomaa.size()-1)), "value not relevant", "EQUAL");
		
		
		return r_dbterm;
	}

}
