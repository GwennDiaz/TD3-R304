package Location;

import Characters.Character;
import Characters.Gallics.Druid;
import Characters.Gender;
import Characters.Origin;
import Characters.Roles.ClanChief;
import Characters.Romans.Legionnaire;
import Characters.MagicalCreature.Lycanthrope.Colony;      // NOUVEAU
import Characters.MagicalCreature.Lycanthrope.Pack;        // NOUVEAU
import Characters.MagicalCreature.Lycanthrope.Lycanthrope; // NOUVEAU
import Characters.MagicalCreature.Lycanthrope.Rank;        // NOUVEAU
import Consommable.FoodItem;
import Location.Place.BattleField;
import Location.Place.GallicVillage;
import Location.Place.RomanFortifiedCamp;
import Utils.Box;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class InvasionTheater {
    private String name;
    private Box<Location> places;
    private Box<ClanChief> chefs;
    private Colony colony; // Lycanthrope Manager

    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);

    public InvasionTheater(String name) {
        this.name = name;
        this.places = new Box<>();
        this.chefs = new Box<>();
        this.colony = new Colony(); // Colony initialization
    }

    public void Initialize() {
        System.out.println("Creation of Theater : " + this.name);

        // Creation of Chefs
        ClanChief chefGaulois = new ClanChief("Abraracourcix", Gender.MALE, 1.60, 50, 80, 80, 100, 0, 10, 0, null, Origin.GALLIC);
        ClanChief chefRomain = new ClanChief("Jules César", Gender.MALE, 1.75, 55, 70, 70, 100, 0, 50, 0, null, Origin.ROMAN);

        // Creation of Places
        GallicVillage village = new GallicVillage("Gallic Village", 100, chefGaulois);
        RomanFortifiedCamp camp = new RomanFortifiedCamp("Babaorum", 200, chefRomain);
        BattleField plain = new BattleField("Plain of the Carnutes", 1000, null);

        chefGaulois.setLocation(village);
        chefRomain.setLocation(camp);

        places.add(village);
        places.add(camp);
        places.add(plain);

        chefs.add(chefGaulois);
        chefs.add(chefRomain);

        // Initiale Humans Population
        Druid panoramix = new Druid("Panoramix", Gender.MALE, 1.70, 80, 10, 50, 100, 0, 0, 10, 100);
        village.addCharacter(panoramix);

        Legionnaire minus = new Legionnaire("Minus", Gender.MALE, 1.80, 25, 50, 50, 100, 0, 20, 0, "Legio I");
        camp.addCharacter(minus);

        Legionnaire brutus = new Legionnaire("Brutus", Gender.MALE, 1.85, 30, 60, 60, 100, 0, 80, 0, "Legio X");
        plain.addCharacter(brutus);

        // Integration of Lycanthropes
        // We create a pack that lives in the Plains
        Pack wildPack = new Pack("The Night Howlers");

        Lycanthrope alphaM = new Lycanthrope("Fenrir", Gender.MALE, 1.90, 100, 90, 90, 100, 0, 80, 0, Rank.ALPHA, 1.5);
        Lycanthrope alphaF = new Lycanthrope("Luna", Gender.FEMALE, 1.80, 95, 85, 90, 100, 0, 70, 0, Rank.ALPHA, 1.2);

        alphaM.transform(); // They start out as wolves
        alphaF.transform();

        wildPack.addMember(alphaM);
        wildPack.addMember(alphaF);

        colony.addPack(wildPack);
        plain.addCharacter(alphaM);
        plain.addCharacter(alphaF);

        System.out.println("World initialized with success (Humans & Lycanthropes)!");
    }

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


    private void handleBattles() {
        System.out.println("-> Potential Battles...");
        for (Location loc : places.getContents()) {
            if (loc instanceof BattleField) {
                List<Character> population = loc.getPresentCharacters();
                if (population.size() >= 2) {
                    Character c1 = population.get(random.nextInt(population.size()));
                    Character c2 = population.get(random.nextInt(population.size()));

                    if (c1 != c2 && !c1.isDead() && !c2.isDead()) {
                        System.out.println("Conflict at " + loc.getName() + "!");
                        c1.fight(c2);
                    }
                }
            }
            for (Character c : loc.getPresentCharacters()) {
                // Make the wolves howl at night if they are wolf-shaped
                if (c instanceof Characters.MagicalCreature.Lycanthrope.Lycanthrope) {
                    Characters.MagicalCreature.Lycanthrope.Lycanthrope lycan =
                            (Characters.MagicalCreature.Lycanthrope.Lycanthrope) c;

                    // We make sure it's shaped like a wolf for howling.
                    if (!lycan.isHuman()) {
                        lycan.howl("BATTLE_CRY");
                    }
                }
            }
        }
    }

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
    public void showPlace() {
        for (Location location : places.getContents()) {
            System.out.println("-" + location.getName());
        }
    }
}