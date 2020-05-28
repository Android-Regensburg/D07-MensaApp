package de.ur.mi.android.mensaapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.ur.mi.android.mensaapp.R;
import de.ur.mi.android.mensaapp.mensa.MenuItem;

/**
 * Dieser Adapter verwaltet eine Liste von MenuItems und stellt diese für die Darstellung in einem
 * RecyclerView bereit (Vgl. auch z.B. PrimeChecker-Demo). Für die Darstellung der einzelnen Elemente
 * wird das Layout aus res/layout/menu_item_entry.xml verwendet.
 */
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
        this.items = items;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_entry, parent, false);
        MenuItemViewHolder vh = new MenuItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        MenuItem item = items.get(position);
        TextView name = holder.entryView.findViewById(R.id.menu_item_name);
        TextView costs = holder.entryView.findViewById(R.id.menu_item_costs);
        name.setText(item.name);
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
        return items.size();
    }
}
