package Location.Place;

import Location.Location;
import Characters.Character;
import Characters.Origin;
import Characters.Romans.Legionnaire;
import Characters.Romans.General;
import Characters.MagicalCreature.Lycanthrope.Lycanthrope;
import Characters.Roles.ClanChief;

public class RomanFortifiedCamp extends Location {
    private int moral;

    public RomanFortifiedCamp(String name, double area, ClanChief clanChief) {
        super(name, area, clanChief);
        this.moral = 100;}

    public void decreaseMoral(int amount) {
        this.moral = Math.max(0, this.moral - amount);
        System.out.println("ALERTE ! Le moral de la garnison chute à " + moral + "/100.");
        if (moral < 20) {
            System.out.println("PANIQUE GÉNÉRALE ! LES PORTES SONT VERROUILLÉES !");
        }
    }

    // Méthode pour le chef : Remotiver les troupes
    public void rallyTroops() {
        this.moral = 100;
        System.out.println("Le chef a remotivé la garnison ! Les portes rouvrent.");
    }
    @Override
    public void addCharacter(Character c) {
        if (c instanceof Legionnaire || c instanceof General || c instanceof Lycanthrope ||
                (c instanceof ClanChief && ((ClanChief) c).getOrigin() == Origin.ROMAN)) {
            super.addCharacter(c);
        } else {
            System.out.println(c.getName() + " : Entrée refusée (Camp Militaire Romain).");
        }
    }

    @Override
    public String getType() { return "Camp Romain"; }
}