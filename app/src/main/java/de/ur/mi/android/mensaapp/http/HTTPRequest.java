package de.ur.mi.android.mensaapp.http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Diese Klasse stellt eine statische Methode bereit, mit der andere Komponenten der Anwendung
 * eine entfernte Ressource über HTTP anfragen können. Dafür werden die JAVA-Komponenten aus dem
 * Paket java.net verwendet. Die HTTP-Anfrage erfolgt in einem separaten Thread, das Ergebnis bzw.
 * das Fehlschlagen der Anfrage, wird über eine Listener-Schnittstelle nach Außen gegeben.
 **/
public class HTTPRequest {

    private static final String LINE_BREAK_DELIMITER = "\n";

    /**
     * Diese Methode stellt die einzige öffentliche Schnittstelle in die Klasse bereit. Die
     * übergebene URL bzw. die dadurch repräsentierte Ressource wird über die HTTP-Schnittstelle
     * angefragt. Das Ergebnis der Anfrage wird an den als Parameter übergebenen Listener zurück
     * gegeben.
     *
     * @param url      URL des Dokuments, das "heruntergeladen" werden soll
     * @param listener Listener mit Callback-Methoden, die nach erfolgreichem Download oder bei
     *                 Fehlern während der Verbindung aufgerufen werden sollen.
     */
    public static void get(URL url, HTTPRequestListener listener) {
        // Die eigentliche HTTP-Verbindung wird in einem separaten Runnable-Objekt aufgebaut
        HTTPRequestTask requestTask = new HTTPRequestTask(url, listener);
        // Das Runnable wird in einem separaten Thread ausgeführt, um die Netzwerkkommunikation,
        // wie vom Android-Framework verlangt, aus dem UI Thread auszulagern.
        Executors.newSingleThreadExecutor().submit(requestTask);
    }

    /**
     * Runnable für die Durchführung der HTTP-Anfrage
     */
    private static class HTTPRequestTask implements Runnable {

        // Ziel der Anfrage
        private URL url;
        // Rezipient der Ergebnisse der Anfrage
        private HTTPRequestListener listener;

        HTTPRequestTask(URL url, HTTPRequestListener listener) {
            this.url = url;
            this.listener = listener;
        }

        @Override
        public void run() {
            try {
                // Aufbau der HTTP-Verbindung zur Ressource, die über this.url spezifziert wurde
                HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
                // Erzeugen eines InputStreams, um die vom Server eingehenden Daten abzufangen
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                // Erstellen eines Readers, um den InputStream zu verarbeiten
                InputStreamReader inputReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                // Erstellen eines BufferedReaders um die Server-Antwort in Textform zu konvertieren
                BufferedReader bufferedReader = new BufferedReader((inputReader));
                // Zusammensetzten der Serverantwort aus den einzelnen Zeilen, die über den
                // BufferedReader aus dem Stream ausgelesen werden konnten
                String result = bufferedReader.lines().collect(Collectors.joining(LINE_BREAK_DELIMITER));
                // Wichtig: Wir schließen den Stream, wenn wir diesen vollständig verarbeitet haben
                inputStream.close();
                // Wichtig: Wir schließen die HTTP-Verbindung, sobald die vollständige Antwort des
                // Servers vorliegt
                connection.disconnect();
                // Die vollständige Serverantwort (als Text) wird an den wartenden Listener übergeben
                listener.onResult(result);
                // Alle Fehler während des Verbindungsversuchs werden hier abgefangen und vereinfacht an
                // den wartenden Listener übergeben.
            } catch (Exception e) {
                listener.onError();
            }
        }
    }
}
