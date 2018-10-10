package domain;

import entities.Detail;

import java.time.Month;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//TODO: may be implements ObservableValue<Balance>, think about this!!! (main task)
public class Balance {
    private int id;
    private Detail detail;
    private Year year;
    private Double balanceAtBeginningYear;
    private Double balanceAtEndOfYear;
    private Double inTotal;
    private Double outTotal;
    private Map<Month,Double> incoming; // приход
    private Map<Month,Double> outcoming; // расход

    public Balance() {
        this.year = Year.now();
    }

    public Balance(int id, Detail detail, Year year, Double balanceAtBeginningYear, Double balanceAtEndOfYear, Double inTotal, Double outTotal, HashMap<Month, Double> incoming, HashMap<Month, Double> outcoming) {
        this.id = id;
        this.detail = detail;
        this.year = year;
        this.balanceAtBeginningYear = balanceAtBeginningYear;
        this.balanceAtEndOfYear = balanceAtEndOfYear;
        this.inTotal = inTotal;
        this.outTotal = outTotal;
        this.incoming = incoming;
        this.outcoming = outcoming;
    }

    public void updateIncomingValue(Month key, Double value){
        incoming.replace(key,value);
    }

    public void updateOutcomingValue(Month key, Double value){
        outcoming.replace(key,value);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Double getBalanceAtBeginningYear() {
        return balanceAtBeginningYear;
    }

    public void setBalanceAtBeginningYear(Double balanceAtBeginningYear) {
        this.balanceAtBeginningYear = balanceAtBeginningYear;
    }

    public Double getBalanceAtEndOfYear() {
        return balanceAtEndOfYear;
    }

    public void setBalanceAtEndOfYear(Double balanceAtEndOfYear) {
        this.balanceAtEndOfYear = balanceAtEndOfYear;
    }

    public Double getInTotal() {
        return inTotal;
    }

    public void setInTotal(Double inTotal) {
        this.inTotal = inTotal;
    }

    public Double getOutTotal() {
        return outTotal;
    }

    public void setOutTotal(Double outTotal) {
        this.outTotal = outTotal;
    }

    public Map<Month, Double> getIncoming() {
        return incoming;
    }

    public void setIncoming(Map<Month, Double> incoming) {
        this.incoming = incoming;
    }

    public Map<Month, Double> getOutcoming() {
        return outcoming;
    }

    public void setOutcoming(Map<Month, Double> outcoming) {
        this.outcoming = outcoming;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Balance balance = (Balance) o;
        return id == balance.id &&
                Objects.equals(year, balance.year);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, year);
    }

    @Override
    public String toString() {
        return "\nBalance{" +
                "id=" + id +
                ", detail=" + detail +
                ", year=" + year +
                ", balanceAtBeginningYear=" + balanceAtBeginningYear +
                ", balanceAtEndOfYear=" + balanceAtEndOfYear +
                ", inTotal=" + inTotal +
                ", outTotal=" + outTotal +
                ", incoming=" + incoming +
                ", outcoming=" + outcoming +
                '}';
    }
}
