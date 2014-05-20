package edu.hfu.refmo.policystore.objectify;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Subclass;

import edu.hfu.refmo.policystore.MatchGroup;


@Subclass(index=true)
public class NoSqlMatchSingle extends NoSqlMatch {
	

	public NoSqlMatchSingle(Key<NoSqlMatch> parentMatch, String functionType,
			Integer lft, Integer rght, boolean isMatchGroup) {
		super(parentMatch, functionType, lft, rght, isMatchGroup);
		// TODO Auto-generated constructor stub
	}
	public NoSqlMatchSingle(Key<NoSqlMatch> parentMatch, String functionType,
			Integer lft, Integer rght, boolean isMatchGroup,
			String attributeName, String attributeDataType,
			String attributeDesignator, String attributeValue) {
		super(parentMatch, functionType, lft, rght, isMatchGroup);
		this.attributeName = attributeName;
		this.attributeDataType = attributeDataType;
		this.attributeDesignator = attributeDesignator;
		this.attributeValue = attributeValue;
	}
	//	private Integer idMatch;
	 String attributeName;
		String attributeDataType;
		 String attributeDesignator;
		 String attributeValue;
		 
		 
	 public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeDataType() {
		return attributeDataType;
	}
	public void setAttributeDataType(String attributeDataType) {
		this.attributeDataType = attributeDataType;
	}
	public String getAttributeDesignator() {
		return attributeDesignator;
	}
	public void setAttributeDesignator(String attributeDesignator) {
		this.attributeDesignator = attributeDesignator;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	
	}

	

