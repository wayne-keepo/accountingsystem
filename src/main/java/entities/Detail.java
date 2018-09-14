package entities;

import java.math.BigDecimal;
import java.util.Objects;

public class Detail {
    private int id;
    private String title;
    private Double count;
    private BigDecimal cost;
    private String descriptions;


    public Detail() {
    }

    public Detail(String title, Double count,BigDecimal cost ,String descriptions) {
        this.title = title;
        this.count = count;
        this.cost = cost;
        this.descriptions = descriptions;
    }

    public Detail(int id, String title, Double count, String descriptions, BigDecimal cost) {
        this.id = id;
        this.title = title;
        this.count = count;
        this.cost = cost;
        this.descriptions = descriptions;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Detail detail = (Detail) o;
        return id == detail.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return title;
    }

    public String getDescriptionDetail(){
        return "Detail{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", count=" + count +
                ", cost=" + cost +
                ", descriptions='" + descriptions + '\'' +
                '}';
    }
}
