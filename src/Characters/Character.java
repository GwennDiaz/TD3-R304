package Characters;

import Consommable.FoodCategory;
import Consommable.FoodItem;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Abstract base class representing any character in the simulation (Gaul, Roman, or Creature).
 * It defines common attributes like health, strength, and common actions like fighting or eating.
 */
public abstract class Character {
    protected String name;
    protected Gender gender;
    protected double height;
    protected int age;
    protected int strength;
    protected int endurance;
    protected int health;
    protected int hunger;
    protected int belligerence;
    protected int magicPotionLevel;

    /**
     * Tracks the category of the last food item eaten to apply dietary rules.
     */
    protected FoodCategory lastFoodCategory;

    /**
     * Constructs a new Character with specific stats.
     *
     * @param name             The name of the character.
     * @param gender           The gender of the character.
     * @param height           The height in meters.
     * @param age              The age in years.
     * @param strength         The physical strength.
     * @param endurance        The physical endurance.
     * @param health           The current health points (0-100).
     * @param hunger           The current hunger level (0-100).
     * @param belligerence     The current belligerence/aggressiveness level.
     * @param magicPotionLevel The current level of magic potion effect.
     */
    public Character(String name, Gender gender, double height, int age, int strength,
                     int endurance, int health, int hunger, int belligerence, int magicPotionLevel) {
        this.name = name;
        this.gender = gender;
        this.height = height;
        this.age = age;
        this.strength = strength;
        this.endurance = endurance;
        this.health = health;
        this.hunger = hunger;
        this.belligerence = belligerence;
        this.magicPotionLevel = magicPotionLevel;
        this.lastFoodCategory = null;
    }

    //---GETTERS
    public String getName() { return name; }
    public Gender getGender() { return gender; }
    public double getHeight() { return height; }
    public int getAge() { return age; }
    public int getStrength() { return strength; }
    public int getEndurance() { return endurance; }
    public int getHealth() { return health; }
    public int getHunger() { return hunger; }
    public int getBelligerence() { return belligerence; }
    public int getMagicPotionLevel() { return magicPotionLevel; }

    // --- SETTERS ---
    public void setHealth(int health) { this.health = max(0, min(100, health)); }
    public void setHunger(int hunger) { this.hunger = max(0, min(100, hunger)); }
    public void setBelligerence(int belligerence) { this.belligerence = max(0, min(100, belligerence)); }
    public void setMagicPotionLevel(int level) { this.magicPotionLevel = max(0, min(100, level)); }

    /**
     * @return A string description of the specific character type (e.g., "Roman Legionnaire").
     */
    public abstract String getType();

    //---ACTIONS

    /**
     * Engages in combat with another character.
     * Calculates damage based on strength and endurance.
     * Considers magic potion effects (invincibility and strength boost).
     *
     * @param opponent The character to fight against.
     */
    public void fight(Character opponent) {
        if (this.health <= 0 || opponent.getHealth() <= 0) {
            System.out.println(name + " or opponent is dead.");
            return;
        }
        System.out.println(name + " fights against " + opponent.getName());

        // The potion gives superhuman strength.
        int effectiveStrength = this.strength;
        if (this.magicPotionLevel > 0) {
            effectiveStrength *= 5;
        }

        // Damage calculation
        int damageInflicted = (effectiveStrength * (100 - opponent.getEndurance())) / 100;

        // Damage Taken Calculation (INVINCIBILITY)
        int damageReceived = 0;
        if (this.magicPotionLevel > 0) {
            System.out.println(">>> " + this.name + " is INVINCIBLE thanks to the potion!");
            damageReceived = 0;
            // The potion drops a little after exertion.
            this.magicPotionLevel = max(0, this.magicPotionLevel - 10);
        } else {
            damageReceived = (opponent.getStrength() * (100 - this.endurance)) / 100;
        }

        opponent.setHealth(opponent.getHealth() - damageInflicted);
        this.health -= damageReceived;

        System.out.println(name + " inflicts " + damageInflicted + " damage to " + opponent.getName());
        if (damageReceived > 0) {
            System.out.println(opponent.getName() + " inflicts " + damageReceived + " damage to " + name);
        }

        this.setBelligerence(min(100, this.belligerence + 10));
        opponent.setBelligerence(Math.min(100, opponent.getBelligerence() + 10));

        this.checkTrepas();
        opponent.checkTrepas();
    }

    /**
     * Heals the character by a specified amount.
     *
     * @param careAmount The amount of health to restore.
     */
    public void cure(int careAmount) {
        if (this.health <= 0) {
            System.out.println(name + " is dead and cannot be treated!");
            return;
        }

        int oldHealth = this.health;
        this.setHealth(this.health + careAmount);
        System.out.println(name + " is treated (Health: " + oldHealth + " -> " + this.health + ")");
    }

    /**
     * Consumes a food item.
     * Applies effects on hunger, health, and belligerence.
     * Enforces rules about fresh food and consecutive vegetables.
     *
     * @param food The food item to eat.
     */
    public void eat(FoodItem food) {
        if (this.health <= 0) {
            System.out.println(name + " is dead and cannot eat!");
            return;
        }

        System.out.println(name + " eats " + food.getName());

        // Hunger reduction (arbitrary default value of 20 here)
        setHunger(max(0, this.hunger - 20));

        // Rule: Fish that is not fresh = Bad for your health
        if (!food.isFresh()) {
            System.out.println("Yuck! It's not fresh! (-10 Health)");
            this.setHealth(this.health - 10);
        } else {
            // Fresh food
            this.setHunger(this.hunger - food.getHungerLevel());
            this.setHealth(this.health + food.getHealthDelta());
            this.setBelligerence(this.belligerence + food.getBelligerence());
        }

        // Rule: Consecutive vegetables = Bad for your health
        boolean isVegetable = food.getCategories().contains(FoodCategory.VEGETABLE);

        if (isVegetable) {
            if (this.lastFoodCategory == FoodCategory.VEGETABLE) {
                System.out.println("Vegetables again?! That's bad for health! (-5 Health)");
                setHealth(this.health - 5);
            }
            this.lastFoodCategory = FoodCategory.VEGETABLE;
        } else {
            this.lastFoodCategory = FoodCategory.MEAT; // Réinitialise la série
        }

        checkTrepas();
    }

    /**
     * Consumes magic potion.
     * Handles positive effects (health, hunger) and overdose risk (Granite Statue).
     *
     * @param quantity The amount of potion consumed.
     */
    public void drinkMagicPotion(int quantity) {
        if (this.health <= 0) return;

        int oldLevel = this.magicPotionLevel;
        this.setMagicPotionLevel(this.magicPotionLevel + quantity);
        System.out.println(name + " drinks magic potion (+" + quantity + ")");

        // The magic potion restores some health and reduces hunger.
        this.cure(quantity / 2);
        this.setHunger(max(0, this.hunger - quantity / 3));

        //Too much potion turns you into a granite statue (Death)
        if (this.magicPotionLevel > 200) {
            System.out.println("!!! OVERDOSE !!! " + name + " turns into a GRANITE STATUE.");
            this.health = 0;
            this.name = name + " (Statue)";
        }
    }

    /**
     * Checks if the character's health has dropped to zero or below.
     * Triggers passAway() if true.
     */
    public void checkTrepas() {
        if (this.health <= 0) {
            this.health = 0;
            passAway();
        }
    }

    /**
     * Handles the character's death.
     */
    public void passAway() {
        System.out.println("☠ " + name + " (" + getType() + ") has passed away... RIP");
        this.health = 0;
        this.belligerence = 0;
    }

    /**
     * @return true if the character is dead (health less than or equal to 0).
     */
    public boolean isDead(){
        return this.health <= 0;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) | Health: %d | Potion: %d", name, getType(), health, magicPotionLevel);
    }
}