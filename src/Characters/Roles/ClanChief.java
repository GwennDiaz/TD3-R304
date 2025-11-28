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
    private Origin origin; // La nationalité du chef

    // --- CORRECTION 1 : Ajout de Origin dans le constructeur ---
    public ClanChief(String name, Gender gender, double height, int age, int strength,
                     int endurance, int health, int hunger, int belligerence,
                     int magicPotionLevel, Location location, Origin origin) {

        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);
        this.myLocation = location;
        this.origin = origin; // On sauvegarde l'origine ici !
    }

    // --- CORRECTION 2 : Ajout du Getter (Indispensable pour Location) ---
    public Origin getOrigin() {
        return origin;
    }

    public void setLocation(Location location) {
        this.myLocation = location;
    }

    // ============================================================
    //                      MENU INTERACTIF
    // ============================================================

    public void openActionMenu(Scanner scanner, List<Location> worldLocations) {
        boolean stayInMenu = true;

        while (stayInMenu) {
            System.out.println("\n==========================================");
            System.out.println("ORDRES DU CHEF " + this.name.toUpperCase() + " (" + origin + ")");
            System.out.println("Lieu actuel : " + (myLocation != null ? myLocation.getName() : "Aucun"));
            System.out.println("==========================================");
            System.out.println("1. Examiner le lieu");
            System.out.println("2. Soigner tout le monde");
            System.out.println("3. Nourrir tout le monde (Festin)");
            System.out.println("4. Recruter un nouveau personnage");
            System.out.println("5. Demander potion au Druide");
            System.out.println("6. Faire boire potion à quelqu'un");
            System.out.println("7. Transférer un personnage");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

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
                    case 0: stayInMenu = false; System.out.println("Fin des ordres."); break;
                    default: System.out.println("Choix invalide.");
                }
            } else {
                System.out.println("Veuillez entrer un chiffre.");
                scanner.next();
            }
        }
    }

    // --- Méthodes Internes ---

    // Version améliorée pour choisir le type de recrue
    private void createNewCharacterInteraction(Scanner scanner) {
        if (myLocation == null) return;

        System.out.print("Nom de la recrue : ");
        String name = scanner.nextLine();

        System.out.println("Type ? 1. Légionnaire (Romain)  2. Forgeron (Gaulois)");
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
        System.out.println(name + " a rejoint les rangs !");
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
            System.out.println("Il n'y a pas de Druide ici !");
            return;
        }

        System.out.println("Pour qui est la potion ? (Nom)");
        String targetName = scanner.nextLine();
        Character target = findCharacterByName(targetName);

        if (target != null) {
            askDruidForPotion(localDruid, target, 20);
        }
    }

    private void handleGivePotion(Scanner scanner) {
        System.out.println("Qui doit boire ? (Nom)");
        String name = scanner.nextLine();
        Character target = findCharacterByName(name);
        if (target != null) {
            target.drinkMagicPotion(10);
        }
    }

    private void handleTransfer(Scanner scanner, List<Location> worldLocations) {
        System.out.println("Qui transférer ? (Nom)");
        String charName = scanner.nextLine();
        Character traveler = findCharacterByName(charName);

        if (traveler == null) return;

        System.out.println("Vers où ? (ID)");
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
                System.out.println("Destination invalide.");
            }
        }
    }

    private Character findCharacterByName(String name) {
        if (myLocation == null) return null;
        for (Character c : myLocation.getPresentCharacters()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        System.out.println("Personnage introuvable ici.");
        return null;
    }

    // ============================================================
    //                   ACTIONS LOGIQUES
    // ============================================================

    public void examineLocation() {
        if (myLocation != null) myLocation.printStatus();
        else System.out.println(this.name + " n'a pas de lieu !");
    }

    public void recruitCharacter(Character newCharacter) {
        if (myLocation != null) myLocation.addCharacter(newCharacter);
    }

    public void orderHealing() {
        if (myLocation != null) {
            System.out.println(this.name + " ordonne de soigner tout le monde !");
            myLocation.healAllCharacters();
        }
    }

    public void orderFeast() {
        if (myLocation != null) {
            System.out.println(this.name + " ordonne un festin !");
            myLocation.feedAllCharacters();
        }
    }

    public void askDruidForPotion(Druid druid, Character target, int quantity) {
        System.out.println(this.name + " demande une potion à " + druid.getName());
        druid.preparePotion(target, quantity);
    }

    public void transferCharacter(Character character, Location destination) {
        if (!myLocation.getPresentCharacters().contains(character)) {
            System.out.println(character.getName() + " n'est pas ici.");
            return;
        }

        if ((destination instanceof BattleField) || (destination instanceof Enclosure)) {
            System.out.println("Transfert de " + character.getName() + " vers " + destination.getName());
            myLocation.removeCharacter(character);
            destination.addCharacter(character);
        } else {
            System.out.println("Le chef ne peut envoyer que vers un Champ de Bataille ou un Enclos.");
        }
    }

    @Override
    public String getType() {
        // Affiche dynamiquement le type selon l'origine
        return (origin == Origin.ROMAN) ? "Chef Romain" : "Chef Gaulois";
    }
}