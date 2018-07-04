package views.tables.utils;

import domain.Balance;
import projectConstants.CustomConstants;

import java.time.Month;

public class Updater {

    public static void updateValueOfMonthColumn(Balance balance, String month, String parentName, Integer newValue) {
        Month key = Searcher.search(month);
        if (parentName == CustomConstants.INCOMING) {
            balance.getReceipt().put(key, newValue);
            System.out.println(balance.getReceipt().get(key));
        }
        if (parentName == CustomConstants.OUTCOMING) {
            balance.getConsumption().put(key, newValue);
            System.out.println(balance.getConsumption().get(key));
        }
    }
}
