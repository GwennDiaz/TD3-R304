package Location.Place;

import Location.Location;
import Characters.Character;
import Characters.Origin;
import Characters.Romans.Legionnaire;
import Characters.Romans.General;
import Characters.MagicalCreature.Lycanthrope;
import Characters.Roles.ClanChief;

public class RomanFortifiedCamp extends Location {

    public RomanFortifiedCamp(String name, double area, ClanChief clanChief) {
        super(name, area, clanChief);
    }

    @Override
    public void addCharacter(Character c) {
        // Combattants Romains OU Loup OU Chef Romain
        if (c instanceof Legionnaire || c instanceof General || c instanceof Lycanthrope ||
                (c instanceof ClanChief && ((ClanChief) c).getOrigin() == Origin.ROMAN)) {
            super.addCharacter(c);
        } else {
            System.out.println(c.getName() + " : Entrée refusée (Camp Militaire Romain).");
        }
    }

    @Override
    public boolean canEnter(Character c) {
        // Accepts Roman
        return c instanceof Characters.Romans.Roman;
    }

    @Override
    public String getType() { return "Camp Romain"; }
}