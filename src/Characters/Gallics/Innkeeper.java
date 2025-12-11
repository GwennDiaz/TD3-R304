package Characters.Gallics;

import Characters.Character;
import Characters.Gender;
import Characters.Worker;

import static java.lang.Math.max;

/**
 * Represents a Gallic Innkeeper.
 * Innkeepers manage inns, host customers, and serve meals.
 */
public non-sealed class Innkeeper extends Gallic implements Worker {
    private int hostingCapacity;
    private int currentCustomers;

    /**
     * Constructs a new Innkeeper.
     *
     * @param name             The name of the innkeeper.
     * @param gender           The gender.
     * @param height           The height.
     * @param age              The age.
     * @param strength         The strength stat.
     * @param endurance        The endurance stat.
     * @param health           The initial health.
     * @param hunger           The initial hunger.
     * @param belligerence     The initial belligerence.
     * @param magicPotionLevel The magic potion level.
     * @param hostingCapacity  The maximum number of customers the inn can hold.
     */
    public Innkeeper(String name, Gender gender, double height, int age, int strength,
                     int endurance, int health, int hunger, int belligerence,
                     int magicPotionLevel, int hostingCapacity) {
        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);
        this.hostingCapacity = hostingCapacity;
        this.currentCustomers = 0;
    }

    public int getHostingCapacity() {
        return hostingCapacity;
    }

    public int getCurrentCustomers() {
        return currentCustomers;
    }

    /**
     * Attempts to admit a new customer into the inn.
     * @return true if the customer was admitted, false if the inn is full.
     */
    public boolean hostCustomer() {
        if (currentCustomers < hostingCapacity) {
            currentCustomers++;
            System.out.println(name + " welcomes a customer. (" + currentCustomers + "/" + hostingCapacity + ")");
            return true;
        } else {
            System.out.println(name + "'s inn is full!");
            return false;
        }
    }

    /**
     * Serves a meal to a customer to reduce their hunger.
     * @param customer The character receiving the meal.
     */
    public void serveMeal(Character customer) {
        customer.setHunger(max(0, customer.getHunger() - 30));
        System.out.println(name + " serves a meal to " + customer.getName());
    }

    @Override
    public void fight(Character adversaire) {
        System.out.println(name + " fights " + adversaire.getName());
        super.fight(adversaire);  // Uses the inherited method from Character
    }

    @Override
    public String getType() {
        return "Gallic Innkeeper";
    }

    /**
     * Performs the innkeeper's daily work duties.
     */
    @Override
    public void work() {
        System.out.println(name + " works at the inn serving customers.");
        setHunger(Math.min(100, getHunger() + 10));
        this.endurance = Math.max(0, this.endurance - 8);
    }
}