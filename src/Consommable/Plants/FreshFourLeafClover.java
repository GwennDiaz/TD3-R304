package Consommable.Plants;

import Consommable.FoodItem;
import Consommable.Freshness;
import static Consommable.FoodCategory.*;

public class FreshFourLeafClover extends FoodItem {
    public FreshFourLeafClover() {
        super("FreshFourLeafClover", Freshness.FRESH, 5, 5, 0, 0, 0, VEGETABLE, INGREDIENT);
    }
}