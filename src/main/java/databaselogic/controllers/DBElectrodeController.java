package databaselogic.controllers;

import databaselogic.Connector;
import databaselogic.interfaces.DBOperations;
import databaselogic.mappers.ElectrodeMapper;
import domain.Electrod;
import org.springframework.jdbc.core.JdbcTemplate;
import projectConstants.DBConstants;

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
