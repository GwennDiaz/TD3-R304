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

public abstract class Location {
    protected String name;
    protected double area;
    protected ClanChief clanChief;
    protected List<Character> presentCharacters = new CopyOnWriteArrayList<>();    protected List<FoodItem> presentFood;
    protected Environment environment;
    protected Potion marmite;

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
    public abstract String getType();
    public int getNumberOfCharactersPresent() { return presentCharacters.size(); }
    public Potion getMarmite() {return this.marmite;}

    // --- SETTERS
    public void setArea(double area) { this.area = max(0, area); }
    public void setClanChief(ClanChief clanChief) { this.clanChief = clanChief; }
    public void setEnvironment(Environment env) { this.environment = env; }
    public void setMarmite(Potion p) {this.marmite = p;}

    // --- METHODS
    public boolean hasPotion() {
        return this.marmite != null;
    }

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

    public abstract boolean canEnter(Character c);

    // Display characteristics
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

    // Heal characters
    public void healAllCharacters() {
        System.out.println("Healing everyone in " + name + "...");
        for (Character c : presentCharacters) c.setHealth(100);
    }


    public void applyEnvironmentEffects() {
        if (environment == Environment.NORMAL) return;
        System.out.println("\n--- Effets Météo (" + environment + ") ---");
        Random rand = new Random();
        for (Character c : presentCharacters) {
            if (environment == Environment.DESERT) {
                c.setHunger(Math.min(100, c.getHunger() + 10));
            } else if (environment == Environment.MOUNTAIN && rand.nextInt(5) == 0) {
                c.setHealth(Math.max(0, c.getHealth() - 10));
                System.out.println("Aïe ! " + c.getName() + " a glissé (-10 Vie).");
            }
        }
    }

    @Override
    public String toString() {
        return name + " (" + getType() + ") - " + getNumberOfCharactersPresent() + " pers.";
    }
}