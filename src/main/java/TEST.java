import databaselogic.controllers.DBAccountingHistoryController;
import entities.AccoutingHistory;
import services.AccoutingHistoryService;
import views.tables.utils.RussianMonths;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

        DBAccountingHistoryController dbachc = new DBAccountingHistoryController();
//        AccoutingHistory achis = dbachc.getByDetail(1).get(0);
        List<AccoutingHistory> ahList = dbachc.getByDetail(1);
//        System.out.println(ahList.toString());
        Map<RussianMonths,List<AccoutingHistory>> testMap = AccoutingHistoryService.historyToMapForAccoutingWindow(ahList);
        testMap.forEach((k,v)->{
            System.out.println(k + "\n"+v+"\n");
        });
    }

}

