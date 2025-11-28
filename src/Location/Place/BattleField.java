package Location.Place;

import Characters.Character;
import Location.Location;
import Characters.Roles.ClanChief;

public class BattleField extends Location {

    public BattleField(String name, double area, ClanChief clanChief) {
        super(name, area, clanChief);
    }

    @Override
    public void addCharacter(Character c) {
        // Aucune restriction
        super.addCharacter(c);
    }

    @Override
    public String getType() { return "Champ de Bataille"; }
}