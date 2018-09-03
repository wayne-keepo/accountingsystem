package databaselogic.mappers;

import entities.Detail;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailRowMapper implements RowMapper<Detail> {
    @Override
    public Detail mapRow(ResultSet rs, int i) throws SQLException {
        return new Detail(
                rs.getInt("ID"),
                rs.getString("TITLE"),
                rs.getDouble("COUNT"),
                rs.getString("descriptors"),
                new BigDecimal(rs.getString("COST"))
        );
    }
}
