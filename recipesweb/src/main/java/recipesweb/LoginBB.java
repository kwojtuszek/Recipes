package recipesweb;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.simplesecurity.RemoteClient;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import recipesdb.User;
import dao.RoleDAO;
import recipesdb.Role;
import dao.UserHasRoleDAO;
import recipesdb.UserHasRole;

@Named
@RequestScoped
public class LoginBB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final String PAGE_RECIPES_LIST = "listRecipes?faces-redirect=true";
	private static final String PAGE_LOGIN = "login?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	private User user = new User();
	private UserHasRole userhasrole = new UserHasRole();
	private Role role = new Role();
	
	public User getUser() {
		return user;
	}
	
	public UserHasRole getUserHasRole() {
		return userhasrole;
	}
	
	public Role getRole() {
		return role;
	}	
	
	@EJB
	UserDAO userDAO;
	
	@EJB
	UserHasRoleDAO userhasroleDAO;
	
	@EJB
	RoleDAO roleDAO;
	
	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public String login() {
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		if (userDAO.validateLogin(user.getLogin(), user.getPass()).isEmpty())  {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "B³êdny login lub has³o."  , null));
            return PAGE_STAY_AT_THE_SAME;
		}
		
		try {
			
			int idUser = (int) userDAO.validateLogin(user.getLogin(), user.getPass()).get(0);
			user.setIdUsers(idUser);
			
			role = (Role) userhasroleDAO.findRoleId(user).get(0);
			
			RemoteClient<User> client = new RemoteClient<User>();
			client.setDetails(user);
			
			client.getRoles().add(role.getRole_Name());
			
			HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
			client.store(request);
		
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "B³ad zapisu.", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		
		return PAGE_RECIPES_LIST;
	}
	
	public String logout(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		//Invalidate session
		// - all objects within session will be destroyed
		// - new session will be created (with new ID)
		session.invalidate();
		return PAGE_LOGIN;
	}
		
}


