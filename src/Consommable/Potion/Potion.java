package Consommable.Potion;

import java.util.*;

/**
 * 1 dose  -> effet temporaire multiplié par le nombre de doses consommées
 * 1 marmite entière (== dosesPerMarmite doses) effet permanent
 * 2 marmites ou plus -> transformation en statue de granit (fatale)
 *
 * La classe renvoie un ConsumptionResult qui contient :
 * - le type de résultat : TEMPORAIRE / PERMANENT / FATAL / NONE
 * - l'effet combiné appliqué (ou null si FATAL/NONE)
 * - un message descriptif de l'action (si implémenté....)
 */

public class Potion {

    private final String name;
    private final List<String> ingredients;
    private final PotionsEffect effectPerDose;
    private final int dosesPerMarmite;
    private final Map<String, PotionsEffect> additivesEffects;

    public Potion(String name, List<String> ingredients, PotionsEffect effectPerDose, int dosesPerMarmite) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("name must be provided");
        if (effectPerDose == null) throw new IllegalArgumentException("effectPerDose must be provided");
        if (dosesPerMarmite <= 0) throw new IllegalArgumentException("dosesPerMarmite must be > 0");
        this.name = name;
        this.ingredients = ingredients == null ? new ArrayList<>() : new ArrayList<>(ingredients);
        this.effectPerDose = effectPerDose;
        this.dosesPerMarmite = dosesPerMarmite;
        this.additivesEffects = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public PotionsEffect getEffectPerDose() {
        return effectPerDose;
    }

    public int getDosesPerMarmite() {
        return dosesPerMarmite;
    }

    /**
     * Additif pour les effets secondaire (Lait de licorne à deux têtes", "Poils d'Idéfix)"
     */
    public void registerAdditive(String additiveName, PotionsEffect additiveEffect) {
        if (additiveName == null || additiveName.trim().isEmpty()) throw new IllegalArgumentException("additiveName required");
        if (additiveEffect == null) throw new IllegalArgumentException("additiveEffect required");
        additivesEffects.put(additiveName, additiveEffect);
    }

    /**
     * Calcule résultat de la consommation avec une liste
     * d'additifs  si fournie.
     * Comportement :
     * - si >= 2 marmites consommées -> FATAL
     * - si exactement 1 marmite consommée et pas de doses en plus -> PERMANENT
     * - sinon -> TEMPORARY
     *
     * Les effets deltas sont sommés: effet de base multiplié par le nombre de doses + les effets
     * des additifs
     *
     * @param dosesConsumed nombre de doses consommées (>= 0)
     * @param additivesUsed liste d'additifs de la potion
     * @return ConsumptionResult -> type d'effet et l'effet combiné (null si fatal)
     */
    public ConsumptionResult consume(int dosesConsumed, List<String> additivesUsed) {
        if (dosesConsumed <= 0) {
            return new ConsumptionResult(ResultType.NONE, null, "0 dose concumed : no effect.");
        }

        int potsEntieres = dosesConsumed / dosesPerMarmite;
        int leftoverDoses = dosesConsumed % dosesPerMarmite;

        // FATAL
        if (potsEntieres >= 2) {
            return new ConsumptionResult(ResultType.FATAL, null,
                    "Effect fatal : transformation in granit statue (consommation of " + potsEntieres + " pots).");
        }

        // Calcul effect
        PotionsEffect totalEffect = scaleEffect(effectPerDose, dosesConsumed);

        // add of additives (only once each)
        StringBuilder additivesDesc = new StringBuilder();
        if (additivesUsed != null) {
            for (String add : additivesUsed) {
                PotionsEffect addEffect = additivesEffects.get(add);
                if (addEffect != null) {
                    totalEffect = combineEffects(totalEffect, addEffect);
                    if (additivesDesc.length() > 0) additivesDesc.append(" + ");
                    additivesDesc.append(add);
                } else {
                    // additive unknow -> ignore
                    if (additivesDesc.length() > 0) additivesDesc.append(" + ");
                    additivesDesc.append(add).append(" (non reconnu)");
                }
            }
        }

        // Message and type
        if (potsEntieres == 1 && leftoverDoses == 0) {
            // 1 pots -> permanent
            String msg = "Consommation of one pots : effects now permanents.";
            if (additivesDesc.length() > 0) msg += " additives apply : " + additivesDesc + ".";

            PotionsEffect permanentEffect = new PotionsEffect(
                    totalEffect.getHealthDelta(),
                    totalEffect.getHungerDelta(),
                    totalEffect.getBelligerenceDelta(),
                    totalEffect.getMagicLevelDelta(),
                    totalEffect.getDescription() + (totalEffect.getDescription().isEmpty() ? "" : " ") + "[PERMANENT]"
            );
            return new ConsumptionResult(ResultType.PERMANENT, permanentEffect, msg);
        } else {
            // less than one pots
            String msg = "Temporary effect for a time (consumption of " + dosesConsumed + " dose(s)).";
            if (additivesDesc.length() > 0) msg += " additive apply : " + additivesDesc + ".";
            return new ConsumptionResult(ResultType.TEMPORARY, totalEffect, msg);
        }
    }

    private PotionsEffect scaleEffect(PotionsEffect base, int multiplier) {
        if (multiplier == 1) return base;
        return new PotionsEffect(
                base.getHealthDelta() * multiplier,
                base.getHungerDelta() * multiplier,
                base.getBelligerenceDelta() * multiplier,
                base.getMagicLevelDelta() * multiplier,
                base.getDescription() + (multiplier > 1 ? " (x" + multiplier + " doses)" : "")
        );
    }

    private PotionsEffect combineEffects(PotionsEffect a, PotionsEffect b) {
        String descA = a == null ? "" : a.getDescription();
        String descB = b == null ? "" : b.getDescription();
        String combinedDesc;
        if (descA.isEmpty()) combinedDesc = descB;
        else if (descB.isEmpty()) combinedDesc = descA;
        else combinedDesc = descA + " + " + descB;

        int health = (a == null ? 0 : a.getHealthDelta()) + (b == null ? 0 : b.getHealthDelta());
        int hunger = (a == null ? 0 : a.getHungerDelta()) + (b == null ? 0 : b.getHungerDelta());
        int bell = (a == null ? 0 : a.getBelligerenceDelta()) + (b == null ? 0 : b.getBelligerenceDelta());
        int magic = (a == null ? 0 : a.getMagicLevelDelta()) + (b == null ? 0 : b.getMagicLevelDelta());

        return new PotionsEffect(health, hunger, bell, magic, combinedDesc);
    }

    public enum ResultType {
        NONE,       // 0 effect
        TEMPORARY,
        PERMANENT,
        FATAL       // transformation in granit statue
    }

    public static class ConsumptionResult {
        private final ResultType resultType;
        private final PotionsEffect effect;
        private final String message;

        public ConsumptionResult(ResultType resultType, PotionsEffect effect, String message) {
            this.resultType = resultType;
            this.effect = effect;
            this.message = message == null ? "" : message;
        }

        public ResultType getResultType() {
            return resultType;
        }

        public PotionsEffect getEffect() {
            return effect;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "ConsumptionResult{" +
                    "resultType=" + resultType +
                    ", effect=" + (effect == null ? "null" : effect) +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}