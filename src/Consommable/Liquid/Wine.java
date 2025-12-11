package Consommable.Liquid;
import Consommable.FoodCategory;
import Consommable.FoodItem;

public class Wine extends FoodItem {
    public Wine() {
        super("Wine", null, 15, 5, 8, -5, 10, FoodCategory.DRINK, FoodCategory.ALCOHOL);
    }
}