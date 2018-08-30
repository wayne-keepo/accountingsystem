package databaselogic.controllers;

import databaselogic.Connector;
import databaselogic.mappers.RawElectrodeRowMapper;
import entities.RawElectrode;
import org.springframework.jdbc.core.JdbcTemplate;
import projectConstants.DBConstants;

public class DBRawElectrodeController {
    public static JdbcTemplate template = Connector.getTemplate();

    public void initRawElectrodeValue(){
        template.queryForObject(DBConstants.GET_RAW_ELECTRODE,new RawElectrodeRowMapper());
    }

    public void updateCount(){
        template.update(
                DBConstants.UPDATE_RAW_ELECTRODE_COUNT,
                RawElectrode.getInstance().getCount());
    }
}
