package services;

import databaselogic.controllers.DBRawElectrodeController;
import entities.RawElectrode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;


public class ElectrodeService {

    private static final DBRawElectrodeController rawController = new DBRawElectrodeController();

    // TODO: пересмотреть решение
    public static String formatElectrodeNumber(String number) {
        if (6 - number.length() == 0)
            return number;
        int delta = 6 - number.length();
        StringBuilder zeros = new StringBuilder();
        for (int i = 0; i < delta; i++) {
            zeros.append("0");
        }
        number = zeros.toString() + number;
        zeros.setLength(0);
        return number;
    }

    public static RawElectrode initRawElectrode(String type, int count) {
        return rawController.initRawElectrode(type, count);
    }

    public static void updateRawElectrodeCount(RawElectrode rw, int count) {
        rawController.updateCount(rw.getId(), count);
    }

    public static RawElectrode getRawElectrodeByType(String type) {
        return rawController.getByType(type);
    }

    public static ObservableList<RawElectrode> getAllAsObservableList() {
        List<RawElectrode> rws = rawController.getAll();
        if (rws.isEmpty())
            return FXCollections.observableList(new ArrayList<>());
        return FXCollections.observableList(rws);
    }
}
