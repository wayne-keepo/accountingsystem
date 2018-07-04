package databaselogic.mappers;

import entities.PrimitiveElectrodeDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PrimitiveElectrodeDetailRowMapper implements RowMapper<PrimitiveElectrodeDetail> {
    @Override
    public PrimitiveElectrodeDetail mapRow(ResultSet resultSet, int i) throws SQLException {
        return new PrimitiveElectrodeDetail(
                resultSet.getInt("idElectrod"),
                resultSet.getInt("idDetail"),
                resultSet.getInt("count")
        );
    }
}
