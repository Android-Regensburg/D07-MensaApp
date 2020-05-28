package de.ur.mi.android.mensaapp.mensa;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import de.ur.mi.android.mensaapp.http.HTTPRequest;
import de.ur.mi.android.mensaapp.http.HTTPRequestListener;

public class MensaAPI {

    private static final String API_BASE_URL = "https://regensburger-forscher.de/mensa-api/{{DAY}}";
    private static final String[] VALID_DAYS = {"Monday","Tuesday","Wednesday","Thursday","Friday"};

    public static void getCurrentMenu(MensaRequestListener listener) {
        String currentWeekday = MensaAPI.getCurrentWeekdayName();
        if(Arrays.asList(VALID_DAYS).contains(currentWeekday)) {
            MensaAPI.requestDataForWeekday(currentWeekday, listener);
        } else {
            listener.onInvalidWeekDay();
        }
    }

    private static void requestDataForWeekday(String weekday, final MensaRequestListener listener) {
        String urlString = API_BASE_URL.replace("{{DAY}}", weekday);
        try {
            URL url = new URL(urlString);
            HTTPRequest.get(url, new HTTPRequestListener() {
                @Override
                public void onResult(String result) {
                    ArrayList<MenuItem> menu =  MenuParser.getMenuFromJSON(result);
                    listener.onMenuDataAvailable(menu);
                }

                @Override
                public void onError() {
                    listener.onNoMenuDataFound();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentWeekdayName() {
        LocalDate date = LocalDate.now();
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
}
