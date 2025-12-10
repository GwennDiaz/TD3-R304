package Location;

import Characters.Character;
import Characters.Gallics.Druid;
import Characters.Gender;
import Characters.Origin;
import Characters.Roles.ClanChief;
import Characters.Romans.Legionnaire;
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
    private int maxPlace;
    private List<Location> places; //
    private List<ClanChief> chefs;

    private  final Random random = new Random();
    private  final Scanner scanner = new Scanner(System.in);

    public void Initialize() {
        System.out.println("Creation of Theatre : " + this.name);

        //ClanChief
        ClanChief chefGaulois = new ClanChief("Abraracourcix", Gender.MALE, 1.60, 50, 80, 80, 100, 0, 10, 0, null, Origin.GALLIC);
        ClanChief chefRomain = new ClanChief("Jules César", Gender.MALE, 1.75, 55, 70, 70, 100, 0, 50, 0, null, Origin.ROMAN);


        GallicVillage village = new GallicVillage("garllic Village", 100, chefGaulois);
        RomanFortifiedCamp camp = new RomanFortifiedCamp("Babaorum", 200, chefRomain);
        BattleField plain = new BattleField("Plain of the Carnutes", 1000, null);

        // Link (Chief <-> place)
        chefGaulois.setLocation(village);
        chefRomain.setLocation(camp);

        // add of list
        places.add(village);
        places.add(camp);
        places.add(plain);

        chefs.add(chefGaulois);
        chefs.add(chefRomain);

        // -Character population
        //Druid for potion
        Druid panoramix = new Druid("Panoramix", Gender.MALE, 1.70, 80, 10, 50, 100, 0, 0, 10, 100);
        village.addCharacter(panoramix);

        // Soldier
        Legionnaire minus = new Legionnaire("Minus", Gender.MALE, 1.80, 25, 50, 50, 100, 0, 20, 0, "Soldier");
        camp.addCharacter(minus);

        //test of fighter in battlefield
        Legionnaire brutus = new Legionnaire("Brutus", Gender.MALE, 1.85, 30, 60, 60, 100, 0, 80, 0, "Centurion");

        Character asterix = new Legionnaire("Astérix", Gender.MALE, 1.50, 35, 90, 90, 100, 0, 50, 100, "Heros"); //legionnaire just for test

        plain.addCharacter(brutus);
        plain.addCharacter(asterix);

        System.out.println("World initialize with success !");
    }


    public InvasionTheater(String name) {
        this.name = name;
        this.maxPlace = 10;   //default max
        this.places = new ArrayList<>();
        this.chefs = new ArrayList<>();
    }

    public void showPlace() {
        for (Location location : places) {
            System.out.println("-" + location.getName() + "\n");
        }
    }

    public void showNumberCharacter() {
        int nb = 0;
        for (Location location : places) {
            nb += location.getNumberOfCharactersPresent();
        }
        System.out.println("Number of Character in" + name + " : " + nb);
    }

    public void showCharacter() {
        System.out.println("\n--- Character per Place :");
        for (Location location : places) {
            System.out.println("Place : " + location.getName());
            List<Character> population = location.getPresentCharacters();
            if (population.isEmpty()) {
                System.out.println("no character");
            } else {
                for (Character c : population) {
                    System.out.println("  - " + c.getName()); //c.toString -> other solution
                }
            }
        }
    }


    public void simulationLoop(){
        boolean running = true;
        int turn = 1;

        while (running) {
            System.out.println("\n---TURN " + turn + ":");

            handleBattles();       //Auto-combat
            handleRandomEvents();  //Hunger/Potions
            handleResources();     //Food spawn
            handleChiefsTurn();    //User input

            turn++;
            System.out.print("\n Continue simulation? (y/n): ");
            if (scanner.next().equalsIgnoreCase("n")) {
                running = false;
            }
        }
    }

    //LOGIC
    private void handleBattles() {
        System.out.println("-> Fight");
        for (Location loc : places) {

            if (loc instanceof BattleField) {
                List<Character> population = loc.getPresentCharacters();

                if (population.size() >= 2) {
                    // Select two aleatoiry fighter
                    Character c1 = population.get(random.nextInt(population.size()));
                    Character c2 = population.get(random.nextInt(population.size()));

                    if (c1 != c2 && !c1.isDead() && !c2.isDead()) {
                        System.out.println("Battle at " + loc.getName() + ": " + c1.getName() + " vs " + c2.getName());
                        c1.fight(c2);
                    }
                }
            }
        }
    }

    private void handleRandomEvents() {
        System.out.println("> Random Events...");
        for (Location loc : places) {
            for (Character c : loc.getPresentCharacters()) {
                if (c.isDead()) continue;

                // 30% chance to get hungry
                if (random.nextDouble() < 0.3) {
                    c.setHunger(c.getHunger() + 10);
                }

                // Potion effect wears off
                if (c.getMagicPotionLevel() > 0 && random.nextDouble() < 0.2) {
                    c.setMagicPotionLevel(Math.max(0, c.getMagicPotionLevel() - 10));
                }
                //If the character is hungry, he loses life
                if (c.getHunger() > 80) {
                    c.setHealth(c.getHealth() - 5);
                    c.checkTrepas();
                }
            }
        }
    }

    private void handleResources() {
        System.out.println("-> Resource Update");
        for (Location loc : places) {
            // Food appears in places other than battlefields
            if (!(loc instanceof BattleField) && random.nextDouble() < 0.4) {
                System.out.println("Supplies arrived at " + loc.getName());
            }
            for (FoodItem food : loc.getPresentFood()) {
                if (food.isFresh()) {
                    // A 1 in 3 chance that it will rot on this turn
                    if (random.nextDouble() < 0.3) {
                        food.rot();
                    }
                }
            }
        }

    }

    private void handleChiefsTurn() {
        System.out.println("\n Clan Chiefs' Orders");
        for (ClanChief chief : chefs) {
            if (!chief.isDead()) {
                // Pass the world list so they can transfer troops
                chief.openActionMenu(scanner, places);
            }
        }
    }
    public static void main(String[] args) {
        InvasionTheater theater = new InvasionTheater("Gaule -50 av. JC");
        theater.Initialize();
        theater.simulationLoop();
    }


}
