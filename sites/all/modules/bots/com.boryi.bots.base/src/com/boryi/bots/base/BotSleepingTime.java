package com.boryi.bots.base;

/**
 * The abstract class to compute sleeping time
 * 
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public abstract class BotSleepingTime 
{
    /**
     * Base to compute the sleeping time
     */
    protected long base;
    /**
     * Step to compute the sleeping time
     */
    protected long step;


    /**
     * Constructor to initialize an instatnce of BotSleepingTime
     * 
     * @param base Base to compute the sleeping time
     * @param step Step to compute the sleeping time
     */
    public BotSleepingTime(long base, long step)
    {
        this.base = base;
        this.step = step;
    }
    
    
    /**
     * Abstract method to get sleeping time
     *
     * @param retries Number of retries
     * @return long Sleeping time
     */
    public abstract long getSleepingTime(int retries);
}
