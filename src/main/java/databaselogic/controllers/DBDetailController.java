package databaselogic.controllers;

import databaselogic.Connector;
import databaselogic.interfaces.DBOperations;
import databaselogic.mappers.DetailRowMapper;
import entities.Detail;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import projectConstants.DBConstants;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

//table detail(id,title,count,cost,descriptors)
public class DBDetailController implements DBOperations<Detail> {
    private JdbcTemplate template;

    public DBDetailController() {
        template = Connector.getTemplate();
    }

    //delete after test
    public List<Map<String,Object>> test(){
        List<Map<String,Object>> test = template.queryForList("SELECT * FROM Detail");
        return test;
    }

    @Override
    public boolean save(Detail o) {
        int flag = template.update(
                DBConstants.SINGLE_INSERT_DETAIL,
                o.getTitle(), o.getCount(), o.getCost().toString(), o.getDescriptions()
        );
        return flag > 0;
    }

    @Override
    public List<Detail> getAll() {
        List<Detail> details = template.query(
                DBConstants.SELECT_ALL_DETAIL,
                new DetailRowMapper()
        );
        if (!details.isEmpty())
            return details;
        return null;
    }

    @Override
    public Detail get(int id) {
        Detail detail = null;
        detail = template.queryForObject(
                DBConstants.SINGLE_SELECT_DETAIL_BY_ID,
                new DetailRowMapper(),
                id
        );
        return detail;
    }

    @Override
    public Detail get(String title) {
        Detail detail = null;
        detail = template.queryForObject(
                DBConstants.SINGLE_SELECT_DETAIL_BY_TITLE,
                new DetailRowMapper(),
                title
        );
        return detail;
    }

    @Override
    public boolean delete(int id) {
        int flag = template.update(
                DBConstants.SINGLE_DELETE_DETAIL,
                id
        );
        return flag > 0;
    }

    public void bulkUpdate(List<Detail> details) {
        template.batchUpdate(
                DBConstants.SINGLE_UPDATE_DETAIL,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Detail d = details.get(i);
                        ps.setString(1, d.getTitle());
                        ps.setDouble(2, d.getCount());
                        ps.setBigDecimal(3, d.getCost());
                        ps.setString(4, d.getDescriptions());
                        ps.setInt(5, d.getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return details.size();
                    }
                }
        );
    }

    public List<Detail> getDetailsByIDs(List<Integer> ids) {
        String tmp = ids.toString().replaceAll("[\\[\\]]","");
        String sql = DBConstants.SELEC_DETAILS_BY_IDS;
        sql = sql.replace("?",tmp);
        System.out.println(sql);
        return template.query(
                sql,
                new DetailRowMapper()
                );
    }
}
