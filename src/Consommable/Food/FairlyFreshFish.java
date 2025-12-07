package Consommable.Food;

import Consommable.FoodItem;
import Consommable.Freshness;
import Consommable.FoodCategory;

public class FairlyFreshFish extends FoodItem {
    public FairlyFreshFish() {
        super(FoodCategory.FISH, FoodCategory.INGREDIENT, Freshness.FAIRLYFRESH , "FairlyFreshFish",+15, 0);
    }
}