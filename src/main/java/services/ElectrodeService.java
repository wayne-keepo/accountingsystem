package services;

import databaselogic.controllers.DBElectrodeController;
import databaselogic.controllers.DBRawElectrodeController;
import domain.Electrod;
import domain.ElectrodeSummary;
import entities.RawElectrode;
import entities.Summary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projectConstants.DBConstants;

import java.util.ArrayList;
import java.util.List;

public class ElectrodeService {
    private static final DBElectrodeController controller = new DBElectrodeController();
    private static final DBRawElectrodeController rawController = new DBRawElectrodeController();

    private static void bulkSave(List<Electrod> electrods) {
        controller.bulkSave(electrods);
    }
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

    public static void initRawElectrode() {
        rawController.initRawElectrodeValue();
    }

    public static void updateRawElectrodeCount(int count) {
        RawElectrode.getInstance().setCount(count);
        rawController.updateCount();
    }

}
