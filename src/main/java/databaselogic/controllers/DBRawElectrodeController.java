package databaselogic.controllers;

import databaselogic.Connector;
import databaselogic.mappers.RawElectrodeRowMapper;
import entities.RawElectrode;
import org.springframework.jdbc.core.JdbcTemplate;
import projectConstants.DBConstants;

import java.util.ArrayList;
import java.util.List;

public class DBRawElectrodeController {
    public static JdbcTemplate template = Connector.getTemplate();

    public RawElectrode getByType(String type) {

        List<RawElectrode> ral = template.query(
                DBConstants.GET_RAW_ELECTRODE_BY_TYPE,
                new RawElectrodeRowMapper(),
                type
        );
        if (ral.isEmpty())
            return null;
        return ral.get(0);
    }

    public void updateCount(int id, int count) {
        template.update(
                DBConstants.UPDATE_RAW_ELECTRODE_COUNT,
                count, id);
    }

    public RawElectrode initRawElectrode(String type, int count) {
        template.update(
                DBConstants.CREATE_RAW_ELECTRODE,
                type,count);

        return getByType(type);
    }

    public List<RawElectrode> getAll() {
        return template.query(
                DBConstants.GET_ALL_RAW_ELECTRODES,
                new RawElectrodeRowMapper()
        );
    }
}
