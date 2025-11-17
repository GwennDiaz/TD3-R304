package Location.Place;
import Location.Location;
import Characters.Character;
import Characters.Romans.RomanSoldier;
import Characters.MagicalCreature.Lycanthrope;
import Characters.Gallics.Gallic;

public class RomanFortifiedCamp extends Location {

    public RomanFortifiedCamp(String name, double area, Gallic clanChief) {
        super(name, area, clanChief);
    }

    @Override
    public void addCharacter(Character character) {
        // Check if the character is specifically a Roman Soldier OR a Creature
        if (character instanceof RomanSoldier || character instanceof Lycanthrope) {
            super.addCharacter(character);
        } else {
            System.out.println(character.getName() + " cannot enter the Roman Camp (Soldiers only)!");
        }
    }

    @Override
    public String getType() {
        return "Roman Fortified Camp";
    }
}
