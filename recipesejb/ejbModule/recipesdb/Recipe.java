package recipesdb;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the recipes database table.
 * 
 */
@Entity
@Table(name="recipes")
@NamedQuery(name="Recipe.findAll", query="SELECT r FROM Recipe r")
public class Recipe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idRecipes;

	private byte accepted;

	private int difficulty;

	private String name;

	private double price;

	private int rate;

	@Lob
	private String steps;

	private String type;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="Users_idUsers")
	private User user;

	//bi-directional many-to-one association to RecipesHasIngridient
	@OneToMany(mappedBy="recipe")
	private List<RecipesHasIngridient> recipesHasIngridients;

	public Recipe() {
	}

	public int getIdRecipes() {
		return this.idRecipes;
	}

	public void setIdRecipes(int idRecipes) {
		this.idRecipes = idRecipes;
	}

	public byte getAccepted() {
		return this.accepted;
	}

	public void setAccepted(byte accepted) {
		this.accepted = accepted;
	}

	public int getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getRate() {
		return this.rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getSteps() {
		return this.steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<RecipesHasIngridient> getRecipesHasIngridients() {
		return this.recipesHasIngridients;
	}

	public void setRecipesHasIngridients(List<RecipesHasIngridient> recipesHasIngridients) {
		this.recipesHasIngridients = recipesHasIngridients;
	}

	public RecipesHasIngridient addRecipesHasIngridient(RecipesHasIngridient recipesHasIngridient) {
		getRecipesHasIngridients().add(recipesHasIngridient);
		recipesHasIngridient.setRecipe(this);

		return recipesHasIngridient;
	}

	public RecipesHasIngridient removeRecipesHasIngridient(RecipesHasIngridient recipesHasIngridient) {
		getRecipesHasIngridients().remove(recipesHasIngridient);
		recipesHasIngridient.setRecipe(null);

		return recipesHasIngridient;
	}

}