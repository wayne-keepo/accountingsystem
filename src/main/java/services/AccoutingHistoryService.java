package services;

import databaselogic.controllers.DBAccountingHistoryController;
import domain.Day;
import entities.AccoutingHistory;
import entities.Detail;
import views.tables.utils.RussianMonths;
import views.tables.utils.Searcher;

import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AccoutingHistoryService {
    private static final DBAccountingHistoryController controller = new DBAccountingHistoryController();

    public static Map<RussianMonths, List<AccoutingHistory>> historyToMapForAccoutingWindow(List<AccoutingHistory> histories) {
        Map<RussianMonths, List<AccoutingHistory>> map = new HashMap<>();
        Month[] months = Month.values();
        for (int i = 0; i < months.length; i++) {
            Month curMonth = months[i];
            List<AccoutingHistory> acc = histories.stream()
                    .filter(history -> history.getMonth() == curMonth)
                    .collect(Collectors.toList());
            if (!acc.isEmpty())
                map.put(
                        Searcher.searchRuMonthByEng(curMonth),
                        acc
                );
        }
        return map;
    }

    public static void buildSqlForBatchUpdAccHist(Map<RussianMonths, List<AccoutingHistory>> histories) {
        String days = "";

        List<String> sqlForUpdate = new ArrayList<>();

        for (Map.Entry<RussianMonths, List<AccoutingHistory>> maps : histories.entrySet()) {

            for (AccoutingHistory history : maps.getValue()) {

                for (Day day : history.getDays()) {
                    if (!(day.getDayNumber() == 31))
                        days += String.format(" d%d = %d,", day.getDayNumber(), day.getCount());
                    else
                        days += String.format(" d%d = %d", day.getDayNumber(), day.getCount());
                }
                String batchUpdate = String.format("UPDATE AccountingHistory SET %s WHERE id = %d and year = %d and month = %d and acc = %d",
                        days, history.getId(), history.getYear().getValue(), history.getMonth().getValue(), history.getAcc());
                sqlForUpdate.add(batchUpdate);
                days = "";
            }
        }
        batchUpdate(sqlForUpdate);
    }
    // use when added new balance
    public static void buildSqlForBatchInsertAccHist(Detail detail) {
        List<String> sql = new ArrayList<>();
        for (Month current : Month.values()) {
            String inInsert = String.format("\nINSERT INTO AccountingHistory(idDetail,month,acc) VALUES(%d,%d,%d)", detail.getId(), current.getValue(), 1);
            String outInsert = String.format("\nINSERT INTO AccountingHistory(idDetail,month,acc) VALUES(%d,%d,%d)", detail.getId(), current.getValue(), 0);
            sql.add(inInsert);
            sql.add(outInsert);
        }
        controller.batchInsert((String[]) sql.toArray());
    }

    private static void batchUpdate(List<String> upd) {
        controller.batchUpdate((String[]) upd.toArray());
    }
}
