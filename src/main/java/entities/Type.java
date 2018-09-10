package entities;

public class Type {
    private Integer id;
    private String type;

    public Type() {
    }

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

    public boolean isType(String type){
        return this.type.equalsIgnoreCase(type);
    }
}
