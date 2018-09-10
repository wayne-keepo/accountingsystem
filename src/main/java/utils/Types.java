package utils;

public enum Types {
    ESMG("ЕСМГ"),
    ESMGM("ЕСМГМ");

    private  String value;
    Types(String value){this.value = value;}
    public String value(){
        return value;
    }
}
