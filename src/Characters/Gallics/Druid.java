package Characters.Gallics;

import Characters.*;
import Characters.Character;
import Characters.Gender;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Druid extends Gallic implements Fighter, Leader, Worker {
    private int magicPower;

    public Druid(String name, Gender gender, double height, int age, int strength,
                 int endurance, int health, int hunger, int belligerence,
                 int magicPotionLevel, int magicPower) {
        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);
        this.magicPower = magicPower;
    }

    public int getMagicPower() {
        return magicPower;
    }

    public void preparePotion(Character target, int quantity) {
        if (magicPower >= 50) {
            target.setMagicPotionLevel(target.getMagicPotionLevel() + quantity);
            System.out.println(name + " prepares a magic potion for " + target.getName() + " (+" + quantity + ")");
        } else {
            System.out.println(name + " does not have enough magic power to prepare a potion!");
        }
    }

    public void heal(Character target) {
        int healing = magicPower / 2;
        target.setHealth(min(100, target.getHealth() + healing));
        System.out.println(name + " heals " + target.getName() + " (+" + healing + " health)");
    }

    public void concocterMarmite() {
        System.out.println(name + " concoct a large pot of magic potion!");
        System.out.println("The pot is bubbling and giving off a mystical smoke...");
        this.magicPotionLevel = 100;
        setHunger(min(100, getHunger() + 20));
        this.endurance = max(0, this.endurance - 15);
    }

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
        System.out.println(name + " fight " + adversaire.getName() + " with its magic!");
        super.fight(adversaire);  // Uses the inherited method from Character
    }

    @Override
    public void work() {
        System.out.println(name + " works by preparing potions and healing.");
        concocterMarmite();
    }

    @Override
    public void lead() {
        System.out.println(name + " leads the community with his wisdom and magic");
        setBelligerence(max(0, getBelligerence() - 10));
    }
}