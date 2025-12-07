package Consommable.Food;

import Consommable.FoodItem;
import Consommable.Freshness;
import Consommable.FoodCategory;

public class StaleFish extends FoodItem {
    public StaleFish() {
        super(FoodCategory.FISH, Freshness.STALE , "StaleFish",+10, -10);
    }
}