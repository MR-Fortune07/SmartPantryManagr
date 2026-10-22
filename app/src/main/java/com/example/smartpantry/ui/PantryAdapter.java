package com.example.smartpantry.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantry.R;
import com.example.smartpantry.model.PantryItem;
import com.example.smartpantry.util.AppDates;
import com.example.smartpantry.util.UnitUtils;

import java.util.ArrayList;
import java.util.List;

//Custom adapter binding PantryItem rows (from SQLite) to item_pantry.xml.
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.VH> {

    public interface Listener {
        void onEdit(PantryItem item);
        void onDelete(PantryItem item);
    }

    private final List<PantryItem> items = new ArrayList<>();
    private final Listener listener;

    public PantryAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setItems(List<PantryItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantry, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        final PantryItem item = items.get(position);
        h.name.setText(item.getName());
        h.qty.setText(UnitUtils.format(item.getQuantity()) + " " + item.getUnit());

        Integer days = AppDates.daysUntil(item.getExpiry());
        int colour = R.color.grey;
        String text;
        if (days == null) {
            text = h.itemView.getContext().getString(R.string.no_expiry);
        } else if (days < 0) {
            text = "Expired " + (-days) + (days == -1 ? " day ago" : " days ago");
            colour = R.color.expired;
        } else if (days == 0) {
            text = "Expires today";
            colour = R.color.soon;
        } else if (days <= 3) {
            text = "Expires in " + days + (days == 1 ? " day" : " days");
            colour = R.color.soon;
        } else {
            text = "Expires " + item.getExpiry();
        }
        h.expiry.setText(text);
        h.expiry.setTextColor(ContextCompat.getColor(h.itemView.getContext(), colour));

        h.itemView.setOnClickListener(v -> listener.onEdit(item));
        h.btnEdit.setOnClickListener(v -> listener.onEdit(item));
        h.btnDelete.setOnClickListener(v -> listener.onDelete(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView name, qty, expiry;
        final View btnEdit, btnDelete;

        VH(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.tvItemName);
            qty = v.findViewById(R.id.tvItemQty);
            expiry = v.findViewById(R.id.tvItemExpiry);
            btnEdit = v.findViewById(R.id.btnEdit);
            btnDelete = v.findViewById(R.id.btnDelete);
        }
    }
}
