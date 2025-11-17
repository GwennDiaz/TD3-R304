package Characters.Gallics;

import Characters.Gender;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Blacksmith extends Gallic {
    private int weaponQuality; // 0-100

    public Blacksmith(String name, Gender gender, double height, int age, int strength,
                      int endurance, int health, int hunger, int belligerence,
                      int magicPotionLevel, int weaponQuality) {
        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);
        this.weaponQuality = weaponQuality;
    }

    public int getWeaponQuality() {
        return weaponQuality;
    }

    public void forgeWeapon() {
        System.out.println(name + " forges a weapon of quality " + weaponQuality + ".");

        setHunger(min(100, getHunger() + 10));

        this.endurance = max(0, this.endurance - 5);
    }

    public void repairWeapon() {
        System.out.println(name + " repairs a weapon.");
    }

    @Override
    public String getType() {
        return "Gallic Blacksmith";
    }
}