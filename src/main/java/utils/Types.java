package utils;

public enum Types {
    ESMG("ЕСМГ"),
    ESMG_M("ЕСМГ-М");

    private  String value;
    Types(String value){this.value = value;}
    public String value(){
        return value;
    }
}
