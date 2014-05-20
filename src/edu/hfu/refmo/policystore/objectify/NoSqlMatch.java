package edu.hfu.refmo.policystore.objectify;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;


@Entity
public class NoSqlMatch {
	
	// single table for class
		@Id
		Long idMatch;
		@Parent
	     Key<NoSqlMatch> parentMatch;
		 String  functionType;
		// nested set
		 Integer lft;
		 Integer rght;
			// discriminator
	     boolean isMatchGroup;
		 
	    List<Key<NoSqlMatch>> sub_matches = new ArrayList<Key<NoSqlMatch>>();
			
		/* embedded entity
		List<NoSqlMatch> emb_sub_matches = new ArrayList<NoSqlMatch>();
		*/
		public Long getIdMatch() {
			return idMatch;
		}

		public void setIdMatch(Long idMatch) {
			this.idMatch = idMatch;
		}

		public Key<NoSqlMatch> getParentMatch() {
			return parentMatch;
		}

		public void setParentMatch(Key<NoSqlMatch> parentMatch) {
			this.parentMatch = parentMatch;
		}

		public String getFunctionType() {
			return functionType;
		}

		public void setFunctionType(String functionType) {
			this.functionType = functionType;
		}

		public Integer getLft() {
			return lft;
		}

		public void setLft(Integer lft) {
			this.lft = lft;
		}

		public Integer getRght() {
			return rght;
		}

		public void setRght(Integer rght) {
			this.rght = rght;
		}



		public List<Key<NoSqlMatch>> getSub_matches() {
			return sub_matches;
		}

		public void setSub_matches(List<Key<NoSqlMatch>> sub_matches) {
			this.sub_matches = sub_matches;
		}

		public boolean isMatchGroup() {
			return isMatchGroup;
		}


		public void setMatchGroup(boolean isMatchGroup) {
			this.isMatchGroup = isMatchGroup;
		}

	
		//constructor
		
		public NoSqlMatch(Key<NoSqlMatch> parentMatch, String functionType,
				Integer lft, Integer rght, boolean isMatchGroup) {
			super();
			this.parentMatch = parentMatch;
			this.functionType = functionType;
			this.lft = lft;
			this.rght = rght;
			this.isMatchGroup = isMatchGroup;
		}
	
		

}
