package Location;

import Characters.Character;
import Characters.Gallics.Druid;
import Characters.Gender;
import Characters.Origin;
import Characters.Roles.ClanChief;
import Characters.Romans.Legionnaire;
import Characters.MagicalCreature.Lycanthrope.Colony;
import Characters.MagicalCreature.Lycanthrope.Pack;
import Characters.MagicalCreature.Lycanthrope.Lycanthrope;
import Characters.MagicalCreature.Lycanthrope.Rank;
import Consommable.FoodItem;
import Location.Place.BattleField;
import Location.Place.GallicVillage;
import Location.Place.RomanFortifiedCamp;
import Utils.Box;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents the main simulation environment where the invasion takes place.
 * It manages locations, clan chiefs, and the global flow of time (turns).
 */
public class InvasionTheater {
    private String name;
    private Box<Location> places;
    private Box<ClanChief> chefs;
    private Colony colony; // Lycanthrope Manager

    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Initializes a new Invasion Theater with a specific name and empty containers.
     *
     * @param name The name of the theater (e.g., "Armorique").
     */
    public InvasionTheater(String name) {
        this.name = name;
        this.places = new Box<>();
        this.chefs = new Box<>();
        this.colony = new Colony(); // Colony initialization
    }

    /**
     * Sets up the initial state of the simulation.
     * Creates places, characters (Gauls, Romans, Lycanthropes), and assigns them to locations.
     */
    public void Initialize() {
        System.out.println("Creation of Theater : " + this.name);

        // 1. Creation of Chefs (Essentiel)
        ClanChief chefGaulois = new ClanChief("Abraracourcix", Gender.MALE, 1.60, 50, 80, 80, 100, 0, 10, 0, null, Origin.GALLIC);
        ClanChief chefRomain = new ClanChief("Jules César", Gender.MALE, 1.75, 55, 70, 70, 100, 0, 50, 0, null, Origin.ROMAN);

        // 2. Creation of Places
        GallicVillage village = new GallicVillage("Gallic Village", 100, chefGaulois);
        RomanFortifiedCamp camp = new RomanFortifiedCamp("Babaorum", 200, chefRomain);
        BattleField plain = new BattleField("Plain of the Carnutes", 1000, null);

        // Assign locations to chiefs
        chefGaulois.setLocation(village);
        chefRomain.setLocation(camp);

        // Add places to the theater
        places.add(village);
        places.add(camp);
        places.add(plain);

        // Add chiefs to the theater
        chefs.add(chefGaulois);
        chefs.add(chefRomain);

        // MASSIVE POPULATION

        //Populate the Gallic Village (1 Druid + 5 random inhabitants)
        Druid panoramix = new Druid("Panoramix", Gender.MALE, 1.70, 80, 10, 50, 100, 0, 0, 10, 100);
        village.addCharacter(panoramix);

        // We add a few generic Gauls (Merchant, Blacksmith classes, etc.).
        for(int i=0; i<5; i++) {
            String nom = Characters.NameRepository.getNameBlacksmith();

            Characters.Gallics.Blacksmith forgeron = new Characters.Gallics.Blacksmith(
                    nom, Gender.MALE, 1.70, 30, 60, 60, 100, 0, 20, 0, 50
            );
            village.addCharacter(forgeron);
        }

        // Populate the Roman Camp (10 Legionnaires)
        for (int i = 1; i <= 10; i++) {
            String legion = (i % 2 == 0) ? "Legio I" : "Legio X";
            String nom = Characters.NameRepository.getNameLegionnaire();
            Legionnaire l = new Legionnaire(
                    nom,
                    Gender.MALE, 1.75, 25, 50, 50, 100, 0, 40, 0, legion
            );
            camp.addCharacter(l);
        }

        // Populate the Battlefield
        Legionnaire brutus = new Legionnaire("Brutus", Gender.MALE, 1.85, 30, 60, 60, 100, 0, 80, 0, "Legio X");
        plain.addCharacter(brutus);

        // Integration of Lycanthropes
        Pack wildPack = new Pack("The Night Howlers");

        // Alpha Couple
        Lycanthrope alphaM = new Lycanthrope("Fenrir", Gender.MALE, 1.90, 100, 90, 90, 100, 0, 80, 0, Rank.ALPHA, 1.5);
        Lycanthrope alphaF = new Lycanthrope("Luna", Gender.FEMALE, 1.80, 95, 85, 90, 100, 0, 70, 0, Rank.ALPHA, 1.2);

        alphaM.transform();
        alphaF.transform();
        wildPack.addMember(alphaM);
        wildPack.addMember(alphaF);
        plain.addCharacter(alphaM);
        plain.addCharacter(alphaF);

        // Added 4 Gamma wolves (Soldiers) to the pack
        for(int i=0; i<4; i++) {
            String nom = Characters.NameRepository.getNameLycanthrope();

            Lycanthrope wolf = new Lycanthrope(
                    nom,
                    (i % 2 == 0 ? Gender.MALE : Gender.FEMALE),
                    1.70, 20, 60, 60, 100, 0, 50, 0, Rank.GAMMA, 1.0
            );

            wolf.transform();
            wildPack.addMember(wolf);
            plain.addCharacter(wolf);
        }

        colony.addPack(wildPack);

        System.out.println("World initialized with success (Population: High)!");
    }

    /**
     * The main loop of the simulation.
     * It handles the progression of turns, colony evolution, events, and user interactions.
     */
    public void simulationLoop() {
        boolean running = true;
        int turn = 1;

        while (running) {
            System.out.println("\n=== TURN " + turn + " ===");

            // Logic: Colony life (Aging, Hierarchy, Reproduction)
            colony.nextTurn();

            // Theater Events
            handleBattles();
            handleRandomEvents();
            handleResources();

            // User Action
            handleChiefsTurn();

            turn++;
            System.out.print("\nContinue simulation? (y/n): ");
            String input = scanner.next();
            if (input.equalsIgnoreCase("n")) {
                running = false;
            }
        }
    }

    /**
     * Checks if two characters belong to the same faction (Gallic, Roman, or Lycanthrope).
     */
    private boolean isSameFaction(Character c1, Character c2) {
        // Verification of Gallic Camp (Gallic Authority OR Leader of Gallic Origin)
        boolean c1IsGallic = (c1 instanceof Characters.Gallics.Gallic) ||
                (c1 instanceof ClanChief && ((ClanChief) c1).getOrigin() == Origin.GALLIC);
        boolean c2IsGallic = (c2 instanceof Characters.Gallics.Gallic) ||
                (c2 instanceof ClanChief && ((ClanChief) c2).getOrigin() == Origin.GALLIC);

        if (c1IsGallic && c2IsGallic) return true;

        //Verification Roman Camp
        boolean c1IsRoman = (c1 instanceof Characters.Romans.Roman) ||
                (c1 instanceof ClanChief && ((ClanChief) c1).getOrigin() == Origin.ROMAN);
        boolean c2IsRoman = (c2 instanceof Characters.Romans.Roman) ||
                (c2 instanceof ClanChief && ((ClanChief) c2).getOrigin() == Origin.ROMAN);

        if (c1IsRoman && c2IsRoman) return true;

        // Lycanthrope Camp Verification
        boolean c1IsLycan = (c1 instanceof Lycanthrope);
        boolean c2IsLycan = (c2 instanceof Lycanthrope);

        if (c1IsLycan && c2IsLycan) return true;

        return false;
    }

    /**
     * Manages combat events on battlefields and triggers special creature behaviors
     */
    private void handleBattles() {
        System.out.println("-> Potential Battles...");

        for (Location loc : places.getContents()) {
            // Combat management
            if (loc instanceof BattleField) {
                List<Character> population = loc.getPresentCharacters();
                if (population.size() >= 2) {
                    Character c1 = population.get(random.nextInt(population.size()));
                    Character c2 = c1;

                    int attempts = 0;
                    while ((c1 == c2 || isSameFaction(c1, c2)) && attempts < 10) {
                        c2 = population.get(random.nextInt(population.size()));
                        attempts++;
                    }

                    if (c1 != c2 && !isSameFaction(c1, c2) && !c1.isDead() && !c2.isDead()) {
                        System.out.println("Conflict at " + loc.getName() + "!");
                        c1.fight(c2);

                        // Gestion des hurlements de loups
                        makeWolfHowlIfPossible(c1);
                        makeWolfHowlIfPossible(c2);
                    }
                }
            }
        }
    }

    private void makeWolfHowlIfPossible(Character c) {
        if (c instanceof Lycanthrope) {
            Lycanthrope lycan =
                    (Lycanthrope) c;
            if (!lycan.isHuman()) {
                lycan.howl("BATTLE_CRY");
            }
        }
    }

    /**
     * Generates random events such as hunger increase or automatic potion brewing by druids
     */
    private void handleRandomEvents() {
        System.out.println("-> Random Events...");
        for (Location loc : places.getContents()) {
            // Using a copy to avoid concurrent modification errors
            for (Character c : new ArrayList<>(loc.getPresentCharacters())) {
                if (c.isDead()) continue;

                if (random.nextDouble() < 0.3) {
                    c.setHunger(c.getHunger() + 10);
                }

                // If it is a Druid, it can attempt to make a potion automatically.
                if (c instanceof Druid && random.nextDouble() < 0.2) {
                    ((Druid) c).brewPotion(loc);
                }
            }
        }
    }

    /**
     * Updates resources in the locations, including food decay and new supply arrivals
     */
    private void handleResources() {
        System.out.println("-> Resource Update");
        for (Location loc : places.getContents()) {
            if (!(loc instanceof BattleField) && random.nextDouble() < 0.4) {
                // Simplification: fictitious spawn or actual item addition
                System.out.println("Supplies arrived at " + loc.getName());
            }
            for (FoodItem food : loc.getPresentFood()) {
                if (food.isFresh() && random.nextDouble() < 0.3) {
                    food.rot();
                }
            }
        }
    }

    /**
     * [Iterates through all clan chiefs to allow them to perform their actions (user controlled)
     */
    private void handleChiefsTurn() {
        System.out.println("\n--- Clan Chiefs' Orders ---");
        // Using our generic Box class to iterate
        for (int i = 0; i < chefs.size(); i++) {
            ClanChief chief = chefs.get(i);
            if (!chief.isDead()) {
                chief.openActionMenu(scanner, places.getContents());
            }
        }
    }

    // Display methods...

    /**
     * Displays the list of all locations present in the theater.
     */
    public void showPlace() {
        for (Location location : places.getContents()) {
            System.out.println("-" + location.getName());
        }
    }
}