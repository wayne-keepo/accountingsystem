package services;

import databaselogic.controllers.DBSummaryController;
import domain.Electrod;
import entities.Summary;

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
}
