package databaselogic.controllers;

import databaselogic.Connector;
import databaselogic.interfaces.DBOperations;
import databaselogic.mappers.SummaryRowMapper;
import entities.Summary;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import projectConstants.DBConstants;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

//electrodeNumber,type,produceDate,Customer,consumeDate,Note
public class DBSummaryController implements DBOperations<Summary> {
    private JdbcTemplate template;

    public DBSummaryController() {
        this.template = Connector.getTemplate();
    }

    @Override
    public boolean save(Summary o) {
        int flag = template.update(
                DBConstants.INSERT_SUMMARY,
                o.getElectrodeNumber(), o.getType(), o.getProduceDate().toString(), o.getCustomer(), o.getConsumeDate().toString(), o.getNote()
        );
        return flag > 0;
    }

    @Override
    public boolean delete(int id) {
        int flag = template.update(DBConstants.DELETE_SUMMARY_BY_ID, id);
        return flag>0;
    }

    @Override
    public List<Summary> getAll() {
        return template.query(
                DBConstants.SELECT_ALL_SUMMARY,
                new SummaryRowMapper()
        );
    }

    @Override
    public Summary get(int id) {
        return template.queryForObject(
                DBConstants.SELECT_SUMMARY_BY_ID,
                new SummaryRowMapper(),
                id);
    }

    @Override
    public Summary get(String title) {
        return null;
    }


    public void bulkInsert(List<Summary> summaries) {
        template.batchUpdate(
                DBConstants.INSERT_SUMMARY,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Summary tmp = summaries.get(i);
                        ps.setString(1, tmp.getElectrodeNumber());
                        ps.setString(2, tmp.getType());
                        ps.setString(3, tmp.getProduceDate().toString());
                        ps.setString(4, tmp.getCustomer());
                        ps.setString(5, tmp.getConsumeDate().toString());
                        ps.setString(6, tmp.getNote());

                    }

                    @Override
                    public int getBatchSize() {
                        return summaries.size();
                    }
                }
        );
    }

    public void update(Summary summary) {
//        UPDATE Summary SET produceDate = ?, Customer = ?, consumeDate= ?, Note = ? WHERE id = ?
        template.update(
                DBConstants.UPDATE_SUMMARY,
                summary.getProduceDate(),summary.getCustomer(),summary.getConsumeDate(),summary.getNote(),summary.getIdSummary());
    }
}
