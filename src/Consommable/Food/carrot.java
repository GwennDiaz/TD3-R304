package Consommable.Food;

import Consommable.FoodItem;
import Consommable.Freshness;
import static Consommable.FoodCategory.*;

public class carrot extends FoodItem {
    public carrot() {
        super("carrot", Freshness.FRESH, 20, 5, 0, 0, 0, VEGETABLE, INGREDIENT);
    }
}