package dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import recipesdb.Recipe;
import recipesdb.User;
import recipesdb.UserHasRole;

@Stateless
public class RecipeDAO {

	@PersistenceContext
	EntityManager em;

	public void insert(Recipe recipe) {
		em.persist(recipe);
	}

	public Recipe update(Recipe recipe) {
		return em.merge(recipe);
	}

	public void delete(Recipe recipe) {
		em.remove(em.merge(recipe));
	}

	public Recipe get(Object idrecipe) {
		return em.find(Recipe.class, idrecipe);
	}

	public List validateAdd(String name) {
		return em.createQuery("SELECT r.idRecipes FROM Recipe r WHERE r.name LIKE :custName")
				.setParameter("custName", name).getResultList();
	}

	public List seeRecipe(String name) {
		return em.createQuery("SELECT r FROM Recipe r WHERE r.name LIKE :custName").setParameter("custName", name)
				.getResultList();
	}

	public List<Recipe> getNotAcceptedList() {
		List<Recipe> list = null;

		Query query = em.createQuery("select r from Recipe r WHERE r.accepted LIKE :custAccept")
				.setParameter("custAccept", (byte) 0);

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Recipe> getAcceptedList(Map<String, Object> searchName) {
		List<Recipe> list = null;

		String select = "select r ";
		String from = "from Recipe r ";
		String where = "where r.accepted like :custAccepted ";

		String name = (String) searchName.get("name");
		if (name != null) {

			where += "and r.name like :custName ";
		}

		Query query = em.createQuery(select + from + where);

		if (name != null) {
			query.setParameter("custName", name + "%");
		}

		query.setParameter("custAccepted", (byte) 1);

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Recipe> getYourList(Map<String, Object> searchName, User user) {
		List<Recipe> list = null;

		String select = "select r ";
		String from = "from Recipe r ";
		String where = "where r.accepted like :custAccepted and r.user.id like :custId ";

		String name = (String) searchName.get("name");
		if (name != null) {

			where += "and r.name like :custName ";
		}

		Query query = em.createQuery(select + from + where);

		if (name != null) {
			query.setParameter("custName", name + "%");
		}

		query.setParameter("custAccepted", (byte) 1);
		query.setParameter("custId", user.getIdUsers());

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	public List<Recipe> getAcceptedListPaginated(Map<String, Object> searchName, int pagesize, int offset) {
		List<Recipe> list = null;

		String select = "select r ";
		String from = "from Recipe r ";
		String where = "where r.accepted like :custAccepted ";

		String name = (String) searchName.get("name");
		if (name != null) {

			where += "and r.name like :custName ";
		}

		Query query = em.createQuery(select + from + where);

		if (name != null) {
			query.setParameter("custName", name + "%");
		}

		query.setParameter("custAccepted", (byte) 1);

		query.setMaxResults(pagesize).setFirstResult(offset);

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public int getAcceptedListCount(Map<String, Object> searchName) {
		int count = 0;

		String select = "select r ";
		String from = "from Recipe r ";
		String where = "where r.accepted like :custAccepted ";

		String name = (String) searchName.get("name");
		if (name != null) {

			where += "and r.name like :custName ";
		}

		Query query = em.createQuery(select + from + where);

		if (name != null) {
			query.setParameter("custName", name + "%");
		}

		query.setParameter("custAccepted", (byte) 1);

		try {
			List list = query.getResultList();
			count = list.size();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}
}
