package Consommable.Food;

import Consommable.FoodItem;
import Consommable.Freshness;
import static Consommable.FoodCategory.*;

public class Lobster extends FoodItem {
    public Lobster() {
        super("Lobster", Freshness.FRESH, 40, 10, 0, 0, 0, FISH, INGREDIENT);
    }
}