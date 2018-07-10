package views.tables.utils;

import domain.Balance;
import domain.DetailElectrod;
import projectConstants.CustomConstants;

import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Searcher {
    private static final Map<RussianMonths, Month> months = createMap();

    private static LinkedHashMap<RussianMonths, Month> createMap() {
        LinkedHashMap<RussianMonths, Month> inimonths = new LinkedHashMap<RussianMonths, Month>();
        inimonths.put(RussianMonths.Январь, Month.JANUARY);
        inimonths.put(RussianMonths.Февраль, Month.FEBRUARY);
        inimonths.put(RussianMonths.Март, Month.MARCH);
        inimonths.put(RussianMonths.Апрель, Month.APRIL);
        inimonths.put(RussianMonths.Май, Month.MAY);
        inimonths.put(RussianMonths.Июнь, Month.JUNE);
        inimonths.put(RussianMonths.Июль, Month.JULY);
        inimonths.put(RussianMonths.Август, Month.AUGUST);
        inimonths.put(RussianMonths.Сентябрь, Month.SEPTEMBER);
        inimonths.put(RussianMonths.Октябрь, Month.OCTOBER);
        inimonths.put(RussianMonths.Ноябрь, Month.NOVEMBER);
        inimonths.put(RussianMonths.Декабрь, Month.DECEMBER);
        return inimonths;
    }

    //for balance
    //TODO: переделать в вид Map<RuMonth,Month> и такой же для обратной конвертации (+)
    public static Month searchMonthByRus(String month) {
        RussianMonths ru = RussianMonths.valueOf(month);
        return months.get(ru);
//        Month key = null;
//        switch (month) {
//            case "Январь":
//                key = Month.JANUARY;
//                break;
//            case "Февраль":
//                key = Month.FEBRUARY;
//                break;
//            case "Март":
//                key = Month.MARCH;
//                break;
//            case "Апрель":
//                key = Month.APRIL;
//                break;
//            case "Май":
//                key = Month.MAY;
//                break;
//            case "Июнь":
//                key = Month.JUNE;
//                break;
//            case "Июль":
//                key = Month.JULY;
//                break;
//            case "Август":
//                key = Month.AUGUST;
//                break;
//            case "Сентябрь":
//                key = Month.SEPTEMBER;
//                break;
//            case "Октябрь":
//                key = Month.OCTOBER;
//                break;
//            case "Ноябрь":
//                key = Month.NOVEMBER;
//                break;
//            case "Декабрь":
//                key = Month.DECEMBER;
//                break;
//        }
//        return key;
    }

//    public static Month

    public static Integer findValueByMonth(Balance balance, String month, String eventName) {
        Month key = searchMonthByRus(month);
        if (eventName == CustomConstants.INCOMING)
            return balance.getReceipt().get(key);
        if (eventName == CustomConstants.OUTCOMING)
            return balance.getConsumption().get(key);
        return null;
    }

    public static List<DetailElectrod> findProductInfo(List<DetailElectrod> items, String type) {
        List<DetailElectrod> electrods = new ArrayList<>();
        for (DetailElectrod electrod : items) {
            if (electrod.getElectrod().getType() == type)
                electrods.add(electrod);
        }
        return electrods;
    }
}
