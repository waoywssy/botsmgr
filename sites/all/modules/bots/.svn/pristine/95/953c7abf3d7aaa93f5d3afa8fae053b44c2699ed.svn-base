package com.boryi.bots.base;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.regex.*;
import java.net.URI;
import java.net.URISyntaxException;

import java.io.*;
import javax.xml.transform.dom.*;
import javax.xml.validation.*;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;
import org.w3c.dom.*;

import org.apache.commons.io.FileUtils;

/**
 * Base class for configuration settings of Bot.  The settings are read only.
 *
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotConfig 
{
    /**
     * Web site URL - crawling started from
     */
    protected URI webUrl;
    /**
     * Web headers - User-Agent, Accept, Accept-Language, Accept-Encoding,
     * Accept-Charset, Connection, Keep-Alive, Referer
     */
    protected BotHttpHeaderEntity webHeader;
    /**
     * Central database server entity: run table and event table
     */
    protected BotDbServerEntity dbCentral;
    /**
     * Local database server entity: qa table, web statistic table,
     * checklist table, bot related data tables
     */
    protected BotDbServerEntity dbLocal;
    /**
     * Proxy server entity
     */
    protected BotProxyServerEntity proxy;
    /**
     * Limit for pages/hits per hour
     */
    protected int maxPageHitsPerHour = 7200; // 2 hits per second
    /**
     * Limit for kilobytes per hour
     */
    protected int maxKilobytesPerHour = 360000; // 100KB per second
    /**
     * Number of times to retry if there is network failure, or
     * verified maintenance page is downloaded, or wrong page,
     * which can not be verified, is downloaded.  The verified
     * page but failed to parse is not retried.
     */
    protected BotRetryEntity webRetry;
    /**
     * Number of times to retry if database server failure
     */
    protected BotRetryEntity dbRetry;
    /**
     * Maximum running time of the bot main business thread in milliseconds
     */
    protected long maxRunTime = 86400000l; // 1 day in milliseconds
    /**
     * Number of threads to crawl web pages
     */
    protected int webThreads = 1;
    /**
     * Temporary directory to store downloaded web pages which cannot be parsed
     */
    protected String tempDir = "tmp/";
    /**
     * Regex directory to store regex files
     */
    private String regexDir = "regex/";
    /**
     * The mail protocol of the email server
     */
    protected String mailProtocol = "smtp";
    /**
     * The email server
     */
    protected String mailHost = "srv000.boryi.com";
    /**
     * The email account that the QA report will be sent to.  Use the local
     * database name and server ip as the subject of the email
     */
    protected String mailTo = "bot@hq.boryi.com";
    /**
     * Prefix part of the subject of the bot automatic QA report email
     */
    protected String mailSubject = "Bot automatic QA report - ";
    /**
     * Variable name and file name of the regex
     */
    protected Hashtable<String, String> regexes;
    /**
     * Variable name and Pattern object of the regex
     */
    private Hashtable<String, Pattern> patterns;
    /**
     * For each stage, a list of variable names of the regexes
     */
    protected Hashtable<Integer, ArrayList<String>> regexStages;
    /**
     * Variable name and store procedure name of the central database procedures
     */
    protected Hashtable<String, String> centralProcedures;
    /**
     * Variable name and store procedure name of the local database procedures
     */
    protected Hashtable<String, String> localProcedures;
    /**
     * Variable name and value of parameters used by the bot
     */
    protected Hashtable<String, String> params;
    /**
     * Document of the configuration xml
     */
    protected Document doc;
    /**
     * Singleton for BotConfig which can be inherited to refer the derived 
     * class instance.  Always refer to the first initialization instance.
     */
    protected static BotConfig instance;
    
    
    /**
     * Default constructor
     * 
     * @param config Configuration file name, e.g. config.xsd, config.xml
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws java.lang.Exception
     */
    public BotConfig(String config)
            throws SAXException, IOException, ParserConfigurationException, Exception
    {
        initialize();
        
        String xsd = config + ".xsd";
        String xml = config + ".xml";
        read(xsd, xml);
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
     * Singleton for BotConfig which can be inherited to refer the derived 
     * class instance.  Always refer to the first initialization instance.
     * 
     * @return The first initialization instance
     */
    public static BotConfig getInstance()
    {
        return instance;
    }


    /**
     * Read configuration settings of Bot
     * 
     * @param xsd Bot configuration XML schema
     * @param xml Bot configuration XML
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws java.lang.Exception
     */
    protected void read(String xsd, String xml)
            throws SAXException, IOException, ParserConfigurationException, 
            URISyntaxException, Exception
    {
        SchemaFactory factory 
                = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        File schemaLocation = new File(xsd);
        Schema schema = factory.newSchema(schemaLocation);
        Validator validator = schema.newValidator();
        
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);                 // Don't forget this
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        
        File xmlFile = new File(xml);
        doc = builder.parse(xmlFile);
        
        DOMSource source = new DOMSource(doc);
        
        validator.validate(source);

        webUrl = new URI(doc.getElementsByTagName("ns1:webUrl").item(0).getTextContent());
        
        String userAgent = doc.getElementsByTagName("ns1:httpUserAgent").item(0).getTextContent();
        String accept = doc.getElementsByTagName("ns1:httpAccept").item(0).getTextContent();
        String acceptLanguage = doc.getElementsByTagName("ns1:httpAcceptLanguage").item(0).getTextContent();
        String acceptEncoding = doc.getElementsByTagName("ns1:httpAcceptEncoding").item(0).getTextContent();
        String acceptCharset = doc.getElementsByTagName("ns1:httpAcceptCharset").item(0).getTextContent();
        String connection = doc.getElementsByTagName("ns1:httpConnection").item(0).getTextContent();
        String keepAlive = doc.getElementsByTagName("ns1:httpKeepAlive").item(0).getTextContent();
        String referer = doc.getElementsByTagName("ns1:httpReferer").item(0).getTextContent();
        
        webHeader = new BotHttpHeaderEntity(userAgent, accept, acceptLanguage,
                acceptEncoding, acceptCharset, connection, keepAlive, referer);
        
        String dbCentralServerType = doc.getElementsByTagName("ns1:dbCentralServerType").item(0).getTextContent();
        String dbCentralServer = doc.getElementsByTagName("ns1:dbCentralServer").item(0).getTextContent();
        int dbCentralServerPort = Integer.parseInt(doc.getElementsByTagName("ns1:dbCentralServerPort").item(0).getTextContent());
        String dbCentralName = doc.getElementsByTagName("ns1:dbCentralName").item(0).getTextContent();
        String dbCentralUser = doc.getElementsByTagName("ns1:dbCentralUser").item(0).getTextContent();
        String dbCentralPassword = doc.getElementsByTagName("ns1:dbCentralPassword").item(0).getTextContent();
        
        dbCentral = new BotDbServerEntity(
                getDbServerType(dbCentralServerType), dbCentralServer, 
                dbCentralServerPort, dbCentralName, 
                dbCentralUser, dbCentralPassword);
        
        String dbLocalServerType = doc.getElementsByTagName("ns1:dbLocalServerType").item(0).getTextContent();
        String dbLocalServer = doc.getElementsByTagName("ns1:dbLocalServer").item(0).getTextContent();
        int dbLocalServerPort = Integer.parseInt(doc.getElementsByTagName("ns1:dbLocalServerPort").item(0).getTextContent());
        String dbLocalName = doc.getElementsByTagName("ns1:dbLocalName").item(0).getTextContent();
        String dbLocalUser = doc.getElementsByTagName("ns1:dbLocalUser").item(0).getTextContent();
        String dbLocalPassword = doc.getElementsByTagName("ns1:dbLocalPassword").item(0).getTextContent();
        
        dbLocal = new BotDbServerEntity(
                getDbServerType(dbLocalServerType), dbLocalServer, 
                dbLocalServerPort, dbLocalName, 
                dbLocalUser, dbLocalPassword);
        
        String proxyScheme = doc.getElementsByTagName("ns1:proxyScheme").item(0).getTextContent();
        String proxyServer = doc.getElementsByTagName("ns1:proxyServer").item(0).getTextContent();
        int proxyPort = Integer.parseInt(doc.getElementsByTagName("ns1:proxyPort").item(0).getTextContent());
        String proxyUser = doc.getElementsByTagName("ns1:proxyUser").item(0).getTextContent();
        String proxyPassword = doc.getElementsByTagName("ns1:proxyPassword").item(0).getTextContent();
        
        proxy = new BotProxyServerEntity(proxyScheme, proxyServer, proxyPort,
                proxyUser, proxyPassword);
        
        maxPageHitsPerHour = Integer.parseInt(doc.getElementsByTagName("ns1:maxPageHitsPerHour").item(0).getTextContent());
        maxKilobytesPerHour = Integer.parseInt(doc.getElementsByTagName("ns1:maxKilobytesPerHour").item(0).getTextContent());
        
        int maxWebRetry = Integer.parseInt(doc.getElementsByTagName("ns1:webRetry").item(0).getTextContent());
        String webRetryMethod = doc.getElementsByTagName("ns1:webRetryMethod").item(0).getTextContent();
        long webRetryBase = Long.parseLong(doc.getElementsByTagName("ns1:webRetryBase").item(0).getTextContent());
        long webRetryStep = Long.parseLong(doc.getElementsByTagName("ns1:webRetryStep").item(0).getTextContent());
        
        webRetry = new BotRetryEntity(maxWebRetry, 
                getRetryMethod(webRetryMethod), webRetryBase, webRetryStep);

        int maxDbRetry = Integer.parseInt(doc.getElementsByTagName("ns1:dbRetry").item(0).getTextContent());
        String dbRetryMethod = doc.getElementsByTagName("ns1:dbRetryMethod").item(0).getTextContent();
        long dbRetryBase = Long.parseLong(doc.getElementsByTagName("ns1:dbRetryBase").item(0).getTextContent());
        long dbRetryStep = Long.parseLong(doc.getElementsByTagName("ns1:dbRetryStep").item(0).getTextContent());
        
        dbRetry = new BotRetryEntity(maxDbRetry, 
                getRetryMethod(dbRetryMethod), dbRetryBase, dbRetryStep);
        
        maxRunTime = 1000 * Long.parseLong(doc.getElementsByTagName("ns1:maxRunTime").item(0).getTextContent());

        webThreads = Integer.parseInt(doc.getElementsByTagName("ns1:webThreads").item(0).getTextContent());
        tempDir = doc.getElementsByTagName("ns1:tempDir").item(0).getTextContent();

        regexDir = doc.getElementsByTagName("ns1:regexDir").item(0).getTextContent();
        
        mailProtocol = doc.getElementsByTagName("ns1:mailProtocol").item(0).getTextContent();
        mailHost = doc.getElementsByTagName("ns1:mailHost").item(0).getTextContent();
        mailTo = doc.getElementsByTagName("ns1:mailTo").item(0).getTextContent();
        mailSubject = doc.getElementsByTagName("ns1:mailSubject").item(0).getTextContent();
        
        regexes = new Hashtable<String, String>();
        patterns = new Hashtable<String, Pattern>();
        regexStages = new Hashtable<Integer, ArrayList<String>>();
        setNameValueStage("ns1:regexes", regexes, regexStages);

        String dir = System.getProperty("user.dir") + "/" + regexDir;
        for (String name : regexes.keySet())
        {
            String pattern = FileUtils.readFileToString(new File(dir + regexes.get(name)));
            getPatterns().put(name,
                Pattern.compile(pattern,
                    Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE | Pattern.COMMENTS));
        }

        centralProcedures = new Hashtable<String, String>();
        setNameValueStage("ns1:centralProcedures", centralProcedures, null);
        
        localProcedures = new Hashtable<String, String>();
        setNameValueStage("ns1:localProcedures", localProcedures, null);
        
        params = new Hashtable<String, String>();
        setNameValueStage("ns1:params", params, null);
    }

    
    /**
     * Get dabase server type according to database server type string
     * 
     * @param dbServerType Database server type string
     * @return Dabase server type
     * @throws java.lang.Exception
     */
    protected BotDbServerType getDbServerType(String dbServerType)
            throws Exception
    {
        BotDbServerType type;
        
        if (dbServerType.equalsIgnoreCase("mysql"))
        {
            type = BotDbServerType.MYSQL;
        }
        else if (dbServerType.equalsIgnoreCase("postgresql"))
        {
            type = BotDbServerType.POSTGRESQL;
        }
        else if (dbServerType.equalsIgnoreCase("mssql"))
        {
            type = BotDbServerType.MSSQL;
        }
        else
        {
            throw new Exception("Unknown database server type: " + dbServerType);
        }
        return type;
    }
    
    
    /**
     * Get retry method according to retry method string
     * 
     * @param retryMethod Retry method string
     * @return Retry method
     * @throws java.lang.Exception
     */
    protected BotRetryMethod getRetryMethod(String retryMethod)
            throws Exception
    {
        BotRetryMethod method;
        
        if (retryMethod.equalsIgnoreCase("linear"))
        {
            method = BotRetryMethod.LINEAR;
        }
        else if (retryMethod.equalsIgnoreCase("quadratic"))
        {
            method = BotRetryMethod.QUADRATIC;
        }
        else if (retryMethod.equalsIgnoreCase("exponential"))
        {
            method = BotRetryMethod.EXPONENTIAL;
        }
        else
        {
            throw new Exception("Unknown retry method: " + retryMethod);
        }
        return method;
    }
    
    
    /**
     * Set the name, value, stages for Regexes, Procedures, and Params
     * 
     * @param tag Tag name of the name, value, stages
     * @param nameValues Name value hashtable
     * @param stages Stage hashtable for name values
     */
    protected void setNameValueStage(String tag, 
            Hashtable<String, String> nameValues,
            Hashtable<Integer, ArrayList<String>> stages)
    {
        if (doc.getElementsByTagName(tag) != null && 
                doc.getElementsByTagName(tag).getLength() > 0)
        {
            NodeList nlNameValues = doc.getElementsByTagName(tag).item(0).getChildNodes();
            ArrayList<Node> alNameValues = getChildElements(nlNameValues);
            int length = alNameValues.size();
            
            for (int i = 0; i < length; i++)
            {
                NodeList nlNameValue = alNameValues.get(i).getChildNodes();

                String name = null;
                String value = null;
                ArrayList<Node> alNameValue = getChildElements(nlNameValue);
                name = alNameValue.get(0).getTextContent();
                value = alNameValue.get(1).getTextContent();
                nameValues.put(name, value);

                if (alNameValue.size() == 3 && stages != null)
                {
                    NodeList nlStages = alNameValue.get(2).getChildNodes();
                    for (int j = 0; j < nlStages.getLength(); j++)
                    {
                        if (!(nlStages.item(j) instanceof Element))
                        {
                            continue;
                        }
                        Integer stage = Integer.valueOf(nlStages.item(j).getTextContent());
                        if (stages.containsKey(stage))
                        {
                            ArrayList<String> values = stages.get(stage);
                            values.add(value);
                        }
                        else
                        {
                            ArrayList<String> values = new ArrayList<String>();
                            values.add(value);
                            stages.put(stage, values);
                        }
                    }
                }
            }
        }
    }
    
    
    /**
     * Get web site URL - crawling started from
     * 
     * @return Web site URL - crawling started from
     */
    public URI getWebUrl()
    {
        return webUrl;
    }

    
    /**
     * Get web headers
     * 
     * @return Web headers
     */
    public BotHttpHeaderEntity getWebHeader()
    {
        return webHeader;
    }
    

    /**
     * Get central database server entity
     * 
     * @return Central database server entity
     */
    public BotDbServerEntity getDbCentral()
    {
        return dbCentral;
    }

    
    /**
     * Get local database server entity
     * 
     * @return Local database server entity
     */
    public BotDbServerEntity getDbLocal()
    {
        return dbLocal;
    }


    /**
     * Get proxy server entity
     * 
     * @return Proxy server entity
     */
    public BotProxyServerEntity getProxy()
    {
        return proxy;
    }


    /**
     * Get limit for pages/hits per hour
     * 
     * @return Limit for pages/hits per hour
     */
    public int getMaxPageHitsPerHour()
    {
        return maxPageHitsPerHour;
    }


    /**
     * Get limit for kilobytes per hour
     * 
     * @return Limit for kilobytes per hour
     */
    public int getMaxKilobytesPerHour()
    {
        return maxKilobytesPerHour;
    }


    /**
     * Get number of times to retry if web activities failure
     * 
     * @return Number of times to retry if web activities failure
     */
    public BotRetryEntity getWebRetry()
    {
        return webRetry;
    }


    /**
     * Get number of times to retry if database server failure
     * 
     * @return Number of times to retry if database server failure
     */
    public BotRetryEntity getDbRetry()
    {
        return dbRetry;
    }


    /**
     * Get maximum running time of the bot main business thread in milliseconds
     * 
     * @return Maximum running time of the bot main business thread in 
     * milliseconds
     */
    public long getMaxRunTime()
    {
        return maxRunTime;
    }


    /**
     * Get number of threads to crawl web pages
     * 
     * @return Number of threads to crawl web pages
     */
    public int getWebThreads()
    {
        return webThreads;
    }


    /**
     * Get temporary directory to store downloaded web pages which cannot 
     * be parsed
     * 
     * @return Temporary directory to store downloaded web pages which cannot 
     * be parsed
     */
    public String getTempDir()
    {
        return tempDir;
    }
    
    
    /**
     * Get the mail protocol of the email server
     * 
     * @return The mail protocol of the email server
     */
    public String getMailProtocol()
    {
        return mailProtocol;
    }
    
    
    /**
     * Get the email server
     * 
     * @return The email server
     */
    public String getMailHost()
    {
        return mailHost;
    }
    
    
    /**
     * Get the email account that the QA report will be sent to
     * 
     * @return The email account that the QA report will be sent to
     */
    public String getMailTo()
    {
        return mailTo;
    }
    
    
    /**
     * Get prefix part of the subject of the bot automatic QA report email
     * 
     * @return Prefix part of the subject of the bot automatic QA report email
     */
    public String getMailSubject()
    {
        return mailSubject;
    }
    
    
    /**
     * Get variable name and file name of the regex
     * 
     * @return Variable name and file name of the regex
     */
    public Hashtable<String, String> getRegexes()
    {
        return regexes;
    }
    
    
    /**
     * Get a list of variable names of the regexes for each stage
     * 
     * @return A list of variable names of the regexes for each stage
     */
    public Hashtable<Integer, ArrayList<String>> getRegexStages()
    {
        return regexStages;
    }
    
    
    /**
     * Get variable name and store procedure name of the central database 
     * procedures
     * 
     * @return Variable name and store procedure name of the central database 
     * procedures
     */
    public Hashtable<String, String> getCentralProcedures()
    {
        return centralProcedures;
    }
    
    
    /**
     * Get variable name and store procedure name of the local database 
     * procedures
     * 
     * @return Variable name and store procedure name of the local database 
     * procedures
     */
    public Hashtable<String, String> getLocalProcedures()
    {
        return localProcedures;
    }
    
    
    /**
     * Get variable name and value of parameters used by the bot
     * 
     * @return Variable name and value of parameters used by the bot
     */
    public Hashtable<String, String> getParams()
    {
        return params;
    }


    /**
     * Get elements (nodes) of a NodeList
     * 
     * @param nodeList Input nodelist
     * @return Node The Nth element
     */
    public static ArrayList<Node> getChildElements(NodeList nodeList)
    {
        int length = nodeList.getLength();
        if (length == 0)
        {
            return null;
        }
        ArrayList result = new ArrayList();

        Node node = null;
        for (int i = 0; i < length; i++)
        {
            node = nodeList.item(i);
            if (node instanceof Element)
            {
                result.add(node);
            }
            node = null;
        }
        return result;
    }


    /**
     * @return the regexDir
     */
    public String getRegexDir()
    {
        return regexDir;
    }


    /**
     * @return the patterns
     */
    public Hashtable<String, Pattern> getPatterns()
    {
        return patterns;
    }
}
