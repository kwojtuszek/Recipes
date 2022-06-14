package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import recipesdb.Ingridient;
import recipesdb.Recipe;



@Stateless
public class IngridientDAO {
    
    @PersistenceContext
    EntityManager em;
    
    public void insert(Ingridient ingridient) {
        em.persist(ingridient);
    }

    public Ingridient update(Ingridient ingridient) {
        return em.merge(ingridient);
    }

    public void delete(Ingridient ingridient) {
        em.remove(em.merge(ingridient));
    }

    public Ingridient get(Object idingridient) {
        return em.find(Ingridient.class, idingridient);
    }
    
    public List validateAdd(String name) {
    	return em.createQuery(
    		"SELECT i.idIngridients FROM Ingridient i WHERE i.name LIKE :custName")
        	.setParameter("custName", name)
    	    .getResultList();
    	}
    
	public List<Ingridient> getFullList() {
		List<Ingridient> list = null;

		Query query = em.createQuery("select i from Ingridient i");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
        
	}

