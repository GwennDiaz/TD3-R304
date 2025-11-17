package Characters.Gallics;

import Characters.Character;
import Characters.Sexe;

import static java.lang.Math.max;

class Innkeeper extends Gallic {
    private int capaciteAccueil;
    private int clientsPresents;

    public Innkeeper(String nom, Sexe sexe, double taille, int age, int force,
                     int endurance, int sante, int faim, int belligerance,
                     int niveauPotionMagique, int capaciteAccueil) {
        super(nom, sexe, taille, age, force, endurance, sante, faim, belligerance, niveauPotionMagique);
        this.capaciteAccueil = capaciteAccueil;
        this.clientsPresents = 0;
    }

    public int getCapaciteAccueil() {
        return capaciteAccueil;
    }

    public int getClientsPresents() {
        return clientsPresents;
    }

    public boolean accueillirClient() {
        if (clientsPresents < capaciteAccueil) {
            clientsPresents++;
            System.out.println(nom + " accueille un client. (" + clientsPresents + "/" + capaciteAccueil + ")");
            return true;
        } else {
            System.out.println("L'auberge de " + nom + " est complète !");
            return false;
        }
    }

    public void servirRepas(Character client) {
        client.setFaim(max(0, client.getFaim() - 30));
        System.out.println(nom + " sert un repas à " + client.getNom());
    }

    @Override
    public String getType() {
        return "Aubergiste Gaulois";
    }
}
