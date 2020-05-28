package de.ur.mi.android.mensaapp.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.ur.mi.android.mensaapp.R;
import de.ur.mi.android.mensaapp.mensa.MenuItem;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuItemViewHolder> {

    private ArrayList<MenuItem> items;
    private Context context;

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        public View entryView;

        public MenuItemViewHolder(View v) {
            super(v);
            entryView = v;
        }
    }

    public MenuAdapter(Context context) {
        this.items = new ArrayList<>();
        this.context = context;
    }

    public void setMenuItems(ArrayList<MenuItem> items) {
        Log.d("MENSA_APP", "Setting new items (size => " +items.size() + ")");
        this.items = items;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MENSA_APP", "in: onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_entry, parent, false);
        MenuItemViewHolder vh = new MenuItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        Log.d("MENSA_APP", "in: onBindViewHolder");
        MenuItem item = items.get(position);
        TextView name = holder.entryView.findViewById(R.id.menu_item_name);
        TextView category = holder.entryView.findViewById(R.id.menu_item_category);
        TextView costs = holder.entryView.findViewById(R.id.menu_item_costs);

        name.setText(item.name);
        category.setText(item.category);
        StringBuilder costStringBuilder = new StringBuilder();
        costStringBuilder.append(item.costForStudentsInEuro);
        costStringBuilder.append(" € (Studierende), ");
        costStringBuilder.append(item.costForEmployeesInEuro);
        costStringBuilder.append(" € (Mitarbeitende), ");
        costStringBuilder.append(item.costForGuestsInEuro);
        costStringBuilder.append(" € (Gäste)");
        costs.setText(costStringBuilder.toString());
    }

    @Override
    public int getItemCount() {
        Log.d("MENSA_APP", "in: getItemCount");
        return items.size();
    }
}
