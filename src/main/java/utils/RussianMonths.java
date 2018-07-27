package utils;

import java.time.DateTimeException;

public enum RussianMonths   {
    Январь,Февраль,Март,Апрель,Май,Июнь,Июль,Август,Сентябрь,Октябрь,Ноябрь,Декабрь;

    private static final RussianMonths[] ruMonths = RussianMonths.values();

    public static RussianMonths of(int month) {
        if (month < 1 || month > 12) {
            throw new DateTimeException("Invalid value for MonthOfYear: " + month);
        }
        return ruMonths[month - 1];
    }

    public int getValue(){return ordinal() + 1;}


}
