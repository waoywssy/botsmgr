package com.boryi.bots.base;

/**
 * Bot event entity class bundles event subject, detail, date and time.
 *
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotEventEntity 
{
    /**
     * Event severity
     */
    protected BotEventSeverity severity;
    /**
     * Event subject
     */
    protected String subject;
    /**
     * Event detail
     */
    protected String detail;
    /**
     * Date and time event happened in milliseconds
     */
    protected long eventTime;
    
    
    /**
     * Constructor to initialize an instance of BotEventEntity
     */
    public BotEventEntity()
    {
        severity = BotEventSeverity.INFO;
        subject = null;
        detail = null;
        eventTime = 0l;
    }
    
    
    /**
     * Constructor to initialize an instance of BotEventEntity
     * 
     * @param severity Event severity
     * @param subjec Event subject
     * @param detail Event detail
     * @param eventTime Date and time event happened in milliseconds
     */
    public BotEventEntity(BotEventSeverity severity, String subject,
            String detail, long eventTime)
    {
        this.severity = severity;
        this.subject = subject;
        this.detail = detail;
        this.eventTime = eventTime;
    }

    
    /**
     * Get event severity
     * 
     * @return Event severity
     */
    public BotEventSeverity getSeverity() 
    {
        return severity;
    }

    
    /**
     * Set event severity
     * 
     * @param severity Event severity
     */
    public void setSeverity(BotEventSeverity severity) 
    {
        this.severity = severity;
    }

    
    /**
     * Get event subject
     * 
     * @return Event subject
     */
    public String getSubject() 
    {
        return subject;
    }

    
    /**
     * Set event subject
     * 
     * @param subject Event subject
     */
    public void setSubject(String subject) 
    {
        this.subject = subject;
    }

    
    /**
     * Get event detail
     * 
     * @return Event detail
     */
    public String getDetail() 
    {
        return detail;
    }

    
    /**
     * Set event detail
     * 
     * @param detail Event detail
     */
    public void setDetail(String detail) 
    {
        this.detail = detail;
    }

    
    /**
     * Get date and time event happened in milliseconds
     * 
     * @return Date and time event happened in milliseconds
     */
    public long getEventTime() 
    {
        return eventTime;
    }

    
    /**
     * Set date and time event happened in milliseconds
     * 
     * @param eventTime Date and time event happened in milliseconds
     */
    public void setEventTime(long eventTime) 
    {
        this.eventTime = eventTime;
    }
}
