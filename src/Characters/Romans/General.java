package Characters.Romans;

import Characters.Sexe;

import static java.lang.Math.min;

class General extends Roman {
    private int nombreVictoires;
    private int nombreSoldats;

    public General(String nom, Sexe sexe, double taille, int age, int force,
                   int endurance, int sante, int faim, int belligerance,
                   int niveauPotionMagique, int nombreVictoires) {
        super(nom, sexe, taille, age, force, endurance, sante, faim, belligerance, niveauPotionMagique);
        this.nombreVictoires = nombreVictoires;
        this.nombreSoldats = 0;
    }

    public int getNombreVictoires() {
        return nombreVictoires;
    }

    public int getNombreSoldats() {
        return nombreSoldats;
    }

    public void recruterSoldats(int nombre) {
        nombreSoldats += nombre;
        System.out.println(nom + " recrute " + nombre + " soldats. Total : " + nombreSoldats);
    }

    public void planifierBataille() {
        System.out.println(nom + " planifie une bataille avec " + nombreSoldats + " soldats.");
        setBelligerance(min(100, getBelligerance() + 20));
    }

    public void remporterVictoire() {
        nombreVictoires++;
        System.out.println(nom + " remporte une victoire ! Total : " + nombreVictoires + " victoires.");
        popularite += 10;
    }

    private int popularite = 50;

    public int getPopularite() {
        return popularite;
    }

    public void haranguerTroupes() {
        System.out.println(nom + " harangue ses troupes avant la bataille !");
        setBelligerance(min(100, getBelligerance() + 15));
    }

    @Override
    public String getType() {
        return "Général Romain";
    }
}
