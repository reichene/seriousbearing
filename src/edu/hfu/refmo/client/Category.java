package edu.hfu.refmo.client;

public abstract class Category {
	
	public Term getRoot_term() {
		return root_term;
	}

	public void setRoot_term(Term root_term) {
		this.root_term = root_term;
	}

	private Term root_term;
	
	public Category(Term term){
		
		this.root_term = term;
		
		
	}

}
