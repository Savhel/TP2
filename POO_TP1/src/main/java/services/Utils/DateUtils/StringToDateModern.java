package services.Utils.DateUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringToDateModern {
    public static LocalDate getDate(String dateString) {
    //        String dateString = "2023-12-25";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return (LocalDate.parse(dateString, formatter)); // Affiche: 2023-12-25
        } catch (DateTimeParseException e) {
            System.err.println("Format de date invalide !");
        }
        return null;
    }
}
