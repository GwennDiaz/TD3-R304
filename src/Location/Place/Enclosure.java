package Location.Place;

import Location.Location;
import Characters.Character;
import Characters.MagicalCreature.Lycanthrope.Lycanthrope;
import Characters.Roles.ClanChief;

public class Enclosure extends Location {

    public Enclosure(String name, double area, ClanChief clanChief) {
        super(name, area, clanChief);
    }

    @Override
    public void addCharacter(Character c) {
        // Loup-Garou uniquement
        if (c instanceof Lycanthrope) {
            super.addCharacter(c);
        } else {
            System.out.println(c.getName() + " : Entrée refusée (Enclos réservé aux créatures).");
        }
    }

    public boolean canEnter(Character c) {
        return c instanceof Characters.Gallics.Gallic || c instanceof Characters.Romans.Roman || c instanceof Lycanthrope ;
    }

    @Override
    public String getType() { return "Enclos"; }
}