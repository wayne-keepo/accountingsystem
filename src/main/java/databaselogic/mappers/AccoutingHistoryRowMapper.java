package databaselogic.mappers;

import entities.AccoutingHistory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccoutingHistoryRowMapper implements RowMapper<AccoutingHistory> {
    @Override
    public AccoutingHistory mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
