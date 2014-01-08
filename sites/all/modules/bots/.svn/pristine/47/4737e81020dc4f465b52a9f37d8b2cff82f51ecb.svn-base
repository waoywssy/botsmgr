package com.boryi.bots.base;

/**
 * Bot failure retry entity bundles maximum retry times, retry method,
 * retry base, and retry step
 *
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotRetryEntity 
{
    /**
     * Maximum number of retry
     */
    protected int retry;
    /**
     * Method of retry, linear, quadratic, exponential
     */
    protected BotRetryMethod method;
    /**
     * The base sleeping time in milliseconds for retry
     */
    protected long base;
    /**
     * The step increased for sleeping time in milliseconds for retry
     */
    protected long step;
    
    
    /**
     * Constructor to initialize an instance of BotRetryEntity
     * 
     * @param retry Maximum number of retry
     * @param method Method of retry, linear, quadratic, exponential
     * @param base The base sleeping time in milliseconds for retry
     * @param step The step increased for sleeping time in milliseconds for 
     * retry
     */
    public BotRetryEntity(int retry, BotRetryMethod method,
            long base, long step)
    {
        this.retry = retry;
        this.method = method;
        this.base = base;
        this.step = step;
    }
    
    
    /**
     * Get maximum number of retry
     * 
     * @return Maximum number of retry
     */
    public int getRetry()
    {
        return retry;
    }
    
    
    /**
     * Get method of retry, linear, quadratic, exponential
     * 
     * @return Method of retry, linear, quadratic, exponential
     */
    public BotRetryMethod getMethod()
    {
        return method;
    }
    
    
    /**
     * Get the base sleeping time in milliseconds for retry
     * 
     * @return The base sleeping time in milliseconds for retry
     */
    public long getBase()
    {
        return base;
    }
    
    
    /**
     * Get the step increased for sleeping time in milliseconds for retry
     * 
     * @return The step increased for sleeping time in milliseconds for retry
     */
    public long getStep()
    {
        return step;
    }
}
