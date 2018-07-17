package views.tables.utils;

import domain.Balance;
import domain.DetailElectrod;
import projectConstants.CustomConstants;

import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Searcher {
    private static final Map<RussianMonths, Month> enMonths = createMap();
    private static final Map<Month,RussianMonths> ruMonths = createRuMonths();

    private static LinkedHashMap<RussianMonths, Month> createMap() {
        LinkedHashMap<RussianMonths, Month> inimonths = new LinkedHashMap<RussianMonths, Month>();
        inimonths.put(RussianMonths.Январь, Month.JANUARY);
        inimonths.put(RussianMonths.Февраль, Month.FEBRUARY);
        inimonths.put(RussianMonths.Март, Month.MARCH);
        inimonths.put(RussianMonths.Апрель, Month.APRIL);
        inimonths.put(RussianMonths.Май, Month.MAY);
        inimonths.put(RussianMonths.Июнь, Month.JUNE);
        inimonths.put(RussianMonths.Июль, Month.JULY);
        inimonths.put(RussianMonths.Август, Month.AUGUST);
        inimonths.put(RussianMonths.Сентябрь, Month.SEPTEMBER);
        inimonths.put(RussianMonths.Октябрь, Month.OCTOBER);
        inimonths.put(RussianMonths.Ноябрь, Month.NOVEMBER);
        inimonths.put(RussianMonths.Декабрь, Month.DECEMBER);
        return inimonths;
    }

    private static LinkedHashMap<Month,RussianMonths> createRuMonths(){
        LinkedHashMap<Month,RussianMonths> inimonth = new LinkedHashMap<>();
        inimonth.put(Month.JANUARY	,RussianMonths.Январь);
        inimonth.put(Month.FEBRUARY	,RussianMonths.Февраль);
        inimonth.put(Month.MARCH		,RussianMonths.Март);
        inimonth.put(Month.APRIL		,RussianMonths.Апрель);
        inimonth.put(Month.MAY		,RussianMonths.Май);
        inimonth.put(Month.JUNE		,RussianMonths.Июнь);
        inimonth.put(Month.JULY		,RussianMonths.Июль);
        inimonth.put(Month.AUGUST	,RussianMonths.Август);
        inimonth.put(Month.SEPTEMBER	,RussianMonths.Сентябрь);
        inimonth.put(Month.OCTOBER	,RussianMonths.Октябрь);
        inimonth.put(Month.NOVEMBER	,RussianMonths.Ноябрь);
        inimonth.put(Month.DECEMBER	,RussianMonths.Декабрь);
        return inimonth;
    }

    //for balance
    //TODO: переделать в вид Map<RuMonth,Month> и такой же для обратной конвертации (+)
    public static Month searchEngMonthByRus(String month) {
        RussianMonths ru = RussianMonths.valueOf(month);
        return enMonths.get(ru);
    }
    public static Month searchEngMonthByRus(RussianMonths month) {
        return enMonths.get(month);
    }

    public static RussianMonths searchRuMonthByEng(Month month){
        return ruMonths.get(month);
    }

    public static Double findValueByMonth(Balance balance, String month, String eventName) {
        Month key = searchEngMonthByRus(month);
        if (eventName == CustomConstants.INCOMING)
            return balance.getIncoming().get(key);
        if (eventName == CustomConstants.OUTCOMING)
            return balance.getOutcoming().get(key);
        return null;
    }

    public static List<DetailElectrod> findProductInfo(List<DetailElectrod> items, String type) {
        List<DetailElectrod> electrods = new ArrayList<>();
        for (DetailElectrod electrod : items) {
            if (electrod.getElectrod().getType() == type)
                electrods.add(electrod);
        }
        return electrods;
    }
}
