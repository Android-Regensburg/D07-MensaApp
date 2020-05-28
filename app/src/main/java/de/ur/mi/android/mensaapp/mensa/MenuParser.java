package de.ur.mi.android.mensaapp.mensa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;

public class MenuParser {

    private static final String NAME_KEY = "name";
    private static final String CATEGORY_KEY = "category";
    private static final String COST_KEY = "cost";
    private static final String COST_FOR_STUDENT_KEY = "students";
    private static final String COST_FOR_EMPLOYEES_KEY = "employees";
    private static final String COST_FOR_GUESTS_KEY = "guests";

    public static ArrayList<MenuItem> getMenuFromJSON(String json) {
        ArrayList<MenuItem> menu = new ArrayList<>();
        try {
            JSONArray jsonMenu = new JSONArray(json);
            for(int i = 0; i < jsonMenu.length(); i++) {
                JSONObject menuObject = jsonMenu.getJSONObject(i);
                MenuItem menuItem = getMenuItemFromJSONObject(menuObject);
                menu.add(menuItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menu;
    }

    private static MenuItem getMenuItemFromJSONObject(JSONObject object) throws JSONException, ParseException {
        MenuItemBuilder builder = new MenuItemBuilder();
        builder.setName(object.getString(NAME_KEY));
        builder.setCategory(object.getString(CATEGORY_KEY));
        JSONObject costsObject = object.getJSONObject(COST_KEY);
        builder.setCostForStudentsInEuro(translateDecimalFormatFromString(costsObject.getString(COST_FOR_STUDENT_KEY)));
        builder.setCostForEmployeesInEuro(translateDecimalFormatFromString(costsObject.getString(COST_FOR_EMPLOYEES_KEY)));
        builder.setCostForGuestsInEuro(translateDecimalFormatFromString(costsObject.getString(COST_FOR_GUESTS_KEY)));
        return builder.getMenuItem();
    }

    private static float translateDecimalFormatFromString(String value) throws ParseException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat format = new DecimalFormat("0.#");
        format.setDecimalFormatSymbols(symbols);
        return format.parse(value).floatValue();
    }
}
