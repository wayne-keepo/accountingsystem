package databaselogic.controllers;

import databaselogic.Connector;
import databaselogic.interfaces.DBOperations;
import databaselogic.mappers.PrimitivityBalanceRowMapper;
import domain.Balance;
import entities.PrimitivityBalance;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import projectConstants.DBConstants;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBBalanceController implements DBOperations<PrimitivityBalance> {
    private JdbcTemplate template;

    public DBBalanceController() {
        this.template = Connector.getTemplate();
    }
// (iddetail,year,month,incoming,outcoming,bAtBeginingYear,bAtEndOfYear) VALUES(?,?,?,?,?,0,0)
    public boolean updateAll(List<PrimitivityBalance> pBalances){
        //incoming = ?, outcoming = ?, bAtBeginingYear = ?, bAtEndOfYear=? where IDdetail = ? AND month = ?
        int[] updateCounts = template.batchUpdate(DBConstants.SINGLE_UPDATE_BALANCE, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setDouble(1, pBalances.get(i).getIncoming());
                ps.setDouble(2, pBalances.get(i).getOutcoming());
                ps.setDouble(3, pBalances.get(i).getBalanceAtBeginningYear());
                ps.setDouble(4, pBalances.get(i).getBalanceAtEndOfYear());
                ps.setInt(5, pBalances.get(i).getIdDetail());
                ps.setString(6, pBalances.get(i).getMonth().toString());
            }
            @Override
            public int getBatchSize() {
                return pBalances.size();
            }
        });
        if (updateCounts.length>0)
            return true;
        return false;
    }
    public boolean saveAll(List<PrimitivityBalance> pBalances) {
        int[] updateCounts = template.batchUpdate(DBConstants.SINGLE_INSERT_BALANCE, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, pBalances.get(i).getIdDetail());
                ps.setString(2, pBalances.get(i).getYear().toString());
                ps.setString(3, pBalances.get(i).getMonth().toString());
                ps.setDouble(4, pBalances.get(i).getIncoming());
                ps.setDouble(5, pBalances.get(i).getOutcoming());
                ps.setDouble(6, pBalances.get(i).getBalanceAtBeginningYear());
            }
            @Override
            public int getBatchSize() {
                return pBalances.size();
            }
        });
        if (updateCounts.length>0)
            return true;
        return false;
    }

    @Override
    public boolean save(PrimitivityBalance o) {
        int flag = template.update(
                DBConstants.SINGLE_INSERT_BALANCE,
                o.getIdDetail(), o.getYear().toString(), o.getMonth().toString(), o.getIncoming(), o.getOutcoming(), o.getBalanceAtBeginningYear()
        );
        return flag > 0;
    }

    //this method is useless for this type
    @Override
    public List<PrimitivityBalance> getAll() {
        List<PrimitivityBalance>
                pBalances = template.query(
                DBConstants.SELECT_ALL_BALANCE,
                new PrimitivityBalanceRowMapper()
        );
        if (!pBalances.isEmpty())
            return pBalances;
        return null;
    }

    @Override
    public PrimitivityBalance get(int id) {
//        PrimitivityBalance
//                pBalance = template.query(
//                        DBConstants.SELEC
//        );
        return null;
    }

    @Override
    public PrimitivityBalance get(String title) {
        return null;
    }

    // id = idDetail
    @Override
    public boolean delete(int idDetail) {
        int flag = template.update(
                DBConstants.SINGLE_DELETE_BALANCE,
                idDetail
        );
        return flag > 0;
    }

    public List<PrimitivityBalance> getAllByDetailId(int idDetail) {
        List<PrimitivityBalance>
                pBalances = template.query(
                DBConstants.SELECT_ALL_BALANCE_BY_DETAIL,
                new PrimitivityBalanceRowMapper(),
                idDetail
        );
        if (!pBalances.isEmpty())
            return pBalances;
        return null;
    }


}
