package Characters;

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
    }

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

    public void setHealth(int health) {
        this.health = max(0, min(100, health));
    }

    public void setHunger(int hunger) {
        this.hunger = max(0, min(100, hunger));
    }

    public void setBelligerence(int belligerence) {
        this.belligerence = max(0, min(100, belligerence));
    }

    public void setMagicPotionLevel(int level) {
        this.magicPotionLevel = max(0, min(100, level));
    }

    public abstract String getType();

    @Override
    public String toString() {
        return String.format("%s (%s) - %s, %d years, %.2fm\nStrength: %d, Endurance: %d, Health: %d, Hunger: %d, Belligerence: %d, Potion: %d",
                name, getType(), gender, age, height, strength, endurance, health, hunger, belligerence, magicPotionLevel);
    }
}