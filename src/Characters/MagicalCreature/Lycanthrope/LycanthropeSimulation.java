package Characters.MagicalCreature.Lycanthrope;

import Characters.Gender;

public class LycanthropeSimulation {

    public static void main(String[] args) {
        System.out.println("=== START OF LYCANTHROPE COLONY SIMULATION ===");

        // 1. Creating the Colony (Manager)
        Colony colony = new Colony();

        // 2. Creating the initial Pack
        Pack winterfell = new Pack("Winterfell");

        // 3. Creating Wolves (Objects)
        Lycanthrope jon = new Lycanthrope("Jon", Gender.MALE, 1.80, 25, 80, 80, 100, 0, 50, 0, Rank.BETA, 0.5);
        Lycanthrope ygritte = new Lycanthrope("Ygritte", Gender.FEMALE, 1.70, 24, 75, 75, 100, 0, 60, 0, Rank.BETA, 0.6);
        Lycanthrope theon = new Lycanthrope("Theon", Gender.MALE, 1.75, 30, 40, 40, 80, 0, 10, 0, Rank.OMEGA, 0.1);

        // Transforming them so they interact as wolves
        jon.transform();
        ygritte.transform();
        theon.transform();

        // 4. Adding members to the pack
        // Addition triggers automatic hierarchy checks
        winterfell.addMember(jon);
        winterfell.addMember(ygritte);
        winterfell.addMember(theon);

        // 5. Adding the Pack to the Colony
        colony.addPack(winterfell);

        // 6. Simulation Loop
        for (int i = 1; i <= 15; i++) {
            colony.nextTurn();    // Handles aging, conflicts, reproduction, etc.
            colony.displayState(); // Displays the current state of the colony

            try {
                Thread.sleep(1000); // Pause for readability
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}