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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class InvasionTheater {
    private String name;
    private List<Location> places;
    private List<ClanChief> chefs;
    private Colony colony; // NOUVEAU : Gestionnaire des Lycanthropes (TD4)

    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);

    public InvasionTheater(String name) {
        this.name = name;
        this.places = new ArrayList<>();
        this.chefs = new ArrayList<>();
        this.colony = new Colony(); // Initialisation de la colonie
    }

    public void Initialize() {
        System.out.println("Creation of Theater : " + this.name);

        // 1. Création des Chefs
        ClanChief chefGaulois = new ClanChief("Abraracourcix", Gender.MALE, 1.60, 50, 80, 80, 100, 0, 10, 0, null, Origin.GALLIC);
        ClanChief chefRomain = new ClanChief("Jules César", Gender.MALE, 1.75, 55, 70, 70, 100, 0, 50, 0, null, Origin.ROMAN);

        // 2. Création des Lieux
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

        // 3. Population Initiale (Humains)
        Druid panoramix = new Druid("Panoramix", Gender.MALE, 1.70, 80, 10, 50, 100, 0, 0, 10, 100);
        village.addCharacter(panoramix);

        Legionnaire minus = new Legionnaire("Minus", Gender.MALE, 1.80, 25, 50, 50, 100, 0, 20, 0, "Legio I");
        camp.addCharacter(minus);

        Legionnaire brutus = new Legionnaire("Brutus", Gender.MALE, 1.85, 30, 60, 60, 100, 0, 80, 0, "Legio X");
        plain.addCharacter(brutus);

        // 4. Intégration des Lycanthropes (TD4 dans TD3)
        // On crée une meute qui vit dans la Plaine
        Pack wildPack = new Pack("The Night Howlers");

        Lycanthrope alphaM = new Lycanthrope("Fenrir", Gender.MALE, 1.90, 100, 90, 90, 100, 0, 80, 0, Rank.ALPHA, 1.5);
        Lycanthrope alphaF = new Lycanthrope("Luna", Gender.FEMALE, 1.80, 95, 85, 90, 100, 0, 70, 0, Rank.ALPHA, 1.2);

        alphaM.transform(); // Ils commencent sous forme de loups
        alphaF.transform();

        wildPack.addMember(alphaM);
        wildPack.addMember(alphaF);

        colony.addPack(wildPack); // Ajout à la logique TD4
        plain.addCharacter(alphaM); // Ajout physique au lieu TD3
        plain.addCharacter(alphaF);

        System.out.println("World initialized with success (Humans & Lycanthropes)!");
    }

    public void simulationLoop() {
        boolean running = true;
        int turn = 1;

        while (running) {
            System.out.println("\n=== TURN " + turn + " ===");

            // 1. Logique TD4 : Vie de la colonie (Vieillissement, Hiérarchie, Reproduction)
            colony.nextTurn();

            // 2. Logique TD3 : Événements du Théâtre
            handleBattles();
            handleRandomEvents();
            handleResources();

            // 3. Actions Utilisateur
            handleChiefsTurn();

            turn++;
            System.out.print("\nContinue simulation? (y/n): ");
            String input = scanner.next();
            if (input.equalsIgnoreCase("n")) {
                running = false;
            }
        }
    }

    // --- LOGIQUE EXISTANTE (Inchangée mais nettoyée) ---

    private void handleBattles() {
        System.out.println("-> Potential Battles...");
        for (Location loc : places) {
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
        }
    }

    private void handleRandomEvents() {
        System.out.println("-> Random Events...");
        for (Location loc : places) {
            // Utilisation d'une copie pour éviter les erreurs de modification concurrente
            for (Character c : new ArrayList<>(loc.getPresentCharacters())) {
                if (c.isDead()) continue;

                if (random.nextDouble() < 0.3) {
                    c.setHunger(c.getHunger() + 10);
                }

                // Si c'est un Druide, il peut tenter de faire une potion automatiquement
                if (c instanceof Druid && random.nextDouble() < 0.2) {
                    ((Druid) c).brewPotion(loc);
                }
            }
        }
    }

    private void handleResources() {
        System.out.println("-> Resource Update");
        for (Location loc : places) {
            if (!(loc instanceof BattleField) && random.nextDouble() < 0.4) {
                // Simplification : spawn fictif ou ajout réel d'item
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
        for (ClanChief chief : chefs) {
            if (!chief.isDead()) {
                chief.openActionMenu(scanner, places);
            }
        }
    }

    // Méthodes d'affichage...
    public void showPlace() {
        for (Location location : places) {
            System.out.println("-" + location.getName());
        }
    }
}