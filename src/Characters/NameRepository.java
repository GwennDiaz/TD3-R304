package Characters;

import java.util.*;

/**
 * Classe pour stocker et gérer les listes de noms des personnages
 * Cette classe est indépendante et peut être utilisée partout dans l'application
 */
public class  NameRepository {

    // Listes statiques de noms Gaulois
    private static final List<String> NAME_MERCHANDT = Arrays.asList(
            "Astérix", "Obélix", "Goudurix", "Tragicomix", "Jolitorax", "Riad"
    );

    private static final List<String> NAME_INNKEEPER = Arrays.asList(
            "Ordralfabétix", "Porquépix", "Téléférix", "Plaintcontrix", "Moralélastix"
    );

    private static final List<String> NAME_BLACKSMITH = Arrays.asList(
            "Cétautomatix", "Métalpolitix", "Agecanonix", "Bénéfix", "Amérix", "Alexandre"
    );

    private static final List<String> NAME_DRUIDE = Arrays.asList(
            "Panoramix", "Diagnostix", "Assurancetourix", "Amnésix", "Septantesix"
    );

    // Listes statiques de noms Romains
    private static final List<String> NAME_LEGIONNAIRE = Arrays.asList(
            "Brutus", "Tullius Détritus", "Marcus Sacapus", "Plexus", "Caius Obtus",
            "Gracchus Nenjetépus", "Claudius Quiétus", "Terminus", "Bonus", "Malus"
    );

    private static final List<String> NAME_PREFECT = Arrays.asList(
            "Caius Bonus", "Encrenus", "Anglaigus", "Encyclopédius", "Motus"
    );

    private static final List<String> NAME_GENERAL = Arrays.asList(
            "Julius Caesar", "Pompée", "Labienus", "Crassus", "Antoine", "Gwenn"
    );

    // Listes statiques de noms de Lycanthropes
    private static final List<String> NAME_LYCANTHROPE = Arrays.asList(
            "Fenrir", "Lupin", "Remus", "Greyback", "Amarok",
            "Lycaon", "Asena", "Morrighan", "Volkodlak", "Loup-Garou"
    );

    // Random pour sélection aléatoire
    private static final Random random = new Random();

    /**
     * Récupère un nom aléatoire de marchand
     */
    public static String getNameMerchant() {
        return NAME_MERCHANDT.get(random.nextInt(NAME_MERCHANDT.size()));
    }

    /**
     * Récupère un nom aléatoire d'aubergiste
     */
    public static String getNameInnkeeper() {
        return NAME_INNKEEPER.get(random.nextInt(NAME_INNKEEPER.size()));
    }

    /**
     * Récupère un nom aléatoire de forgeron
     */
    public static String getNameBlacksmith() {
        return NAME_BLACKSMITH.get(random.nextInt(NAME_BLACKSMITH.size()));
    }

    /**
     * Récupère un nom aléatoire de druide
     */
    public static String getNameDruide() {
        return NAME_DRUIDE.get(random.nextInt(NAME_DRUIDE.size()));
    }

    /**
     * Récupère un nom aléatoire de légionnaire
     */
    public static String getNameLegionnaire() {
        return NAME_LEGIONNAIRE.get(random.nextInt(NAME_LEGIONNAIRE.size()));
    }

    /**
     * Récupère un nom aléatoire de préfet
     */
    public static String getNamePrefect() {
        return NAME_PREFECT.get(random.nextInt(NAME_PREFECT.size()));
    }

    /**
     * Récupère un nom aléatoire de général
     */
    public static String getNameGeneral() {
        return NAME_GENERAL.get(random.nextInt(NAME_GENERAL.size()));
    }

    /**
     * Récupère un nom aléatoire de lycanthrope
     */
    public static String getNameLycanthrope() {
        return NAME_LYCANTHROPE.get(random.nextInt(NAME_LYCANTHROPE.size()));
    }

    /**
     * Récupère un nom aléatoire selon le type de personnage
     * @param type Le type de personnage (marchand, druide, legionnaire, etc.)
     * @return Un nom aléatoire correspondant au type
     */
    public static String getNameByType(String type) {
        switch(type.toLowerCase()) {
            case "marchand": return getNameMerchant();
            case "aubergiste": return getNameInnkeeper();
            case "forgeron": return getNameBlacksmith();
            case "druide": return getNameDruide();
            case "legionnaire": return getNameLegionnaire();
            case "prefet": return getNamePrefect();
            case "general": return getNameGeneral();
            case "lycanthrope": return getNameLycanthrope();
            default: return "Inconnu";
        }
    }

    /**
     * Récupère la liste complète des noms d'un type
     */
    public static List<String> getListNameByType(String type) {
        switch(type.toLowerCase()) {
            case "marchand": return new ArrayList<>(NAME_MERCHANDT);
            case "aubergiste": return new ArrayList<>(NAME_INNKEEPER);
            case "forgeron": return new ArrayList<>(NAME_BLACKSMITH);
            case "druide": return new ArrayList<>(NAME_DRUIDE);
            case "legionnaire": return new ArrayList<>(NAME_LEGIONNAIRE);
            case "prefet": return new ArrayList<>(NAME_PREFECT);
            case "general": return new ArrayList<>(NAME_GENERAL);
            case "lycanthrope": return new ArrayList<>(NAME_LYCANTHROPE);
            default: return new ArrayList<>();
        }
    }

    /**
     * Compte le nombre total de noms disponibles
     */
    public static int getTotalNumberNames() {
        return NAME_MERCHANDT.size() + NAME_INNKEEPER.size() +
                NAME_BLACKSMITH.size() + NAME_DRUIDE.size() +
                NAME_LEGIONNAIRE.size() + NAME_PREFECT.size() +
                NAME_GENERAL.size() + NAME_LYCANTHROPE.size();
    }

    /**
     * Affiche toutes les listes de noms disponibles
     */
    public static void displayAllNames() {
        System.out.println("=== REPOSITORY DES NOMS ===\n");

        System.out.println("GAULOIS :");
        System.out.println("  Marchands : " + NAME_MERCHANDT);
        System.out.println("  Aubergistes : " + NAME_INNKEEPER);
        System.out.println("  Forgerons : " + NAME_BLACKSMITH);
        System.out.println("  Druides : " + NAME_DRUIDE);

        System.out.println("\nROMAINS :");
        System.out.println("  Légionnaires : " + NAME_LEGIONNAIRE);
        System.out.println("  Préfets : " + NAME_PREFECT);
        System.out.println("  Généraux : " + NAME_GENERAL);

        System.out.println("\nCREATURES :");
        System.out.println("  Lycanthropes : " + NAME_LYCANTHROPE);

        System.out.println("\nTotal : " + getTotalNumberNames() + " noms disponibles");
    }
}