package recipesweb;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

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
public class IngridientBB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final String PAGE_LIST_INGRIDIENT = "listIngridients?faces-redirect=true";
	private static final String PAGE_LIST_EDIT_INGRIDIENT = "editAddIngridient?faces-redirect=true";
	private static final String PAGE_ASSIGN_INGRIDIENT = "assignIngridient?faces-redirect=true";
	private static final String PAGE_ASSIGN_EDIT_INGRIDIENT = "assignEditIngridient?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	private Ingridient ingridient = new Ingridient();
	private Recipe recipe = new Recipe();
	private RecipesHasIngridient recipeshasingridient = new RecipesHasIngridient();
	private Recipe loaded = null;
	private Ingridient loaded2 = null;
	
	public Ingridient getIngridient() {
		return ingridient;
	}
	
	public Recipe getRecipe() {
		return recipe;
	}
	
	public RecipesHasIngridient getRecipesHasIngridient() {
		return recipeshasingridient;
	}
	
	@EJB
	IngridientDAO ingridientDAO;
	
	@EJB
	RecipeDAO recipeDAO;
	
	@EJB
	RecipesHasIngridientDAO recipeshasingridientDAO;
	
	@Inject
	FacesContext context;

	@Inject
	Flash flash;
	
	@Inject
	ExternalContext extcontext;
	
	public List<Ingridient> getFullList(){
		return ingridientDAO.getFullList();

	}

	public String addIngridient() {
		
		
		if (!ingridientDAO.validateAdd(ingridient.getName()).isEmpty())  {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Sk³adnik o tej nazwie istnieje." , null));
            return PAGE_STAY_AT_THE_SAME;
		}
		
		try {
			
				ingridientDAO.insert(ingridient);	
				
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "B³ad zapisu.", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_LIST_INGRIDIENT;
		
	}
	
	public String addEditIngridient() {
		
		
		if (!ingridientDAO.validateAdd(ingridient.getName()).isEmpty())  {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Sk³adnik o tej nazwie istnieje." , null));
            return PAGE_STAY_AT_THE_SAME;
		}
		
		try {
			
				ingridientDAO.insert(ingridient);	
				
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "B³ad zapisu.", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_LIST_EDIT_INGRIDIENT;
		
	}
	
	public String putIngridient(Ingridient ingridient) {
		
		int id = (int) ingridientDAO.validateAdd(ingridient.getName()).get(0);
		ingridient.setIdIngridients(id);
		
		HttpSession session2 = (HttpSession) extcontext.getSession(true);
		session2.setAttribute("ingridient", ingridient);
		
		return PAGE_ASSIGN_INGRIDIENT;
	}
	
	public String putEditIngridient(Ingridient ingridient) {
		
		int id = (int) ingridientDAO.validateAdd(ingridient.getName()).get(0);
		ingridient.setIdIngridients(id);
		
		HttpSession session2 = (HttpSession) extcontext.getSession(true);
		session2.setAttribute("ingridient", ingridient);
		
		return PAGE_ASSIGN_EDIT_INGRIDIENT;
	}
	
	public String assignIngridient() {		
		
		try {
			
			HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
			recipe = (Recipe) session.getAttribute("recipe");
			ingridient = (Ingridient) session.getAttribute("ingridient");
			
			recipeshasingridient.setRecipe(recipe);
			recipeshasingridient.setIngridient(ingridient);
			recipeshasingridientDAO.insert(recipeshasingridient);
			
			flash.put("recipe", recipe);
			
	} catch (Exception e) {
		e.printStackTrace();
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "B³ad zapisu.", null));
		return PAGE_STAY_AT_THE_SAME;
	}
		
		return PAGE_LIST_INGRIDIENT;
	}

	
	public String assignEditIngridient() {
		

		
		
		try {
			
			HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
			recipe = (Recipe) session.getAttribute("recipe");
			ingridient = (Ingridient) session.getAttribute("ingridient");
			
			recipeshasingridient.setRecipe(recipe);
			recipeshasingridient.setIngridient(ingridient);
			recipeshasingridientDAO.insert(recipeshasingridient);
			
			flash.put("recipe", recipe);
			
	} catch (Exception e) {
		e.printStackTrace();
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "B³ad zapisu.", null));
		return PAGE_STAY_AT_THE_SAME;
	}
		
		return PAGE_LIST_EDIT_INGRIDIENT;
	}
}
