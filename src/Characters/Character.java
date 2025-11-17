package Characters;

import static java.lang.Math.max;
import static java.lang.Math.min;

public abstract class Character {
    protected String nom;
    protected Sexe sexe;
    protected double taille; // en mètres
    protected int age;
    protected int force; // 0-100
    protected int endurance; // 0-100
    protected int sante; // 0-100
    protected int faim; // 0-100 (0 = pas faim, 100 = très affamé)
    protected int belligerance; // 0-100
    protected int niveauPotionMagique; // 0-100

    public Character(String nom, Sexe sexe, double taille, int age, int force,
                     int endurance, int sante, int faim, int belligerance, int niveauPotionMagique) {
        this.nom = nom;
        this.sexe = sexe;
        this.taille = taille;
        this.age = age;
        this.force = force;
        this.endurance = endurance;
        this.sante = sante;
        this.faim = faim;
        this.belligerance = belligerance;
        this.niveauPotionMagique = niveauPotionMagique;
    }

    // Getters
    public String getNom() { return nom; }
    public Sexe getSexe() { return sexe; }
    public double getTaille() { return taille; }
    public int getAge() { return age; }
    public int getForce() { return force; }
    public int getEndurance() { return endurance; }
    public int getSante() { return sante; }
    public int getFaim() { return faim; }
    public int getBelligerance() { return belligerance; }
    public int getNiveauPotionMagique() { return niveauPotionMagique; }

    // Setters avec validation (valeurs entre 0 et 100)
    public void setSante(int sante) {
        this.sante = max(0, min(100, sante));
    }

    public void setFaim(int faim) {
        this.faim = max(0, min(100, faim));
    }

    public void setBelligerance(int belligerance) {
        this.belligerance = max(0, min(100, belligerance));
    }

    public void setNiveauPotionMagique(int niveau) {
        this.niveauPotionMagique = max(0, min(100, niveau));
    }

    // Méthode abstraite à implémenter par les sous-classes
    public abstract String getType();

    @Override
    public String toString() {
        return String.format("%s (%s) - %s, %d ans, %.2fm\nForce: %d, Endurance: %d, Santé: %d, Faim: %d, Belligérance: %d, Potion: %d",
                nom, getType(), sexe, age, taille, force, endurance, sante, faim, belligerance, niveauPotionMagique);
    }
}