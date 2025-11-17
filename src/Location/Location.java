package Location;
import Characters.Gallics.Gallic;
import java.util.*;
import static java.lang.Math.max;

public abstract class Location {
    protected String name;
    protected double area;
    protected Gallic clanChief;
    protected int numberOfCharactersPresent;
    protected List<Character> presentCharacters;
    protected List<String> presentFood;

    public Location(String name, double area, Gallic clanChief) {
        this.name = name;
        this.area = area;
        this.clanChief = clanChief;
        this.presentCharacters = new ArrayList<>();
        this.presentFood = new ArrayList<>();
    }

    //---GETTERS---

    public String getName() { return name; }
    public double getArea() { return area; }
    public Gallic getClanChief() { return clanChief; }
    public int getNumberOfCharactersPresent() {
        return presentCharacters.size();}
    public List<Character> getPresentCharacters() {
        return presentCharacters; }
    public List<String> getPresentFood() {
        return presentFood; }

    // --- SETTERS---

    public void setArea(double area) {
        this.area = max(0, area);
    }
    public void setClanChief(Gallic clanChief) {
        this.clanChief = clanChief;
    }
    public abstract String getType();

    //Method who add a new character
    public void addCharacter(Character c) {
        this.presentCharacters.add(c);
        System.out.println(c.getName() + " entered into " + this.name);
    }
}
