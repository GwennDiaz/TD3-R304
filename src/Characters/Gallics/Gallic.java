package Characters.Gallics;

import Characters.Character;
import Characters.Gender;
import Consommable.FoodItem;

public abstract class Gallic extends Character {
    public Gallic(String name, Gender gender, double height, int age, int strength,
                  int endurance, int health, int hunger, int belligerence, int magicPotionLevel) {
        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);
    }

    public void eat(FoodItem food) {
        // Check whether it is boar or fish.
        if (food.getName().equalsIgnoreCase("Boar") || food.getName().contains("Fish")) {
            super.eat(food);
        } else {
            System.out.println(name + " refuse de manger ça ! Ce n'est pas un régime de Gaulois.");
        }
    }
}