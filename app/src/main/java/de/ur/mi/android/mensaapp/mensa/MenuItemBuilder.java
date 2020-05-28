package de.ur.mi.android.mensaapp.mensa;


/**
 * Hilfsklasse für die Erstellung von MenuItems
 * Java-Klassen mit vielen Parametern haben häufig komplexe Konstrukturen. Builder-Klassen helfen beim
 * Erstellen von Instanzen dieser Klassen, in dem Sie den Erstellungsprozess auf einzelne Schritte
 * (Methoden) aufteilen und so eine übersichtlichere Gestalung des Konstruktionsvorgangs erlauben.
 * Zusätzlich kann auch eine Fehlerkontrolle der Daten erfolgen, bevor versucht wird, das entsprechende
 * Objekte zu erstellen. Möglicherweise ist Ihnen dieses Muster bereits in Form der StringBuilder-Klasse
 * bekannt. Weitere Infos zu diesem Muster: https://en.wikipedia.org/wiki/Builder_pattern
 */
public class MenuItemBuilder {

    private static final String DEFAULT_TEXT_VALUE = "UNDEFINED";
    private static final float DEFAULT_CURRENCY_VALUE = 0.0f;

    private String name = DEFAULT_TEXT_VALUE;
    private float costForStudentsInEuro = DEFAULT_CURRENCY_VALUE;
    private float costForEmployeesInEuro = DEFAULT_CURRENCY_VALUE;
    private float costForGuestsInEuro = DEFAULT_CURRENCY_VALUE;

    public void setName(String name) {
        this.name = name;
    }

    public void setCostForStudentsInEuro(float costForStudentsInEuro) {
        this.costForStudentsInEuro = costForStudentsInEuro;
    }

    public void setCostForEmployeesInEuro(float costForEmployeesInEuro) {
        this.costForEmployeesInEuro = costForEmployeesInEuro;
    }

    public void setCostForGuestsInEuro(float costForGuestsInEuro) {
        this.costForGuestsInEuro = costForGuestsInEuro;
    }

    public MenuItem getMenuItem() {
        return new MenuItem(name, costForStudentsInEuro, costForEmployeesInEuro, costForGuestsInEuro);
    }
}
