package Location.Place;
import Location.Location;
import Characters.Character;
import Characters.MagicalCreature;
import Characters.Gallics.Gallic;

    public class Enclosure extends Location {

        public Enclosure(String name, double area, Gallic clanChief) {
            super(name, area, clanChief);
        }

        @Override
        public void addCharacter(Character character) {
            if (character instanceof MagicalCreature) {
                super.addCharacter(character);
            } else {
                System.out.println(character.getName() + " is not a creature and cannot enter the Enclosure!");
            }
        }

        @Override
        public String getType() {
            return "Enclosure";
        }
    }