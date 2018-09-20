package services;

import databaselogic.controllers.DBDetaileElectrodeController;
import domain.DetailElectrodeDataUpdate;
import utils.ChainUtil;
import domain.DetailElectrod;
import entities.Detail;
import entities.DetailElectrodePrimitive;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DetailElectrodeService {
    private static DBDetaileElectrodeController controller = new DBDetaileElectrodeController();

    public static List<DetailElectrodePrimitive> getAllByType(String type) {
        return controller.getAllByType(type);
    }

    public static List<DetailElectrodePrimitive> getAllDEPrimitivs() {
        return controller.getAll();
    }

    public static List<DetailElectrod> getAllDE(List<Detail> details) {
        List<DetailElectrodePrimitive> tmp = getAllDEPrimitivs();
        return ChainUtil.createChainDetailElectrode(details, tmp);
    }

    public static List<DetailElectrod> getDEByType(List<Detail> details, String type) {
        List<DetailElectrodePrimitive> tmp = getAllByType(type);
        return ChainUtil.createChainDetailElectrode(details, tmp)
                .stream()
                .filter(d -> d.getElectrodeType().equals(type))
                .collect(Collectors.toList());
    }

    public static void dataUpdate(DetailElectrod de, List<Integer> changes) {
        String electrodeType = de.getElectrodeType();
        List<DetailElectrodeDataUpdate> dataUpdate = new ArrayList<>(changes.size());
        Map<Detail, Map<Double, BigDecimal>> detailCountCost = de.getDetails();

        changes.forEach(id -> {
            Detail detail = detailCountCost.keySet().stream().filter(did -> did.getId() == id).findFirst().get();
            Double count = detailCountCost.get(detail).keySet().iterator().next();
            BigDecimal cost = detailCountCost.get(detail).get(count);
            dataUpdate.add(new DetailElectrodeDataUpdate(detail.getId(), count, cost, electrodeType));
        });

        if (!dataUpdate.isEmpty())
            controller.batchDataUpdate(dataUpdate);
    }

    public static void deleteByDetailAndElType(int detailID, String type) {
        controller.deleteByDetailAndElType(detailID,type);
    }

    public static DetailElectrodePrimitive add(Detail detail, Double count, BigDecimal cost, String type) {
        DetailElectrodePrimitive primitive = new DetailElectrodePrimitive(detail.getId(),type,count,cost);
        controller.save(primitive);
        primitive = controller.getByDetailAndType(detail.getId(),type);
        return primitive;
    }
}
