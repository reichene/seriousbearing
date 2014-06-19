package edu.hfu.refmo.client;


public class Condition extends Term {

    public enum Comparision {
        EQUAL, GREATER, SMALLER
    }
	private Comparision compare;
	private String name;
	private String value;
	public Condition(Comparision compare, String name, String value){
		
		this.compare = compare;
		this.name = name;
		this.value = value;
		
		
	}
	public Comparision getCompare() {
		return compare;
	}
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	public void setCompare(Comparision compare) {
		this.compare = compare;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

}
