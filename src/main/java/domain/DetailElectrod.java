package domain;

import entities.Detail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailElectrod {
    private List<Integer> ids;
    // деталь: [количество,стоимость]
    private Map<Detail,Map<Double,BigDecimal>> details;
//    private List<Detail> details;
    private String electrodeType;
//    private int countDetailForElectrode;

    public DetailElectrod() {}
    // нужен для первичной (когда в таблице нет связи деталь-электрод) инициализации и добавления в tableview
    public DetailElectrod(boolean initDE){
        if (initDE){
            ids = new ArrayList<>();
            details = new HashMap<>();
            electrodeType = "";
        }

    }
    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Map<Detail, Map<Double, BigDecimal>> getDetails() {
        return details;
    }

    public void setDetails(Map<Detail, Map<Double, BigDecimal>> details) {
        this.details = details;
    }

    public String getElectrodeType() {
        return electrodeType;
    }

    public void setElectrodeType(String electrodeType) {
        this.electrodeType = electrodeType;
    }

    public boolean isEmpty(){
        return ids.isEmpty() && details.isEmpty() && electrodeType.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("DE with ids: %s ; Details and Counts: %s ; Electrode Type: %s",ids.toString(),details.toString(),electrodeType);
    }
}
