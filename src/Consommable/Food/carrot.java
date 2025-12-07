package Consommable.Food;

import Consommable.FoodItem;
import Consommable.Freshness;
import Consommable.FoodCategory;

public class carrot extends FoodItem {
    public carrot() {
        super(FoodCategory.VEGETABLE, FoodCategory.INGREDIENT,Freshness.FRESH , "carrot",+20, +5);
    }
}