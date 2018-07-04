package entities;

public class PrimitiveElectrodeDetail {
    private int idElectrode;
    private int idDetail;
    private int count;

    public PrimitiveElectrodeDetail() {
    }

    public PrimitiveElectrodeDetail(int idElectrode, int idDetail, int count) {
        this.idElectrode = idElectrode;
        this.idDetail = idDetail;
        this.count = count;
    }

    public int getIdElectrode() {
        return idElectrode;
    }

    public void setIdElectrode(int idElectrode) {
        this.idElectrode = idElectrode;
    }

    public int getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
