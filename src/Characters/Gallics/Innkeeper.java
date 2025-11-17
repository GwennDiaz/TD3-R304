package Characters.Gallics;

import Characters.Character;
import Characters.Gender;

import static java.lang.Math.max;

public class Innkeeper extends Gallic {
    private int hostingCapacity;
    private int currentCustomers;

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

    public void serveMeal(Character customer) {
        customer.setHunger(max(0, customer.getHunger() - 30));
        System.out.println(name + " serves a meal to " + customer.getName());
    }

    @Override
    public String getType() {
        return "Gallic Innkeeper";
    }
}