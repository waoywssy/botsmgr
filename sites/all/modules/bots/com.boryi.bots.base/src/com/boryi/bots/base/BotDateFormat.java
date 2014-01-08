package com.boryi.bots.base;

import java.text.SimpleDateFormat;

/**
 * Get BotDateFormat according to date format type
 * 
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotDateFormat 
{
    /**
     * Get date format for date and time expression
     * 
     * @param type Date format type
     * @return Date format for date and time expression
     */
    public static SimpleDateFormat getDateFormat(BotDateFormatType type)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        switch (type)
        {
            case DATE:
                format = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case HOUR:
                format = new SimpleDateFormat("yyyy-MM-dd HH");
                break;
            case MINUTE:
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                break;
            case SECOND:
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            case MILLISECOND:
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                break;
            case WEEK:
                format = new SimpleDateFormat("yyyy-MM-dd EEE HH:mm:ss.SSS");
                break;
        }
        return format;
    }
}
