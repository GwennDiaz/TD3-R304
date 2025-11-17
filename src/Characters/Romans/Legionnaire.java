package Characters.Romans;

import Characters.Character;
import Characters.Sexe;

import static java.lang.Math.max;
import static java.lang.Math.min;

class Legionnaire extends Roman {
    private String legion;
    private boolean enService;

    public Legionnaire(String nom, Sexe sexe, double taille, int age, int force,
                       int endurance, int sante, int faim, int belligerance,
                       int niveauPotionMagique, String legion) {
        super(nom, sexe, taille, age, force, endurance, sante, faim, belligerance, niveauPotionMagique);
        this.legion = legion;
        this.enService = true;
    }

    public String getLegion() {
        return legion;
    }

    public boolean isEnService() {
        return enService;
    }

    public void patrouiller() {
        if (enService) {
            System.out.println(nom + " de la " + legion + " patrouille.");
            setFaim(min(100, getFaim() + 15));
            this.endurance = max(0, this.endurance - 10);
        } else {
            System.out.println(nom + " n'est pas en service.");
        }
    }

    public void combattre(Character adversaire) {
        System.out.println(nom + " combat contre " + adversaire.getNom() + " !");
        int degats = this.force / 10;
        adversaire.setSante(max(0, adversaire.getSante() - degats));
        setBelligerance(min(100, getBelligerance() + 10));
    }

    public void seReposer() {
        enService = false;
        System.out.println(nom + " se repose.");
        setFaim(max(0, getFaim() - 20));
        this.endurance = min(100, this.endurance + 20);
    }

    @Override
    public String getType() {
        return "Légionnaire Romain";
    }
}
