package entities;

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

    public void changeCountAndType(int count, String type){
        this.count = count;
        this.type = type;
    }
}
