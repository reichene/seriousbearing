package edu.hfu.refmo.query;

import java.util.List;

import de.abacs.base.entity.Attribute;
import de.abacs.base.entity.Decision;
import de.abacs.base.query.Query;

public interface QueryManager {
	
	Query read(List<Attribute> ruleAttributes, List<Attribute> subjectAttributes, List<Attribute> actionAttributes, List<Attribute> resourceAttributes);
	Query create(List<Attribute> ruleAttributes, List<Attribute> subjectAttributes, List<Attribute> actionAttributes, List<Attribute> resourceAttributes,
            Decision decision);
	Query update(List<Attribute> ruleAttributes, List<Attribute> subjectAttributes, List<Attribute> actionAttributes, List<Attribute> resourceAttributes,
            List<Attribute> newRuleAttributes, List<Attribute> newSubjectAttributes, List<Attribute> newActionAttributes,
            List<Attribute> newResourceAttributes);
	Query delete(List<Attribute> ruleAttributes, List<Attribute> subjectAttributes, List<Attribute> actionAttributes, List<Attribute> resourceAttributes);

}
