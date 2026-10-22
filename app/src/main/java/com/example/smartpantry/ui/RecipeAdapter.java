package com.example.smartpantry.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantry.R;
import com.example.smartpantry.model.Recipe;

import java.util.ArrayList;
import java.util.List;

// Custom adapter for the Suggested Recipes list.
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.VH> {

    public interface Listener {
        void onRecipeClicked(Recipe recipe);
    }

    private final List<Recipe> recipes = new ArrayList<>();
    private final Listener listener;

    public RecipeAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setRecipes(List<Recipe> newRecipes) {
        recipes.clear();
        recipes.addAll(newRecipes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        final Recipe r = recipes.get(position);
        h.name.setText(r.getName());
        int n = r.getIngredients().size();
        h.sub.setText(n + (n == 1 ? " ingredient" : " ingredients"));
        if (r.getNote() != null) {
            h.note.setVisibility(View.VISIBLE);
            h.note.setText(r.getNote());
        } else {
            h.note.setVisibility(View.GONE);
        }
        h.itemView.setOnClickListener(v -> listener.onRecipeClicked(r));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView name, sub, note;

        VH(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.tvRecipeName);
            sub = v.findViewById(R.id.tvRecipeSub);
            note = v.findViewById(R.id.tvRecipeNote);
        }
    }
}
