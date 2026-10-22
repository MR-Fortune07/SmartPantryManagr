package com.example.smartpantry.ui;

import android.app.Activity;
import android.content.Intent;

import com.example.smartpantry.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

// Wires the shared bottom navigation bar used by the three top-level screens. //
final class BottomNavHelper {
    private BottomNavHelper() {}

    static void setup(final Activity current, BottomNavigationView nav, final int selectedId) {
        nav.setSelectedItemId(selectedId);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == selectedId) return true;

            Class<?> target;
            if (id == R.id.nav_pantry) target = MainActivity.class;
            else if (id == R.id.nav_recipes) target = SuggestedRecipesActivity.class;
            else target = SettingsActivity.class;

            Intent intent = new Intent(current, target);
            if (target == MainActivity.class) {
                // go back to the existing pantry screen instead of stacking a new one
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            }
            current.startActivity(intent);
            current.overridePendingTransition(0, 0);
            if (!(current instanceof MainActivity)) current.finish();
            return true;
        });
    }
}
