package com.boryi.bots.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Generate database connection instance according to database server
 * type, ip, port, user, and password
 * 
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotDbConnection 
{
    /**
     * Get connection according to database server type, mysql, postgresql,
     * ms sql, etc.
     * 
     * @param dbServer A BotDbServerEntity instance
     * @return Connection A java.sql.Connection instance
     */
    public static Connection getConnection(BotDbServerEntity dbServer)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, SQLException
    {
        String className = null;
        String url = "jdbc:";
        switch (dbServer.serverType)
        {
            case MYSQL: // default port 3306
                className = "com.mysql.jdbc.Driver";
                url += "mysql://" + dbServer.getServer()
                        + ":" + dbServer.getServerPort()
                        + "/" + dbServer.getDbName();
                break;
            case POSTGRESQL: // default port 5432
                className = "org.postgresql.Driver";
                url = "postgresql://" + dbServer.getServer()
                        + ":" + dbServer.getServerPort()
                        + "/" + dbServer.getDbName();
                break;
            case MSSQL: // default port 1433
                className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                url = "microsoft:sqlserver://" + dbServer.getServer()
                        + ":" + dbServer.getServerPort()
                        + ";databaseName=" + dbServer.getDbName() + ";";
                break;
        }
        
        Class.forName(className).newInstance();
        
        if (dbServer.getDbUser() == null || dbServer.getDbUser().isEmpty())
        {
            return DriverManager.getConnection(url);
        }
        
        return DriverManager.getConnection(url, dbServer.getDbUser(), 
                dbServer.getDbPassword());
    }
}
