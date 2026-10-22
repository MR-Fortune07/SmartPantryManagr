package com.example.smartpantry.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.smartpantry.R;
import com.example.smartpantry.db.DatabaseHelper;
import com.example.smartpantry.model.Recipe;
import com.example.smartpantry.model.RecipeIngredient;
import com.example.smartpantry.util.RecipeMatcher;
import com.example.smartpantry.util.UnitUtils;

import java.util.Map;

// Recipe Detail screen: full ingredient list (with a have / missing mark) and the method.
public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "recipe_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseHelper db = DatabaseHelper.getInstance(this);
        Recipe recipe = db.getRecipe(getIntent().getLongExtra(EXTRA_RECIPE_ID, -1));
        if (recipe == null) {
            finish();
            return;
        }
        setTitle(recipe.getName());

        Map<String, Double> totals = RecipeMatcher.totals(db.getAllItems());
        int green = ContextCompat.getColor(this, R.color.primary);
        int red = ContextCompat.getColor(this, R.color.expired);

        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (RecipeIngredient ing : recipe.getIngredients()) {
            boolean have = RecipeMatcher.hasIngredient(totals, ing);
            String line = (have ? "\u2713  " : "\u2717  ")
                    + UnitUtils.format(ing.getQuantity()) + " " + ing.getUnit() + "  " + ing.getName() + "\n";
            int start = sb.length();
            sb.append(line);
            sb.setSpan(new ForegroundColorSpan(have ? green : red), start, start + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        ((TextView) findViewById(R.id.tvIngredients)).setText(sb);

        // number the preparation steps
        String[] steps = recipe.getSteps().split("\n");
        StringBuilder method = new StringBuilder();
        for (int i = 0; i < steps.length; i++) {
            method.append(i + 1).append(". ").append(steps[i]).append("\n\n");
        }
        ((TextView) findViewById(R.id.tvSteps)).setText(method.toString().trim());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
