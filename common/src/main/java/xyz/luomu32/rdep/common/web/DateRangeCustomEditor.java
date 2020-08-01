package xyz.luomu32.rdep.common.web;

import java.beans.PropertyEditorSupport;

public class DateRangeCustomEditor extends PropertyEditorSupport {
    @Override
    public void setValue(Object value) {
        super.setValue(value);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        System.out.println(text);
        super.setAsText(text);
    }
}
