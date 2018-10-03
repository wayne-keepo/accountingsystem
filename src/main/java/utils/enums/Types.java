package utils.enums;

public enum Types {
    ESMG("ЕСМГ", "ESMG"),
    ESMG_M("ЕСМГ-М", "ESMG-M");

    private String ruValue;
    private String engValue;

    Types(String ruValue, String engValue) {
        this.ruValue = ruValue;
        this.engValue = engValue;
    }

    public String ru() {
        return ruValue;
    }

    public String eng() {
        return engValue;
    }
}
