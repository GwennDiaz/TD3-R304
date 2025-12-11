package Characters.MagicalCreature;

import Characters.Character;
import Characters.Gender;

public class Korrigan extends Character {

    public Korrigan(String name) {
        // Stats : petit, rapide, mais faible en vie
        super(name, Gender.MALE, 0.5, 200, 10, 80, 50, 10, 10, 0);
    }

    public void deroberMagie(Character cible) {
        if (cible.getMagicPotionLevel() > 0) {
            System.out.println(this.name + " ricane et vole la potion magique de " + cible.getName() + " !");
            int vol = cible.getMagicPotionLevel();
            cible.setMagicPotionLevel(0);
            // Le Korrigan récupère la potion pour lui
            this.setMagicPotionLevel(this.getMagicPotionLevel() + vol);
        } else {
            System.out.println(this.name + " fouille " + cible.getName() + " mais ne trouve rien.");
        }
    }

    @Override
    public String getType() { return "Korrigan Voleur"; }
}