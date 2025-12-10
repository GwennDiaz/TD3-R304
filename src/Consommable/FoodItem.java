package Consommable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodItem {
    protected List<FoodCategory> categories;
    protected Freshness freshness;
    protected String name;

    protected int hungerLevel;
    protected int healthDelta;
    protected int belligerence;
    protected int strengthDelta;
    protected int enduranceDelta;

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

    public boolean isFresh() {
        return this.freshness == Freshness.FRESH || this.freshness == Freshness.FAIRLYFRESH;
    }

    // Necessary for the vegetable rule
    public List<FoodCategory> getCategories() {
        return categories;
    }

    public boolean hasCategory(FoodCategory cat) {
        return categories.contains(cat);
    }

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