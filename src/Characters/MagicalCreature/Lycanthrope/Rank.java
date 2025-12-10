package Characters.MagicalCreature.Lycanthrope;

/**
 * Represents the hierarchy level within a Lycanthrope pack[cite: 40].
 * Based on Greek alphabet hierarchy.
 */
public enum Rank {
    // Ordered by power/status
    OMEGA(1),   // The lowest rank (scapegoats) [cite: 42]
    GAMMA(2),   // Regular members/Soldiers
    BETA(3),    // Lieutenants
    ALPHA(5);   // The leaders (Alpha couple)

    private final int powerLevel;

    Rank(int powerLevel) {
        this.powerLevel = powerLevel;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    /**
     * Returns the previous rank (lower status).
     * Example: BETA -> GAMMA. OMEGA stays OMEGA.
     */
    public Rank previous() {
        int ordinal = this.ordinal();
        if (ordinal > 0) {
            return values()[ordinal - 1];
        }
        return this;
    }
}