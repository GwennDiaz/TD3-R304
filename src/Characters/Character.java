package Characters;

import Consommable.FoodCategory;
import Consommable.FoodItem;
import static java.lang.Math.max;
import static java.lang.Math.min;

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

    // NOUVEAU : Pour la règle des légumes consécutifs
    protected FoodCategory lastFoodCategory;

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

    // --- GETTERS ---
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

    public abstract String getType();

    // --- ACTIONS ---

    public void fight(Character opponent) {
        if (this.health <= 0 || opponent.getHealth() <= 0) {
            System.out.println(name + " or opponent is dead.");
            return;
        }
        System.out.println(name + " fights against " + opponent.getName());

        // La potion donne une force surhumaine
        int effectiveStrength = this.strength;
        if (this.magicPotionLevel > 0) {
            effectiveStrength *= 5;
        }

        // Calcul des dégâts infligés
        int damageInflicted = (effectiveStrength * (100 - opponent.getEndurance())) / 100;

        // Calcul des dégâts reçus (Gère l'INVINCIBILITÉ)
        int damageReceived = 0;
        if (this.magicPotionLevel > 0) {
            System.out.println(">>> " + this.name + " is INVINCIBLE thanks to the potion!");
            damageReceived = 0;
            // La potion baisse un peu après l'effort
            this.magicPotionLevel = max(0, this.magicPotionLevel - 10);
        } else {
            // Formule standard (corrigée pour être plus juste que /1000)
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

    public void cure(int careAmount) {
        if (this.health <= 0) {
            System.out.println(name + " is dead and cannot be treated!");
            return;
        }
        int oldHealth = this.health;
        this.setHealth(this.health + careAmount);
        System.out.println(name + " is treated (Health: " + oldHealth + " -> " + this.health + ")");
    }

    // CORRECTION MAJEURE : Prend un FoodItem, pas un int
    public void eat(FoodItem food) {
        if (this.health <= 0) {
            System.out.println(name + " is dead and cannot eat!");
            return;
        }

        System.out.println(name + " eats " + food.getName());

        // Réduction de la faim (valeur par défaut arbitraire de 20 ici)
        setHunger(max(0, this.hunger - 20));

        // Règle : Poisson pas frais = Mauvais pour la santé
        if (!food.isFresh()) {
            System.out.println("Yuck! It's not fresh! (-10 Health)");
            setHealth(this.health - 10);
        }

        // Règle : Légumes consécutifs = Mauvais pour la santé
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

    public void drinkMagicPotion(int quantity) {
        if (this.health <= 0) return;

        int oldLevel = this.magicPotionLevel;
        this.setMagicPotionLevel(this.magicPotionLevel + quantity);
        System.out.println(name + " drinks magic potion (+" + quantity + ")");

        // Effets secondaires positifs [cite: 299]
        this.cure(quantity / 2);
        this.setHunger(max(0, this.hunger - quantity / 3));

        // Règle : Trop de potion transforme en statue de granit (Mort)
        if (this.magicPotionLevel > 200) {
            System.out.println("!!! OVERDOSE !!! " + name + " turns into a GRANITE STATUE.");
            this.health = 0;
            this.name = name + " (Statue)";
        }
    }

    public void checkTrepas() {
        if (this.health <= 0) {
            this.health = 0;
            trepasser();
        }
    }

    public void trepasser() {
        System.out.println("☠ " + name + " (" + getType() + ") has passed away... RIP");
        this.health = 0;
        this.belligerence = 0;
    }

    public boolean isDead(){
        return this.health <= 0;
    }

    @Override
    public String toString() {
        // Safe : n'affiche pas Location pour éviter la boucle infinie
        return String.format("%s (%s) | Health: %d | Potion: %d", name, getType(), health, magicPotionLevel);
    }
}