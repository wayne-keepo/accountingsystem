package projectConstants;

import java.time.format.DateTimeFormatter;

public class CustomConstants {
    public static final String DATE_FORMAT_FOR_DB = "yyyy-mm-dd hh:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_TIME_FORMATTER_FOR_DB = DateTimeFormatter.ofPattern(CustomConstants.DATE_FORMAT_FOR_DB);
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(CustomConstants.DATE_FORMAT);

    public static final String ESMG = "ESMG";
    public static final String ESMG_M = "ESMG-M";

    public static final String INCOMING = "Приход";
    public static final String OUTCOMING = "Расход";

    public static final int INCOMING_IFlag = 1;
    public static final int OUTCOMING_IFlag = 0;

}
