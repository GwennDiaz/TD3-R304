package Consommable.Food;

import Consommable.FoodItem;
import Consommable.Freshness;
import Consommable.FoodCategory;

public class Lobster extends FoodItem {
    public Lobster() {
        super(FoodCategory.FISH, FoodCategory.INGREDIENT, Freshness.FRESH , "Lobster",40, +10);
    }
}