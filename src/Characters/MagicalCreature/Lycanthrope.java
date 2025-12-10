package Characters.MagicalCreature;

import Characters.Character;
import Characters.Gender;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Lycanthrope extends Character {

    // --- Attributs Spécifiques ---
    private boolean humanForm;
    private int originalStrength;
    private int originalBelligerence; // Sert aussi pour le calcul de rage

    // Caractéristiques demandées
    private AgeCategory ageCategory;
    private int dominationFactor; // Domination exercée - subie
    private Rank rank;
    private double impetuosityFactor; // Facteur d'impétuosité
    private Pack pack; // La meute (null si solitaire)

    // Constructeur complet
    public Lycanthrope(String name, Gender gender, double height, int age, int strength,
                       int endurance, int health, int hunger, int belligerence,
                       int magicPotionLevel, Rank rank, double impetuosity) { // <--- C'EST ICI QU'IL MANQUE PEUT-ÊTRE "double impetuosity"

        super(name, gender, height, age, strength, endurance, health, hunger, belligerence, magicPotionLevel);

        this.humanForm = true;
        this.originalStrength = strength;
        this.originalBelligerence = belligerence;

        this.rank = rank;
        this.impetuosityFactor = impetuosity; // Maintenant, cette ligne ne sera plus rouge
        this.dominationFactor = 0;
        this.pack = null;

        defineAgeCategory(age);
    }

    // --- Méthodes de calcul interne ---

    private void defineAgeCategory(int age) {
        if (age < 15) this.ageCategory = AgeCategory.JEUNE;
        else if (age > 50) this.ageCategory = AgeCategory.VIEUX;
        else this.ageCategory = AgeCategory.ADULTE;
    }

    // Calcul du "Niveau" (Critère subjectif de qualité)
    public double getLevel() {
        // Formule arbitraire basée sur la consigne : Age + Force + Domination + Rang
        double ageBonus = (ageCategory == AgeCategory.ADULTE) ? 1.5 : 1.0;
        double rankBonus = rank.getValue() * 10;

        // Niveau = (Force * BonusAge) + FacteurDomination + BonusRang
        return (originalStrength * ageBonus) + dominationFactor + rankBonus;
    }

    // --- Gestion de la Meute ---

    public void setPack(Pack pack) {
        this.pack = pack;
        if (pack != null && !pack.getMembers().contains(this)) {
            pack.addMember(this);
        }
    }

    public void seSeparerDeLaMeute() {
        if (this.pack != null) {
            System.out.println(this.getName() + " quitte la meute " + this.pack.getName() + " et devient solitaire.");
            this.pack.removeMember(this);
            this.pack = null;
            // Un loup solitaire perd souvent en rang
            this.rank = Rank.OMEGA;
        } else {
            System.out.println(this.getName() + " est déjà un loup solitaire.");
        }
    }

    // --- Communication (Hurlements) ---

    /**
     * Hurler pour communiquer.
     * @param typeHurlement : "DOMINATION", "APPEL", "DANGER", "PLAISIR"
     */
    public void hurler(String typeHurlement) {
        if (humanForm) {
            System.out.println(getName() + " tente de hurler mais ne produit qu'un cri humain bizarre.");
            return;
        }

        System.out.println(getName() + " pousse un hurlement de type : " + typeHurlement + " !");

        // Impact sur l'impétuosité ou la belligérance
        setBelligerence(min(100, getBelligerence() + 5));

        // Si appartient à une meute, on diffuse le message
        if (pack != null) {
            pack.broadcastHowl(this, typeHurlement);
        } else {
            System.out.println("Le hurlement se perd dans le vide (Loup solitaire).");
        }
    }

    // Entendre un hurlement
    public void entendreHurlement(Lycanthrope emetteur, String typeHurlement) {
        // Si trop malade (ex: santé < 20%), il n'entend pas ou ne réagit pas
        if (this.getHealth() < 20) {
            System.out.println(getName() + " est trop faible pour réagir au hurlement de " + emetteur.getName());
            return;
        }

        System.out.println(getName() + " entend le hurlement (" + typeHurlement + ") de " + emetteur.getName());

        // Réaction selon le type
        switch (typeHurlement) {
            case "DOMINATION":
                if (this.getLevel() < emetteur.getLevel()) {
                    System.out.println(" -> " + getName() + " baisse la tête en signe de soumission.");
                    this.dominationFactor--; // Perd un point de domination
                } else {
                    System.out.println(" -> " + getName() + " grogne en retour ! (Défi)");
                }
                break;
            case "APPEL":
                System.out.println(" -> " + getName() + " se prépare à rejoindre la meute.");
                break;
            default:
                System.out.println(" -> " + getName() + " dresse les oreilles.");
        }
    }

    // --- Affichage des caractéristiques ---

    public void afficherCaracteristiques() {
        System.out.println("\n--- Fiche Lycanthrope : " + getName() + " ---");
        System.out.println("Sexe : " + getGender());
        System.out.println("Catégorie d'âge : " + ageCategory);
        System.out.println("Force (Originale) : " + originalStrength);
        System.out.println("Rang : " + rank);
        System.out.println("Facteur Domination : " + dominationFactor);
        System.out.println("Facteur Impétuosité : " + impetuosityFactor);
        System.out.println("Niveau Global (Qualité) : " + getLevel());
        System.out.println("Meute : " + (pack != null ? pack.getName() : "Solitaire"));
        System.out.println("Forme actuelle : " + (humanForm ? "Humain" : "Loup-Garou"));
        System.out.println("-----------------------------------");
    }

    @Override
    public String toString() {
        return super.toString() + " | Rang: " + rank + " | Niveau: " + getLevel();
    }

    // --- Méthodes de Transformation (Héritées/Adaptées) ---

    // Transformation en loup-garou
    public void seTransformer() {
        if (humanForm) {
            humanForm = false;
            // La force augmente, boostée par l'impétuosité
            this.strength = (int)(originalStrength * 1.5 + (impetuosityFactor * 2));
            this.belligerence = min(100, originalBelligerence + 30);
            this.height = height * 1.2;
            System.out.println(getName() + " se transforme en loup-garou ! (Force augmentée à " + this.strength + ")");
        } else {
            System.out.println(getName() + " est déjà sous forme de loup-garou !");
        }
    }

    // Retour à la forme humaine (Méthode demandée "se transformer en humain")
    public void reprendreFormeHumaine() {
        if (!humanForm) {
            humanForm = true;
            this.strength = originalStrength;
            this.belligerence = originalBelligerence;
            this.height = height / 1.2;
            System.out.println(getName() + " reprend forme humaine.");
        } else {
            System.out.println(getName() + " est déjà sous forme humaine");
        }
    }

    // Getters pour les nouvelles propriétés si besoin
    public AgeCategory getAgeCategory() { return ageCategory; }
    public Rank getRank() { return rank; }
    public int getDominationFactor() { return dominationFactor; }

    @Override
    public String getType() {
        return humanForm ? "Lycanthrope (forme humaine)" : "Lycanthrope (loup-garou)";
    }
}