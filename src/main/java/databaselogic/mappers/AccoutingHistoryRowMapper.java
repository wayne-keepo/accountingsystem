package databaselogic.mappers;

import domain.Day;
import entities.AccoutingHistory;
import entities.Detail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AccoutingHistoryRowMapper implements RowMapper<AccoutingHistory> {
    private List<Day> extractDays(ResultSet resultSet) throws SQLException {
        Day d1 = new Day(1,resultSet.getDouble("d1"));
        Day d2 = new Day(2,resultSet.getDouble("d2"));
        Day d3 = new Day(3,resultSet.getDouble("d3"));
        Day d4 = new Day(4,resultSet.getDouble("d4"));
        Day d5 = new Day(5,resultSet.getDouble("d5"));
        Day d6 = new Day(6,resultSet.getDouble("d6"));
        Day d7 = new Day(7,resultSet.getDouble("d7"));
        Day d8 = new Day(8,resultSet.getDouble("d8"));
        Day d9 = new Day(9,resultSet.getDouble("d9"));
        Day d10 = new Day(10,resultSet.getDouble("d10"));
        Day d11 = new Day(11,resultSet.getDouble("d11"));
        Day d12 = new Day(12,resultSet.getDouble("d12"));
        Day d13 = new Day(13,resultSet.getDouble("d13"));
        Day d14 = new Day(14,resultSet.getDouble("d14"));
        Day d15 = new Day(15,resultSet.getDouble("d15"));
        Day d16 = new Day(16,resultSet.getDouble("d16"));
        Day d17 = new Day(17,resultSet.getDouble("d17"));
        Day d18 = new Day(18,resultSet.getDouble("d18"));
        Day d19 = new Day(19,resultSet.getDouble("d19"));
        Day d20 = new Day(20,resultSet.getDouble("d20"));
        Day d21 = new Day(21,resultSet.getDouble("d21"));
        Day d22 = new Day(22,resultSet.getDouble("d22"));
        Day d23 = new Day(23,resultSet.getDouble("d23"));
        Day d24 = new Day(24,resultSet.getDouble("d24"));
        Day d25 = new Day(25,resultSet.getDouble("d25"));
        Day d26 = new Day(26,resultSet.getDouble("d26"));
        Day d27 = new Day(27,resultSet.getDouble("d27"));
        Day d28 = new Day(28,resultSet.getDouble("d28"));
        Day d29 = new Day(29,resultSet.getDouble("d29"));
        Day d30 = new Day(30,resultSet.getDouble("d30"));
        Day d31 = new Day(31,resultSet.getDouble("d31"));

        return new LinkedList<>(Arrays.asList(d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16,d17,d18,d19,d20,d21,d22,d23,d24,d25,d26,d27,d28,d29,d30,d31));
    }

    @Override
    public AccoutingHistory mapRow(ResultSet resultSet, int i) throws SQLException {
        List<Day> days = extractDays(resultSet);
        Year year = Year.of(resultSet.getInt("year"));
        Month month = Month.of(resultSet.getInt("month"));
        int acc = resultSet.getInt("acc");
        int idDetail = resultSet.getInt("idDetail");
        AccoutingHistory ah = new AccoutingHistory(idDetail,year,month,acc,days);

        ah.setId(resultSet.getInt("id"));

        return ah;
    }
}
