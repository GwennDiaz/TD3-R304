package Consommable.Liquid;

import Consommable.FoodItem;
import static Consommable.FoodCategory.*;

public class Mead extends FoodItem {
    public Mead() {
        super("Mead", null, 10, 2, 2, -2, 5, DRINK, ALCOHOL, INGREDIENT);
    }
}