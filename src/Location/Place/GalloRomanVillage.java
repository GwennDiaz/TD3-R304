package Location.Place;
import Location.Location;
import Characters.Character;
import Characters.Gallics.Gallic;
import Characters.Romans.Roman;

public class GalloRomanVillage {

    public class GalloRomanTown extends Location {

        public GalloRomanTown(String name, double area, Gallic clanChief) {
            super(name, area, clanChief);
        }

        @Override
        public void addCharacter(Character character) {
            if (character instanceof Gallic || character instanceof Roman) {
                super.addCharacter(character);
            } else {
                System.out.println(character.getName() + " cannot enter the Gallo-Roman Town!");
            }
        }

        @Override
        public String getType() {
            return "Gallo-Roman Town";
        }
    }
}