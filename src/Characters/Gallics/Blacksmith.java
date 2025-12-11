package Characters.Gallics;

import Characters.Gender;
import Characters.Worker;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Represents a Gallic Blacksmith.
 * Responsible for forging and repairing weapons.
 */
public non-sealed class Blacksmith extends Gallic implements Worker {
    private int weaponQuality; // 0-100

    /**
     * Constructs a new Blacksmith.
     *
     * @param name             The name of the blacksmith.
     * @param gender           The gender.
     * @param height           The height.
     * @param age              The age.
     * @param strength         The strength stat.
     * @param endurance        The endurance stat.
     * @param health           The initial health.
     * @param hunger           The initial hunger.
     * @param belligerence     The initial belligerence.
     * @param magicPotionLevel The magic potion level.
     * @param weaponQuality    The quality of weapons forged (0-100).
     */
    public Blacksmith(String name, Gender gender, double height, int age, int strength,
                      int endurance, int health, int hunger, int belligerence,
                      int magicPotionLevel, int weaponQuality) {
        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);
        this.weaponQuality = weaponQuality;
    }

    public int getWeaponQuality() {
        return weaponQuality;
    }

    /**
     * Forges a new weapon.
     * Increases hunger and decreases endurance.
     */
    public void forgeWeapon() {
        System.out.println(name + " forges a weapon of quality " + weaponQuality + ".");

        setHunger(min(100, getHunger() + 10));

        this.endurance = max(0, this.endurance - 5);
    }

    /**
     * Repairs a weapon.
     */
    public void repairWeapon() {
        System.out.println(name + " repairs a weapon.");
    }

    @Override
    public String getType() {
        return "Gallic Blacksmith";
    }

    /**
     * Performs the blacksmith's work task.
     */
    @Override
    public void work() {
        System.out.println(name + " works at the forge creating weapons.");
        forgeWeapon();
    }
}