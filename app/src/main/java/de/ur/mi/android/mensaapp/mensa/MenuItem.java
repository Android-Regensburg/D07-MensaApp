package de.ur.mi.android.mensaapp.mensa;

public class MenuItem {

    public final String name;
    public final String category;
    public final float costForStudentsInEuro;
    public final float costForEmployeesInEuro;
    public final float costForGuestsInEuro;

    public MenuItem(String name, String category, float costForStudentsInEuro, float costForEmployeesInEuro, float costForGuestsInEuro) {
        this.name = name;
        this.category = category;
        this.costForStudentsInEuro = costForStudentsInEuro;
        this.costForEmployeesInEuro = costForEmployeesInEuro;
        this.costForGuestsInEuro = costForGuestsInEuro;
    }

}
