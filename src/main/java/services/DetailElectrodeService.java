package services;

import databaselogic.controllers.DBDetaileElectrodeController;
import utils.ChainUtil;
import domain.DetailElectrod;
import entities.Detail;
import entities.DetailElectrodePrimitive;

import java.util.List;
import java.util.stream.Collectors;

public class DetailElectrodeService {
    private static DBDetaileElectrodeController controller = new DBDetaileElectrodeController();

    public static List<DetailElectrodePrimitive> getAllByType(String type){
        return controller.getAllByType(type);
    }

    public static List<DetailElectrodePrimitive> getAllDEPrimitivs(){
        return controller.getAll();
    }

    public static List<DetailElectrod> getAllDE(List<Detail> details){
        List<DetailElectrodePrimitive> tmp = getAllDEPrimitivs();
        return ChainUtil.createChainDetailElectrode(details,tmp);
    }

    public static List<DetailElectrod> getDEByType(List<Detail> details, String type){
        List<DetailElectrodePrimitive> tmp = getAllByType(type);
        return ChainUtil.createChainDetailElectrode(details,tmp)
                .stream()
                .filter(d->d.getElectrodeType().equals(type))
                .collect(Collectors.toList());
    }
}
