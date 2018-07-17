package entities;

import java.time.Month;
import java.time.Year;

public class PrimitivityBalance {

    private int id = 0;
    private int idDetail;
    private Year year;
    private Month month;
    private double incoming;
    private double outcoming;
    private double balanceAtBeginningYear;
    private double balanceAtEndOfYear;
    public PrimitivityBalance(int idDetail,Year year,Month month){
        this.idDetail = idDetail;
        this.year = year;
        this.month = month;
        this.incoming = 0;
        this.outcoming = 0;
        this.balanceAtBeginningYear = 0;
        this.balanceAtEndOfYear = 0;
    }
    public PrimitivityBalance(int idDetail, Year year, Month month, double incoming, double outcoming, double balanceAtBeginningYear, double balanceAtEndOfYear) {
        this.idDetail = idDetail;
        this.year = year;
        this.month = month;
        this.incoming = incoming;
        this.outcoming = outcoming;
        this.balanceAtBeginningYear = balanceAtBeginningYear;
        this.balanceAtEndOfYear = balanceAtEndOfYear;
    }

    public PrimitivityBalance(int id, int idDetail, Year year, Month month, double incoming, double outcoming, double balanceAtBeginningYear, double balanceAtEndOfYear) {
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

    public double getIncoming() {
        return incoming;
    }

    public void setIncoming(double incoming) {
        this.incoming = incoming;
    }

    public double getOutcoming() {
        return outcoming;
    }

    public void setOutcoming(double outcoming) {
        this.outcoming = outcoming;
    }

    public double getBalanceAtBeginningYear() {
        return balanceAtBeginningYear;
    }

    public void setBalanceAtBeginningYear(double balanceAtBeginningYear) {
        this.balanceAtBeginningYear = balanceAtBeginningYear;
    }

    public double getBalanceAtEndOfYear() {
        return balanceAtEndOfYear;
    }

    public void setBalanceAtEndOfYear(double balanceAtEndOfYear) {
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
