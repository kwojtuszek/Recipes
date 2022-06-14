package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import recipesdb.User;



@Stateless
public class UserDAO {
    
    @PersistenceContext
    EntityManager em;
    
    public void insert(User user) {
        em.persist(user);
    }

    public User update(User user) {
        return em.merge(user);
    }

    public void delete(User user) {
        em.remove(em.merge(user));
    }

    public User get(Object iduser) {
        return em.find(User.class, iduser);
    }
    
    public List validateCreate(String login) {
    	return em.createQuery(
    		"SELECT u.idUsers FROM User u WHERE u.login LIKE :custLogin")
        	.setParameter("custLogin", login)
    	    .getResultList();
    	}
    
    public List validateLogin(String login, String pass) {
    	return em.createQuery(
    	    "SELECT u.idUsers FROM User u WHERE u.login LIKE :custLogin AND u.pass LIKE :custPass")
    	    .setParameter("custLogin", login)
    	    .setParameter("custPass", pass)
    	    .getResultList();
    	}
    
}