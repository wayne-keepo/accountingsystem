package model.projectConstants;

import java.time.format.DateTimeFormatter;

public class CustomConstants {
    private static final String DATE_FORMAT_FOR_DB = "yyyy-mm-dd hh:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "hh:mm:ss";

    public static final DateTimeFormatter DT_FROMATTER_FOR_TIME = DateTimeFormatter.ofPattern(TIME_FORMAT);
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(CustomConstants.DATE_FORMAT);
    public static final DateTimeFormatter DATE_TIME_FORMATTER_FOR_DB = DateTimeFormatter.ofPattern(CustomConstants.DATE_FORMAT_FOR_DB);


    public static final String ESMG = "ESMG";
    public static final String ESMG_M = "ESMG-M";

    public static final String INCOMING = "Приход";
    public static final String OUTCOMING = "Расход";

    public static final int INCOMING_IFlag = 1;
    public static final int OUTCOMING_IFlag = 0;

    public static final String GREEN = "-fx-background-color: rgba(181, 255, 192, 0.42)";
    public static final String YELLOW = "-fx-background-color: rgba(254, 255, 181, 0.42)";
}
