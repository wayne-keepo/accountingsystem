package services;

import databaselogic.controllers.DBRawElectrodeController;
import entities.RawElectrode;


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

    public static void initRawElectrode() {
        rawController.initRawElectrodeValue();
    }

    public static void updateRawElectrodeCount(int count) {
        RawElectrode.getInstance().setCount(count);
        rawController.updateCount();
    }

}
