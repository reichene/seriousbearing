package edu.hfu.refmo.query;

import java.util.List;

import de.abacs.base.entity.Attribute;
import de.abacs.base.entity.Decision;
import de.abacs.base.query.Create;
import de.abacs.base.query.Delete;
import de.abacs.base.query.Query;
import de.abacs.base.query.Read;
import de.abacs.base.query.Update;

public class QueryManagerImpl implements QueryManager {

	@Override
	public  Query read(List<Attribute> ruleAttributes,
			List<Attribute> subjectAttributes,
			List<Attribute> actionAttributes, List<Attribute> resourceAttributes) {
		// TODO Auto-generated method stub
		return new Read(ruleAttributes, subjectAttributes,	actionAttributes,  resourceAttributes);
	}

	@Override
	public Query create(List<Attribute> ruleAttributes,
			List<Attribute> subjectAttributes,
			List<Attribute> actionAttributes,
			List<Attribute> resourceAttributes, Decision decision) {
		// TODO Auto-generated method stub
		return new Create(ruleAttributes, subjectAttributes, actionAttributes, resourceAttributes, decision);
	}

	@Override
	public Query update(List<Attribute> ruleAttributes,
			List<Attribute> subjectAttributes,
			List<Attribute> actionAttributes,
			List<Attribute> resourceAttributes,
			List<Attribute> newRuleAttributes,
			List<Attribute> newSubjectAttributes,
			List<Attribute> newActionAttributes,
			List<Attribute> newResourceAttributes) {
		// TODO Auto-generated method stub
		return new Update( ruleAttributes,
				 subjectAttributes,
				actionAttributes,
				 resourceAttributes,
				 newRuleAttributes,
				 newSubjectAttributes,
				 newActionAttributes,
				 newResourceAttributes);
	}

	@Override
	public Query delete(List<Attribute> ruleAttributes,
			List<Attribute> subjectAttributes,
			List<Attribute> actionAttributes, List<Attribute> resourceAttributes) {
		// TODO Auto-generated method stub
		return new Delete(ruleAttributes,
				 subjectAttributes,
				 actionAttributes,  resourceAttributes);
	}

}
