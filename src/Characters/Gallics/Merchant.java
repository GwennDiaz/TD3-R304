package Characters.Gallics;

import Characters.Gender;

public class Merchant extends Gallic {
    private int money;

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

    public void sell(int amount) {
        this.money += amount;
        System.out.println(name + " sold for " + amount + " gold coins.");
    }

    public boolean buy(int amount) {
        if (money >= amount) {
            this.money -= amount;
            System.out.println(name + " bought for " + amount + " gold coins.");
            return true;
        } else {
            System.out.println(name + " does not have enough money!");
            return false;
        }
    }

    @Override
    public String getType() {
        return "Gallic Merchant";
    }
}