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


    public FoodItem(String name, Freshness freshness, int hungerLevel, int healthDelta, int belligerence, int strengthDelta, int enduranceDelta, FoodCategory... cats)
    { //allows you to skip 1, 2, or 3 categories
        this.name = name;
        this.freshness = freshness;
        this.hungerLevel = hungerLevel;
        this.healthDelta = healthDelta;
        this.belligerence = belligerence;
        this.strengthDelta = strengthDelta;
        this.enduranceDelta = enduranceDelta;

        // Converting the category table into a list
        this.categories = new ArrayList<>(Arrays.asList(cats));
    }


    public String getName() {
        return name;
    }
    public boolean isFresh() {
        return this.freshness == Freshness.FRESH || this.freshness == Freshness.FAIRLYFRESH;
    }

    //to rot food
    public void rot(){
        if (this.freshness == Freshness.FRESH) {
            this.freshness = Freshness.FAIRLYFRESH;
            System.out.println(this.name + " beggining to rot...");
        } else if (this.freshness == Freshness.FAIRLYFRESH) {
            this.freshness = Freshness.STALE;
            System.out.println(this.name + " is outdated !");
        }
    }


}
