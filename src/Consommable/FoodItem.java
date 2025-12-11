package Consommable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a consumable food item in the simulation.
 * It holds nutritional values, freshness status, and categories.
 */
public class FoodItem {
    protected List<FoodCategory> categories;
    protected Freshness freshness;
    protected String name;

    protected int hungerLevel;
    protected int healthDelta;
    protected int belligerence;
    protected int strengthDelta;
    protected int enduranceDelta;

    /**
     * Constructs a new FoodItem with specific properties.
     *
     * @param name           The name of the food.
     * @param freshness      The initial freshness state.
     * @param hungerLevel    The amount of hunger this item satisfies.
     * @param healthDelta    The effect on health (positive or negative).
     * @param belligerence   The effect on belligerence.
     * @param strengthDelta  The temporary bonus to strength.
     * @param enduranceDelta The temporary bonus to endurance.
     * @param cats           Variable arguments listing the categories this food belongs to.
     */
    public FoodItem(String name, Freshness freshness, int hungerLevel, int healthDelta, int belligerence, int strengthDelta, int enduranceDelta, FoodCategory... cats) {
        this.name = name;
        this.freshness = freshness;
        this.hungerLevel = hungerLevel;
        this.healthDelta = healthDelta;
        this.belligerence = belligerence;
        this.strengthDelta = strengthDelta;
        this.enduranceDelta = enduranceDelta;

        this.categories = new ArrayList<>(Arrays.asList(cats));
    }

    //---GETTER

    public int getHungerLevel() { return hungerLevel; }
    public int getHealthDelta() { return healthDelta; }
    public int getBelligerence() { return belligerence; }
    public int getStrengthDelta() { return strengthDelta; }
    public int getEnduranceDelta() { return enduranceDelta; }

    public String getName() {
        return name;
    }

    /**
     * Checks if the food is considered edible and fresh.
     *
     * @return true if the food is FRESH or FAIRLYFRESH, false if STALE.
     */
    public boolean isFresh() {
        return this.freshness == Freshness.FRESH || this.freshness == Freshness.FAIRLYFRESH;
    }

    /**
     * Retrieves the list of categories associated with this item.
     * Necessary for applying dietary rules (e.g., the vegetable rule).
     *
     * @return A list of FoodCategory.
     */
    public List<FoodCategory> getCategories() {
        return categories;
    }

    /**
     * Checks if the food belongs to a specific category.
     *
     * @param cat The category to check.
     * @return true if the food belongs to this category.
     */
    public boolean hasCategory(FoodCategory cat) {
        return categories.contains(cat);
    }

    /**
     * Simulates the natural decay of the food item.
     * Reduces the freshness level (e.g., FRESH -> FAIRLYFRESH -> STALE).
     */
    public void rot(){
        if (this.freshness == Freshness.FRESH) {
            this.freshness = Freshness.FAIRLYFRESH;
            System.out.println(this.name + " is beginning to rot...");
        } else if (this.freshness == Freshness.FAIRLYFRESH) {
            this.freshness = Freshness.STALE;
            System.out.println(this.name + " is stale!");
        }
    }

    @Override
    public String toString() {
        return name + " (" + freshness + ")";
    }
}