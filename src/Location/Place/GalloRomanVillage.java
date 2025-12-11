package Location.Place;

import Location.Location;
import Characters.Character;
import Characters.Gallics.Gallic;
import Characters.Romans.Roman;
import Characters.Roles.ClanChief;

public class GalloRomanVillage extends Location {

    public GalloRomanVillage(String name, double area, ClanChief clanChief) {
        super(name, area, clanChief);
    }

    @Override
    public void addCharacter(Character c) {
        // Humains uniquement (Gaulois OU Romain OU Chef)
        if (c instanceof Gallic || c instanceof Roman || c instanceof ClanChief) {
            super.addCharacter(c);
        } else {
            System.out.println(c.getName() + " : Entrée refusée (Pas de créatures ici).");
        }
    }
    @Override
    public boolean canEnter(Character c) {
        return c instanceof Gallic || c instanceof Roman || c instanceof Characters.MagicalCreature.Lycanthrope.Lycanthrope ;
    }

    @Override
    public String getType() { return "Bourgade Gallo-Romaine"; }
}

