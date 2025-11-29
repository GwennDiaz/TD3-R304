package Characters.MagicalCreature;

import Characters.Character;
import Characters.Gender;

import static java.lang.Math.max;
import static java.lang.Math.min;

// Classe Lycanthrope (Loup-garou)
public class Lycanthrope extends Character {
    private boolean humanForm;
    private int originalStrength;
    private int originalBelligerence;

    public Lycanthrope(String name, Gender gender, double height, int age, int strength,
                       int endurance, int health, int hunger, int belligerence,
                       int magicPotionLevel) {
        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);
        this.humanForm = true;
        this.originalStrength = strength;
        this.originalBelligerence = belligerence;
    }

    public boolean estFormeHumaine() {
        return humanForm;
    }

    // Transformation en loup-garou
    public void seTransformer() {
        if (humanForm) {
            // Transformation en loup-garou
            humanForm = false;
            this.strength = (int)(originalStrength * 1.5);
            this.belligerence = min(100, originalBelligerence + 30);
            this.height = height * 1.2; // Le loup-garou est plus grand
            System.out.println(name + " se transforme en loup-garou");
            System.out.println("Force : " + originalStrength + " -> " + this.strength);
            System.out.println("Belligérance : " + originalBelligerence + " -> " + this.belligerence);
        } else {
            System.out.println(name + " est déjà sous forme de loup-garou !");
        }
    }

    // Retour à la forme humaine
    public void reprendreFormeHumaine() {
        if (!humanForm) {
            humanForm = true;
            this.strength = originalStrength;
            this.belligerence = originalBelligerence;
            this.height = height / 1.2; // Retour à la taille normale
            System.out.println(name + " reprend forme humaine.");
            System.out.println("Force : " + this.strength);
            System.out.println("Belligérance : " + this.belligerence);
        } else {
            System.out.println(name + " est déjà sous forme humaine");
        }
    }

    // Hurler (seulement en forme loup-garou)
    public void hurler() {
        if (!humanForm) {
            System.out.println(name + " hurle à la lune");
            setBelligerence(min(100, getBelligerence() + 10));
        } else {
            System.out.println(name + " ne peut pas hurler sous forme humaine");
        }
    }

    // Chasser (plus efficace en forme loup-garou)
    public void chasser() {
        if (!humanForm) {
            System.out.println(name + " chasse sous forme de loup-garou");
            setHunger(max(0, getHunger() - 40));
            this.endurance = max(0, this.endurance - 15);
        } else {
            System.out.println(name + " chasse sous forme humaine");
            setHunger(max(0, getHunger() - 20));
            this.endurance = max(0, this.endurance - 10);
        }
    }

    // Attaquer avec rage (seulement en forme loup-garou)
    public void attaquerAvecRage(Character cible) {
        if (!humanForm) {
            System.out.println(name + " attaque " + cible.getName() + " avec rage !");
            int degats = this.strength / 5;
            cible.setHealth(max(0, cible.getHealth() - degats));
            System.out.println(cible.getName() + " subit " + degats + " points de dégâts !");

            // L'attaque fatigue et affame le lycanthrope
            setHunger(min(100, getHunger() + 20));
            this.endurance = max(0, this.endurance - 20);
        } else {
            System.out.println(name + " doit être transformé pour attaquer avec rage");
        }
    }

    // Vérifier si la transformation est nécessaire
    public boolean doitSeTransformer() {
        return (getHunger() > 70 || getBelligerence() > 80) && humanForm;
    }

    @Override
    public String getType() {
        return humanForm ? "Lycanthrope (forme humaine)" : "Lycanthrope (loup-garou)";
    }

    @Override
    public String toString() {
        String forme = humanForm ? "Forme humaine" : "Forme loup-garou";
        return super.toString() + "\n" + forme;
    }

    @Override
    public void fight(Character adversaire) {
        if (!humanForm) {
            // En forme loup-garou : attaque puissante avec rage
            System.out.println(name + " attaque " + adversaire.getName() + " avec rage en forme de loup-garou !");
            attaquerAvecRage(adversaire);
        } else {
            // En forme humaine : combat normal
            System.out.println(name + " combat " + adversaire.getName() + " sous forme humaine.");
            fight(adversaire);  // Utilise la méthode héritée de Personnage
        }
    }
}
