package de.ur.mi.android.mensaapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.ur.mi.android.mensaapp.mensa.MensaAPI;
import de.ur.mi.android.mensaapp.mensa.MensaRequestListener;
import de.ur.mi.android.mensaapp.mensa.MenuItem;
import de.ur.mi.android.mensaapp.ui.MenuAdapter;
import de.ur.mi.android.mensaapp.utils.DateUtils;

/**
 * Diese App stellt den Speiseplan der Mensa der Universität Regensburg für den heutigen Wochentag
 * dar. Sobal die Activity sichtbar wird (onResume) wird eine Anfrage an den API-Server gestellt.
 * Der so erhaltene Speiseplan wird in einem RecyclerView angezeigt.
 */
public class MensaActivity extends AppCompatActivity {

    private TextView todayLabel;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }

    private void initUI() {
        setContentView(R.layout.activity_mensa);
        todayLabel = findViewById(R.id.today_label);
        RecyclerView menuList = findViewById(R.id.menu_item_list);
        menuAdapter = new MenuAdapter(this);
        menuList.setAdapter(menuAdapter);
    }

    private void updateData() {
        // Anfragen der Speiseplandaten
        MensaAPI.getCurrentMenu(new MensaRequestListener() {
            // Abfangen der Ergebnisse der Anfrage
            @Override
            public void onMenuDataAvailable(final ArrayList<MenuItem> menu) {
                // Wechsel in den UI-Thread (die Anfrage in der MensaAPI-Klasse lief in einem
                // separaten Thread) um Probleme mit dem RecyclerView zu vermeiden.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Anzeigen des aktuellen Wochentags
                        todayLabel.setText(DateUtils.getCurrentWeekdayName());
                        // Aktualisieren der Daten im Adapter (der Adapter informiert selbständig
                        // den angeschlossenen RecyclerView).
                        menuAdapter.setMenuItems(menu);
                    }
                });
            }

            @Override
            public void onNoMenuDataFound() {

            }
        });
    }
}
