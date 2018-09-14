package domain;

import java.math.BigDecimal;

public class DetailElectrodeDataUpdate {
    private Integer idDetail;
    private Double count;
    private BigDecimal cost;
    private String type;

    public DetailElectrodeDataUpdate(Integer idDetail, Double count, BigDecimal cost, String type) {
        this.idDetail = idDetail;
        this.count = count;
        this.cost = cost;
        this.type = type;
    }

    public Integer getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(Integer idDetail) {
        this.idDetail = idDetail;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
