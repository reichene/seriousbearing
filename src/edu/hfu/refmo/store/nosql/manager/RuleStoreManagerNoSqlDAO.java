package edu.hfu.refmo.store.nosql.manager;

import java.util.List;

import de.abacs.base.entity.Action;
import de.abacs.base.entity.Attribute;
import de.abacs.base.entity.Decision;
import de.abacs.base.entity.Resource;
import de.abacs.base.entity.Rule;
import de.abacs.base.entity.Subject;
import de.abacs.base.store.RuleStore;

public class RuleStoreManagerNoSqlDAO implements RuleStore {

	@Override
	public boolean create(List<Attribute> p_rule_att, Subject p_subject, Action p_action,
			Resource p_resource, Decision p_decision) {
		
		
		
		
		return false;
	}

	@Override
	public boolean delete(List<Attribute> p_rule_att, Subject p_subject, Action p_action,
			Resource p_resource) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Rule> find(List<Attribute> p_rule_att, Subject p_subject, Action p_action,
			Resource p_resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rule> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(List<Attribute> p_rule_att, Subject p_subject, Action p_action,
			Resource p_resource, List<Attribute> p_new_rule_att, Subject p_new_subject, Action p_new_action,
			Resource p_new_resource) {
		// TODO Auto-generated method stub
		return false;
	}

}
