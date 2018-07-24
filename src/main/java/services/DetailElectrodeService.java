package services;

import databaselogic.controllers.DBDetaileElectrodeController;
import entities.DetailElectrodePrimitive;

import java.util.List;
import java.util.Map;

public class DetailElectrodeService {
    private static DBDetaileElectrodeController controller = new DBDetaileElectrodeController();

    public static List<DetailElectrodePrimitive> getAllByType(String type){
        return controller.getAllByType(type);
    }

    public static List<DetailElectrodePrimitive> getAllDEPrimitivs(){
        return controller.getAll();
    }
}
