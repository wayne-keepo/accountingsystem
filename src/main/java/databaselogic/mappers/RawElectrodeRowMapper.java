package databaselogic.mappers;

import entities.RawElectrode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RawElectrodeRowMapper implements RowMapper<RawElectrode> {
    @Override
    public RawElectrode mapRow(ResultSet resultSet, int i) throws SQLException {
        RawElectrode.getInstance().setCount(resultSet.getInt("count"));
        return RawElectrode.getInstance();
    }
}
