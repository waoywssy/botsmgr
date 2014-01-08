package com.boryi.bots.base;

/**
 * Compute the sleeping time using exponential method, a0 * a1^n
 * 
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotSleepingTimeExponential extends BotSleepingTime
{
    /**
     * Constructor to initialize an instatnce of BotSleepingTimeExponential
     * 
     * @param base Base to compute the sleeping time
     * @param step Step to compute the sleeping time
     */
    public BotSleepingTimeExponential(long base, long step)
    {
        super(base, step);
    }
    
    
    @Override
    public long getSleepingTime(int retries)
    {
        return (base * Math.round(Math.pow((double)step, (double)retries)));
    }

}
