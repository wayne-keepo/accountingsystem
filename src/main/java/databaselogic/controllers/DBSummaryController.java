package databaselogic.controllers;

import databaselogic.Connector;
import databaselogic.interfaces.DBOperations;
import databaselogic.mappers.SummaryRowMapper;
import domain.Electrod;
import entities.Summary;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import projectConstants.CustomConstants;
import projectConstants.DBConstants;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBSummaryController implements DBOperations<Summary> {
    private JdbcTemplate template;

    public DBSummaryController() {
        this.template = Connector.getTemplate();
    }

    //idElectrode,produceDate,Customer,consumeDate,Note
    @Override
    public boolean save(Summary o) {
        int flag = template.update(
                DBConstants.INSERT_SUMMARY,
                o.getIdElectrode(), o.getProduceDate().toString(), o.getCustomer(), o.getConsumeDate().toString(), o.getNote()
        );
        return flag > 0;
    }

    @Override
    public boolean delete(int idElectrode) {
        int flag = template.update(
                DBConstants.DELETE_SUMMARY_BY_ID_ELECTRODE,
                idElectrode
        );
        return flag > 0;
    }

    @Override
    public List<Summary> getAll() {
        return template.query(
                DBConstants.SELECT_ALL_SUMMARY,
                new SummaryRowMapper()
        );
    }

    @Override
    public Summary get(int idElectrode) {
        return template.queryForObject(
                DBConstants.SELECT_SUMMARY_BY_ELECTRODE_ID,
                new SummaryRowMapper(),
                idElectrode
        );
    }

    @Override
    public Summary get(String title) {
        return null;
    }
//idElectrode,produceDate,Customer,consumeDate,Note
    public List<Summary> bulkInsert(List<Summary> summaries) {
        template.batchUpdate(
                DBConstants.INSERT_SUMMARY,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Summary tmp = summaries.get(i);
                        ps.setInt(1,tmp.getIdElectrode());
                        ps.setDate(2, Date.valueOf(tmp.getProduceDate().toString()));
                        ps.setString(3, tmp.getCustomer());
                        ps.setDate(4, Date.valueOf(tmp.getConsumeDate().toString()));
                        ps.setString(5, tmp.getNote());
                        System.out.println(ps.getGeneratedKeys().getInt(1)+" "+i);
                    }

                    @Override
                    public int getBatchSize() {
                        return summaries.size();
                    }
                }
        );

    return null;
    }

    public List<Summary> getSummariesByElectrods(String electrods) {
        return template.query(
                electrods,
                new SummaryRowMapper()
        );
//        return null;
    }
}
