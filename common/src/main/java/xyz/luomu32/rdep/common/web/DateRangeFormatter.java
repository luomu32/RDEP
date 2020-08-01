package xyz.luomu32.rdep.common.web;

import org.springframework.format.Formatter;
import org.springframework.format.datetime.DateFormatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateRangeFormatter implements Formatter<DateRange> {

    private static final String SEPARATION_CHART = "@";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public DateRange parse(String text, Locale locale) throws ParseException {
        if (null == text || text.length() == 0)
            return DateRange.empty();
        if (!text.contains(SEPARATION_CHART))
            throw new IllegalArgumentException("missing '@' to separation date");
        if (text.equals(SEPARATION_CHART))
            return DateRange.empty();
        String[] dates = text.split(SEPARATION_CHART);
        if (dates.length > 2)
            throw new IllegalArgumentException("date range not support more than two date");
        DateRange dateRange = new DateRange();

        if (dates[0].length() != 0)
            dateRange.setStart(parseDate(dates[0]));
        if (dates.length == 2)
            dateRange.setEnd(parseDate(dates[1]));

        return dateRange;
    }

    private LocalDate parseDate(String str) throws ParseException {
        try {
            return LocalDate.parse(str, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException(str, 0);
        }
    }

    @Override
    public String print(DateRange object, Locale locale) {
        return object.getStart().format(FORMATTER) + SEPARATION_CHART + object.getEnd().format(FORMATTER);
    }
}
