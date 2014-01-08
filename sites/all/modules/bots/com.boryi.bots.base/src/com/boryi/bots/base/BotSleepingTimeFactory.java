package com.boryi.bots.base;

/**
 * Base class create an instance of sleeping time computer
 * 
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotSleepingTimeFactory
{
    /**
     * Get the instance to compute sleeping time according to method name
     * 
     * @param method Method to get sleeping time
     * @param base Base to compute the sleeping time
     * @param step Step to compute the sleeping time
     * @return A BotSleepingTime instance
     */
    public static BotSleepingTime getSleepingTimeMethod(
            BotRetryMethod method, long base, long step)
    {
        BotSleepingTime time = null;
        switch (method)
        {
            case LINEAR:
                time = new BotSleepingTimeLinear(base, step);
                break;
            case QUADRATIC:
                time = new BotSleepingTimeQuadratic(base, step);
                break;
            case EXPONENTIAL:
                time = new BotSleepingTimeExponential(base, step);
                break;
        }
        return time;
    }
}
