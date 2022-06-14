package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import recipesdb.Role;



@Stateless
public class RoleDAO {
    
    @PersistenceContext
    EntityManager em;
    
    public void insert(Role role) {
        em.persist(role);
    }

    public Role update(Role role) {
        return em.merge(role);
    }

    public void delete(Role role) {
        em.remove(em.merge(role));
    }

    public Role get(Object idrole) {
        return em.find(Role.class, idrole);
    }
    
    public List findRole(String role_Name) {
    	return em.createQuery(
    		"SELECT r FROM Role r WHERE r.role_Name LIKE :custName")
        	.setParameter("custName", role_Name)
    	    .getResultList();
    	}

    
}