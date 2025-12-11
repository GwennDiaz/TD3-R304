package Characters.Romans;

import Characters.Gender;
import Characters.Leader;

import static java.lang.Math.min;

/**
 * Represents a Roman Prefect, an administrative official governing a province.
 * Prefects manage popularity through administration, taxes, and games.
 */
public non-sealed class Prefect extends Roman implements Leader {
    private String province;
    private int popularity; // 0-100

    /**
     * Constructs a new Prefect.
     *
     * @param name             The name of the prefect.
     * @param gender           The gender.
     * @param height           The height.
     * @param age              The age.
     * @param strength         The strength stat.
     * @param endurance        The endurance stat.
     * @param health           The initial health.
     * @param hunger           The initial hunger.
     * @param belligerence     The initial belligerence.
     * @param magicPotionLevel The magic potion level.
     * @param province         The name of the governed province.
     */
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

    /**
     * Administers the province, slightly increasing popularity.
     */
    public void administerProvince() {
        System.out.println(name + " administers the province of " + province + ".");
        popularity = min(100, popularity + 5);
    }

    /**
     * Collects taxes from the province.
     * Decreases popularity but increases belligerence (stress/power).
     */
    public void collectTaxes() {
        System.out.println(name + " collects taxes in the province of " + province + ".");
        popularity = Math.max(0, popularity - 10);
        setBelligerence(min(100, getBelligerence() + 5));
    }

    /**
     * Organizes games (circus/gladiators) to boost popularity.
     */
    public void organizeGames() {
        System.out.println(name + " organizes games in the province of " + province + ".");
        popularity = min(100, popularity + 15);
    }

    /**
     * Issues an order to a legionnaire.
     * @param legionnaire The subordinate soldier.
     */
    public void giveOrder(Legionnaire legionnaire) {
        System.out.println(name + " gives an order to " + legionnaire.getName() + ".");
    }


    @Override
    public String getType() {
        return "Roman Prefect";
    }

    /**
     * Leads the province with authority.
     */
    @Override
    public void lead() {
        System.out.println(name + " governs the province of " + province + " with authority.");
        administerProvince();
    }
}