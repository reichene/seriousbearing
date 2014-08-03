package edu.hfu.refmo.query;

import de.abacs.base.entity.AttributeTreeElement;
import de.abacs.base.entity.Decision;
import de.abacs.base.query.Create;
import de.abacs.base.query.Delete;
import de.abacs.base.query.Query;
import de.abacs.base.query.Read;
import de.abacs.base.query.Update;

public class QueryManagerImpl implements QueryInterface {

	@Override
	public Query read(AttributeTreeElement ruleAttributes,
			AttributeTreeElement subjectAttributes,
			AttributeTreeElement actionAttributes,
			AttributeTreeElement resourceAttributes) {
		// TODO Auto-generated method stub
		return new Read(ruleAttributes, subjectAttributes, actionAttributes, resourceAttributes);
	}

	@Override
	public Query create(AttributeTreeElement ruleAttributes,
			AttributeTreeElement subjectAttributes,
			AttributeTreeElement actionAttributes,
			AttributeTreeElement resourceAttributes, Decision decision) {
		// TODO Auto-generated method stub
		return new Create(ruleAttributes, subjectAttributes, actionAttributes, resourceAttributes, decision);
	}

	@Override
	public Query update(AttributeTreeElement ruleAttributes,
			AttributeTreeElement subjectAttributes,
			AttributeTreeElement actionAttributes,
			AttributeTreeElement resourceAttributes,
			AttributeTreeElement newRuleAttributes,
			AttributeTreeElement newSubjectAttributes,
			AttributeTreeElement newActionAttributes,
			AttributeTreeElement newResourceAttributes) {
		// TODO Auto-generated method stub
		return new Update(ruleAttributes, subjectAttributes, actionAttributes, resourceAttributes, newRuleAttributes, newSubjectAttributes, newActionAttributes, newResourceAttributes);
	}

	@Override
	public Query delete(AttributeTreeElement ruleAttributes,
			AttributeTreeElement subjectAttributes,
			AttributeTreeElement actionAttributes,
			AttributeTreeElement resourceAttributes) {
		// TODO Auto-generated method stub
		return new Delete(ruleAttributes, subjectAttributes, actionAttributes, resourceAttributes);
	}

	

}
