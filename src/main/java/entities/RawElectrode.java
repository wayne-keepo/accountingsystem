package entities;

import java.util.Objects;

public class RawElectrode {
    private Integer id ;
    private int count;
    private String type;

    public RawElectrode() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RawElectrode that = (RawElectrode) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type);
    }

    public void changeCountAndType(int count, String type){
        this.count = count;
        this.type = type;
    }
}
