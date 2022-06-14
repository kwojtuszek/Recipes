package recipesweb;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.simplesecurity.RemoteClient;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import dao.UserDAO;
import recipesdb.User;
import dao.IngridientDAO;
import dao.RecipeDAO;
import dao.RecipesHasIngridientDAO;
import dao.RoleDAO;
import recipesdb.Ingridient;
import recipesdb.Recipe;
import recipesdb.RecipesHasIngridient;
import recipesdb.Role;
import dao.UserHasRoleDAO;
import recipesdb.UserHasRole;

@Named
@RequestScoped
public class RecipeBB implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String PAGE_LIST_INGRIDIENTS = "listIngridients?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_SEE_RECIPE = "seeRecipe?faces-redirect=true";
	private static final String PAGE_EDIT_RECIPE = "editRecipe?faces-redirect=true";
	private static final String PAGE_YOUR_RECIPES = "listYourRecipes?faces-redirect=true";
	private static final String PAGE_EDIT_ASSIGN = "editAssign?faces-redirect=true";

	private Ingridient ingridient = new Ingridient();
	private Recipe recipe = new Recipe();
	private RecipesHasIngridient recipeshasingridient = new RecipesHasIngridient();
	private User user = new User();
	private String name;
	private LazyDataModel<Recipe> lazyModel;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModel<Recipe>() {

			@Override
			public List<Recipe> load(int offset, int pagesize, Map<String, SortMeta> arg2,
					Map<String, FilterMeta> arg3) {

				List<Recipe> list = null;

				Map<String, Object> searchName = new HashMap<String, Object>();

				if (name != null && name.length() > 0) {
					searchName.put("name", name);
				}

				list = recipeDAO.getAcceptedListPaginated(searchName, pagesize, offset);

				this.setRowCount(recipeDAO.getAcceptedListCount(searchName));

				return list;

			}
			

		};
	}
	
	

	public LazyDataModel<Recipe> getLazyModel() {
		return lazyModel;
	}



	public Ingridient getIngridient() {
		return ingridient;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public RecipesHasIngridient getRecipesHasIngridient() {
		return recipeshasingridient;
	}

	public User getUser() {
		return user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@EJB
	IngridientDAO ingridientDAO;

	@EJB
	RecipeDAO recipeDAO;

	@EJB
	RecipesHasIngridientDAO recipeshasingridientDAO;

	@EJB
	UserDAO userDAO;

	@Inject
	FacesContext context;

	@Inject
	ExternalContext extcontext;

	@Inject
	Flash flash;

	public String addRecipe() {

		if (!recipeDAO.validateAdd(recipe.getName()).isEmpty()) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Przepis o tej nazwie istnieje.", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		RemoteClient<User> client = RemoteClient.load(session);

		try {

			user = client.getDetails();
			recipe.setRate(0);
			recipe.setAccepted((byte) 0);
			recipe.setUser(user);
			recipeDAO.insert(recipe);

			int id = (int) recipeDAO.validateAdd(recipe.getName()).get(0);
			recipe.setIdRecipes(id);

			HttpSession session2 = (HttpSession) extcontext.getSession(true);
			session2.setAttribute("recipe", recipe);

		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "B³ad zapisu.", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_LIST_INGRIDIENTS;

	}

	public List<Recipe> getNotAcceptedList() {
		return recipeDAO.getNotAcceptedList();

	}

	public String acceptRecipe(Recipe recipe) {

		recipe.setAccepted((byte) 1);

		recipeDAO.update(recipe);

		return PAGE_STAY_AT_THE_SAME;

	}

	public List<Recipe> getAcceptedList() {
		List<Recipe> list = null;

		Map<String, Object> searchName = new HashMap<String, Object>();

		if (name != null && name.length() > 0) {
			searchName.put("name", name);
		}

		list = recipeDAO.getAcceptedList(searchName);

		return list;
	}

	public List<Recipe> getYourList() {
		List<Recipe> list = null;

		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		RemoteClient<User> client = RemoteClient.load(session);

		user = client.getDetails();

		Map<String, Object> searchName = new HashMap<String, Object>();

		if (name != null && name.length() > 0) {
			searchName.put("name", name);
		}

		list = recipeDAO.getYourList(searchName, user);

		return list;
	}

	public String deleteRecipe(Recipe recipe) {

		recipeDAO.delete(recipe);
		return PAGE_STAY_AT_THE_SAME;
	}

	public String seeRecipe(Recipe recipe) {

		recipe = (Recipe) recipeDAO.seeRecipe(recipe.getName()).get(0);

		HttpSession session2 = (HttpSession) extcontext.getSession(true);
		session2.setAttribute("recipe", recipe);

		return PAGE_SEE_RECIPE;
	}

	public List<RecipesHasIngridient> getAssignedIngridients() {
		List<RecipesHasIngridient> list = null;

		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		recipe = (Recipe) session.getAttribute("recipe");

		return recipeshasingridientDAO.seeAssignedIngridients(recipe);
	}

	public String editView(Recipe recipe) {

		recipe = (Recipe) recipeDAO.seeRecipe(recipe.getName()).get(0);

		HttpSession session2 = (HttpSession) extcontext.getSession(true);
		session2.setAttribute("recipe", recipe);

		return PAGE_EDIT_RECIPE;
	}

	public String editRecipe() {

		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		recipe = (Recipe) session.getAttribute("recipe");
		try {
			recipeDAO.update(recipe);
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "B³¹d zapisu.", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_YOUR_RECIPES;
	}

	public String deleteAssign(RecipesHasIngridient recipeshasingridient) {

		recipeshasingridientDAO.delete(recipeshasingridient);
		return PAGE_STAY_AT_THE_SAME;
	}

	public String editAssignView(RecipesHasIngridient recipeshasingridient) {

		recipe = (Recipe) recipeDAO.seeRecipe(recipe.getName()).get(0);
		recipeshasingridient = (RecipesHasIngridient) recipeshasingridientDAO.seeAssignedIngridients(recipe).get(0);

		HttpSession session2 = (HttpSession) extcontext.getSession(true);
		session2.setAttribute("recipeshasingridient", recipeshasingridient);

		return PAGE_EDIT_ASSIGN;
	}

	public String editAssign() {

		HttpSession session2 = (HttpSession) extcontext.getSession(true);
		recipeshasingridient = (RecipesHasIngridient) session2.getAttribute("recipeshasingridient");
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "B³¹d zapisu. " + recipeshasingridient + " kek", null));

		try {
			recipeshasingridientDAO.update(recipeshasingridient);
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "B³¹d zapisu.", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_EDIT_RECIPE;

	}

}