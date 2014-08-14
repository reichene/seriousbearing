package edu.hfu.refmo.store.nosql.advanced;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import de.abacs.base.entity.Action;
import de.abacs.base.entity.AttributeTreeElement;
import de.abacs.base.entity.Condition;
import de.abacs.base.entity.Conjunction;
import de.abacs.base.entity.Resource;
import de.abacs.base.entity.Subject;
import edu.hfu.refmo.store.nosql.advanced.GDSTerm.TermPara;

public class GDSSelectionOptimizer {

	
	
	public Filter getCombinationFilter(GDSRule new_gdsrule){
		
		return this.getFilter(this.getAttributeCombination(new_gdsrule));
		
	}
	
	
	private Filter getFilter(Set<KeyStoreEntity> attrComb) {
		
		Filter ret_filter = null;
		
		if(attrComb.size() == 1){
			
			
			ret_filter = getSubFilterForSingleCombi((KeyStoreEntity)attrComb.toArray()[0]);
			
		}
		else{
			
		
			List<Filter> subFilters = new ArrayList();
			for (KeyStoreEntity keyStoreEntity : attrComb) {
				
				subFilters.add(getSubFilterForSingleCombi(keyStoreEntity));
			
			}	
			
			ret_filter = new CompositeFilter(CompositeFilterOperator.OR,subFilters);
		}
		
		
		
		return ret_filter;

		
	}


	private Filter getSubFilterForSingleCombi(KeyStoreEntity kse) {
	
	List<Filter> lst_filter = new ArrayList();	
	
	Filter ret_filter = null;
		
	if(kse.getActionHash() != null){
		lst_filter.add(new FilterPredicate(
				TermKey.ACTION.name(), FilterOperator.EQUAL,
				kse.getActionHash()));
	
			
	}
	else{   lst_filter.add(new FilterPredicate(
			TermKey.ACTION.name(), FilterOperator.EQUAL,
			"0"));
	
	}
	if(kse.getSubjectHash() != null){
			
		lst_filter.add(new FilterPredicate(
				TermKey.SUBJECT.name(), FilterOperator.EQUAL,
				kse.getSubjectHash()));
		}
	
	else {
		lst_filter.add(new FilterPredicate(
				TermKey.SUBJECT.name(), FilterOperator.EQUAL,
				"0"));
	}
	
	if(kse.getResourceHash() != null){
		
		lst_filter.add( new FilterPredicate(
				TermKey.RESOURCE.name(), FilterOperator.EQUAL,
				kse.getResourceHash()));
	}
	else{
		
		lst_filter.add( new FilterPredicate(
				TermKey.RESOURCE.name(), FilterOperator.EQUAL,
				"0"));
	}
	
	
		
	if(lst_filter.size() == 1){
		
		
		ret_filter = lst_filter.get(0);
	}
	else{
		if(lst_filter.size() > 1){
		
		ret_filter = new CompositeFilter(CompositeFilterOperator.AND, lst_filter);
			
		}
		
	}
		
		return ret_filter;
	}


	public void createOptimizationKeys(GDSRule new_gdsrule){
		
	
		this.createKeyStoreEntity(new_gdsrule.getRule_key(), this.getAttributeCombination(new_gdsrule));
		
	}
	
	
	
	private Set<KeyStoreEntity> getAttributeCombination(GDSRule new_gdsrule) {
		
		List<String> action_hashs 	=  determineRelevantRuleCombination(new_gdsrule.getCategory_action());
		List<String> subject_hashs 	=  determineRelevantRuleCombination(new_gdsrule.getCategory_subject());
		List<String> resource_hashs =  determineRelevantRuleCombination(new_gdsrule.getCategory_resource());
		
		HashMap<String,List<String>> listlist= new HashMap();
		 Set<KeyStoreEntity> asd = new HashSet();
		

		if(action_hashs.size() > 0){
	
			listlist.put("ACTION",action_hashs);
		}
		if(subject_hashs.size() > 0){

			listlist.put("SUBJECT",subject_hashs);
		}
		if(resource_hashs.size() > 0){
			listlist.put("RESOURCE", resource_hashs);
}
		
	   
		
		
		//list itteration
		for (String key_map : listlist.keySet()) {
			
			   Set<KeyStoreEntity> current_attributes = new HashSet();
	
			
			//attributes
			for (int j = 0; j < listlist.get(key_map).size(); j++) {
				
				String curr_hash = listlist.get(key_map).get(j);
				
				if(curr_hash == ""){ curr_hash = null;}
				
				KeyStoreEntity nks = new KeyStoreEntity();
				
				if(key_map.equals("ACTION")){
					nks.setActionHash(curr_hash);
				}
				if(key_map.equals("SUBJECT")){
					nks.setSubjectHash(curr_hash);
				}
				if(key_map.equals("RESOURCE")){
					nks.setResourceHash(curr_hash);
				}
				
				if(!asd.contains(nks) && !current_attributes.contains(nks)){
				current_attributes.add(nks);
				
				nks = null;
				}
				
		
				for (KeyStoreEntity kse: asd) {
					
					KeyStoreEntity nks_new = new KeyStoreEntity();
					
					nks_new.setActionHash(kse.getActionHash());
					nks_new.setResourceHash(kse.getResourceHash());
					nks_new.setSubjectHash(kse.getSubjectHash());
					
					if(key_map.equals("ACTION")){
						nks_new.setActionHash(curr_hash);
					}
					if(key_map.equals("SUBJECT")){
						nks_new.setSubjectHash(curr_hash);
					}
					if(key_map.equals("RESOURCE")){
						nks_new.setResourceHash(curr_hash);
					}
					
					if(!asd.contains(nks_new) && !current_attributes.contains(nks_new)){
						current_attributes.add(nks_new);
					    nks_new = null;
					}
				
			
				}
			}
			
			asd.addAll(current_attributes);
			
			current_attributes  = null;
		}
		
	
		/***
		 * test usage only ----------------------------
		 */
		for (KeyStoreEntity keySE : asd) {
			
			
			System.out.println(keySE.getActionHash() + " | " + keySE.getResourceHash() + " | " + keySE.getSubjectHash());
			
		}
		return asd;
	
	}
	
	private  String generateMD5(String to_be_md5) {
	    String md5_sum = "";
	    //If the provided String is null, then throw an Exception.
	    if (to_be_md5 == null) {
	        throw new RuntimeException("There is no string to calculate a MD5 hash from.");
	    }
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] array = md.digest(to_be_md5.getBytes("UTF-8"));
	        StringBuffer collector = new StringBuffer();
	        for (int i = 0; i < array.length; ++i) {
	            collector.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
	        }
	        md5_sum = collector.toString();
	    }//end try
	    catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("Could not find a MD5 instance: " + e.getMessage());
	    }
	    catch (UnsupportedEncodingException e) {
	        throw new RuntimeException("Could not translate UTF-8: " + e.getMessage());
	    }
	    return md5_sum;
	}//end generateMD5
	
	
	private List<String> determineRelevantRuleCombination(GDSTerm gdsTerm) {
		
		List<String> attr_list = new ArrayList();
		
		if(gdsTerm != null){
		if(gdsTerm instanceof GDSTerm_Condition){
			
			attr_list.add(this.generateMD5(((GDSTerm_Condition) gdsTerm).getName()));
			
		}
		
		else if(gdsTerm instanceof GDSTerm_Conjunction) {
			
			// to be implemented
			
		}
		
		
		attr_list.add("");
		
		}
		
		return attr_list;
	}


	private List<Entity> createKeyStoreEntity(Key ruleKey, Set<KeyStoreEntity> keyStoreEntity){
		
		
		try {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			List<Entity> entity_set = new ArrayList();
			
			
			for (KeyStoreEntity i_kse : keyStoreEntity) {		
				entity_set.add(setKeyStoreEntityProperties(ruleKey, i_kse));
			}
			
			datastore.put(entity_set);


		}

		catch (Exception e) {

			e.printStackTrace();
		}
		
		
		return null;
	}


	private Entity setKeyStoreEntityProperties(Key ruleKey, KeyStoreEntity i_kse) {

		Entity new_entity = new Entity(TermKey.TERMKEY.name(), ruleKey);
		new_entity.setProperty(TermKey.RULEKEY.name(), ruleKey);
		
		if(i_kse.getActionHash() != "" && i_kse.getActionHash() != null)
			new_entity.setProperty(TermKey.ACTION.name(), i_kse.getActionHash());
		else{
			new_entity.setProperty(TermKey.ACTION.name(), "0");
		}
		
		if(i_kse.getResourceHash() != "" && i_kse.getResourceHash() != null)
			new_entity.setProperty(TermKey.RESOURCE.name(), i_kse.getResourceHash());
		
else{
	new_entity.setProperty(TermKey.RESOURCE.name(), "0");
	
		}
		
		
		if(i_kse.getSubjectHash() != "" && i_kse.getSubjectHash() != null)
			new_entity.setProperty(TermKey.SUBJECT.name(), i_kse.getSubjectHash());
		
else{
	new_entity.setProperty(TermKey.SUBJECT.name(), "0");
		}
		
		
			
		return  new_entity;
	}


	public void createOptimizationKeys(Key rule_key,
			AttributeTreeElement ruleRootElement, Subject subject,
			Action action, Resource resource) {
		
		this.createKeyStoreEntity(rule_key, this.getAttributeCombinationCreate( ruleRootElement, subject,
				action, resource));
		
	}


	public Set<KeyStoreEntity> getAttributeCombinationCreate(
			AttributeTreeElement ruleRootElement, Subject subject,
			Action action, Resource resource) {
		
		
		Set<KeyStoreEntity> asd = new HashSet();
		
		KeyStoreEntity kse = new KeyStoreEntity();
		kse.setActionHash("0");
		kse.setResourceHash("0");
		kse.setSubjectHash("0");
		
		if(subject != null){
		if(subject.getRootElement() != null){
			
			if(subject.getRootElement() instanceof Condition){
			
				kse.setSubjectHash(this.generateMD5(((Condition) subject.getRootElement()).getName()));
			}
		}}
		
		if(action != null){
		if(action.getRootElement() != null){
			
			if(action.getRootElement() instanceof Condition){
			
				kse.setActionHash(this.generateMD5(((Condition) action.getRootElement()).getName()));
			}
		}}
		
		
		if(resource != null){
		if(resource.getRootElement() != null){
			
			if(resource.getRootElement() instanceof Condition){
			
				kse.setResourceHash(this.generateMD5(((Condition) resource.getRootElement()).getName()));
			}
		}}
		
		asd.add(kse);
		
		return asd;
	}


	public Set<KeyStoreEntity> getAttributeCombination(
			AttributeTreeElement ruleRootElement, Subject subject,
			Action action, Resource resource) {


		List<String> action_hashs 	=  determineRelevantRuleCombination(action.getRootElement());
		List<String> subject_hashs 	=  determineRelevantRuleCombination(subject.getRootElement());
		List<String> resource_hashs =  determineRelevantRuleCombination(resource.getRootElement());
		
		HashMap<String,List<String>> listlist= new HashMap();
		 Set<KeyStoreEntity> asd = new HashSet();
		

		if(action_hashs.size() > 0){
	
			listlist.put("ACTION",action_hashs);
		}
		if(subject_hashs.size() > 0){

			listlist.put("SUBJECT",subject_hashs);
		}
		if(resource_hashs.size() > 0){
			listlist.put("RESOURCE", resource_hashs);
}
		
	   
		
		
		//list itteration
		for (String key_map : listlist.keySet()) {
			
			   Set<KeyStoreEntity> current_attributes = new HashSet();
	
			
			//attributes
			for (int j = 0; j < listlist.get(key_map).size(); j++) {
				
				String curr_hash = listlist.get(key_map).get(j);
				
				if(curr_hash == ""){ curr_hash = null;}
				
				KeyStoreEntity nks = new KeyStoreEntity();
				
				if(key_map.equals("ACTION")){
					nks.setActionHash(curr_hash);
				}
				if(key_map.equals("SUBJECT")){
					nks.setSubjectHash(curr_hash);
				}
				if(key_map.equals("RESOURCE")){
					nks.setResourceHash(curr_hash);
				}
				
				if(!asd.contains(nks) && !current_attributes.contains(nks)){
				current_attributes.add(nks);
				
				nks = null;
				}
				
		
				for (KeyStoreEntity kse: asd) {
					
					KeyStoreEntity nks_new = new KeyStoreEntity();
					
					nks_new.setActionHash(kse.getActionHash());
					nks_new.setResourceHash(kse.getResourceHash());
					nks_new.setSubjectHash(kse.getSubjectHash());
					
					if(key_map.equals("ACTION")){
						nks_new.setActionHash(curr_hash);
					}
					if(key_map.equals("SUBJECT")){
						nks_new.setSubjectHash(curr_hash);
					}
					if(key_map.equals("RESOURCE")){
						nks_new.setResourceHash(curr_hash);
					}
					
					if(!asd.contains(nks_new) && !current_attributes.contains(nks_new)){
						current_attributes.add(nks_new);
					    nks_new = null;
					}
				
			
				}
			}
			
			asd.addAll(current_attributes);
			
			current_attributes  = null;
		}
		
	
		/***
		 * test usage only ----------------------------
		 */
		for (KeyStoreEntity keySE : asd) {
			
			
			System.out.println(keySE.getActionHash() + " | " + keySE.getResourceHash() + " | " + keySE.getSubjectHash());
			
		}
		return asd;
		
		
	
	}


	private List<String> determineRelevantRuleCombination(AttributeTreeElement ate) {
		List<String> attr_list = new ArrayList();
		
		if(ate != null){
		if(ate instanceof Condition){
			
			attr_list.add(this.generateMD5(((Condition) ate).getName()));
			
		}
		
		else if(ate instanceof Conjunction) {
			
			// to be implemented
			
		}
		
		
		attr_list.add("");
		
		}
		
		return attr_list;
	}


	public Filter getCombinationFilter(AttributeTreeElement ruleRootElement,
			Subject subject, Action action, Resource resource) {
		
		return this.getFilter(this.getAttributeCombination(ruleRootElement, subject,
				action, resource));
		
	}

}
