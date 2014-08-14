package edu.hfu.refmo.store.nosql.advanced;

public class KeyStoreEntity {
	
	String ActionHash;
	String SubjectHash;
	String ResourceHash;

	
	public String getActionHash() {
		return ActionHash;
	}
	public String getSubjectHash() {
		return SubjectHash;
	}
	public String getResourceHash() {
		return ResourceHash;
	}
	public void setActionHash(String actionHash) {
		ActionHash = actionHash;
	}
	public void setSubjectHash(String subjectHash) {
		SubjectHash = subjectHash;
	}
	public void setResourceHash(String resourceHash) {
		ResourceHash = resourceHash;
	}
	
	@Override
	public boolean equals(Object ae) {
		 
		 return (this.hashCode()== ae.hashCode() ? true: false);

	}
	
	public String toString(){
		
	  return "   A: "+ 	this.ActionHash + " | R: " + this.ResourceHash + " | S: " + this.SubjectHash;
	}


		
		public int hashCode(){
		    StringBuffer buffer = new StringBuffer();
		    buffer.append(this.ActionHash);
		    buffer.append(this.ResourceHash);
		    buffer.append(this.SubjectHash);
		    return buffer.toString().hashCode();
		
	}
}
