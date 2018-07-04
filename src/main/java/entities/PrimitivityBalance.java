package entities;

import java.time.Month;
import java.time.Year;

public class PrimitivityBalance {

    private int id = 0;
    private int idDetail;
    private Year year;
    private Month month;
    private int incoming;
    private int outcoming;
    private int balanceAtBeginningYear;
    private int balanceAtEndOfYear;
    public PrimitivityBalance(int idDetail,Year year,Month month){
        this.idDetail = idDetail;
        this.year = year;
        this.month = month;
        this.incoming = 0;
        this.outcoming = 0;
        this.balanceAtBeginningYear = 0;
        this.balanceAtEndOfYear = 0;
    }
    public PrimitivityBalance(int idDetail, Year year, Month month, int incoming, int outcoming, int balanceAtBeginningYear, int balanceAtEndOfYear) {
        this.idDetail = idDetail;
        this.year = year;
        this.month = month;
        this.incoming = incoming;
        this.outcoming = outcoming;
        this.balanceAtBeginningYear = balanceAtBeginningYear;
        this.balanceAtEndOfYear = balanceAtEndOfYear;
    }

    public PrimitivityBalance(int id, int idDetail, Year year, Month month, int incoming, int outcoming, int balanceAtBeginningYear, int balanceAtEndOfYear) {
        this.id = id;
        this.idDetail = idDetail;
        this.year = year;
        this.month = month;
        this.incoming = incoming;
        this.outcoming = outcoming;
        this.balanceAtBeginningYear = balanceAtBeginningYear;
        this.balanceAtEndOfYear = balanceAtEndOfYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public int getIncoming() {
        return incoming;
    }

    public void setIncoming(int incoming) {
        this.incoming = incoming;
    }

    public int getOutcoming() {
        return outcoming;
    }

    public void setOutcoming(int outcoming) {
        this.outcoming = outcoming;
    }

    public int getBalanceAtBeginningYear() {
        return balanceAtBeginningYear;
    }

    public void setBalanceAtBeginningYear(int balanceAtBeginningYear) {
        this.balanceAtBeginningYear = balanceAtBeginningYear;
    }

    public int getBalanceAtEndOfYear() {
        return balanceAtEndOfYear;
    }

    public void setBalanceAtEndOfYear(int balanceAtEndOfYear) {
        this.balanceAtEndOfYear = balanceAtEndOfYear;
    }

    @Override
    public String toString() {
        return "PrimitivityBalance{" +
                "id=" + id +
                ", idDetail=" + idDetail +
                ", year=" + year +
                ", month=" + month +
                '}';
    }
}
