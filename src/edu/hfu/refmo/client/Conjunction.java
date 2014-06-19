package edu.hfu.refmo.client;

public class Conjunction extends Term {

	public Function getFunction() {
		return function;
	}

	public Term[] getTerm_childs() {
		return term_childs;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public void setTerm_childs(Term[] term_childs) {
		this.term_childs = term_childs;
	}

	private Function function;
	private Term[] term_childs;
	
	   public enum Function {
	        AND, OR
	    }
	
	public Conjunction(Function function, Term...terms){
		
		this.function = function;
		this.term_childs = new Term[terms.length];
		this.term_childs = terms;
	}
}
