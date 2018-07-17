package domain;

import entities.Detail;
import entities.Summary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import projectConstants.CustomConstants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InitializerForTest {
    private static List<Detail> details = Arrays.asList(
            new Detail(1, "Корпус с гайками и комплектом прокладок", 1.1, null, new BigDecimal("600.00")),
            new Detail(2, "Медный стержень", 1.1, null, new BigDecimal("100.00")),
            new Detail(3, "Шайба медная большая", 1.1, null, new BigDecimal("40.00")),
            new Detail(4, "Шайба медная маленькая", 1.1, null, new BigDecimal("40.00")),
            new Detail(5, "Гайка М5", 1.1, null, new BigDecimal("0.07")),
            new Detail(6, "Шайба М5", 1.1, null, new BigDecimal("4.90")),
            new Detail(7, "Наконечник кольцевой неизол. C-RC 1/M6", 5.1, null, new BigDecimal("11.60")),
            new Detail(8, "Наконечник кольцевой изол НКИ (комп. 5 шт)", 1.1, null, new BigDecimal("1.52")),
            new Detail(9, "Разъем плоский РПИ-П", 1.1, null, new BigDecimal("3.32")),
            new Detail(10, "Разъем плоский РППИ-М", 1.1, null, new BigDecimal("600.00"))
            );

    private static Electrod e1 =
            new Electrod(1, "666", CustomConstants.ESMG);

    private static Electrod e2 =
            new Electrod(2, "999", CustomConstants.ESMG_M);

    private static List<ElectrodTree> tree = Arrays.asList(
            new ElectrodTree(e1,details),
            new ElectrodTree(e2,details)
    );

    private static Summary s1 = new Summary(
            1, e1.getId(), LocalDate.now(), "Test One Customer", LocalDate.now(),"HZ One"
    );
    private static Summary s2 = new Summary(
            2, e2.getId(), LocalDate.now(), "Test Two Customer", LocalDate.now(),"HZ Two"
    );

    private static List<ElectrodeSummary> electrodeSummaries = Arrays.asList(
            new ElectrodeSummary(e1,s1),
            new ElectrodeSummary(e2,s2)
    );

    public static ObservableList<ElectrodTree> getElectrodTree(){
        return FXCollections.observableArrayList(tree);
    }
    public static ObservableList<Detail> getDetails() {
        return FXCollections.observableArrayList(details);
    }
//    public static ObservableList<Balance> getTestBalance() {
//        ObservableList<Balance> balances = FXCollections.observableArrayList();
//
//        HashMap<Month, Double> receipt = new HashMap<>();
//        HashMap<Month, Double> consumption = new HashMap<>();
//
//        receipt.put(Month.JANUARY, 1234);
//        receipt.put(Month.FEBRUARY, 1456);
//        receipt.put(Month.MARCH, 1786);
//        receipt.put(Month.APRIL, 1589);
//        receipt.put(Month.MAY, 1756);
//        receipt.put(Month.JUNE, 7896);
//        receipt.put(Month.JULY, 1238);
//        receipt.put(Month.AUGUST, 7539);
//        receipt.put(Month.SEPTEMBER, 7824);
//        receipt.put(Month.OCTOBER, 1234);
//        receipt.put(Month.NOVEMBER, 1234);
//        receipt.put(Month.DECEMBER, 1234);
//
//        consumption.put(Month.JANUARY, 999);
//        consumption.put(Month.FEBRUARY, 999);
//        consumption.put(Month.MARCH, 999);
//        consumption.put(Month.APRIL, 999);
//        consumption.put(Month.MAY, 999);
//        consumption.put(Month.JUNE, 999);
//        consumption.put(Month.JULY, 999);
//        consumption.put(Month.AUGUST, 999);
//        consumption.put(Month.SEPTEMBER, 999);
//        consumption.put(Month.OCTOBER, 999);
//        consumption.put(Month.NOVEMBER, 999);
//        consumption.put(Month.DECEMBER, 999);
//
//        Integer balanceAtBeginningYear = 1234;
//        Integer balanceAtEndOfYear = 1234 - 999;
//
//        for (int i = 0; i<details.size();i++){
//            balances.add(new Balance(
//                    i+1,
//                    details.get(i),
//                    Year.now(),
//                    balanceAtBeginningYear,
//                    balanceAtEndOfYear,
//                    1234,
//                    balanceAtEndOfYear,
//                    receipt,
//                    consumption
//            ));
//        }
//        return balances;
//    }
    public static Electrod getElectrode() {
        return e1;
    }

    public static ObservableList<ElectrodeSummary> getElectrodeSummary(){
        return FXCollections.observableArrayList(electrodeSummaries);
    }

    public static ObservableList<DetailElectrod> getTestDE() {
        ObservableList<DetailElectrod> des = FXCollections.observableArrayList();
        init(des);
        return des;
    }

    private static void init(ObservableList<DetailElectrod> des){
        for (Detail detail: details){
            des.addAll(
                    new DetailElectrod(
                            detail,
                            e1,
                            100
                            ),
                    new DetailElectrod(
                            detail,
                            e2,
                            200
                            )
            );
        }
    }
}
