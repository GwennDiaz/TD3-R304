package Consommable.Food;

import Consommable.FoodItem;
import Consommable.Freshness;
import Consommable.FoodCategory;

public class Strawberries extends FoodItem {
    public Strawberries() {
        super(FoodCategory.VEGETABLE, FoodCategory.INGREDIENT, Freshness.STALE , "Strawberries",+15, +5);
    }
}
