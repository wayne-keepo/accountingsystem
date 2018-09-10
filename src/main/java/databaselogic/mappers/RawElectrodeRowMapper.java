package databaselogic.mappers;

import entities.RawElectrode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RawElectrodeRowMapper implements RowMapper<RawElectrode> {
    @Override
    public RawElectrode mapRow(ResultSet resultSet, int i) throws SQLException {
        RawElectrode rw = new RawElectrode();
        rw.setId(resultSet.getInt("id"));
        rw.setCount(resultSet.getInt("count"));
        rw.setType(resultSet.getString("type"));
        return rw;
    }
}
