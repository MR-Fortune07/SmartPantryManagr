package com.example.smartpantry.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

//Preloads the recipe collection on first run (22 recipes).
final class SeedData {
    private SeedData() {}

    // Ingredient format: "name;quantity;unit"
    static void seed(SQLiteDatabase db) {
        add(db, "Scrambled Eggs on Toast",
                "Toast the bread.\nWhisk the eggs with the salt.\nMelt the butter in a pan and stir the eggs gently until just set.\nServe on the toast.",
                "egg;3;pcs", "bread;2;pcs", "butter;10;g", "salt;1;tsp");
        add(db, "Tomato Pasta",
                "Boil the pasta in salted water.\nFry chopped onion and garlic in olive oil.\nAdd chopped tomatoes and simmer 10 minutes.\nToss with the drained pasta.",
                "pasta;200;g", "tomato;4;pcs", "onion;1;pcs", "garlic;2;pcs", "olive oil;2;tbsp", "salt;1;tsp");
        add(db, "Cheese Omelette",
                "Beat the eggs with the salt.\nMelt butter in a pan and pour in the eggs.\nAdd grated cheese, fold in half and cook 1 more minute.",
                "egg;3;pcs", "cheese;50;g", "butter;10;g", "salt;1;tsp");
        add(db, "Egg Fried Rice",
                "Cook and cool the rice.\nStir-fry diced onion and carrot in oil.\nPush aside, scramble the eggs, then mix everything with the rice and soy sauce.",
                "rice;200;g", "egg;2;pcs", "onion;1;pcs", "carrot;1;pcs", "soy sauce;2;tbsp", "oil;1;tbsp");
        add(db, "Pancakes",
                "Whisk flour, sugar, egg and milk into a smooth batter.\nMelt a little butter in a pan.\nCook ladles of batter 2 minutes per side until golden.",
                "flour;200;g", "milk;300;ml", "egg;2;pcs", "sugar;2;tbsp", "butter;20;g");
        add(db, "Mashed Potatoes",
                "Peel and cut the potatoes, boil in salted water until soft.\nDrain and mash with butter and warm milk.\nSeason to taste.",
                "potato;500;g", "butter;30;g", "milk;100;ml", "salt;1;tsp");
        add(db, "Chicken Stir-Fry",
                "Slice the chicken, pepper and onion.\nFry the chicken in oil until cooked.\nAdd vegetables and garlic, stir-fry 5 minutes, finish with soy sauce.",
                "chicken breast;300;g", "bell pepper;1;pcs", "onion;1;pcs", "soy sauce;2;tbsp", "oil;1;tbsp", "garlic;2;pcs");
        add(db, "Vegetable Soup",
                "Chop potatoes, carrots and onion.\nSimmer everything in the stock for 25 minutes.\nSeason with salt and serve hot.",
                "potato;2;pcs", "carrot;2;pcs", "onion;1;pcs", "vegetable stock;500;ml", "salt;1;tsp");
        add(db, "Cheese Toastie",
                "Butter the outside of the bread.\nPlace cheese between the slices.\nToast in a pan 2-3 minutes per side until melted.",
                "bread;2;pcs", "cheese;60;g", "butter;10;g");
        add(db, "Peanut Butter Banana Toast",
                "Toast the bread.\nSpread the peanut butter.\nTop with sliced banana.",
                "bread;2;pcs", "peanut butter;2;tbsp", "banana;1;pcs");
        add(db, "Beans on Toast",
                "Warm the beans in a pot.\nToast and butter the bread.\nSpoon beans over the toast.",
                "baked beans;400;g", "bread;2;pcs", "butter;10;g");
        add(db, "Tuna Pasta",
                "Boil the pasta.\nGently fry crushed garlic in olive oil, add tuna.\nToss with the pasta and a squeeze of lemon.",
                "pasta;200;g", "tuna;160;g", "olive oil;1;tbsp", "garlic;1;pcs", "lemon;1;pcs");
        add(db, "Spaghetti Bolognese",
                "Brown the mince with onion and garlic.\nAdd chopped tomatoes and simmer 20 minutes.\nServe over cooked spaghetti.",
                "spaghetti;250;g", "minced beef;300;g", "tomato;3;pcs", "onion;1;pcs", "garlic;2;pcs", "salt;1;tsp");
        add(db, "Banana Smoothie",
                "Peel the bananas.\nBlend with the milk and honey until smooth.\nServe cold.",
                "banana;2;pcs", "milk;250;ml", "honey;1;tbsp");
        add(db, "Greek Salad",
                "Chop cucumber, tomatoes and onion.\nCrumble the feta on top.\nDress with olive oil.",
                "cucumber;1;pcs", "tomato;2;pcs", "onion;1;pcs", "feta;100;g", "olive oil;2;tbsp");
        add(db, "Creamy Porridge",
                "Combine the oats and milk in a pot.\nStir over medium heat for 5 minutes.\nServe with honey.",
                "oats;80;g", "milk;300;ml", "honey;1;tbsp");
        add(db, "Potato Wedges",
                "Heat the oven to 220 C.\nCut potatoes into wedges and toss with oil, salt and paprika.\nBake 30-35 minutes, turning once.",
                "potato;600;g", "olive oil;2;tbsp", "salt;1;tsp", "paprika;1;tsp");
        add(db, "Chicken Rice Bowl",
                "Cook the rice.\nPan-fry the chicken, steam the broccoli.\nServe over rice with soy sauce.",
                "chicken breast;250;g", "rice;200;g", "broccoli;150;g", "soy sauce;1;tbsp");
        add(db, "French Toast",
                "Whisk eggs, milk and sugar.\nSoak the bread slices.\nFry in butter until golden on both sides.",
                "bread;4;pcs", "egg;2;pcs", "milk;100;ml", "sugar;1;tbsp", "butter;15;g");
        add(db, "Lentil Curry",
                "Fry onion and garlic, stir in curry powder.\nAdd lentils, tomatoes and water.\nSimmer 25 minutes until soft.",
                "lentil;200;g", "onion;1;pcs", "tomato;2;pcs", "garlic;2;pcs", "curry powder;2;tsp", "water;500;ml");
        add(db, "Avocado Toast",
                "Toast the bread.\nMash the avocado with lemon juice and salt.\nSpread on the toast.",
                "bread;2;pcs", "avocado;1;pcs", "lemon;1;pcs", "salt;1;tsp");
        add(db, "Mac and Cheese",
                "Boil the macaroni.\nMake a sauce with butter, flour and milk, melt in the cheese.\nCombine with the macaroni.",
                "macaroni;250;g", "cheese;150;g", "milk;300;ml", "butter;30;g", "flour;2;tbsp");
    }

    private static void add(SQLiteDatabase db, String name, String steps, String... ingredients) {
        ContentValues rv = new ContentValues();
        rv.put(DatabaseHelper.COL_R_NAME, name);
        rv.put(DatabaseHelper.COL_R_STEPS, steps);
        long recipeId = db.insert(DatabaseHelper.TABLE_RECIPES, null, rv);
        for (String s : ingredients) {
            String[] p = s.split(";");
            ContentValues iv = new ContentValues();
            iv.put(DatabaseHelper.COL_RI_RECIPE_ID, recipeId);
            iv.put(DatabaseHelper.COL_RI_NAME, p[0]);
            iv.put(DatabaseHelper.COL_RI_QTY, Double.parseDouble(p[1]));
            iv.put(DatabaseHelper.COL_RI_UNIT, p[2]);
            db.insert(DatabaseHelper.TABLE_RECIPE_ING, null, iv);
        }
    }
}
