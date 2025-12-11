package Consommable.Others;

import Consommable.FoodItem;

import static Consommable.FoodCategory.*;

public class Salt extends FoodItem {
    public Salt() {
        super("Salt", null, 2, -1, 0, 0, -2, CONDIMENT, INGREDIENT);
    }
}