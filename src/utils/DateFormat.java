package utils;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DateFormat {

    private static final DateTimeFormatter DATE_FORMATTER =  DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date,DATE_FORMATTER);
    }

}
