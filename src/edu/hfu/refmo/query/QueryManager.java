package edu.hfu.refmo.query;

import de.abacs.base.entity.AttributeTreeElement;
import de.abacs.base.entity.Decision;
import de.abacs.base.query.Query;

public interface QueryManager {
	
	Query read(AttributeTreeElement ruleAttributes, AttributeTreeElement subjectAttributes, AttributeTreeElement actionAttributes,
            AttributeTreeElement resourceAttributes);
	Query create(AttributeTreeElement ruleAttributes, AttributeTreeElement subjectAttributes, AttributeTreeElement actionAttributes,
            AttributeTreeElement resourceAttributes, Decision decision);
	Query update(AttributeTreeElement ruleAttributes, AttributeTreeElement subjectAttributes, AttributeTreeElement actionAttributes, AttributeTreeElement resourceAttributes,
            AttributeTreeElement newRuleAttributes, AttributeTreeElement newSubjectAttributes, AttributeTreeElement newActionAttributes,
            AttributeTreeElement newResourceAttributes);
	Query delete(AttributeTreeElement ruleAttributes, AttributeTreeElement subjectAttributes, AttributeTreeElement actionAttributes,
            AttributeTreeElement resourceAttributes);

}
