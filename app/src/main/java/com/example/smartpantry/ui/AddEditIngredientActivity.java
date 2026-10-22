package com.example.smartpantry.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantry.R;
import com.example.smartpantry.db.DatabaseHelper;
import com.example.smartpantry.model.PantryItem;
import com.example.smartpantry.util.Prefs;
import com.example.smartpantry.util.UnitUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Locale;

// Add / Edit Ingredient screen. Receives an optional item id through an Intent extra.
public class AddEditIngredientActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_ID = "item_id";

    private DatabaseHelper db;
    private long itemId = -1;

    private TextInputLayout tilName, tilQty, tilExpiry;
    private TextInputEditText etName, etQty, etExpiry;
    private Spinner spUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = DatabaseHelper.getInstance(this);
        tilName = findViewById(R.id.tilName);
        tilQty = findViewById(R.id.tilQty);
        tilExpiry = findViewById(R.id.tilExpiry);
        etName = findViewById(R.id.etName);
        etQty = findViewById(R.id.etQty);
        etExpiry = findViewById(R.id.etExpiry);
        spUnit = findViewById(R.id.spUnit);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnClearDate = findViewById(R.id.btnClearDate);

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, UnitUtils.UNITS);
        spUnit.setAdapter(unitAdapter);
        spUnit.setSelection(indexOfUnit(Prefs.defaultUnit(this)));

        itemId = getIntent().getLongExtra(EXTRA_ITEM_ID, -1);
        if (itemId != -1) {
            setTitle(R.string.title_edit);
            PantryItem item = db.getItem(itemId);
            if (item == null) { // deleted meanwhile
                finish();
                return;
            }
            etName.setText(item.getName());
            etQty.setText(UnitUtils.format(item.getQuantity()));
            spUnit.setSelection(indexOfUnit(item.getUnit()));
            etExpiry.setText(item.getExpiry());
        } else {
            setTitle(R.string.title_add);
        }

        etExpiry.setOnClickListener(v -> showDatePicker());
        btnClearDate.setOnClickListener(v -> etExpiry.setText(""));
        btnSave.setOnClickListener(v -> save());
    }

    private int indexOfUnit(String unit) {
        for (int i = 0; i < UnitUtils.UNITS.length; i++) {
            if (UnitUtils.UNITS[i].equals(unit)) return i;
        }
        return 0;
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, y, m, d) ->
                etExpiry.setText(String.format(Locale.US, "%04d-%02d-%02d", y, m + 1, d)),
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    // Input validation, then INSERT (new) or UPDATE (existing).
    private void save() {
        tilName.setError(null);
        tilQty.setError(null);

        String name = etName.getText() == null ? "" : etName.getText().toString().trim();
        String qtyText = etQty.getText() == null ? "" : etQty.getText().toString().trim();
        String expiry = etExpiry.getText() == null ? "" : etExpiry.getText().toString().trim();

        boolean valid = true;
        if (name.isEmpty()) {
            tilName.setError(getString(R.string.err_name_required));
            valid = false;
        } else if (!name.matches(".*[A-Za-z].*") || name.length() > 40) {
            tilName.setError(getString(R.string.err_name_invalid));
            valid = false;
        }

        double qty = 0;
        if (qtyText.isEmpty()) {
            tilQty.setError(getString(R.string.err_qty_required));
            valid = false;
        } else {
            try {
                qty = Double.parseDouble(qtyText);
                if (qty <= 0 || qty > 100000) {
                    tilQty.setError(getString(R.string.err_qty_range));
                    valid = false;
                }
            } catch (NumberFormatException e) {
                tilQty.setError(getString(R.string.err_qty_invalid));
                valid = false;
            }
        }
        if (!valid) return;

        String unit = (String) spUnit.getSelectedItem();
        String expiryValue = expiry.isEmpty() ? null : expiry;

        if (itemId == -1) {
            db.insertItem(new PantryItem(0, name, qty, unit, expiryValue));
            Toast.makeText(this, R.string.item_added, Toast.LENGTH_SHORT).show();
        } else {
            db.updateItem(new PantryItem(itemId, name, qty, unit, expiryValue));
            Toast.makeText(this, R.string.item_updated, Toast.LENGTH_SHORT).show();
        }
        finish();
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
