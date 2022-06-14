package recipesdb;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ingridients database table.
 * 
 */
@Entity
@Table(name="ingridients")
@NamedQuery(name="Ingridient.findAll", query="SELECT i FROM Ingridient i")
public class Ingridient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idIngridients;

	private String name;

	//bi-directional many-to-one association to RecipesHasIngridient
	@OneToMany(mappedBy="ingridient")
	private List<RecipesHasIngridient> recipesHasIngridients;

	public Ingridient() {
	}

	public int getIdIngridients() {
		return this.idIngridients;
	}

	public void setIdIngridients(int idIngridients) {
		this.idIngridients = idIngridients;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RecipesHasIngridient> getRecipesHasIngridients() {
		return this.recipesHasIngridients;
	}

	public void setRecipesHasIngridients(List<RecipesHasIngridient> recipesHasIngridients) {
		this.recipesHasIngridients = recipesHasIngridients;
	}

	public RecipesHasIngridient addRecipesHasIngridient(RecipesHasIngridient recipesHasIngridient) {
		getRecipesHasIngridients().add(recipesHasIngridient);
		recipesHasIngridient.setIngridient(this);

		return recipesHasIngridient;
	}

	public RecipesHasIngridient removeRecipesHasIngridient(RecipesHasIngridient recipesHasIngridient) {
		getRecipesHasIngridients().remove(recipesHasIngridient);
		recipesHasIngridient.setIngridient(null);

		return recipesHasIngridient;
	}

}