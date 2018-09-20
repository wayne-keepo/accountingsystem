package entities;

import java.math.BigDecimal;

public class DetailElectrodePrimitive {
    private Integer id;
    private Integer idDetail;
    private String electrodeType;
    private Double count;
    private BigDecimal cost;

    public DetailElectrodePrimitive() {
    }

    public DetailElectrodePrimitive(Integer idDetail, String electrodeType, Double count, BigDecimal cost) {
        this.idDetail = idDetail;
        this.electrodeType = electrodeType;
        this.count = count;
        this.cost = cost;
    }

    public DetailElectrodePrimitive(Integer id, Integer idDetail, String electrodeType, Double count, BigDecimal cost) {
        this.id = id;
        this.idDetail = idDetail;
        this.electrodeType = electrodeType;
        this.count = count;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(Integer idDetail) {
        this.idDetail = idDetail;
    }

    public String getElectrodeType() {
        return electrodeType;
    }

    public void setElectrodeType(String electrodeType) {
        this.electrodeType = electrodeType;
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

    @Override
    public String toString() {
        return "DetailElectrodePrimitive{" +
                "id=" + id +
                ", idDetail=" + idDetail +
                ", electrodeType='" + electrodeType + '\'' +
                ", count=" + count +
                ", cost=" + cost +
                '}';
    }
}
