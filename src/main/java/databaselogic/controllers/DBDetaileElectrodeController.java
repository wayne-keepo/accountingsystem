package databaselogic.controllers;

import databaselogic.Connector;
import databaselogic.interfaces.DBOperations;
import databaselogic.mappers.DetailElectrodePrimitiveRowMapper;
import domain.DetailElectrodeDataUpdate;
import entities.DetailElectrodePrimitive;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import projectConstants.DBConstants;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public void batchDataUpdate(List<DetailElectrodeDataUpdate> updMap) {
//        "UPDATE ElectrodeDetail SET count = ?, cost = ? WHERE idPDetail = ? AND electrodeType = ?";
        template.batchUpdate(
                DBConstants.UPDATE_ELECTRODE_DETAIL_BY_DETAIL_AND_ELECTRODE_TYPE,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        DetailElectrodeDataUpdate dedu = updMap.get(i);
                        ps.setDouble(1,dedu.getCount());
                        ps.setBigDecimal(2,dedu.getCost());
                        ps.setInt(3,dedu.getIdDetail());
                        ps.setString(4,dedu.getType());
                    }

                    @Override
                    public int getBatchSize() {
                        return updMap.size();
                    }
                }
        );
    }
}
