package main.util;

import com.opencsv.bean.AbstractBeanField;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeConverter extends AbstractBeanField<LocalTime, String> {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    protected Object convert(String value) {
        return LocalTime.parse(value, TIME_FORMATTER);
    }

    @Override
    protected String convertToWrite(Object value) {
        return ((LocalTime) value).format(TIME_FORMATTER);
    }
}

