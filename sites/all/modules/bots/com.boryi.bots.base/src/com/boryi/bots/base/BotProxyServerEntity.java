package com.boryi.bots.base;

/**
 * Bot proxy server entity bundles proxy server ip, port, user and password
 *
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotProxyServerEntity 
{
    /**
     * The http scheme for the proxy server, default is http.  For secure proxy
     * server, it is https.
     */
    protected String scheme = "http";
    /**
     * Proxy server ip address
     */
    protected String server;
    /**
     * Proxy server port number
     */
    protected int port = 80;
    /**
     * Proxy server user name
     */
    protected String user = null;
    /**
     * Proxy server password of the user
     */
    protected String password = null;
    
    
    /**
     * Constructor to initialize an instance of BotProxyServerEntity
     * 
     * @param server Proxy server ip address
     */
    public BotProxyServerEntity(String server)
    {
        this.server = server;
    }

    
    /**
     * Constructor to initialize an instance of BotProxyServerEntity
     * 
     * @param server Proxy server ip address
     * @param port Proxy server port number
     */
    public BotProxyServerEntity(String server, int port)
    {
        this.server = server;
        this.port = port;
    }
    
    
    /**
     * Constructor to initialize an instance of BotProxyServerEntity
     * 
     * @param server Proxy server ip address
     * @param user Proxy server user name
     * @param password Proxy server password of the user
     */
    public BotProxyServerEntity(String server, String user, String password)
    {
        this.server = server;
        this.user = user;
        this.password = password;
    }
    
    /**
     * Constructor to initialize an instance of BotProxyServerEntity
     * 
     * @param server Proxy server ip address
     * @param port Proxy server port number
     * @param user Proxy server user name
     * @param password Proxy server password of the user
     */
    public BotProxyServerEntity(String server, int port, 
            String user, String password)
    {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }
    
    
    /**
     * Constructor to initialize an instance of BotProxyServerEntity
     * 
     * @param server Proxy server ip address
     * @param port Proxy server port number
     * @param user Proxy server user name
     * @param password Proxy server password of the user
     */
    public BotProxyServerEntity(String scheme, String server, int port, 
            String user, String password)
    {
        this.scheme = scheme;
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    
    /**
     * Get proxy server http scheme
     * 
     * @return Proxy server http scheme
     */
    public String getScheme() 
    {
        return scheme;
    }

    
    /**
     * Get proxy server ip address
     * 
     * @return Proxy server ip address
     */
    public String getServer() 
    {
        return server;
    }

    
    /**
     * Get proxy server port number
     * 
     * @return Proxy server port number
     */
    public int getPort() 
    {
        return port;
    }
    

    /**
     * Get proxy server user name
     * 
     * @return Proxy server user name
     */
    public String getUser() 
    {
        return user;
    }

    
    /**
     * Get proxy server password of the user
     * 
     * @return Proxy server password of the user
     */
    public String getPassword() 
    {
        return password;
    }
}
