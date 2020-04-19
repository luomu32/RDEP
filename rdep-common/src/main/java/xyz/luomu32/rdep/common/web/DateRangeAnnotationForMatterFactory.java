package xyz.luomu32.rdep.common.web;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.Set;

@Deprecated
public class DateRangeAnnotationForMatterFactory implements AnnotationFormatterFactory<DateRangeFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return null;
    }

    @Override
    public Printer<?> getPrinter(DateRangeFormat annotation, Class<?> fieldType) {
        return null;
    }

    @Override
    public Parser<?> getParser(DateRangeFormat annotation, Class<?> fieldType) {
        return null;
    }
}
