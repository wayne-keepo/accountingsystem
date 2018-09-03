package domain;

import java.time.Month;

public class RefreshBalanceData {
    public Month month;
    public int idDetail;
    public double value;

    public RefreshBalanceData(Month month, int idDetail, double value) {
        this.month = month;
        this.idDetail = idDetail;
        this.value = value;
    }

}
