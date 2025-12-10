package Characters.MagicalCreature.Lycanthrope;

import Characters.Character;
import Characters.Gender;
import Characters.MagicalCreature.AgeCategory;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Lycanthrope extends Character {

    // --- Specific Attributes ---
    private boolean humanForm;
    private int originalStrength;
    private int originalBelligerence;

    private AgeCategory ageCategory;
    private int dominationFactor; // Domination exerted - suffered
    private Rank rank;
    private double impetuosityFactor; // Impetuosity factor
    private Pack pack; // The pack (null if solitary)

    // Constructor
    public Lycanthrope(String name, Gender gender, double height, int age, int strength,
                       int endurance, int health, int hunger, int belligerence,
                       int magicPotionLevel, Rank rank, double impetuosity) {

        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);

        this.humanForm = true;
        this.originalStrength = strength;
        this.originalBelligerence = belligerence;

        this.rank = rank;
        this.impetuosityFactor = impetuosity;
        this.dominationFactor = 0;
        this.pack = null;

        defineAgeCategory(age);
    }

    // --- Internal Calculation Methods ---

    private void defineAgeCategory(int age) {
        if (age < 15) this.ageCategory = AgeCategory.YOUNG;
        else if (age > 50) this.ageCategory = AgeCategory.OLD;
        else this.ageCategory = AgeCategory.ADULT;
    }

    // Calculation of "Level" (Subjective quality criterion)
    public double getLevel() {
        double ageBonus = (ageCategory == AgeCategory.ADULT) ? 1.5 : 1.0;
        double rankBonus = rank.getPowerLevel() * 10;
        // Level = (Strength * AgeBonus) + DominationFactor + RankBonus
        return (originalStrength * ageBonus) + dominationFactor + rankBonus;
    }


    public void attemptDomination(Lycanthrope target) {
        if (this.humanForm || target.humanForm) {
            System.out.println(getName() + " cannot dominate while in human form.");
            return;
        }

        // Cannot attack the Alpha Female
        if (target.getPack() != null && target.getPack().isAlphaFemale(target)) {
            System.out.println(getName() + " cannot attack the Alpha Female " + target.getName());
            return;
        }

        // Perceived Strength (Strength + Impetuosity)
        double perceivedSelfStrength = this.strength * (1 + this.impetuosityFactor);

        // Rule: Can only attempt if target is considered inferior or equal
        if (perceivedSelfStrength < target.getStrength() && target.getRank() != Rank.ALPHA) {
            System.out.println(getName() + " feels too weak to dominate " + target.getName());
            return;
        }

        System.out.println(getName() + " attempts to dominate " + target.getName() + "!");

        // Resolution
        boolean success = (this.getLevel() > target.getLevel()) || (target.getRank() == Rank.OMEGA);

        if (success) {
            System.out.println(">>> Domination SUCCESSFUL!");
            this.dominationFactor++;
            target.sufferDomination();

            // Rank Exchange
            if (this.pack != null && this.pack == target.getPack()) {
                this.pack.processRankExchange(this, target);
            }
        } else {
            System.out.println(">>> Domination FAILED.");
            // Target becomes aggressive
            target.reactAggressively(this);
            this.dominationFactor--; // The aggressor loses face
        }
    }

    public void sufferDomination() {
        this.dominationFactor--;
        System.out.println(getName() + " submits and loses domination points.");
    }

    public void reactAggressively(Lycanthrope aggressor) {
        System.out.println(getName() + " reacts AGGRESSIVELY towards " + aggressor.getName() + "!");
        this.belligerence = min(100, this.belligerence + 10);
    }

    // --- Life Cycle ---

    public void age() {
        this.age++;
        AgeCategory oldCat = this.ageCategory;
        defineAgeCategory(this.age);

        if (oldCat != this.ageCategory) {
            System.out.println(getName() + " is now " + this.ageCategory);
            // Old wolves lose strength naturally
            if (this.ageCategory == AgeCategory.OLD) {
                this.originalStrength = max(1, (int)(originalStrength * 0.8));
            }
        }
    }

    // --- Pack Management ---

    public void setPack(Pack pack) {
        this.pack = pack;
        if (pack != null && !pack.getMembers().contains(this)) {
            pack.addMember(this);
        }
    }

    public void separateFromPack() {
        if (this.pack != null) {
            System.out.println(getName() + " leaves pack " + this.pack.getName() + " and becomes solitary.");
            this.pack.removeMember(this);
            this.pack = null;
            this.rank = Rank.OMEGA;
        }
    }

    // --- Communication (Howls) ---

    public void howl(String howlType) {
        if (humanForm) {
            System.out.println(getName() + " tries to howl but only shouts.");
            return;
        }

        System.out.println(getName() + " howls: " + howlType + " !");

        setBelligerence(min(100, getBelligerence() + 5));

        if (pack != null) {
            pack.broadcastHowl(this, howlType);
        } else {
            System.out.println("The howl is lost in the void (Solitary).");
        }
    }

    public void hearHowl(Lycanthrope emitter, String howlType) {
        if (this.getHealth() < 20) {
            System.out.println(getName() + " is too sick to react to " + emitter.getName());
            return;
        }

        System.out.println(getName() + " hears the howl (" + howlType + ") from " + emitter.getName());

        switch (howlType) {
            case "DOMINATION":
                if (this.getLevel() < emitter.getLevel()) {
                    System.out.println(" -> " + getName() + " howls in SUBMISSION.");
                    this.dominationFactor--;
                } else {
                    System.out.println(" -> " + getName() + " howls in AGGRESSION!");
                }
                break;
            case "BELONGING":
                break;
            default:
                System.out.println(" -> " + getName() + " listens.");
        }
    }

    // --- Display & Transformation ---

    public void displayCharacteristics() {
        System.out.println("\n--- Lycanthrope Sheet: " + getName() + " ---");
        System.out.println("Gender: " + getGender());
        System.out.println("Age Category: " + ageCategory + " (" + age + " years)");
        System.out.println("Strength: " + strength + " (Base: " + originalStrength + ")");
        System.out.println("Rank: " + rank);
        System.out.println("Domination Factor: " + dominationFactor);
        System.out.println("Level: " + getLevel());
        System.out.println("Pack: " + (pack != null ? pack.getName() : "Solitary"));
        System.out.println("Form: " + (humanForm ? "Human" : "Werewolf"));
        System.out.println("-----------------------------------");
    }

    @Override
    public String toString() {
        return super.toString() + " | Rank: " + rank + " | Level: " + getLevel();
    }

    public void transform() {
        if (humanForm) {
            humanForm = false;
            this.strength = (int)(originalStrength * 1.5 + (impetuosityFactor * 2));
            this.belligerence = min(100, originalBelligerence + 30);
            this.height = height * 1.2;
            System.out.println(getName() + " transforms into a Werewolf!");
        }
    }

    public void turnHuman() {
        if (!humanForm) {
            humanForm = true;
            this.strength = originalStrength;
            this.belligerence = originalBelligerence;
            this.height = height / 1.2;
            System.out.println(getName() + " returns to human form.");
        }
    }

    // Getters / Setters
    public AgeCategory getAgeCategory() { return ageCategory; }
    public Rank getRank() { return rank; }
    public void setRank(Rank rank) { this.rank = rank; }
    public int getDominationFactor() { return dominationFactor; }
    public Pack getPack() { return pack; }
    public boolean isHuman() { return humanForm; }

    public void setDominationFactor(int dominationFactor) {
        this.dominationFactor = dominationFactor;
    }

    @Override
    public String getType() {
        return humanForm ? "Lycanthrope (Human Form)" : "Lycanthrope (Wolf Form)";
    }
}