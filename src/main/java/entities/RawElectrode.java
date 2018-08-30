package entities;

public class RawElectrode {
    private int id = 1;
    private int count;
    private static RawElectrode instance;

    private RawElectrode() {}

    public static RawElectrode getInstance() {
        if (instance==null)
            instance = new RawElectrode();
        return instance;
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
