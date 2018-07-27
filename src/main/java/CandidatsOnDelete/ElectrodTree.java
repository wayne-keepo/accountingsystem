package CandidatsOnDelete;

import domain.Electrod;
import entities.Detail;

import java.math.BigDecimal;
import java.util.List;

public class ElectrodTree {
    private Electrod electrod;
    private List<Detail> details;

    public ElectrodTree() {
    }

    public ElectrodTree(Electrod electrod, List<Detail> details) {
        this.electrod = electrod;
        this.details = details;
    }

    public Electrod getElectrod() {
        return electrod;
    }

    public void setElectrod(Electrod electrod) {
        this.electrod = electrod;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

}
