package recipesdb;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idRole;

	private byte aktywna;

	private String role_Name;

	//bi-directional many-to-one association to UserHasRole
	@OneToMany(mappedBy="role")
	private List<UserHasRole> userHasRoles;

	public Role() {
	}

	public int getIdRole() {
		return this.idRole;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}

	public byte getAktywna() {
		return this.aktywna;
	}

	public void setAktywna(byte aktywna) {
		this.aktywna = aktywna;
	}

	public String getRole_Name() {
		return this.role_Name;
	}

	public void setRole_Name(String role_Name) {
		this.role_Name = role_Name;
	}

	public List<UserHasRole> getUserHasRoles() {
		return this.userHasRoles;
	}

	public void setUserHasRoles(List<UserHasRole> userHasRoles) {
		this.userHasRoles = userHasRoles;
	}

	public UserHasRole addUserHasRole(UserHasRole userHasRole) {
		getUserHasRoles().add(userHasRole);
		userHasRole.setRole(this);

		return userHasRole;
	}

	public UserHasRole removeUserHasRole(UserHasRole userHasRole) {
		getUserHasRoles().remove(userHasRole);
		userHasRole.setRole(null);

		return userHasRole;
	}

}