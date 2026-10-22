package com.example.smartpantry.model;

import java.util.ArrayList;
import java.util.List;

/* A recipe with its required ingredients and preparation steps. */
public class Recipe {
    private final long id;
    private final String name;
    private final String steps;
    private final List<RecipeIngredient> ingredients = new ArrayList<>();


    /* Optional note shown in the lists (e.g. "Missing: butter" on the Almost There tab). */
    private String note;

    public Recipe(long id, String name, String steps) {
        this.id = id;
        this.name = name;
        this.steps = steps;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getSteps() { return steps; }
    public List<RecipeIngredient> getIngredients() { return ingredients; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
