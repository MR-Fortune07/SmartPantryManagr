package com.example.smartpantry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.smartpantry.model.PantryItem;
import com.example.smartpantry.model.Recipe;
import com.example.smartpantry.model.RecipeIngredient;
import com.example.smartpantry.util.NameUtils;
import com.example.smartpantry.util.RecipeMatcher;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Unit tests for the strict-matching rule (run with ./gradlew test).
public class RecipeMatcherTest {

    private static Recipe omelette() {
        Recipe r = new Recipe(1, "Omelette", "x");
        r.getIngredients().add(new RecipeIngredient("egg", 3, "pcs"));
        r.getIngredients().add(new RecipeIngredient("cheese", 50, "g"));
        r.getIngredients().add(new RecipeIngredient("butter", 10, "g"));
        return r;
    }

    private static PantryItem p(String n, double q, String u) {
        return new PantryItem(0, n, q, u, null);
    }

    @Test
    public void allIngredientsPresent_isSuggested() {
        List<Recipe> res = RecipeMatcher.strictMatches(Arrays.asList(omelette()),
                Arrays.asList(p("Eggs", 6, "pcs"), p("Cheese", 0.1, "kg"), p("butter", 250, "g")));
        assertEquals(1, res.size());
    }

    @Test
    public void oneMissingIngredient_isExcluded() {
        List<Recipe> res = RecipeMatcher.strictMatches(Arrays.asList(omelette()),
                Arrays.asList(p("egg", 3, "pcs"), p("cheese", 50, "g")));
        assertTrue(res.isEmpty());
    }

    @Test
    public void insufficientQuantity_isExcluded() {
        List<Recipe> res = RecipeMatcher.strictMatches(Arrays.asList(omelette()),
                Arrays.asList(p("egg", 2, "pcs"), p("cheese", 50, "g"), p("butter", 10, "g")));
        assertTrue(res.isEmpty());
    }

    @Test
    public void separateRowsAreAddedTogether() {
        List<Recipe> res = RecipeMatcher.strictMatches(Arrays.asList(omelette()),
                Arrays.asList(p("egg", 2, "pcs"), p("egg", 1, "pcs"), p("cheese", 50, "g"), p("butter", 10, "g")));
        assertEquals(1, res.size());
    }

    @Test
    public void almostThere_listsRecipesMissingExactlyOne() {
        List<Recipe> res = RecipeMatcher.almostThere(Arrays.asList(omelette()),
                Arrays.asList(p("egg", 3, "pcs"), p("cheese", 50, "g")));
        assertEquals(1, res.size());
        assertTrue(res.get(0).getNote().contains("butter"));
    }

    @Test
    public void countAndWeightAreNotMixed() {
        List<Recipe> res = RecipeMatcher.strictMatches(Arrays.asList(omelette()),
                Arrays.asList(p("egg", 300, "g"), p("cheese", 50, "g"), p("butter", 10, "g")));
        assertTrue(res.isEmpty());
    }

    @Test
    public void namesAreNormalised() {
        assertEquals("tomato", NameUtils.normalise("Tomatoes"));
        assertEquals("tomato", NameUtils.normalise(" TOMATO "));
        assertEquals("berry", NameUtils.normalise("berries"));
        assertEquals("cherry tomato", NameUtils.normalise("cherry tomatoes"));
        assertEquals("egg", NameUtils.normalise("eggs"));
        assertFalse(NameUtils.normalise("rice").equals("ric"));
        assertEquals("grass", NameUtils.normalise("grass"));
    }
}
