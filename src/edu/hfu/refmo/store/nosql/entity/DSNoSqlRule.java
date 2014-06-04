package edu.hfu.refmo.store.nosql.entity;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

import edu.hfu.refmo.store.nosql.manager.OfyServNoEmbBas;

@Entity
public class DSNoSqlRule {
	
	@Id Long id;
	String effect;
    String description;
    
    @Load List<Ref<DSNoSqlMatch>> matches = new ArrayList<Ref<DSNoSqlMatch>>();

	 
	public String getEffect() {
		return effect;
	}


	public String getDescription() {
		return description;
	}


	public void setEffect(String effect) {
		this.effect = effect;
	}


	public void setDescription(String description) {
		this.description = description;
	}



	

    public List<DSNoSqlMatch> getMatches() {
	     
    	List<DSNoSqlMatch> match_return= new ArrayList<DSNoSqlMatch>();
    	
    	for (Ref<DSNoSqlMatch> iterable_match : matches) {
    		
    		match_return.add(iterable_match.get());
			
		}
		return match_return;
	}
	

	public void addMatch (DSNoSqlMatch match) {
		
		OfyServNoEmbBas.ofy().save().entity(match).now();
		match.setRule(this);
		this.matches.add(Ref.create(match));
	}


	public DSNoSqlRule(String effect, String description) {
		super();
		this.effect = effect;
		this.description = description;
	
	}
	
	

}
