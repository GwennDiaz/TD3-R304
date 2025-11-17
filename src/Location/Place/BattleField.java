package Location.Place;
import Characters.Character;
import Location.Location;
import Characters.Gallics.Gallic;

public class BattleField extends Location {

    public BattleField(String name, double area, Gallic clanChief) {
        super(name, area, clanChief);
    }

    @Override
    public void addCharacter(Character character) {
        super.addCharacter(character);
    }

    @Override
    public String getType() {
        return "Battlefield";
    }
}