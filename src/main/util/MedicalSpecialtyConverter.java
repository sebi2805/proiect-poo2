package main.util;

import com.opencsv.bean.AbstractBeanField;
import main.enums.MedicalSpecialty;

public class MedicalSpecialtyConverter extends AbstractBeanField<MedicalSpecialty, String> {
    @Override
    protected Object convert(String value) {
        return MedicalSpecialty.valueOf(value);
    }

    @Override
    protected String convertToWrite(Object value) {
        return ((MedicalSpecialty) value).name();
    }
}
