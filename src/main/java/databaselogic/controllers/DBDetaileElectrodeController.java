package databaselogic.controllers;

import databaselogic.Connector;
import databaselogic.interfaces.DBOperations;
import databaselogic.mappers.DetailElectrodePrimitiveRowMapper;
import entities.DetailElectrodePrimitive;
import org.springframework.jdbc.core.JdbcTemplate;
import projectConstants.DBConstants;

import java.util.List;
import java.util.Map;

public class DBDetaileElectrodeController implements DBOperations<DetailElectrodePrimitive> {
    private JdbcTemplate template;

    public DBDetaileElectrodeController() {
        this.template = Connector.getTemplate();
    }

    public List<DetailElectrodePrimitive> getAllByType(String type) {
        return template.query(
                DBConstants.SELECT_DETAIL_ELECTRODE_BY_TYPE,
                new DetailElectrodePrimitiveRowMapper(),
                type
        );
    }

    @Override
    public boolean save(DetailElectrodePrimitive o) {
        template.update(DBConstants.INSERT_ELECTRODE_DETAIL,
                o.getIdDetail(),o.getElectrodeType(),o.getCount());
        return true;
    }

    @Override
    public List<DetailElectrodePrimitive> getAll() {
        return template.query(DBConstants.SELECT_ALL_ELECTRODE_DETAIL, new DetailElectrodePrimitiveRowMapper());
    }

    @Override
    public DetailElectrodePrimitive get(int id) {
        return null;
    }

    @Override
    public DetailElectrodePrimitive get(String title) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
