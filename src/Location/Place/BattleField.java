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

    public boolean canEnter(Character c) {
        return c instanceof Characters.Gallics.Gallic || c instanceof Characters.Romans.Roman || c instanceof Characters.MagicalCreature.Lycanthrope.Lycanthrope ;
    }

    @Override
    public String getType() { return "Champ de Bataille"; }
}