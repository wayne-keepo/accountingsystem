package databaselogic;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import projectConstants.DBConstants;

public class Connector {
    private static JdbcTemplate template =null;
    private static DriverManagerDataSource dataSource ;

    static  {
        dataSource = new DriverManagerDataSource(DBConstants.DB_URL);
        dataSource.setDriverClassName("org.sqlite.JDBC");
    }


    public static JdbcTemplate getTemplate(){
        if (template!=null)
            return template;
        else
            return template=new JdbcTemplate(dataSource);
    }
}
