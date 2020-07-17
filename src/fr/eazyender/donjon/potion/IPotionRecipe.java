package fr.eazyender.donjon.potion;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class IPotionRecipe {
	
	private List<ItemStack> ingredients;
	private ItemStack craft;
	private int cost;
	private int time;
	private int level;
	private int difficulty;
	
	public IPotionRecipe(List<ItemStack> ingredients, ItemStack craft, int cost, int time, int level, int difficulty) {
		this.ingredients = ingredients;
		this.craft = craft;
		this.cost = cost;
		this.time = time;
		this.level = level;
		this.difficulty = difficulty;
	}
	
	

	public int getDifficulty() {
		return difficulty;
	}



	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}



	public List<ItemStack> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<ItemStack> ingredients) {
		this.ingredients = ingredients;
	}

	public ItemStack getCraft() {
		return craft;
	}

	public void setCraft(ItemStack craft) {
		this.craft = craft;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	

}
