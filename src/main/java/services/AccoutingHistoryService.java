package services;

import entities.AccoutingHistory;
import views.tables.utils.RussianMonths;
import views.tables.utils.Searcher;

import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AccoutingHistoryService {

    public static Map<RussianMonths, List<AccoutingHistory>> historyToMapForAccoutingWindow(List<AccoutingHistory> histories) {
        Map<RussianMonths, List<AccoutingHistory>> map = new HashMap<>();
//        AtomicInteger index = new AtomicInteger(0);
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
}
