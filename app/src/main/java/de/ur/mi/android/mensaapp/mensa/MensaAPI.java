package de.ur.mi.android.mensaapp.mensa;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import de.ur.mi.android.mensaapp.http.HTTPRequest;
import de.ur.mi.android.mensaapp.http.HTTPRequestListener;
import de.ur.mi.android.mensaapp.utils.DateUtils;

/**
 * Diese Klasse stellt die Schnittstelle der Anwendung zum API-Server dar. Die Klasse kommuniziert
 * über die HTTP-Schnittstelle (z.B. https://regensburger-forscher.de/mensa-api/Friday) mit der
 * API und stellt die so erfragten Speiseplandaten für andere Komponenten der Anwendung bereit. Die
 * Anfragen erfolgen asynchron. Interessierte Komponenten werden über eine Listener-Schnittstelle
 * (MensaRequestListener) über den Ausgang der Anfrage informiert.
 */
public class MensaAPI {

    // Aufbau der URLs für die Anfragen an der Server
    private static final String API_BASE_URL = "https://regensburger-forscher.de/mensa-api/{{DAY}}";
    // Liste der Wochentage, für die Speiseplandaten angefragt werden können (Am WE hat die Mensa zu)
    private static final String[] VALID_DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    /**
     * Öffentliche Methode, um die Speiseplandaten für den heutigen Wochentag vom API-Server zu beziehen
     *
     * @param listener Komponente, die über die Ergebnisse der Anfrage informiert werden soll
     */
    public static void getCurrentMenu(MensaRequestListener listener) {
        // Stellt den heutigen Wochentag fest, um die korrekte Anfrage-URL erstellen zu können
        String currentWeekday = DateUtils.getCurrentWeekdayName(Locale.ENGLISH);
        // Wenn der aktuelle Wochentag ein Werktag ist, wird die Anfrage an den API-Server gestartet
        if (Arrays.asList(VALID_DAYS).contains(currentWeekday)) {
            MensaAPI.requestDataForWeekday(currentWeekday, listener);
        } else {
            listener.onNoMenuDataFound();
        }
    }

    /**
     * In dieser Methode wird das Menü für den angegebenen Tag vom API-Server heruntergeladen und in
     * einer Liste von MenuItem-Objekte transformiert. Das Ergebnis, oder etwaige Fehler bei der
     * Anfrage oder Verarbeitung, werden an den wartenden Listener kommuniziert.
     *
     * @param weekday  Wochentag, für den die Speiseplandaten erstellt werden sollen
     * @param listener Listener, der über das Ergebnis informiert werden soll
     */
    private static void requestDataForWeekday(String weekday, final MensaRequestListener listener) {
        // Zusammensetzen der korrekten URL für die Anfrage
        String urlString = API_BASE_URL.replace("{{DAY}}", weekday);
        try {
            URL url = new URL(urlString);
            // Absenden der HTTP-Anfrage: Der hier inline erstellte Listener wartete auf die Antwort
            // der HTTPRequest-Klasse.
            HTTPRequest.get(url, new HTTPRequestListener() {
                @Override
                public void onResult(String result) {
                    // Wenn eine Antwort des Servers eingeht, wird versucht, das erhaltene JSON-Format
                    // in eine Liste von MenuItems zu überführen, die dann an den wartenden Listener
                    // zurückgesendet wird.
                    ArrayList<MenuItem> menu = MenuParser.getMenuFromJSON(result);
                    listener.onMenuDataAvailable(menu);
                }

                @Override
                public void onError() {
                    // Tritt bei der Kommunikation mit dem Server ein Fehler auf, wird der wartenden
                    // listener darüber informiert, dass keine Speisplandaten bereitgestellt werden
                    // können.
                    listener.onNoMenuDataFound();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
