package services;

import databaselogic.controllers.DBElectrodeController;
import domain.Electrod;
import domain.ElectrodeSummary;
import entities.Summary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ElectrodeService {
    private static final DBElectrodeController controller = new DBElectrodeController();

    public static void save(Electrod electrod){
        controller.save(electrod);
    }

    public static Electrod getByNumber(String number){
        return controller.get(number);
    }

    public static ObservableList<Electrod> getAll(){
        return FXCollections.observableArrayList(controller.getAll());
    }

    public static void delete(Electrod electrod) {
        controller.delete(electrod.getId());
    }

    public static ObservableList<ElectrodeSummary> buildElectrodeSummary(){
        List<Electrod> electrods = controller.getAll();
        List<Summary> summaries = SummaryService.getAll();
        List<ElectrodeSummary> es = new ArrayList<>();
        for (Summary summary: summaries){
            es.add(new ElectrodeSummary(
                    electrods.stream().filter(e->e.getId()==summary.getIdElectrode()).findFirst().get(),
                    summary
            ));
        }
        return FXCollections.observableArrayList(es);
    }
}
