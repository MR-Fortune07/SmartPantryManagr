package com.example.smartpantry.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantry.R;
import com.example.smartpantry.db.DatabaseHelper;
import com.example.smartpantry.model.PantryItem;
import com.example.smartpantry.model.Recipe;
import com.example.smartpantry.util.RecipeMatcher;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.List;

/**
 * Suggested Recipes screen. "Can cook now" runs the strict-matching rule;
 * "Almost there" (bonus) is a clearly separate tab for recipes missing exactly one ingredient.
 */
public class SuggestedRecipesActivity extends AppCompatActivity implements RecipeAdapter.Listener {

    private DatabaseHelper db;
    private RecipeAdapter adapter;
    private TextView tvEmpty, tvHint;
    private boolean almostMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested);
        setTitle(R.string.title_suggested);

        db = DatabaseHelper.getInstance(this);
        tvEmpty = findViewById(R.id.tvEmpty);
        tvHint = findViewById(R.id.tvHint);

        RecyclerView rv = findViewById(R.id.rvRecipes);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeAdapter(this);
        rv.setAdapter(adapter);

        MaterialButtonToggleGroup group = findViewById(R.id.toggleMode);
        group.check(R.id.btnStrict);
        group.addOnButtonCheckedListener((g, checkedId, isChecked) -> {
            if (isChecked) {
                almostMode = (checkedId == R.id.btnAlmost);
                refresh();
            }
        });

        BottomNavigationView nav = findViewById(R.id.bottomNav);
        BottomNavHelper.setup(this, nav, R.id.nav_recipes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        List<PantryItem> pantry = db.getAllItems();
        List<Recipe> all = db.getAllRecipes();

        List<Recipe> shown;
        if (almostMode) {
            shown = RecipeMatcher.almostThere(all, pantry);
            tvHint.setText(R.string.hint_almost);
            tvEmpty.setText(R.string.empty_almost);
        } else {
            shown = RecipeMatcher.strictMatches(all, pantry);
            tvHint.setText(R.string.hint_strict);
            tvEmpty.setText(R.string.empty_strict);
        }
        adapter.setRecipes(shown);
        tvEmpty.setVisibility(shown.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Intent i = new Intent(this, RecipeDetailActivity.class);
        i.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.getId());
        startActivity(i);
    }
}
