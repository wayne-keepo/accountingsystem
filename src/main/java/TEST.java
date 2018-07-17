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

        //Detail tests
//        DBDetailController dbDetailController = new DBDetailController();
//        Detail detail = dbDetailController.get(1);

        //Accounting History tests
//        DBAccountingHistoryController dbachc = new DBAccountingHistoryController();
//        AccoutingHistory achis = dbachc.getByDetail(1).get(0);
//        achis.setDetail(detail);
//        List<AccoutingHistory> ahList = dbachc.getByDetail(1);
//        Map<RussianMonths, List<AccoutingHistory>> histories = AccoutingHistoryService.historyToMapForAccoutingWindow(ahList);
        Double d = 10.0512;
        String s = String.valueOf(d);
        String s2 = String.format("test %s",s);
        System.out.println(s2);

        StringBuilder st = new StringBuilder();
        st.append(123).append(321).append("qweqwe");
        System.out.println(st+" "+st.length());
        st.setLength(0);
        System.out.println(st);
        st.append(1).append(2).append("zxc");
        System.out.println(st+" "+st.length());
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

