package edu.hfu.refmo.policystore.objectify;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Subclass;


@Subclass(index=true)
public class NoSqlMatchGroup extends NoSqlMatch {
	

	
	@Load Ref<NoSqlRule> rule;
	
	


	public NoSqlMatchGroup(Key<NoSqlMatch> parentMatch, String functionType,
			Integer lft, Integer rght, boolean isMatchGroup, Ref<NoSqlRule> rule) {
		super(parentMatch, functionType, lft, rght, isMatchGroup);
		this.rule = rule;
	}

	public Ref<NoSqlRule> getRule() {
		return rule;
	}

	public void setRule(Ref<NoSqlRule> rule) {
		rule = rule;
	}



}
