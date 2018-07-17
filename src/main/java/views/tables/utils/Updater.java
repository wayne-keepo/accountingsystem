package views.tables.utils;

import domain.Balance;
import projectConstants.CustomConstants;

import java.time.Month;

public class Updater {

    public static void updateValueOfMonthColumn(Balance balance, String month, String parentName, Double newValue) {
        Month key = Searcher.searchEngMonthByRus(month);
        if (parentName == CustomConstants.INCOMING) {
            balance.getIncoming().put(key, newValue);
            System.out.println(balance.getIncoming().get(key));
        }
        if (parentName == CustomConstants.OUTCOMING) {
            balance.getOutcoming().put(key, newValue);
            System.out.println(balance.getOutcoming().get(key));
        }
    }
}
