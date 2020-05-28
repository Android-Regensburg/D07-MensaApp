package de.ur.mi.android.mensaapp.mensa;

import java.util.ArrayList;

/**
 * Interface für Listener-Komponenten, die auf eine Antwort der MensaAPI-Klasse warten
 */
public interface MensaRequestListener {
    /**
     * Wird aufgerufen, wenn die MensaAPI-Klasse Speiseplandaten für den aktuellen Wochentag vom
     * Server beziehen und in eine Liste von MenuItems transformieren konnte.
     *
     * @param menu Liste der Speisen, die es heute in der Mensa zu kaufen gibt
     */
    void onMenuDataAvailable(ArrayList<MenuItem> menu);

    /**
     * Wird aufgerufen wenn die MensaAPI-Klasse keine Daten für den aktuellen Wochentag beziehen konnte
     */
    void onNoMenuDataFound();
}