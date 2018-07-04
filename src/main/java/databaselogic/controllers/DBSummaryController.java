package databaselogic.controllers;

import databaselogic.Connector;
import databaselogic.interfaces.DBOperations;
import databaselogic.mappers.SummaryRowMapper;
import entities.Summary;
import org.springframework.jdbc.core.JdbcTemplate;
import projectConstants.DBConstants;

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
                o.getIdElectrode(),o.getProduceDate().toString(),o.getCustomer(),o.getConsumeDate().toString(),o.getNote()
        );
        return flag>0;
    }

    @Override
    public boolean delete(int idElectrode) {
        int flag = template.update(
                DBConstants.DELETE_SUMMARY_BY_ID_ELECTRODE,
                idElectrode
        );
        return flag>0;
    }

    @Override
    public List<Summary> getAll() {
        List<Summary>
                summaries = template.query(
                        DBConstants.SELECT_ALL_SUMMARY,
                        new SummaryRowMapper()
        );
        if (!summaries.isEmpty())
            return summaries;
        return null;
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
}
