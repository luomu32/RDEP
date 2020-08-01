package xyz.luomu32.rdep.common.web;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DateRange {

    public DateRange() {

    }

    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    private LocalDate start;

    private LocalDate end;

    public boolean isEmpty() {
        return null == this.start && null == this.end;
    }

    public boolean isSameDate() {
        if (null == this.start || null == this.end)
            return false;
        return this.start.isEqual(this.end);
    }

    public static DateRange empty() {
        return new DateRange();
    }
}
