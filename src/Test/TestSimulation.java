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

public class TestSimulation {

    /**
     * Vérifie que manger un poisson pas frais diminue la santé.
     * Basé sur la règle : "Manger du poisson pas frais est mauvais pour la santé".
     */
    @Test
    public void testRomainMangePoissonPasFrais() {
        // ARRANGE (Préparation)
        // Santé initiale mise à 100
        Legionnaire minus = new Legionnaire("Minus", Gender.MALE, 1.70, 20, 50, 50, 100, 50, 0, 0, "I");
        StaleFish poissonPourri = new StaleFish(); // Delta Santé : -10
        int santeAvant = minus.getHealth();

        // ACT (Action)
        minus.eat(poissonPourri);

        // ASSERT (Vérification)
        // La santé doit avoir baissé (100 - 10 = 90)
        assertEquals(90, minus.getHealth(), "La santé aurait dû baisser de 10 points après avoir mangé un poisson pourri.");
        assertTrue(minus.getHealth() < santeAvant, "La santé actuelle doit être inférieure à la santé d'origine.");
    }

    /**
     * Vérifie que manger un sanglier diminue la faim.
     * Les Romains se nourrissent de sanglier.
     */
    @Test
    public void testRomainMangeSanglier() {
        // ARRANGE
        // Faim initiale mise à 50
        Legionnaire brutus = new Legionnaire("Brutus", Gender.MALE, 1.80, 30, 60, 60, 80, 50, 0, 0, "X");
        Boar sanglier = new Boar(); // Niveau de faim (nourrissant) : 50

        // ACT
        brutus.eat(sanglier);

        // ASSERT
        // Faim : 50 - 50 = 0
        assertEquals(0, brutus.getHunger(), "La faim aurait dû tomber à 0 après ce repas.");
    }

    /**
     * Vérifie le calcul de l'effet cumulé des potions.
     * Une dose multiplie l'effet par le nombre de doses.
     */
    @Test
    public void testEffetPotion() {
        // ARRANGE
        // Effet de base : +50 magie
        PotionsEffect effetBase = new PotionsEffect(0, 0, 10, 50, "Magie");
        Potion potion = new Potion("Test Potion", Collections.emptyList(), effetBase, 5);

        // ACT
        // On consomme 2 doses -> L'effet devrait être doublé
        Potion.ConsumptionResult result = potion.consume(2, null);

        // ASSERT
        assertNotNull(result, "Le résultat de la consommation ne doit pas être nul.");
        assertNotNull(result.getEffect(), "L'effet résultant ne doit pas être nul.");

        // 50 * 2 = 100
        assertEquals(100, result.getEffect().getMagicLevelDelta(), "L'effet magique aurait dû être de 100 (50 * 2 doses).");

        // Vérification du message
        assertTrue(result.getMessage().contains("2 dose(s)"), "Le message devrait mentionner les 2 doses consommées.");
    }
}