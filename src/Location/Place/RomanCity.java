package Location.Place;
import Location.Location;
import Characters.Character;
import Characters.Romans.Roman;
import Characters.MagicalCreature.Lycanthrope;
import Characters.Gallics.Gallic;

public class RomanCity {

    public class RomanCity extends Location {

        public RomanCity(String name, double area, Gallic clanChief) {
            super(name, area, clanChief);
        }

        @Override
        public void addCharacter(Character character) {
            if (character instanceof Roman || character instanceof Lycanthrope) {
                super.addCharacter(character);
            } else {
                System.out.println(character.getName() + " cannot enter the Roman City!");
            }
        }

        @Override
        public String getType() {
            return "Roman City";
        }
    }
}