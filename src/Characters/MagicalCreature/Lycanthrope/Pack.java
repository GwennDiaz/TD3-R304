package Characters.MagicalCreature.Lycanthrope;

import Characters.Gender;
import Characters.MagicalCreature.AgeCategory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Pack {
    private String name;
    private List<Lycanthrope> members;

    // The Alpha couple
    private Lycanthrope maleAlpha;
    private Lycanthrope femaleAlpha;

    public Pack(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    // --- Membership ---

    public void addMember(Lycanthrope l) {
        if (!members.contains(l)) {
            members.add(l);
            l.setPack(this);
            System.out.println(l.getName() + " joined " + this.name);
            // If the pack has no alpha, try to establish one
            if (maleAlpha == null || femaleAlpha == null) {
                recalculateAlphaPair();
            }
        }
    }

    public void removeMember(Lycanthrope l) {
        if (members.contains(l)) {
            members.remove(l);
            l.setPack(null);

            // If Alpha leaves, chaos ensures, need new Alpha
            if (l == maleAlpha || l == femaleAlpha) {
                System.out.println("ALERT: An Alpha has left " + name + "!");
                if (l == maleAlpha) maleAlpha = null;
                if (l == femaleAlpha) femaleAlpha = null;
                recalculateAlphaPair();
            }
        }
    }

    // --- Hierarchy & Domination Logic ---

    public void processRankExchange(Lycanthrope winner, Lycanthrope loser) {
        Rank winnerRank = winner.getRank();
        Rank loserRank = loser.getRank();

        // Specific case: Winner challenges the Alpha Male
        if (loser == maleAlpha) {
            System.out.println("!!! THE ALPHA MALE HAS BEEN DEFEATED !!!");
            maleAlpha = winner;
            winner.setRank(Rank.ALPHA);
            loser.setRank(winnerRank);

            recalculateAlphaPair();
        }
        else {
            // Standard exchange
            System.out.println("Rank Swap: " + winner.getName() + " (" + loserRank + ") <-> " + loser.getName() + " (" + winnerRank + ")");
            winner.setRank(loserRank);
            loser.setRank(winnerRank);
        }
    }

    public void recalculateAlphaPair() {
        // Find best Male
        Lycanthrope potentialMale = members.stream()
                .filter(l -> l.getGender() == Gender.MALE && l.getAgeCategory() == AgeCategory.ADULT)
                .max(Comparator.comparingInt(Lycanthrope::getStrength))
                .orElse(null);

        // Find best Female
        Lycanthrope potentialFemale = members.stream()
                .filter(l -> l.getGender() == Gender.FEMALE && l.getAgeCategory() == AgeCategory.ADULT)
                .max(Comparator.comparingDouble(Lycanthrope::getLevel))
                .orElse(null);

        if (potentialMale != null) {
            if (maleAlpha != null && maleAlpha != potentialMale) {
                maleAlpha.setRank(Rank.BETA);
            }
            maleAlpha = potentialMale;
            maleAlpha.setRank(Rank.ALPHA);
        }

        if (potentialFemale != null) {
            if (femaleAlpha != null && femaleAlpha != potentialFemale) {
                femaleAlpha.setRank(Rank.BETA);
            }
            femaleAlpha = potentialFemale;
            femaleAlpha.setRank(Rank.ALPHA);
        }

        if (maleAlpha != null && femaleAlpha != null) {
            System.out.println("The Alpha Couple is now: " + maleAlpha.getName() + " & " + femaleAlpha.getName());
        }
    }

    // --- Natural Decay ---

    public void handleNaturalRankDecay() {
        System.out.println("--- Checking Rank Stability in " + name + " ---");
        for (Lycanthrope m : members) {
            if (m.getDominationFactor() < -2 && m.getRank() != Rank.OMEGA) {
                if (!isLastOfSexInRank(m)) {
                    Rank oldRank = m.getRank();
                    m.setRank(oldRank.previous());
                    System.out.println(m.getName() + " loses rank due to low domination (" + oldRank + " -> " + m.getRank() + ")");
                } else {
                    System.out.println(m.getName() + " has low domination but is the last " + m.getGender() + " " + m.getRank());
                }
            }
        }
    }

    private boolean isLastOfSexInRank(Lycanthrope l) {
        long count = members.stream()
                .filter(m -> m.getRank() == l.getRank() && m.getGender() == l.getGender())
                .count();
        return count <= 1;
    }

    // --- Reproduction ---

    public void reproduce() {
        if (maleAlpha == null || femaleAlpha == null) return;

        System.out.println("♥ Mating season for " + maleAlpha.getName() + " and " + femaleAlpha.getName() + " ♥");
        Random rand = new Random();
        int litterSize = 1 + rand.nextInt(7); // 1 to 7

        System.out.println("A litter of " + litterSize + " puppies is born!");

        for (int i = 0; i < litterSize; i++) {
            Rank birthRank = Rank.GAMMA;
            Lycanthrope baby = new Lycanthrope(
                    "Pup-" + rand.nextInt(999),
                    rand.nextBoolean() ? Gender.MALE : Gender.FEMALE,
                    0.3, 0, 10, 10, 100, 0, 0, 0,
                    birthRank, 0.0
            );
            this.addMember(baby);
        }
    }

    // --- Communication ---

    public void broadcastHowl(Lycanthrope sender, String howlType) {
        System.out.println(">> Pack " + name + " hears " + howlType);
        for (Lycanthrope m : members) {
            if (m != sender) {
                if (howlType.equals("BELONGING")) {
                    System.out.println(m.getName() + " responds to confirm belonging.");
                } else {
                    m.hearHowl(sender, howlType);
                }
            }
        }
    }

    public boolean isAlphaFemale(Lycanthrope l) {
        return l == femaleAlpha;
    }

    // --- Display ---

    public void displayCharacteristics() {
        System.out.println("\n=== PACK: " + name + " ===");
        if (maleAlpha != null) System.out.println("Alpha Male: " + maleAlpha.getName());
        if (femaleAlpha != null) System.out.println("Alpha Female: " + femaleAlpha.getName());

        List<Lycanthrope> sorted = members.stream()
                .sorted(Comparator.comparing((Lycanthrope l) -> l.getRank().getPowerLevel()).reversed()
                        .thenComparing(Lycanthrope::getLevel).reversed())
                .collect(Collectors.toList());

        for (Lycanthrope m : sorted) {
            System.out.println(" [" + m.getRank() + "] " + m.getName() + " (Dom: " + m.getDominationFactor() + ")");
        }
        System.out.println("===========================");
    }

    public String getName() { return name; }
    public List<Lycanthrope> getMembers() { return members; }
}