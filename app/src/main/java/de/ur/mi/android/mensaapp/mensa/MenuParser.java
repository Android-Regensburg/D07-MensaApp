package de.ur.mi.android.mensaapp.mensa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Diese Klasse übernimmt die Transformation des JSON-Texts in eine Liste von MenuItems. Die Klasse
 * kennt die zu erwartende Struktur des JSON-Texts und weiß wie MenuItems erzeugt werden müssen.
 * In den Methoden der Klasse werden die notwendigen Informationen schrittweise aus dem JSON-Text
 * extrahiert und, separat für jeden der aufgeführten Speiseplaneinträge, an einen MenuItemBuilder
 * übergeben. Die Liste der so erstellen MenuItems wird als Ergebnis an die aufrufende Komponenten
 * zurückgegeben.
 */
public class MenuParser {

    // Schlüssel (Eigenschaftsname) für den Zugriff auf die JSON-Inhalte
    private static final String NAME_KEY = "name";
    private static final String COST_KEY = "cost";
    private static final String COST_FOR_STUDENT_KEY = "students";
    private static final String COST_FOR_EMPLOYEES_KEY = "employees";
    private static final String COST_FOR_GUESTS_KEY = "guests";

    /**
     * Wandelt den übergebenen JSON-String in eine Liste von MenuItems um
     *
     * @param json JSON-formatierte Antwort des API-Servers (z.B von https://regensburger-forscher.de/mensa-api/Friday/)
     * @return Liste der aus dem JSON-extrahierten und tranformierten MenuItem-Objekte
     */
    public static ArrayList<MenuItem> getMenuFromJSON(String json) {
        // Erstellen einer leeren Liste: Hier werden alle erstellen MenuItems gesammelt
        ArrayList<MenuItem> menu = new ArrayList<>();
        try {
            // Erzeugen des JSONArrays aus dem übergebenen String
            JSONArray jsonMenu = new JSONArray(json);
            // Wir iterieren über jeden Eintrag des JSONArrays ...
            for (int i = 0; i < jsonMenu.length(); i++) {
                // ... und verarbeiten jedes der gespeicherten Objekte einzeln
                JSONObject menuObject = jsonMenu.getJSONObject(i);
                // In einer separaten Methode werden die Einträge des Objekts zum Erstellen einer
                // MenuItem-Instanz verwendet ...
                MenuItem menuItem = getMenuItemFromJSONObject(menuObject);
                // ... die zur oben erstellen Liste hinzugefügt wird
                menu.add(menuItem);
            }
            // Etwaige Fehler bei der Verarbeitung des JSON-Texts werden hier gesammelt
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Die erzeugten MenuItems werde als Ergebnis der Methode zurückgegeben
        return menu;
    }

    /**
     * Erzeugt auf Basis des übergebenen JSONObjects ein MenuItem
     *
     * @param object JSONObject, das vom API-Server an uns zurück geschickt wurde
     * @return Eine Repräsentation der Inhalte des JSONObject in Form einer MenuItem-Instanz für die
     * weitere Verwerndung in unserer Anwendung.
     * @throws JSONException
     * @throws ParseException
     */
    private static MenuItem getMenuItemFromJSONObject(JSONObject object) throws JSONException, ParseException {
        // Erzeugen des Builder für die übersichtlichere Erzeugung der MenuItems
        MenuItemBuilder builder = new MenuItemBuilder();
        // Auslesen und Setzen des Namens des aktuellen Menü-Eintrags
        String itemName = object.getString(NAME_KEY);
        builder.setName(itemName);
        // Auslesen des JSONObjects, das die Kosten für den aktuellen Menü-Eintrag enthält
        JSONObject costsObject = object.getJSONObject(COST_KEY);
        // Auslesen und Setzten der Kosten des aktuellen Menü-Eintrags: Für Studierende
        String costForStudentsAsString = costsObject.getString(COST_FOR_STUDENT_KEY);
        float costForStudentsAsNumber = translateDecimalFormatFromString(costForStudentsAsString);
        builder.setCostForStudentsInEuro(costForStudentsAsNumber);
        // Auslesen und Setzten der Kosten des aktuellen Menü-Eintrags: Für Mitarbeitende
        String costForEmployeesAsString = costsObject.getString(COST_FOR_EMPLOYEES_KEY);
        float costForEmployeesAsNumber = translateDecimalFormatFromString(costForEmployeesAsString);
        builder.setCostForEmployeesInEuro(costForEmployeesAsNumber);
        // Auslesen und Setzten der Kosten des aktuellen Menü-Eintrags: Für Gäste
        String costForGuestsAsString = costsObject.getString(COST_FOR_GUESTS_KEY);
        float costForGuestsAsNumber = translateDecimalFormatFromString(costForGuestsAsString);
        builder.setCostForGuestsInEuro(costForGuestsAsNumber);
        return builder.getMenuItem();
    }

    /**
     * Die Methode wandelt die in Text-Form vorliegenden Preisangaben aus den Menü-Einträgen in
     * numerische Werte um.
     *
     * @param value Preisangabe in Textform
     * @return Preisangabe in numerischer Form
     * @throws ParseException
     */
    private static float translateDecimalFormatFromString(String value) throws ParseException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat format = new DecimalFormat("0.#");
        format.setDecimalFormatSymbols(symbols);
        return format.parse(value).floatValue();
    }
}
