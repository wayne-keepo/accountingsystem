package domain;

import entities.Detail;

import java.time.Month;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public class Balance {
    private int id;
    private Detail detail;
    private Year year;
    private Integer balanceAtBeginningYear;
    private Integer balanceAtEndOfYear;
    private Integer inTotal;
    private Integer outTotal;
    private Map<Month,Integer> receipt;
    private Map<Month,Integer> consumption;

    public Balance() {
        this.id = 0;
        this.detail = null;
        this.year = Year.now();
        this.balanceAtBeginningYear = 0;
        this.balanceAtEndOfYear = 0;
        this.inTotal = 0;
        this.outTotal = 0;
        this.receipt  = new HashMap<Month,Integer>();
        this.consumption = new HashMap<Month,Integer>();
    }

    public Balance(int id, Detail detail, Year year, Integer balanceAtBeginningYear, Integer balanceAtEndOfYear, Integer inTotal, Integer outTotal, HashMap<Month, Integer> receipt, HashMap<Month, Integer> consumption) {
        this.id = id;
        this.detail = detail;
        this.year = year;
        this.balanceAtBeginningYear = balanceAtBeginningYear;
        this.balanceAtEndOfYear = balanceAtEndOfYear;
        this.inTotal = inTotal;
        this.outTotal = outTotal;
        this.receipt = receipt;
        this.consumption = consumption;
    }

    public Integer getInTotal() {
        return inTotal;
    }

    public void setInTotal(Integer inTotal) {
        this.inTotal = inTotal;
    }

    public Integer getOutTotal() {
        return outTotal;
    }

    public void setOutTotal(Integer outTotal) {
        this.outTotal = outTotal;
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

    public Integer getBalanceAtBeginningYear() {
        return balanceAtBeginningYear;
    }

    public void setBalanceAtBeginningYear(Integer balanceAtBeginningYear) {
        this.balanceAtBeginningYear = balanceAtBeginningYear;
    }

    public Integer getBalanceAtEndOfYear() {
        return balanceAtEndOfYear;
    }

    public void setBalanceAtEndOfYear(Integer balanceAtEndOfYear) {
        this.balanceAtEndOfYear = balanceAtEndOfYear;
    }

    public Map<Month, Integer> getReceipt() {
        return receipt;
    }

    public void setReceipt(Map<Month, Integer> receipt) {
        this.receipt = receipt;
    }

    public Map<Month, Integer> getConsumption() {
        return consumption;
    }

    public void setConsumption(Map<Month, Integer> consumption) {
        this.consumption = consumption;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", detail=" + detail +
                ", year=" + year +
                ", balanceAtBeginningYear=" + balanceAtBeginningYear +
                ", balanceAtEndOfYear=" + balanceAtEndOfYear +
                ", inTotal=" + inTotal +
                ", outTotal=" + outTotal +
                ", receipt=" + receipt +
                ", consumption=" + consumption +
                '}';
    }
}
