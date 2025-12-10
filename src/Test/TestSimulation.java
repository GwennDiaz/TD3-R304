package Test;

import Characters.Gender;
import Characters.Romans.Legionnaire;
import Consommable.Food.StaleFish;
import Consommable.Food.Boar;
import Consommable.Potion.Potion;
import Consommable.Potion.PotionsEffect;
import java.util.Collections;

public class TestSimulation {

    public static void main(String[] args) {
        System.out.println("---START OF UNIT TESTS\n");

        testRomainMangePoissonPasFrais();
        testRomainMangeSanglier();
        testEffetPotion();

        System.out.println("\n---TESTING COMPLETE: EVERYTHING IS OK ");
    }

    // TEST 1: Check for disease
    public static void testRomainMangePoissonPasFrais() {
        System.out.print("[TEST 1] Romain eats fish that isn't fresh... ");

        Legionnaire minus = new Legionnaire("Minus", Gender.MALE, 1.70, 20, 50, 50, 100, 50, 0, 0, "I");
        StaleFish poissonPourri = new StaleFish(); // Poisson pas frais (-10 santé dans ton code ?)

        int santeAvant = minus.getHealth();
        minus.eat(poissonPourri);
        int santeApres = minus.getHealth();

        if (santeApres < santeAvant) {
            System.out.println("✅ SUCCESS (Health reduced by " + santeAvant + " to " + santeApres + ")");
        } else {
            System.out.println("❌ FAIL (Health has not decreased)");
        }
    }

    // TEST 2: Check the diet
    public static void testRomainMangeSanglier() {
        System.out.print("[TEST 2] Romain eats wild boar (authorized)... ");

        Legionnaire brutus = new Legionnaire("Brutus", Gender.MALE, 1.80, 30, 60, 60, 80, 50, 0, 0, "X");
        Boar sanglier = new Boar(); // Viande

        int hungerBefore = brutus.getHunger();
        brutus.eat(sanglier);
        int faimApres = brutus.getHunger();

        if (faimApres < hungerBefore) {
            System.out.println("✅ SUCCESS (Hunger has decreased)");
        } else {
            System.out.println("❌ FAIL (He didn't eat)");
        }
    }

    // TEST 3: Verify the potion calculation
    public static void testEffetPotion() {
        System.out.print("[TEST 3] Calculating Potion Effect... ");

        PotionsEffect effetBase = new PotionsEffect(0, 0, 10, 50, "Magie");
        Potion potion = new Potion("Test", Collections.emptyList(), effetBase, 5);

        // We consume 2 doses
        Potion.ConsumptionResult result = potion.consume(2, null);

        // The magical force is expected to double (50 * 2 = 100).
        if (result.getEffect().getMagicLevelDelta() == 100) {
            System.out.println("✅ SUCCESS (Double effect correct))");
        } else {
            System.out.println("❌ FAIL (Expected 100, Received " + result.getEffect().getMagicLevelDelta() + ")");
        }
    }
}