package databaselogic.mappers;

import domain.Electrod;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ElectrodeMapper implements RowMapper<Electrod>{
    @Override
    public Electrod mapRow(ResultSet rs, int i) throws SQLException {
        return new Electrod(
                rs.getInt("ID"),
                rs.getString("NUMBER"),
                rs.getString("TYPE")
        );
    }
}
