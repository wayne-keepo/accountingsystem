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

    public DBAccountingHistoryController() {
        this.template = Connector.getTemplate();
    }

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
        return flag > 0;
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
        ) > 0;
    }

    public void batchInsert(String... sql) {
        template.batchUpdate(sql);
    }

    public void batchUpdate(String[] sql) {
        template.batchUpdate(sql);
    }

    @Deprecated
    @Override
    public AccoutingHistory get(String title) {
        return null;
    }

    @Override
    public List<AccoutingHistory> getAll() {
        return template.query(
                DBConstants.SELECT_ACCOUNTING_HISTORY,
                new AccoutingHistoryRowMapper()
        );
    }

    public Double getDayValue(int year, int month, int day, int acc, int detailId) {
        return template.queryForObject(
                "SELECT d"+day+" FROM AccountingHistory WHERE year = ? and month = ? and acc = ? and idDetail = ?",
                Double.class,
                year, month, acc, detailId);
    }

    public void updateHistoryForDay(int year, int month, int day, int acc, int detailId, double newValue) {
        template.update(
                "UPDATE AccountingHistory SET d"+day+" = ? WHERE year = ? and month = ? and acc = ? and idDetail = ?",
                newValue, year, month, acc, detailId
        );
    }
}
