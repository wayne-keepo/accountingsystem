package databaselogic.mappers;

import entities.PrimitivityBalance;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.time.Year;

public class PrimitivityBalanceRowMapper implements RowMapper<PrimitivityBalance> {
// (iddetail,year,month,incoming,outcoming,bAtBeginingYear,bAtEndOfYear) VALUES(?,?,?,?,?,0,0)
    @Override
    public PrimitivityBalance mapRow(ResultSet rs, int i) throws SQLException {
        return new PrimitivityBalance(
                rs.getInt("id"),
                rs.getInt("idDetail"),
                Year.parse(rs.getString("year")),
                Month.valueOf(rs.getString("month")),
                rs.getDouble("incoming"),
                rs.getDouble("outcoming"),
                rs.getDouble("bAtBeginingYear"),
                rs.getDouble("bAtEndOfYear")
        );
    }
}
