package views.tables.utils;

import domain.Balance;
import domain.DetailElectrod;
import projectConstants.CustomConstants;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Searcher {
    //for balance
    public static Month search(String month) {
        Month key = null;
        switch (month) {
            case "Январь":
                key = Month.JANUARY;
                break;
            case "Февраль":
                key = Month.FEBRUARY;
                break;
            case "Март":
                key = Month.MARCH;
                break;
            case "Апрель":
                key = Month.APRIL;
                break;
            case "Май":
                key = Month.MAY;
                break;
            case "Июнь":
                key = Month.JUNE;
                break;
            case "Июль":
                key = Month.JULY;
                break;
            case "Август":
                key = Month.AUGUST;
                break;
            case "Сентябрь":
                key = Month.SEPTEMBER;
                break;
            case "Октябрь":
                key = Month.OCTOBER;
                break;
            case "Ноябрь":
                key = Month.NOVEMBER;
                break;
            case "Декабрь":
                key = Month.DECEMBER;
                break;
        }
        return key;
    }

    public static Integer findValueByMonth(Balance balance, String month, String eventName) {
        Month key = search(month);
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
