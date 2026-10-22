package com.example.smartpantry.util;

import com.example.smartpantry.model.PantryItem;
import com.example.smartpantry.model.Recipe;
import com.example.smartpantry.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


 // Core business logic: the STRICT-MATCHING RULE.

 // A recipe is "suggested" only if EVERY ingredient it requires is in the pantry in at least
 //the required quantity. One missing or insufficient ingredient excludes the recipe.

 // Robustness: names are normalized (case, whitespace, plurals, a few synonyms), units are
 // converted to a base unit (kg->g, l->ml, tbsp/tsp/cup->ml) and several pantry rows for the
 // same ingredient are added together (e.g. 200 g + 0.3 kg flour = 500 g).
 // Counts (pcs) and amounts (g/ml) are kept apart - "2 eggs" never matches "200 g egg".

public final class RecipeMatcher {
    private RecipeMatcher() {}

    private static final double EPS = 1e-9;

    private static String key(String name, String unit) {
        return NameUtils.normalise(name) + "|" + (UnitUtils.isCount(unit) ? "count" : "amount");
    }

    // Total pantry stock per (ingredient, count/amount) key, in base units. */
    public static Map<String, Double> totals(List<PantryItem> pantry) {
        Map<String, Double> totals = new HashMap<>();
        for (PantryItem p : pantry) {
            String k = key(p.getName(), p.getUnit());
            double q = UnitUtils.toBase(p.getQuantity(), p.getUnit());
            Double old = totals.get(k);
            totals.put(k, (old == null ? 0 : old) + q);
        }
        return totals;
    }

    public static boolean hasIngredient(Map<String, Double> totals, RecipeIngredient ing) {
        Double have = totals.get(key(ing.getName(), ing.getUnit()));
        double need = UnitUtils.toBase(ing.getQuantity(), ing.getUnit());
        return have != null && have + EPS >= need;
    }

    public static List<RecipeIngredient> missing(Recipe recipe, Map<String, Double> totals) {
        List<RecipeIngredient> out = new ArrayList<>();
        for (RecipeIngredient ing : recipe.getIngredients()) {
            if (!hasIngredient(totals, ing)) out.add(ing);
        }
        return out;
    }

    // Recipes the user can cook right now (zero missing ingredients). */
    public static List<Recipe> strictMatches(List<Recipe> recipes, List<PantryItem> pantry) {
        Map<String, Double> totals = totals(pantry);
        List<Recipe> result = new ArrayList<>();
        for (Recipe r : recipes) {
            if (!r.getIngredients().isEmpty() && missing(r, totals).isEmpty()) result.add(r);
        }
        return result;
    }

    // Bonus: recipes missing exactly one ingredient. Kept separate from the strict list. */
    public static List<Recipe> almostThere(List<Recipe> recipes, List<PantryItem> pantry) {
        Map<String, Double> totals = totals(pantry);
        List<Recipe> result = new ArrayList<>();
        for (Recipe r : recipes) {
            List<RecipeIngredient> miss = missing(r, totals);
            if (miss.size() == 1) {
                RecipeIngredient m = miss.get(0);
                r.setNote("Missing: " + UnitUtils.format(m.getQuantity()) + " " + m.getUnit() + " " + m.getName());
                result.add(r);
            }
        }
        return result;
    }
}
