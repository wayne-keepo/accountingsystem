import databaselogic.controllers.DBAccountingHistoryController;
import entities.AccoutingHistory;
import services.AccoutingHistoryService;
import views.modalWindows.AccoutingHistoryWindow;
import views.tables.utils.RussianMonths;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;

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

        //Accounting History Controller tests

//        DBAccountingHistoryController dbachc = new DBAccountingHistoryController();
//        AccoutingHistory achis = dbachc.getByDetail(1).get(0);
//        List<AccoutingHistory> ahList = dbachc.getByDetail(1);
//        Map<RussianMonths,List<AccoutingHistory>> testMap = AccoutingHistoryService.historyToMapForAccoutingWindow(ahList);
//        testMap.forEach((k,v)->{
//            System.out.println(k + "\n"+v+"\n");
//        });
        Simple s1 = new Simple(11);
        Simple s2 = new Simple(22);
        Map<Integer,Simple> m1 = new HashMap<>();
        m1.put(1,s1);
        m1.put(2,s2);

        Map<Integer,Simple> m2 = new HashMap<>();
        m2.put(3,s1);
        m2.put(4,s2);
        System.out.println("Befor update: ");
        System.out.println(m1.toString()+"\n"+m2.toString());
        m2.forEach((k,v)->{
            v.setValue(123);
        });

        System.out.println("After update: ");
        System.out.println(m1.toString()+"\n"+m2.toString());
    }
    static class Simple{
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
            return "Simple: "+value;
        }
    }
}

