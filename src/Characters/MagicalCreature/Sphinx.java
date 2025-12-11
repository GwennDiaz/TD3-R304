package Characters.MagicalCreature;

import Characters.Character;
import Characters.Gender;
import java.util.Scanner;

public class Sphinx extends Character {

    public Sphinx(String name) {
        // Stats : Enorme force, beaucoup de vie
        super(name, Gender.OTHER, 3.0, 1000, 100, 100, 100, 0, 0, 0);
    }

    public void poserEnigme(Character cible) {
        System.out.println("\n--- ÉNIGME DU SPHINX ---");
        System.out.println(this.name + " regarde " + cible.getName() + " dans les yeux.");
        System.out.println("Je suis grand quand je suis jeune, et petit quand je suis vieux. Qui suis-je ?");
        System.out.println("(Tapez votre réponse...)");

        Scanner sc = new Scanner(System.in);
        String reponse = sc.nextLine().toLowerCase();

        // Accepte plusieurs variantes de la réponse "L'ombre" ou "Une bougie"
        if (reponse.contains("ombre") || reponse.contains("bougie") || reponse.contains("feu")) {
            System.out.println("Le Sphinx s'incline. 'Tu es sage'. (+50 PV)");
            cible.setHealth(Math.min(100, cible.getHealth() + 50));
        } else {
            System.out.println("FAUX ! Le Sphinx rugit et frappe ! (-30 PV)");
            cible.setHealth(Math.max(0, cible.getHealth() - 30));
        }
    }

    @Override
    public String getType() { return "Sphinx Mystique"; }
}