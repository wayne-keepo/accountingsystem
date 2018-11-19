package utils.converter;

import javafx.util.StringConverter;

public class IntegerConverter extends StringConverter<Integer> {
    @Override
    public String toString(Integer object) {
        return object.toString();
    }

    @Override
    public Integer fromString(String string) {
        return Integer.valueOf(string);
    }
}
