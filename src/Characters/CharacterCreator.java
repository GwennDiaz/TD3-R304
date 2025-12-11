package Characters;

import Characters.Gallics.Blacksmith;
import Characters.Gallics.Druid;
import Characters.Gallics.Innkeeper;
import Characters.Gallics.Merchant;
import Characters.MagicalCreature.Lycanthrope.Lycanthrope;
import Characters.MagicalCreature.Lycanthrope.Rank;
import Characters.Romans.General;
import Characters.Romans.Legionnaire;
import Characters.Romans.Prefect;

import java.util.*;

public class CharacterCreator implements Runnable {
    private String name;
    private String type;
    private List<Character> listCharacters;
    private Random random;

    public CharacterCreator(String name, String type, List<Character> listCharacters) {
        this.name = name;
        this.type = type;
        this.listCharacters = listCharacters;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread " + Thread.currentThread().getName() + " : Creation of " + name + " (" + type + ")...");

            // Simulating creation time
            Thread.sleep(random.nextInt(500) + 200);

            // Generating random stats
            Gender gender = random.nextBoolean() ? Gender.MALE : Gender.FEMALE;
            double height = 1.50 + random.nextDouble() * 0.50;
            int age = 20 + random.nextInt(60);
            int strength = 40 + random.nextInt(60);
            int endurance = 40 + random.nextInt(60);
            int health = 80 + random.nextInt(21);
            int hunger = random.nextInt(50);
            int belligerence = random.nextInt(100);
            int magicPotionLevel = random.nextInt(50);

            Character character = null;

            switch(type.toLowerCase()) {
                case "marchand":
                    int money = 500 + random.nextInt(1500);
                    character = new Merchant(name, gender, height, age, strength, endurance,
                            health, hunger, belligerence, magicPotionLevel, money);
                    break;

                case "aubergiste":
                    int hostingCapacity = 10 + random.nextInt(40);
                    character = new Innkeeper(name, gender, height, age, strength, endurance,
                            health, hunger, belligerence, magicPotionLevel, hostingCapacity);
                    break;

                case "forgeron":
                    int weaponQuality = 50 + random.nextInt(50);
                    character = new Blacksmith(name, gender, height, age, strength, endurance,
                            health, hunger, belligerence, magicPotionLevel, weaponQuality);
                    break;

                case "druide":
                    int magicPower = 70 + random.nextInt(30);
                    character = new Druid(name, gender, height, age, strength, endurance,
                            health, hunger, belligerence, magicPotionLevel, magicPower);
                    break;

                case "legionnaire":
                    String[] legions = {"Legio I", "Legio II", "Legio III", "Legio X", "Legio XII"};
                    String legion = legions[random.nextInt(legions.length)];
                    character = new Legionnaire(name, gender, height, age, strength, endurance,
                            health, hunger, belligerence, magicPotionLevel, legion);
                    break;

                case "prefet":
                    String[] provinces = {"Gaule", "Hispanie", "Germanie", "Britannia", "Africa"};
                    String province = provinces[random.nextInt(provinces.length)];
                    character = new Prefect(name, gender, height, age, strength, endurance,
                            health, hunger, belligerence, magicPotionLevel, province);
                    break;

                case "general":
                    int victoryCount = random.nextInt(100);
                    character = new General(name, gender, height, age, strength, endurance,
                            health, hunger, belligerence, magicPotionLevel, victoryCount);
                    break;

                case "lycanthrope":
                    // CORRECTION : Ajout des paramètres manquants pour le TD4 (Rank + Impetuosity)
                    // Par défaut, un lycanthrope créé aléatoirement est OMEGA avec impétuosité moyenne
                    character = new Lycanthrope(name, gender, height, age, strength, endurance,
                            health, hunger, belligerence, magicPotionLevel,
                            Rank.OMEGA, 1.0);
                    break;
            }

            if (character != null) {
                synchronized(listCharacters) {
                    listCharacters.add(character);
                }
                System.out.println("Thread " + Thread.currentThread().getName() + " : " + name + " created successfully!");
            }

        } catch (InterruptedException e) {
            System.err.println("Error creating " + name);
            e.printStackTrace();
        }
    }
}