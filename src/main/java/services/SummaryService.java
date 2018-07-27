package services;

import com.sun.istack.internal.NotNull;
import databaselogic.controllers.DBSummaryController;
import domain.Electrod;
import domain.ElectrodeSummary;
import entities.Summary;

import java.time.LocalDate;
import java.util.List;

public class SummaryService {
    private static final DBSummaryController contriller = new DBSummaryController();

    public static void save(Summary summary){
        contriller.save(summary);
    }

    public static Summary getSummaryByElectrode(Electrod electrod){
        return contriller.get(electrod.getId());
    }

    public static List<Summary> getAll(){
        return contriller.getAll();
    }

    public static List<ElectrodeSummary> bulkCreateElectrodeSummaryFromRange(@NotNull String from,@NotNull String to,@NotNull String type, LocalDate produce, LocalDate consume, String customer, String note){
        List<Electrod> electrods = ElectrodeService.bulkCreateElectrodeFromRange(from,to,type);

        return null;
    }
}
