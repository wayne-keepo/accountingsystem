package projectConstants;

import utils.enums.Paths;

public class DBConstants {
    public static final String JDBC= "jdbc:";
    public static final String SQLITE_DRIVER = "sqlite:";
    public static final String DB_PATH = Paths.C.get()+Paths.ACCOUNTING_SYSTEM.get()+Paths.DB_DONT_TOUCH_IT.get()+Paths.ACCOUNTING_SYSTEM_DB.get();
    public static final String DB_URL = JDBC+SQLITE_DRIVER+DB_PATH;

    //table detail(id,title,count,cost,descriptors)
    public static final String SINGLE_INSERT_DETAIL = "INSERT INTO DETAIL(TITLE,COUNT,COST,DESCRIPTORS) VALUES(?,?,?,?)";
    public static final String SINGLE_UPDATE_DETAIL = "UPDATE DETAIL SET TITLE = ? , COUNT = ?,COST = ?,DESCRIPTORS = ? WHERE ID = ?";
    public static final String SINGLE_SELECT_DETAIL_BY_ID = "SELECT * FROM DETAIL WHERE ID = ?";
    public static final String SINGLE_DELETE_DETAIL = "DELETE FROM DETAIL WHERE ID = ?";
    public static final String SINGLE_SELECT_DETAIL_BY_TITLE = "SELECT * FROM DETAIL WHERE TITLE = ?";
    public static final String SELEC_DETAILS_BY_IDS = "SELECT * FROM DETAIL WHERE ID in(?)";
    public static final String SELECT_ALL_DETAIL = "SELECT * FROM DETAIL";

    //table balance(id,IDdetail,year,month,incoming,outcoming,bAtBeginingYear,bAtEndOfYear)
    public static final String SINGLE_INSERT_BALANCE =
            "INSERT INTO BALANCE(IDdetail,year,month,incoming,outcoming,bAtBeginingYear,bAtEndOfYear) VALUES(?,?,?,?,?,?,?)";
    public static final String SINGLE_UPDATE_BALANCE = "UPDATE BALANCE SET " +
                                                        "incoming = ?, outcoming = ?, bAtBeginingYear = ?, bAtEndOfYear=?" +
                                                        "WHERE IDdetail = ? AND month = ?";
    public static final String SINGLE_DELETE_BALANCE = "DELETE FROM BALANCE WHERE IDdetail = ?";
    public static final String SELECT_ALL_BALANCE_BY_DETAIL = "SELECT * FROM BALANCE WHERE IDdetail = ?";
    public static final String SELECT_ALL_BALANCE = "SELECT * FROM BALANCE";

    // ElectrodeDetail(idDetail,electrodeType,count)
    public static final String INSERT_ELECTRODE_DETAIL = "INSERT INTO ElectrodeDetail(idDetail,electrodeType,count,cost) VALUES(?,?,?,?)";
    public static final String UPDATE_ELECTRODE_DETAIL_BY_DETAIL_AND_ELECTRODE_TYPE = "UPDATE ElectrodeDetail SET count = ?, cost = ? WHERE idDetail = ? AND electrodeType = ?";
    public static final String DELETE_ELECTRODE_DETAIL_BY_ID = "DELETE FROM ElectrodeDetail WHERE id = ?";
    public static final String DELETE_DETAIL_ELECTRODE_BY_DETAIL_ID_AND_ELECTRODE_TYPE = "DELETE FROM ElectrodeDetail WHERE idDetail = ? and electrodeType = ?";

    public static final String SELECT_ALL_ELECTRODE_DETAIL = "SELECT * FROM ElectrodeDetail";
    public static final String SELECT_DETAIL_ELECTRODE_BY_DETAIL_ID_AND_TYPE = "SELECT * FROM ElectrodeDetail WHERE idDetail = ? AND electrodeType = ?";
    public static final String SELECT_DETAIL_ELECTRODE_BY_TYPE = "SELECT * FROM ElectrodeDetail WHERE electrodeType = ?";
    public static final String SELECT_DETAIL_ELECTRODE_BY_ID = "SELECT * FROM ElectrodeDetail WHERE id = ?";

    // Summary id electrodeNumber, type produceDate Customer consumeDate Note
    public static final String SELECT_SUMMARY_BY_ID = "SELECT * FROM Summary WHERE id = ?";
    public static final String INSERT_SUMMARY = "INSERT INTO Summary(electrodeNumber,type,produceDate,Customer,consumeDate,Note) VALUES(?,?,date(?),?,date(?),?)";
    public static final String UPDATE_SUMMARY = "UPDATE Summary SET produceDate = ?, Customer = ?, consumeDate= ?, Note = ? WHERE id = ?";
    public static final String DELETE_SUMMARY_BY_ID = "DELETE FROM Summary WHERE id = ? ";
    public static final String SELECT_ALL_SUMMARY = "SELECT * FROM Summary";

    // Electrode id number type
    public static final String INSERT_ELECTRODE = "INSERT INTO Electrode(number,type) VALUES(?,?)";
    public static final String DELETE_ELECTRODE_BY_ID = "DELETE FROM Electrode WHERE id = ?";
    public static final String DELETE_ELECTRODE_BY_NUMBER = "DELETE FROM Electrode WHERE number = ?";
    public static final String SELECT_ELECTRODE_BY_NUMBER = "SELECT * FROM Electrode WHERE number = ?";
    public static final String SELECT_ELECTRODS_BY_TYPE = "SELECT * FROM Electrode WHERE type = ?";
    public static final String SELECT_ALL_ELECTRODS = "SELECT * FROM Electrode";
    public static final String SELECT_ELECTRODS_BY_NUMBERS = "SELECT * FROM Electrode where number in";

    //Accounting History
    public static final String INSERT_ACCOUNTING_HISTORY =
            "INSERT INTO AccountingHistory(idDetail,year,month,acc,d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16,d17,d18,d19,d20,d21,d22,d23,d24,d25,d26,d27,d28,d29,d30,d31)" +
                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String INSERT_ACCOUNTING_HISTORY_START_INITIALIZE_FOR_NEW_BALANCE_HISTORY ="INSERT INTO AccountingHistory(idDetail,month,acc) VALUES(?,?,?)";
    // see on code, think that it's created on AccountingHistoryService
    public static final String UPDATE_ACCOUNTING_HISTORY ="UPDATE AccountingHistory SET " +
        "d1=?,d2=?,d3=?,d4=?,d5=?,d6=?,d7=?,d8=?,d9=?,d10=?,d11=?,d12=?,d13=?,d14=?,d15=?,d16=?,d17=?,d18=?,d19=?,d20=?,d21=?,d22=?,d23=?,d24=?,d25=?,d26=?,d27=?,d28=?,d29=?,d30=?,d31=?" +
        "WHERE id = ? and year = ? and month = ? and acc = ?";
//    public static final String UPDATE_DAY_ACC_HIS = "UPDATE AccountingHistory SET d? = ? WHERE year = ? and month = ? and acc = ? and idDetail = ?";
    public static final String SELECT_ACCOUNTING_HISTORY ="SELECT * FROM AccountingHistory";
    public static final String SELECT_ACCOUNTING_HISTORY_BY_DETAIL_ID ="SELECT * FROM AccountingHistory WHERE idDetail = ?";
//    public static final String SELECT_DAY_FROM_ACC_HIST = "SELECT d? FROM AccountingHistory WHERE year = ? and month = ? and acc = ? and idDetail = ?";
    public static final String DELETE_ACCOUNTING_HISTORY = "DELETE TABLE AccountingHistory WHERE id = ?";

    //RawElectrode( id ,type , count)
    public static final String CREATE_RAW_ELECTRODE = "INSERT INTO RawElectrode(type,count) VALUES(?,?)";
    public static final String GET_RAW_ELECTRODE_BY_ID = "SELECT * FROM RawElectrode where id = ?"; // changed
    public static final String UPDATE_RAW_ELECTRODE_COUNT = "UPDATE RawElectrode SET count = ? WHERE id = ?";
    public static final String GET_RAW_ELECTRODE_BY_TYPE = "SELECT * FROM RawElectrode WHERE type = ?";
    public static final String GET_ALL_RAW_ELECTRODES = "SELECT * FROM RawElectrode";
}
