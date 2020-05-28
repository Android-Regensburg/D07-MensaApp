package de.ur.mi.android.mensaapp.http;

/**
 * Interface für Listener-Komponenten, die auf eine Antwort der HTTPRequest-Klasse warten.
 */
public interface HTTPRequestListener {

    /**
     * Wird aufgerufen, wenn die Antwort des Servers vollständig vorliegt und lokal zusammengesetzt wurde
     *
     * @param result Die Antwort des Servers in Textform
     */
    void onResult(String result);

    /**
     * Wird aufgerufen, wenn beim Verbindungsaufbau oder während der Kommunikation mit dem Server ein
     * Fehler aufgetreten ist
     */
    void onError();

}
