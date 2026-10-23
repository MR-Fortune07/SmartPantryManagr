package com.example.smartpantry.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantry.R;
import com.example.smartpantry.db.DatabaseHelper;
import com.example.smartpantry.model.PantryItem;
import com.example.smartpantry.util.AppDates;
import com.example.smartpantry.util.Prefs;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

// Pantry List screen: READ + entry point for CREATE / UPDATE / DELETE.
public class MainActivity extends AppCompatActivity implements PantryAdapter.Listener {

    private DatabaseHelper db;
    private PantryAdapter adapter;
    private TextView tvEmpty, tvBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_pantry);

        db = DatabaseHelper.getInstance(this);
        tvEmpty = findViewById(R.id.tvEmpty);
        tvBanner = findViewById(R.id.tvExpiryBanner);

        RecyclerView rv = findViewById(R.id.rvPantry);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PantryAdapter(this);
        rv.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> startActivity(new Intent(this, AddEditIngredientActivity.class)));

        BottomNavigationView nav = findViewById(R.id.bottomNav);
         BottomNavHelper.setup(this, nav, R.id.nav_pantry);
    }

    // The list is reloaded every time the screen comes back to the foreground, so changes made
    // in AddEditIngredientActivity (or in a previous session) always show up.
    @Override
    // reloads the list each time the screen comes back
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        List<PantryItem> items = db.getAllItems();
        adapter.setItems(items);
        tvEmpty.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);

        int expiring = 0;
        for (PantryItem i : items) {
            Integer d = AppDates.daysUntil(i.getExpiry());
            if (d != null && d <= 3) expiring++;
        }
        if (Prefs.alertsEnabled(this) && expiring > 0) {
            tvBanner.setVisibility(View.VISIBLE);
            tvBanner.setText(getString(R.string.expiry_banner, expiring));
        } else {
            tvBanner.setVisibility(View.GONE);
        }
    }

    @Override
    public void onEdit(PantryItem item) {
        Intent i = new Intent(this, AddEditIngredientActivity.class);
        i.putExtra(AddEditIngredientActivity.EXTRA_ITEM_ID, item.getId());
        startActivity(i);
    }

    @Override
    public void onDelete(final PantryItem item) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_title)
                .setMessage(getString(R.string.delete_message, item.getName()))
                .setPositiveButton(R.string.delete, (d, w) -> {
                    db.deleteItem(item.getId());
                    Toast.makeText(this, R.string.item_deleted, Toast.LENGTH_SHORT).show();
                    refresh();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
