import databaselogic.controllers.DBAccountingHistoryController;
import databaselogic.controllers.DBDetailController;
import domain.Day;
import entities.AccoutingHistory;
import entities.Detail;
import services.AccoutingHistoryService;
import views.modalWindows.AccoutingHistoryWindow;
import views.tables.utils.RussianMonths;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class TEST {
    public static void main(String[] args) {

//        Integer year = Year.now().getValue();
//        System.out.println(year);
//        System.out.println(Arrays.toString(Month.values()));
//        System.out.println(Month.of(7));
//        System.out.println(LocalDate.now().getMonth().getValue());
//        System.out.println(Arrays.toString(RussianMonths.values()));
//        System.out.println(RussianMonths.of(8)+ " "+ RussianMonths.Август.getValue());
//        System.out.println(achis.toString());

        //Detail tests
        DBDetailController dbDetailController = new DBDetailController();
        Detail detail = dbDetailController.get(1);
        //Accounting History tests
        DBAccountingHistoryController dbachc = new DBAccountingHistoryController();

        AccoutingHistory achis = dbachc.getByDetail(1).get(0);
        achis.setDetail(detail);
        List<AccoutingHistory> ahList = dbachc.getByDetail(1);
        Map<RussianMonths, List<AccoutingHistory>> histories = AccoutingHistoryService.historyToMapForAccoutingWindow(ahList);

        String days = "";
        String batchUpdate="";
//        for (Day day : achis.getDays()) {
//            if (!(day.getDayNumber() == 31))
//                days.append(String.format(" d%d = %d,\n", day.getDayNumber(), day.getCount()));
//            else
//                days.append(String.format(" d%d = %d\n", day.getDayNumber(), day.getCount()));
//
//        }
//        String batchUpdate = String.format("UPDATE AccountingHistory SET\n%s WHERE\n id = %d", days.toString(), achis.getId());

        List<String> sqlForUpdate = new ArrayList<>();
        System.out.println("RUN FOR HISTORY MAP:--------------------------------------------");
        for (Map.Entry<RussianMonths,List<AccoutingHistory>> maps: histories.entrySet()){
            System.out.println(maps.getKey());
            System.out.println("RUN FOR HISTORY LIST:--------------------------------------------");
            for (AccoutingHistory history: maps.getValue()){
                System.out.println("RUN FOR HISTORY DAY:--------------------------------------------");
                for (Day day: history.getDays()){
                    if (!(day.getDayNumber() == 31))
                        days+=String.format(" d%d = %d,", day.getDayNumber(), day.getCount());
                    else
                        days+=String.format(" d%d = %d", day.getDayNumber(), day.getCount());
                }
                batchUpdate = String.format("\nUPDATE AccountingHistory SET\n%s WHERE\n id = %d and year = %d and month = %d and acc = %d",
                        days, history.getId(),history.getYear().getValue(),history.getMonth().getValue(),history.getAcc());
                sqlForUpdate.add(batchUpdate);
                batchUpdate ="";
                days ="";
            }
        }
        System.out.println(sqlForUpdate.get(7));

    }

    static class Simple {
        private int value;

        public Simple(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Simple: " + value;
        }
    }
}

