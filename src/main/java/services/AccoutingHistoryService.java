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

    public static double[] calculate(List<AccoutingHistory> histories){
        System.out.println("Run calculate sum of month ");
        double incSum = 0.0;
        double outSum = 0.0;
        for (AccoutingHistory history: histories){
            if (history.getAcc()==1){
                for (Day day: history.getDays()){
                    incSum+=day.getCount();
                }
            } else {
                for (Day day: history.getDays()){
                    outSum+=day.getCount();
                }
            }
        }
        System.out.println(String.format("End calculate sum of month. Sum: Inc - %f | Out - %f ",incSum,outSum));
        return new double[]{incSum,outSum};
    }

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
        StringBuilder days = new StringBuilder();

        List<String> sqlForUpdate = new ArrayList<>();

        for (Map.Entry<RussianMonths, List<AccoutingHistory>> maps : histories.entrySet()) {

            for (AccoutingHistory history : maps.getValue()) {
//TODO: change to StringBuilder (faster and less memory-intensive)
                for (Day day : history.getDays()) {
                    if (!(day.getDayNumber() == 31))
                        days.append(String.format(" d%d = %s,", day.getDayNumber(), String.valueOf(day.getCount())));
                    else
                        days.append(String.format(" d%d = %s", day.getDayNumber(), String.valueOf(day.getCount())));
                }
                String batchUpdate = String.format("UPDATE AccountingHistory SET %s WHERE id = %d and year = %d and month = %d and acc = %d",
                        days.toString(), history.getId(), history.getYear().getValue(), history.getMonth().getValue(), history.getAcc());
                sqlForUpdate.add(batchUpdate);
                days.setLength(0);
            }
        }
        batchUpdate(sqlForUpdate);
    }

    // use when added new balance
    public static void buildSqlForBatchInsertAccHist(Detail detail) {
        List<String> sql = new ArrayList<>();
        for (Month current : Month.values()) {
            String inInsert = String.format("INSERT INTO AccountingHistory(idDetail,month,acc) VALUES(%d,%d,%d)", detail.getId(), current.getValue(), 1);
            String outInsert = String.format("INSERT INTO AccountingHistory(idDetail,month,acc) VALUES(%d,%d,%d)", detail.getId(), current.getValue(), 0);
            sql.add(inInsert);
            sql.add(outInsert);
        }
        String[] tmp = sql.toArray(new String[sql.size()]);
        controller.batchInsert(tmp);
    }

    private static void batchUpdate(List<String> upd) {
        String[] tmp = upd.toArray(new String[upd.size()]);
        System.out.println(Arrays.toString(tmp));
        controller.batchUpdate(tmp);
    }

    public static List<AccoutingHistory> getAll() {
        return controller.getAll();
    }

    public static List<AccoutingHistory> getHistoryByDetail(Detail detail) {
        return controller.getByDetail(detail.getId());
    }
}
