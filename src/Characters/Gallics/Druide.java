package Characters.Gallics;

import Characters.Character;
import Characters.Sexe;

import static java.lang.Math.min;

class Druide extends Gallic {
    private int puissanceMagique; // 0-100

    public Druide(String nom, Sexe sexe, double taille, int age, int force,
                  int endurance, int sante, int faim, int belligerance,
                  int niveauPotionMagique, int puissanceMagique) {
        super(nom, sexe, taille, age, force, endurance, sante, faim, belligerance, niveauPotionMagique);
        this.puissanceMagique = puissanceMagique;
    }

    public int getPuissanceMagique() {
        return puissanceMagique;
    }

    public void preparerPotion(Character cible, int quantite) {
        if (puissanceMagique >= 50) {
            cible.setNiveauPotionMagique(cible.getNiveauPotionMagique() + quantite);
            System.out.println(nom + " prépare une potion magique pour " + cible.getNom() + " (+" + quantite + ")");
        } else {
            System.out.println(nom + " n'a pas assez de puissance magique pour préparer une potion !");
        }
    }

    public void soigner(Character cible) {
        int soin = puissanceMagique / 2;
        cible.setSante(min(100, cible.getSante() + soin));
        System.out.println(nom + " soigne " + cible.getNom() + " (+" + soin + " santé)");
    }

    public void cueillirGui() {
        System.out.println(nom + " cueille du gui sacré avec sa serpe d'or.");
        puissanceMagique = min(100, puissanceMagique + 5);
    }

    @Override
    public String getType() {
        return "Druide Gaulois";
    }
}
