package Location.Place;

import Location.Location;
import Characters.Character;
import Characters.Origin;
import Characters.Romans.Roman;
import Characters.MagicalCreature.Lycanthrope.Lycanthrope;
import Characters.Roles.ClanChief;

public class RomanCity extends Location {

    public RomanCity(String name, double area, ClanChief clanChief) {
        super(name, area, clanChief);
    }

    @Override
    public void addCharacter(Character c) {
        // N'importe quel Romain OU Loup OU Chef Romain
        if (c instanceof Roman || c instanceof Lycanthrope ||
                (c instanceof ClanChief && ((ClanChief) c).getOrigin() == Origin.ROMAN)) {
            super.addCharacter(c);
        } else {
            System.out.println(c.getName() + " : Entrée refusée (Cité Romaine).");
        }
    }

    public boolean canEnter(Characters.Character c) {
        return c instanceof Characters.Romans.Roman || c instanceof Characters.MagicalCreature.Lycanthrope.Lycanthrope ;
    }

    @Override
    public String getType() { return "Cité Romaine"; }
}