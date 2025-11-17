package Consommable.Potion;

public class PotionsEffect {
    private final int healthDelta;
    private final int hungerDelta;
    private final int belligerenceDelta;
    private final int magicLevelDelta;
    private final String description;

    public PotionsEffect(int healthDelta, int hungerDelta, int belligerenceDelta, int magicLevelDelta, String description) {
        this.healthDelta = healthDelta;
        this.hungerDelta = hungerDelta;
        this.belligerenceDelta = belligerenceDelta;
        this.magicLevelDelta = magicLevelDelta;
        this.description = description == null ? "" : description;
    }

    public int getHealthDelta() {
        return healthDelta;
    }

    public int getHungerDelta() {
        return hungerDelta;
    }

    public int getBelligerenceDelta() {
        return belligerenceDelta;
    }

    public int getMagicLevelDelta() {
        return magicLevelDelta;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "PotionEffect{" + "healthDelta=" + healthDelta + ", hungerDelta=" + hungerDelta + ", belligerenceDelta="
                + belligerenceDelta + ", magicLevelDelta=" + magicLevelDelta + ", description='" + description + '\'' + '}';
    }
}
