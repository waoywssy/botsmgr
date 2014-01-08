package com.boryi.bots.base;

/**
 * Bot database server entity bundles server type, server ip, server port, 
 * database name, database user, database password of the user
 *
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotDbServerEntity 
{
    /**
     * Database server type, e.g. mysql, or postgresql, or ms sql
     */
    protected BotDbServerType serverType;
    /**
     * Database server ip address
     */
    protected String server;
    /**
     * Database server port
     */
    protected int serverPort;
    /**
     * Database name
     */
    protected String dbName;
    /**
     * User to access the database
     */
    protected String dbUser;
    /**
     * Password of the user to access the database
     */
    protected String dbPassword;
    
    
    /**
     * Constructor to initialize an instance of the BotServerEntity
     * 
     * @param serverType Database server type
     * @param server Database server ip address
     * @param serverPort Database server port
     * @param dbName Database name
     * @param dbUser User to access the database
     * @param dbPassword Password of the user to access the database
     */
    public BotDbServerEntity(BotDbServerType serverType,
            String server, int serverPort, String dbName,
            String dbUser, String dbPassword)
    {
        this.serverType = serverType;
        this.server = server;
        this.serverPort = serverPort;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    
    
    /**
     * Get database server type
     * 
     * @return Database server type
     */
    public BotDbServerType getServerType()
    {
        return serverType;
    }
    
    
    /**
     * Get database server ip address
     * 
     * @return Database server ip address
     */
    public String getServer()
    {
        return server;
    }
    
    
    /**
     * Get database server port
     * 
     * @return Database server port
     */
    public int getServerPort()
    {
        return serverPort;
    }
    
    
    /**
     * Get database name
     * 
     * @return Database name
     */
    public String getDbName()
    {
        return dbName;
    }
    
    
    /**
     * Get user to access the database
     * 
     * @return User to access the database
     */
    public String getDbUser()
    {
        return dbUser;
    }
    
    
    /**
     * Get password of the user to access the database
     * 
     * @return Password of the user to access the database
     */
    public String getDbPassword()
    {
        return dbPassword;
    }
}
