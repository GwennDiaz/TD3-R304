package Consommable.Liquid;

import Consommable.FoodItem;
import Consommable.Freshness;
import Consommable.FoodCategory;

public class BeetrootJuice extends FoodItem {
    public BeetrootJuice() {
        super(FoodCategory.VEGETABLE, FoodCategory.INGREDIENT, Freshness.FRESH , "BeetrootJuice",+10, +2);
    }
}
