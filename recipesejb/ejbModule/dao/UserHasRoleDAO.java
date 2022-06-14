package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import recipesdb.Ingridient;
import recipesdb.User;
import recipesdb.UserHasRole;



@Stateless
public class UserHasRoleDAO {
    
    @PersistenceContext
    EntityManager em;
    
    public void insert(UserHasRole userHasRole) {
        em.persist(userHasRole);
    }

    public UserHasRole update(UserHasRole userHasRole) {
        return em.merge(userHasRole);
    }

    public void delete(UserHasRole userHasRole) {
        em.remove(em.merge(userHasRole));
    }

    public UserHasRole get(Object iduserhasrole) {
        return em.find(UserHasRole.class, iduserhasrole);
    }
    
    public List findRoleId(User userId) {
    	return em.createQuery(
    	    "SELECT u.role FROM UserHasRole u WHERE u.user LIKE :custUserId")
    	    .setParameter("custUserId", userId)
    	    .getResultList();
    	}
    
	public List<UserHasRole> getFullList() {
		List<UserHasRole> list = null;

		Query query = em.createQuery("select i from UserHasRole i WHERE i.user.login NOT LIKE :custLogin")
				.setParameter("custLogin", "klauders");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
    public List getId(User user) {
    	return em.createQuery(
    		"SELECT i.idUserRole FROM UserHasRole i WHERE i.user LIKE :custUser")
        	.setParameter("custUser", user)
    	    .getResultList();
    	}

    
}
