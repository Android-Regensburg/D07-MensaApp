package de.ur.mi.android.mensaapp.mensa;

import java.util.ArrayList;

public interface MensaRequestListener {
    void onMenuDataAvailable(ArrayList<MenuItem> menu);

    void onNoMenuDataFound();

    void onInvalidWeekDay();
}