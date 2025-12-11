package Location;

import Characters.Character;
import Characters.Roles.ClanChief;
import Consommable.FoodItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.lang.Math.max;
import java.util.concurrent.CopyOnWriteArrayList;
import Consommable.Potion.Potion;

/**
 * Abstract base class representing a physical location in the simulation.
 * It manages the characters present, available food, environment settings, and the magic potion pot.
 */
public abstract class Location {
    protected String name;
    protected double area;
    protected ClanChief clanChief;
    // Using CopyOnWriteArrayList to prevent concurrent modification issues during iteration
    protected List<Character> presentCharacters = new CopyOnWriteArrayList<>();
    protected List<FoodItem> presentFood;
    protected Environment environment;
    protected Potion marmite;

    /**
     * Constructor to initialize a location.
     *
     * @param name The name of the location.
     * @param area The surface area of the location.
     * @param clanChief The chief in charge of this location.
     */
    public Location(String name, double area, ClanChief clanChief) {
        this.name = name;
        this.area = area;
        this.clanChief = clanChief;
        this.presentCharacters = new ArrayList<>();
        this.presentFood = new ArrayList<>();
        this.environment = Environment.NORMAL;
    }

    //---GETTERS

    public String getName() { return name; }
    public double getArea() { return area; }
    public ClanChief getClanChief() { return clanChief; }
    public List<Character> getPresentCharacters() { return presentCharacters; }
    public List<FoodItem> getPresentFood() { return presentFood; }

    /**
     * @return The specific type of the location (e.g., "Gallic Village").
     */
    public abstract String getType();

    public int getNumberOfCharactersPresent() { return presentCharacters.size(); }
    public Potion getMarmite() { return this.marmite; }

    // --- SETTERS

    public void setArea(double area) { this.area = max(0, area); }
    public void setClanChief(ClanChief clanChief) { this.clanChief = clanChief; }
    public void setEnvironment(Environment env) { this.environment = env; }
    public void setMarmite(Potion p) { this.marmite = p; }

    // --- METHODS

    /**
     * Checks if a magic potion pot (marmite) is currently available in this location.
     * @return true if a pot is present, false otherwise.
     */
    public boolean hasPotion() {
        return this.marmite != null;
    }

    /**
     * Adds a character to the location and logs the event.
     * @param c The character to add.
     */
    public void addCharacter(Character c) {
        this.presentCharacters.add(c);
        System.out.println(c.getName() + " entered " + this.name);
    }

    /**
     * Removes a character from the location if they are present.
     * @param c The character to remove.
     */
    public void removeCharacter(Character c) {
        if (presentCharacters.contains(c)) {
            presentCharacters.remove(c);
            System.out.println(c.getName() + " left " + this.name);
        } else {
            System.out.println(c.getName() + " is not in " + this.name);
        }
    }

    /**
     * Defines whether a specific character is allowed to enter this location.
     * @param c The character trying to enter.
     * @return true if entry is allowed.
     */
    public abstract boolean canEnter(Character c);

    /**
     * Prints the current status of the location, including type, food, and list of characters.
     */
    public void printStatus() {
        System.out.println("\n--- STATUS OF " + name.toUpperCase() + " ---");
        System.out.println("Type: " + getType());
        System.out.println("Food available: " + presentFood);
        System.out.println("Characters present (" + getNumberOfCharactersPresent() + "):");
        for (Character c : presentCharacters) {
            System.out.println(" - " + c.toString());
        }
        System.out.println("-----------------------------------\n");
    }

    /**
     * Heals all characters currently present in the location to full health.
     */
    public void healAllCharacters() {
        System.out.println("Healing everyone in " + name + "...");
        for (Character c : presentCharacters) c.setHealth(100);
    }

    /**
     * Applies environmental effects to characters (e.g., desert heat causes hunger, mountains cause falls).
     */
    public void applyEnvironmentEffects() {
        if (environment == Environment.NORMAL) return;

        System.out.println("\n--- Weather Effects (" + environment + ") ---");
        Random rand = new Random();
        for (Character c : presentCharacters) {
            if (environment == Environment.DESERT) {
                // Desert increases hunger
                c.setHunger(Math.min(100, c.getHunger() + 10));
            } else if (environment == Environment.MOUNTAIN && rand.nextInt(5) == 0) {
                // Mountain has a risk of falling
                c.setHealth(Math.max(0, c.getHealth() - 10));
                System.out.println("Ouch! " + c.getName() + " slipped (-10 Health).");
            }
        }
    }

    @Override
    public String toString() {
        return name + " (" + getType() + ") - " + getNumberOfCharactersPresent() + " pers.";
    }
}