package Characters.Gallics;

import Characters.Character;
import Characters.Sexe;

abstract class Gallic extends Character {
    public Gallic(String nom, Sexe sexe, double taille, int age, int force,
                  int endurance, int sante, int faim, int belligerance, int niveauPotionMagique) {
        super(nom, sexe, taille, age, force, endurance, sante, faim, belligerance, niveauPotionMagique);
    }
}
