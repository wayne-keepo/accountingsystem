package utils.enums;

public enum Paths {
    COLON_SLASH(":\\"),
    SLASH("\\"),
    C("C:\\"),
    ACCOUNTING_SYSTEM("ACCOUNTING_SYSTEM\\"),
    DOCUMENTS("DOCUMENTS\\"),
    ESMG("ESMG\\"),
    ESMG_M("ESMG_M\\"),
    DB_DONT_TOUCH_IT("DB_DONT_TOUCH_IT\\"),
    ACCOUNTING_SYSTEM_DB("ACCOUNTING_SYSTEM_DB.db");

    private String value;

    Paths(String slashs){this.value =slashs;}

    public String get() {
        return value;
    }
}
