package Location.Place;
import Location.Location;
import Characters.Character;
import Characters.Gallics.Gallic;
import Characters.MagicalCreature;

public class GallicVillage extends {

    public GallicVillage(String name, double area, Gallic clanChief) {
        super(name, area, clanChief);
    }

    @Override
    public void addCharacter(Character character) {
        if (character instanceof Gallic || character instanceof MagicalCreature) {
            super.addCharacter(character);
        } else {
            System.out.println(character.getName() + " cannot enter the Gallic Village!");
        }
    }

    @Override
    public String getType() {
        return "Gallic Village";
    }
}