package recipesweb;

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
import javax.faces.simplesecurity.RemoteClient;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import recipesdb.User;
import dao.RoleDAO;
import recipesdb.Ingridient;
import recipesdb.Recipe;
import recipesdb.Role;
import dao.UserHasRoleDAO;
import recipesdb.UserHasRole;

@Named
@RequestScoped
public class UserBB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final String PAGE_LOGIN = "login?faces-redirect=true";
	private static final String PAGE_CHANGE_ROLE = "changeRole?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_LIST_USERS = "listUsers?faces-redirect=true";
	
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
	
	public Role userRole() {
		
		role.setAktywna((byte) 1);
		role.setIdRole(2);
		role.setRole_Name("U¿ytkownik");
		
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
	
	@Inject
	ExternalContext extcontext;

	public String addUser() {
		
		
		if (!userDAO.validateCreate(user.getLogin()).isEmpty())  {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login zazjêty." , null));
            return PAGE_STAY_AT_THE_SAME;
		}
		
		try {
				userDAO.insert(user);
				
				Timestamp date = Timestamp.from(Instant.now());
				
				userhasrole.setUser(user);
				userhasrole.setRole(userRole());
				userhasrole.setAssigned("System");
				userhasrole.setDate(date);
				
				userhasroleDAO.insert(userhasrole);
				
				
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "B³ad zapisu.", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_LOGIN;
		
	}
	
	public List<UserHasRole> getFullList(){
		return userhasroleDAO.getFullList();

	}
	
	public String putUser(UserHasRole userhasrole) {
		
		int id = (int) userhasroleDAO.getId(userhasrole.getUser()).get(0);
		userhasrole.setIdUserRole(id);
		
		HttpSession session2 = (HttpSession) extcontext.getSession(true);
		session2.setAttribute("userhasrole", userhasrole);
		
		return PAGE_CHANGE_ROLE;
	}
	
	public String changeRole() {
		
		try {
		
			HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
			userhasrole = (UserHasRole) session.getAttribute("userhasrole");
			RemoteClient<User> client = RemoteClient.load(session);
			
			user = client.getDetails();
			
			
			role = (Role) roleDAO.findRole(role.getRole_Name()).get(0);
			
			userhasrole.setRole(role);
			userhasrole.setAssigned(user.getLogin());
			userhasrole.setDate(Timestamp.from(Instant.now()));
			
			userhasroleDAO.update(userhasrole);
		
			return PAGE_LIST_USERS;
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "B³ad zapisu. " + role.getAktywna() + " ", null));
			return PAGE_STAY_AT_THE_SAME;
		}
	}

}
