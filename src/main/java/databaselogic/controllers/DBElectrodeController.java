package databaselogic.controllers;

import databaselogic.Connector;
import databaselogic.interfaces.DBOperations;
import databaselogic.mappers.ElectrodeMapper;
import domain.Electrod;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import projectConstants.DBConstants;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBElectrodeController implements DBOperations<Electrod> {
    private JdbcTemplate template;

    public DBElectrodeController() {
        this.template = Connector.getTemplate();
    }
    // Electrode id number type
    @Override
    public boolean save(Electrod o) {
        int flag = template.update(
                DBConstants.INSERT_ELECTRODE,
                o.getElectrodNumber(),o.getType()
        );
        return flag>0;
    }

    public List<Electrod> getByNumbers(String sql){
        return template.query(
                sql,
                new ElectrodeMapper()
        );
    }

    public void bulkSave(List<Electrod> electrods){
        template.batchUpdate(
                DBConstants.INSERT_ELECTRODE,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1,electrods.get(i).getElectrodNumber());
                        ps.setString(2,electrods.get(i).getType());
                    }

                    @Override
                    public int getBatchSize() {
                        return electrods.size();
                    }
                }
        );

    }

    @Override
    public List<Electrod> getAll() {
        return template.query(
                DBConstants.SELECT_ALL_ELECTRODS,
                new ElectrodeMapper()
        );
    }

    @Override
    public Electrod get(int id) {
        return null;
    }

    public List<Electrod> getByType(String type){
        return template.query(
                DBConstants.SELECT_ELECTRODS_BY_TYPE,
                new ElectrodeMapper(),
                type
        );
    }

    @Override
    public Electrod get(String number) {
        return template.queryForObject(
                DBConstants.SELECT_ELECTRODE_BY_NUMBER,
                new ElectrodeMapper(),
                number
        );
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
