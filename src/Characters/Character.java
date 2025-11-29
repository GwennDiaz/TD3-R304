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

    public void fight (Character opponent){
        if (this.health <= 0){
            System.out.println(name + "is dead");
            return;
        }
        if(opponent.getHealth() <= 0){
            System.out.println(name + "is dead");
            return;
        }
        System.out.println(name + " fights against " + opponent.getName());

        int damageInflicted = (this.strength * (100 - opponent.getEndurance())) / 100;
        int damageReceived = (opponent.getStrength() * (100 - this.endurance)) / 1000;

        opponent.setHealth(opponent.getHealth() - damageInflicted);
        this.health -= damageReceived;

        System.out.println(name + " inflicts " + damageInflicted + " damage to " + opponent.getName());
        System.out.println(opponent.getName() + " inflicts " + damageReceived + " damage to " + name);

        this.setBelligerence(min(100, this.belligerence + 10));
        opponent.setBelligerence(Math.min(100, opponent.getBelligerence() + 10));

        this.checkTrepas();
        opponent.checkTrepas();

    }

    public void cure(int careAmount) {
        if (this.health <= 0) {
            System.out.println(name + " He is dead and can no longer be treated! ");
            return;
        }

        int oldHealth = this.health;
        this.setHealth(this.health + careAmount);
        System.out.println(name + " is treated with " + careAmount + " points (Healt: " + oldHealth + " -> " + this.health + ")");
    }

    public void eat(int quantity) {
        if (this.health <= 0) {
            System.out.println(name + " is dead and cant eat !");
            return;
        }

        int ancienneFaim = this.hunger;
        this.setHunger(this.hunger - quantity);
        System.out.println(name + " eats and reduces his hunger by " + quantity + " points (Hunge: " + ancienneFaim + " -> " + this.hunger + ")");
    }

    public void drinkMagicPotion(int quantity) {
        if (this.health <= 0) {
            System.out.println(name + " is dead and cant drink potion!");
            return;
        }

        int ancienNiveau = this.magicPotionLevel;
        this.setMagicPotionLevel(this.magicPotionLevel + quantity);
        System.out.println(name + " drinks a magic potion (+" + quantity + " points) (Level: " + ancienNiveau + " -> " + this.magicPotionLevel + ")");

        // The magic potion restores some health and reduces hunger.
        this.cure(quantity / 2);
        this.setHunger(max(0, this.hunger - quantity / 3));
    }

    public void checkTrepas() {
        if (this.health <= 0) {
            this.health = 0;
            trepasser();
        }
    }

    public void trepasser() {
        System.out.println(name + " (" + getType() + ") dies... RIP");
        this.health = 0;
        this.belligerence = 0;
    }

    public boolean isDead(){
        return this.health <= 0;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s, %d years, %.2fm\nStrength: %d, Endurance: %d, Health: %d, Hunger: %d, Belligerence: %d, Potion: %d",
                name, getType(), gender, age, height, strength, endurance, health, hunger, belligerence, magicPotionLevel);
    }
}