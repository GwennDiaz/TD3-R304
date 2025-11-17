package Characters.Romans;

import Characters.Character;
import Characters.Gender;
import Location.*;
import Characters.Gallics.*;
import Location.Place.BattleField;
import Location.Place.Enclosure;
import java.util.List;
import java.util.Scanner;

public class ClanChief extends Roman {
    private Location myLocation;

    public ClanChief(String name, Gender gender, double height, int age, int strength,
                     int endurance, int health, int hunger, int belligerence,
                     int magicPotionLevel, Location location) {
        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);
        this.myLocation = location;
    }

    public void setLocation(Location location) {
        this.myLocation = location;
    }

    // ============================================================
    //                      INTERACTIVE MENU
    // ============================================================

    /**
     * Displays the Chief's action menu and executes the user's choice.
     * @param scanner To read keyboard input
     * @param worldLocations The list of all existing locations (for transfers)
     */
    public void openActionMenu(Scanner scanner, List<Location> worldLocations) {
        boolean stayInMenu = true;

        while (stayInMenu) {
            System.out.println("\n==========================================");
            System.out.println("ORDERS OF CHIEF " + this.name.toUpperCase());
            System.out.println("Location: " + (myLocation != null ? myLocation.getName() : "None"));
            System.out.println("==========================================");
            System.out.println("1. Examine location (Status)");
            System.out.println("2. Heal everyone");
            System.out.println("3. Feed everyone (Feast)");
            System.out.println("4. Recruit a new character");
            System.out.println("5. Ask Druid for potion");
            System.out.println("6. Make someone drink potion");
            System.out.println("7. Transfer someone to another location");
            System.out.println("0. Quit menu");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    examineLocation();
                    break;
                case 2:
                    orderHealing();
                    break;
                case 3:
                    orderFeast();
                    break;
                case 4:
                    System.out.print("Name of the recruit: ");
                    String recruitName = scanner.nextLine();
                    Gallic newGuy = new Blacksmith(recruitName, Gender.MALE, 1.70, 20, 50, 50, 100, 0, 0, 0, 50);
                    recruitCharacter(newGuy);
                    break;
                case 5:
                    handlePotionRequest(scanner);
                    break;
                case 6:
                    handleGivePotion(scanner);
                    break;
                case 7:
                    handleTransfer(scanner, worldLocations);
                    break;
                case 0:
                    stayInMenu = false;
                    System.out.println("End of orders.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // --- Internal Helper Methods for the Menu ---

    private void handlePotionRequest(Scanner scanner) {
        // 1. Find a druid in the current location
        Druid localDruid = null;
        for (Character c : myLocation.getPresentCharacters()) {
            if (c instanceof Druid) {
                localDruid = (Druid) c;
                break;
            }
        }

        if (localDruid == null) {
            System.out.println("There is no Druid here!");
            return;
        }

        // 2. Choose the target
        System.out.println("Who is the potion for? (Enter name)");
        String targetName = scanner.nextLine();
        Character target = findCharacterByName(targetName);

        if (target != null) {
            askDruidForPotion(localDruid, target, 20); // Fixed quantity 20 for example
        }
    }

    private void handleGivePotion(Scanner scanner) {
        System.out.println("Who to give the potion to? (Name)");
        String name = scanner.nextLine();
        Character target = findCharacterByName(name);
        if (target != null) {
            givePotionTo(target, 10);
        }
    }

    private void handleTransfer(Scanner scanner, List<Location> worldLocations) {
        // 1. Who to transfer?
        System.out.println("Who to transfer? (Name)");
        String charName = scanner.nextLine();
        Character traveler = findCharacterByName(charName);

        if (traveler == null) return;

        // 2. Where? (List valid destinations: Battlefield or Enclosure)
        System.out.println("Where to? (Choose an ID)");
        for (int i = 0; i < worldLocations.size(); i++) {
            Location loc = worldLocations.get(i);
            if (loc instanceof BattleField || loc instanceof Enclosure) {
                System.out.println(i + ". " + loc.getName() + " (" + loc.getType() + ")");
            }
        }

        int destIndex = scanner.nextInt();
        if (destIndex >= 0 && destIndex < worldLocations.size()) {
            transferCharacter(traveler, worldLocations.get(destIndex));
        } else {
            System.out.println("Invalid destination.");
        }
    }

    private Character findCharacterByName(String name) {
        if (myLocation == null) return null;
        for (Character c : myLocation.getPresentCharacters()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        System.out.println("Character not found in this location.");
        return null;
    }


    // ============================================================
    //                   LOGICAL ACTIONS
    // ============================================================

    public void examineLocation() {
        if (myLocation != null) {
            myLocation.printStatus();
        } else {
            System.out.println(this.name + " has no location!");
        }
    }

    public void recruitCharacter(Character newCharacter) {
        if (myLocation != null) {
            myLocation.addCharacter(newCharacter);
        }
    }

    public void orderHealing() {
        if (myLocation != null) {
            System.out.println(this.name + " orders healing for everyone!");
            myLocation.healAllCharacters();
        }
    }

    public void orderFeast() {
        if (myLocation != null) {
            System.out.println(this.name + " orders a feast!");
            myLocation.feedAllCharacters();
        }
    }

    public void askDruidForPotion(Druid druid, Character target, int quantity) {
        System.out.println(this.name + " asks " + druid.getName() + " for potion.");
        druid.preparePotion(target, quantity);
    }

    public void givePotionTo(Character target, int amount) {
        if (myLocation.getPresentCharacters().contains(target)) {
            target.setMagicPotionLevel(target.getMagicPotionLevel() + amount);
            System.out.println(this.name + " gives potion to " + target.getName());
        } else {
            System.out.println(target.getName() + " is not here.");
        }
    }

    public void transferCharacter(Character character, Location destination) {
        if (!myLocation.getPresentCharacters().contains(character)) {
            System.out.println(character.getName() + " is not here.");
            return;
        }

        if ((destination instanceof BattleField) || (destination instanceof Enclosure)) {
            System.out.println(this.name + " transfers " + character.getName() + " to " + destination.getName());
            myLocation.removeCharacter(character);
            destination.addCharacter(character);
        } else {
            System.out.println("Impossible: The Chief can only send to a Battlefield or an Enclosure.");
        }
    }

    @Override
    public String getType() {
        return "Roman Chief";
    }
}