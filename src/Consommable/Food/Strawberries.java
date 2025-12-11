package Consommable.Food;
import Consommable.FoodItem;
import Consommable.Freshness;
import Consommable.FoodCategory;

public class Strawberries extends FoodItem {
    public Strawberries() {
        super("Strawberries", Freshness.STALE, 15, 5, 0, 0, 0, FoodCategory.VEGETABLE, FoodCategory.INGREDIENT);
    }
}