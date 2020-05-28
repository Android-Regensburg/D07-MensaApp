package de.ur.mi.android.mensaapp.mensa;

public class MenuItemBuilder {

    private static final String DEFAULT_TEXT_VALUE = "UNDEFINED";
    private static final float DEFAULT_CURRENCY_VALUE = 0.0f;

    private String name = DEFAULT_TEXT_VALUE;
    private String category = DEFAULT_TEXT_VALUE;
    private float costForStudentsInEuro = DEFAULT_CURRENCY_VALUE;
    private float costForEmployeesInEuro = DEFAULT_CURRENCY_VALUE;
    private float costForGuestsInEuro = DEFAULT_CURRENCY_VALUE;

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
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
        return new MenuItem(name,category,costForStudentsInEuro,costForEmployeesInEuro,costForGuestsInEuro);
    }
}
