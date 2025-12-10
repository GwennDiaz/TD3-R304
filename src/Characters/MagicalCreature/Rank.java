package Characters.MagicalCreature;

public enum Rank {
    OMEGA(1),   // Le plus bas
    GAMMA(2),
    BETA(3),
    ALPHA(5);   // Le chef

    private final int value;
    Rank(int value) { this.value = value; }
    public int getValue() { return value; }
}