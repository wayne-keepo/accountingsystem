package services;

import com.sun.istack.internal.NotNull;
import databaselogic.controllers.DBSummaryController;
import domain.Electrod;
import domain.ElectrodeSummary;
import entities.Summary;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SummaryService {
    private static final DBSummaryController contriller = new DBSummaryController();

    private static List<Summary> bulkInsertSummaries(List<Summary> summaries) {
        contriller.bulkInsert(summaries);
        return null;
    }

    public static void save(Summary summary) {
        contriller.save(summary);
    }

    private static List<Summary> getSummariesByElectrods(List<Electrod> electrods) {
        StringBuilder ids = new StringBuilder();
        electrods.forEach(e -> ids.append(e.getId()).append(","));
        int idex = ids.lastIndexOf(",");
        ids.delete(idex, idex + 1);
        String sql = String.format("SELECT * FROM Summary WHERE iDElectrode in(%s)", ids.toString());
        return contriller.getSummariesByElectrods(sql);
    }

    public static Summary getSummaryByElectrode(Electrod electrod) {
        return contriller.get(electrod.getId());
    }

    public static List<Summary> getAll() {
        return contriller.getAll();
    }

    public static List<ElectrodeSummary> bulkCreateElectrodeSummaryFromRange(@NotNull String from, @NotNull String to, @NotNull String type, LocalDate produce, LocalDate consume, String customer, String note) {
        if (from.isEmpty() || to.isEmpty() || type.isEmpty())
            return null;
        List<Electrod> electrods = ElectrodeService.bulkCreateElectrodeFromRange(from, to, type);
        List<Summary> summaries = new ArrayList<>();
        List<ElectrodeSummary> electrodeSummaries = new ArrayList<>();
        electrods.forEach(e -> summaries.add(new Summary(e.getId(), produce, customer, consume, note)));
        bulkInsertSummaries(summaries);
        List<Summary> tmp = getSummariesByElectrods(electrods);

        electrodeSummaries = buildElectrodeSummaryFromSource(electrods, tmp);
        return electrodeSummaries;
    }

    private static List<ElectrodeSummary> buildElectrodeSummaryFromSource(List<Electrod> electrods, List<Summary> summaries) {
        List<ElectrodeSummary> es = new ArrayList<>();
        electrods.forEach(electrod -> {
            String num = ElectrodeService.formatElectrodeNumber(electrod.getElectrodNumber());
            electrod.setElectrodNumber(num);
            summaries.stream()
                    .filter(s -> s.getIdElectrode().equals(electrod.getId()))
                    .findFirst()
                    .ifPresent(summary -> es.add(new ElectrodeSummary(electrod, summary)));
        });

        return es;
    }

}
