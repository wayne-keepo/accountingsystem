package services;

import databaselogic.controllers.DBElectrodeController;
import domain.Electrod;
import domain.ElectrodeSummary;
import entities.Summary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projectConstants.DBConstants;

import java.util.ArrayList;
import java.util.List;

public class ElectrodeService {
    private static final DBElectrodeController controller = new DBElectrodeController();

    private static void bulkSave(List<Electrod> electrods){
        controller.bulkSave(electrods);
    }

    public static List<Electrod> getElectrodsByNumbers(List<String > numbers){
        StringBuilder st = new StringBuilder();
        for (int i = 0;i<numbers.size();i++){
            if (numbers.size()-i==1)
                st.append(numbers.get(i));
            else
                st.append(numbers.get(i)).append(",");
        }
        String sql = String.format("%s ( %s )",DBConstants.SELECT_ELECTRODS_BY_NUMBERS,st.toString());
        st.setLength(0);
        return controller.getByNumbers(sql);

    }
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
        if (electrods.isEmpty() && summaries.isEmpty())
            return null;
        List<ElectrodeSummary> es = new ArrayList<>();
        for (Summary summary: summaries){
            es.add(new ElectrodeSummary(
                    electrods.stream().filter(e-> e.getId().equals(summary.getIdElectrode())).findFirst().get(),
                    summary
            ));
        }
        return FXCollections.observableArrayList(es);
    }

// stupid decision, to find an acceptable
    public static List<Electrod> bulkCreateElectrodeFromRange(String from, String to, String type){
        int iFrom = Integer.valueOf(from);
        int iTo = Integer.valueOf(to);
        List<Electrod> electrods = new ArrayList<>();
        for (int i = iFrom;i<iTo;i++){
            String number = String.valueOf(i);
            int addZero = 6-number.length();
            if (addZero>0) {
                StringBuilder zero = new StringBuilder();
                for (int z = 0; z < addZero; z++)
                    zero.append("0");
                number = zero.toString()+number;
                zero.setLength(0);
            }
            electrods.add(new Electrod(number,type));
        }
        electrods.add(new Electrod(to,type));
        List<String> numbers = new ArrayList<>();
        electrods.forEach(e->numbers.add(e.getElectrodNumber()));
        electrods = getElectrodsByNumbers(numbers);
        return electrods;
    }


}
