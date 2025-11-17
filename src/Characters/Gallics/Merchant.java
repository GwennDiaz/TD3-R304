package Characters.Gallics;

import Characters.Sexe;

class Merchant extends Gallic {
    private int argentPossede;

    public Merchant(String nom, Sexe sexe, double taille, int age, int force,
                    int endurance, int sante, int faim, int belligerance,
                    int niveauPotionMagique, int argentPossede) {
        super(nom, sexe, taille, age, force, endurance, sante, faim, belligerance, niveauPotionMagique);
        this.argentPossede = argentPossede;
    }

    public int getArgentPossede() {
        return argentPossede;
    }

    public void setArgentPossede(int argent) {
        this.argentPossede = argent;
    }

    public void vendre(int montant) {
        this.argentPossede += montant;
        System.out.println(nom + " a vendu pour " + montant + " pièces d'or.");
    }

    public boolean acheter(int montant) {
        if (argentPossede >= montant) {
            this.argentPossede -= montant;
            System.out.println(nom + " a acheté pour " + montant + " pièces d'or.");
            return true;
        } else {
            System.out.println(nom + " n'a pas assez d'argent !");
            return false;
        }
    }

    @Override
    public String getType() {
        return "Marchand Gaulois";
    }
}

