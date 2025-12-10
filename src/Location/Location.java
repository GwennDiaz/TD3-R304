package Location;

import Characters.Character; // Important : Import de votre classe Character
import Characters.Gallics.Gallic;
import java.util.*;
import Characters.Roles.ClanChief;
import static java.lang.Math.max;
import java.util.Random;

public abstract class Location {
    protected String name;
    protected double area;
    protected ClanChief clanChief;
    protected List<Character> presentCharacters;
    protected List<String> presentFood;
    protected Environment environment;

    public Location(String name, double area, ClanChief clanChief) {
        this.name = name;
        this.area = area;
        this.clanChief = clanChief;
        this.presentCharacters = new ArrayList<>();
        this.presentFood = new ArrayList<>();
        this.environment = Environment.NORMAL;
    }

    //---GETTERS---

    public String getName() { return name; }
    public double getArea() { return area; }
    public ClanChief getClanChief() { return clanChief; }

    public int getNumberOfCharactersPresent() {
        return presentCharacters.size();
    }

    public List<Character> getPresentCharacters() {
        return presentCharacters;
    }

    public List<String> getPresentFood() {
        return presentFood;
    }

    // --- SETTERS---

    public void setArea(double area) {
        this.area = max(0, area);
    }
    public void setClanChief(ClanChief clanChief) {
        this.clanChief = clanChief;
    }
    public abstract String getType();
    public void setEnvironment(Environment env) {this.environment = env;}

    // --- METHODS ---

    // Add a character
    public void addCharacter(Character c) {
        this.presentCharacters.add(c);
        System.out.println(c.getName() + " entered into " + this.name);
    }

    // Remove a character
    public void removeCharacter(Character c) {
        if (presentCharacters.contains(c)) {
            presentCharacters.remove(c);
            System.out.println(c.getName() + " left " + this.name);
        } else {
            System.out.println(c.getName() + " is not in " + this.name);
        }
    }

    // Display characteristics
    public void printStatus() {
        System.out.println("\n--- STATUS OF " + name.toUpperCase() + " ---");
        System.out.println("Type: " + getType());
        System.out.println("Area: " + area + " m²");
        if (clanChief != null) {
            System.out.println("Chief: " + clanChief.getName());
        }
        System.out.println("Food available: " + presentFood);
        System.out.println("Characters present (" + getNumberOfCharactersPresent() + "):");
        for (Character c : presentCharacters) {
            System.out.println(" - " + c.toString());
        }
        System.out.println("-----------------------------------\n");
    }

    // Heal characters
    public void healAllCharacters() {
        System.out.println("Healing everyone in " + name + "...");
        for (Character c : presentCharacters) {
            c.setHealth(100); // Remet la santé à 100
        }
        System.out.println("All characters have been healed!");
    }

    // Feed characters
    public void feedAllCharacters() {
        System.out.println("Feeding time in " + name + "...");

        for (Character c : presentCharacters) {
            if (!presentFood.isEmpty()) {
                String food = presentFood.removeFirst();
                c.setHunger(0);
                System.out.println(c.getName() + " ate " + food + ".");
            } else {
                System.out.println("No more food for " + c.getName() + "!");
            }
        }
    }

    @Override
    public String toString() {
        return String.format(
                this.name,
                this.getType(),
                this.getNumberOfCharactersPresent()
        );
    }

    //APPLIQUER EFFET METEO
    public void applyEnvironmentEffects() {
        if (environment == Environment.NORMAL) return;

        System.out.println("\n--- Effets Météo (" + environment + ") ---");
        Random rand = new Random();

        for (Character c : presentCharacters) {
            switch (environment) {
                case DESERT:
                    // La chaleur fatigue et affame
                    c.setHunger(Math.min(100, c.getHunger() + 10));
                    System.out.println(c.getName() + " souffre de la chaleur (+10 Faim).");
                    break;
                case MOUNTAIN:
                    // 20% de chance de glisser
                    if (rand.nextInt(5) == 0) {
                        c.setHealth(Math.max(0, c.getHealth() - 10));
                        System.out.println("Aïe ! " + c.getName() + " a glissé sur un rocher (-10 Vie).");
                    }
                    break;
                case FOG:
                    System.out.println(c.getName() + " ne voit pas à 2 mètres dans ce brouillard...");
                    break;
            }
        }
    }
}