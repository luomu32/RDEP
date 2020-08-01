package xyz.luomu32.common.test;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import xyz.luomu32.rdep.common.web.DateRange;
import xyz.luomu32.rdep.common.web.DateRangeFormatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.stream.Stream;

public class DateRangeFormatterTest {

    DateRangeFormatter formatter = new DateRangeFormatter();

    @Test
    public void testEmpty() throws ParseException {
        DateRange dateRange = formatter.parse("@", Locale.CANADA);
        Assertions.assertThat(dateRange.isEmpty()).isTrue();
    }

    @Test
    public void testOnlyStart() throws ParseException {
        DateRange dateRange = formatter.parse("2019-11-11@", Locale.CANADA);
        Assertions.assertThat(dateRange.getStart()).isNotNull().isEqualTo(LocalDate.of(2019, 11, 11));
        Assertions.assertThat(dateRange.getEnd()).isNull();
    }

    @Test
    public void testOnlyEnd() throws ParseException {
        DateRange dateRange = formatter.parse("@2019-10-12", Locale.CANADA);
        Assertions.assertThat(dateRange.getStart()).isNull();
        Assertions.assertThat(dateRange.getEnd()).isNotNull().isEqualTo(LocalDate.of(2019, 10, 12));
    }

    @Test
    public void testFull() throws ParseException {
        DateRange dateRange = formatter.parse("2018-03-04@2019-10-12", Locale.CANADA);
        Assertions.assertThat(dateRange.getStart()).isNotNull().isEqualTo(LocalDate.of(2018, 3, 4));
        Assertions.assertThat(dateRange.getEnd()).isNotNull().isEqualTo(LocalDate.of(2019, 10, 12));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoreThenTwo() throws ParseException {
        DateRange dateRange = formatter.parse("2018-03-04@2019-10-12@2020-01-02", Locale.CANADA);
    }

    @Test(expected = ParseException.class)
    public void testWrongDateFormat() throws ParseException {
        DateRange dateRange = formatter.parse("2018-wer@ewr", Locale.CANADA);

    }
}
