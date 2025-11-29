package Characters.Roles;

import Characters.Character;
import Characters.Gender;
import Characters.Origin; // Important
import Location.*;
import Characters.Gallics.Druid; // Assure-toi que c'est Druid ou Druide selon ton fichier
import Characters.Gallics.Blacksmith;
import Characters.Romans.Legionnaire; // Pour recruter des soldats
import Location.Place.BattleField;
import Location.Place.Enclosure;

import java.util.List;
import java.util.Scanner;

public class ClanChief extends Character {
    private Location myLocation;
    private Origin origin; // Chief Nationality

    // constructor
    public ClanChief(String name, Gender gender, double height, int age, int strength,
                     int endurance, int health, int hunger, int belligerence,
                     int magicPotionLevel, Location location, Origin origin) {

        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);
        this.myLocation = location;
        this.origin = origin; // save origin here
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setLocation(Location location) {
        this.myLocation = location;
    }

    // MENU INTERACTIF
    public void openActionMenu(Scanner scanner, List<Location> worldLocations) {
        boolean stayInMenu = true;

        while (stayInMenu) {
            System.out.println("\n--------------------------------------");
            System.out.println("CHIEF ORDER " + this.name.toUpperCase() + " (" + origin + ")");
            System.out.println("Current location : " + (myLocation != null ? myLocation.getName() : "None"));
            System.out.println("\n 1. Examine the location");
            System.out.println("2. Treat everyone");
            System.out.println("3. Feed everyone (Feast)");
            System.out.println("4. Recruit a new character");
            System.out.println("5. Ask the Druid for a potion");
            System.out.println("6. Give someone a potion");
            System.out.println("7. Transfer character");
            System.out.println("0. Quit");
            System.out.print("Your choise : ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1: examineLocation(); break;
                    case 2: orderHealing(); break;
                    case 3: orderFeast(); break;
                    case 4: createNewCharacterInteraction(scanner); break; // Méthode améliorée
                    case 5: handlePotionRequest(scanner); break;
                    case 6: handleGivePotion(scanner); break;
                    case 7: handleTransfer(scanner, worldLocations); break;
                    case 0: stayInMenu = false; System.out.println("End of orders."); break;
                    default: System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Please enter a number.");
                scanner.next();
            }
        }
    }

    // -Internal Methods

    // Method for selecting a character by name
    private Character selectCharacter(Scanner scanner) {
        if (myLocation == null || myLocation.getPresentCharacters().isEmpty()) {
            System.out.println("There's nobody here!");
            return null;
        }

        System.out.println("-Choose your character here.");
        List<Character> population = myLocation.getPresentCharacters();

        // Displays the list: 0. Asterix, 1. Obelix....
        for (int i = 0; i < population.size(); i++) {
            Character c = population.get(i);
            System.out.println(i + ". " + c.getName() + " (Health: " + c.getHealth() + ")");
        }
        System.out.print("Enter the number of the character you would like to choose: ");

        if (scanner.hasNextInt()) {
            int index = scanner.nextInt();
            scanner.nextLine(); // is used to consume the player's entry

            if (index >= 0 && index < population.size()) {
                return population.get(index); // Return the chosen character
            }
        } else {
            scanner.next(); // Clear the incorrect entry
        }

        System.out.println("Invalid number.");
        return null;
    }

    private void createNewCharacterInteraction(Scanner scanner) {
        if (myLocation == null) return;

        System.out.print("Recruit's name: ");
        String name = scanner.nextLine();

        System.out.println("Type? 1. Legionary (Roman) 2. Blacksmith (Gaul)");
        int type = 0;
        if(scanner.hasNextInt()) {
            type = scanner.nextInt();
            scanner.nextLine();
        }

        Character newChar;
        if (type == 1) {
            newChar = new Legionnaire(name, Gender.MALE, 1.80, 25, 60, 50, 100, 0, 10, 0, "I");
        } else {
            newChar = new Blacksmith(name, Gender.MALE, 1.70, 40, 70, 60, 100, 0, 0, 0, 50);
        }

        recruitCharacter(newChar);
        System.out.println(name + " has joined the ranks!");
    }

    private void handlePotionRequest(Scanner scanner) {
        Druid localDruid = null;
        for (Character c : myLocation.getPresentCharacters()) {
            if (c instanceof Druid) {
                localDruid = (Druid) c;
                break;
            }
        }

        if (localDruid == null) {
            System.out.println("There are no Druids here !");
            return;
        }

        System.out.println("Who is the potion for? (Name)");
        Character target = selectCharacter(scanner);

        if (target != null) {
            askDruidForPotion(localDruid, target, 20);
        }
    }

    private void handleGivePotion(Scanner scanner) {
        System.out.println("Who should drink? (Name)");
        Character target = selectCharacter(scanner);
        if (target != null) {
            target.drinkMagicPotion(10);
        }
    }

    private void handleTransfer(Scanner scanner, List<Location> worldLocations) {
        System.out.println("Who should transfer ? (Name");
        Character traveler = selectCharacter(scanner);

        if (traveler == null) return;

        System.out.println("Towards where ? (ID)");
        for (int i = 0; i < worldLocations.size(); i++) {
            Location loc = worldLocations.get(i);
            if (loc instanceof BattleField || loc instanceof Enclosure) {
                System.out.println(i + ". " + loc.getName() + " (" + loc.getType() + ")");
            }
        }

        if (scanner.hasNextInt()) {
            int destIndex = scanner.nextInt();
            scanner.nextLine();
            if (destIndex >= 0 && destIndex < worldLocations.size()) {
                transferCharacter(traveler, worldLocations.get(destIndex));
            } else {
                System.out.println("Invalid destination.");
            }
        }
    }

    // -LOGICAL ACTIONS

    public void examineLocation() {
        if (myLocation != null) myLocation.printStatus();
        else System.out.println(this.name + " has no place!");
    }

    public void recruitCharacter(Character newCharacter) {
        if (myLocation != null) myLocation.addCharacter(newCharacter);
    }

    public void orderHealing() {
        if (myLocation != null) {
            System.out.println(this.name + " orders everyone to be treated!");
            myLocation.healAllCharacters();
        }
    }

    public void orderFeast() {
        if (myLocation != null) {
            System.out.println(this.name + " order a feast!");
            myLocation.feedAllCharacters();
        }
    }

    public void askDruidForPotion(Druid druid, Character target, int quantity) {
        System.out.println(this.name + " give a potion to " + druid.getName());
        druid.preparePotion(target, quantity);
    }

    public void transferCharacter(Character character, Location destination) {
        if (!myLocation.getPresentCharacters().contains(character)) {
            System.out.println(character.getName() + " is not here.");
            return;
        }

        if ((destination instanceof BattleField) || (destination instanceof Enclosure)) {
            System.out.println("Transfer of " + character.getName() + " towards " + destination.getName());
            myLocation.removeCharacter(character);
            destination.addCharacter(character);
        } else {
            System.out.println("The leader can only send to a Battlefield or an Enclosure.");
        }
    }

    @Override
    public String getType() {
        // Displays the type based on the origin
        return (origin == Origin.ROMAN) ? "Roman Chief": "Gallic Chief";
    }
}