package xyz.luomu32.common.test;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import xyz.luomu32.rdep.common.web.DateRange;

import java.time.LocalDate;

public class DateRangeTest {

    @Test
    public void testIsSameDayMissStartAndEnd() {
        DateRange dateRange = new DateRange();
        Assertions.assertThat(dateRange.isSameDate()).isFalse();
    }

    @Test
    public void testIsSameDayOnMisStart() {
        DateRange dateRange = new DateRange();
        dateRange.setEnd(LocalDate.now());
        Assertions.assertThat(dateRange.isSameDate()).isFalse();
    }

    @Test
    public void testIsSameDayOnMisEnd() {
        DateRange dateRange = new DateRange();
        dateRange.setStart(LocalDate.now());
        Assertions.assertThat(dateRange.isSameDate()).isFalse();
    }

    @Test
    public void testIsSameDayDifference() {
        DateRange dateRange = new DateRange();
        dateRange.setStart(LocalDate.now());
        dateRange.setEnd(LocalDate.now().plusDays(2));
        Assertions.assertThat(dateRange.isSameDate()).isFalse();
    }

    @Test
    public void testIsSameDay() {
        DateRange dateRange = new DateRange();
        dateRange.setStart(LocalDate.now());
        dateRange.setEnd(LocalDate.now());
        Assertions.assertThat(dateRange.isSameDate()).isTrue();
    }
}
