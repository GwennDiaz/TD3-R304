package Consommable;

public class FoodItem {
    protected FoodCategory foodCategory;
    protected Freshness freshness;
    protected String name;
    protected int hungerLevel;
    protected int healthDelta;


    public FoodItem(FoodCategory foodCategory, Freshness freshness, String name,  int hungerLevel, int healthDelta) {
        this.foodCategory = foodCategory;
        this.freshness = freshness;
        this.name = name;
        this.hungerLevel = hungerLevel;
        this.healthDelta = healthDelta;
    }
    public FoodItem(FoodCategory foodCategory, FoodCategory foodCategory2,Freshness freshness, String name,  int hungerLevel, int healthDelta) {
        this.foodCategory = foodCategory;
        this.foodCategory = foodCategory2;
        this.freshness = freshness;
        this.name = name;
        this.hungerLevel = hungerLevel;
        this.healthDelta = healthDelta;
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
