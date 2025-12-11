package Characters.Gallics;

import Characters.*;
import Characters.Character;
import Characters.Gender;
import Consommable.Potion.Potion;
import Consommable.Potion.PotionsEffect;
import Location.Location;
import java.util.Arrays;
import Consommable.FoodItem;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Represents a Gallic Druid.
 * Druids are responsible for healing, brewing magic potions, and guiding the community.
 */
public non-sealed class Druid extends Gallic implements Fighter, Leader, Worker {
    private int magicPower;

    /**
     * Constructs a new Druid.
     *
     * @param name             The name of the druid.
     * @param gender           The gender.
     * @param height           The height.
     * @param age              The age.
     * @param strength         The strength stat.
     * @param endurance        The endurance stat.
     * @param health           The initial health.
     * @param hunger           The initial hunger.
     * @param belligerence     The initial belligerence.
     * @param magicPotionLevel The magic potion level.
     * @param magicPower       The specific magic power stat for druids.
     */
    public Druid(String name, Gender gender, double height, int age, int strength,
                 int endurance, int health, int hunger, int belligerence,
                 int magicPotionLevel, int magicPower) {
        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);
        this.magicPower = magicPower;
    }

    public int getMagicPower() {
        return magicPower;
    }

    /**
     * Creates a magic potion cauldron (Marmite) with fixed effects and places it in the location.
     * This method manually defines the potion properties.
     *
     * @param lieu The location where the cauldron will be placed.
     */
    public void concoctCauldron(Location lieu) {
        System.out.println(name + " lights the fire under the cauldron...");

        // Define the "Superhuman Strength" effect
        // Health=0, Hunger=-20, Belligerence=+50, Magic=+100
        PotionsEffect effet = new PotionsEffect(0, -20, 50, 100, "Superhuman Strength");

        // Create the Potion object
        Potion laMarmite = new Potion(
                "Magic Potion",
                Arrays.asList("Mistletoe", "Carrots", "Salt", "Lobster"),
                effet,
                10 // One pot = 10 servings
        );

        // Place the pot in the location
        if (lieu != null) {
            lieu.setMarmite(laMarmite);
            System.out.println("✨ " + name + " It's done! A steaming pot awaits in the center of " + lieu.getName());
        }

        // Tired
        this.endurance = Math.max(0, this.endurance - 20);
    }

    /**
     * Serves a ladle of potion to a specific target if the druid has enough magic power.
     *
     * @param target   The character receiving the potion.
     * @param quantity The amount of potion given.
     */
    public void preparePotion(Character target, int quantity) {
        if (magicPower >= 10) {
            target.setMagicPotionLevel(target.getMagicPotionLevel() + quantity);
            System.out.println(name + " pours a ladle of potion for " + target.getName() + " (+" + quantity + ")");
        } else {
            System.out.println(name + " is too tired to serve potion!");
        }
    }

    /**
     * Heals a target character based on the druid's magic power.
     *
     * @param target The character to heal.
     */
    public void heal(Character target) {
        int healing = magicPower / 2;
        target.setHealth(min(100, target.getHealth() + healing));
        System.out.println(name + " heals " + target.getName() + " (+" + healing + " health)");
    }

    /**
     * Attempts to brew a potion using ingredients available in the current location.
     * Consumes the ingredients if found.
     *
     * @param location The location to search for ingredients.
     */
    public void brewPotion(Location location) {
        System.out.println(name + " attempts to brew the Magic Potion...");

        // List of required ingredients names
        String[] requiredIngredients = {
                "Mistletoe", "Carrot", "Salt", "FreshFourLeafClover",
                "FairlyFreshFish", "RockOil", "Honey", "Mead", "SecretIngredient"
        };

        List<FoodItem> availableFood = location.getPresentFood();
        List<FoodItem> toConsume = new ArrayList<>();
        boolean missing = false;

        // Check availability
        for (String req : requiredIngredients) {
            FoodItem found = null;
            for (FoodItem item : availableFood) {
                if (item.getName().equalsIgnoreCase(req) && !toConsume.contains(item)) {
                    found = item;
                    break;
                }
            }
            if (found != null) {
                toConsume.add(found);
            } else {
                System.out.println("Missing ingredient: " + req);
                missing = true;
            }
        }

        if (missing) {
            System.out.println("FAILURE: Cannot brew potion. Ingredients missing.");
        } else {
            // Consume ingredients
            availableFood.removeAll(toConsume);
            System.out.println("SUCCESS: The cauldron bubbles! The potion is ready!");
            // Refill Druid's potion supply (abstract concept of the cauldron)
            this.magicPotionLevel = 100;

            setHunger(min(100, getHunger() + 20));
            this.endurance = max(0, this.endurance - 15);
        }
    }

    /**
     * Gathers mistletoe to increase magic power.
     */
    public void gatherMistletoe() {
        System.out.println(name + " gathers sacred mistletoe with his golden sickle.");
        magicPower = min(100, magicPower + 5);
    }

    @Override
    public String getType() {
        return "Gallic Druid";
    }

    @Override
    public void fight(Character adversaire) {
        System.out.println(name + " fights " + adversaire.getName() + " with his magic!");
        super.fight(adversaire);
    }

    /**
     * Performs the druid's daily work (making potions, healing).
     */
    @Override
    public void work() {
        System.out.println(name + " works on potions and healing.");
        gatherMistletoe();
        magicPower = min(100, magicPower + 10);
        this.endurance = max(0, this.endurance - 5);
    }

    @Override
    public void lead() {
        System.out.println(name + " guides the community with wisdom.");
        setBelligerence(max(0, getBelligerence() - 10));
    }
}