package services;

import databaselogic.controllers.DBDetailController;
import entities.Detail;

import java.util.List;

public class DetailService {
    private static final DBDetailController controller = new DBDetailController();

    public static List<Detail> getAll(){
        return controller.getAll();
    }

}
