package Consommable.Food;

import Consommable.FoodItem;
import Consommable.Freshness;
import static Consommable.FoodCategory.*;

public class FairlyFreshFish extends FoodItem {
    public FairlyFreshFish() {
        super("FairlyFreshFish", Freshness.FAIRLYFRESH, 15, 0, 0, 0, 0, FISH);
    }
}