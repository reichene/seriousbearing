package edu.hfu.refmo.store.nosql.model;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;


@Entity
public class NoSqlMatch {
	
	
	    // MatchElement Id
		@Id
		Long idMatch;
		
		// Verweis auf Parent Match Element
		//@Parent
	    // Key<NoSqlMatch> parentMatch;
		NoSqlMatch parentMatch;
		
		//Parent rule element
		//@Load
		public NoSqlRule rule;
		
		// nested set
		Integer lft;
		Integer rght;
		

		 

		

}
