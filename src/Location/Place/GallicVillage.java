package Location.Place;

import Location.Location;
import Characters.Character;
import Characters.Origin;
import Characters.Gallics.Gallic;
import Characters.MagicalCreature.Lycanthrope;
import Characters.Roles.ClanChief;

public class GallicVillage extends Location {

    public GallicVillage(String name, double area, ClanChief clanChief) {
        super(name, area, clanChief);
    }

    @Override
    public void addCharacter(Character c) {
        // Condition unique : Gaulois OU Loup OU Chef d'origine Gauloise
        if (c instanceof Gallic || c instanceof Lycanthrope ||
                (c instanceof ClanChief && ((ClanChief) c).getOrigin() == Origin.GALLIC)) {
            super.addCharacter(c);
        } else {
            System.out.println(c.getName() + " : Entrée refusée (Village Gaulois).");
        }
    }

    @Override
    public String getType() { return "Village Gaulois"; }
}