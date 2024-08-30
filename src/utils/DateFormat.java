package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parseDate(String date) throws  ParseException {
        return DATE_FORMATTER.parse(date);
    }
}
