package Characters.Romans;

import Characters.Character;
import Characters.Gender;

public abstract sealed class Roman extends Character permits Legionnaire, Prefect, General {
    public Roman(String name, Gender gender, double height, int age, int strength,
                 int endurance, int health, int hunger, int belligerence, int magicPotionLevel) {
        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);
    }
}