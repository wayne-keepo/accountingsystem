package services;

import databaselogic.controllers.DBSummaryController;
import entities.Summary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SummaryService {
    private static final DBSummaryController controller = new DBSummaryController();

    public static void save(Summary summary) {
        controller.save(summary);
    }

    private static void bulkInsertSummaries(List<Summary> summaries) {
        controller.bulkInsert(summaries);
    }

    public static ObservableList<Summary> getAllAsObservableList() {
        List<Summary> summaries = controller.getAll();
        summaries.forEach(summary -> {
            String number = summary.getElectrodeNumber();
            number = ElectrodeService.formatElectrodeNumber(number);
            summary.setElectrodeNumber(number);
        });

        return FXCollections.observableList(summaries);
    }

    public static List<Summary> getAll() {
        return controller.getAll();
    }

    public static void bulkCreateSummaryFromRange(String from, String to, String type, LocalDate produce, LocalDate consume, String customer, String note) {
        List<Summary> summaries = new ArrayList<>();
        int numericFrom = Integer.valueOf(from);
        int numericTo = Integer.valueOf(to);
        for (int i = numericFrom; i < numericTo + 1; i++) {
            // TODO: переделать метод formatElectrodeNumber и вынести его в этот класс, ElectrodeService просмотреть, забрать ползеное и удалить.
            String strNumber = ElectrodeService.formatElectrodeNumber(String.valueOf(i));
            summaries.add(new Summary(strNumber, type, produce, customer, consume, note));
        }
        bulkInsertSummaries(summaries);

    }

    public static void delete(Summary summary) {
        controller.delete(summary.getIdSummary());
    }

    public static void update(Summary summary) {
        controller.update(summary);
    }

    public static boolean checkOnDuplicateNumbers(String from, List<Summary> summaries) {
        String _from = ElectrodeService.formatElectrodeNumber(from);
        for (Summary summary : summaries)
            if (summary.getElectrodeNumber().equals(_from))
                return true;
        return false;
    }
// TODO: ?? просмотреть со свежей головой
//    private static List<ElectrodeSummary> buildElectrodeSummaryFromSource(List<Electrod> electrods, List<Summary> summaries) {
//        List<ElectrodeSummary> es = new ArrayList<>();
//        electrods.forEach(electrod -> {
//            String num = ElectrodeService.formatElectrodeNumber(electrod.getElectrodNumber());
//            electrod.setElectrodNumber(num);
//            summaries.stream()
//                    .filter(s -> s.getIdElectrode().equals(electrod.getId()))
//                    .findFirst()
//                    .ifPresent(summary -> es.add(new ElectrodeSummary(electrod, summary)));
//        });
//
//        return es;
//    }

}
