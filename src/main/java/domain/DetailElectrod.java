package domain;

import entities.Detail;

import java.util.Objects;

public class DetailElectrod {
    private Detail detail;
    private Electrod electrod;
    private int countDetailForElectrode;

    public DetailElectrod() {
    }

    public DetailElectrod(Detail detail, Electrod electrod, int countDetailForElectrode) {
        this.detail = detail;
        this.electrod = electrod;
        this.countDetailForElectrode = countDetailForElectrode;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public Electrod getElectrod() {
        return electrod;
    }

    public void setElectrod(Electrod electrod) {
        this.electrod = electrod;
    }

    public int getCountDetailForElectrode() {
        return countDetailForElectrode;
    }

    public void setCountDetailForElectrode(int countDetailForElectrode) {
        this.countDetailForElectrode = countDetailForElectrode;
    }

    @Override
    public String toString() {
        return "DetailElectrod{" +
                "detail=" + detail +
                ", electrod=" + electrod +
                ", countDetailForElectrode=" + countDetailForElectrode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailElectrod that = (DetailElectrod) o;
        return countDetailForElectrode == that.countDetailForElectrode &&
                Objects.equals(detail, that.detail) &&
                Objects.equals(electrod, that.electrod);
    }

    @Override
    public int hashCode() {

        return Objects.hash(detail, electrod, countDetailForElectrode);
    }
}
