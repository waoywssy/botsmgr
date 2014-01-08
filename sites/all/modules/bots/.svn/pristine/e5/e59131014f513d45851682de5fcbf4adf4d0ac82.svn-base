package com.boryi.bots.base;

import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 * Entity class bundles http headers to simulate a browser activities
 * 
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotHttpHeaderEntity 
{
    /**
     * Http header User-Agent: 
     * Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; brip1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)
     */
    protected String userAgent;
    /**
     * Http header Accept: 
     * text/html,application/xhtml+xml,application/xml;q=0.9,* /*;q=0.8
     */
    protected String accept;
    /**
     * Http header Accept-Language: 
     * en-us,en;q=0.8
     */
    protected String acceptLanguage;
    /**
     * Http header Accept-Encoding: 
     * gzip,deflate
     */
    protected String acceptEncoding;
    /**
     * Http header Accept-Charset: 
     * ISO-8859-1,utf-8;q=0.7,*;q=0.7
     */
    protected String acceptCharset;
    /**
     * Http header Connection: 
     * Keep-Alive or close
     */
    protected String connection;
    /**
     * Http header Keep-Alive: 
     * 0#
     */
    protected String keepAlive;
    /**
     * Http header Referer: 
     * http://www.google.com/
     */
    protected String referer;
    /**
     * Singleton for BotHttpHeaderEntity which can be inherited to refer the 
     * derived class instance.  Always refer to the first initialization 
     * instance.
     */
    protected static BotHttpHeaderEntity instance;
    
    
    /**
     * Default Constructor
     */
    public BotHttpHeaderEntity()
    {
        initialize();
    }
    
    
    /**
     * Constructor to initialize an instance of BotHttpHeaderEntity
     * 
     * @param userAgent Http header User-Agent:
     * @param accept Http header Accept: 
     * @param acceptLanguage Http header Accept-Language: 
     * @param acceptEncoding Http header Accept-Encoding: 
     * @param acceptCharset Http header Accept-Charset: 
     * @param connection Http header Connection: 
     * @param keepAlive Http header Keep-Alive: 
     * @param referer Http header Referer: 
     */
    public BotHttpHeaderEntity(String userAgent, String accept, 
            String acceptLanguage, String acceptEncoding, String acceptCharset,
            String connection, String keepAlive, String referer)
    {
        initialize();
        
        instance.userAgent = userAgent;
        instance.accept = accept;
        instance.acceptLanguage = acceptLanguage;
        instance.acceptEncoding = acceptEncoding;
        instance.acceptCharset = acceptCharset;
        instance.connection = connection;
        instance.keepAlive = keepAlive;
        instance.referer = referer;
    }
    
    
    /**
     * The first initialization instance. It can refer to the derived class 
     * instance.
     */
    protected void initialize()
    {
        if (instance == null)
        {
            instance = this;
        }
    }

    
    /**
     * Singleton for BotHttpHeaderEntity which can be inherited to refer the 
     * derived class instance.  Always refer to the first initialization 
     * instance.
     * 
     * @return The first initialization instance
     */
    public static BotHttpHeaderEntity getInstance()
    {
        return instance;
    }

    
    /**
     * Get Http header User-Agent:
     * 
     * @return Http header User-Agent:
     */
    public String getUserAgent() 
    {
        return userAgent;
    }


    /**
     * Get Http header Accept: 
     * 
     * @return Http header Accept: 
     */
    public String getAccept() 
    {
        return accept;
    }


    /**
     * Get Http header Accept-Language: 
     * 
     * @return Http header Accept-Language: 
     */
    public String getAcceptLanguage() 
    {
        return acceptLanguage;
    }


    /**
     * Get Http header Accept-Encoding: 
     * 
     * @return Http header Accept-Encoding: 
     */
    public String getAcceptEncoding() 
    {
        return acceptEncoding;
    }


    /**
     * Get Http header Accept-Charset: 
     * 
     * @return Http header Accept-Charset: 
     */
    public String getAcceptCharset() 
    {
        return acceptCharset;
    }


    /**
     * Get Http header Connection: 
     * 
     * @return Http header Connection: 
     */
    public String getConnection() 
    {
        return connection;
    }


    /**
     * Get Http header Keep-Alive: 
     * 
     * @return Http header Keep-Alive: 
     */
    public String getKeepAlive()
    {
        return keepAlive;
    }
    
    
    /**
     * Get Http header Referer: 
     * 
     * @return Http header Referer: 
     */
    public String getReferer() 
    {
        return referer;
    }
    
    
    /**
     * Get Http headers
     * 
     * @return Http headers
     */
    public Header[] getHeaders()
    {
        ArrayList<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Host", "www.example.com"));
        if (!userAgent.isEmpty())
        {
            headers.add(new BasicHeader("User-Agent", userAgent));
        }
        if (!accept.isEmpty())
        {
            headers.add(new BasicHeader("Accept", accept));
        }
        if (!acceptLanguage.isEmpty())
        {
            headers.add(new BasicHeader("Accept-Language", acceptLanguage));
        }
        if (!acceptEncoding.isEmpty())
        {
            headers.add(new BasicHeader("Accept-Encoding", acceptEncoding));
        }
        if (!acceptCharset.isEmpty())
        {
            headers.add(new BasicHeader("Accept-Charset", acceptCharset));
        }
        if (!connection.isEmpty()
                && connection.equalsIgnoreCase("keep-alive") 
                && !keepAlive.isEmpty())
        {
            headers.add(new BasicHeader("Keep-Alive", keepAlive));
        }
        else
        {
            headers.add(new BasicHeader("Connection", "close"));
        }
        if (!referer.isEmpty())
        {
            headers.add(new BasicHeader("Referer", referer));
        }
        
        Header[] header = new Header[headers.size()];
        for (int i = 0; i < headers.size(); i++)
        {
            header[i] = headers.get(i);
        }
        
        return header;
    }
}
