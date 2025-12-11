package Characters.Romans;

import Characters.Character;
import Characters.Fighter;
import Characters.Gender;
import Characters.Leader;

import static java.lang.Math.min;

/**
 * Represents a Roman General, a high-ranking leader of the legion.
 * Generals can recruit soldiers, plan battles, and gain popularity through victories.
 */
public non-sealed class General extends Roman implements Fighter, Leader {
    private int victoryCount;
    private int soldierCount;
    private int popularity = 50;

    /**
     * Constructs a new Roman General.
     *
     * @param name             The name of the general.
     * @param gender           The gender of the general.
     * @param height           The height of the general.
     * @param age              The age of the general.
     * @param strength         The strength stat.
     * @param endurance        The endurance stat.
     * @param health           The initial health.
     * @param hunger           The initial hunger.
     * @param belligerence     The initial belligerence.
     * @param magicPotionLevel The magic potion level.
     * @param victoryCount     The initial number of victories.
     */
    public General(String name, Gender gender, double height, int age, int strength,
                   int endurance, int health, int hunger, int belligerence,
                   int magicPotionLevel, int victoryCount) {
        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);
        this.victoryCount = victoryCount;
        this.soldierCount = 0;
    }

    public int getVictoryCount() {
        return victoryCount;
    }

    public int getSoldierCount() {
        return soldierCount;
    }

    /**
     * Increases the number of soldiers under command.
     * @param number The number of new recruits.
     */
    public void recruitSoldiers(int number) {
        soldierCount += number;
        System.out.println(name + " recruits " + number + " soldiers. Total: " + soldierCount);
    }

    /**
     * Plans a battle strategy, increasing belligerence.
     */
    public void planBattle() {
        System.out.println(name + " plans a battle with " + soldierCount + " soldiers.");
        setBelligerence(min(100, getBelligerence() + 20));
    }

    /**
     * Records a victory and increases popularity.
     */
    public void winVictory() {
        victoryCount++;
        System.out.println(name + " wins a victory! Total: " + victoryCount + " victories.");
        popularity += 10;
    }

    public int getPopularity() {
        return popularity;
    }

    /**
     * Rallies the troops, boosting belligerence before a fight.
     */
    public void rallyTroops() {
        System.out.println(name + " rallies his troops before the battle!");
        setBelligerence(min(100, getBelligerence() + 15));
    }

    @Override
    public String getType() {
        return "Roman General";
    }

    /**
     * Engages in combat.
     * If the General wins (opponent dies and General survives), a victory is recorded.
     */
    @Override
    public void fight(Character opponent) {
        System.out.println("The General " + name + " personally fights against " + opponent.getName());
        super.fight(opponent);  // Uses the inherited method from Character

        // If the General wins, he wins a victory.
        if (!isDead() && opponent.isDead()) {
            winVictory();
        }
    }

    /**
     * Performs leadership duties.
     */
    @Override
    public void lead() {
        System.out.println(name + " leads his armies with strategy and courage.");
    }
}