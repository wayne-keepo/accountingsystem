package databaselogic.controllers;

import databaselogic.Connector;
import databaselogic.interfaces.DBOperations;
import databaselogic.mappers.AccoutingHistoryRowMapper;
import domain.Day;
import entities.AccoutingHistory;
import org.springframework.jdbc.core.JdbcTemplate;
import projectConstants.DBConstants;

import java.util.List;

public class DBAccountingHistoryController implements DBOperations<AccoutingHistory> {
    private JdbcTemplate template;

    public DBAccountingHistoryController(){this.template = Connector.getTemplate();}
//    "INSERT INTO AccountingHistory(idDetail,month,acc,d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16,d17,d18,d19,d20,d21,d22,d23,d24,d25,d26,d27,d28,d29,d30,d31)" +
//            " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    @Override
    public boolean save(AccoutingHistory o) {
        List<Day> days = o.getDays();
        int flag = template.update(
                DBConstants.INSERT_ACCOUNTING_HISTORY,
                o.getDetail().getId(),
                o.getMonth().getValue(),
                o.getAcc(),
                days.get(0),
                days.get(1),
                days.get(2),
                days.get(3),
                days.get(4),
                days.get(5),
                days.get(6),
                days.get(7),
                days.get(8),
                days.get(9),
                days.get(10),
                days.get(11),
                days.get(12),
                days.get(13),
                days.get(14),
                days.get(15),
                days.get(16),
                days.get(17),
                days.get(18),
                days.get(19),
                days.get(20),
                days.get(21),
                days.get(22),
                days.get(23),
                days.get(24),
                days.get(25),
                days.get(26),
                days.get(27),
                days.get(28),
                days.get(29),
                days.get(30)

        );
        return flag>0;
    }

    @Override
    public AccoutingHistory get(int id) {
        return template.query(
                DBConstants.SELECT_ACCOUNTING_HISTORY,
                new AccoutingHistoryRowMapper(),
                id
        ).get(0);
    }

    public List<AccoutingHistory> getByDetail(int idDetail) {
        return template.query(
                DBConstants.SELECT_ACCOUNTING_HISTORY_BY_DETAIL_ID,
                new AccoutingHistoryRowMapper(),
                idDetail
        );
    }

    @Override
    public boolean delete(int id) {
        return template.update(
                DBConstants.DELETE_ACCOUNTING_HISTORY,
                id
        )>0;
    }


    @Deprecated
    @Override
    public AccoutingHistory get(String title) {
        return null;
    }

    @Deprecated
    @Override
    public List<AccoutingHistory> getAll() {
        return null;
    }

}
