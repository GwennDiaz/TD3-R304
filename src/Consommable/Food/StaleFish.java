package Consommable.Food;

import Consommable.FoodItem;
import Consommable.Freshness;
import static Consommable.FoodCategory.*;

public class StaleFish extends FoodItem {
    public StaleFish() {
        super("StaleFish", Freshness.STALE, 10, -10, 0, 0, 0, FISH);
    }
}