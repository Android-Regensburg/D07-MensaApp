package de.ur.mi.android.mensaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import de.ur.mi.android.mensaapp.mensa.MensaAPI;
import de.ur.mi.android.mensaapp.mensa.MensaRequestListener;
import de.ur.mi.android.mensaapp.mensa.MenuItem;
import de.ur.mi.android.mensaapp.ui.MenuAdapter;

public class MensaActivity extends AppCompatActivity {

    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        updateData();
    }

    private void initUI() {
        setContentView(R.layout.activity_mensa);
        RecyclerView menuList = findViewById(R.id.menu_item_list);
        menuAdapter = new MenuAdapter(this);
        menuList.setAdapter(menuAdapter);
    }

    private void updateData() {
        MensaAPI.getCurrentMenu(new MensaRequestListener() {
            @Override
            public void onMenuDataAvailable(final ArrayList<MenuItem> menu) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        menuAdapter.setMenuItems(menu);
                    }
                });
            }

            @Override
            public void onNoMenuDataFound() {

            }

            @Override
            public void onInvalidWeekDay() {

            }
        });
    }
}
