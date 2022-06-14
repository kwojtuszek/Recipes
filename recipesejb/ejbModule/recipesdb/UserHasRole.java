package recipesdb;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the user_has_role database table.
 * 
 */
@Entity
@Table(name="user_has_role")
@NamedQuery(name="UserHasRole.findAll", query="SELECT u FROM UserHasRole u")
public class UserHasRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUserRole;

	private String assigned;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	//bi-directional many-to-one association to Role
	@ManyToOne
	private Role role;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="Users_idUsers")
	private User user;

	public UserHasRole() {
	}

	public int getIdUserRole() {
		return this.idUserRole;
	}

	public void setIdUserRole(int idUserRole) {
		this.idUserRole = idUserRole;
	}

	public String getAssigned() {
		return this.assigned;
	}

	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}