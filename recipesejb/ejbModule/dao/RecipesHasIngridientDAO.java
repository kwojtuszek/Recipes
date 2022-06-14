package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import recipesdb.Recipe;
import recipesdb.RecipesHasIngridient;



@Stateless
public class RecipesHasIngridientDAO {
    
    @PersistenceContext
    EntityManager em;
    
    public void insert(RecipesHasIngridient recipeshasingridient) {
        em.persist(recipeshasingridient);
    }

    public RecipesHasIngridient update(RecipesHasIngridient recipeshasingridient) {
        return em.merge(recipeshasingridient);
    }

    public void delete(RecipesHasIngridient recipeshasingridient) {
        em.remove(em.merge(recipeshasingridient));
    }

    public RecipesHasIngridient get(Object idrecipeshasingridient) {
        return em.find(RecipesHasIngridient.class, idrecipeshasingridient);
    }
    
    
    public List seeAssignedIngridients(Recipe recipe) {
    	return em.createQuery(
    		"SELECT r FROM RecipesHasIngridient r WHERE r.recipe LIKE :custRecipe")
        	.setParameter("custRecipe", recipe)
    	    .getResultList();
    	}   

    
}
