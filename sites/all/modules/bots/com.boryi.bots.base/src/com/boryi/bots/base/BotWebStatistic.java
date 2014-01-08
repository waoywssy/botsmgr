package com.boryi.bots.base;

/**
 * Base class for web statistic data of bot
 *
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotWebStatistic
{
    /**
     * maximum number of page hits per hour expressed in milliseconds
     */
    protected long pageHitsMaxPerHour;
    /**
     * The minimum time in milliseconds for one page hit
     */
    protected long pageHitsMinTime;
    /**
     * Time of the previous over limit downloading from the web site
     * for Page Hits Case
     */
    protected long pageHitsTimePrevious = 0l;
    /**
     * Number of page hits which are fast than average hourly hits
     */
    protected int pageHitsFastCount = 0;
    /**
     * Current total page hits
     */
    private long pageHitsTotal = 0l;
    /**
     * Maximum kilobytes downloaded per hour expressed in milliseconds
     */
    protected long kilobytesMaxPerHour;
    /**
     * The minimum time in milliseconds 1 kilobyte downloaded
     */
    protected long kilobytesMinTime;
    /**
     * Kilobytes of the previous over limit downloading from the web
     * site for kilobytes case
     */
    protected long kilobytesTimePrevious = 0l;
    /**
     * Kilobytes downloaded since previous time is over the average
     * speed limit.  If kilobytes downloaded since previous time is
     * over the limits per hour, the bot has to wait (1 hour -
     * (timeEnd - timePrevious)), otherwise, set the timePrevious
     * to be timeEnd
     */
    protected int kilobytesOverCount = 0;
    /**
     * Current total kilobytes downloaded
     */
    protected long kilobytesTotal = 0l;
    /**
     * Current total request header bytes sent
     */
    protected long bytesTotalRequestHeader = 0l;
    /**
     * Current total request conent bytes sent
     */
    protected long bytesTotalRequestContent = 0l;
    /**
     * Current total reponse header bytes downloaded
     */
    protected long bytesTotalResponseHeader = 0l;
    /**
     * Current total reponse conent bytes downloaded
     */
    protected long bytesTotalResponseContent = 0l;
    /**
     * Start time of the first page downloaded from the web site
     */
    protected long timeStart;
    /**
     * End time of the last or current page downloaded from the web site
     */
    protected long timeEnd;
    /**
     * Actual web traffic time - for each downloading: connecting start
     * to download finished
     */
    protected long actualWebTime = 0l;
    /**
     * Singleton for BotWebStatistic which can be inherited to refer the derived 
     * class instance.  Always refer to the first initialization instance.
     */
    protected static BotWebStatistic instance;
    /**
     * To indicate whether web statistic computation is needed.
     */
    protected BotWebStatisticType botWebStatisticType;


    /**
     * Default constructor
     */
    public BotWebStatistic()
    {
        initialize();
        
        pageHitsMaxPerHour = BotConfig.getInstance().getMaxPageHitsPerHour();
        kilobytesMaxPerHour = BotConfig.getInstance().getMaxKilobytesPerHour();

        botWebStatisticType = BotWebStatisticType.NONE;
        if (pageHitsMaxPerHour > 0)
        {
            pageHitsMinTime = 3600000l / pageHitsMaxPerHour;
            botWebStatisticType = BotWebStatisticType.values()[botWebStatisticType.ordinal()
                                | BotWebStatisticType.PAGEHITS.ordinal()];
        }
        if (kilobytesMaxPerHour > 0)
        {
            kilobytesMinTime = 3600000l / kilobytesMaxPerHour;
            botWebStatisticType = BotWebStatisticType.values()[botWebStatisticType.ordinal()
                    | BotWebStatisticType.TRAFFIC.ordinal()];
        }
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
     * Singleton for BotWebStatistic which can be inherited to refer the derived 
     * class instance.  Always refer to the first initialization instance.
     * 
     * @return The first initialization instance
     */
    public static BotWebStatistic getInstance()
    {
        return instance;
    }


    /**
     * Increase the page hits, kilobytes downloaded. Return a sleeping 
     * time if it is needed before next page download.
     * 
     * @param pageHits Number of page hits
     * @param bytesRequestHeader Current total request header bytes sent
     * @param bytesRequestContent Current total request content bytes sent
     * @param bytesResponseHeader Current total response header bytes downloaded
     * @param bytesResponseContent Current total response content bytes downloaded
     * @param actualWebTime The actual web time for the page hits
     * @throws java.lang.InterruptedException
     */
    public synchronized void increase(int pageHits, 
            long bytesRequestHeader, long bytesRequestContent,
            long bytesResponseHeader, long bytesResponseContent,
            long actualWebTime) throws InterruptedException
    {
        if (botWebStatisticType == BotWebStatisticType.NONE)
        {
            return;
        }

        long current = System.currentTimeMillis();

        timeEnd = current;
        this.actualWebTime += actualWebTime;

        pageHitsTotal += pageHits;

        long bytes = 0l;
        long bytesTotal = 0l;
        long kilobytes = 0l;

        if (botWebStatisticType == BotWebStatisticType.TRAFFIC
            || botWebStatisticType == BotWebStatisticType.WEBSTATISTIC)
        {
            bytesTotalResponseHeader += bytesResponseHeader;
            bytesTotalResponseContent += bytesResponseContent;
            bytesTotalRequestHeader += bytesRequestHeader;
            bytesTotalRequestContent += bytesRequestContent;

            bytes = bytesResponseHeader + bytesResponseContent;
            bytesTotal = bytesTotalResponseHeader + bytesTotalResponseContent;

            kilobytesTotal = Math.round((double)bytesTotal / 1024.0d);
            kilobytes = Math.round((double)bytes / 1024.0d);
        }

        long sleepTime = 0l;
        long pageHitsAvgTime = 0l;
        long kilobytesAvgTime = 0l;

        if (botWebStatisticType == BotWebStatisticType.PAGEHITS
            || botWebStatisticType == BotWebStatisticType.WEBSTATISTIC)
        {
            pageHitsAvgTime = actualWebTime / pageHits;
        }
        if (botWebStatisticType == BotWebStatisticType.TRAFFIC
            || botWebStatisticType == BotWebStatisticType.WEBSTATISTIC)
        {
            kilobytesAvgTime = actualWebTime / kilobytes;
        }

        if (pageHitsTotal == 0l)
        {
            timeStart = current - actualWebTime;
            pageHitsTimePrevious = timeStart;
            kilobytesTimePrevious = timeStart;
        }

        if (botWebStatisticType == BotWebStatisticType.PAGEHITS
            || botWebStatisticType == BotWebStatisticType.WEBSTATISTIC)
        {
            if (pageHitsAvgTime > pageHitsMinTime)
            {
                if ((pageHitsFastCount + 2 * pageHits) >= pageHitsMaxPerHour)
                {
                    sleepTime = pageHitsMinTime * (pageHitsFastCount + pageHits) - (timeEnd - pageHitsTimePrevious);
                    pageHitsTimePrevious = timeEnd;
                }
                else
                {
                    pageHitsFastCount += pageHits;
                }
            }
            else
            {
                pageHitsTimePrevious = timeEnd;
            }
        }

        if (botWebStatisticType == BotWebStatisticType.TRAFFIC
            || botWebStatisticType == BotWebStatisticType.WEBSTATISTIC)
        {
            if (kilobytesAvgTime > kilobytesMinTime)
            {
                if ((kilobytesOverCount + 2 * kilobytes) >= kilobytesMaxPerHour)
                {
                    long tmp = kilobytesMinTime * (kilobytesOverCount + kilobytes) - (timeEnd - kilobytesTimePrevious);
                    sleepTime = sleepTime > tmp ? sleepTime : tmp;
                    kilobytesTimePrevious = timeEnd;
                }
                else
                {
                    kilobytesOverCount += kilobytes;
                }
            }
            else
            {
                kilobytesTimePrevious = timeEnd;
            }
        }
        
        if (sleepTime > 0l)
        {
            Thread.sleep(sleepTime);
        }
    }
    

    /**
     * Get current total page hits
     * 
     * @return Current total page hits
     */
    public long getPageHitsTotal()
    {
        return pageHitsTotal;
    }


    /**
     * Get current total kilobytes downloaded
     * 
     * @return Current total kilobytes downloaded
     */
    public long getKilobytesTotal()
    {
        return kilobytesTotal;
    }


    /**
     * Get current total request header bytes sent
     * 
     * @return Current total request header bytes sent
     */
    public long getBytesTotalRequestHeader()
    {
        return bytesTotalRequestHeader;
    }


    /**
     * Get current total request content bytes sent
     * 
     * @return Current total request content bytes sent
     */
    public long getBytesTotalRequestContent()
    {
        return bytesTotalRequestContent;
    }


    /**
     * Get current total response header bytes downloaded
     * 
     * @return Current total response header bytes downloaded
     */
    public long getBytesTotalResponseHeader()
    {
        return bytesTotalResponseHeader;
    }


    /**
     * Get current total response content bytes downloaded
     * 
     * @return Current total response content bytes downloaded
     */
    public long getBytesTotalResponseContent()
    {
        return bytesTotalResponseContent;
    }
    
    
    /**
     * Get current total request bytes sent
     * 
     * @return Current total request bytes sent
     */
    public long getBytesTotalRequest()
    {
        return (bytesTotalRequestHeader + bytesTotalRequestContent);
    }

    
    /**
     * Get current total response bytes downloaded
     * 
     * @return Current total response bytes downloaded
     */
    public long getBytesTotalResponse()
    {
        return (bytesTotalResponseHeader + bytesTotalResponseContent);
    }
    

    /**
     * Get start time of the first page downloaded from the web site
     * 
     * @return Start time of the first page downloaded from the web site
     */
    public long getTimeStart()
    {
        return timeStart;
    }


    /**
     * Get end time of the last or current page downloaded from the web site
     * 
     * @return End time of the last or current page downloaded from the web site
     */
    public long getTimeEnd()
    {
        return timeEnd;
    }


    /**
     * Get actual web traffic time
     * 
     * @return Actual web traffic time
     */
    public long getActualWebTime()
    {
        return actualWebTime;
    }
}
