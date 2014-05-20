package edu.hfu.refmo.policystore.objectify;

import java.util.ArrayList;






import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

import edu.hfu.refmo.policystore.Target;

public class NoSqlRule {
	
	@Id
	private Long idRule;
	private String effect;
	private String ruleDescription;
	/*private String created;
	private String lastmodified;
	private String owner;

	//Embedded
	private NoSqlMatchGroup emb_mg_subject; 
	private NoSqlMatchGroup emb_mg_object; 
	private NoSqlMatchGroup emb_mg_action; 
	private NoSqlMatchGroup emb_mg_enviro; 
		*/
	
	private @Load Ref<NoSqlMatchGroup> mg_subject; 
	public NoSqlRule( String effect, String ruleDescription) {
		super();
		this.effect = effect;
		this.ruleDescription = ruleDescription;
	}
	public Long getIdRule() {
		return idRule;
	}
	public void setIdRule(Long idRule) {
		this.idRule = idRule;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public String getRuleDescription() {
		return ruleDescription;
	}
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	public Ref<NoSqlMatchGroup> getMg_subject() {
		return mg_subject;
	}
	public void setMg_subject(Ref<NoSqlMatchGroup> mg_subject) {
		this.mg_subject = mg_subject;
	}
	public Ref<NoSqlMatchGroup> getMg_object() {
		return mg_object;
	}
	public void setMg_object(Ref<NoSqlMatchGroup> mg_object) {
		this.mg_object = mg_object;
	}
	public Ref<NoSqlMatchGroup> getMg_action() {
		return mg_action;
	}
	public void setMg_action(Ref<NoSqlMatchGroup> mg_action) {
		this.mg_action = mg_action;
	}
	public Ref<NoSqlMatchGroup> getMg_enviro() {
		return mg_enviro;
	}
	public void setMg_enviro(Ref<NoSqlMatchGroup> mg_enviro) {
		this.mg_enviro = mg_enviro;
	}
	private @Load Ref<NoSqlMatchGroup> mg_object; 
	private @Load Ref<NoSqlMatchGroup> mg_action; 
	private @Load Ref<NoSqlMatchGroup> mg_enviro; 

}
