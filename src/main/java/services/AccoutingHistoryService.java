package services;

import databaselogic.controllers.DBAccountingHistoryController;
import domain.Day;
import entities.AccoutingHistory;
import entities.Detail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Searcher;
import utils.enums.RussianMonths;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccoutingHistoryService {
    private static final Logger logger = LogManager.getLogger(AccoutingHistoryService.class);

    private static final DBAccountingHistoryController controller = new DBAccountingHistoryController();

    public static double[] calculate(List<AccoutingHistory> histories) {
        double incSum = 0.0;
        double outSum = 0.0;
        for (AccoutingHistory history : histories) {
            if (history.getAcc() == 1) {
                for (Day day : history.getDays()) {
                    incSum += day.getCount();
                }
            } else {
                for (Day day : history.getDays()) {
                    outSum += day.getCount();
                }
            }
        }
        return new double[]{incSum, outSum};
    }

    public static double calculateOneHistory(AccoutingHistory ac){
        double sum = 0.0;
        for (Day day: ac.getDays())
            sum+=day.getCount();
        return sum;
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
        logger.info("[AccountingHistoryLogic.Service.InitAccHis] START build sql for batch insert accounting history by detail...");
        logger.debug("  detail: {}",detail);
        List<String> sql = new ArrayList<>();
        for (Month current : Month.values()) {
            String inInsert = String.format("INSERT INTO AccountingHistory(idDetail,month,acc) VALUES(%d,%d,%d)", detail.getId(), current.getValue(), 1);
            String outInsert = String.format("INSERT INTO AccountingHistory(idDetail,month,acc) VALUES(%d,%d,%d)", detail.getId(), current.getValue(), 0);
            sql.add(inInsert);
            sql.add(outInsert);
        }
        logger.debug("  sql list size: {}",sql.size());
        String[] tmp = sql.toArray(new String[sql.size()]);
        logger.info("   batch insert in database");
        controller.batchInsert(tmp);
        logger.info("   initialize value in history by count detail: {}");
        logger.debug("  count detail: {}",detail.getCount());
        insertInitialValueInAccHisByDetailCount(detail);
        logger.info("[AccountingHistoryLogic.Service.InitAccHis] END build sql for batch insert accounting history by detail...");
    }
    private static void insertInitialValueInAccHisByDetailCount(Detail detail){
        updateHistoryForDay(
                Year.now().getValue(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getDayOfMonth(),
                1,
                detail.getId(),
                detail.getCount(),
                false
        );
    }
    private static void batchUpdate(List<String> upd) {
        String[] tmp = upd.toArray(new String[upd.size()]);
        controller.batchUpdate(tmp);
    }

    public static List<AccoutingHistory> getAll() {
        return controller.getAll();
    }

    public static List<AccoutingHistory> getHistoryByDetail(Detail detail) {
        return controller.getByDetail(detail.getId());
    }

    public static void updateHistoryForDay(int year, int month, int day, int acc, int detailId, double num, boolean isSum) {
        double newValue;
        if (isSum) {
            Double oldValue = controller.getDayValue(year, month, day, acc, detailId);
            newValue = oldValue + num;
        }
        else newValue = num;
        controller.updateHistoryForDay(year, month, day, acc, detailId, newValue);
    }
}
