package com.example.smartpantry.util;

import java.util.Locale;

// Unit handling: aliases, conversion to a base unit and number formatting.
public final class UnitUtils {
    private UnitUtils() {}

    //Units offered in the app's unit spinner.
    public static final String[] UNITS = {"g", "kg", "ml", "l", "tsp", "tbsp", "cup", "pcs"};

    //Maps user/recipe spellings (gram, litres, piece...) onto one of {@link #UNITS}.
    public static String normalise(String unit) {
        if (unit == null) return "pcs";
        String u = unit.trim().toLowerCase(Locale.ROOT);
        switch (u) {
            case "g": case "gram": case "grams": case "gm": return "g";
            case "kg": case "kilo": case "kilos": case "kilogram": case "kilograms": return "kg";
            case "ml": case "millilitre": case "millilitres": case "milliliter": case "milliliters": return "ml";
            case "l": case "litre": case "litres": case "liter": case "liters": return "l";
            case "tsp": case "teaspoon": case "teaspoons": return "tsp";
            case "tbsp": case "tablespoon": case "tablespoons": return "tbsp";
            case "cup": case "cups": return "cup";
            default: return "pcs";
        }
    }

    //True when the unit counts whole items (pcs) rather than an amount (weight/volume).
    public static boolean isCount(String unit) {
        return "pcs".equals(normalise(unit));
    }

    //Converts a quantity to its base unit: grams/millilitres for amounts, items for counts.
     //Weight and volume share one scale (1 g is treated as roughly 1 ml) so that
     //"1 cup flour" in a recipe can be satisfied by "500 g flour" in the pantry.

    public static double toBase(double quantity, String unit) {
        switch (normalise(unit)) {
            case "kg": case "l": return quantity * 1000.0;
            case "tsp": return quantity * 5.0;
            case "tbsp": return quantity * 15.0;
            case "cup": return quantity * 240.0;
            default: return quantity; // g, ml, pcs
        }
    }

    // 2 -> "2", 2.50 -> "2.5".
    public static String format(double q) {
        if (q == Math.rint(q)) return String.valueOf((long) q);
        return String.format(Locale.US, "%.2f", q).replaceAll("0+$", "").replaceAll("\\.$", "");
    }
}
