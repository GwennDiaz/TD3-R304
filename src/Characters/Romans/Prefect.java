package Characters.Romans;

import Characters.Sexe;

import static java.lang.Math.min;

class Prefect extends Roman {
    private String province;
    private int popularite; // 0-100

    public Prefect(String nom, Sexe sexe, double taille, int age, int force,
                   int endurance, int sante, int faim, int belligerance,
                   int niveauPotionMagique, String province) {
        super(nom, sexe, taille, age, force, endurance, sante, faim, belligerance, niveauPotionMagique);
        this.province = province;
        this.popularite = 50;
    }

    public String getProvince() {
        return province;
    }

    public int getPopularite() {
        return popularite;
    }

    public void administrerProvince() {
        System.out.println(nom + " administre la province de " + province);
        popularite = min(100, popularite + 5);
    }

    public void leverImpots() {
        System.out.println(nom + " lève des impôts dans la province de " + province);
        popularite = Math.max(0, popularite - 10);
        setBelligerance(min(100, getBelligerance() + 5));
    }

    public void organiserJeux() {
        System.out.println(nom + " organise des jeux dans la province de " + province);
        popularite = min(100, popularite + 15);
    }

    public void donnerOrdre(Legionnaire legionnaire) {
        System.out.println(nom + " donne un ordre à " + legionnaire.getNom());
    }

    @Override
    public String getType() {
        return "Préfet Romain";
    }
}
