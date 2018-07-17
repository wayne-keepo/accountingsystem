package databaselogic.controllers;

import databaselogic.Connector;
import databaselogic.interfaces.DBOperations;
import databaselogic.mappers.DetailRowMapper;
import entities.Detail;
import org.springframework.jdbc.core.JdbcTemplate;
import projectConstants.DBConstants;
import java.util.List;

//table detail(id,title,count,cost,descriptors)
public class DBDetailController implements DBOperations<Detail> {
    private JdbcTemplate template;

    public DBDetailController(){
        template = Connector.getTemplate();
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
        return flag>0;
    }
}
