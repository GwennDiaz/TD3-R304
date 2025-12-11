package Characters.MagicalCreature.Lycanthrope;

import Characters.Gender;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Represents a colony of Lycanthropes.
 * It manages multiple packs and solitary wolves, handling the passage of time and global events.
 */
public class Colony {
    private List<Pack> packs;
    private List<Lycanthrope> solitaires;
    private int turnCount; // To manage time/seasons

    /**
     * Initializes a new, empty colony.
     */
    public Colony() {
        this.packs = new ArrayList<>();
        this.solitaires = new ArrayList<>();
        this.turnCount = 0;
    }

    /**
     * Adds an existing pack to the colony.
     * @param p The pack to add.
     */
    public void addPack(Pack p) { packs.add(p); }

    /**
     * Adds a solitary lycanthrope to the colony.
     * @param l The solitary lycanthrope.
     */
    public void addSolitaire(Lycanthrope l) { solitaires.add(l); }

    /**
     * Advances the simulation by one turn.
     * Handles aging, reproduction, hierarchy changes, and random events for all wolves.
     */
    public void nextTurn() {
        turnCount++;
        boolean isMatingSeason = (turnCount % 12 == 0); // E.g., once every 12 turns

        System.out.println("\n\n################################");
        System.out.println("### TURN " + turnCount + (isMatingSeason ? " [MATING SEASON]" : "") + " ###");
        System.out.println("################################");

        Random rand = new Random();

        // 1. Manage Packs
        for (Pack p : packs) {
            // Natural hierarchy evolution
            p.handleNaturalRankDecay();

            // Reproduction
            if (isMatingSeason) {
                p.reproduce();
            }

            // Random Events within Pack (Howls/Conflicts)
            if (!p.getMembers().isEmpty()) {
                Lycanthrope actor = p.getMembers().get(rand.nextInt(p.getMembers().size()));

                // 30% chance to howl
                if (rand.nextDouble() < 0.3) {
                    String[] types = {"BELONGING", "DOMINATION", "AGGRESSION"};
                    actor.howl(types[rand.nextInt(types.length)]);
                }

                // 20% chance to attempt domination on a random packmate
                if (rand.nextDouble() < 0.2 && p.getMembers().size() > 1) {
                    Lycanthrope target = p.getMembers().get(rand.nextInt(p.getMembers().size()));
                    if (actor != target) {
                        actor.attemptDomination(target);
                    }
                }
            }
        }

        // 2. Manage All Lycanthropes (Aging & Transformations)
        List<Lycanthrope> allWolves = getAllLycanthropes();
        Iterator<Lycanthrope> it = allWolves.iterator();

        while (it.hasNext()) {
            Lycanthrope l = it.next();

            // Aging
            l.age();

            // Transformation into human
            if (rand.nextDouble() < 0.05) { // 5% chance per turn
                l.turnHuman();
                // Possibility to leave the colony/die if human
                if (rand.nextDouble() < 0.1) { // 10% risk
                    System.out.println("CRITICAL: " + l.getName() + " left the colony permanently (as human).");
                    if (l.getPack() != null) l.getPack().removeMember(l);
                    else solitaires.remove(l);
                }
            } else {
                // Ensure they are wolves mostly
                if (l.isHuman()) l.transform();
            }
        }

        // 3. Manage Solitaires (Formation of new packs)
        manageSolitaires();
    }

    /**
     * Checks if solitary wolves can form new packs.
     * If a male and a female solitary meet, they start a new pack.
     */
    private void manageSolitaires() {
        // If a Male and Female solitary meet, they form a pack
        Lycanthrope male = null;
        Lycanthrope female = null;

        // Simple logic: find the first available pair
        for (Lycanthrope l : new ArrayList<>(solitaires)) {
            if (l.getGender() == Gender.MALE && male == null) male = l;
            if (l.getGender() == Gender.FEMALE && female == null) female = l;
        }

        if (male != null && female != null) {
            System.out.println(">>> NEW PACK FOUNDED by " + male.getName() + " and " + female.getName());
            Pack newPack = new Pack("Pack-" + turnCount);

            // Remove from solitaires
            solitaires.remove(male);
            solitaires.remove(female);

            // Add to new pack
            newPack.addMember(male);
            newPack.addMember(female);

            // Register pack
            this.packs.add(newPack);
        }
    }

    /**
     * Helper method to get a list of every lycanthrope in the colony.
     * @return A list containing all pack members and solitaires.
     */
    private List<Lycanthrope> getAllLycanthropes() {
        List<Lycanthrope> all = new ArrayList<>(solitaires);
        for (Pack p : packs) {
            all.addAll(p.getMembers());
        }
        return all;
    }

    /**
     * Displays the current status of the colony, including all packs and solitary wolves.
     */
    public void displayState() {
        System.out.println("\n--- COLONY STATUS ---");
        System.out.println("Total Packs: " + packs.size());
        for (Pack p : packs) {
            p.displayCharacteristics();
        }
        System.out.println("Solitaires: " + solitaires.size());
        for (Lycanthrope s : solitaires) {
            System.out.println(" - " + s.getName() + " (Solitary)");
        }
    }
}