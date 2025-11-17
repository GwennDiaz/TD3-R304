package Characters.Romans;

import Characters.Gender;

import static java.lang.Math.min;

public class Prefect extends Roman {
    private String province;
    private int popularity; // 0-100

    public Prefect(String name, Gender gender, double height, int age, int strength,
                   int endurance, int health, int hunger, int belligerence,
                   int magicPotionLevel, String province) {
        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);
        this.province = province;
        this.popularity = 50;
    }

    public String getProvince() {
        return province;
    }

    public int getPopularity() {
        return popularity;
    }

    public void administerProvince() {
        System.out.println(name + " administers the province of " + province + ".");
        popularity = min(100, popularity + 5);
    }

    public void collectTaxes() {
        System.out.println(name + " collects taxes in the province of " + province + ".");
        popularity = Math.max(0, popularity - 10);
        setBelligerence(min(100, getBelligerence() + 5));
    }

    public void organizeGames() {
        System.out.println(name + " organizes games in the province of " + province + ".");
        popularity = min(100, popularity + 15);
    }

    public void giveOrder(Legionnaire legionnaire) {
        System.out.println(name + " gives an order to " + legionnaire.getName() + ".");
    }

    @Override
    public String getType() {
        return "Roman Prefect";
    }
}