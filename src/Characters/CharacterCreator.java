package Characters;

import Characters.Gallics.Blacksmith;
import Characters.Gallics.Druid;
import Characters.Gallics.Innkeeper;
import Characters.Gallics.Merchant;
import Characters.MagicalCreature.Lycanthrope;
import Characters.Romans.General;
import Characters.Romans.Legionnaire;
import Characters.Romans.Prefect;

import java.util.*;
import java.util.concurrent.*;

// Classe pour créer des personnages dans un thread


// Classe pour créer des personnages dans un thread
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
            System.out.println("Thread " + Thread.currentThread().getName() + " : Création de " + name + " (" + type + ")...");

            // Simulation du temps de création
            Thread.sleep(random.nextInt(500) + 200);

            // Génération des caractéristiques aléatoires
            Gender gender = random.nextBoolean() ? Gender.MALE : Gender.FEMALE;
            double height = 1.50 + random.nextDouble() * 0.50; // Entre 1.50m et 2.00m
            int age = 20 + random.nextInt(60); // Entre 20 et 80 ans
            int strength = 40 + random.nextInt(60); // Entre 40 et 100
            int endurance = 40 + random.nextInt(60);
            int health = 80 + random.nextInt(21); // Entre 80 et 100
            int hunger = random.nextInt(50); // Entre 0 et 50
            int belligerence = random.nextInt(100);
            int magicPotionLevel = random.nextInt(50);

            Character character = null;

            // Création selon le type
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
                            health, hunger, belligerence, magicPotionLevel, legion) {
                    };
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
                    character = new Lycanthrope(name, gender, height, age, strength, endurance,
                            health, hunger, belligerence, magicPotionLevel);
                    break;
            }

            if (character != null) {
                synchronized(listCharacters) {
                    listCharacters.add(character);
                }
                System.out.println("Thread " + Thread.currentThread().getName() + " : " + name + " créé avec succès !");
            }

        } catch (InterruptedException e) {
            System.err.println("Erreur lors de la création de " + name);
            e.printStackTrace();
        }
    }
}