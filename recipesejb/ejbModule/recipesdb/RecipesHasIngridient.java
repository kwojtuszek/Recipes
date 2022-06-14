package recipesdb;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the recipes_has_ingridients database table.
 * 
 */
@Entity
@Table(name="recipes_has_ingridients")
@NamedQuery(name="RecipesHasIngridient.findAll", query="SELECT r FROM RecipesHasIngridient r")
public class RecipesHasIngridient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idRecipesIngridients;

	private int amount;

	private String type;

	//bi-directional many-to-one association to Ingridient
	@ManyToOne
	@JoinColumn(name="Ingridients_idIngridients")
	private Ingridient ingridient;

	//bi-directional many-to-one association to Recipe
	@ManyToOne
	@JoinColumn(name="Recipes_idRecipes")
	private Recipe recipe;

	public RecipesHasIngridient() {
	}

	public int getIdRecipesIngridients() {
		return this.idRecipesIngridients;
	}

	public void setIdRecipesIngridients(int idRecipesIngridients) {
		this.idRecipesIngridients = idRecipesIngridients;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Ingridient getIngridient() {
		return this.ingridient;
	}

	public void setIngridient(Ingridient ingridient) {
		this.ingridient = ingridient;
	}

	public Recipe getRecipe() {
		return this.recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

}