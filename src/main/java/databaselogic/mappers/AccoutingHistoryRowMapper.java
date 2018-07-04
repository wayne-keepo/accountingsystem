package databaselogic.mappers;

import domain.Day;
import entities.AccoutingHistory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AccoutingHistoryRowMapper implements RowMapper<AccoutingHistory> {
    @Override
    public AccoutingHistory mapRow(ResultSet resultSet, int i) throws SQLException {
        Day d1 = new Day(1,resultSet.getString("acc"),resultSet.getInt("d1"));
        Day d2 = new Day(2,resultSet.getString("acc"),resultSet.getInt("d2"));
        Day d3 = new Day(3,resultSet.getString("acc"),resultSet.getInt("d3"));
        Day d4 = new Day(4,resultSet.getString("acc"),resultSet.getInt("d4"));
        Day d5 = new Day(5,resultSet.getString("acc"),resultSet.getInt("d5"));
        Day d6 = new Day(6,resultSet.getString("acc"),resultSet.getInt("d6"));
        Day d7 = new Day(7,resultSet.getString("acc"),resultSet.getInt("d7"));
        Day d8 = new Day(8,resultSet.getString("acc"),resultSet.getInt("d8"));
        Day d9 = new Day(9,resultSet.getString("acc"),resultSet.getInt("d9"));
        Day d10 = new Day(10,resultSet.getString("acc"),resultSet.getInt("d10"));
        Day d11 = new Day(11,resultSet.getString("acc"),resultSet.getInt("d11"));
        Day d12 = new Day(12,resultSet.getString("acc"),resultSet.getInt("d12"));
        Day d13 = new Day(13,resultSet.getString("acc"),resultSet.getInt("d13"));
        Day d14 = new Day(14,resultSet.getString("acc"),resultSet.getInt("d14"));
        Day d15 = new Day(15,resultSet.getString("acc"),resultSet.getInt("d15"));
        Day d16 = new Day(16,resultSet.getString("acc"),resultSet.getInt("d16"));
        Day d17 = new Day(17,resultSet.getString("acc"),resultSet.getInt("d17"));
        Day d18 = new Day(18,resultSet.getString("acc"),resultSet.getInt("d18"));
        Day d19 = new Day(19,resultSet.getString("acc"),resultSet.getInt("d19"));
        Day d20 = new Day(20,resultSet.getString("acc"),resultSet.getInt("d20"));
        Day d21 = new Day(21,resultSet.getString("acc"),resultSet.getInt("d21"));
        Day d22 = new Day(22,resultSet.getString("acc"),resultSet.getInt("d22"));
        Day d23 = new Day(23,resultSet.getString("acc"),resultSet.getInt("d23"));
        Day d24 = new Day(24,resultSet.getString("acc"),resultSet.getInt("d24"));
        Day d25 = new Day(25,resultSet.getString("acc"),resultSet.getInt("d25"));
        Day d26 = new Day(26,resultSet.getString("acc"),resultSet.getInt("d26"));
        Day d27 = new Day(27,resultSet.getString("acc"),resultSet.getInt("d27"));
        Day d28 = new Day(28,resultSet.getString("acc"),resultSet.getInt("d28"));
        Day d29 = new Day(29,resultSet.getString("acc"),resultSet.getInt("d29"));
        Day d30 = new Day(30,resultSet.getString("acc"),resultSet.getInt("d30"));
        Day d31 = new Day(31,resultSet.getString("acc"),resultSet.getInt("d31"));

        List<Day> days = new LinkedList<>(Arrays.asList(d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16,d17,d18,d19,d20,d21,d22,d23,d24,d25,d26,d27,d28,d29,d30,d31));

        AccoutingHistory ah = new AccoutingHistory(null,days);

        ah.setId(resultSet.getInt("id"));






        return ah;
    }
}
