package com.boryi.bots.base;

import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Transport;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.*;

/**
 * Base class for bot business thread - bot's business logic
 * 
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotThread extends Thread
{
    /**
     * Java mail protocol property name
     */
    protected static final String MAIL_PROTOCOL = "mail.transport.protocol";
    /**
     * Java mail host property name
     */
    protected static final String MAIL_HOST = "mail.host";
    /**
     * Java mail content property name
     */
    protected static final String MAIL_CONTENT = "text/html";
    
    /**
     * Whether the run is success
     */
    protected boolean runSuccess = false;
    /**
     * Singleton for BotThread which can be inherited to refer the derived 
     * class instance.  Always refer to the first initialization instance.
     */
    protected static BotThread instance;
    
    /**
     * Default constructor
     * 
     */
    public BotThread()
    {
        initialize();
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
     * Singleton for BotThread which can be inherited to refer the derived 
     * class instance.  Always refer to the first initialization instance.
     * 
     * @return The first initialization instance
     */
    public static BotThread getInstance()
    {
        return instance;
    }


    /**
     * Start crawling items according to checklist, monitor the web threads,
     * check if a web thread is alive. Save events
     */
    protected void crawl() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException, 
            InterruptedException, Exception
    {
        BotDao.getInstance().saveEvent(new BotEventEntity(
                BotEventSeverity.INFO, 
                "Web crawling starts", 
                "Web crawling starts ...", 
                System.currentTimeMillis()));
        
        for (int i = 0; i < BotWebThread.getWebThreads().size(); i++)
        {
            BotWebThread.getWebThreads().get(i).start();
        }
        for (int i = 0; i < BotWebThread.getWebThreads().size(); i++)
        {
            BotWebThread.getWebThreads().get(i).join(
                    BotConfig.getInstance().getMaxRunTime());
        }
    }


    /**
     * Finish crawling items according to checklist. Save event of crawling 
     * finished, and web statistic data to local database
     */
    protected void finishCrawl() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException, 
            InterruptedException, Exception
    {
        BotDao.getInstance().saveEvent(new BotEventEntity(
                BotEventSeverity.INFO, 
                "Web crawling finished", 
                "Web crawling finished. Automatic QA starts...", 
                System.currentTimeMillis()));
        
        BotDao.getInstance().saveWebStatistic();
    }


    /**
     * Run QA and data sanity test after, and save the QA result, event,
     * and email the qa report
     */
    protected void qa() throws ClassNotFoundException, InstantiationException, 
            IllegalAccessException, SQLException, InterruptedException,
            MessagingException, Exception
    {
        BotDao.getInstance().qaComputeStatistic();
        String report = BotDao.getInstance().qaAnalyze();
        
        BotDao.getInstance().saveEvent(new BotEventEntity(
                BotEventSeverity.QA, 
                "Automatic QA report", 
                report, 
                System.currentTimeMillis()));
        
        if (report != null && !report.isEmpty() 
                && !BotConfig.getInstance().getMailProtocol().isEmpty()
                && !BotConfig.getInstance().getMailHost().isEmpty()
                && !BotConfig.getInstance().getMailTo().isEmpty())
        {
            sendMail(report);
        }
        
        BotDao.getInstance().saveEvent(new BotEventEntity(
                BotEventSeverity.INFO, 
                "Automatic finished", 
                "Automatic QA finished.", 
                System.currentTimeMillis()));
    }

    
    /**
     * Email qa report to the specified email account
     */
    protected void sendMail(String body) throws MessagingException
    {
        Properties props = new Properties();
        props.setProperty(MAIL_PROTOCOL, 
                BotConfig.getInstance().getMailProtocol());
        props.setProperty(MAIL_HOST, BotConfig.getInstance().getMailHost());

        Session mailSession = Session.getDefaultInstance(props, null);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        
        message.setSubject(BotConfig.getInstance().getMailSubject()
                + "botId: " + BotRunEntity.getInstance().getBotId()
                + " jobId: " + BotRunEntity.getInstance().getJobId()
                + " taskId: " + BotRunEntity.getInstance().getTaskId()
                + " runId: " + BotRunEntity.getInstance().getRunId()
                + " runDate: " + BotDateFormat.getDateFormat(
                    BotDateFormatType.MILLISECOND).format(
                        new Date(BotRunEntity.getInstance().getRunDate()))
                + " isResume: " + BotRunEntity.getInstance().getIsResume());
        
        message.setContent(body, MAIL_CONTENT);

        message.addRecipient(
                Message.RecipientType.TO, 
                new InternetAddress(BotConfig.getInstance().getMailTo()));

        transport.connect();
        transport.sendMessage(message, 
                message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
    
    
    /**
     * Start bot business or logic running
     */
    @Override
    public void run()
    {
        try
        {
            crawl();
            finishCrawl();
            qa();
            runSuccess = true;
        }
        catch (Exception ex)
        {
            BotBase.logger.fatal(ex.toString());            
        }
    }
    
    
    /**
     * Get whether the run is success
     * 
     * @return Whether the run is success
     */
    public boolean getRunSuccess()
    {
        return runSuccess;
    }
}
