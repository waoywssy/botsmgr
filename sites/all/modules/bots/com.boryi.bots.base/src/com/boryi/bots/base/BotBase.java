package com.boryi.bots.base;

import java.util.Hashtable;
import java.util.ArrayList;
import java.sql.SQLException;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.Logger;

/**
 * Bot base class for bot
 *
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotBase 
{
    static
    {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        System.setProperty("logDir", System.getProperty("user.dir"));
        DOMConfigurator.configure("boryi.bot.log4j.xml");
    }
    /**
     * 
     */
    public static  Logger logger = Logger.getLogger("boryi.bot.logger");
    

    /**
     * Constructor to initialize an instance of BotBase
     */
    public BotBase() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException, 
            InterruptedException, Exception
    {
        prepare();
        monitor();
        finished();
    }

    
    /**
     * Prepare the run of the bot.  Write to the run table of 
     * the central database, and get the run id.  Write to the
     * event table of the central database. Get start time, clear checklist
     * or get checklist to be resumed
     */
    protected void prepare() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException, 
            InterruptedException, Exception
    {
        if (!BotDao.getInstance().getTaskStatus())
        {
            BotDao.getInstance().updateTaskStatus(true);
            BotDao.getInstance().getRun();
            if (BotRunEntity.getInstance().getRunId() == 0)
            {
                if (BotRunEntity.getInstance().getIsResume())
                {
                    throw new Exception("Cannot resume a finished or non-existing run.  Please start a new run if needed.");
                }
                else
                {
                    throw new Exception("Cannot create a run id in the central database or insert bot-run id in local database.");
                }
            }

            BotDao.getInstance().saveEvent(new BotEventEntity(
                    BotEventSeverity.INFO, "Run starts", "Run starts ...",
                    System.currentTimeMillis()));

            if (!BotRunEntity.getInstance().getIsResume())
            {
                BotChecklistEntity item = new BotChecklistEntity(
                        BotConfig.getInstance().getWebUrl(), 0, 1, 0);

                BotDao.getInstance().clearChecklist();
                            ArrayList<BotChecklistEntity> checkList = new ArrayList<BotChecklistEntity>(1);
                            checkList.add(item);
                            BotDao.getInstance().saveChecklist(checkList);
            }
            BotChecklist.getInstance().addChecklist(
                    BotDao.getInstance().getChecklist());
        }
        else
        {
            throw new Exception("Already has one task running.");
        }
    }


    /**
     * Monitor the bot thread if it is alive and business main
     * thread running too long (timeout)
     */
    protected void monitor() throws InterruptedException
    {
        BotThread.getInstance().start();
        BotThread.getInstance().join(BotConfig.getInstance().getMaxRunTime());
    }


    /**
     * The bot finishes and save the event, end time and run to central database
     */
    protected void finished() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, SQLException, 
            InterruptedException
    {
        long endTime = System.currentTimeMillis();

        BotDao.getInstance().updateTaskStatus(false);

        BotDao.getInstance().saveEvent(new BotEventEntity(
                BotEventSeverity.INFO, "Run ended", "Run ended.", endTime));
        
        if (BotRunEntity.getInstance().getIsResume())
        {
            if (BotThread.getInstance().getRunSuccess())
            {
                BotDao.getInstance().updateRun(BotRunStatus.RESUME_SUCCESS);
            }
            else 
            {
                BotDao.getInstance().updateRun(BotRunStatus.RESUME_FAILED);
            }
        }
        else
        {
            if (BotThread.getInstance().getRunSuccess())
            {
                BotDao.getInstance().updateRun(BotRunStatus.SUCCESS);
            }
            else 
            {
                BotDao.getInstance().updateRun(BotRunStatus.FAILED);
            }
        }
    }
    
    
    /**
     * Get vaules of arguments of the input
     * 
     * @param args Input arguments: botId, jobId, taskId, resume, config
     * @return Hashtable includes the params
     */
    public static Hashtable<String, Object> getArguments(String[] args)
    {
         Hashtable<String, Object> params = new Hashtable<String, Object>();
         String sServerId = "serverId";
         String sBotId = "botId";
         String sJobId = "jobId";
         String sTaskId = "taskId";
         String sIsResume = "resume";
         String sConfig = "config";

         Integer serverId = Integer.valueOf(args[0]);
         Integer botId = Integer.valueOf(args[1]);
         Integer jobId = Integer.valueOf(args[2]);
         
         String config = args[args.length - 1];
         
         Integer taskId = new Integer(-1);
         Boolean resume = Boolean.FALSE;
         
         switch (args.length)
         {
             case 5:
                 try
                 {
                     taskId = Integer.valueOf(args[3]);
                 }
                 catch (Exception ex)
                 {
                     try
                     {
                         resume = Boolean.valueOf(args[3]);
                     }
                     catch (Exception exb)
                     {
                     }
                 }
                 break;
             case 6:
                 taskId = Integer.valueOf(args[3]);
                 resume = Boolean.valueOf(args[4]);
                 break;
         }

         params.put(sServerId, serverId);
         params.put(sBotId, botId);
         params.put(sJobId, jobId);
         params.put(sTaskId, taskId);
         params.put(sIsResume, resume);
         params.put(sConfig, config);
         
         return params;
    }
}
