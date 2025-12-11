package Consommable.Plants;

import Consommable.FoodItem;
import Consommable.Freshness;
import static Consommable.FoodCategory.*;

public class FourLeafCloverNotFresh extends FoodItem {
    public FourLeafCloverNotFresh() {
        super("FourLeafCloverNotFresh", Freshness.STALE, 2, -5, 0, 0, 0, VEGETABLE, INGREDIENT);
    }
}