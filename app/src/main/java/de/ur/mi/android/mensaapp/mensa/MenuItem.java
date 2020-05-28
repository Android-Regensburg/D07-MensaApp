package de.ur.mi.android.mensaapp.mensa;

/**
 * Java-Repräsentation eines einzelnen Eintrags im Speiseplan. Instanzen dieser Klasse werden auf
 * Basis des JSON-Arrays erzeugt, das vom API-Server heruntergeladen wird. Die so erstellten Objekte
 * dienen anschließend als Datengrundlage für den RecyclerView.
 */
public class MenuItem {

    public final String name;
    public final float costForStudentsInEuro;
    public final float costForEmployeesInEuro;
    public final float costForGuestsInEuro;

    public MenuItem(String name, float costForStudentsInEuro, float costForEmployeesInEuro, float costForGuestsInEuro) {
        this.name = name;
        this.costForStudentsInEuro = costForStudentsInEuro;
        this.costForEmployeesInEuro = costForEmployeesInEuro;
        this.costForGuestsInEuro = costForGuestsInEuro;
    }

}
