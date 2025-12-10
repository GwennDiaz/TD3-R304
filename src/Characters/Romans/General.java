package Characters.Romans;

import Characters.Character;
import Characters.Fighter;
import Characters.Gender;
import Characters.Leader;

import static java.lang.Math.min;

public class General extends Roman implements Fighter, Leader {
    private int victoryCount;
    private int soldierCount;
    private int popularity = 50;

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

    public void recruitSoldiers(int number) {
        soldierCount += number;
        System.out.println(name + " recruits " + number + " soldiers. Total: " + soldierCount);
    }

    public void planBattle() {
        System.out.println(name + " plans a battle with " + soldierCount + " soldiers.");
        setBelligerence(min(100, getBelligerence() + 20));
    }

    public void winVictory() {
        victoryCount++;
        System.out.println(name + " wins a victory! Total: " + victoryCount + " victories.");
        popularity += 10;
    }

    public int getPopularity() {
        return popularity;
    }

    public void rallyTroops() {
        System.out.println(name + " rallies his troops before the battle!");
        setBelligerence(min(100, getBelligerence() + 15));
    }

    @Override
    public String getType() {
        return "Roman General";
    }

    @Override
    public void fight(Character opponent) {
        System.out.println("The General " + name + " personally fight against " + opponent.getName());
        fight(opponent);  // Uses the inherited method from Character

        // Bif the General wins, he wins a victory.
        if (!isDead() && opponent.isDead()) {
            winVictory();
        }
    }

    @Override
    public void lead() {
        System.out.println(name + " dirige ses armées avec stratégie et courage.");
    }
}