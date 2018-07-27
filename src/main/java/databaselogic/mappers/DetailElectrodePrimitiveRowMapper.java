package databaselogic.mappers;

import entities.DetailElectrodePrimitive;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailElectrodePrimitiveRowMapper implements RowMapper<DetailElectrodePrimitive> {
//Integer id, Integer idDetail, String electrodeType, Double count
    @Override
    public DetailElectrodePrimitive mapRow(ResultSet resultSet, int i) throws SQLException {
        return new DetailElectrodePrimitive(
                resultSet.getInt("id"),
                resultSet.getInt("idDetail"),
                resultSet.getString("electrodeType"),
                resultSet.getDouble("count"),
                new BigDecimal(resultSet.getString("cost"))
        );
    }
}
