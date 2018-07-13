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

