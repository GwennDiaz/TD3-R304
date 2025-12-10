package Characters.Gallics;

import Characters.*;
import Characters.Character;
import Characters.Gender;
import Consommable.Potion.Potion;
import Consommable.Potion.PotionsEffect;
import Location.Location;
import java.util.Arrays;

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

    public void concocterMarmite(Location lieu) {
        System.out.println(name + " concoct a large pot of magic potion!");
        System.out.println("The pot is bubbling and giving off a mystical smoke...");
        PotionsEffect MagicEffect = new PotionsEffect(0, -50, 100, 100, "Superhuman Strength");
        Potion laMarmite = new Potion("Magic Potion", Arrays.asList("Gui", "Carottes", "Sel"), 10, 5, 5); // Ingrédients);
        lieu.setMarmite(laMarmite);

        System.out.println("A steaming pot is ready in the center of" + lieu.getName() + "!");

        setHunger(Math.min(100, getHunger() + 20));
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
        System.out.println(name + " combat " + adversaire.getName() + " avec sa magie !");
        fight(adversaire);  // Utilise la méthode héritée de Personnage
    }

    @Override
    public void work() {
        System.out.println(name + " travaille en préparant des potions et en soignant.");
        concocterMarmite();
    }

    @Override
    public void lead() {
        System.out.println(name + " dirige la communauté avec sa sagesse et sa magie.");
        setBelligerence(max(0, getBelligerence() - 10));
    }
}