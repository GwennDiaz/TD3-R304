package Consommable.Food;
import Consommable.FoodItem;
import Consommable.FoodCategory;
import Consommable.Freshness;

public class Boar extends FoodItem {
    public Boar() {
        super("Boar", Freshness.FRESH, 50, 15, 0, 0, 0, FoodCategory.MEAT);
    }
}