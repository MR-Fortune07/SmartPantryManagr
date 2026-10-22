package com.example.smartpantry.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

// Normalizes ingredient names so "Tomatoes", "tomato " and "TOMATO" all match. */
public final class NameUtils {
    private NameUtils() {}

    private static final Map<String, String> SYNONYMS = new HashMap<>();
    static {
        SYNONYMS.put("aubergine", "eggplant");
        SYNONYMS.put("scallion", "spring onion");
        SYNONYMS.put("capsicum", "bell pepper");
        SYNONYMS.put("mince", "minced beef");
        SYNONYMS.put("ground beef", "minced beef");
        SYNONYMS.put("spud", "potato");
        SYNONYMS.put("courgette", "zucchini");
        SYNONYMS.put("coriander leaf", "cilantro");
    }

    public static String normalise(String raw) {
        if (raw == null) return "";
        String s = raw.toLowerCase(Locale.ROOT).replaceAll("[^a-z ]", " ").trim().replaceAll("\\s+", " ");
        if (s.isEmpty()) return s;
        // singularize only the last word ("cherry tomatoes" -> "cherry tomato")
        int sp = s.lastIndexOf(' ');
        String head = sp >= 0 ? s.substring(0, sp + 1) : "";
        String result = head + singular(s.substring(sp + 1));
        String syn = SYNONYMS.get(result);
        return syn != null ? syn : result;
    }

    static String singular(String w) {
        int n = w.length();
        if (n > 4 && w.endsWith("ies")) return w.substring(0, n - 3) + "y";          // berries -> berry
        if (n > 3 && w.endsWith("oes")) return w.substring(0, n - 2);                // tomatoes -> tomato
        if (n > 4 && (w.endsWith("ches") || w.endsWith("shes")
                || w.endsWith("xes") || w.endsWith("sses"))) return w.substring(0, n - 2); // radishes -> radish
        if (n > 3 && w.endsWith("s") && !w.endsWith("ss") && !w.endsWith("us")) {     // eggs -> egg
            return w.substring(0, n - 1);
        }
        return w;
    }
}
