package de.ur.mi.android.mensaapp.utils;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateUtils {

    // Gibt den aktuellen Wochentag in Text-Form  zurück (Systemsprache wird verwendet)
    public static String getCurrentWeekdayName() {
        return getCurrentWeekdayName(Locale.getDefault());
    }

    // Gibt den aktuellen Wochentag in Text-Form  zurück (Die Sprache kann über locale beeinflusst werden)
    public static String getCurrentWeekdayName(Locale locale) {
        LocalDate date = LocalDate.now();
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, locale);
    }
}
