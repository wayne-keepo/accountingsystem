package domain;

import entities.Detail;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DetailElectrod {
    private List<Integer> ids;
    private Map<Detail,Double> details;
//    private List<Detail> details;
    private String electrodeType;
//    private int countDetailForElectrode;

    public DetailElectrod() {}

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Map<Detail, Double> getDetails() {
        return details;
    }

    public void setDetails(Map<Detail, Double> details) {
        this.details = details;
    }

    public String getElectrodeType() {
        return electrodeType;
    }

    public void setElectrodeType(String electrodeType) {
        this.electrodeType = electrodeType;
    }

    @Override
    public String toString() {
        return String.format("DE with ids: %s ; Details and Counts: %s ; Electrode Type: %s",ids.toString(),details.toString(),electrodeType);
    }
}
