package Consommable.Food;

import Consommable.FoodCategory;
import Consommable.Freshness;

import static Consommable.FoodCategory.FISH;
import static Consommable.Freshness.STALE;

public class StaleFish {
    private int hungerRestore=10;
    private int healthDelta=-10;
    private FoodCategory category =FISH;
    private Freshness fresh =STALE;
}
