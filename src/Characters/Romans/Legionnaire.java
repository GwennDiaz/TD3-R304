package Characters.Romans;

import Characters.Character;
import Characters.Gender;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Represents a Roman Legionnaire, a standard soldier of the Roman army.
 * Legionnaires can patrol, fight, and rest.
 */
public non-sealed class Legionnaire extends Roman {
    private String legion;
    private boolean inService;

    /**
     * Constructs a new Legionnaire.
     *
     * @param name             The name of the legionnaire.
     * @param gender           The gender.
     * @param height           The height.
     * @param age              The age.
     * @param strength         The strength stat.
     * @param endurance        The endurance stat.
     * @param health           The initial health.
     * @param hunger           The initial hunger.
     * @param belligerence     The initial belligerence.
     * @param levelMagicPotion The magic potion level.
     * @param legion           The name of the legion they belong to (e.g., "Legio I").
     */
    public Legionnaire(String name, Gender gender, double height, int age, int strength,
                       int endurance, int health, int hunger, int belligerence,
                       int levelMagicPotion, String legion) {
        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, levelMagicPotion);
        this.legion = legion;
        this.inService = true;
    }

    public String getLegion() {
        return legion;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    public boolean isEnService() {
        return inService;
    }

    /**
     * Performs a patrol duty.
     * Increases hunger and decreases endurance.
     */
    public void patrol() {
        if (inService) {
            System.out.println(name + " of the " + legion + " patrols.");
            setHunger(min(100, getHunger() + 15));
            this.endurance = max(0, this.endurance - 10);
        } else {
            System.out.println(name + " is not on duty.");
        }
    }

    /**
     * Rests to recover endurance and reduce hunger.
     * Sets the status to not in service.
     */
    public void rest() {
        inService = false;
        System.out.println(name + " rests.");
        setHunger(max(0, getHunger() - 20));
        this.endurance = min(100, this.endurance + 20);
    }

    @Override
    public String getType() {
        return "Roman Legionnaire";
    }

    /**
     * Engages in combat with an opponent.
     *
     * @param opponent The enemy character.
     */
    @Override
    public void fight(Character opponent) {
        System.out.println(name + " of the " + legion + " fights against " + opponent.getName());
        super.fight(opponent);
    }
}