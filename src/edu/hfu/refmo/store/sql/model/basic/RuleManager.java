package edu.hfu.refmo.store.sql.model.basic;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import edu.hfu.refmo.store.sql.manager.EMFSingleton;


public class RuleManager {
	

	public DBRule create(DBRule p_dbrule) {
		
		EntityManager em = EMFSingleton.getEMF().createEntityManager();
		DBRule ret_dbrule = null;
		try
		{
		    em.getTransaction().begin(); 
		    // ---------------------------------------------
		    em.persist(p_dbrule);	    
		    // ---------------------------------------------
		    em.getTransaction().commit();
		}
		finally
		{
		    if (  em.getTransaction().isActive())
		    {
		    	  em.getTransaction().rollback();
		    }

		    em.close();
		}
		
		return ret_dbrule;
	}

	
	public List<DBRule> delete(DBRule p_dbrule) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DBRule> find(DBRule p_dbrule) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DBRule> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DBRule> update(DBRule p_dbrule, DBRule p_update_dbrule ) {
		
		return null;
	}

}
