package model.projectConstants;

import model.enums.Paths;

public class DBConstants {
    public static final String JDBC= "jdbc:";
    public static final String SQLITE_DRIVER = "sqlite:";
    public static final String DB_PATH = Paths.C.get()+Paths.ACCOUNTING_SYSTEM.get()+Paths.DB_DONT_TOUCH_IT.get()+Paths.ACCOUNTING_SYSTEM_DB.get();
    public static final String DB_URL = JDBC+SQLITE_DRIVER+DB_PATH;


}
