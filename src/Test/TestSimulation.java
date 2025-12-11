package Test;

import Characters.Gender;
import Characters.Romans.Legionnaire;
import Consommable.Food.StaleFish;
import Consommable.Food.Boar;
import Consommable.Potion.Potion;
import Consommable.Potion.PotionsEffect;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

/**
 * Contains the unit tests for core mechanics of the simulation,
 * including food consumption and magic potion effects.
 */
public class TestSimulation {

    /**
     * Verifies that eating stale fish reduces health.
     * Based on the rule: "Eating stale fish is bad for health".
     */
    @Test
    public void testRomainMangePoissonPasFrais() {
        // ARRANGE (Preparation)
        // Initial Health set to 100
        Legionnaire minus = new Legionnaire("Minus", Gender.MALE, 1.70, 20, 50, 50, 100, 50, 0, 0, "I");
        StaleFish poissonPourri = new StaleFish(); // Health Delta: -10
        int santeAvant = minus.getHealth();

        // ACT (Action)
        minus.eat(poissonPourri);

        // ASSERT (Verification)
        // Health should have decreased (100 - 10 = 90)
        assertEquals(90, minus.getHealth(), "Health should have decreased by 10 points after eating stale fish.");
        assertTrue(minus.getHealth() < santeAvant, "Current health must be less than original health.");
    }

    /**
     * Verifies that eating boar reduces hunger.
     * Romans feed on boar.
     */
    @Test
    public void testRomainMangeSanglier() {
        // ARRANGE
        // Initial Hunger set to 50
        Legionnaire brutus = new Legionnaire("Brutus", Gender.MALE, 1.80, 30, 60, 60, 80, 50, 0, 0, "X");
        Boar sanglier = new Boar(); // Hunger Level (nourishing): 50

        // ACT
        brutus.eat(sanglier);

        // ASSERT
        // Hunger: 50 - 50 = 0
        assertEquals(0, brutus.getHunger(), "Hunger should have dropped to 0 after this meal.");
    }

    /**
     * Verifies the calculation of the cumulative effect of potions.
     * One dose multiplies the effect by the number of doses consumed.
     */
    @Test
    public void testEffetPotion() {
        // ARRANGE
        // Base effect: +50 magic
        PotionsEffect effetBase = new PotionsEffect(0, 0, 10, 50, "Magic");
        Potion potion = new Potion("Test Potion", Collections.emptyList(), effetBase, 5);

        // ACT
        // Consume 2 doses -> The effect should be doubled
        Potion.ConsumptionResult result = potion.consume(2, null);

        // ASSERT
        assertNotNull(result, "The consumption result must not be null.");
        assertNotNull(result.getEffect(), "The resulting effect must not be null.");

        // 50 * 2 = 100
        assertEquals(100, result.getEffect().getMagicLevelDelta(), "The magic effect should have been 100 (50 * 2 doses).");

        // Message verification
        assertTrue(result.getMessage().contains("2 dose(s)"), "The message should mention the 2 doses consumed.");
    }
}