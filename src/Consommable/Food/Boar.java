package Consommable.Food;

import Consommable.FoodItem;
import Consommable.FoodCategory;
import Consommable.Freshness;

public class Boar extends FoodItem {
    public Boar() {
        super(FoodCategory.MEAT, Freshness.FRESH , "Boar",50, 15);
    }
}