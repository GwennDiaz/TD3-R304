package Characters.Romans;

import Characters.Character;
import Characters.Gender;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Legionnaire extends Roman {
    private String legion;
    private boolean inService;

    public Legionnaire(String name, Gender gender, double height, int age, int strength,
                       int endurance, int health, int hunger, int belligerence,
                       int niveauPotionMagique, String legion) {
        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, niveauPotionMagique);
        this.legion = legion;
        this.inService = true;
    }

    public String getLegion() {
        return legion;
    }
    public String getName() {
        return name;
    }
    public Gender getGender() {
        return gender;
    }

    public boolean isEnService() {
        return inService;
    }

    public void patrol() {
        if (inService) {
            System.out.println(name + " of the" + legion + " patrol.");
            setHunger(min(100, getHunger() + 15));
            this.endurance = max(0, this.endurance - 10);
        } else {
            System.out.println(name + " is not in service.");
        }
    }

    public void combattre(Character adversaire) {
        System.out.println(name + " combat contre " + adversaire.getName());
        int degats = this.strength / 10;
        adversaire.setHealth(max(0, adversaire.getHealth() - degats));
        setBelligerence(min(100, getBelligerence() + 10));
    }

    public void rest() {
        inService = false;
        System.out.println(name + " rest.");
        setHunger(max(0, getHunger() - 20));
        this.endurance = min(100, this.endurance + 20);
    }

    @Override
    public String getType() {
        return "Roman Legionnaire";
    }

    @Override
    public void fight(Character opponent) {
        System.out.println(name + " of the " + legion + " fight against " + opponent.getName());
        super.fight(opponent);
    }
}
