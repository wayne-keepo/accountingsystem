package databaselogic.mappers;

import entities.Summary;
import org.springframework.jdbc.core.RowMapper;
import projectConstants.CustomConstants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SummaryRowMapper implements RowMapper<Summary> {
    @Override
    public Summary mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Summary(
                resultSet.getInt("id"),
                resultSet.getString("electrodeNumber"),
                resultSet.getString("type"),
                LocalDate.parse(resultSet.getString("produceDate"), CustomConstants.DATE_TIME_FORMATTER),
                resultSet.getString("Customer"),
                LocalDate.parse(resultSet.getString("consumeDate"), CustomConstants.DATE_TIME_FORMATTER),
                resultSet.getString("Note")
        );
    }
}
