package com.example.smartpantry.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantry.R;
import com.example.smartpantry.util.Prefs;
import com.example.smartpantry.util.UnitUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

// Settings screen: expiring-soon alert toggle and default unit (stored in SharedPreferences).
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(R.string.title_settings);

        SwitchMaterial swAlerts = findViewById(R.id.swAlerts);
        swAlerts.setChecked(Prefs.alertsEnabled(this));
        swAlerts.setOnCheckedChangeListener((b, checked) -> Prefs.setAlertsEnabled(this, checked));

        Spinner spUnit = findViewById(R.id.spDefaultUnit);
        spUnit.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, UnitUtils.UNITS));
        String current = Prefs.defaultUnit(this);
        for (int i = 0; i < UnitUtils.UNITS.length; i++) {
            if (UnitUtils.UNITS[i].equals(current)) spUnit.setSelection(i);
        }
        spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Prefs.setDefaultUnit(SettingsActivity.this, UnitUtils.UNITS[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        BottomNavigationView nav = findViewById(R.id.bottomNav);
        BottomNavHelper.setup(this, nav, R.id.nav_settings);
    }
}
