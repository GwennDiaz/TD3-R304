package Characters.Gallics;

import Characters.Character;
import Characters.Gender;
import Characters.Worker;

/**
 * Represents a Gallic Merchant.
 * Merchants can trade (buy/sell) and manage their money.
 */
public non-sealed class Merchant extends Gallic implements Worker {
    private int money;

    /**
     * Constructs a new Merchant.
     *
     * @param name             The name of the merchant.
     * @param gender           The gender.
     * @param height           The height.
     * @param age              The age.
     * @param strength         The strength stat.
     * @param endurance        The endurance stat.
     * @param health           The initial health.
     * @param hunger           The initial hunger.
     * @param belligerence     The initial belligerence.
     * @param magicPotionLevel The magic potion level.
     * @param money            The initial amount of money (gold coins).
     */
    public Merchant(String name, Gender gender, double height, int age, int strength,
                    int endurance, int health, int hunger, int belligerence,
                    int magicPotionLevel, int money) {
        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Sells items and increases money.
     * @param amount The value of the items sold.
     */
    public void sell(int amount) {
        this.money += amount;
        System.out.println(name + " sold items for " + amount + " gold coins.");
    }

    /**
     * Attempts to buy items using current money.
     * @param amount The cost of the items.
     * @return true if the purchase was successful, false if not enough money.
     */
    public boolean buy(int amount) {
        if (money >= amount) {
            this.money -= amount;
            System.out.println(name + " bought items for " + amount + " gold coins.");
            return true;
        } else {
            System.out.println(name + " does not have enough money!");
            return false;
        }
    }

    @Override
    public void fight(Character adversaire) {
        System.out.println(name + " fights " + adversaire.getName());
        super.fight(adversaire);  // Uses the inherited method from Character
    }

    @Override
    public String getType() {
        return "Gallic Merchant";
    }

    /**
     * Performs the merchant's daily work (selling goods).
     */
    @Override
    public void work() {
        System.out.println(name + " works by selling goods.");
        setHunger(Math.min(100, getHunger() + 15));
        this.endurance = Math.max(0, this.endurance - 10);
    }
}