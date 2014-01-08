package com.boryi.bots.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.regex.Pattern;

import java.net.URI;

import java.sql.SQLException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.util.EntityUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.ProtocolException;
import org.apache.http.message.BasicHeader;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.ProtocolVersion;
import org.apache.http.HttpVersion;

/**
 * Bot web thread class to crawl page according to checklist, parse page, 
 * save data to database, add checklist if needed, get next checklist
 * item and crawl.
 * 
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotWebThread extends Thread
{
    /**
     * Bot web threads
     */
    protected static ArrayList<BotWebThread> webThreads;
    /**
     * Http client for the web thread
     */
    protected DefaultHttpClient httpclient;
    /**
     * To store the cookies
     */
    protected CookieStore cookieStore;
    /**
     * HTTP request header length
     */
    protected long requestHeaderLength;
    /**
     * HTTP request content length
     */
    protected long requestContentLength;
    /**
     * HTTP response header length
     */
    protected long responseHeaderLength;
    /**
     * HTTP response content length
     */
    protected long responseContentLength;
    /**
     * Record the web connection time: time[0] start, time[1] end
     */
    protected long[] time = new long[2];
    /**
     * The checklist item to be crawled
     */
    protected BotChecklistEntity item = null;
    /**
     * Web page or xml or data downloaded from the given url
     */
    protected String webData = null;
    /**
     * Regexes to parse web data
     */
    protected Hashtable<String, Pattern> regexes = null;
    /**
     * Data to be saved in local database according to its stored procedure name
     */
    protected Hashtable<String, ArrayList<BotDataEntity>> data = null;
    /**
     * Newly founded and created checklist
     */
    protected ArrayList<BotChecklistEntity> checklist = null;
    /**
     * Network connection failed sleeping time calculator
     */
    protected BotSleepingTime sleepingTime;

    
    /**
     * Constructor to initialize an instance of BotWebThread
     */
    public BotWebThread()
    {
        initialize();
    }

    
    /**
     * Add the current newly created web thread to the list of web threads
     */
    protected void initialize()
    {
        if (webThreads == null)
        {
            webThreads = new ArrayList<BotWebThread>();
        }
        webThreads.add(this);
    }
    
    
    /**
     * Get bot web threads
     * 
     * @return Bot web threads
     */
    public static ArrayList<BotWebThread> getWebThreads()
    {
        return webThreads;
    }
    
    
    /**
     * Prepare the http connection including proxy
     */
    protected void prepare() throws Exception
    {
        httpclient = new DefaultHttpClient();
        cookieStore = new BasicCookieStore();
        
        sleepingTime = BotSleepingTimeFactory.getSleepingTimeMethod(
                BotConfig.getInstance().getWebRetry().getMethod(),
                BotConfig.getInstance().getWebRetry().getBase(),
                BotConfig.getInstance().getWebRetry().getStep());

        // Set up proxy server
        if (BotConfig.getInstance().getProxy().getServer() != null 
                && !BotConfig.getInstance().getProxy().getServer().isEmpty())
        {
            HttpHost proxy = new HttpHost(
                    BotConfig.getInstance().getProxy().getServer(), 
                    BotConfig.getInstance().getProxy().getPort(),
                    BotConfig.getInstance().getProxy().getScheme());
            
            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            
            if (BotConfig.getInstance().getProxy().getUser() != null
                    && !BotConfig.getInstance().getProxy().getUser().isEmpty())
            {
                httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(
                        BotConfig.getInstance().getProxy().getServer(), 
                        BotConfig.getInstance().getProxy().getPort()), 
                    new UsernamePasswordCredentials(
                        BotConfig.getInstance().getProxy().getUser(), 
                        BotConfig.getInstance().getProxy().getPassword()));
            }
        }

        // Set up headers which are the same for all the requests
        // Set up cookie store to deal with cookies
        httpclient.addRequestInterceptor(new HttpRequestInterceptor() {
            @Override
            public void process(final HttpRequest request, 
                    final HttpContext context) throws HttpException, IOException 
            {
                if (request == null) 
                {
                    throw new IllegalArgumentException("HTTP request may not be null");
                }
                if (request instanceof HttpEntityEnclosingRequest) 
                {
                    if (request.containsHeader(HTTP.TRANSFER_ENCODING)) 
                    {
                        throw new ProtocolException("Transfer-encoding header already present");
                    }

                    // Set Headers
                    Header[] headers = BotHttpHeaderEntity.getInstance().getHeaders();
                    headers[0] = new BasicHeader("Host", item.getUrl().getHost());
                    request.setHeaders(headers);

                    // Set Cookies
                    if (cookieStore.getCookies().size() > 0)
                    {
                        List<Cookie> cookies = cookieStore.getCookies();
                        String cookie = "";
                        for (int i = 0; i < cookies.size(); i++)
                        {
                            if (!cookies.get(i).isExpired(new Date()) 
                                    && item.getUrl().getHost().indexOf(cookies.get(i).getDomain()) >= 0)
                            {
                                cookie += cookies.get(i).getName() 
                                        + "=" 
                                        + cookies.get(i).getValue()
                                        + ",";
                            }
                        }
                        if (cookie.length() > 0)
                        {
                            request.setHeader("Cookie", 
                                    cookie.substring(0, cookie.length() - 1));
                        }
                    }

                    context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

                    getRequestHeaderLength(request);
                    getRequestContentLength(request);

                    time[0] = System.currentTimeMillis();
                
                    ProtocolVersion ver = request.getRequestLine().getProtocolVersion();
                    HttpEntity entity = ((HttpEntityEnclosingRequest)request).getEntity();
                    if (entity == null) 
                    {
                        request.addHeader(HTTP.CONTENT_LEN, "0");
                        return;
                    }
                    
                    // Must specify a transfer encoding or a content length 
                    if (entity.isChunked() || entity.getContentLength() < 0) 
                    {
                        if (ver.lessEquals(HttpVersion.HTTP_1_0)) 
                        {
                            throw new ProtocolException("Chunked transfer encoding not allowed for " + ver);
                        }
                        request.addHeader(HTTP.TRANSFER_ENCODING, HTTP.CHUNK_CODING);
                    } 
                    else 
                    {
                        request.addHeader(HTTP.CONTENT_LEN, Long.toString(entity.getContentLength()));
                    }
                    // Specify a content type if known
                    if (entity.getContentType() != null && !request.containsHeader(HTTP.CONTENT_TYPE)) 
                    {
                        request.addHeader(entity.getContentType()); 
                    }
                    // Specify a content encoding if known
                    if (entity.getContentEncoding() != null && !request.containsHeader(HTTP.CONTENT_ENCODING)) 
                    {
                        request.addHeader(entity.getContentEncoding()); 
                    }
                }
            }
        });

        
        // Set up response to retrieve cookies and uncompress gzipped content
        httpclient.addResponseInterceptor(new HttpResponseInterceptor() {
            @Override
            public void process(final HttpResponse response, 
                    final HttpContext context) throws HttpException, IOException 
            {
                time[1] = System.currentTimeMillis();
                
                HttpEntity entity = response.getEntity();
                Header ceheader = entity.getContentEncoding();
                if (ceheader != null) 
                {
                    HeaderElement[] codecs = ceheader.getElements();
                    for (int i = 0; i < codecs.length; i++) 
                    {
                        if (codecs[i].getName().equalsIgnoreCase("gzip")) 
                        {
                            response.setEntity(
                                    new GzipDecompressingEntity(response.getEntity())); 
                            return;
                        }
                    }
                }
                getResponseHeaderLength(response);
                getResponseContentLength(response);
                try
                {
                    // Compute web statistic data
                    BotWebStatistic.getInstance().increase(1,
                            requestHeaderLength, requestContentLength,
                            responseHeaderLength, responseContentLength,
                            time[1] - time[0]);
                }
                catch (InterruptedException ex)
                {
                }
            }
        });
        
        // Set up redirect
        // setRedirectHandler();
        // Set up request retry
        // setHttpRequestRetryHandler();
        
        // Prepare regexes
        regexes = new Hashtable<String, Pattern>(
                BotConfig.getInstance().getRegexes().size());
        Enumeration<String> regexNames = 
                BotConfig.getInstance().getRegexes().keys();
        while (regexNames.hasMoreElements())
        {
            String regexName = regexNames.nextElement();
            String regex = BotConfig.getInstance().getRegexes().get(regexName);
            regexes.put(regexName, 
                    Pattern.compile(regex, Pattern.CASE_INSENSITIVE 
                                        | Pattern.DOTALL | Pattern.MULTILINE));
        }
    }
    
    
    /**
     * Get web page or xml according to checklist item.  Before getting the
     * web page, which contains data information or checklist items, it may
     * crawl other web pages and parsed for parameters.  For example, a site 
     * needs login to get data list and the next stage of checklist items.
     * 
     * Proxy is considered. Web statistic is computed. Sleeping may needed.
     */
    protected void getWebData() throws InterruptedException, Exception
    {
        HttpGet httpget = new HttpGet(item.getUrl());

        int retry = 0;
        boolean failed = true;
        while (failed)
        {
            try
            {
                time[0] = 0l;
                time[1] = 0l;

                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();

                if (entity != null)
                {
                    webData = EntityUtils.toString(entity);
                    failed = false;
                }
                else
                {
                    webData = null;
                }
            }
            catch (Exception ex)
            {
                if (retry < BotConfig.getInstance().getWebRetry().getRetry())
                {
                    BotBase.logger.warn("Network connection retry: " 
                            + retry);
                    Thread.sleep(sleepingTime.getSleepingTime(retry));
                    retry++;
                }
                else
                {
                    throw ex;
                }
            }
        }
    }
    
    
    /**
     * Retrieve web data by HTTP POST method
     * 
     * @param nvps 
     * @return Instance of HttpPost to be executed by httpclient
     * @throws java.io.UnsupportedEncodingException
     */
    protected HttpPost preparePostWebData(Hashtable<String, String> nameValues) 
            throws UnsupportedEncodingException
    {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        
        Enumeration<String> names = nameValues.keys();
        while (names.hasMoreElements())
        {
            String name = names.nextElement();
            String value = nameValues.get(name);
            
            nvps.add(new BasicNameValuePair(name, value));
        }
        
        HttpPost httpost = new HttpPost(item.getUrl());
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        
        return httpost;
    }
    
    
    /**
     * Get HTTP request header length in bytes
     * 
     * @param request HTTP request
     */
    protected void getRequestHeaderLength(HttpRequest request)
    {
        Header[] headers = request.getAllHeaders();
        requestHeaderLength = 0;
        try
        {
            URI uri = new URI(request.getRequestLine().getUri());
            int l = uri.getScheme().length() + uri.getHost().length() + 3; // 3: ://

            requestHeaderLength = request.getRequestLine().toString().length() - l;
        }
        catch (Exception ex) {}

        for (int i = 0; i < headers.length; i++)
        {
            requestHeaderLength += headers[i].toString().length() + 2; // /r/n
        }
        requestHeaderLength += 2; // empty line after the headers
    }


    /**
     * Get HTTP request content length in bytes
     * 
     * @param request HTTP request
     */
    protected void getRequestContentLength(HttpRequest request)
    {
        requestContentLength = 0l;
        if (request.getLastHeader("Content-Length") != null)
        {
            requestContentLength = Long.parseLong(
                    request.getLastHeader("Content-Length").getValue());
        }
    }

    
    /**
     * Get HTTP response header length in bytes
     * 
     * @param response HTTP response
     */
    protected void getResponseHeaderLength(HttpResponse response)
    {
        responseHeaderLength = 
                response.getStatusLine().toString().length() + 2;
        
        Header[] headers = response.getAllHeaders();
        for (int i = 0; i < headers.length; i++)
        {
            responseHeaderLength += 
                    headers[i].toString().length() + 2; // /r/n
        }
        responseHeaderLength += 2; // empty line
        
        // if using proxy, responseHeaderLength - 6 "Proxy-"
        if (BotConfig.getInstance().getProxy().getServer() != null 
                && !BotConfig.getInstance().getProxy().getServer().isEmpty())
        {
            Header header = response.getLastHeader("Proxy-Connection");
            if (header != null)
            {
                if (header.getValue().equalsIgnoreCase("close"))
                {
                    responseHeaderLength -= 6;
                }
                else
                {
                    responseHeaderLength -= (header.toString().length() + 2);
                }
            }
        }
   }


    /**
     * Get HTTP response content length in bytes
     * 
     * @param response HTTP response
     */
    protected void getResponseContentLength(HttpResponse response)
    {
        responseContentLength = 0l;
        if (response.getLastHeader("Content-Length") != null)
        {
            responseContentLength = Long.parseLong(
                    response.getLastHeader("Content-Length").getValue());
        }
    }
    
    
    /**
     * Set up for redirect for httpclient
     */
    protected void setRedirectHandler()
    {
        // Set up for redirect
        httpclient.setRedirectHandler(new RedirectHandler() {
            @Override
            public URI getLocationURI(HttpResponse response, 
                    HttpContext context) throws ProtocolException
            {
                URI uri = null;
                try
                {
                    switch (response.getStatusLine().getStatusCode())
                    {
                        case 300:
                        case 301:
                        case 302:
                        case 303:
                        case 307:
                            if (response.getLastHeader("Location") != null)
                            {
                                uri = new URI(response.getLastHeader("Location").getValue());
                            }
                            break;
                        case 200:
                            if (response.getLastHeader("Location") != null)
                            {
                                uri = new URI(response.getLastHeader("Location").getValue());
                            }
                            else if (response.getLastHeader("Refresh") != null)
                            {
                                String value = response.getLastHeader("Refresh").getValue();
                                int index = value.toLowerCase().indexOf("http");
                                uri = new URI(value.substring(index));
                            }
                            else
                            {
                                HttpEntity entity = response.getEntity();
                                if (entity != null)
                                {
                                    String content = EntityUtils.toString(entity);
                                    // meta tag refresh
                                    // <meta http-equiv="Refresh" content="0; url=http://www.example.com/">
                                    String lcontent = content.toLowerCase();
                                    int index = lcontent.indexOf("<meta http-equiv=\"refresh\"");
                                    if (index > 0)
                                    {
                                        int lstart = lcontent.indexOf("url=http", index + 26);
                                        int lend = lcontent.indexOf("\">", lstart);
                                        if (lstart > 0 && lend > 0)
                                        {
                                            uri = new URI(content.substring(lstart + 4, lend));
                                        }
                                    }
                                    else
                                    {
                                        // javascript redirect
                                        // <script ... window.location.href=
                                    }
                                }
                            }
                            break;
                    }
                }
                catch (Exception ex)
                { }
                
                return uri;
            }
            
            @Override
            public boolean isRedirectRequested(HttpResponse response, 
                    HttpContext context) 
            {
                boolean isRedirect = false;
                switch (response.getStatusLine().getStatusCode())
                {
                    case 300:
                    case 301:
                    case 302:
                    case 303:
                    case 307:
                        isRedirect = response.getLastHeader("Location") != null;
                        break;
                    case 200:
                        if (response.getLastHeader("Location") != null)
                        {
                            isRedirect = true;
                        }
                        else if (response.getLastHeader("Refresh") != null)
                        {
                            isRedirect = true;
                        }
                        else
                        {
                            HttpEntity entity = response.getEntity();
                            if (entity != null)
                            {
                                try
                                {
                                    String content = EntityUtils.toString(entity);
                                    // meta tag refresh
                                    // <meta http-equiv="Refresh" content="0; url=http://www.example.com/">
                                    String lcontent = content.toLowerCase();
                                    int index = lcontent.indexOf("<meta http-equiv=\"refresh\"");
                                    if (index > 0)
                                    {
                                        int lstart = lcontent.indexOf("url=http", index + 26);
                                        int lend = lcontent.indexOf("\">", lstart);
                                        if (lstart > 0 && lend > 0)
                                        {
                                            isRedirect = true;
                                        }
                                    }
                                    else
                                    {
                                        // javascript redirect
                                        // <script ... window.location.href=
                                    }
                                }
                                catch (Exception ex)
                                {}
                            }
                        }
                        break;
                }
                return isRedirect;
            }
        });
    }
    
    
    /**
     * Set up for request retry for httpclient
     */
    protected void setHttpRequestRetryHandler()
    {
        // Set up for request retry
        httpclient.setHttpRequestRetryHandler(new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(
                    IOException exception, 
                    int executionCount, 
                    HttpContext context) 
            {
                if (executionCount < BotConfig.getInstance().getWebRetry().getRetry())
                {
                    BotBase.logger.warn("Network IO request failed, http client request retry: " 
                            + executionCount);
                    try
                    {
                        Thread.sleep(sleepingTime.getSleepingTime(executionCount));
                    }
                    catch (Exception ex)
                    {                        
                    }
                    return true;
                }
                return false;
            }
        });
    }
       
    
    /**
     * Parse downloaded web data according to "stage", using switch case.  Save
     * the parsed data into a hashtable (stored procedure name, rows of data 
     * for the procedure name). Create a new checklist if there is one, array 
     * of checklist item without id information for each item.
     */
    protected void parseWebData()
    {
    }


    /**
     * Save parsed data based on "stage", using switch case, to local database.
     * Id of the checklist item needs to be retrieved from database.  Add new
     * checklist items to database, add new checklist items to memory checklist.
     * Mark the current checklist item in server to be done, mark the current
     * checklist item in the memory to be done
     */
    protected void saveData() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException, 
            InterruptedException
    {
    }


    /**
     * Add newly found checklist items to checklist, insert to database 
     * of the newly checklist, mark the checklist item done in database
     * and memory BotChecklist instance
     * 
     * First add the new checklist, then done the current checklist item
     */
    protected void addChecklist() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException, 
            InterruptedException
    {
        if (checklist != null)
        {
            BotDao.getInstance().saveChecklist(checklist);
            
            BotChecklist.getInstance().addChecklist(checklist);
        }
        BotDao.getInstance().doneChecklistItem(item);
        BotChecklist.getInstance().removeChecklistItem(item);
    }


    /**
     * Thread run
     */
    @Override
    public void run()
    {
        try
        {
            prepare();
            while (!BotChecklist.getInstance().IsFinished())
            {
                item = BotChecklist.getInstance().getChecklistItem();
                if (item == null)
                {
                    Thread.sleep(1000);
                }
                else
                {
                    BotDao.getInstance().checkingChecklistItem(item);
                    
                    getWebData();

                    // You must implement this method
                    parseWebData();
                    // You must implement this method
                    saveData();

                    addChecklist();
                }
                item = null;
                webData = null;
                data = null;
                checklist = null;
            }
            httpclient.getConnectionManager().shutdown();
        }
        catch (Exception ex)
        {
            BotBase.logger.fatal(ex.toString());
        }
    }
    
    
    
    static class GzipDecompressingEntity extends HttpEntityWrapper 
    {
        public GzipDecompressingEntity(final HttpEntity entity) 
        {
            super(entity);
        }
    
        @Override
        public InputStream getContent() throws IOException, 
                IllegalStateException 
        {
            // the wrapped entity's getContent() decides about repeatability
            InputStream wrappedin = wrappedEntity.getContent();

            return new GZIPInputStream(wrappedin);
        }

        @Override
        public long getContentLength() 
        {
            // length of ungzipped content is not known
            return -1;
        }

    } 
}
