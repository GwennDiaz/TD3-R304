package Characters.Gallics;

import Characters.Sexe;

import static java.lang.Math.max;
import static java.lang.Math.min;

class Blacksmith extends Gallic {
    private int qualiteArmes; // 0-100

    public Blacksmith(String nom, Sexe sexe, double taille, int age, int force,
                      int endurance, int sante, int faim, int belligerance,
                      int niveauPotionMagique, int qualiteArmes) {
        super(nom, sexe, taille, age, force, endurance, sante, faim, belligerance, niveauPotionMagique);
        this.qualiteArmes = qualiteArmes;
    }

    public int getQualiteArmes() {
        return qualiteArmes;
    }

    public void forgerArme() {
        System.out.println(nom + " forge une arme de qualité " + qualiteArmes + ".");
        setFaim(min(100, getFaim() + 10));
        // Forger fatigue le forgeron
        this.endurance = max(0, this.endurance - 5);
    }

    public void reparerArme() {
        System.out.println(nom + " répare une arme.");
    }

    @Override
    public String getType() {
        return "Forgeron Gaulois";
    }
}

